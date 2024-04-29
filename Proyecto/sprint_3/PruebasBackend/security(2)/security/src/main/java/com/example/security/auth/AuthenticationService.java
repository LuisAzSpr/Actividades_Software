package com.example.security.auth;

import com.example.security.auth.Exceptions.EmailAlredyInUse;
import com.example.security.auth.Exceptions.PasswordDoesntMatch;
import com.example.security.auth.Exceptions.UserAlredyExistsException;
import com.example.security.auth.Exceptions.UserDoesntExist;
import com.example.security.auth.authClass.AuthenticationRequest;
import com.example.security.auth.authClass.AuthenticationResponse;
import com.example.security.auth.authClass.RegisterRequest;
import com.example.security.config.JwtService;
import com.example.security.token.TokenRepository;
import com.example.security.token.tokenEntity;
import com.example.security.user.Role;
import com.example.security.user.User;
import com.example.security.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    public AuthenticationResponse register(RegisterRequest request) {// Obtenemos un objeto de la clase RegisterRequest

        // verificamos si es que ya se registraron el nombre de usuario o el correo
        Optional<User> optionUsername = repository.findByUsername(request.getUsername());
        Optional<User> optionEmail = repository.findByEmail(request.getEmail());

        // ademas tomamos las 2 contrasenas ingresadas para el registro
        String contrasena = request.getPassword();
        String confirmContrasena = request.getConfirmPassword();

        // si optionUsername esta presente entonces si existe un usuario con ese nombre
        if(optionUsername.isPresent()){
            throw new UserAlredyExistsException("El nombre de usuario ya se encuentra registrado"); // lanza exception personalizada de username
        }
        if(optionEmail.isPresent()){ // si optionEmail esta presente entonces si existe un usuario con ese correo
            throw new EmailAlredyInUse("El correo electronico ya se encuentra registrado"); // lanza exception personalizada de email
        }
        if(!contrasena.equals(confirmContrasena)){ // si las contrasenas no coinciden
            throw new PasswordDoesntMatch("Las contrasenas no coinciden"); // lanza exception personalizada de email
        }

        // ahora guarda el usuario en la base de datos
        var user = User.builder()
                .username(request.getUsername()) // se ingresan los campos ...
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // codifica la contrasena
                .role(Role.USER)
                .build();
        repository.save(user);

        // se generoa el token apartir de los datos
        var jwtToken = jwtService.generateToken(user);

        // el token es guardado en una base de datos (esto nos servira a la hora de hacer logout)
        var token = tokenEntity.builder()
                .tokenStr(jwtToken)
                .username(request.getUsername())
                .build();
        tokenRepository.save(token);

        // retorna un Authentication response cn el jwtToken con el que posteriormente accedera
        // a endpoints protegidos
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Verifica si el nombre de usuario ya esta registrado
        Optional<User> optionUsername = repository.findByUsername(request.getUsername());

        if(optionUsername.isEmpty()){ // si no esta registrado
            throw new UserDoesntExist("Nombre de usuario no registrado"); // retorna exception
        }

        try{
            // si el usuario estaba registrado, verificamos ahora si las credenciales son correctas
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        }catch(AuthenticationException e){
            // si no, entonces la contrasena debe ser incorrecta
            throw new PasswordDoesntMatch("La contrasena es incorrecta");
        }

        // ahora generamos un user que posteriormente se usara para crear un token
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();

        // se crea el token
        var jwtToken = jwtService.generateToken(user);

        //el token es guardado en la base de datos
        var token = tokenEntity.builder()
                .tokenStr(jwtToken)
                .username(request.getUsername())
                .build();
        tokenRepository.save(token);

        // retorna un Authentication response cn el jwtToken con el que posteriormente accedera
        // a endpoints protegidos
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    @Transactional
    public String logOut(HttpServletRequest request) {
        // este metodo al ser una transaccion se ejectuara tod o nad
        String authorizationHeader = request.getHeader("Authorization"); // toma la autorizacion del header
        String jwtToken;
        // Verificamos si la cabecera comienza con Bearer y la autorizacion no es nula
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            // si es asi extraemos el token
            jwtToken = authorizationHeader.substring(7);
            // eliminamos el token de la base de datos (el usuario ya no puede acceder con ese token)
            tokenRepository.deleteByTokenStr(jwtToken);
            return "Se cerro el inicio de sesion";
        }
        return "No existe inicio de sesion";
    }

}
