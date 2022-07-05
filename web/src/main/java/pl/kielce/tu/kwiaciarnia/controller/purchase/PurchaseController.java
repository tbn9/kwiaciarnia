package pl.kielce.tu.kwiaciarnia.controller.purchase;

import com.paypal.http.serializer.Json;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.kwiaciarnia.dto.purchase.NewPurchase;
import pl.kielce.tu.kwiaciarnia.service.paypal.PaypalService;
import pl.kielce.tu.kwiaciarnia.service.purchase.PurchaseService;

import static pl.kielce.tu.kwiaciarnia.service.purchase.OrderStatusConstants.FINISHED;
import static pl.kielce.tu.kwiaciarnia.service.purchase.OrderStatusConstants.NEW;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final PaypalService paypalService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, PaypalService paypalService) {
        this.purchaseService = purchaseService;
        this.paypalService = paypalService;
    }

    @PostMapping("/api/private/orders/add")
    public ResponseEntity<?> addPurchase(@RequestBody NewPurchase purchase, Authentication authentication) {
        return new ResponseEntity<>(purchaseService.add(purchase, authentication), HttpStatus.OK);
    }

    @GetMapping("/api/private/orders/all")
    public ResponseEntity<?> getAllPurchases() {
        return new ResponseEntity<>(purchaseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/private/orders/new/user")
    public ResponseEntity<?> getAllNewUserPurchases(Authentication authentication) {
        return new ResponseEntity<>(purchaseService.findAllUserPurchases(authentication, NEW), HttpStatus.OK);
    }

    @GetMapping("/api/private/orders/pending/user")
    public ResponseEntity<?> getAllPendingUserPurchases(Authentication authentication) {
        return new ResponseEntity<>(purchaseService.findAllUserPurchases(authentication, FINISHED), HttpStatus.OK);
    }

    @PutMapping("/api/private/orders/update/{id}")
    public ResponseEntity<?> updatePurchase(@PathVariable long id) {
        return new ResponseEntity<>(purchaseService.finish(id), HttpStatus.OK);
    }

    @GetMapping("/api/private/orders/payment/create/{id}")
    public ResponseEntity<?> createPayment(@PathVariable long id) throws Exception {
        return new ResponseEntity<>(new JSONObject(new Json().serialize(paypalService.createPayment(id))).toMap(), HttpStatus.OK);
    }

    @PostMapping("/api/private/orders/payment/approve/{id}")
    public ResponseEntity<?> approvePayment(@PathVariable long id) {
        return new ResponseEntity<>(purchaseService.approvePayment(id), HttpStatus.OK);
    }
}
