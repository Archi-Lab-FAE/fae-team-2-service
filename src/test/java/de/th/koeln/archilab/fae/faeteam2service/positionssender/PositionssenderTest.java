package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.threeten.bp.Clock;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PositionssenderTest {

    private final Random rng = new Random();
    private String uuid = "f95dsdfa92-1921-4c7a-9fa7-d13ecccf2669";
    private DemenziellErkrankter demenziellErkrankter = new DemenziellErkrankter("Hans",null);

    private Positionssender getPositionssender() {
        return new Positionssender(
                OffsetDateTime.now(Clock.systemUTC()),
                4f,
                4f,
                new Position(30.0,55.0)
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

        float newBatterieStatus = 5f;
        Positionssender toUpdatePositionssender = getPositionssender();
        toUpdatePositionssender.setBatterieStatus(newBatterieStatus);
        toUpdatePositionssender.setPositionssenderId(uuid);

        positionssender.update(toUpdatePositionssender);

        assertEquals(positionssender.getBatterieStatus().doubleValue(),newBatterieStatus,0.0002);
    }

    @Test
    public void updatePositionssenderSetNotNullValues(){
        Position position = new Position(2.0,2.1);
        String letztesSignal = getRandomDate().toString();

        Positionssender positionssender = getPositionssender();
        positionssender.setPositionssenderId(uuid);
        positionssender.setDemenziellErkrankter(demenziellErkrankter);
        positionssender.setLetztesSignal(letztesSignal);

        Positionssender positionssenderNull = new Positionssender(null,null,null,null);
        positionssender.update(positionssenderNull);

        assertEquals("Hans", positionssender.getDemenziellErkrankter().getName());
        assertEquals(4f, positionssender.getBatterieStatus(),0.0002);
        assertEquals(letztesSignal, positionssender.getLetztesSignal());
        assertEquals(55.0, positionssender.getPosition().getBreitengrad(), 0);

    }
    @Test
    public void positionssenderIstInZoneKorrektInitialisiert() {
        Position northEast = new Position(40.0,60.0);
        Position southWest =   new Position(20.0,50.0);
        List<Position> positionsset = new ArrayList<>();
        positionsset.add(northEast);
        positionsset.add(southWest);
        Zone zone = new Zone(2f, ZonenTyp.GEWOHNT, positionsset);

        Positionssender positionssender = getPositionssender();
        String letztesSignal = getRandomDate().toString();
        Position positionPositionssender = new Position();
        positionPositionssender.setBreitengrad(55.0);
        positionPositionssender.setLaengengrad(30.0);
        positionssender.setLetztesSignal(letztesSignal);
        positionssender.setPosition(positionPositionssender);
        boolean posSenderInZone =positionPositionssender.inZone(zone);

        Assert.assertTrue(posSenderInZone);
    }
    @Test
    public void positionssenderIstInZoneFalschInitialisiert(){
        boolean exception = false;
        Position position = new Position(43.0,43.6);
        List<Position> positionsset = new ArrayList<>();
        positionsset.add(position);
        Zone zone = new Zone(2f, ZonenTyp.GEWOHNT, positionsset);

        Positionssender positionssender = getPositionssender();
        String letztesSignal = getRandomDate().toString();
        Position positionPositionssender = new Position();
        positionPositionssender.setBreitengrad(43.6);
        positionPositionssender.setLaengengrad(43.0);
        positionssender.setLetztesSignal(letztesSignal);
        positionssender.setPosition(positionPositionssender);

        try {
            positionPositionssender.inZone(zone);
        } catch(Exception e){
            exception = true;
        }
        Assert.assertTrue(exception);
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
