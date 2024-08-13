import { createRouter, createWebHistory } from 'vue-router'
import homeRoute from '@/router/HomeRoute.js'
import introRoute from '@/router/IntroRoute.js'
import { prevPathStore } from '@/store/prevStore.js'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    homeRoute, introRoute
  ]
});

router.beforeEach((to, from, next) => {
  const routerStore = prevPathStore();
  if (from.fullPath) {
    routerStore.setPreviousRoute(from.fullPath);
  }
  console.log('Previous Route:', routerStore.previousRoute); // Pinia 스토어에서 이전 경로 확인
  next();
});


export default router;
