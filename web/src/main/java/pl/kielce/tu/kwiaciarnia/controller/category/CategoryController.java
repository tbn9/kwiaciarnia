package pl.kielce.tu.kwiaciarnia.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.kwiaciarnia.model.category.Category;
import pl.kielce.tu.kwiaciarnia.service.category.CategoryService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<?> getCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/api/private/categories/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable long id, @RequestBody Category category) {
        return new ResponseEntity<>(categoryService.update(id, category), HttpStatus.OK);
    }

    @PostMapping("/api/private/categories/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.add(category), HttpStatus.OK);
    }
}
