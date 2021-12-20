<template>
<div>
<a-upload-dragger
    name="file"
    :multiple="true"
    :action="BASE_API + '/image/add/' + fileName"
    @change="handleChange"
    :beforeUpload="beforeUpload"
    withCredentials
    :headers="headers"
>
    <p class="ant-upload-drag-icon">
      <a-icon type="inbox" />
    </p>
    <p class="ant-upload-text">
      Click or drag file to this area to upload
    </p>
    <p class="ant-upload-hint">
      Support for a single or bulk upload. Strictly prohibit from uploading company data or other
      band files
    </p>
</a-upload-dragger>
</div>
</template>

<script>
import { getToken } from '@/utils/auth'
export default {
    data() {
        return {
            BASE_API: process.env.BASE_API,
            headers: {
              token: ''
            },
            fileName: '',
        }
    },
    created() {
        this.headers.token = getToken()   
    },
    mounted() {
      
    },
    methods: {
        async handlePreview(file) {
            if (!file.url && !file.preview) {
                file.preview = await this.getBase64(file.originFileObj);
            }
            this.previewImage = file.url || file.preview;
            this.previewVisible = true;
        },
        handleChange(info) {
          if (info.file.response) {
            if (info.file.response.message == 'success') {
              this.getImages()
            }
            }
        },
        getBase64(file) {
            return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result);
            reader.onerror = error => reject(error);
        })
        },
        beforeUpload(file) {
          this.fileName = file.name
        }
    }
}
</script>