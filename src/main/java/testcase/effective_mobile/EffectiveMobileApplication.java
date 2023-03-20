package testcase.effective_mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class EffectiveMobileApplication {


    public static void main(String[] args) {
        SpringApplication.run(EffectiveMobileApplication.class, args);
    }

}
