<template>
  <div>
    <el-row style="margin-bottom: 20px;" type="flex" :gutter="20">
      <el-col :span="20">
        <el-input
          placeholder="请选择构建地址"
          :disabled="true"
          class="input-with-select"
          v-model="cmd_file_path"
        >
          <el-button slot="append" icon="el-icon-search" v-on:click="openDialog"></el-button>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="clearCmdPath">重置地址</el-button>
      </el-col>
    </el-row>
    <el-row style="margin-bottom: 20px;" type="flex" :gutter="20">
      <el-col :span="20">
        <el-input
          placeholder="请输入需要执行的命令"
          v-model="cli_value"
          class="input-with-select"
          @keyup.enter.native="execAction"
        >
          <el-select
            v-model="cli_default_value"
            clearable
            slot="prepend"
            placeholder="请选择默认命令"
            @change="changeDefaultValue"
          >
            <el-option label="启动Vue ui" value="vue ui"></el-option>
            <el-option label="安装Yarn" value="npm install -g yarn"></el-option>
            <el-option label="安装Vue Cli" value="yarn global add @vue/cli"></el-option>
            <el-option label="Fis start" value="fis3 server start --type node"></el-option>
            <el-option label="Fis release" value="fis3 release -w"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-d-arrow-right" @click="execAction"></el-button>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="exitProcess">退出进程</el-button>
      </el-col>
    </el-row>

    <el-card class="box-card" style="margin-bottom: 20px;">
      <div slot="header" class="clearfix">
        <span>log日志</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="clearLogs">清除日志</el-button>
      </div>
      <div v-for="(log,index) in vue_logs" class="text item" :key="index">
        <div :class="'logs-color-box bg-' + log.type ">{{ log.log }}</div>
      </div>
    </el-card>

    <el-alert
      title="Node 版本要求 : Vue CLI 需要 Node.js 8.9 或更高版本 (推荐 8.11.0+)。windows系统你可以使用 nvm 或 nvm-windows 在同一台电脑中管理多个 Node 版本。Mac系统你可以使用 n 管理多个Node版本"
      type="warning"
      :closable="false"
    ></el-alert>
  </div>
</template>
<script>
import { runExec, closeExec } from "../../template/frontEnd";
export default {
  data() {
    return {
      cmd_file_path: "",
      cli_value: "",
      cli_default_value: ""
    };
  },
  methods: {
    openDialog() {
      let filePaths = this.$electron.remote.dialog.showOpenDialog({
        properties: ["openDirectory"]
      });
      if (filePaths && filePaths.length > 0) {
        this.cmd_file_path = filePaths[0];
      }
    },
    changeDefaultValue(cli) {
      this.cli_value = cli;
    },
    clearLogs() {
      this.$store.dispatch("clearLogs", []);
    },
    exitProcess() {
      closeExec();
    },
    clearCmdPath() {
      this.cmd_file_path = "";
    },
    execAction() {
      if (this.cli_value.length == 0) return;
      runExec(this.$store, this.cli_value, this.cmd_file_path);
      this.$notify({
        title: "成功",
        message: "执行" + this.cli_value + "成功",
        type: "success"
      });
    }
  },
  computed: {
    vue_logs() {
      return this.$store.state.frontEnd.vue_logs;
    }
  },
  mounted() {}
};
</script>

<style>
</style>
