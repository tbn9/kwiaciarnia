package pl.kielce.tu.kwiaciarnia.controller.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.kielce.tu.kwiaciarnia.dto.user.UserDto;
import pl.kielce.tu.kwiaciarnia.exception.CrudExceptionAdvice;
import pl.kielce.tu.kwiaciarnia.model.address.Address;
import pl.kielce.tu.kwiaciarnia.model.role.Role;
import pl.kielce.tu.kwiaciarnia.model.user.User;
import pl.kielce.tu.kwiaciarnia.service.user.UserServiceImpl;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {
    private MockMvc mockMvc;

    private UserServiceImpl service;

    private UserController controller;

    @BeforeEach
    void setUp() {
        service = mock(UserServiceImpl.class);
        controller = new UserController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CrudExceptionAdvice())
                .build();
    }

    @Test
    void testGetAllUsersEndpoint() throws Exception {
        //given
        UserDto user = prepareUser();
        when(service.findAll()).thenReturn(Collections.singletonList(user));
        //when
        //then
        mockMvc.perform(get("/api/private/users/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(service).findAll();
    }

    private UserDto prepareUser() {
        User user = new User();
        Address address = new Address();
        Role role = new Role();
        role.setName("ROLE_USER");

        user.setId(1L);
        user.setName("Maciej");
        user.setSurname("Maciejowski");
        user.setUsername("macmac");
        user.setPassword("$2a$10$pdGz7a7V/tVpWNDmq7WjGOqVAkGXljFeYFAORKaPGTm1uvF4PkZmK");
        user.setRole(role);
        user.setPhone(111111111);
        user.setAddress(address);

        return UserDto.create(user);
    }
}