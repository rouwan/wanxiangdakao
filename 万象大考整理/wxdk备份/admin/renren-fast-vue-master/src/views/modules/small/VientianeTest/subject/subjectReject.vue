<template >
  <el-dialog
    title='驳回理由' 
    width="25%"
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form label-width="100px">
       <el-select v-model="rejectSeasonId" placeholder="全部" clearable style="width:160px;margin-left: 15px;">
             <el-option
                v-for="item in rejectSeasonList"
                :key="item.id"
                :label="item.advice"
                :value="item.id">
            </el-option>
        </el-select> 
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" type="danger" @click="rejectHandler()">驳回</el-button>
      <el-button @click="closeDialog()">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { Loading } from "element-ui";
import { treeDataTranslate } from "@/utils";
import Icon from "@/icons";
export default {
  data() {
    return {
      visible: false,
      fileList: [],
      reqExcelUrl: "",
      ExcelUrl: "",
      ExcelName: "",
      loadingInstance: null,
      rejectSeasonList: [],
      rejectSeasonId: "",
      ids: []
    };
  },
  created() {},
  destroyed() {},
  methods: {
    selChange() {
      this.$http({
        url: this.$http.adornUrl(
          "/smallVientianeTest/rejectSeason/getPageList"
        ),
        method: "get",
        params: this.$http.adornParams({
          keyWords: "",
          pageIndex: 1,
          pageSize: 10000
        })
      }).then(({ data }) => {
        debugger;
        if (data && data.Tag === 1 && data.Result != "") {
          this.rejectSeasonList = data.Result;
        }
      });
    },
    init(ids) {
      debugger;
      if (ids == "img[preview]") {
        return;
      }
      this.visible = true;
      this.ids = ids;
      this.selChange();
    },
    rejectHandler() {
      var thiz = this;
      var rejectSeasonId = this.rejectSeasonId || "-1";
      if (rejectSeasonId == "-1") {
        this.$message({
          message: "请选择一个驳回理由",
          type: "warning"
        });
        return;
      }
      this.$http({
        url: this.$http.adornUrl("/smallVientianeTest/subject/reject"),
        method: "get",
        params: this.$http.adornParams({
          ids: this.ids.join(","),
          rejectType: parseInt(rejectSeasonId)
        })
      }).then(({ data }) => {
        if (data && data.Tag === 1) {
          this.$message({
            message: "恭喜你，驳回成功！",
            type: "success"
          });
          thiz.$emit("refreshDataList");
          thiz.closeDialog();
        } else {
          thiz.$message.error(statusName + "错误，" + data.Message);
        }
      });
    },
    closeDialog() {
      this.$emit("close");
    }
  }
};
</script>
