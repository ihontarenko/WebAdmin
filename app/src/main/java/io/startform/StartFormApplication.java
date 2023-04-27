package io.startform;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "io.startform.jpa")
public class StartFormApplication {

    public static void main(String... arguments) {
        run(StartFormApplication.class, arguments);
    }

}
