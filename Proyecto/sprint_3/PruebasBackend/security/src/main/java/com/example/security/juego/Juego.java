package com.example.security.juego;

import com.example.security.juego.ResponseClasses.StateGame;
import com.example.security.juego.componentes.*;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.plaf.nimbus.State;
import java.util.Arrays;

@Data
public class Juego {

    private Tablero tablero;
    private String turno;
    private String username;
    private int fin;
    private String juego;
    private String ganador;

    //Nombres de los players
    private String blanco = "blanco";
    private String negro = "negro";

    public Juego(Tablero tablero){
        this.tablero = tablero;
        this.turno = blanco;
        ganador = "";
        this.fin = 0;
    }

    //Encuentra una ficha dada su posicion.
    public Ficha encontrarFicha(int fila,int col){
        if(tablero.getCasilla(fila,col)==Casilla.PEONNEGRO){
            return new Peon(fila,col,negro);
        }
        if(tablero.getCasilla(fila,col)==Casilla.PEONBLANCO){
            return new Peon(fila,col,blanco);
        }
        if(tablero.getCasilla(fila,col)==Casilla.REINABLANCA){
            return new Dama(fila,col,blanco);
        }
        if(tablero.getCasilla(fila,col)==Casilla.REINANEGRA){
            return new Dama(fila,col,negro);
        }
        return null;
    }

    //Precondiciones generales para mover las casillas:
    //Que la casilla de destino ue esté dentro del tablero
    //Que la ficha se mueva solo durante el turno del jugador, que solo se mueva a una casilla oscura y vacia
    public boolean preCondicionesParaMovimiento(int filaIni,int colIni,int filaFin,int colFin){
        return tablero.esRangoCorrecto(filaFin, colFin) &&
                tablero.esLaFichaDelJugador(filaIni, colIni, turno) &&
                tablero.estaVacia(filaFin, colFin) && (filaFin+colFin)%2==1;
    }

    //Valida que exista una diagonal libre de piezas entre 2 casillas dadas
    public boolean diagonalLibre(int filaIni,int colIni,int filaFin,int colFin){

        //Se realiza este operador para saber la direccion de la diagonal a comprobar
        int sigi = (filaIni<filaFin) ? 1:-1;
        int sigj = (colIni<colFin) ? 1:-1;
        filaIni += sigi;
        colIni += sigj;
        for(int i=filaIni,j=colIni;i!=filaFin+sigi;i = i+sigi,j=j+sigj){
            if(!tablero.estaVacia(i,j)){
                return false;
            }
        }
        return true;
    }

    //Se valida que existe un movimiento simple (no captura, solo desplazamiento) entre un par de casillas.
    public boolean movimientoSimpleValido(int filaIni,int colIni,int filaFin,int colFin){
        if(!preCondicionesParaMovimiento(filaIni,colIni,filaFin,colFin)){
            return false;
        }
        Ficha ficha = encontrarFicha(filaIni,colIni);

        if(ficha==null){
            return false;
        }
        if(ficha instanceof Peon){
            //Se valida si hay un movimiento de desplazamiento
            return ficha.movimientoBasicoValido(filaFin,colFin);
        }

        if(ficha instanceof Dama){
            //Se valida si hay un movimiento de desplazamiento
            return ficha.saltoValido(filaFin,colFin)
                    && diagonalLibre(filaIni,colIni,filaFin,colFin);
        }
        return false;
    }

    //Se valida si hay una movimiento de captura dadas 2 casillas
    public boolean capturaValida(int filaIni,int colIni,int filaFin,int colFin){
        if(!preCondicionesParaMovimiento(filaIni,colIni,filaFin,colFin)){
            return false;
        }

        Ficha ficha = encontrarFicha(filaIni,colIni);
        if(ficha instanceof Peon){ // si la ficha es un peon

            int i = (filaIni+filaFin)/2;  //verifica la fila intermedia
            int j = (colIni+colFin)/2; // verifica la columna intermedia
            return ficha.saltoValido(filaFin,colFin)
                    && tablero.fichaDelOponente(i,j,turno); // verifica si se encuentra una ficha del opoenente

        }
        if(ficha instanceof Dama){

            int sigi = (filaIni<filaFin) ? -1:1;
            int sigj = (colIni<colFin) ? -1:1;
            int filx = filaFin+2*sigi;
            int colx = colFin+2*sigj;
            return tablero.esRangoCorrecto(filx,colx) && diagonalLibre(filaIni,colIni,filx,colx)
                    && tablero.fichaDelOponente(filaFin+sigi,colFin+sigj,turno);
        }
        return false;
    }

