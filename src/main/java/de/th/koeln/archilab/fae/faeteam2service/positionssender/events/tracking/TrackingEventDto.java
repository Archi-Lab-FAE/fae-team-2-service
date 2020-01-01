package de.th.koeln.archilab.fae.faeteam2service.positionssender.events.tracking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TrackingEventDto {

    @JsonProperty("payload")
    private TrackerDto trackerDto;

    private String id;
    private String time;
    private String type;
    private String version;
    private String key;
}
