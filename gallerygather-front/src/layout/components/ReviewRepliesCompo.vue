<template>
    <div class="reply-container">
        <div class="reply-register">
            <textarea name="register" placeholder="댓글을 입력하세요"></textarea>
            <button type="button2">등록</button>
        </div>
        <div class="reply-lists-container">
            <ul class="reply-list">
                <li class="reply-item">
                    <div class="reply-box">
                        <div class="reply-info">
                            <span class="replyer">작성자</span>
                        </div>
                        <div class="reply-content">
                            <textarea name="reply">
                                댓글 이러쿵 저렇궁궁
                            </textarea>
                        </div>
                        <div>
                            <span class="date">2024-07-24</span>
                        </div>
                    </div>
                    <div class="reply-manage">
                        <button>수정</button>
                        <button>삭제</button>
                    </div>                    
                </li>
                <li class="reply-item">
                    <div class="reply-box">
                        <span class="replyer">작성자</span>
                        <textarea class="reply-content">
                            댓글 이러쿵 저렇궁궁
                        </textarea>
                        <span class="date">2024-07-24</span>
                    </div>
                    <div class="reply-manage">
                        <button>수정</button>
                        <button>삭제</button>
                    </div>                    
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style scoped>


* {
    /* 스타일 초기화 */
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.reply-container {
    width: 60%;
    margin: 20px auto;
}
.reply-register {
    padding: 20px;
    /* border: 1px solid red; */
    border-radius: 5px;
    background-color: red;
    margin-bottom: 20px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    gap: 10px;
}

.reply-register textarea {
    width: 90%;
}

button {
    position: relative;
    width: 70px;
    height: 40px;
    background-color: black;
    border-radius: 5px;
}
.reply-register button {
    position: relative;
    width: 70px;
    height: 40px;
    background-color: black;
    border-radius: 5px;
}

.reply-lists-container{
    background-color: blue;
    border-radius: 5px;
    padding: 20px 20px 10px;
}

li {
    list-style: none;
    background-color: pink;
    border-radius: 5px;
    margin-bottom: 10px;
    padding: 10px;
}

textarea {
    background-color: white;
    border-radius: 5px;
}

.reply-item {
    display: flex;
    flex-direction: row;
    width: 100%;
}

.reply-box {
    display: flex;
    flex-direction: column;
}

.reply-content {
    width: 400px;
}

.reply-manage {
    display: flex;
    flex-direction: row;
    gap: 5px;
    margin-left: 10px;
}

.reply-manage button {
}
</style>