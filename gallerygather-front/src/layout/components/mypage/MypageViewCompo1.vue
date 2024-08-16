<!--유은-->
<template>
  <div class="mypage">
    <br />
    <br />
    <br />
    <br />
    <div class="summary-container">
      <router-link to="/mypage/mylikecompo" class="my-summary__inbox">
        <span class="my-summary__tit">좋아요</span><span class="my-summary__num">{{ likeCount }}</span>
      </router-link>
      <router-link to="/mypage/myreviewcompo" class="my-summary__inbox">
        <span class="my-summary__tit">작성리뷰</span><span class="my-summary__num">{{ reviewCount }}</span>
      </router-link>
      <router-link to="/mypage/myreplycompo" class="my-summary__inbox">
        <span class="my-summary__tit">작성댓글</span><span class="my-summary__num">{{ replyCount }}</span>
      </router-link>
    </div>
    <br />
    <br />
    <div class="mypage-title-pack">
      <div class="inform__block">
        <h4 class="mypage-title">내 정보</h4>
        <button @click="togglePasswordFields" class="action-button">비밀번호변경</button>&nbsp;&nbsp;
<!--        <button @click="deleteMember" class="action-button">회원탈퇴</button>-->
      </div>
    </div>
    <!--비번 변경 필드 숨김-->
    <!-- 비밀번호 변경 입력 필드 숨김상태-->
    <div v-if="showPasswordFields">
      <div class="inform__block">
        <label class="form-label">현재 비밀번호* </label>
        <input type="password" v-model="currentPassword" placeholder="현재 비밀번호를 입력하세요" class="form-input" />
      </div>

      <div class="inform__block">
        <label class="form-label">새 비밀번호* </label>
        <input type="password" v-model="newPassword" placeholder="새 비밀번호를 입력하세요" class="form-input" />
      </div>

      <div class="inform__block">
        <label class="form-label">새 비밀번호 확인* </label>
        <input type="password" v-model="confirmPassword" placeholder="새 비밀번호를 다시 입력하세요" class="form-input" />
      </div>

      <div class="btn-save">
        <button type="button" class="btn" @click="changepw">비밀번호 저장</button>
      </div>
    </div>

    <!--내 정보 필드  -->
    <div class="inform__block">
      <label class="form-label">로그인 이메일 : </label>
      <input type="text" v-model="email" readonly />
    </div>

    <div class="inform__block" style="display: flex; flex-direction: column;">
      <div style="display: flex; align-items: center;">
        <label class="form-label">닉네임* </label>
        <input type="text" v-model="nickName" maxlength="10" class="form-input" />
        <button type="button" class="nick-btn" @click="checkNickDuplicate" style="margin-left: 10px;">중복확인</button>
      </div>
      <span class="form-note" style="margin-top: 10px; display: block;">※ 2자~10자, 영문, 한글, 숫자만 가능.</span>
    </div>


    <div class="inform__block">
      <label class="form-label">이름 </label>
      <input
        type="text"
        v-model="name"
        placeholder=" 이름을 입력해주세요."
        maxlength="10"
        class="form-input"
      />
    </div>

    <div class="inform__block">
      <label class="form-label">생년월일 </label>
      <div class="dropdown-container">
        <select class="dropdown" v-model="selectedYear" @change="updateDays">
          <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
        </select>
        <select class="dropdown" v-model="selectedMonth" @change="updateDays">
          <option v-for="month in months" :key="month" :value="month">{{ month }}</option>
        </select>
        <select class="dropdown" v-model="selectedDay">
          <option v-for="day in days" :key="day" :value="day">{{ day }}</option>
        </select>
      </div>
    </div>

    <div class="inform__block">
      <label class="form-label">주소 </label><br />
      <div class="address-container">
        <input
          type="text"
          class="form-input"
          v-model="zipcode"
          placeholder="우편번호 찾기"
          readonly
          @click="findPostcode"
        />
        <input type="text" class="form-input" v-model="address" placeholder="주소" readonly />
        <input type="text" class="form-input" v-model="detailAddress" placeholder="상세주소" />
      </div>
    </div>

    <div class="btn-save">
      <button type="button" class="btn" @click="saveUserInfo">저장</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { apiRequest } from '@/util/RequestUtil.js'

