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
            @Value("${spring.kafka.topic.producer.zonenabweichung}") String topic,
            KafkaMessageProducer kafkaMessageProducer
    ) {
        this.topic = topic;
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    @PostPersist
    public void handleZonenAbweichungCreate(ZonenAbweichung zonenAbweichung)
            throws JsonProcessingException {
        this.kafkaMessageProducer.send(
                this.topic,
                buildZonenAbweichungEvent(zonenAbweichung, CrudEventType.CREATED)
        );
    }

    @PostUpdate
    public void handleZonenAbweichungSave(ZonenAbweichung zonenAbweichung)
            throws JsonProcessingException {
        this.kafkaMessageProducer.send(
                this.topic,
                buildZonenAbweichungEvent(zonenAbweichung, CrudEventType.UPDATED)
        );
    }

    @PostRemove
    public void handleZonenAbweichungDelete(ZonenAbweichung zonenAbweichung)
            throws JsonProcessingException {
        this.kafkaMessageProducer.send(
                this.topic,
                buildZonenAbweichungEvent(zonenAbweichung, CrudEventType.DELETED)
        );
    }

    private ZonenAbweichungEvent buildZonenAbweichungEvent(
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
