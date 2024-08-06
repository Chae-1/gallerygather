import { createRouter, createWebHistory } from 'vue-router'
import homeRoute from "@/router/HomeRoute.js";
import introRoute from "@/router/IntroRoute.js";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    homeRoute, introRoute
  ]
})

export default router
