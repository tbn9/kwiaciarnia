package pl.kielce.tu.kwiaciarnia.controller.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.kwiaciarnia.model.ribbon.Ribbon;
import pl.kielce.tu.kwiaciarnia.service.ribbon.RibbonServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RibbonController {
    private final RibbonServiceImpl ribbonService;

    @Autowired
    public RibbonController(RibbonServiceImpl ribbonService) {
        this.ribbonService = ribbonService;
    }

    @GetMapping("/api/public/ribbons/all")
    public ResponseEntity<?> getAllRibbons() {
        return new ResponseEntity<>(ribbonService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/api/private/ribbons/update/{id}")
    public ResponseEntity<?> updateRibbon(@PathVariable long id, @RequestBody Ribbon ribbon) {
        return new ResponseEntity<>(ribbonService.update(id, ribbon), HttpStatus.OK);
    }

    @PostMapping("/api/private/ribbons/add")
    public ResponseEntity<?> addRibbon(@RequestBody Ribbon ribbon) {
        return new ResponseEntity<>(ribbonService.add(ribbon), HttpStatus.OK);
    }

    @DeleteMapping("/api/private/ribbons/delete/{id}")
    public ResponseEntity<?> deleteRibbon(@PathVariable long id) {
        return new ResponseEntity<>(ribbonService.delete(id), HttpStatus.OK);
    }
}
