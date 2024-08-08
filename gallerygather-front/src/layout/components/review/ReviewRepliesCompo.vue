<template>
    <div class="reply-container">
        <div class="reply-register">
            <textarea placeholder="댓글을 입력해주세요." v-model="newReplyContent" @input="autoResize($event)"></textarea>
            <button>등록</button>
        </div>
        <div class="reply-lists-container">
            <ul class="reply-list">
                <li class="reply-item" v-for="(reply, idx) in replies" :key="idx">
                    <div class="reply-box">
                        <div class="reply-info">
                            <span class="replyer">작성자</span>
                        </div>
                        <div class="reply-content">
                            <textarea :readonly="!reply.editable" v-model="reply.content" @input="autoResize($event)"></textarea>
                        </div>
                        <div>
                            <span class="date">{{ reply.date }}</span>
                        </div>
                    </div>
                    <div class="reply-manage">
                        <button @click="editReply(idx)"> {{ reply.editable ? '저장' : '수정' }}</button>
                        <button @click="deleteReply(idx)">삭제</button>
                    </div>
                </li>
            </ul>
        </div>
        <pagination-compo></pagination-compo>
    </div>
</template>

<script>
import PaginationCompo from '../PaginationCompo.vue';
export default {
    components: { PaginationCompo },
    data() {
        return {
            newReplyContent: '',
            replies: [],
        };
    },
    created() {
        //데이터 불러오기
        this.replies = [
        {content: '댓글 이러쿵 저렇궁궁\nEhㅇ\nㅇㄹㅇㄹ\nㅇㄹㅇㄹㅇ\nㅇㄹㅇㄹㅇㅇㄹㅇㄹ\nㅇㄹ', date: '2024-07-24', editable: false},
        {content: '댓글 이러쿵 저렇궁궁', date: '2024-07-24', editable: false }
        ]
    },
    mounted() {
        this.$nextTick(() => {
            this.replies.forEach((reply, index) => {
                const textarea = this.$el.querySelectorAll('.reply-item .reply-content textarea')[index]
                if (textarea) {
                    this.autoResize({target : textarea});
                }
            })
        });
    },
    methods: {
        autoResize(event) {
            const textarea = event.target;
            textarea.style.height = 'auto';
            textarea.style.height = textarea.scrollHeight + 'px';
        },
        addReply() {

        },
        editReply(index) {
            const reply = this.replies[index];
            reply.editable = !reply.editable; //true
            if (reply.editable) {
                this.$nextTick(() => {
                    const textarea = this.$el.querySelectorAll('.reply-item .reply-content textarea')[index];
                    if (textarea) {
                        this.autoResize({target: textarea});
                    }
                });
            }
        },
        deleteReply(index) {
            this.replies.splice(index, 1);
        }
    }
}
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.reply-container {
    width: 60%;
    margin: 20px auto;
}
.reply-register {
    position: relative;
    padding: 20px;
    border-radius: 5px;
    background-color: red;
    margin-bottom: 20px;
}

.reply-register textarea {
    width: calc(100% - 60px);
    /* background-color: transparent; */
    color: aliceblue;
}

.reply-register button {
    position: absolute;
    bottom: 20px;
    right: 10px;
    width: 60px;
    height: 30px;
    background-color: black;
    border-radius: 5px;
    color: white;
}

.reply-lists-container {
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
    padding: 10px;
    resize: none;
    overflow: hidden;
}

textarea.editable {
    border: 1px solid black;
}

.reply {
    margin-bottom: 10px;
}

.reply-item {
    display: flex;
    flex-direction: column;
    width: 100%;
    position: relative;
}

.reply-box {
    display: flex;
    flex-direction: column;
    width: 100%;
}

.reply-content textarea {
    width: 100%;
    margin-bottom: 20px;
}

.reply-manage {
    position: absolute;
    bottom: 10px;
    right: 10px;
    display: flex;
    gap: 5px;
}

.reply-manage button {
    width: 60px;
    height: 30px;
    background-color: black;
    border-radius: 5px;
    color: white;
}

.date {
    margin-top: 5px;
}
</style>