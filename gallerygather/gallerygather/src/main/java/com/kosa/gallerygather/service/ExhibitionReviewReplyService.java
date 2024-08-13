package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ExhibitionReviewReplyDto;
import com.kosa.gallerygather.dto.PageRequestDto;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import com.kosa.gallerygather.repository.ExhibitionReviewReplyRepository;
import com.kosa.gallerygather.repository.ExhibitionReviewRepository;
import com.kosa.gallerygather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExhibitionReviewReplyService {
    private final ExhibitionReviewReplyRepository reviewReplyRepository;
    private final MemberRepository memberRepository;
    private final ExhibitionReviewRepository exhibitionReviewRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final ExhibitionReviewReplyRepository exhibitionReviewReplyRepository;

    @Transactional
    public void addCommentToReview(String email, Long reviewId,
                                   ExhibitionReviewReplyDto.ExhibitionReviewRequestDto replyDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        ExhibitionReview exhibitionReview = exhibitionReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
        ExhibitionReviewReply reply = reviewReplyRepository
                .save(ExhibitionReviewReply.ofNewReply(member, exhibitionReview, replyDto.getReply()));


//        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("regDate").ascending());

//        return reviewReplyRepository.findByExhibitionReviewWithMember(exhibitionReview, pageable)
//                .map(ExhibitionReviewReplyDto.ExhibitionReviewReplyResponseDto::new);
    }

    public Page<ExhibitionReviewReplyDto.ExhibitionReviewReplyResponseDto> findAllRepliesAboutReview(Long reviewId,
                                                                                                     Pageable pageable) {
        System.out.println("============== 댓글리스트 조회 ===================");
        ExhibitionReview exhibitionReview = exhibitionReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
        Page<ExhibitionReviewReply> allRepliesAboutReview = reviewReplyRepository
                .findAllRepliesAboutReview(exhibitionReview, pageable);
        System.out.println("================ 댓글리스트 조회 종료 ==================");
        return allRepliesAboutReview
                .map(ExhibitionReviewReplyDto.ExhibitionReviewReplyResponseDto::new);
    }

    /*
    작성자: 오지수
    댓글 수정
     */
    @Transactional
    public void updateReplyWithId(Long reviewId, Long memberId, ExhibitionReviewReplyDto.updateReplyDto replyDto) {
        // 유효성 검사
        ExhibitionReview review = exhibitionReviewRepository.findById(reviewId).orElseThrow();
        ExhibitionReviewReply reply = exhibitionReviewReplyRepository.findByIdAndMemberIdAndExhibitionReview(replyDto.getReplyId(), memberId, review).orElseThrow();
        reply.setReply(replyDto.getReplyContent());
    }

    /*
    작성자: 오지수
    댓글 삭제
     */
    @Transactional
    public void deleteRplyWithId(Long reviewId, Long memberId, Long replyId) {
        ExhibitionReview review = exhibitionReviewRepository.findById(reviewId).orElseThrow();
        ExhibitionReviewReply reply = exhibitionReviewReplyRepository.findByIdAndMemberIdAndExhibitionReview(replyId, memberId, review).orElseThrow();
        exhibitionReviewReplyRepository.delete(reply);
    }
}
