package de.th.koeln.archilab.fae.faeteam2service.verantwortlicher;

import de.th.koeln.archilab.fae.faeteam2service.heimbewohner.Heimbewohner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class VerantwortlicherController {

    @Autowired
    private VerantwortlicherRepository verantwortlicherRepository;

    @GetMapping("verantwortlicher")
    public Iterable<Verantwortlicher> verantwortliche() {
        return verantwortlicherRepository.findAll();
    }

    @PostMapping("verantwortlicher")
    public Verantwortlicher newVerantwortlicher(@RequestBody Verantwortlicher verantwortlicher) {
        return verantwortlicherRepository.save(verantwortlicher);
    }

    @GetMapping("/verantwortlicher/{id}/heimbewohner")
    public Iterable<Heimbewohner> verantwortlicherHeimbewohner(@PathVariable Integer id) {
        Optional<Verantwortlicher> verantwortlicher = verantwortlicherRepository.findById(id);
        if (verantwortlicher.isPresent())
            return verantwortlicher.get().getHeimbewohner();
        else
            return new ArrayList<>();
    }
}
