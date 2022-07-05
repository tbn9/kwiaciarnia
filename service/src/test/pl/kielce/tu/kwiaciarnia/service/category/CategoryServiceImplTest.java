package pl.kielce.tu.kwiaciarnia.service.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.category.Category;
import pl.kielce.tu.kwiaciarnia.repository.CategoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = spy(new CategoryServiceImpl(categoryRepository));
    }

    @Test
    void shouldGetAllQuestions() {
        //given
        List<Category> givenCategories = prepareCategories();
        when(categoryRepository.findAll()).thenReturn(givenCategories);
        //when
        List<Category> allCategories = categoryService.findAll();
        //then
        assertEquals(givenCategories, allCategories);
        verify(categoryRepository).findAll();
    }

    @Test
    void shouldUpdateCategory() {
        //given
        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName("Stara kategoria");
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(existingCategory));
        //when
        String result = categoryService.update(1L, new Category());
        //then
        assertEquals("Kategoria o podanym id: " + 1L + " została pomyślnie zedytowana", result);
        verify(categoryRepository).findById(anyLong());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void updateShouldThrowExceptionIfCategoryNotExists() {
        //given
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToEditException.class, () -> categoryService.update(1L, new Category()));
        verify(categoryRepository).findById(anyLong());
    }

    @Test
    void shouldAddNewCategory() {
        //given
        Category category = new Category();
        category.setId(1L);
        //when
        String result = categoryService.add(category);
        //then
        assertEquals("Kategoria o podanym id: " + category.getId() + " została pomyślnie dodana", result);
        verify(categoryRepository).save(any(Category.class));
    }

    private List<Category> prepareCategories() {
        Category category0 = new Category();
        Category category1 = new Category();
        Category category2 = new Category();

        category0.setId(0L);
        category1.setId(1L);
        category2.setId(2L);

        return Arrays.asList(category0, category1, category2);
    }
}