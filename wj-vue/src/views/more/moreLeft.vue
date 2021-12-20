<template>
    <el-menu
      default-active="1"
      router
      class="el-menu-vertical-demo"
      background-color="#fff"
      text-color="#808080"
      active-text-color="#ff9999">
       <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
          {{ item.navItem }}
      </el-menu-item>
    </el-menu>
</template>

<script>
export default {
    data() {
      return {
        activeIndex: '/notice',
        navList: [
          {name: '/notice', navItem: '通知管理'},
          {name: '/google/version', navItem: '谷歌版本'},
        ]
      }
    },
}
</script>