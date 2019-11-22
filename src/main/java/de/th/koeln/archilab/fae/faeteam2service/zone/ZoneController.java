package de.th.koeln.archilab.fae.faeteam2service.zone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;

@RestController
public class ZoneController {

    private ZoneRepository zoneRepository;

    @Autowired
    public ZoneController(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @GetMapping("/zonen")
    public Iterable<Zone> zonen(){
        return zoneRepository.findAll();
    }

    @PostMapping("/zonen")
    public Zone newZone(@RequestBody Zone newZone){
        return zoneRepository.save(newZone);
    }

    @GetMapping("/zonen/{id}/positionen")
    public Iterable<Position> zonenPositionen(@PathVariable UUID id){
        Optional<Zone> zone =zoneRepository.findById(id);
        if(zone.isPresent()){
            return zone.get().getPositionen();
        }else{
            return new ArrayList<>();
        }
    }
}
