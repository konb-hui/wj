<template>
  <div style="width: 100%">
    <div style="margin-bottom: 20px">
      <a-upload-dragger
        name="file"
        :multiple="true"
        :action="BASE_API + '/notice/upload/' + fileName"
        @change="handleChange"
        :beforeUpload="beforeUpload"
        withCredentials
        :headers="headers"
      >
        <p class="ant-upload-drag-icon">
          <a-icon type="inbox" />
        </p>
        <p class="ant-upload-text">Click or drag file to this area to upload</p>
        <p class="ant-upload-hint">
          Support for a single or bulk upload. Strictly prohibit from uploading
          company data or other band files
        </p>
      </a-upload-dragger>
    </div>

    <div>
      <div :style="{ borderBottom: '1px solid #E9E9E9' }">
        <a-checkbox
          :indeterminate="indeterminate"
          :checked="checkAll"
          @change="onCheckAllChange"
        >
          Check all
        </a-checkbox>
      </div>
      <br />
      <a-checkbox-group
        v-model="checkedList"
        :options="plainOptions"
        @change="onChange"
      />
    </div>
    <a-divider />
    <div>
      <a-checkbox @change="onlyPanel"> 仅换OC </a-checkbox>
      <a-input-search
        placeholder="请输入批次号"
        style="width: 200px"
        @search="onSearch"
      />
    </div>
    <a-divider />
    <diV>
      <a-popconfirm
        title="确认删除数据？"
        ok-text="确认"
        cancel-text="取消"
        @confirm="deleteAll"
      >
        <a-button type="danger"> 清空数据 </a-button>
      </a-popconfirm>
      <a-input-search placeholder="输入通知单保存路径" style="width:600px" @search="uploadByPath">
        <a-button slot="enterButton" icon="upload"> 上传</a-button>
      </a-input-search>
    </diV>
    <a-divider />
    <div id="mydiv"></div>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import user from "@/api/user/user";
import notice from "@/api/notice/notice";
export default {
  data() {
    return {
      BASE_API: process.env.BASE_API,
      headers: {
        token: "",
      },
      fileName: "",
      checkedList: [],
      indeterminate: true,
      checkAll: false,
      plainOptions: ["1", "2"],
      changeBatchQuery: {
        onlyPanel: 0,
        batch: "",
      },
      result: {}
    };
  },
  created() {
    this.headers.token = getToken()
    this.getTeams()
  },
  mounted() {},
  methods: {
    uploadByPath(path) {
      let noticeVo = {}
      noticeVo.excelPath = path
      notice.uploadByPath(noticeVo)
      .then((res) => {
        if (res.code === 200) {
          this.$message.success('文件解析完成')
        }
        this.getTeams()
      })
    },
    deleteAll() {
      notice.deleteAll()
      .then((res) => {
        if (res.code === 200) {
          this.$message.success('删除成功')
        }
      })
      this.getTeams()
    },
    onSearch(val) {
      this.changeBatchQuery.batch = val;
      this.getChangeBatchByTeams();
    },
    onlyPanel(val) {
      this.changeBatchQuery.onlyPanel = val.target.checked;
      this.getChangeBatchByTeams();
    },
    initTable() {
      let mdiv = document.getElementById("mydiv");
      mdiv.innerHTML = this.createTable();
    },
    createTable() {
      let html = "";
      let changeIndex;
      for (let i = 0; i < this.checkedList.length; i++) {
        //显示表头
        html = html + `<h1>${this.checkedList[i]}</h1>`;
        for (let d = 0; d < this.result[this.checkedList[i]].length; d++) {
          let table = this.result[this.checkedList[i]][d];
          html =
            html +
            `<h4><a href=${table.notice.url}>${table.notice.name}</a></h2>`;
          html = html + `<table border="1"><tr>`;
          for (let a = 0; a < table.template.length; a++) {
            if (table.template[a] == "更改后") changeIndex = a;
            html = html + `<th>${table.template[a]}</th>`;
          }
          html = html + "</tr>";

          //显示表中的数据
          for (let b = 0; b < table.changeData.length; b++) {
            let data = table.changeData[b];
            html = html + "<tr>";
            for (let c = 0; c <= data.length; c++) {
              if (c === changeIndex) {
                html = html + `<td></td>`;
              } else {
                if (c > changeIndex) {
                  if (data[c - 1] === "不变") {
                    html = html + `<td>${data[c - 1]}</td>`;
                  } else {
                    html =
                      html + `<td style="color:#FF0000">${data[c - 1]}</td>`;
                  }
                } else {
                  html = html + `<td>${data[c]}</td>`;
                }
              }
            }
            html = html + "</tr>";
          }
          html = html + "</table><br>";
        }
      }
      return html;
    },
    getTeams() {
      user.getTeams().then((res) => {
        this.plainOptions = res.data.teams;
      });
    },
    getChangeBatchByTeams() {
      user.getChangeBatchByTeams(this.changeBatchQuery).then((res) => {
        this.result = res.data.data;
        console.log("change", this.result);
        this.initTable();
      });
    },
    handleCancel() {
      this.previewVisible = false;
    },
    async handlePreview(file) {
      if (!file.url && !file.preview) {
        file.preview = await this.getBase64(file.originFileObj);
      }
      this.previewImage = file.url || file.preview;
      this.previewVisible = true;
    },
    handleChange(info) {
      if (info.file.response) {
        if (info.file.response.message == "success") {
          this.getImages();
        }
      }
    },
    getBase64(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = (error) => reject(error);
      });
    },
    beforeUpload(file) {
      this.fileName = file.name;
    },
    onChange(checkedList) {
      console.log(this.plainOptions);
      this.indeterminate =
        !!checkedList.length && checkedList.length < this.plainOptions.length;
      this.checkAll = checkedList.length === this.plainOptions.length;
      this.changeBatchQuery.teams = checkedList;
      this.getChangeBatchByTeams();
    },
    onCheckAllChange(e) {
      Object.assign(this, {
        checkedList: e.target.checked ? this.plainOptions : [],
        indeterminate: false,
        checkAll: e.target.checked,
      });
      this.changeBatchQuery.teams = this.checkedList;
      this.getChangeBatchByTeams();
    },
  },
};
</script>