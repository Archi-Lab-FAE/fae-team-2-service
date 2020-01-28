package de.th.koeln.archilab.fae.faeteam2service.zonenausnahme;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class ZonenAusnahme {

    @Id
    @GeneratedValue
    private long zonenAusnahmeId;

    @NotNull
    private LocalDateTime entstanden;

    private boolean abgeschlossen;

    /**
     * Create a new instance with {@link #entstanden} set to the current time and
     * {@link #abgeschlossen} to false.
     */
    public ZonenAusnahme() {
        this(LocalDateTime.now(), false);
    }

    public ZonenAusnahme(LocalDateTime entstanden, boolean abgeschlossen) {
        this.entstanden = entstanden;
        this.abgeschlossen = abgeschlossen;
    }

}
