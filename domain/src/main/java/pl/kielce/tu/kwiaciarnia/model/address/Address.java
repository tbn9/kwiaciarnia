package pl.kielce.tu.kwiaciarnia.model.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String street;
    @Column(name = "HOUSE_NUMBER")
    private long houseNumber;
    private String city;
    @Column(name = "ZIP_CODE")
    private String zipCode;
}
