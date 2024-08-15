<script>
import axios from "axios";
import { apiRequest } from '@/util/RequestUtil.js'

export default {
  name: "CardComponent",
  props: {
    cards : Array,
  }
}
</script>

<template>
  <b-card-group deck >
    <router-link v-for="item in cards"
                :to="{path: '/exhibitiondetails/' + item.exhibitionId}"
                :key="item.exhibitionId">
      <b-card class="b-card"
              title-tag="h6"
              :title="item.title" style="max-width: 20rem; font-size: 15px;font-weight:500; margin: auto; flex: 25%;" img-width="400px"
              img-height="400px"
              :img-src="item.imageUrl"  img-top>

        <template #footer>
          
          <div class="card-duration">
            <!-- <span class="card-detail-title">기간</span> -->
            <div> {{ item.startDate }} ~ {{ item.endDate }} </div>
          </div>
          <div class="card-location">
            <!-- <span class="card-detail-title">장소</span> -->
            <div class="one-line">{{ item.place }}</div>
          </div>
          <div class="card-info">
            <!-- <span>진행상태</span> -->
            <div class="card-info-grade">
              <div style="margin: 0 20px;"> ⭐ {{item.rating}}</div>
              <!-- <em>별점</em> -->
            </div>
          </div>
        </template>
      </b-card>
  </router-link>
  </b-card-group>
</template>

<style>

.b-card {
  transition: transform 0.2s;
}

.card-title {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.b-card:hover {
  transform: scale(1.02);
}

.card-title {
  text-align: center;
  font-weight: bold;
}

.card-duration {
  display: flex;
  gap: 10px;
}

.card-location {
  display: flex;
  gap: 10px;
}

.card-duration::before,
.card-location::before {
  width: 19px;
  height: 19px;
  content: '';
  display: inline-block;
}

.card-duration::before {
  background-image: url("../../../assets/img/calander.svg");
}

.card-location::before {
  background-image: url("../../../assets/img/location.svg");
}

.card-footer > div {
  margin: 10px 0;
}

.card-footer > div:nth-of-type(1) {
  margin-top: 0;
}

.card-info {
  display: flex;
  justify-content: space-between;
}

.card-info > span + div {
  margin: 0 10px;
}

.card-detail-title {
  margin: 0 10px;
}

.main-content >div {
  display: grid;
  grid-template-rows: 4;
}

/* .card-deck {
  display: grid;
  grid-template-columns: repeat(4, 1fr); 
}*/

.card-deck > a {
  display: block;
  margin: 0 auto;

}

.one-line {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

</style>