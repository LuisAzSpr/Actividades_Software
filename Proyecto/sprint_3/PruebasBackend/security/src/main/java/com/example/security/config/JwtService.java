package com.example.security.config;


import com.example.security.token.TokenRepository;
import com.example.security.user.User;
import com.example.security.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final TokenRepository tokenRepository;

    // extreamos el clain subject (username) del token
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    // extraemos un claim del token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generatedToken(new HashMap<>(), userDetails);
    }

    // si el token es valido quiere decir que le pertenece a un usuario, aun no ha expirado y el usuario
    // no ha cerrado sesion
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token) && TokenDoesntClose(token);
    }

    // si el usuario no ha cerrado la sesion, el token se mantiene en la base de datos
    public boolean TokenDoesntClose(String token){
        return tokenRepository.existsByTokenStr(token);
    }

    // se verifica si el token aun no ha expirado
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // extraemos la expiracion del token para ver si es valido
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generatedToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // generamos un token
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // el token sera identificado con el nombre de usuario
                .setIssuedAt(new Date(System.currentTimeMillis())) // la fecha actual
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // cuanto tiempo se mantendra la sesion iniciada
                .signWith(secretKey, SignatureAlgorithm.HS256) // Utilizamos la clave generada
                .compact();
    }

    private Claims extractAllClaims(String token) {
        // extraermos todos los claims del token
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey) // Utilizamos la clave generada
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
