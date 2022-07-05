package pl.kielce.tu.kwiaciarnia.service.product;

import org.springframework.stereotype.Service;
import pl.kielce.tu.kwiaciarnia.model.product.Product;
import pl.kielce.tu.kwiaciarnia.repository.ProductRepository;
import pl.kielce.tu.kwiaciarnia.service.item.AbstractItemServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl extends AbstractItemServiceImpl<Product, ProductRepository> implements ProductService {

    private static final int RECOMMENDED_LIMIT = 10;

    public ProductServiceImpl(ProductRepository repository) {
        super(repository);
    }

    @Override
    public List<Product> getProductsByCategory(long id) {
        return super.getRepository().findAllByCategoryId(id);
    }

    @Override
    public List<Product> getRecommendedProducts() {
        List<Product> products = super.findAll();
        Collections.shuffle(products);

        return products.stream()
                .limit(RECOMMENDED_LIMIT)
                .collect(Collectors.toList());
    }

    @Override
    public void reduce(List<Product> products) {
        products.forEach(this::reduceAmount);
    }
}
