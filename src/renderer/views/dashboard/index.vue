<template>
  <div class="dashboard-container">
    <div class="dashboard-text"></div>
    <el-card class="box-card">
      <div class="text item">
        <el-button type="text" @click="push(2)">Java项目列表</el-button>
        <el-alert
          title="管理Java项目."
          type="success"
          :closable="false"
          show-icon >
        </el-alert>
      </div>
      <div class="text item">
        <el-button type="text" @click="push(1)">初始化Java</el-button>
        <el-alert
          title="新建项目"
          type="success"
          :closable="false"
          show-icon >
        </el-alert>
      </div>
      <div class="text item">
        <el-button type="text" @click="push(3)">POJO&VO</el-button>
        <el-alert
          title="根据mysql模型自动生成POJO与VO ."
          type="success"
          :closable="false"
          show-icon >
        </el-alert>
      </div>
      <div class="text item">
        <el-button type="text" @click="push(4)">SERVICE&CONTROLLER</el-button>
        <el-alert
          title="根据POJO、VO生成service、controller、mapper、e2e test."
          type="success"
          :closable="false"
          show-icon >
        </el-alert>
      </div>
      <div class="text item">
        <el-button type="text" @click="push(5)">DEPLOY</el-button>
        <el-alert
          title="开发中"
          type="info"
          :closable="false"
          show-icon >
        </el-alert>
      </div>
      <div class="text item">
        <el-button type="text" @click="push(6)">TEST</el-button>
        <el-alert
          title="创建测试计划"
          type="success"
          :closable="false"
          show-icon >
        </el-alert>
      </div>
      <div class="text item">
        <el-button type="text" @click="push(7)">前端生成器</el-button>
        <el-alert
          title="开发中"
          type="info"
          :closable="false"
          show-icon >
        </el-alert>
      </div>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import * as md5 from "blueimp-md5";

export default {
  name: "Dashboard",
  data() {
    return {
      temp: {
        id: undefined
      }
    };
  },
  computed: {
    ...mapGetters(["menuMap"])
  },
  methods: {
    push(index){
      switch(index){
        case 1:
        this.$router.push({ path: '/developer/InitJavaPage' })
        break;
        case 2:
        this.$router.push({ path: '/developer/InitJavaListPage' })
        break;
        case 3:
        this.$router.push({ path: '/developer/JavaModels' })
        break;
        case 4:
        this.$router.push({ path: '/developer/JavaService' })
        break;
        case 5:
        this.$router.push({ path: '/developer/JavaDeploy' })
        break;
        case 6:
        this.$router.push({ path: '/developer/JavaTest' })
        break;
        case 7:
        this.$router.push({ path: '/developer/Main' })
        break;
        default:break
      }
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.updatePassWorld {
  font-size: 20px;
  cursor: pointer;

  margin-bottom: 15px;
  line-height: 44px;
  width: 13.66667%;
  text-align: center;
  transition: width 2s;
}
.updatePassWorld:hover {
  background: #f7f8fa;
}
</style>
