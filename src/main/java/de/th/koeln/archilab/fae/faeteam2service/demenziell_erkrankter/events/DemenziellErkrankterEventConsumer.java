package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterRepository;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEvent;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEventParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

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

    @KafkaListener(topics = "${demenziellerkrankter.topic}", groupId = "${spring.kafka.group-id}")
    public void listen(String message) throws IOException {
        CrudDomainEvent crudDomainEvent = this.objectMapper
                .readValue(message, CrudDomainEvent.class);


        // TODO use DTO for parsing and then convert to entity to safe the data (todo in all consumers)
        demenziellErkrankterRepository.save(
                new CrudDomainEventParser<DemenziellErkrankter>().parse(message, DemenziellErkrankter.class)
        );
        demenziellErkrankterEventInformationRepository.save(
                new DemenziellErkrankterEventInformation(crudDomainEvent.getEventType(), new Date())
        );

        log.info("*****************************");
        log.info("DemenziellErkrankter {}", crudDomainEvent.getEventType());
        log.info("*****************************");
    }
}
