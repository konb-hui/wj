<template>
  <div>
    <a-input-search placeholder="input search text" enter-button @search="onSearch" style="margin-bottom: 50px" v-model="jotterVo.key"/>
    <a-card size="small" :title="jotter.name" style="width: 1200px;margin-top: 10px" v-for="jotter in list" :key="jotter.id">
      <span slot="extra" style="margin-right: 40px">路径：{{jotter.path}}</span>
      <span slot="extra" style="margin-right: 40px">编辑时间：{{jotter.editTime}}</span>
      <a slot="extra" href="#" @click="edit(jotter.id)" style="margin-right: 10px">编辑</a>
      <a slot="extra" href="#" @click="deleteJotter(jotter.id)">删除</a>
      <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">{{jotter.content.replace(/<\/?.+?>/g,"")}}</p>
    </a-card>
    <a-pagination style="margin-top:50px" simple :current="jotterVo.current" :total="total" :defaultPageSize="jotterVo.size" @change="jumpPage" />
  </div>
</template> 
<script>
import jotter from "@/api/jotter/jotter"
export default {
    data() {
        return {
            jotterVo: {
                size: 10,
                current: 1,
                key: ''
            },
            list: [],
            total: 0
        }
    },
    created() {
        this.getJotter()
    },
    methods: {
        getJotter() {
            jotter.getNewJotter(this.jotterVo)
            .then((res) => {
                this.list = res.data.page.records
                this.total = res.data.page.total
                console.log(this.list)
            })
        },
        onSearch() {
            this.getJotter()
        },
        edit(id) {
            this.$router.push({
                path: '/jotter/editor',
                query: {id: id}
            })
        },
        jumpPage(current) {
            this.jotterVo.current = current
            this.getJotter()
        },
        deleteJotter(id) {
            jotter.deleteJotter(id)
            .then((res) => {
                this.$message.success(res.message)
                this.getJotter()
            })
        }
    }
}
</script>