package pl.kielce.tu.kwiaciarnia.service.present;

import org.springframework.stereotype.Service;
import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;
import pl.kielce.tu.kwiaciarnia.model.present.Present;
import pl.kielce.tu.kwiaciarnia.repository.PresentRepository;
import pl.kielce.tu.kwiaciarnia.service.bouquet.BouquetReducer;
import pl.kielce.tu.kwiaciarnia.service.item.AbstractItemServiceImpl;

import java.util.List;

@Service
public class PresentServiceImpl extends AbstractItemServiceImpl<Present, PresentRepository> implements BouquetReducer {

    public PresentServiceImpl(PresentRepository repository) {
        super(repository);
    }

    @Override
    public void reduce(List<Bouquet> bouquets) {
        bouquets.forEach(e -> e.getPresents().forEach(this::reduceAmount));
    }
}
