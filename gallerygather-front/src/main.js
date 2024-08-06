<<<<<<< HEAD

=======
import './assets/base.css'
>>>>>>> 5a1de5acdd6a6e25a800659e52071ca2aa892c54

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(router)

app.mount('#app')
