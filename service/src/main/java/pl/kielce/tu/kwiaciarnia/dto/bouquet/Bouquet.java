package pl.kielce.tu.kwiaciarnia.dto.bouquet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kielce.tu.kwiaciarnia.model.flower.Flower;
import pl.kielce.tu.kwiaciarnia.model.giftcard.Giftcard;
import pl.kielce.tu.kwiaciarnia.model.present.Present;
import pl.kielce.tu.kwiaciarnia.model.ribbon.Ribbon;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bouquet {

    private List<Flower> flowers;
    private List<Present> presents;

    private Ribbon ribbon;
    private Giftcard giftcard;

    private int price;
}
