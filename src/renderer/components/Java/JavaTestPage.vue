<template>
  <div>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column fixed prop="project_name" label="项目名称" width="100"></el-table-column>
      <el-table-column fixed prop="desc" label="项目描述" width="100"></el-table-column>
      <el-table-column prop="project_path" label="项目地址" width="200"></el-table-column>
      <el-table-column label="构建地址" width="300">
        <template slot-scope="scope">
          <el-tag
            type="success"
            v-for="(item,index) in scope.row.build_path"
            :key="index"
          >{{ item | buildPathFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="test_step" label="执行步骤" width="120">
        <template slot-scope="scope">
          <el-tag type="success">{{ scope.row.test_step | stepFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="create_date"
        label="创建时间"
        width="180"
        :formatter="formatter_create_date"
      ></el-table-column>
      <el-table-column
        prop="update_date"
        label="更新时间"
        width="180"
        :formatter="formatter_create_date"
      ></el-table-column>
      <el-table-column fixed="right" label="操作" width="200">
        <template slot-scope="scope">
          <el-button @click="editTest(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="removeTest(scope.row)" type="text" size="small">删除</el-button>
          <el-button @click="exportTest(scope.row)" type="text" size="small">导出JSON</el-button>
          <el-button @click="exportHtml(scope.row)" type="text" size="small">导出Html</el-button>
          <el-button @click="exportWord(scope.row)" type="text" size="small">导出Word</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 20px">
      <el-button @click="removeAll()">删除历史</el-button>
      <el-button @click="initTest()">新建测试</el-button>
      <el-button @click="dialogFormVisible = true">导入本地JSON</el-button>
    </div>
    <el-dialog title="导入本地" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="JSON地址" prop="test_path_json">
          <el-input
            placeholder="请选择项目下testApi.json文件"
            v-model="form.test_path_json"
            :disabled="true"
            class="input-with-select"
          >
            <el-button slot="append" icon="el-icon-search" v-on:click="openFileDialog"></el-button>
          </el-input>
        </el-form-item>
        <el-alert title="请选择项目下名称为 testApi.json 的文件" type="warning" :closable="false"></el-alert>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getJsonFromPath, exportWord, fs, path } from "@/template/java";

export default {
  filters: {
    buildPathFilter(build_path) {
      return build_path.slice(build_path.indexOf("com"), build_path.length);
    },
    stepFilter(step) {
      const stepList = {
        1: "提交项目信息",
        2: "生成基础文档",
        3: "自定义流程"
      };
      return stepList[step];
    }
  },
  data() {
    return {
      api_path_name: "testApi.json",
      api_html_path_name: "testApi.html",
      dialogFormVisible: false,
      form: {
        name: ""
      },
      tableData: []
    };
  },
  methods: {
    openFileDialog() {
      let filePaths = this.$electron.remote.dialog.showOpenDialog({
        properties: ["openFile"],
        filters: [{ name: "Json", extensions: ["json"] }]
      });
      if (filePaths && filePaths.length > 0) {
        let json = getJsonFromPath(filePaths[0]);
        delete json._id;
        json.update_date = new Date().getTime();
        this.$db.test.insert(json);
        this.loadList();
        this.dialogFormVisible = false;
        this.$notify({
          title: "成功",
          type: "success",
          message: "导入成功!"
        });
      }
    },
    formatter_create_date(row, column, cellValue) {
      return cellValue ? new Date(cellValue).Format("yyyy-MM-dd hh:mm:ss") : "";
    },
    // 加载构建记录
    loadList() {
      var that = this;
      this.$db.test.find({}, function(err, docs) {
        that.tableData = docs;
      });
    },
    exportTest(row) {
      fs.writeFileSync(
        path.join(row.project_path, this.api_path_name),
        JSON.stringify(row)
      );
      this.$notify({
        title: "成功",
        type: "success",
        message: "导出成功!"
      });
    },
    exportHtml(row) {
      const script_pre = "<";
      let html = `<!DOCTYPE html>
          <html>
            <head>
              <meta charset="utf-8">
              <meta name="viewport" content="width=device-width,initial-scale=1.0">
              <title>API</title>
              <style>
                /* 简易数据表格-格边框 */
                .m-table{table-layout:fixed;width:100%;line-height:1.5;}
                .m-table th,.m-table td{padding:10px;border:1px solid #ddd;}
                .m-table th{font-weight:bold;}
                .m-table tbody tr:nth-child(2n){background:#fafafa;}
                .m-table tbody tr:hover{background:#f0f0f0;}
                .m-table .cola{width:100px;}
                .m-table .colb{width:200px;}
              </style>
              <!-- 引入样式 -->
              <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">

            </head>
            <body>
              <div id="app" style="width:100%">
                <el-table
                  :data="tableData"
                  border
                  stripe
                  style="width: 100%;margin-bottom: 20px;">
                    <el-table-column type="expand">
                      <template slot-scope="props">
                        <el-table
                          :data="props.row.result"
                          border
                          style="width: 100%;margin-bottom: 20px;">
                            <el-table-column type="expand">
                              <template slot-scope="scope">
                                <el-table
                                  :data="scope.row.ApiImplicitParamsText"
                                  border>
                                    <el-table-column
                                      label="参数名"
                                      prop="name"
                                      width="100">
                                    </el-table-column>
                                    <el-table-column
                                      label="参数值"
                                      width="150">
                                      <template slot-scope="params">
                                        <el-input
                                          size="medium"
                                          :placeholder="params.row.value"
                                          v-model="params.row.data">
                                        </el-input>
                                      </template>
                                    </el-table-column>
                                    <el-table-column
                                      label="参数描述"
                                      prop="value"
                                      width="120">
                                    </el-table-column>
                                    <el-table-column
                                      label="数据类型"
                                      prop="dataType"
                                      width="80">
                                    </el-table-column>
                                    <el-table-column
                                      label="是否必传"
                                      prop="required"
                                      width="80">
                                    </el-table-column>
                                </el-table>
                              </template>
                            </el-table-column>
                            <el-table-column
                              label="Api名称"
                              width="150">
                              <template slot-scope="scope">
                                {{ scope.row.ApiOperationText }}
                              </template>
                            </el-table-column>
                            <el-table-column
                              label="接口名"
                              prop="RequestMapping"
                              width="250">
                              <template slot-scope="scope">
                                {{ props.row.RequestMappingText }}{{ scope.row.RequestMappingText }}
                              </template>
                            </el-table-column>
                            <el-table-column
                              label="接口方式"
                              prop="RequestMapping"
                              width="100">
                              <template slot-scope="scope">
                                {{ scope.row.RequestMappingType}}
                              </template>
                            </el-table-column>
                        </el-table>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="className"
                      label="controller"
                      width="200">
                    </el-table-column>
                    <el-table-column
                      label="controller名称"
                      prop="ApiText"
                      width="200">
                    </el-table-column>
                    <el-table-column
                      label="请求接口"
                      prop="RequestMappingText"
                      width="300">
                    </el-table-column>
                    <el-table-column
                      label="接口数量"
                      width="100">
                      <template slot-scope="scope">
                        {{ scope.row.result.length}}
                      </template>
                    </el-table-column>
                  </el-table>
                
              </div>
              <!-- built files will be auto injected -->
              <!-- 引入组件库 -->
              <script src="https://cdn.jsdelivr.net/npm/vue">${script_pre}/script>
              <script src="https://unpkg.com/element-ui/lib/index.js">${script_pre}/script>
              <script>
              var app = new Vue({
                el: '#app',
                data: {
                  tableData: ${JSON.stringify(row.apiJsonList)}
                }
              })
              ${script_pre}/script>
            </body>
          </html>`;

      fs.writeFileSync(
        path.join(row.project_path, this.api_html_path_name),
        html
      );
      this.$notify({
        title: "成功",
        type: "success",
        message: "导出成功!"
      });
    },
    exportWord(row) {
      console.log(row);
      let controller_table = [
        [
          {
            val: "Controller",
            opts: {
              bold: true,
              shd: {
                fill: "EDEDED"
              },
              vAlign: "baseline"
            }
          },
          {
            val: "Controller名称",
            opts: {
              bold: true,
              shd: {
                fill: "EDEDED"
              }
            }
          },
          {
            val: "请求接口",
            opts: {
              bold: true,
              shd: {
                fill: "EDEDED"
              }
            }
          }
        ]
      ];
      let api_table = [
        [
          {
            val: "API名称",
            opts: {
              bold: true,
              shd: {
                fill: "EDEDED"
              },
              vAlign: "baseline"
            }
          },
          {
            val: "接口名",
            opts: {
              bold: true,
              shd: {
                fill: "EDEDED"
              }
            }
          },
          {
            val: "请求方式",
            opts: {
              bold: true,
              shd: {
                fill: "EDEDED"
              }
            }
          }
        ]
      ];

      const tableStyle = {
        tableColWidth: 4261,
        borders: true,
        tableSize: 18,
        tableColor: "ada",
        tableAlign: "left"
      };

      for (const v of row.apiJsonList) {
        let table_row = [];
        table_row.push(v.className, v.ApiText, v.RequestMappingText);
        controller_table.push(table_row);

        for (const api of v.result) {
          let api_row = [];
          api_row.push(
            api.ApiOperationText,
            v.RequestMappingText + api.RequestMappingText,
            api.RequestMappingType
          );
          api_table.push(api_row);
        }
      }

      var data = [
        {
          type: "text",
          val: "  API概览 ",
          opt: { bold: true, font_size: 32 }
        },
        {
          type: "linebreak"
        },
        {
          type: "table",
          val: controller_table,
          opt: tableStyle
        },
        {
          type: "pagebreak"
        },
        {
          type: "text",
          val: "  API详情 ",
          opt: { bold: true, font_size: 32 }
        }
        // {
        //   type: "linebreak"
        // },
        // {
        //     type: "table",
        //     val: api_table,
        //     opt: tableStyle
        // },
        // {
        //     type: "pagebreak"
        // },
      ];
      for (const v of row.apiJsonList) {
        data.push({
          type: "linebreak"
        });
        data.push([
          { backline: "EDEDED" },
          {
            type: "text",
            val: v.className + "[ " + v.ApiText + " ]",
            opt: { bold: true, pStyleDef: "Heading1" }
          }
        ]);
        for (const api of v.result) {
          data.push({
            type: "linebreak"
          });
          data.push([
            { backline: "EDEDED" },
            {
              type: "text",
              val: v.RequestMappingText + api.RequestMappingText,
              opt: { bold: true, pStyleDef: "Normal" }
            }
          ]);
          data.push({
            type: "text",
            val: "接口描述",
            opt: { bold: true, pStyleDef: "Normal" }
          });
          data.push({
            type: "text",
            val: api.ApiOperationText,
            opt: { pStyleDef: "Normal" }
          });
          data.push({
            type: "text",
            val: "入参描述",
            opt: { bold: true, pStyleDef: "Normal" }
          });
          let paramsTable = [
            [
              {
                val: "参数名",
                opts: {
                  bold: true,
                  shd: {
                    fill: "EDEDED"
                  },
                  vAlign: "baseline"
                }
              },
              {
                val: "参数描述",
                opts: {
                  bold: true,
                  shd: {
                    fill: "EDEDED"
                  }
                }
              },
              {
                val: "参数类型",
                opts: {
                  bold: true,
                  shd: {
                    fill: "EDEDED"
                  }
                }
              },
              {
                val: "必传",
                opts: {
                  bold: true,
                  shd: {
                    fill: "EDEDED"
                  }
                }
              }
            ]
          ];
          for (const params of api.ApiImplicitParamsText) {
            let params_row = [];
            params_row.push(
              params.name,
              params.value,
              params.dataType,
              params.required
            );
            paramsTable.push(params_row);
          }
          data.push({
            type: "table",
            val: paramsTable,
            opt: tableStyle
          });
        }
      }
      exportWord(path.join(row.project_path, "api.docx"), data);
    },
    // 删除所有记录
    removeAll() {
      this.$db.test.remove({}, { multi: true }, (err, numRemoved) => {
        this.tableData = [];
        this.$notify({
          title: "成功",
          type: "success",
          message: "删除成功!"
        });
      });
    },
    // 删除项目
    removeTest(row) {
      this.$db.test.remove(
        { _id: row._id },
        { multi: true },
        (err, numRemoved) => {
          this.loadList();
          this.$notify({
            title: "成功",
            type: "success",
            message: "删除成功!"
          });
        }
      );
    },
    // 跳转新建
    initTest() {
      this.$router.push({ path: "JavaTestProject" });
    },
    // 重新编辑
    editTest(row) {
      switch (row.test_step) {
        case 1:
          this.$router.push({ path: "JavaTestProject?id=" + row._id });
          break;
        case 2:
          this.$router.push({ path: "JavaTestApi?id=" + row._id });
          break;
        case 3:
          this.$router.push({ path: "JavaTestCustom?id=" + row._id });
          break;
        default:
          break;
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
