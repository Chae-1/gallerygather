<template>
    <div class="exhibition-reviews">
        <div>
            <span>리뷰 목록</span>
            <button @click="goToWrite">리뷰 쓰기</button>
        </div>
        <div class="review-content">
            <div v-if="exhibitReviewList.length === 0" class="non-review">
                <span>아직 작성된 리뷰가 없습니다.</span>
            </div>
            <ul class="review-list" v-else>
                <li class="review-item" v-for="(review, idx) in exhibitReviewList" :key="idx">
                    <div class="review-box">
                        <router-link :to="{ path: '/exhibitiondetails/'+ exhibitionId +'/reviewdetails/' + review.id }">
                            <div class="reviewbox-title">
                                <p>{{ review.title }}</p>
                            </div>
                            <div v-html="review.content" class="review_detail"></div>
                        </router-link>
                        <div class="reviewbox-by">
                            <span class="byname">{{ review.reviewer }}</span>
                        </div>
                        <div class="reviewbox-sub">
                            <span class="text-date">{{ review.regDate }}</span>
                            <div class="access-nums">
                                <span class="view">👁️ {{ review.viewCount }}</span>
                                <span class="like">❤️{{ review.likeCount }} </span>
                                <span class="replies">💬 {{ review.replyCount }}</span>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>'

    <pagination-compo
        :currentPage="currentPage"
        :perPage="perPage"
        :totalRows="totalElement"
        @page-changed="onPageChanged">
    </pagination-compo>
</template>

<script>
import {userStore} from "@/store/userStore.js";
import PaginationCompo from './PaginationCompo.vue';
import axios from 'axios';
export default {
    components: {PaginationCompo},
    data() {
        return {
            totalElement: null,
            exhibitionId: null,
            currentPage: 1,
            perPage: 2,
            exhibitReviewList: [],
            
        };
    },
    created() {
        console.log('created()');
        this.exhibitionId = this.$route.params.exhibitionId;
        console.log(`exhibitionId : ${this.exhibitionId}`);
        this.getExhibitReviewList();
    },
    mounted() {
        console.log('mounted()');
    },
    methods: {
        async getExhibitReviewList() {
            try {
                await axios.get(`http://localhost:8080/api/exhibition/${this.exhibitionId}/review?pageNo=${this.currentPage}&pagePer=${this.perPage}`)
                    .then((response) => {
                    this.exhibitReviewList = response.data.content;
                    this.totalElement = response.data.totalElements;
                    console.log(response.data.content);
                    console.log(response.data.totalElements);
                    })
            }catch (error) {
                console.error("리뷰를 불러오는 중 오류가 발생했습니다.", error)
            }
        },
        onPageChanged(newPage) {
            console.log(`reviewcompo에서 받은 newpage ${newPage}`);
            this.currentPage = newPage;
            this.getExhibitReviewList();
        },
        goToWrite() {
            const store = userStore();
            
            if (store.loginCheck()) {
                this.$router.push({ name: 'ReviewWrite'});
            } else {
                alert("로그인 후 리뷰 작성이 가능합니다.");
            }
            
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
    background-color: #3d3b3a;
    color: #669900;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    margin-bottom: 10px;
    margin-right: 2.5%;
    border-radius: 5px;
    float: right;
    transition: transform 0.2s;
}

.exhibition-reviews button:hover {
    transform: scale(1.025);
}

.review-content {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.non-review {
    border:1px solid #ddd;
    border-radius: 5px;
    width: 95%;
    margin: 20px auto 0;
    text-align: center;
    padding: 30px;
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

.review_detail {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
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