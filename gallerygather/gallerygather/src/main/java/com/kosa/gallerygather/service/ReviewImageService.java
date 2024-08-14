package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ReviewImageRequestDto;
import com.kosa.gallerygather.repository.ReviewImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ReviewImageService {

    private final String uploadDir = "uploads/"; // 이미지를 저장할 디렉토리

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Transactional
    public ReviewImageRequestDto saveImage(MultipartFile multipartFile) throws IOException {
        File uploadDir = new File(this.uploadDir);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }
        // 파일명 생성
        String originalName = multipartFile.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + "_" + originalName; // uploads 폴더에 생기는 파일 이름
        String filePath = uploadDir.getAbsolutePath() + File.separator + filename;
        String responsePath = "/uploads/" + filename;

        // 파일을 서버에 저장 (이 시점에 파일이 실제로 uploads 폴더에 저장됩니다)
        File file = new File(filePath);
        multipartFile.transferTo(file);

        return new ReviewImageRequestDto(originalName, responsePath);
    }

    }
