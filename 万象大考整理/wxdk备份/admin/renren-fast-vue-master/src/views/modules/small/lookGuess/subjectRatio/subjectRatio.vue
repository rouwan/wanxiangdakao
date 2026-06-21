<template>
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane label="列表" name="first">
      <SubjectRaioList ref="subjectRaioList"  @dataList="getDataList" ></SubjectRaioList>
    </el-tab-pane>
    <el-tab-pane label="图形" name="second">
      <SubjectRatioChar ref="subjectRatioChar" v-if="isVisibleChar" ></SubjectRatioChar>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
  import SubjectRaioList from './subjectRatioList'
  import SubjectRatioChar from './subjectRatioChar'
  export default {
    data() {
      return {
        isVisibleChar:false,
        activeName: 'first',
        dataList:[],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0
      };
    },
    components:{
      SubjectRaioList,
      SubjectRatioChar
    },
    methods: {
      getDataList(data){
        debugger;
        this.dataList = data[0];
        this.pageIndex = data[1];
        this.pageSize = data[2];
        this.totalPage = data[3];
      },
      handleClick(tab, event) {
        if(this.activeName == "first"){
          this.isVisibleChar = false;
        }
        else if(this.activeName == "second"){
          this.isVisibleChar = true;
          this.$nextTick(() => {
           this.$refs.subjectRatioChar.init(this.dataList,this.pageIndex,this.pageSize,this.totalPage);
          });
        }
      }
    }
  };
</script>