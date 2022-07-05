package pl.kielce.tu.kwiaciarnia.utility;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.kielce.tu.kwiaciarnia.model.role.Role;
import pl.kielce.tu.kwiaciarnia.model.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthoritiesUtilityTest {

    @Test
    void shouldGetUserAuthorities() {
        //given
        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setName("a");
        user.setRole(role);

        List<GrantedAuthority> givenAuthorities = new ArrayList<>();
        givenAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        //when
        List<GrantedAuthority> result = AuthoritiesUtility.getUserAuthorities(user);
        //then
        assertEquals(givenAuthorities, result);
    }

    @Test
    void shouldConvertAuthoritiesToString() {
        //given
        List<GrantedAuthority> givenAuthorities = new ArrayList<>();
        givenAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        //when
        List<String> result = AuthoritiesUtility.convertAuthoritiesToStrings(givenAuthorities);
        //then
        assertEquals(givenAuthorities.toString(), result.toString());
    }

    @Test
    void shouldConvertStringsToAuthorities() {
        //given
        List<GrantedAuthority> givenAuthorities = new ArrayList<>();
        givenAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        List<String> authorities = new ArrayList<>();
        authorities.add("ROLE_USER");
        //when
        List<GrantedAuthority> result = AuthoritiesUtility.convertStringsToAuthorities(authorities);
        //then
        assertEquals(givenAuthorities, result);
    }
}