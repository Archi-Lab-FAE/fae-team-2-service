package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events;

import de.th.koeln.archilab.fae.faeteam2service.kafka.DomainEvent;

public class DemenziellErkrankterDomainEvent extends DomainEvent {

    private final String demenziellErkrankterIdentifier;
    private final DemenziellErkrankterEventType eventType;

    public DemenziellErkrankterDomainEvent(
            String demenziellErkrankterIdentifier,
            DemenziellErkrankterEventType eventType
    ) {
        this.demenziellErkrankterIdentifier = demenziellErkrankterIdentifier;
        this.eventType = eventType;
    }

    @Override
    public String getEventType() {
        return eventType.name();
    }

    public String getDemenziellErkrankterIdentifier() {
        return demenziellErkrankterIdentifier;
    }
}
