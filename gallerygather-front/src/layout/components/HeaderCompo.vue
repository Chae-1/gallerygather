<script>
export default {

};
</script>

<template>
    <header>
        <div class="logo">
            <a href="/" class="logo">
                <span>로고나 이름</span>
            </a>
        </div>
        <div class="menu-container">
            <ul class="main-menu">
                <li class="item">
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
                <li class="item">
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
                <li class="item">
                    <div class="item__name">My Exhibition</div>
                    <div class="item__contents">
                        <div class="contents__menu">
                            <ul class="inner">
                                <li><a href="/">My Likes</a></li>
                                <li><a href="/">My Planning</a></li>
                                <li><a href="/">Visited Exhibition</a></li>
                                <li><a href="/">Whats' New</a></li>
                            </ul>
                        </div>
                    </div>
                </li>
                <li class="item">
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
            <div>
                회원가입
            </div>
            <div>
                로그인
            </div>

        </div>
    </header>
</template>

<style scoped>
ul {
    padding: 0;
    margin: 0;
}

header {
    display: flex;
    flex-direction: row;
    padding: 0 5%;
    /* position: relative; */
    width: 100%;
    justify-content: space-between;
    border-bottom: 1px solid #ddd;
}

.logo {
    padding: 20px 10px;
}

.main-menu {
    display: flex;
    flex-direction: row;
    gap: 50px;
    z-index: 1;
    padding: 0;
}

.main-menu .item .item__name{
    padding: 20px 20px 25px;
    margin-top : 5px;
}
.main-menu .item:hover .item__name{
  background-color: #2c2a29;
  color: #669900;
  border-radius: 6px 6px 0 0;
}

.main-menu .item .item__contents{
    width: 100%;
    position: absolute;
    z-index: 2;
    left: 0;
    display: none;
    transition-delay: 2s;
    transition-property: height;
}

.main-menu .item:hover .item__contents {
    display: block;
}

.main-menu .item .item__contents .contents__menu{
  background-color: #2c2a29;
  height: 400px; 
}
/* .main-menu .item .item__contents{
  width: 100%;
  position: absolute;
  left: 0;
  display: none;
  z-index: 2;
  transition: height 2s ease-in-out;
}

.main-menu .item:hover .item__contents{
    display: block;
}

.main-menu .item .item__contents .contents__menu{
  background-color: #2c2a29;
  height: 400px; 
} */
/* 
.inner {
    font-size: 40px;
    padding-top: 50px;
}

.inner > li {
    padding: 20px 10px;
    align-content: center;
}

.inner > li > a::before {
    content:"";
    width: 0;
    height: 100%;
}

.inner > li > a:hover::before {
    content:"ㅡ";
    width: auto;
} */

.login-bar {
    display: flex;
    flex-direction: row;
    padding: 20px 10px;
}
</style>