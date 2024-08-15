package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    // 이미지 경로를 통해 이미지를 검색하는 메서드
    Optional<ReviewImage> findByPath(String path);
}
