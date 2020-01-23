package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
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

import java.util.HashSet;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;
import lombok.val;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@EmbeddedKafka
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class DemenziellErkrankterControllerTest {

    private static final String PATH = "/demenziellerkrankter";

    private final String name = "K. Löhler";
    private final String uuid = "f95dde92-1921-4c7a-9fa7-d13ecccf2669";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    DemenziellErkrankterRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private DemenziellErkrankter demenziellErkrankter;
    private DemenziellErkrankterDTO demenziellErkrankterDTO;


    @Before
    public void init() {
        val positionen1 = new HashSet<Position>();
        positionen1.add(new Position(7.5649, 51.02322));
        positionen1.add(new Position(6.5649, 50.02322));

        val positionen2 = new HashSet<Position>();
        positionen2.add(new Position(8.5649, 52.02322));
        positionen2.add(new Position(9.5649, 49.02322));

        val zonen = new HashSet<Zone>();
        zonen.add(new Zone(3.5f, ZonenTyp.GEWOHNT, positionen1));
        zonen.add(new Zone(5.5f, ZonenTyp.UNGEWOHNT, positionen2));

        demenziellErkrankter = new DemenziellErkrankter(name, zonen);
        demenziellErkrankter.setDemenziellErkrankterId(uuid);

        demenziellErkrankterDTO = DemenziellErkrankter.convert(demenziellErkrankter);
    }

    @Test
    public void testCreateDemenziellErkrankter() throws Exception {
        String payload = objectMapper.writeValueAsString(demenziellErkrankterDTO);

        mockMvc.perform(post(PATH)
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("demenziellErkrankterId").value(uuid))
                .andExpect(jsonPath("name").value(name));
    }

    @Test
    public void testCreateDemenziellErkrankterWithInvalidPayload() throws Exception {
        String payload = "{" +
                "\"demenziellErkrankterId\": \"f33c6fa8-1697-11ea-8d71-362b9e155667\"," +
                "\"name\": null" +
                "\"zonen\": []}";

        mockMvc.perform(post(PATH)
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testReadDemenziellErkrankter() throws Exception {
        repository.save(demenziellErkrankter);

        mockMvc.perform(get(PATH + "/" + demenziellErkrankter.getDemenziellErkrankterId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("demenziellErkrankterId")
                        .value(uuid));
    }

    @Test
    public void testReadDemenziellErkrankterWithWrongId() throws Exception {
        repository.save(demenziellErkrankter);

        mockMvc.perform(get(PATH + "/" + "not an id"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateDemenziellerkrankter() throws Exception {
        repository.save(demenziellErkrankter);
        String newName = "Hans";
        DemenziellErkrankterDTO dto = DemenziellErkrankter.convert(demenziellErkrankter);
        dto.setName(newName);
        String payload = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put(PATH + "/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("demenziellErkrankterId").value(uuid))
                .andExpect(jsonPath("name").value(newName));
    }

    @Test
    public void testUpdateDemenziellerkrankterWithInvalidId() throws Exception {
        repository.save(demenziellErkrankter);

        String newName = "Hans";
        DemenziellErkrankterDTO dto = DemenziellErkrankter.convert(demenziellErkrankter);
        dto.setName(newName);

        String payload = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put(PATH + "/{id}", "not-a-uuid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUpdateDemenziellerkrankterWithIdNotInDatabase() throws Exception {
        String newName = "Hans";
        String newUuid = "not-an-id";
        DemenziellErkrankterDTO dto = DemenziellErkrankter.convert(demenziellErkrankter);
        dto.setName(newName);
        dto.setDemenziellErkrankterId(newUuid);

        String payload = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put(PATH + "/{id}", newUuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateDemenziellerkrankterWithInvalidPayload() throws Exception {
        repository.save(demenziellErkrankter);

        String invalidPayload = "{" +
                "\"demenziellErkrankterId\": \"" + uuid + "\"," +
                "\"name\": null" +
                "\"zonen\": []}";

        mockMvc.perform(put(PATH + "/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidPayload))
                .andExpect(status().is4xxClientError());
    }
}
