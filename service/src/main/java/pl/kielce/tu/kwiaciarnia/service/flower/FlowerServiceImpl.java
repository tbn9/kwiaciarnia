package pl.kielce.tu.kwiaciarnia.service.flower;

import org.springframework.stereotype.Service;
import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;
import pl.kielce.tu.kwiaciarnia.model.flower.Flower;
import pl.kielce.tu.kwiaciarnia.repository.FlowerRepository;
import pl.kielce.tu.kwiaciarnia.service.bouquet.BouquetReducer;
import pl.kielce.tu.kwiaciarnia.service.item.AbstractItemServiceImpl;

import java.util.List;

@Service
public class FlowerServiceImpl extends AbstractItemServiceImpl<Flower, FlowerRepository> implements BouquetReducer {

    public FlowerServiceImpl(FlowerRepository repository) {
        super(repository);
    }

    @Override
    public void reduce(List<Bouquet> bouquets) {
        bouquets.forEach(e -> e.getFlowers().forEach(this::reduceAmount));
    }
}
