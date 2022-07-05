package pl.kielce.tu.kwiaciarnia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.kwiaciarnia.model.present.Present;

@Repository
public interface PresentRepository extends CrudRepository<Present, Long> {
}
