package com.kosa.gallerygather.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TBL")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String email;

}

