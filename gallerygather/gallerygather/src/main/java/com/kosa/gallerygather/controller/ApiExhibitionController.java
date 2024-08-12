package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.ExhibitionDto;
import com.kosa.gallerygather.dto.ExhibitionLikeDto;
import com.kosa.gallerygather.dto.PageRequestDto;
import com.kosa.gallerygather.dto.ExhibitionCardDto;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.ExhibitionLikeService;
import com.kosa.gallerygather.service.ExhibitionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/exhibitions")
public class ApiExhibitionController {
    private final ExhibitionService exhibitionService;
    private final ExhibitionLikeService exhibitionLikeService;

    @GetMapping
    public ResponseEntity<Page<ExhibitionCardDto>> getCards(@ModelAttribute PageRequestDto page) {
        log.info("{}", page);
        return ResponseEntity.ok(exhibitionService.getCardDto(page));
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
}
