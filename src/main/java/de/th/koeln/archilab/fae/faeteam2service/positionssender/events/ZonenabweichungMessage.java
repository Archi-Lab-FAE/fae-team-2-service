package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import lombok.Data;

@Data
public class ZonenabweichungMessage {

    private final String positionssenderId;
    private final Position position;
    private final String message;

    public ZonenabweichungMessage(String positionssenderId, Position position, String message) {
        this.positionssenderId = positionssenderId;
        this.position = position;
        this.message = message;
    }
}
