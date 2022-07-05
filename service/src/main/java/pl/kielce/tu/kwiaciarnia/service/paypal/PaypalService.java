package pl.kielce.tu.kwiaciarnia.service.paypal;

import com.paypal.orders.Order;

public interface PaypalService {
    Order createPayment(long id);
}
