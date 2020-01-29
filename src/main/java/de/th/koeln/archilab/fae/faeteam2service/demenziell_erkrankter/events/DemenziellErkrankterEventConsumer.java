package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterDTO;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterRepository;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEvent;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEventParser;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * The DemenziellErkrankterEventConsumer consumes all events of the topic demenziellerkrankter,
 * which are published by Team-1 and persistently saves them in the database.
 */
@Component
public class DemenziellErkrankterEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(DemenziellErkrankterEventConsumer.class);
    private final ObjectMapper objectMapper;

    private final DemenziellErkrankterEventInformationRepository demenziellErkrankterEventInformationRepository;

    private final DemenziellErkrankterRepository demenziellErkrankterRepository;


    @Autowired
    public DemenziellErkrankterEventConsumer(
            final ObjectMapper objectMapper,
            DemenziellErkrankterEventInformationRepository demenziellErkrankterEventInformationRepository,
            DemenziellErkrankterRepository demenziellErkrankterRepository) {
        this.objectMapper = objectMapper;
        this.demenziellErkrankterEventInformationRepository = demenziellErkrankterEventInformationRepository;
        this.demenziellErkrankterRepository = demenziellErkrankterRepository;
    }

    /**
     * Consume all events for the topic demenziellerkrankter and save them in the database.
     * @param message json message of the event
     * @throws IOException If the message cannot be parsed into an object.
     */
    @KafkaListener(topics = "${demenziellerkrankter.topic}", groupId = "${spring.kafka.group-id}", autoStartup = "${spring.kafka.enabled}")
    public void listen(String message) throws IOException {
        CrudDomainEvent crudDomainEvent = this.objectMapper
                .readValue(message, CrudDomainEvent.class);

        val demenziellErkrankterDTO = new CrudDomainEventParser<DemenziellErkrankterDTO>()
                .parse(message, DemenziellErkrankterDTO.class);
        val demenziellErkrankterEntity = DemenziellErkrankter.convert(demenziellErkrankterDTO);
        demenziellErkrankterRepository.save(demenziellErkrankterEntity);

        demenziellErkrankterEventInformationRepository.save(
                new DemenziellErkrankterEventInformation(crudDomainEvent.getId(), crudDomainEvent.getType(), new Date())
        );

        log.info("*****************************");
        log.info("DemenziellErkrankter {}", crudDomainEvent.getType());
        log.info("*****************************");
    }
}
