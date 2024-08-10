package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.ExhibitionReviewRequestDto;
import com.kosa.gallerygather.service.ExhibitionReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApiExhibitionReviewController {

    private final ExhibitionReviewService reviewService;

    @PostMapping("/api/exhibition/{exhibitionId}/review")
    public ResponseEntity<Long> createReview(@RequestBody ExhibitionReviewRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam Long exhibitionId) {
        Long memberId = Long.valueOf(userDetails.getUsername());
        Long reviewId = reviewService.write(requestDto, memberId, exhibitionId);
        return ResponseEntity.ok(reviewId);

    }

//@Slf4j
//@RequestMapping("/api/exhibition/{exhibitionId}/review")
//public class ApiExhibitionReviewController {
//    private final ExhibitionReviewService exhibitionReviewService;
//    // @ModelAttribute -> Request Parameter -> Object, value
//    // @RequestBody -> Http Body -> Json -> Object, value
//    @PostMapping
//    public ResponseEntity<ReviewDetailDto> postExhibitionReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                                                @PathVariable Long exhibitionId,
//                                                                @ModelAttribute ExhibitionReviewRequestDto requestDto) {
//        ReviewDetailDto reviewDetailDto = exhibitionReviewService.addReviewToExhibition(userDetails.getEmail(), exhibitionId, requestDto);
//        return ResponseEntity.ok(reviewDetailDto);
//    }

}
