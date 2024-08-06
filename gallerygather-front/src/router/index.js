import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/layout/Home.vue";
import HomeMain from "@/views/HomeMain.vue"
import Intro from "@/layout/Intro.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: Home,
      children: [
        {
          path: '/main',
          component: HomeMain,
        }
      ]
    },
    {
      path: '/intro',
      name: 'intro',
      component: Intro
    }
  ]
})

export default router
