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
      previousContent: '', // 이전 content 저장
      pendingImages: [], // 서버로 업로드할 이미지 리스트
      imagesToDelete: []
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

    this.previousContent = this.quill.root.innerHTML

    this.quill.on('text-change', () => {
      const currentContent = this.quill.root.innerHTML
      // 이미지 삭제 감지
      this.detectImageDeletion(this.previousContent, currentContent)
      // 상태를 업데이트
      this.previousContent = currentContent
      this.$emit('update:modelValue', currentContent)
    })

    // this.quill.getModule('toolbar').addHandler('image', this.handleImageAdded)
  },
  methods: {
    handleImageAdded() {
      const input = document.createElement('input')
      input.setAttribute('type', 'file')
      input.setAttribute('accept', 'image/*')
      input.click()

      input.onchange = () => {
        const file = input.files[0]

        const reader = new FileReader()
        reader.onload = (e) => {
          const range = this.quill.getSelection()
          // Base64 이미지를 먼저 삽입
          this.quill.insertEmbed(range.index, 'image', e.target.result)

          // 서버로 업로드할 파일과 위치를 리스트에 추가
          this.pendingImages.push({ file, range: { index: range.index, length: 1 } })
        }
        reader.readAsDataURL(file)
      }
    },

    async uploadImages() {
      const uploadedUrls = []

      for (const { file, range } of this.pendingImages) {
        const formData = new FormData()
        formData.append('image', file)

        try {
          const response = await axios.post('http://localhost:8080/api/uploads', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })

          console.log('서버에서 온 응답: ', response.data)

          const imagePath = response.data.path // 서버에서 반환된 경로를 사용
          const imageName = response.data.originalName

          // 업로드된 URL 정보를 배열에 추가합니다.
          uploadedUrls.push({
            originalName: imageName,
            path: imagePath
          })

          // Base64 이미지를 서버에서 받은 URL로 교체
          this.quill.deleteText(range.index, range.length) // Base64 이미지를 제거
          this.quill.insertEmbed(range.index, 'image', imagePath) // 서버에서 받은 이미지 URL을 삽입
        } catch (error) {
          console.error('Image upload failed:', error)
          throw error
        }
      }

      return uploadedUrls
    },
    detectImageDeletion(previousContent, currentContent) {
      console.log('이전 Content:', previousContent)
      console.log('현재 Content:', currentContent)

      const previousImages = this.extractImageSources(previousContent)
      const currentImages = this.extractImageSources(currentContent)

      console.log('이전 Images:', previousImages)
      console.log('현재 Images:', currentImages)

      const deletedImages = previousImages.filter((src) => {
        return !currentImages.includes(src) && !src.startsWith('data:image/')
      })
      if (deletedImages.length > 0) {
        console.log('삭제된 Images:', deletedImages)
        this.imagesToDelete.push(...deletedImages)
      }
    },

    extractImageSources(content) {
      const div = document.createElement('div')
      div.innerHTML = content
      const imgElements = div.getElementsByTagName('img')
      const imageSources = []

      for (let img of imgElements) {
        console.log('Found Image Source:', img.src)
        imageSources.push(img.src)
      }

      return imageSources
    }
  },

  watch: {
    modelValue(val) {
      if (val !== this.quill.root.innerHTML) {
        this.quill.root.innerHTML = val
      }
    }
  }
}
</script>

<style scoped>
.quill-editor {
  max-height: none; /* 최대 높이 제한 제거 */
  overflow: visible; /* 스크롤바를 없애고 내용이 넘칠 때 컨테이너가 자동으로 커지도록 설정 */
  min-height: 500px; /* 에디터의 최소 높이를 설정 */
  /* border-radius: 10px; */
}
</style>
