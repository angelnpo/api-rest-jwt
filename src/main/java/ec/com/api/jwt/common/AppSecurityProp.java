package ec.com.api.jwt.common;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.security")
public class AppSecurityProp {

    private Jwt jwt;
    private Rsa rsa;
    private User user;

    @Getter
    @Setter
    public static class Jwt {
        String tokenPrefix;
        String secret;
        Long expiration;
    }

    @Getter
    @Setter
    public static class Rsa {
        KeyPair keyPair;
    }
    @Getter
    @Setter
    public static class KeyPair {
        RSAPublicKey publicKey;
        RSAPrivateKey privateKey;
    }

    public record User(String username, Integer age) {
    }
}
