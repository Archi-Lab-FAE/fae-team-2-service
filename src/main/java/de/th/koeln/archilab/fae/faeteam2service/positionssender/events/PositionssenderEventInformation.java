package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

/**
 * Here is all information necessary for an event for a positionssender.
 * @see <a href="https://fae.archi-lab.io/global/2020/01/10/team-3-Event-Structure.html">Event Structure Definition</a>
 */
@Entity
@Getter
@EqualsAndHashCode
@ToString
public class PositionssenderEventInformation {

    @Id
    private String positionssenderEventId;

    private String positionssenderEventType;
    private Date date;

    public PositionssenderEventInformation() {
        this.positionssenderEventId = UUID.randomUUID().toString();
    }

    public PositionssenderEventInformation(
            String positionssenderEventId,
            String positionssenderEventType,
            Date date
    ) {
        this.positionssenderEventId = positionssenderEventId;
        this.positionssenderEventType = positionssenderEventType;
        this.date = date;
    }
}
