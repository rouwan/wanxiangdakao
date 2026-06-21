<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" >
      <div style="float:left;width: 100%;">
      <div style="float:left;"> 
      </div>
      <div style="float:right;">
       <el-form-item>
        <el-button @click="getDataList()">刷新</el-button>
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
        prop="cardNumber"
        header-align="center"
        align="center"
        label="关卡数">
      </el-table-column> 
       <el-table-column
        prop="totalNum"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="题目总数">
      </el-table-column>
        <el-table-column
        prop="totalUserNum"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="用户总数">
      </el-table-column>
       <el-table-column
        prop="userNum"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="用户使用数">
      </el-table-column>
       <el-table-column
        prop="userRatio"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="使用率">
         <template slot-scope="scope"> 
            {{ratioRenderer(scope.row.userRatio)}}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import TableTreeColumn from '@/components/table-tree-column'
  import { treeDataTranslate } from '@/utils'
   
  export default {
 
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        dataListLoading: false
      }
    },
    components: {
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
        // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 获取数据列表
      getDataList () {
        debugger;
        this.dataListLoading = true;
        this.$http({
          url: this.$http.adornUrl('/smallLookGuess/cardNumber/getCardNumberUse'),
          method: 'get',
          params: this.$http.adornParams({
          })
        }).then(({data}) => {
           debugger
          if (data && data.Tag === 1 && data.Result != "") {
            this.dataList = data.Result;
           
          } else {
            this.dataList = [];
            this.totalPage = 0;
          }
          this.$previewRefresh();
          this.dataListLoading = false;
        })
      }
    }
  }
</script>
<style scope >
</style>
