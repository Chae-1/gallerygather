<!--유은-->
<template>
  <div class="navi-bar">
    <ul>
      <li>
        <h4><router-link to="/mypage/mypagecompo">내 정보</router-link></h4>
      </li>
      <li>
        <h4><router-link to="/mypage/mylikecompo">좋아요</router-link></h4>
      </li>
      <li>
        <h4><router-link to="/mypage/myreviewcompo">리뷰</router-link></h4>
      </li>
      <li>
        <h4><router-link to="/mypage/myreplycompo">댓글</router-link></h4>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'MypageNaviCompo'
}
</script>

<style scoped>
.navi-bar{
  background-color: darkslategrey;
}
ul {
  margin-top: 180px;
}

ul li {
    margin-top: 30px;
    margin-bottom: 50px;
    margin-left: 50px;
}
h4 {
  font-size: 20px;
}
ul li a {
    text-decoration: none; /* 밑줄 제거 */
    color: white; /* 링크 색상 변경 (원하는 색상으로 변경 가능) */
}

ul li a:hover {
    color: darkcyan; /* 링크 호버 시 색상 변경 (원하는 색상으로 변경 가능) */
}
</style>
