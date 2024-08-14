<template>
  <div class="review-details">
    <div class="review-container">
      <div class="review-box">
        <p>후기 상세보기</p>
        <br />
        <h2 class="title">{{ reviewDetail.title }}</h2>
        <div class="review-info">
          <span class="reviewer"> {{ reviewDetail.authorName }} </span>
          <span class="scope"> | ⭐ {{ reviewDetail.rating }}</span>
        </div>
        <div>
          관람일자 | <span class="review-date">{{ reviewDetail.viewDate }}</span>
          <br />
          <span class="view">👓 {{ reviewDetail.viewCount }}</span>
          <br />
          <span class="likes">❤️ {{ reviewDetail.likeCount }}</span>
          <br />
          <span class="replies">💬 {{ reviewDetail.replyCount }}</span>
        </div>
      </div>
      <div class="exhibit-img">
        <img :src="reviewDetail.exhibitionImgUrl" alt="exhibitionImgUrl" />
      </div>
    </div>
    <div class="review">
      <div class="review-content">
        <div class="ql-editor">
          <div v-html="safeContent"></div>
        </div>
        <!-- <div v-dompurify-html="reviewDetail.content"></div> -->
        <!-- <quill-editor v-model="content" placeholder="게시글이 없습니다."></quill-editor> -->
      </div>
      <div class="button-container">
        <span>후기 작성일 {{ reviewDetail.regDate }}</span>
        <button type="button" class="editButton" @click="editReview">수정</button>
        <button type="button" class="deleteButton" @click="deleteReview">삭제</button>
        <button type="button" class="likeButton">❤️</button>
      </div>
    </div>
    <!-- <ReviewRepliesCompo/> -->
  </div>
</template>

<script>
import axios from 'axios'
import ReviewRepliesCompo from './ReviewRepliesCompo.vue'
import DOMPurify from 'dompurify'
import { apiRequest } from '@/util/RequestUtil'
// import QuillEditor from './QuillEditor.vue'

export default {
  components: {
    ReviewRepliesCompo
    // QuillEditor
  },
  data() {
    return {
      reviewDetail: {}
    }
  },
  computed: {
    safeContent() {
      return DOMPurify.sanitize(this.reviewDetail.content)
    }
  },

  async created() {
    if (this.$route.params.reviewDetail) {
      this.reviewDetail = this.$route.params.reviewDetail
    } else {
      const { exhibitionId, reviewId } = this.$route.params

      try {
        const response = await axios.get(
          `http://localhost:8080/api/exhibition/${exhibitionId}/review/${reviewId}`
        )
        this.reviewDetail = response.data
      } catch (error) {
        console.error('Failed to fetch review detail:', error)
      }
    }
  },

  methods: {
    editReview() {
      // 리뷰 수정 페이지로 이동
      const exhibitionId = this.$route.params.exhibitionId
      const reviewId = this.$route.params.reviewId
      this.$router.push({ name: 'ReviewEdit', params: { exhibitionId, reviewId } })
    },
    async deleteReview() {
      const reviewId = this.$route.params.reviewId
      try {
        await apiRequest('delete', `http://localhost:8080/api/exhibition/deleteReview/${reviewId}`)

        // 삭제 후 페이지 이동 또는 사용자에게 메시지 표시
        // 예를 들어, 리뷰 목록 페이지로 이동
        this.$router.push(`/exhibitiondetails/${this.$route.params.exhibitionId}`)
      } catch (error) {
        console.error('리뷰 삭제 실패:', error)
        // 오류가 발생했을 때의 처리 (예: 사용자에게 오류 메시지 표시)
      }
    }
  }
}
</script>

<style scoped>
.review-details {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.review-box {
  color: #ddd !important;
}
.review {
}

.review-container {
  min-height: 250px;
  width: 100%;
  display: flex;
  flex-direction: row;
  gap: 10%;
  padding: 30px 0;
  background-color: darkslategray;
  justify-content: center;
  align-items: center;
}

.exhibit-img {
  height: 33vh;
}

.exhibit-img > img {
  position: inherit;
  height: 100%;
}
.review-content {
  margin: 10px auto;
  width: 50%;
  min-height: 400px; /* 최소 높이 설정 */
  max-height: none; /* 최대 높이 설정을 없애서 글 길이에 따라 변하도록 함 */
  overflow: auto; /* 글이 많을 경우 스크롤 생기도록 설정 */
  border: 1px solid #ddd;
  border-radius: 4px;
  border: 1px solid transparent;
}

.button-container {
  width: 50%;
  display: flex;
  justify-content: flex-end; /* 오른쪽 정렬을 유지하려면 'right' 대신 'flex-end' 사용 */
  gap: 10px;
  margin: 20px auto; /* 위아래 margin을 20px로 설정하고 수평 중앙 정렬 */
  align-items: center;
}

.editButton,
.deleteButton,
.likeButton {
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.editButton {
  background-color: #5cb85c;
  color: white;
}

.editButton:hover {
  background-color: #4cae4c;
}

.deleteButton {
  background-color: #eb3e3e;
  color: white;
}

.deleteButton:hover {
  background-color: #d94949;
}

.likeButton {
  background-color: #ffcc00;
  color: white;
}

.likeButton:hover {
  background-color: #e6b800;
}
</style>
