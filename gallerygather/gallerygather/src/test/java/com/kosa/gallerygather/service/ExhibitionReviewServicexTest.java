package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.ExhibitionLikeDto;
import com.kosa.gallerygather.dto.ExhibitionReviewDto;
import com.kosa.gallerygather.dto.ExhibitionReviewReplyDto;
import com.kosa.gallerygather.dto.PageRequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
public class ExhibitionReviewServicexTest {


    @Autowired
    private ExhibitionReviewReplyService exhibitionReviewReplyService;
//    private ExhibitionLikeService exhibitionLikeService;
//    private ExhibitionService exhibitionService;

//    @Autowired
//    private ExhibitionReviewService exhibitionReviewService;

    @Test
    @Transactional
    public void getAllreplies() {
        Pageable pageable = PageRequest.of(1, 3, Sort.by("regDate").ascending());
        Page<ExhibitionReviewReplyDto.ExhibitionReviewReplyResponseDto> pagelist = exhibitionReviewReplyService.findAllRepliesAboutReview(3L, pageable);
        pagelist.forEach(list -> System.out.println(list.getReplyContent()));
    }

//    @Test
//    @Transactional
//    public void increaseCntGetExhibit(){
//        exhibitionService.enterExhibitionDetail(1L);
//    }

//    @Test
//    public void increaseLikeTest() {
//        ExhibitionLikeDto.RequestExhibitionLike dto= new ExhibitionLikeDto.RequestExhibitionLike(1L, 1L);
//        exhibitionLikeService.clickExhibitionLike(true, dto);
//    }

//    @Test
//    @Transactional
//    public void test() {
//        PageRequestDto pageRequestDto = new PageRequestDto(2, 1);
//        List<ExhibitionReviewDto.RequestReviewList> list = exhibitionReviewService.getExhibitionReviews(1L, pageRequestDto);
//        for (ExhibitionReviewDto.RequestReviewList review : list) {
//            System.out.println(review.getTitle());
//        }
//    }
}
