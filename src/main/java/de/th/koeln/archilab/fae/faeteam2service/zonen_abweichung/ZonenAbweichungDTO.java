package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.th.koeln.archilab.fae.faeteam2service.position.PositionDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ZonenAbweichungDTO {
    private String positionssenderId;
    private PositionDTO position;

    @JsonProperty("text")
    private String message;
}
