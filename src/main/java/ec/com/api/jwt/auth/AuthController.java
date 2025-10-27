package ec.com.api.jwt.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.api.jwt.common.AppSecurityProp;
import ec.com.api.jwt.dto.AuthDto;
import ec.com.api.jwt.dto.LoginDto;
import ec.com.api.jwt.dto.RegisterDto;
import ec.com.api.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppSecurityProp appSecurityProp;

    /**
     * Login a user.
     * 
     * @param loginReq
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody LoginDto loginReq) {
        // This is an example of how to use the properties
        this.appSecurityProp.getJwt().getSecret();
        this.appSecurityProp.getData().age();

        return ResponseEntity.ok(this.authService.login(loginReq));
    }

    /**
     * Register a new user.
     * 
     * @param registerReq
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<AuthDto> register(@RequestBody RegisterDto registerReq) {
        return ResponseEntity.ok(this.authService.register(registerReq));
    }
}
