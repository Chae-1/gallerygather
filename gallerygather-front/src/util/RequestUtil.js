/**
 * 작성자: 채형일
 *
 * 요청 도중, accessToken 만료로 인한 토큰 재발급 요청과, 재요청이 포함된
 *
 * 유틸리티 함수
 *
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
    // 401 UnAuthorized 로 인해 요청이 실패 했다면, accessToken이 만료되었거나 인증 실패 오류이다.
    // 토큰을 재발급 하고, 재요청을 시도한다.
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