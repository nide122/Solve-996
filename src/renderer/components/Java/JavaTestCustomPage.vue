<template>
  <el-row style="max-width: 900px;margin: 0 auto">
    <el-steps :active="form.test_step" align-center style="margin-bottom: 20px">
      <el-step title="项目信息" description="采集项目信息"></el-step>
      <el-step title="基础文档" description="根据服务器地址生成基础接口文档"></el-step>
      <el-step title="自定义流程" description="自定义参数与流程"></el-step>
    </el-steps>
    <el-tabs v-model="activeName" style="max-width: 850px;margin: 40px auto;margin-bottom: 20px;">
      <el-tab-pane label="接口设置" name="0">
        <el-table :data="tableData" border stripe style="width: 100%;margin-bottom: 20px;">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-table :data="props.row.result" border style="width: 100%;margin-bottom: 20px;">
                <el-table-column type="expand">
                  <template slot-scope="scope">
                    <el-table :data="scope.row.ApiImplicitParamsText" border>
                      <el-table-column label="参数名" prop="name" width="100"></el-table-column>
                      <el-table-column label="参数值" width="150">
                        <template slot-scope="params">
                          <el-input
                            size="medium"
                            :placeholder="params.row.value"
                            v-model="params.row.data"
                          ></el-input>
                        </template>
                      </el-table-column>
                      <el-table-column label="参数描述" prop="value" width="120"></el-table-column>
                      <el-table-column label="数据类型" prop="dataType" width="80"></el-table-column>
                      <el-table-column label="是否必传" prop="required" width="80"></el-table-column>
                      <el-table-column label="操作" width="100">
                        <template slot-scope="params">
                          <el-button
                            @click="removeParam(params.row, scope.row)"
                            type="text"
                            size="small"
                          >删除</el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </template>
                </el-table-column>
                <el-table-column label="Api名称" width="150">
                  <template slot-scope="scope">{{ scope.row.ApiOperationText }}</template>
                </el-table-column>
                <el-table-column label="接口名" prop="RequestMapping" width="250">
                  <template
                    slot-scope="scope"
                  >{{ props.row.RequestMappingText }}{{ scope.row.RequestMappingText }}</template>
                </el-table-column>
                <el-table-column label="接口方式" prop="RequestMapping" width="100">
                  <template slot-scope="scope">{{ scope.row.RequestMappingType}}</template>
                </el-table-column>
                <el-table-column label="操作" width="150">
                  <template slot-scope="scope">
                    <el-button @click="addParam(scope.row, props.row)" type="text" size="small">增加参数</el-button>
                    <el-button @click="execAjax(scope.row, props.row)" type="text" size="small">执行测试</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>
          <el-table-column prop="className" label="controller" width="200"></el-table-column>
          <el-table-column label="controller名称" prop="ApiText" width="200"></el-table-column>
          <el-table-column label="请求接口" prop="RequestMappingText" width="300"></el-table-column>
          <el-table-column label="接口数量" width="100">
            <template slot-scope="scope">{{ scope.row.result.length}}</template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="流程设置" name="1"></el-tab-pane>
    </el-tabs>
    <el-form ref="form" :model="form" label-width="80px" style="max-width: 850px;margin: 40px auto">
      <el-form-item label="过滤接口" v-if="activeName==0">
        <el-input
          v-model="form.filter_apis"
          placeholder="以逗号分割 例如:/service/users/logout,/service/users/login"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="prevStep">上一步</el-button>
        <el-button @click="execAllApi" v-if="activeName==0">执行接口全部测试</el-button>
        <el-button @click="addTestFlow" v-if="activeName==1">新增测试流程</el-button>
      </el-form-item>
    </el-form>

    <el-card class="box-card" style="margin-bottom: 20px;max-width: 850px;margin: 40px auto">
      <div slot="header" class="clearfix">
        <span>执行日志</span>
        <el-button style="float: right; " type="text" @click="clearLogs">清除日志</el-button>
        <el-switch
          style="float: right; margin-top: 10px; padding-right: 10px;"
          v-model="logsType"
          active-text="成功日志"
          inactive-text="失败日志"
        ></el-switch>
      </div>
      <div v-for="(log,index) in logs" class="text item" :key="index">
        <div
          :class="'logs-color-box bg-' + log.type "
          v-if="logsType ? log.type == 'success' : ['danger','warning'].includes(log.type)  "
        >
          {{log.url}}
          <br>
          {{ log.params }}
          <br>
          {{ log.log }}
        </div>
      </div>
    </el-card>

    <el-dialog title="增加参数" :visible.sync="dialogFormVisible">
      <el-form :model="paramObj" label-width="80px">
        <el-form-item label="参数名">
          <el-input v-model="paramObj.name"></el-input>
        </el-form-item>
        <el-form-item label="参数值">
          <el-input v-model="paramObj.data"></el-input>
        </el-form-item>
        <el-form-item label="参数描述">
          <el-input v-model="paramObj.value"></el-input>
        </el-form-item>
        <el-form-item label="参数类型">
          <el-input v-model="paramObj.dataType"></el-input>
        </el-form-item>
        <el-form-item label="是否必传">
          <el-switch v-model="paramObj.required"></el-switch>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onAddParam">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </el-row>
