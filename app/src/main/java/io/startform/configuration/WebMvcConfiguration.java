package io.startform.configuration;

import com.mitchellbosecke.pebble.extension.Extension;
import io.startform.common.pebble.PebbleExtendedExtension;
import io.startform.common.spring.web.servlet.resource.ContentHashVersionStrategy;
import io.startform.property.ApplicationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static io.startform.utils.Strings.substringBetween;
import static java.lang.String.format;

@Configuration
@EnableConfigurationProperties
@Import({JpaConfiguration.class})
@ConfigurationPropertiesScan(basePackageClasses = {ApplicationProperties.class})
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final ApplicationProperties properties;

    public WebMvcConfiguration(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        source.setResourceLoader(resolver);
        source.addBasenames(
                "classpath:locale/main"
        );
        source.setFallbackToSystemLocale(true);
        source.setDefaultEncoding("UTF-8");

        try {
            Resource[] resources = resolver.getResources(
                    "classpath*:locale/shared/**/*.properties");
            for (Resource resource : resources) {
                source.addBasenames(
                        format("classpath:%s",
                                substringBetween("locale", "_",
                                        resource.getURI().toString()))
                );
            }
        } catch (IOException ignore) {
        }

        return source;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();

        resolver.setCookieName(properties.getLocale().getCookieName());
        resolver.setCookieMaxAge(properties.getLocale().getCookieMaxAge());

        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();

        interceptor.setParamName("locale");

        return interceptor;
    }

    @Bean
    public Extension pebbleExtendedExtension(HttpServletRequest request, ResourceUrlProvider provider) {
        return new PebbleExtendedExtension(request, provider);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/assets/")
                .setCachePeriod(2629743)
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver())
                .addResolver(new VersionResourceResolver()
                        .addVersionStrategy(new ContentHashVersionStrategy(), "/**"));
    }

}
