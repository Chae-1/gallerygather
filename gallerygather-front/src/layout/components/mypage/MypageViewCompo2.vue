<template>
  <div class="reviews">
    <div class="container">
      <br/>
      <br/>
      <h3>관심있는 전시</h3>
      <br/>
      <br/>
      <!-- 좋아요 리스트 시작 -->
      <div class="card-container">
        <!-- 반복 생성 -->
        <div class="card" v-for="(like, index) in likes" :key="index">
          <!-- 각 리뷰 항목을 카드 형식으로 표시 -->
          <div class="card-image">
            <img :src="like.imageUrl" alt="Card Image" />
            <span class="favorite">
              <i class="fas fa-heart"></i>
            </span>
          </div>
          <div class="card-body" @click="goToDetail(card)">
            <p class="title">{{ like.title }}</p>
            <p class="date-range">{{ like.dateRange }}</p>
            <p class="location">{{ like.location }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      likes: [], // 좋아요 리스트를 저장할 배열
    };
  },

  created() {
    this.fetchLikes(); // 컴포넌트 생성 시 좋아요 목록을 가져옵니다.
    console.log('fetchLikes Created');
  },

  methods: {
    async fetchLikes() {
      // JWT 토큰을 로컬 스토리지에서 가져옵니다.
      const token = localStorage.getItem('accessToken');
      console.log('like 페이지의', token);

      const config = {
        headers: {
          Authorization: token,
          'Content-Type': 'application/json'
        }
      };

      try {
        const response = await axios.get('http://localhost:8080/api/exhibitions/likelist', config);
        this.likes = response.data; // 서버로부터 받은 데이터를 likes 배열에 저장
        console.log('서버로부터 받은 likes 데이터:', response.data);
      } catch (error) {
        console.error('Error fetching likes:', error); // 오류 발생 시 콘솔에 출력
      }
    },

    // 전시 상세 페이지로 이동
    goToDetail(like) {
      console.log(like);
      // 예: this.$router.push({ name: 'ExhibitionDetail', params: { id: like.id } });
    },
  },
};
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

.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.card {
  width: 24%;
  border: 1px solid #ddd;
  margin-bottom: 20px;
  padding: 10px;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
}

.card-image img {
  width: 100%;
  height: auto;
  display: block;
  margin-bottom: 10px;
}

.card-body {
  text-align: left;
}

.date-range,
.location {
  margin: 5px 0;
  font-size: 0.9em;
  color: #555;
}
</style>
