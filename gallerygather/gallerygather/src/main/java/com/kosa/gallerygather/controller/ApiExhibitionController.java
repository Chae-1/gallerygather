package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.ExhibitionDto;
import com.kosa.gallerygather.dto.PageRequestDto;
import com.kosa.gallerygather.dto.ExhibitionCardDto;
import com.kosa.gallerygather.service.ExhibitionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
        log.info("{}", page);
        return ResponseEntity.ok(exhibitionService.getCardDto(page));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ExhibitionDto> getExhibitionDetails(@PathVariable Long id) {
        return ResponseEntity.ok(exhibitionService.getExhibitionDetail(id));
    }
}
