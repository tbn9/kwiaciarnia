package pl.kielce.tu.kwiaciarnia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "pl.kielce.tu.*")
public class KwiaciarniaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KwiaciarniaApplication.class, args);
    }
}
