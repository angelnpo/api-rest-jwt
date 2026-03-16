package ec.com.api.jwt.service;

import ec.com.api.jwt.entity.RefreshTokenEntity;
import ec.com.api.jwt.entity.UserEntity;
import ec.com.api.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${app.jwt.refresh-expiration}")
    private Long refreshExpirationMs;

    private final JwtService jwtService;
    private final RefreshTokenRepository repository;

    /**
     * Create a refresh token for the user.
     *
     * @param userEntity
     * @return
     */
    @Transactional
    public RefreshTokenEntity createRefreshToken(UserEntity userEntity) {
        this.repository.deleteByUserId(userEntity.getId());

        //Generated refresh token
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(refreshExpirationMs);
        String refreshTokenJwt = this.jwtService.getToken(userEntity);

        //Save to DB
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUserId(userEntity.getId());
        refreshToken.setToken(refreshTokenJwt);
        refreshToken.setExpiryDate(expiry);

        return this.repository.save(refreshToken);
    }

    public RefreshTokenEntity validateRefreshToken(String token) {

        RefreshTokenEntity refreshToken = repository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Refresh token no encontrado"));

        if (refreshToken.isExpired()) {
            repository.delete(refreshToken);
            throw new RuntimeException("Refresh token expirado");
        }

        return refreshToken;
    }

    public void deleteByUserId(Integer userId) {
        this.repository.deleteByUserId(userId);
    }
}