export default {
  data() {
    return {
      likeCount: 0, //라이크
      reviewCount: 0, //리뷰
      replyCount: 0, // 댓글
      email: '',
      nickName: '', // 닉네임을 저장하는 데이터
      name: '', // 이름을 저장하는 데이터
      selectedYear: null, // 선택된 연도
      selectedMonth: null, // 선택된 월
      selectedDay: null, // 선택된 일
      years: [], // 연도 목록
      months: [], // 월 목록
      days: [], // 일 목록
      zipcode: '', // 우편번호
      address: '', // 주소
      detailAddress: '', // 상세주소
      currentPassword: '',//현재비번
      newPassword: '',//새비번
      confirmPassword: '',//새비번 확인
      showPasswordFields: false // 비밀번호 입력 필드 보이기
    }
  },
  created() {
    this.fetchUserSummary()// 좋아요,댓글,리뷰 카운트
    this.originalUserInfo() //로그인시 기존정보 보여줌

    const currentYear = new Date().getFullYear()
    // 현재 연도부터 1910년까지의 연도를 years 배열에 추가
    for (let year = currentYear; year >= 1910; year--) {
      this.years.push(year)
    }
    // 1월부터 12월까지의 월을 months 배열에 추가
    for (let month = 1; month <= 12; month++) {
      this.months.push(month)
    }
    this.updateDays()
  },

  methods: {
    async fetchUserSummary() {
      try {
        const token = localStorage.getItem('accessToken') // JWT 토큰 가져오기

        // 좋아요 개수 가져오기
        const likeCountResponse = await apiRequest('get', 'http://192.168.230.3:8080/api/exhibitions/likes/member/like-count', null, {
          headers: {
            Authorization: token
          }
        })
        this.likeCount = likeCountResponse.data


        const reviewCountResponse = await apiRequest('get', 'http://192.168.230.3:8080/api/reviews/member/review-count', null, {
          headers: {
            Authorization: token
          }
        })
        this.reviewCount = reviewCountResponse.data

        const replyCountResponse = await apiRequest('get', 'http://192.168.230.3:8080/api/replys/member/reply-count', null, {
          headers: {
            Authorization: token
          }
        })
        this.replyCount = replyCountResponse.data

        // 여기에 likeCount API 호출 및 데이터 설정을 추가할 수 있습니다.

      } catch (error) {
        console.error('사용자 요약 정보를 가져오는 중 오류 발생:', error)
      }
    },
    async checkNickDuplicate(nickName) {
      console.log('닉네임 중복 확인 요청', nickName)

      try {
        const token = localStorage.getItem('accessToken') // JWT 토큰 가져오기
        console.log('닉네임 중복 확인 토큰:', token)

        const response = await apiRequest(
          'post',
          'http://192.168.230.3:8080/api/members/check-nickname',
          { nickname: this.nickName }, // POST 요청 데이터
          {
            headers: {
              Authorization: token,
              'Content-Type': 'application/json'
            }
            // POST 요청으로 닉네임 전달
          }
        )

        if (response.data) {
          alert('이미 사용 중인 닉네임입니다.')
          console.log('응답:', response.data)
        } else {
          alert('사용 가능한 닉네임입니다.')
          console.log('응답2:', response.data)
        }
      } catch (error) {
        console.error('닉네임 중복 확인 중 오류 발생:', error)
        alert('닉네임 중복 확인에 실패했습니다.')
      }
    },    // 닉네임 중복확인 메서드
    updateDays() {
      if (this.selectedYear && this.selectedMonth) {
        const daysInMonth = new Date(this.selectedYear, this.selectedMonth, 0).getDate()
        this.days = []

        for (let day = 1; day <= daysInMonth; day++) {
          this.days.push(day)
        }

        if (this.selectedDay > daysInMonth) {
          this.selectedDay = null
        }
      } else {
        // 연도나 월이 선택되지 않은 경우, selectedDay를 변경하지 않음
      }
    },
    findPostcode() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          console.log('받은 데이터 : ', data)
          this.zipcode = data.zonecode
          this.address = data.address
          this.detailAddress = '' // 상세주소 초기화
        }
      }).open()
    },// 우편번호 찾기 메서드
    async originalUserInfo() {
      try {
        const token = localStorage.getItem('accessToken') // JWT 토큰 가져오기
        const response = await apiRequest('get',
          'http://192.168.230.3:8080/api/members/original', null, {
            headers: {
              Authorization: token
            }
          })

        const userInfo = response.data
        this.email = userInfo.email
        this.nickName = userInfo.nickName
        this.name = userInfo.name

        // 생년월일 처리
        if (userInfo.dateOfBirth) {
          const birthDate = new Date(userInfo.dateOfBirth)
          this.selectedYear = birthDate.getFullYear()
          this.selectedMonth = birthDate.getMonth() + 1
          this.selectedDay = birthDate.getDate()

          // 날짜를 설정한 후에만 updateDays를 호출
          this.updateDays()
        }


        // 주소 처리
        if (userInfo.address) {
          const addressParts = userInfo.address.split(' ')
          this.zipcode = addressParts.pop() // 마지막 부분이 우편번호라고 가정
          this.detailAddress = addressParts.pop() // 마지막에서 두 번째가 상세주소
          this.address = addressParts.join(' ') // 나머지가 주소
        }
      } catch (error) {
        console.error('사용자 정보를 가져오는 중 오류 발생:', error)
      }
    },// 기존 사용자 정보 불러오기
    async saveUserInfo() {
      const dateOfBirth = `${this.selectedYear}-${String(this.selectedMonth).padStart(2, '0')}-${String(this.selectedDay).padStart(2, '0')}`
      console.log('수집된 사용자 정보:', dateOfBirth)

      // 수집된 데이터
      const userInfo = {
        nickName: this.nickName,
        name: this.name,
        dateOfBirth: dateOfBirth, // 올바른 날짜 형식 전달
        address: `${this.address} ${this.detailAddress},${this.zipcode}`
      }

      console.log('수집된 email 정보:', userInfo.email)
      console.log('수집된 nickName 정보:', userInfo.nickName)
      console.log('수집된 name 정보:', userInfo.name)
      console.log('수집된 dateOfBirth 정보:', userInfo.dateOfBirth)
      console.log('수집된 address 정보:', userInfo.address)

      try {
        const token = localStorage.getItem('accessToken') // JWT 토큰 가져오기
        console.log('내정보 변경 토큰 :', token)

        const response = await apiRequest('post',
          'http://192.168.230.3:8080/api/members/update',
          userInfo,
          {
            headers: {
              Authorization: token
            }
          }
        )
        if (response.status === 200) {
          alert('정보가 성공적으로 저장되었습니다.')
        }
      } catch (error) {
        console.error('저장 중 오류 발생:', error)
        alert('정보 저장에 실패했습니다.')
      }
    },//정보업데이트
    async changepw() {
      if (this.newPassword !== this.confirmPassword) {
        alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다.')
        return
      }
      if (!this.currentPassword || !this.newPassword) {
        alert('모든 비밀번호 필드를 입력하세요.')
        return
      }

      // 서버로 요청 보내기
      try {
        const token = localStorage.getItem('accessToken') // JWT 토큰 가져오기
        console.log('비밀번호 변경 토큰 :', token)
        const response = await apiRequest('post',
          'http://192.168.230.3:8080/api/members/change-password',
          {
            currentPassword: this.currentPassword,
            newPassword: this.newPassword

          }, {
            headers: {
              Authorization: token
            }
          }
        )
        if (response.status === 200) {
          alert('비밀번호가 성공적으로 변경되었습니다.')
          console.log('변경 후 현재 비밀번호  :', this.newPassword)
          this.showPasswordFields = false // 비밀번호 변경 후 입력 필드 숨기기
          this.clearPasswordFields()//비번 변경후 입력 칸 초기화
          console.log(this)
        }
      } catch (error) {
        console.error('비밀번호 변경 중 오류 발생:', error)
        alert('비밀번호 변경에 실패했습니다.')
      }
    },//비번변경 코드
    togglePasswordFields() {
      this.showPasswordFields = !this.showPasswordFields
      console.log('비밀번호 변경 화면 보이기')
    },// 비번변경 보이기
    clearPasswordFields() {
      this.currentPassword = ''
      this.newPassword = ''
      this.confirmPassword = ''
    }, // 비밀번호 필드를 초기화
    async deleteMember() {
      if (confirm('정말로 회원 탈퇴하시겠습니까?')) {
        const token = localStorage.getItem('accessToken');
        apiRequest('DELETE', 'http://192.168.230.3:8080/api/members/delete', {
          headers: {
            Authorization: token
          }
        })
          .then(response => {
            if (response.status === 200) {
              alert('회원 탈퇴가 성공적으로 처리되었습니다.');
              localStorage.removeItem('accessToken'); // JWT 토큰 삭제
              this.$router.push('/'); // 홈 화면으로 리다이렉트
            }
          })
          .catch(error => {
            console.error('회원 탈퇴 중 오류 발생:', error);
            alert('회원 탈퇴 중 오류가 발생했습니다.');
          });
      }
    }//delete
  }
}

