<template>
<div>
  <a-button type="primary" @click="modelVisible=true" style="width:150px" icon="plus-circle">
      新增
  </a-button>

  <a-breadcrumb style="margin-top:20px">
    <a-breadcrumb-item @click.native="jumpBread(bread.parent, index)" href v-for="(bread, index) in breadList" :key="index">{{bread.label}}</a-breadcrumb-item>
  </a-breadcrumb>

  <a-list size="small" bordered :data-source="directoryList" style="margin-top:20px">
      <a-list-item slot="renderItem" split="true" slot-scope="item" style="width:1000px">
        <a-button type="link" block @click="openFolder(item)" icon="folder">
          {{ item.name }}
        </a-button>
      </a-list-item>
  </a-list>
  <a-list size="small" bordered :data-source="jotterList" style="margin-top:20px">
      <a-list-item slot="renderItem" split="true" slot-scope="item" style="width:1000px">
        <a-button type="link" block @click="updateJotter(item)" icon="file">
          {{ item.name }}
        </a-button>
      </a-list-item>
  </a-list>
  <a-modal
    title="新增文件夹"
    :visible="modelVisible"
    @ok="handleOk"
    @cancel="modelVisible=false"
  >
    <a-input placeholder="文件夹名称" v-model="directory.name" />
  </a-modal>
</div>
</template>
<script>
import jotter from "@/api/jotter/jotter"
export default {
  data() {
    return {
      modelVisible: false,
      loading: true,
      loadingMore: false,
      showLoadingMore: true,
      directoryList: [],
      jotterList:[],
      directory: {
          name: '',
          parent: '0'
      },
      parentFolder: {
        id: '0'
      },
      breadList: [
        {
          label: 'Home',
          parent: '0'
        }
      ]
    };
  },
  mounted() {
    this.getData('0')
  },
  methods: {
    getData(parent) {
        jotter.getFolder(parent)
        .then((res) => {
            this.directoryList = res.data.data.directory
            this.jotterList = res.data.data.jotter
            this.loading = false
        })
        .catch((errpr) => {
            console.error()
        })
    },
    handleOk() {
      this.directory.parent = this.parentFolder.id
        jotter.addFolder(this.directory)
        .then((res) => {
            this.$message.success('创建成功')
            this.modelVisible = false
            this.getData(this.parentFolder.id)
        })
        .catch((error) => {
            this.$message.error(error.message)
        })
    },
    openFolder(folder) {
      console.log(folder)
      this.getData(folder.id)
      this.parentFolder = folder
      let bread = {}
      bread.label = folder.name
      bread.parent = folder.id
      this.breadList.push(bread)
    },
    jumpBread(parent, index) {
      this.getData(parent)
      let length = this.breadList.length
      for (let i = length; i > index + 1; i --) {
        this.breadList.pop()
        console.log(length)
      }
    },
    updateJotter(jotter) {
    this.$router.push({
      path: '/jotter/editor',
      query: {id: jotter.id}
    })
    }
  },
};
</script>
<style>
.demo-loadmore-list {
  min-height: 350px;
}
</style>