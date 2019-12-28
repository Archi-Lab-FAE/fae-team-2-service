package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterRepository;
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
        DemenziellErkrankterDomainEvent demenziellErkrankterDomainEvent = this.objectMapper
                .readValue(message, DemenziellErkrankterDomainEvent.class);

//        DemenziellErkrankter demenziellErkrankter = this.objectMapper.readValue(message, DemenziellErkrankter.class);
//        System.out.println("MSG: "+ message);
//        System.out.println(demenziellErkrankter);
//        demenziellErkrankterRepository.save(demenziellErkrankter);

        demenziellErkrankterEventInformationRepository.save(
                new DemenziellErkrankterEventInformation(demenziellErkrankterDomainEvent.getEventType(), new Date())
        );

        System.out.println("*****************************");
        System.out.println("DemenziellErkrankter " + demenziellErkrankterDomainEvent.getEventType());
        System.out.println("*****************************");
    }
}
