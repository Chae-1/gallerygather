<template>
    <div class="exhibition">
        <div class="exhibition-poster">
            <div class="poster-wrapper">
                <img src="../../assets/img/daniel_arsham.png" alt="Exhibition Poster Blurry" class="blurry-image" />
                <img src="../../assets/img/daniel_arsham.png" alt="Exhibition Poster Clear" class="clear-image" />
            </div>
        </div>
        <div class="exhibition-info">
            <span class="exhibition-status">{{exhibitDetails.status}}</span>
            <h1 class="exhibition-title">{{ exhibitDetails.title }}</h1>
            <div class="">
                <p>{{exhibitDetails.startDate}} - {{ exhibitDetails.endDate }}</p>
                <p>{{exhibitDetails.place}}</p>
            </div>
            <div class="exhibition-desc">
                {{ exhibitDetails.content }}
            </div>
            <div class="exhibition-stats">
                <span class="view">👁️ {{ exhibitDetails.view }}</span>
                <span class="likes">❤️ {{ exhibitDetails.likes }}</span>
                <span class="replies">💬 {{ exhibitDetails.replies }}</span>
            </div>
            <button class="ticket-button">예매처 보기</button>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            exhibitDetails: {
                status: "진행중",
                title: "[미술] 국보순회전: 모두의 곁으로 ‘금관총 금관, 그리고 이사지왕’",
                startDate: "2024.06.06",
                endDate: "2024.09.01",
                place: "보령석탄박물관",
                content: "문화는어쩌고저쩌고",
                view: 43,
                likes: 10,
                replies: 2,
            }
        }
    },
    created() {
        this.getExhibitDetails();
    },
    methods: {
        async getExhibitDetails() {
            // this.exhibitDetails = []; //await this.$api.만들어야함. 
        }
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
    background-color: #eae7e2;;
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

.ticket-button {
    margin-top: 20px;
    padding: 10px 15px;
    background-color: #f0f0f0;
    border: 1px solid #ccc;
    cursor: pointer;
}

.ticket-button:hover {
    background-color: #e0e0e0;
}
</style>