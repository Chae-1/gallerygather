package com.kosa.gallerygather.repository;
//유은
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//유은
public interface MyPageReplyRepository extends JpaRepository<ExhibitionReviewReply, Long> {

    List<ExhibitionReviewReply> findByMemberEmail(String memberEmail);
}
