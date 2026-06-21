<template>
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane label="列表" name="first">
      <SubjectLevelTypeRatioList ref="subjectLevelTypeRatioList"  @dataList="getDataList" ></SubjectLevelTypeRatioList>
    </el-tab-pane>
    <el-tab-pane label="图形" name="second">
      <SubjectLevelTypeRatioChar ref="subjectLevelTypeRatioChar" v-if="isVisibleChar" ></SubjectLevelTypeRatioChar>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
  import SubjectLevelTypeRatioList from './subjectLevelTypeRatioList'
  import SubjectLevelTypeRatioChar from './subjectLevelTypeRatioChar'
  export default {
    data() {
      return {
        isVisibleChar:false,
        activeName: 'first',
        dataList:[],
      };
    },
    components:{
      SubjectLevelTypeRatioList,
      SubjectLevelTypeRatioChar
    },
    methods: {
      getDataList(data){
        debugger;
        this.dataList = data[0];
      },
      handleClick(tab, event) {
        if(this.activeName == "first"){
          this.isVisibleChar = false;
        }
        else if(this.activeName == "second"){
          this.isVisibleChar = true;
          this.$nextTick(() => {
           this.$refs.subjectLevelTypeRatioChar.init(this.dataList);
          });
        }
      }
    }
  };
</script>