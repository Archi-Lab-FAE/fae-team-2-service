package de.th.koeln.archilab.fae.faeteam2service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterRepository;
import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import de.th.koeln.archilab.fae.faeteam2service.position.PositionRepository;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.Positionssender;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.PositionssenderRepository;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneRepository;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZonenTyp;

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
        zonen.add(new Zone(.8f, ZonenTyp.GEWOHNT, generateAndSaveRandomPositions(4)));
        zonen.add(new Zone(.5f, ZonenTyp.GEWOHNT, generateAndSaveRandomPositions(6)));
        zonen.add(new Zone(.7f, ZonenTyp.UNGEWOHNT, generateAndSaveRandomPositions(5)));
        zoneRepository.saveAll(zonen);
        erkrankterRepository.save(new DemenziellErkrankter("K. Löhler", zonen));


        zonen = new HashSet<>();
        zonen.add(new Zone(.2f, ZonenTyp.UNGEWOHNT, generateAndSaveRandomPositions(3)));
        zonen.add(new Zone(.25f, ZonenTyp.UNGEWOHNT, generateAndSaveRandomPositions(4)));
        zoneRepository.saveAll(zonen);
        erkrankterRepository.save(new DemenziellErkrankter("B. Duderus", zonen));


        zonen = new HashSet<>();
        zonen.add(new Zone(.123f, ZonenTyp.UNGEWOHNT, generateAndSaveRandomPositions(4)));
        zoneRepository.saveAll(zonen);
        erkrankterRepository.save(new DemenziellErkrankter("K. Hocke", zonen));


        //Positionssender
        senderRepository.save(new Positionssender(getRandomDate(), .8f, .6f, generateAndSaveRandomPosition()));
        senderRepository.save(new Positionssender(getRandomDate(), .4f, .74f, generateAndSaveRandomPosition()));
        senderRepository.save(new Positionssender(getRandomDate(), .12f, .42f, generateAndSaveRandomPosition()));
    }


    private Set<Position> generateAndSaveRandomPositions(int count) {
        Set<Position> positionen = new HashSet<>();
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
                2019,
                rng.nextInt(12) + 1,
                rng.nextInt(31) + 1,
                rng.nextInt(23) + 1,
                rng.nextInt(59) + 1,
                rng.nextInt(59) + 1,
                0,
                ZoneOffset.UTC
        );
    }

}
