package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEvent;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEventParser;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.Positionssender;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.PositionssenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class PositionssenderEventConsumer {

    private final ObjectMapper objectMapper;

    @Autowired
    private PositionssenderEventInformationRepository positionssenderEventInformationRepository;

    @Autowired
    private PositionssenderRepository positionssenderRepository;


    @Autowired
    public PositionssenderEventConsumer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${positionssender.topic}", groupId = "${spring.kafka.group-id}")
    public void listen(String message) throws IOException {
        CrudDomainEvent crudDomainEvent = this.objectMapper
                .readValue(message, CrudDomainEvent.class);

        positionssenderRepository.save(
                new CrudDomainEventParser<Positionssender>().parse(message, Positionssender.class)
        );
        positionssenderEventInformationRepository.save(
                new PositionssenderEventInformation(crudDomainEvent.getEventType(), new Date())
        );

        System.out.println("*****************************");
        System.out.println("Positionssender " + crudDomainEvent.getEventType());
        System.out.println("*****************************");
    }
}
