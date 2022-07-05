package pl.kielce.tu.kwiaciarnia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentExceptionAdvice {

    @ExceptionHandler(FailedToCreatePayment.class)
    public ResponseEntity<?> paymentFailed() {
        return new ResponseEntity<>("Nie udało się stworzyć płatności", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
