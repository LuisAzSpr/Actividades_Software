package com.example.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    Optional<User>findByEmail(String email);

    @Transactional
    default void updateTokenValidByUsername(String username, boolean tokenValid) {
        Optional<User> optionalUser = findByUsername(username);
        optionalUser.ifPresent(user -> {
            user.setTokenValid(tokenValid);
            save(user);
        });
    }

}