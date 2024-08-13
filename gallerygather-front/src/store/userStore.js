/*
* 작성자: 채형일
*/
import { defineStore } from 'pinia'
import axios from 'axios'

export const userStore = defineStore({
  id: 'user',

  state: () => ({
    email: 'not login',
    nickName: '',
    auth: localStorage.getItem('accessToken') !== null,
    prevPageUrl: ''
  }),

  getters: {
    isAuthenticated(state) {
      return state.auth
    }
  },

  actions: {
    setUserInfo(successfulUserInfo) {
      const accessToken = successfulUserInfo.accessToken
      const nickName = successfulUserInfo.nickName
      const email = successfulUserInfo.email
      const refreshToken = successfulUserInfo.refreshToken

      localStorage.setItem('accessToken', 'Bearer ' + accessToken) // 만료될 수 있음 -> 5분 짜리
      localStorage.setItem('refreshToken', refreshToken) // refreshToken -> 1시간
      this.$patch({
        email: email,
        nickName: nickName,
        auth: localStorage.getItem('accessToken') !== null
      })
    },

    clearUserInfo() {
      localStorage.clear()
      this.$patch({
        email: null,
        nickName: null,
        auth: null
      })
    },
  },

  loginCheck() {
    return this.auth
  },

  getUser() {
    return this.email
  }
});