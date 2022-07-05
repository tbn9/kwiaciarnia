package pl.kielce.tu.kwiaciarnia.utility;

import pl.kielce.tu.kwiaciarnia.model.user.User;

public class UserUtility {
    public static String convertToString(User user) {
        return user.getName() + " " + user.getSurname();
    }
}
