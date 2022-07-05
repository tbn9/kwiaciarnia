package pl.kielce.tu.kwiaciarnia.dto.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.kielce.tu.kwiaciarnia.model.user.User;
import pl.kielce.tu.kwiaciarnia.utility.AddressUtility;


@Builder
@Getter
@EqualsAndHashCode
public class UserDto {

    private final long id;
    private final String username;
    private final String name;
    private final String surname;
    private final long phone;
    private final String address;

    public static UserDto create(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .phone(user.getPhone())
                .surname(user.getSurname())
                .address(AddressUtility.convertToString(user.getAddress()))
                .build();
    }
}
