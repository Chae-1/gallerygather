<template>
  <div class="page-background">
    <div class="review-container">
      <h3 class="title">후기 작성하기</h3>
      <div class="exhibition-info">
        <img :src="exhibitInfo.imgUrl" alt="전시 이미지" class="exhibition-image" />
        <div class="exhibition-detail">
          <h3>{{ exhibitInfo.title }}</h3>
          <p>기간: {{ exhibitInfo.startDate }} ~ {{ exhibitInfo.endDate }}</p>
          <p>평점: ⭐ {{ exhibitInfo.avgRating }}</p>
          <div class="star-rating-container">
            <star-rating
              v-model:rating="review.rating"
              :increment="0.5"
              :star-size="30"
              required
              :rounded-corners="true"
            ></star-rating>
            <span class="rating-text">별점을 매겨주세요</span>
          </div>
        </div>
      </div>
      <form @submit.prevent="submit">
        <input
          type="text"
          class="input-title"
          v-model="review.title"
          placeholder="제목을 입력해주세요."
          required
        />
        <br />
        <div class="visit-date">
          <div class="date-picker-wrapper">
            <input
              type="text"
              v-model="formattedDate"
              @focus="showDatePicker = true"
              placeholder="관람일자 선택"
              class="date-input"
              readonly
            />
            <button type="button" class="calendar-button" @click="showDatePicker = !showDatePicker">
              📅
            </button>
          </div>
          <div v-if="showDatePicker" class="date-picker-container">
            <v-date-picker
              v-model="review.viewDate"
              @input="onDateSelected"
              mode="single"
              :min-date="exhibitInfo.startDate"
              :max-date="exhibitInfo.endDate"
            ></v-date-picker>
          </div>
        </div>
        <QuillEditor
          v-model:modelValue="review.content"
          ref="quillEditor"
          placeholder="내용을 입력해 주세요."
          required
        ></QuillEditor>
        <br />
        <button type="submit" class="submit-button">등록</button>
      </form>
    </div>
  </div>
</template>

<script>
import QuillEditor from './QuillEditor.vue'
import StarRating from 'vue-star-rating'
import { ref } from 'vue'
import VCalendar from 'v-calendar'
import axios from 'axios'
import { apiRequest } from '@/util/RequestUtil.js'

export default {
  components: {
    QuillEditor,
    StarRating,
    'v-date-picker': VCalendar.DatePicker
  },
  setup() {
    const date = ref(new Date())
    const showDatePicker = ref(false)
    return {
      date,
      showDatePicker
    }
  },
  data() {
    return {
      exhibitInfo: {},
      review: {
        title: '',
        content: '',
        viewDate: null,
        rating: 0,
        images: []
      }
    }
  },

  async created() {
    if (this.$route.params.exhibitInfo) {
      this.exhibitInfo = this.$route.params.exhibitInfo
    } else {
      const { exhibitionId } = this.$route.params
      try {
        const response = await axios.get(
          `http://192.168.230.3:8080/api/exhibitions/${exhibitionId}`
        )
        this.exhibitInfo = response.data.exhibition
        console.log('전시정보', this.exhibitInfo)
      } catch (error) {
        console.error('Failed to fetch exhibition some detail:', error)
        // 오류 처리 로직 추가 가능 (예: 에러 메시지 표시, 다른 페이지로 리다이렉트 등)
      }
    }
  },

  computed: {
    formattedDate() {
      if (!this.review.viewDate) return ''
      const date = new Date(this.review.viewDate)
      const year = date.getFullYear()
      const month = ('0' + (date.getMonth() + 1)).slice(-2)
      const day = ('0' + date.getDate()).slice(-2)
      return `${year}-${month}-${day}`
    }
  },
  methods: {
    async submit() {
      const exhibitionId = this.$route.params.exhibitionId
      console.log('?????????????????????', exhibitionId)

      try {
        // QuillEditor에서 이미지 업로드
        const uploadUrls = await this.$refs.quillEditor.uploadImages()
        this.review.images = uploadUrls

        console.log('리뷰객체 보내지는 값!!!!!!!!!!!!', this.review)

        console.log('URL:', `http://192.168.230.3:8080/api/exhibition/${exhibitionId}/review`)
        console.log('Payload:', this.review)

        apiRequest(
          'post',
          `http://192.168.230.3:8080/api/exhibition/${exhibitionId}/review`,
          this.review
        ).then((response) => {
          const reviewDetail = response.data
          console.log('!!!!!!!!!!!!!!!!!!!!!1111', reviewDetail)

          // 상세보기 페이지로 이동합니다.
          ///exhibitiondetails/:exhibitionId/reviewdetails/:reviewId
          this.$router.push(
            `/exhibitiondetails/${exhibitionId}/reviewdetails/${reviewDetail.reviewId}`
          )
        })
      } catch (error) {
        console.error('리뷰 생성 실패:', error)
      }
    }
  },
  watch: {
    'review.content': function (newContent) {
      console.log('Parent component review.content changed:', newContent)
    },
    'review.rating': function (newRating) {
      console.log('Parent component review.rating changed:', newRating)
    }
  },
  mounted() {
    console.log('Mounted with initial review:', this.review)
  }
}
</script>

