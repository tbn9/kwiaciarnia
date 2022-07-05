package pl.kielce.tu.kwiaciarnia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.kwiaciarnia.model.category.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