    //Lista los movimientos simples validos para una casilla en especifico
    public int[][] movimientosSimplesValidos(int filaIni,int colIni){

        int[][] matrizMovimientos = new int[14][4];
        int contador = 0;
        if(tablero.getCasilla(filaIni,colIni)==Casilla.VACIA){
            return null;
        }
        Ficha ficha = encontrarFicha(filaIni,colIni);
        int[][] direcciones = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        if(ficha==null){
            return null;
        }
        for(int variacion=1;variacion<8;variacion++){
            for(int[]direccion:direcciones){
                int filaNueva = filaIni + direccion[0]*variacion;
                int colNueva = colIni + direccion[1]*variacion;
                if(tablero.esRangoCorrecto(filaNueva,colNueva)
                        && movimientoSimpleValido(filaIni,colIni,filaNueva,colNueva)){
                    matrizMovimientos[contador][0] = filaIni;
                    matrizMovimientos[contador][1] = colIni;
                    matrizMovimientos[contador][2] = filaNueva;
                    matrizMovimientos[contador][3] = colNueva;
                    contador += 1;
                }
            }
        }
        if(contador==0){
            return null;
        }
        return matrizMovimientos;
    }

    //Lista los movimientos de captura validos para una casilla en especifico
    public int[][] movimientosCapturaValidos(int filaIni,int colIni){
        int[][] matrizMovimientos = new int[14][4];
        int contador = 0;
        if(tablero.getCasilla(filaIni,colIni)==Casilla.VACIA){
            return null;
        }

        Ficha ficha = encontrarFicha(filaIni,colIni);
        int[][] direcciones = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        if(ficha==null){
            return null;
        }
        for(int variacion=1;variacion<8;variacion++){
            for(int[]direccion:direcciones){
                int filaNueva = filaIni + direccion[0]*variacion;
                int colNueva = colIni + direccion[1]*variacion;
                if(tablero.esRangoCorrecto(filaNueva,colNueva)
                        && capturaValida(filaIni,colIni,filaNueva,colNueva)){
                    matrizMovimientos[contador][0] = filaIni;
                    matrizMovimientos[contador][1] = colIni;
                    matrizMovimientos[contador][2] = filaNueva;
                    matrizMovimientos[contador][3] = colNueva;
                    contador +=1;
                }
            }
        }
        if(contador==0){
            return null;
        }
        return matrizMovimientos;
    }

