package com.kosa.gallerygather.repository;


import com.kosa.gallerygather.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("select t from RefreshToken t join fetch t.member where t.token = :token")
    Optional<RefreshToken> findByToken(@Param("token") String token);

    @Query("select t from RefreshToken t join fetch t.member m where m.email = :email")
    @Transactional
    RefreshToken findByUserEmail(@Param("email") String email);
}
