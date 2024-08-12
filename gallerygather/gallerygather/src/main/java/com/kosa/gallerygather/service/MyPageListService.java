package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.MyPageReplyListResponseDto;
import com.kosa.gallerygather.dto.MyPageReviewListResponseDto;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import com.kosa.gallerygather.repository.MyPageReplyRepository;
import com.kosa.gallerygather.repository.MyPageReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//유은
@Service
public class MyPageListService {

    // MyPageReviewRepository 주입
    private final MyPageReviewRepository myPageReviewRepository;

    // MyPageReplyRepository 주입(
    private final MyPageReplyRepository myPageReplyRepository;//생성자 주입과 사용하면 final필드를 초기화함 null예방


    // 생성자 주입
    public MyPageListService(MyPageReviewRepository myPageReviewRepository,
                             MyPageReplyRepository myPageReplyRepository) {
        this.myPageReviewRepository = myPageReviewRepository;
        this.myPageReplyRepository = myPageReplyRepository;
    }


    //멤버 이메일로 모든 댓글 조회
    public List<MyPageReplyListResponseDto> getMyPageReplyRepository(String email) {

        //주입된 레포지토리로 reply를 조회
        List<ExhibitionReviewReply> replys = myPageReplyRepository.findByMemberEmail(email);
        System.out.println("가져온 댓글 데이터: " + replys);

        //그릇
        List<MyPageReplyListResponseDto> replysDto = new ArrayList<>();

        //DTO로 변환, 리스트 추가
        for (ExhibitionReviewReply reply : replys) {
            MyPageReplyListResponseDto dto = new MyPageReplyListResponseDto(
                    reply.getReply(),
                    reply.getRegDate(),
                    reply.getUpdateDate(),
                    reply.getReplyReviewId(),
                    reply.getReviewTitle()
            );
            System.out.println("댓글에서 가져온 전시제목(서비스단): "+ reply.getReviewTitle());
            replysDto.add(dto); // 변환된 DTO를 리스트에 추가합니다.
        }

        return replysDto;
    }

    // 멤버의 이메일로 모든 리뷰를 조회하는 메소드
    public List<MyPageReviewListResponseDto> getReviewsByMemberEmail(String email) {

        // 주입된 ExhibitionReview 사용하여 리뷰 엔티티 목록을 조회합니다.
        List<ExhibitionReview> reviews = myPageReviewRepository.findByMemberEmail(email);
        System.out.println("가져온 리뷰 데이터: " + reviews);

        // 리뷰 DTO를 담을 리스트를 생성합니다.
        List<MyPageReviewListResponseDto> responseDtos = new ArrayList<>();

        // 각 리뷰 엔티티를 DTO로 변환하여 리스트에 추가합니다.
        for (ExhibitionReview review : reviews) {
            Exhibition exhibition = review.getExhibition();
            String exhibitionTitle = exhibition != null ? exhibition.getTitle() : "No Title";
            Long exhibitId = exhibition != null ? exhibition.getId() : null;

            MyPageReviewListResponseDto dto = new MyPageReviewListResponseDto(
                    review.getId(),
                    review.getTitle(), // 리뷰 제목
                    review.getContent(), // 리뷰 내용
                    review.getRating(),// 리뷰 평점
                   exhibitionTitle,// 전시 제목
                    exhibitId
            );
            System.out.println("가져온 전시제목(서비스): " + exhibitionTitle);
            System.out.println("가져온 전시번호(서비스): " + exhibitId);
            System.out.println("가져온 리뷰번호(서비스): " + review.getId());
            responseDtos.add(dto); // 변환된 DTO를 리스트에 추가합니다.
        }

        // DTO 리스트를 반환합니다.
        return responseDtos;
    }
}
