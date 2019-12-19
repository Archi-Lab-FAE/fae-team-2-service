package de.th.koeln.archilab.fae.faeteam2service.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positionen")
public class PositionController {

    private PositionRepository positionRepository;

    @Autowired
    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @PostMapping
    public ResponseEntity<Position> newPosition(@RequestBody Position newPosition) {
        return new ResponseEntity<>(positionRepository.save(newPosition), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Position>> positions() {
        return new ResponseEntity<>((List<Position>) positionRepository.findAll(), HttpStatus.OK);
    }


}
