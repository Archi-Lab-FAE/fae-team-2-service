package de.th.koeln.archilab.fae.faeteam2service.zone;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ZoneTest {

    @Test
    public void updateDontSetValuesToNull() {
        Zone updateZone = new Zone(ZonenTyp.GEWOHNT, null);
        Zone newData = new Zone(null, new ArrayList<>());

        updateZone.update(newData);

        Assert.assertEquals(ZonenTyp.GEWOHNT, updateZone.getTyp());
        Assert.assertEquals(new ArrayList<>(), updateZone.getPositionen());
    }

    @Test
    public void convertBackAndForthReturnsInitialZone() {
        Zone zone = new Zone(ZonenTyp.GEWOHNT, new ArrayList<>());

        ZoneDTO dto = Zone.convert(zone);
        Zone zoneConverted = Zone.convert(dto);

        Assert.assertEquals(zone, zoneConverted);
    }

}
