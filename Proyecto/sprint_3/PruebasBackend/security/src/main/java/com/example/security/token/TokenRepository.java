package com.example.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.config.annotation.web.oauth2.resourceserver.OpaqueTokenDsl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<tokenEntity,Integer> {
    void deleteByTokenStr(String tokenStr);
    boolean existsByTokenStr(String tokenStr);
    List<tokenEntity> findByusername(String username);

}
