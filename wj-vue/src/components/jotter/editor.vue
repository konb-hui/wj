<template>
<div style="display: inline-block;">
<div>
<div>
<el-input v-model="jotter.name" placeholder="文件名" style="width: 200px;"></el-input>
<el-cascader
    v-model="directory"
    :options="options"
    :props="{ expandTrigger: 'hover', checkStrictly: true }"
    @change="handleChange" placeholder="选择文件夹"></el-cascader>
<el-button type="primary" @click="save">保存</el-button>
<el-button type="primary" @click="clear">清空</el-button>
</div>
    <tinymce :height="300" v-model="editor"/>
</div>
</div>
</template>

<script>
import E from 'wangeditor'
import jotter from "@/api/jotter/jotter"
export default {
    data() {
        return {
          options: [],
          input: '',
          jotter: {
            id: '',
            name: '',
            parent: '',
            content: ''
          },
          editor: '',
          directory: []
        }
    },
    created() {
      this.getFolderTree()
        let id = this.$route.query.id
        if (id != undefined) {
          jotter.getJotter(id)
          .then((res) => {
            this.jotter = res.data.jotter
            this.editor.txt.html(this.jotter.content)
            jotter.getDirectoryIds(this.jotter.parent)
            .then((res) => {
              console.log("list", res.data.list)
              this.directory = res.data.list
            })
          })
        }
    },
    mounted() {
        this.editor = new E(this.$refs.editor)
        this.editor.create()
    },
    methods: {
      getFolderTree() {
        jotter.getFolderTree()
        .then((response) => {
          this.options = response.data.tree
        })
      },
      handleChange(value) {
        console.log("directory", this.directory)
        this.jotter.parent = value[value.length - 1]
      },
      save() {
        this.jotter.content = this.editor.txt.html()
        if (this.directory.length == 0) {
          this.jotter.parent = '0'
        }
        if (this.jotter.id == '') {
          jotter.addJotter(this.jotter)
          .then((res) => {
            this.$message.success(res.message)
            this.clear()
          })
        } else {
          jotter.updateJotter(this.jotter)
          .then((res) => {
            this.$message.success(res.message)
          })
        }
        
      },
      clear() {
        this.jotter = {}
        this.directory = []
        this.editor.txt.html('')
      }
    }
}
</script>

<style lang="css">
  .editor {
    width: 100%;
    margin: 0 auto;
    position: relative;
    z-index: 0;
    margin-top: 50px;
  }
  .toolbar {
    border: 1px solid #ccc;
  }
  .text {
    border: 1px solid #ccc;
    min-height: 500px;
  }
</style>