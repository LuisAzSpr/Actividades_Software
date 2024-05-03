package com.example.security;


import com.example.security.auth.AuthenticationService;
import com.example.security.auth.Exceptions.*;
import com.example.security.auth.authClass.AuthenticationRequest;
import com.example.security.auth.authClass.AuthenticationResponse;
import com.example.security.auth.authClass.RegisterRequest;
import com.example.security.config.JwtService;
import com.example.security.token.TokenRepository;
import com.example.security.user.Role;
import com.example.security.user.User;
import com.example.security.user.UserRepository;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RequiredArgsConstructor
@CucumberContextConfiguration
@SpringBootTest
public class AuthTest {

    // Variables de -------
    private AuthenticationRequest authenticationRequest;
    private RegisterRequest registerRequest;
    private AuthenticationResponse authenticationResponse;
    private Exception exception;
    private AuthenticationResponse authResponse_logout;
    private User user;

    // Servicios
    @Autowired
    private JwtService jwtService;
    private final AuthenticationService service;
    private final PasswordEncoder passwordEncoder;

    // Acceso a datos
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @Before
    public void setUp() {

        // instanciamos nuevamente
        registerRequest = new RegisterRequest();
        authenticationResponse = new AuthenticationResponse();
        authenticationRequest = new AuthenticationRequest();
        exception = null;

        user = User.builder()
                .username("Pedro")
                .password("Pedro123")
                .email("Pedro@gmail.com")
                .role(Role.USER)
                .build();

        User userSave = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword())) // contrasena hasehada
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        // agregamos un primer usuario a la base de datos en cada escenario
        userRepository.save(userSave);

    }

    @After
    public void tearDown() {
        //limpiamos la base de datos despues de cada escenenario
        userRepository.deleteAll();
    }

    // ======================================= @Given ========================================

    @Given("^(?i)un\\s+nombre\\s+de\\s+usuario\\s+(\\w+)\\s+no\\s+registrado$")
    public void nombreDeUsuarioNoRegistrado(String username) {
        registerRequest.setUsername(username);
        authenticationRequest.setUsername(username);
        Optional<User> user = userRepository.findByUsername(username);
        assertTrue(user.isEmpty());
    }

    @Given("^(?i)un\\s+nombre\\s+de\\s+usuario\\s+(\\w+)\\s+registrado$")
    public void nombreDeUsuarioRegistrado(String username) {
        registerRequest.setUsername(username);
        authenticationRequest.setUsername(username);
        Optional<User> user = userRepository.findByUsername(username);
        assertTrue(user.isPresent());
    }

    @Given("^(?i)una\\s+direccion\\s+de\\s+correo\\s+([\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,})\\s+no\\s+registrado$")
    public void direccionDeCorreoNoRegistrado(String email) {
        registerRequest.setEmail(email);
        Optional<User> user = userRepository.findByEmail(email);
        assertTrue(user.isEmpty());
    }

    @Given("^(?i)una\\s+direccion\\s+de\\s+correo\\s+([\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,})\\s+registrado$")
    public void direccionDeCorreoRegistrado(String email) {
        registerRequest.setEmail(email);
        Optional<User> user = userRepository.findByEmail(email);
        assertTrue(user.isPresent());
    }

    @Given("^(?i)una\\s+contrasena\\s+(\\w+)$")
    public void contrasenaValida(String password) {
        registerRequest.setPassword(password);
        assertTrue(service.contrasenaEsValida(password));
    }

    @Given("^(?i)una\\s+contrasena\\s+no\\s+valida\\s+(\\w+)$")
    public void contrasenaNoValida(String password) {
        registerRequest.setPassword(password);
        assertFalse(service.contrasenaEsValida(password));
    }

    @Given("^(?i)una\\s+contrasena\\s+de\\s+(\\w+)\\s+que\\s+coincida\\s+con\\s+la\\s+anterior$")
    public void contrasenaCoincidente(String password) {
        registerRequest.setConfirmPassword(password);
        assertEquals(registerRequest.getPassword(),
                registerRequest.getConfirmPassword());
    }

    @Given("^(?i)una\\s+contrasena\\s+de\\s+(\\w+)\\s+que\\s+NO\\s+coincida\\s+con\\s+la\\s+anterior$")
    public void contrasenaNOcoincidente(String password) {
        registerRequest.setConfirmPassword(password);
        assertNotEquals(registerRequest.getPassword(),
                registerRequest.getConfirmPassword());
    }

    @Given("^(?i)una\\s+contrasena\\s+(\\w+)\\s+correspondiente\\s+al\\s+usuario$")
    public void contrasenaCorrectaDelUsuario(String password){
        authenticationRequest.setPassword(password);
    }

    @Given("^(?i)una\\s+contrasena\\s+(\\w+)\\s+no\\s+correspondiente\\s+al\\s+usuario$")
    public void contrasenaIncorrectaDelUsuario(String password){
        authenticationRequest.setPassword(password);
    }

    @Given("(?i)un\\s+usuario\\s+logeado\\s+con\\s+nombre\\s+de\\s+usuario\\s+(\\w+)$")
    public void usuarioLogeado(String username){
        assertEquals(user.getUsername(),username); // comparamos si es el mismo usuario creado en el setup
        authenticationRequest = AuthenticationRequest.builder() // creamos un authenticationResponse para su inicio de sesion
                        .username(username)
                        .password(user.getPassword())
                        .build();
        // iniciamos sesion con el usuario
        authResponse_logout = service.authenticate(authenticationRequest);

        // verificamos que sea no nulo
        assertNotNull(authResponse_logout.getToken());
    }

    // ======================================= @When ========================================

    @When("^(?i)me\\s+registro\\s+con\\s+esos\\s+campos$")
    public void registroDeUsuario() {
        try {
            authenticationResponse = service.register(registerRequest);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("^(?i)inicio\\s+sesion\\s+con\\s+esos\\s+campos$")
    public void inicioDeSesionDeUsuario() {
        try {
            authenticationResponse = service.authenticate(authenticationRequest);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("^(?i)cierro\\s+sesion\\s+con\\s+este\\s+usuario$")
    public void cierreDeSesion(){
        service.realizarCierre(authResponse_logout.getToken());
    }

    // ======================================= @Then ========================================

    // ============= Acciones del sistema ==========

    @Then("^(?i)la\\s+cuenta\\s+debe\\s+ser\\s+registrada\\s+exitosamente$")
    public void seRegistraExitosamente() {
        Optional<User> user = userRepository.findByUsername(registerRequest.getUsername());
        assertTrue(user.isPresent());
    }

    @Then("^(?i)la\\s+cuenta\\s+no\\s+debe\\s+ser\\s+registrada$")
    public void noSeRegistra() {
        assertEquals(1,userRepository.findAll().size());
    }

    // la cuenta no se crea exitosamente
    @Then("^(?i)la\\s+cuenta\\s+debe\\s+ser\\s+logeada\\s+exitosamente$")
    public void seLogeaExitosamente() {
        assertNotNull(authenticationResponse);
    }

    @Then("^(?i)la\\s+cuenta\\s+no\\s+debe\\s+ser\\s+logeada$")
    public void noSeLogeaExitosamente() {
        assertNull(authenticationResponse.getToken());
    }

    // Retorna un token de acceso valido
    @Then("^(?i)el\\s+sistema\\s+retorna\\s+un\\s+token\\s+de\\s+sesion\\s+valido$")
    public void retornaUnToken() {
        String token = authenticationResponse.getToken();
        String name = (registerRequest!=null) ? registerRequest.getUsername() : authenticationRequest.getUsername();
        User user = userRepository.findByUsername(name).orElseThrow(null);
        boolean isTokenValid = jwtService.isTokenValid(token, user);
        assertTrue(isTokenValid);
    }

    @Then("^(?i)la\\s+sesion\\s+es\\s+cerrada\\s+invalidando\\s+el\\s+token\\s+de\\s+acceso$")
    public void invalidaToken(){
        assertFalse(jwtService.isTokenValid(authResponse_logout.getToken(),user));
    }

    // =========== Mensajes de error ===========

    // Mensaje de error -> Usuario NO registrado
    @Then("^(?i)el\\s+sistema\\s+muestra\\s+un\\s+mensaje\\s+de\\s+nombre\\s+ya\\s+en\\s+uso$")
    public void mensajeDeErrorUsuarioYAenUso() {
        assertEquals(UserAlredyExistsException.class,exception.getClass());
    }

    // Mensaje de error -> Usuario NO registrado
    @Then("^(?i)el\\s+sistema\\s+muestra\\s+un\\s+mensaje\\s+de\\s+correo\\s+ya\\s+en\\s+uso$")
    public void mensajeDeErrorCorreoYAenUso() {
        assertEquals(EmailAlredyInUse.class,exception.getClass());
    }

    // Mensaje de error -> Usuario NO registrado
    @Then("^(?i)el\\s+sistema\\s+muestra\\s+un\\s+mensaje\\s+de\\s+contrasenas\\s+no\\s+coinciden$")
    public void mensajeDeErrorContrasenasNOcoinciden() {
        assertEquals(PasswordDoesntMatch.class,exception.getClass());
    }

    @Then("^(?i)el\\s+sistema\\s+muestra\\s+un\\s+mensaje\\s+de\\s+contrasena\\s+insegura$")
    public void contrasenaInsegura(){
        assertEquals(InsecurePassword.class,exception.getClass());
    }

    // Mensaje de error -> Usuario NO registrado
    @Then("^(?i)el\\s+sistema\\s+muestra\\s+un\\s+mensaje\\s+de\\s+usuario\\s+inexistente$")
    public void usuarioInexistente() {
        assertEquals(UserDoesntExist.class,exception.getClass());
    }
}




