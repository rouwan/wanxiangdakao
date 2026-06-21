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
        prop="headImgUrl"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="头像">
        <template slot-scope="scope">
          <img  :src="scope.row.headImgUrl" alt=""  :preview="pageIndex" :preview-text="scope.row.answer" style="width: 50px;height: 50px" />
        </template>
      </el-table-column>
      <el-table-column
        prop="strengthCount"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="体力">
      </el-table-column>
      <el-table-column
        prop="cardNumber"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="关卡数">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        width="180"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updateHandle(scope.row.nickname)" >修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
    <AddTestUserModules v-if="addVisible" @close="addVisible=false" ref="addModules" @refreshDataList="getDataList"></AddTestUserModules>
    <UpdateTestUserModules v-if="updateVisible" @close="updateVisible=false" ref="updateModules" @refreshDataList="getDataList"></UpdateTestUserModules>
  </div>
</template>

<script>
import TableTreeColumn from "@/components/table-tree-column";
import AddTestUserModules from "./testUserAdd";
import UpdateTestUserModules from "./testUserUpdate";
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
      subjectModeType: "-1",
      subjectType: "-1",
      subjectStatus: "-1",
      addVisible: false,
      updateVisible: false,
      dataListLoading: false,
      dataListSelections: []
    };
  },
  components: {
    AddTestUserModules,
    UpdateTestUserModules
  },
  created() {
    this.getDataList();
  },
  mounted() {},
  methods: {
    addHandle(sortId) {
      this.addVisible = true;
      this.$nextTick(() => {
        console.log(this.$refs.addModules);
        this.$refs.addModules.init(sortId);
      });
    },
    updateHandle(nickname) {
      debugger;
      this.updateVisible = true;
      this.$nextTick(() => {
        console.log(this.$refs.updateModules);
        this.$refs.updateModules.init(nickname);
      });
    },
    deleteHandle(id) {
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            url: this.$http.adornUrl("/smallLookGuess/testUser/delete"),
            method: "get",
            params: this.$http.adornParams({
              userId: id
            })
          }).then(({ data }) => {
            if (data && data.Tag === 1) {
              this.$message({
                message: "恭喜你，删除成功！",
                type: "success"
              });
              this.getDataList();
            } else {
              this.$message.error("删除错误，" + data.Message);
            }
          });
        })
        .catch(() => {});
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 获取数据列表
    getDataList() {
      debugger;
      this.dataListLoading = true;
      if (this.keyWords.length > 0) {
        this.pageIndex = 1;
        this.pageSize = 10;
      }
      this.$http({
        url: this.$http.adornUrl("/smallLookGuess/testUser/getPageList"),
        method: "get",
        params: this.$http.adornParams({
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
      this.getDataList();
    }
  }
};
</script>
<style scope >
</style>
