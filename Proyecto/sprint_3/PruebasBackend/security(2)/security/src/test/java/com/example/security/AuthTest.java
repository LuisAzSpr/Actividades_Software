package com.example.security;


import com.example.security.auth.AuthenticationService;
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
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@CucumberContextConfiguration
@SpringBootTest

public class AuthTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RegisterRequest registerRequest;

    @Autowired
    private TokenRepository tokenRepository;

    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse authenticationResponse;
    private Exception exception;


    @Given("^(?i)nombre\\s+de\\s+usuario\\s+no\\s+registrado\\s+(\\w+)$")
    public void nombreDeUsuarioNOregistrado(String nombreUsuario){

        exception = null;
        registerRequest = new RegisterRequest();
        authenticationRequest = new AuthenticationRequest();

        registerRequest.setUsername(nombreUsuario);
        authenticationRequest.setUsername(nombreUsuario);

        Optional<User> user = userRepository.findByUsername(nombreUsuario);
        assertTrue(user.isEmpty());

    }

    @Given("^(?i)nombre\\s+de\\s+usuario\\s+registrado\\s+(\\w+)$")
    public void nombreDeUsuarioRegistrado(String nombreUsuario){
        exception = null;
        userRepository.save(User.builder().username(nombreUsuario).email(nombreUsuario+"@gmail.com").
                password(passwordEncoder.encode(nombreUsuario+"123")).role(Role.USER).build());
        registerRequest = new RegisterRequest();
        authenticationRequest = new AuthenticationRequest();

        registerRequest.setUsername(nombreUsuario);
        authenticationRequest.setUsername(nombreUsuario);

        Optional<User> user = userRepository.findByUsername(nombreUsuario);
        assertTrue(user.isPresent());
    }

    @Given("^(?i)un\\s+correo\\s+electronico\\s+registrado\\s+(\\w+)$")
    public void correoElectronicoRegistrado(String correo){
        var user = User.builder().username("unEquis").email(correo).
                password("UnX123").role(Role.USER).build();
        userRepository.save(user);
        registerRequest.setEmail(correo);
        Optional<User> user2 = userRepository.findByEmail(correo);
        assertTrue(user2.isPresent());
    }

    @Given("^(?i)un\\s+correo\\s+electronico\\s+no\\s+registrado\\s+(\\w+)$")
    public void correoElectronicoNOregistrado(String correo){
        registerRequest.setEmail(correo);
        Optional<User> user = userRepository.findByEmail(correo);
        assertFalse(user.isPresent());
    }

    @Given("^(?i)una\\s+contrasena\\s+(\\w+)")
    public void contrasena(String contrasena1){
        registerRequest.setPassword(contrasena1);
        authenticationRequest.setPassword(contrasena1);
    }

    @Given("^(?i)otra\\s+contrasena\\s+(\\w+)\\s+coincidente\\s+con\\s+la\\s+anterior$")
    public void contrasenaCoincidente(String contrasena2){
        registerRequest.setConfirmPassword(contrasena2);
        assertEquals(registerRequest.getPassword(),contrasena2);
    }

    @Given("^(?i)otra\\s+contrasena\\s+(\\w+)\\s+que\\s+no\\s+coincide\\s+con\\s+la\\s+anterior$")
    public void contrasenaNOcoincidente(String contrasena2){
        registerRequest.setConfirmPassword(contrasena2);
        assertNotEquals(registerRequest.getPassword(),contrasena2);
    }

    @When("^(?i)me\\s+registro\\s+con\\s+esos\\s+campos$")
    public void MeRegistroConEsosCampos(){
        try{
            authenticationResponse = authenticationService.register(registerRequest);
        }catch(Exception e){
            exception = e;
        }
    }

    @When("^(?i)inicio\\s+sesion\\s+con\\s+esos\\s+campos$")
    public void inicioSesionConEsosCampos(){
        try{
            authenticationResponse = authenticationService.authenticate(authenticationRequest);
        }catch(Exception e){
            exception = e;
        }
    }

    @Then("^(?i)la\\s+cuenta\\s+debe\\s+ser\\s+creada\\s+exitosamente$")
    public void RegistroExitosoDeUsuario(){
        assertNotNull(authenticationResponse.getToken());
    }

    @Then("^(?i)la\\s+cuenta\\s+no\\s+debe\\s+ser\\s+creada$")
    public void RegistroNOexitosoDeUsuario(){
        assertNull(authenticationResponse);
    }


    @Then("^(?i)la\\s+cuenta\\s+debe\\s+ser\\s+logeada\\s+exitosamente$")
    public void LogeoExistosoDeUsuario(){
        assertNotNull(authenticationResponse);
    }


    @Then("^(?i)la\\s+cuenta\\s+NO\\s+debe\\s+ser\\s+logeada\\s+exitosamente$")
    public void LogeoFallidoDeUsuario(){
        assertNull(authenticationResponse);
    }

    @Then("^(?i)el\\s+sistema\\s+retorna\\s+usuario\\s+no\\s+registrado$")
    public void sistemaRetornaUsuarioNoRegistrado(){
        assertEquals(UserDoesntExist.class,exception.getClass());
    }


    @Then("^(?i)el\\s+sistema\\s+retorna\\s+contrasena\\s+incorrecta$")
    public void sistemaRetornaContrasenaIncorrecta(){
        assertEquals(PasswordDoesntMatch.class,exception.getClass());
    }

    @Then("^(?i)el\\s+sistema\\s+retorna\\s+usuario\\s+ya\\s+registrado$")
    public void SistemaDevuelveUsuarioYaRegistrado(){
        assertEquals(UserAlredyExistsException.class,exception.getClass());
    }

    @Then("^(?i)el\\s+sistema\\s+retorna\\s+correo\\s+ya\\s+registrado$")
    public void SistemaDevuelveCorreoYaRegistrado(){
        assertEquals(EmailAlredyInUse.class,exception.getClass());
    }

    @Then("^(?i)el\\s+sistema\\s+retorna\\s+contrasenas\\s+no\\s+coincidentes$")
    public void SistemaDevuelveContrasenasNoCoinciden(){
        assertEquals(PasswordDoesntMatch.class,exception.getClass());
    }

}






