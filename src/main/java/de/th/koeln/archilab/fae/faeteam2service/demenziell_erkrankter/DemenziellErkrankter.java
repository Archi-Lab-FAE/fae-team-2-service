package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events.DemenziellErkrankterEventHandler;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

@Entity
@EntityListeners(DemenziellErkrankterEventHandler.class)
@Data
public class DemenziellErkrankter {

    @Id
    private String demenziellErkrankterId;

    private String name;

}
