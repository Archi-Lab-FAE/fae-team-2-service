package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.Positionssender;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.PositionssenderDTO;
import de.th.koeln.archilab.fae.faeteam2service.positionssender.PositionssenderRepository;
import io.swagger.annotations.ApiParam;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-19T10:48:55.616846300+01:00[Europe/Berlin]")
@Controller
public class DemenziellErkrankterApiController implements DemenziellErkrankterApi {

    private static final Logger log = LoggerFactory.getLogger(DemenziellErkrankterApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private DemenziellErkrankterRepository repository;

    private PositionssenderRepository positionssenderRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public DemenziellErkrankterApiController(
            ObjectMapper objectMapper,
            HttpServletRequest request,
            DemenziellErkrankterRepository repository,
            PositionssenderRepository positionssenderRepository
    ) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.repository = repository;
        this.positionssenderRepository = positionssenderRepository;
    }

    public ResponseEntity<DemenziellErkrankterDTO> addDemenziellErkankten(@ApiParam(value = "Objekt, welches angelegt werden soll.", required = true) @Valid @RequestBody DemenziellErkrankterDTO body) {
        val demenziellErkrankter = DemenziellErkrankter.convert(body);
        DemenziellErkrankter saved = repository.save(demenziellErkrankter);

        DemenziellErkrankterDTO demenziellErkrankterDTO = getDemenziellErkrankterDTOWithPositionssender(body, demenziellErkrankter, saved);

        return new ResponseEntity<>(demenziellErkrankterDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<DemenziellErkrankterDTO> getDemenziellErkankten(@ApiParam(value = "ID des demenziell Erkranten, welcher ausgelesen werden soll.", required = true) @PathVariable("id") String id) {
        Optional<DemenziellErkrankter> result = repository.findById(id);
        if (result.isPresent()) {
            val demenziellErkrankterEntity = result.get();
            val demenziellErkrankterDTO = DemenziellErkrankter.convert(demenziellErkrankterEntity);
            val positionssenderList = positionssenderRepository.findByDemenziellErkrankter(demenziellErkrankterEntity);
            val positionssenderDTOList = new ArrayList<PositionssenderDTO>();
            positionssenderList.forEach(it -> positionssenderDTOList.add(Positionssender.convert(it)));

            demenziellErkrankterDTO.setPositionssender(positionssenderDTOList);

            return new ResponseEntity<>(demenziellErkrankterDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<DemenziellErkrankterDTO> updateDemenziellerkankten(@ApiParam(value = "Objekt eines demenziell Erkranten, welches aktualisiert werden soll.", required = true) @Valid @RequestBody DemenziellErkrankterDTO body, @ApiParam(value = "ID des demenziell Erkranten, welcher aktualisiert werden soll.", required = true) @PathVariable("id") String id) {
        DemenziellErkrankter result = repository.findById(id).orElse(null);
        if (result != null) {
            val demenziellErkrankter = DemenziellErkrankter.convert(body);
            result.update(demenziellErkrankter);
            DemenziellErkrankter saved = repository.save(result);

            DemenziellErkrankterDTO demenziellErkrankterDTO = getDemenziellErkrankterDTOWithPositionssender(body, demenziellErkrankter, saved);

            return new ResponseEntity<>(demenziellErkrankterDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private DemenziellErkrankterDTO getDemenziellErkrankterDTOWithPositionssender(@RequestBody @ApiParam(value = "Objekt eines demenziell Erkranten, welches aktualisiert werden soll.", required = true) @Valid DemenziellErkrankterDTO body, DemenziellErkrankter demenziellErkrankter, DemenziellErkrankter saved) {
        val positionssenderDTOList = body.getPositionssender();
        val positionssenderList = new ArrayList<Positionssender>();
        positionssenderDTOList.forEach(it -> {
            val positionssender = Positionssender.convert(it);
            positionssender.setDemenziellErkrankter(demenziellErkrankter);
            positionssenderList.add(positionssender);
        });
        positionssenderRepository.saveAll(positionssenderList);

        val demenziellErkrankterDTO = DemenziellErkrankter.convert(saved);
        demenziellErkrankterDTO.setPositionssender(positionssenderDTOList);
        return demenziellErkrankterDTO;
    }

}
