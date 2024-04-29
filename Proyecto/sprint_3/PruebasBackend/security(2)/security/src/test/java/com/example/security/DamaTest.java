package com.example.security;

import com.example.security.juego.componentes.Dama;
import com.example.security.juego.componentes.Peon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class DamaTest {

    @ParameterizedTest
    @CsvSource({
            "2,3,6,7",
            "0,3,3,6"
    })
    public void damaRealizaUnMovimientoValido(int fi, int ci,int ff, int cf){

        //Arrange
        int filaInicial=fi; int columnaInicial=ci;
        int filaFinal=ff; int columnaFinal=cf;
        var dama = new Dama(filaInicial, columnaInicial, "blanco");

        //Act
        var movimiento = dama.movimientoBasicoValido(filaFinal, columnaFinal);

        //Assert
        assertThat(movimiento)
                .isTrue()
                .as("El movimiento deberia de ser valido");
    }

    @ParameterizedTest
    @CsvSource({
            "2,0,0,3",
            "2,7,7,3"
    })
    public void damaRealizaUnMovimientoInvalido(int fi, int ci,int ff, int cf){

        //Arrange
        int filaInicial=fi; int columnaInicial=ci;
        int filaFinal=ff; int columnaFinal=cf;
        var dama = new Dama(filaInicial, columnaInicial, "blanco");

        //Act
        var movimiento = dama.movimientoBasicoValido(filaFinal, columnaFinal);

        //Assert
        assertThat(movimiento)
                .isFalse()
                .as("El movimiento deberia de ser invalido");
    }

}