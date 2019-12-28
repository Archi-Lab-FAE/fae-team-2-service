package de.th.koeln.archilab.fae.faeteam2service.zone.events;

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
