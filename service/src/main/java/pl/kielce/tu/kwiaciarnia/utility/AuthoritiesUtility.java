package pl.kielce.tu.kwiaciarnia.utility;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.kielce.tu.kwiaciarnia.model.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthoritiesUtility {
    public static List<GrantedAuthority> getUserAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role = user.getRole().getName();
        authorities.add(new SimpleGrantedAuthority(role));

        return authorities;
    }

    public static List<String> convertAuthoritiesToStrings(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public static List<GrantedAuthority> convertStringsToAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
