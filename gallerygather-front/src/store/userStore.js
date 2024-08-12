/*
* 작성자: 채형일
*/
import {defineStore} from "pinia";
import axios from "axios";

async function requestLogin(email, password) {
}


export const userStore = defineStore({
    id: 'user', state: () => ({
        email: 'not login',
        accessToken: sessionStorage.getItem('accessToken'),
        nickName: '',
        refreshToken: sessionStorage.getItem('refreshToken')
    }), getters: {
        isAuthenticated(state) {
            return state.accessToken !== null;
        }
    }, actions: {
        logout() {
            // 서버에 logout을 호출
            this.$patch({
                name: null, accessToken: null,
            });
            sessionStorage.setItem('accessToken', null);
            axios.post("http://localhost:8080/api/members/logout", {}, {
                headers: {
                    Authorization: localStorage.getItem('accessToken'),
                }
            })
        },

        async login(email, password) {
            return await axios.post("http://localhost:8080/api/members/auth/login", {
                email, password
            }, {
                responseType: "json"
            }).then(response => {
                console.log(response);
                const accessToken = response.data.accessToken;
                const nickName = response.data.nickName;
                const email = response.data.email;
                localStorage.setItem('accessToken', "Bearer " + accessToken);
                localStorage.setItem('refreshToken', response.data.refreshToken);
                this.$patch({
                    email: email,
                    accessToken: accessToken,
                    nickName: nickName
                });
                return response;
            }).catch((error) => {
                console.log(error);
                throw error;
            });
        },

        async reLogin() {
            return await axios.post("http://localhost:8080/api/members/login", {

            })
        }
    }

});