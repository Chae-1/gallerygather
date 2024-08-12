package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {

    @Query("select e from Exhibition e")
    Page<Exhibition> fetchExhibitionsWithPagination(PageRequest pageRequest);

//    @Modifying
//    @Query("update Exhibition e SET e.readCount = e.readCount + 1 WHERE e.id = :id")
//    void increaseReadCount(Long id);
//
//    @Modifying
//    @Query("update Exhibition e SET e.reviewCount = e.reviewCount + 1 WHERE e.id = :id")
//    void increaseReviewCount(Long id);
//
//    @Modifying
//    @Query("update Exhibition e SET e.reviewCount = e.reviewCount - 1 WHERE e.id = :id")
//    void decreaseReviewCount(Long id);
//
//    @Modifying
//    @Query("update Exhibition e SET e.likeCount = e.likeCount + 1 WHERE e.id = :id")
//    void increaseLikeCount(Long id);
//
//    @Modifying
//    @Query("update Exhibition e SET e.likeCount = e.likeCount - 1 WHERE e.id = :id")
//    void decreaseLikeCount(Long id);


}
