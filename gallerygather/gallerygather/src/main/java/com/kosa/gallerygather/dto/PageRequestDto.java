package com.kosa.gallerygather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageRequestDto {
    private Integer pagePer = 10;
    private Integer pageNo = 1;
}
