<script>
export default {
  data() {
    return {
      toggle: false,
      buttons: [
        { value: 'reviewCount', label: '리뷰순' },
        { value: 'startDate', label: '최신순' },
        { value: 'readCount', label: '조회순' },
        { value: 'likeCount', label: '좋아요순' }
      ],
      selectedButton: null,
      content: null
    }
  },

  props: {
    totalElements: Number
  },

  methods: {
    toggleDetailSearchMenu() {
      this.toggle = !this.toggle
    },

    handleSearch(sortOption = null, searchContent = null) {
      this.selectedButton = sortOption;
      console.log(this.selectedButton, this.searchContent);
      this.$emit('onClickSortOption', this.selectedButton, this.content);

    }
  }
}
</script>

<template>
  <div class="sort-selection">
    <p class="result-text">결과 총 <span style="font-weight: bold">{{ totalElements }}</span> 개</p>
    <div class="sort-selection-side">
      <ul class="view-sort-list">
        <li class="button-text" :class="{active: selectedButton === button.value}" v-for="button in buttons">
          <button
            @click="handleSearch(button.value)"
            >{{ button.label }}
          </button>
        </li>
        <div class="rbox-block">
          <span class="search-box">
            <input
              type="text"
              class="o-input"
              v-model="content"
              placeholder="전시명, 작가명, 전시 공간명 등을 입력해주세요"
              name="search_keyword"
              maxlength="20"
              value=""
              autocomplete="false"
            />
            <button type="button" class="o-btn o-btn-search" aria-label="검색" @click="handleSearch(this.selectedButton, this.content)">
              <img
                src="https://png.pngtree.com/png-clipart/20190705/original/pngtree-vector-search-icon-png-image_4271228.jpg">
            </button>
          </span>
        </div>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.search-option {
  width: 33%;
  display: flex;
  flex-wrap: wrap;
  flex: 1 1;
  padding: 20px;
}

.search-items {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
}

.search-item {
  flex: 50% 1;
  margin-bottom: 17px;
  font-size: 17px;
}

.search-detail-menu {
  max-width: 1920px;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background-color: #ede9e0;
  padding: 30px;
}

.search-item span,
p {
  padding: 0 10px;
}

.sort-selection {
  display: flex;
  width: 100%;
  height: 100%;
  flex-wrap: wrap;
  align-items: center;
}

.result-text {
  font-size: 18px;
  color: black;
}

.sort-selection .rbox-block {
  display: flex;
  z-index: 10;
}

.sort-selection-side {
  display: flex;
  margin-left: auto;
  align-items: center;
}

.sort-selection-side .search-box {
  width: 100%;
  min-width: 360px;
  max-width: 360px;
}

.view-sort-list {
  display: flex;
  align-items: center;
  line-height: 1;
  color: #666;
}

.view-sort-list li {
  position: relative;
  padding-left: 4px;
  padding-right: 4px;
}

.filter-button {
  position: relative;
  display: inline-flex;
  border: 1px solid #dbd3c7;
  border-radius: 999px;
  padding: 11px 16px;
}

.view-sort li + li:before {
  position: absolute;
  top: 50%;
  left: 0;
  display: block;
  width: 1px;
  height: 10px;
  margin-top: -5px;
  background: #dbd3c7;
  background-color: rgb(219, 211, 199);
  content: '';
}

.search-box .o-btn-search {
  position: absolute;
  width: 40px;
  height: 100%;
  border-radius: 0;
  vertical-align: middle;
  object-fit: cover;
  flex-shrink: 0;
  top: 0;
  right: 0;
}

.o-btn-search > img {
  object-fit: cover;
  width: 100%;
  height: 100%;

}

.search-box {
  position: relative;
  display: inline-flex;
  flex-direction: row;
  align-items: center;
}

.search-box .o-input {
  width: 100%;
  flex: 1 1;
  height: 40px;
  font-size: 14px;
  color: #666;
  padding: 8px 45px 8px 16px;
  border: 1px solid #c8c6bd;
  border-radius: 8px;
  background: #eae7e2;
}

.filter-button .st-text {
  margin-left: 3px;
}

.active {
  background-color: #E0F2F1;
  border-radius: 30px;
  color: white;
}

.button-text {
  padding: 20px !important;
}
.rbox-block {
  margin-left: 20px !important;
}
</style>
