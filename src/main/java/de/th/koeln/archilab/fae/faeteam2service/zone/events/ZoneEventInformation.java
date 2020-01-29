package de.th.koeln.archilab.fae.faeteam2service.zone.events;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * All information for which are defined for the zone event.
 *
 * @see <a href="https://fae.archi-lab.io/global/2020/01/10/team-3-Event-Structure.html">Event Structure Definition</a>
 */
@Entity
@Getter
@EqualsAndHashCode
@ToString
public class ZoneEventInformation {

    @Id
    private String zoneEventId;

    private String zoneEventType;
    private Date date;

    public ZoneEventInformation() {
        this.zoneEventId = UUID.randomUUID().toString();
    }

    public ZoneEventInformation(
            String zoneEventType,
            Date date
    ) {
        this.zoneEventId = UUID.randomUUID().toString();
        this.zoneEventType = zoneEventType;
        this.date = date;
    }
}
