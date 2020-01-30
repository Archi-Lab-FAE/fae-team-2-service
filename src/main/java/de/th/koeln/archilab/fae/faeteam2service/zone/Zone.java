package de.th.koeln.archilab.fae.faeteam2service.zone;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.zone.events.ZoneEventHandler;
import lombok.Data;

/**
 * This class represents a zone which can be relevant for the Zonenalarmsystem.
 *
 * @see <a href="https://fae.archi-lab.io/glossary/2019/11/15/Glossary-Zone.html">Glossary Definition</a>
 */
@Entity
@Data
@EntityListeners(ZoneEventHandler.class)
public class Zone {

    @Id
    private String zoneId;

    private Float toleranz;

    private ZonenTyp typ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Position> positionen;

    public Zone() {
        this(null, null, null);
    }

    public Zone(Float toleranz, ZonenTyp typ, List<Position> positionen) {
        this.zoneId = UUID.randomUUID().toString();
        this.toleranz = toleranz;
        this.typ = typ;
        this.positionen = positionen;
    }


    public void update(Zone update) {
        if (StringUtils.isNotBlank(update.zoneId)) zoneId = update.getZoneId();
        if (update.toleranz != null) toleranz = update.getToleranz();
        if (update.typ != null) typ = update.getTyp();
        if (update.positionen != null) positionen = update.getPositionen();
    }

    public static Zone convert(ZoneDTO dto) {
        Zone entity = new Zone();
        entity.zoneId = dto.getZoneId();
        entity.typ = dto.getTyp();

        if (dto.getPositionen() != null) {
            entity.positionen = new ArrayList<>();
            dto.getPositionen().forEach(positionDto -> entity.positionen.add(Position.convert(positionDto)));
        }

        return entity;
    }

    public static ZoneDTO convert(Zone entity) {
        ZoneDTO dto = new ZoneDTO();
        dto.setZoneId(entity.zoneId);
        dto.setTyp(entity.typ);

        if (entity.getPositionen() != null) {
            dto.setPositionen(new ArrayList<>());
            entity.positionen.forEach(positionEntity -> dto.addPositionenItem(Position.convert(positionEntity)));
        }

        return dto;
    }
}
