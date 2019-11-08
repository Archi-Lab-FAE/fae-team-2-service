package de.th.koeln.archilab.fae.faeteam2service.position;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Position {
    @JsonUnwrapped
    @Id
    private UUID uuid;
    private float laengengrad;
    private float breitengrad;

    public Position(){
        this.uuid= UUID.randomUUID();
    }
    public Position(float laenge, float breite){
        this.uuid = UUID.randomUUID();
        this.breitengrad = breite;
        this.laengengrad = laenge;
    }

}