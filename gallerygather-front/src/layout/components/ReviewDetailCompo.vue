<template>
    <div class="review-details">
        <div class="review-container">
            <div class="review-box">
                <div>리뷰 상세보기</div>
                <div class="title">{{ review.title }}</div>
                <div class="review-info">
                    <span class="reviewer">안수영</span>
                    <span class="scope">10.0</span>
                </div>
                <div>
                    <span class="review-date">2024-04-18</span>
                    <span class="view">89</span>
                    <span class="likes">2</span>
                    <span class="replies">0</span>
                </div>
            </div>
            <div class="exhibit-img">
                <img src="../../assets/img/daniel_arsham.png" alt="daniel_arsham">
            </div>
        </div>         
        <div class="review-content">
            <p>내용: {{ review.content }}</p>
            <p>평점: {{ review.rating }}</p>
        </div>
    </div>
</template>

<script>


export default {
    data() {
    return {
      review: null
    };
  },
  created(){
    EventBus.$on('review-submitted', this.updateReview);
  },
  methods: {
    updateReview(review) {
      this.review = review;
    }
  },
  beforeDestroy() {
    EventBus.$off('review-submitted', this.updateReview);
  }
};
</script>

<style scoped>
.review-details {
    display: flex;
    flex-direction: column;
    gap: 20px;
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

.review-box {
    
}

.exhibit-img {
    height: 33vh;
}

.exhibit-img > img {
    position: inherit;
    height: 100%;
}

.review-content textarea{
    position: relative;
    width: 60%;
    margin: 20px auto;
}
</style>