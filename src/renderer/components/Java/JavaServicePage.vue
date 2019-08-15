<template>
  <el-form ref="form" :model="form" label-width="120px">
    <el-form-item label="产出项目">
      <el-select v-model="form.project_path" placeholder="请选择项目地址" @change="change_build_path">
        <el-option
          v-for="item in project"
          :key="item._id"
          :label="item.project_name"
          :value="item.build_path_text"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="POJO">
      <el-cascader
        :options="pojo_path"
        placeholder="试试搜索：文件名"
        filterable
        v-model="form.pojo_path"
        style="width: 60%"
        @change="changePojo"
      ></el-cascader>
    </el-form-item>
    <el-form-item label="VO">
      <el-cascader
        :options="vo_path"
        placeholder="试试搜索：文件名"
        filterable
        v-model="form.vo_path"
        style="width: 60%"
      ></el-cascader>
    </el-form-item>

    <el-form-item label="Service目录">
      <el-select v-model="form.service_path" filterable placeholder="请选择" style="width: 60%">
        <el-option
          v-for="item in service_path"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="ServiceImpl目录">
      <el-select v-model="form.service_impl_path" filterable placeholder="请选择" style="width: 60%">
        <el-option
          v-for="item in service_impl_path"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="Controller目录">
      <el-select
        v-model="form.controller_path"
        clearable
        filterable
        placeholder="请选择"
        style="width: 60%"
      >
        <el-option
          v-for="item in controller_path"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="Mapper目录">
      <el-radio-group v-model="form.mapper_path">
        <div v-for="item in mapper_path" style="margin-bottom: 10px;" :key="item.id">
          <el-radio :label="item.value" :key="item.value" border>{{item.label}}</el-radio>
        </div>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="单元测试目录">
      <el-select v-model="form.e2e_path" clearable filterable placeholder="请选择" style="width: 60%">
        <el-option
          v-for="item in e2e_path"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="必填字段">
      <el-select
        v-model="form.table_col"
        clearable
        filterable
        multiple
        placeholder="请选择"
        style="width: 60%"
      >
        <el-option
          v-for="(item,index) in table_models.table_col_arr"
          :key="index"
          :label="item.col + '  [' + item.name + ']'"
          :value="item.col"
        ></el-option>
      </el-select>
    </el-form-item>
  
    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>

    <el-alert
      title="除了controller&单元测试目录非必填，其他选项必须录入 ."
      type="warning"
      :closable="false"
    ></el-alert>
  </el-form>
</template>

<script>
const path = require("path");
import { updateProjectDirJson } from "@/template/java";
import JavaService from "@/template/JavaService";
import { ProjectService } from "@/models/ProjectService";
import FileOpt from "@/models/FileOpt";
export default {
  data() {
    let that = this;
    return {
      project: [],
      pojo_path: [],
      vo_path: [],
      service_impl_path: [],
      mapper_path: [],
      service_path: [],
      controller_path: [],
      e2e_path: [],
      table_models: {},
      form: new ProjectService({
        service_path: "",
        mapper_path: "",
        pojo_path: [],
        vo_path: [],
        table_name: "",
        table_col: [],
        table_col_arr: [],
        service_impl_path: "",
        project_path: "",
        controller_path: "",
        e2e_path: ""
      })
    };
  },
  methods: {
    openFileDialog(str) {
      let filePaths = this.$electron.remote.dialog.showOpenDialog({
        properties: ["openFile"],
        filters: [{ name: "Java", extensions: ["java"] }]
      });
      if (filePaths && filePaths.length > 0) {
        console.log(filePaths[0]);
        this.form[str] = filePaths[0];
      }
    },
    onSubmit() {
      if (this.form.pojo_path.length == 0) {
        this.$message({
          message: "缺少pojo 参数",
          type: "warning"
        });
        return;
      }
      if (this.form.vo_path.length == 0) {
        this.$message({
          message: "缺少vo 参数",
          type: "warning"
        });
        return;
      }
      // 过滤非空组合
      if(this.form.table_col && this.form.table_col.length > 0){
        this.form.table_col_arr = this.table_models.table_col_arr.filter(ele => {
          return this.form.table_col.indexOf(ele.col) >= 0
        })
      }
      new JavaService(this.form).create();
      // 更新文件结构
      let row = this.project.find(ele=>{
        return ele.build_path_text == this.form.project_path
      })
      row.update_date = new Date().getTime();
      updateProjectDirJson(row);
      this.$notify({
        title: "成功",
        message: "操作成功",
        type: "success",
        duration: 2000
      });
    },
    loadList: function() {
      var that = this;
      this.$db.project.find({}, function(err, docs) {
        docs.forEach(function(ele) {
          ele.build_path_text = ele.build_path
            ? path.join(ele.build_path, ele.project_name)
            : "";
        });
        that.project = docs;
      });
    },
    changePojo() {
      this.form.table_col_arr = [];
      // 实体类解析
      this.table_models = FileOpt.getModelByPojoPath(this.form.pojo_path[1]);
      this.form.table_name = this.table_models.table_name;
    },
    change_build_path() {
      this.form = new ProjectService({
        service_path: "",
        mapper_path: "",
        pojo_path: [],
        vo_path: [],
        table_name: "",
        table_col:[],
        table_col_arr: [],
        service_impl_path: "",
        controller_path: "",
        e2e_path: "",
        project_path: this.form.project_path
      });
      this.project.forEach((ele, index) => {
        if (this.form.project_path === ele.build_path_text) {
          this.form.project_name = ele.project_name;
          return;
        }
      });
      // service生成地址
      const servicePath = new FileOpt().findFolderByKey(
        this.form.project_path,
        "service"
      );
      this.service_path = servicePath.filter((ele, index) => {
        return ele.label.indexOf("api") >= 0;
      });
      this.service_impl_path = servicePath.filter((ele, index) => {
        return ele.label.indexOf("api") < 0;
      });
      // xml映射地址
      this.mapper_path = new FileOpt().findFolderByKey(
        this.form.project_path,
        "mapper"
      );
      // controller 地址
      this.controller_path = new FileOpt().findFolderByKey(
        this.form.project_path,
        "controller"
      );
      // 单元测试地址
      this.e2e_path = new FileOpt()
        .findFolderByKey(this.form.project_path, this.form.project_name)
        .filter(item => {
          // 过滤只要test目录下的
          return item.value.indexOf("test") >= 0;
        });
      // pojo vo 地址
      this.pojo_path = new FileOpt()
        .findFolderByKey(this.form.project_path, "pojo")
        .map(item => {
          return {
            value: item.value,
            label: item.label,
            children: (item.fileList || []).map(ele => {
              return {
                label: ele.fileName,
                value: ele.path
              };
            })
          };
        });
      this.vo_path = new FileOpt()
        .findFolderByKey(this.form.project_path, "vo")
        .map(item => {
          return {
            value: item.value,
            label: item.label,
            children: (item.fileList || []).map(ele => {
              return {
                label: ele.fileName,
                value: ele.path
              };
            })
          };
        });
    }
  },
  created() {
    this.loadList();
  }
};
</script>

<style>
</style>
