package de.th.koeln.archilab.fae.faeteam2service.position;

import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PositionTest {

    @Test
    public void positionIstInZoneKorrektInitialisiert() {
        Position northEast = new Position(40.0,60.0);
        Position southWest =   new Position(20.0,50.0);
        List<Position> positionsset = new ArrayList<>();
        positionsset.add(northEast);
        positionsset.add(southWest);
        Zone zone = new Zone(2f, ZonenTyp.GEWOHNT, positionsset);
        Position position = new Position(30.0,55.0);

        Assert.assertTrue(position.inZone(zone));
    }

    @Test
    public void positionIstInZoneFalschInitialisiert(){
        boolean exception = false;
        Position position = new Position(43.0,43.6);
        List<Position> positionsset = new ArrayList<>();
        positionsset.add(position);
        Zone zone = new Zone(2f, ZonenTyp.GEWOHNT, positionsset);
        Position positionToTest = new Position(43.6,43.0);

        try {
            positionToTest.inZone(zone);
        } catch(Exception e){
            exception = true;
        }
        Assert.assertTrue(exception);
    }

    @Test
    public void randpunkteDerZoneLiegenInDerZone(){
        Position northEast = new Position(40.0,60.0);
        Position southWest =   new Position(20.0,50.0);
        List<Position> positionsset = new ArrayList<>();
        positionsset.add(northEast);
        positionsset.add(southWest);
        Zone zone = new Zone(2f, ZonenTyp.GEWOHNT, positionsset);
        Position position = new Position(20.0,50.0);

        Assert.assertTrue(position.inZone(zone));
    }
    @Test
    public void updatePositionTest(){
        Position position = new Position(40.0,60.0);
        Double laengengrad = 10.0;
        Double breitengrad = 10.0;

        Position updatePosition = new Position(laengengrad,breitengrad);
        position.update(updatePosition);

        assertEquals(position.getBreitengrad(),breitengrad);
        assertEquals(position.getLaengengrad(),laengengrad);
    }
}
