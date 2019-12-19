package de.th.koeln.archilab.fae.faeteam2service.positionssender;


import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Positionssender {

    @Id
    private String positionssenderId;

    private String letztesSignal;

    private Float batterieStatus;

    private Float genauigkeit;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Position position;

}
