import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {


    proxy: {
      '/uploads': {
        target: 'http://192.168.230.3:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/uploads/, '/uploads'),
      },
    },

    host: '0.0.0.0',
    port: 5173,
  }
})
