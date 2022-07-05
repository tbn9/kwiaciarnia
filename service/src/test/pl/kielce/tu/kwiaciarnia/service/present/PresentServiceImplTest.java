package pl.kielce.tu.kwiaciarnia.service.present;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;
import pl.kielce.tu.kwiaciarnia.exception.FailedToDeleteException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.present.Present;
import pl.kielce.tu.kwiaciarnia.repository.PresentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PresentServiceImplTest {

    private PresentServiceImpl presentService;

    @Mock
    private PresentRepository presentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        presentService = spy(new PresentServiceImpl(presentRepository));
    }

    @Test
    void shouldGetAllPresents() {
        //given
        List<Present> givenPresents = preparePresents();
        when(presentRepository.findAll()).thenReturn(givenPresents);
        //when
        List<Present> allPresents = presentService.findAll();
        //then
        assertEquals(givenPresents, allPresents);
        verify(presentRepository).findAll();
    }

    @Test
    void shouldAddNewPresent() {
        //given
        Present present = new Present();
        present.setId(1L);
        //when
        String result = presentService.add(present);
        //then
        assertEquals("Item o podanym id: " + present.getId() + " został pomyślnie dodany", result);
        verify(presentRepository).save(any(Present.class));
    }

    @Test
    void shouldUpdatePresent() {
        //given
        Present existingPresent = new Present();
        existingPresent.setId(1L);
        when(presentRepository.findById(anyLong())).thenReturn(Optional.of(existingPresent));
        //when
        String result = presentService.update(1L, new Present());
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie zedytowany", result);
        verify(presentRepository).findById(anyLong());
        verify(presentRepository).save(any(Present.class));
    }

    @Test
    void updateShouldThrowExceptionIfPresentNotExists() {
        //given
        when(presentRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToEditException.class, () -> presentService.update(1L, new Present()));
        verify(presentRepository).findById(anyLong());
    }

    @Test
    void shouldDeletePresent() {
        //given
        Present existingPresent = new Present();
        existingPresent.setId(1L);
        when(presentRepository.findById(anyLong())).thenReturn(Optional.of(existingPresent));
        //when
        String result = presentService.delete(1L);
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie usunięty", result);
        verify(presentRepository).findById(anyLong());
        verify(presentRepository).deleteById(anyLong());
    }

    @Test
    void deleteShouldThrowExceptionIfPresentNotExists() {
        //given
        when(presentRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToDeleteException.class, () -> presentService.delete(1L));
        verify(presentRepository).findById(anyLong());
    }

    @Test
    void shouldReduceAmountOfPresents() {
        //given
        List<Bouquet> bouquets = new ArrayList<>();
        List<Present> presents = new ArrayList<>();
        Present present = preparePresent();
        Bouquet bouquet = new Bouquet();

        presents.add(present);
        bouquet.setPresents(presents);
        bouquets.add(bouquet);

        when(presentRepository.findById(anyLong())).thenReturn(Optional.of(present));
        when(presentRepository.save(any(Present.class))).then(i -> i.getArgument(0, Present.class));
        //when
        presentService.reduce(bouquets);
        //then
        assertEquals(0, present.getAmount());
        verify(presentRepository).findById(anyLong());
    }

    private Present preparePresent() {
        Present present = new Present();
        present.setId(1L);
        present.setAmount(10);
        return present;
    }

    private List<Present> preparePresents() {
        Present present0 = new Present();
        Present present1 = new Present();
        Present present2 = new Present();

        present0.setId(0L);
        present1.setId(1L);
        present2.setId(2L);

        return Arrays.asList(present0, present1, present2);
    }


}