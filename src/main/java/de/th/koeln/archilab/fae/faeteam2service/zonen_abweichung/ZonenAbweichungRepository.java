package de.th.koeln.archilab.fae.faeteam2service.zonen_abweichung;

import org.springframework.data.repository.CrudRepository;

public interface ZonenAbweichungRepository extends CrudRepository<ZonenAbweichung, Long> {

    Iterable<ZonenAbweichung> findAllByAbgeschlossenFalse();
}
