package cz.cvut.fit.niam1.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class RestApplicationController {

    @Autowired
    RestApplicationRepository repository;

    @GetMapping("/tour")
    @ResponseStatus(HttpStatus.OK)
    public List<Tour> getTours() {
        return repository.getTours();
    }

    @GetMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getTour(@PathVariable String id) {
        Tour t = repository.getTourById(id);
        return ResponseEntity.status(t==null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(t);
    }

    @PostMapping(value = "/tour")
    public ResponseEntity createTour(@RequestBody Tour tour) {
        repository.addTour(tour);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/tour/"+tour.getId()).build();
    }

    @DeleteMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteTour(@PathVariable String id) {
        repository.deleteTour(id);
        return ResponseEntity.status(HttpStatus.OK).body("{\n" +
                "  \"delete-status-at\": \"/tour/delete-status\"\n" +
                "}");
    }

    @PutMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTour(@PathVariable String id, @RequestBody Tour tour) {
        repository.updateTour(id, tour);
    }

    @GetMapping(value = "/tour/delete-status")
    @ResponseStatus(HttpStatus.OK)
    public List<Tour> deleteTourStatus() {
        return repository.deleteTourStatus();
    }

}
