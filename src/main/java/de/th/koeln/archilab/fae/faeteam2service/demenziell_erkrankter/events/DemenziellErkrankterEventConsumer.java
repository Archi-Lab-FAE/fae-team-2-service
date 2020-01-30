package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterDTO;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterRepository;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEvent;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEventParser;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.Positionssender;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.PositionssenderRepository;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
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


    private final PositionssenderRepository positionssenderRepository;


    @Autowired
    public DemenziellErkrankterEventConsumer(
            final ObjectMapper objectMapper,
            DemenziellErkrankterEventInformationRepository demenziellErkrankterEventInformationRepository,
            DemenziellErkrankterRepository demenziellErkrankterRepository,
            PositionssenderRepository positionssenderRepository
    ) {
        this.objectMapper = objectMapper;
        this.demenziellErkrankterEventInformationRepository = demenziellErkrankterEventInformationRepository;
        this.demenziellErkrankterRepository = demenziellErkrankterRepository;
        this.positionssenderRepository = positionssenderRepository;
    }

    /**
     * Consume all events for the topic demenziellerkrankter and save them in the database.
     * demenziellerkrankter events contain information about {@link DemenziellErkrankter} as well as
     * {@link Positionssender}.
     * @param message json message of the event
     * @throws IOException If the message cannot be parsed into an object.
     */
    @KafkaListener(topics = "${spring.kafka.topic.consumer.demenziellerkrankter}", groupId = "${spring.kafka.group-id}", autoStartup = "${spring.kafka.enabled}")
    public void listen(String message) throws IOException {
        CrudDomainEvent crudDomainEvent = this.objectMapper
                .readValue(message, CrudDomainEvent.class);

        val demenziellErkrankterDTO = new CrudDomainEventParser<DemenziellErkrankterDTO>()
                .parse(message, DemenziellErkrankterDTO.class);
        val demenziellErkrankterEntity = DemenziellErkrankter.convert(demenziellErkrankterDTO);
        demenziellErkrankterRepository.save(demenziellErkrankterEntity);



        val positionssenderDTOList = demenziellErkrankterDTO.getPositionssender();
        val positionssenderList = new ArrayList<Positionssender>();
        positionssenderDTOList.forEach(it -> {
            val positionssender = Positionssender.convert(it);
            positionssender.setDemenziellErkrankter(demenziellErkrankterEntity);
            positionssenderList.add(positionssender);
        });
        positionssenderRepository.saveAll(positionssenderList);

        demenziellErkrankterEventInformationRepository.save(
                new DemenziellErkrankterEventInformation(crudDomainEvent.getId(), crudDomainEvent.getType(), new Date())
        );

        log.info("*****************************");
        log.info("DemenziellErkrankter {}", crudDomainEvent.getType());
        log.info("*****************************");
    }
}
