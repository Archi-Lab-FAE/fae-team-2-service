package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import lombok.Data;

@Entity
@Data
public class DemenziellErkrankter {

    @Id
    private String demenziellErkrankterId;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Zone> zonen;


    public static DemenziellErkrankter convert(DemenziellErkrankterDTO dto) {
        DemenziellErkrankter entity = new DemenziellErkrankter();
        entity.demenziellErkrankterId = dto.getDemenziellErkrankterId();
        entity.name = dto.getName();

        entity.zonen = new HashSet<>();
        dto.getZonen().forEach(zoneDto -> entity.zonen.add(Zone.convert(zoneDto)));

        return entity;
    }

    public static DemenziellErkrankterDTO convert(DemenziellErkrankter entity) {
        DemenziellErkrankterDTO dto = new DemenziellErkrankterDTO();
        dto.setDemenziellErkrankterId(entity.demenziellErkrankterId);
        dto.setName(entity.name);

        dto.setZonen(new ArrayList<>());
        entity.zonen.forEach(zoneEntity -> dto.addZonenItem(Zone.convert(zoneEntity)));

        return dto;
    }
}
