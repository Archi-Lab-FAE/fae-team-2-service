package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Event for the Zonenabweichung with a specific structure.
 * @see <a href="https://fae.archi-lab.io/global/2020/01/10/team-3-Event-Structure.html">Event Structure Definition</a>
 */
@EqualsAndHashCode(callSuper = false)
public class ZonenabweichungEvent extends DomainEvent {

    @JsonProperty("payload")
    @Getter
    private final ZonenabweichungMessage message;

    public ZonenabweichungEvent(String key, int version, String timestamp, ZonenabweichungMessage message) {
        super(key, version, timestamp);
        this.message = message;
    }

    @Override
    public String getType() {
        return "Zonenabweichung";
    }
}
