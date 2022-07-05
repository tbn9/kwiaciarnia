package pl.kielce.tu.kwiaciarnia.controller.present;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.kielce.tu.kwiaciarnia.exception.CrudExceptionAdvice;
import pl.kielce.tu.kwiaciarnia.exception.FailedToDeleteException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.present.Present;
import pl.kielce.tu.kwiaciarnia.service.present.PresentServiceImpl;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PresentControllerTest {
    private MockMvc mockMvc;

    private PresentServiceImpl service;

    private PresentController controller;

    @BeforeEach
    void setUp() {
        service = mock(PresentServiceImpl.class);
        controller = new PresentController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CrudExceptionAdvice())
                .build();
    }

    @Test
    void testGetAllPresentsEndpoint() throws Exception {
        //given
        Present present = preparePresent();
        when(service.findAll()).thenReturn(Collections.singletonList(present));
        //when
        //then
        mockMvc.perform(get("/api/public/presents/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(service).findAll();
    }

    @Test
    void testUpdatePresentEndpoint() throws Exception {
        //given
        Present present = preparePresent();
        when(service.update(anyLong(), any(Present.class)))
                .thenReturn("Kwiat o podanym id: " + 1L + " został pomyślnie zedytowany");

        String inputJson = "{\"id\":1," +
                "\"name\":\"a\"," +
                "\"imageUrl\":\"b\"}" +
                "\"price\":\"1\"," +
                "\"amount\":\"1\",";
        //when
        //then
        mockMvc.perform(put("/api/private/presents/update/1").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(""));

        verify(service).update(anyLong(), any(Present.class));
    }

    @Test
    void testUpdatePresentEndpointWhenPresentNotFound() throws Exception {
        //given
        Present present = preparePresent();
        when(service.update(anyLong(), any(Present.class))).thenThrow(new FailedToEditException());

        String inputJson = "{\"id\":1," +
                "\"name\":\"a\"," +
                "\"imageUrl\":\"b\"}" +
                "\"price\":\"1\"," +
                "\"amount\":\"1\",";
        //when
        //then
        mockMvc.perform(put("/api/private/presents/update/1").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        verify(service).update(anyLong(), any(Present.class));
    }

    @Test
    void testAddNewPresentEndpoint() throws Exception {
        //given
        Present present = preparePresent();
        when(service.add(any(Present.class)))
                .thenReturn("Item o podanym id: " + anyLong() + " został pomyślnie dodany");

        String inputJson = "{\"id\":1," +
                "\"name\":\"a\"," +
                "\"imageUrl\":\"b\"}" +
                "\"price\":\"1\"," +
                "\"amount\":\"1\",";
        //when
        //then
        mockMvc.perform(post("/api/private/presents/add").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(""));

        verify(service).add(any(Present.class));
    }

    @Test
    void testDeletePresentEndpoint() throws Exception {
        //given
        when(service.delete(anyLong()))
                .thenReturn("Item o podanym id: " + anyLong() + " został pomyślnie usunięty");
        //when
        //then
        mockMvc.perform(delete("/api/private/presents/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string(""));

        verify(service).delete(anyLong());
    }

    @Test
    void testDeletePresentEndpointWhenPresentNotFound() throws Exception {
        //given
        when(service.delete(anyLong()))
                .thenThrow(new FailedToDeleteException());
        //when
        //then
        mockMvc.perform(delete("/api/private/presents/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        //.andExpect(content().string(""));

        verify(service).delete(anyLong());
    }

    private Present preparePresent() {
        Present present = new Present();
        present.setId(1L);
        present.setName("a");
        present.setAmount(1);
        present.setPrice(1);
        present.setImageUrl("b");

        return present;
    }
}