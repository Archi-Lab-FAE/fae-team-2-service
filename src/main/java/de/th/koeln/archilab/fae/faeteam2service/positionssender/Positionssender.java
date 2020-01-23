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
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import javax.persistence.*;
import java.io.InvalidObjectException;
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

    public static List<PositionssenderDTO> positionssenderInZone(List<PositionssenderDTO> posSender, Zone zone) throws InvalidObjectException {
        Positionssender positionssender;
        List<PositionssenderDTO> result = new ArrayList<>();
        for (PositionssenderDTO sender : posSender) {
            positionssender = convert(sender);
            if (positionssender.getPosition().getLaengengrad() != null & positionssender.getPosition().getBreitengrad() != null) {
                Point positionPoint = Point.at(Coordinate.fromDegrees(positionssender.getPosition().getBreitengrad()),
                        Coordinate.fromDegrees(positionssender.getPosition().getLaengengrad()));

                List<Position> positionsliste = new ArrayList<>(zone.getPositionen());

                //TODO: Vielleicht Decision wie die Punkte definiert werden. NorthWest an Position 0 oder als Attribute?
                if (positionsliste.size() == 2) {
                    Point northWest = Point.at(Coordinate.fromDegrees(positionsliste.get(0).getBreitengrad()),
                            Coordinate.fromDegrees(positionsliste.get(0).getLaengengrad()));
                    Point southEast = Point.at(Coordinate.fromDegrees(positionsliste.get(1).getBreitengrad()),
                            Coordinate.fromDegrees(positionsliste.get(1).getLaengengrad()));

                    BoundingArea area = BoundingArea.at(northWest, southEast);

                    if (area.contains(positionPoint)) {
                        result.add(convert(positionssender));
                    }
                } else {
                    throw new InvalidObjectException("Zone wurde nicht korrekt initialisiert");
                }
            }
        }
        return result;
    }

    public static List<PositionssenderDTO> positionssenderInnerhalbRadius(List<PositionssenderDTO> posSender, Double radius, Position position) {
        Positionssender positionssender;
        List<PositionssenderDTO> result = new ArrayList<>();
        Point ursprung = Point.at(Coordinate.fromDegrees(position.getBreitengrad()), Coordinate.fromDegrees(position.getLaengengrad()));
        BoundingArea kreisArea = EarthCalc.around(ursprung, radius);

        for (PositionssenderDTO sender : posSender) {
            positionssender = convert(sender);
            if (positionssender.getPosition().getBreitengrad() != null && positionssender.getPosition().getLaengengrad() != null) {
                log.debug("{}", positionssender.getPosition().getBreitengrad());
                log.debug("{}", positionssender.getPosition().getLaengengrad());

                Point positionssenderPunkt = Point.at(Coordinate.fromDegrees(positionssender.getPosition().getBreitengrad()), Coordinate.fromDegrees(positionssender.getPosition().getLaengengrad()));
                if (kreisArea.contains(positionssenderPunkt)) {
                    result.add(convert(positionssender));
                }
            }
        }
        return result;
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

        val zonen = demenziellErkrankter.getZonen();
        var isInGewohnteZone = false;

        for (Zone zone : zonen) {
            if (zone.getTyp() == ZonenTyp.GEWOHNT && position.inZone(zone)) {
                isInGewohnteZone = true;
            } else if (position.inZone(zone)) {
                val msg = "Achtung, " + demenziellErkrankter.getName() + " hat eine ungewohnte Zone betreten.";
                eventPublisher.publishZonenabweichung(this, msg);
                // TODO REST-CALL team4
            }
        }

        if (!isInGewohnteZone) {
            val msg = "Achtung, " + demenziellErkrankter.getName() + " hat die gewohnte Zone verlassen.";
            eventPublisher.publishZonenabweichung(this, msg);
            // TODO REST-CALL team4
        }
    }
}
