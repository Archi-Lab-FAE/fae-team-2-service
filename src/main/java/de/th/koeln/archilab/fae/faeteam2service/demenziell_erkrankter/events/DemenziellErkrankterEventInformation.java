package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

/**
 * All information for which is given in an event for a demenziell Erkrankten.
 * @see <a href="https://fae.archi-lab.io/global/2020/01/10/team-3-Event-Structure.html">Event Structure Definition</a>
 */
@Entity
@Getter
@EqualsAndHashCode
@ToString
public class DemenziellErkrankterEventInformation {

    @Id
    private String demenziellErkrankterEventId;

    private String demenziellErkrankterEventType;
    private Date date;

    public DemenziellErkrankterEventInformation() {
        this.demenziellErkrankterEventId = UUID.randomUUID().toString();
    }

    public DemenziellErkrankterEventInformation(
            String demenziellErkrankterEventId,
            String demenziellErkrankterEventType,
            Date date
    ) {
        this.demenziellErkrankterEventId = demenziellErkrankterEventId;
        this.demenziellErkrankterEventType = demenziellErkrankterEventType;
        this.date = date;
    }
}
