package de.th.koeln.archilab.fae.faeteam2service.zonenausnahme;

import org.springframework.data.repository.CrudRepository;

public interface ZonenAusnahmeRepository extends CrudRepository<ZonenAusnahme, Long> {

    Iterable<ZonenAusnahme> findAllByAbgeschlossenFalse();
}
