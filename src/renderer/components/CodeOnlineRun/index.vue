<template>
  <el-card
    class="box-card"
    ref="onlineHomeworck"
    :body-style="{padding:'10px',height:onlineHomeworckHeight+'px'}"
    style="width:100%;"
  >
    <div slot="header" class="clearfix">
      <span>代码在线编辑</span>
      <el-button
        @click="homeWorkScreenFull($refs.onlineHomeworck.$el)"
        type="primary"
        round
        size="mini"
        style="float: right; "
      >{{isFullscreen?'退出全屏':'全屏'}}</el-button>
    </div>
    <el-row style="height:100%">
      <el-col style="height:100%" :span="12">
        <el-card
          shadow="hover"
          ref="homeWorckCodeCard"
          :body-style="{padding:'10px',paddingBottom:'45px'}"
          style="width:100%;height:100%;padding-right:5px;"
          class="box-card"
        >
          <CodeEditor
            ref="homeWorkCodeMirrorEditor"
            v-model="curValue"
            mode="text/html"
            style="width:100%;height:100%;"
            class="homeWork"
          ></CodeEditor>
          <el-button
            ref="fullButton"
            class="screen-full-button"
            style="margin-top:8px;"
            type="primary"
            round
            @click="homeWorkScreenFull($refs.homeWorckCodeCard.$el)"
            size="mini"
          >{{isFullscreen?'退出全屏':'全屏'}}</el-button>
          <el-button
            class="run-button"
            type="danger"
            round
            size="mini"
            @click="run"
          >运行</el-button>
        </el-card>
      </el-col>
      <el-col style="height:100%;padding-left:5px;" :span="12">
        <el-card
          shadow="hover"
          ref="codeView"
          style="width:100%;height:100%;"
          :body-style="{padding:'10px',height:'100%'}"
          class="box-card"
        >
          <ViewCodeResult
            v-if="time"
            style="height:100%;width:100%;"
            :src="`${HTML_BASE_URL}?authToken=${$store.state.user.token}&time=${time}`"
          ></ViewCodeResult>
          <el-button
            class="screen-full-button"
            @click="homeWorkScreenFull($refs.codeView.$el)"
            type="primary"
            round
            size="mini"
          >{{isFullscreen?'退出全屏':'全屏'}}</el-button>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import screenfull from "screenfull";
import ViewCodeResult from "@/components/ViewCodeResult";
import CodeEditor from "@/components/CodeEditor";
import { sendPreHtml } from "@/api/person";
export default {
  components: {
    ViewCodeResult,
    CodeEditor
  },
  props: {
    value: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      onlineHomeworckHeight: 376,
      isFullscreen: false,
      time: 0,
      curValue:''
    };
  },
  created() {},
  mounted() {
      this.curValue=this.value
      
      if(this.curValue) this.run()
      
  },
  methods: {
    run() {
      sendPreHtml({ preHtml: this.value }).then(res => {
        this.time = new Date().getTime();
      });
    },
    screenFullByEl(el, cb) {
      if (screenfull.enabled) {
        screenfull.toggle(el);
        let _this = this;
        screenfull.on("change", () => {
          _this.isFullscreen = screenfull.isFullscreen;
          typeof cb === "function" && cb(_this.isFullscreen);
        });
      } else {
        this.$message({
          message: "默认浏览器不支持全屏状态",
          type: "warning"
        });
      }
    },
    homeWorkScreenFull(el) {
      this.screenFullByEl(el, state => {
        if (state) {
          this.$refs.homeWorkCodeMirrorEditor.codeMirrorEditor.setSize(
            "100%",
            window.screen.availHeight * 0.8
          );
          this.$refs.onlineHomeworck.$el.style.height = "100%";
          this.onlineHomeworckHeight = window.screen.availHeight * 0.9;
        } else {
          this.$refs.homeWorkCodeMirrorEditor.codeMirrorEditor.setSize(
            "100%",
            ""
          );
          this.onlineHomeworckHeight = 376;
        }
      });
    }
  },
  watch: {
      curValue(newVal){
          this.$emit('input',newVal)
      }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.screen-full-button{
    position: absolute;
    left: 8px;
    bottom: 8px;
}
.el-card{
    position: relative;
}
.el-card__header{
    line-height: 28px !important;
}
.run-button{
    position: absolute;
    right: 8px;
    bottom: 8px;
}
</style>
