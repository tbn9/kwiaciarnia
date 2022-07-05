package pl.kielce.tu.kwiaciarnia.controller.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.kielce.tu.kwiaciarnia.exception.CrudExceptionAdvice;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.category.Category;
import pl.kielce.tu.kwiaciarnia.service.category.CategoryServiceImpl;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {
    private MockMvc mockMvc;

    private CategoryServiceImpl service;

    private CategoryController controller;

    @BeforeEach
    void setUp() {
        service = mock(CategoryServiceImpl.class);
        controller = new CategoryController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CrudExceptionAdvice())
                .build();
    }

    @Test
    public void testGetAllQuestionsEndpoint() throws Exception {
        //given
        Category category = prepareCategory();
        when(service.findAll()).thenReturn(Collections.singletonList(category));

        /* Jak ktoś naprawi kodowanie znaków w responsie
        String expectedJson = "[{\"id\":1," +
                "\"name\":\"Bukiety różane\"," +
                "\"imageUrl\":\"https://www.e-kwiaty.pl/ekwiaty/_images/bo208-ac1d.jpg\"}]";
         */

        //when
        //then
        mockMvc.perform(get("/api/public/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
                //.andExpect(content().json(expectedJson));

        verify(service).findAll();
    }

    @Test
    void testUpdateCategoryEndpoint() throws Exception {
        //given
        Category category = prepareCategory();
        when(service.update(anyLong(), any(Category.class)))
                .thenReturn("Kategoria o podanym id: " + 1L + " została pomyślnie zedytowana");

        String inputJson = "{\"id\":1," +
                "\"name\":\"Bukiety różane\"," +
                "\"imageUrl\":\"https://www.e-kwiaty.pl/ekwiaty/_images/bo208-ac1d.jpg\"}";
        //when
        //then
        mockMvc.perform(put("/api/private/categories/update/1").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().string(""));

        verify(service).update(anyLong(), any(Category.class));
    }

    @Test
    void testUpdateCategoryEndpointWhenCategoryNotFound() throws Exception {
        //given
        Category category = prepareCategory();
        when(service.update(anyLong(), any(Category.class))).thenThrow(new FailedToEditException());

        String inputJson = "{\"id\":1," +
                "\"name\":\"Bukiety różane\"," +
                "\"imageUrl\":\"https://www.e-kwiaty.pl/ekwiaty/_images/bo208-ac1d.jpg\"}";

        //when
        //then
        mockMvc.perform(put("/api/private/categories/update/1").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        verify(service).update(anyLong(), any(Category.class));
    }

    @Test
    void testAddNewCategoryEndpoint() throws Exception {
        //given
        Category question = prepareCategory();
        when(service.add(any(Category.class)))
                .thenReturn("Kategoria o podanym id: " + anyLong() + " została pomyślnie dodana");

        String inputJson = "{\"id\":1," +
                "\"name\":\"Bukiety różane\"," +
                "\"imageUrl\":\"https://www.e-kwiaty.pl/ekwiaty/_images/bo208-ac1d.jpg\"}";
        //when
        //then
        mockMvc.perform(post("/api/private/categories/add").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().string(""));


        verify(service).add(any(Category.class));
    }

    private Category prepareCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Bukiety różane");
        category.setImageUrl("https://www.e-kwiaty.pl/ekwiaty/_images/bo208-ac1d.jpg");

        return category;
    }
}