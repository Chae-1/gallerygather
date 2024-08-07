import Home from "@/layout/Home.vue";
import HomeMain from "@/views/HomeMain.vue";
import Review from "@/views/Review.vue";
import PageExhibitionDetail from "@/views/PageExhibitionDetail.vue";
import MypageView from '@/views/MypageView.vue'
import Login from "@/views/Login.vue";
import PageReviewWrite from "@/views/PageReviewWrite.vue";
import PageReviewDetail from "@/views/PageReviewDetail.vue";
import JoinForm from "@/layout/components/main/JoinForm.vue";

const homeRoute = {
    path: '/',
    name: 'home',
    redirect: '/main',
    component: Home,
    children: [

        {
            path: '/join',
            component: JoinForm
        },

        {
            path: '/main',
            component: HomeMain,

        },
        {
            path: '/review',
            component: Review,
            
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
        },
        {
            path: '/exhibitWrite',
            component: PageReviewWrite
        }

    ]
};

export default homeRoute;
