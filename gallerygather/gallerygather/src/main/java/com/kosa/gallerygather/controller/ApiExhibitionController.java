package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.PageRequestDto;
import com.kosa.gallerygather.entity.ExhibitionCardDto;
import com.kosa.gallerygather.service.ExhibitionListUpdateService;
import com.kosa.gallerygather.service.ExhibitionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ApiExhibitionController {
    private final ExhibitionListUpdateService listUpdateService;
    private final ExhibitionService exhibitionService;

    @GetMapping
    public ResponseEntity<Page<ExhibitionCardDto>> getCards(@RequestBody PageRequestDto page) {
        return ResponseEntity.ok(exhibitionService.getCardDto(page));
    }

    @GetMapping("/api/exhibitions")
    public ResponseEntity<String> fetchExhibitionFromOther(Integer pageNo, Integer recordPerSession) {
        try {
            listUpdateService.callExhibitionUpdateRequest(recordPerSession, pageNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("ok");
    }
}
