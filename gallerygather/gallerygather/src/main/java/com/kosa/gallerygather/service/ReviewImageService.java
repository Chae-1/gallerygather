package com.kosa.gallerygather.service;

import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ReviewImage;
import com.kosa.gallerygather.repository.ReviewImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class ReviewImageService {

    private final String uploadDir = "uploads/"; // 이미지를 저장할 디렉토리

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Transactional
    public ReviewImage saveImage(MultipartFile multipartFile) throws IOException {
        File uploadDir = new File(this.uploadDir);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }
        // 파일명 생성
        String originalName = multipartFile.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + "_" + originalName;
        String filePath = uploadDir.getAbsolutePath() + File.separator + filename;

        // 파일을 서버에 저장 (이 시점에 파일이 실제로 uploads 폴더에 저장됩니다)
        File file = new File(filePath);
        multipartFile.transferTo(file);

        // 이미지 엔티티 생성 및 저장
        ReviewImage image = new ReviewImage();
        image.setPath(filePath);
        image.setOriginalName(originalName);

        return reviewImageRepository.save(image);
    }

    }
