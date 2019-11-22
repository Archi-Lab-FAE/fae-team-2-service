package de.th.koeln.archilab.fae.faeteam2service.heimbewohner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/heimbewohner")
public class HeimbewohnerController {

    private HeimbewohnerRepository heimbewohnerRepository;

    @Autowired
    public HeimbewohnerController(HeimbewohnerRepository heimbewohnerRepository) {
        this.heimbewohnerRepository = heimbewohnerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Heimbewohner>> heimbewohner() {
        return new ResponseEntity<>((List<Heimbewohner>) heimbewohnerRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Heimbewohner> newHeimbewohner(@RequestBody Heimbewohner heimbewohner) {
        return new ResponseEntity<>(heimbewohnerRepository.save(heimbewohner), HttpStatus.CREATED);
    }

}
