package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ExhibitionReviewRequestDto;
import com.kosa.gallerygather.dto.ReviewDetailDto;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import com.kosa.gallerygather.repository.MemberRepository;
import com.kosa.gallerygather.repository.ExhibitionReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExhibitionReviewService {

    @Autowired
    private final ExhibitionReviewRepository exhibitionReviewRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final MemberRepository memberRepository;
    private final ReviewImageService imageService;

    public ExhibitionReviewService(ExhibitionReviewRepository exhibitionReviewRepository, ExhibitionRepository exhibitionRepository, MemberRepository memberRepository, ReviewImageService imageService) {
        this.exhibitionReviewRepository = exhibitionReviewRepository;
        this.exhibitionRepository = exhibitionRepository;
        this.memberRepository = memberRepository;
        this.imageService = imageService;
    }
    
    public ExhibitionReview findByExhibitionId(Long exhibitionId) {
        return exhibitionReviewRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException( "리뷰 Id 찾기 오류" + exhibitionId));
    }

    @Transactional
    public ReviewDetailDto write(final ExhibitionReviewRequestDto requestDto, String memberEmail, Long exhibitionId){
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID(Email) 찾기 오류: " + memberEmail));
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("전시회 ID 찾기 오류: " + exhibitionId));
        ExhibitionReview exhibitionReview = requestDto.toEntity(member, exhibition);
        ExhibitionReview savedReview = exhibitionReviewRepository.saveAndFlush(exhibitionReview);
        extractAndSaveImages(requestDto.getContent(), savedReview);
        return new ReviewDetailDto(savedReview, member, exhibition);
    }

    private void extractAndSaveImages(String content, ExhibitionReview review){
        List<String> imageUrls = extractImageUrls(content);
        for (String imageUrl : imageUrls) {
            imageService.createImage(imageUrl, review);
        }
    }
    private List<String> extractImageUrls(String content) {
        List<String> imageUrls = new ArrayList<>();
        String imgTagPattern = "<img[^>]+src=\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(imgTagPattern);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    @Transactional
    public ReviewDetailDto getReviewDetail(Long exhibitionId, Long reviewId){
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("전시회 ID 찾기 오류: " + exhibitionId));
        ExhibitionReview review = exhibitionReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 ID 찾기 오류: " + reviewId));
        return new ReviewDetailDto(review, review.getMember(), exhibition);
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
