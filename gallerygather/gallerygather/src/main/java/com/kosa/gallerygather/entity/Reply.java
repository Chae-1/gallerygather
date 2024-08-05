package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_REPLY")
@Getter
@Setter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reply;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
