package pl.kielce.tu.kwiaciarnia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.kwiaciarnia.model.giftcard.Giftcard;

@Repository
public interface GiftcardRepository extends CrudRepository<Giftcard, Long> {

}
