package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.Positionssender;
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
import org.threeten.bp.Clock;
import org.threeten.bp.OffsetDateTime;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
@TestPropertySource(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class ZonenAbweichungRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ZonenAbweichungRepository abweichungRepository;

    @Test
    public void findAllByAbgeschlossenFalseTest() {
        Positionssender positionssender = new Positionssender(
                OffsetDateTime.now(Clock.systemUTC()),
                4f,
                4f,
                new Position(30.0,55.0)
        );
        String message = "HilfeHilfe";

        ZonenAbweichung za1 = new ZonenAbweichung(
                LocalDateTime.now(),
                true,
                positionssender.getPositionssenderId(),
                positionssender.getPosition(),
                message
        );
        ZonenAbweichung za2 = new ZonenAbweichung(positionssender, message);
        ZonenAbweichung za3 = new ZonenAbweichung(positionssender, message);

        entityManager.persist(za1);
        entityManager.persist(za2);
        entityManager.persist(za3);
        entityManager.flush();

        List<ZonenAbweichung> found = new ArrayList<>();
        abweichungRepository.findAllByAbgeschlossenFalse().forEach(found::add);

        Assert.assertFalse(found.contains(za1));
        Assert.assertTrue(found.contains(za2));
        Assert.assertTrue(found.contains(za3));
    }
}
