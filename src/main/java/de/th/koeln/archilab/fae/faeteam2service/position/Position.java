package de.th.koeln.archilab.fae.faeteam2service.position;

import com.grum.geocalc.BoundingArea;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.Point;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import lombok.Data;
import lombok.val;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Data
public class Position {

    @Id
    private String positionsId;

    private Double laengengrad;

    private Double breitengrad;

    public Position() {
        this(null, null);
    }

    public Position(Double laengengrad, Double breitengrad) {
        this.positionsId = UUID.randomUUID().toString();
        this.laengengrad = laengengrad;
        this.breitengrad = breitengrad;
    }

    public void update(Position update) {
        if (StringUtils.isNotBlank(update.positionsId)) positionsId = update.getPositionsId();
        laengengrad = update.getLaengengrad();
        breitengrad = update.getBreitengrad();
    }

    public static Position convert(PositionDTO dto) {
        Position entity = new Position();
        entity.laengengrad = dto.getLaengengrad();
        entity.breitengrad = dto.getBreitengrad();

        return entity;
    }

    public static PositionDTO convert(Position entity) {
        PositionDTO dto = new PositionDTO();
        dto.setLaengengrad(entity.laengengrad);
        dto.setBreitengrad(entity.breitengrad);

        return dto;
    }

    public Point toPoint() {
        return Point.at(Coordinate.fromDegrees(breitengrad), Coordinate.fromDegrees(laengengrad));
    }

    public boolean inZone(Zone zone) {
        val positionenList = new ArrayList<Position>(zone.getPositionen());

        Point northWest = positionenList.get(0).toPoint();
        Point southEast = positionenList.get(1).toPoint();

        BoundingArea area = BoundingArea.at(northWest, southEast);

        return area.contains(toPoint());
    }
}