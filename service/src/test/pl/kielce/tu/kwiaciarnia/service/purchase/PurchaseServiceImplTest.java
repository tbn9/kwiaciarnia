package pl.kielce.tu.kwiaciarnia.service.purchase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kielce.tu.kwiaciarnia.dto.purchase.PurchaseDto;
import pl.kielce.tu.kwiaciarnia.model.address.Address;
import pl.kielce.tu.kwiaciarnia.model.purchase.Purchase;
import pl.kielce.tu.kwiaciarnia.model.user.User;
import pl.kielce.tu.kwiaciarnia.repository.AddressRepository;
import pl.kielce.tu.kwiaciarnia.repository.PurchaseRepository;
import pl.kielce.tu.kwiaciarnia.repository.UserRepository;
import pl.kielce.tu.kwiaciarnia.service.flower.FlowerServiceImpl;
import pl.kielce.tu.kwiaciarnia.service.giftcard.GiftcardServiceImpl;
import pl.kielce.tu.kwiaciarnia.service.present.PresentServiceImpl;
import pl.kielce.tu.kwiaciarnia.service.product.ProductServiceImpl;
import pl.kielce.tu.kwiaciarnia.service.ribbon.RibbonServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PurchaseServiceImplTest {

    private PurchaseServiceImpl purchaseService;
    private FlowerServiceImpl flowerService;
    private RibbonServiceImpl ribbonService;
    private GiftcardServiceImpl giftcardService;
    private PresentServiceImpl presentService;
    private AddressRepository addressRepository;

    @Mock
    private ProductServiceImpl productService;
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        purchaseService = spy(new PurchaseServiceImpl(purchaseRepository, flowerService,
                ribbonService, giftcardService, addressRepository, productService, presentService, userRepository));
    }

    @Test
    void shouldGetAllPurchases() {
        //given
        List<Purchase> givenPurchases = preparePurchases();
        when(purchaseRepository.findAll()).thenReturn(givenPurchases);
        //when
        List<PurchaseDto> allPurchases = purchaseService.findAll();
        //then
        List<PurchaseDto> expected = expectedPurchasesDto();
        assertEquals( expected, allPurchases);
        verify(purchaseRepository).findAll();
    }

    private List<Purchase> preparePurchases() {
        Purchase purchase1 = new Purchase();
        Purchase purchase2 = new Purchase();

        Address address = new Address();
        address.setCity("a");
        address.setHouseNumber(2);
        address.setStreet("b");
        address.setZipCode("c");
        address.setId(1);

        User user = new User();
        user.setId(1);
        user.setName("a");
        user.setAddress(address);
        user.setPassword("aa");
        user.setSurname("a");

        purchase1.setId(1L);
        purchase1.setAddress(address);
        purchase1.setUser(user);
        purchase2.setId(2L);
        purchase2.setAddress(address);
        purchase2.setUser(user);

        return Arrays.asList(purchase1, purchase2);
    }

    private List<PurchaseDto> expectedPurchasesDto() {
        return preparePurchases().stream()
                .map(PurchaseDto::create)
                .collect(Collectors.toList());
    }
}