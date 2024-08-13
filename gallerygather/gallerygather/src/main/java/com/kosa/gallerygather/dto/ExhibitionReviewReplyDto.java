package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExhibitionReviewReplyDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ExhibitionReviewReplyResponseDto {
        private String replyContent;
        private String replyAuthorNickName;
        private Long replyId;
        private Long replyAuthorId;
        private LocalDateTime replyRegDate;

        public ExhibitionReviewReplyResponseDto(ExhibitionReviewReply rp) {
            this.replyAuthorId = rp.getMember().getId();
            this.replyAuthorNickName = rp.getMember().getNickName();
            this.replyId = rp.getId();
            this.replyRegDate = rp.getRegDate();
            this.replyContent = rp.getReply();
        }
    }

    @Setter
    @Getter
    public static class ExhibitionReviewRequestDto {
        private String reply;
    }
}