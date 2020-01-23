package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import org.junit.Test;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.Random;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;

import static org.junit.Assert.assertEquals;

public class PositionssenderTest {

    private final Random rng = new Random();
    private String uuid = "f95dsdfa92-1921-4c7a-9fa7-d13ecccf2669";

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
        Positionssender positionssender = new Positionssender(getRandomDate(),1f,1f, new Position());

        PositionssenderDTO positionssenderDTO = Positionssender.convert(positionssender);
        Positionssender positionssenderConverted = Positionssender.convert(positionssenderDTO);
        positionssenderConverted.getPosition().setPositionsId(positionssender.getPosition().getPositionsId());

        assertEquals(positionssender, positionssenderConverted);
    }

    @Test
    public void updatePositionssenderSuccessfullyWithValues(){
        Positionssender positionssender = new Positionssender();
        positionssender.setPositionssenderId(uuid);
        positionssender.setBatterieStatus(1f);

        float newBatterieStatus = 5f;
        Positionssender toUpdatePositionssender = new Positionssender();
        toUpdatePositionssender.setPositionssenderId(uuid);
        toUpdatePositionssender.setBatterieStatus(newBatterieStatus);

        positionssender.update(toUpdatePositionssender);

        assertEquals(positionssender.getBatterieStatus().doubleValue(),newBatterieStatus,0.0002);
    }

    @Test
    public void updatePositionssenderSetNotNullValues(){
        DemenziellErkrankter demenziellErkrankter = new DemenziellErkrankter("Hans", null);
        Position position = new Position(2.0,2.1);
        String letztesSignal = getRandomDate().toString();

        Positionssender positionssender = new Positionssender();
        positionssender.setPositionssenderId(uuid);
        positionssender.setBatterieStatus(1f);
        positionssender.setDemenziellErkrankter(demenziellErkrankter);
        positionssender.setPosition(position);
        positionssender.setLetztesSignal(letztesSignal);

        Positionssender positionssenderNull = new Positionssender(null,null,null,null);

        positionssender.update(positionssenderNull);

        assertEquals("Hans", positionssender.getDemenziellErkrankter().getName());
        assertEquals(1f, positionssender.getBatterieStatus().floatValue(),0.0002);
        assertEquals(letztesSignal, positionssender.getLetztesSignal());
        assertEquals(position.getBreitengrad(), positionssender.getPosition().getBreitengrad());

    }
}
