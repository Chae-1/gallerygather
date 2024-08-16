<!-- 작성자: 채형일-->

<script>
export default {
  data() {
    return {
      visible: false,
    }
  },

  methods: {
    descVisible() {
      this.visible = !this.visible;
    }
  }
}
</script>

<template>
  <footer>
    <div class="footer-title">

      <div class="footer-logo">
        <router-link to="/" class="logo-path">
          <img src="../../assets/img/logo.png" alt="">
        </router-link>
      </div>
      <div class="footer-menu">
        <a href="#">회사소개</a>
        <a href="#">고객센터</a>
        <a href="#">공지사항</a>
        <a href="#">개인정보 처리방침</a>
      </div>

      <div class="footer-social">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path
            d="M21 6.736a7.394 7.394 0 01-2.12.58 3.702 3.702 0 001.623-2.043 7.373 7.373 0 01-2.345.896 3.694 3.694 0 00-6.293 3.37 10.487 10.487 0 01-7.612-3.86 3.694 3.694 0 001.143 4.93 3.685 3.685 0 01-1.672-.461v.047a3.694 3.694 0 002.962 3.621 3.72 3.72 0 01-1.668.064 3.695 3.695 0 003.45 2.564A7.41 7.41 0 013 17.974a10.444 10.444 0 005.66 1.659c6.794 0 10.508-5.627 10.508-10.507 0-.159-.004-.319-.011-.477a7.507 7.507 0 001.841-1.91L21 6.735z"
            fill="currentColor">
          </path>
        </svg>
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"
             class="sc-c415ad88-6 cknyN">
          <path
            d="M12 5c-8.845 0-9 .787-9 6.93s.155 6.93 9 6.93 9-.787 9-6.93S20.845 5 12 5Zm2.884 7.23-4.04 1.887c-.354.164-.644-.02-.644-.41v-3.554c0-.39.29-.574.644-.41l4.04 1.886c.354.166.354.436 0 .602Z"
            fill="currentColor"></path>
        </svg>
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"
             class="sc-c415ad88-6 cknyN">
          <path
            d="M11.998 9.332A2.673 2.673 0 0 0 9.33 12a2.673 2.673 0 0 0 2.668 2.668A2.673 2.673 0 0 0 14.666 12a2.673 2.673 0 0 0-2.668-2.668ZM19.999 12c0-1.105.01-2.2-.052-3.302-.062-1.281-.354-2.418-1.29-3.355-.94-.938-2.074-1.228-3.355-1.29C14.197 3.99 13.102 4 12 4c-1.105 0-2.2-.01-3.302.052-1.281.062-2.418.354-3.355 1.29-.938.94-1.228 2.074-1.29 3.355C3.99 9.803 4 10.898 4 12c0 1.103-.01 2.2.052 3.302.062 1.281.354 2.418 1.29 3.355.94.938 2.074 1.228 3.355 1.29 1.104.062 2.2.052 3.302.052 1.105 0 2.2.01 3.302-.052 1.28-.062 2.418-.354 3.354-1.29.939-.94 1.229-2.074 1.291-3.355C20.011 14.2 20 13.105 20 12Zm-8.001 4.105A4.099 4.099 0 0 1 7.893 12a4.099 4.099 0 0 1 4.105-4.105A4.099 4.099 0 0 1 16.102 12a4.099 4.099 0 0 1-4.104 4.105Zm4.273-7.42a.958.958 0 1 1 0-1.917.957.957 0 1 1 0 1.918Z"
            fill="currentColor"></path>
        </svg>
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"
             class="sc-c415ad88-6 cknyN">
          <path
            d="M12.302 4.013c.872-.013 1.74-.005 2.606-.013.053 1.02.42 2.06 1.167 2.782.745.74 1.8 1.078 2.826 1.192v2.685c-.961-.032-1.927-.232-2.8-.646a8.107 8.107 0 0 1-1.08-.62c-.005 1.948.007 3.894-.013 5.834a5.075 5.075 0 0 1-.902 2.628c-.872 1.278-2.385 2.112-3.94 2.138-.953.054-1.906-.206-2.718-.685-1.347-.794-2.295-2.247-2.433-3.807-.017-.33-.02-.662-.008-.992A4.998 4.998 0 0 1 6.73 11.2c1.104-.962 2.65-1.42 4.099-1.149.013.988-.026 1.974-.026 2.962-.662-.214-1.435-.154-2.013.248a2.31 2.31 0 0 0-.91 1.166c-.137.338-.098.714-.09 1.073.159 1.094 1.21 2.013 2.334 1.914.744-.008 1.458-.44 1.846-1.073.125-.221.266-.448.273-.708.066-1.193.04-2.38.048-3.573.005-2.688-.008-5.368.012-8.047Z"
            fill="currentColor"></path>
        </svg>
      </div>


    </div>

    <div class="toggle" @click="descVisible">
      <span>갤러리개더 정보</span>
    </div>

    <div class="footer-description" v-show="!visible">
      <div>
        <p>상호: 주식회사 갤러리개더 / 대표: 없음 </p>
        <p>|</p>
        <p>주소: 매각 협의 중 </p>
        <p>|</p>
        <p>이메일 : god@normalize.net / 대표전화 : 1644-1052</p>
      </div>
    </div>
  </footer>
</template>

<style>



@media screen and (max-width: 770px) {
  .footer-title {
    flex-direction: column;
  }

  .footer-menu {
    flex-direction: column;
    text-align: center;
  }
}

footer {
  width: 100%;
  height: 100%;
  padding: 5px;
  background: #f8f7f3;
  font-size: 0.75rem;
}

.footer-menu {
  width: 100%;
  padding: 0 30px;
  display: flex;
}

.footer-logo img {
  width: 200px;
}

.footer-menu a {
  display: block;
  padding: 0 20px;
  color: #737373;
}

.footer-title {
  display: flex;
  align-items: center;
}

.footer-menu a {
  display: block;
  padding: 20px;
}

.footer-social {
  display: flex;
  flex: 1 1;
}

.toggle {
  cursor: pointer;
  margin-top: 1rem;
  font-weight: bold;
  text-align: center;
  margin-bottom: 10px;
}

.footer-description {
  display : flex;
  justify-content: center;
  text-align: center;
  margin-bottom: 10px;
}

.footer-description div{
  display : flex;
  flex-direction: row;
  gap: 20px;
}

.footer-description p {
  margin : 5px 0;
}

</style>