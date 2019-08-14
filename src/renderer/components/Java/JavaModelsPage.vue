<template>
  <el-form ref="form" :model="form" label-width="80px">
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
    <el-form-item label="POJO地址" prop="build_path">
      <el-select v-model="form.build_path" clearable filterable placeholder="请选择" style="width: 50%">
        <el-option
          v-for="item in build_path"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="VO地址" prop="build_path_vo">
      <el-select v-model="form.build_path_vo" clearable filterable placeholder="请选择" style="width: 50%">
        <el-option
          v-for="item in build_path_vo"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="SQL地址" prop="sql_path">
      <el-input
        placeholder="请选择SQL文件地址"
        v-model="form.sql_path"
        :disabled="true"
        class="input-with-select"
        style="width: 50%"
      >
        <el-button slot="append" icon="el-icon-search" v-on:click="openFileDialog('sql_path')"></el-button>
      </el-input>
    </el-form-item>
    <el-form-item label="SQL结构">
      <el-table
        ref="multipleTable"
        :data="sql_json_arr"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="dbName" label="库名" width="100"></el-table-column>
        <el-table-column prop="tableName" label="表名" width="200"></el-table-column>
        <el-table-column prop="sql" label="sql" width="800"></el-table-column>
      </el-table>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <!-- <el-button>反转</el-button> -->
    </el-form-item>
    <el-alert
      title="库名格式 : T_DB_NAME ; 表名格式 : T_TABLE_NAME ; 字段格式 : cloum_name ; sql.json 必须使用MySqlWorkbench 生成 ."
      type="warning"
      :closable="false"
    ></el-alert>
  </el-form>
</template>

<script>
const path = require("path");
import { getKeyPath } from "@/template/java";
import { createModelsFile, getSqlJSON } from "@/template/javaModels";
import { ProjectModel } from "@/models/projectModel";
export default {
  data() {
    return {
      sql_json_arr: [],
      project: [],
      build_path: [],
      build_path_vo: [],
      form: new ProjectModel({
        project_path: "",
        sql_path: "",
        build_path: "",
        tableArr: [],
        build_path_vo: ""
      })
    };
  },
  methods: {
    onSubmit() {
      createModelsFile(this.form);
      this.$notify({
        title: "成功",
        message: "操作成功",
        type: "success",
        duration: 2000
      });
    },
    handleSelectionChange(val) {
      this.form.tableArr = val;
    },
    // 加载构建记录
    loadList: function() {
      var that = this;
      this.$db.project.find({}, function(err, docs) {
        docs.forEach(function(ele) {
          ele.build_path = ele.build_path
            ? path.join(ele.build_path, ele.project_name)
            : "";
        });
        that.project = docs;
      });
    },
    openDialog(name) {
      let filePaths = this.$electron.remote.dialog.showOpenDialog({
        properties: ["openDirectory"]
      });
      if (filePaths && filePaths.length > 0) {
        this.form[name] = filePaths[0];
      }
    },
    openFileDialog(name) {
      let filePaths = this.$electron.remote.dialog.showOpenDialog({
        properties: ["openFile"],
        filters: [{ name: "Sql", extensions: ["sql"] }]
      });
      if (filePaths && filePaths.length > 0) {
        this.form[name] = filePaths[0];
        let sql_json = getSqlJSON(filePaths[0]);
        this.sql_json_arr = sql_json;
        this.$refs.multipleTable.toggleAllSelection();
      }
    },
    change_build_path() {
      // 读取文件 获取pojo 内容
      this.build_path = getKeyPath(this.form.project_path, "pojo");
      this.build_path_vo = getKeyPath(this.form.project_path, "vo");
    }
  },
  mounted() {
    this.loadList();
  }
};
</script>

<style>
</style>
