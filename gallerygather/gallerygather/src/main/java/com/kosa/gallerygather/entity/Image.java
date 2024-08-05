package com.kosa.gallerygather.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "img_idx")
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "original_name")
    private String originalName;
}