</script>

<style scoped>
/* 마이페이지 스타일 정의 */
/* 마이페이지 컨테이너 스타일: 고정된 너비를 설정하고 가운데 정렬, Arial 폰트 적용 */
.mypage {
  width: 70%;
  margin: 0 auto;
  font-family: 'Arial', sans-serif;
  padding-bottom: 100px; /* Footer와 겹치지 않도록 하단에 여백 추가 */
}

/* 요약 정보 컨테이너 스타일: flex 컨테이너를 사용하여 자식 요소들을 수평으로 정렬 */
.summary-container {
  display: flex;
  justify-content: space-between;
  margin-bottom: 80px;
  padding: 24px 0;
  background: darkslategrey;
  border-radius: 8px;
  color: darkslategrey;
  min-height: 149px;
}

/* 요약 정보 박스 스타일: 배경색, 테두리, 패딩 및 텍스트 정렬 설정 */
.my-summary__inbox {
  background: darkslategrey;
  border-radius: 0px;
  padding: 20px;
  text-align: center;
  flex: 1;
  margin: 0 10px;
}


.my-summary__inbox:not(:last-child) {
  border-right: 1px solid #dbd3c7; /* 중간에 선 추가 */
}

/* 요약 정보 타이틀 스타일: 글씨를 굵게 하고, 아래쪽 마진을 추가 */
.my-summary__tit {
  color: white;
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}

