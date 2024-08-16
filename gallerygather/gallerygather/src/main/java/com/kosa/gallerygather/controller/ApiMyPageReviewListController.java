package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.MyPageReviewListResponseDto;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.MyPageListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//유은
@RestController
@RequiredArgsConstructor
public class ApiMyPageReviewListController {

    private final MyPageListService myPageListService;

    // 로그인된 멤버의 이메일로 리뷰를 조회하는
    @GetMapping("/api/reviews/member")
    public ResponseEntity<List<MyPageReviewListResponseDto>> getReviewsByMemberEmail(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // 로그인된 사용자의 이메일을 가져옵니다.
        String email = userDetails.getEmail();
        System.out.println("리뷰작성시의  email : " + email); // 콘솔 확인

        // 서비스 메소드를 호출하여 리뷰 목록을 가져옵니다.
        List<MyPageReviewListResponseDto> reviews = myPageListService.getReviewsByMemberEmail(email);
        System.out.println("리뷰작성시 reviews : " + reviews); // 콘솔 확인

        // 조회된 리뷰 리스트를 클라이언트에 응답으로 반환
        return ResponseEntity.ok(reviews);
    }

    // 로그인된 멤버의 이메일로 리뷰 개수 조회
    @GetMapping("/api/reviews/member/review-count")
    public ResponseEntity<Integer> getReviewCount(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("getReviewCount 호출 완료");
        // 로그인된 사용자의 이메일
        String email = userDetails.getEmail();
        System.out.println("리뷰 개수 조회 시 email 값 확인: " + email); // 콘솔 확인

        // MyPageListService 메서드 호출하여 리뷰 리스트 조회
        List<MyPageReviewListResponseDto> reviews = myPageListService.getReviewsByMemberEmail(email);
        int reviewCount = reviews.isEmpty() ? 0 : reviews.get(0).getReviewCount();
        System.out.println("리뷰 개수: " + reviewCount); // 콘솔 확인

        // 조회된 리뷰 개수를 클라이언트에 응답으로 반환
        return ResponseEntity.ok(reviewCount);
    }


}
