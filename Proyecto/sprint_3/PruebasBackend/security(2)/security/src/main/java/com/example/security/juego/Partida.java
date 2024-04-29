package com.example.security.juego;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Partida")
public class Partida {
    @Id
    @GeneratedValue
    private Integer id;

    private String juego;

    private String username;

    private Date fecha;

    public Partida(String juego, String name, Date date) {

    }
}
