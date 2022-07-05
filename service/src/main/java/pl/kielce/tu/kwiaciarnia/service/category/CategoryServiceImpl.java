package pl.kielce.tu.kwiaciarnia.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.category.Category;
import pl.kielce.tu.kwiaciarnia.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public String update(long id, Category category) {

        return categoryRepository.findById(id)
                .map(e -> {
                    category.setId(id);
                    categoryRepository.save(category);
                    return "Kategoria o podanym id: " + id + " została pomyślnie zedytowana";
                }).orElseThrow(FailedToEditException::new);
    }

    @Override
    public String add(Category category) {
        categoryRepository.save(category);

        return "Kategoria o podanym id: " + category.getId() + " została pomyślnie dodana";
    }
}
