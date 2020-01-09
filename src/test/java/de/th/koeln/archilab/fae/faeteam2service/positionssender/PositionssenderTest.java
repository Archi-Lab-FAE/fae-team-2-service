package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;

import static de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp.GEWOHNT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PositionssenderTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PositionssenderRepository positionssenderRepository;


    private Positionssender positionssender;
    private Position position;
    private Zone zone;

    @Before
    public void init() {
        positionssender = new Positionssender();
        positionssender.setPositionssenderId("f33c6cd8-1697-11ea-8d71-362b9e155667");
        positionssender.setBatterieStatus((float) 0.8);
        positionssender.setLetztesSignal("2019-11-29T12:03:46.164");
        positionssender.setGenauigkeit((float) 1.0);

        position = new Position();
        position.setPositionsId("5d17f942-2394-40b5-8065-a7fe12937659");
        position.setBreitengrad(1.9852);
        position.setLaengengrad(1.3254);
        positionssender.setPosition(position);

        zone = new Zone();
        zone.setZoneId("3c17f942-2394-40b5-8065-a7fe12937659");
        zone.setTyp(GEWOHNT);
        zone.setToleranz((float) 1.5);
        zone.setPositionen((Set<Position>) position);
    }

    @Test
    public void testFindPositionssender() throws Exception {
        positionssenderRepository.findById(positionssender.getPositionssenderId());

        mockMvc.perform(get("/positionssender/f33c6cd8-1697-11ea-8d71-362b9e155667"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    //TODO: Missing?
    public void testGetPositionssenderByRadius() {

    }

    @Test
    //TODO: Zone erstellen und dann sucessful
    public void testGetZonenByPositionssenderId() throws Exception {
        mockMvc.perform(get("/positionssender/" + positionssender.getPositionssenderId() + "/zone"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    //Liefert alle Positionssender in einer Zone. Bei keiner Id werden alle Positionssender ausgegeben
    public void testFindAllPositionssender() throws Exception {
        positionssenderRepository.findAll();

        mockMvc.perform(get("/positionssender"))
                .andExpect(jsonPath("$[0].positionssenderId").value(positionssender.getPositionssenderId()))
                .andExpect(jsonPath("$[0].letztesSignal").value(positionssender.getLetztesSignal()))
                .andExpect(jsonPath("$[0].batterieStatus").value(positionssender.getBatterieStatus()))
                .andExpect(jsonPath("$[0].genauigkeit").value(positionssender.getGenauigkeit()));
    }

    @Test
    //Liefert alle Positionssender in einer Zone. Bei einer zonen Id werden alle Positionssender innerhalb der Zone ausgegeben
    public void testFindAllPositionssenderByZoneId() throws Exception {
        mockMvc.perform(get("/positionssender?zoneId=3c17f942-2394-40b5-8065-a7fe12937659r"))
                .andExpect(jsonPath("$[0].positionssenderId").value(positionssender.getPositionssenderId()))
                .andExpect(jsonPath("$[0].letztesSignal").value(positionssender.getLetztesSignal()))
                .andExpect(jsonPath("$[0].batterieStatus").value(positionssender.getBatterieStatus()))
                .andExpect(jsonPath("$[0].genauigkeit").value(positionssender.getGenauigkeit()));
    }

    @Test
    public void testAddPositionssender() throws Exception {
        positionssenderRepository.save(positionssender);

        mockMvc.perform(post("/positionssender", positionssender))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].positionssenderId").value(positionssender.getPositionssenderId()))
                .andExpect(jsonPath("$[0].letztesSignal").value(positionssender.getLetztesSignal()))
                .andExpect(jsonPath("$[0].batterieStatus").value(positionssender.getBatterieStatus()))
                .andExpect(jsonPath("$[0].genauigkeit").value(positionssender.getGenauigkeit()));
    }

    @Test
    public void testUpdatePositionssender() throws Exception {
        String id = "g33c6cd8-1697-11ea-8d71-362b9e155667";

        mockMvc.perform(put("/positionssender/" + positionssender.getPositionssenderId(), id))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].positionssenderId").value(id));

    }
}


