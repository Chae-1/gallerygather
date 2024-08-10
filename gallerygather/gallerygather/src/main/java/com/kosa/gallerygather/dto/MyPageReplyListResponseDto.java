package com.kosa.gallerygather.dto;

//작성자 : 박유은
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageReplyListResponseDto {
    private String content;          // 댓글 내용
    private LocalDateTime regDate;     // 등록 날짜
    private LocalDateTime updateDate;  // 수정 날짜

}
