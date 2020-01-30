package de.th.koeln.archilab.fae.faeteam2service.zone;

import org.springframework.data.repository.CrudRepository;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;

public interface ZoneRepository extends CrudRepository<Zone, String> {

    Iterable<Zone> findAllByDemenziellErkrankter(DemenziellErkrankter demenziellErkrankter);
}
