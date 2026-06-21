<template>
  <el-dialog
    title='修改' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form :model="basicData" :rules="dataRule" ref="basicData" label-width="100px">
      <el-form-item label="类型" prop="type" >  
        <el-input-number  v-model="basicData.type" controls-position="right" :min="0" label="类型" style="margin-left: 15px;border:0px; "></el-input-number>            
      </el-form-item>
       <el-form-item label="名称" prop="name">
        <el-input class="title-input" v-model="basicData.name"  :value="basicData.name" style=" border:0px; width:220px;"></el-input>
      </el-form-item>
      <el-form-item label="图片" prop="image">
            <el-upload
                  class="avatar-uploader"
                  :action="reqImageUrl"
                  :show-file-list="false"
                  :before-upload="beforeUploadHandle"
                  :on-success="handleAvatarSuccess">
                  <img v-if="imageUrl" :src="imageUrl" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
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
          type:"",
          name:"",
          image:''
        },
        typeName:["小体力瓶","小体力瓶","中体力瓶","大体力瓶","小宝箱","中宝箱","大宝箱","商","文","理","艺","体","综"],
        dataRule: {
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ]
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
                url: this.$http.adornUrl(`/smallVientianeTest/goods/getGoodsById`),
                method: "get",
                params: this.$http.adornParams({
                  id: id
                })
              }).then(({ data }) => {
                if (data && data.Tag === 1) {
                  debugger;
                  this.basicData.id = data.Result.id;
                  this.basicData.type = data.Result.type.toString();
                  this.basicData.name = data.Result.name;
                  this.imageName = data.Result.image;
                  this.imageUrl = this.GLOBALUploadSourceURL + data.Result.image;
                }
              });
            }
        });
        this.reqImageUrl = this.$http.adornUrl(`/smallVientianeTest/goods/uploadImage?token=${this.$cookie.get("token")}`);
        this.visible = true;
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
      // 表单提交
      dataFormSubmit () {
         debugger;
          if(this.imageName.length<=0){
            this.$message.error('请上传图片！');
            return;
          }
        
        this.$refs['basicData'].validate((valid) => {
        if(valid){     
          var remark = "";
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/goods/update'),
            method: 'post',
            data: {
              id: this.basicData.id,
              type: this.basicData.type,
              name: this.basicData.name,
              image: this.imageName
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
