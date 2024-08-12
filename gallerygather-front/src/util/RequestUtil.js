/**
 * 작성자: 채형일
 */
import axios from 'axios'
import { createPinia, defineStore } from 'pinia'
import { userStore } from '@/store/userStore.js'

const pinia = createPinia();
const store = userStore(pinia);

let isRefreshed = false;

export async function apiRequest(method, url, data = null, options = {}) {
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
    if (error.response && error.response.status === 401 && !isRefreshed) {
      console.log('토큰 재발급 중..')
      await handleTokenRefresh();
      isRefreshed = true;
      return apiRequest(method, url, data, options); // 재요청
    }
    console.error('Request error:', error);
    throw error;
  } finally {
    isRefreshed = false;
  }
}

// 토큰 재발급 유틸리티 함수
async function handleTokenRefresh() {
  const store = userStore();
  await store.reLogin(); // 토큰 재발급 처리
}