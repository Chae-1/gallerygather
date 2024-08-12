package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ExhibitionCardDto;
import com.kosa.gallerygather.dto.ExhibitionDto;
import com.kosa.gallerygather.dto.PageRequestDto;
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
        PageRequest pageRequest = PageRequest.of(pageRequestDto
                .getPageNo() - 1, pageRequestDto.getPagePer(), Sort.by("startDate")
                .ascending());

        return exhibitionRepository.fetchExhibitionsWithPagination(pageRequest)
                .map(exhibition -> new ExhibitionCardDto(exhibition.getId(), exhibition.getImgUrl(),
                        exhibition.getTitle(), exhibition.getDescription(),
                        exhibition.getStartDate(), exhibition.getEndDate()));
    }


    // 작성자: 오지수
    // 전시 상세 정보 페이지
    public ExhibitionDto getExhibitionDetail(Long id) {
        return  exhibitionRepository.findById(id)
                .map(exhibition -> new ExhibitionDto(exhibition))
                .orElseGet(() -> new ExhibitionDto());
    }
}
