function extractUserInfoFrom(token) {
    // JWT는 Header, Payload, Signature의 3부분으로 나누어져 있다.
    // JWT의 Payload 부분을 추출한다.
    const parts = token.split('.');
    if (parts.length !== 3) {
        throw new Error('Invalid JWT token');
    }

    // Payload 부분을 Base64Url 디코딩한다.
    const payload = parts[1];
    const decodedPayload = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));

    // JSON으로 파싱한다.
    const payloadObj = JSON.parse(decodedPayload);

    // 클레임에서 이메일을 추출한다.
    return payloadObj.email || null;
}

export default extractUserInfoFrom;