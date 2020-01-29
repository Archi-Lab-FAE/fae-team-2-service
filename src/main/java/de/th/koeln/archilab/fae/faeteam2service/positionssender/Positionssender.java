package de.th.koeln.archilab.fae.faeteam2service.positionssender;


import com.grum.geocalc.BoundingArea;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.events.ZonenabweichungKafkaPublisher;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;
import lombok.Data;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Configurable
@Data
public class Positionssender {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE_TIME;
    private static final Logger log = LoggerFactory.getLogger(Positionssender.class);

    @Id
    private String positionssenderId;

    private String letztesSignal;

    private Float batterieStatus;

    private Float genauigkeit;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Position position;

    @ManyToOne(fetch = FetchType.EAGER)
    private DemenziellErkrankter demenziellErkrankter;

    @Transient
    private ZonenabweichungKafkaPublisher eventPublisher;


    public Positionssender() {
        this(null, null, null, null);
    }

    public Positionssender(OffsetDateTime letztesSignal, Float batterieStatus, Float genauigkeit, Position position) {
        positionssenderId = UUID.randomUUID().toString();

        if (letztesSignal == null) this.letztesSignal = null;
        else this.letztesSignal = letztesSignal.format(DATE_FORMAT);

        this.batterieStatus = batterieStatus;
        this.genauigkeit = genauigkeit;
        this.position = position;
    }

    public void update(Positionssender update) {
        if (StringUtils.isNotBlank(update.positionssenderId))
            positionssenderId = update.getPositionssenderId();
        if (update.letztesSignal != null) letztesSignal = update.getLetztesSignal();
        if (update.batterieStatus != null) batterieStatus = update.getBatterieStatus();
        if (update.genauigkeit != null) genauigkeit = update.getGenauigkeit();
        if (update.position != null) position = update.getPosition();
        if (update.demenziellErkrankter != null) demenziellErkrankter = update.getDemenziellErkrankter();
    }


    public boolean isInZone(Zone zone) {
        if (position.getLaengengrad() != null && position.getBreitengrad() != null) {
            Point positionPoint = position.toPoint();

            List<Position> positionsliste = new ArrayList<>(zone.getPositionen());

            if (positionsliste.size() == 2) {

                Point northWest = positionsliste.get(0).toPoint();
                Point southEast = positionsliste.get(1).toPoint();

                BoundingArea area = BoundingArea.at(northWest, southEast);

                return area.contains(positionPoint);
            }
        }

        return false;
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
        entity.positionssenderId = dto.getPositionssenderId();
        entity.letztesSignal = dto.getLetztesSignal().format(DATE_FORMAT);
        entity.batterieStatus = dto.getBatterieStatus();
        entity.genauigkeit = dto.getGenauigkeit();
        entity.position = Position.convert(dto.getPosition());

        return entity;
    }

    public static PositionssenderDTO convert(Positionssender entity) {
        PositionssenderDTO dto = new PositionssenderDTO();
        dto.setPositionssenderId(entity.positionssenderId);
        dto.setLetztesSignal(OffsetDateTime.parse(entity.letztesSignal, DATE_FORMAT));
        dto.setBatterieStatus(entity.batterieStatus);
        dto.setGenauigkeit(entity.genauigkeit);
        dto.setPosition(Position.convert(entity.position));

        return dto;
    }


    public void setPosition(Position position) {
        this.position = position;

        if (demenziellErkrankter == null
                || demenziellErkrankter.getZonen() == null
                || demenziellErkrankter.getZonen().isEmpty()
        ) return;

        val zonen = demenziellErkrankter.getZonen();
        boolean isInGewohnteZone = false;

        for (Zone zone : zonen) {
            if (zone.getTyp() == ZonenTyp.GEWOHNT && position.inZone(zone)) {
                isInGewohnteZone = true;
            } else if (position.inZone(zone)) {
                val msg = "Achtung, " + demenziellErkrankter.getName() + " hat eine ungewohnte Zone betreten.";
                eventPublisher.publishZonenabweichung(this, msg);
                // TODO REST-CALL team4

                // @Value("${messagingSystem.url}")
            }
        }

        if (!isInGewohnteZone) {
            val msg = "Achtung, " + demenziellErkrankter.getName() + " hat die gewohnte Zone verlassen.";
            eventPublisher.publishZonenabweichung(this, msg);
            // TODO REST-CALL team4
        }
    }
}
