<template>
    <div class="review-details">
        <div class="review-container">
            <div class="review-box">
                <p>후기 상세보기</p>
                <br/>
                <h2 class="title">헬로키티 전시회</h2>
                <div class="review-info">
                    <span class="reviewer">안수영</span>
                    <br/>
                    <span class="scope">⭐ 4</span>
                </div>
                <div>
                    후기 작성일자: <span class="review-date">2024-04-18</span>
                    <br/>
                    <span class="view">👁️ 89</span>
                    <br/>
                    <span class="likes">❤️ 2</span>
                    <br/>
                    <span class="replies">💬 0</span>
                </div>
            </div>
            <div class="exhibit-img">
                <img src="../../assets/img/daniel_arsham.png" alt="daniel_arsham">
            </div>
        </div>         
        <div class="review-content">
            <p>내용: 진짜 재밌더라고요~ 우하하</p>
            <!-- <quill-editor v-model="content" placeholder="게시글이 없습니다."></quill-editor> -->
        </div>
    </div>
</template>

<script>

import ReviewRepliesCompo from './ReviewRepliesCompo.vue'
// import QuillEditor from './QuillEditor.vue'

export default { 
    components: {
        ReviewRepliesCompo,
        // QuillEditor
    },
    
}


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
    color: aliceblue;
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

.review-content {
    margin: 10px auto;
    width: 60%;
    min-height: 40vh;
    border: 1px solid #ddd;
    border-radius: 4px;
}
</style>