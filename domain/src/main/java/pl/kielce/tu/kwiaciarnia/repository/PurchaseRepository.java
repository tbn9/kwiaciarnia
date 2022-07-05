package pl.kielce.tu.kwiaciarnia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.kwiaciarnia.model.purchase.Purchase;

import java.util.List;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findAllByUserId(long id);
}
