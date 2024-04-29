package com.example.security.juego.componentes;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.parameters.P;

public class Dama extends Ficha{

    public Dama(int fila,int col,String player){
        super(fila,col,player);
    }

    //Se valida si el movimiento esta dentro de los limites del tablero
    @Override
    protected boolean movimientoFueraDeRango(int fila, int columna) {
        return fila<0 || columna<0 || fila>=8 || columna>=8;
    }

    //Se valida si el movimiento es de desplazamiento y si es valido
    public boolean movimientoBasicoValido(int f,int c) {
        if(movimientoFueraDeRango(f,c)) return false;
        if(fila==f || columna==c){
            return false;
        }
        return Math.abs(fila-f)==Math.abs(columna-c);
    }

    //Se valida si el movimiento que implica moverse muchas casillas es valido
    @Override
    public boolean saltoValido(int fila,int columna) {
        return movimientoBasicoValido(fila,columna);
    }
}
