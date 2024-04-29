package com.example.security.juego.componentes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

@Data
public abstract class Ficha {

    protected int fila;
    protected int columna;
    protected String player;

    //Valida que la casilla no salga de los limites del tablero
    protected Ficha(int fila,int columna,String player){
        if((fila<0 || fila>=8 || columna>=8 || columna<0) &&
                !Objects.equals(player, "blanco") && !"negro".equals(player)){
            throw new IllegalArgumentException("error en los argumentos");
        }
        this.fila = fila;
        this.columna = columna;
        this.player = player;
    }

    //Valida que el movimiento termine en una casilla dentro del tablero
    protected abstract boolean movimientoFueraDeRango(int fila, int columna);

    //Verifica que el movimiento simple de desplazamiento sea valido
    public abstract boolean movimientoBasicoValido(int fila,int columna);

    //Verifica que el movimiento que implica mas de 1 casilla sea valido
    public abstract boolean saltoValido(int fila,int columna);

}