<style scoped>
.page-background {
  background-color: #f0f0f0; /* 페이지 전체 배경색 */
  padding: 20px; /* 페이지 내용과 테두리 사이의 간격 */
}

.review-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 30px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 24px;
  margin-bottom: 20px;
}

.exhibition-info {
  padding: 30px;
  display: flex;
  margin-bottom: 20px;
  background-color: rgb(231, 231, 238);
  border-radius: 10px;
}

.exhibition-image {
  width: 150px;
  height: auto;
  margin-right: 20px;
  border-radius: 8px;
}

.exhibition-detail {
  margin-top: 20px;
}

/* .exhibition-detail h3 {
  font-size: 20px;
  margin-bottom: 10px;
}

.exhibition-detail p {
  margin: 5px 0;
  color: #555;
} */

.star-rating-container {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.vue-star-rating .star svg {
  border-radius: 50%; /* 둥근 모서리 적용 */
}

.rating-text {
  margin-left: 30px;
  font-size: 16px;
}

.visit-date {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  position: relative; /* 날짜 선택기를 절대 위치시킬 수 있도록 설정 */
}

.date-picker-wrapper {
  display: flex;
  align-items: center;
  border: 1px solid #ccc; /* 외곽선 */
  border-radius: 5px; /* 모서리 둥글게 */
  overflow: hidden; /* 둥근 모서리에 맞춰 내부 요소 잘림 */
  width: max-content; /* 입력 필드와 아이콘 버튼의 내용에 맞게 크기 조정 */
}

.date-input {
  border: none;
  padding: 8px 12px;
  font-size: 17px;
  flex-grow: 1; /* 입력 필드가 가능한 공간을 모두 차지하도록 */
  min-width: 150px;
  margin-right: -5px;
}

.calendar-button {
  background-color: #b7d1eb;
  border: none;
  padding: 8px;
  cursor: pointer;
  font-size: 17px; /* 이모티콘 크기 */
  display: flex;
  align-items: center;
  justify-content: center;
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
  width: auto; /* 컨테이너의 너비를 내용에 맞게 설정 */
}

.v-date-picker {
  width: auto; /* 달력 자체의 너비를 조정 */
  min-width: 100%; /* 달력의 최소 너비를 부모 컨테이너와 동일하게 */
  max-width: 300px; /* 달력의 최대 너비 설정 */
  box-sizing: border-box; /* 패딩과 보더를 포함하여 너비 계산 */
}

.input-title {
  width: 100%;
  padding: 10px;
  font-size: 30px;
  margin-bottom: 35px;
  border-bottom: 2px solid black;
  /* border: 1px solid #ccc; */
  /* border-radius: 4px; */
}

/* .input-content {
  height: 400px;
} */

.submit-button {
  background-color: #021c19;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #021c19bb;
}
</style>
