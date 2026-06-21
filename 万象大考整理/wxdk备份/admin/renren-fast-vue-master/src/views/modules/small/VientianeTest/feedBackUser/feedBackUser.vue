<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" >
      <div style="float:left;width: 100%;">
      <div style="float:left;"> 
      </div>
      <div style="float:right;">
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
        prop="feedBackUserName"
        header-align="center"
        align="center"
        label="举报用户">
      </el-table-column> 
       <el-table-column
        prop="feedBackHeadImgUrl"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="举报用户头像">
        <template slot-scope="scope"> 
          <img :src="scope.row.feedBackHeadImgUrl" alt=""  :preview="pageIndex" :preview-text="scope.row.feedBackUserName" style="width: 50px;height: 50px">
         </template> 
      </el-table-column>
       <el-table-column
        prop="userName"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="被举报用户">
      </el-table-column>
      <el-table-column
        prop="HeadImgUrl"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="被举报用户头像">
        <template slot-scope="scope"> 
          <img :src="scope.row.HeadImgUrl" alt=""  :preview="pageIndex" :preview-text="scope.row.userName" style="width: 50px;height: 50px">
         </template> 
      </el-table-column>
       <el-table-column
        prop="types"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="250"
        label="举报类型">
         <template slot-scope="scope"> 
          {{renderTypes(scope.row.types)}}
        </template>
      </el-table-column>
       <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="举报时间">
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
  import Sortable from 'sortablejs'
  import { treeDataTranslate } from '@/utils'
   
  export default {
 
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
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
      renderTypes(val){
        var arr = val.split(',');
        var typeStr = '';
        var typeArr = ["","对方疑似作弊","对方头像和昵称违规"];
        for(var i = 0;i<arr.length;i++){
             typeStr += typeArr[arr[i]]+"，";
        }
        return typeStr.length>0 ? typeStr.substring(0,typeStr.length-1) : "";
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 获取数据列表
      getDataList (type) {
        debugger;
        this.dataListLoading = true;
        if(type != 2){
           if(this.keyWords.length > 0){
            this.pageIndex = 1;
           }
        }
        this.$http({
          url: this.$http.adornUrl('/smallVientianeTest/feedBackUser/getPageList'),
          method: 'get',
          params: this.$http.adornParams({
            'keyWords': this.keyWords ,
            'pageIndex':  this.pageIndex,
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
        this.getDataList(2);
      
      }
    }
  }
</script>
<style scope >
</style>
