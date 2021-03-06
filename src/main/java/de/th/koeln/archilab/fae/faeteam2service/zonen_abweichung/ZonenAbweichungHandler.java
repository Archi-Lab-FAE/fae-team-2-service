package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.stream.StreamSupport;

/**
 * Component for handling synchronous calls of {@link ZonenAbweichung} to the messaging microservice
 * with retry functionality.
 */
@Component
public class ZonenAbweichungHandler {

    private static final Logger log = LoggerFactory.getLogger(ZonenAbweichungHandler.class);

    @Value("${messaging.service-post-url}")
    private String messagingServiceUrl;

    @Value("${messaging.maxAgeForRetry}")
    private long maxAgeForRetry;

    private ZonenAbweichungRepository abweichungRepository;

    private RestTemplate restTemplate = new RestTemplate();


    @Autowired
    public ZonenAbweichungHandler(ZonenAbweichungRepository abweichungRepository) {
        this.abweichungRepository = abweichungRepository;
    }


    /**
     * Periodically tries to send all {@link ZonenAbweichung} with
     * {@link ZonenAbweichung#isAbgeschlossen()} set to false.
     */
    @Scheduled(initialDelay = 30000L, fixedDelayString = "${messaging.delayBetweenRetry}")
    private void sendZonenAbweichung() {
        val ausnahmeSpliterator = abweichungRepository.findAllByAbgeschlossenFalse().spliterator();
        val maxDate = LocalDateTime.now().plusMinutes(maxAgeForRetry);

        //"Remove" all entries which are older then the given threshold
        //and try resend all others
        StreamSupport.stream(ausnahmeSpliterator, false).forEach(zonenAusnahme -> {
            log.info("Handling open ZonenAbweichung with ID {}", zonenAusnahme.getZonenAbweichungId());

            if (zonenAusnahme.getEntstanden().isBefore(maxDate)) {
                zonenAusnahme.setAbgeschlossen(true);
                abweichungRepository.save(zonenAusnahme);
            } else {
                sendZonenAbweichung(zonenAusnahme);
            }
        });
    }

    /**
     * Sends a {@link ZonenAbweichung} to the messaging service.
     * The {@link ZonenAbweichung} will get saved into the {@link ZonenAbweichungRepository}
     * regardless of if the sending was successful or not.
     *
     * @param zonenAbweichung Abweichung to be send.
     */
    public void sendZonenAbweichung(ZonenAbweichung zonenAbweichung) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        ZonenAbweichungDTO body = ZonenAbweichung.convert(zonenAbweichung);

        log.info("ZonenAbweichung senden." );

        //Send the request
        ResponseEntity<String> response = null;
        try {
            HttpEntity<ZonenAbweichungDTO> request = new HttpEntity<>(body, headers);
            response = restTemplate.postForEntity(messagingServiceUrl, request, String.class);
        } catch (RestClientException e) {
            log.warn("ZonenAbweichung konnte nicht gesendet werden!");
        }
        if (response != null) zonenAbweichung.setAbgeschlossen(response.getStatusCode().is2xxSuccessful());

        //If successful, change the flag in the database
        abweichungRepository.save(zonenAbweichung);
    }
}
