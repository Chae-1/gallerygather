<template>
  <div class="mypage">
    <h3>마이페이지 입니다</h3>
<!--    <form @submit.prevent="handleSubmit">-->
      <button @click="logout" >로그아웃</button><br>
    닉네임 :   <input type="text" v-model="memberid"  /><br>
    <button>패스워드 변경</button><br>
    이름 : <input type="text" /><br>
      생년월일 : <input type="text" /><br>
      주소 :  <input type="text" /><br>
      이메일주소 :  <input type="text" /><br>
      <button type="submit">전체 저장 </button><br>
      마지막수정날짜

<!--    </form>-->
</div>
</template>
<script>
export default {
  data() {
    return {
      memberid: "현재 로그인된 id",
      memberpw: ""
    };
  },
  methods: {

      }
};
</script>

<style scoped>

</style>