package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_MEMBER")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private static final String BASIC_AUTH = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "auth")
    private String auth;

    private LocalDate dateOfBirth;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "member")
    private List<ExhibitionReviewReply> replies = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private RefreshToken refreshToken;

    private Member(String name, String password, String email, String auth, LocalDate dateOfBirth,
                   LocalDateTime regDate, LocalDateTime updateDate, String nickName) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.auth = auth;
        this.dateOfBirth = dateOfBirth;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.nickName = nickName;
    }

    public static Member ofNewMember(String name, String email,
                                     String password, LocalDate dateOfBirth, String nickName) {
        return new Member(name, password, email, BASIC_AUTH,
                dateOfBirth, LocalDateTime.now(), LocalDateTime.now(), nickName);
    }

}

