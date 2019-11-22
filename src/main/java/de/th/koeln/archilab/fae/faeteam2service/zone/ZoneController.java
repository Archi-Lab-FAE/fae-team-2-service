package de.th.koeln.archilab.fae.faeteam2service.zone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;

@RestController
@RequestMapping("/zonen")
public class ZoneController {

    private ZoneRepository zoneRepository;

    @Autowired
    public ZoneController(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @GetMapping
    public ResponseEntity<List<Zone>> zonen() {
        return new ResponseEntity<>((List<Zone>) zoneRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Zone> newZone(@RequestBody Zone newZone) {
        return new ResponseEntity<>(zoneRepository.save(newZone), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/positionen")
    public ResponseEntity<Iterable<Position>> zonenPositionen(@PathVariable long id) {
        var zone = zoneRepository.findById(id);
        if (zone.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(zone.get().getPositionen(), HttpStatus.OK);
        }
    }
}
