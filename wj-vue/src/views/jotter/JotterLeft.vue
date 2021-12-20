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
        activeIndex: '/jotter/editor',
        navList: [
          {name: '/jotter/editor', navItem: '新建笔记'},
          {name: '/jotter/list', navItem: '最新笔记'},
          {name: '/jotter/directory', navItem: '文件夹'},
          {name: '/jotter/recycle', navItem: '回收站'}
        ]
      }
    },
}
</script>