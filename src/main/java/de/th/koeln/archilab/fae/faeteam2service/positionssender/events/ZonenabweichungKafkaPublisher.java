package de.th.koeln.archilab.fae.faeteam2service.positionssender.events;

import de.th.koeln.archilab.fae.faeteam2service.positionssender.Positionssender;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.Clock;
import java.time.OffsetDateTime;

/**
 * Class to publish the event of a Zonenabweichung. It is consumed by team 3 - Nachrichtenteam.
 * Structure for the publishing:
 * @see <a href="https://fae.archi-lab.io/global/2020/01/10/team-3-Event-Structure.html">Event Structure Definition</a>
 */
@Component
public class ZonenabweichungKafkaPublisher {

    private final ZonenabweichungKafkaGateway kafkaGateway;
    private static final int EVENT_VERSION = 1;

    @Autowired
    public ZonenabweichungKafkaPublisher(ZonenabweichungKafkaGateway kafkaGateway) {
        this.kafkaGateway = kafkaGateway;
    }

    public void publishZonenabweichung(Positionssender positionssender, String msg) {
        val event = new ZonenabweichungEvent(
                positionssender.getPositionssenderId(),
                EVENT_VERSION,
                OffsetDateTime.now(Clock.systemUTC()).toString(),
                new ZonenabweichungMessage(positionssender.getPositionssenderId(), positionssender.getPosition(), msg)
        );

        kafkaGateway.publishZonenabweichungEvent(event);
    }
}
