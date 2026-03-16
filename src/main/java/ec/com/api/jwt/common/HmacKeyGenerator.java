package ec.com.api.jwt.common;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to generate HMAC key.
 *
 * @author Angel Cuenca
 */
@Slf4j
public class HmacKeyGenerator {

	/**
	 * Main method to generate HMAC key.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Generated secretKey
			SecretKey secretKey = Jwts.SIG.HS512.key().build();

			// Convert to Base64 for used how secret
			// String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
			// log.info("HMAC-SHA512 key (base64): {}", base64Key);
			String base64UrlKey = Encoders.BASE64URL.encode(secretKey.getEncoded());
			log.info("HMAC-SHA512 key (base64): {}", base64UrlKey);
		} catch (Exception e) {
			log.error("Error HmacSHA512 secret:", e);
		}
	}
}
