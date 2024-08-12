/**
 * 작성자: 채형일
 */
import axios from 'axios'
import { createPinia, defineStore } from 'pinia'
import { userStore } from '@/store/userStore.js'

const pinia = createPinia();
const store = userStore(pinia);


export async function apiRequest(method, url, data = null, options = {}) {
  let isRefreshed = false;
  try {
    const response = await axios({
      method,
      url,
      data,
      headers: {
        Authorization: localStorage.getItem('accessToken'),
        ...options.headers,
      },
      ...options,
    });
    return response;
  } catch (error) {
    if (error.response && error.response.status === 401) {
      console.log('토큰 재발급 중..')
      await handleTokenRefresh();
      return apiRequest(method, url, data, options);
    }
    console.error('Request error:', error);
    throw error;
  }
}

// 토큰 재발급 유틸리티 함수
async function handleTokenRefresh() {
  const store = userStore();
  await store.reLogin(); // 토큰 재발급 처리
}