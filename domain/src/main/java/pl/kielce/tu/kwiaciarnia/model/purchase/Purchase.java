package pl.kielce.tu.kwiaciarnia.model.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kielce.tu.kwiaciarnia.model.address.Address;
import pl.kielce.tu.kwiaciarnia.model.user.User;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int price;
    private String date;
    private String status;

    @ManyToOne
    @JoinColumn(name = "ADDRESS")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "USER")
    private User user;
}
