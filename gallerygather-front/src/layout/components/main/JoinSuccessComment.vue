<script>
export default {

  name: "JoinSuccessComment",
  computed: {
    // 라우트 파라미터를 computed property로 접근
    joinedName() {
      return this.$route.params.joinedName ?? 'asdasd';
    },
    joinedEmail() {
      return this.$route.params.joinedEmail ?? 'asdad';
    },
    joinedNickName() {
      return this.$route.params.joinedNickName ?? 'asdasd';
    },
    joinedDate() {
      return this.$route.params.joinedDate ?? 'adssad';
    }
  }
}
</script>

<template>
  <div>
    <div class="join">
      <div class="join-wellcome">가입을 환영합니다.</div>
      <span>이름</span>
      <div class="join-wellcome">{{joinedName}}</div>
      <span>이메일</span>
      <div class="join-wellcome">
        {{joinedEmail}}
      </div>
      <span>닉네임</span>
      <div class="join-wellcome">
        {{joinedNickName}}
      </div>
      <span>가입일자</span>
      <div class="join-wellcome">
        {{joinedDate}}
      </div>
      <div class="button-group">
        <span>
          <a href="/">
          홈으로
          </a>
        </span>
      </div>
    </div>

  </div>

</template>

<style scoped>
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

.join div {
  font-size: 1.5rem;
}
.button-group {
  text-align: center;
}

.error-message > p {
  color: red;
  font-weight: 700;
}

.join-wellcome {
  text-align: center;
  font-family: 'Arial', sans-serif;
  padding: 10px;
  background: white;
}

</style>
