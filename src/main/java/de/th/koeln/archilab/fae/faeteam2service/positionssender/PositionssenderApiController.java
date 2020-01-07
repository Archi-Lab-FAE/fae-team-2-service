package de.th.koeln.archilab.fae.faeteam2service.positionssender;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankter;
import de.th.koeln.archilab.fae.faeteam2service.demenziell_erkrankter.DemenziellErkrankterRepository;
import de.th.koeln.archilab.fae.faeteam2service.zone.Zone;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneDTO;
import de.th.koeln.archilab.fae.faeteam2service.zone.ZoneRepository;
import io.swagger.annotations.ApiParam;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-19T10:48:55.616846300+01:00[Europe/Berlin]")
@Controller
public class PositionssenderApiController implements PositionssenderApi {

    private static final Logger log = LoggerFactory.getLogger(PositionssenderApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private PositionssenderRepository repository;
    private ZoneRepository zoneRepository;
    private DemenziellErkrankterRepository demenziellErkrankterRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public PositionssenderApiController(ObjectMapper objectMapper, HttpServletRequest request,
                                        PositionssenderRepository repository, ZoneRepository zoneRepository, DemenziellErkrankterRepository demenziellErkrankterRepository) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.repository = repository;
        this.zoneRepository = zoneRepository;
        this.demenziellErkrankterRepository = demenziellErkrankterRepository;
    }

    public ResponseEntity<PositionssenderDTO> addPositionssender(@ApiParam(value = "Objekt, welches angelegt werden soll.", required = true) @Valid @RequestBody PositionssenderDTO body) {
        Positionssender saved = repository.save(Positionssender.convert(body));
        return new ResponseEntity<>(Positionssender.convert(saved), HttpStatus.CREATED);
    }

    public ResponseEntity<List<PositionssenderDTO>> findAllPositionssender(@ApiParam(value = "Ergebnis nach Zone filtern") @Valid @RequestParam(value = "zoneId", required = false) String zoneId) {
        List<PositionssenderDTO> results;

        if (StringUtils.isBlank(zoneId)) {
            results = StreamSupport.stream(repository.findAll().spliterator(), false)
                    .map(x -> Positionssender.convert(x))
                    .collect(Collectors.toList());

        } else {
            Zone zone = zoneRepository.findById(zoneId).orElse(null);
            if (zone == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            //TODO Implement logic
            results = StreamSupport.stream(repository.findAll().spliterator(), false)
                    //.filter(x -> x.getPosition() is in zone.getPositionen())
                    .map(x -> Positionssender.convert(x))
                    .collect(Collectors.toList());
        }

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    public ResponseEntity<PositionssenderDTO> findPositionssender(@ApiParam(value = "ID des Positionssender der zurückgeliefert werden soll.", required = true) @PathVariable("id") String id) {
        Optional<Positionssender> result = repository.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(Positionssender.convert(result.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<ZoneDTO>> getZonenByPositionssenderId(@ApiParam(value = "ID des Positionssenders", required = true) @PathVariable("id") String id) {
        Positionssender sender = repository.findById(id).orElse(null);
        if (sender != null) {
            DemenziellErkrankter demenziellErkrankter = demenziellErkrankterRepository.findByZonen_zoneId(id);
            List<ZoneDTO> results = StreamSupport.stream(demenziellErkrankter.getZonen().spliterator(), false)
                    .map(x -> Zone.convert(x))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(results, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PositionssenderDTO> updatePositionssender(@ApiParam(value = "Objekt eines Positionssenders, welches aktualisiert werden soll.", required = true) @Valid @RequestBody PositionssenderDTO body, @ApiParam(value = "ID des Positionssender der aktualisiert werden soll.", required = true) @PathVariable("id") String id) {
        Positionssender result = repository.findById(id).orElse(null);
        if (result != null) {
            result.update(Positionssender.convert(body));
            Positionssender saved = repository.save(result);
            return new ResponseEntity<>(Positionssender.convert(saved), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
