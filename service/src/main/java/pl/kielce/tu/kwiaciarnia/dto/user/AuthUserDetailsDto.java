package pl.kielce.tu.kwiaciarnia.dto.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import pl.kielce.tu.kwiaciarnia.utility.AuthoritiesUtility;

import java.util.Collection;
import java.util.List;


@Builder
@Getter
public class AuthUserDetailsDto {

    private final String username;
    private final List<String> authorities;

    public static AuthUserDetailsDto create(String username, Collection<? extends GrantedAuthority> authorities) {
        return AuthUserDetailsDto.builder()
                .username(username)
                .authorities(AuthoritiesUtility.convertAuthoritiesToStrings(authorities))
                .build();
    }
}
