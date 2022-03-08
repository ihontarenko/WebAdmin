package net.borisovich;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "guru.users.borisovich.jpa")
public class AdminApplication {

    private final Logger log = getLogger(AdminApplication.class);

    public final void test() {
        new ArrayList<>().contains(null);
    }

    public static void main(String... arguments) {
        run(AdminApplication.class, arguments);
    }

//    @Bean
//    public CommandLineRunner runner(UserRepository repository) {
//        return runner -> log.info(repository.findAll().toString());
//    }

}
