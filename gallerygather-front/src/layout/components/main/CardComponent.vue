<script>
import axios from "axios";

export default {
  name: "CardComponent",
  data() {
    return {
      totalElement: 101,
      currentPage: 1,
      perPage: 10,
      cardItems: [],
      conditions: [],
    }
  },

  methods: {
    fetchNewItems() {
      axios.get(`http://localhost:8080/api/exhibitions?pageNo=${this.currentPage}&perPage=${this.perPage}`)
          .then(response => {
            this.cardItems = response.data.content;
            this.totalElement = response.data.totalElements;
            console.log(response);
          })
          .catch(ex => {
            console.log(ex);
          })
    },
    updatePageNum(pageEvent, pageNo) {
      this.currentPage = pageNo;
    }
  },
  created() {
    this.fetchNewItems();
  }
}
</script>

<template>
  <b-card-group deck>
    <b-card v-for="item in cardItems" :title="item.title" style="max-width: 20rem; flex: 25%;" img-width="400px"
            img-height="400px"
            :img-src="item.imageUrl" img-alt="" key="" img-top>
      <b-card-text>
        {{ item.description.slice(0, 30) }}
      </b-card-text>
    </b-card>
  </b-card-group>

  <div class="mt-3">
    <h6>Large Pills</h6>
    <b-pagination v-model="currentPage"
                  :per-page="perPage"
                  @page-click="fetchNewItems"
                  pills :total-rows="totalElement"
                  size="lg" align="fill">
    </b-pagination>
  </div>

</template>

<style scoped>
</style>