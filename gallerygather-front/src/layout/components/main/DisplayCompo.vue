<template>
  <div v-html="content"></div> <!-- Use v-html to render HTML content -->
</template>
  
<script>
  export default {
    props: {
      content: {
        type: String,
        required: true
      }
    }
  };
</script>

<style scoped>
  div {
    padding: 30px 20px;
    margin: 0;
  }
</style>