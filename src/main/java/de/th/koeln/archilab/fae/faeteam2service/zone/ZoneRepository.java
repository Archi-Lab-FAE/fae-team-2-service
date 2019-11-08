package de.th.koeln.archilab.fae.faeteam2service.zone;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ZoneRepository extends CrudRepository<Zone, UUID> {
}
