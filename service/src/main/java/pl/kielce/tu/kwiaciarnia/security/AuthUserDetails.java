package pl.kielce.tu.kwiaciarnia.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kielce.tu.kwiaciarnia.model.user.User;
import pl.kielce.tu.kwiaciarnia.utility.AuthoritiesUtility;

import java.util.Collection;
import java.util.List;

public class AuthUserDetails implements UserDetails {
    private final String username;

    private final String password;

    private final List<GrantedAuthority> authorities;


    public AuthUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = AuthoritiesUtility.getUserAuthorities(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
