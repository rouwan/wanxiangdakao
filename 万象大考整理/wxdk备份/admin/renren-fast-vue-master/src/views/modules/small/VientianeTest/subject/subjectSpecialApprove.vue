<template >
  <el-dialog
    title='特殊审批' 
    width="650px"
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-table
      :data="dataList"
      border
      ref="table"
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;height: 100%">
        <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
       <el-table-column
        header-align="center"
        align="center"
        width="80"
        label="序号">
        <template slot-scope="scope">
          <span style="width:80px;height:45px">{{scope.$index+1}}</span>
        </template>
      </el-table-column>
       <el-table-column
        prop="modeType"
        header-align="center"
        align="center"
        label="类型">
        <template slot-scope="scope"> 
          <span v-if="scope.row.modeType== 1">图文</span>
          <span v-else-if="scope.row.modeType== 2">音文</span>
          <span v-else-if="scope.row.modeType== 3">文字</span>
          <span v-else-if="scope.row.modeType== 4">判断</span>
          <span v-else-if="scope.row.modeType== 5">成语</span>
          <span v-else-if="scope.row.modeType== 6">单词</span>
          <span v-else-if="scope.row.modeType== 7">五言标题上</span>
          <span v-else-if="scope.row.modeType== 8">五言标题下</span>
          <span v-else-if="scope.row.modeType== 9">七言标题上</span>
          <span v-else-if="scope.row.modeType== 10">七言标题下</span>
        </template>
      </el-table-column> 
      <el-table-column
        prop="totalNum"
        header-align="center"
        align="center"
        label="总数">
      </el-table-column> 
       <el-table-column
        prop="approveNum"
        header-align="center"
        align="center"
        width="150"
        label="已审批数">
        <template slot-scope="scope"> 
           <el-input-number v-model="scope.row.approveNum" :value="scope.row.approveNum" controls-position="right" size="mini" style=" border:0px;"></el-input-number>
        </template>
      </el-table-column> 
      <el-table-column
        header-align="center"
        align="center"
        width="120"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="approveHandle(scope.row)" >审批</el-button>
        </template>
      </el-table-column>
      </el-table>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDialog()">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { treeDataTranslate } from "@/utils";
import Icon from "@/icons";
export default {
  data() {
    return {
      visible: false,
      dataListLoading: false,
      dataList: [
        { modeType: "5", totalNum: "1000", approveNum: "500" },
        { modeType: "6", totalNum: "1200", approveNum: "300" }
      ],
      dataListSelections: []
    };
  },
  created() {},
  destroyed() {},
  methods: {
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    init() {
      this.visible = true;
      this.$http({
        url: this.$http.adornUrl(
          "/smallVientianeTest/subject/getModeTypeNumList"
        ),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        if (data && data.Tag === 1) {
          this.dataList = data.Result;
        }
      });
    },
    approveHandle(row) {
      console.log(row);
      if (row.totalNum < row.approveNum) {
        this.$message({
          message: "审批数不能大于总数",
          type: "warning"
        });
        return;
      }
      this.$confirm("确定要批量审批吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl(
              "/smallVientianeTest/subject/batchUpdateStatus"
            ),
            method: "get",
            params: this.$http.adornParams({
              modeType: row.modeType,
              num: row.approveNum
            })
          }).then(({ data }) => {
            if (data && data.Tag === 1) {
              this.$message({
                message: "恭喜你，审批成功！",
                type: "success"
              });
              //this.init();
              this.$emit('refreshDataList');
            }
          });
        })
        .catch(() => {});
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    closeDialog() {
      this.$emit("close");
    }
  }
};
</script>
