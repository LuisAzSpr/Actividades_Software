package com.example.security;


import com.example.security.juego.Juego;
import com.example.security.juego.componentes.Casilla;
import com.example.security.juego.componentes.Tablero;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.Before;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Juego juego;
    int[] posicionIni;
    int[] posicionFin;
    int[] posicionExtra;

    String turno1;

    Casilla cIni,cFin;

    @Before
    public void setUp(){
        posicionIni = new int[2];
        posicionFin = new int[2];
        posicionExtra = new int[2];
        turno1 = "";
    }

    // =========================================== GIVEN ==============================

    // iniciacion de juego
    @Given("^(?i)hay\\s+un\\s+tablero\\s+en\\s+juego$")
    public void tableroEnJuego(){
        Tablero tablero = new Tablero();
        juego = new Juego(tablero);
    }

    // Setteo de turno
    @Given("^(?i)es\\s+turno\\s+de\\s+(negro|blanco)$")
    public void cambiarTurno(String turno){
        turno1 = turno;
        juego.setTurno(turno);
    }

    // agregar ficha inicial del jugador=turno
    @Given("^(?i)una?\\s+(peon|dama)\\s+del\\s+jugador\\s+en\\s+la\\s+casilla\\s+" +
            "([A-H][1-8])$")
    public void agregarFichaPropia(String ficha,String posicionInicial){
        if(Objects.equals(ficha,"dama")){ficha="reina";}
        posicionIni = juego.transformarCasillaReversa(posicionInicial);
        Casilla fichaInsertar = obtenerCasilla(ficha,turno1);
        cIni = fichaInsertar;
        juego.getTablero().setCasilla(posicionIni[0], posicionIni[1],fichaInsertar);
    }


    @Given("^(?i)(?:en)?\\s*la\\s+casilla\\s+([A-F][1-8])\\s+esta\\s+ocupada")
    public void agregarCasillaVacia(String posicionFinal){
        posicionFin = juego.transformarCasillaReversa(posicionFinal);
        juego.getTablero().setCasilla(posicionFin[0], posicionFin[1],Casilla.VACIA);
    }

    // agrega una ficha cualquiera
    @Given("^(?i)una\\s+ficha\\s+cualquiera\\s+en\\s+la\\s+casilla\\s+([A-H][1-8])$")
    public void setCualquierFicha(String posicionFicha){
        posicionExtra = juego.transformarCasillaReversa(posicionFicha);
        cFin = Casilla.PEONNEGRO;
        juego.getTablero().setCasilla(posicionExtra[0],posicionExtra[1],cFin);
    }

    // agrega una ficha rival
    @Given("^(?i)una\\s+ficha\\s+rival\\s+en\\s+([A-H][1-8])$")
    public void setFichaRival(String posicionExt){
        posicionExtra= juego.transformarCasillaReversa(posicionExt);
        Casilla rival = (Objects.equals(turno1, "blanco")) ? Casilla.PEONNEGRO:Casilla.PEONBLANCO;
        juego.getTablero().setCasilla(posicionExtra[0],posicionExtra[1],rival);
    }

    // setea la casilla final vacia
    @Given("^(?i)la\\s+casilla\\s+([A-H][1-8])\\s+esta\\s+vacia$")
    public void setCasillaVacia(String posicionFinal){
        posicionFin = juego.transformarCasillaReversa(posicionFinal);
        juego.getTablero().setCasilla(posicionFin[0],posicionFin[1],Casilla.VACIA);
    }

    // =========================================== WHEN ==============================

    // Realizar movimiento simple con con peon o dama
    @When("^(?i)el\\s+jugador\\s+mueve\\s+su\\s+ficha\\s+a\\s+la\\s+casilla\\s+(?:vacia|ocupada)$")
    public void jugadorMueveLaFicha(){
        boolean seRealizo = juego.realizarMovimiento(posicionIni[0],posicionIni[1],posicionFin[0],posicionFin[1]);
        if(seRealizo){juego.cambiarDeTurno();}
    }

    // Realizar captura con peon o dama
    @When("^(?i)el\\s+jugador\\s+captura\\s+con\\s+(?:peon|dama)$")
    public void jugadorComeFicha(){
        //La funcion no maneja bien damas
        //juego.realizarCaptura(posicionIni[0],posicionIni[1],posicionFin[0],posicionFin[1]);
    }

    @When("^(?i)(negro|blanco)\\s+no\\s+tiene\\s+movimientos\\s+disponibles$")
    public void setGanador(String turno){
        String rival = (Objects.equals(turno,"blanco")) ? "negro":"blanco";
        boolean fin=juego.tieneMovimientosValidos(turno);
        if(!fin){juego.setGanador(rival);}
    }
    // =========================================== THEN ==============================

    // actualiza el tablero
    @Then("^(?i)la\\s+ficha\\s+del\\s+jugador\\s+se\\s+mueve\\s+a\\s+la\\s+nueva" +
            "\\s+posicion\\s+en\\s+el\\s+tablero$")
    public void actualizacionDelTablero(){
        assertEquals(Casilla.VACIA, juego.getTablero().getCasilla(posicionIni[0], posicionIni[1]),"La casilla esta vacia");
        assertEquals(juego.getTablero().getCasilla(posicionFin[0],posicionFin[1]),cIni);
    }


    // no actualiza el tablero ( se queda tal cual esta)
    @Then("^(?i)la\\s+ficha\\s+del\\s+jugador\\s+no\\s+se\\s+mueve\\s+a\\s+la\\s+nueva" +
            "\\s+posicion\\s+en\\s+el\\s+tablero$")
    public void mantenerTablero(){
        // se mantiene en la posicion inicial
        assertEquals(cIni, juego.getTablero().getCasilla(posicionIni[0], posicionIni[1]),"");
        // la ficha final tambien se mantiene  en la posicion final
        assertEquals(cFin,juego.getTablero().getCasilla(posicionExtra[0],posicionExtra[1]));
    }

    //Cambia de turno
    @Then("^(?i)el\\s+turno\\s+cambia$")
    public void actualizaTurno(){
        assertNotEquals(juego.getTurno(),turno1);
    }

    //No Cambia de turno
    @Then("^(?i)el\\s+turno\\s+no\\s+cambia$")
    public  void noActualizaTurno(){assertEquals(juego.getTurno(),turno1);}

    // Se retira ficha
    @Then("^(?i)la\\s+ficha\\s+rival\\s+se\\s+retira\\s+del\\s+tablero$")
    public void seRetiraFicha(){
        assertEquals(Casilla.VACIA,juego.getTablero().getCasilla(posicionExtra[0],posicionExtra[1]));
    }

    // Confirmar ganador
    @Then("^(?i)el\\s+juego\\s+termina\\s+y\\s+(blanco|negro)\\s+es\\s+el\\s+ganador$")
    public void ganadorEs(String turno){
        assertEquals(juego.getGanador(),turno);
    }






    public static Casilla obtenerCasilla(String ficha, String jugador) {
        Casilla casilla = null;
        if(ficha.equals("peon")){
            if(jugador.equals("blanco")){
                casilla = Casilla.PEONBLANCO;
            }
            if(jugador.equals("negro")){
                casilla = Casilla.PEONNEGRO;
            }
        }
        if(ficha.equals("reina")){
            if(jugador.equals("blanco")){
                casilla = Casilla.REINABLANCA;
            }
            if(jugador.equals("negro")){
                casilla = Casilla.REINANEGRA;
            }
        }
        return casilla;
    }

}
