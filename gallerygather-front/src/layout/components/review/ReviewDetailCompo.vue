<template>
  <div class="review-details">
    <div class="review-container">
      <div class="review-box">
        <router-link :to="{ path: '/exhibitiondetails/' + this.exhibitionId }">
          <p>&lt; {{ reviewDetail.exhibitionTitle }}</p></router-link
        >
        <br />
        <h2 class="title">{{ reviewDetail.title }}</h2>
        <div class="review-info">
          <span class="reviewer"> {{ reviewDetail.authorName }} </span>
          <span class="scope"> | ⭐ {{ reviewDetail.rating }}</span>
        </div>
        <div class="review-contents">
          <div>
            관람일자 | <span class="review-date">{{ reviewDetail.viewDate }}</span>
          </div>
          <div>
            작성일자 | <span class="review-date">{{ reviewDetail.regDate }}</span>
          </div>
          <div class="count-content">
            <span class="view"> 👓 {{ reviewDetail.viewCount }} </span>
            <span class="likes"> ❤️ {{ reviewDetail.likeCount }} </span>
            <span class="replies"> 💬 {{ reviewDetail.replyCount }} </span>
          </div>
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
      </div>
      <div class="button-container">
        <div>
          <button
            type="button"
            class="{'like-button': isLike, 'unLike-button' : !isLike}"
            @click="handleLikeClick"
          >
            {{ isLike ? '❤️ 좋아요 취소' : '🩶 좋아요' }}
          </button>
        </div>
        <div class="edit-buttons">
          <button
            type="button"
            class="editButton"
            @click="editReview"
            v-if="getUser === reviewDetail.authorEmail"
          >
            수정
          </button>
          <button type="button" class="deleteButton" v-if="getUser === reviewDetail.authorEmail">
            삭제
          </button>
        </div>
      </div>
    </div>
    </div>
</template>

<script>
import ReviewRepliesCompo from './ReviewRepliesCompo.vue'
import DOMPurify from 'dompurify';
import { userStore } from '@/store/userStore';
import { apiRequest } from '@/util/RequestUtil';

export default { 
    components: {
        ReviewRepliesCompo,
    },
    data() {
        return {
            exhibitionId: null,
            reviewId: null,
            reviewDetail: [],
            isLike: null,
            ifLoggedIn: null,
        };
    },
    computed: {
    safeContent() {
        return DOMPurify.sanitize(this.reviewDetail.content);
    },
    ifLoggedIn() {
          const store = userStore();
          return store.isAuthenticated;
    },
    getUser() {
        const store = userStore();
        return store.getUser;
    },
  },
  created() {
    const { exhibitionId, reviewId } = this.$route.params
    this.exhibitionId = exhibitionId
    this.reviewId = reviewId
    this.getReviewDetail()
  },
  methods: {
      
      async getReviewDetail() {
          apiRequest("get", `http://localhost:8080/api/exhibition/${this.exhibitionId}/review/${this.reviewId}`)
              .then((response) => {
                  this.reviewDetail = response.data.reviewDetail;
                  this.ifLoggedIn = response.data.isLoggedIn;
                  this.isLike = response.data.isLike;
              }).catch(error => console.log(error));
      },
      editReview() {
      // 리뷰 수정 페이지로 이동
      if (this.ifLoggedIn === false) {
          alert("잘못된 접근입니다. 로그인 후 이용해주시기 바랍니다.");
          } else {
              this.$router.push({ name: 'ReviewWrite' });
          }
      },
      handleLikeClick() {
          if (this.ifLoggedIn) { //로그인 되었으면
              apiRequest("post",
                  `http://localhost:8080/api/exhibition/reviews/${this.reviewId}/like`,
                  {"isLike": this.isLike}
              ).then((response) => {
                  console.log(response);
                  this.isLike = !this.isLike;
                  this.reviewDetail.likeCount += this.isLike? 1: -1;
              }).catch(error=>{console.log(error);})

          } else { //로그인 안되었으면
              alert("로그인 후 이용 가능합니다.");
          }
      },
      async deleteReview() {
        const reviewId = this.$route.params.reviewId
        try {
          await apiRequest('delete', `http://localhost:8080/api/exhibition/deleteReview/${reviewId}`)

          this.$router.push(`/exhibitiondetails/${this.$route.params.exhibitionId}`)
        } catch (error) {
          console.error('리뷰 삭제 실패:', error)
        }
      },
  }  
    
};

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
  width: 60%;
  display: flex;
  justify-content: space-between;
  /* 오른쪽 정렬을 유지하려면 'right' 대신 'flex-end' 사용 */
  gap: 10px;
  margin: auto; /* 위아래 margin을 20px로 설정하고 수평 중앙 정렬 */
  align-items: center;
}

.edit-buttons button {
  margin-left: 10px;
}

.editButton,
.deleteButton,
.like-button,
.unLike-button {
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

.like-button,
.unLike-button {
  background-color: #ffcc00;
  color: white;
}

.like-button:hover,
.unLike-button:hover {
  background-color: #e6b800;
}
</style>
