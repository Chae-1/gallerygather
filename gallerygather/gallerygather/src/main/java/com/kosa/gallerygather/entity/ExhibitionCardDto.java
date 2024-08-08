package com.kosa.gallerygather.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExhibitionCardDto {
    private String imageUrl;
    private String title;
    private String description;
}
