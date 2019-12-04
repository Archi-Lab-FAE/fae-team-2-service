package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class DemenziellErkrankter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long demenziellErkrankterId;

    private String name;

}
