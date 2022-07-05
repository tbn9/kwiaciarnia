package pl.kielce.tu.kwiaciarnia.dto.purchase;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.kielce.tu.kwiaciarnia.model.purchase.Purchase;
import pl.kielce.tu.kwiaciarnia.utility.AddressUtility;
import pl.kielce.tu.kwiaciarnia.utility.UserUtility;

@Builder
@Getter
@EqualsAndHashCode
public class PurchaseDto {

    private final long id;
    private final int price;
    private final String date;
    private final String status;
    private final String address;
    private final String user;

    public static PurchaseDto create(Purchase purchase) {
        return PurchaseDto.builder()
                .id(purchase.getId())
                .price(purchase.getPrice())
                .date(purchase.getDate())
                .status(purchase.getStatus())
                .address(AddressUtility.convertToString(purchase.getAddress()))
                .user(UserUtility.convertToString(purchase.getUser()))
                .build();
    }
}
