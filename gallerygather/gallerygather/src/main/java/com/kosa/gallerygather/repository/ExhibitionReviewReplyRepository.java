package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExhibitionReviewReplyRepository extends JpaRepository<ExhibitionReviewReply, Long> {

    List<ExhibitionReviewReply> findByExhibitionId(Long exhibitionReviewId);
}
