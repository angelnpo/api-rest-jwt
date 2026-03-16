package ec.com.api.jwt.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.api.jwt.common.AppProperties;
import ec.com.api.jwt.dto.AuthDTO;
import ec.com.api.jwt.dto.LoginDTO;
import ec.com.api.jwt.dto.UserDTO;
import ec.com.api.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to handle the authentication.
 *
 * @author Angel Cuenca
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final AppProperties appProperties;
	private final AuthService authService;

	/**
	 * Login a user.
	 *
	 * @param loginDTO
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<AuthDTO> login(@RequestBody LoginDTO loginDTO) {
		// This is an example of how to use the properties
		log.info("property secret: {}", this.appProperties.getJwt().getSecret());
		log.info("property name: {}", this.appProperties.getData().name());

		return ResponseEntity.ok(this.authService.login(loginDTO));
	}

	/**
	 * Register a new user.
	 *
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<AuthDTO> register(@RequestBody UserDTO userDTO) {
		return ResponseEntity.ok(this.authService.register(userDTO));
	}

	/**
	 * Refresh the token.
	 *
	 * @param body
	 * @return
	 */
	@PostMapping("/refresh")
	public ResponseEntity<AuthDTO> refresh(@RequestBody Map<String, String> body) {

		return ResponseEntity.ok(this.authService.refreshToken(body.get("refreshToken")));
	}
}
