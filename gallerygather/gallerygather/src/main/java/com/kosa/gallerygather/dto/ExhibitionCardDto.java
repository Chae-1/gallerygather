package com.kosa.gallerygather.dto;

import lombok.*;

import java.time.LocalDate;

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
}
