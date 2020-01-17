package de.th.koeln.archilab.fae.faeteam2service.positionssender;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterRepository;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.position.PositionDTO;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneRepository;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;
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
                2019,
                rng.nextInt(12) + 1,
                rng.nextInt(29) + 1,
                rng.nextInt(23) + 1,
                rng.nextInt(59) + 1,
                rng.nextInt(59) + 1,
                0,
                ZoneOffset.UTC
        );
    }

    @Test
    public void createPositionssenderReturnCreated()throws Exception{
        PositionDTO posDTO = new PositionDTO();
        posDTO.setPositionsId("42");
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
        Positionssender positionssender = new Positionssender(getRandomDate(),2f,2f, new Position());
        positionssenderRepository.save(positionssender);

        mvc.perform(get("/positionssender")
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].positionssenderId").isNotEmpty());
    }
    @Test
    //TODO: Fehlt da noch etwas? Es müssen keine Positionssender übergeben werden, da diese Funktion speziell woanders getestet wird?
    public void findeAllePositionssenderMitZoneIdWennSieExistiert() throws Exception {
        Position position = new Position(2.0,3.6);
        Position position2 =   new Position(2.4,3.3);
        Set<Position> positionsset = new HashSet<>();
        positionsset.add(position);
        positionsset.add(position2);
        Zone zone = new Zone(2f, ZonenTyp.GEWOHNT, positionsset);
        zoneRepository.save(zone);

        String zoneId = zone.getZoneId();
        mvc.perform(get("/positionssender")
                .param("zoneId", zoneId)
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"));
                //.andExpect(content.isEmpty())?
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
        Positionssender positionssender = new Positionssender(getRandomDate(),2f,2f, new Position());
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
        Zone zone = new Zone(2f, ZonenTyp.GEWOHNT, new HashSet<>());
        zoneSet.add(zone);
        zoneRepository.save(zone);

        DemenziellErkrankter demenziellErkrankter = new DemenziellErkrankter("Maria B", zoneSet);
        demenziellErkrankterRepository.save(demenziellErkrankter);

        Positionssender positionssender = new Positionssender(getRandomDate(),2f,2f, new Position());
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
    //TODO Without letztesSignal, Position the test failed
    public void updatePositionssenderDerExistiert() throws Exception {
        Positionssender positionssender = new Positionssender(getRandomDate(),2f,2f, new Position());
        positionssenderRepository.save(positionssender);


        String id = positionssender.getPositionssenderId();
        PositionssenderDTO positionssenderDTO = new PositionssenderDTO();
        OffsetDateTime letztesSignal = getRandomDate();
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setPositionsId("123");
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
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setPositionsId("ursprungspos");
        positionDTO.setLaengengrad(1.5);
        positionDTO.setBreitengrad(1.5);

        Double radius = 200.9; //meter

        mvc.perform(get("/positionssender?radius="+radius)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionDTO)))

                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    public void getAllPositionssenderInnerhalbRadiusMitPositionOhneRadius() throws Exception{
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setPositionsId("ursprungspos");
        positionDTO.setLaengengrad(1.5);
        positionDTO.setBreitengrad(1.5);


        mvc.perform(get("/positionssender")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionDTO)))

                .andExpect(status().isOk())
                .andExpect(header().string("content-type", "application/json;charset=UTF-8"));
    }

    @Test
    public void getNoPositionssenderInnerhalbeinesRadiusOhnePosition() throws Exception{
        Double radius = 500.9; //meter

        mvc.perform(get("/positionssender")
                .accept(MediaType.APPLICATION_JSON)
                .param("radius", String.valueOf(radius))
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isBadRequest());
    }
}


