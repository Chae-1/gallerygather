<script>
export default {

};
</script>

<template>
  <header>
    <div class="logo">로고나 이름</div>
        <nav>
            <ul>
                <li><a href="#">장르별</a></li>
                <li><a href="#">지역별</a></li>
                <li><a href="#">리뷰</a></li>
                <li><a href="#">@@@ 님</a></li>
            </ul>
        </nav>
  </header>
</template>

<style>

header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #fff;
    padding: 10px 20px;
    border-bottom: 1px solid #ddd;
    width: 100%;
    height: 100px;
}

header .logo {
    font-size: 18px;
    font-weight: bold;
}

header nav ul {
    list-style: none;
    display: flex;
    margin: 0;
    padding: 0;
}

header nav ul li {
    margin-left: 20px;
}

header nav ul li a {
    text-decoration: none;
    color: #333;
}
</style>