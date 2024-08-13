package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
        private String replyAuthorEmail;
        private Long replyId;
        private Long replyAuthorId;
        private LocalDate replyRegDate;
        private final boolean editable = false;

        public ExhibitionReviewReplyResponseDto(ExhibitionReviewReply rp) {
            this.replyAuthorId = rp.getMember().getId();
            this.replyAuthorNickName = rp.getMember().getNickName();
            this.replyAuthorEmail = rp.getMember().getEmail();
            this.replyId = rp.getId();
            this.replyRegDate = rp.getRegDate() == null ? null : rp.getRegDate().toLocalDate();
            this.replyContent = rp.getReply();
        }
    }

    @Setter
    @Getter
    public static class ExhibitionReviewRequestDto {
        private String reply;
    }

    @Setter
    @Getter
    public static class updateReplyDto {
        private Long replyId;
        private String replyContent;
    }
}