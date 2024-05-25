package miu.edu.cse.securitydemo.auth;

import lombok.RequiredArgsConstructor;
import miu.edu.cse.securitydemo.config.JwtService;
import miu.edu.cse.securitydemo.user.User;
import miu.edu.cse.securitydemo.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    //register
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = new User(
                registerRequest.getFirstName(),
                registerRequest.getFirstName(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
//                registerRequest.getPassword(),
                registerRequest.getRole()
                );
        User registerUser = userRepository.save(user);
        //Generate a token
        String token = jwtService.generateToken(registerUser);
        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }

    //authenticate
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //do authentication
        //If authentication fails, authenticate() throws AuthenticationException
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        //check user is available in database
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        //generate token
        String token  = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }
}
