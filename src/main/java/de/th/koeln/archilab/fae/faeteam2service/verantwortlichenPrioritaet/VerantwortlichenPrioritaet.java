package de.th.koeln.archilab.fae.faeteam2service.verantwortlichenPrioritaet;

import de.th.koeln.archilab.fae.faeteam2service.heimbewohner.Heimbewohner;
import de.th.koeln.archilab.fae.faeteam2service.verantwortlicher.Verantwortlicher;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class VerantwortlichenPrioritaet implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Heimbewohner heimbewohner;

    @Id
    @ManyToOne
    @JoinColumn
    private Verantwortlicher verantwortlicher;

    private Integer prioritaet;

    public VerantwortlichenPrioritaet(Heimbewohner heimbewohner, Verantwortlicher verantwortlicher) {
        this.heimbewohner = heimbewohner;
        this.verantwortlicher = verantwortlicher;
    }
}
