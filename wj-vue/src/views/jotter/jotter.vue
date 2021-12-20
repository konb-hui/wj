<template>
<div>
    <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <JotterLeft ref="jotterLeft"></JotterLeft>
    </el-aside>
    <router-view style="margin:0 auto;margin-top: 20px" />
  </el-container>
</div>
</template>

<script>
import JotterLeft from './JotterLeft'
import Editor from './editor'

export default {
    name: 'jotter',
    components: {JotterLeft, Editor},
}
</script>
