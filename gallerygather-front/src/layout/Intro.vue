<template>
  <div style="width: 100%">
    <!-- Fixed Navigation Bar -->
    <div class="navbar">
      <div class="logo">
        <img src="../assets/img/logo.png" alt="Logo" style="height: 70px; margin-top:20px;">
      </div>
    </div>

    <!-- Photo Container -->
    <div class="container-x" ref="container">
      <div class="photo" id="photo1">
        <img src="../assets/img/intro/intro1.jpg" alt="Photo 1">
      </div>
      <div class="photo" id="photo2">
        <img src="../assets/img/intro/intro2.jpg" alt="Photo 2">
      </div>
      <div class="photo" id="photo3">
        <img src="../assets/img/intro/intro3.jpg" alt="Photo 3">
      </div>
    </div>

    <!-- Dots Navigation -->
    <div class="dots">
      <div 
        v-for="(dot, index) in photos" 
        :key="index" 
        :class="['dot', { active: currentIndex === index }]"
        @click="scrollToPhoto(index)"
      ></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Intro",
  data() {
    return {
      currentIndex: 0,
      isTransitioning: false,
      photos: [1, 2, 3],
    };
  },
  methods: {
    handleScroll(event) {
      if (this.isTransitioning) return;

      const delta = event.deltaY;
      if (delta > 0) {
        if (this.currentIndex < this.photos.length - 1) {
          this.currentIndex++;
          this.scrollToPhoto(this.currentIndex);
        } else if (this.currentIndex === this.photos.length - 1) {
          this.$router.push({ name: 'home' });
        }
      } else if (delta < 0) {
        if (this.currentIndex > 0) {
          this.currentIndex--;
          this.scrollToPhoto(this.currentIndex);
        }
      }
    },
    scrollToPhoto(index) {
      this.isTransitioning = true;
      this.currentIndex = index;
      const photoElement = this.$refs.container.children[index];
      photoElement.scrollIntoView({ behavior: 'smooth' });

      photoElement.style.opacity = 0;
      setTimeout(() => {
        photoElement.style.transition = 'opacity 1s ease-in-out';
        photoElement.style.opacity = 1;
        this.isTransitioning = false;
      }, 200);
    }
  },
  mounted() {
    window.addEventListener('wheel', this.handleScroll);
  },
  beforeDestroy() {
    window.removeEventListener('wheel', this.handleScroll);
  }
};
</script>

<style scoped>
div {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  overflow: hidden;
}

/* Fixed Navbar Style */
.navbar {
  position: fixed !important;
  top: 0;
  left: 0;
  width: 100%;
  height: 90px;
  /* background-color: rgba(0, 0, 0, 0.5) !important; */
  justify-content: space-between !important;
  align-items: center !important;
  padding: 0 20px !important;
  z-index: 10000 !important; /* Ensure it appears above everything else */
}
/* 
.menu ul {
  list-style: none !important;
  display: flex !important;
  margin: 0 !important;
  padding: 0 !important;
}

.menu ul li {
  margin-left: 20px !important;
  cursor: pointer !important;
  color: white !important; /* Ensure text is visible on dark background 
} */

/* Container and Photos */
.container-x {
  width: 100% !important;
  height: 100vh !important;
  overflow-y: scroll !important;
  scroll-behavior: smooth !important;
  position: relative !important;
}

.photo {
  scroll-snap-align: start !important;
  width: 100% !important;
  height: 100vh !important;
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
  position: relative !important;
  overflow: hidden !important;
}

.photo img {
  width: 100% !important;
  height: 100% !important;
  object-fit: cover !important;
  position: absolute !important;
  top: 50% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
}

/* Dots Navigation */
.dots {
  position: fixed !important;
  top: 50% !important;
  right: 20px !important;
  transform: translateY(-50%) !important;
  display: flex !important;
  flex-direction: column !important;
  z-index: 9999 !important; /* Ensure it appears above the photos */
}

.dot {
  width: 12px !important;
  height: 12px !important;
  background-color: red !important;
  border-radius: 50% !important;
  margin: 8px 0 !important;
  cursor: pointer !important;
  transition: background-color 0.3s !important;
}

.dot.active {
  background-color: rgba(255, 255, 255, 0.9) !important;
}

</style>