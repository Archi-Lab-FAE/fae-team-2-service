package de.th.koeln.archilab.fae.faeteam2service.heimbewohner;

import de.th.koeln.archilab.fae.faeteam2service.verantwortlicher.Verantwortlicher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

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

    @GetMapping("/heimbewohner/{id}/verantwortlicher")
    public Iterable<Verantwortlicher> heimbewohnerVerantwortliche(@PathVariable Integer id) {
        Optional<Heimbewohner> heimbewohner = heimbewohnerRepository.findById(id);
        if (heimbewohner.isPresent())
            return heimbewohner.get().getVerantwortliche();
        else
            return new ArrayList<>();
    }
}
