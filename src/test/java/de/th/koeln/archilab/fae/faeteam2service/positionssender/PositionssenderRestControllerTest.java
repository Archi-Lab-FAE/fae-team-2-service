package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterRepository;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.position.PositionDTO;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneRepository;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;
import lombok.val;
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
import org.threeten.bp.Clock;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
@AutoConfigureMockMvc
@DirtiesContext
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class PositionssenderRestControllerTest {

    private final Random rng = new Random();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PositionssenderRepository positionssenderRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private DemenziellErkrankterRepository demenziellErkrankterRepository;

    private OffsetDateTime getRandomDate() {
        return OffsetDateTime.of(
                2020,
                rng.nextInt(12) + 1,
                rng.nextInt(29) + 1,
                rng.nextInt(23) + 1,
                rng.nextInt(59) + 1,
                rng.nextInt(59) + 1,
                0,
                ZoneOffset.UTC
        );
    }

    private Positionssender getPositionssender() {
        return new Positionssender(
                OffsetDateTime.now(Clock.systemUTC()),
                4f,
                4f,
                new Position(30.0,55.0)
        );
    }

    @Test
    public void createPositionssenderReturnCreated()throws Exception{
        PositionDTO posDTO = new PositionDTO();

        posDTO.setBreitengrad(0.5);
        posDTO.setLaengengrad(2.3);

        PositionssenderDTO body = new PositionssenderDTO();
        body.setPositionssenderId("88");
        body.setGenauigkeit(2f);
        body.setLetztesSignal(getRandomDate());
        body.setBatterieStatus(2f);
        body.setPosition(posDTO);

        mvc.perform(post("/positionssender")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))

                .andExpect(status().isCreated())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(content().json(objectMapper.writeValueAsString(body)));
    }
    @Test
    public void findeAllePositionssenderOhneZoneId() throws Exception {
        Positionssender positionssender = getPositionssender();
        positionssenderRepository.save(positionssender);

        mvc.perform(get("/positionssender")
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].positionssenderId").isNotEmpty());
    }
    @Test
    public void findeAllePositionssenderMitZoneIdWennSieExistiert() throws Exception {
        Position northEast = new Position(40.0,60.0);
        Position southWest = new Position(20.0,50.0);
        List<Position> positionsset = new ArrayList<>();
        positionsset.add(northEast);
        positionsset.add(southWest);
        Zone zone = new Zone(2f, ZonenTyp.GEWOHNT, positionsset);

        zoneRepository.save(zone);
        String zoneId = zone.getZoneId();

        Positionssender positionssender = getPositionssender();
        positionssenderRepository.save(positionssender);

        val all = positionssenderRepository.findAll();
        all.forEach(it -> {
            System.out.println(it);
        });

        mvc.perform(get("/positionssender")
                .param("zoneId", zoneId)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"));
    }

    @Test
    public void findeAllePositionssenderMitZoneIdWennSieNichtExistiert() throws Exception {
        String zoneId = "13464337368383837575";
        mvc.perform(get("/positionssender")
                .param("zoneId", zoneId)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound());
    }

    @Test
    public void findePositionssenderByIdWennSenderExistiert() throws Exception {
        Positionssender positionssender = getPositionssender();
        positionssenderRepository.save(positionssender);

        String positionssenderId = positionssender.getPositionssenderId();
        mvc.perform(get("/positionssender/"+positionssenderId)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(content().json(objectMapper.writeValueAsString(Positionssender.convert(positionssender))));

    }
    @Test
    public void findePositionssenderByIdWennSenderNichtExistiert() throws Exception {
        String positionssenderId = "42";

        mvc.perform(get("/positionssender/"+positionssenderId)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound());
    }

    @Test
    public void findeZoneByPositionssenderIdWennSenderExistiert() throws Exception {
        Set<Zone> zoneSet = new HashSet<>();
        Zone zone = new Zone(2f, ZonenTyp.GEWOHNT, new ArrayList<>());
        zoneSet.add(zone);
        zoneRepository.save(zone);

        DemenziellErkrankter demenziellErkrankter = new DemenziellErkrankter("Maria B", zoneSet);
        demenziellErkrankterRepository.save(demenziellErkrankter);

        Positionssender positionssender = getPositionssender();
        positionssender.setDemenziellErkrankter(demenziellErkrankter);
        positionssenderRepository.save(positionssender);
        String positionssenderId = positionssender.getPositionssenderId();

        mvc.perform(get("/positionssender/"+positionssenderId+"/zone")
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].zoneId").value(zone.getZoneId()));
    }

    @Test
    public void findeKeineZoneByPositionssenderIdWennSenderNichtExistiert() throws Exception {
        String positionssenderId = "42";

        mvc.perform(get("/positionssender/"+positionssenderId+"/zone")
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isNotFound());
    }

   @Test
    public void updatePositionssenderDerExistiert() throws Exception {
        Positionssender positionssender = getPositionssender();
        positionssenderRepository.save(positionssender);


        String id = positionssender.getPositionssenderId();
        PositionssenderDTO positionssenderDTO = new PositionssenderDTO();
        OffsetDateTime letztesSignal = getRandomDate();
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setBreitengrad(55.0);
        positionDTO.setLaengengrad(30.0);
        positionssenderDTO.setLetztesSignal(letztesSignal);
        positionssenderDTO.setPosition(positionDTO);


        positionssender.setPosition(Position.convert(positionDTO));
        positionssender.setLetztesSignal(letztesSignal.toString());


        mvc.perform(put("/positionssender/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionssenderDTO)))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(content().json(objectMapper.writeValueAsString(Positionssender.convert(positionssender))));
    }

    @Test
    public void updatePositionssenderDerNichtExistiert() throws Exception {
        String positionssenderId = "GibtesNicht";

        PositionssenderDTO positionssenderDTO = new PositionssenderDTO();

        mvc.perform(put("/positionssender/{id}", positionssenderId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionssenderDTO)))

                .andDo(print())
                .andExpect(status().isNotFound());

    }

   @Test
    public void getAllPositionssenderInnerhalbRadiusMitPositionUndRadius() throws Exception {
        double laengengrad = 1.5;
        double breitengrad = 1.5;
        double radius = 200.9; //meter

        mvc.perform(get("/positionssender/findInRadius?radius="+radius+"&laengengrad="+laengengrad+"&breitengrad="+breitengrad)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    public void getAllPositionssenderInnerhalbRadiusMitPositionOhneRadius() throws Exception{
        mvc.perform(get("/positionssender/findInRadius")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isBadRequest());
    }

}


