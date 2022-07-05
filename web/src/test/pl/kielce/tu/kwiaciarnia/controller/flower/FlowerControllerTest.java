package pl.kielce.tu.kwiaciarnia.controller.flower;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.kielce.tu.kwiaciarnia.exception.CrudExceptionAdvice;
import pl.kielce.tu.kwiaciarnia.exception.FailedToDeleteException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.flower.Flower;
import pl.kielce.tu.kwiaciarnia.service.flower.FlowerServiceImpl;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FlowerControllerTest {
    private MockMvc mockMvc;

    private FlowerServiceImpl service;

    private FlowerController controller;

    @BeforeEach
    void setUp() {
        service = mock(FlowerServiceImpl.class);
        controller = new FlowerController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CrudExceptionAdvice())
                .build();
    }

    @Test
    void testGetAllFlowersEndpoint() throws Exception {
        //given
        Flower flower = prepareFlower();
        when(service.findAll()).thenReturn(Collections.singletonList(flower));
        //when
        //then
        mockMvc.perform(get("/api/public/flowers/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(service).findAll();
    }

    @Test
    void testUpdateFlowerEndpoint() throws Exception {
        //given
        Flower flower = prepareFlower();
        when(service.update(anyLong(), any(Flower.class)))
                .thenReturn("Kwiat o podanym id: " + 1L + " został pomyślnie zedytowany");

        String inputJson = "{\"id\":1," +
                "\"name\":\"a\"," +
                "\"imageUrl\":\"b\"}" +
                "\"price\":\"1\"," +
                "\"amount\":\"1\",";
        //when
        //then
        mockMvc.perform(put("/api/private/flowers/update/1").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(""));

        verify(service).update(anyLong(), any(Flower.class));
    }

    @Test
    void testUpdateFlowerEndpointWhenFlowerNotFound() throws Exception {
        //given
        Flower flower = prepareFlower();
        when(service.update(anyLong(), any(Flower.class))).thenThrow(new FailedToEditException());

        String inputJson = "{\"id\":1," +
                "\"name\":\"a\"," +
                "\"imageUrl\":\"b\"}" +
                "\"price\":\"1\"," +
                "\"amount\":\"1\",";
        //when
        //then
        mockMvc.perform(put("/api/private/flowers/update/1").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        verify(service).update(anyLong(), any(Flower.class));
    }

    @Test
    void testAddNewFlowerEndpoint() throws Exception {
        //given
        Flower flower = prepareFlower();
        when(service.add(any(Flower.class)))
                .thenReturn("Item o podanym id: " + anyLong() + " został pomyślnie dodany");

        String inputJson = "{\"id\":1," +
                "\"name\":\"a\"," +
                "\"imageUrl\":\"b\"}" +
                "\"price\":\"1\"," +
                "\"amount\":\"1\",";
        //when
        //then
        mockMvc.perform(post("/api/private/flowers/add").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(""));

        verify(service).add(any(Flower.class));
    }

    @Test
    void testDeleteFlowerEndpoint() throws Exception {
        //given
        when(service.delete(anyLong()))
                .thenReturn("Item o podanym id: " + anyLong() + " został pomyślnie usunięty");
        //when
        //then
        mockMvc.perform(delete("/api/private/flowers/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(""));

        verify(service).delete(anyLong());
    }

    @Test
    void testDeleteFlowerEndpointWhenFlowerNotFound() throws Exception {
        //given
        when(service.delete(anyLong()))
                .thenThrow(new FailedToDeleteException());
        //when
        //then
        mockMvc.perform(delete("/api/private/flowers/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        //.andExpect(content().string(""));

        verify(service).delete(anyLong());
    }

    private Flower prepareFlower() {
        Flower flower = new Flower();
        flower.setId(1L);
        flower.setName("a");
        flower.setAmount(1);
        flower.setPrice(1);
        flower.setImageUrl("b");

        return flower;
    }
}