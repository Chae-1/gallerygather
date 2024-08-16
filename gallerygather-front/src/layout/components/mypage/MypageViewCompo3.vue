<template>
  <div class="reviews">
    <div class="container">
      <br/>
      <br/>
      <h3>내가 작성한 리뷰 확인</h3>
      <br/>
      <br/>
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
        <button @click="deleteSelectedReviews" class="deletebtn">삭제</button>
      </div>

      <!-- 리뷰리스트 시작 -->
      <div class="reviewlist">
        <!-- 리뷰 항목을 반복하여 생성 -->
        <div v-for="(review) in reviews" :key="review.id" class="card">
          <!-- 각 리뷰 항목을 카드 형식으로 표시 -->
          <div class="card-content">
              <!-- 선택 체크박스 -->
                <input type="checkbox" v-model="review.selected" class="checkbox" @click.stop />
              <!-- 리뷰 이미지 보류-->
              <div class="review-pic">
                <img :src="review.image" class="review-image" />
              </div>
              <!-- 리뷰 내용 -->
              <div class="content-text" @click="goToDetail(review)">
                <!-- 리뷰 별점 -->
                <div class="">⭐{{ review.rating }}</div>
                <!-- 리뷰 제목-->
                <br />
                <div class="review-title">{{ review.title }}</div>
                <!-- 리뷰 내용 텍스트 -->
                <div>{{ review.exhibitTitle }}</div>
                <div>{{ formatDate(review.updateDate) }}</div>
                <div class="text-grey update-date">{{ review.date }}</div>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios'
import { apiRequest } from '@/util/RequestUtil.js'

export default {
  name: 'ReviewList',
  data() {
    return {
      selectedTab: 'review', // 선택된 탭을 저장하는 데이터
      selectAll: false, // 전체 선택 체크박스 상태를 저장하는 데이터
      reviews: [
        // 추가 리뷰 항목을 여기에 추가
      ]
    }
  },
  created() {
    this.fetchReviews(); // 컴포넌트가 생성될 때 리뷰 목록을 가져옵니다.
    console.log("Reviews Component created");
  },
  methods: {
    //로그인정보
    async fetchReviews() {
      // JWT 토큰 추가
      const token = localStorage.getItem('accessToken')
      console.log('서버로부터 받은 데이터 토큰:', token);

      const config = {
        headers: {
          'Authorization': token, // 인증 헤더에 JWT 토큰을 포함
          'Content-Type': 'application/json'
        }
      }
      try {

        const response = await axios.get('http://localhost:8080/api/reviews/member', config)
        console.log('서버로부터 받은 리뷰 데이터:', response.data);
        this.reviews = response.data;// 서버로부터 받은 데이터를 reviews에 저장
        console.log('서버로부터 받은 리뷰 데이터:', response.data);
      } catch (error) {
        console.error('Error fetching reviews:', error)
      }
    },
    toggleSelectAll() {
      // 전체 선택 체크박스를 변경할 때 호출되는 메서드
      this.reviews.forEach((review) => {
        review.selected = this.selectAll
      })
    },
    goToDetail(review) {
      console.log("review에 들어있는데이터 : "+review);
      console.log('고디테일 : exhibitionId:', review.exhibitId, '   reviewId:', review.id);
      if (review.exhibitId && review.id) {
        this.$router.push({
          name: 'ReviewDetail',
          params: {
            exhibitionId: review.exhibitId,
            reviewId: review.id
          }
        });
      } else {
        console.error('리뷰에서 goToDetail.');
      }
    },
    async deleteSelectedReviews() {
      const selectedReviews = this.reviews.filter(review => review.selected);

      if (selectedReviews.length === 0) {
        alert("삭제할 리뷰를 선택하세요.");
        return;
      }

      try {
        for (const review of selectedReviews) {
          await apiRequest('delete', `http://192.168.230.3:8080/api/exhibition/deleteReview/${review.id}`);
        }
        // 선택된 리뷰가 삭제된 후, 목록을 갱신
        this.fetchReviews();
        this.selectAll = false; // 전체 선택 상태를 초기화
      } catch (error) {
        console.error('리뷰 삭제 실패:', error);
      }
    },
    formatDate(dateTime) {// 로케일에 맞게 날짜까지만 반환
      const date = new Date(dateTime);
      return date.toLocaleDateString(); // 로케일에 맞게 날짜까지만 반환
    }
  }
}
</script>

<style scoped>
.container {
  width: 80%;
  margin: 0 auto;
}

h3 {
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: bold;
}

.btn-toggle {
  margin-bottom: 20px;
}

.selectedAllrow {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.deletebtn {
  color: black;
  border: none;
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.reviewlist .card {
  padding: 20px;
  min-height: 150px;
  height: auto;
  display: flex;
  align-items: center;

}


.card-content {
  display: flex;
  flex-wrap: wrap;
  height: 100px;
  justify-content: flex-start; /* 왼쪽 정렬 */
  align-items: stretch;
  gap: 20px; /* 카드 간의 간격을 일정하게 유지 */
  width: 100%;
}

.card-link {
  text-decoration: none;
  flex: 1 1 calc(25% - 20px); /* 카드의 최소 너비를 설정하고, 간격을 고려하여 유연하게 배치 */
  max-width: calc(25% - 20px); /* 간격을 고려하여 최대 너비 설정 */
  margin: 10px;
  display: flex;
  flex-direction: column;
}

.checkbox {
  margin-right: 15px;
  flex-shrink: 0;
}
.review-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.update-date {
  font-size: 12px;
  color: #888;
  margin-bottom: 8px;
}

.content-text {
  font-size: 14px;
  color: #333;
}

</style>