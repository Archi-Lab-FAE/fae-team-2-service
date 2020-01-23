package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

import lombok.Data;

@Data
public class ZonenabweichungMessage {

    private final String positionssenderId;
    private final String message;

    public ZonenabweichungMessage(String positionssenderId, String message) {
        this.positionssenderId = positionssenderId;
        this.message = message;
    }
}
