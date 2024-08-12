package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.MyPageReplyListResponseDto;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.MyPageListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 유은
@RestController
@RequiredArgsConstructor
public class ApiMyPageReplyListController {

    //주입
    private final MyPageListService myPageListService;
    
    //로그인된 멤버의 이메일로 댓글 조회
    @GetMapping("/api/replys/member/reply")
    public ResponseEntity<List<MyPageReplyListResponseDto>> getReplyList(
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("getReplyList호출완료 ");
        // 로그인된 사용자의 이메일
        String email = userDetails.getEmail();
        System.out.println("댓글 작성시 email값 확인: " + email); // 콘솔 확인

        //메서드호출
        List<MyPageReplyListResponseDto> replys = myPageListService.getMyPageReplyRepository(email);
        System.out.println("댓글 작성시 replys에 담긴것 : " + replys); // 콘솔 확인

        // 조회된 리뷰 리스트를 클라이언트에 응답으로 반환
        return ResponseEntity.ok(replys);

    }
}
