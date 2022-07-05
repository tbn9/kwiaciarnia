package pl.kielce.tu.kwiaciarnia.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kielce.tu.kwiaciarnia.model.address.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
