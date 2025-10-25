package ec.com.api.jwt.common;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security")
public record AppSecurityProperties(Jwt jwt, Rsa rsa, User user) {

    public record Jwt(String tokenPrefix, String secret, Long expiration) {
    }

    public record Rsa(KeyPair keyPair) {
    }

    public record KeyPair(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
    }

    public record User(String username, Integer age) {
    }
}
