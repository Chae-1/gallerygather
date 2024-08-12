package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ExhibitionDto;
import com.kosa.gallerygather.dto.ExhibitionLikeDto;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionLike;
import com.kosa.gallerygather.repository.ExhibitionLikeRepository;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExhibitionLikeService {

    private final ExhibitionLikeRepository exhibitionLikeRepository;
    private final ExhibitionRepository exhibitionRepository;
    /*
    작성자: 오지수
    전시 like 추가 및 삭제
     */
    @Transactional
    public ExhibitionDto clickExhibitionLike(boolean ifLike, ExhibitionLikeDto.RequestExhibitionLike dto) {
        Exhibition exhibition = exhibitionRepository.findById(dto.getExhibitionId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전시입니다."));

        if (ifLike) { // 있으면 delete
            ExhibitionLike exhibitionLike = exhibitionLikeRepository.findExhibitionLikeByallId(dto.getMemberId(), dto.getExhibitionId())
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다."));
            exhibitionLikeRepository.delete(exhibitionLike);
            exhibition.decreaseLikeCount();

        } else { // 만약 없으면 insert
            exhibitionLikeRepository.save(ExhibitionLike.setExhibitionLike(dto));
            exhibition.increaseLikeCount();
        }
        return new ExhibitionDto(exhibition);
    }
}
