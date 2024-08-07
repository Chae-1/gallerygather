<template>
    <div class="review-details">
        <div class="review-container">

            <div class="review-box">
                <div>리뷰</div>
                <div class="title">너무 재밌는 전시회였습니다.</div>
                <div class="review-">
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
            <!-- <quill-editor v-model="content" placeholder="게시글이 없습니다."></quill-editor> -->
            <display-compo :content="reviewDetails.content" ></display-compo>
        </div>
    </div>
</template>

<script>
import DisplayCompo from './main/DisplayCompo.vue'
import ReviewRepliesCompo from './ReviewRepliesCompo.vue'
// import QuillEditor from './QuillEditor.vue'

export default { 
    components: {
        DisplayCompo,
        ReviewRepliesCompo,
        // QuillEditor
    },
    data() {
        return {
            reviewDetails: {

                content: `
            <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
            <HTML>
            <HEAD>
                <TITLE>Understanding HTML File Format</TITLE>
            </HEAD>
            <BODY>
                <P>Hello World!</p>
                <div>이것참</div>
                <h3>맞아?</h3>
                <p>근ㄱ길 아니 긴 글 긴글이ㅏ러디ㅏ머리ㅏㅓㅎ;ㅁ디험;'ㅣㅏㅎㄷ;'ㅣ함;'ㅣ핟;'히ㅓㅁ;ㅎ'ㅣㅓㅣㅏ더히;ㄷ험;ㅣ하ㅓㄷ;ㅣ헏
                    이ㅏㅁ허ㅣ다ㅓㅎ미;ㅓㅎㄷ;ㅣ핟;ㅣㄱ헉;헏ㅁ허;</p>
            </BODY>
            </HTML>
            `
            },
        }
    },
    created() {

    },
    methods: {
        // getReviewDetails
    }

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