package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.dto.ExhibitionReviewReplyDto;
import com.kosa.gallerygather.dto.ReviewDetailDto2;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomExhibitionReviewRepositoryImpl implements CustomExhibitionReviewRepository {

    private final JdbcTemplate template;

    @Override
    public List<ReviewDetailDto2> findExhibitionReviewWithAllReplies(Long exhibitionId) {
        String sql =
                """
                with reply_with_member as (
                    select m.ID as reply_author_id, reply.id as reply_id, reply.REPLY, reply.REG_DATE as reply_reg_date, reply.EXHIBIT_REVIEW_ID as review_id, count(*) over() as reply_count
                    from TBL_REVIEW_REPLY reply
                        join TBL_MEMBER m on reply.MEMBER_ID = m.ID
                    where EXHIBIT_REVIEW_ID = ?
                ), review_with_reply as (
                    select review.member_id as review_author_id, review_id, reply_id, reply_author_id, REPLY as review_reply,
                           reply_count, reply_reg_date, review.RATING as review_rating, review.TITLE as review_title,
                           review.CONTENT as review_content, m.nick_name as review_author_name
                    from TBL_EXHIBIT_REVIEW review
                        join TBL_EXHIBITION exhibition on exhibition.ID = review.EXHIBITION_ID
                        join TBL_MEMBER m on review.MEMBER_ID = m.ID
                        left join reply_with_member rm on review.ID = rm.review_id
                    where review.ID = ?
                    order by reply_reg_date asc
                )
                select *
                from review_with_reply
                """;

        return template.query(sql, new Object[]{exhibitionId, exhibitionId}, (rs, rowNum) -> {
            ReviewDetailDto2 dtos = new ReviewDetailDto2();
            List<ExhibitionReviewReplyDto> exhibitionReviewReplyDtos = new ArrayList<>();

            while (rs.next()) {
                Long reviewAuthorId = rs.getLong("review_author_id");
                Long reviewId = rs.getLong("review_id");
                String reviewAuthorNickName = rs.getString("review_author_name");
                String reviewTitle = rs.getString("review_title");
                String reviewContent = rs.getString("review_content");
                double reviewRating = rs.getDouble("review_rating");
                int totalReplyCount = rs.getInt("reply_count");

                dtos.setTotalReplyCount(totalReplyCount);
                dtos.setReviewAuthorId(reviewAuthorId);
                dtos.setReviewRating(reviewRating);
                dtos.setReviewId(reviewId);
                dtos.setReviewContent(reviewContent);
                dtos.setReviewTitle(reviewTitle);
                dtos.setReviewAuthorNickName(reviewAuthorNickName);

            }
            dtos.setReviewReplies(exhibitionReviewReplyDtos);
            return dtos;
        });

    }
}
