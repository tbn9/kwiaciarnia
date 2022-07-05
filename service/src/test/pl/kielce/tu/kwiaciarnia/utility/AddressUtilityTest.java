package pl.kielce.tu.kwiaciarnia.utility;

import org.junit.jupiter.api.Test;
import pl.kielce.tu.kwiaciarnia.model.address.Address;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressUtilityTest {

    @Test
    void shouldConvertToString() {
        //given
        Address address = new Address();
        address.setCity("a");
        address.setStreet("b");
        address.setHouseNumber(1);
        address.setZipCode("c");
        //when
        String result = AddressUtility.convertToString(address);
        //then
        assertEquals("a, ul. b 1, c", result);
    }

}