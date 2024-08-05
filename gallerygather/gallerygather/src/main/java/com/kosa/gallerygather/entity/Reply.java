package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_REPLY")
@Getter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reply")
    private String reply;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;


}
