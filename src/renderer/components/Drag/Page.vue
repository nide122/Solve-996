<template>
  <draggable
    :group="{ name: 'shared' }"
    :list="pageList"
    @add="onAddAction"
    @end="component_drag_end"
    style="height:100%;width:100%"
    tag="div"
  >
    <div
      :class="active_component_index == element.id ? 'page-component active' : 'page-component'"
      v-for="element in realList"
      :key="element.id"
      @click.prevent="avtiveComponentEdit(element.id)"
    >
      <div v-html="element.html"></div>
    </div>
  </draggable>
</template>
<script>
import draggable from "vuedraggable";
import cheerio from "cheerio";
import FileOpt from '@/models/FileOpt'
export default {
  props: {
    active_component_index: {
      required: true
    },
    pageList: {
      required: true
    },
    avtiveComponentEdit: {
      required: true,
      type: Function
    },
    component_drag_end: {
      required: true,
      type: Function
    },
    onAddAction: {
      required: true,
      type: Function
    }
  },
  data() {
    return {
      dragOptions: {
        animation: 0,
        ghostClass: "ghost",
        disabled: false,
        group: "shared"
      }
    };
  },
  created() {},
  computed: {
    realList: {
      get() {
        return FileOpt.getHtmlStr(this.pageList);
      },
      set(value) {
        this.pageList = this.realList;
      }
    }
  },
  methods: {
    
  },
  components: {
    draggable
  },
  name: "page-draggable"
};
</script>
<style scoped>
.page-component {
  position: relative;
  outline: 1px dashed #d3dce6;
  box-sizing: border-box;
}
.page-component.active {
  outline-color: #20A0FF;
}
.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}
</style>
