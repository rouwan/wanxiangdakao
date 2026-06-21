<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" >
      <div style="float:left;width: 100%;">
      <div style="float:left;"> 
        <el-form-item>
          <el-button size="small" type="success" @click="approveHandle(1)">审批</el-button>
        </el-form-item>
      </div>
      <div style="float:right;">
      <el-form-item>
          <el-select  v-model="typeId" placeholder="请选择" @change="getDataList()" clearable style="margin-left: 15px; width:120px;">
            <el-option key="-1" label="全部" value="-1" ></el-option>
            <el-option key="1" label="排版有问题" value="1" ></el-option>
            <el-option key="2" label="题目不严谨" value="2" ></el-option>
            <el-option key="3" label="有错别字" value="3" ></el-option>
            <el-option key="4" label="题目错误" value="4" ></el-option>
            <el-option key="5" label="题目缺乏价值" value="5" ></el-option>
            <el-option key="6" label="其他问题" value="6" ></el-option>
        </el-select> 
      </el-form-item>
      <el-form-item>
        <el-input v-model="keyWords" placeholder="关键字" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
     </div>
      </div>
    </el-form>
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
        prop="id"
        header-align="center"
        align="center"
        width="80"
        v-if='false'
        label="ID">
      </el-table-column> 
      <el-table-column
        prop="nickname"
        header-align="center"
        align="center"
        label="反馈用户">
      </el-table-column> 
       <el-table-column
        prop="headImgUrl"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="反馈用户头像">
        <template slot-scope="scope"> 
          <img :src="scope.row.headImgUrl" alt=""  :preview="pageIndex" :preview-text="scope.row.nickname" style="width: 50px;height: 50px">
         </template> 
      </el-table-column>
       <el-table-column
        prop="promptWord"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="题目标题">
         <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateHandle(scope.row.subjectId,scope.row.parentType)" >{{scope.row.promptWord}}</el-button>
        </template>
      </el-table-column> 
       <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="250"
        label="备注">
      </el-table-column>
       <el-table-column
        prop="types"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="250"
        label="反馈类型">
         <template slot-scope="scope"> 
          {{renderTypes(scope.row.types)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="80"
        label="状态">
         <template slot-scope="scope"> 
          <span v-if="scope.row.status== 0" style="color: blue">未审批</span>
          <span v-else-if="scope.row.status== 1" style="color: green">已审批</span>
        </template>
      </el-table-column>
       <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="反馈时间">
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
    <UpdateModules v-if="updateVisible" @close="updateVisible=false" ref="updateModules" @refreshDataList="getDataList"></UpdateModules>
  </div>
</template>

<script>
import TableTreeColumn from "@/components/table-tree-column";
import Sortable from "sortablejs";
import UpdateModules from '../subject/subjectUpdate' 
import { treeDataTranslate } from "@/utils";

export default {
  data() {
    return {
      dataForm: {
        key: ""
      },
      updateVisible:false,
      typeId:'-1',
      dataList: [],
      keyWords: "",
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: []
    };
  },
  components: {
    UpdateModules
  },
  created() {
    this.getDataList();
  },
  mounted() {},
  methods: {
    renderTypes(val) {
      var arr = val.split(",");
      var typeStr = "";
      //1:排版有问题,2:题目不严谨,3:有错别字,4:题目错误,5:题目缺乏价值,6:其他问题
      var typeArr = ["", "排版有问题", "题目不严谨","有错别字","题目错误","题目缺乏价值","其他问题"];
      for (var i = 0; i < arr.length; i++) {
        typeStr += typeArr[arr[i]] + "，";
      }
      return typeStr.length > 0 ? typeStr.substring(0, typeStr.length - 1) : "";
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    approveHandle(){
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
      this.$confirm("确定要审核吗?【注：一旦审核成功本题将署上反馈者的名字和微信头像】", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl(
              "/smallVientianeTest/feedBackSubject/updateStatus"
            ),
            method: "get",
            params: this.$http.adornParams({
              ids: ids.join(","),
              status: status
            })
          }).then(({ data }) => {
            if (data && data.Tag === 1) {
              this.$message({
                message: "恭喜你，审核成功！",
                type: "success"
              });
              this.getDataList();
            } else {
              this.$message.error(statusName + "错误，" + data.Message);
            }
          });
        })
        .catch(() => {});
    },
    updateHandle(id,parentType){
        debugger
        this.updateVisible = true;
        this.$nextTick(() => {
            this.$refs.updateModules.init(id,parentType,true);
        });
      },
    // 获取数据列表
    getDataList(type) {
      debugger;
      this.dataListLoading = true;
      if(type != 2){
           if(this.keyWords.length > 0){
            this.pageIndex = 1;
           }
        }
      this.$http({
        url: this.$http.adornUrl(
          "/smallVientianeTest/feedBackSubject/getPageList"
        ),
        method: "get",
        params: this.$http.adornParams({
          typeId:this.typeId,
          keyWords: this.keyWords,
          pageIndex: this.pageIndex,
          pageSize: this.pageSize
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
