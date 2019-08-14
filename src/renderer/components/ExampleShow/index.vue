<template>
  <el-row ref="exampleShow" class="m-example-show">
    <el-col :span="8" class="example-col" :style="{height:height!=0?height+'px':''}">
      <el-card
        ref="codeCardDesc"
        shadow="hover"
        :body-style="{padding:'10px',height:'100%',overflow:'auto'}"
        class="box-card"
      >
        <div v-html="data.content"></div>
        <el-button
          class="screen-full-button"
          @click="screenFull($refs.codeCardDesc.$el)"
          type="primary"
          round
          size="mini"
        >{{isFullscreen?'退出全屏':'全屏'}}</el-button>
      </el-card>
    </el-col>
    <el-col :span="8" class="example-col" :style="{height:height!=0?height+'px':''}">
      <el-card shadow="hover" ref="codeCard" :body-style="{padding:'10px'}" class="box-card">
        <CodeEditor @ready="codeEditorReady" v-model="data.show_the_code " mode="text/html"></CodeEditor>
        <el-button
          ref="fullButton"
          class="screen-full-button"
          @click="screenFull($refs.codeCard.$el)"
          type="primary"
          round
          size="mini"
        >{{isFullscreen?'退出全屏':'全屏'}}</el-button>
        <el-button class="run-button" @click="run" type="danger" round size="mini">运行</el-button>
      </el-card>
    </el-col>
    <el-col :span="8" class="example-col" :style="{height:height!=0?height+'px':''}">
      <el-card
        shadow="hover"
        ref="codeView"
        :body-style="{padding:'10px',height:'100%'}"
        class="box-card"
      >
        <ViewCodeResult
          v-if="time"
          :style="{height:height!=0?height+'px':''}"
          :src="`${HTML_BASE_URL}?authToken=${$store.state.user.token}&time=${time}`"
        ></ViewCodeResult>
        <el-button
          class="screen-full-button"
          @click="screenFull($refs.codeView.$el)"
          type="primary"
          round
          size="mini"
        >{{isFullscreen?'退出全屏':'全屏'}}</el-button>
      </el-card>
    </el-col>
  </el-row>
</template>
<script>
import ViewCodeResult from "@/components/ViewCodeResult";
import CodeEditor from "@/components/CodeEditor";
import screenfull from "screenfull";
import { mapGetters } from "vuex";
import { sendPreHtml } from "@/api/person";
const buttonDefaultHeight = 66;
export default {
  components: {
    CodeEditor,
    ViewCodeResult
  },
  props: {
    data: {
      type: Object,
      default: null
    },
    height: {
      type: Number | String,
      default: 364
    },
    ifFullscreen: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      levelList: null,
      codeMirrorEditor: null,
      isFullscreen: false,
      codeCarDefaultHeight: 0,
      runCode: "",
      time: 0
    };
  },
  created() {},
  mounted() {
    this.isFullscreen = this.ifFullscreen;
    this.$nextTick(() => {
      this.codeCarDefaultHeight = this.$refs.codeCard.$el.clientHeight;
      if (
        this.data.show_the_code &&
        this.$refs.exampleShow.$el.clientHeight > 0
      ) {
        this.run();
      }
    });
  },
  methods: {
    run() {
      this.runCode = this.data.show_the_code;
      sendPreHtml({ preHtml: this.runCode }).then(res => {
        this.time = new Date().getTime();
      });
    },
    codeEditorReady(codeMirrorEditor) {
      this.codeMirrorEditor = codeMirrorEditor;
      this.data.codeMirrorEditorInstantce = codeMirrorEditor;
      this.data.run = this.run;
      this.$emit("ready", this.data);
    },
    screenFull(el) {
      if (screenfull.enabled) {
        let instant = screenfull.toggle(el);
        let _this = this;
        screenfull.on("change", () => {
          _this.isFullscreen = screenfull.isFullscreen;
        });
      } else {
        this.$message({
          message: "默认浏览器不支持全屏状态",
          type: "warning"
        });
      }
    }
  },
  computed: {
    ...mapGetters["token"]
  },
  watch: {
    isFullscreen(newVal) {
      if (newVal) {
        this.codeMirrorEditor.setSize("100%", window.screen.availHeight * 0.95);
      } else {
        this.codeMirrorEditor.setSize("100%", "");
      }
    },
    // height(newVal){
    //   newVal=newVal?newVal:364
    //   // console.log(newVal)
    //   this.codeMirrorEditor.setSize('100%',newVal-buttonDefaultHeight)
    // },
    ifFullscreen(newVal) {
      this.isFullscreen = newVal;
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.m-example-show {
  .example-col {
    height: 364px;
    box-sizing: border-box;
    padding: 5px;
    .box-card {
      position: relative;
      width: 100%;
      height: 100%;
      padding-bottom: 40px;
      overflow: hidden;
      .screen-full-button {
        z-index: 99;
        position: absolute;
        bottom: 5px;
        left: 10px;
      }
      .run-button {
        position: absolute;
        bottom: 5px;
        right: 10px;
        z-index: 99;
      }
    }
  }
}
</style>
<style rel="stylesheet/scss" lang="scss">
.m-example-show {
  .CodeMirror {
    // height: 100;
  }
}
</style>