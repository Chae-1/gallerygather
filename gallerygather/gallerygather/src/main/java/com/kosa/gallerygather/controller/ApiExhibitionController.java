package com.kosa.gallerygather.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ApiExhibitionController {

    @GetMapping("/api/exhibitions")
    public ResponseEntity<String> exhibition() {
        return ResponseEntity.ok("ok");
    }
}
