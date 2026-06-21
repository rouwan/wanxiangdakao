<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" >
      <div style="float:left;width: 100%;">
      <div style="float:left;"> 
      </div>
      <div style="float:right;">
        <el-date-picker
            v-model="selectDate"
            type="date"
            format="yyyy-MM-dd"
            placeholder="选择日期">
        </el-date-picker>
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
        prop="nickname"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="名字">
      </el-table-column>
      <el-table-column
        prop="money"
        header-align="center"
        align="center"
         width="120"
        :show-overflow-tooltip="true"
        label="金额">
      </el-table-column>
       <el-table-column
        prop="type"
        header-align="center"
        align="center"
        width="120"
        :show-overflow-tooltip="true"
        label="类型">
         <template slot-scope="scope"> 
          <span v-if="scope.row.type== 1">关卡跳过</span>
          <span v-else-if="scope.row.type== 2">补充体力</span>
        </template>
      </el-table-column>
       <el-table-column
        prop="transactionId"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="交易单号">
      </el-table-column>
       <el-table-column
        prop="outTradeNo"
        header-align="center"
        align="center"
        label="商品单号">
      </el-table-column>
       <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="充值时间">
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
        selectDate:'',
        keyWords:'',
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: []
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
       // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 获取数据列表
      getDataList () {
        debugger;
        this.dataListLoading = true;
        if(this.keyWords.length > 0){
          this.pageIndex = 1;
          this.pageSize = 10;
        }
        this.$http({
          url: this.$http.adornUrl('/smallLookGuess/rechargerecord/getPageList'),
          method: 'get',
          params: this.$http.adornParams({
            'keyWords': this.keyWords ,
            'pageIndex':  this.pageIndex,
            'pageSize': this.pageSize,
            'date': this.selectDate == "" || this.selectDate == null ? '':this.selectDate
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
