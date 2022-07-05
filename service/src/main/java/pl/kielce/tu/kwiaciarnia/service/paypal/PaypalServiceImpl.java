package pl.kielce.tu.kwiaciarnia.service.paypal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.kwiaciarnia.exception.FailedToCreatePayment;
import pl.kielce.tu.kwiaciarnia.exception.FailedToFindException;
import pl.kielce.tu.kwiaciarnia.model.purchase.Purchase;
import pl.kielce.tu.kwiaciarnia.repository.PurchaseRepository;
import pl.kielce.tu.kwiaciarnia.utility.UserUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalServiceImpl implements PaypalService {
    private static final String CLIENT_ID = "AWp5q3KpqkeuQJYGLBvPWR0rZWZD3ePnRGmMMwWRhqucwQxdV23ASAa3yTLbDNgAk8co-epQpKl0lOCS";
    private static final String CLIENT_SECRET = "EKK6jS19qULLnXRe5uXf65kcx_CF3aOPdJfD5Lw7Fozm_9YJokHSGhZwOaon_8pSqkIvvQgeiDuX7AE-";
    private static final String PAYMENT_INTENT = "CAPTURE";
    private static final String BRAND_NAME = "Kwiaciarnia Aeris";
    private static final String SHIPPING_PREFERENCE = "SET_PROVIDED_ADDRESS";
    private static final String CURRENCY = "PLN";
    private static final String COUNTRY_CODE = "PL";

    private final PurchaseRepository purchaseRepository;
    private final PayPalHttpClient client;

    @Autowired
    public PaypalServiceImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
        client = new PayPalHttpClient(new PayPalEnvironment.Sandbox(CLIENT_ID, CLIENT_SECRET));
    }

    @Override
    public Order createPayment(long id) {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.requestBody(createRequestBody(id));
        HttpResponse<Order> response;

        try {
            response = client.execute(request);
        } catch (IOException e) {
           throw new FailedToCreatePayment();
        }

        return response.result();
    }

    private OrderRequest createRequestBody(long id) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent(PAYMENT_INTENT);

        ApplicationContext applicationContext = new ApplicationContext().brandName(BRAND_NAME).shippingPreference(SHIPPING_PREFERENCE);
        orderRequest.applicationContext(applicationContext);

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(FailedToFindException::new);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode(CURRENCY)
                        .value(String.valueOf(purchase.getPrice())))
                .shippingDetail(new ShippingDetail().name(new Name().fullName(UserUtility.convertToString(purchase.getUser())))
                        .addressPortable(getUserAddress(purchase)));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);

        return orderRequest;
    }

    private AddressPortable getUserAddress(Purchase purchase) {
        return new AddressPortable().addressLine1(purchase.getAddress().getStreet() + " " + purchase.getAddress().getHouseNumber())
                .adminArea2(purchase.getAddress().getCity()).postalCode(purchase.getAddress().getZipCode()).countryCode(COUNTRY_CODE);
    }
}
