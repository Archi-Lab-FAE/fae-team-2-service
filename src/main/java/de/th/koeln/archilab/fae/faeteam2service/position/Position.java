package de.th.koeln.archilab.fae.faeteam2service.position;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Position {

    @Id
    private String positionsId;

    private float laengengrad;

    private float breitengrad;

    public Position() {
        this.positionsId = UUID.randomUUID().toString();
    }

    public Position(float laengengrad, float breitengrad) {
        this.positionsId = UUID.randomUUID().toString();
        this.laengengrad = laengengrad;
        this.breitengrad = breitengrad;
    }
}