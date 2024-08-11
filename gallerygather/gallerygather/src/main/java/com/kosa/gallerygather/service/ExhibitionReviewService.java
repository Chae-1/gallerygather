package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ExhibitionReviewDto;
import com.kosa.gallerygather.dto.ExhibitionReviewRequestDto;
import com.kosa.gallerygather.dto.PageRequestDto;
import com.kosa.gallerygather.dto.ReviewDetailDto;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import com.kosa.gallerygather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
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
    public Long write(final ExhibitionReviewRequestDto requestDto, String memberEmail, Long exhibitionId){
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID(Email) 찾기 오류: " + memberEmail));
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("전시회 ID 찾기 오류: " + exhibitionId));
        ExhibitionReview exhibitionReview = requestDto.toEntity(member, exhibition);
        ExhibitionReview savedReview = exhibitionReviewRepository.saveAndFlush(exhibitionReview);
        return savedReview.getId();
    }

    // 작성자: 오지수
    // 전시 상세 페이지에서 하단의 리뷰 리스트를 가져오는 Service
    public List<ExhibitionReviewDto.RequestReviewList> getExhibitionReviews(Long exhibitionId, PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(pageRequestDto
                .getPageNo()-1, pageRequestDto.getPagePer(), Sort.by("regDate").descending());
        List<ExhibitionReview> exhibitionReviews = exhibitionReviewRepository.findByExhibitionId(exhibitionId, pageable);
        return exhibitionReviews.stream().map(ExhibitionReviewDto.RequestReviewList::new)
                .collect(Collectors.toList());
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
