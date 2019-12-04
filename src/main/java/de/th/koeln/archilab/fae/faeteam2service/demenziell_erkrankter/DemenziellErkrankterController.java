package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demenziellerkrankter")
public class DemenziellErkrankterController {

    private DemenziellErkrankterRepository demenziellErkrankterRepository;

    @Autowired
    public DemenziellErkrankterController(DemenziellErkrankterRepository demenziellErkrankterRepository) {
        this.demenziellErkrankterRepository = demenziellErkrankterRepository;
    }

    @GetMapping
    public ResponseEntity<List<DemenziellErkrankter>> heimbewohner() {
        return new ResponseEntity<>((List<DemenziellErkrankter>) demenziellErkrankterRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DemenziellErkrankter> newHeimbewohner(@RequestBody DemenziellErkrankter demenziellErkrankter) {
        return new ResponseEntity<>(demenziellErkrankterRepository.save(demenziellErkrankter), HttpStatus.CREATED);
    }

}
