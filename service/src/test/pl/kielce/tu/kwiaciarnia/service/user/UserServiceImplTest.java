package pl.kielce.tu.kwiaciarnia.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kielce.tu.kwiaciarnia.dto.user.UserDto;
import pl.kielce.tu.kwiaciarnia.model.address.Address;
import pl.kielce.tu.kwiaciarnia.model.role.Role;
import pl.kielce.tu.kwiaciarnia.model.user.User;
import pl.kielce.tu.kwiaciarnia.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = spy(new UserServiceImpl(repository));
    }

    @Test
    void shouldGetAllUsers() {
        //given
        List<User> givenUsers = prepareUsers();
        List<UserDto> helper = new ArrayList<>();

        givenUsers.forEach(e -> helper.add(UserDto.create(e)));

        when(repository.findAll()).thenReturn(givenUsers);
        //when
        List <UserDto> allUsers = service.findAll();
        //then
        assertEquals(helper,allUsers);
        verify(repository).findAll();
    }

    private List<User> prepareUsers() {
        User user0 = new User();
        User user1 = new User();
        User user2 = new User();

        List<User> users = new ArrayList<>();

        Address address = new Address();

        Role role = new Role();

        role.setId(0L);
        role.setName("ROLE_USER");

        address.setId(0L);
        address.setCity("a");
        address.setHouseNumber(11);
        address.setStreet("b");
        address.setZipCode("c-d");

        user0.setId(0L);
        user0.setAddress(address);
        user0.setPassword("$2a$10$pdGz7a7V/tVpWNDmq7WjGOqVAkGXljFeYFAORKaPGTm1uvF4PkZmK");
        user0.setPhone(123123123);
        user0.setRole(role);
        user0.setName("aaa");
        user0.setSurname("bbb");
        user0.setUsername("dada");

        user1 = user0;
        user1.setId(1L);
        user2 = user0;
        user2.setId(2L);

        users.add(user0);
        users.add(user1);
        users.add(user2);

        return users;
    }
}