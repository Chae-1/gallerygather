<template>
  <HeaderCompo/>
  <div id="container">
      <router-view></router-view>
  </div>
  <FooterCompo/>
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
  #container {
    min-height: 90vh;
  }
</style>