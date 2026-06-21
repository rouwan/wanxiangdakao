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
        <el-form-item label="" style="margin-left: 15px; width:110px;">
         <el-select  v-model="parentType" @change="getDataList()" placeholder="请选择" clearable>
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
        prop="parentType"
        header-align="center"
        align="center"
        label="父类名称">
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
        prop="name"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="类型名称">
      </el-table-column>
      <el-table-column
        prop="sortId"
        header-align="center"
        align="center"
        width="80"
        :show-overflow-tooltip="true"
        label="顺序">
      </el-table-column>
       <el-table-column
        prop="modifyTime"
        header-align="center"
        align="center"
        width="170"
        label="最后修改时间">
      </el-table-column>
       <el-table-column
        header-align="center"
        align="center"
        width="180"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateHandle(scope.row.id)" >修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)" v-if="isAuth('small:dataConfig:delete')">删除</el-button>
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
    <AddSubjectTypeModules v-if="addVisible" @close="addVisible=false" ref="addModules" @refreshDataList="getDataList"></AddSubjectTypeModules>
    <UpdateSubjectTypeModules v-if="updateVisible" @close="updateVisible=false" ref="updateModules" @refreshDataList="getDataList"></UpdateSubjectTypeModules>
  </div>
</template>

<script>
  import TableTreeColumn from '@/components/table-tree-column'
  import AddSubjectTypeModules from './subjectTypeAdd'
  import UpdateSubjectTypeModules from './subjectTypeUpdate' 
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
        parentType:'-1',
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        subjectModeType: '-1',
        subjectType: '-1',
        subjectStatus: '-1',
        addVisible: false,
        updateVisible:false,
        dataListLoading: false,
        dataListSelections: []
      }
    },
    components: {
      AddSubjectTypeModules,
      UpdateSubjectTypeModules
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
      updateHandle(id){
        debugger
        this.updateVisible = true;
        this.$nextTick(() => {
            console.log(this.$refs.updateModules)
            this.$refs.updateModules.init(id);
        });
      },
      deleteHandle(id){
         this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
           this.$http({
              url: this.$http.adornUrl('/smallVientianeTest/subjectType/delete'),
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
          url: this.$http.adornUrl('/smallVientianeTest/subjectType/getPageList'),
          method: 'get',
          params: this.$http.adornParams({
            'keyWords': this.keyWords ,
            'pageIndex':  this.pageIndex,
            'pageSize': this.pageSize,
            'parentType': this.parentType
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
        this.getDataList(2);
      
      }
    }
  }
</script>
<style scope >
</style>
