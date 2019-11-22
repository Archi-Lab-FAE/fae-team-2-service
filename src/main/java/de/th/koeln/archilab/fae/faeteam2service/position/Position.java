package de.th.koeln.archilab.fae.faeteam2service.position;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long positionsId;

    private float laengengrad;

    private float breitengrad;

}