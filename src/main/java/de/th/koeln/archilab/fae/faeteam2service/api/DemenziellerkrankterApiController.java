package de.th.koeln.archilab.fae.faeteam2service.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import de.th.koeln.archilab.fae.faeteam2service.model.DemenziellErkrankter;
import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-19T10:48:55.616846300+01:00[Europe/Berlin]")
@Controller
public class DemenziellerkrankterApiController implements DemenziellerkrankterApi {

    private static final Logger log = LoggerFactory.getLogger(DemenziellerkrankterApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public DemenziellerkrankterApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<DemenziellErkrankter> addDemenziellErkankten(@ApiParam(value = "Objekt, welches angelegt werden soll.", required = true) @Valid @RequestBody DemenziellErkrankter body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<DemenziellErkrankter>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<DemenziellErkrankter> getDemenziellErkankten(@ApiParam(value = "ID des demenziell Erkranten, welcher ausgelesen werden soll.", required = true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<DemenziellErkrankter>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<DemenziellErkrankter> updateDemenziellerkankten(@ApiParam(value = "Objekt eines demenziell Erkranten, welches aktualisiert werden soll.", required = true) @Valid @RequestBody DemenziellErkrankter body, @ApiParam(value = "ID des demenziell Erkranten, welcher aktualisiert werden soll.", required = true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<DemenziellErkrankter>(HttpStatus.NOT_IMPLEMENTED);
    }

}
