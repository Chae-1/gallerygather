import Home from "@/layout/Home.vue";
import HomeMain from "@/views/HomeMain.vue";
import PageExhibitionDetail from "@/views/PageExhibitionDetail.vue";
import Login from "@/views/Login.vue";

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
            path: '/exhibitiondetails',
            component: PageExhibitionDetail
        },
        {
            path: '/login',
            name: 'login',
            component: Login,
        }
    ]
};

export default homeRoute;
