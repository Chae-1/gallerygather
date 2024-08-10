package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.ExhibitionReviewResponseDto;
import com.kosa.gallerygather.service.ExhibitionReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/mypage")

public class ApiMemberReivewListController {
    private ExhibitionReviewService exhibitionReviewService;

    // 멤버의 이메일로 리뷰를 조회하는 새로운 엔드포인트
    @GetMapping("/review/{email}")
    public ResponseEntity<List<ExhibitionReviewResponseDto>> getReviewsByMemberEmail(@PathVariable String email) {
        List<ExhibitionReviewResponseDto> reviews = exhibitionReviewService.getReviewsByMemberEmail(email);
        return ResponseEntity.ok(reviews);
    }

}
