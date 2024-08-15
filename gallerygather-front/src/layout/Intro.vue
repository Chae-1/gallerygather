<template>
  <div style="width: 100%">
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
      photos: [1, 2, 3], // Dummy array to represent photos
    };
  },
  methods: {
    handleScroll(event) {
      if (this.isTransitioning) return;

      const delta = event.deltaY;
      if (delta > 0) {
        // Scroll down
        if (this.currentIndex < this.photos.length - 1) {
          this.currentIndex++;
          this.scrollToPhoto(this.currentIndex);
        } else if (this.currentIndex === this.photos.length - 1) {
          // Redirect to another page
          this.$router.push({ name: 'home' });
        }
      } else if (delta < 0) {
        // Scroll up
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
      }, 100);
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


.container-x {
  width: 100%;
  height: 100%;
  overflow-y: scroll;
  scroll-behavior: smooth;
}

.photo {
    scroll-snap-align: start;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    overflow: hidden;
}

.photo img {
    width: 100%; /* This makes the image fill the entire width of the viewport */
    height: 100%; /* This makes the image fill the entire height of the viewport */
    object-fit: cover;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.dots {
    position: absolute; /* Change from 'fixed' to 'absolute' */
    top: 50%;
    right: 20px;
    transform: translateY(-50%);
    display: flex;
    flex-direction: column;
    z-index: 1; /* Ensure dots are on top of the images */
}

.dot {
    width: 12px;
    height: 12px;
    background-color: gray;
    border-radius: 50%;
    margin: 8px 0;
    cursor: pointer;
    transition: background-color 0.3s;
}

.dot.active {
    background-color: white;
}

</style>
