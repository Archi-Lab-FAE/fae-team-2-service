package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.events;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.kafka.CrudEventType;
import de.th.koeln.archilab.fae.faeteam2service.kafka.DomainEvent;

public class DemenziellErkrankterDomainEvent extends DomainEvent {

    private final DemenziellErkrankter demenziellErkrankter;
    private final CrudEventType eventType;

    public DemenziellErkrankterDomainEvent(
            DemenziellErkrankter demenziellErkrankter,
            CrudEventType eventType
    ) {
        this.demenziellErkrankter = demenziellErkrankter;
        this.eventType = eventType;
    }

    @Override
    public String getEventType() {
        return eventType.name();
    }

    public DemenziellErkrankter getDemenziellErkrankter() {
        return demenziellErkrankter;
    }
}
