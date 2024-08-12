package com.kosa.gallerygather.dto;

//유은
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageReplyListResponseDto {
    private String content;          // 댓글 내용
    private LocalDateTime regDate;     // 등록 날짜
    private LocalDateTime updateDate;  // 수정 날짜
    private long replyReviewId;
    private String reviewTitle;
}
