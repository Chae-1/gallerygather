package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.service.ExhibitionListUpdateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ApiExhibitionController {
    private final ExhibitionListUpdateService listUpdateService;

    @GetMapping("/api/exhibitions")
    public ResponseEntity<String> exhibition(Integer pageNo, Integer recordPerSession) {
        try {
            listUpdateService.updateExhibition(recordPerSession, pageNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("ok");
    }
}
