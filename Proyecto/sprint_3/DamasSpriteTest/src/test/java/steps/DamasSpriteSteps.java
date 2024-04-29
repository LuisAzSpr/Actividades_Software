package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.PaginaBienvenida;
import pages.PaginaMenu;
import pages.PaginaPrincipal;
import pages.PaginaRegistro;

import static org.junit.jupiter.api.Assertions.*;

public class DamasSpriteSteps {
    PaginaPrincipal paginaPrincipal = new PaginaPrincipal();
    PaginaRegistro paginaRegistro = new PaginaRegistro();
    PaginaBienvenida paginaBienvenida = new PaginaBienvenida();
    PaginaMenu paginaMenu = new PaginaMenu();

    @Given("Navego a www.DamasSprite.com")
    public void navegoADamasSprite() {
        paginaPrincipal.navegarADamasSprite();
    }
    @When("^Yo voy a la (.*) usando la barra de navegacion$")
    public void usoDeLaBarraDeNavegacion(String seccion) {
        paginaPrincipal.clickOnSectionNavigateBar(seccion);
    }

    @Then("^Navego a las rutas correspondientes de cada (.*)$")
    public void navegarPorLaBarraDeNavegacion(String seccion){
        String urlActual = paginaPrincipal.getUrlActual();
        if(seccion.equals("Inicio")) seccion = "";
        assertTrue(urlActual.contains(seccion.replaceAll("\\s+", "")), "No se navego a la seccion " + seccion);
    }
    @And("^Dejo todos los campos vacios$")
    public void dejoTodosLosCamposVacios() {

    }
    @When("^Yo le doy al boton Ingresar$")
    public void yoLeDoAlBotonIngresar() {
        paginaPrincipal.clickOnIngresarBotton();
    }
    @Then("^El sistema no me permite (ingresar|registrar)$")
    public void elSistemaNoPermiteAvanzar(String tipo) {
        if(tipo.equals("ingresar")) tipo = "";
        String urlActual = paginaPrincipal.getUrlActual();
        assertTrue(urlActual.contains(tipo), "El sistema no deberia permitir pasar de: " + tipo);
    }
    @Then("^Yo le doy al boton Registrarse$")
    public void yoLeDoAlBotonRegistrarse() {
        paginaPrincipal.clickOnRegistrarseButton();
    }
    @And("^Lleno los campos nombre de usuario con (.*) correo electronico con (.*) elegimos el password (.*) confirmamos el password con (.*)$")
    public void llenamosLosCampos(String usuario, String correo, String password, String confirmPassword) {
        paginaRegistro.llenarCampo("nombre",usuario);
        paginaRegistro.llenarCampo("email",correo);
        paginaRegistro.llenarCampo("password",password);
        paginaRegistro.llenarCampo("confirmPassword",confirmPassword);
    }
    @And("^Aceptamos los terminos y condiciones$")
    public void aceptamosLosTerminosYCondiciones() {
        paginaRegistro.clickTerminosYCondiciones();
    }
    @When("^Yo le doy otra vez al boton Registrarse$")
    public void yoLeDoyOtraVezAlBotonRegistrarse() {
        paginaRegistro.clickRegistrarseButton();
    }
    @Then("^El sistema me da la bienvenida a DamasSprite$")
    public void elSistemaMeDaLaBienvenidaADamasSprite() {
        assertTrue(paginaBienvenida.isMessageWelcomePresent(), "El mensaje de bienvenida no se encuentra en la pagina");
    }
    @And("^Lleno los campos de login con nombre de usuario con (.*) y con un password (.*)$")
    public void llenarCampoLogin(String nombre, String password) {
        paginaPrincipal.completarCampos(nombre, password);
    }
    @Then("^El sistema me muestra el menu del juego$")
    public void elSistemaMeMuestraElMenuDelJuego() {
        assertTrue(paginaMenu.isPresentButton("Jugar 1 vs 1") &
                paginaMenu.isPresentButton("Jugar contra la Maquina"), "Deberia mostrarse el menu del juego");
    }
    @And("^Estoy en la pagina del menu del juego$")
    public void estoyEnLaPaginaDelJuego() {
        paginaPrincipal.completarCampos("Ao1234", "Ao1234AoAo");
        paginaPrincipal.clickOnIngresarBotton();
    }
    @When("^Yo le doy al boton Cerrar sesion$")
    public void yoLeDoyAlBotonCerrarSesion() {
        paginaMenu.cerrarSesion();
    }
    @Then("^Regreso a la pagina de inicio$")
    public void regresoALaPaginaDeInicio() {
        assertEquals(paginaPrincipal.getUrlActual(),"http://localhost:3000/","Deberiamos retornar la pagina de inicio");
    }
}
