package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.dto.ReviewDetailDto2;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
                    where EXHIBIT_REVIEW_ID = ?
                ), review_with_reply as (
                    select review.member_id as review_author_id, review_id, reply_author_id, REPLY,
                           reply_count, reply_reg_date, review.RATING as review_rating, review.TITLE,
                           review.CONTENT as review_content
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
        int size = template.query(sql, new Object[]{exhibitionId, exhibitionId}, new RowMapper<ReviewDetailDto2>() {
            @Override
            public ReviewDetailDto2 mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println("쿼리실행");
                if (rs.next()) {
                    System.out.println(rs.getLong("review_author_id"));
                }
                return null;
            }
        }).size();
        System.out.println(size);
    }
}
