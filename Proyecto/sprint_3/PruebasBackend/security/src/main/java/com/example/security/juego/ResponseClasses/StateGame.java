package com.example.security.juego.ResponseClasses;

import com.example.security.juego.Juego;
import com.example.security.juego.componentes.Casilla;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateGame {

    String turno; // turno del jugador (blancas o negras)
    String username; //
    int termino; // si termina o no el juego
    String ganador; // si termino el juego, quien es el ganador
    int[][]saltos; // los saltos posibles
    Casilla[][]casillas; // tablero a mostrar

    public StateGame(Juego juego,int[][]saltos){
        this.casillas = juego.getTablero().getTablero();
        this.turno = juego.getTurno();
        this.username = juego.getUsername();
        this.termino = juego.getFin();
        this.ganador = (termino==1) ? turno:"";
        this.saltos = saltos;
    }

}
