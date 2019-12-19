package de.th.koeln.archilab.fae.faeteam2service.zone;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;


@Entity
@Data
public class Zone  {

    public enum ZonenTyp {
        UNGEWOHNTE,
        GEWOHNTE
    }

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
}
