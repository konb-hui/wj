<template>
<div>
  <div>
  </div>
  <div style="margin-top:20px;margin-left:110px" class="myImage">
    <a-row :gutter="[16,16]" style="margin:0 auto">
    <a-card hoverable v-for="image in images" :key="image.id" style="float: left;width:300px;height:230px;margin:16px">
    <img
      slot="cover"
      alt="example"
      :src="image.url" 
      :preview-src-list="image.url"
      style="width:300px;height:180px;" 
      @click="showImg(image)"
    />
    <template slot="actions" class="ant-card-actions">
      <a-icon key="cloud-download" type="cloud-download" @click="downloadImg(image)"/>
      <a-icon key="delete" type="delete" @click="deleteImg(image.id)" />
    </template>
  </a-card>
  </a-row>
  <a-pagination style="margin-top:30px" simple :current="page.current" :total="page.total" :defaultPageSize="page.size" @change="jumpPage" />
  </div>
    <el-dialog
      :visible.sync="visible">
       <el-image
        style="width: 900px; height: 540px"
        :src="url"
        fit="cover"></el-image>
    </el-dialog>
  </div>
</template>

<script>
import image from "@/api/image/image"


export default {
    data() {
        return {         
            images: [],
            visible: false,
            url: '',
            page: {
              size: 15,
              current: 1,
              total: 0
            }
        }
    },
    created() {
      this.getImages()
    },
    mounted() {

    },
    methods: {
        getImages() {
          image.getImages(this.page)
          .then((res) => {
            this.images = res.data.page.records
            this.page.total = res.data.page.total
          })
        },
        showImg(image) {
          this.url = image.url
          this.visible = true
        },
        deleteImg(id) {
          image.deleteImage(id)
          .then((res) => {
            this.$message.success(res.message)
            this.getImages()
          })
        },
        changeActive(obj) {
          console.log(obj)
        },
        removeActive(obj) {

        },
        downloadImg(img) {
          image.downloadImage(img.id)
          .then((res) => {
            console.log(res)
            let url = window.URL.createObjectURL(new Blob([res.data], {type: 'image/png'}))
            let link = document.createElement('a')
            link.style.display = 'none'
            link.href = url
            link.setAttribute('download', img.name)
            document.body.appendChild(link)
            link.click()
          })
        },
        jumpPage(current) {
          this.page.current = current
          this.getImages()
        }
    }
}
</script>

<style>
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}

.myImage .cover {
    display:none;
  }
.myImage:hover .cover {
  width:300px;
    height:180px;
    top:0px;
    left:0px;
    background-color:black;
    opacity:0.5;
    position:absolute;
}

</style>
