package ec.com.api.jwt.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Application properties record.
 *
 * @author Angel Cuenca
 */
@ConfigurationProperties(prefix = "app")
public record AppRecordProperties(Jwt jwt, Data data) {

	public record Jwt(String tokenPrefix, String secret, Long expiration) {
	}

	public record Data(String name, Integer age) {
	}
}