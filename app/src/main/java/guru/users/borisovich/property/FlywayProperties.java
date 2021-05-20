package guru.users.borisovich.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.flyway")
public class FlywayProperties {

    private boolean enable;

    public boolean isEnable() {
        return enable;
    }



}
