<template>
  <div>
    <div ref="quillEditor" class="quill-editor"></div>
  </div>
</template>

<script>
import Quill from 'quill';
import 'quill/dist/quill.snow.css';

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
        toolbar: [
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
        ]
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
  overflow: hidden;
  
}
</style>
