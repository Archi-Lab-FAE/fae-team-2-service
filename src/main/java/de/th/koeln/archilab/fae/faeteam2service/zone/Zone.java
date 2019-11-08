package de.th.koeln.archilab.fae.faeteam2service.zone;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


@Entity
@Data
public class Zone  {
    public enum Typ{
        GEFAHR,
        SICHER
    };
    @JsonUnwrapped
    @Id
    private UUID uuid;
    private float toleranz;
    private Typ typ;

    @OneToMany
    private Set<Position> positionen;

    public Zone(){
        this.uuid= UUID.randomUUID();
    }


    public Set<Position> getPositionen() {
        return positionen;
    }
}
