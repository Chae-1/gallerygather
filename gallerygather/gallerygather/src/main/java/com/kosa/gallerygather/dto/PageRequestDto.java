package com.kosa.gallerygather.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageRequestDto {
    private Integer pagePer;
    private Integer pageNo;
}
