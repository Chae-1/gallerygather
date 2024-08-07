import Home from "@/layout/Home.vue";
import HomeMain from "@/views/HomeMain.vue";
import Review from "@/views/Review.vue";
import PageExhibitionDetail from "@/views/PageExhibitionDetail.vue";
import MypageView from '@/views/MypageView.vue'
import Login from "@/views/Login.vue";
import PageReviewDetail from "@/views/PageReviewDetail.vue";
import ReviewDetail from "@/views/ReviewDetail.vue";

const homeRoute = {
    path: '/',
    name: 'home',
    redirect: '/main',
    component: Home,
    children: [
        {
            path: '/main',
            component: HomeMain,

        },
        {
            path: '/review',
            name: 'ReviewWrite',
            component: Review,
            
        },
        {
            path: '/review/detail',
            name: 'ReviewDetail',
            component: ReviewDetail,

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
        },{
            path: '/login',
            name: 'login',
            component: Login,
        }

    ]
};

export default homeRoute;
