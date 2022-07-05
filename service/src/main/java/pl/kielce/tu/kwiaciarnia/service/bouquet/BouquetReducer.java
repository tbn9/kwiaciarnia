package pl.kielce.tu.kwiaciarnia.service.bouquet;

import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;

import java.util.List;

public interface BouquetReducer {
    void reduce(List<Bouquet> bouquets);
}
