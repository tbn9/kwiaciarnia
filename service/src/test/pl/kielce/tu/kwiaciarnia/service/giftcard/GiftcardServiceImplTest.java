package pl.kielce.tu.kwiaciarnia.service.giftcard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;
import pl.kielce.tu.kwiaciarnia.exception.FailedToDeleteException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.model.giftcard.Giftcard;
import pl.kielce.tu.kwiaciarnia.repository.GiftcardRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GiftcardServiceImplTest {
    private GiftcardServiceImpl giftcardService;

    @Mock
    private GiftcardRepository giftcardRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        giftcardService = spy(new GiftcardServiceImpl(giftcardRepository));
    }

    @Test
    void shouldGetAllGiftcards() {
        //given
        List<Giftcard> givenGiftcards = prepareGiftcards();
        when(giftcardRepository.findAll()).thenReturn(givenGiftcards);
        //when
        List<Giftcard> allGiftcards = giftcardService.findAll();
        //then
        assertEquals(givenGiftcards, allGiftcards);
        verify(giftcardRepository).findAll();
    }

    @Test
    void shouldAddNewGiftcard() {
        //given
        Giftcard giftcard = new Giftcard();
        giftcard.setId(1L);
        //when
        String result = giftcardService.add(giftcard);
        //then
        assertEquals("Item o podanym id: " + giftcard.getId() + " został pomyślnie dodany", result);
        verify(giftcardRepository).save(any(Giftcard.class));
    }

    @Test
    void shouldUpdateGiftcard() {
        //given
        Giftcard existingGiftcard = new Giftcard();
        existingGiftcard.setId(1L);
        when(giftcardRepository.findById(anyLong())).thenReturn(Optional.of(existingGiftcard));
        //when
        String result = giftcardService.update(1L, new Giftcard());
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie zedytowany", result);
        verify(giftcardRepository).findById(anyLong());
        verify(giftcardRepository).save(any(Giftcard.class));
    }

    @Test
    void updateShouldThrowExceptionIfGiftcardNotExist() {
        //given
        when(giftcardRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToEditException.class, () -> giftcardService.update(1L, new Giftcard()));
        verify(giftcardRepository).findById(anyLong());
    }

    @Test
    void shouldDeleteGiftcard() {
        //given
        Giftcard existingGiftcard = new Giftcard();
        existingGiftcard.setId(1L);
        when(giftcardRepository.findById(anyLong())).thenReturn(Optional.of(existingGiftcard));
        //when
        String result = giftcardService.delete(1L);
        //then
        assertEquals("Item o podanym id: " + 1L + " został pomyślnie usunięty", result);
        verify(giftcardRepository).findById(anyLong());
        verify(giftcardRepository).deleteById(anyLong());
    }

    @Test
    void deleteShouldThrowExceptionIfGiftcardNotExist() {
        //given
        when(giftcardRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        //then
        Assertions.assertThrows(FailedToDeleteException.class, () -> giftcardService.delete(1L));
        verify(giftcardRepository).findById(anyLong());
    }

    @Test
    void shouldReduceAmountOfGiftcards() {
        //given
        List<Bouquet> bouquets = new ArrayList<>();
        Giftcard giftcard = prepareGiftcard();
        Bouquet bouquet = new Bouquet();

        bouquet.setGiftcard(giftcard);
        bouquets.add(bouquet);

        when(giftcardRepository.findById(anyLong())).thenReturn(Optional.of(giftcard));
        when(giftcardRepository.save(any(Giftcard.class))).then(i -> i.getArgument(0, Giftcard.class));
        //when
        giftcardService.reduce(bouquets);
        //then
        assertEquals(0, giftcard.getAmount());
        verify(giftcardRepository).findById(anyLong());
    }

    private Giftcard prepareGiftcard() {
        Giftcard giftcard = new Giftcard();
        giftcard.setId(1L);
        giftcard.setAmount(5);
        return giftcard;
    }

    private List<Giftcard> prepareGiftcards() {
        Giftcard giftcard0 = new Giftcard();
        Giftcard giftcard1 = new Giftcard();
        Giftcard giftcard2 = new Giftcard();

        giftcard0.setId(0L);
        giftcard1.setId(1L);
        giftcard2.setId(2L);

        return Arrays.asList(giftcard0, giftcard1, giftcard2);
    }
}