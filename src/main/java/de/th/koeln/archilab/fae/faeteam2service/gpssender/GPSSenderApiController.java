package de.th.koeln.archilab.fae.faeteam2service.gpssender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sender")
public class GPSSenderApiController {

    private GPSSenderRepository senderRepository;

    @Autowired
    public GPSSenderApiController(GPSSenderRepository senderRepository) {
        this.senderRepository = senderRepository;
    }


    @PostMapping
    public ResponseEntity<GPSSender> createSender(@RequestBody GPSSender sender) {
        senderRepository.save(sender);
        return new ResponseEntity<>(sender, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSenderById(@PathVariable long id) {
        if (senderRepository.existsById(id)) {
            senderRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<GPSSender>> getAllZones() {
        return new ResponseEntity<>((List<GPSSender>) senderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GPSSender> getSenderById(@PathVariable long id) {
        var sender = senderRepository.findById(id);
        if (sender.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(sender.get(), HttpStatus.OK);
        }
    }

}
