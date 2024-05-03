package com.example.security.juego;

import com.example.security.config.JwtService;
import com.example.security.juego.ResponseClasses.StateGame;
import com.example.security.juego.componentes.Tablero;
import com.example.security.user.User;
import com.example.security.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.swing.plaf.nimbus.State;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ServiceGame {
    /*
    Estas rutas son protegidas, por lo que el contexto de seguridad implementando en JwtAuthenticationFilter
    debe verificar si el token del usuario es valido y el contexto de seguridad de ese usuario, por lo que para
    cada metodo aqui asumimos que el token es valido.
     */

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final UserDetailsService userDetails;

    private ArrayList<Juego> juegos = new ArrayList<>();

    // incia la partida
    public StateGame iniciarPartida(HttpServletRequest request) {
        // establecemos el tablero de juego
        Juego juego;
        StateGame stateGame;
        Tablero tablero = new Tablero();
        tablero.inicializarTableroDefault();
        // tomamos la cabecera y extraemos el nombre de usuario del token
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String name = jwtService.extractUsername(jwt);

        // instanciamos juego y seteamos el nombre de  usuario
        juego = new Juego(tablero);
        juego.setUsername(name);

        // anadimos el juego a una lista de juegos en curso
        juegos.add(juego);

        // devolvemos una instancia del juego inicial
        stateGame = new StateGame("blancas",
                    name,0, "",null,tablero.getTablero());
        return stateGame;
    }

    // devuelve la matriz de juego (tablero) con otros atributos que representa el estado del juego
    public StateGame realizarMovimiento(HttpServletRequest request){

        StateGame stateGame;
        Juego juego;

        // encontramos el juego
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = authorizationHeader.substring(7);
        String name = jwtService.extractUsername(jwt);
        juego = encontrarJuegoPorUsuario(name);

        // si el juego no es nulo (esta en curso)
        if(juego!=null){

            try(InputStream inputStream  = request.getInputStream()){
                byte[] data = inputStream.readAllBytes();
                String bodyContent = new String(data, StandardCharsets.UTF_8);
                JSONObject jsonObject = new JSONObject(bodyContent);
                // tomamos los 2 puntos del tablero en los que tenemos que realizar la jugada
                int filaIni= jsonObject.getInt("filaIni");
                int colIni = jsonObject.getInt("colIni");
                int filaFin= jsonObject.getInt("filaFin");
                int colFin = jsonObject.getInt("colFin");

                // llamamos al metodo hacerJugada implementdo en juego
                stateGame = juego.hacerJugada(filaIni,colIni,filaFin,colFin);

                // si el juego ha terminado eliminamos el juego
                if(juego.getFin()==1){
                    juegos.remove(juego);
                }
                // retormanos el estado del juego
                return stateGame;
            } catch (Exception e) {
                // si hay alguna excepcion retornamos nulo
                return null;
            }
        }
        // si el juego no se encuentra tambien retornamos nulo
        return null;
    }

    // devuelve una matriz con los posibles movimientos validos de una ficha.
    public int[][] obtenerMovimientos(HttpServletRequest request) {

        // encontramos el juego
        String authorizationHeader = request.getHeader("Authorization");
        Juego juego;
        String jwt = authorizationHeader.substring(7);
        String name = jwtService.extractUsername(jwt);
        juego = encontrarJuegoPorUsuario(name);

        if(juego!=null){
            try(InputStream inputStream  = request.getInputStream()){
                byte[] data = inputStream.readAllBytes();
                String bodyContent = new String(data, StandardCharsets.UTF_8);
                JSONObject jsonObject = new JSONObject(bodyContent);
                // tomamos la posicion de la ficha en fila y columna
                int fila = jsonObject.getInt("fila");
                int columna = jsonObject.getInt("columna");

                // verificamos los movimientos simples validos de esa ficha
                return juego.movimientosSimplesValidos(fila,columna);
            } catch (Exception e) {
                return new int[0][0];
            }
        }
        return new int[0][0];
    }

    // encuentra el juego por nombre de usuario
    private Juego encontrarJuegoPorUsuario(String nombreUsuario){
        for(Juego juego:juegos){
            if(juego.getUsername().equals(nombreUsuario)){
                return juego;
            }
        }
        return null;
    }
}