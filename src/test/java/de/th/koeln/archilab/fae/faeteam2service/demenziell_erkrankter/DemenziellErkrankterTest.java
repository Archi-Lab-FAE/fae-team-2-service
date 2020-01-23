package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import lombok.val;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DemenziellErkrankterTest {

    private String name = "Hans";
    private String uuid = "f95dde92-1921-4c7a-9fa7-d13ecccf2669";

    @Test
    public void testConversionToDto() {
        val entity = new DemenziellErkrankter();
        entity.setDemenziellErkrankterId(uuid);
        entity.setName(name);

        val dto = DemenziellErkrankter.convert(entity);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDemenziellErkrankterId(), entity.getDemenziellErkrankterId());
    }

    @Test
    public void testConversionToEntity() {
        val dto = new DemenziellErkrankterDTO();
        dto.setDemenziellErkrankterId(uuid);
        dto.setName(name);

        val entity = DemenziellErkrankter.convert(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDemenziellErkrankterId(), entity.getDemenziellErkrankterId());
    }

    @Test
    public void testUpdateDemenziellErkrankten() {
        val entity = new DemenziellErkrankter();
        entity.setDemenziellErkrankterId(uuid);
        entity.setName(name);

        val newName = "Hans Peter";
        val updatedEntity = new DemenziellErkrankter();
        updatedEntity.setDemenziellErkrankterId(uuid);
        updatedEntity.setName(newName);

        entity.update(updatedEntity);

        assertEquals(entity.getName(), newName);
    }

    @Test
    public void testConversionBackAndForthReturnsInitialEntity() {
        val entity = new DemenziellErkrankter();
        entity.setDemenziellErkrankterId(uuid);
        entity.setName(name);

        val dto = DemenziellErkrankter.convert(entity);
        val entityConverted = DemenziellErkrankter.convert(dto);

        assertEquals(entity, entityConverted);
    }
}
