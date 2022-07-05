package pl.kielce.tu.kwiaciarnia.service.flower;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;
import pl.kielce.tu.kwiaciarnia.exception.FailedToDeleteException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.flower.Flower;
import pl.kielce.tu.kwiaciarnia.repository.FlowerRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlowerServiceImplTest {
    private FlowerServiceImpl flowerService;

    @Mock
    private FlowerRepository flowerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        flowerService = spy(new FlowerServiceImpl(flowerRepository));
    }

    @Test
    void shouldGetAllFlowers() {
        //given
        List<Flower> givenFlowers = prepareFlowers();
        when(flowerRepository.findAll()).thenReturn(givenFlowers);
        //when
        List<Flower> allFlowers = flowerService.findAll();
        //then
        assertEquals(givenFlowers, allFlowers);
        verify(flowerRepository).findAll();
    }

    @Test
    void shouldAddNewFlower() {
        //given
        Flower flower = new Flower();
        flower.setId(1L);
        //when
        String result = flowerService.add(flower);
        //then
        assertEquals("Item o podanym id: " + flower.getId() + " został pomyślnie dodany", result);
        verify(flowerRepository).save(any(Flower.class));
    }

    @Test
    void shouldUpdateFlower() {
        //given
        Flower existingFlower = new Flower();
        existingFlower.setId(1L);
        when(flowerRepository.findById(anyLong())).thenReturn(Optional.of(existingFlower));
        //when
        String result = flowerService.update(1L, new Flower());
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie zedytowany", result);
        verify(flowerRepository).findById(anyLong());
        verify(flowerRepository).save(any(Flower.class));
    }

    @Test
    void updateShouldThrowExceptionIfFlowerNotExist() {
        //given
        when(flowerRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToEditException.class, () -> flowerService.update(1L, new Flower()));
        verify(flowerRepository).findById(anyLong());
    }

    @Test
    void shouldDeleteFlower() {
        //given
        Flower existingFlower = new Flower();
        existingFlower.setId(1L);
        when(flowerRepository.findById(anyLong())).thenReturn(Optional.of(existingFlower));
        //when
        String result = flowerService.delete(1L);
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie usunięty", result);
        verify(flowerRepository).findById(anyLong());
        verify(flowerRepository).deleteById(anyLong());
    }

    @Test
    void deleteShouldThrowExceptionIfFlowerNotExist() {
        //given
        when(flowerRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToDeleteException.class, () -> flowerService.delete(1L));
        verify(flowerRepository).findById(anyLong());
    }

    @Test
    void shouldReduceAmountOfFlowers() {
        //given
        List<Bouquet> bouquets = new ArrayList<>();
        List<Flower> flowers = new ArrayList<>();
        Flower flower = prepareFlower();
        Bouquet bouquet = new Bouquet();

        flowers.add(flower);
        bouquet.setFlowers(flowers);
        bouquets.add(bouquet);

        when(flowerRepository.findById(anyLong())).thenReturn(Optional.of(flower));
        when(flowerRepository.save(any(Flower.class))).then(i -> i.getArgument(0, Flower.class));
        //when
        flowerService.reduce(bouquets);
        //then
        assertEquals(0, flower.getAmount());
        verify(flowerRepository).findById(anyLong());
    }

    private Flower prepareFlower() {
        Flower flower = new Flower();
        flower.setId(1L);
        flower.setAmount(5);
        return flower;
    }

    private List<Flower> prepareFlowers() {
        Flower flower0 = new Flower();
        Flower flower1 = new Flower();
        Flower flower2 = new Flower();

        flower0.setId(0L);
        flower1.setId(1L);
        flower2.setId(2L);

        return Arrays.asList(flower0, flower1, flower2);
    }
}