package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.ExhibitionReviewRequestDto;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.ExhibitionReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/exhibition/{exhibitionId}/review")
public class ApiExhibitionReviewController {
    private final ExhibitionReviewService exhibitionReviewService;
    // ?serviceKey=1234 ...
    // @ModelAttribute -> Request Parameter -> Object, value
    // @RequestBody -> Http Body -> Json -> Object, value
    @PostMapping
    public ResponseEntity<Page> postExhibitionReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @RequestParam Long exhibitionId,
                                                     @ModelAttribute ExhibitionReviewRequestDto requestDto) {
        exhibitionReviewService.addReviewToExhibition(userDetails.getEmail(), exhibitionId, requestDto);
        return ResponseEntity.ok(null);
    }
}
