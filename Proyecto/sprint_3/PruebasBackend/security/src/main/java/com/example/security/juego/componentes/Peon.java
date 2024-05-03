package com.example.security.juego.componentes;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

public class Peon extends Ficha{

    public Peon(int fila,int columna,String player){
        super(fila,columna,player);
    }

    //Se valida si el movimiento esta dentro de los limites del tablero
    @Override
    protected boolean movimientoFueraDeRango(int fila, int columna) {
        return fila<0 || columna<0 || fila>=8 || columna>=8;
    }

    //Se valida si el movimiento es uno de desplazamiento
    @Override
    public boolean movimientoBasicoValido(int f,int c) {

        if(movimientoFueraDeRango(f,c)) return false;

        //EL jugador blanco solo avanza a casillas cuyas filas son mayores que la actual
        if(player.equals("blanco")){
            return fila+1==f && (columna==c+1 || columna==c-1);
        }
        //El jugador negro solo avanza a casillas cuyas filas son menores a la actual
        if(player.equals("negro")){
            return fila-1==f && (columna==c+1 || columna==c-1);
        }
        return false;
    }

    //Verifica si el movimiento que implica moverse mas de una casilla es valido
    @Override
    public boolean saltoValido(int f,int c) {
        if(player.equals("blanco")){
            return fila+2==f && (columna==c+2 || columna==c-2);
        }
        if(player.equals("negro")){
            return fila-2==f && (columna==c+2 || columna==c-2);
        }
        return false;
    }

    /*
    public boolean seCorona(int f){
        if(player.equals("blancas")){
            return f==7;
        }
        if(player.equals("negras")){
            return f==0;
        }
    }*/

}

