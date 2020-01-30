package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.threeten.bp.Clock;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class PositionssenderTest {

    private final Random rng = new Random();
    private String uuid = "f95dsdfa92-1921-4c7a-9fa7-d13ecccf2669";
    private DemenziellErkrankter demenziellErkrankter = new DemenziellErkrankter("Hans", "Peter", null);

    private Positionssender getPositionssender() {
        return new Positionssender(
                OffsetDateTime.now(Clock.systemUTC()),
                OffsetDateTime.now(Clock.systemUTC()),
                new Position(30.0, 55.0)
        );
    }

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
    public void convertBackAndForthReturnsInitialPositionssender(){
        Positionssender positionssender = getPositionssender();

        PositionssenderDTO positionssenderDTO = Positionssender.convert(positionssender);
        Positionssender positionssenderConverted = Positionssender.convert(positionssenderDTO);
        positionssenderConverted.getPosition().setPositionsId(positionssender.getPosition().getPositionsId());

        assertEquals(positionssender, positionssenderConverted);
    }

    @Test
    public void updatePositionssenderSuccessfullyWithValues(){
        Positionssender positionssender = getPositionssender();
        positionssender.setPositionssenderId(uuid);

        String letztesWartung = getRandomDate().toString();
        Positionssender toUpdatePositionssender = getPositionssender();
        toUpdatePositionssender.setLetzteWartung(letztesWartung);
        toUpdatePositionssender.setPositionssenderId(uuid);

        positionssender.update(toUpdatePositionssender);

        assertEquals(letztesWartung, toUpdatePositionssender.getLetzteWartung());
    }

    @Test
    public void updatePositionssenderSetNotNullValues(){
        Position position = new Position(2.0,2.1);
        String letztesSignal = getRandomDate().toString();
        String letztesWartung = getRandomDate().toString();

        Positionssender positionssender = getPositionssender();
        positionssender.setPositionssenderId(uuid);
        positionssender.setDemenziellErkrankter(demenziellErkrankter);
        positionssender.setLetztesSignal(letztesSignal);
        positionssender.setLetzteWartung(letztesWartung);

        Positionssender positionssenderNull = new Positionssender(null, null, null);
        positionssender.update(positionssenderNull);

        assertEquals("Hans", positionssender.getDemenziellErkrankter().getVorname());
        assertEquals("Peter", positionssender.getDemenziellErkrankter().getName());
        assertEquals(letztesWartung, positionssender.getLetzteWartung());
        assertEquals(letztesSignal, positionssender.getLetztesSignal());
        assertEquals(55.0, positionssender.getPosition().getBreitengrad(), 0);

    }

    @Test
    public void positionssenderIstInRadius(){
        Position position = new Position();
        position.setLaengengrad(12.3);
        position.setBreitengrad(34.0);
        Positionssender positionssender = getPositionssender();
        positionssender.setPosition(position);


        boolean result = positionssender.isInnerhalbRadius(5.0,position.getLaengengrad(),position.getBreitengrad());
        Assert.assertTrue(result);
    }
    @Test
    public void positionssenderIstNichtImRadius(){
        Position position = new Position();
        position.setLaengengrad(180.0);
        position.setBreitengrad(180.0);
        Positionssender positionssender = getPositionssender();
        positionssender.setPosition(position);


        boolean result = positionssender.isInnerhalbRadius(5.0,1.0,1.0);
        Assert.assertFalse(result);

    }

    @Test
    public void setPositionWennZoneLeerIst (){
        demenziellErkrankter.setZonen(new HashSet<>());

        Position position = new Position();

        Positionssender positionssender = getPositionssender();
        positionssender.setDemenziellErkrankter(demenziellErkrankter);
        positionssender.setPosition(position);

        assertEquals(position, positionssender.getPosition());
    }

    @Test
    public void setPositionWennZoneGewohntUndPositionInZone(){
        Position northEast = new Position(40.0,60.0);
        Position southWest =   new Position(20.0,50.0);
        List<Position> positionsset = new ArrayList<>();
        positionsset.add(northEast);
        positionsset.add(southWest);


        Zone gewohnteZone = new Zone();
        gewohnteZone.setTyp(ZonenTyp.GEWOHNT);
        gewohnteZone.setPositionen(positionsset);
        Set<Zone> zonenset = new HashSet<>();
        zonenset.add(gewohnteZone);
        demenziellErkrankter.setZonen(zonenset);

        Position position = new Position(30.0,55.0);

        Positionssender positionssender = getPositionssender();
        positionssender.setDemenziellErkrankter(demenziellErkrankter);
        positionssender.setPosition(position);

        assertEquals(position, positionssender.getPosition());

    }

    @Test
    public void setPositionWennZoneUngewohntUndPositionInZone(){
        Position northEast = new Position(40.0,60.0);
        Position southWest = new Position(20.0,50.0);
        List<Position> positionsset = new ArrayList<>();
        positionsset.add(northEast);
        positionsset.add(southWest);

        Zone ungewohnteZone = new Zone();
        ungewohnteZone.setTyp(ZonenTyp.UNGEWOHNT);
        ungewohnteZone.setPositionen(positionsset);
        Set<Zone> zonenset = new HashSet<>();
        zonenset.add(ungewohnteZone);
        demenziellErkrankter.setZonen(zonenset);


        Position position = new Position(30.0,55.0);

        Positionssender positionssender = getPositionssender();
        positionssender.setDemenziellErkrankter(demenziellErkrankter);
        positionssender.setPosition(position);

        assertEquals(position, positionssender.getPosition());

    }

}
