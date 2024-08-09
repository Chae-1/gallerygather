package com.kosa.gallerygather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExhibitionReviewReplyDto {
    private String replyContent;
    private LocalDateTime replyRegDate;
    private String replyAuthorNickName;
    private Long replyId;
    private Long replyAuthorId;
}
