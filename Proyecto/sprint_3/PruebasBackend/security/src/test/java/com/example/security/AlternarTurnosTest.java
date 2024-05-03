package com.example.security;


import com.example.security.juego.Juego;
import com.example.security.juego.componentes.Casilla;
import com.example.security.juego.componentes.Tablero;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Objects;

import static com.example.security.GameTest.obtenerCasilla;
import static org.junit.jupiter.api.Assertions.*;

public class AlternarTurnosTest{

    //====Variables====
    Juego game;
    int[] posicionInicial= new int[2];
    int[] posicionFinal= new int[2];
    int[] posicionRival1=new int[2];
    int[] posicionRival2=new int[2];
    String turnoActual;



    // =========================================== GIVEN ==============================

    //Iniciacion de juego
    @Given("^(?i)un\\s+tablero\\s+en\\s+juego$")
    public void tableroEnJuego(){
        Tablero table = new Tablero();
        game = new Juego(table);
    }

    // Setteo de turno
    @Given("^(?i)turno\\s+de\\s+(negro|blanco)$")
    public void cambiarTurno(String turno){
        turnoActual = turno;
        game.setTurno(turno);
    }

    // Agrega una ficha en una casilla especifica
    @Given("^(?i)el\\s+jugador\\s+tiene\\s+una?\\s+(peon|dama)\\s+en\\s+([A-H][1-8])$")
    public void setFicha(String ficha,String posicionFicha){
        if(Objects.equals(ficha,"dama")){ficha="reina";}
        posicionInicial = game.transformarCasillaReversa(posicionFicha);
        Casilla fichaInsertar = obtenerCasilla(ficha,turnoActual);
        game.getTablero().setCasilla(posicionInicial[0], posicionInicial[1],fichaInsertar);
    }

    // Agrega una ficha rival en una casilla especifia
    @Given("^(?i)el\\s+rival\\s+tiene\\s+una\\s+ficha\\s+cualquiera\\s+en\\s+([A-H][1-8])$")
    public void setFicha(String posicionFicha){
        posicionRival1 = game.transformarCasillaReversa(posicionFicha);
        Casilla casilla1=(Objects.equals(turnoActual,"negro"))? Casilla.PEONBLANCO:Casilla.PEONNEGRO;
        game.getTablero().setCasilla(posicionRival1[0],posicionRival1[1],casilla1);
    }

    // Agrega una ficha rival extra en una casilla especifica
    @Given("^(?i)el\\s+rival\\s+tiene\\s+una\\s+ficha\\s+extra\\s+cualquiera\\s+en\\s+([A-H][1-8])$")
    public void setFichaExtra(String posicionFicha){
        posicionRival2 = game.transformarCasillaReversa(posicionFicha);
        Casilla casilla1=(Objects.equals(turnoActual,"negro"))? Casilla.PEONBLANCO:Casilla.PEONNEGRO;
        game.getTablero().setCasilla(posicionRival2[0],posicionRival2[1],casilla1);
    }

    // =========================================== WHEN ==============================

    // Realiza un movimiento simple a una casilla
    @When("^(?i)(negro|blanco)\\s+realiza\\s+un\\s+movimiento\\s+simple\\s+a\\s+([A-H][1-8])$")
    public void realizarMovimiento(String turno, String extra){
        posicionFinal = game.transformarCasillaReversa(extra);
        game.realizarMovimiento(posicionInicial[0],posicionInicial[1],posicionFinal[0],posicionFinal[1]);
        game.cambiarDeTurno();
    }

    // Realiza una captura de una ficha rival moviendose a una casilla especifica
    @When("^(?i)(negro|blanco)\\s+captura\\s+([A-H][1-8])\\s+en\\s+([A-H][1-8])$")
    public void realizarCaptura(String turno, String posicionRival, String posicionTemp){
        posicionRival1 = game.transformarCasillaReversa(posicionRival);
        posicionFinal = game.transformarCasillaReversa(posicionTemp);
        game.realizarCaptura(posicionInicial[0],posicionInicial[1],posicionFinal[0],posicionFinal[1]);
    }

    // Tiene una captura disponible luego de mover una pieza. En tal caso, no cambia de turno
    @When("^(?i)tiene\\s+otra\\s+captura\\s+disponible\\s+desde\\s+([A-H][1-8])$")
    public void tieneCaptura(String posFinal){
        boolean seguirJugando=game.tieneMovimientosDeCaptura(turnoActual);
        if(!seguirJugando){game.cambiarDeTurno();}
    }

    // =========================================== THEN ==============================

    //Cambia de turno
    @Then("^(?i)cambia\\s+de\\s+turno$")
    public void cambiaTurno(){
        assertNotEquals(game.getTurno(),turnoActual);
    }

    //NO cambia de turno
    @Then("^(?i)no\\s+cambia\\s+de\\s+turno$")
    public void noCambiaTurno(){
        assertEquals(game.getTurno(),turnoActual);
    }

}
