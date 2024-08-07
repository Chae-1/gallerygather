<template>
    <div class="review-detail">
        <review-detail-compo ></review-detail-compo>
        <review-replies-compo></review-replies-compo>
    </div>
</template>

<script>
import ReviewDetailCompo from '@/layout/components/review/ReviewDetailCompo.vue'
import ReviewRepliesCompo from '@/layout/components/review/ReviewRepliesCompo.vue'
export default {
    components: { ReviewDetailCompo, ReviewRepliesCompo },

}
</script>

<style>
</style>