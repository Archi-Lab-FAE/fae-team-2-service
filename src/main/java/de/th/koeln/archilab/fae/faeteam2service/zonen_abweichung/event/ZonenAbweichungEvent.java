package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.CrudEventType;
import de.th.koeln.archilab.fae.faeteam2service.kafka.events.DomainEvent;
import de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.ZonenAbweichungDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
public class ZonenAbweichungEvent extends DomainEvent {

    @JsonProperty("payload")
    @Getter
    private final ZonenAbweichungDTO zonenAbweichungDTO;

    private final CrudEventType type;

    public ZonenAbweichungEvent(String key, int version, String timestamp, CrudEventType type, ZonenAbweichungDTO zonenAbweichungDTO) {
        super(key, version, timestamp);
        this.zonenAbweichungDTO = zonenAbweichungDTO;
        this.type = type;
    }

    @Override
    public String getType() {
        return type.name();
    }
}
