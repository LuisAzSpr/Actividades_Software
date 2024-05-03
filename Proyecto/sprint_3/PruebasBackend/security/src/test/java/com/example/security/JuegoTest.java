package com.example.security;

import com.example.security.juego.Juego;
import com.example.security.juego.componentes.Casilla;
import com.example.security.juego.componentes.Dama;
import com.example.security.juego.componentes.Peon;
import com.example.security.juego.componentes.Tablero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class JuegoTest {

    private Juego juego;
    private Tablero tablero;

    @BeforeEach
    public void setUp() {
        tablero = new Tablero(); //inicializamos un tablero vacio
        juego = new Juego(tablero);
    }

    @ParameterizedTest
    @CsvSource({
            "2,1",
            "1,4",
    })
    public void encontrarUnPeonBlancoEnElTablero(int f, int c) {

        //Arrange
        int fila = f;
        int columna = c;
        tablero.setCasilla(fila, columna, Casilla.PEONBLANCO);

        //Act
        var ficha = juego.encontrarFicha(fila, columna);

        //Assert
        assertThat(ficha.getPlayer().equals("blanco") && (ficha instanceof Peon))
                .isTrue()
                .as("La ficha deberia ser un peon blanco");
    }

    @ParameterizedTest
    @CsvSource({
            "1,6",
            "6,5"
    })
    public void encontrarUnPeonNegroEnElTablero(int f, int c) {

        //Arrange
        int fila = f;
        int columna = c;
        tablero.setCasilla(fila, columna, Casilla.PEONNEGRO);

        //Act
        var ficha = juego.encontrarFicha(fila, columna);

        //Assert
        assertThat(ficha.getPlayer().equals("negro") && (ficha instanceof Peon))
                .isTrue()
                .as("La ficha deberia ser un peon negro");
    }

    @ParameterizedTest
    @CsvSource({
            "2,1",
            "1,4",
    })
    public void encontrarUnaReinaBlancaEnElTablero(int f, int c) {
        //Arrange
        int fila = f;
        int columna = c;
        tablero.setCasilla(fila, columna, Casilla.REINABLANCA);

        //Act
        var ficha = juego.encontrarFicha(fila, columna);

        //Assert
        assertThat(ficha.getPlayer().equals("blanco") && (ficha instanceof Dama))
                .isTrue()
                .as("La ficha deberia ser una dama blanca");
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,5,5",
            "0,3,3,0"
    })
    public void diagonalLibreTest(int fi, int ci, int ff, int cf) {
        //Arrange
        int filalInicial = fi;
        int columnaInicial = ci;
        int filaFinal = ff;
        int columnaFinal = cf;

        //Act
        boolean value=juego.diagonalLibre(filalInicial, columnaInicial, filaFinal, columnaFinal);

        //Assert
        assertThat(value).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,5,5,3,3",
            "4,1,1,5,2,3"
    })
    public void diagonalLibreInvalidTest(int fi, int ci, int ff, int cf, int fr, int cr) {
        //Arrange
        int filalInicial = fi;
        int columnaInicial = ci;
        int filaFinal = ff;
        int columnaFinal = cf;
        int filaRival = fr;
        int columnaRival = cr;
        tablero.setCasilla(filaRival, columnaRival, Casilla.PEONNEGRO);

        //Act
        boolean value=juego.diagonalLibre(filalInicial, columnaInicial, filaFinal, columnaFinal);

        //Assert
        assertThat(value).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,2,3",
            "4,1,5,0"
    })
    public void movimientoSimplePeonTest(int filIni, int colIni, int filFin, int colFin) {
        //Arrange
        tablero.setCasilla(filIni, colIni, Casilla.PEONBLANCO);

        //Act
        boolean value=juego.movimientoSimpleValido(filIni, colIni, filFin, colFin);

        //Assert
        assertThat(value).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "1,0,2,1",
            "5,2,2,5"
    })
    public void movimientoSimpleDamaTest(int filIni, int colIni, int filFin, int colFin) {
        //Arrange
        tablero.setCasilla(filIni, colIni, Casilla.REINABLANCA);

        //Act
        boolean value=juego.movimientoSimpleValido(filIni, colIni, filFin, colFin);

        //Assert
        assertThat(value).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3,0,2,1",
            "5,2,7,4,6,3"
    })
    public void capturaValidaPeonTest(int filIni, int colIni, int filFin, int colFin, int filRival, int colRival) {
        //Arrange
        tablero.setCasilla(filIni, colIni, Casilla.PEONBLANCO);
        tablero.setCasilla(filRival, colRival, Casilla.PEONNEGRO);

        //Act
        boolean value=juego.capturaValida(filIni, colIni, filFin, colFin);

        //Assert
        assertThat(value).isTrue();

    }

    @ParameterizedTest
    @CsvSource({
            "0,1,3,4,2,3",
            "4,1,1,4,2,3"
    })
    public void capturaValidaDamaTest(int filIni, int colIni, int filFin, int colFin, int filExtra, int colExtra) {
        //Arrange
        tablero.setCasilla(filIni, colIni, Casilla.REINABLANCA);
        tablero.setCasilla(filExtra, colExtra, Casilla.PEONNEGRO);

        //Act
        boolean value=juego.capturaValida(filIni, colIni, filFin, colFin);

        //Assert
        assertThat(value).isTrue();

    }

    @Test
    public void TodosMovimientosSimplesValidosTest() {

        //Arrange
        tablero.setCasilla(4, 5, Casilla.PEONBLANCO);
        tablero.setCasilla(3, 2, Casilla.PEONBLANCO);
        int[][] expectedMovBlanco = {{3, 2, 4, 1}, {3, 2, 4, 3}, {4, 5, 5, 4}, {4, 5, 5, 6}};

        //Act
        int[][] matrizMovSimplesBlanco = juego.todosLosMovimientosSimplesValidos("blanco");

        //Assert
        assertArrayEquals(expectedMovBlanco, matrizMovSimplesBlanco);
    }
    @Test
    public void TodosMovimientosCapturaValidosTest(){

        //Arrange
        tablero.setCasilla(4, 5, Casilla.PEONBLANCO);
        tablero.setCasilla(5, 4, Casilla.PEONNEGRO);
        tablero.setCasilla(5, 6, Casilla.PEONNEGRO);
        int[][] expectedMovBlanco = {{4, 5, 6, 3}, {4, 5, 6, 7}};

        //Act
        int[][] matrizMovCapturaBlanco = juego.todosLosMovimientosDeCapturaValidos("blanco");

        //Assert
        assertArrayEquals(expectedMovBlanco,matrizMovCapturaBlanco);
    }





}