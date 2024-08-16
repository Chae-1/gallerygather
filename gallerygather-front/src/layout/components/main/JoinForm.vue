<script>
import flatpickr from 'flatpickr'
import 'flatpickr/dist/flatpickr.min.css'
import 'flatpickr/dist/l10n/ko.js'
import axios from 'axios'

function canNotJoin(email) {
  let canJoin = true
  let result = axios.post('http://192.168.230.3:8080/api/members/check', {
    email: email
  }, {
    responseType: 'json'
  }).then((response) => {
    // 정상적인 응답이 오면, 회원 가입 확인 폼으로 이동
    canJoin = response.data.canJoin
    response.status
  }).catch((error) => {
    canJoin = false
    console.log(error)
  })
  // checkResult =
  return !canJoin
}

export default {
  data() {
    return {
      name: '',
      email: '',
      nickName: '',
      password: '',
      passwordValid: '',
      dateOfBirth: '',
      errorMessage: '이메일이 중복되었습니다.',
      showErrormessage: false
    }
  },

  mounted() {
    flatpickr('#date-picker', {
      dateFormat: 'Y-m-d',
      minDate: '1900-01-01',
      maxDate: '2099-12-31',
      defaultDate: '2000-01-01',
      locale: 'ko'
      // Customize options here if needed
    })
  },

  methods: {
    submitForm() {
      // valid
      console.log(this.dateOfBirth)
      if (this.password !== this.passwordValid) {
        this.errorMessage = '비밀번호가 일치하지 않습니다.'
        return
      }

      if (canNotJoin(this.email)) {
        this.errorMessage = '이미 가입된 회원입니다.'
        return
      }

      // 정상일 경우, 회원 가입 요청 시도
      axios.post('http://192.168.230.3:8080/api/members/join', {
        email: this.email,
        name: this.name,
        nickName: this.nickName,
        password: this.password,
        dateOfBirth: this.dateOfBirth
      }, {
        responseType: 'json'
      }).then((response) => {
        console.log(response)
        const joinedEmail = response.data.email
        const joinedName = response.data.name
        const joinedNickName = response.data.nickName
        const joinedDate = response.data.regDate

        this.$router.push({
          name: 'success',
          query: {
            joinedName: joinedName,
            joinedEmail: joinedEmail,
            joinedNickName: joinedNickName,
            joinedDate: joinedDate
          }
        })
      }).catch((error) => {
        console.log(error.response.data);
        this.errorMessage = error.response.data;
        this.showErrormessage = true
      })
    }
  }
}
</script>

<template>
  <div>
    <div class="join">
      <div class="error-message">
        <p v-if="showErrormessage">{{ errorMessage }}</p>
      </div>
      <form method="post" @submit.prevent="submitForm">
        <span>이름</span>
        <input type="text" v-model="name" placeholder="이름을 적어주세요" autocomplete="false">
        <span>닉네임</span>
        <input type="text" v-model="nickName" placeholder="닉네임을 적어주세요" autocomplete="false">
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
  width: 70%; /* 줄여서 가독성 향상 */
  background-color: #b8bab4;
  border: 1px solid #eee;
  border-radius: 5px;
  padding: 25px;
  box-sizing: border-box;
  box-shadow: 0 0 25px rgba(0, 0, 0, 0.2);
  margin: 10% auto;
  font-size: 1.2rem;
}

.join span {
  font-weight: bold;
  display: block;
  margin-top: 20px;
}


.join input {
  font-size: 1rem;
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
  border: 1px solid #669900;
  box-shadow: 0 0 5px #669900;
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
  background-color: #2c2a29;
  color: #669900;
  width: 100%; /* 버튼 너비 조정 */
  padding: 10px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  font-size: 1rem; /* 폰트 사이즈 조정 */
  transition: 0.3s;
}

.join button:hover {
  transform: scale(1.025);
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

@media screen and (max-width: 992px) {

  .join {
    max-width: 700px;
  }
}

@media screen and (max-width: 600px) {
  .join {
    max-width: 400px;
  }
}

.button-group {
  display: flex;
  justify-content: center;
}
</style>
