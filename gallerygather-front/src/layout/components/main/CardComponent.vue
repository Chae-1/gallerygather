<script>
export default {
  name: "CardComponent",
  data() {
    return {
      rows: 100,
      currentPage: 1,
    }
  }
}
</script>

<template>
  <b-card-group deck>
    <b-card title="Title" img-src="https://picsum.photos/100/100/?image=41" img-alt="Image" img-top>
      <b-card-text>
        This is a wider card with supporting text below as a natural lead-in to additional content.
        This content is a little bit longer.
      </b-card-text>
      <template #footer>
        <small class="text-muted">Last updated 3 mins ago</small>
      </template>
    </b-card>

    <b-card title="Title" img-src="https://picsum.photos/100/100/?image=41" img-alt="Image" img-top>
      <b-card-text>
        This card has supporting text below as a natural lead-in to additional content.
      </b-card-text>
      <template #footer>
        <small class="text-muted">Last updated 3 mins ago</small>
      </template>
    </b-card>

    <b-card title="Title" img-src="https://picsum.photos/100/100/?image=41" img-alt="Image" img-top>
      <b-card-text>
        This is a wider card with supporting text below as a natural lead-in to additional content.
        This card has even longer content than the first to show that equal height action.
      </b-card-text>
      <template #footer>
        <small class="text-muted">Last updated 3 mins ago</small>
      </template>
    </b-card>
  </b-card-group>

  <div class="mt-3">
    <h6>Large Pills</h6>
    <b-pagination v-model="currentPage" :per-page=10 pills :total-rows="rows" size="lg" align="fill"></b-pagination>
  </div>

</template>

<style scoped>

</style>