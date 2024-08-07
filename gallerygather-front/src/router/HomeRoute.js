import Home from '@/layout/Home.vue'
import HomeMain from '@/views/HomeMain.vue'
import Review from '@/views/Review.vue'
import PageExhibitionDetail from '@/views/PageExhibitionDetail.vue'
import MypageView from '@/views/MypageView.vue'
import Login from '@/views/Login.vue'
import PageReviewWrite from '@/views/PageReviewWrite.vue'
import PageReviewDetail from '@/views/PageReviewDetail.vue'
import MypageViewCompo1 from '@/layout/components/mypage/MypageViewCompo1.vue'
import MypageViewCompo2 from '@/layout/components/mypage/MypageViewCompo2.vue'
import MypageViewCompo3 from '@/layout/components/mypage/MypageViewCompo3.vue'
import MypageViewCompo4 from '@/layout/components/mypage/MypageViewCompo4.vue'

const homeRoute = {
  path: '/',
  name: 'home',
  redirect: '/main',
  component: Home,
  children: [
    {
      path: '/main',
      component: HomeMain
    },
    {
      path: '/review',
      component: Review
    },
    {
      path: '/reviewdetails',
      component: PageReviewDetail
    },
    {
      path: '/exhibitiondetails',
      component: PageExhibitionDetail
    },
    {
      path: '/mypage',
      component: MypageView,
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
    },
    {
      path: '/exhibitWrite',
      component: PageReviewWrite
    }
  ]
}

export default homeRoute
