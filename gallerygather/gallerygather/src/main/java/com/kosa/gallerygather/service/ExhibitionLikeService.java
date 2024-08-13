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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExhibitionLikeService {

    private final ExhibitionLikeRepository exhibitionLikeRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final MemberService memberService;
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

    //유은 - 좋아요한 모든 전시 가져오기
    @Transactional
    public List<Exhibition> getLikedExhibitionsForCurrentMember() {
        // 현재 로그인된 사용자의 memberId를 가져옴
        Long memberId = memberService.getCurrentMemberId();

        // 가져온 memberId를 사용하여 좋아요한 전시 목록을 조회하고 반환
        return exhibitionLikeRepository.findLikedExhibitionsByMemberId(memberId);
    }

    //유은 - 좋아요한 모든 전시 갯수 가져오기
    @Transactional
    public int getLikeCountForCurrentMember() {
        // 현재 로그인된 사용자의 memberId를 가져옴
        Long memberId = memberService.getCurrentMemberId();
        System.out.println("좋아요에서 가져온 사용자 id"+memberId);

        // 해당 사용자가 좋아요한 전시회의 수를 반환
        return exhibitionLikeRepository.countByMemberId(memberId);
    }

}
