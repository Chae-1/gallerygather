<template>
  <!-- 제목라인 -->
  <div class="reply">
    <h3>내가 작성한 댓글 확인</h3>
    <div class="btn-toggle">
      <button @click="selectedTab = 'reply'" :class="{ active: selectedTab === 'reply' }">
        댓글리스트
      </button>
    </div>
  </div>
  <!-- 리스트 컨텐츠라인 -->
  <div>
    <input type="checkbox" v-model="selectAll" @change="selectAllcheck" />전체선택
    <button @click="deleteSelected" class="deleteSelected">삭제</button>
  </div>
  <!-- 댓글 리스트  -->
  <div class="replylist">
    <div class="replyforlist" v-for="(reply, index) in replys" :key="index">
      <div class="card">
        <div class="row align-center">
          <!-- 선택 체크박스 -->
          <div class="col">
            <input type="checkbox" v-model="reply.selected" @click.stop />
          </div>
          <!-- 댓글 작성 -->
          <div class="" @click="goToExhibitionDetailCompo(reply.exhibition_id)">
            <!-- 전시 제목 -->
            <div class="">{{ reply.title }}</div>
            <!-- 댓글 내용 -->
            <div>{{ reply.content }}</div>
            <!-- 리뷰 작성 날짜 -->
            <div class="">{{ reply.date }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReplyList',
  data() {
    return {
      selectedTab: 'reply',
      selectAll: false,
      replys: [
        {
          selected: false, // 체크박스 선택 여부
          content: '진짜 어이없을 정도로 허무하게 느껴졌다니까요..', // 내용
          title: '밤 끝으로의 여행', // 제목
          date: '2024.08.07', // 날짜
          exhibition_id: 1 // 작품번호
        },
        {
          selected: false, // 체크박스 선택 여부
          content: '진짜 수쉽지않잖아 ..', // 내용
          title: '밤 ㅇㄴ 여행', // 제목
          date: '2024.07.07', // 날짜
          exhibition_id: 1 // 작품번호
        },
        {
          selected: false, // 체크박스 선택 여부
          content: '진짜 ..', // 내용
          title: '여행', // 제목
          date: '2024.06.07', // 날짜
          exhibition_id: 1 // 작품번호
        }
      ]
    }
  }, //data
  methods: {
    selectAllcheck() {
      this.replys.forEach((reply) => {
        reply.selected = this.selectAll
      })
    },
    deleteSelected() {
      // 선택된 리뷰 항목을 삭제하는 메서드
      this.replys = this.replys.filter((reply) => !reply.selected)
    },
    goToExhibitionDetailCompo(exhibitionId) {
      this.$router.push({ name: 'ExhibitionDetails', params: { id: exhibitionId } }) // 전시 아이디로 넘어가는거
    }
  }
}
</script>

<style scoped></style>
