package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.PageRequestDto;
import com.kosa.gallerygather.entity.ExhibitionCardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExhibitionServiceTest {
    @Autowired
    ExhibitionService exhibitionService;

    @Test
    void getCardDto() {
        Page<ExhibitionCardDto> cardDto = exhibitionService.getCardDto(new PageRequestDto(10, 1));
        System.out.println(cardDto);

        System.out.println("cardDto.getTotalElements() = " + cardDto.getTotalElements());
        System.out.println("cardDto.getTotalPages() = " + cardDto.getTotalPages());
        for (ExhibitionCardDto exhibitionCardDto : cardDto.getContent()) {
            System.out.println("exhibitionCardDto = " + exhibitionCardDto);
        }
        System.out.println(cardDto.getNumberOfElements());
        System.out.println("cardDto.getSize() = " + cardDto.getSize());
    }
}