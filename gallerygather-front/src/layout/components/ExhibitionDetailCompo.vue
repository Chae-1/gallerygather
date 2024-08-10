<template>
    <div class="exhibition">
        <div class="exhibition-poster">
            <div class="poster-wrapper">
                <img :src="exhibitDetails.imgUrl" alt="Exhibition Poster Blurry" class="blurry-image" />
                <img :src="exhibitDetails.imgUrl" alt="Exhibition Poster Clear" class="clear-image" />
            </div>
        </div>
        <div class="exhibition-info">
            <span class="exhibition-status">진행중</span>
            <h1 class="exhibition-title">{{ exhibitDetails.title }}</h1>
            <div class="">
                <p>{{exhibitDetails.startDate}} ~ {{ exhibitDetails.endDate }}</p>
                <p>{{exhibitDetails.place}}</p>
            </div>
            <div class="exhibition-desc">
                <div  v-html="exhibitDetails.description"></div>
            </div>
            <div class="exhibition-stats">
                <span class="view">👁️ {{ exhibitDetails.readCount }}</span>
                <span class="likes">❤️ {{ exhibitDetails.likeCount }}</span>
                <span class="replies">💬 {{ exhibitDetails.reviewCount }}</span>
            </div>
            <a   :href="exhibitDetails.siteUrl" role="button" class="site-button">사이트 바로가기</a>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            exhibitionId: null,
            exhibitDetails: {}
        };
    },
    created() {
        this.exhibitionId = this.$route.params.exhibitionId;
        this.getExhibitDetails();
        // this.get();
    },
    mounted() {
    },
    methods: {
        async getExhibitDetails() {
            axios.get(`http://localhost:8080/api/exhibitions/details/${this.exhibitionId}`).then((response) => {
                this.exhibitDetails = response.data;
            })
        },
    }
}
</script>

<style scoped>
.exhibition {
    display: flex;
    flex-direction: row;
    gap: 20px;
    justify-content: center;
    align-items: center;
}

.exhibition-poster {
    position: relative;
    width: 40vw;
    height: 90vh;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
}

.poster-wrapper {
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.blurry-image {
    position: absolute;
    width: 100%;
    height: 100%;
    object-fit: cover;
    filter: blur(10px);
    transform: scale(1.3);
}

.clear-image {
    position: relative;
    width: 50%; /* Adjust the size as needed */
    height: auto;
    z-index: 1;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); /* Optional: Add a shadow for better visibility */
    transition: transform 0.2s;
}

.clear-image:hover {
    transform: scale(1.1);
}

.exhibition-info {
    margin: 5%;
    flex: 2;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.exhibition-info h1 {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 10px;
}

.exhibition-info p {
    margin: 5px 0;
}

.exhibition-status {
    margin-bottom: 10px;
}

.exhibition-desc {
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 10px 10px;
    margin: 10px 0;
    min-height: 30vh;
}

.exhibition-stats {
    display: flex;
    gap: 10px;
    margin-top: 10px;
}
/* 
.ticket-button {
    margin-top: 20px;
    padding: 10px 15px;
    background-color: #f0f0f0;
    border: 1px solid #ccc;
    cursor: pointer;
}

.ticket-button:hover {
    background-color: #e0e0e0;
} */

a {
    color: #737373;
}

a:hover {
    color:#669900;
}
.site-button {
    padding: 10px 15px;
    margin: 20px 0 0;
    background-color: #e0e0e0;
    border: 1px solid #ddd;
}
</style>