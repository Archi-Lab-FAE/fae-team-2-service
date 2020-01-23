package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events.DemenziellErkrankterEventConsumer;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events.DemenziellErkrankterEventInformationRepository;
import lombok.val;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@EmbeddedKafka
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class DemenziellErkrankterEventConsumerTest {

    @Autowired
    DemenziellErkrankterRepository entityRepository;

    @Autowired
    DemenziellErkrankterEventConsumer consumer;

    @Autowired
    DemenziellErkrankterEventInformationRepository eventRepository;

    @Test
    public void testEventConsumption() {
        val eventId  = "5bc9f935-32f1-4d7b-a90c-ff0e6e34125a";
        val entityId = "5bc9f935-32f1-4d7b-a90c-ff0e6e34125b";

        val msg = "{\n" +
                "    \"id\": \""+eventId+"\",\n" +
                "    \"key\": \""+entityId+"\",\n" +
                "    \"version\": \"1\",\n" +
                "    \"timestamp\": \"2020-01-10T12:00:00Z\",\n" +
                "    \"type\":\"CREATED\",\n" +
                "    \"payload\": {\n" +
                "        \"demenziellErkrankterId\": \""+entityId+"\",\n" +
                "        \"name\": \"Hans Peter\",\n" +
                "        \"zonen\": []\n" +
                "    }\n" +
                "}";

        var exception = false;
        try {
            consumer.listen(msg);
        } catch (IOException e) {
            e.printStackTrace();
            exception = true;
        }

        assertFalse(exception);

        val entity = entityRepository.findById(entityId);
        var entityPresent = false;

        if (entity.isPresent()) {
            assertEquals(entityId, entity.get().getDemenziellErkrankterId());
            entityPresent = true;
        }
        assertTrue(entityPresent);

        val event = eventRepository.findById(eventId);
        var eventPresent = false;

        if (event.isPresent()) {
            assertEquals(eventId, event.get().getDemenziellErkrankterEventId());
            eventPresent = true;
        }
        assertTrue(eventPresent);
    }
}
