<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" >
      <div style="float:left;width: 100%;">
      <div style="float:left;"> 
       <el-form-item>
        <el-button size="small" type="primary" @click="addHandle(0)">新增</el-button>
      </el-form-item>
      </div>
      <div style="float:right;">
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
        prop="type"
        header-align="center"
        align="center"
        label="类型">       
      </el-table-column>  
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="名称">
      </el-table-column>       
       <el-table-column
        prop="image"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="图片">
        <template slot-scope="scope"> 
          <img :src="GLOBALUploadSourceURL+scope.row.image" alt=""  :preview="pageIndex" :preview-text="scope.row.name" style="width: 50px;height: 50px">
         </template> 
      </el-table-column>   
      <el-table-column
        prop="sortId"
        header-align="center"
        align="center"
        label="序号">
      </el-table-column>            
       <el-table-column
        header-align="center"
        align="center"
        width="180"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateHandle(scope.row.id,scope.row.parentType)" >修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)" :disabled="scope.row.status == '1'" >删除</el-button>
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
  </div>
</template>

<script>
  import TableTreeColumn from '@/components/table-tree-column'
  import Sortable from 'sortablejs'
  import AddModules from './bookAdd'
  import UpdateModules from './bookUpdate' 
  import { treeDataTranslate } from '@/utils'
   
  export default {
 
    data () {
      return {
        dataForm: {
          key: ''
        },
        sortable:'',
        dataList: [],
        keyWords:'',
        pageIndex: 1,
        pageSize: 50,
        totalPage: 0,
        typeId:'-1',
        subjectModeType: '-1',
        subjectType: '-1',
        subjectStatus: '-1',
        isSaveSort: false,
        addVisible: false,
        updateVisible:false,
        importExcelVisible:false,
        dataListLoading: false,
        dataListSelections: []
      }
    },
    components: {
      AddModules,
      UpdateModules
    },
    created () {
      this.getDataList();     
    },
    mounted(){
     
    },
    methods: {
      
      addHandle(sortId) {
       
        this.addVisible = true;
        this.$nextTick(() => {
            console.log(this.$refs.addModules)
            this.$refs.addModules.init(sortId);
        });
      },
      updateHandle(id,parentType){
        debugger
        this.updateVisible = true;
        this.$nextTick(() => {
            console.log(this.$refs.updateModules)
            this.$refs.updateModules.init(id,parentType);
        });
      },
      deleteHandle(id){
         this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
           this.$http({
              url: this.$http.adornUrl('/smallVientianeTest/book/delete'),
              method: 'get',
              params: this.$http.adornParams({
                'id': id
              })
           }).then(({data}) => {
                if (data && data.Tag === 1) {
                  this.$message({
                  message: '恭喜你，删除成功！',
                  type: 'success'
                }); 
                this.getDataList();
              }else{
                this.$message.error('删除错误，'+data.Message);
              }
            
            })
        }).catch(() => {});
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
          url: this.$http.adornUrl('/smallVientianeTest/book/getPageList'),
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
