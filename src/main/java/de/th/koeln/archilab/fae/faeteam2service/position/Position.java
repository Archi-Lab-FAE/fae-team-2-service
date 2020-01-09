package de.th.koeln.archilab.fae.faeteam2service.position;

import org.apache.commons.lang.StringUtils;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

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
        entity.positionsId = dto.getPositionsId();
        entity.laengengrad = dto.getLaengengrad();
        entity.breitengrad = dto.getBreitengrad();

        return entity;
    }

    public static PositionDTO convert(Position entity) {
        PositionDTO dto = new PositionDTO();
        dto.setPositionsId(entity.positionsId);
        dto.setLaengengrad(entity.laengengrad);
        dto.setBreitengrad(entity.breitengrad);

        return dto;
    }
}