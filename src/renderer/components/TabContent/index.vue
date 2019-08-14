<template>
  <div>
    <el-collapse v-model="active" style="border: none;">
      <el-collapse-item
        :key="index"
        v-for="(item,index) in List"
        :title="item.question"
        :name="index"
      >
        <div class="content" v-html="item.answer"></div>
      </el-collapse-item>
    </el-collapse>
    <div v-show="Total<=0" style="text-align:center;padding:50px">暂无数据</div>
    <div class="pagination-container">
      <el-pagination
        v-show="Total>10"
        :current-page="page"
        :page-sizes="[10,20,30, 50]"
        :page-size="rows"
        :total="Total"
        background
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="sizeChange"
        @current-change="currentChange"
      />
    </div>
  </div>

  <!-- <el-collapse v-model="activeCase">
                  <el-collapse-item :key="index" v-for="(item,index) in caseList" :title="item.question" :name="index">
                    <div class="content" v-html="item.answer"></div>
                  </el-collapse-item>
                </el-collapse>
                <div v-show="caseTotal<=0">暂无数据</div>
  <div class="pagination-container"><el-pagination v-show="caseTotal>0" :current-page="caseListQuery.page" :page-sizes="[10,20,30, 50]" :page-size="caseListQuery.rows" :total="caseTotal" background layout="total, sizes, prev, pager, next, jumper" @size-change="handleCaseSizeChange" @current-change="handleCaseChange"/></div>-->
</template>
<script>
export default {
  props: {
    List: {
      type: Array,
      default: []
    },
    value: {
      type: Number,
      default: 0
    },
    Total: {
      type: Number,
      default: 0
    },
    rows: {
      type: Number,
      default: 0
    },
    page: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      active: 0
    };
  },
  methods: {
    sizeChange(num) {
      this.$emit("size-change", num);
    },
    currentChange(num) {
      this.$emit("current-change", num);
    }
  },
  created() {
    this.active = this.value;
  },
  computed: {}
};
</script>

<style scoped>
</style>



