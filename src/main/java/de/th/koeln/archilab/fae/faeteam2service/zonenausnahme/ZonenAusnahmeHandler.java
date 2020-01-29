package de.th.koeln.archilab.fae.faeteam2service.zonenausnahme;

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
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.stream.StreamSupport;

import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.ZonenabweichungMessage;
import lombok.val;

/**
 * Component for handling synchronous calls of {@link ZonenAusnahme} to the messaging microservice
 * with retry functionality.
 */
@Component
public class ZonenAusnahmeHandler {

    private static final Logger log = LoggerFactory.getLogger(ZonenAusnahmeHandler.class);

    @Value("${messaging.service-post-url}")
    private String messagingServiceUrl;

    @Value("${messaging.maxAgeForRetry}")
    private long maxAgeForRetry;

    private ZonenAusnahmeRepository ausnahmeRepository;

    private RestTemplate restTemplate = new RestTemplate();


    @Autowired
    public ZonenAusnahmeHandler(ZonenAusnahmeRepository ausnahmeRepository) {
        this.ausnahmeRepository = ausnahmeRepository;
    }


    /**
     * Periodically tries to send all {@link ZonenAusnahme} with
     * {@link ZonenAusnahme#isAbgeschlossen()} set to false.
     */
    @Scheduled(initialDelay = 30000L, fixedDelayString = "${messaging.delayBetweenRetry}")
    private void sendZonenAusnahmen() {
        log.info("Handling all open ZonenAusnahmen...");
        val ausnahmeSpliterator = ausnahmeRepository.findAllByAbgeschlossenFalse().spliterator();
        val maxDate = LocalDateTime.now().plusMinutes(maxAgeForRetry);

        //"Remove" all entries which are older then the given threshold
        //and try resend all others
        StreamSupport.stream(ausnahmeSpliterator, false).forEach(zonenAusnahme -> {
            if (zonenAusnahme.getEntstanden().isBefore(maxDate)) {
                zonenAusnahme.setAbgeschlossen(true);
                ausnahmeRepository.save(zonenAusnahme);
            } else {
                sendZonenAusnahme(zonenAusnahme);
            }
        });
    }

    /**
     * Sends a {@link ZonenAusnahme} to the messaging service.
     * The {@link ZonenAusnahme} will get saved into the {@link ZonenAusnahmeRepository}
     * regardless of if the sending was successful or not.
     *
     * @param zonenAusnahme Ausnahme to be send.
     */
    public void sendZonenAusnahme(ZonenAusnahme zonenAusnahme) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        //TODO Convert ZonenAusnahme zu dem, was die Schnittstelle will...
        //Das hier ist nur ein Platzhalter
        ZonenabweichungMessage body = new ZonenabweichungMessage(null, null, null);

        //Send the request
        HttpEntity<ZonenabweichungMessage> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(messagingServiceUrl, request, String.class);

        //If successful, change the flag in the database
        zonenAusnahme.setAbgeschlossen(response.getStatusCode().is2xxSuccessful());
        ausnahmeRepository.save(zonenAusnahme);
    }
}
