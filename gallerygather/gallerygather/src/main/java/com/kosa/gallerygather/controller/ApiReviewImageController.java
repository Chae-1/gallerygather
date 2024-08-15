package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.ReviewImageRequestDto;
import com.kosa.gallerygather.service.ReviewImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class ApiReviewImageController {

    @Autowired
    private ReviewImageService reviewImageService;

    @PostMapping("/api/uploads")
    public ResponseEntity<ReviewImageRequestDto> uploadImage(@RequestParam("image") MultipartFile image){

        try{
            ReviewImageRequestDto imgDto = reviewImageService.saveImage(image);
            return ResponseEntity.ok(imgDto);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
