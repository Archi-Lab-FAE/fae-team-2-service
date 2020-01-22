package de.th.koeln.archilab.fae.faeteam2service.kafka.events;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class DomainEvent {

    private final String id;
    private final String key;
    private final int version;
    private final String timestamp;

    public DomainEvent(String key, int version, String timestamp) {
        this.id = UUID.randomUUID().toString();
        this.key = key;
        this.version = version;
        this.timestamp = timestamp;
    }

    public abstract String getType();

    public String getId() {
        return this.id;
    }
}
