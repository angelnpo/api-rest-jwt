package ec.com.api.jwt.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Application properties.
 * 
 * @author Angel Cuenca
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

	private Jwt jwt;
	private Data data;

	@Getter
	@Setter
	public static class Jwt {
		String tokenPrefix;
		String secret;
		Long expiration;
	}

	public record Data(String name, Integer age) {
	}
}
