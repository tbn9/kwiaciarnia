package pl.kielce.tu.kwiaciarnia.dto.purchase;

import lombok.Getter;
import lombok.Setter;
import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;
import pl.kielce.tu.kwiaciarnia.model.address.Address;
import pl.kielce.tu.kwiaciarnia.model.product.Product;

import java.util.List;

@Getter
@Setter
public class NewPurchase {
    private List<Product> products;
    private List<Bouquet> bouquets;
    private int price;
    private Address address;
}
