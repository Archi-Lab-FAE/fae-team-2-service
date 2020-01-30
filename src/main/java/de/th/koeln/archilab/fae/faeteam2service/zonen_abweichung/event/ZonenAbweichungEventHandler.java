package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.th.koeln.archilab.fae.faeteam2service.BeanUtil;
import de.th.koeln.archilab.fae.faeteam2service.kafka.config.KafkaMessageProducer;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudEventType;
import de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.ZonenAbweichung;
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

@Component
public class ZonenAbweichungEventHandler {

    private final String topic;
    private final KafkaMessageProducer kafkaMessageProducer;
    private static final int EVENT_VERSION = 1;

    private List<ZonenAbweichungEventInformation> eventInformationList = new ArrayList<>();

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

    /**
     * Build a {@link ZonenAbweichungEvent} based on the {@link ZonenAbweichung} parameter
     * and saves {@link ZonenAbweichungEventInformation} in {@link ZonenAbweichungEventRepository}
     * @return {@link ZonenAbweichungEvent} based on the {@link ZonenAbweichung} parameter
     */
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

    /**
     * Backs up all {@link ZonenAbweichungEventInformation} to the corresponding repository.
     *
     * This cannot be done directly when the event is transmitted, otherwise there is a circular dependency.
     * The problem occurs between the associated repository and the event repository.
     * Both use the EntityManger.
     */
    @Scheduled(initialDelay = 30000L, fixedDelay = 15000)
    private void saveEvents() {
        val repository = BeanUtil.getBean(ZonenAbweichungEventRepository.class);
        repository.saveAll(eventInformationList);
    }
}
