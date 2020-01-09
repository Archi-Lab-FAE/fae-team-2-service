package de.th.koeln.archilab.fae.faeteam2service.positionssender.events.tracking;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import lombok.Data;

@Data
public class TrackerDto {

    private String trackerId;

    @JsonProperty("currentPosition")
    private TrackerPositionsDTO positionDTO;

    @Data
    public static class TrackerPositionsDTO {

        private double longitude;
        private double latitude;
        private double altitude;

        public static Position convert(TrackerPositionsDTO dto) {
            return new Position(dto.longitude, dto.latitude);
        }
    }
}
