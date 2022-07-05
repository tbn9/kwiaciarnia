package pl.kielce.tu.kwiaciarnia.controller.ribbon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.kielce.tu.kwiaciarnia.exception.CrudExceptionAdvice;
import pl.kielce.tu.kwiaciarnia.exception.FailedToDeleteException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.ribbon.Ribbon;
import pl.kielce.tu.kwiaciarnia.service.ribbon.RibbonServiceImpl;

import java.awt.*;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RibbonControllerTest {
    private MockMvc mockMvc;

    private RibbonServiceImpl service;

    private RibbonController controller;

    @BeforeEach
    void setUp() {
        service = mock(RibbonServiceImpl.class);
        controller = new RibbonController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CrudExceptionAdvice())
                .build();
    }

    @Test
    void testGetAllRibbonsEndpoints() throws Exception {
        //given
        Ribbon ribbon = prepareRibbon();
        when(service.findAll()).thenReturn(Collections.singletonList(ribbon));
        //when
        //then
        mockMvc.perform(get("/api/public/ribbons/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(service).findAll();
    }

    @Test
    void testUpdateRibbonEndpoint() throws Exception {
        //given
        when(service.update(anyLong(), any(Ribbon.class)))
                .thenReturn("Wstążka o podanym id: " + 1L + " została pomyślnie zedytowana");

        String inputJson = "{\"id\":1," +
                "\"name\":\"Bukiety różane\"," +
                "\"imageUrl\":\"http://d3ui957tjb5bqd.cloudfront.net/images/screenshots/products/160/1606/1606646/1608.m00.i121.n037.p.c25.309286820-_gtp_-pink-ribbon-isolated-on-white-background.-woman-breast-cancer-symbol-for-october-banner-vector-illustration-f.jpg\"," +
                "\"length\":\"krótka\"," +
                "\"price\":\"1\"," +
                "\"amount\":\"44\"}";
        //when
        //then
        mockMvc.perform(put("/api/private/ribbons/update/1").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service).update(anyLong(), any(Ribbon.class));
    }

    @Test
    void testUpdateRibbonEndpointWhenRibbonNotFound() throws Exception {
        //given
        when(service.update(anyLong(), any(Ribbon.class)))
                .thenThrow(new FailedToEditException());

        String inputJson = "{\"id\":1," +
                "\"name\":\"Bukiety różane\"," +
                "\"imageUrl\":\"http://d3ui957tjb5bqd.cloudfront.net/images/screenshots/products/160/1606/1606646/1608.m00.i121.n037.p.c25.309286820-_gtp_-pink-ribbon-isolated-on-white-background.-woman-breast-cancer-symbol-for-october-banner-vector-illustration-f.jpg\"," +
                "\"length\":\"krótka\"," +
                "\"price\":\"5\"," +
                "\"amount\":\"100\"}";
        //when
        //then
        mockMvc.perform(put("/api/private/ribbons/update/1").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        verify(service).update(anyLong(), any(Ribbon.class));
    }

    @Test
    void testAddNewRibbonEndpoint() throws Exception {
        //given
        when(service.add(any(Ribbon.class)))
                .thenReturn("Wstążka o podanym id: " + anyLong() + " została pomyślnie dodana");

        String inputJson = "{\"id\":1," +
                "\"name\":\"Bukiety różane\"," +
                "\"imageUrl\":\"http://d3ui957tjb5bqd.cloudfront.net/images/screenshots/products/160/1606/1606646/1608.m00.i121.n037.p.c25.309286820-_gtp_-pink-ribbon-isolated-on-white-background.-woman-breast-cancer-symbol-for-october-banner-vector-illustration-f.jpg\"," +
                "\"length\":\"krótka\"," +
                "\"price\":\"5\"," +
                "\"amount\":\"100\"}";
        //when
        //then
        mockMvc.perform(post("/api/private/ribbons/add").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service).add(any(Ribbon.class));
    }

    @Test
    void testDeleteRibbonEndpoint() throws Exception {
        //given
        when(service.delete(anyLong()))
                .thenReturn("Wstążka o podanym id: " + anyLong() + " została pomyślnie usunięta");
        //when
        //then
        mockMvc.perform(delete("/api/private/ribbons/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service).delete(anyLong());
    }

    @Test
    void testDeleteRibbonEndpointWhenRibbonNotFound() throws Exception{
        //given
        when(service.delete(anyLong())).thenThrow(new FailedToDeleteException());
        //when
        //then
        mockMvc.perform(delete("/api/private/ribbons/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        verify(service).delete(anyLong());
    }

    private Ribbon prepareRibbon() {
        Ribbon ribbon = new Ribbon();
        ribbon.setId(1L);
        ribbon.setName("Różowa wstążka");
        ribbon.setImageUrl("http://d3ui957tjb5bqd.cloudfront.net/images/screenshots/products/160/1606/1606646/1608.m00.i121.n037.p.c25.309286820-_gtp_-pink-ribbon-isolated-on-white-background.-woman-breast-cancer-symbol-for-october-banner-vector-illustration-f.jpg");
        ribbon.setLength("krótka");
        ribbon.setPrice(5);
        ribbon.setAmount(100);

        return ribbon;
    }
}