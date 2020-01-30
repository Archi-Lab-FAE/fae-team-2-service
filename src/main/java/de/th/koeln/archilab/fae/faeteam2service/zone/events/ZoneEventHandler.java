package de.th.koeln.archilab.fae.faeteam2service.zone.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.th.koeln.archilab.fae.faeteam2service.BeanUtil;
import de.th.koeln.archilab.fae.faeteam2service.kafka.config.KafkaMessageProducer;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEvent;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudEventType;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneDTO;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is responsable for sending kafka events when a {@link Zone} gets changed and
 * saved in the {@link de.th.koeln.archilab.fae.faeteam2service.zone.ZoneRepository}.
 */
@Component
public class ZoneEventHandler {

    private final String topic;
    private final KafkaMessageProducer kafkaMessageProducer;
    private static final int EVENT_VERSION = 1;

    private List<ZoneEventInformation> eventInformationList = new ArrayList<>();

    @Autowired
    public ZoneEventHandler(
            @Value("${spring.kafka.topic.producer.zone}") final String topic,
            final KafkaMessageProducer kafkaMessageProducer
    ) {
        this.kafkaMessageProducer = kafkaMessageProducer;
        this.topic = topic;
    }

    @PostPersist
    public void handleZoneCreate(Zone zone)
            throws JsonProcessingException {
        this.kafkaMessageProducer.send(
                this.topic,
                buildZoneDomainEvent(Zone.convert(zone), CrudEventType.CREATED)
        );
    }

    @PostUpdate
    public void handleZoneSave(Zone zone)
            throws JsonProcessingException {
        this.kafkaMessageProducer.send(
                this.topic,
                buildZoneDomainEvent(Zone.convert(zone), CrudEventType.UPDATED)
        );
    }

    @PostRemove
    public void handleZoneDelete(Zone zone)
            throws JsonProcessingException {
        this.kafkaMessageProducer.send(
                this.topic,
                buildZoneDomainEvent(Zone.convert(zone), CrudEventType.DELETED)
        );
    }

    /**
     * Build a {@link CrudDomainEvent} based on the {@link ZoneDTO} parameter
     * and saves {@link ZoneEventInformation} in {@link ZoneEventInformationRepository}
     * @return {@link CrudDomainEvent} based on the {@link ZoneDTO} parameter
     */
    private CrudDomainEvent<ZoneDTO> buildZoneDomainEvent(
            ZoneDTO zoneDTO,
            CrudEventType eventType
    ) {
        val event = new CrudDomainEvent<>(
                zoneDTO, eventType, zoneDTO.getZoneId(), EVENT_VERSION, OffsetDateTime.now(ZoneOffset.UTC).toString()
        );

        val eventInformation = new ZoneEventInformation(event.getType(), event.getTimestamp());
        eventInformationList.add(eventInformation);

        return event;
    }

    /**
     * Backs up all {@link ZoneEventInformation} to the corresponding repository.
     *
     * This cannot be done directly when the event is transmitted, otherwise there is a circular dependency.
     * The problem occurs between the associated repository and the event repository.
     * Both use the EntityManger.
     */
    @Scheduled(initialDelay = 30000L, fixedDelay = 15000)
    private void saveEvents() {
        val repository = BeanUtil.getBean(ZoneEventInformationRepository.class);
        repository.saveAll(eventInformationList);
    }
}
