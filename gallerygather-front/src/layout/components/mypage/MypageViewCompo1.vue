<template>
  <div class="mypage">
    <div class="summary-container">
      <router-link to="/mylikecompo" class="my-summary__inbox">
        <span class="my-summary__tit">좋아요</span><span class="my-summary__num">0</span>
      </router-link>
      <router-link to="/myreviewcompo" class="my-summary__inbox">
        <span class="my-summary__tit">작성글</span><span class="my-summary__num">0</span>
      </router-link>
      <router-link to="/myreplycompo" class="my-summary__inbox">
        <span class="my-summary__tit">작성댓글</span><span class="my-summary__num">0</span>
      </router-link>
    </div>

    <div class="mypage-title-pack">
      <div class="inform__block">
        <h4 class="mypage-title">내 정보</h4>
        <button @click="logout" class="action-button">로그아웃</button>
        <button @click="changepw" class="action-button">비밀번호변경</button>
      </div>
    </div>

    <div class="inform__block">
      <label class="form-label">아이디 </label>
      <input type="text" v-model="membername" value="{{memberid}}" readonly />
    </div>

    <div class="inform__block">
      <label class="form-label">닉네임* </label>
      <input type="text" v-model="membernick" maxlength="10" class="form-input" />
      <button type="button" class="nick-btn" @click="checkNickDuplicate">중복확인</button>
      <br />
      <span class="form-note"
        >※ 2자~15자, 영문, 한글, 숫자만 가능. (특수문자, 문장기호, 이모티콘 등 사용불가)</span
      >
    </div>

    <div class="inform__block">
      <label class="form-label">이름 </label>
      <input
        type="text"
        v-model="membername"
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
      <button type="button" class="btn" @click="printAllValues">저장</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      memberid: '현재 로그인된 id',
      membernick: '', // 닉네임을 저장하는 데이터
      membername: '', // 이름을 저장하는 데이터
      selectedYear: null, // 선택된 연도
      selectedMonth: null, // 선택된 월
      selectedDay: null, // 선택된 일
      years: [], // 연도 목록
      months: [], // 월 목록
      days: [], // 일 목록
      zipcode: '', // 우편번호
      address: '', // 주소
      detailAddress: '' // 상세주소
    }
  },
  created() {
    const currentYear = new Date().getFullYear()
    // 현재 연도부터 1910년까지의 연도를 years 배열에 추가
    for (let year = currentYear; year >= 1910; year--) {
      this.years.push(year)
    }
    // 1월부터 12월까지의 월을 months 배열에 추가
    for (let month = 1; month <= 12; month++) {
      this.months.push(month)
    }
  },
  methods: {
    // 닉네임 중복확인 메서드
    checkNickDuplicate() {
      console.log('닉넴중복확인', this.membernick)
    },
    // 선택된 연도와 월에 따라 일 목록을 업데이트하는 메서드
    updateDays() {
      if (this.selectedYear && this.selectedMonth) {
        const daysInMonth = new Date(this.selectedYear, this.selectedMonth, 0).getDate()
        this.days = []
        // 해당 월의 일 수만큼 days 배열에 추가
        for (let day = 1; day <= daysInMonth; day++) {
          this.days.push(day)
        }
        // 현재 선택된 일이 해당 월에 존재하지 않으면 초기화
        if (this.selectedDay > daysInMonth) {
          this.selectedDay = null
        }
      }
    },
    // 우편번호 찾기 메서드
    findPostcode() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          console.log('받은 데이터 : ', data)
          this.zipcode = data.zonecode
          this.address = data.address
          this.detailAddress = '' // 상세주소 초기화
        }
      }).open()
    },
    // 모든 값을 콘솔에 출력하는 메서드
    printAllValues() {
      console.log('아이디:', this.memberid)
      console.log('닉네임:', this.membernick)
      console.log('이름:', this.membername)
      console.log('생년월일:', `${this.selectedYear}-${this.selectedMonth}-${this.selectedDay}`)
      console.log('우편번호:', this.zipcode)
      console.log('주소:', this.address)
      console.log('상세주소:', this.detailAddress)
    }
  }
}
</script>

<style scoped>
/* 마이페이지 스타일 정의 */
/* 마이페이지 컨테이너 스타일: 고정된 너비를 설정하고 가운데 정렬, Arial 폰트 적용 */
.mypage {
  width: 700px;
  margin: 0 auto;
  font-family: 'Arial', sans-serif;
}

/* 요약 정보 컨테이너 스타일: flex 컨테이너를 사용하여 자식 요소들을 수평으로 정렬 */
.summary-container {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

/* 요약 정보 박스 스타일: 배경색, 테두리, 패딩 및 텍스트 정렬 설정 */
.my-summary__inbox {
  background: white;
  border: 1px solid #dbd3c7;
  border-radius: 30px;
  padding: 20px;
  text-align: center;
  flex: 1;
  margin: 0 10px;
}

/* 요약 정보 타이틀 스타일: 글씨를 굵게 하고, 아래쪽 마진을 추가 */
.my-summary__tit {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}

/* 요약 정보 숫자 스타일: 글씨 크기와 색상을 설정 */
.my-summary__num {
  font-size: 24px;
  color: #333;
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
}
</style>
