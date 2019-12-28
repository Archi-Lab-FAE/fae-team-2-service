package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterRepository;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class DemenziellErkrankterEventConsumer {

    private final ObjectMapper objectMapper;

    @Autowired
    private DemenziellErkrankterEventInformationRepository demenziellErkrankterEventInformationRepository;

    @Autowired
    private DemenziellErkrankterRepository demenziellErkrankterRepository;


    @Autowired
    public DemenziellErkrankterEventConsumer(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${demenziellerkrankter.topic}", groupId = "${spring.kafka.group-id}")
    public void listen(String message) throws IOException {
        CrudDomainEvent crudDomainEvent = this.objectMapper
                .readValue(message, CrudDomainEvent.class);

        // read and save data --> needed because you can't get the class of a generic typed class
        // alternative would be new DomainEvent classes for every entity
        String json = objectMapper.readTree(message).get("eventData").toString();
        DemenziellErkrankter demenziellErkrankter = new ObjectMapper()
                .readerFor(DemenziellErkrankter.class)
                .readValue(json);

        demenziellErkrankterRepository.save(demenziellErkrankter);
        demenziellErkrankterEventInformationRepository.save(
                new DemenziellErkrankterEventInformation(crudDomainEvent.getEventType(), new Date())
        );

        System.out.println("*****************************");
        System.out.println("DemenziellErkrankter " + crudDomainEvent.getEventType());
        System.out.println("*****************************");
    }
}
