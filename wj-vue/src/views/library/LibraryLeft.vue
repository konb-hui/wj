<template>
    <el-menu
      default-active="0"
      class="el-menu-vertical-demo"
      @select="handlerSelect">
      <el-menu-item index="0">
        <i class="el-icon-menu"></i>
        <span slot="title">全部</span>
      </el-menu-item>
      <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.id">
        <i class="el-icon-menu"></i>
        <span slot="title">{{ item.name }}</span>
      </el-menu-item>
    </el-menu>
</template>

<script>

import type from "@/api/library/type"

export default {
    name: 'SideMenu',
    data() {
        return {
          navList: [],
        }
    },
    created() {
      this.getType()
      console.log(this.navList)
    },
    methods: {
      getType() {
        type.list()
        .then((response) => {
          console.log("res", response)
          this.navList = response.data.items
        })
        .catch((error) => {
          console.error()
        })
      },
      handlerSelect(key, keyPath) {
        //调用父组件handchange方法
        this.$emit("handchange",key)
      }
    }
}
</script>

<style scoped>

</style>