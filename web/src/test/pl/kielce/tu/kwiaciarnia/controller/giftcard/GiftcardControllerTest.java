package pl.kielce.tu.kwiaciarnia.controller.giftcard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.kielce.tu.kwiaciarnia.exception.CrudExceptionAdvice;
import pl.kielce.tu.kwiaciarnia.exception.FailedToDeleteException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.giftcard.Giftcard;
import pl.kielce.tu.kwiaciarnia.service.giftcard.GiftcardServiceImpl;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GiftcardControllerTest {
    private MockMvc mockMvc;

    private GiftcardServiceImpl service;

    private GiftcardController controller;

    @BeforeEach
    void setUp() {
        service = mock(GiftcardServiceImpl.class);
        controller = new GiftcardController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CrudExceptionAdvice())
                .build();
    }

    @Test
    void testGetAllGiftcardsEndpoint() throws Exception {
        //given
        Giftcard giftcard = prepareGiftcard();
        when(service.findAll()).thenReturn(Collections.singletonList(giftcard));
        //when
        //then
        mockMvc.perform(get("/api/public/giftcards/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(service).findAll();
    }

    @Test
    void testUpdateGiftcardEndpoint() throws Exception {
        //given
        Giftcard giftcard = prepareGiftcard();
        when(service.update(anyLong(), any(Giftcard.class)))
                .thenReturn("Kwiat o podanym id: " + 1L + " został pomyślnie zedytowany");

        String inputJson = "{\"id\":1," +
                "\"name\":\"a\"," +
                "\"imageUrl\":\"b\"}" +
                "\"price\":\"1\"," +
                "\"amount\":\"1\",";
        //when
        //then
        mockMvc.perform(put("/api/private/giftcards/update/1").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(""));

        verify(service).update(anyLong(), any(Giftcard.class));
    }

    @Test
    void testUpdateGiftcardEndpointWhenGiftcardNotFound() throws Exception {
        //given
        Giftcard giftcard = prepareGiftcard();
        when(service.update(anyLong(), any(Giftcard.class))).thenThrow(new FailedToEditException());

        String inputJson = "{\"id\":1," +
                "\"name\":\"a\"," +
                "\"imageUrl\":\"b\"}" +
                "\"price\":\"1\"," +
                "\"amount\":\"1\",";
        //when
        //then
        mockMvc.perform(put("/api/private/giftcards/update/1").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        verify(service).update(anyLong(), any(Giftcard.class));
    }

    @Test
    void testAddNewGiftcardEndpoint() throws Exception {
        //given
        Giftcard giftcard = prepareGiftcard();
        when(service.add(any(Giftcard.class)))
                .thenReturn("Item o podanym id: " + anyLong() + " został pomyślnie dodany");

        String inputJson = "{\"id\":1," +
                "\"name\":\"a\"," +
                "\"imageUrl\":\"b\"}" +
                "\"price\":\"1\"," +
                "\"amount\":\"1\",";
        //when
        //then
        mockMvc.perform(post("/api/private/giftcards/add").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(""));

        verify(service).add(any(Giftcard.class));
    }

    @Test
    void testDeleteGiftcardEndpoint() throws Exception {
        //given
        when(service.delete(anyLong()))
                .thenReturn("Item o podanym id: " + anyLong() + " został pomyślnie usunięty");
        //when
        //then
        mockMvc.perform(delete("/api/private/giftcards/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(""));

        verify(service).delete(anyLong());
    }

    @Test
    void testDeleteGiftcardEndpointWhenGiftcardNotFound() throws Exception {
        //given
        when(service.delete(anyLong()))
                .thenThrow(new FailedToDeleteException());
        //when
        //then
        mockMvc.perform(delete("/api/private/giftcards/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        //.andExpect(content().string(""));

        verify(service).delete(anyLong());
    }

    private Giftcard prepareGiftcard() {
        Giftcard giftcard = new Giftcard();
        giftcard.setId(1L);
        giftcard.setName("a");
        giftcard.setAmount(1);
        giftcard.setPrice(1);
        giftcard.setImageUrl("b");

        return giftcard;
    }
}