package com.example.security;

import com.example.security.juego.componentes.Peon;
import com.example.security.juego.componentes.Tablero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.security.juego.componentes.Casilla;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TableroTest {

    private Tablero tablero;
    @BeforeEach
    public void setUp(){tablero = new Tablero();}


    void inicializarTableroDefaultTest(int indFil, int indCol){

        //Arrange

        var casillas=tablero.getTablero();

        //Act
        tablero.inicializarTableroDefault();

        //Assert
        assertEquals(Casilla.PEONBLANCO, casillas[2][3]);
        assertEquals(Casilla.PEONNEGRO, casillas[5][4]);
        assertEquals(Casilla.VACIA, casillas[4][1]);
        assertEquals(Casilla.VACIA, casillas[2][2]); //Casilla de color claro

    }

    @ParameterizedTest
    @CsvSource({
            "2,0",
            "0,3",
            "2,7",
    })
    void inicializarTableroVacioTest(int indFil, int indCol){
        //Assert
        assertEquals(Casilla.VACIA, tablero.getCasilla(indFil,indCol));
    }


    @ParameterizedTest
    @CsvSource({"-1,2","1,-1","-2,-2","10,2"})
    void setCasillaInvalidTest(int indFil, int indCol){
        //Assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()->tablero.setCasilla(indFil,indCol,Casilla.PEONBLANCO));
    }

    @ParameterizedTest
    @CsvSource({"0,0,PEONBLANCO", "1,1,PEONNEGRO", "2,2,VACIA"})
    void setCasillaValidTest(int indFil, int indCol, Casilla casilla){

        //Arrange
        tablero.setCasilla(indFil, indCol, casilla);

        //Assert
        assertEquals(casilla, tablero.getCasilla(indFil, indCol));

    }

    @ParameterizedTest
    @CsvSource({"1,2","2,3","1,5"})
    void quitarCasillaValidTest(int indFil, int indCol){

        //Arrange
        tablero.setCasilla(indFil, indCol, Casilla.PEONBLANCO);

        //Act
        tablero.quitarCasilla(indFil, indCol);

        //Assert
        assertEquals(Casilla.VACIA, tablero.getCasilla(indFil, indCol));

    }

    @Test
    void quitarCasillaInvalidTest(){

        //Assert
        assertThrows(IllegalArgumentException.class, ()->tablero.quitarCasilla(0,0));
        assertThrows(IllegalArgumentException.class, ()->tablero.quitarCasilla(10,-2));
    }

}