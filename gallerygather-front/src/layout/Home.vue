<template>
  <div class="wrap">
    <HeaderCompo/>
    <div id="container">
      <router-view></router-view>
    </div>
    <FooterCompo/>
  </div>
</template>

<script>
import HeaderCompo from '@/layout/components/HeaderCompo.vue';
import FooterCompo from "@/layout/components/FooterCompo.vue";

export default {
  components: {
    HeaderCompo, FooterCompo
  }
}
</script>

<style>
  .wrap {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
  }

  #container {
    min-height: 90vh;
  }
</style>