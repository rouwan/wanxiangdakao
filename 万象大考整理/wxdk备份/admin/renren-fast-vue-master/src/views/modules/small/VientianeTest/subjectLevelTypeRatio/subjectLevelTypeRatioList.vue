<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" >
      <div style="float:left;width: 100%;">
      <div style="float:left;"> 
      </div>
      <div style="float:right;">
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
        prop="totalNum"
        header-align="center"
        align="center"
        label="题目总数">
      </el-table-column> 
        <el-table-column
        prop="simpleNum"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="简单总数">
      </el-table-column>
       <el-table-column
        prop="commonNum"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="一般总数">
      </el-table-column>
       <el-table-column
        prop="hardNum"
        header-align="center"
        align="center"
        label="困难总数">
      </el-table-column>
      <el-table-column
        prop="simpleRatio"
        header-align="center"
        align="center"
        label="简单占有率">
        <template slot-scope="scope"> 
            {{ratioRenderer(scope.row.simpleRatio)}}
        </template>
      </el-table-column>
       <el-table-column
        prop="commonRatio"
        header-align="center"
        align="center"
        label="一般占有率">
         <template slot-scope="scope"> 
            {{ratioRenderer(scope.row.commonRatio)}}
        </template>
      </el-table-column>
       <el-table-column
        prop="hardRatio"
        header-align="center"
        align="center"
        label="困难占有率">
         <template slot-scope="scope"> 
            {{ratioRenderer(scope.row.hardRatio)}}
        </template>
      </el-table-column>
    </el-table>
     <SubjectLevelTypeRatioChar ref="subjectLevelTypeRatioChar" ></SubjectLevelTypeRatioChar>
  </div>
  
</template>

<script>
  import TableTreeColumn from '@/components/table-tree-column'
  import { treeDataTranslate } from '@/utils'
  import SubjectLevelTypeRatioChar from './subjectLevelTypeRatioChar'
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
        SubjectLevelTypeRatioChar
    },
    created () {
      this.getDataList();     
    },
    mounted(){
    },
    methods: {
      ratioRenderer(value){
         return value+'%';
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      initCharBar(data){
          this.$nextTick(() => {
           this.$refs.subjectLevelTypeRatioChar.init(data);
          });
      },
      // 获取数据列表
      getDataList () {
        debugger;
        this.dataListLoading = true;
        this.$http({
          url: this.$http.adornUrl('/smallVientianeTest/subjectLevelTypeRatio/get'),
          method: 'get',
          params: this.$http.adornParams({

          })
        }).then(({data}) => {
           debugger
          if (data && data.Tag === 1 && data.Result != "") {
            this.dataList = data.Result;   
          } else {
            this.dataList = [];
          }
          this.initCharBar(this.dataList);
          this.$emit("dataList", [this.dataList]);
          //this.$previewRefresh();
          this.dataListLoading = false;
        })
      }
    }
  }
</script>
<style scope >
</style>
