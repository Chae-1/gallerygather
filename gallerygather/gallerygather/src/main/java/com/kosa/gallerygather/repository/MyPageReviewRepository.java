package com.kosa.gallerygather.repository;
//유은
import com.kosa.gallerygather.entity.ExhibitionReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPageReviewRepository extends JpaRepository<ExhibitionReview, Long> {

    List<ExhibitionReview> findByMemberEmail(String email);

}
