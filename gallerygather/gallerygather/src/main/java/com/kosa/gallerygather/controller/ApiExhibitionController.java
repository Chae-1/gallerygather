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
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/exhibitions")
public class ApiExhibitionController {
    private final ExhibitionService exhibitionService;

    @GetMapping
    public ResponseEntity<Page<ExhibitionCardDto>> getCards(@ModelAttribute PageRequestDto page) {
        return ResponseEntity.ok(exhibitionService.getCardDto(page));
    }

}
