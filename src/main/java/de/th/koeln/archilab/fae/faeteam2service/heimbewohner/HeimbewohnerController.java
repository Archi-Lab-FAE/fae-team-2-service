package de.th.koeln.archilab.fae.faeteam2service.heimbewohner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeimbewohnerController {
    @Autowired
    private HeimbewohnerRepository heimbewohnerRepository;

    @GetMapping("/heimbewohner")
    public Iterable<Heimbewohner> heimbewohner() {
        return heimbewohnerRepository.findAll();
    }

    @PostMapping("/heimbewohner")
    public Heimbewohner newHeimbewohner(@RequestBody Heimbewohner heimbewohner) {
        return heimbewohnerRepository.save(heimbewohner);
    }

}
