package cz.cvut.fit.niam1.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.text.*;

@RestController
public class RestApplicationController {

    @Autowired
    RestApplicationRepository repository;

    private static SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

    @GetMapping("/tour")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Tour>> getTours(@RequestHeader Map<String, String> headers) throws ParseException {
        long timer = headers.containsKey("If-Modified-Since") ? format.parse(headers.get("If-Modified-Since")).getTime() : 0;
        if (repository.timer > timer) {
            return ResponseEntity.status(HttpStatus.OK).lastModified(repository.timer).body(repository.getTours());
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @GetMapping(value = "/tour/weak")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Tour>> getToursWeakETag(@RequestHeader Map<String, String> headers) {
        List<Tour> tours = repository.getTours();
        String eTag = String.format("W/\"%d\"", Objects.hash(tours.stream().map(Tour::getHash).toArray()));

        if (headers.containsKey("If-None-Match") && eTag.equals(headers.get("If-None-Match"))) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).eTag(eTag).body(tours);
    }

    @GetMapping(value = "/tour/strong")
    @ResponseStatus(HttpStatus.OK)
    public List<Tour> getToursStrongETag() {
        return repository.getTours();
    }

    @GetMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getTour(@PathVariable String id) {
        Tour t = repository.getTourById(id);
        return ResponseEntity.status(t==null?HttpStatus.NOT_FOUND:HttpStatus.OK).body(t);
    }

    @PostMapping(value = "/tour")
    public ResponseEntity createTour(@RequestBody Tour tour) {
        repository.addTour(tour);
        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/tour/"+tour.getId()).build();
    }

    @PostMapping(value = "/tour/{id}/customer")
    public ResponseEntity addCustomer(@PathVariable String id, @RequestBody String customer) {
        repository.addCustomer(id, customer);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTour(@PathVariable String id) {
        repository.deleteTour(id);
    }

    @PutMapping("/tour/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTour(@PathVariable String id, @RequestBody Tour tour) {
        repository.updateTour(id, tour);
    }
}
