package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEvent;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEventParser;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudEventType;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.Positionssender;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.PositionssenderDTO;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.PositionssenderRepository;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.tracking.TrackerDto;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.tracking.TrackingEventDto;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class PositionssenderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(PositionssenderEventConsumer.class);

    private final ObjectMapper objectMapper;
    private final PositionssenderRepository positionssenderRepository;
    private final PositionssenderEventInformationRepository positionssenderEventInformationRepository;


    @Autowired
    public PositionssenderEventConsumer(final ObjectMapper objectMapper, PositionssenderEventInformationRepository positionssenderEventInformationRepository, PositionssenderRepository positionssenderRepository) {
        this.objectMapper = objectMapper;
        this.positionssenderEventInformationRepository = positionssenderEventInformationRepository;
        this.positionssenderRepository = positionssenderRepository;
    }

    @KafkaListener(topics = "${positionssender.topic}", groupId = "${spring.kafka.group-id}", autoStartup = "${spring.kafka.enabled}")
    public void listen(String message) throws IOException {
        val crudDomainEvent = this.objectMapper.readValue(message, CrudDomainEvent.class);
        val positionssenderDTO = new CrudDomainEventParser<PositionssenderDTO>()
                .parse(message, PositionssenderDTO.class);
        val positionssenderEntity = Positionssender.convert(positionssenderDTO);

        positionssenderRepository.save(positionssenderEntity);
        positionssenderEventInformationRepository.save(
                new PositionssenderEventInformation(crudDomainEvent.getEventType(), new Date())
        );

        log.info("**\n Positionssender {} \n**", crudDomainEvent.getEventType());
    }

    @KafkaListener(topics = "${tracker.topic}", groupId = "${spring.kafka.group-id}")
    public void listenToTracking(String message) throws IOException {
        val trackingEventDto = objectMapper.readValue(message, TrackingEventDto.class);
        val tracker = trackingEventDto.getTrackerDto();
        val positionssenderOpt = positionssenderRepository.findById(tracker.getTrackerId());

        if (positionssenderOpt.isPresent()) {
            val positionssender = positionssenderOpt.get();

            positionssender.setPosition(TrackerDto.TrackerPositionsDTO.convert(tracker.getPositionDTO()));
            positionssender.setLetztesSignal(trackingEventDto.getTime());

            positionssenderEventInformationRepository.save(
                    new PositionssenderEventInformation(CrudEventType.UPDATED.name(), new Date())
            );

            positionssenderRepository.save(positionssender);


            // TODO implement business logic --> out of zone?
        } else {
            log.warn("Could not find Tracker with id {}", tracker.getTrackerId());
            return;
        }

        log.info("Updated Tracker: {}", tracker.getTrackerId());
    }
}
