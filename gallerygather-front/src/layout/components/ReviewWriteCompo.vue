<template>
    <div class="review-container">
      <h2 class="title">후기 작성하기</h2>
      <div class="exhibition-info">
        <img src="../../assets/img/kitty.jpg" alt="전시 이미지" class="exhibition-image">
        <div class="exhibition-detail">
          <h3>{{ gtitle }}</h3>
          <p>기간: {{ period }}</p>
          <p>평점: {{ avgRating }}</p>
        </div>
      </div>
      <form @submit.prevent="submit">
        별점을 입력하세요. <star-rating v-model:rating="review.rating" :increment="0.5" :star-size="20"></star-rating>
      <br />
        제목: <input type="text" class="input-title" v-model="review.title" placeholder="제목을 입력해주세요." required />
        <br />
        내용: <QuillEditor v-model:modelValue="review.content" placeholder="내용을 입력해 주세요." required></QuillEditor>
        <br />
        <button type="submit" class="submit-button">등록</button>
      </form>
    </div>
  </template>
  
  <script>
  import QuillEditor from './QuillEditor.vue';
  import StarRating from 'vue-star-rating';
  
  export default {
    components: {
      QuillEditor,
      StarRating
      
    },
    data() {
      return {
        gtitle: '헬로키티 전시',
        period: '2024.01.01 ~ 2024.12.12',
        avgRating: '⭐ 3', 
        review: { id: '', title: '', content: '', rating: 0, createdAt: '', updatedAt: '' }
      };
    },
    methods: {
      submit() {
        console.log('제목:', this.review.title);
        console.log('내용:', this.review.content);
        console.log('평점:', this.review.rating);
        this.$emit('review-submitted', {
          title: this.review.title,
          content: this.review.content,
          rating: this.review.rating
        });
        this.review.title = '';
        this.review.content = '';
        this.review.rating = 0;
      }
    },
    watch: {
      'review.content': function (newContent) {
        console.log('Parent component review.content changed:', newContent);
      },
      'review.rating': function (newRating) {
        console.log('Parent component review.rating changed:', newRating);
      }
    },
    mounted() {
      console.log('Mounted with initial review:', this.review);
    }
  };
  </script>
  
  <style scoped>
  .review-container {
    background-color: #f8f4e5;
    padding: 20px;
    border-radius: 8px;
    width: 800px;
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
  
  .exhibition-detail {
=======

}
</script>

<style scoped>
.review-container {
  /* background-color: #f8f4e5; */
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
  
  .input-title {
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .input-content {
    height: 400px;
  }
  
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
  