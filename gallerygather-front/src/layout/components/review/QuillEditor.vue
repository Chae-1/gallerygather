<template>
  <div>
    <div ref="quillEditor" class="quill-editor"></div>
  </div>
</template>

<script>
import Quill from 'quill';
import 'quill/dist/quill.snow.css';
import axios from 'axios';

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
  mounted() {
    this.quill = new Quill(this.$refs.quillEditor, {
      theme: 'snow',
      placeholder: this.placeholder,
      modules: {
        toolbar: {
          container: [
          ['bold', 'italic', 'underline', 'strike'],
          ['blockquote'],
          [{ 'header': 1 }, { 'header': 2 }],
          [{ 'list': 'ordered' }, { 'list': 'bullet' }],
          [{ 'indent': '-1' }, { 'indent': '+1' }],
          [{ 'direction': 'rtl' }],
          [{ 'size': ['small', false, 'large', 'huge'] }],
          [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
          [{ 'color': [] }, { 'background': [] }],
          [{ 'font': [] }],
          [{ 'align': [] }],
          ['link', 'image', 'video'],
          ['clean']
        ],
        handlers: {
          image: this.imageHandler
        }
      }
    }
  });

    if (this.modelValue) {
      this.quill.root.innerHTML = this.modelValue;
    }

    this.quill.on('text-change', () => {
      const content = this.quill.root.innerHTML;
      this.$emit('update:modelValue', content);
    });
  },
  methods: {
    // 이미지를 선택하고, 그 이미지를 서버로 업로드한 후, 에디터에 이미지를 표시하는 작업 수행
    async imageHandler() { 
      // 웹사이트에서 파일을 선택할 때 사용하는 "파일 선택" 버튼과 같은 역할
      const input = document.createElement('input');
      input.setAttribute('type', 'file');
      input.setAttribute('accept', 'image/*');
      input.click();

      input.onchange = async () => {
        const file = input.files[0]; // 사용자가 선택한 파일
        const formData = new FormData(); // FormData는 HTML 폼 데이터를 쉽게 만들고 관리할 수 있게 해주는 객체
        formData.append('image', file); // 'image'는 서버에서 이 파일을 받아들일 때 사용할 키(key)이고, 'file'은 실제 파일 데이터

        try {
          // 서버에 이미지를 업로드합니다.
          const response = await axios.post('http://localhost:8080/api/upload', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          });

          const url = response.data.url; // 서버에서 반환된 이미지 URL을 가져옵니다.
          console.log("이미지 url::::::",url)
          const range = this.quill.getSelection();
          this.quill.insertEmbed(range.index, 'image', url); // 이미지 URL을 에디터에 삽입합니다.
        } catch (error) {
          console.error('Image upload failed:', error);
        }
      };
    }
  },
  watch: {
    modelValue(val) {
      if (val !== this.quill.root.innerHTML) {
        this.quill.root.innerHTML = val;
      }
    }
  }
};
</script>

<style scoped>
.quill-editor {
  height: 500px;
  max-height: none;
  overflow: auto;
  
}
</style>
