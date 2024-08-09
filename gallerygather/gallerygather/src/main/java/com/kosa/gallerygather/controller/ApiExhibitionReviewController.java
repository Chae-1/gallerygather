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

    @PostMapping("/review")
    public ResponseEntity<Long> createReview(@RequestBody ExhibitionReviewRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam Long exhibitionId) {
        Long memberId = Long.valueOf(userDetails.getUsername());
        Long reviewId = reviewService.write(requestDto, memberId, exhibitionId);
        return ResponseEntity.ok(reviewId);

    }



}
