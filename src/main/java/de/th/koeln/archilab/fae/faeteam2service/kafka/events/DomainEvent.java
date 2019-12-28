package de.th.koeln.archilab.fae.faeteam2service.kafka.events;

import java.util.UUID;

public abstract class DomainEvent {

    private final String eventID;

    public DomainEvent() {
        this.eventID = UUID.randomUUID().toString();
    }

    public abstract String getEventType();

    public String getEventID() {
        return this.eventID;
    }
}
