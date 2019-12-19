package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class DemenziellErkrankter {

    @Id
    private String demenziellErkrankterId;

    private String name;

}
