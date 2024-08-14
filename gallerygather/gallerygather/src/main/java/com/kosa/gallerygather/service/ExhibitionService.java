package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ExhibitionCardDto;
import com.kosa.gallerygather.dto.ExhibitionDto;
import com.kosa.gallerygather.dto.PageRequestDto;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.repository.ExhibitionLikeRepository;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import com.kosa.gallerygather.repository.ExhibitionSpecs;
import com.kosa.gallerygather.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExhibitionService {
    private final ExhibitionRepository exhibitionRepository;
    private final ExhibitionLikeRepository exhibitionLikeRepository;

    public Page<ExhibitionCardDto> getCardDto(Pageable pageable, String title) {
        return exhibitionRepository.findAll(ExhibitionSpecs.containsTitle(title), pageable)
                .map(exhibition -> new ExhibitionCardDto(exhibition.getId(), exhibition.getImgUrl(),
                        exhibition.getTitle(), exhibition.getDescription(),
                        exhibition.getStartDate(), exhibition.getEndDate(),
                        exhibition.getAvgRating(), exhibition.getPlace()));
    }


    // 작성자: 오지수
    public ExhibitionDto getExhibitionDetail(Long id) {
        return exhibitionRepository.findById(id)
                .map(exhibition -> new ExhibitionDto(exhibition))
                .orElseGet(() -> new ExhibitionDto());
    }

    /*
    작성자: 오지수
    readCount + 1 하고 전시 상세 페이지 가져가기
     */
    @Transactional
    public Map<String, Object> findExhibitionDetailWithLikes(Long exhibitonId, Long memberId) {
        Map<String, Object> exhibitionInfo = new HashMap<>();

        ExhibitionDto exhibitionDto = exhibitionRepository
                .findById(exhibitonId)
                .filter(Exhibition::increaseReadCount)
                .map(ExhibitionDto::new)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전시입니다."));
        exhibitionInfo.put("exhibition", exhibitionDto);

        System.out.println("memberId: " + memberId);
        if (memberId == null) {
            exhibitionInfo.put("isLoggedIn", false);
            exhibitionInfo.put("isLike", false);
            return exhibitionInfo;
        }

        exhibitionInfo.put("isLoggedIn", true);
        exhibitionInfo.put("isLike", exhibitionLikeRepository.existsByMemberIdAndExhibitionId(memberId, exhibitonId));

        return exhibitionInfo;
    }
}
