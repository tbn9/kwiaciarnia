package pl.kielce.tu.kwiaciarnia.model.giftcard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kielce.tu.kwiaciarnia.model.item.Item;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Giftcard extends Item {
    private String message;
}
