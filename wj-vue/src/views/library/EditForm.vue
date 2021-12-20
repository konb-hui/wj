<template>
  <div>
    <i class="el-icon-circle-plus-outline"  @click="dialogFormVisible = true"></i>
    <el-dialog
      title="添加/修改图书"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form v-model="form" style="text-align: left" ref="dataForm">
        <el-form-item label="书名" :label-width="formLabelWidth" prop="title">
          <el-input v-model="form.title" autocomplete="off" placeholder="不加《》"></el-input>
        </el-form-item>
        <el-form-item label="作者" :label-width="formLabelWidth" prop="author">
          <el-input v-model="form.author" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出版日期" :label-width="formLabelWidth" prop="date">
          <el-input v-model="form.date" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出版社" :label-width="formLabelWidth" prop="press">
          <el-input v-model="form.press" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="预览" :label-width="formLabelWidth" prop="cover">
          <el-image :src="form.cover" style="width: 100px; height: 161px">
            <div slot="placeholder" class="image-slot">
              加载中<span class="dot">...</span>
            </div>
          </el-image>
        </el-form-item>
        <el-form-item label="封面" :label-width="formLabelWidth" prop="cover">
          <el-input v-model="form.cover" autocomplete="off" placeholder="图片 URL"></el-input>
        </el-form-item>
        <el-form-item label="上传书籍" :label-width="formLabelWidth" prop="bookUrl">
        <el-upload
          name="book"
          class="upload-demo"
          :headers="headers"
          :before-upload="beforeUpload"
          :on-success="successUpload"
          :action="BASE_API + '/book/upload/'">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">最好上传PDF文件，其他类型文件可能无法预览</div>
        </el-upload>
        </el-form-item>
        <el-form-item label="简介" :label-width="formLabelWidth" prop="abs">
          <el-input type="textarea" v-model="form.abs" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类" :label-width="formLabelWidth" prop="cid">
          <el-select v-model="form.tid" placeholder="请选择分类">
            <el-option
                v-for="type in typeList"
                :key="type.id"
                :label="type.name"
                :value="type.id"/>
          </el-select>
        </el-form-item>
        <el-form-item prop="id" style="height: 0">
          <el-input type="hidden" v-model="form.id" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import type from "@/api/library/type"
import book from "@/api/library/book"

  export default {
    name: 'EditForm',
    data () {
      return {
        dialogFormVisible: false,
        form: {
          id: '',
          title: '',
          author: '',
          date: '',
          press: '',
          cover: '',
          abs: '',
          tid: '',
          bookUrl: ''
        },
        formLabelWidth: '120px',
        typeList: [],
        BASE_API: process.env.BASE_API,
        headers: {
          fileName: ''
        }
      }
    },
    created() {
        this.getType()
    },
    methods: {
      clear () {
        this.form = {
          id: '',
          title: '',
          author: '',
          date: '',
          press: '',
          cover: '',
          abs: '',
          tid: ''
        }
      },
      onSubmit () {
        book.saveOrUpdate(this.form)
        .then((response) => {
          this.dialogFormVisible = false
          this.$emit('onSubmit')
        })
      },
      getType() {
          type.list()
          .then((response) => {
              this.typeList = response.data.items
          })
      },
      beforeUpload(file) {
        let strs = file.name.split('.')
        this.headers.fileName = strs.length > 1?('.' + strs[strs.length - 1]):''
      },
      successUpload(res) {
        this.form.bookUrl = res.data.url
      }
    }
  }
</script>

<style scoped>
  .el-icon-circle-plus-outline {
    margin: 50px 0 0 20px;
    font-size: 100px;
    float: left;
    cursor: pointer;
  }
</style>