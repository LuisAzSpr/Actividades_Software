package com.example.security.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tokens")
public class tokenEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String tokenStr;

    private String username;
}
