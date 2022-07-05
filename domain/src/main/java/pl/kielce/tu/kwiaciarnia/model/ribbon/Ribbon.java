package pl.kielce.tu.kwiaciarnia.model.ribbon;

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
public class Ribbon extends Item {
    private String length;
}