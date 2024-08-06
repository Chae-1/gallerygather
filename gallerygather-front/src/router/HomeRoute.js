import Home from "@/layout/Home.vue";
import HomeMain from "@/views/HomeMain.vue";

const homeRoute = {
    path: '/',
    name: 'home',
    redirect: '/main',
    component: Home,
    children: [
        {
            path: '/main',
            component: HomeMain,

        }
    ]
};

export default homeRoute;
