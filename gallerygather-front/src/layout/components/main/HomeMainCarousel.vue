<!-- 작성자: 채형일-->

<script>
export default {
  name: "HomeMainCarousel"
}
</script>

<template>
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
          <img src="../../../assets/img/pic.jpg" class="d-block w-100 img"  alt="...">
          <div class="carousel-caption d-none d-md-block">
            <h3>베스티지-존재의 리좀</h3>
          </div>
        </div>
        <div class="carousel-item">
          <img src="../../../assets/img/sky2.jpg" class="d-block w-100 img" alt="...">
          <div class="carousel-caption d-none d-md-block">
            <h3>빛의 벙커 : 반 고흐</h3>
          </div>
        </div>
        <div class="carousel-item">
          <img src="../../../assets/img/statu.jpg" class="d-block w-100 img" alt="..." >
          <div class="carousel-caption d-none d-md-block">
            <h3>전시의 전시</h3>
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
</template>

<style>

.carousel-item {
  font-family: "Nanum Brush Script", cursive;
  font-weight: 400;
  font-style: normal;

  scroll-snap-align: start;
  width: 100%;
  height: 80vh;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
  opacity: 1;
  transition: opacity 1s ease-in-out;
}
.carousel-indicators > button {
  border-radius: 50% !important;
  width: 10px !important;
  height: 10px !important;
}
.img {
  width: 100%;
  height: auto;
  max-height: none;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  object-fit: cover;
  min-height: 100vh;
}
</style>