package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

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
            String demenziellErkrankterEventType,
            Date date
    ) {
        this.demenziellErkrankterEventId = UUID.randomUUID().toString();
        this.demenziellErkrankterEventType = demenziellErkrankterEventType;
        this.date = date;
    }
}
