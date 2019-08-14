<template>
  <el-row>
    <el-steps :active="form.test_step" align-center style="margin-bottom: 20px">
      <el-step title="项目信息" description="采集项目信息"></el-step>
      <el-step title="基础文档" description="根据服务器地址生成基础接口文档"></el-step>
      <el-step title="自定义流程" description="自定义参数与流程"></el-step>
    </el-steps>
    <el-form ref="form" :model="form" label-width="80px" style="max-width: 700px;margin: 40px auto">
      <el-form-item label="产出项目" prop="project_path">
        <el-select v-model="form.project_path" placeholder="请选择项目地址" @change="change_build_path">
          <el-option
            v-for="item in project"
            :key="item._id"
            :label="item.project_name"
            :value="item.build_path"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Controller">
        <el-checkbox-group v-model="form.build_path">
          <div v-for="(item,index) in build_path" :key="index">
            <el-checkbox :label="item.value" :key="item.value" border></el-checkbox>
          </div>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="项目描述" prop="desc">
        <el-input type="textarea" v-model="form.desc"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">下一步</el-button>
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
      project: [],
      build_path: [],
      form: {
        desc: "",
        test_step: 1,
        project_name: "",
        project_path: "",
        build_path: []
      }
    };
  },
  methods: {
    onSubmit() {
      this.form.apiJsonList = javaTest.initControllerTestApi(
        this.form.build_path
      );
      if (this.$route.query.id) {
        this.form.update_date = new Date();
        this.$db.test.update({ _id: this.$route.query.id }, this.form);
        this.$router.push({ path: "JavaTestApi?id=" + this.$route.query.id });
      } else {
        this.form.create_date = new Date();
        this.$db.test.insert(this.form);
        this.$db.test.findOne(
          { create_date: this.form.create_date },
          (err, docs) => {
            this.$router.push({ path: "JavaTestApi?id=" + docs._id });
          }
        );
      }
    },
    // 加载构建记录
    loadList: function() {
      this.$db.project.find({}, (err, docs) => {
        docs.forEach(function(ele) {
          ele.build_path = ele.build_path
            ? path.join(ele.build_path, ele.project_name)
            : "";
        });
        this.project = docs;
        this.loadDetail();
      });
    },
    change_build_path() {
      this.project.forEach((ele, index) => {
        if (this.form.project_path === ele.build_path) {
          this.form.project_name = ele.project_name;
          return;
        }
      });
      this.build_path = getKeyPath(this.form.project_path, "controller");
    },
    loadDetail() {
      if (this.$route.query.id) {
        this.$db.test.findOne(
          { _id: this.$route.query.id },
          (err, newDoc) => {
            this.form = newDoc;
            this.change_build_path();
          }
        );
      }
    }
  },
  mounted() {
    this.loadList();
  }
};
</script>

<style>
</style>
