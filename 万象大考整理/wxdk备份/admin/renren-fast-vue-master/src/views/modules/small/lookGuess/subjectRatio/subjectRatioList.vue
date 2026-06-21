<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" >
      <div style="float:left;width: 100%;">
      <div style="float:left;"> 
      </div>
      <div style="float:right;">
      <el-form-item>
         <el-select  v-model="orderType" placeholder="请选择" @change="getDataList()" clearable style="margin-left: 15px; width:120px;">
            <el-option key="-1" label="请选择" value="-1" ></el-option>
            <el-option key="1" label="正确率降序" value="1" ></el-option>
            <el-option key="2" label="正确率升序" value="2" ></el-option>
            <el-option key="3" label="退出率降序" value="3" ></el-option>
            <el-option key="4" label="退出率升序" value="4" ></el-option>
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
      row-key = "id"
      style="width: 100%;height: 100%">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
       <el-table-column
        fixed="left"
        header-align="center"
        align="center"
        width="80"
        label="序号">
        <template slot-scope="scope">
          <span style="width:80px;height:45px">{{scope.$index+1}}</span>
        </template>
      </el-table-column>
       <el-table-column
        prop="subjectId"
        header-align="center"
        align="center"
        width="80"
        v-if='false'
        label="subjectId">
      </el-table-column> 
       <el-table-column
        header-align="center"
        align="center"
        width="320"
        v-if="false"
        label="图片/音频">
        <template slot-scope="scope">
          <img v-if="scope.row.modeType == 1" :src="GLOBALlookGuessImageURL+scope.row.image" alt=""  :preview="pageIndex" :preview-text="scope.row.answer" style="width: 50px;height: 50px">
          <audio v-if="scope.row.modeType == 2" :src="GLOBALlookGuessAudioURL+scope.row.audio"  controls="controls" preload="auto" ></audio>
          <span v-if="scope.row.modeType == 3" >无</span>
        </template>
      </el-table-column>
       <el-table-column
        prop="promptWord"
        header-align="center"
        align="center"
        width="250"
        :show-overflow-tooltip="true"
        label="题目">
      </el-table-column>
       <el-table-column
        prop="answer"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="答案">
      </el-table-column>
        <el-table-column
        prop="replyBefNumber"
        header-align="center"
        align="center"
        label="启动总数">
      </el-table-column>
       <el-table-column
        prop="replyAftNumber"
        header-align="center"
        align="center"
        label="答题总数">
      </el-table-column>
       <el-table-column
        prop="rightNumber"
        header-align="center"
        align="center"
        label="正确数">
      </el-table-column>
       <el-table-column
        prop="likeNumber"
        header-align="center"
        align="center"
        v-if="false"
        label="喜欢数">
      </el-table-column>
      <el-table-column
        prop="unlikeNumber"
        header-align="center"
        align="center"
        v-if="false"
        label="不喜欢数">
      </el-table-column>
       <el-table-column
        prop="exitNumber"
        header-align="center"
        align="center"
        label="退出数">
      </el-table-column>
        <el-table-column
        prop="rightRatio"
        header-align="center"
        align="center"
        label="正确率">
        <template slot-scope="scope"> 
            {{ratioRenderer(scope.row.rightRatio)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="errorRatio"
        header-align="center"
        align="center"
        label="失败率">
        <template slot-scope="scope"> 
            {{ratioRenderer(scope.row.errorRatio)}}
        </template>
      </el-table-column>
       <el-table-column
        prop="exitRatio"
        header-align="center"
        align="center"
        label="退出率">
        <template slot-scope="scope"> 
            {{ratioRenderer(scope.row.exitRatio)}}
        </template>
      </el-table-column>
       <el-table-column
        prop="likeRatio"
        header-align="center"
        align="center"
        v-if="false"
        label="喜欢率">
        <template slot-scope="scope"> 
            {{ratioRenderer(scope.row.likeRatio)}}
        </template>
      </el-table-column>
        <el-table-column
        prop="unlikeRatio"
        header-align="center"
        align="center"
        v-if="false"
        label="不喜欢率">
        <template slot-scope="scope"> 
            {{ratioRenderer(scope.row.unlikeRatio)}}
        </template>
      </el-table-column>
       <el-table-column
        header-align="center"
        align="center"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateHandle(scope.row.subjectId)" >详情</el-button>
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
    <UpdateModules v-if="updateVisible" @close="updateVisible=false" ref="updateModules" @refreshDataList="getDataList"></UpdateModules>
  </div>
</template>

<script>
  import TableTreeColumn from '@/components/table-tree-column'
  import { treeDataTranslate } from '@/utils'
  import UpdateModules from '../subject/subjectUpdate' 
  export default {
 
    data () {
      return {
        dataForm: {
          key: ''
        }, 
        orderType:'-1',
        sortable:'',
        dataList: [],
        keyWords:'',
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        subjectModeType: '-1',
        subjectType: '-1',
        subjectStatus: '-1',
        isSaveSort: false,
        addVisible: false,
        updateVisible:false,
        dataListLoading: false,
        dataListSelections: []
      }
    },
    components: {
        UpdateModules
    },
    created () {
      this.getDataList();     
    },
    mounted(){
    },
    methods: {
      ratioRenderer(value){
         return Math.floor((value*100))+'%';
      },
      updateHandle(id){
        debugger
        this.updateVisible = true;
        this.$nextTick(() => {
            this.$refs.updateModules.init(id);
        });
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 获取数据列表
      getDataList () {
        debugger;
        this.dataListLoading = true;
        this.$http({
          url: this.$http.adornUrl('/smallLookGuess/getSubjectRatio'),
          method: 'get',
          params: this.$http.adornParams({
            'orderType': this.orderType,
            'keyWords': this.keyWords ,
            'pageIndex': this.pageIndex,
            'pageSize': this.pageSize
          })
        }).then(({data}) => {
           debugger
          if (data && data.Tag === 1 && data.Result != "") {
            this.dataList = data.Result;
            this.totalPage = data.TotalCount;
          } else {
            this.dataList = [];
            this.totalPage = 0;
          }
          this.$emit("dataList", [this.dataList,this.pageIndex,this.pageSize,this.totalPage]);
          this.$previewRefresh();
          this.dataListLoading = false;
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val;
        this.pageIndex = 1;
        this.getDataList();
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val;
        this.getDataList();
      
      }
    }
  }
</script>
<style scope >
</style>
