<!--유은-->
<template>
  <div class="reviews">
    <div class="container">
      <br />
      <br />
      <h3>관심있는 전시</h3>
      <br />
      <br />
      <!-- 좋아요 리스트 시작 -->
      <div class="card-container">
        <!-- 반복 생성 -->
        <router-link v-for="item in cards"
                     :to="{ path: '/exhibitiondetails/' + item.id }"
                     :key="item.id"
                     class="card-link"
                     style="display: block;">
          <b-card class="b-card">
            <!-- 이미지를 카드의 최상단에 배치 -->
            <img :src="item.imgUrl" class="card-img" alt="Exhibition Image">
            <span class="favorite">
              <i class="fas fa-heart"></i>
            </span>

            <!-- 타이틀을 이미지 아래에 배치 -->
            <div class="card-title">
              <h6>{{ item.title }}</h6>
            </div>
            <template #footer>
              <div class="card-body">
                <div class="card-duration">
                  <span>{{ item.startDate }}</span>
                  <span> ~ </span>
                  <span>{{ item.endDate }}</span>
                </div>
                <div class="card-location">
                  <span class="card-detail-title">장소</span>
                  <span>{{ item.place }}</span>
                </div>
                <div class="card-info">
                  <div class="card-info-grade">
                    <em>별점</em>
                    <span style="margin: 0 20px;"> ⭐{{ item.avgRating
                    || '0' }}</span>
                  </div>
                </div>
              </div>
            </template>
          </b-card>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'CardComponent',
  data() {
    return {
      cards: [] // cards 배열 초기화

    }
  },
  created() {
    this.fetchLikes() // 컴포넌트 생성 시 좋아요 목록을 가져옵니다.
    console.log('fetchLikes Created')

  },
  methods: {
    async fetchLikes() {
      // JWT 토큰을 로컬 스토리지에서 가져옵니다.
      const token = localStorage.getItem('accessToken')
      console.log('like 페이지의', token)

      const config = {
        headers: {
          Authorization: token,
          'Content-Type': 'application/json'
        }
      }

      try {
        const response = await axios.get('http://192.168.230.3:8080/api/exhibitions/likelist', config)
        this.cards = response.data // 서버로부터 받은 데이터를 cards 배열에 저장
        console.log('서버로부터 받은 likes 데이터:', response.data)
      } catch (error) {
        console.error('Error fetching likes:', error) // 오류 발생 시 콘솔에 출력
      }
    }
  }
}
</script>

<style scoped>
.container {
  width: 90%; /* 컨테이너의 너비를 90%로 설정 */
  margin: 0 auto; /* 가운데 정렬 */
}

.card-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); /* 고정된 카드 크기 유지 */
  gap: 10px; /* 카드 사이의 간격 */
}

.card-link {
  text-decoration: none;
  display: flex;
  flex-direction: column;
}

.b-card {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.card-img {
  width: 100%;
  height: 200px;
  object-fit: contain;
  border-radius: 8px 8px 0 0;
}

.card-title {
  min-height: 50px;
  text-align: center;
  padding: 10px 15px;
  font-weight: bold;
}

.card-body {
  display: flex;
  height: 200px;
  flex-direction: column;
  justify-content: space-between;
  padding: 10px 15px;
  flex-grow: 1;
  min-height: 150px;
}

.b-card:hover {
  transform: scale(1.02);
}

.card-duration::before,
.card-location::before {
  width: 19px;
  height: 19px;
  content: '';
  display: inline-block;
  margin-right: 5px;
}

.card-duration::before {
  background-image: url("../../../assets/img/calander.svg");
  background-size: cover;
}

.card-location::before {
  background-image: url("../../../assets/img/location.svg");
  background-size: cover;
}

.card-location {
  display: flex;
  align-items: center;
}

.card-detail-title {
  white-space: nowrap;
  margin-right: 5px;
  flex-shrink: 0;
}

.card-location span,
.card-duration span {
  display: block; /* 각 span을 블록 요소로 만들어 줄바꿈을 합니다. */
  font-size: 0.9em; /* 글자 크기를 줄입니다. */
  max-width: calc(100% - 40px);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}


.card-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-info-grade {
  display: flex;
  align-items: center;
}

.card-info-grade em {
  font-style: normal;
  font-weight: bold;
}

.card-detail-title {
  font-weight: bold;
  margin-right: 5px;
}

.card-link:hover .b-card {
  transform: scale(1.05);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

</style>