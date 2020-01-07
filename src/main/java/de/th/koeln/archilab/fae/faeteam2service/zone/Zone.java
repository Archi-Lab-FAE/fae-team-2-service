package de.th.koeln.archilab.fae.faeteam2service.zone;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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


@Entity
@Data
@EntityListeners(ZoneEventHandler.class)
public class Zone  {

    @Id
    private String zoneId;

    private Float toleranz;

    private ZonenTyp typ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Position> positionen;

    public Zone() {
        this.zoneId = UUID.randomUUID().toString();
    }

    public Zone(Float toleranz, ZonenTyp typ, Set<Position> positionen) {
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
        entity.toleranz = dto.getToleranz();
        entity.typ = dto.getTyp();

        entity.positionen = new HashSet<>();
        dto.getPositionen().forEach(positionDto -> entity.positionen.add(Position.convert(positionDto)));

        return entity;
    }

    public static ZoneDTO convert(Zone entity) {
        ZoneDTO dto = new ZoneDTO();
        dto.setZoneId(entity.zoneId);
        dto.setToleranz(entity.toleranz);
        dto.setTyp(entity.typ);

        dto.setPositionen(new ArrayList<>());
        entity.positionen.forEach(positionEntity -> dto.addPositionenItem(Position.convert(positionEntity)));

        return dto;
    }
}
