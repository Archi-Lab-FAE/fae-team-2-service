package de.th.koeln.archilab.fae.faeteam2service.zone;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
public class Zone  {

    public enum ZonenTyp {
        UNGEWOHNTE,
        GEWOHNTE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long zoneId;

    private Float toleranz;

    private ZonenTyp typ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Position> positionen;

}
