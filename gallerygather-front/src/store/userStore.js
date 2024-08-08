/*
* 작성자: 채형일
*/
import {defineStore} from "pinia";
import axios from "axios";

async function requestLogin(email, password) {
}


export const userStore = defineStore({
    id: 'user', state: () => ({
        email: 'not login', token: null,
    }), getters: {
        isAuthenticated(state) {
            return state.token !== null;
        }
    }, actions: {
        logout() {
            this.$patch({
                name: null, token: null,
            })
        },
        extractAndSetUserInfo(token) {
        },

        async login(email, password) {
            const resp = await axios.post("http://localhost:8080/api/members/login", {
                email, password
            }, {
                responseType: "json"
            }).then(response => {
                const token = response.data.token;

                const userInfo = extractAndSetUserInfo(token);
                localStorage.setItem('token', token);
                this.$patch({
                    email,
                    token: value // assuming the token is returned in the response
                });
            }).catch((error) => {
            });
        },
    }

});