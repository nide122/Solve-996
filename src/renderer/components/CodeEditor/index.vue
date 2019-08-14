<template>
  <div class="u-editor" ref="uEditor">
    <textarea class="form-control" v-model="value" ref="editor" name="code"></textarea>
  </div>
</template>

<script>

import * as CodeMirror from "codemirror/lib/codemirror";
import "codemirror/lib/codemirror.css";
import "codemirror/theme/monokai.css";
import "codemirror/mode/javascript/javascript";
import "codemirror/mode/http/http";
import "codemirror/mode/sql/sql";
import "codemirror/mode/vue/vue";
import "codemirror/mode/xml/xml";
import "codemirror/keymap/sublime.js";
import "codemirror/addon/hint/show-hint";
import "codemirror/addon/hint/javascript-hint";
import "codemirror/addon/hint/sql-hint";
import "codemirror/addon/hint/html-hint";
import "codemirror/addon/hint/xml-hint";
import "codemirror/addon/hint/anyword-hint";
import "codemirror/addon/hint/css-hint";
import "codemirror/addon/hint/show-hint";
import "codemirror/addon/scroll/simplescrollbars.css";
import "codemirror/addon/scroll/simplescrollbars";
import "codemirror/addon/selection/active-line";
const ENVET_NAMES = [
  "change",
  "changes",
  "beforeChange",
  "cursorActivity",
  "keyHandled",
  "inputRead",
  "electrictInput",
  "beforeSelectionChange",
  "viewportChange",
  "gutterClick",
  "focus",
  "blur",
  "scroll",
  "keydown",
  "keypress",
  "keydown",
  "keyup",
  "mousedown",
  "dblclick"
];
export default {
  data() {
    return {
      levelList: null,
      codeMirrorEditor: null,
      currVal:''
    };
  },
  props: {
    value: {
      type: String,
      default: ""
    },
    mode: {
      type: String,
      default: "javascript"
    },
    theme: {
      type: String,
      default: "monokai"
    },
    extraKeys: {
      type: Object,
      default: null
    },
    lineNumbers: {
      type: Boolean,
      default: true
    },
    scrollbarStyle: {
      type: String,
      default: "overlay"
    },
    readOnly: {
      type: Boolean,
      default: false
    },
    autofocus: {
      type: Boolean,
      default: true
    },
    styleActiveLine: {
      type: Boolean,
      default: true
    },
    tabSize: {
      type: Number,
      default: 2
    },
    indentUnit: {
      type: Number,
      default: 2
    },
  },
  watch: {
    mode(newVal) {
      this.codeMirrorEditor.setOption("mode", newVal);
    },
  },
  created() {
    
  },
  mounted() {
    this.$nextTick(() => {
      this.init();
      this.codeMirrorEditor.refresh();
    });
  },
  methods: {
    init() {
      this.codeMirrorEditor = CodeMirror.fromTextArea(this.$refs.editor, {
        value: this.value,
        tabSize: this.tabSize, // tab的空格宽度
        indentUnit: this.indentUnit, //缩进单位
        smartIndent: true, //自动缩进是否开启
        mode: this.mode, //编辑器语言
        theme: this.theme, //编辑器主题
        // extraKeys: {"Ctrl+D":"findPrev"}, //ctrl可以弹出选择项
        lineNumbers: this.lineNumbers, //显示行号
        // lineWrapping:'wrap',//长行时文字是换行(wrap)还是滚动(scroll)，默认为滚动(scroll)。÷
        scrollbarStyle: this.scrollbarStyle, //设置滚动条,
        readOnly: this.readOnly, //设置为只读true/false;也可设置为"nocursor"失去焦点
        autofocus: this.autofocus, //初始时是否自动获取焦点boolean
        styleActiveLine: this.styleActiveLine //设置光标所在行高亮true/false，需引入工具包：
      });
      this.initEvent();
      this.$emit("ready", this.codeMirrorEditor);
    },
    initEvent() {
      let _this = this;
      ENVET_NAMES.forEach(eventName => {
        this.codeMirrorEditor.on(eventName, function(event) {
          _this.$emit(eventName, event);
          if(/change/.test(eventName)){
            _this.$emit('input',_this.codeMirrorEditor.getValue())
            // this.codeMirrorEditor.refresh();
          }
        });
      });
    }
  },
  computed:{
  },
  destroyed(){
    this.codeMirrorEditor=null
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.u-editor {
  width: 100%;
  height: 100%;
  textarea {
  }
}
</style>
<style rel="stylesheet/scss" lang="scss">
.CodeMirror {
  border-radius: 5px;
}
</style>
