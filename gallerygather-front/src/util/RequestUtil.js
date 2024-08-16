/**
 * 작성자: 채형일
 */
import axios from 'axios'


export async function apiRequest(method, url, data = null, options = {}, isAuthenticatedRequest) {
  try {
    const response = await axios({
      method,
      url,
      data,
      headers: {
        Authorization: localStorage.getItem('accessToken'),
        ...options.headers
      },
      ...options
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
  return await axios.post('http://192.168.230.3:8080/api/members/auth/refresh', {
    refreshToken: localStorage.getItem('refreshToken'),
  }).then(response => {
    console.log('재 로그인 요청');
    return response;
  }).catch(error => {
    // 에러가 발생한다면 유효하지 않은 refreshToken, 해당 요청이 결국 실패한다.
    console.log(error.data);
    throw error;
  }) // 토큰 재발급 처리
}