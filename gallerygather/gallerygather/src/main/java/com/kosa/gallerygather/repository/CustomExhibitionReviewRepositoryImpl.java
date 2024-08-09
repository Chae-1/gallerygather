package com.kosa.gallerygather.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class CustomExhibitionReviewRepositoryImpl implements CustomExhibitionReviewRepository{

    private final JdbcTemplate template;

    @Override
    public void findExhibitionReviewWithAllReplies(Long exhibitionId) {
        String sql =
                """
                with reply_with_member as (
                    select m.ID as reply_author_id, reply.REPLY, reply.REG_DATE as reply_reg_date, reply.EXHIBIT_REVIEW_ID as review_id, count(*) over() as reply_count
                    from TBL_REVIEW_REPLY reply
                             join TBL_MEMBER m on reply.MEMBER_ID = m.ID
                    where EXHIBIT_REVIEW_ID = 1
                ), review_with_reply as (
                    select review.member_id as review_author_id, review_id, reply_author_id, REPLY,
                           reply_count, reply_reg_date, review.RATING as review_rating, review.TITLE,
                           review.CONTENT
                    from TBL_EXHIBIT_REVIEW review
                        join TBL_EXHIBITION exhibition on exhibition.ID = review.EXHIBITION_ID
                        join TBL_MEMBER m on review.MEMBER_ID = m.ID -- 1
                        left join reply_with_member rm on review.ID = rm.review_id
                    where review.ID = 1
                    order by reply_reg_date asc
                )
                select *
                from review_with_reply;
                """;
        template.queryForRowSet(sql, new Object[]{});
    }
}
