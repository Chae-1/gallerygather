<template>
    <div class="exhibition-reviews">
        <div>
            <span>리뷰 목록</span>
            <button>리뷰 쓰기</button>
        </div>
        <div class="review-content">
            <ul class="review-list">
                <li class="review-item">
                    <div class="review-box">
                        <a href="">
                            <div class="reviewbox-title">
                                <p>90세 노장 미셸 들라크루아가 그리는 파리의 예술</p>
                            </div>
                            <span class="review_detail">어쩌구저쩌구 예술이 아주 그냥</span>
                        </a>
                        <div class="reviewbox-by">
                            <span class="byname">홍길동</span>
                        </div>
                        <div class="reviewbox-sub">
                            <span class="text-date">2024-02-28</span>
                            <div class="access-nums">
                                <span class="view">👁️ 101</span>
                                <span class="likes">❤️ 31</span>
                                <span class="replies">💬 11</span>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="review-item">
                    <div class="review-box">
                        <a href="http://www.naver.com">
                            <div class="reviewbox-title">
                                <p>90세 노장 미셸 들라크루아가 그리는 파리의 예술</p>
                            </div>
                            <span class="review_detail">어쩌구저쩌구 예술이 아주 그냥</span>
                        </a>
                        <div class="reviewbox-by">
                            <span class="byname">홍길동</span>
                        </div>
                        <div class="reviewbox-sub">
                            <span class="text-date">2024-02-28</span>
                            <div class="access-nums">
                                <span class="view">👁️ 101</span>
                                <span class="likes">❤️ 31</span>
                                <span class="replies">💬 11</span>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            exhibitReviewList: []
        };
    },
    created() {
        this.getExhibitReviewList;
    },
    methods: {
        async getExhibitReviewList() {
            this.exhibitReviewList = []; //await this.$api.만들어야함. 
        }
    }
}
</script>

<style scoped>
.exhibition-reviews {
    padding: 20px;
    display: flex;
    flex-direction: column;
    /* background-color: #f5f3ec;; */
}

.exhibition-reviews > div > span {
    margin-left: 2.5%;
    padding-left: 10px;
    font-weight: bold;
}

.exhibition-reviews button {
    background-color: #ba9d73;
    color: white;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    margin-bottom: 10px;
    margin-right: 2.5%;
    border-radius: 5px;
    float: right;
}

.exhibition-reviews button:hover {
    background-color: #a78558;
}

.review-content {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.review-list {
    list-style: none;
    padding: 0;
    margin: 0;
    width: 100%;
}

.review-item {
    width: 95%;
    margin: 0 auto 20px;
}

.review-box {
    border: 1px solid #ddd;
    border-radius: 10px;
    padding: 20px;
    transition: transform 0.2s;
}

.review-box:hover {
    transform: scale(1.012);
}

.reviewbox-title p {
    font-size: 18px;
    font-weight: bold;
    margin: 0 0 10px 0;
}

.reviewbox-by {
    margin-top: 10px;
}

.reviewbox-sub {
    display: flex;
    align-content: center;
    margin-top: 10px;
    color: #8a7b6a;
    gap: 13px;
}

.access-nums {
    display: flex;
    gap: 10px;
}

.access-nums .view::before,
.access-nums .likes::before,
.access-nums .replies::before {
    display: inline-block;
    /* content: url('view-icon-url'); */
}


</style>