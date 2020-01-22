package de.th.koeln.archilab.fae.faeteam2service.zone.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.th.koeln.archilab.fae.faeteam2service.kafka.config.KafkaMessageProducer;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudDomainEvent;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudEventType;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Component
public class ZoneEventHandler {

    private final String topic;
    private final KafkaMessageProducer kafkaMessageProducer;
    private static final int EVENT_VERSION = 1;

    @Autowired
    public ZoneEventHandler(
            @Value("${zone.topic}") final String topic,
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

    private CrudDomainEvent buildZoneDomainEvent(
            ZoneDTO zoneDTO,
            CrudEventType eventType
    ) {
        return new CrudDomainEvent<>(
                zoneDTO, eventType, zoneDTO.getZoneId(), EVENT_VERSION, OffsetDateTime.now(ZoneOffset.UTC).toString()
        );
    }
}
