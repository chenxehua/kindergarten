import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  server: {
    port: 3001,
    proxy: {
      '/api/user': {
        target: 'http://localhost:8081',
        changeOrigin: true
      },
      '/api/student': {
        target: 'http://localhost:8082',
        changeOrigin: true
      },
      '/api/class': {
        target: 'http://localhost:8083',
        changeOrigin: true
      },
      '/api/record': {
        target: 'http://localhost:8084',
        changeOrigin: true
      },
      '/api/food': {
        target: 'http://localhost:8085',
        changeOrigin: true
      },
      '/api/course': {
        target: 'http://localhost:8086',
        changeOrigin: true
      },
      '/api/growth': {
        target: 'http://localhost:8087',
        changeOrigin: true
      },
      '/api/video': {
        target: 'http://localhost:8088',
        changeOrigin: true
      },
      '/api/notice': {
        target: 'http://localhost:8089',
        changeOrigin: true
      }
    }
  }
})
