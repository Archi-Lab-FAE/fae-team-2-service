package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import org.apache.commons.lang.StringUtils;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import lombok.Data;

/**
 * This class stores all data of a demenziell Erkrankten that are relevant for the Zonenalarmsystem.
 *
 * @see <a href="https://fae.archi-lab.io/glossary/2019/11/15/Glossary-dementiell-Erkrankter.html">Glossary Definition</a>
 */
@Entity
@Data
public class DemenziellErkrankter {

    @Id
    private String demenziellErkrankterId;

    private String vorname;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Zone> zonen;


    public DemenziellErkrankter() {
        this(null, null, null);
    }

    public DemenziellErkrankter(String vorname, String name, Set<Zone> zonen) {
        demenziellErkrankterId = UUID.randomUUID().toString();
        this.vorname = vorname;
        this.name = name;
        this.zonen = zonen;
    }

    public void update(DemenziellErkrankter update) {
        if (StringUtils.isNotBlank(update.demenziellErkrankterId))
            demenziellErkrankterId = update.getDemenziellErkrankterId();
        if (StringUtils.isNotBlank(update.name)) name = update.getName();
        if (StringUtils.isNotBlank(update.vorname)) vorname = update.getVorname();
        if (update.zonen != null) zonen = update.getZonen();
    }

    public static DemenziellErkrankter convert(DemenziellErkrankterDTO dto) {
        DemenziellErkrankter entity = new DemenziellErkrankter();
        entity.demenziellErkrankterId = dto.getId();
        entity.name = dto.getName();
        entity.vorname = dto.getVorname();

        return entity;
    }

    public static DemenziellErkrankterDTO convert(DemenziellErkrankter entity) {
        DemenziellErkrankterDTO dto = new DemenziellErkrankterDTO();
        dto.setId(entity.demenziellErkrankterId);
        dto.setName(entity.name);
        dto.setVorname(entity.vorname);

        return dto;
    }
}
