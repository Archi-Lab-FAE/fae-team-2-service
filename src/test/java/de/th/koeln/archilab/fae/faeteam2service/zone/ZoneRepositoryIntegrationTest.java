package de.th.koeln.archilab.fae.faeteam2service.zone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class ZoneRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ZoneRepository zoneRepository;

    @Test
    public void findByIdReturnsSavedZone() {
        Zone zone = new Zone();
        entityManager.persist(zone);
        entityManager.flush();

        Zone found = zoneRepository.findById(zone.getZoneId()).orElse(null);

        Assert.assertEquals(zone, found);
    }

}
