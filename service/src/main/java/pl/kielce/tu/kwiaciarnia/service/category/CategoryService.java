package pl.kielce.tu.kwiaciarnia.service.category;

import pl.kielce.tu.kwiaciarnia.model.category.Category;

import java.util.List;

public interface CategoryService {
    String update(long id, Category category);
    String add(Category category);
    List<Category> findAll();
}
