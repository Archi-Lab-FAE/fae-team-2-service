package de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-19T10:48:55.616846300+01:00[Europe/Berlin]")
@Controller
public class DemenziellErkrankterApiController implements DemenziellErkrankterApi {

    private static final Logger log = LoggerFactory.getLogger(DemenziellErkrankterApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private DemenziellErkrankterRepository repository;

    @org.springframework.beans.factory.annotation.Autowired
    public DemenziellErkrankterApiController(ObjectMapper objectMapper, HttpServletRequest request, DemenziellErkrankterRepository repository) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.repository = repository;
    }

    public ResponseEntity<DemenziellErkrankterDTO> addDemenziellErkankten(@ApiParam(value = "Objekt, welches angelegt werden soll.", required = true) @Valid @RequestBody DemenziellErkrankterDTO body) {
        DemenziellErkrankter saved = repository.save(DemenziellErkrankter.convert(body));
        return new ResponseEntity<>(DemenziellErkrankter.convert(saved), HttpStatus.CREATED);
    }

    public ResponseEntity<DemenziellErkrankterDTO> getDemenziellErkankten(@ApiParam(value = "ID des demenziell Erkranten, welcher ausgelesen werden soll.", required = true) @PathVariable("id") String id) {
        Optional<DemenziellErkrankter> result = repository.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(DemenziellErkrankter.convert(result.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<DemenziellErkrankterDTO> updateDemenziellerkankten(@ApiParam(value = "Objekt eines demenziell Erkranten, welches aktualisiert werden soll.", required = true) @Valid @RequestBody DemenziellErkrankterDTO body, @ApiParam(value = "ID des demenziell Erkranten, welcher aktualisiert werden soll.", required = true) @PathVariable("id") String id) {
        DemenziellErkrankter result = repository.findById(id).orElse(null);
        if (result != null) {
            result.update(DemenziellErkrankter.convert(body));
            DemenziellErkrankter saved = repository.save(result);
            return new ResponseEntity<>(DemenziellErkrankter.convert(saved), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
