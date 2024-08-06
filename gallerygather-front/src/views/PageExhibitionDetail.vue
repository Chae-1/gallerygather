<template>
    <section class="exhibition">
        <exhibition-detail-compo></exhibition-detail-compo>
        <exhibition-review-compo></exhibition-review-compo>
    </section>
</template>

<script>
import ExhibitionReviewCompo from '@/layout/components/ExhibitionReviewCompo.vue'
import ExhibitionDetailCompo from '../layout/components/ExhibitionDetailCompo.vue'

export default {
    components: { ExhibitionDetailCompo, ExhibitionReviewCompo },

}
</script>

<style scoped>

</style>