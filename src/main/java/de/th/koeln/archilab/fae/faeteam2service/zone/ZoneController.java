package de.th.koeln.archilab.fae.faeteam2service.zone;

import de.th.koeln.archilab.fae.faeteam2service.position.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ZoneController {
    @Autowired
    private ZoneRepository zoneRepository;

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
