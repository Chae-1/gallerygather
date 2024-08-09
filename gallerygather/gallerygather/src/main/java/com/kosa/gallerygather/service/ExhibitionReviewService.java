package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ExhibitionReviewRequestDto;
import com.kosa.gallerygather.dto.ReviewDetailDto;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.exception.member.MemberException;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import com.kosa.gallerygather.repository.ExhibitionReviewReplyRepository;
import com.kosa.gallerygather.repository.ExhibitionReviewRepository;
import com.kosa.gallerygather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExhibitionReviewService {

    private final ExhibitionReviewRepository exhibitionReviewRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final MemberRepository memberRepository;
    private final ExhibitionReviewReplyRepository exhibitionReviewReplyRepository;

    @Transactional
    public ReviewDetailDto addReviewToExhibition(String email, Long exhibitionId, ExhibitionReviewRequestDto requestDto) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("가입되지 않은 사용자 입니다."));

        Exhibition findExhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("작성되지 않은 전시글 입니다."));

        ExhibitionReview savedExhibitionReview = exhibitionReviewRepository.saveAndFlush(ExhibitionReview.ofNewReview(requestDto.getTitle(),
                        requestDto.getContent(),
                        requestDto.getRating(),
                        findExhibition, findMember));

//        exhibitionReviewRepository.findExhibitionReviewWithAllReplies(savedExhibitionReview.getId());
        List<ExhibitionReviewReply> exhibitionReviewReplies = exhibitionReviewReplyRepository
                .findByExhibitReview(savedExhibitionReview);
        return new ReviewDetailDto(findExhibition, exhibitionReviewReplies);
    }
}
