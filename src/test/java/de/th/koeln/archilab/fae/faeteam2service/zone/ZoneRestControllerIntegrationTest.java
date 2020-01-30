package de.th.koeln.archilab.fae.faeteam2service.zone;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.position.PositionDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
@AutoConfigureMockMvc
@DirtiesContext
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class ZoneRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ZoneRepository zoneRepository;

    private PositionDTO getPositionDTO() {
        PositionDTO dto = new PositionDTO();
        dto.setBreitengrad(42d);
        dto.setLaengengrad(815d);

        return dto;
    }

    @Test
    public void createZoneReturnSavedZone() throws Exception {
        ZoneDTO body = new ZoneDTO();
        body.setZoneId("42");
        body.setTyp(ZonenTyp.UNGEWOHNT);
        body.setPositionen(Arrays.asList(getPositionDTO(), getPositionDTO()));

        mvc.perform(post("/zone")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(body)))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(content().json(objectMapper.writeValueAsString(body)));
    }


    @Test
    public void getZoneShouldWorkWhenZoneExists() throws Exception {
        Zone zone = new Zone(ZonenTyp.GEWOHNT, new ArrayList<>());
        zoneRepository.save(zone);

        String zoneId = zone.getZoneId();
        mvc.perform(get("/zone/" + zoneId)
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(content().json(objectMapper.writeValueAsString(Zone.convert(zone))));
    }


    @Test
    public void getZoneReturnNotFoundWhenZoneNotExists() throws Exception {
        String zoneId = "DieseIdGibtsNicht";
        mvc.perform(get("/zone/" + zoneId)
                .accept(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void updateZoneReturnUpdatedZone() throws Exception {
        Zone zone = new Zone(
                ZonenTyp.GEWOHNT,
                Arrays.asList(
                        new Position(42d, 52d),
                        new Position(75d, 34d)
                )
        );
        zoneRepository.save(zone);

        ZoneDTO zonenUpdate = Zone.convert(zone);
        zonenUpdate.setZoneId(zone.getZoneId());
        zonenUpdate.setTyp(ZonenTyp.UNGEWOHNT);

        zone.setTyp(ZonenTyp.UNGEWOHNT);

        mvc.perform(put("/zone/" + zone.getZoneId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(zonenUpdate)))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(content().json(objectMapper.writeValueAsString(Zone.convert(zone))));
    }


    @Test
    public void updateZoneReturnNotFoundWhenZoneNotExists() throws Exception {
        String zoneId = "DieseIdGibtsNicht";

        ZoneDTO zonenUpdate = new ZoneDTO();
        zonenUpdate.setPositionen(Arrays.asList(getPositionDTO(), getPositionDTO()));
        zonenUpdate.setTyp(ZonenTyp.GEWOHNT);
        zonenUpdate.setZoneId(zoneId);

        mvc.perform(put("/zone/" + zoneId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(zonenUpdate)))

                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
