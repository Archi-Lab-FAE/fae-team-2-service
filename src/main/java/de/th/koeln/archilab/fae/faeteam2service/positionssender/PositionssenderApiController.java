package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positionssender")
public class PositionssenderApiController {

    private PositionssenderRepository senderRepository;

    @Autowired
    public PositionssenderApiController(PositionssenderRepository senderRepository) {
        this.senderRepository = senderRepository;
    }


    @PostMapping
    public ResponseEntity<Positionssender> createSender(@RequestBody Positionssender sender) {
        var neuerSender = senderRepository.save(sender);
        return new ResponseEntity<>(neuerSender, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSenderById(@PathVariable String id) {
        if (senderRepository.existsById(id)) {
            senderRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Positionssender>> getAllZones() {
        return new ResponseEntity<>((List<Positionssender>) senderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Positionssender> getSenderById(@PathVariable String id) {
        var sender = senderRepository.findById(id);
        if (!sender.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(sender.get(), HttpStatus.OK);
        }
    }

}
