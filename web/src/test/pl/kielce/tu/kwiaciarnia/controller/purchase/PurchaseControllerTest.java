package pl.kielce.tu.kwiaciarnia.controller.purchase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.kielce.tu.kwiaciarnia.dto.purchase.NewPurchase;
import pl.kielce.tu.kwiaciarnia.dto.purchase.PurchaseDto;
import pl.kielce.tu.kwiaciarnia.exception.CrudExceptionAdvice;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.address.Address;
import pl.kielce.tu.kwiaciarnia.model.product.Product;
import pl.kielce.tu.kwiaciarnia.model.purchase.Purchase;
import pl.kielce.tu.kwiaciarnia.model.role.Role;
import pl.kielce.tu.kwiaciarnia.model.user.User;
import pl.kielce.tu.kwiaciarnia.service.paypal.PaypalService;
import pl.kielce.tu.kwiaciarnia.service.purchase.PurchaseServiceImpl;
import pl.kielce.tu.kwiaciarnia.utility.AuthoritiesUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PurchaseControllerTest {
    private MockMvc mockMvc;
    private PurchaseServiceImpl service;
    private PurchaseController controller;
    private PaypalService paypalService;

    @BeforeEach
    void setUp() {
        service = mock(PurchaseServiceImpl.class);
        controller = new PurchaseController(service, paypalService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CrudExceptionAdvice())
                .build();
    }

    @Test
    void testGetAllPurchases() throws Exception {
        //given
        PurchaseDto purchase = preparePurchase();
        when(service.findAll()).thenReturn(Collections.singletonList(purchase));
        //when
        //then
        mockMvc.perform(get("/api/private/orders/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(service).findAll();
    }

    @Test
    void testUpdatePurchaseEndpoint() throws Exception {
        //given
        when(service.finish(anyLong())).thenReturn("Zamówienie o id: " + 1L + " zostało pomyślnie zeedytowane");
        //when
        //then
        mockMvc.perform(put("/api/private/orders/update/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service).finish(anyLong());
    }

    @Test
    void testUpdatePurchaseEndpointWhenPurchaseNotFound() throws Exception {
        //given
        when(service.finish(anyLong())).thenThrow(new FailedToEditException());
        //when
        //then
        mockMvc.perform(put("/api/private/orders/update/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        verify(service).finish(anyLong());
    }

    private PurchaseDto preparePurchase() {
        Purchase purchase = new Purchase();
        Address address = new Address();
        User user = new User();

        user.setId(0L);
        address.setId(0L);

        purchase.setId(0L);
        purchase.setAddress(address);
        purchase.setUser(user);

        return PurchaseDto.create(purchase);
    }
}