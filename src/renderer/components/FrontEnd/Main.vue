<template>
  <el-container class="g-bd">
    <!-- 头部 -->
    <el-header class="g-hd" height="60px">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-tabs
            v-model="activeProjectIndex"
            closable
            @tab-click="changeProject"
            @tab-remove="removeProject"
            style="padding-top:6px;"
          >
            <el-tab-pane
              v-for="item in projectTableList"
              :key="item._id"
              :label="item.project_name"
              :name="item._id"
            ></el-tab-pane>
          </el-tabs>
        </el-col>
        <el-col :span="8">
          <el-form :inline="true" size="mini" style="padding-top:15px;">
            <el-form-item label="UI库">
              <el-select v-model="ui_library_index" filterable placeholder="请选择UI库">
                <el-option
                  v-for="(item,index) in ui_library"
                  :key="item.value"
                  :label="item.label"
                  :value="index"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="8">
          <el-row style="line-height:60px;">
            <!-- <el-button round size="mini" icon="el-icon-s-tools"></el-button> -->
            <el-button round size="mini" icon="el-icon-plus" @click="importProject">导入项目</el-button>
            <!-- <el-button round size="mini" icon="el-icon-more"></el-button> -->
            <!-- <el-button round size="mini" icon="el-icon-minus"></el-button> -->
            <!-- <el-button round size="mini" icon="el-icon-delete"></el-button> -->
            <el-button type="danger" round size="mini" icon="el-icon-share" @click="exportCode">导出代码</el-button>
          </el-row>
        </el-col>
      </el-row>
    </el-header>
    <!-- 核心区域 -->
    <el-container style="position: relative;">
      <el-aside width="250px" class="g-sd">
        <el-collapse v-model="sd_collapse_active" accordion>
          <el-collapse-item title="项目目录" name="1">
            <el-tree
              :data="project_file_structure"
              :expand-on-click-node="false"
              node-key="path"
              :default-expanded-keys="[1]"
              :props="defaultProps"
            >
              <span class="custom-tree-node" slot-scope="{ node, data }">
                <span>{{ node.label }}</span>
                <span>
                  <el-dropdown>
                    <span class="el-dropdown-link">
                      操作
                      <i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item @click="() => append(data)">添加子页面</el-dropdown-item>
                      <el-dropdown-item>移动到</el-dropdown-item>
                      <el-dropdown-item @click="() => remove(node, data)" divided>删除</el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </span>
              </span>
            </el-tree>
          </el-collapse-item>
          <el-collapse-item title="UI组件" name="2">
            <el-select
              v-model="ui_library_type_index"
              filterable
              placeholder="请选择UI组件"
              size="mini"
              style="margin-bottom:5px"
            >
              <el-option
                v-for="(item,index) in (ui_library[ui_library_index].children || [])"
                :key="item.value"
                :label="item.label"
                :value="index"
              ></el-option>
            </el-select>
            <el-tag
              v-for="item in (ui_library[ui_library_index].children ? ui_library[ui_library_index].children[ui_library_type_index].children:[])"
              :key="item.id"
              style="margin-bottom:5px;margin-right:5px;"
              @click="addComponent(item)"
            >{{item.label}}</el-tag>
          </el-collapse-item>
          <el-collapse-item title="图标" name="3">
            <div>暂未开启</div>
          </el-collapse-item>
          <el-collapse-item title="模版" name="4">
            <div>暂未开启</div>
          </el-collapse-item>
        </el-collapse>
      </el-aside>
      <el-container>
        <el-container>
          <el-main class="g-mn">
            
            <el-collapse v-model="rsd_collapse_active" accordion>
              <el-collapse-item title="组件code" name="1">
                <CodeEditor
                  ref="pageComponentCode"
                  v-model="page_htmls"
                  mode="text/html"
                  style="width:100%;height:100%;"
                  @input="changeComponent"
                ></CodeEditor>
              </el-collapse-item>
              <el-collapse-item title="属性" name="2">
                <div>暂未开启</div>
              </el-collapse-item>
              <el-collapse-item title="事件" name="3">
                <div>暂未开启</div>
              </el-collapse-item>
            </el-collapse>
            
          </el-main>
          <el-aside width="400px" class="g-sd g-sd-rt">
            <!-- <ul class="g-tools">
              <li @click="edit_code_type = 0">
                <el-link icon="el-icon-menu" :type="edit_code_type == 0 ? 'primary':''">编码模式</el-link>
              </li>
              <li @click="edit_code_type = 1" >
                <el-link icon="el-icon-s-grid" :type="edit_code_type == 1 ? 'primary':''">结构模式</el-link>
              </li>
            </ul> -->
            <div
              class="g-code-box"
              :style="'width:' + form.width + 'px;height:' + form.height + 'px'"
              ref="pageBox"
            >
              <div v-html="page_htmls"></div>
              <!-- <nested-draggable
                :pageList="pageList"
                :onAddAction="onAddAction"
                :avtiveComponentEdit="avtiveComponentEdit"
                :component_drag_end="component_drag_end"
                :active_component_index="active_component_index"
                v-if="edit_code_type == 1"
              ></nested-draggable>
              <page-draggable 
                :pageList="pageList"
                :onAddAction="onAddAction"
                :active_component_index="active_component_index"
                :avtiveComponentEdit="avtiveComponentEdit"
                :component_drag_end="component_drag_end"
                v-if="edit_code_type == 0"></page-draggable> -->
            </div>
          </el-aside>
        </el-container>
        <el-footer class="g-ft" height="40px">
          日志
        </el-footer>
      </el-container>
    </el-container>
    <!-- 导入项目 -->
    <el-dialog title="导入项目" :visible.sync="importDialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="tempForm"
        label-position="left"
        label-width="120px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="项目名称" prop="project_name">
          <el-input v-model="tempForm.project_name" type="text" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="代码库" prop="produce_language_library">
          <el-select
            v-model="tempForm.produce_language_library"
            filterable
            placeholder="请选择代码库"
            @change="change_produce_language_library"
          >
            <el-option disabled value>请选择</el-option>
            <el-option
              v-for="item in produce_language_library"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="width" prop="width">
          <el-input-number v-model="tempForm.width" :min="360" :max="1440" label="px"></el-input-number>
        </el-form-item>
        <el-form-item label="height" prop="height">
          <el-input-number v-model="tempForm.height" :min="360" :max="1440" label="px"></el-input-number>
        </el-form-item>
        <el-form-item label="项目地址" prop="build_path">
          <el-input
            placeholder="请选择项目地址"
            v-model="tempForm.build_path"
            :disabled="true"
            class="input-with-select"
          >
            <el-button slot="append" icon="el-icon-search" v-on:click="openDialog"></el-button>
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="importDialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="importSubmit">提交</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>
<script>
import FrontEndProject from "@/models/FrontEndProject";
import draggable from "vuedraggable";
import uiLibrary from "@/props/uiLibrary";
import produceLanguageLibrary from "@/props/produceLanguageLibrary";
import FileOpt from "@/models/FileOpt";
import Structure from "@/models/Structure";
import * as dom from "@/utils/dom";
import { setTimeout } from "timers";
import CodeEditor from "@/components/CodeEditor";
import nestedDraggable from "@/components/Drag";
import pageDraggable from '@/components/Drag/Page'
const beautify_js = require('js-beautify').js_beautify
const beautify_html = require('js-beautify').html
let idGlobal = 8;

