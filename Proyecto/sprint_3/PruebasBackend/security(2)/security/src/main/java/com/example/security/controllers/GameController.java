package com.example.security.controllers;


import com.example.security.juego.ResponseClasses.StateGame;
import com.example.security.juego.ServiceGame;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//endpoints para el juego
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game-controller")
public class GameController {

    private final ServiceGame serviceGame;

    // inicia una nueva partida
    @PostMapping("/iniciarPartida")
    public ResponseEntity<StateGame> iniciarPartida(HttpServletRequest request){
        return ResponseEntity.ok(serviceGame.iniciarPartida(request));
    }

    // obtiene los posibles movimientos para que se sombreen en el frotend
    @GetMapping("/obtenerMovimientos")
    public ResponseEntity<int[][]> obtenerMovimientos(HttpServletRequest request){
        return ResponseEntity.ok(serviceGame.obtenerMovimientos(request));
    }

    // realiza los movimientos
    @PostMapping("/realizarMovimiento")
    public ResponseEntity<StateGame> realizarMovimiento(HttpServletRequest request){
        return ResponseEntity.ok(serviceGame.realizarMovimiento(request));
    }


}
