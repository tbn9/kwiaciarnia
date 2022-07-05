package pl.kielce.tu.kwiaciarnia.controller.present;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.kwiaciarnia.model.present.Present;
import pl.kielce.tu.kwiaciarnia.service.present.PresentServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PresentController {
    private final PresentServiceImpl presentService;

    @Autowired
    public PresentController(PresentServiceImpl presentService) {
        this.presentService = presentService;
    }

    @GetMapping("/api/public/presents/all")
    public ResponseEntity<?> getAllPresents() {
        return new ResponseEntity<>(presentService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/api/private/presents/update/{id}")
    public ResponseEntity<?> updatePresent(@PathVariable long id, @RequestBody Present present) {
        return new ResponseEntity<>(presentService.update(id, present), HttpStatus.OK);
    }

    @PostMapping("/api/private/presents/add")
    public ResponseEntity<?> addPresent(@RequestBody Present present) {
        return new ResponseEntity<>(presentService.add(present), HttpStatus.OK);
    }

    @DeleteMapping("/api/private/presents/delete/{id}")
    public ResponseEntity<?> deletePresent(@PathVariable long id) {
        return new ResponseEntity<>(presentService.delete(id), HttpStatus.OK);
    }
}
