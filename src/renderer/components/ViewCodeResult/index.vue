<template>
  <div class="u-view-result">
    <iframe  class="result-page"  :src="src" frameborder="0">
      
    </iframe>
  </div>
</template>

<script>

export default {
  data() {
    return {
      levelList: null,
      codeMirrorEditor: null
    };
  },
  props: {
    src: {
      type: String,
      default: ""
    },
    html:{
      type: String,
      default: ""
    },
    height:{
      type: Number | String,
      default: ""
    },
  },
  watch: {
   
  },
  created() {},
  mounted() {
    // console.log(this.height)
  },
  methods: {
    
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.u-view-result {
  width: 100%;
  height: 100%;
  .result-page {
    width: 100%;
    height: 100%;
    border: none;
    display: flex;
    flex: 1 1;
  }
}
</style>
