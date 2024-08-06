<template>
  <div class="mypage">
    <h3>마이페이지 입니다</h3>
    <button @click="logout">로그아웃</button>
    <br>
    <button @click="changepw">비밀번호변경</button>
    <br>
    <div class="form-group">
      <span>닉네임* </span>
      <input type="text" v-model="membernick" id="memnick">
      <button type="button" @click="checkNickDuplicate">중복확인</button>
    </div>
    <br>
    <div class="form-group">
      <span>이름 </span>
      <input type="text" v-model="membername" id="memname" placeholder="이름을 입력해주세요." />
    </div>
    <br>
    <div class="form-group">
      <span>생년월일 </span>
      <select class="dropdown-year" v-model="selectedYear">
        <option v-for="year in years" :key="year" value="year">{{year}}</option>
      </select>
      <select class="dropdown-month" v-model="selectedMonth">
        <option v-for="month in months" :key="month" value="month">{{month}}</option>
      </select>
      <select class="dropdown-Day" v-model="selectedDay">
        <option v-for="day in days" :key="day" value="day">{{day}}</option>
      </select>
    </div>
    <br>

  </div>
</template>
<script>
export default {
  data() {
    return {
      memberid: '현재 로그인된 id',
      selectedYear: null,
      selectedMonth: null,
      selectedDay: null,
      years: [],
      months: [],
      days: []


    }
  },
  created() {
    const currentYear = new Date().getFullYear();
    for (let year = currentYear; year >= 1910; year--){
      this.years.push(year);
    };
   for (let month = 1; month <=12; month++){
     this.months.push(month);
   }

  },
  methods: {
    checkNickDuplicate() {
      //닉네임 중복확인 메서드 맹글기
      console.log('닉넴중복확인', this.membernick)
    },

    updateDays() {
      if (this.selectedYear && this.selectedMonth) {
        const daysInMonth = new Date(this.selectedYear, this.selectedMonth, 0).getDate();
        this.days = [];
        for (let day = 1; day <= daysInMonth; day++) {
          this.days.push(day);
        }
        if (this.selectedDay > daysInMonth) {
          this.selectedDay = null; // 현재 선택된 일이 해당 월에 존재하지 않으면 초기화
        }
      }
    }

  }
}
</script>

<style scoped>
#memnick {
  border: 1px solid gray;
}

#memname {
  border: 1px solid gray;
}
</style>