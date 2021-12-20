<template>
  <div>
    <el-row v-for="(version, index) in googleVersionList" :key="index">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span style="float: left"
            ><el-tag color="#E0ECF8">{{ version.versionCode }}</el-tag></span
          >
          <!-- <span>{{ version.productName }}</span> -->
          <el-button
            style="float: right; padding: 3px 0"
            type="text"
            @click="show(version)"
            >查看修改信息</el-button
          >
        </div>
        <el-row
          type="flex"
          class="row-bg"
          justify="space-around"
          style="margin-bottom: 5px"
          v-for="(base, index) in version.versionBaseInfoList"
          :key="index"
        >
          <el-col :span="6"
            ><el-tag style="float: left" color="#F8E0EC">{{
              base.productName
            }}</el-tag></el-col
          >
          <el-col :span="10"
            ><el-tag style="float: left" color="#F3F781">{{
              base.versionName
            }}</el-tag></el-col
          >
          <el-col :span="5"
            ><el-tag :type="base.isAuthorize == 'Y' ? 'success' : 'danger'">{{
              base.isAuthorize == "Y" ? "已授权" : "未授权"
            }}</el-tag></el-col
          >
          <el-col :span="16"
            ><el-tag style="float: left" color="#E0F8F7">{{
              base.fingerPrint
            }}</el-tag></el-col
          >
          <el-col :span="3"
            ><el-link
              type="primary"
              @click="openMore(base.versionDetailInfoList)"
              >more</el-link
            ></el-col
          >
        </el-row>
      </el-card>
    </el-row>
    <el-backtop></el-backtop>
    <el-dialog
      title="修改内容"
      :visible.sync="modifiedItemgVisible"
      width="60%"
    >
      <span>{{ modifiedItem }}</span>
    </el-dialog>
    <el-dialog title="更多" :visible.sync="moreVisible" width="60%">
      <el-table :data="detailList" style="width: 100%">
        <el-table-column prop="client" label="客户" width="180">
        </el-table-column>
        <el-table-column
          prop="detailList"
          label="批次"
          width="400"
        >
        <template slot-scope="scope">
        <el-tag v-for="(batch, index) in scope.row.batchList" :key="index" style="margin-right:5px;margin-bottom:5px">{{ batch }}</el-tag>
        </template>
        </el-table-column>
        <el-table-column prop="otaRecord" label="OTA记录"> </el-table-column>
        <el-table-column prop="otaVersion" label="OTA版本"> </el-table-column>
        <el-table-column prop="otaTime" label="OTA时间"> </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import version from "@/api/google/version/version";
export default {
  data() {
    return {
      googleVersionList: [],
      query: {},
      modifiedItemgVisible: false,
      modifiedItem: "",
      moreVisible: false,
      detailList: [],
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      version.list(this.query).then((res) => {
        this.googleVersionList = res.data.data;
        console.log("googleVersionList", this.googleVersionList);
      });
    },
    show(version) {
      this.modifiedItem = version.modifiedItem;
      this.modifiedItemgVisible = true;
    },
    openMore(list) {
      this.detailList = list
      for (let i = 0; i < list.length; i++) {
        if (list[i].batch != null) {
          this.detailList[i].batchList = list[i].batch.split("/");
        }
      }
      this.moreVisible = true;
    },
  },
};
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 1600px;
}
</style>