package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import org.springframework.data.repository.CrudRepository;

public interface PositionssenderRepository extends CrudRepository<Positionssender, String> {
    Iterable<Positionssender> findByDemenziellErkrankter(DemenziellErkrankter demenziellErkrankter);
}
