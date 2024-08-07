<template>
  <div class="home-main">
    <HomeMainCarousel />

    <article class="main-content">
      <HomeMainConditionSearchBar />
      <div>
        <CardComponent />
      </div>
    </article>
  </div>
</template>

<script>

import { defineComponent } from "vue";
import HomeMainCarousel from "@/layout/components/main/HomeMainCarousel.vue";
import HomeMainConditionSearchBar from "@/layout/components/main/HomeMainConditionSearchBar.vue";
import CardComponent from "@/layout/components/main/CardComponent.vue";

export default defineComponent({
  components: {CardComponent, HomeMainConditionSearchBar, HomeMainCarousel}
})
</script>

<style>


.home-main {
  max-width: 1920px;
  width: 100%;
  margin: 0 auto;

}


.main-content {
  padding: 50px;
  display: flex;
  flex-direction: column;
}

</style>