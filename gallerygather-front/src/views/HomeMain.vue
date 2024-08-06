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
            <li class="button-text">
              <button>추천순</button>
            </li>
            <li class="button-text">
              <button>최신순</button>
            </li>
            <li class="button-text">
              <button>조회순</button>
            </li>
            <li class="button-text">
              <button>좋아요순</button>
            </li>
          </ul>
          <div class="rbox-block">
            <button class="filter-button">
              상세옵션
              <span class="st-text">열기</span>
            </button>
            <span class="search-box hide-mo">
              <input type="text" class="o-input" placeholder="전시명, 작가명, 전시 공간명 등을 입력해주세요" name="search_keyword"
                     maxlength="20" value="">
              <button type="button" class="o-btn o-btn-search" aria-label="검색"></button>
            </span>
          </div>
        </div>
      </div>

      <div>
        <b-card-group deck>
          <b-card title="Title" img-src="https://picsum.photos/300/300/?image=41" img-alt="Image" img-top>
            <b-card-text>
              This is a wider card with supporting text below as a natural lead-in to additional content.
              This content is a little bit longer.
            </b-card-text>
            <template #footer>
              <small class="text-muted">Last updated 3 mins ago</small>
            </template>
          </b-card>

          <b-card title="Title" img-src="https://picsum.photos/300/300/?image=41" img-alt="Image" img-top>
            <b-card-text>
              This card has supporting text below as a natural lead-in to additional content.
            </b-card-text>
            <template #footer>
              <small class="text-muted">Last updated 3 mins ago</small>
            </template>
          </b-card>

          <b-card title="Title" img-src="https://picsum.photos/300/300/?image=41" img-alt="Image" img-top>
            <b-card-text>
              This is a wider card with supporting text below as a natural lead-in to additional content.
              This card has even longer content than the first to show that equal height action.
            </b-card-text>
            <template #footer>
              <small class="text-muted">Last updated 3 mins ago</small>
            </template>
          </b-card>
        </b-card-group>
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
}


.sort-selection {
  display: flex;
  width: 100%;
  flex-wrap: wrap;
  align-items: center;
}

.sort-selection .rbox-block {
  display: flex;
  z-index: 10;
}

.sort-selection-side {
  display: flex;
  margin-left: auto;
  align-items: center;
}

.sort-selection-side .search-box {
  width: 100%;
  min-width: 360px;
  max-width: 360px;
}


.view-sort-list {
  display: flex;
  align-items: center;
  line-height: 1;
  color: #666;
}

.view-sort-list li {
  position: relative;
  padding-left: 4px;
  padding-right: 4px;
}

.filter-button {
  position: relative;
  display: inline-flex;
  border: 1px solid #dbd3c7;
  border-radius: 999px;
  padding: 11px 16px;
}

.view-sort li + li:before {
  position: absolute;
  top: 50%;
  left: 0;
  display: block;
  width: 1px;
  height: 10px;
  margin-top: -5px;
  background: #dbd3c7;
  background-color: rgb(219, 211, 199);
  content: "";
}

.search-box .o-btn-search {
  position: absolute;
  padding: 3px;
  border-radius: 0;
  vertical-align: middle;
  background: url("https://png.pngtree.com/png-clipart/20190705/original/pngtree-vector-search-icon-png-image_4271228.jpg");
  background-repeat: no-repeat;
  background-size: 18px auto;
  flex-shrink: 0;
}

.search-box {
  position: relative;
  display: inline-flex;
  flex-direction: row;
  align-items: center;
}

.search-box .o-input {
  width: 100%;
  flex: 1 1;
  height: 40px;
  font-size: 14px;
  color: #666;
  padding: 8px 45px 8px 16px;
  border: 1px solid #c8c6bd;
  border-radius: 8px;
  background: #eae7e2;
}

.filter-button .st-text {
  margin-left: 3px;
}
</style>