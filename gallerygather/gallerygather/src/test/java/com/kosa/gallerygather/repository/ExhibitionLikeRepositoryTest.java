package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.entity.ReviewReply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(false)
class ExhibitionLikeRepositoryTest {
    @Autowired
    ReviewReplyRepository repository;

    @Autowired
    MemberRepository memberRepository;


    @Test
    @Transactional
    void test() {
        Member member = Member.ofNewMember("a", "a", "a", null, "a");
        memberRepository.save(member);

        ReviewReply reviewReply = new ReviewReply();
        reviewReply.setMember(member);
        reviewReply.setReply("reply");
        repository.save(reviewReply);

        reviewReply.setReply("dd");
    }
}