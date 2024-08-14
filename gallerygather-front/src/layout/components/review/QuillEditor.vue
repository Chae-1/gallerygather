<template>
  <div>
    <div ref="quillEditor" class="quill-editor"></div>
  </div>
</template>

<script>
import Quill from 'quill'
import 'quill/dist/quill.snow.css'
import axios from 'axios'

export default {
  props: {
    modelValue: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      pendingImages: [] // 서버로 업로드할 이미지 리스트
    }
  },
  mounted() {
    this.quill = new Quill(this.$refs.quillEditor, {
      theme: 'snow',
      placeholder: this.placeholder,
      modules: {
        toolbar: {
          container: [
            ['bold', 'italic', 'underline', 'strike'],
            ['blockquote'],
            [{ header: 1 }, { header: 2 }],
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ indent: '-1' }, { indent: '+1' }],
            [{ direction: 'rtl' }],
            [{ size: ['small', false, 'large', 'huge'] }],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ color: [] }, { background: [] }],
            [{ font: [] }],
            [{ align: [] }],
            ['link', 'image', 'video'],
            ['clean']
          ],
          handlers: {
            image: this.handleImageAdded // 커스텀 핸들러 등록
          }
        }
      }
    })

    if (this.modelValue) {
      this.quill.root.innerHTML = this.modelValue
    }

    this.quill.on('text-change', () => {
      const content = this.quill.root.innerHTML
      this.$emit('update:modelValue', content)
    })

    // this.quill.getModule('toolbar').addHandler('image', this.handleImageAdded)
  },
  methods: {
  handleImageAdded() {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/*');
    input.click();

    input.onchange = () => {
      const file = input.files[0];

      const reader = new FileReader();
      reader.onload = (e) => {
        const range = this.quill.getSelection();
        // Base64 이미지를 먼저 삽입
        this.quill.insertEmbed(range.index, 'image', e.target.result);

        // 서버로 업로드할 파일과 위치를 리스트에 추가
        this.pendingImages.push({ file, range: { index: range.index, length: 1 } });
      };
      reader.readAsDataURL(file);
    };
  },

  async uploadImages() {
    const uploadedUrls = [];

    for (const { file, range } of this.pendingImages) {
      const formData = new FormData();
      formData.append('image', file);

      try {
        const response = await axios.post('http://localhost:8080/api/uploads', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });

        console.log('서버에서 온 응답: ', response.data);

        const imagePath = response.data.path; // 서버에서 반환된 경로를 사용
        const imageName = response.data.originalName;

        console.log('경로:', imagePath);
        console.log('range:', range);

        // 업로드된 URL 정보를 배열에 추가합니다.
        uploadedUrls.push({
          originalName: imageName,
          path: imagePath,
        });

        console.log('업로드된 URL:', uploadedUrls);

        // Base64 이미지를 서버에서 받은 URL로 교체
        this.quill.deleteText(range.index, range.length); // Base64 이미지를 제거
        this.quill.insertEmbed(range.index, 'image', imagePath);// 서버에서 받은 이미지 URL을 삽입

      } catch (error) {
        console.error('Image upload failed:', error);
        throw error;
      }
    }

    return uploadedUrls;
  },
},
  watch: {
    modelValue(val) {
      if (val !== this.quill.root.innerHTML) {
        this.quill.root.innerHTML = val;
      }
    }
  }
}
</script>

<style scoped>
.quill-editor {
  height: 500px;
  max-height: none;
  overflow: auto;
}
</style>
