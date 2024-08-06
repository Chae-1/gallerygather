<template>
   <div>
    <h1>
        후기 작성하기
    </h1>
    <div>
       <h2> {{ gtitle }}</h2>
       <p>{{ period }}</p>
       <p>{{ rating }}</p>
    </div>
    <form>
        id: {{ review.id}}
        <br/>
        title: <input type="text" name="title" v-model="review.title"/>
        <br/>
        content: <textarea name="content" v-model="review.content"/>
        <br/>
        createdAt: {{ review.createdAt }}
        <br/>
        updatedAt: {{ review.updatedAt }}
        <button type="submit" v-if="!idx">등록</button>

    </form>
  </div>
</template>

<script>
import axois from 'axios';

export default {
    data(){
        return{
            title: '헬로키티 전시',
            period: '2024.01.01 ~ 2024.12.12',
            rating: '⭐3',
            idx: this.$route.params.idx,
            review: { id: '', title: '', content: '', createdAt: '', updatedAt: '' },
        };
    },

}
</script>

<style scoped>

</style>