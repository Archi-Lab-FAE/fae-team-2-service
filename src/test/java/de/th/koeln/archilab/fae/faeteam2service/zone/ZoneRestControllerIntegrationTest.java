package de.th.koeln.archilab.fae.faeteam2service.zone;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.position.PositionDTO;
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

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    public void createZoneReturnSavedZone() throws Exception {
        ZoneDTO body = new ZoneDTO();
        body.setZoneId("42");
        body.setToleranz(1f);
        body.setTyp(ZonenTyp.UNGEWOHNT);
        body.setPositionen(Arrays.asList(new PositionDTO(), new PositionDTO()));

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
        Zone zone = new Zone(1f, ZonenTyp.GEWOHNT, new HashSet<>());
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
        Zone zone = new Zone(1f, ZonenTyp.GEWOHNT, new HashSet<>());
        zoneRepository.save(zone);

        ZoneDTO zonenUpdate = new ZoneDTO();
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
        ZoneDTO zonenUpdate = new ZoneDTO();
        String zoneId = "DieseIdGibtsNicht";

        mvc.perform(put("/zone/" + zoneId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(zonenUpdate)))

                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
