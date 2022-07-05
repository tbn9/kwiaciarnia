package pl.kielce.tu.kwiaciarnia.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kielce.tu.kwiaciarnia.model.address.Address;
import pl.kielce.tu.kwiaciarnia.model.role.Role;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    private String name;
    private String surname;
    private long phone;

    @ManyToOne
    @JoinColumn(name = "ADDRESS")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "ROLE")
    private Role role;
}
