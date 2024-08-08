package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.PageRequestDto;
import com.kosa.gallerygather.entity.ExhibitionCardDto;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExhibitionService {
    private final ExhibitionRepository exhibitionRepository;

    public Page<ExhibitionCardDto> getCardDto(PageRequestDto pageRequestDto) {
        PageRequest pageRequest = PageRequest.of(pageRequestDto.getPageNo(), pageRequestDto.getPagePer(), Sort.by("startDate")
                .ascending());
        return exhibitionRepository.fetchExhibitionsWithPagination(pageRequest)
                .map(exhibition -> new ExhibitionCardDto(exhibition.getImgUrl(),
                        exhibition.getTitle(), exhibition.getDescription()));
    }
}
