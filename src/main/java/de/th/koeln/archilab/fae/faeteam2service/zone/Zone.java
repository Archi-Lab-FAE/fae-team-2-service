package de.th.koeln.archilab.fae.faeteam2service.zone;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import lombok.Data;


@Entity
@Data
public class Zone  {

    public enum ZonenTyp {
        GEFAHR,
        SICHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long zoneId;

    private Float toleranz;

    private ZonenTyp typ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Position> positionen;

}
