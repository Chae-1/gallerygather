import Home from "@/layout/Home.vue";
import HomeMain from "@/views/HomeMain.vue";
import Review from "@/views/Review.vue";
import PageExhibitionDetail from "@/views/PageExhibitionDetail.vue";
import Login from "@/views/Login.vue";
import PageReviewWrite from "@/views/PageReviewWrite.vue";

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
            component: Review,
            
        },

        {
            path: '/exhibitiondetails',
            component: PageExhibitionDetail
        },
        {
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
