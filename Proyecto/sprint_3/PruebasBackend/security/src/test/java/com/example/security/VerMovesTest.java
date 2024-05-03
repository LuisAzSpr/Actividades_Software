package com.example.security;
import com.example.security.juego.Juego;
import com.example.security.juego.componentes.Casilla;
import com.example.security.juego.componentes.Ficha;
import com.example.security.juego.componentes.Tablero;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Before;

import java.util.Objects;

import static com.example.security.GameTest.obtenerCasilla;
import static org.junit.jupiter.api.Assertions.*;


public class VerMovesTest {

    Juego juego;
    String turno1;

    int[] posicionJugador;
    int[] posicionRival;

    @Given("^(?i)el\\s+tablero\\s+en\\s+juego$")
    public void tableroJuego(){
        Tablero tablero = new Tablero();
        juego = new Juego(tablero);
    }

    // Setteo de turno
    @Given("^(?i)es\\s+el\\s+turno\\s+de\\s+(negro|blanco)$")
    public void cambiarTurno(String turno){
        turno1 = turno;
        juego.setTurno(turno);
    }

    // agregar ficha inicial del jugador=turno
    @Given("^(?i)el\\s+jugador\\s+tiene\\s+(peon|dama)\\s+en\\s+" +
            "([A-H][1-8])$")
    public void propiaFicha(String ficha,String posicion){
        if(Objects.equals(ficha,"dama")){ficha="reina";}
        posicionJugador = juego.transformarCasillaReversa(posicion);
        Casilla fichaInsertar = obtenerCasilla(ficha,turno1);
        juego.getTablero().setCasilla(posicionJugador[0], posicionJugador[1],fichaInsertar);
    }


    @Given("^(?i)el\\s+rival\\s+tiene\\s+una\\s+ficha\\s+en\\s+" +
            "([A-H][1-8])$")
    public void fichaRival(String posicion){

        posicionRival = juego.transformarCasillaReversa(posicion);
        Casilla fichaInsertar=(Objects.equals(turno1,"negro"))? Casilla.PEONBLANCO:Casilla.PEONNEGRO;
        juego.getTablero().setCasilla(posicionRival[0], posicionRival[1],fichaInsertar);
    }


    @When("^(?i)el\\s+jugador\\s+tiene\\s+movimientos\\s+de\\s+captura$")
    public void movCaptura(){
        boolean cap=juego.tieneMovimientosDeCaptura(turno1);
        assertTrue(cap);
    }

    @When("^(?i)el\\s+jugador\\s+no\\s+tiene\\s+movimientos\\s+de\\s+captura$")
    public void noMovCaptura(){
        boolean cap=juego.tieneMovimientosDeCaptura(turno1);
        assertFalse(cap);
    }


    @Then("^se muestran los movimientos de captura")
    public void showCapture(){
        boolean cap=juego.tieneMovimientosDeCaptura(turno1);
        int[][] arr=juego.movimientosCapturaValidos(posicionJugador[0],posicionJugador[1]);
        assertNotNull(arr);
    }

    @Then("^se muestran los movimientos simples")
    public void showSimple(){
        int[][] arr = juego.movimientosSimplesValidos(posicionJugador[0],posicionJugador[1]);
        assertNotNull(arr);
    }



}
