<template>
  <el-form :rules="rules" ref="form" :model="form" label-width="80px" v-loading="loading">
    <el-form-item label="项目名称" prop="project_name">
      <el-input v-model="form.project_name"></el-input>
    </el-form-item>
    <el-form-item label="简介" prop="desc">
      <el-input type="textarea" v-model="form.desc"></el-input>
    </el-form-item>
    <el-form-item label="立即构建">
      <el-switch v-model="form.is_build"></el-switch>
    </el-form-item>
    <el-form-item label="构建地址" v-if="form.is_build" prop="bulid_path">
      <el-input
        placeholder="请选择构建地址"
        v-model="form.build_path"
        :disabled="true"
        class="input-with-select"
      >
        <el-button slot="append" icon="el-icon-search" v-on:click="openDialog"></el-button>
      </el-input>
    </el-form-item>
    <el-form-item label="目录结构">
      <el-tree
        :data="dir_structure"
        show-checkbox
        node-key="path"
        :default-expanded-keys="[1]"
        :default-checked-keys="[]"
        :props="defaultProps"
      ></el-tree>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('form')" v-if="isBuild">立即创建</el-button>
      <el-button @click="resetForm('form')" v-if="isBuild">重置</el-button>
      <el-button @click="goToList">构建历史</el-button>
      <el-button @click="changeShow">预览目录结构</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import {  initProject } from "@/template/javaInit";
import { Loading } from "element-ui";
import { Project } from "@/models/project";
import FileOpt from "@/models/FileOpt";
export default {
  data() {
    return {
      dir_structure: [],
      loading: false,
      defaultProps: {
        children: "children",
        label: "fileName"
      },
      isBuild: true,
      form: new Project({
        project_name: "",
        template_id: 0,
        build_path: "",
        desc: ""
      }), // 初始化
      rules: {
        project_name: [
          {
            required: true,
            message: "请输入项目名称",
            trigger: "blur"
          },
          {
            min: 2,
            max: 15,
            message: "长度在 2 到 15 个字符",
            trigger: "blur"
          }
        ],
        build_path: [
          {
            required: true,
            message: "请选择构建地址",
            trigger: "blur"
          }
        ],
        desc: [
          {
            required: true,
            message: "请填写项目简介",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    changeShow: function(argument) {
      let dir_structure = FileOpt.showProjectStructure(
        FileOpt.getJavaInitStructure()
      );
      this.dir_structure = [dir_structure];
      console.log(this.dir_structure);
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.$route.query.id) {
            this.form.update_date = new Date().getTime();
          } else {
            this.form.input_date = new Date().getTime();
          }
          if (this.form.build_path.length > 0) {
            initProject(this.form);
          }
          this.loading = true;
          // 如果更新
          if (this.$route.query.id) {
            this.$db.project.update({ _id: this.$route.query.id }, this.form);
            this.loading = false;
          } else {
            this.$db.project.insert(this.form, (err, newDoc) => {
              console.log(newDoc);
            });
            this.loading = false;
          }
          this.$notify({
            title: "成功",
            message: "操作成功",
            type: "success",
            duration: 2000
          });
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    openDialog() {
      let filePaths = this.$electron.remote.dialog.showOpenDialog({
        properties: ["openDirectory"]
      });
      if (filePaths && filePaths.length > 0) {
        this.form.build_path = filePaths[0];
      }
    },
    // 去列表页
    goToList() {
      this.$router.push({ path: "initJavaList" });
    },
    loadDetail() {
      if (this.$route.query.id) {
        var that = this;
        this.$db.project.findOne({ _id: that.$route.query.id }, function(
          err,
          newDoc
        ) {
          that.form = newDoc;
        });
      }
      if (this.$route.query.detail) {
        this.isBuild = false;
      }
    }
  },
  created() {
    // this.$db.project.remove({}, { multi: true }, function(err, numRemoved) {});
    this.loadDetail();
  }
};
</script>

<style>
</style>
