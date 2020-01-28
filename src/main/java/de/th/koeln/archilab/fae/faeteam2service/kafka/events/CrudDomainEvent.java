package de.th.koeln.archilab.fae.faeteam2service.kafka.events;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class CrudDomainEvent<T> extends DomainEvent {

    private final T payload;
    private final CrudEventType type;

    public CrudDomainEvent(T payload, CrudEventType type, String key, int version, String timestamp) {
        super(key, version, timestamp);
        this.payload = payload;
        this.type = type;
    }

    @Override
    public String getType() {
        return type.name();
    }

    public T getPayload() {
        return payload;
    }
}
