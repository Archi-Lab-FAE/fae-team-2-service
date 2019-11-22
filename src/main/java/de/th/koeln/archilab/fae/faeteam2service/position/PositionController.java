package de.th.koeln.archilab.fae.faeteam2service.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PositionController {

    private PositionRepository positionRepository;

    @Autowired
    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @PostMapping ("/positions")
    public ResponseEntity<Position> newPosition(@RequestBody Position newPosition) {
        return new ResponseEntity<>(positionRepository.save(newPosition), HttpStatus.CREATED);
    }

    @GetMapping("/positions")
    public ResponseEntity<List<Position>> positions() {
        return new ResponseEntity<>((List<Position>) positionRepository.findAll(), HttpStatus.OK);
    }


}
