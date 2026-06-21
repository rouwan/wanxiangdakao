<template>
  <el-dialog
    title='修改' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form :model="basicData" :rules="dataRule" ref="basicData" label-width="100px">
      <el-form-item label="昵称" prop="nickname">
            <el-input v-model="basicData.nickname" :value="basicData.nickname" class="title-input" style=" border:0px;"></el-input>
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
        <el-form-item label="性别" prop="sexDesc">
           <el-select  v-model="basicData.sexDesc" placeholder="请选择" clearable style="margin-left: 15px; width:110px;">
            <el-option key="男" label="男" value="男" ></el-option>
            <el-option key="女" label="女" value="女" ></el-option>
        </el-select> 
       </el-form-item>
       <el-form-item label="地区" prop="country">
            <el-input class="title-input" v-model="basicData.country"  :value="basicData.country" placeholder="国家" style=" border:0px; width:120px;"></el-input>&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;
            <el-input class="title-input" v-model="basicData.province"  :value="basicData.province" placeholder="省份" style=" border:0px; width:120px;"></el-input>--
            <el-input class="title-input" v-model="basicData.city"  :value="basicData.city" placeholder="市区" style=" border:0px; width:120px;"></el-input>
      </el-form-item>      
       <el-form-item label="是否发布" style="margin-left: 15px; ">
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
          nickname:'',
          sexDesc:'男',
          headImgUrl:'',        
          status:false,
          country:'中国',
          province:'福建',
          city:'厦门',        
        },
        optionItem:[],
        dataRule: {
          nickname: [
            { required: true, message: '昵称不能为空', trigger: 'blur' }
          ],
          sexDesc:[
            { required: true, message: '性别不能为空', trigger: 'blur' }
          ],
          headImgUrl: [
            { required: true, message: '头像不能为空', trigger: 'blur' }
          ],         
          country: [
            { required: true, message: '地区不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/smallVientianeTest/robotBasicInfo/getRobotBasicInfoById`),
              method: "get",
              params: this.$http.adornParams({
                id: id
              })
            }).then(({ data }) => {
              if (data && data.Tag === 1) {
                debugger;
                this.basicData.id = data.Result.id;
                this.basicData.nickname = data.Result.nickname;
                this.basicData.sexDesc = data.Result.sexDesc;        
                this.basicData.status = data.Result.status == 1 ? true : false;
                this.imageName = data.Result.headImgUrl;
                this.imageUrl = this.GLOBALUploadSourceURL + data.Result.headImgUrl;
                this.basicData.country = data.Result.country;
                this.basicData.province = data.Result.province;
                this.basicData.city = data.Result.city;              
              }
            });
          }
        });
        this.reqImageUrl = this.$http.adornUrl(`/smallVientianeTest/robotBasicInfo/uploadImage?token=${this.$cookie.get("token")}`);
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
        if(this.imageName.length<=0){
          this.$message.error('请上传图片！');
          return;
        }
      
        if(this.basicData.sexDesc.length<=0){
          this.$message.error('请选择性别！');
          return; 
        }
        
        this.$refs['basicData'].validate((valid) => {
        if(valid){     

            var basicData = this.basicData ;
            var id = basicData.id;
            var nickname = basicData.nickname;
            var sexDesc = basicData.sexDesc;
            var headImgUrl = this.imageName;          
            var status = basicData.status ? 1 : 0;
            var country = basicData.country;
            var province = basicData.province;
            var city = basicData.city;          
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/robotBasicInfo/update'),
            method: 'post',
            data: {
              id: id,
              nickName: nickname,
              sexDesc: sexDesc,
              headImgUrl: headImgUrl,
              status: status,
              country: country,
              province: province,
              city: city,             
            }
          }).then(({data}) => {
              if (data && data.Tag === 1) {
                   this.$message({
                    message: '恭喜你，修改成功！',
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
