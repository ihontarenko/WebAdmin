package io.startform.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;

import static java.lang.String.format;

@ConfigurationProperties(prefix = "http-security")
public class HttpSecurityProperties {

    private String[] permitAll;
    private String h2Console;

    private RememberMeProperties rememberMe;
    private FormLoginProperties formLogin;
    private WebSecurityProperties webSecurity;

    private String sessionCookie;

    public String[] getPermitAll() {
        return permitAll;
    }

    public void setPermitAll(String[] permitAll) {
        this.permitAll = permitAll;
    }

    public String getH2Console() {
        return h2Console;
    }

    public void setH2Console(String h2Console) {
        this.h2Console = h2Console;
    }

    public WebSecurityProperties getWebSecurity() {
        return webSecurity;
    }

    public void setWebSecurity(WebSecurityProperties webSecurity) {
        this.webSecurity = webSecurity;
    }

    public FormLoginProperties getFormLogin() {
        return formLogin;
    }

    public void setFormLogin(FormLoginProperties formLogin) {
        this.formLogin = formLogin;
    }

    public RememberMeProperties getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(RememberMeProperties rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    @Override
    public String toString() {
        return "HttpSecurityProperties{permitAll=%s, h2Console='%s', rememberMe=%s, formLogin=%s, webSecurity=%s, sessionCookie='%s'}"
                .formatted(Arrays.toString(permitAll), h2Console, rememberMe, formLogin, webSecurity, sessionCookie);
    }

    public static class FormLoginProperties {

        private String processingUrl;
        private String login;
        private String logout;
        private String username;
        private String password;

        public String getProcessingUrl() {
            return processingUrl;
        }

        public void setProcessingUrl(String processingUrl) {
            this.processingUrl = processingUrl;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getLogout() {
            return logout;
        }

        public void setLogout(String logout) {
            this.logout = logout;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "FormLoginProperties{processingUrl='%s', login='%s', logout='%s', username='%s', password='%s'}"
                    .formatted(processingUrl, login, logout, username, password);
        }
    }

    public static class RememberMeProperties {

        private String cookieName;
        private String parameterName;
        private String secretKey;
        private int    validitySeconds;

        public String getCookieName() {
            return cookieName;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }

        public String getParameterName() {
            return parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public int getValiditySeconds() {
            return validitySeconds;
        }

        public void setValiditySeconds(int validitySeconds) {
            this.validitySeconds = validitySeconds;
        }

        @Override
        public String toString() {
            return format("RememberMe{cookieName='%s', parameterName='%s', secretKey='%s', validitySeconds=%d}",
                    cookieName, parameterName, secretKey, validitySeconds);
        }
    }

    public static class WebSecurityProperties {

        private String[] permitAll;

        public String[] getPermitAll() {
            return permitAll;
        }

        public void setPermitAll(String[] permitAll) {
            this.permitAll = permitAll;
        }

        @Override
        public String toString() {
            return "WebSecurityProperties{permitAll=%s}".formatted(Arrays.toString(permitAll));
        }
    }

}
