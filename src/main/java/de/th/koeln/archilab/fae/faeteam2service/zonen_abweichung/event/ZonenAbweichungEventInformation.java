package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.event;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class ZonenAbweichungEventInformation {

    @Id
    private String zonenAbweichungId;

    private String zonenAbweichungEventType;

    private String date;

    private ZonenAbweichungEventInformation() {
    }

    public ZonenAbweichungEventInformation(String zonenAbweichungId, String zonenAbweichungEventType, String date) {
        this.zonenAbweichungId = zonenAbweichungId;
        this.zonenAbweichungEventType = zonenAbweichungEventType;
        this.date = date;
    }
}
