<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" >
      <div style="float:left;width: 100%;">
      <div style="float:left;"> 
       <el-form-item>
        <el-button size="small" type="primary" @click="addHandle(0)">新增</el-button>
         <el-button size="small" type="primary" @click="importExcel(0)">导入Word</el-button>
        <el-button size="small" v-if="isAuth('samll:lookGuess:approve')" type="success" @click="approveHandle(1)">审批</el-button>
        <el-button size="small" v-if="isAuth('samll:lookGuess:approve')" type="danger" @click="approveHandle(2)">驳回</el-button>
        <el-button size="small" type="primary" @click="saveSortHandle()" :disabled="!isSaveSort">保存排序</el-button>
      </el-form-item>
      </div>
      <div style="float:right;">
       <el-form-item >
          <el-select  v-model="subjectModeType" placeholder="请选择" @change="getDataList()" clearable style="margin-left: 15px; width:110px;">
            <el-option key="-1" label="全部" value="-1" ></el-option>
            <el-option key="3" label="文字题目" value="3" ></el-option>
            <el-option key="1" label="图文题目" value="1" ></el-option>
            <el-option key="2" label="音文题目" value="2"  ></el-option>
        </el-select> 
         <el-select  v-model="subjectType" placeholder="请选择" @change="getDataList()" clearable style="margin-left: 15px; width:90px;">
            <el-option key="-1" label="全部" value="-1" ></el-option>
            <el-option key="1" label="选项型" value="1" ></el-option>
            <el-option key="2" label="选字型" value="2" ></el-option>
        </el-select> 
         <el-select  v-model="subjectStatus" placeholder="请选择" @change="getDataList()" clearable style="margin-left: 15px; width:90px;">
            <el-option key="-1" label="全部" value="-1" ></el-option>
            <el-option key="0" label="未审批" value="0" ></el-option>
            <el-option key="1" label="已审批" value="1" ></el-option>
            <el-option key="2" label="已驳回" value="2" ></el-option>
            <el-option key="3" label="已更改" value="3" ></el-option>
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
      class="ktccSubjectTable"
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
        header-align="center"
        align="center"
        width="320"
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
        prop="description"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="答案释义">
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
        </template>
      </el-table-column>
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        width="120"
        label="选项类型">
        <template slot-scope="scope"> 
          <span v-if="scope.row.type== 1">选项型</span>
          <span v-else-if="scope.row.type== 2">选字型</span>
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
      <el-table-column
        prop="sortId"
        header-align="center"
        align="center"
        width="80"
        label="排序">  
      </el-table-column>
       <el-table-column
        header-align="center"
        align="center"
        width="180"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateHandle(scope.row.id)" >修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)" :disabled="scope.row.status == '1'" v-if="isAuth('samll:lookGuess:delete')">删除</el-button>
          <el-button type="text" size="small" @click="addHandle(scope.row.sortId)" v-if="isAuth('samll:lookGuess:insertAfter')">插入下方</el-button>
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
  </div>
</template>

<script>
  import TableTreeColumn from '@/components/table-tree-column'
  import Sortable from 'sortablejs'
  import AddModules from './subjectAdd'
  import UpdateModules from './subjectUpdate' 
  import ImportExcelMoudles from './subjectImportExcel' 
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
        pageSize: 10,
        totalPage: 0,
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
      UpdateModules,
      ImportExcelMoudles
    },
    created () {
      this.getDataList();     
    },
    mounted(){
      let el = document.querySelectorAll('.ktccSubjectTable .el-table__body-wrapper > table > tbody')[0];
      //let sortable = this.Sortable.create(el)
      var thiz = this;
      this.sortable = Sortable.create(el, {
        onEnd: evt => { //监听end事件 手动维护列表   
          debugger;       
          var tempIndex = thiz.dataList.splice(evt.oldIndex, 1)[0];
          thiz.dataList.splice(evt.newIndex, 0, tempIndex);
          thiz.isSaveSort = true;
          console.log(thiz.dataList); 
        }
      });
    },
    methods: {
      saveSortHandle(){
        debugger;
        var sortStr = '';
        for(var i=0;i<this.dataList.length;i++){
              sortStr += this.dataList[i].id+";";
        }
        this.$http({
          url: this.$http.adornUrl('/smallLookGuess/saveSort'),
          method: 'get',
          params: this.$http.adornParams({
          'ids': sortStr,
          'pageIndex':this.pageIndex,
          'pageSize':this.pageSize
          })
        }).then(({data}) => {
          if (data && data.Tag === 1) {
            this.$message({
              message: '恭喜你，保存成功！',
              type: 'success'
            }); 
            this.getDataList();
            this.isSaveSort = false;
          }else{
            this.$message.error('保存错误！');
          }
        })
      },
      addHandle(sortId) {
       
        this.addVisible = true;
        this.$nextTick(() => {
            console.log(this.$refs.addModules)
            this.$refs.addModules.init(sortId);
        });
      },
      importExcel(){
        this.importExcelVisible = true;
        this.$nextTick(() => {
            console.log(this.$refs.importExcelMoudles)
            this.$refs.importExcelMoudles.init();
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
      approveHandle(status) {
        //debugger;
        var ids = [];
        for(var i= 0;i<this.dataListSelections.length;i++){
          ids.push(this.dataListSelections[i].id);
        }
        if(ids.length<=0){
          this.$message({
            message: '请至少选择一条记录！',
            type: 'warning'
          });
          return 
        }
        var statusName = status == 1 ? "审核":"驳回";
        this.$confirm('确定要'+statusName+'吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.$http({
              url: this.$http.adornUrl('/smallLookGuess/updateSubjectStatus'),
              method: 'get',
              params: this.$http.adornParams({
              'ids': ids.join(','),
              'status':status
              })
            }).then(({data}) => {
              if (data && data.Tag === 1) {
                this.$message({
                message: '恭喜你，'+statusName+'成功！',
                type: 'success'
              }); 
                this.getDataList();
              }else{
                this.$message.error(statusName+'错误，'+data.Message);
              }
            })
        }).catch(() => {});
      },
      deleteHandle(id){
         this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
           this.$http({
              url: this.$http.adornUrl('/smallLookGuess/delSubejctById'),
              method: 'get',
              params: this.$http.adornParams({
                'ids': id
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
          url: this.$http.adornUrl('/smallLookGuess/getSubjectList'),
          method: 'get',
          params: this.$http.adornParams({
            'keyWords': this.keyWords ,
            'pageIndex':  this.pageIndex,
            'pageSize': this.pageSize,
            'type':this.subjectType,
            'status':this.subjectStatus,
            'modeType':this.subjectModeType 
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
