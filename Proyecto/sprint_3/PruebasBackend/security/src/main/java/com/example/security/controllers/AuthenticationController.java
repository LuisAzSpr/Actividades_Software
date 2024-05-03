package com.example.security.controllers;

import com.example.security.auth.authClass.AuthenticationRequest;
import com.example.security.auth.authClass.AuthenticationResponse;
import com.example.security.auth.AuthenticationService;
import com.example.security.auth.authClass.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// endpoints no protegidos
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register") // registrarse
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    )
    {
        return ResponseEntity.ok(service.register(request)); // llama a service
    }

    @PostMapping("/authenticate") //autenticarse
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    )
    {
        return ResponseEntity.ok(service.authenticate(request)); // llama a service
    }

}
