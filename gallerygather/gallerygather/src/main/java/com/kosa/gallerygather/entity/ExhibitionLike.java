package com.kosa.gallerygather.entity;

import com.kosa.gallerygather.dto.ExhibitionLikeDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="TBL_EXHIBITLIKE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExhibitionLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    public static ExhibitionLike setExhibitionLike(ExhibitionLikeDto.RequestExhibitionLike dto) {
        ExhibitionLike exhibitionLike = new ExhibitionLike();
        Exhibition exhibition = new Exhibition();
        exhibition.setId(dto.getExhibitionId());
        Member member = new Member();
        member.setId(dto.getMemberId());
        exhibitionLike.member = member;
        exhibitionLike.exhibition = exhibition;
        return exhibitionLike;
    }
}
