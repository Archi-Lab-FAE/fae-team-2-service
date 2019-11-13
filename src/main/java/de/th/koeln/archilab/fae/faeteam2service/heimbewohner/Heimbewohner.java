package de.th.koeln.archilab.fae.faeteam2service.heimbewohner;

import de.th.koeln.archilab.fae.faeteam2service.verantwortlicher.Verantwortlicher;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Heimbewohner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bewohnerId;

    private String name;

    @OneToMany
    private Set<Verantwortlicher> verantwortliche;

    public Heimbewohner() {}
    public Heimbewohner(String name) {
        this.name = name;
    }

    public Set<Verantwortlicher> getVerantwortliche() {
        return verantwortliche;
    }
}
