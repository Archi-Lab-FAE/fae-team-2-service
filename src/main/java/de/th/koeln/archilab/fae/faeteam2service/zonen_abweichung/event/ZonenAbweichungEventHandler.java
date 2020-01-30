package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.th.koeln.archilab.fae.faeteam2service.kafka.config.KafkaMessageProducer;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudEventType;
import de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.ZonenAbweichung;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class ZonenAbweichungEventHandler {

    private final String topic;
    private final KafkaMessageProducer kafkaMessageProducer;
    private static final int EVENT_VERSION = 1;

    @Autowired
    public ZonenAbweichungEventHandler(
            @Value("${zonenabweichung.topic}") String topic,
            KafkaMessageProducer kafkaMessageProducer
    ) {
        this.topic = topic;
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    @PostPersist
    public void handleZonenAusnahmeCreate(ZonenAbweichung zonenAbweichung)
            throws JsonProcessingException {
        this.kafkaMessageProducer.send(
                this.topic,
                buildZonenAusnahmeEvent(zonenAbweichung, CrudEventType.CREATED)
        );
    }

    @PostUpdate
    public void handleZonenAusnahmeSave(ZonenAbweichung zonenAbweichung)
            throws JsonProcessingException {
        this.kafkaMessageProducer.send(
                this.topic,
                buildZonenAusnahmeEvent(zonenAbweichung, CrudEventType.UPDATED)
        );
    }

    @PostRemove
    public void handleZonenAusnahmeDelete(ZonenAbweichung zonenAbweichung)
            throws JsonProcessingException {
        this.kafkaMessageProducer.send(
                this.topic,
                buildZonenAusnahmeEvent(zonenAbweichung, CrudEventType.DELETED)
        );
    }

    private ZonenAbweichungEvent buildZonenAusnahmeEvent(
            ZonenAbweichung zonenAbweichung,
            CrudEventType eventType
    ) {
        return new ZonenAbweichungEvent(
                zonenAbweichung.getZonenAusnahmeId(),
                EVENT_VERSION,
                OffsetDateTime.now(ZoneOffset.UTC).toString(),
                eventType,
                ZonenAbweichung.convert(zonenAbweichung)
        );
    }
}
