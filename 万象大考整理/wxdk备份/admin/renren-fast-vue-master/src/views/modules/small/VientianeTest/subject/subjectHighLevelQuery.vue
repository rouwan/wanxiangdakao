<template >
  <el-dialog
    title='高级查询' 
    width="25%"
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form label-width="100px">
      <el-form-item label="驳回理由">
          <el-select v-model="rejectSeasonId" placeholder="全部"   @change="onSelect()" clearable style="width:160px;">
             <el-option
                v-for="item in rejectSeasonList"
                :key="item.id"
                :label="item.advice"
                :value="item.id">
            </el-option>
        </el-select> 
      </el-form-item >
       <el-form-item label="好评人数">
         <el-input v-model="likeMinNum"  clearable style=" width:75px;"></el-input> - 
        <el-input v-model="likeMaxNum"  clearable style=" width:75px;"></el-input>
      </el-form-item>
       <el-form-item label="差评人数">
         <el-input v-model="noLikeMinNum"  clearable style=" width:75px;"></el-input> - 
        <el-input v-model="noLikeMaxNum"  clearable style=" width:75px;"></el-input>
      </el-form-item>
      <el-form-item label="答题人数">
         <el-input v-model="answerMinNum"  clearable style=" width:75px;"></el-input> - 
        <el-input v-model="answerMaxNum"  clearable style=" width:75px;"></el-input>
      </el-form-item>
      <el-form-item label="正确率">
         <el-input v-model="ratioMinNum"  clearable style=" width:75px;"></el-input> - 
        <el-input v-model="ratioMaxNum"  clearable style=" width:75px;"></el-input>
      </el-form-item>
      <el-form-item label="科目">
       <el-select  v-model="typeId" @change="onSelect()" placeholder="请选择" clearable style="width:160px;">
            <el-option key="-1" label="全部" value="-1" ></el-option>
            <el-option key="1" label="德" value="1" ></el-option>
            <el-option key="2" label="商" value="2" ></el-option>
            <el-option key="3" label="文" value="3" ></el-option>
            <el-option key="4" label="理" value="4" ></el-option>
            <el-option key="5" label="艺" value="5" ></el-option>
            <el-option key="6" label="体" value="6" ></el-option>
            <el-option key="7" label="综" value="7" ></el-option>
        </el-select> 
         </el-form-item>
          <el-form-item label="题目类型">
         <el-select  v-model="subjectModeType" placeholder="请选择" @change="onSelect()" clearable style="width:160px;">
            <el-option key="-1" label="全部" value="-1" ></el-option>
            <el-option key="3" label="文字题目" value="3" ></el-option>
            <el-option key="1" label="图文题目" value="1" ></el-option>
            <el-option key="2" label="音文题目" value="2"  ></el-option>
            <el-option key="4" label="判断题目" value="4"  ></el-option>
            <el-option key="5" label="成语题目" value="5"  ></el-option>
            <el-option key="6" label="单词题目" value="6"  ></el-option>
            <el-option key="7" label="五言标题上" value="7"  ></el-option>
            <el-option key="8" label="五言标题下" value="8"  ></el-option>
            <el-option key="9" label="七言标题上" value="9"  ></el-option>
            <el-option key="10" label="七言标题下" value="10"  ></el-option>
        </el-select> 
         </el-form-item>
          <el-form-item label="状态">
         <el-select  v-model="subjectStatus" placeholder="请选择" @change="onSelect()" clearable style="width:160px;">
            <el-option key="-1" label="全部" value="-1" ></el-option>
            <el-option key="0" label="未审批" value="0" ></el-option>
            <el-option key="1" label="已审批" value="1" ></el-option>
            <el-option key="2" label="已驳回" value="2" ></el-option>
            <el-option key="3" label="已更改" value="3" ></el-option>
        </el-select> 
         </el-form-item>
      <el-form-item label="关键字">
        <el-input v-model="keyWords" placeholder="关键字" clearable style="width:160px;"></el-input>
      </el-form-item>
    </el-form >
    <span slot="footer" class="dialog-footer">
      <el-button size="small" type="success" @click="queryHandler()">查询</el-button>
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
      ids: [],
      answerMinNum: "",
      answerMaxNum: "",
      ratioMinNum: "",
      ratioMaxNum: "",
      likeMinNum: "",
      likeMaxNum: "",
      noLikeMinNum: "",
      noLikeMaxNum: "",
      subjectStatus: "-1",
      subjectModeType: "-1",
      typeId: "-1",
      keyWords: ""
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
        if (data && data.Tag === 1 && data.Result != "") {
          this.rejectSeasonList = data.Result;
        }
      });
    },
    init(ids) {
      if (ids == "img[preview]") {
        return;
      }
      this.visible = true;
      this.ids = ids;
      this.selChange();
    },
    queryHandler() {
      var obj = {
        keyWords: this.keyWords,
        typeId: this.typeId,
        subjectStatus: this.subjectStatus,
        subjectModeType: this.subjectModeType,
        ratioMinNum: this.ratioMinNum,
        ratioMaxNum: this.ratioMaxNum,
        answerMinNum: this.answerMinNum,
        answerMaxNum: this.answerMaxNum,
        rejectSeasonId: this.rejectSeasonId,
        likeMinNum: this.likeMinNum,
        likeMaxNum: this.likeMaxNum,
        noLikeMinNum: this.noLikeMinNum,
        noLikeMaxNum: this.noLikeMaxNum
      };
      this.$emit("refreshDataList",obj);
    },
    closeDialog() {
      this.$emit("close");
    }
  }
};
</script>
