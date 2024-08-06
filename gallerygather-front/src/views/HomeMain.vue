<template>
  <div class="home-main">
    <div class="main-carousel">
      <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
          <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active"
                  aria-current="true" aria-label="Slide 1"></button>
          <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1"
                  aria-label="Slide 2"></button>
          <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2"
                  aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="https://picsum.photos/1024/480/?image=12" class="d-block w-100" alt="...">
            <div class="carousel-caption d-none d-md-block">
              <h5>First slide label</h5>
              <p>Some representative placeholder content for the first slide.</p>
            </div>
          </div>
          <div class="carousel-item">
            <img src="https://picsum.photos/1024/480/?image=12" class="d-block w-100" alt="...">
            <div class="carousel-caption d-none d-md-block">
              <h5>Second slide label</h5>
              <p>Some representative placeholder content for the second slide.</p>
            </div>
          </div>
          <div class="carousel-item">
            <img src="https://picsum.photos/1024/480/?image=12" class="d-block w-100" alt="...">
            <div class="carousel-caption d-none d-md-block">
              <h5>Third slide label</h5>
              <p>Some representative placeholder content for the third slide.</p>
            </div>
          </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
    </div>


    <article class="main-content">
      <div class="sort-selection">
        <p class="result-text">
          결과 총 1000개
        </p>
        <div class="sort-selection-side">
          <ul class="view-sort-list">
            <li class="button-text">추천순</li>
            <li class="button-text">최신순</li>
            <li class="button-text">조회순</li>
            <li class="button-text">좋아요순</li>
            <li class="button-round">상세옵션 열기</li>
            <input type="text" class="search-input"/>
          </ul>
        </div>
      </div>

      <div class="main-content-list">

      </div>

    </article>
  </div>
</template>

<script>

</script>

<style>

.carousel-indicators > button {
  border-radius: 50% !important;
  width: 10px !important;
  height: 10px !important;
}

.home-main {
  max-width: 1920px;
  width: 100%;
  margin: 0 auto;

}

.result-text {
  font-size: 18px;
  color: black;
}


.main-content {
  padding: 50px;
  display: flex;
  flex-direction: column;
  background-color: beige;
}

.main-content .button-text {
  color: darkgray;
  padding-right: 20px;
  text-align: center;
  margin-right: 7px;
  position: relative;
}

.sort-selection {
  display: flex;
  width: 100%;
  flex-wrap: wrap;
  align-items: center;
}

.sort-selection-side {
  display: flex;
  margin-left: auto;
  align-items: center;
}

.view-sort-list {
  display:flex;
}

</style>