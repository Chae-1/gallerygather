<template>
  <div class="reply-container">
    <div class="reply-register">
      <textarea
        placeholder="댓글을 입력해주세요."
        v-model="newReplyContent"
        @input="autoResize($event)"
      ></textarea>
      <button @click="addReply()">등록</button>
    </div>
    <div class="reply-lists-container">
      <ul class="reply-list">
        <li class="reply-item" v-for="(reply, idx) in replies" :key="idx">
          <div class="reply-box">
            <div class="reply-info">
              <span class="replyer">작성자</span>
            </div>
            <div class="reply-content">
              <textarea
                :readonly="!reply.editable"
                v-model="reply.content"
                @input="autoResize($event)"
              ></textarea>
            </div>
            <div>
              <span class="date">{{ reply.date }}</span>
            </div>
          </div>
          <div class="reply-manage">
            <button @click="editReply(idx)">{{ reply.editable ? '저장' : '수정' }}</button>
            <button @click="deleteReply(idx)">삭제</button>
          </div>
        </li>
      </ul>
    </div>
    <b-pagination v-model="currentPage"
                  :per-page="perPage"
                  @page-click="updatePageNum"
                  pills :total-rows="totalElement"
                  size="lg" align="fill">
    </b-pagination>
<!--    <pagination-compo></pagination-compo>-->
  </div>
</template>

<script>
import PaginationCompo from '../PaginationCompo.vue'
import axios from 'axios'
import { apiRequest } from '@/util/RequestUtil.js'
export default {
  components: { PaginationCompo },
  data() {
    return {
      reviewId: null,
      totalElement: 101,
      currentPage: 1,
      perPage: 10,
      newReplyContent: '',
      replies: {},
    }
  },
  created() {
    //exhigitionId 가져오기
    this.reviewId = this.$route.params.reviewId; // undefined
    console.log(this.reviewId)
    //데이터 불러오기
    this.replies = this.getExhibitReviews();
  },

  mounted() {
    //   this.$nextTick(() => {
    //     this.replies.forEach((reply, index) => {
    //       const textarea = this.$el.querySelectorAll('.reply-item .reply-content textarea')[index]
    //       if (textarea) {
    //         this.autoResize({ target: textarea })
    //       }
    //     })
    //   })
    // }
  },
  methods: {
    getExhibitReviews() {
      axios.get(`http:/localhost:8080/api/exhibition?exhibitionId=${this.exhibitionId}/review?pageNo=${this.currentPage}?pageNum=${this.perPage}`)
      .then(response =>{
        console.log(response);
        this.replies = response.data.content;
        this.totalElement = response.data.totalElement;
      }).catch(error => {
        console.log(error);
      })
    },

    updatePageNum(pageEvent, no) {
      this.currentPage = no;
      getExhibitReviews();
    },

    autoResize(event) {
      const textarea = event.target
      textarea.style.height = 'auto'
      textarea.style.height = textarea.scrollHeight + 'px'
    },

    addReply() {
      apiRequest('post', `http://localhost:8080/api/exhibition/review/${this.reviewId}/replies`, {
        reply : this.newReplyContent
      }).then(response => {
        console.log(response);
      })
    },

    editReply(index) {
      const reply = this.replies[index]
      reply.editable = !reply.editable //true
      if (reply.editable) {
        this.$nextTick(() => {
          const textarea = this.$el.querySelectorAll('.reply-item .reply-content textarea')[index]
          if (textarea) {
            this.autoResize({ target: textarea })
          }
        })
      }
    },
    deleteReply(index) {
      this.replies.splice(index, 1);
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.reply-container {
  /* background-color: #f8f5eb; */
  /* width: 60%;
    margin: 20px auto; */
}
.reply-register {
  position: relative;
  padding: 20px;
  border-radius: 5px;
  background-color: #3d3b3a;
  width: 60%;
  margin: 20px auto;
  /* margin-bottom: 20px; */
}

.reply-register textarea {
  width: calc(100% - 60px);
  /* background-color: transparent; */
  color: aliceblue;
  background-color: #f8f5eb;
}

.reply-register button {
  position: absolute;
  bottom: 20px;
  right: 10px;
  width: 60px;
  height: 30px;
  background-color: darkslategray;
  border-radius: 5px;
  color: #f8f5eb;
}

.reply-lists-container {
  width: 60%;
  margin: 0 auto;
  background-color: darkslategray;
  border-radius: 5px;
  padding: 20px 20px 10px;
}

li {
  list-style: none;
  background-color: #f8f5eb;
  border-radius: 5px;
  margin-bottom: 10px;
  padding: 10px;
}

textarea {
  /* background-color: white; */
  border-radius: 5px;
  padding: 10px;
  resize: none;
  overflow: hidden;
}

textarea.editable {
  border: 1px solid #669900;
}

.reply {
  margin-bottom: 10px;
}

.reply-item {
  display: flex;
  flex-direction: column;
  width: 100%;
  position: relative;
}

.reply-box {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.reply-content textarea {
  width: 100%;
  margin-bottom: 20px;
}

.reply-manage {
  position: absolute;
  bottom: 10px;
  right: 10px;
  display: flex;
  gap: 5px;
}

.reply-manage button {
  width: 60px;
  height: 30px;
  background-color: #3d3b3a;
  border-radius: 5px;
  color: #f8f5eb;
}

.date {
  margin-top: 5px;
}
</style>
