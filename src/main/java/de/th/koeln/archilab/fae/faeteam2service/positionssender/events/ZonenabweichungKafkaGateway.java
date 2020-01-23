package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class ZonenabweichungKafkaGateway {

    private static final Logger log = LoggerFactory.getLogger(ZonenabweichungKafkaGateway.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topic;

    @Autowired
    public ZonenabweichungKafkaGateway(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper,
            @Value("${zone.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }

    public ListenableFuture<SendResult<String, String>> publishZonenabweichungEvent(
            final ZonenabweichungEvent zonenabweichungEvent
    ) {
        log.info("publishing event {} to topic {}", zonenabweichungEvent.getId(), topic);
        return kafkaTemplate.send(topic, zonenabweichungEvent.getKey(), toMessageString(zonenabweichungEvent));
    }

    private String toMessageString(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (final JsonProcessingException e) {
            log.error("Could not serialize event {}", event.toString(), e);
            return "";
        }
    }
}
