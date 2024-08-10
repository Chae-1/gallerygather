/*
* 작성자: 채형일
*/
import {defineStore} from "pinia";
import axios from "axios";

async function requestLogin(email, password) {
}


export const userStore = defineStore({
    id: 'user', state: () => ({
        email: 'not login', accessToken: sessionStorage.getItem('accessToken'), nickName: '',
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
        },

        async login(email, password) {
            return await axios.post("http://localhost:8080/api/members/login", {
                email, password
            }, {
                responseType: "json"
            }).then(response => {
                console.log(response);
                const accessToken = response.data.accessToken;
                const nickName = response.data.nickName;
                const email = response.data.email;
                localStorage.setItem('accessToken', accessToken);
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
    }

});