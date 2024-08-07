/*
* 작성자: 채형일
*/
import {defineStore} from "pinia";
import axios from "axios";

/**
 *
 */

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

        async login(email, password) {
            const resp = await axios.post("http://localhost:8080/api/members/login", {
                email, password
            }, {
                responseType: "json"
            }).then(response => {
                console.log(response);
                this.$patch({
                    email,
                    token: response.data.token // assuming the token is returned in the response
                });
            }).catch((error) => {
            });
        },
    }

});