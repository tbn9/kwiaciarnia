package pl.kielce.tu.kwiaciarnia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CrudExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FailedToEditException.class)
    public ResponseEntity<?> failedToEdit() {
        return new ResponseEntity<>("Nie udało się edytować elementu o wybranym ID", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FailedToFindException.class)
    public ResponseEntity<?> failedToFind() {
        return new ResponseEntity<>("Nie znaleziono elementu", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FailedToSaveOrder.class)
    public ResponseEntity<?> failedToSaveOrder() {
        return new ResponseEntity<>("Nie udało się stworzyć zamówienia. Spróbuj ponownie później.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FailedToDeleteException.class)
    public ResponseEntity<?> failedToDelete() {
        return new ResponseEntity<>("Nie udało się usunąć elementu o wybranym ID.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
