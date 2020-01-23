package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
