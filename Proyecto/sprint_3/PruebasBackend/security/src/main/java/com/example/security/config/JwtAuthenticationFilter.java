package com.example.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Cada solicitud del cliente pasa por este filtro, establece un contexto de seguridad para cada usuario
    @Override
    protected void doFilterInternal(

        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)

        throws ServletException, IOException {

            // obtiene la authorization de la cabecera
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String username;

            // verifica si la cabecera es no nula y si es no nula verifica si empieza con Bearer (campo usado para un jwt)
            if(authHeader==null || !authHeader.startsWith("Bearer ")){
                // si no es asi solo retornamos
                filterChain.doFilter(request,response);
                return;
            }
            // si cumple con los condiciones anteriores quiere decir que existe un token, por lo que lo extraemos
            jwt = authHeader.substring(7);
            // extrae el nombre de usuario del token
            username = jwtService.extractUsername(jwt);
            //verifica que el usuario sea no nulo y no exista un contexto de seguridad
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if(jwtService.isTokenValid(jwt,userDetails)){
                    //  establece un contexto de seguridad para el usuario
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    // guarda los detalles de la authenticacion en authToken
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    // guarda el contexto de seguridad de ese usuario
                     SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request,response);
    }

}
