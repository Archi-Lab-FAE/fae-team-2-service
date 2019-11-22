package de.th.koeln.archilab.fae.faeteam2service.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PositionController {

    private PositionRepository positionRepository;

    @Autowired
    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @PostMapping ("/positions")
    public Position newPosition(@RequestBody Position newPosition){
        return positionRepository.save(newPosition);
    }

    @GetMapping("/positions")
    public Iterable<Position> positions() {
        return positionRepository.findAll();
    }


}
