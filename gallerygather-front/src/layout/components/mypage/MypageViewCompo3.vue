<template>
  <div class="reivews">
    <div class="container">
      <!-- 행을 사용하여 탭 버튼들을 정렬 -->
      <div class="row">
        <!-- 버튼 토글을 사용하여 탭을 만듭니다 -->
        <div class="btn-toggle">
          <button @click="selectedTab = 'review'" :class="{ active: selectedTab === 'review' }">
            리뷰
          </button>
        </div>
      </div>
      <!-- 선택 및 삭제 버튼을 포함하는 행 -->
      <div class="selectedAllrow">
        <!-- 전체 선택 체크박스 -->
        <div class="col-auto">
          <input type="checkbox" v-model="selectAll" @change="toggleSelectAll" /> 전체선택
        </div>
        <!-- 선택된 항목을 삭제하는 버튼 -->
        <button @click="deleteSelected" class="deletebtn">삭제</button>
      </div>
      <!-- 리뷰 항목들을 나열하는 행 -->
      <div class="row">
        <!-- 리뷰 항목을 반복하여 생성 -->
        <div class="col-12" v-for="(review, index) in reviews" :key="index">
          <!-- 각 리뷰 항목을 카드 형식으로 표시 -->
          <div class="card my-2">
            <div class="row align-center">
              <!-- 선택 체크박스 -->
              <div class="col-auto">
                <input type="checkbox" v-model="review.selected" @click.stop />
              </div>
              <!-- 리뷰 이미지 -->
              <div class="col-2">
                <img :src="review.image" class="review-image" />
              </div>
              <!-- 리뷰 내용 -->
              <div class="col-9" @click="goToDetail(review)">
                <!-- 리뷰 제목 -->
                <div class="font-weight-bold">{{ review.title }}</div>
                <!-- 리뷰 내용 텍스트 -->
                <div>{{ review.content }}</div>
                <!-- 리뷰 작성 날짜 -->
                <div class="text-grey">{{ review.date }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReviewList',
  data() {
    return {
      selectedTab: 'review', // 선택된 탭을 저장하는 데이터
      selectAll: false, // 전체 선택 체크박스 상태를 저장하는 데이터
      reviews: [
        {
          selected: false, // 선택 여부
          image: 'path/to/image.png', // 이미지 경로
          title: '밤 끝으로의 여행', // 제목
          content: '어려워!', // 내용
          date: '2024.08.07', // 날짜
          id: 1 // 리뷰 ID
        },
        {
          selected: false,
          image: 'path/to/image.png', // 이미지 경로
          title: '여행기',
          content: '재밌어!',
          date: '2024.08.08',
          id: 2
        }
        // 추가 리뷰 항목을 여기에 추가
      ]
    }
  },
  methods: {
    toggleSelectAll() {
      // 전체 선택 체크박스를 변경할 때 호출되는 메서드
      this.reviews.forEach((review) => {
        review.selected = this.selectAll
      })
    },
    deleteSelected() {
      // 선택된 리뷰 항목을 삭제하는 메서드
      this.reviews = this.reviews.filter((review) => !review.selected)
    },
    goToDetail(review) {
      // 리뷰 상세 페이지로 이동하는 메서드
      this.$router.push({ name: 'ReviewDetail', params: { id: review.id } }) //
    }
  }
}
</script>

<style scoped>
.review-image {
  max-width: 100px; /* 이미지 최대 너비 설정 */
  max-height: 100px; /* 이미지 최대 높이 설정 */
}
/* 체크박스와 라벨을 한 줄로 유지 */
.no-wrap .v-label {
  white-space: nowrap;
}
.selectedAllrow {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.deletebtn {
  margin-left: auto;
  padding: 6px 12px;
}
</style>
