<script>
// 작성자: 채형일
import {userStore} from "@/store/userStore.js";
import extractUserInfoFrom from "@/util/jwtUtil.js";

export default {
  name: "Login.vue",
  data() {
    return {
      email: '',
      password: ''
    }
  },

  methods: {
    login() {
      console.log("login 요청 중");
      const store = userStore();
      store.login(this.email, this.password)
          .then(response => {
            console.log(response);
            const accessToken = response.data.accessToken;
            const nickName = response.data.nickName;
            const email = response.data.email;
            console.log(accessToken, nickName, email);
            this.$router.push('/main');
          })
          .catch((error) => console.log(error));
    }
  }

}
</script>

<template>
  <div>
    <form class="login" method="post">
      <span>이메일</span>
      <input type="email" v-model="email" placeholder="Email Address">
      <span>비밀번호</span>
      <input type="password" v-model="password" placeholder="Password">
      <p>
        <label>
          <input type="checkbox"> 로그인 지속
        </label>
        <a href="#none">비밀번호를 잊으셨나요?</a>
      </p>
      <button type="button" @click="login">로그인</button>
    </form>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Raleway&display=swap');

a {
  text-decoration: none;
  color: #222;
}

.login {
  max-width: 1920px;
  background-color: #f5f5f5;
  border: 1px solid #eee;
  border-radius: 5px;
  padding: 25px;
  box-sizing: border-box;
  box-shadow: 0 0 25px rgba(0, 0, 0, 0.2);
  margin: 10% auto;
}

.login span {
  font-weight: bold;
  display: block;
  margin-top: 20px;
}

.login input[type=email],
.login input[type=password] {
  width: 100%;
  padding: 15px;
  box-sizing: border-box;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding-left: 50px;
}

.login input[type=email]:hover,
.login input[type=password]:hover {
  border: 1px solid dodgerblue;
  box-shadow: 0 0 5px dodgerblue;
}

.login input[type=email] {
  background: #fff url(../assets/img/icon-email.png) no-repeat center left 10px;
}

.login input[type=password] {
  background: #fff url(../assets/img/icon-lock.png) no-repeat center left 10px;
}

.login input[type=email]::placeholder,
.login input[type=password]::placeholder {
  opacity: 1;
  transition: 0.3s;
}

.login input[type=email]:focus::placeholder,
.login input[type=password]:focus::placeholder {
  opacity: 0;
  visibility: hidden;
}

.login p {
  overflow: hidden;
}

.login p label {
  float: left;
  cursor: pointer;
}

.login p a {
  float: right;
}

.login p a:hover {
  text-decoration: underline;
}

.login button {
  background-color: #2991b1;
  color: #fff;
  width: 300px;
  padding: 10px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  font-size: 24px;
  transition: 0.3s;
}

.login button:hover {
  background-color: #2c778e;
}
</style>