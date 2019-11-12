package de.th.koeln.archilab.fae.faeteam2service.gpssender;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GPSSender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String letztesSignal;

    private Float batterieStatus;

    private Float genauigkeit;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Position position;

}
