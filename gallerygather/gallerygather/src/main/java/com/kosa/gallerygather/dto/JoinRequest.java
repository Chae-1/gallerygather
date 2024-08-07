package com.kosa.gallerygather.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JoinRequest {
    private String email;
    private String name;
    private String password;
    private LocalDate dateOfBirth;
}
