<template>
  <el-row>
    <el-steps :active="form.test_step" align-center style="margin-bottom: 20px">
      <el-step title="项目信息" description="采集项目信息"></el-step>
      <el-step title="基础文档" description="根据服务器地址生成基础接口文档"></el-step>
      <el-step title="自定义流程" description="自定义参数与流程"></el-step>
    </el-steps>
    <el-form ref="form" :model="form" label-width="80px" style="max-width: 700px;margin: 40px auto">
      <el-form-item label="API地址">
        <el-input v-model="form.api_path"></el-input>
      </el-form-item>
      <el-form-item label="token名称">
        <el-input v-model="form.api_token_name"></el-input>
      </el-form-item>
      <el-form-item label="token值">
        <el-input v-model="form.api_token_value"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">下一步</el-button>
        <el-button @click="prevStep">上一步</el-button>
      </el-form-item>
    </el-form>
  </el-row>
</template>

<script>
const path = require("path");
import * as javaTest from "../../template/javaTest";
import { getKeyPath } from "../../template/java";

export default {
  data() {
    return {
      form: {
        test_step: 2,
        project_name: "",
        project_path: "",
        api_path: "",
        api_token_name: "",
        api_token_value: "",
        build_path: []
      }
    };
  },
  methods: {
    onSubmit() {
      this.form.test_step = 3;
      this.$db.test.update({ _id: this.$route.query.id }, this.form);
      this.$router.push({ path: "JavaTestCustom?id=" + this.$route.query.id });
    },
    prevStep() {
      this.$router.push({ path: "JavaTestProject?id=" + this.$route.query.id });
    },
    loadDetail() {
      if (this.$route.query.id) {
        this.$db.test.findOne(
          { _id: this.$route.query.id },
          (err, newDoc) => {
            this.form = newDoc;
            this.form.test_step = 2;
          }
        );
      }
    }
  },
  mounted() {
    this.loadDetail();
  }
};
</script>

<style>
</style>
