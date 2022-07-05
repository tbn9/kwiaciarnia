package pl.kielce.tu.kwiaciarnia.controller.giftcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.kwiaciarnia.model.giftcard.Giftcard;
import pl.kielce.tu.kwiaciarnia.service.giftcard.GiftcardServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class GiftcardController {
    private final GiftcardServiceImpl giftcardService;

    @Autowired
    public GiftcardController(GiftcardServiceImpl giftcardService) {
        this.giftcardService = giftcardService;
    }

    @GetMapping("/api/public/giftcards/all")
    public ResponseEntity<?> getAllGiftcards() {
        return new ResponseEntity<>(giftcardService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/api/private/giftcards/update/{id}")
    public ResponseEntity<?> updateGiftcard(@PathVariable long id, @RequestBody Giftcard giftcard) {
        return new ResponseEntity<>(giftcardService.update(id, giftcard), HttpStatus.OK);
    }

    @PostMapping("/api/private/giftcards/add")
    public ResponseEntity<?> addGiftcard(@RequestBody Giftcard giftcard) {
        return new ResponseEntity<>(giftcardService.add(giftcard), HttpStatus.OK);
    }

    @DeleteMapping("/api/private/giftcards/delete/{id}")
    public ResponseEntity<?> deleteGiftcard(@PathVariable long id) {
        return new ResponseEntity<>(giftcardService.delete(id), HttpStatus.OK);
    }
}
