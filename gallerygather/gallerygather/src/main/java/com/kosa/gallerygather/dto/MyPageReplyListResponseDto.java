package com.kosa.gallerygather.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//유은
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageReplyListResponseDto {
    private String content;          // 댓글 내용
    private LocalDateTime regDate;     // 등록 날짜
    private LocalDateTime updateDate;  // 수정 날짜
    private long replyReviewId;
    private String reviewTitle;
    private int replyCount;               // 댓글의 총 개수 추가
    private Long getExhibitionId;

}
