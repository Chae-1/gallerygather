import Home from "@/layout/Home.vue";
import HomeMain from "@/views/HomeMain.vue";
import Review from "@/views/Review.vue";

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
            path: 'review',
            component: Review,
            
        },
    ]
};

export default homeRoute;
