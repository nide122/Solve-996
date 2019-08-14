<template>
  <div class="no-data">
    <div class="text">{{text}}</div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {};
  },
  props: {
    text: {
      type: String,
      default: "没有更多数据了"
    }
  },
  mounted() {},
  methods: {},
  computed: {},
  watch: {}
};
</script>

<style scoped lang="scss">
.no-data {
  width: 300px;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
  margin:50px auto;
  line-height: 1.6em;
  font-size: 14px;
  text-align: center;
  .text {
    position: relative;
    display: inline-block;
    top: -0.9em;
    padding: 0 0.55em;
    background-color: #ffffff;
    color: rgba(0, 0, 0, 0.5);
  }
}
</style>
