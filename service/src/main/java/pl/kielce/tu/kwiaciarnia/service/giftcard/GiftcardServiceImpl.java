package pl.kielce.tu.kwiaciarnia.service.giftcard;

import org.springframework.stereotype.Service;
import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;
import pl.kielce.tu.kwiaciarnia.model.giftcard.Giftcard;
import pl.kielce.tu.kwiaciarnia.repository.GiftcardRepository;
import pl.kielce.tu.kwiaciarnia.service.bouquet.BouquetReducer;
import pl.kielce.tu.kwiaciarnia.service.item.AbstractItemServiceImpl;

import java.util.List;
import java.util.Optional;

@Service
public class GiftcardServiceImpl extends AbstractItemServiceImpl<Giftcard, GiftcardRepository> implements BouquetReducer {

    public GiftcardServiceImpl(GiftcardRepository repository) {
        super(repository);
    }

    @Override
    public void reduce(List<Bouquet> bouquets) {
        bouquets.forEach(e -> Optional.ofNullable(e.getGiftcard())
                .ifPresent(this::reduceAmount));
    }
}
