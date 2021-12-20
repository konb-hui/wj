<template>
<div>
    <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <MoreLeft ref="moreLeft"></MoreLeft>
    </el-aside>
    <router-view style="margin:0 auto;margin-top: 20px" />
  </el-container>
</div>
</template>

<script>
import MoreLeft from './moreLeft'

export default {
    name: 'more',
    components: {MoreLeft},
}
</script>
