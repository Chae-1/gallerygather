<template>
  <div>
    <ul>
      <li>
        <h4><router-link to="/mypagecompo">page</router-link></h4>
      </li>
      <li>
        <h4><router-link to="/mylikecompo">like</router-link></h4>
      </li>
      <li>
        <h4><router-link to="/myreviewcompo">review</router-link></h4>
      </li>
      <li>
        <h4><router-link to="/myreplycompo">reply</router-link></h4>
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
/* 필요한 스타일 추가 */
</style>
