package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_MEMBER")
@Getter
public class REPLY {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reply;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;


}
