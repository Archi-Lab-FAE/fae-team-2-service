package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.event;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import lombok.Data;

@Data
public class ZonenAbweichungMessage {

    private final String positionssenderId;
    private final Position position;
    private final String message;

    public ZonenAbweichungMessage(String positionssenderId, Position position, String message) {
        this.positionssenderId = positionssenderId;
        this.position = position;
        this.message = message;
    }
}
