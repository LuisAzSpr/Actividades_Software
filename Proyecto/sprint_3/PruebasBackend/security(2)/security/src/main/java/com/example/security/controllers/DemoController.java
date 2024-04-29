package com.example.security.controllers;

import com.example.security.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;


// endpoints protegidos
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    private final AuthenticationService service;

    @GetMapping("/Hello") // prueba
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @PostMapping("/logout") // para cerrar sesion
    public ResponseEntity<String> logout(HttpServletRequest request){
        return ResponseEntity.ok(service.logOut(request));
    }

}
