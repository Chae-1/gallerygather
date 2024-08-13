<template>
  <div class="layout">
    <!-- 시맨틱구조를 사용하기위한 section사용 -->
    <section class="mypage">
      <mypage-navi-compo class="navi"></mypage-navi-compo>
      <mypage-view-compo-main class="main">
        <router-view></router-view> <!-- 디폴트 -->
      </mypage-view-compo-main>
    </section>
  </div>
</template>

<script>
import MypageNaviCompo from '../layout/components/mypage/MypageNaviCompo.vue'
import MypageViewCompoMain from '../layout/components/mypage/MypageViewCompoMain.vue'

export default {
  components: { MypageNaviCompo, MypageViewCompoMain }
}
</script>

<style scoped>
.mypage {
  display: flex; /* Flexbox 레이아웃을 사용하여 나란히 배치 */
  height: 100vh;
}

.navi {
  width: 300px; /* 네비게이션 바의 너비를 설정 */
  background-color: f9f6f3; /* 배경 색상 */
  flex-shrink: 0;
}

.main {
  flex-grow: 1; /* 나머지 공간을 차지 */
  padding: 20px; /* 여백 설정 */
}
</style>
