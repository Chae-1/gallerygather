package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.entity.ReviewImage;
import com.kosa.gallerygather.service.ReviewImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@RestController
public class ApiReviewImageController {

    @Autowired
    private ReviewImageService reviewImageService;

    @PostMapping("/api/uploads")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile image){

        try{
            ReviewImage savedImage = reviewImageService.saveImage(image);
            String imageUrl = "/uploads/" + savedImage.getOriginalName();
            Map<String, String> response = new HashMap<>();
            response.put("url", imageUrl);
            return ResponseEntity.ok(response);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
