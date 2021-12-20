<template>
  <div>
    <a-card size="small" :title="jotter.name" style="width: 1200px;margin-top: 10px" v-for="jotter in list" :key="jotter.id">
      <span slot="extra" style="margin-right: 40px">路径：{{jotter.path}}</span>
      <span slot="extra" style="margin-right: 40px">编辑时间：{{jotter.editTime}}</span>
      <a slot="extra" href="#" @click="recover(jotter.id)" style="margin-right: 10px">恢复</a>
      <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">{{jotter.content.replace(/<\/?.+?>/g,"")}}</p>
    </a-card>
  </div>
</template> 

<script>
import jotter from "@/api/jotter/jotter"
export default {
    data() {
        return {
            list: []
        }
    },
    created() {
        this.getData()
    },
    methods: {
        getData() {
            jotter.getDeletedJotter()
            .then((res) => {
                this.list = res.data.list
                console.log(res)
            })
        },
        recover(id) {
            jotter.recoverJotter(id)
            .then((res) => {
                this.$message.success(res.message)
                this.getData()
            })
        }
    }
}
</script>