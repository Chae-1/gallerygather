package com.kosa.gallerygather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class MypageInfoDto {
    private String memberEmail;
    private String memberNick;
    private String memberName; // 이름을 저장하는 데이터
    private LocalDate birthdate; // 선택된 일
    private String  zipcode; // 우편번호
    private String address; // 주소
    private String detailAddress; // 상세주소
}