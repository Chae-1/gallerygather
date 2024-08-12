package com.kosa.gallerygather.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiReviewImageController {

    private final String uploadDir = "uploads/";

    @PostMapping("/api/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile file) {
        try{
            // 파일 이름 가져오기
            String fileName = file.getOriginalFilename();
            // 파일 경로 설정
            Path filePath = Paths.get(uploadDir + fileName);
            // 파일을 서버에 저장
            Files.copy(file.getInputStream(), filePath);

            // 저장된 이미지의 URL을 생성
            String imageUrl = "/uploads/" + fileName;
            Map<String, String> response = new HashMap<>();
            response.put("url", imageUrl);

            // 클라이언트에게 이미지 URL을 반환
            return ResponseEntity.ok(response);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


}