/* 요약 정보 숫자 스타일: 글씨 크기와 색상을 설정 */
.my-summary__num {
  font-size: 40px;
  color: white;
}

/* 공통 스타일 정의 */
/* 글자 라벨 스타일: inline-block으로 설정하여 크기를 조정하고, 고정 너비와 패딩을 추가 */
.form-label {
  display: inline-block;
  width: 118px;
  padding-top: 16px;
  flex-shrink: 0;
  font-weight: 700;
  line-height: 1.2;
}

/* 각 입력 블록 스타일: 블록의 전체 너비를 사용하고, 패딩과 아래쪽 테두리를 추가하여 구분 */
.inform__block {
  width: 100%;
  display: flex;
  padding: 24px 0;
  border-bottom: 1px solid #dfdbd3;
}

/* 입력 필드 스타일: 입력 필드의 너비를 100%로 설정하여 부모 컨테이너에 맞추고, 패딩과 테두리를 추가 */
.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #dbd3c7;
  border-radius: 6px;
  line-height: 1.5;
  background: transparent none repeat 0 0 / auto auto padding-box border-box scroll;
  margin-bottom: 10px; /* 필드 간 간격 추가 */
}

/* 버튼 스타일 정의 */
/* 액션 버튼 스타일: 테두리 반경, 폰트 크기, 패딩 및 정렬 설정 */
.action-button {
  border-radius: 42px;
  font-size: 14px;
  display: inline-flex;
  padding: 14px 16px;
  border: 1px solid;
  letter-spacing: -0.02em;
  align-items: center;
}

/* 저장 버튼 스타일: 테두리 반경, 폰트 크기, 패딩 및 정렬 설정 */
.btn {
  border-radius: 12px;
  font-size: 14px;
  display: inline-flex;
  padding: 12px 20px;
  border: 1px solid;
  letter-spacing: -0.02em;
  align-items: center;
}

/* 중복확인 버튼 스타일: 높이, 마진, 테두리 반경, 폰트 크기 및 정렬 설정 */
.nick-btn {
  height: 48px;
  margin: 0 5px 0 8px;
  border-radius: 12px;
  font-size: 14px;
  display: flex;
  padding: 4px 24px;
  border: 1px solid;
  letter-spacing: -0.02em;
  align-items: center;
  font-weight: 500;
  white-space: nowrap;
}

/* 마지막 닉네임 버튼 스타일: 오른쪽 마진 추가 */
.nick-btn:last-child {
  margin-right: 10px;
}

/* 폼 주석 스타일: 폰트 크기와 색상 설정 */
.form-note {
  font-size: 12px;
  color: #666;
}

/* 드롭다운 컨테이너 스타일: flex 컨테이너를 사용하여 자식 요소들을 수평으로 정렬 */
.dropdown-container {
  display: flex;
  justify-content: space-between;
}

/* 드롭다운 스타일: 너비, 패딩, 테두리 및 테두리 반경 설정 */
.dropdown {
  flex: 1;
  padding: 10px;
  border: 1px solid #dbd3c7;
  border-radius: 5px;
  margin-right: 10px;
}

/* 마지막 드롭다운 스타일: 오른쪽 마진 제거 */
.dropdown:last-child {
  margin-right: 0;
}

/* 주소 컨테이너 스타일: flex 컨테이너를 사용하여 자식 요소들을 수직으로 정렬 */
.address-container {
  display: flex;
  flex-direction: column;
}

/* 주소 입력 필드 스타일: 필드 간 마진 추가 */
.address-container .form-input {
  margin-bottom: 10px;
}

.mypage-title {
  margin: 10px 30px 10px 10px;
}

/* 저장 버튼 컨테이너 스타일: 컨테이너를 flex로 설정하고 중앙에 배치 */
.btn-save {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  padding-bottom: 20px;
  position: relative;
  z-index: 2; /* Footer보다 위에 위치하도록 설정 */
}

footer {
  position: static;
  z-index: 1;
}

</style>
