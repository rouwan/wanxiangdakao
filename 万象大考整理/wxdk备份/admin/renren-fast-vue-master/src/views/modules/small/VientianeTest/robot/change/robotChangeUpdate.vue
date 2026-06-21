<template>
  <el-dialog
    title='修改' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form :model="basicData" :rules="dataRule" ref="basicData" label-width="100px">
      
      <el-form-item label="段位">
       <el-select v-model="basicData.paragraph" ref="optionItem" placeholder="请选择" clearable style="margin-left: 15px;">
             <el-option
                v-for="item in optionItem"
                :key="item.level"
                :label="item.name"
                :value="item.level"
                >
            </el-option>
        </el-select> 
      </el-form-item>
       <el-form-item label="类型" >
            <el-select  v-model="basicData.actionType" placeholder="请选择" clearable style="margin-left: 15px; width:210px;">
            <el-option
                v-for="item in robotOptionItem"
                :key="item.type"
                :label="item.name"
                :value="item.type"
                >
            </el-option>
        </el-select> 
       </el-form-item>
      <el-form-item label="权值" prop="value">
       <el-input class="title-input" v-model="basicData.value"  :value="basicData.value" placeholder="权值" style=" border:0px; width:120px;"></el-input>
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
          paragraph:1,
          actionType:'1',
          value:'1',   
          status:0,      
        },
        robotOptionItem:[{name:'简单快机器人',type:'1'},{name:'简单慢机器人',type:'2'},{name:'中等快机器人',type:'3'}, {name:'中等慢机器人',type:'4'},{name:'高等快机器人',type:'5'},{name:'高等慢机器人',type:'6'}],        
        optionItem:[],
        dataRule: {         
          value: [
            { required: true, message: '星数不能为空', trigger: 'blur' }
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
      init (id) {
        if(id == "img[preview]"){return ;}      
        this.basicData.id = id || 0;
        this.visible = true;
        this.$nextTick(() => {
          this.$refs["basicData"].resetFields();
          if (this.basicData.id) {
            this.$http({
              url: this.$http.adornUrl(`/smallVientianeTest/robotChange/getRobotChangeById`),
              method: "get",
              params: this.$http.adornParams({
                id: id
              })
            }).then(({ data }) => {
              if (data && data.Tag === 1) {
                debugger;
                this.basicData.id = data.Result.id;
                this.basicData.paragraph = data.Result.paragraph;
                this.basicData.actionType = data.Result.actionType.toString();
                this.basicData.value = data.Result.value;
                this.basicData.status = data.Result.status == 1 ? true : false;              
              }
            });
          }
        });  
        this.$nextTick(() => {
            this.paragraphSelect();
        });
        this.visible = true;          
        
      },
      paragraphSelect(){
        debugger
        this.$http({
          url: this.$http.adornUrl('/smallVientianeTest/paragraph/getPageList'),
          method: 'get',
          params: this.$http.adornParams({
            'pageIndex': 1,
            'pageSize' : 10000,
            'keyWords' : ''
          })
        }).then(({data}) => {
          debugger
          if (data && data.Tag === 1) {
            debugger
            this.optionItem = data.Result;  
          }else{
            this.$message.error('错误，'+data.Message);
          }
        })
      },
       // 上传之前
      beforeUploadHandle (file) {
        debugger;
         if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
            this.$message.error('只支持jpg、png、gif格式的图片！');
            return false;
        }
      },
      handleAvatarSuccess(response, file, fileList){
          //图片路径        
          this.imageName = response.Result;
          this.imageUrl = this.GLOBALUploadSourceURL+response.Result;
          
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
         debugger;        
        this.$refs['basicData'].validate((valid) => {
        if(valid){     
            var basicData = this.basicData ;
            var id = basicData.id;
            var paragraph = basicData.paragraph;
            var actionType = basicData.actionType;
            var value = basicData.value;
            var status = basicData.status ? 1 : 0;            
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/robotChange/update'),
            method: 'post',
            data: {
              id: id,
              paragraph: paragraph,
              actionType: actionType,
              value: value,
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
