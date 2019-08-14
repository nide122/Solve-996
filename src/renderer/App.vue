<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      dialogVisible: false,
      downloadPercent: 0,
      tips: "",
      type: ''
    };
  },
  created() {
   
  },
  mounted() {
    
  },
  methods: {
    
  }
};
</script>