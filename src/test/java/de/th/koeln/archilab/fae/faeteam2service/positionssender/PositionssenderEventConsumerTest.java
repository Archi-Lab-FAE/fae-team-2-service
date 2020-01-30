package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.threeten.bp.Clock;
import org.threeten.bp.OffsetDateTime;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudEventType;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.position.PositionDTO;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.PositionssenderEventConsumer;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.PositionssenderEventInformation;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.PositionssenderEventInformationRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@EmbeddedKafka
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class PositionssenderEventConsumerTest {

    @Autowired
    PositionssenderRepository entityRepository;

    @Autowired
    PositionssenderEventConsumer consumer;

    @Autowired
    PositionssenderEventInformationRepository eventRepository;

    @Test
    public void testEventConsumption(){
        String eventId  = "5bc9f935-32f1-4d7b-a90c-ff0e6e34125c";
        String entityId = "5bc9f935-32f1-4d7b-a90c-ff0e6e34125d";

        Positionssender positionssender = new Positionssender(
                OffsetDateTime.now(Clock.systemUTC()),
                OffsetDateTime.now(Clock.systemUTC()),
                new Position(30.0, 55.0)
        );
        positionssender.setPositionssenderId(entityId);
        entityRepository.save(positionssender);

        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setLaengengrad(1.0);
        positionDTO.setBreitengrad(2.5);


        String msg = "{\n"+
                "    \"id\": \""+eventId+"\",\n" +
                "    \"time\": \"2020-01-10T12:00:00Z\",\n" +
                "    \"type\":\"CREATED\",\n" +
                "    \"version\": \"1\",\n" +
                "    \"key\": \""+entityId+"\",\n" +
                "    \"payload\": {\n" +
                "       \"trackerId\": \""+entityId+"\",\n" +
                "       \"currentPosition\": {\n" +
                "             \"longitude\": 2.0,\n" +
                "             \"latitude\": 3.0,\n" +
                "             \"altitude\": 0.0\n" +
                "       }\n" +
                "    }\n" +
                "}";



        //Test for listen function
        boolean exception = false;

        try {
            consumer.listenToTracking(msg);
        } catch (IOException e) {
            e.printStackTrace();
            exception = true;
        }

        assertFalse(exception);

        //Test if entity is present
        Optional<Positionssender> entity = entityRepository.findById(entityId);
        boolean entityPresent = false;

        if (entity.isPresent()) {
            assertEquals(entityId, entity.get().getPositionssenderId());
            entityPresent = true;
        }
        assertTrue(entityPresent);

        eventRepository.save(new PositionssenderEventInformation(eventId, CrudEventType.UPDATED.name(), new Date()));

        Optional<PositionssenderEventInformation> event = eventRepository.findById(eventId);
        boolean eventPresent = false;

        if (event.isPresent()) {
            assertEquals(eventId, event.get().getPositionssenderEventId());
            eventPresent = true;
        }
        assertTrue(eventPresent);

    }
    @Test
    public void testEventConsumptionMitNichtExistierendemTracker(){
        String eventId  = "5bc9f935-32f1-4d7b-a90c-ff0e6e34125e";
        String entityId = "5bc9f935-32f1-4d7b-a90c-ff0e6e34125f";

        Positionssender positionssender = new Positionssender(
                OffsetDateTime.now(Clock.systemUTC()),
                OffsetDateTime.now(Clock.systemUTC()),
                new Position(30.0, 55.0)
        );
        positionssender.setPositionssenderId(entityId);

        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setLaengengrad(1.0);
        positionDTO.setBreitengrad(2.5);


        String msg = "{\n"+
                "    \"id\": \""+eventId+"\",\n" +
                "    \"time\": \"2020-01-10T12:00:00Z\",\n" +
                "    \"type\":\"CREATED\",\n" +
                "    \"version\": \"1\",\n" +
                "    \"key\": \""+entityId+"\",\n" +
                "    \"payload\": {\n" +
                "       \"trackerId\": \""+entityId+"\",\n" +
                "       \"currentPosition\": {\n" +
                "             \"longitude\": 2.0,\n" +
                "             \"latitude\": 3.0,\n" +
                "             \"altitude\": 0.0\n" +
                "       }\n" +
                "    }\n" +
                "}";



        //Test for listen function
        boolean exception = false;

        try {
            consumer.listenToTracking(msg);
        } catch (IOException e) {
            e.printStackTrace();
            exception = true;
        }

        assertFalse(exception);

        //Test if entity is present
        Optional<Positionssender> entity = entityRepository.findById(entityId);
        boolean entityPresent = false;

        if (entity.isPresent()) {
            assertEquals(entityId, entity.get().getPositionssenderId());
            entityPresent = true;
        }
        assertFalse(entityPresent);
    }
}
