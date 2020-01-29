package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This class stores all data of a demenziell Erkrankten that are relevant for the Zonenalarmsystem.
 * @see <a href="https://fae.archi-lab.io/glossary/2019/11/15/Glossary-dementiell-Erkrankter.html">Glossary Definition</a>
 */
@Entity
@Data
public class DemenziellErkrankter {

    @Id
    private String demenziellErkrankterId;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Zone> zonen;


    public DemenziellErkrankter() {
        this(null, null);
    }

    public DemenziellErkrankter(String name, Set<Zone> zonen) {
        demenziellErkrankterId = UUID.randomUUID().toString();
        this.name = name;
        this.zonen = zonen;
    }

    public void update(DemenziellErkrankter update) {
        if (StringUtils.isNotBlank(update.demenziellErkrankterId))
            demenziellErkrankterId = update.getDemenziellErkrankterId();
        if (StringUtils.isNotBlank(update.name)) name = update.getName();
        if (update.zonen != null) zonen = update.getZonen();
    }

    public static DemenziellErkrankter convert(DemenziellErkrankterDTO dto) {
        DemenziellErkrankter entity = new DemenziellErkrankter();
        entity.demenziellErkrankterId = dto.getDemenziellErkrankterId();
        entity.name = dto.getName();

        if (dto.getZonen() != null) {
            entity.zonen = new HashSet<>();
            dto.getZonen().forEach(zoneDto -> entity.zonen.add(Zone.convert(zoneDto)));
        }

        return entity;
    }

    public static DemenziellErkrankterDTO convert(DemenziellErkrankter entity) {
        DemenziellErkrankterDTO dto = new DemenziellErkrankterDTO();
        dto.setDemenziellErkrankterId(entity.demenziellErkrankterId);
        dto.setName(entity.name);

        if (entity.getZonen() != null) {
            dto.setZonen(new ArrayList<>());
            entity.zonen.forEach(zoneEntity -> dto.addZonenItem(Zone.convert(zoneEntity)));
        }

        return dto;
    }
}
