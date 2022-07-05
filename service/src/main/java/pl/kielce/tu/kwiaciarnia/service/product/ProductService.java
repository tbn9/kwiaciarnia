package pl.kielce.tu.kwiaciarnia.service.product;

import pl.kielce.tu.kwiaciarnia.model.product.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByCategory(long id);

    List<Product> getRecommendedProducts();

    void reduce(List<Product> products);
}
