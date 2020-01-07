package de.th.koeln.archilab.fae.faeteam2service.zone;

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
public class ZoneApiController implements ZoneApi {

    private static final Logger log = LoggerFactory.getLogger(ZoneApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private ZoneRepository repository;

    @org.springframework.beans.factory.annotation.Autowired
    public ZoneApiController(ObjectMapper objectMapper, HttpServletRequest request, ZoneRepository repository) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.repository = repository;
    }

    public ResponseEntity<ZoneDTO> addZone(@ApiParam(value = "Objekt, welches angelegt werden soll.", required = true) @Valid @RequestBody ZoneDTO body) {
        Zone saved = repository.save(Zone.convert(body));
        return new ResponseEntity<>(Zone.convert(saved), HttpStatus.CREATED);
    }

    public ResponseEntity<ZoneDTO> getZone(@ApiParam(value = "ID der Zone die zurückgeliefert werden soll.", required = true) @PathVariable("id") String id) {
        Optional<Zone> result = repository.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(Zone.convert(result.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ZoneDTO> updateZone(@ApiParam(value = "Objekt einer Zone, welches aktualisiert werden soll.", required = true) @Valid @RequestBody ZoneDTO body, @ApiParam(value = "ID der Zone die aktualisiert werden soll.", required = true) @PathVariable("id") String id) {
        Zone result = repository.findById(id).orElse(null);
        if (result != null) {
            result.update(Zone.convert(body));
            Zone saved = repository.save(result);
            return new ResponseEntity<>(Zone.convert(saved), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
