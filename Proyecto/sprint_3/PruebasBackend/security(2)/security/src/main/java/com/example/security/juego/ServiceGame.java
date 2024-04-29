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
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ServiceGame {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final UserDetailsService userDetails;

    private final PartidaRepository partidaRepository;

    private ArrayList<Juego> juegos = new ArrayList<>();

    public StateGame iniciarPartida(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String jwt,name;
        Tablero tablero = new Tablero();
        tablero.inicializarTableroDefault();
        Juego juego;
        StateGame stateGame;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            name = jwtService.extractUsername(jwt);
            if(name==null){
                return null;
            }
            if(jwtService.isTokenValid(jwt,userDetails.loadUserByUsername(name))){
                juego = new Juego(tablero);
                juego.setUsername(name);
                juegos.add(juego);
                stateGame = new StateGame("blancas",
                        name,0, "",null,tablero.getTablero());
                return stateGame;
            }

        }
        return null;
    }

    public StateGame realizarMovimiento(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        Juego juego;
        String jwt,name;
        StateGame stateGame;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            name = jwtService.extractUsername(jwt);
            if (name == null) {
                return null;
            }
            if(jwtService.isTokenValid(jwt,userDetails.loadUserByUsername(name))){
                juego = encontrarJuegoPorUsuario(name);
                if(juego!=null){
                    try(InputStream inputStream  = request.getInputStream()){
                        byte[] data = inputStream.readAllBytes();
                        String bodyContent = new String(data, StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(bodyContent);
                        int filaIni= jsonObject.getInt("filaIni");
                        int colIni = jsonObject.getInt("colIni");
                        int filaFin= jsonObject.getInt("filaFin");
                        int colFin = jsonObject.getInt("colFin");
                        stateGame = juego.hacerJugada(filaIni,colIni,filaFin,colFin);

                        if(juego.getFin()==1){
                            Partida partida = Partida.builder()
                                            .juego(juego.getJuego())
                                            .username(name)
                                            .fecha(new Date())
                                            .build();
                            juegos.remove(juego);
                            partidaRepository.save(partida);
                        }
                        return stateGame;
                    } catch (Exception e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
    public int[][] obtenerMovimientos(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        Juego juego;
        String jwt,name;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            name = jwtService.extractUsername(jwt);
            if (name == null) {
                return null;
            }
            if(jwtService.isTokenValid(jwt,userDetails.loadUserByUsername(name))){
                juego = encontrarJuegoPorUsuario(name);
                if(juego!=null){
                    try(InputStream inputStream  = request.getInputStream()){
                        byte[] data = inputStream.readAllBytes();
                        String bodyContent = new String(data, StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(bodyContent);
                        int fila = jsonObject.getInt("fila");
                        int columna = jsonObject.getInt("columna");

                        return juego.movimientosSimplesValidos(fila,columna);
                    } catch (Exception e) {
                        return new int[0][0];
                    }
                }
            }
        }
        return new int[0][0];
    }

    private Juego encontrarJuegoPorUsuario(String nombreUsuario){
        for(Juego juego:juegos){
            if(juego.getUsername().equals(nombreUsuario)){
                return juego;
            }
        }
        return null;
    }
}