package de.th.koeln.archilab.fae.faeteam2service.zonenausnahme;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
@TestPropertySource(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class ZonenAusnahmeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ZonenAusnahmeRepository ausnahmeRepository;

    @Test
    public void findAllByAbgeschlossenFalseTest() {
        ZonenAusnahme za1 = new ZonenAusnahme(LocalDateTime.now(), true);
        ZonenAusnahme za2 = new ZonenAusnahme(LocalDateTime.now(), false);
        ZonenAusnahme za3 = new ZonenAusnahme(LocalDateTime.now(), false);

        entityManager.persist(za1);
        entityManager.persist(za2);
        entityManager.persist(za3);
        entityManager.flush();

        List<ZonenAusnahme> found = new ArrayList<>();
        ausnahmeRepository.findAllByAbgeschlossenFalse().forEach(found::add);

        Assert.assertFalse(found.contains(za1));
        Assert.assertTrue(found.contains(za2));
        Assert.assertTrue(found.contains(za3));
    }

}
