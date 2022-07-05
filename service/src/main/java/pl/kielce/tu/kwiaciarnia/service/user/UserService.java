package pl.kielce.tu.kwiaciarnia.service.user;

import org.springframework.security.core.Authentication;
import pl.kielce.tu.kwiaciarnia.dto.user.AuthUserDetailsDto;
import pl.kielce.tu.kwiaciarnia.dto.user.UserDto;
import pl.kielce.tu.kwiaciarnia.model.address.Address;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    Address getUserAddress(Authentication authentication);

    AuthUserDetailsDto getAuthUserDetails(Authentication authentication);

    UserDto findUser(Authentication authentication);
}
