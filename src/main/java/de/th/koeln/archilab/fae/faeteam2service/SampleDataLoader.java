package de.th.koeln.archilab.fae.faeteam2service;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterRepository;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.position.PositionRepository;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.Positionssender;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.PositionssenderRepository;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneRepository;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.*;

@Component
@Profile("local")
public class SampleDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final Random rng = new Random();

    private final DemenziellErkrankterRepository erkrankterRepository;
    private final PositionRepository posRepository;
    private final PositionssenderRepository senderRepository;
    private final ZoneRepository zoneRepository;

    @Autowired
    public SampleDataLoader(DemenziellErkrankterRepository erkrankterRepository, PositionRepository posRepository,
                            PositionssenderRepository senderRepository, ZoneRepository zoneRepository) {
        this.erkrankterRepository = erkrankterRepository;
        this.posRepository = posRepository;
        this.senderRepository = senderRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Set<Zone> zonen;

        //Demenziell Erkrankte
        zonen = new HashSet<>();
        val lohler = new DemenziellErkrankter("Kutz", "Löhler");
        zonen.add(new Zone(ZonenTyp.GEWOHNT, lohler, generateAndSaveRandomPositions(4)));
        zonen.add(new Zone(ZonenTyp.GEWOHNT, lohler, generateAndSaveRandomPositions(6)));
        zonen.add(new Zone(ZonenTyp.UNGEWOHNT, lohler, generateAndSaveRandomPositions(5)));
        erkrankterRepository.save(lohler);
        zoneRepository.saveAll(zonen);


        zonen = new HashSet<>();
        val duderus = new DemenziellErkrankter("Bennis", "Duderus");
        zonen.add(new Zone(ZonenTyp.UNGEWOHNT, duderus, generateAndSaveRandomPositions(3)));
        zonen.add(new Zone(ZonenTyp.UNGEWOHNT, duderus, generateAndSaveRandomPositions(4)));
        erkrankterRepository.save(duderus);
        zoneRepository.saveAll(zonen);


        zonen = new HashSet<>();
        val hocke = new DemenziellErkrankter("Kleiner", "Hocke");
        zonen.add(new Zone(ZonenTyp.UNGEWOHNT, hocke, generateAndSaveRandomPositions(4)));
        erkrankterRepository.save(hocke);
        zoneRepository.saveAll(zonen);


        //Positionssender
        val trackerA = new Positionssender(getRandomDate(), getRandomDate(), generateAndSaveRandomPosition());
        trackerA.setPositionssenderId("73839307-86b3-3df5-aa4f-79f5db505bc5");
        trackerA.setDemenziellErkrankter(lohler);
        val trackerB = new Positionssender(getRandomDate(), getRandomDate(), generateAndSaveRandomPosition());
        trackerB.setPositionssenderId("2f9259fd-3ba7-3f8d-8ab5-f1547121075b");
        trackerB.setDemenziellErkrankter(duderus);
        val trackerC = new Positionssender(getRandomDate(), getRandomDate(), generateAndSaveRandomPosition());
        trackerC.setPositionssenderId("fac154b2-8c00-385a-82eb-8313336f0ba4");
        trackerC.setDemenziellErkrankter(hocke);
        senderRepository.save(trackerA);
        senderRepository.save(trackerB);
        senderRepository.save(trackerC);
    }


    private List<Position> generateAndSaveRandomPositions(int count) {
        List<Position> positionen = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            positionen.add(new Position(rng.nextDouble() * 1000, rng.nextDouble() * 1000));
        }

        posRepository.saveAll(positionen);
        return positionen;
    }

    private Position generateAndSaveRandomPosition() {
        return posRepository.save(new Position(rng.nextDouble() * 1000, rng.nextDouble() * 1000));
    }


    private OffsetDateTime getRandomDate() {
        return OffsetDateTime.of(
                2018,
                rng.nextInt(12) + 1,
                rng.nextInt(25) + 1,
                rng.nextInt(23) + 1,
                rng.nextInt(59) + 1,
                rng.nextInt(59) + 1,
                0,
                ZoneOffset.UTC
        );
    }

}
