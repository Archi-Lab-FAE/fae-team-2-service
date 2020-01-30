package de.th.koeln.archilab.fae.faeteam2service.zone;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;

public class ZoneTest {

    @Test
    public void updateDontSetValuesToNull() {
        Zone updateZone = new Zone(ZonenTyp.GEWOHNT, null, null);
        Zone newData = new Zone(null, null, new ArrayList<>());

        updateZone.update(newData);

        Assert.assertEquals(ZonenTyp.GEWOHNT, updateZone.getTyp());
        Assert.assertEquals(new ArrayList<>(), updateZone.getPositionen());
    }

    @Test
    public void convertBackAndForthReturnsInitialZone() {
        DemenziellErkrankter demenziellErkrankter = new DemenziellErkrankter();
        Zone zone = new Zone(ZonenTyp.GEWOHNT, demenziellErkrankter, new ArrayList<>());

        ZoneDTO dto = Zone.convert(zone);
        Zone zoneConverted = Zone.convert(dto, demenziellErkrankter);

        Assert.assertEquals(zone, zoneConverted);
    }

}
