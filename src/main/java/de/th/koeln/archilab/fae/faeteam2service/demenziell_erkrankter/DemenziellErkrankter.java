package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import org.apache.commons.lang.StringUtils;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

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


    public DemenziellErkrankter() {
        this(null, null);
    }

    public DemenziellErkrankter(String vorname, String name) {
        demenziellErkrankterId = UUID.randomUUID().toString();
        this.vorname = vorname;
        this.name = name;
    }

    public void update(DemenziellErkrankter update) {
        if (StringUtils.isNotBlank(update.demenziellErkrankterId))
            setDemenziellErkrankterId(update.getDemenziellErkrankterId());
        if (StringUtils.isNotBlank(update.name)) setName(update.getName());
        if (StringUtils.isNotBlank(update.vorname)) setVorname(update.getVorname());
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
