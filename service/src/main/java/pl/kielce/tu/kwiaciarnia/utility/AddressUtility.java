package pl.kielce.tu.kwiaciarnia.utility;

import pl.kielce.tu.kwiaciarnia.model.address.Address;

public class AddressUtility {
    public static String convertToString (Address address) {
                return address.getCity() + ", ul. "
                        + address.getStreet() + " "
                        + address.getHouseNumber() + ", "
                        + address.getZipCode();
    }
}
