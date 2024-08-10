package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ExhibitionReviewRequestDto;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import com.kosa.gallerygather.repository.MemberRepository;
import com.kosa.gallerygather.repository.ExhibitionReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ExhibitionReviewService {

    private final ExhibitionReviewRepository exhibitionReviewRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final MemberRepository memberRepository;

    public ExhibitionReviewService(ExhibitionReviewRepository exhibitionReviewRepository, ExhibitionRepository exhibitionRepository, MemberRepository memberRepository) {
        this.exhibitionReviewRepository = exhibitionReviewRepository;
        this.exhibitionRepository = exhibitionRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long write(final ExhibitionReviewRequestDto requestDto, Long memberId, Long exhibitionId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID 찾기 오류: " + memberId));
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("전시회 ID 찾기 오류: " + exhibitionId));
        ExhibitionReview exhibitionReview = requestDto.toEntity(member, exhibition);
        ExhibitionReview savedReview = exhibitionReviewRepository.save(exhibitionReview);
        return savedReview.getId();
    }


//    private final ExhibitionReviewReplyRepository exhibitionReviewReplyRepository;
//
//    @Transactional
//    public ReviewDetailDto addReviewToExhibition(String email, Long exhibitionId, ExhibitionReviewRequestDto requestDto) {
//        Member findMember = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new MemberException("가입되지 않은 사용자 입니다."));
//
//        Exhibition findExhibition = exhibitionRepository.findById(exhibitionId)
//                .orElseThrow(() -> new IllegalArgumentException("작성되지 않은 전시글 입니다."));
//
//        ExhibitionReview savedExhibitionReview = exhibitionReviewRepository.saveAndFlush(ExhibitionReview.ofNewReview(requestDto.getTitle(),
//                        requestDto.getContent(),
//                        requestDto.getRating(),
//                        findExhibition, findMember));
//
//        List<ExhibitionReviewReply> exhibitionReviewReplies = exhibitionReviewReplyRepository
//                .findByExhibitReview(savedExhibitionReview);
//
//        return new ReviewDetailDto(findExhibition, exhibitionReviewReplies);
//    }
}
