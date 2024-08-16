package com.kosa.gallerygather.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * 작성자: 채형일
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExhibitionCardDto {
    private Long exhibitionId;
    private String imageUrl;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double rating;
    private String place;
}
