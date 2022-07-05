package pl.kielce.tu.kwiaciarnia.service.purchase;

import org.springframework.security.core.Authentication;
import pl.kielce.tu.kwiaciarnia.dto.purchase.NewPurchase;
import pl.kielce.tu.kwiaciarnia.dto.purchase.PurchaseDto;

import java.util.List;

public interface PurchaseService {
    String add(NewPurchase purchase, Authentication authentication);

    List<PurchaseDto> findAll();

    String finish(long id);

    String approvePayment(long id);

    List<PurchaseDto> findAllUserPurchases(Authentication authentication, String status);
}