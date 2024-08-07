<script>
export default {
  name: "Login.vue",
  methods: {
    login() {
      axios.get()
    }
  }
}
</script>

<template>
  <div>
    <form class="login" method="post" action="http://localhost:8080/api/users/login" @submit="login(form)">
      <span>이메일</span>
      <input type="email" placeholder="Email Address">
      <span>비밀번호</span>
      <input type="password" placeholder="Password">
      <p>
        <label>
          <input type="checkbox"> 로그인 지속
        </label>
        <a href="#none">비밀번호를 잊으셨나요?</a>
      </p>
      <button type="submit">로그인</button>
    </form>
  </div>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Raleway&display=swap');

body {
  font-family: 'Raleway', sans-serif;
  line-height: 1.5em;
  margin: 0;
  font-weight: 300;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  color: #222;
}
a {
  text-decoration: none;
  color: #222;
}

.login {
  max-width: 1220px;
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