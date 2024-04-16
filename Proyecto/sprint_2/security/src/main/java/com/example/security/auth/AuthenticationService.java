package com.example.security.auth;

import com.example.security.auth.Exceptions.PasswordDoesntMatch;
import com.example.security.auth.Exceptions.UserAlredyExistsException;
import com.example.security.auth.Exceptions.UserDoesntExist;
import com.example.security.config.JwtService;
import com.example.security.token.TokenRepository;
import com.example.security.token.tokenEntity;
import com.example.security.user.Role;
import com.example.security.user.User;
import com.example.security.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
//import org.apache.commons.validator.routines.EmailVaidator;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {

        Optional<User> optionUsername = repository.findByUsername(request.getUsername());
        Optional<User> optionEmail = repository.findByEmail(request.getEmail());

        if(optionUsername.isPresent()){
            System.out.println("El nombre de usuario ya se encuentra registrado, elija otro");
            throw new UserAlredyExistsException("El nombre de usuario ya se encuentra registrado");
        }

        if(optionEmail.isPresent()){
            System.out.println("El email ya se encuentra registrado");
            throw new UserAlredyExistsException("El correo electronico ya se encuentra registrado");
        }

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .tokenValid(true)
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);


        var token = tokenEntity.builder()
                .tokenStr(jwtToken)
                .username(request.getUsername())
                .build();

        tokenRepository.save(token);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        Optional<User> optionUsername = repository.findByUsername(request.getUsername());

        if(optionUsername.isEmpty()){
            System.out.println("Nombre de usuario no registrado");
            throw new UserDoesntExist("Nombre de usuario no registrado");
        }

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        }catch(AuthenticationException e){
            throw new PasswordDoesntMatch("La contrasena es incorrecta");
        }

        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        var token = tokenEntity.builder()
                .tokenStr(jwtToken)
                .username(request.getUsername())
                .build();

        tokenRepository.save(token);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public String logOut(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken;
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            jwtToken = authorizationHeader.substring(7);
            tokenRepository.deleteByTokenStr(jwtToken);
            return "Se cerro el inicio de sesion";
        }
        return "No existe inicio de sesion";
    }

}
