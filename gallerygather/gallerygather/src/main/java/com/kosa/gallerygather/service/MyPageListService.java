package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ExhibitionReviewResponseDto;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.repository.MyPageReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyPageListService {

    // MyPageReviewRepository 주입
    private final MyPageReviewRepository myPageReviewRepository;

    // 생성자 주입
    public MyPageListService(MyPageReviewRepository myPageReviewRepository) {
        this.myPageReviewRepository = myPageReviewRepository;
    }

    // 멤버의 이메일로 모든 리뷰를 조회하는 메소드
    public List<ExhibitionReviewResponseDto> getReviewsByMemberEmail(String email) {

        // 주입된 MyPageReviewRepository를 사용하여 리뷰 엔티티 목록을 조회합니다.
        List<ExhibitionReview> reviews = myPageReviewRepository.findByMemberEmail(email);

        // 리뷰 DTO를 담을 리스트를 생성합니다.
        List<ExhibitionReviewResponseDto> responseDtos = new ArrayList<>();

        // 각 리뷰 엔티티를 DTO로 변환하여 리스트에 추가합니다.
        for (ExhibitionReview review : reviews) {
            ExhibitionReviewResponseDto dto = new ExhibitionReviewResponseDto(
                    review.getTitle(), // 리뷰 제목
                    review.getContent(), // 리뷰 내용
                    review.getRating() // 리뷰 평점
            );
            responseDtos.add(dto); // 변환된 DTO를 리스트에 추가합니다.
        }

        // DTO 리스트를 반환합니다.
        return responseDtos;
    }
}
