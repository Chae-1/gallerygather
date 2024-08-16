<!-- 작성자: 채형일-->

import { defineStore } from 'pinia'

export const prevPathStore = defineStore('router', {
  state: () => ({
    previousRoute: ''
  }),
  actions: {
    setPreviousRoute(path) {
      if (!path.includes('/login')) {
        this.previousRoute = path;
      }
    }
  },
  getters: {
    getPreviousRoute: (state) => {
      return state.previousRoute;
    }
  }
});