</template>

<script>
const path = require("path");
import * as api from "@/utils/request";
import * as javaTest from "../../template/javaTest";
import { getKeyPath } from "../../template/java";

export default {
  data() {
    return {
      dialogFormVisible: false,
      paramObj: {
        name: "",
        value: "",
        data: "",
        dataType: "",
        required: false
      },
      activeName: "0",
      logsType: false,
      logs: [],
      addParamRow: {},
      apiControllerData: {},
      tableData: [],
      form: {
        test_step: 3,
        project_name: "",
        api_token_name: "",
        api_token_value: "",
        project_path: "",
        filter_apis: "",
        build_path: []
      }
    };
  },
  methods: {
    addTestFlow() {
      this.$notify.error({
        title: "错误",
        message: "此服务暂未提供!"
      });
    },
    clearLogs() {
      this.logs = [];
    },
    onSubmit() {
      this.form.apiJsonList = this.tableData;
      if (this.$route.query.id) {
        this.form.update_date = new Date();
        this.$db.test.update({ _id: this.$route.query.id }, this.form);
      } else {
        this.form.create_date = new Date();
        this.$db.test.insert(this.form);
      }
      // this.$router.push({ path: 'testJava' })
    },
    prevStep() {
      this.$router.push({ path: "JavaTestApi?id=" + this.$route.query.id });
    },
    addParam(row, parentRow) {
      this.addParamRow = row;
      this.paramObj = {
        name: "",
        value: "",
        data: "",
        dataType: "",
        required: false
      };
      this.dialogFormVisible = true;
    },
    removeParam(row, parentRow) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          parentRow.ApiImplicitParamsText.splice(
            parentRow.ApiImplicitParamsText.indexOf(row),
            1
          );
          this.onSubmit();
          this.$notify({
            title: "成功",
            type: "success",
            message: "删除成功!"
          });
        })
        .catch(() => {});
    },
    onAddParam() {
      if (this.addParamRow.ApiImplicitParamsText) {
        this.paramObj.required = this.paramObj.required + "";
        this.addParamRow.ApiImplicitParamsText.push(this.paramObj);
      }
      this.dialogFormVisible = false;
      this.onSubmit();
      this.$notify({
        title: "成功",
        type: "success",
        message: "新增成功!"
      });
    },
    execAjax(rows, parentRow) {
      if (rows.RequestMappingType) {
        let url =
          this.form.api_path +
          parentRow.RequestMappingText +
          rows.RequestMappingText;
        switch (rows.RequestMappingType) {
          case "POST":
            console.log();
            let params = {};
            if (
              rows.ApiImplicitParamsText &&
              rows.ApiImplicitParamsText.length > 0
            )
              for (const param of rows.ApiImplicitParamsText) {
                params[param.name] = param.data;
              }
            params[this.form.api_token_name] = this.form.api_token_value;
            params.token_key =this.form.api_token_name
            console.log(api)
            api.apiPost(url, params).then(data => {
              this.logs.push({
                type:
                  data.success == 1
                    ? "success"
                    : data.success == 1000
                    ? "danger"
                    : "warning",
                url: rows.RequestMappingType + " : " + url,
                params: "传参 : " + JSON.stringify(params),
                log: "返回消息 : " + JSON.stringify(data.message),
                data: JSON.stringify(data.object)
              });
            });
            break;
          default:
            break;
        }
      }
    },
    execAllApi() {
      for (const v of this.tableData) {
        for (const api of v.result) {
          let flag = true;
          if (this.form.filter_apis) {
            for (const filter_api of this.form.filter_apis.split(",")) {
              if (
                filter_api ===
                v.RequestMappingText + api.RequestMappingText
              ) {
                flag = false;
                break;
              }
            }
          }
          if (flag) {
            this.execAjax(api, v);
          }
        }
      }
    },
    loadDetail() {
      if (this.$route.query.id) {
        this.$db.test.findOne(
          { _id: this.$route.query.id },
          (err, newDoc) => {
            this.form = newDoc;
            this.form.test_step = 3;
            this.tableData = this.form.apiJsonList;
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
