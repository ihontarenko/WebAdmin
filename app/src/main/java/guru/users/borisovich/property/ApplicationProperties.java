package guru.users.borisovich.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public sealed class ApplicationProperties {

}

non-sealed class A extends ApplicationProperties {}
non-sealed class B extends ApplicationProperties {}

