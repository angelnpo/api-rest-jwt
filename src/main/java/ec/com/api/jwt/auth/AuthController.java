package ec.com.api.jwt.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.api.jwt.common.AppSecurityProp;
import ec.com.api.jwt.service.AuthService;
import ec.com.api.jwt.vo.AuthRes;
import ec.com.api.jwt.vo.LoginReq;
import ec.com.api.jwt.vo.RegisterReq;
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
    public ResponseEntity<AuthRes> login(@RequestBody LoginReq loginReq) {
        // This is an example of how to use the properties
        this.appSecurityProp.getJwt().getSecret();
        this.appSecurityProp.getUser().age();

        return ResponseEntity.ok(this.authService.login(loginReq));
    }

    /**
     * Register a new user.
     * 
     * @param registerReq
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<AuthRes> register(@RequestBody RegisterReq registerReq) {
        return ResponseEntity.ok(this.authService.register(registerReq));
    }
}
