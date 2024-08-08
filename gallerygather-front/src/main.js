import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue3 from 'bootstrap-vue-3'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'
import 'bootstrap-icons/font/bootstrap-icons.css'
import axios from 'axios'
import { createPinia } from 'pinia'
// import { createVuetify } from 'vuetify'
// import 'vuetify/styles' // Vuetify 스타일 로드
// import * as components from 'vuetify/components'
// import * as directives from 'vuetify/directives'

const pinia = createPinia()
// const vuetify = createVuetify({
//   components,
//   directives
// }) // Vuetify 초기화
const app = createApp(App)
app.use(router)
app.use(pinia)
app.use(BootstrapVue3)
// app.use(vuetify)

app.mount('#app')
