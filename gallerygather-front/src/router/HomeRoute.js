import Home from '@/layout/Home.vue'
import HomeMain from '@/views/HomeMain.vue'
import Review from '@/views/Review.vue'
import PageExhibitionDetail from '@/views/PageExhibitionDetail.vue'
import MypageView from '@/views/MypageView.vue'
import Login from '@/views/Login.vue'
import PageReviewDetail from '@/views/PageReviewDetail.vue'
import JoinForm from '@/layout/components/main/JoinForm.vue'
import MypageViewCompo1 from '@/layout/components/mypage/MypageViewCompo1.vue'
import MypageViewCompo2 from '@/layout/components/mypage/MypageViewCompo2.vue'
import MypageViewCompo3 from '@/layout/components/mypage/MypageViewCompo3.vue'
import MypageViewCompo4 from '@/layout/components/mypage/MypageViewCompo4.vue'
import JoinSuccessComment from "@/layout/components/main/JoinSuccessComment.vue";
import ReviewDetailCompo from '@/layout/components/review/ReviewDetailCompo.vue'

const homeRoute = {
  path: '/',
  name: 'home',
  redirect: '/main',
  component: Home,
  children: [
    {
      path: '/join',
      component: JoinForm,
    },

    {
      name: 'success',
      path: '/join/success',
      component: JoinSuccessComment
    },

    {
      path: '/main',
      component: HomeMain
    },
    {
      path: '/exhibitiondetails/:exhibitionId/review',
      name: 'ReviewWrite',
      component: Review
    },
    {
      path: '/exhibitiondetails/:exhibitionId/reviewdetails/:reviewId',
      name: 'ReviewDetail',
      component: ReviewDetailCompo
    },
    {//유은
      path: '/api/exhibition/:exhibitionId',
      name: 'ReviewDetailReply',
      component: ReviewDetailCompo
    },
    {
      path: '/reviewdetails/:reviewId',
      component: PageReviewDetail
    },
    {
      path: '/exhibitiondetails/:exhibitionId',
      name: 'ExhibitionDetails',
      component: PageExhibitionDetail
    },
    {
      path: '/mypage',
      component: MypageView,
      // redirect: '/mypage/mypagecompo',  // 기본 페이지를 MypageViewCompo1로 설정
      children: [
        { path: '/mypagecompo', component: MypageViewCompo1 }, // 마이페이지
        { path: '/mylikecompo', component: MypageViewCompo2 }, // 마이페이지
        { path: '/myreviewcompo', component: MypageViewCompo3 }, // 마이페이지
        { path: '/myreplycompo', component: MypageViewCompo4 } // 마이페이지
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    }
  ]
}

export default homeRoute
