package pl.kielce.tu.kwiaciarnia.service.ribbon;

import org.springframework.stereotype.Service;
import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;
import pl.kielce.tu.kwiaciarnia.model.ribbon.Ribbon;
import pl.kielce.tu.kwiaciarnia.repository.RibbonRepository;
import pl.kielce.tu.kwiaciarnia.service.bouquet.BouquetReducer;
import pl.kielce.tu.kwiaciarnia.service.item.AbstractItemServiceImpl;

import java.util.List;
import java.util.Optional;

@Service
public class RibbonServiceImpl extends AbstractItemServiceImpl<Ribbon, RibbonRepository> implements BouquetReducer {

    public RibbonServiceImpl(RibbonRepository repository) {
        super(repository);
    }

    @Override
    public void reduce(List<Bouquet> bouquets) {
        bouquets.forEach(e -> Optional.ofNullable(e.getRibbon())
                .ifPresent(this::reduceAmount));
    }
}
