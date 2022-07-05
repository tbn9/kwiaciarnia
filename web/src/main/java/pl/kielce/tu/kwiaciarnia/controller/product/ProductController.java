package pl.kielce.tu.kwiaciarnia.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.kwiaciarnia.model.product.Product;
import pl.kielce.tu.kwiaciarnia.service.product.ProductServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/api/public/products/categories/{id}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable long id) {
        return new ResponseEntity<>(productService.getProductsByCategory(id), HttpStatus.OK);
    }

    @GetMapping("/api/private/products/all")
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/public/products/recommended")
    public ResponseEntity<?> getRecommendedProducts() {
        return new ResponseEntity<>(productService.getRecommendedProducts(), HttpStatus.OK);
    }

    @PutMapping("/api/private/products/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody Product product) {
        return new ResponseEntity<>(productService.update(id, product), HttpStatus.OK);
    }

    @PostMapping("/api/private/products/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.add(product), HttpStatus.OK);
    }

    @DeleteMapping("/api/private/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        return new ResponseEntity<>(productService.delete(id), HttpStatus.OK);
    }
}
