package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudEventType;
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

/**
 * The PositionssenderEventConsumer is used to consume all events of the topic tracker and to save them
 * persistently into the database.
 */
@Component
public class PositionssenderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(PositionssenderEventConsumer.class);

    private final ObjectMapper objectMapper;
    private final PositionssenderRepository positionssenderRepository;
    private final PositionssenderEventInformationRepository positionssenderEventInformationRepository;


    @Autowired
    public PositionssenderEventConsumer(final ObjectMapper objectMapper,
                                        PositionssenderEventInformationRepository positionssenderEventInformationRepository,
                                        PositionssenderRepository positionssenderRepository) {
        this.objectMapper = objectMapper;
        this.positionssenderEventInformationRepository = positionssenderEventInformationRepository;
        this.positionssenderRepository = positionssenderRepository;
    }

    /**
     * This function consumes all events for the topic tracker and saves them into the database.
     * @param message json message of the event
     * @throws IOException if the message cannot be parsed into an object
     */
    @KafkaListener(topics = "${spring.kafka.topic.consumer.tracker}", groupId = "${spring.kafka.group-id}", autoStartup = "${spring.kafka.enabled}")
    public void listenToTracking(String message) throws IOException {
        val trackingEventDto = objectMapper.readValue(message, TrackingEventDto.class);
        val tracker = trackingEventDto.getTrackerDto();
        val positionssenderOpt = positionssenderRepository.findById(tracker.getTrackerId());

        if (positionssenderOpt.isPresent()) {
            val positionssender = positionssenderOpt.get();

            positionssender.setPosition(TrackerDto.TrackerPositionsDTO.convert(tracker.getPositionDTO()));
            positionssender.setLetztesSignal(trackingEventDto.getTime());

            positionssenderEventInformationRepository.save(
                    new PositionssenderEventInformation(trackingEventDto.getId(), CrudEventType.UPDATED.name(), new Date())
            );

            positionssenderRepository.save(positionssender);
        } else {
            log.warn("Could not find Tracker with id {}", tracker.getTrackerId());
            return;
        }
        log.info("Updated Tracker: {}", tracker.getTrackerId());
    }
}
