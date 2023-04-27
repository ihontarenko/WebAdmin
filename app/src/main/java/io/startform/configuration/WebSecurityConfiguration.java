package io.startform.configuration;

import io.startform.property.HttpSecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final HttpSecurityProperties properties;
    private final DataSource dataSource;

    public WebSecurityConfiguration(HttpSecurityProperties properties, DataSource dataSource) {
        this.properties = properties;
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void initialize() {

    }

    @Bean
    public UserDetailsService userDetailsManager() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();

        manager.setDataSource(dataSource);
        manager.setRolePrefix("ROLE_");

        return manager;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(createDelegatingPasswordEncoder());

        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurityProperties.RememberMeProperties rememberMe = properties.getRememberMe();

        http
                // global configuration
                .authorizeRequests()
                .antMatchers(properties.getPermitAll()).permitAll()
                .anyRequest().authenticated()
                // log-in configuration
                .and()
                .formLogin()
                .loginPage(properties.getFormLogin().getLogin()).permitAll()
                .usernameParameter(properties.getFormLogin().getUsername())
                .passwordParameter(properties.getFormLogin().getPassword())
                .loginProcessingUrl(properties.getFormLogin().getLogin())
                // remember-me configuration
                .and()
                .rememberMe()
                .rememberMeParameter(properties.getRememberMe().getParameterName())
                .rememberMeCookieName(properties.getRememberMe().getCookieName())
                .key(properties.getRememberMe().getSecretKey())
                .tokenValiditySeconds(properties.getRememberMe().getValiditySeconds())
                // log-out configuration
                .and()
                .logout().logoutUrl(properties.getFormLogin().getLogout())
                .deleteCookies(properties.getSessionCookie())
                // others
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().ignoringAntMatchers(properties.getH2Console())
                .and()
                .cors().disable();
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers(properties.getWebSecurity().getPermitAll());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = createDelegatingPasswordEncoder();

        auth.authenticationProvider(authenticationProvider());

        auth
                .inMemoryAuthentication()
                .withUser("USER")
                .password(encoder.encode("password"))
                .roles("USER");

        auth
                .inMemoryAuthentication()
                .withUser("ADMIN")
                .password(encoder.encode("admin"))
                .roles("USER", "ADMIN");
    }

}
