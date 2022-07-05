package pl.kielce.tu.kwiaciarnia.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.kwiaciarnia.service.user.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/private/users/address")
    public ResponseEntity<?> getUserAddress(Authentication authentication) {
        return new ResponseEntity<>(userService.getUserAddress(authentication), HttpStatus.OK);
    }

    @GetMapping("/api/private/users/details")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        return new ResponseEntity<>(userService.getAuthUserDetails(authentication), HttpStatus.OK);
    }

    @GetMapping("/api/private/users/all")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/private/users/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        return new ResponseEntity<>(userService.findUser(authentication), HttpStatus.OK);
    }
}
