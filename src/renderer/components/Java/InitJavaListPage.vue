<template>
  <div>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column fixed prop="project_name" label="项目名称" width="100"></el-table-column>
      <el-table-column prop="build_path" label="构建地址" width="300"></el-table-column>
      <el-table-column prop="desc" label="简介" width="300"></el-table-column>
      <el-table-column
        prop="input_date"
        label="创建时间"
        width="180"
        :formatter="formatter_create_date"
      ></el-table-column>
      <el-table-column
        prop="update_date"
        label="更新时间"
        width="180"
        :formatter="formatter_update_date"
      ></el-table-column>
      <el-table-column fixed="right" label="操作" width="250">
        <template slot-scope="scope">
          <el-button @click="detailProject(scope.row)" type="text" size="small">查看</el-button>
          <el-button @click="updateProject(scope.row)" type="text" size="small">更新项目结构</el-button>
          <el-button @click="editProject(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="removeProject(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 20px">
      <el-button @click="removeAll()">删除构建历史</el-button>
      <el-button @click="initProject()">新建构建</el-button>
      <el-button @click="dialogFormVisible = true">导入本地构建</el-button>
    </div>
    <el-dialog title="导入本地构建" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="JSON地址" prop="dir_structure_path">
          <el-input
            placeholder="请选择项目下dir_structure.json文件"
            v-model="form.dir_structure_path"
            :disabled="true"
            class="input-with-select"
          >
            <el-button slot="append" icon="el-icon-search" v-on:click="openFileDialog"></el-button>
          </el-input>
        </el-form-item>
        <el-alert title="请选择项目下名称为 dir_structure.json 的文件" type="warning" :closable="false"></el-alert>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getJsonFromPath, updateProjectDirJson } from "@/template/java";
export default {
  data() {
    return {
      dialogFormVisible: false,
      form: {
        name: ""
      },
      tableData: []
    };
  },
  methods: {
    openFileDialog(name) {
      let filePaths = this.$electron.remote.dialog.showOpenDialog({
        properties: ["openFile"],
        filters: [{ name: "Json", extensions: ["json"] }]
      });
      if (filePaths && filePaths.length > 0) {
        this.form["dir_structure_path"] = filePaths[0];
        let json = getJsonFromPath(filePaths[0]);
        json.formData.update_date = new Date().getTime();
        this.$db.project.insert(json.formData);
        this.loadList();
        this.dialogFormVisible = false;
        this.$notify({
          title: "成功",
          type: "success",
          message: "导入成功!"
        });
      }
    },
    formatter_create_date: function(row, column, cellValue) {
      return new Date(cellValue).Format("yyyy-MM-dd hh:mm:ss");
    },
    formatter_update_date: function(row, column, cellValue) {
      return cellValue ? new Date(cellValue).Format("yyyy-MM-dd hh:mm:ss") : "";
    },
    // 加载构建记录
    loadList: function() {
      var that = this;
      this.$db.project.find({}, function(err, docs) {
        that.tableData = docs;
      });
    },
    // 删除所有构建记录
    removeAll: function() {
      var that = this;
      this.$db.project.remove({}, { multi: true }, (err, numRemoved) => {
        that.tableData = [];
        this.$notify({
          title: "成功",
          message: "删除成功",
          type: "success",
          duration: 2000
        });
      });
    },
    // 删除项目
    removeProject: function(row) {
      var that = this;
      this.$db.project.remove(
        { _id: row._id },
        { multi: true },
        (err, numRemoved) => {
          this.loadList();
          this.$notify({
            title: "成功",
            message: "删除成功",
            type: "success",
            duration: 2000
          });
        }
      );
    },
    // 跳转新建
    initProject: function() {
      this.$router.push({ path: "InitJavaPage" });
    },
    // 重新编辑
    editProject: function(row) {
      this.$router.push({ path: "InitJavaPage?id=" + row._id });
    },
    detailProject: function(row) {
      this.$router.push({
        path: "InitJavaPage?id=" + row._id + "&detail=" + "true"
      });
    },
    // 更新项目目录结构JSON
    updateProject: function(row) {
      console.log(row)
      row.update_date = new Date().getTime();
      updateProjectDirJson(row);
      this.$db.project.update({ _id: row._id }, row);
      this.$notify({
        title: "成功",
        type: "success",
        message: "更新成功!"
      });
    }
  },
  mounted() {
    this.loadList();
  }
};
</script>

<style>
</style>
