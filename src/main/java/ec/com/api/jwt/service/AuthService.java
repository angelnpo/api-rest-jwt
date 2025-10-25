package ec.com.api.jwt.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ec.com.api.jwt.entity.UserEntity;
import ec.com.api.jwt.enums.Role;
import ec.com.api.jwt.repository.UserRepository;
import ec.com.api.jwt.vo.AuthRes;
import ec.com.api.jwt.vo.LoginReq;
import ec.com.api.jwt.vo.RegisterReq;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user.
     * 
     * @param registerReq
     * @return
     */
    public AuthRes register(RegisterReq registerReq) {
        UserEntity userEntity = UserEntity.builder().username(registerReq.getUsername())
                .firstName(registerReq.getFirstName()).lastName(registerReq.getLastName())
                .password(this.passwordEncoder.encode(registerReq.getPassword()))
                .country(registerReq.getCountry()).role(Role.USER).build();

        this.userRepository.save(userEntity);

        return AuthRes.builder().token(this.jwtService.getToken(userEntity)).build();
    }

    /**
     * Login a user.
     * 
     * @param loginReq
     * @return
     */
    public AuthRes login(LoginReq loginReq) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginReq.getUsername(), loginReq.getPassword()));

        UserEntity userEntity = this.userRepository.findByUsername(loginReq.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return AuthRes.builder().token(this.jwtService.getToken(userEntity)).build();
    }
}
