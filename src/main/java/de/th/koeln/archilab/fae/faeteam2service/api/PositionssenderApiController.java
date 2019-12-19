package de.th.koeln.archilab.fae.faeteam2service.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import de.th.koeln.archilab.fae.faeteam2service.model.Positionssender;
import de.th.koeln.archilab.fae.faeteam2service.model.Zone;
import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-19T10:48:55.616846300+01:00[Europe/Berlin]")
@Controller
public class PositionssenderApiController implements PositionssenderApi {

    private static final Logger log = LoggerFactory.getLogger(PositionssenderApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PositionssenderApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Positionssender> addPositionssender(@ApiParam(value = "Objekt, welches angelegt werden soll.", required = true) @Valid @RequestBody Positionssender body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Positionssender>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Positionssender>> findAllPositionssender(@ApiParam(value = "Ergebnis nach Zone filtern") @Valid @RequestParam(value = "zoneId", required = false) String zoneId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<Positionssender>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Positionssender> findPositionssender(@ApiParam(value = "ID des Positionssender der zurückgeliefert werden soll.", required = true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Positionssender>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Zone>> getZonenByPositionssenderId(@ApiParam(value = "ID des Positionssenders", required = true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<Zone>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Positionssender> updatePositionssender(@ApiParam(value = "Objekt eines Positionssenders, welches aktualisiert werden soll.", required = true) @Valid @RequestBody Positionssender body, @ApiParam(value = "ID des Positionssender der aktualisiert werden soll.", required = true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Positionssender>(HttpStatus.NOT_IMPLEMENTED);
    }

}
