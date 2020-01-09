package de.th.koeln.archilab.fae.faeteam2service.zone;


import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

public class ZoneTest {

    @Test
    public void updateDontSetValuesToNull() {
        Zone updateZone = new Zone(1f, ZonenTyp.GEWOHNT, null);
        Zone newData = new Zone(null, null, new HashSet<>());

        updateZone.update(newData);

        Assert.assertEquals(1, updateZone.getToleranz().intValue());
        Assert.assertEquals(ZonenTyp.GEWOHNT, updateZone.getTyp());
        Assert.assertEquals(new HashSet<>(), updateZone.getPositionen());
    }

    @Test
    public void convertBackAndForthReturnsInitialZone() {
        Zone zone = new Zone(1f, ZonenTyp.GEWOHNT, null);

        ZoneDTO dto = Zone.convert(zone);
        Zone zoneConverted = Zone.convert(dto);

        Assert.assertEquals(zone, zoneConverted);
    }

}
