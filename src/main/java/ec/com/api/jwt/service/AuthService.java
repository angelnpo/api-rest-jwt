package ec.com.api.jwt.service;

import ec.com.api.jwt.dto.AuthDTO;
import ec.com.api.jwt.dto.LoginDTO;
import ec.com.api.jwt.dto.UserDTO;
import ec.com.api.jwt.entity.RefreshTokenEntity;
import ec.com.api.jwt.entity.UserEntity;
import ec.com.api.jwt.enums.Role;
import ec.com.api.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Class to handle the authentication.
 *
 * @author Angel Cuenca
 */
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final RefreshTokenService refreshTokenService;

	/**
	 * Login a user.
	 *
	 * @param loginDTO
	 * @return
	 */
	public AuthDTO login(LoginDTO loginDTO) {
//		this.authenticationManager
//				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

//		UserEntity userEntity = this.userRepository.findByUsername(loginDTO.getUsername())
//				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		//FIXME: authenticate by email
		this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

		UserEntity userEntity = this.userRepository.findByEmail(loginDTO.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		String token = this.jwtService.getToken(userEntity);
		RefreshTokenEntity refreshToken = this.refreshTokenService.createRefreshToken(userEntity);

		return AuthDTO.builder().token(token).refreshToken(refreshToken.getToken()).build();
	}

	/**
	 * Register a new user.
	 *
	 * @param userDTO
	 * @return
	 */
	public AuthDTO register(UserDTO userDTO) {
		UserEntity userEntity = UserEntity.builder().username(userDTO.getUsername()).firstName(userDTO.getFirstName())
				.lastName(userDTO.getLastName()).password(this.passwordEncoder.encode(userDTO.getPassword()))
				.role(Role.ADMIN).build();

		this.userRepository.save(userEntity);
		return AuthDTO.builder().token(this.jwtService.getToken(userEntity)).build();
	}

	/**
	 * Refresh the token.
	 *
	 * @param oldRefreshToken
	 * @return
	 */
	public AuthDTO refreshToken(String oldRefreshToken) {

		RefreshTokenEntity validToken = this.refreshTokenService.validateRefreshToken(oldRefreshToken);

		UserEntity user = this.userRepository.findById(validToken.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		this.refreshTokenService.deleteByUserId(user.getId());

		String newAccessToken = this.jwtService.getToken(user);
		RefreshTokenEntity newRefreshToken = this.refreshTokenService.createRefreshToken(user);

		return AuthDTO.builder().token(newAccessToken).refreshToken(newRefreshToken.getToken()).build();
	}
}
