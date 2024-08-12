<template>
  <div class="review-container">
    <h2 class="title">후기 작성하기</h2>
    <div class="exhibition-info">
      <img src="../../../assets/img/kitty.jpg" alt="전시 이미지" class="exhibition-image" />
      <div class="exhibition-detail">
        <h3>{{ gtitle }}</h3>
        <p>기간: {{ period }}</p>
        <p>평점: {{ avgRating }}</p>
      </div>
    </div>
    <form @submit.prevent="submit">
      별점을 입력하세요.
      <star-rating v-model:rating="review.rating" :increment="0.5" :star-size="20" required></star-rating>
      <br />
      제목:
      <input
        type="text"
        class="input-title"
        v-model="review.title"
        placeholder="제목을 입력해주세요."
        required
      />
      <br />
      <div class="visit-date">
        <label id="date-button">관람 일자 </label>
        <button type="button" class="date-button" @click="showDatePicker = !showDatePicker">
          {{ showDatePicker ? (formattedDate ? formattedDate + ' [닫기]' : '날짜 선택 [닫기]') : (formattedDate || '날짜 선택하기') }}
          <span></span>
        </button>
        <div v-if="showDatePicker" class="date-picker-container">
          <v-date-picker
          v-model="review.viewDate"
          @input="showDatePicker = false"
            mode="single"
          ></v-date-picker>
        </div>
      </div>
      <br />
      내용:
      <QuillEditor
        v-model:modelValue="review.content"
        placeholder="내용을 입력해 주세요."
        required
      ></QuillEditor>
      <br />
      <button type="submit" class="submit-button">등록</button>
    </form>
  </div>
</template>

<script>
import QuillEditor from './QuillEditor.vue'
import StarRating from 'vue-star-rating'
import { ref } from 'vue';
import VCalendar from 'v-calendar';
import axios from 'axios';

export default {
  components: {
    QuillEditor,
    StarRating,
    'v-date-picker': VCalendar.DatePicker
  },
  setup() {
    const date = ref(new Date());
    const showDatePicker = ref(false);
    return {
      date,
      showDatePicker
    };
  },
  data() {
    return {
      gtitle: '헬로키티 전시',
      period: '2024.01.01 ~ 2024.12.12',
      avgRating: '⭐ 3',
      review: {title: '', content: '', viewDate: new Date().toISOString().substr(0, 10), rating: 0 }
    }
  },
  computed: {
    formattedDate() {
      if (!this.review.viewDate) return '';
      const date = new Date(this.review.viewDate);
      const year = date.getFullYear();
      const month = ('0' + (date.getMonth() + 1)).slice(-2);
      const day = ('0' + date.getDate()).slice(-2);
      return `${year}-${month}-${day}`;
    }
  },
  methods: {
    async submit() {
      
      const exhibitionId = this.$route.params.exhibitionId;
      console.log('?????????????????????',exhibitionId)

      try {
        console.log('URL:', `http://localhost:8080/api/exhibition/${exhibitionId}/review`);
        console.log('Payload:', this.review);
        // const token = localStorage.getItem('token');
        // if (!token) {
        //   console.error('JWT 토큰이 없습니다. 로그인 상태를 확인하세요.');
        //   // 로그인 페이지로 리다이렉트 등 추가 작업 필요
        //   return;
        // }
        // 서버로 POST 요청을 보내고, reviewId를 응답받습니다.
        const response = await axios.post(`http://localhost:8080/api/exhibition/${exhibitionId}/review`, 
          this.review
        , {
          headers: {
            'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlZWVAbmF2ZXIuY29tIiwiaWF0IjoxNzIzMzkyNDA3LCJleHAiOjE3MjMzOTQyMDd9.zPk8WjTDXHXQVxldyRLfzVgmb8-ok9uCQDk1koLxFM8`
          }
        });
        const reviewDetail = response.data;

        console.log('!!!!!!!!!!!!!!!!!!!!!1111',reviewDetail)
        const reviewId = reviewDetail.reviewId;
        console.log('리뷰아이디', reviewId);
        //const exhibitionId = reviewDetail.exhibitionId;

        // 상세보기 페이지로 이동합니다.
        this.$router.push(`api/exhibition/${reviewDetail.exhibitionId}/review/${reviewDetail.reviewId}`);
      } catch (error) {
        console.error('리뷰 생성 실패:', error);
      }
    }
  },
  watch: {
    'review.content': function (newContent) {
      console.log('Parent component review.content changed:', newContent)
    },
    'review.rating': function (newRating) {
      console.log('Parent component review.rating changed:', newRating)
    },
  },
  mounted() {
    console.log('Mounted with initial review:', this.review)
  }
}
</script>

<style scoped>
.review-container {
  background-color: #f8f4e5;
  padding: 20px;
  border-radius: 8px;
  width: 800px;
  margin: 0 auto;
}

.title {
  font-size: 24px;
  margin-bottom: 20px;
}

.exhibition-info {
  display: flex;
  margin-bottom: 20px;
}

.exhibition-image {
  width: 150px;
  height: 200px;
  margin-right: 20px;
}

.exhibition-detail {
  margin-top: 20px;
}

.input-title {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.visit-date {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  position: relative; /* 날짜 선택기를 절대 위치시킬 수 있도록 설정 */
}

.date-button {
  background-color: #3b82f6;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  margin-left: 10px;
}

.date-button span {
  margin-left: 5px;
}

.date-picker-container {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1000; /* 날짜 선택기가 다른 요소들 위에 나타나도록 설정 */
  background: white; /* 배경을 흰색으로 설정 */
  border: 1px solid #ccc; /* 일반적인 드롭다운과 일치하도록 테두리를 추가 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 선택적으로 더 나은 가시성을 위해 그림자를 추가 */
  border-radius: 5px; /* 버튼의 테두리 반경과 일치시킴 */
  margin-top: 5px; /* 겹침을 피하기 위한 약간의 여백 */
}

.input-content {
  height: 400px;
}

.submit-button {
  background-color: #5cb85c;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #4cae4c;
}
</style>
