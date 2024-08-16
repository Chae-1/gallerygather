<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
import { apiRequest } from '@/util/RequestUtil.js'

export default {
  mounted() {
    apiRequest('get', "http://192.168.230.3:8080/test/canaccess/logined")
      .then(response => console.log(response.data))
      .catch(error => console.log(error))
  }
}
</script>
