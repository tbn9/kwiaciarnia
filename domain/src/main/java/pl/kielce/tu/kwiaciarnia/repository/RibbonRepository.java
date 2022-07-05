package pl.kielce.tu.kwiaciarnia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.kwiaciarnia.model.ribbon.Ribbon;

@Repository
public interface RibbonRepository extends CrudRepository<Ribbon, Long> {
}
