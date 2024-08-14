<script>
import { userStore } from '@/store/userStore.js'
import { apiRequest } from '@/util/RequestUtil.js'

export default {
  data() {
    return {
      store: userStore(),
      isHovered: false,
    }
  },

  computed: {
    isAuthenticated() {
      console.log(this.store.isAuthenticated)
      return this.store.isAuthenticated
    }
  },

  methods: {
    async logout() {
      try {
        await apiRequest('post', 'http://localhost:8080/api/members/auth/logout', {
          accessToken: localStorage.getItem('accessToken'),
          refreshToken: localStorage.getItem('refreshToken')
        })
      } finally {
        this.store.clearUserInfo()
        console.log('finally 호출')
        const currentPath = this.$route.fullPath
        if (currentPath.includes('mypage')) {
          this.$router.push('/login')
          return
        }
        this.$router.push(currentPath)
      }
    },

    showAfter() {
      this.isHovered = true;
    },

    hideAfter() {
      this.isHovered = false;
    }
  }
}
</script>

<template>
  <header :class="{ 'show-after': isHovered }">
    <div class="header">
      <div class="logo">
        <a href="/" class="logo-path">
          <img src="../../assets/img/logo.png" alt="">
        </a>
      </div>

      <div class="menu-container">

        <ul class="main-menu">
          <li class="item"  @mouseenter="showAfter" @mouseleave="hideAfter">
            <div class="item__name">Exhibition</div>
            <div class="item__contents">
              <div class="contents__menu">
                <ul class="inner">
                  <li><a href="/">Whats' New</a></li>
                  <li><a href="/">Best Exhibition</a></li>
                  <li><a href="/">Somthing New</a></li>
                  <li><a href="/">Whats' New</a></li>
                </ul>
              </div>
            </div>
          </li>
          <li class="item" @mouseenter="showAfter" @mouseleave="hideAfter">
            <div class="item__name">Review</div>
            <div class="item__contents">
              <div class="contents__menu">
                <ul class="inner">
                  <li><a href="/">Whats' New</a></li>
                  <li><a href="/">Best Review</a></li>
                  <li><a href="/">My Review</a></li>
                  <li><a href="/">Follow Best Reviewer</a></li>
                </ul>
              </div>
            </div>
          </li>
          <li class="item" @mouseenter="showAfter" @mouseleave="hideAfter">
            <div class="item__name">My Exhibition</div>
            <div class="item__contents">
              <div class="contents__menu">
                <ul class="inner">
                  <li><a href="/">Where</a></li>
                  <li><a href="/">Who</a></li>
                  <li><a href="/">Contact Us</a></li>
                  <li><a href="/">Ask Us</a></li>
                </ul>
              </div>
            </div>
          </li>
          <li class="item" @mouseenter="showAfter" @mouseleave="hideAfter">
            <div class="item__name">About Us</div>
            <div class="item__contents">
              <div class="contents__menu">
                <ul class="inner">
                  <li><a href="/">Where</a></li>
                  <li><a href="/">Who</a></li>
                  <li><a href="/">Contact Us</a></li>
                  <li><a href="/">Ask Us</a></li>
                </ul>
              </div>
            </div>
          </li>
        </ul>

      </div>

      <div class="login-bar">
        <div class="login-menu">
          <router-link to="/mypage" v-if="isAuthenticated">
            마이페이지
          </router-link>
          <router-link to="/join" v-else>
            회원가입
          </router-link>
        </div>
        <div class="login-menu">
          <a v-if="isAuthenticated" @click="logout">
            로그아웃
          </a>
          <router-link to="/login" v-else>
            로그인
          </router-link>
        </div>
      </div>
    </div>
  </header>

</template>

<style scoped>
ul {
  padding: 0;
  margin: 0;
}

.header {
  display: flex;
  position: relative;
  padding: 0 3%;
  width: 100%;
  justify-content: space-between;
  background-color: #f8f5eb;
}

a {
  color: #737373;
}

a:hover {
  color: #669900;
}

.logo {
  padding: 10px 0 5px;
  vertical-align: middle;
}

.logo-path img {
  height: 70px;
}

.login-bar > div:hover a {
  background-color: #2c2a29;
  color: #669900;
  border-radius: 6px;
}

.main-menu {
  display: flex;
  flex-direction: row;
  gap: 30px;
  padding: 0;
}

.item__name {
  padding: 20px 20px 30px;
  margin-top: 10px;
}

.item:hover .item__name {
  background-color: #2c2a29;
  color: #669900;
  border-radius: 6px 6px 0 0;
}

.login-bar {
  display: flex;
  vertical-align: center;
}

.login-bar > div {
  padding: 20px;
  margin-top: 10px;
}

.login-menu a {
  padding: 10px 20px;
}

.contents__menu {
  height: 400px;
}

.item__contents ul > li::before {
  content: '';
  width: 0px;
  height: 3px;
  display: inline-block;
  background: #669900;
  transition: width 1.2s;
}

.item__contents ul > li:hover::before {
  width: 50px;
}

.item__contents {
  position: absolute;
  z-index: 6;
  height: 0;
  font-size: 1.5rem;
  overflow: hidden;
  transition: height 1s;
}


header::after {
  content: '';
  width: 100%;
  height: 0px;
  left: 0;
  z-index: 5;
  position: absolute;
  background: white;
  display: block;
  transition: height 1s;
  background: #2c2a29;
}

.main-menu .item:hover .item__contents {
  display: block;
  height: 400px;
}


header.show-after::after {
  display: block; /* `isHovered`가 true일 때 보이도록 설정 */
  height: 400px;
}
.inner > li + li {
  margin-top: 20px;
}
</style>