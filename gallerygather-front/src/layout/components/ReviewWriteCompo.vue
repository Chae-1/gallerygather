<template>
   <div class="review-container">
    <h2 class="title">
        후기 작성하기
    </h2>
    <div class="exhibition-info">
    <img src="../../assets/img/kitty.jpg" alt="전시 이미지" class="exhibition-image">
       
       <div class="exhibition-detail">
        <h3> {{ gtitle }}</h3>
            <p>기간: {{ period }}</p>
            <p>평점: {{ rating }}</p>
       </div>     
    </div>
    <form @submit.prevent="submit">
        <!-- id: {{ review.id}} -->
        <br/>
        제목: <input type="text" class="input-title" v-model="review.title" placeholder="제목을 입력해주세요." required/>
        <br/>
        내용: <textarea class="input-content" v-model="review.content" placeholder="내용을 입력해 주세요." required></textarea>
        <br/>
        <button type="submit" class="submit-button">등록</button>

    </form>
  </div>
</template>

<script>
import axois from 'axios';

export default {
    data(){
        return{
            gtitle: '헬로키티 전시',
            period: '2024.01.01 ~ 2024.12.12',
            rating: '⭐3',
            // idx: this.$route.params.idx,
            review: { id: '', title: '', content: '', createdAt: '', updatedAt: '' },
        };
    },

    methods: {
        submit(){
            console.log('Title:', this.review.title);
            console.log('Content:', this.review.content);
            this.$emit('review-submitted', {
                title: this.review.title,
                content: this.review.content
            
            });
            this.review.title = '';
            this.review.content = '';
            

        }
    }

}
</script>

<style scoped>
.review-container {
  background-color: #f8f4e5;
  padding: 20px;
  border-radius: 8px;
  width: 700px;
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
.exhibition-detail{
    margin-top: 20px;

}
/*
.status {
  background-color: #ffcc00;
  padding: 2px 6px;
  border-radius: 4px;
  display: inline-block;
  margin-bottom: 10px;
}

.rating {
  font-size: 14px;
  color: #888;
} */

.input-title, .input-content {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.input-content {
  height: 200px;
  resize: none;
}

/* .file-upload {
  margin-bottom: 20px;
}

.file-input {
  display: block;
  margin-top: 10px;
} */

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