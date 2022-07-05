package pl.kielce.tu.kwiaciarnia.service.ribbon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;
import pl.kielce.tu.kwiaciarnia.exception.FailedToDeleteException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.ribbon.Ribbon;
import pl.kielce.tu.kwiaciarnia.repository.RibbonRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RibbonServiceImplTest {
    private RibbonServiceImpl ribbonService;

    @Mock
    private RibbonRepository ribbonRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ribbonService = spy(new RibbonServiceImpl(ribbonRepository));
    }

    @Test
    void shouldGetAllRibbon() {
        //given
        List<Ribbon> givenRibbons = prepareRibbons();
        when(ribbonRepository.findAll()).thenReturn(givenRibbons);
        //when
        List<Ribbon> allRibbons = ribbonService.findAll();
        //then
        assertEquals(givenRibbons, allRibbons);
        verify(ribbonRepository).findAll();
    }

    @Test
    void shouldAddNewRibbon() {
        //given
        Ribbon ribbon = new Ribbon();
        ribbon.setId(1L);
        //when
        String result = ribbonService.add(ribbon);
        //then
        assertEquals("Item o podanym id: " + ribbon.getId() + " został pomyślnie dodany", result);
        verify(ribbonRepository).save(any(Ribbon.class));
    }

    @Test
    void shouldUpdateRibbon() {
        //given
        Ribbon existingRibbon = new Ribbon();
        existingRibbon.setId(1L);
        when(ribbonRepository.findById(anyLong())).thenReturn(Optional.of(existingRibbon));
        //when
        String result = ribbonService.update(1L, new Ribbon());
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie zedytowany", result);
        verify(ribbonRepository).findById(anyLong());
        verify(ribbonRepository).save(any(Ribbon.class));
    }

    @Test
    void updateShouldTrhowExceptionIfRibbonNotExists() {
        //given
        when(ribbonRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToEditException.class, () -> ribbonService.update(1L, new Ribbon()));
        verify(ribbonRepository).findById(anyLong());
    }

    @Test
    void shouldDeleteRibbon() {
        //given
        Ribbon existingRibbon = new Ribbon();
        existingRibbon.setId(1L);
        when(ribbonRepository.findById(anyLong())).thenReturn(Optional.of(existingRibbon));
        //when
        String result = ribbonService.delete(1L);
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie usunięty", result);
        verify(ribbonRepository).findById(anyLong());
        verify(ribbonRepository).deleteById(anyLong());
    }

    @Test
    void deleteShouldThrowExceptionIfRibbonNotExists() {
        //given
        when(ribbonRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToDeleteException.class, () -> ribbonService.delete(1L));
        verify(ribbonRepository).findById(anyLong());
    }

    @Test
    void shouldReduceAmountOfRibbons() {
        //given
        List<Bouquet> bouquets = new ArrayList<>();
        Ribbon ribbon = prepareRibbon();
        Bouquet bouquet = new Bouquet();

        bouquet.setRibbon(ribbon);
        bouquets.add(bouquet);

        when(ribbonRepository.findById(anyLong())).thenReturn(Optional.of(ribbon));
        when(ribbonRepository.save(any(Ribbon.class))).then(i -> i.getArgument(0, Ribbon.class));
        //when
        ribbonService.reduce(bouquets);
        //then
        assertEquals(0, ribbon.getAmount());
        verify(ribbonRepository).findById(anyLong());
    }

    private Ribbon prepareRibbon() {
        Ribbon ribbon = new Ribbon();
        ribbon.setId(1L);
        ribbon.setAmount(10);
        return ribbon;
    }

    private List<Ribbon> prepareRibbons() {
        Ribbon ribbon0 = new Ribbon();
        Ribbon ribbon1 = new Ribbon();
        Ribbon ribbon2 = new Ribbon();

        ribbon0.setId(0L);
        ribbon1.setId(1L);
        ribbon2.setId(2L);

        return Arrays.asList(ribbon0, ribbon1, ribbon2);
    }


}