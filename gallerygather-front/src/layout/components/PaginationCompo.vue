<template>
  <div class="mt-3">
    <b-pagination
      ref="pagination"
      class="custom-pagination"
      v-model="localCurrentPage"
      :per-page="perPage"
      pills="true"
      @page-click="handleClick"
      :total-rows="totalRows"
      size="lg"
      align="center">
    </b-pagination>
  </div>
</template>

<script>
export default {
  props: {
    currentPage: {
      type: Number,
      required: true
    },
    perPage: {
      type: Number,
      required: true
    },
    totalRows: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      localCurrentPage: this.currentPage
    }
  },
  watch: {
    currentPage(newVal) {
      this.localCurrentPage = newVal
    }
  },
  mounted() {
    this.addClickListeners()
  },
  methods: {
    addClickListeners() {
      const paginationButtons = this.$refs.pagination.$el.querySelectorAll('li.page-item')
      paginationButtons.forEach(button => {
        button.addEventListener('click', this.handleClick)
      })
    },
    handleClick(event, pageNo) {
      this.$emit('onclick-change', pageNo)
    }
  },
  beforeDestroy() {
    const paginationButtons = this.$refs.pagination.$el.querySelectorAll('li.page-item')
    paginationButtons.forEach(button => {
      button.removeEventListener('click', this.handleClick)
    })
  }
}
</script>

<style scoped>
.mt-3 {
  margin-bottom: 15px;
}
</style>