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
      <div v-if="replies.length === 0" class="non-reply">
        <span>아직 작성된 댓글이 존재하지 않습니다.</span>
      </div>
      <ul class="reply-list" v-else>
        <li class="reply-item" v-for="(reply, index) in replies" :key="index">
          <div class="reply-box">
            <div class="reply-info">
              <span class="replyer">{{ reply.replyAuthorNickName }}</span>
            </div>
            <div class="reply-content">
              <textarea
                :class="{'editable-text' : reply.editable, 'uneditable-text' : !reply.editable}"
                :readonly="!reply.editable"
                v-model="reply.replyContent"
                @input="autoResize($event)"
              ></textarea>
            </div>
            <div>
              <span class="date">{{ reply.replyRegDate }}</span>
            </div>
          </div>
          <div class="reply-manage" v-if="getUser() == reply.replyAuthorEmail">
            <button v-if="!reply.editable" @click="editReply(index)">수정</button>
            <button v-if="!reply.editable" @click="deleteReply(reply.replyId)">삭제</button>
            <button v-if="reply.editable" @click="updateReply(reply.replyId, reply.replyContent)">저장</button>
          </div>
        </li>
      </ul>
    </div>
    <pagination-compo :currentPage="currentPage"
                      :perPage="perPage"
                      :totalRows="totalElement"
                      @onclick-change="onPageChanged"
                      >
    </pagination-compo>
  </div>
</template>

<script>
import PaginationCompo from '../PaginationCompo.vue'
import axios from 'axios'
import { apiRequest } from '@/util/RequestUtil.js'
import {userStore} from "@/store/userStore.js";

export default {
  components: { PaginationCompo },
  data() {
    return {
      reviewId: null,
      exhibitionId: null,
      totalElement: null,
      currentPage: 1,
      perPage: 2,
      newReplyContent: '',
      replies: {}
    }
  },
  created() {
    //exhigitionId 가져오기
    this.reviewId = this.$route.params.reviewId; // undefined
    this.exhibitionId = this.$route.params.exhibitionId;
    console.log(this.reviewId);
    console.log(this.exhibitionId);
    //데이터 불러오기
    this.getExhibitReviews();
  },

  mounted() {

  },
  methods: {
    getUser() {
      const store = userStore();
      return store.getUser();
    },

    setReplies(response) {
      this.replies = response.data.content;
      this.totalElement = response.data.totalElements;
      console.log(`pageNo: ${this.currentPage}`);
      console.log(`total: ${this.totalElement}`);
    },

    async getExhibitReviews() {
      axios.get(`http://localhost:8080/api/exhibition/${this.exhibitionId}/review/${this.reviewId}/replies?page=${this.currentPage-1}&size=${this.perPage}&sort=regDate,desc`)
        .then(response => {
          console.log(response)
          this.setReplies(response)
        }).catch(error => {
        console.log(error)
      })
    },

    onPageChanged(newPage) {
      console.log("클릭 : " + newPage);
      this.currentPage = newPage;
      this.getExhibitReviews();
    },

    autoResize(event) {
      const textarea = event.target
      textarea.style.height = 'auto'
      textarea.style.height = textarea.scrollHeight + 'px'
    },

    async addReply() {
      const store = userStore();
      if (store.loginCheck()) { //로그인 한 상태라면 댓글 등록 가능
        try {
          apiRequest('post', `http://localhost:8080/api/exhibition/review/${this.reviewId}/replies`, {
            reply: this.newReplyContent
          }).then(response => {
            if (response.status === 201) { //정상적으로 댓글 등록 성공
              this.currentPage = 1;
              this.getExhibitReviews(); //데이터 다시 가져오기
              this.newReplyContent = '';
            }
          })

        } catch (error) {
          console.log(error);
        }

      } else { // 로그인 X ->  댓글등록 불가
        alert("로그인 후 이용해 주세요.");
      }
    },

    editReply(index) {
      console.log(`index, replyid: ${index}`);
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

    async deleteReply(replyId) {
      const store = userStore();
      if (store.loginCheck()) { //로그인 상태 다시 체크
        try {
          await apiRequest("delete", `http://localhost:8080/api/exhibition/reviews/${this.reviewId}/replies/${replyId}`)
          .then(response => {
            if (response.status == 201) {
              this.getExhibitReviews();
            } else {
              alert("댓글 삭제에 실패하였습니다. 잠시 후 다시 시도해주세요.")
            }
          })
        } catch (error) {
          console.log(error);
          alert("잠시 후 다시 시도해주세요.");
        }
      } else {
        alert("로그아웃된 사용자입니다.");
      }

      console.log(`삭제: ${replyId}`);
      this.replies.splice(replyId, 1);
    },

    async updateReply(replyId, replyContent) {
      console.log("전달 받는 데이터가 수정된 데이터냐 :" +replyContent);
      const store = userStore();
      if (store.loginCheck) { //로그인 여부 한 번 더 체크
        try {
          await apiRequest("put", `http://localhost:8080/api/exhibition/reviews/${this.reviewId}/replies/${replyId}`, {
            replyContent: replyContent
          }).then(response => {
            if (response.status == 201) { //정상적으로 수정되었다면,
              this.getExhibitReviews();
            } else {
              alert("댓글 수정에 실패하였습니다. 잠시 후 다시 시도해주세요.");
            }
          })
        } catch(error) {
          console.log(error);
          alert("댓글 수정에 실패하였습니다. 잠시 후 다시 시도해주세요.");
        }
      } else { //로그인 해제 됐다면
        alert("로그인 후 수정해주세요.");
      }
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
  color: #3d3b3a;
  background-color: #f8f5eb;
}

.editable-text {
  border: 1px solid darkslategray;
  ;
}

/* .uneditable-text {
  background-color: blue;
} */

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

.non-reply {
  text-align: center;
  padding-bottom: 10px;;
}

.non-reply span{
  color: #ffffff;
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
  color: black;
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
  color: black;
}
</style>
