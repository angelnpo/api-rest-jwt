package ec.com.api.jwt.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ec.com.api.jwt.common.AppSecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.api.jwt.secret}")
    private String secretKey;

    private final AppSecurityProperties appSecurityProperties;

    /**
     * Generate a token for the user.
     * 
     * @param userEntity
     * @return
     */
    public String getToken(UserDetails userEntity) {
        return this.generateToken(new HashMap<>(), userEntity);
    }

    /**
     * Generate a token for the user.
     * 
     * @param extraClaims
     * @param userEntity
     * @return
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userEntity) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userEntity.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(
                        System.currentTimeMillis() + this.appSecurityProperties.jwt().expiration()))
                .setIssuer("apiRestJwt").signWith(this.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Get the key.
     * 
     * @return
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
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

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(this.getKey()).build().parseClaimsJws(token)
                .getBody();
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
