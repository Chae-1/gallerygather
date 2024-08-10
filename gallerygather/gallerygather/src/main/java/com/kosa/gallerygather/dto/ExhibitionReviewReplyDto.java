package com.kosa.gallerygather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionReviewReplyDto {
    private String replyContent;
    private String replyAuthorNickName;
    private Long replyId;
    private Long replyAuthorId;
    private LocalDateTime replyRegDate;
}