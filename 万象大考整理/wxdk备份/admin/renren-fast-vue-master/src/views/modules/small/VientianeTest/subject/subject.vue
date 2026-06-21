<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" >
      <div style="float:left;width: 100%;">
      <div style="float:left;"> 
       <el-form-item>
        <el-button size="small" type="primary" @click="addHandle(0)">新增</el-button>
        <el-button size="small" type="primary" @click="importExcel(0)">导入Word</el-button>        
        <el-button size="small" v-if="isAuth('samll:vientianeTest:approve')" type="success" @click="approveHandler(1)">审批</el-button>
        <!-- <el-button size="small" v-if="isAuth('samll:vientianeTest:approve')" type="success" @click="spcialApproveHandle()">特殊审批</el-button> -->
        <el-button size="small" v-if="isAuth('samll:vientianeTest:approve')" type="danger" @click="rejectHandler()">驳回</el-button>
        <!-- <el-button size="small" v-if="isAuth('samll:vientianeTest:approve')" type="danger" @click="deleteHandle(0)">批量删除</el-button> -->
        <el-button size="small" type="primary" @click="saveSortHandle()" v-if="false">保存排序</el-button>
        <el-button size="small" type="primary" @click="saveSujectType()">修改类型</el-button>
      </el-form-item>
      </div>
      <div style="float:right;">
       <el-form-item >
          <el-select  v-model="typeId" @change="onSelect()" placeholder="请选择" clearable style="margin-left: 15px; width:90px;">
            <el-option key="-1" label="全部" value="-1" ></el-option>
            <el-option key="1" label="德" value="1" ></el-option>
            <el-option key="2" label="商" value="2" ></el-option>
            <el-option key="3" label="文" value="3" ></el-option>
            <el-option key="4" label="理" value="4" ></el-option>
            <el-option key="5" label="艺" value="5" ></el-option>
            <el-option key="6" label="体" value="6" ></el-option>
            <el-option key="7" label="综" value="7" ></el-option>
        </el-select> 
         <el-select  v-model="subjectModeType" placeholder="请选择" @change="onSelect()" clearable style="margin-left: 15px; width:100px;">
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
         <el-select  v-model="subjectStatus" placeholder="请选择" @change="onSelect()" clearable style="margin-left: 15px; width:90px;">
            <el-option key="-1" label="全部" value="-1" ></el-option>
            <el-option key="0" label="未审批" value="0" ></el-option>
            <el-option key="1" label="已审批" value="1" ></el-option>
            <el-option key="2" label="已驳回" value="2" ></el-option>
            <el-option key="3" label="已更改" value="3" ></el-option>
        </el-select> 
      </el-form-item>
      <el-form-item>
        <el-input v-model="keyWords" placeholder="关键字" clearable style="width:120px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="query()">查询</el-button>
        <el-button @click="levelHighQuery()">高级查询</el-button>
      </el-form-item>
     </div>
      </div>
    </el-form>

    <el-table
      class="wxdkSubjectTable"
      :data="dataList"
      border
      ref="table"
      v-loading="dataListLoading"
      row-key = "id"
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
        prop="id"
        header-align="center"
        align="center"
        width="80"
        v-if='false'
        label="ID">
      </el-table-column> 
      <el-table-column
        prop="parentType"
        header-align="center"
        align="center"
        width="80"
        v-if='false'
        label="parentType">
      </el-table-column> 
      <!-- <el-table-column
        header-align="center"
        align="center"
        width="220"
        label="图片/音频">
        <template slot-scope="scope">
          <img v-if="scope.row.modeType == 1" :src="GLOBALUploadSourceURL+scope.row.image" alt=""  :preview="pageIndex" :preview-text="scope.row.answer" style="width: 50px;height: 50px">
          <audio v-else-if="scope.row.modeType == 2" :src="GLOBALUploadSourceURL+scope.row.audio"  controls="controls" preload="auto" ></audio> 
          <span >暂不显示</span>
        </template>
      </el-table-column> -->
       <el-table-column
        prop="promptWord"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="题目">
      </el-table-column>
       <el-table-column
        prop="answer"
        header-align="center"
        align="center"
        width="150"
        :show-overflow-tooltip="true"
        label="答案">
      </el-table-column>
        <el-table-column
        prop="parentType"
        header-align="center"
        align="center"
        width="80"
        label="科目">
        <template slot-scope="scope"> 
          <span v-if="scope.row.parentType== 1">德</span>
          <span v-else-if="scope.row.parentType== 2">商</span>
          <span v-else-if="scope.row.parentType== 3">文</span>
          <span v-else-if="scope.row.parentType== 4">理</span>
          <span v-else-if="scope.row.parentType== 5">艺</span>
          <span v-else-if="scope.row.parentType== 6">体</span>
          <span v-else-if="scope.row.parentType== 7">综</span>
        </template>
      </el-table-column> 
      <el-table-column
        prop="modeType"
        header-align="center"
        align="center"
        width="120"
        label="题目类型">
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
        prop="createUserName"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="100"
        label="出题人">
      </el-table-column> 
      <el-table-column
        prop="realTotalNum"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="80"
        label="答题人数">
      </el-table-column>
      <el-table-column
        prop="diffType"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="80"
        label="正确率">
        <template slot-scope="scope"> 
          <span>{{scope.row.diffType}}%</span> 
        </template> 
      </el-table-column>
      <el-table-column
        prop="isBest"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="120"
        label="是否精品">
         <template slot-scope="scope"> 
          <span v-if="scope.row.isBest== 0" style="color: blue">否</span>
          <span v-else-if="scope.row.isBest== 1" style="color: green">是</span>
        </template>
      </el-table-column>
       <el-table-column
        prop="likeNum"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="80"
        label="好评人数">
      </el-table-column>
       <el-table-column
        prop="noLikeNum"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="80"
        label="差评人数">
      </el-table-column>
       <el-table-column
        prop="description"
        header-align="center"
        align="center"
        v-if="false"
        :show-overflow-tooltip="true"
        label="答案释义">
      </el-table-column>
      <el-table-column
        prop="isUser"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="100"
        v-if="false"
        label="用户出题">
         <template slot-scope="scope"> 
          <span v-if="scope.row.isUser== 0" style="color: blue">否</span>
          <span v-else-if="scope.row.isUser== 1" style="color: green">是</span>
        </template>
      </el-table-column> 
       <el-table-column
        prop="status"
        header-align="center"
        align="center"
        width="80"
        :show-overflow-tooltip="true"
        label="状态">
        <template slot-scope="scope"> 
          <span v-if="scope.row.status== 0" style="color: blue">未审批</span>
          <span v-else-if="scope.row.status== 1" style="color: green">已审批</span>
          <span v-else-if="scope.row.status== 2" style="color: red">已驳回</span>
          <span v-else-if="scope.row.status== 3" style="color: blue">已更改</span>
        </template>
      </el-table-column>
      <!-- <el-table-column
        prop="sortId"
        header-align="center"
        align="center"
        width="80"
        label="排序">  
      </el-table-column> -->
       <el-table-column
        header-align="center"
        align="center"
        width="180"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateIsBest(scope.row)" >{{scope.row.isBest == '1' ? "取消加精" :"加精"}}</el-button>
          <el-button type="text" size="small" @click="updateHandle(scope.row.id,scope.row.parentType)" >修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)" :disabled="scope.row.status == '1'" v-if="isAuth('samll:vientianeTest:delete')">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>  
     <!-- 弹窗, 新增 / 修改 -->
    <addModules v-if="addVisible" @close="addVisible=false" ref="addModules" @refreshDataList="getDataList"></addModules>
    <UpdateModules v-if="updateVisible" @close="updateVisible=false" ref="updateModules" @refreshDataList="getDataList"></UpdateModules>
    <ImportExcelMoudles v-if="importExcelVisible" @close="importExcelVisible=false" ref="importExcelMoudles" @refreshDataList="getDataList"></ImportExcelMoudles>
    <SpecialApproveMoudles v-if="specialApproveVisible" @close="specialApproveVisible=false" ref="SpecialApproveMoudles" @refreshDataList="getDataList" ></SpecialApproveMoudles>
    <UpdateTypeModules v-if="updateTypeVisible" @close="updateTypeVisible=false" ref="updateTypeModules" @refreshDataList="getDataList" ></UpdateTypeModules>
    <SubjectRejectModules v-if="subjectRejectVisible" @close="subjectRejectVisible=false" ref="subjectRejectModules" @refreshDataList="getDataList" ></SubjectRejectModules>
    <SubjectHighLevelQuery v-if="subjectQueryVisible" @close="subjectQueryVisible=false" ref="subjectHighLevelQueryModules" @refreshDataList="highLevelQuery" ></SubjectHighLevelQuery>
  </div>
