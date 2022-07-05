package pl.kielce.tu.kwiaciarnia.controller.flower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.kwiaciarnia.model.flower.Flower;
import pl.kielce.tu.kwiaciarnia.service.flower.FlowerServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FlowerController {
    private final FlowerServiceImpl flowerService;

    @Autowired
    public FlowerController(FlowerServiceImpl flowerService) {
        this.flowerService = flowerService;
    }

    @GetMapping("/api/public/flowers/all")
    public ResponseEntity<?> getAllFlowers() {
        return new ResponseEntity<>(flowerService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/api/private/flowers/update/{id}")
    public ResponseEntity<?> updateFlower(@PathVariable long id, @RequestBody Flower flower) {
        return new ResponseEntity<>(flowerService.update(id, flower), HttpStatus.OK);
    }

    @PostMapping("/api/private/flowers/add")
    public ResponseEntity<?> addFlower(@RequestBody Flower flower) {
        return new ResponseEntity<>(flowerService.add(flower), HttpStatus.OK);
    }

    @DeleteMapping("/api/private/flowers/delete/{id}")
    public ResponseEntity<?> deleteFlower(@PathVariable long id) {
        return new ResponseEntity<>(flowerService.delete(id), HttpStatus.OK);
    }
}
