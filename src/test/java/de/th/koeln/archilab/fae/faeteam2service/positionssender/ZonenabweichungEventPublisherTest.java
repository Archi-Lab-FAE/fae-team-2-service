package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.ZonenabweichungEvent;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.ZonenabweichungKafkaGateway;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.ZonenabweichungKafkaPublisher;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.ZonenabweichungMessage;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertFalse;
import java.time.Clock;
import java.time.OffsetDateTime;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@EmbeddedKafka
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class ZonenabweichungEventPublisherTest {

    @Autowired
    ZonenabweichungKafkaPublisher publisher;

    @Autowired
    ZonenabweichungKafkaGateway gateway;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void publishZonenabweichungTest(){
        String msg = "Achtung, Hans hat eine ungewohnte Zone betreten.";

        Positionssender positionssender = new Positionssender();
        publisher.publishZonenabweichung(positionssender,msg);
    }

    @Test
    public void createZonenabweichungEvent(){
        String msg = "Achtung, Hans hat eine ungewohnte Zone betreten.";
        Position position = new Position(44.0,44.0);
        Positionssender positionssender = new Positionssender();
        positionssender.setPosition(position);
        boolean exception = false;

        ZonenabweichungMessage message = new ZonenabweichungMessage(positionssender.getPositionssenderId(), position,msg);
        ZonenabweichungEvent event = new ZonenabweichungEvent(positionssender.getPositionssenderId(),1, OffsetDateTime.now(Clock.systemUTC()).toString(),message);
        try{
            objectMapper.writeValueAsString(event);
        }catch(JsonProcessingException e){
            exception = true;
        }

        assertFalse(exception);
    }
}
