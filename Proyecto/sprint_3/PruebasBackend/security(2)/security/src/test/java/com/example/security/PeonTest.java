package com.example.security;

import com.example.security.juego.componentes.Dama;
import com.example.security.juego.componentes.Peon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PeonTest {

    @ParameterizedTest
    @CsvSource({
            "3,0,4,1",
            "3,2,4,3"
    })
    public void peonBlancaRealizaMovimientoValido(int fi,int ci,int ff, int cf) {

        //Arrange
        int filaInicial=fi; int columnaInicial=ci;
        int filaFinal=ff; int columnaFinal=cf;
        var peon = new Peon(filaInicial, columnaInicial, "blanco");

        //Act
        var movimiento = peon.movimientoBasicoValido(filaFinal, columnaFinal);

        //Assert
        assertThat(movimiento)
                .isTrue()
                .as("El movimiento deberia de ser valido");
    }

    @ParameterizedTest
    @CsvSource({
            "3,0,2,1",
            "3,2,5,2"
    })
    public void peonBlancaRealizaMovimientoInvalido(int fi,int ci,int ff, int cf) {

        //Arrange
        int filaInicial=fi; int columnaInicial=ci;
        int filaFinal=ff; int columnaFinal=cf;
        var peon = new Peon(filaInicial, columnaInicial, "blanco");

        //Act
        var movimiento = peon.movimientoBasicoValido(filaFinal, columnaFinal);

        //Assert
        assertThat(movimiento)
                .isFalse()
                .as("El movimiento deberia ser invalido");
    }

    @ParameterizedTest
    @CsvSource({
            "6,3,5,2",
            "6,3,5,4",
    })
    public void peonNegroRealizaMovimientoValido(int fi, int ci, int ff, int cf){

        //Arrange
        int filaInicial=fi; int columnaInicial=ci;
        int filaFinal=ff; int columnaFinal=cf;
        var peon = new Peon(filaInicial, columnaInicial, "negro");

        //Act
        var movimiento = peon.movimientoBasicoValido(filaFinal, columnaFinal);

        //Assert
        assertThat(movimiento)
                .isTrue()
                .as("El movimiento deberia de ser valido");

    }

    @ParameterizedTest
    @CsvSource({
            "3,0,3,2",
            "4,3,-2,10"
    })
    public void peonNegroRealizaMovimientoInvalido(int fi, int ci, int ff, int cf) {

        //Arrange
        int filaInicial=fi; int columnaInicial=ci;
        int filaFinal=ff; int columnaFinal=cf;
        var peon = new Peon(filaInicial, columnaInicial, "blanco");

        //Act
        var movimiento = peon.movimientoBasicoValido(filaFinal, columnaFinal);

        //Assert
        assertThat(movimiento)
                .isFalse()
                .as("El movimiento deberia ser invalido");
    }
}