<template>
  <draggable
    :group="{ name: 'shared' }"
    :list="pageList"
    @add="onAddAction"
    @end="component_drag_end"
    style="height:100%;width:100%"
    class="item-container"
    tag="div"
  >
    <div
      class="item-group"
      v-for="element in pageList"
      :key="element.id"
      @click.stop="avtiveComponentEdit(element.id)"
    >
      <div :class="active_component_index == element.id ? 'item active' : 'item'">{{element.label}}</div>
      <nested-draggable
        class="item-sub"
        :pageList="element.children"
        :onAddAction="onAddAction"
        :avtiveComponentEdit="avtiveComponentEdit"
        :active_component_index="active_component_index"
        :component_drag_end="component_drag_end"
      />
    </div>
  </draggable>
</template>
<script>
import draggable from "vuedraggable";

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
  components: {
    draggable
  },
  name: "nested-draggable"
};
</script>
<style scoped>

.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}
.item-container {
  margin: 0;
}
.item {
  padding: 8px;
  width: 50%;
  outline: solid #d3dce6 1px;
  background-color: #fefefe;
}
.item.active {
  outline-color: #20A0FF;
}
.item-sub {
  margin: 0 0 0 1rem;
}
</style>
