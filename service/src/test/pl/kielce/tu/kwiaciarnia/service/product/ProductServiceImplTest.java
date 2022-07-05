package pl.kielce.tu.kwiaciarnia.service.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kielce.tu.kwiaciarnia.exception.FailedToDeleteException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.category.Category;
import pl.kielce.tu.kwiaciarnia.model.present.Present;
import pl.kielce.tu.kwiaciarnia.model.product.Product;
import pl.kielce.tu.kwiaciarnia.repository.ProductRepository;
import pl.kielce.tu.kwiaciarnia.service.category.CategoryServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = spy(new ProductServiceImpl(productRepository));
    }

    @Test
    void shouldGetAllProducts() {
        //given
        List<Product> givenProducts = prepareProducts();
        when(productRepository.findAll()).thenReturn(givenProducts);
        //when
        List<Product> allProducts = productService.findAll();
        //then
        assertEquals(givenProducts, allProducts);
        verify(productRepository).findAll();
    }

    @Test
    void shouldAddNewProducts() {
        //given
        Product product = new Product();
        product.setId(1L);
        //when
        String result = productService.add(product);
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie dodany", result);
        verify(productRepository).save(any(Product.class));
    }
    @Test
    void shouldUpdateProducts() {
        //given
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));
        //when
        String result = productService.update(1L, new Product());
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie zedytowany", result);
        verify(productRepository).findById(anyLong());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateShouldThrowExceptionIfProductNotExists() {
        //given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToEditException.class, () -> productService.update(1L, new Product()));
        verify(productRepository).findById(anyLong());
    }

    @Test
    void shouldDeleteProducts() {
        //given
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(existingProduct));
        //when
        String result = productService.delete(1L);
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie usunięty", result);
        verify(productRepository).findById(anyLong());
        verify(productRepository).deleteById(anyLong());
    }

    @Test
    void deleteShouldThrowExceptionIfProductNotExists() {
        //given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToDeleteException.class, () -> productService.delete(1L));
        verify(productRepository).findById(anyLong());
    }

    @Test
    void shouldGetProductsByCategory() {
        //given
        List<Product> givenProducts = prepareProducts();
        when(productRepository.findAllByCategoryId(anyLong())).thenReturn(givenProducts);
        //when
        List<Product> allProducts = productService.getProductsByCategory(1L);
        //then
        assertEquals(givenProducts, allProducts);
        verify(productRepository).findAllByCategoryId(anyLong());
    }

    @Test
    void shouldGetRecommendedProducts() {
        //given
        List<Product> givenProducts = prepareProducts();
        when(productRepository.findAll()).thenReturn(givenProducts);
        //when
        List<Product> allProducts = productService.getRecommendedProducts();
        //then
        assertEquals(givenProducts.size(), allProducts.size());
        verify(productRepository).findAll();
    }

    @Test
    void shouldReduceAmountOfProducts() {
        //given
        List<Product> products = new ArrayList<>();
        Product product = prepareProduct();

        products.add(product);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).then(i -> i.getArgument(0, Product.class));
        //when
        productService.reduce(products);
        //then
        assertEquals(0, product.getAmount());
        verify(productRepository).findById(anyLong());
    }

    private Product prepareProduct() {
        Product product = new Product();

        product.setId(1L);
        product.setAmount(10);

        return product;
    }

    private List<Product> prepareProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        product1.setId(1L);
        product2.setId(2L);
        product3.setId(3L);

        return Arrays.asList(product1, product2, product3);
    }
}