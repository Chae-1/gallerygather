package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReviewDetailDto {
    private Exhibition exhibition;
    private List<ExhibitionReviewReply> reviewReplies;
}