    //Lista los movimientos simples validos para un jugador
    public int[][] todosLosMovimientosSimplesValidos(String player){
        int[][] movimientoSimples = new int[14][4];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if((i+j)%2==1 && tablero.esLaFichaDelJugador(i,j,player)){
                    int[][] matriz = movimientosSimplesValidos(i,j);
                    if(matriz!=null){
                        movimientoSimples = concatenarMatrices(movimientoSimples,matriz);
                    }
                }
            }
        }
        return movimientoSimples;
    }

    //encuentra todos los movimientos de captura validos para el jugador = player
    public int[][] todosLosMovimientosDeCapturaValidos(String player){
        int[][] movimientosDeCaptura = new int[14][4];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if((i+j)%2==1 && tablero.esLaFichaDelJugador(i,j,player)){
                    int[][] matriz = movimientosCapturaValidos(i,j);
                    if(matriz!=null){
                        movimientosDeCaptura = concatenarMatrices(movimientosDeCaptura,matriz);
                    }
                }
            }
        }
        return movimientosDeCaptura;
    }

    //Valida si existen movimientos simples para un jugador
    public boolean tieneMovimientosSimples(String player){
        int[][] arr = todosLosMovimientosSimplesValidos(player);
        return arr[0][0] != arr[0][1];
    }

    //Valida si existen movimientos de captura para un jugador
    public boolean tieneMovimientosDeCaptura(String player){
        int[][] arr = todosLosMovimientosDeCapturaValidos(player);
        return arr[0][0] != arr[0][1];
    }

    //Valida si el jugador tiene movimientos validos disponibles
    public boolean tieneMovimientosValidos(String player){
        return tieneMovimientosSimples(player) || tieneMovimientosDeCaptura(player);
    }

    //realiz captura , retorna true si la hace false sino
    public boolean realizarCaptura(int filaIni,int colIni,int filaFin,int colFin){

        if(capturaValida(filaIni,colIni,filaFin,colFin)){

            //Encuentra el tipo de pieza que está en la casilla inicial.
            //Luego verifica la direccion del movimiento
            Casilla ficha = tablero.getCasilla(filaIni,colIni);
            int sigi = (filaIni<filaFin) ? -1:1;
            int sigj = (colIni<colFin) ? -1:1;
            int filx = filaFin + sigi;
            int colx = colFin + sigj;

            //En caso se llegue a una fila límite, se corona el peon a dama
            if(ficha==Casilla.PEONBLANCO && filaFin==7){
                ficha = Casilla.REINABLANCA;
            }
            if(ficha==Casilla.PEONNEGRO && filaFin==0){
                ficha = Casilla.REINANEGRA;
            }

            tablero.setCasilla(filaIni,colIni,Casilla.VACIA); // la celda queda vacia
            tablero.setCasilla(filaFin,colFin,ficha); // salto de la ficha
            tablero.setCasilla(filx,colx,Casilla.VACIA); // se elimina la ficha comida
            juego = transformarCasilla(filaIni,colIni)+" "+transformarCasilla(filaFin,colFin);
            return true;
        }
        return false;
    }

    //Realiza el movimiento simple, retorna true si la hace false sino
    public boolean realizarMovimiento(int filaIni,int colIni,int filaFin,int colFin){

        if(movimientoSimpleValido(filaIni,colIni,filaFin,colFin)){
            // toma la casilla en esa posicion
            Casilla ficha = tablero.getCasilla(filaIni,colIni);

            // verifica si es un peon que va a coronar
            if(ficha==Casilla.PEONBLANCO && filaFin==7){
                ficha = Casilla.REINABLANCA;
            }
            if(ficha==Casilla.PEONNEGRO && filaFin==0){
                ficha = Casilla.REINANEGRA;
            }

            // setea los cambios del movimiento
            tablero.setCasilla(filaIni,colIni,Casilla.VACIA);
            tablero.setCasilla(filaFin,colFin,ficha);
            juego = transformarCasilla(filaIni,colIni)+" "+transformarCasilla(filaFin,colFin);

            // retorna true para confirmar los cambios
            return true;
        }

        // retorna false sino se dieron los cambios
        return false;
    }

    //Cambia de turno.
    public void cambiarDeTurno(){
        turno = (turno.equals(blanco))?negro:blanco;
    }

    //Realiza una jugada.
    public StateGame hacerJugada(int filaIni, int colIni, int filaFin, int colFin){

        // instancia un StateGame
        StateGame stateGame = new StateGame();

        // inicializamos la cantidad de saltos
        int[][] saltos = new int[0][0];

        if(tieneMovimientosDeCaptura(turno)){ // verificamos si el player=turno tiene movimientos de captura

            // verificamos si el movimiento hecho es de captura o no
            boolean realizoCaptura = realizarCaptura(filaIni,colIni,filaFin,colFin);

            // si no es de captura sale de la funcion y retorna lo mismo
            if(realizoCaptura && !tieneMovimientosDeCaptura(turno)){
                // si realizo la captura y el jugador no tiene mas movimientos de captura cambia de turno
                cambiarDeTurno();
            }
        }
        else{
            // si no tiene movimientos de captura entonces verifica si es un movimiento valido
            boolean realizoMovimiento = realizarMovimiento(filaIni,colIni,filaFin,colFin);
            if(realizoMovimiento){
                // si es un movimiento valido cambia de turno
                cambiarDeTurno();
            }
        }

        // se verifica una segunda vez para poder enviar una matriz con las casillas sombreadas al frontend y muestre al usuario
        // que debe comer obligatoriamente
        if(tieneMovimientosDeCaptura(turno)){ // y ahora se verifica que el otro jugador tenga movimientos de captura
            saltos = todosLosMovimientosDeCapturaValidos(turno);
        }
        else{
            if(!tieneMovimientosSimples(turno)){
                // si no tiene movimientos simples, retorna fin
                fin = 1;
                stateGame.setGanador(turno);
                ganador = turno;
            }
        }

        //
        return new StateGame(this,saltos);
    }

    // =================================== Funciones de apoyo ==============================

    // toma una fila y una columna de mi matriz y la transforma a la notacion en ajedrez
    public String transformarCasilla(int fila,int columna){
        String casilla = "";
        String[]columnas = {"A","B","C","D","E","F","G","H"};
        for(int i=0;i<8;i++){
            if(i==columna){
                casilla = casilla + columnas[i];
                break;
            }
        }
        casilla = casilla + String.valueOf(fila);
        return casilla;
    }

    // toma una fila y una notacion de ajedrez y la transforma a una fila y una columna de mi matriz
    public int[] transformarCasillaReversa(String notacion){
        String casilla = "";
        int[] posicion = new int[2];
        char[] columnas = {'A','B','C','D','E','F','G','H'};
        for(int i=0;i<8;i++){
            if(notacion.charAt(0)==columnas[i]){
                posicion[1] = i;
                break;
            }
        }
        posicion[0] = Character.getNumericValue(notacion.charAt(1));
        return posicion;
    }

    public int[][] concatenarMatrices(int[][] matriz1, int[][] matriz2) {
        int filasValidas = contarFilasValidas(matriz1) + contarFilasValidas(matriz2);
        int columnas = matriz1[0].length;
        int[][] matrizConcatenada = new int[filasValidas][columnas];
        int filaActual = 0;
        for (int[] fila : matriz1) {
            if (fila[0] != 0 || fila[1] != 0) {
                System.arraycopy(fila, 0, matrizConcatenada[filaActual++], 0, columnas);
            }
        }
        for (int[] fila : matriz2) {
            if (fila[0] != 0 || fila[1] != 0) {
                System.arraycopy(fila, 0, matrizConcatenada[filaActual++], 0, columnas);
            }
        }
        return matrizConcatenada;
    }

    private static int contarFilasValidas(int[][] matriz) {
        int contador = 0;
        for (int[] fila : matriz) {
            if (fila[0] != 0 || fila[1] != 0) {
                contador++;
            }
        }
        return contador;
    }

}

