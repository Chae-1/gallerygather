<template>
  <div class="home-main">
    <HomeMainCarousel />

    <article class="main-content">
      <HomeMainConditionSearchBar :search="search" :conditions="conditions"
                                  :totalElements="totalElement"
                                  @onClickSortOption="selectSortOptionAndInput" />
      <div>
        <CardComponent :currentPage="currentPage" :cards="cardItems" :perPage="perPage" @onPageClick="updatePageNum" />
        <div class="mt-3">
          <b-pagination v-model="selectedNum"
                        :per-page="perPage"
                        @page-click="updatePageNum"
                        pills :total-rows="totalElement"
                        size="lg" align="center">
          </b-pagination>
        </div>
      </div>
    </article>
  </div>
</template>

<script>

import { defineComponent } from 'vue'
import HomeMainCarousel from '@/layout/components/main/HomeMainCarousel.vue'
import HomeMainConditionSearchBar from '@/layout/components/main/HomeMainConditionSearchBar.vue'
import CardComponent from '@/layout/components/main/CardComponent.vue'
import { apiRequest } from '@/util/RequestUtil.js'

export default {
  data() {
    return {
      totalElement: 0,
      currentPage: 0,
      perPage: 12,
      cardItems: [],
      conditions: '',
      search: ''
    }
  },

  computed: {
    selectedNum() {
      return this.currentPage + 1;
    }
  },

  components: { CardComponent, HomeMainConditionSearchBar, HomeMainCarousel },

  methods: {
    fetchNewItems(sortOption = null, searchContent = null) {
      let url = `http://192.168.230.3:8080/api/exhibitions?page=${this.currentPage}&size=${this.perPage}&sort=startDate,desc`
      sortOption = sortOption != null ? 'sort=' + sortOption + ',desc' : ''
      if (sortOption !== null && sortOption !== '') {
        url += '&' + sortOption
      }
      searchContent = searchContent != null ? 'title=' + searchContent : '';
      if (searchContent !== null && searchContent !== null) {
        url += '&' + searchContent;
      }

      apiRequest('get', url, null)
        .then(response => {
          console.log(response)
          this.cardItems = response.data.content
          console.log('데이터 전체 개수: ' + response.data.totalElements)
          this.totalElement = response.data.totalElements
          console.log(response)
        })
        .catch(ex => {
          console.log(ex)
        })
    },

    updatePageNum(pageEvent, pageNo) {
      this.currentPage = pageNo - 1;
      this.fetchNewItems()
    },

    selectSortOptionAndInput(sortOption, input) {
      this.selectedButton = sortOption;
      console.log('이벤트 정상 호출 : ', sortOption, input);
      this.fetchNewItems(sortOption, input);
    }

  },


  created() {
    this.fetchNewItems()
  }

}
</script>

<style>

.home-main {
  max-width: 1920px;
  width: 100%;
  margin: 0 auto;

}

.main-content {
  padding: 50px;
  min-height: 1024px;
  display: flex;
  flex-direction: column;
}


</style>