package com.example.security.juego.componentes;


import lombok.Data;

import java.util.Objects;

@Data
public class Tablero {

    final int FILAS=8;
    final int COLUMNAS=8;
    private String juego;
    private Casilla[][] tablero;
    private String blanco = "blanco";
    private String negro = "negro";


    // COnstructor que inicializa el tablero con todas sus casillas vacias
    public Tablero(){
        this.tablero = new Casilla[FILAS][COLUMNAS];
        inicializarTableroVacio();
    }
    private void inicializarTableroVacio(){
        for(int i=0; i<FILAS; i++){
            for(int j=0; j<COLUMNAS; j++){
                tablero[i][j] = Casilla.VACIA;
            }
        }
    }
    public void inicializarTableroDefault(){
        for(int i=0; i<FILAS; i++){
            for(int j=0; j<COLUMNAS; j++){
                if((i + j) % 2 == 1){
                    if(i < 3){
                        tablero[i][j] = Casilla.PEONBLANCO;
                    }else if(i > 4){
                        tablero[i][j] = Casilla.PEONNEGRO;
                    }else{
                        tablero[i][j] = Casilla.VACIA;
                    }
                }else {
                    tablero[i][j] = Casilla.VACIA;
                }
            }
        }
    }

    // settea una casilla del tablero
    public void setCasilla(int fila, int columna, Casilla casilla){

        if(esRangoCorrecto(fila,columna)){
            tablero[fila][columna] = casilla;
        }else{
            throw new IllegalArgumentException("El rango no es correcto o la casilla no esta vacia");
        }
    }

    // quita una casilla de tablero
    public void quitarCasilla(int fila, int columna){
        if(esRangoCorrecto(fila,columna) && !estaVacia(fila,columna)){
            tablero[fila][columna] = Casilla.VACIA;
        }else {
            throw new IllegalArgumentException("El rango no es correcto o la casilla ya esta vacia");
        }
    }

    // Verifica si el rango de la fila y columna es la correcta
    public boolean esRangoCorrecto(int filaIni,int colIni){
        return filaIni<8 && filaIni>=0 && colIni>=0 && colIni<8;
    }


    // Verifica si la casilla en una determinada posicion esta vacia o es una ficha de un determinado jugador
    public boolean esLaFichaDelJugador(int fila,int columna,String player){
        if(player.equals(blanco)){
            if (tablero[fila][columna] == Casilla.PEONBLANCO ||
                    tablero[fila][columna] == Casilla.REINABLANCA) {
                return true;
            }
        }
        if(player.equals(negro)){
            if (tablero[fila][columna] == Casilla.PEONNEGRO ||
                    tablero[fila][columna] == Casilla.REINANEGRA) {
                return true;
            }
        }
        return false;
    }

    // verifica si es la ficha del oponente en la posicion dada
    public boolean fichaDelOponente(int i,int j,String player){
        if(player.equals(blanco)){
            return esLaFichaDelJugador(i,j,negro);
        }
        if(player.equals(negro)){
            return esLaFichaDelJugador(i,j,blanco);
        }
        return false;
    }

    // verifica si la casilla especificada es vacia
    public boolean estaVacia(int fila,int col){
        return tablero[fila][col]==Casilla.VACIA;
    }

    // devolvemos el tipo de casilla
    public Casilla getCasilla(int fila,int col){
        return tablero[fila][col];
    }



}
