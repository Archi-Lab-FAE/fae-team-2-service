package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class DemenziellErkrankter {

    @Id
    private String demenziellErkrankterId;

    private String name;


    public static DemenziellErkrankter convert(DemenziellErkrankterDTO dto) {
        DemenziellErkrankter entity = new DemenziellErkrankter();
        entity.demenziellErkrankterId = dto.getDemenziellErkrankterId();
        entity.name = dto.getName();

        return entity;
    }

    public static DemenziellErkrankterDTO convert(DemenziellErkrankter entity) {
        DemenziellErkrankterDTO dto = new DemenziellErkrankterDTO();
        dto.setDemenziellErkrankterId(entity.demenziellErkrankterId);
        dto.setName(entity.name);

        return dto;
    }
}
