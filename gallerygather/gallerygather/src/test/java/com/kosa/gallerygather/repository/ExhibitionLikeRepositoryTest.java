package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Rollback(false)
class ExhibitionLikeRepositoryTest {
    @Autowired
    ExhibitionReviewReplyRepository repository;

    @Autowired
    MemberRepository memberRepository;


    @Test
    @Transactional
    void test() {
        Member member = Member.ofNewMember("a", "a", "a", null, "a");
        memberRepository.save(member);

        ExhibitionReviewReply exhibitionReviewReply = new ExhibitionReviewReply();
        exhibitionReviewReply.setMember(member);
        exhibitionReviewReply.setReply("reply");
        repository.save(exhibitionReviewReply);

        exhibitionReviewReply.setReply("dd");
    }
}