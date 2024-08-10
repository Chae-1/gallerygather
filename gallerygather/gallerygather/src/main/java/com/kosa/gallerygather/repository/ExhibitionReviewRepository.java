package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

<<<<<<< HEAD
public interface ExhibitionReviewRepository extends JpaRepository<ExhibitionReview, Long> {

    List<ExhibitionReview> findByContent(String content);

=======
public interface ExhibitionReviewRepository extends JpaRepository<ExhibitionReview, Long>, CustomExhibitionReviewRepository {

    List<ExhibitionReview> findByContentContains(String content);
>>>>>>> 81c64caf9d296f12feeb0fd637ffa77c0536f556

}
