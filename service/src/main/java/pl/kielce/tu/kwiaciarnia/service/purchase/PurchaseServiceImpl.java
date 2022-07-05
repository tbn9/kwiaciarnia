package pl.kielce.tu.kwiaciarnia.service.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.kielce.tu.kwiaciarnia.dto.bouquet.Bouquet;
import pl.kielce.tu.kwiaciarnia.dto.purchase.NewPurchase;
import pl.kielce.tu.kwiaciarnia.dto.purchase.PurchaseDto;
import pl.kielce.tu.kwiaciarnia.exception.FailedToEditException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToFindException;
import pl.kielce.tu.kwiaciarnia.exception.FailedToSaveOrder;
import pl.kielce.tu.kwiaciarnia.model.address.Address;
import pl.kielce.tu.kwiaciarnia.model.product.Product;
import pl.kielce.tu.kwiaciarnia.model.purchase.Purchase;
import pl.kielce.tu.kwiaciarnia.model.user.User;
import pl.kielce.tu.kwiaciarnia.repository.AddressRepository;
import pl.kielce.tu.kwiaciarnia.repository.PurchaseRepository;
import pl.kielce.tu.kwiaciarnia.repository.UserRepository;
import pl.kielce.tu.kwiaciarnia.service.bouquet.BouquetReducer;
import pl.kielce.tu.kwiaciarnia.service.flower.FlowerServiceImpl;
import pl.kielce.tu.kwiaciarnia.service.giftcard.GiftcardServiceImpl;
import pl.kielce.tu.kwiaciarnia.service.present.PresentServiceImpl;
import pl.kielce.tu.kwiaciarnia.service.product.ProductServiceImpl;
import pl.kielce.tu.kwiaciarnia.service.ribbon.RibbonServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.kielce.tu.kwiaciarnia.service.purchase.OrderStatusConstants.*;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final AddressRepository addressRepository;

    private final ProductServiceImpl productService;

    private final List<BouquetReducer> bouquetReducers;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, FlowerServiceImpl flowerService,
                               RibbonServiceImpl ribbonService, GiftcardServiceImpl giftcardService,
                               AddressRepository addressRepository, ProductServiceImpl productService,
                               PresentServiceImpl presentService, UserRepository userRepository) {

        this.productService = productService;
        this.purchaseRepository = purchaseRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.bouquetReducers = new ArrayList<>();

        bouquetReducers.add(flowerService);
        bouquetReducers.add(ribbonService);
        bouquetReducers.add(giftcardService);
        bouquetReducers.add(presentService);
    }

    @Override
    public String add(NewPurchase purchase, Authentication authentication) {

        List<Bouquet> bouquets = purchase.getBouquets();
        List<Product> products = purchase.getProducts();

        try {
            bouquetReducers.forEach(e -> e.reduce(bouquets));
            productService.reduce(products);
        } catch (Exception e) {
            throw new FailedToSaveOrder();
        }

        purchaseRepository.save(createPurchase(purchase, authentication));

        return "Zamówienie zostało przyjęte!";
    }

    @Override
    public List<PurchaseDto> findAll() {
        List<Purchase> purchases = (List<Purchase>) purchaseRepository.findAll();

        return purchases.stream()
                .map(PurchaseDto::create)
                .collect(Collectors.toList());
    }

    @Override
    public String finish(long id) {
        return purchaseRepository.findById(id)
                .map(e -> {
                    e.setStatus(FINISHED);
                    purchaseRepository.save(e);
                    return "Zamówienie o id: " + id + " zostało zakończone";
                }).orElseThrow(FailedToEditException::new);
    }

    private Purchase createPurchase(NewPurchase newPurchase, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(FailedToSaveOrder::new);

        Address address = newPurchase.getAddress();

        if (address.getId() == 0) {
            addressRepository.save(newPurchase.getAddress());
        }

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        Purchase purchase = new Purchase();

        purchase.setAddress(address);
        purchase.setDate(date);
        purchase.setPrice(newPurchase.getPrice());
        purchase.setStatus(NEW);
        purchase.setUser(user);

        return purchase;
    }

    @Override
    public String approvePayment(long id) {
        return purchaseRepository.findById(id)
                .map(e -> {
                    e.setStatus(PAID);
                    purchaseRepository.save(e);
                    return "Płatność zatwierdzona dla zamówienia o id: " + id;
                }).orElseThrow(FailedToFindException::new);
    }

    @Override
    public List<PurchaseDto> findAllUserPurchases(Authentication authentication, String status) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(FailedToFindException::new);
        List<Purchase> purchases = purchaseRepository.findAllByUserId(user.getId());

        return purchases.stream()
                .map(PurchaseDto::create)
                .filter(e -> e.getStatus().equals(status))
                .collect(Collectors.toList());
    }
}
