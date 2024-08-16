package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.ExhibitionDto;
import com.kosa.gallerygather.dto.ExhibitionLikeDto;
import com.kosa.gallerygather.dto.PageRequestDto;
import com.kosa.gallerygather.dto.ExhibitionCardDto;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.ExhibitionLikeService;
import com.kosa.gallerygather.service.ExhibitionService;
import com.kosa.gallerygather.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/exhibitions")
public class ApiExhibitionController {

    private final ExhibitionService exhibitionService;
    private final ExhibitionLikeService exhibitionLikeService;
    private final MemberService memberService;

    @DeleteMapping("/{exhibitionId}")
    public ResponseEntity<Boolean> deleteExhibition(@PathVariable Long exhibitionId) {
        exhibitionService.deleteExhibition(exhibitionId);
        return ResponseEntity.ok(true);
    }

    @GetMapping
    public ResponseEntity<Page<ExhibitionCardDto>> getCards(Pageable pageable, @RequestParam(required = false) String title) {
        log.info("{}", pageable);
        return ResponseEntity.ok(exhibitionService.getCardDto(pageable, title));
    }

    /*
    작성자: 오지수
    전시 상세 페이지 들어가기
     */
    @GetMapping("/{exhibitonId}")
    public ResponseEntity<Map<String, Object>> getExhibitionDetails(@PathVariable Long exhibitonId,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 1. exhibitonDetail
        // 2. 로그인 되었다면 이 전시를 like 했는지 여부
        Map<String, Object> response = exhibitionService.findExhibitionDetailWithLikes(exhibitonId, userDetails !=null ? userDetails.getId(): null);

        return ResponseEntity.ok().body(response);
    }

    /*
    작성자: 오지수
    전시 페이지 좋아요 클릭하기
    */
    @PostMapping("/{exhibitionId}/like")
    public ResponseEntity<ExhibitionDto> likeExhibition(@PathVariable Long exhibitionId,
                                                        @RequestBody ExhibitionLikeDto.RequestLike likeDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok()
                .body(exhibitionLikeService.clickExhibitionLike(likeDto.getIsLike(), new ExhibitionLikeDto.RequestExhibitionLike(exhibitionId, userDetails.getId())));
    }


    //유은 - 좋아요 전시목록(memberService는 memberId를가져오기위해)
    @GetMapping("/likelist")
    public ResponseEntity<List<Exhibition>> getLikedExhibitions() {
        // 현재 로그인된 사용자의 memberId를 가져옴
        Long memberId = memberService.getCurrentMemberId();
        System.out.println("담겨있는 memberId: " + memberId);

        // memberId를 이용하여 좋아요한 전시 목록을 가져옴
        List<Exhibition> likedExhibitions = exhibitionLikeService.getLikedExhibitionsForCurrentMember();

        // 조회된 목록을 클라이언트에 반환
        return ResponseEntity.ok(likedExhibitions);
    }

    //유은 - 좋아요 전시목록 갯수 카운트
    @GetMapping("/likes/member/like-count")
    public ResponseEntity<Integer> getLikeCount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("getLikeCount 호출 완료");

        // ExhibitionLikeService에서 현재 사용자의 좋아요 개수를 조회
        int likeCount = exhibitionLikeService.getLikeCountForCurrentMember();
        System.out.println("좋아요 개수: " + likeCount); // 콘솔 확인

        // 조회된 좋아요 개수를 클라이언트에 응답으로 반환
        return ResponseEntity.ok(likeCount);
    }



}