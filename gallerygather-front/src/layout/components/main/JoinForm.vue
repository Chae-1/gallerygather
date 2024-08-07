<script>
import flatpickr from 'flatpickr';
import 'flatpickr/dist/flatpickr.min.css';
import 'flatpickr/dist/l10n/ko.js';
import axios from "axios";

export default {
  data() {
    return {
      name: '',
      email: '',
      password: '',
      passwordValid: '',
      dateOfBirth: '',
      errorMessage: '에러 메시지',
      showErrormessage: true
    }
  },

  mounted() {
    flatpickr("#date-picker", {
      dateFormat: "Y-m-d",
      minDate: "1900-01-01",
      maxDate: "2099-12-31",
      defaultDate: "2000-01-01",
      locale: "ko",
      // Customize options here if needed
    });
  },

  methods: {
    submitForm() {
      // valid
      console.log(this.dateOfBirth);

      if (this.password !== this.passwordValid) {
        this.errorMessage = '비밀번호가 일치하지 않습니다.';
        return ;
      }

      axios.post('http://localhost:8080/api/members/join', {
        email: this.email,
        name: this.name,
        password: this.password,
        dateOfBirth: this.dateOfBirth
      }, {
        responseType: "json",
      }).then((response) => {
        console.log(response);
        response.status;
      }).catch((error) => {
        console.log(error);
      });

    },
  }
}
</script>

<template>
  <div>
    <div class="join">
      <div class="error-message">
        <p>{{ errorMessages }}</p>
      </div>
      <form method="post" @submit.prevent="submitForm">
        <span>이름</span>
        <input type="text" v-model="name" placeholder="이름을 적어주세요" autocomplete="false">
        <span>이메일</span>
        <input type="email" v-model="email" placeholder="이메일을 적어주세요" autocomplete="false">
        <span>비밀번호</span>
        <input type="password" v-model="password" placeholder="패스워드를 적어주세요" autocomplete="false">
        <span>비밀번호 화인</span>
        <input type="password" v-model="passwordValid" placeholder="패스워드를 확인해주세요" autocomplete="false">
        <span>생년월일</span>
        <input type="text" v-model="dateOfBirth" id="date-picker" placeholder="생년월일을 선택해주세요" autocomplete="off"
               required>
        <div class="join-bottom">
          <button type="submit">회원가입</button>
        </div>
      </form>
    </div>
  </div>

</template>

<style>
@import url('https://fonts.googleapis.com/css?family=Raleway&display=swap');

a {
  text-decoration: none;
  color: #222;
}

.join {
  max-width: 1220px; /* 줄여서 가독성 향상 */
  background-color: #f5f5f5;
  border: 1px solid #eee;
  border-radius: 5px;
  padding: 25px;
  box-sizing: border-box;
  box-shadow: 0 0 25px rgba(0, 0, 0, 0.2);
  margin: 10% auto;
  font-size: 1.5rem;
}

.join span {
  font-weight: bold;
  display: block;
  margin-top: 20px;
}


.join input {
  font-size: 1.5rem;
}

.join input[type=email],
.join input[type=password],
.join input[type=text],
.join input[type=date] {
  width: 100%;
  padding: 15px;
  box-sizing: border-box;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding-left: 10px; /* padding-left 조정 */
}

.join input[type=email]:hover,
.join input[type=password]:hover,
.join input[type=text]:hover,
.join input[type=date]:hover {
  border: 1px solid dodgerblue;
  box-shadow: 0 0 5px dodgerblue;
}

.join input[type=email],
.join input[type=text],
.join input[type=password],
.join input[type=date] {
  background: #fff;
}

.join input[type=email]::placeholder,
.join input[type=password]::placeholder,
.join input[type=text]::placeholder,
.join input[type=date]::placeholder {
  opacity: 1;
  transition: 0.3s;
}

.join input[type=email]:focus::placeholder,
.join input[type=password]:focus::placeholder,
.join input[type=text]:focus::placeholder,
.join input[type=date]:focus::placeholder {
  opacity: 0;
  visibility: hidden;
}

.join p {
  overflow: hidden;
}

.join p label {
  float: left;
  cursor: pointer;
}

.join p a {
  float: right;
}

.join p a:hover {
  text-decoration: underline;
}

.join button {
  background-color: #2991b1;
  color: #fff;
  width: 100%; /* 버튼 너비 조정 */
  padding: 10px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  font-size: 1.5rem; /* 폰트 사이즈 조정 */
  transition: 0.3s;
}

.join button:hover {
  background-color: #2c778e;
}

.join-bottom {
  display: flex;
  justify-content: center;
  padding: 30px 0;
}

.error-message {
  display: flex;
  justify-content: center;
}

.error-message > p {
  color: red;
  font-weight: 700;
}

</style>