</template>

<script>
import TableTreeColumn from "@/components/table-tree-column";
import Sortable from "sortablejs";
import AddModules from "./subjectAdd";
import UpdateModules from "./subjectUpdate";
import ImportExcelMoudles from "./subjectImportExcel";
import SpecialApproveMoudles from "./subjectSpecialApprove";
import UpdateTypeModules from "./subjectUpdateType";
import SubjectRejectModules from "./subjectReject";
import SubjectHighLevelQuery from "./subjectHighLevelQuery";
import { treeDataTranslate } from "@/utils";

export default {
  data() {
    return {
      dataForm: {
        key: ""
      },
      sortable: "",
      dataList: [],
      keyWords: "",
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      typeId: "-1",
      subjectDiffType: "-1",
      subjectModeType: "-1",
      subjectType: "-1",
      subjectStatus: "-1",
      isSaveSort: false,
      addVisible: false,
      updateVisible: false,
      importExcelVisible: false,
      dataListLoading: false,
      specialApproveVisible: false,
      updateTypeVisible: false,
      subjectRejectVisible: false,
      subjectQueryVisible: false,
      dataListSelections: [],
      rejectSeasonList: [],
      rejectSeasonId: "",
      answerMinNum: "",
      answerMaxNum: "",
      ratioMinNum: "",
      ratioMaxNum: "",
      likeMinNum: "",
      likeMaxNum: "",
      noLikeMinNum: "",
      noLikeMaxNum: "",
      isHighLevelQuery: false,
    };
  },
  components: {
    AddModules,
    UpdateModules,
    ImportExcelMoudles,
    SpecialApproveMoudles,
    UpdateTypeModules,
    SubjectRejectModules,
    SubjectHighLevelQuery
  },
  created() {
    this.getDataList();
    this.selChange();
  },
  mounted() {},
  methods: {
    levelHighQuery() {
      this.subjectQueryVisible = true;
      this.$nextTick(() => {
        this.$refs.subjectHighLevelQueryModules.init();
      });
    },
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
    titleHover(row) {
      debugger;
      this.updateHandle(row.id, row.parentType);
    },
    onSelect() {
      this.pageIndex = 1;
      this.getDataList();
    },
    saveSortHandle() {
      debugger;
      var sortStr = "";
      for (var i = 0; i < this.dataList.length; i++) {
        sortStr += this.dataList[i].id + ";";
      }
      this.$http({
        url: this.$http.adornUrl("/smallVientianeTest/subject/saveSort"),
        method: "get",
        params: this.$http.adornParams({
          ids: sortStr,
          pageIndex: this.pageIndex,
          pageSize: this.pageSize
        })
      }).then(({ data }) => {
        if (data && data.Tag === 1) {
          this.$message({
            message: "恭喜你，保存成功！",
            type: "success"
          });
          this.getDataList();
          this.isSaveSort = false;
        } else {
          this.$message.error("保存错误！");
        }
      });
    },
    addHandle(sortId) {
      this.addVisible = true;
      this.$nextTick(() => {
        console.log(this.$refs.addModules);
        this.$refs.addModules.init(sortId);
      });
    },
    importExcel() {
      this.importExcelVisible = true;
      this.$nextTick(() => {
        console.log(this.$refs.importExcelMoudles);
        this.$refs.importExcelMoudles.init();
      });
    },
    updateIsBest(row) {
      var id = row.id;
      var isBest = row.isBest;
      debugger
      if(row.status != 1) {
          this.$message({
            message: "对不起，只能选择已审核状态，加精！",
            type: "warning"
          });
          return
      }
      var isBestName = isBest == 1 ? "取消加精" : "加精";
      this.$confirm("确定要" + isBestName + "吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl(
              "/smallVientianeTest/subject/updateIsBest"
            ),
            method: "get",
            params: this.$http.adornParams({
              id: id,
              isBest: isBest == 1 ? 0 : 1
            })
          }).then(({ data }) => {
            if (data && data.Tag === 1) {
              this.$message({
                message: "恭喜你，" + isBestName + "成功！",
                type: "success"
              });
              this.getDataList(2);
            } else {
              this.$message.error(isBestName + "错误，" + data.Message);
            }
          });
        })
        .catch(() => {});
    },
    updateHandle(id, parentType) {
      debugger;
      this.updateVisible = true;
      this.$nextTick(() => {
        console.log(this.$refs.updateModules);
        this.$refs.updateModules.init(id, parentType);
      });
    },
    isNumber(value) {
      var reg = /^[0-9]*$/;
      if (!reg.test(value) || value.length <= 0) {
        return false;
      }
      return true;
    },
    rejectHandler() {
      //驳回状态
      var ids = [];
      var isExistsUser = false;
      for (var i = 0; i < this.dataListSelections.length; i++) {
        ids.push(this.dataListSelections[i].id);
        debugger;
        if (this.dataListSelections[i].isUser == 1) {
          isExistsUser = true;
        }
      }
      if (ids.length <= 0) {
        this.$message({
          message: "请至少选择一条记录！",
          type: "warning"
        });
        return;
      }
      // if (isExistsUser) {
      //   this.$message({
      //     message:
      //       "您当前选择了用户的记录【驳回】,备注：请到用户题目管理模块做操作！",
      //     type: "warning"
      //   });
      //   return;
      // }
      this.subjectRejectVisible = true;
      this.$nextTick(() => {
        this.$refs.subjectRejectModules.init(ids);
      });
    },
    approveHandler(status) {
      //debugger;
      var ids = [];
      var isExistsUser = false;
      for (var i = 0; i < this.dataListSelections.length; i++) {
        ids.push(this.dataListSelections[i].id);
        if (this.dataListSelections[i].isUser == 1) {
          isExistsUser = true;
        }
      }

      if (ids.length <= 0) {
        this.$message({
          message: "请至少选择一条记录！",
          type: "warning"
        });
        return;
      }
      var statusName = status == 1 ? "审核" : "驳回";
      // if (isExistsUser) {
      //   this.$message({
      //     message:
      //       "您当前选择了用户的记录【审核】,备注：请到用户题目管理模块做操作！",
      //     type: "warning"
      //   });
      //   return;
      // }

      this.$confirm("确定要" + statusName + "吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl(
              "/smallVientianeTest/subject/updateStatus"
            ),
            method: "get",
            params: this.$http.adornParams({
              ids: ids.join(","),
              status: status
            })
          }).then(({ data }) => {
            if (data && data.Tag === 1) {
              this.$message({
                message: "恭喜你，" + statusName + "成功！",
                type: "success"
              });
              this.getDataList(2);
            } else {
              this.$message.error(statusName + "错误，" + data.Message);
            }
          });
        })
        .catch(() => {});
    },
    spcialApproveHandle() {
      this.specialApproveVisible = true;
    },
    deleteHandle(id) {
      var ids = [];

      if (id > 0) {
        ids.push(id);
      } else {
        for (var i = 0; i < this.dataListSelections.length; i++) {
          ids.push(this.dataListSelections[i].id);
        }
      }
      if (ids.length <= 0) {
        this.$message({
          message: "请至少选择一条记录！",
          type: "warning"
        });
        return;
      }
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/smallVientianeTest/subject/delete"),
            method: "get",
            params: this.$http.adornParams({
              ids: ids.join(";")
            })
          }).then(({ data }) => {
            if (data && data.Tag === 1) {
              this.$message({
                message: "恭喜你，删除成功！",
                type: "success"
              });
              this.getDataList(2);
            } else {
              this.$message.error("删除错误，" + data.Message);
            }
          });
        })
        .catch(() => {});
    },
    saveSujectType() {
      //debugger;
      var ids = [];
      for (var i = 0; i < this.dataListSelections.length; i++) {
        ids.push(this.dataListSelections[i].id);
      }
      if (ids.length <= 0) {
        this.$message({
          message: "请至少选择一条记录！",
          type: "warning"
        });
        return;
      }
      this.updateTypeVisible = true;
      this.$nextTick(() => {
        this.$refs.updateTypeModules.init(ids.join(";"));
      });
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    highLevelQuery(obj) {
      debugger
      if (obj == "img[preview]") {
        return;
      }
      this.keyWords = obj.keyWords;
      this.typeId = obj.typeId;
      this.subjectStatus = obj.subjectStatus;
      this.subjectModeType = obj.subjectModeType;
      this.ratioMinNum = obj.ratioMinNum;
      this.ratioMaxNum = obj.ratioMaxNum;
      this.answerMinNum = obj.answerMinNum;
      this.answerMaxNum = obj.answerMaxNum;
      this.rejectSeasonId = obj.rejectSeasonId;
      this.likeMinNum = obj.likeMinNum;
      this.likeMaxNum = obj.likeMaxNum;
      this.noLikeMinNum = obj.noLikeMinNum;
      this.noLikeMaxNum = obj.noLikeMaxNum;
      this.getDataList();
    },
    query(){
      this.ratioMinNum = "";
      this.ratioMaxNum = "";
      this.answerMinNum = "";
      this.answerMaxNum = "";
      this.rejectSeasonId = "";
      this.likeMinNum = "";
      this.likeMaxNum = "";
      this.noLikeMinNum = "";
      this.noLikeMaxNum = "";
      this.getDataList();
    },
    // 获取数据列表
    getDataList(type) {
      debugger;
      if (type != 2) {
        if (this.keyWords.length > 0) {
          this.pageIndex = 1;
        }
      }
      var answerMinNum = this.answerMinNum;
      var answerMaxNum = this.answerMaxNum;
      if (
        answerMinNum.length > 0 &&
        answerMaxNum.length > 0 &&
        !this.isNumber(answerMinNum) &&
        !this.isNumber(answerMaxNum)
      ) {
        this.$message({
          message: "回答人数请输入整数",
          type: "warning"
        });
        return;
      }
      var ratioMinNum = this.ratioMinNum;
      var ratioMaxNum = this.ratioMaxNum;
      if (
        ratioMinNum.length > 0 &&
        ratioMaxNum.length > 0 &&
        !this.isNumber(ratioMinNum) &&
        !this.isNumber(ratioMaxNum)
      ) {
        this.$message({
          message: "正确率请输入整数",
          type: "warning"
        });
        return;
      }
      var likeMinNum = this.likeMinNum;
      var likeMaxNum = this.likeMaxNum;
      if (
        likeMinNum.length > 0 &&
        likeMaxNum.length > 0 &&
        !this.isNumber(likeMinNum) &&
        !this.isNumber(likeMaxNum)
      ) {
        this.$message({
          message: "好评人数请输入整数",
          type: "warning"
        });
        return;
      }
      var noLikeMinNum = this.noLikeMinNum;
      var noLikeMaxNum = this.noLikeMaxNum;
      if (
        noLikeMinNum.length > 0 &&
        noLikeMaxNum.length > 0 &&
        !this.isNumber(noLikeMinNum) &&
        !this.isNumber(noLikeMaxNum)
      ) {
        this.$message({
          message: "差评人数请输入整数",
          type: "warning"
        });
        return;
      }
      var rejectSeasonId = this.rejectSeasonId;
      var rejectType = this.rejectSeasonId || "-1";
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/smallVientianeTest/subject/getPageList"),
        method: "get",
        params: this.$http.adornParams({
          keyWords: this.keyWords,
          pageIndex: this.pageIndex,
          pageSize: this.pageSize,
          typeId: this.typeId,
          type: this.subjectType,
          status: this.subjectStatus,
          modeType: this.subjectModeType,
          diffType: this.subjectDiffType,
          ratioMinNum: ratioMinNum,
          ratioMaxNum: ratioMaxNum,
          answerMinNum: answerMinNum,
          answerMaxNum: answerMaxNum,
          rejectType: rejectType,
          likeMinNum: likeMinNum,
          likeMaxNum: likeMaxNum,
          noLikeMinNum: noLikeMinNum,
          noLikeMaxNum: noLikeMinNum
        })
      }).then(({ data }) => {
        debugger;
        if (data && data.Tag === 1 && data.Result != "") {
          this.dataList = data.Result;
          this.totalPage = data.TotalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.$previewRefresh();
        this.dataListLoading = false;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList(2);
    }
  }
};
</script>
<style scope >
</style>
