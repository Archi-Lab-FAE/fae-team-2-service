package de.th.koeln.archilab.fae.faeteam2service.positionssender.events.tracking;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.th.koeln.archilab.fae.faeteam2service.position.PositionDto;
import lombok.Data;

@Data
public class TrackerDto {

    private String trackerId;

    @JsonProperty("currentPosition")
    private PositionDto positionDto;
}
