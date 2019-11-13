package de.th.koeln.archilab.fae.faeteam2service.verantwortlicher;

import de.th.koeln.archilab.fae.faeteam2service.heimbewohner.Heimbewohner;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Verantwortlicher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer verantwortlicherId;

    private String name;

    private String telefonnummer;

    @OneToMany
    private Set<Heimbewohner> heimbewohner;

    public Set<Heimbewohner> getHeimbewohner() {
        return heimbewohner;
    }
}
