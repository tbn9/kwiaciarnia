package pl.kielce.tu.kwiaciarnia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.kwiaciarnia.model.flower.Flower;

@Repository
public interface FlowerRepository extends CrudRepository<Flower, Long> {

}
