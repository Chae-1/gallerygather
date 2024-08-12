package com.kosa.gallerygather.repository;


import com.kosa.gallerygather.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("select t from RefreshToken t join fetch t.member")
    Optional<RefreshToken> findByToken(String token);
}
