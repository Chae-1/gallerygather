package com.kosa.gallerygather.service;

import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ReviewImage;
import com.kosa.gallerygather.repository.ReviewImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewImageService {

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Transactional
    public ReviewImage createImage(String imageUrl, ExhibitionReview review) {
        ReviewImage image = new ReviewImage();
        image.setPath(imageUrl);
        image.setOriginalName(extractOriginalName(imageUrl));
        image.setExhibitionReview(review);
        return reviewImageRepository.save(image);
    }

    private String extractOriginalName(String imageUrl) {
        // 이미지 URL에서 파일 이름을 추출하는 로직
        return imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
    }
}
