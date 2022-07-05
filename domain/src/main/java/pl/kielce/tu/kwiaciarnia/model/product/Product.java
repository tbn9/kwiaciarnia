package pl.kielce.tu.kwiaciarnia.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kielce.tu.kwiaciarnia.model.category.Category;
import pl.kielce.tu.kwiaciarnia.model.item.Item;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Item {
    @ManyToOne
    @JoinColumn(name = "CATEGORY")
    private Category category;

    private String description;
}
