package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneDTO;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;
import lombok.val;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DemenziellErkrankterDtoTest {

    private String name = "Dan";
    private String uuid = "f95dde92-1921-4c7a-9fa7-d13ecccf2669";

    @Test
    public void testDemenziellErkrankterId() {
        val dto1 = new DemenziellErkrankterDTO();
        val dto2 = dto1.demenziellErkrankterId(uuid);

        assertEquals(dto1.getDemenziellErkrankterId(), uuid);
        assertEquals(dto2.getDemenziellErkrankterId(), uuid);
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
    public void testZonen() {
        val zone = getZoneDTO();
        val zonen = new ArrayList<ZoneDTO>();
        zonen.add(zone);
        zonen.add(zone);

        val dto1 = new DemenziellErkrankterDTO();
        val dto2 = dto1.zonen(zonen);

        assertEquals(dto1.getZonen(), zonen);
        assertEquals(dto2.getZonen(), zonen);
        assertEquals(dto1, dto2);
    }

    @Test
    public void addZonenItemWithZoneListNull() {
        val zone = getZoneDTO();

        val dto = new DemenziellErkrankterDTO();
        dto.setZonen(null);
        dto.addZonenItem(zone);

        assertEquals(dto.getZonen().size(), 1);
        assertEquals(dto.getZonen().get(0), zone);
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

    @Test
    public void testHashCode() {
        val dto = new DemenziellErkrankterDTO();
        dto.setName(name);

        val hash = Objects.hash(null, name, null);
        assertEquals(dto.hashCode(), hash);

        dto.setDemenziellErkrankterId("ID");
        assertNotEquals(dto.hashCode(), hash);
    }

    @Test
    public void testToString() {
        val string = "class DemenziellErkrankterDTO {\n"+
                "    demenziellErkrankterId: null\n" +
                "    name: " + name + "\n" +
                "    zonen: null\n" +
                "}";

        val dto = new DemenziellErkrankterDTO();
        dto.setName(name);

        assertEquals(dto.toString(), string);

        dto.setDemenziellErkrankterId("ID");
        assertNotEquals(dto.toString(), string);
    }

    private ZoneDTO getZoneDTO() {
        val zone = new ZoneDTO();
        zone.setTyp(ZonenTyp.GEWOHNT);
        zone.setToleranz(5f);

        return zone;
    }
}
