package ec.com.api.jwt.service;

import ec.com.api.jwt.common.AppRecordProperties;
import ec.com.api.jwt.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    private final AppRecordProperties appRecordProperties;

    /**
     * Generate a token for the user.
     *
     * @param userEntity
     * @return
     */
    public String getToken(UserEntity userEntity) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("email", userEntity.getEmail());
        return this.generateToken(extraClaims, userEntity);
    }

    /**
     * Generate a token for the user.
     *
     * @param extraClaims
     * @param userEntity
     * @return
     */
    private String generateToken(Map<String, Object> extraClaims, UserEntity userEntity) {
        return Jwts.builder().claims(extraClaims).subject(userEntity.getUsername())
            .issuedAt(new Date(System.currentTimeMillis())).expiration(
                new Date(System.currentTimeMillis() + this.appRecordProperties.jwt().expiration()))
            .issuer("api-rest-jwt").signWith(this.getKey()).compact();
    }

    /**
     * Get the key.
     *
     * @return
     */
    private SecretKey getKey() {
        //byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        byte[] keyBytes = Decoders.BASE64URL.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extract the username from the token.
     *
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract the email from the token.
     *
     * @param token
     * @return
     */
    public String extractEmail(String token) {
        return this.extractClaim(token, claims -> claims.get("email", String.class));
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(this.getKey()).build().parseSignedClaims(token)
            .getPayload();
    }

    /**
     * Extract a claim from the token.
     *
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }
}
