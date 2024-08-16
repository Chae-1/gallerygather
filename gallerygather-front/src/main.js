import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue3 from 'bootstrap-vue-3'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'
import 'bootstrap-icons/font/bootstrap-icons.css'
import {createPinia} from "pinia";
import VCalendar from 'v-calendar'
import 'v-calendar/style.css';
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
<!-- 작성자: 채형일-->


const pinia = createPinia()
const app = createApp(App);
app.use(pinia);
app.use(router);
app.use(BootstrapVue3);
app.use(VCalendar,{});
app.mount('#app')
