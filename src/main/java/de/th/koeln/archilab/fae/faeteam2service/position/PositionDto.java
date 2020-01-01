package de.th.koeln.archilab.fae.faeteam2service.position;

import lombok.Data;

@Data
public class PositionDto {

    private float longitude;
    private float latitude;
    private float altitude;

    public Position convertToEntity() {
        return new Position(longitude, latitude);
    }
}
