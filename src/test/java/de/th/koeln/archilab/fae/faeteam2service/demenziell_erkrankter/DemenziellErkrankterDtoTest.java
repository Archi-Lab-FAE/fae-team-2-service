package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import org.junit.Test;

import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneDTO;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;
import lombok.val;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DemenziellErkrankterDtoTest {

    private String name = "Dan";
    private String vorname = "Dee";
    private String uuid = "f95dde92-1921-4c7a-9fa7-d13ecccf2669";

    @Test
    public void testDemenziellErkrankterId() {
        val dto1 = new DemenziellErkrankterDTO();
        val dto2 = dto1.id(uuid);

        assertEquals(dto1.getId(), uuid);
        assertEquals(dto2.getId(), uuid);
        assertEquals(dto1, dto2);
    }

    @Test
    public void testName() {
        val dto1 = new DemenziellErkrankterDTO();
        val dto2 = dto1.name(name);

        assertEquals(dto1.getName(), name);
        assertEquals(dto2.getName(), name);
        assertEquals(dto1, dto2);
    }

    @Test
    public void testEqualsReturnTrue() {
        val dto1 = new DemenziellErkrankterDTO();
        val dto2 = new DemenziellErkrankterDTO();

        assertEquals(dto1, dto1);
        assertEquals(dto1, dto2);

        dto1.setName(name);
        dto2.setName(name);

        assertEquals(dto1, dto2);
    }

    @Test
    public void testEqualsReturnFalse() {
        val dto1 = new DemenziellErkrankterDTO();
        val dto2 = new DemenziellErkrankterDTO();
        val disguisedDto = "DemenziellErkrankterDTO";
        dto1.setName(name);

        assertNotEquals(dto1, disguisedDto);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, dto2);
    }


    private ZoneDTO getZoneDTO() {
        val zone = new ZoneDTO();
        zone.setTyp(ZonenTyp.GEWOHNT);

        return zone;
    }
}
