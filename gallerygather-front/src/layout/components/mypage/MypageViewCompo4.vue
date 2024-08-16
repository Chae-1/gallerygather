<template>
  <div class="reply">
    <div class="container">
      <br />
      <br />
      <h3>내가 작성한 댓글 확인</h3>
      <br />
      <br />
      <div class="btn-toggle">
        <button @click="selectedTab = 'reply'" :class="{ active: selectedTab === 'reply' }">
          댓글리스트
        </button>
      </div>

      <!-- 선택 및 삭제 버튼을 포함하는 행 -->
      <div class="selectedAllrow">
        <!-- 전체 선택 체크박스 -->
        <div>
          <input type="checkbox" v-model="selectAll" @change="selectAllcheck" /> 전체선택
        </div>
        <!-- 선택된 항목을 삭제하는 버튼 -->
        <button @click="deleteSelected" class="deleteSelected">삭제</button>
      </div>

      <!-- 댓글 리스트 -->
      <div class="replylist">
        <div v-for="(reply, index) in replys" :key="index" class="card">
          <div class="card-content">
            <!-- 선택 체크박스 -->
            <input type="checkbox" v-model="reply.selected" class="checkbox" @click.stop />

            <!-- 댓글 작성 -->
            <div class="content" @click="goToExhibitionDetailCompo(reply)">
              <!-- 전시 제목 -->
              <div class="review-title">{{ reply.reviewTitle }}</div>
              <!-- 댓글 내용 -->
              <div class="content-text">{{ reply.content }}</div>
              <!-- 리뷰 작성 날짜 -->
              <div class="update-date">{{ reply.updateDate }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios'

export default {
  name: 'ReplyList',
  data() {
    return {
      selectedTab: 'reply',
      selectAll: false,
      replys: []
    }
  }, //data
  created() {
    this.fetchReplys()
    console.log('fetchReplys Created')
  },
  methods: {
    //로그인정보
    async fetchReplys() {
      //jwt 토큰추가
      const token = localStorage.getItem('accessToken')
      console.log('reply호출시의', token)

      const config = {
        headers: {
          'Authorization': token, // 인증 헤더에 JWT 토큰을 포함
          'Content-Type': 'application/json'
        }
      }
      try {
        const response = await axios.get('http://192.168.230.3:8080/api/replys/member/reply', config)
        this.replys = response.data//
        console.log('서버로부터 받은 댓글 데이터:', response.data)
      } catch (error) {
        console.error('Error fetching replys:', error)
      }
    },
    selectAllcheck() {
      this.replys.forEach((reply) => {
        reply.selected = this.selectAll
      })
    },
    goToExhibitionDetailCompo(reply) {
      console.log('전시댓글: reply:', reply);
      console.log('전시댓글: replyReviewId:', reply.replyReviewId);
      console.log('전시댓글: reply:', reply.getExhibitionId);
      if (reply.getExhibitionId && reply.replyReviewId) {
        this.$router.push({
          name: 'ReviewDetail',
          params: {
            exhibitionId: reply.getExhibitionId,
            reviewId: reply.replyReviewId } // reviewId를 함께 전달
        });
      } else {
        console.error('댓글에서 goToExhibitionDetailCompo 문제.');
      }
    }
  },
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

.deleteSelected {
  color: black;
  border: none;
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.replylist .card {
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 15px;
  padding: 10px;
  display: flex;
  align-items: center;
}

.card-content {
  display: flex;
  align-items: center;
  width: 100%;
}

.checkbox {
  margin-right: 15px;
  flex-shrink: 0;
}

.content {
  cursor: pointer;
  width: 100%;
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
