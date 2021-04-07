package guru.users.borisovich;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "guru.users.borisovich.jpa")
public class AdminApplication {

    public static void main(String... arguments) {
        run(AdminApplication.class, arguments);
    }

}
