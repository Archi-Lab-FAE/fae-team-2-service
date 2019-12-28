package de.th.koeln.archilab.fae.faeteam2service.kafka.events;

public class CrudDomainEvent<T> extends DomainEvent {

    private final T eventData;
    private final CrudEventType eventType;

    public CrudDomainEvent(T eventData, CrudEventType eventType) {
        this.eventData = eventData;
        this.eventType = eventType;
    }

    @Override
    public String getEventType() {
        return eventType.name();
    }

    public T getEventData() {
        return eventData;
    }
}
