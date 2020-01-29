package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

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
public class PositionssenderEventInformation {

    @Id
    private String positionssenderEventId;

    private String positionssenderEventType;
    private Date date;

    public PositionssenderEventInformation() {
        this.positionssenderEventId = UUID.randomUUID().toString();
    }

    public PositionssenderEventInformation(
            String positionssenderEventType,
            String positionssenderEventId,
            Date date
    ) {
        this.positionssenderEventId = positionssenderEventId;
        this.positionssenderEventType = positionssenderEventType;
        this.date = date;
    }
}
