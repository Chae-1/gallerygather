<script>
import axios from "axios";

export default {
  name: "CardComponent",

  data() {
    return {
      totalElement: 101,
      currentPage: 1,
      perPage: 12,
      cardItems: [],
      conditions: [],
    }
  },

  methods: {
    fetchNewItems() {
      axios.get(`http://localhost:8080/api/exhibitions?pageNo=${this.currentPage}&pagePer=${this.perPage}`)
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

  updated() {
    this.fetchNewItems();
  },

  created() {
    this.fetchNewItems();
  }
}
</script>

<template>
  <b-card-group deck>
    <b-card v-for="item in cardItems" title-tag="h6"
            :title="item.title" style="max-width: 20rem; font-weight:700; margin: auto; flex: 25%;" img-width="400px"
            img-height="400px"
            :img-src="item.imageUrl" :key="item.exhibitionId" img-top>


      <template #footer>
        <div class="card-duration">
          <span>기간</span>
        </div>
        <div class="card-location">
          <span>장소</span>
        </div>
        <div class="card-info">
          <span>진행상태</span>
        </div>
      </template>
    </b-card>
  </b-card-group>

  <div class="mt-3">
    <h6>Large Pills</h6>
    <b-pagination v-model="currentPage"
                  :per-page="perPage"
                  @page-click="updatePageNum"
                  pills :total-rows="totalElement"
                  size="lg" align="fill">
    </b-pagination>
  </div>

</template>

<style>
.card-title {
  text-align: center;
  font-weight: bold;
}


.card-duration::before,
.card-location::before {
  width: 19px;
  height: 19px;
  content: '';
  display: inline-block;
}

.card-duration::before {
  background-image: url("../../../assets/img/calander.svg");
}

.card-location::before {
  background-image: url("../../../assets/img/location.svg");
}
.card-footer > div {
  margin: 10px 0;
}

.card-footer > div:nth-of-type(1) {
  margin-top: 0;
}
</style>