package pl.kielce.tu.kwiaciarnia.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import pl.kielce.tu.kwiaciarnia.dto.user.AuthUserDetailsDto;
import pl.kielce.tu.kwiaciarnia.dto.user.UserDto;
import pl.kielce.tu.kwiaciarnia.exception.FailedToFindException;
import pl.kielce.tu.kwiaciarnia.model.address.Address;
import pl.kielce.tu.kwiaciarnia.model.user.User;
import pl.kielce.tu.kwiaciarnia.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = (List<User>) userRepository.findAll();

        return users.stream()
                .map(UserDto::create)
                .collect(Collectors.toList());
    }

    @Override
    public Address getUserAddress(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .map(User::getAddress)
                .orElseThrow(FailedToFindException::new);
    }

    @Override
    public AuthUserDetailsDto getAuthUserDetails(Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return AuthUserDetailsDto.create(username, authorities);
    }

    @Override
    public UserDto findUser(Authentication authentication) {

        return userRepository.findByUsername(authentication.getName())
                .map(UserDto::create)
                .orElseThrow(FailedToFindException::new);
    }
}
