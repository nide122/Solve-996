<template>
  <div ref="viewer" class="u-marckdown-viewer">
    <viewer
      :value="value"
      :height="`${height}px`"
      @load="onEditorLoad"
      @focus="onEditorFocus"
      @blur="onEditorBlur"
      @change="onEditorChange"
      @stateChange="onEditorStateChange"
    />
  </div>
</template>

<script>
import "tui-editor/dist/tui-editor-contents.css";
import "highlight.js/styles/github.css";
import { Viewer } from "@toast-ui/vue-editor";
export default {
  components: { Viewer },
  props: {
    value: {
      type: String,
      default: ""
    },
    height: {
      type: Number | String,
      default: 500
    }
  },
  data() {
    return {
      editor: null,
      html: ""
    };
  },
  created() {},
  mounted() {
  },
  methods: {
    onEditorLoad() {
      // implement your code
    },
    onEditorFocus() {
      // implement your code
    },
    onEditorBlur() {
      // implement your code
    },
    onEditorChange() {
      // implement your code
    },
    onEditorStateChange() {
      // implement your code
    }
  },
  watch: {
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.u-marckdown-viewer {
  height: 100%;
  width: 100%;
}
</style>
