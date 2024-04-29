package com.example.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.config.annotation.web.oauth2.resourceserver.OpaqueTokenDsl;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<tokenEntity,Integer> {
    void deleteByTokenStr(String tokenStr);
    boolean existsByTokenStr(String tokenStr);

}
