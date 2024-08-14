package com.kosa.gallerygather.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TBL_REVIEW_IMAGE")
@NoArgsConstructor
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_idx")
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "original_name")
    private String originalName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibitreview_id")
    private ExhibitionReview exhibitionReview;


    private ReviewImage(String path, String originalName) {
        this.path = path;
        this.originalName = originalName;
    }

    public static ReviewImage ofNewImage(String path, String originalName) {
        return new ReviewImage(path, originalName);
    }
}