export default {
  filters: {
    formatHtmlFilter(htmlStr) {
      return beautify_html(htmlStr)
    },
    formatStyleFilter(cssStr){
      return `<style>${cssStr}</style>`
    },
    formatJsFilter(jsStr) {
      return beautify_js(jsStr)
    }
  },
  components: {
    draggable,
    nestedDraggable,
    pageDraggable,
    CodeEditor
  },
  data() {
    //选定语言-》选定UI库->画页面原型->数据对接->UI与数据绑定->生成代码
    return {
      // 项目列表
      projectTableList: [],
      // 页面列表
      pageList: [],
      // 激活的项目索引
      activeProjectIndex: null,
      // 导入项目弹出窗口
      importDialogFormVisible: false,
      // 验证条件
      rules: {
        project_name: [{ required: true, message: "必填", trigger: "blur" }],
        produce_language_library: [
          { required: true, message: "必填", trigger: "blur" }
        ],
        width: [{ required: true, message: "必填", trigger: "blur" }],
        height: [{ required: true, message: "必填", trigger: "blur" }],
        build_path: [{ required: true, message: "必填", trigger: "blur" }]
      },
      // 默认激活的面板
      sd_collapse_active: "1",
      rsd_collapse_active: "1",
      // 代码编辑模式 默认编码模式
      edit_code_type: 0 ,
      // 当前组件代码
      page_htmls: "",
      // 页面css
      page_styles: "",
      // 激活编辑的组件索引
      active_component_index: 0,
      // 代码库
      produce_language_library: produceLanguageLibrary.value,
      // UI库
      ui_library: uiLibrary.value,
      // 已选中的UI库
      ui_library_index: 0,
      // 已选中UI库的分类索引
      ui_library_type_index: 0,
      tempForm: this.resetForm(),
      form: new FrontEndProject({
        project_name: "",
        build_path: "",
        produce_language_library: "",
        page_components: [],
        page_events: [],
        ui_library: ""
      }),
      defaultProps: {
        children: "children",
        label: "fileName"
      },
      project_file_structure: []
    };
  },
  methods: {
    addComponent(item){
      this.page_htmls += item.html
      this.page_styles += item.css
      this.$refs.pageComponentCode.codeMirrorEditor.setValue(beautify_html(this.page_htmls))
    },
    // 编码改变
    changeComponent() {
      
    },
    avtiveComponentEdit(id) {
      let obj = this.gothrough(this.pageList).find(ele=>{
        return id == ele.id
      })
      this.page_htmls = obj.h5_tag
      this.active_component_index = id
      this.$refs.pageComponentCode.codeMirrorEditor.setValue(beautify_html(FileOpt.getHtmlStr({...obj})[0].html))
    },
    // 深度遍历
    gothrough(data) {
      const queue = [...data];
      const result = [];
      while (true) {
        const next = queue.shift();
        if (!next) {
          break;
        }
        result.push(next);
        if (Array.isArray(next.children)) {
          queue.push(...next.children);
        }
      }
      return result;
    },

    resetForm() {
      return new FrontEndProject({
        project_name: "",
        build_path: "",
        produce_language_library: "",
        page_components: [],
        page_events: [],
        ui_library: ""
      });
    },
    // 加载项目记录
    loadList: function() {
      this.$db.frontEndProject
        .find({})
        .sort({ input_date: -1 })
        .exec((err, docs) => {
          this.projectTableList = docs;
          if (docs.length > 0) {
            this.activeProjectIndex = docs[0]._id;
            this.form = docs[0];
            this.loadProjectTree();
          } else {
            this.form = this.resetForm();
          }
        });
    },
    /**
     * 导入项目form
     */
    importProject() {
      // 重置页面数据
      this.importDialogFormVisible = true;
      this.tempForm = this.resetForm();
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    // 切换项目
    changeProject(tab, event) {
      this.form = this.projectTableList[tab.index];
      this.loadProjectTree();
    },
    // 删除项目
    removeProject(targetName) {
      var that = this;
      this.$db.frontEndProject.remove(
        { _id: targetName },
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
    // 导入项目
    importSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let formData = Object.assign({}, this.tempForm);
          // 如果更新
          if (this.$route.query.id) {
            this.$db.frontEndProject.update(
              { _id: this.$route.query.id },
              formData
            );
            this.loading = false;
          } else {
            this.$db.frontEndProject.insert(formData, (err, newDoc) => {
              console.log(newDoc);
            });
            this.loading = false;
          }
          this.loadList();
          // 加载结构
          this.loadProjectTree();
          this.importDialogFormVisible = false;
          this.$notify({
            title: "成功",
            message: "操作成功",
            type: "success",
            duration: 2000
          });
        }
      });
    },
    /**
     * 加载项目目录结构
     */
    loadProjectTree() {
      let structure = FileOpt.showProjectStructure({
        fileName: this.form.project_name,
        path: this.form.build_path,
        fromPath: ""
      });
      this.project_file_structure = [structure];
    },

    // 语言库 change事件
    change_produce_language_library(event) {
      let library = this.produce_language_library.filter(ele => {
        return event == ele.value;
      })[0];
      this.tempForm.width = library.width;
      this.tempForm.height = library.height;
    },
    append(data) {
      const newChild = { id: id++, label: "testtest", children: [] };
      if (!data.children) {
        this.$set(data, "children", []);
      }
      data.children.push(newChild);
    },

    remove(node, data) {
      const parent = node.parent;
      const children = parent.data.children || parent.data;
      const index = children.findIndex(d => d.id === data.id);
      children.splice(index, 1);
    },
    goBack() {
      this.$router.back();
    },
    openDialog() {
      let filePaths = this.$electron.remote.dialog.showOpenDialog({
        properties: ["openDirectory"]
      });
      if (filePaths && filePaths.length > 0) {
        this.tempForm.build_path = filePaths[0];
      }
    },

    onAddAction(e) {
      // TODO : 减去自身高度，宽度 / 2
    },
    cloneDog(e) {
      e.id = idGlobal++;
      e.children = [];
      return JSON.parse(JSON.stringify(e));
    },
    // 拖拽事件
    component_drag_end(e) {
      // TODO: 计算组件宽高 / 2
      let evt = e.originalEvent;
      // this.pageList[e.newDraggableIndex]["x"] = evt.layerX;
      // this.pageList[e.newDraggableIndex]["y"] = evt.layerY;
    },
    exportCode(){
      console.log(FileOpt.getHtmlStr(this.pageList))
    }
  },
  computed: {
    // 拖拽表单1
    dragOptions1() {
      return {
        animation: 0,
        ghostClass: "ghost",
        // 分组
        group: {
          name: "shared",
          pull: "clone",
          put: false,
          revertClone: false
        },
        // 禁止拖动排序
        sort: false
      };
    }
  },
  mounted() {},
  created() {
    this.loadList();
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.g-bd {
  min-height: calc(100vh - 80px);
}
.g-hd {
  border: 1px solid #eee;
  border-bottom: none;
  box-sizing: border-box;
}
.g-sd {
  padding: 10px;
  background: #fff;
  border: 1px solid #eee;
  box-sizing: border-box;
}
.g-sd-rt{
  position: relative;
  background-image: linear-gradient(
      45deg,
      #f5f5f5 25%,
      transparent 0,
      transparent 75%,
      #f5f5f5 0
    ),
    linear-gradient(
      45deg,
      #f5f5f5 25%,
      transparent 0,
      transparent 75%,
      #f5f5f5 0
    );
  background-position: 0 0, 10px 10px;
  background-size: 20px 20px;
  overflow: auto;
}
.g-ft{
  border-right: 1px solid #eee;
  border-bottom: 1px solid #eee;
}
.g-mn {
  position: relative;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
  box-sizing: border-box;
}
.g-code-box {
  position: relative;
  margin: 0 auto;
  border: 1px solid #ebeef5;
  background-color: #fff;
  color: #303133;
  -webkit-transition: 0.3s;
  transition: 0.3s;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.g-tools{
  position: absolute;
  z-index: 1000;
  left: 10px;
  top: 10px;
  background: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  li{
    border-top: 1px solid #f2f2f2;
    line-height: 28px;
    padding: 0 8px;
    &:first-child{
      border-top: none
    }
  }
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.bg-purple {
  background: #d3dce6;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
  line-height: 36px;
  text-align: center;
  margin-bottom: 5px;
}
.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}
</style>
