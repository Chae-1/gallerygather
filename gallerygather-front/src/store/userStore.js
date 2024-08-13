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
  }),

  getters: {
    isAuthenticated(state) {
      return state.auth;
    }
  },

  actions: {
    logout() {
      // 서버에 logout을 호출

      localStorage.removeItem('accessToken')
      localStorage.removeItem('refreshToken')

      this.$patch({
        name: null,
        email: null,
        auth: localStorage.getItem('accessToken') !== null
      })
    },

    setUserInfo(successfulUserInfo) {
      const accessToken = successfulUserInfo.accessToken;
      const nickName = successfulUserInfo.nickName;
      const email = successfulUserInfo.email;
      const refreshToken = successfulUserInfo.refreshToken;

      localStorage.setItem('accessToken', 'Bearer ' + accessToken); // 만료될 수 있음 -> 5분 짜리
      localStorage.setItem('refreshToken', refreshToken); // refreshToken -> 1시간
      this.$patch({
        email: email,
        nickName: nickName,
        auth: localStorage.getItem('accessToken') !== null,
      })
    },

    async login(email, password) {
      return await axios.post('http://localhost:8080/api/members/auth/login', {
        email, password
      }, {
        responseType: 'json'
      }).then(response => {
        this.setUserInfo(response.data);
        return response;
      }).catch((error) => {
        console.log(error)
        throw error
      })
    },

    async reLogin() {
      return await axios.post('http://localhost:8080/api/members/auth/refresh', {
        refreshToken: localStorage.getItem('refreshToken'),
      }).then(response => {
        console.log("재 로그인 요청")
        this.setUserInfo(response.data);
        return response;
      }).catch(error => {
        // 에러가 발생한다면 유효하지 않은 refreshToken, 해당 요청이 결국 실패한다.
        console.log(error.data);
        throw error;
      })
    },
    
    loginCheck() {
      return this.auth;
    },

    getUser() {
      return this.email;
    }
  },
  

})