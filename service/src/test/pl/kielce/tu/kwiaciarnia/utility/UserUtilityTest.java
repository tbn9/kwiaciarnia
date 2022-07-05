package pl.kielce.tu.kwiaciarnia.utility;

import org.junit.jupiter.api.Test;
import pl.kielce.tu.kwiaciarnia.model.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserUtilityTest {

    @Test
    void shouldConvertToString() {
        //given
        User user = new User();
        user.setName("a");
        user.setSurname("b");
        //when
        String result = UserUtility.convertToString(user);
        //then
        assertEquals("a b", result);
    }

}