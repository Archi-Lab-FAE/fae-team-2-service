package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung;


import com.fasterxml.jackson.core.JsonProcessingException;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.Positionssender;
import de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.event.ZonenAbweichungEventHandler;
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

import static org.junit.Assert.assertFalse;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@EmbeddedKafka
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class ZonenAbweichungEventHandlerTest {

    @Autowired
    ZonenAbweichungEventHandler producer;

    private ZonenAbweichung getZonenAbweichung(){
        Positionssender positionssender = new Positionssender(OffsetDateTime.now(Clock.systemUTC()),
                4f,
                4f,
                new Position(30.0,55.0));
         return new ZonenAbweichung(positionssender,"zonenAbweichung");
    }

    @Test
    public void eventSendOnZonenAbweichungCreated() {
        boolean exception = false;

        try {
            producer.handleZonenAbweichungCreate(getZonenAbweichung());
        }catch(JsonProcessingException e){
            exception = true;
        }
        assertFalse(exception);
    }

    @Test
    public void eventSendOnZonenAbweichungSave(){
        boolean exception = false;
        try {
            producer.handleZonenAbweichungSave(getZonenAbweichung());
        }catch(JsonProcessingException e){
            exception = true;
        }
        assertFalse(exception);
    }

    @Test
    public void eventSendOnZonenAbweichungDelete(){
        boolean exception = false;
        try{
            producer.handleZonenAbweichungDelete(getZonenAbweichung());
        }catch(JsonProcessingException e){
            exception = true;
        }
        assertFalse(exception);
    }
}
