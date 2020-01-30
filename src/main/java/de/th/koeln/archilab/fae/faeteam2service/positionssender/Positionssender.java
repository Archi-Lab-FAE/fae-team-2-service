package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import com.grum.geocalc.BoundingArea;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import de.th.koeln.archilab.fae.faeteam2service.BeanUtil;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneRepository;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;
import de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.ZonenAbweichung;
import de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung.ZonenAbweichungRepository;
import lombok.Data;
import lombok.val;

/**
 * This class stores all data of the positionssender for the Zonenalarmsystem.
 * @see <a href="https://fae.archi-lab.io/glossary/2019/11/15/Glossary-Positionssender.html">Glossary Definition</a>
 */
@Entity
@Data
public class Positionssender {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE_TIME;
    private static final Logger log = LoggerFactory.getLogger(Positionssender.class);

    @Id
    private String positionssenderId;

    private String letztesSignal;

    private String letzteWartung;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Position position;

    @ManyToOne(fetch = FetchType.EAGER)
    private DemenziellErkrankter demenziellErkrankter;

    @Transient
    private ZonenAbweichungRepository zonenAbweichungRepository;
    @Transient
    private ZoneRepository zoneRepository;


    private Positionssender() {
    }

    public Positionssender(OffsetDateTime letztesSignal, OffsetDateTime letzteWartung, Position position) {
        positionssenderId = UUID.randomUUID().toString();

        if (letztesSignal == null) this.letztesSignal = null;
        else this.letztesSignal = letztesSignal.format(DATE_FORMAT);

        if (letzteWartung == null) this.letzteWartung = null;
        else this.letzteWartung = letzteWartung.format(DATE_FORMAT);

        this.position = position;

        zonenAbweichungRepository = BeanUtil.getBean(ZonenAbweichungRepository.class);
        zoneRepository = BeanUtil.getBean(ZoneRepository.class);
    }

    public void update(Positionssender update) {
        if (StringUtils.isNotBlank(update.positionssenderId))
            positionssenderId = update.getPositionssenderId();
        if (update.letztesSignal != null) letztesSignal = update.getLetztesSignal();
        if (update.letzteWartung != null) letzteWartung = update.getLetzteWartung();
        if (update.position != null) position = update.getPosition();
        if (update.demenziellErkrankter != null) demenziellErkrankter = update.getDemenziellErkrankter();
    }

    public boolean isInnerhalbRadius(double radius, double laengengrad, double breitengrad) {

        if (position.getBreitengrad() != null && position.getLaengengrad() != null) {
            log.debug("{}", position.getBreitengrad());
            log.debug("{}", position.getLaengengrad());

            Point ursprung = Point.at(Coordinate.fromDegrees(breitengrad), Coordinate.fromDegrees(laengengrad));
            BoundingArea kreisArea = EarthCalc.around(ursprung, radius);
            Point positionssenderPunkt = position.toPoint();

            return kreisArea.contains(positionssenderPunkt);
        }

        return false;
    }

    public static Positionssender convert(PositionssenderDTO dto) {
        Positionssender entity = new Positionssender();
        entity.positionssenderId = dto.getId();
        entity.letztesSignal = dto.getLetztesSignal().format(DATE_FORMAT);
        entity.letzteWartung = dto.getLetzteWartung().format(DATE_FORMAT);

        entity.position = Position.convert(dto.getPosition());

        return entity;
    }

    public static PositionssenderDTO convert(Positionssender entity) {
        PositionssenderDTO dto = new PositionssenderDTO();
        dto.setId(entity.positionssenderId);
        dto.setLetztesSignal(OffsetDateTime.parse(entity.letztesSignal, DATE_FORMAT));
        dto.setLetzteWartung(OffsetDateTime.parse(entity.letzteWartung, DATE_FORMAT));
        dto.setPosition(Position.convert(entity.position));

        return dto;
    }


    public void setPosition(Position position) {
        this.position = position;

        if (demenziellErkrankter == null) return;
        Iterable<Zone> zonen = zoneRepository.findAllByDemenziellErkrankter(demenziellErkrankter);
        boolean isInGewohnteZone = false;

        for (Zone zone : zonen) {
            if (zone.getTyp() == ZonenTyp.GEWOHNT && position.inZone(zone)) {
                isInGewohnteZone = true;
            } else if (position.inZone(zone)) {
                val msg = "Achtung, " + demenziellErkrankter.getName() + " hat eine ungewohnte Zone betreten.";
                val zonenAusnahme = new ZonenAbweichung(this, msg);
                zonenAbweichungRepository.save(zonenAusnahme);
            }
        }

        if (!isInGewohnteZone) {
            val msg = "Achtung, " + demenziellErkrankter.getName() + " hat die gewohnte Zone verlassen.";
            val zonenAusnahme = new ZonenAbweichung(this, msg);
            zonenAbweichungRepository.save(zonenAusnahme);
        }
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Positionssender p = (Positionssender) o;
        return Objects.equals(this.demenziellErkrankter, p.demenziellErkrankter) &&
                Objects.equals(this.letzteWartung, p.letzteWartung) &&
                Objects.equals(this.letztesSignal, p.letztesSignal) &&
                Objects.equals(this.position, p.position);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
                positionssenderId,
                demenziellErkrankter,
                letzteWartung,
                letztesSignal,
                position
        );
    }
}
