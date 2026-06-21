<template>
  <el-dialog
    title='新增' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form :model="basicData" :rules="dataRule" ref="basicData" label-width="100px">
       <el-form-item label="类型" >
            <el-select  v-model="basicData.type" placeholder="请选择" clearable style="margin-left: 15px; width:210px;">
            <el-option
                v-for="item in robotOptionItem"
                :key="item.type"
                :label="item.name"
                :value="item.type"
                >
            </el-option>
        </el-select> 
       </el-form-item>
        <el-form-item label="简单题正确率">
           <el-select  v-model="basicData.simpleRatio" placeholder="请选择" clearable style="margin-left: 15px; width:210px;">
            <el-option
                v-for="item in ratioOptionItem"
                :key="item.ratio"
                :label="item.name"
                :value="item.ratio"
                >
            </el-option>
        </el-select> 
       </el-form-item>
       <el-form-item label="中等题正确率">
           <el-select  v-model="basicData.commonRatio" placeholder="请选择" clearable style="margin-left: 15px; width:210px;">
            <el-option
                v-for="item in ratioOptionItem"
                :key="item.ratio"
                :label="item.name"
                :value="item.ratio"
                >
            </el-option>
        </el-select> 
       </el-form-item>
       <el-form-item label="困难题正确率">
           <el-select  v-model="basicData.hardRatio" placeholder="请选择" clearable style="margin-left: 15px; width:210px;">
            <el-option
                v-for="item in ratioOptionItem"
                :key="item.ratio"
                :label="item.name"
                :value="item.ratio"
                >
            </el-option>
        </el-select> 
       </el-form-item>
         <el-form-item label="答题时间">
           <el-select  v-model="basicData.answerTime" placeholder="请选择" clearable style="margin-left: 15px; width:210px;">
            <el-option
                v-for="item in timeOptionItem"
                :key="item.time"
                :label="item.name"
                :value="item.time"
                >
            </el-option>
        </el-select> 
       </el-form-item>      
       <el-form-item label="是否发布" style="margin-left: 15px;">
         <el-checkbox-group v-model="basicData.status">
           <el-checkbox :checked="basicData.status"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDialog()">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { treeDataTranslate } from '@/utils'
  import Icon from '@/icons'
  export default {
    data () {
      return {
        visible: false,
        reqImageUrl:'',
        imageUrl:'',
        imageName:'',
        basicData:{
          id:0,
          type:'1',
          status:false,
          simpleRatio:'30-50',      
          commonRatio:'30-50',    
          hardRatio:'30-50',
          answerTime : '1-4',
        },
        robotOptionItem:[{name:'简单快机器人',type:'1'},{name:'简单慢机器人',type:'2'},{name:'中等快机器人',type:'3'}, {name:'中等慢机器人',type:'4'},{name:'高等快机器人',type:'5'},{name:'高等慢机器人',type:'6'}],
        ratioOptionItem:[{name:'低',ratio:'30-50'},{name:'中',ratio:'40-60'},{name:'高',ratio:'50-70'}],
        timeOptionItem:[{name:'快',time:'1-4'},{name:'慢',time:'1-6'}],
        dataRule: {     
           name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],   
        }
      }
    },
    created () {
      this.iconList = Icon.getNameList();
    },
    destroyed(){
      console.log("over!!!");
    },
    methods: {
      init (sortId) {
        if(sortId == "img[preview]"){return ;}        
        this.visible = true;          
      },      
      isNumber(value) {
          var reg = /^[0-9]*$/;
          if (!reg.test(value) || value.length <= 0) {
            return false;
          }
          return true;
      },
      // 表单提交
      dataFormSubmit () {                
        this.$refs['basicData'].validate((valid) => {
        if(valid){     
            var basicData = this.basicData ;    
            var type = basicData.type;        
            var simpleRatio = basicData.simpleRatio;
            var commonRatio = basicData.commonRatio;
            var hardRatio = basicData.hardRatio;
            var answerTime = basicData.answerTime;
            var status = basicData.status ? 1 : 0;
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/robotAction/add'),
            method: 'post',
            data: {
              type: type,
              simpleRatio: simpleRatio,
              commonRatio: commonRatio,
              hardRatio: hardRatio,
              answerTime: answerTime,
              status: status,         
            }
          }).then(({data}) => {
              if (data && data.Tag === 1) {
                   this.$message({
                    message: '恭喜你，添加成功！',
                    type: 'success'
                  }); 
                  thiz.closeDialog();
                  thiz.$emit('refreshDataList');
              }else{
                this.$message.error('添加错误，'+data.Message);
              }
              //debugger
            })
          }
         })
      },
      closeDialog(){
        this.$emit('close');
      }
    }
  }
</script>

<style lang="scss">
  .title-input{
    background-image: none;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    color: #606266;
    display: inline-block;
    font-size: inherit;
    height: 40px;
    line-height: 40px;
    outline: 0;
    padding: 0 15px;
    -webkit-transition: border-color .2s cubic-bezier(.645,.045,.355,1);
    transition: border-color .2s cubic-bezier(.645,.045,.355,1);
    width:43%;
    margin-bottom: 5px;
  } 
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    margin-left: 15px;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 140px;
    height: 140px;
    line-height: 140px;
    text-align: center;
  }
  .avatar {
    width: 140px;
    height: 140px;
    display: block;
  }
</style>
