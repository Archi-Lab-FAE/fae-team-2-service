package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.Positionssender;
import de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.event.ZonenAbweichungEventHandler;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This class represents data which are relevant for a zone event like going out of a zone.
 *
 * @see <a href="https://fae.archi-lab.io/glossary/2020/01/28/Glossary-Zonen-Ausnahme.html">Glossary Definition</a>
 */
@Data
@Entity
@EntityListeners(ZonenAbweichungEventHandler.class)
public class ZonenAbweichung {

    @Id
    private String zonenAusnahmeId;

    @NotNull
    private LocalDateTime entstanden;

    private boolean abgeschlossen;

    private String positionssenderId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Position position;

    private String message;

    /**
     * Create a new instance with {@link #entstanden} set to the current time and
     * {@link #abgeschlossen} to false.
     */
    public ZonenAbweichung(
            Positionssender positionssender,
            String message
    ) {
        this(
                LocalDateTime.now(),
                false,
                positionssender.getPositionssenderId(),
                positionssender.getPosition(),
                message
        );
    }

    public ZonenAbweichung(
            LocalDateTime entstanden,
            boolean abgeschlossen,
            String positionssenderId,
            Position position,
            String message
    ) {
        this.zonenAusnahmeId = UUID.randomUUID().toString();
        this.entstanden = entstanden;
        this.abgeschlossen = abgeschlossen;
        this.positionssenderId = positionssenderId;
        this.position = position;
        this.message = message;
    }

    public static ZonenAbweichungDTO convert(ZonenAbweichung zonenAbweichung) {
        return new ZonenAbweichungDTO(
                zonenAbweichung.positionssenderId,
                Position.convert(zonenAbweichung.position),
                zonenAbweichung.message
        );
    }
}
