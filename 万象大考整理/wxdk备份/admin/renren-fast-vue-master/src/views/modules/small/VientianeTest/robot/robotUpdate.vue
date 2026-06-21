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
       <el-form-item label="赛事" prop="type" >
           <el-select  v-model="basicData.type" placeholder="请选择" clearable style="margin-left: 15px; width:110px;">
            <el-option key="0" label="通用" value="0" ></el-option>
            <el-option key="1" label="排位" value="1" ></el-option>
            <el-option key="2" label="混战" value="2" ></el-option>
        </el-select> 
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
        <el-form-item label="简单题" prop="simpleRatio">
            <el-input class="title-input" v-model="basicData.simpleRatio"  :value="basicData.simpleRatio" placeholder="正确率" style=" border:0px; width:120px;"></el-input>
            时间<el-input class="title-input" v-model="basicData.simpleMinTime" :value="basicData.simpleMinTime" placeholder="最小值" style=" border:0px; width:120px;"></el-input>--
            <el-input class="title-input" v-model="basicData.simpleMaxTime" :value="basicData.simpleMaxTime" placeholder="最大值" style=" border:0px; width:120px;"></el-input>
      </el-form-item>
      <el-form-item label="一般题" prop="simpleRatio">
            <el-input class="title-input" v-model="basicData.commonRatio" :value="basicData.commonRatio" placeholder="正确率" style=" border:0px; width:120px;"></el-input>
            时间<el-input class="title-input" v-model="basicData.commonMinTime" :value="basicData.commonRatio" placeholder="最小值" style=" border:0px; width:120px;"></el-input>--
            <el-input class="title-input" v-model="basicData.commonMaxTime" :value="basicData.commonRatio" placeholder="最大值" style=" border:0px; width:120px;"></el-input>
      </el-form-item>
        <el-form-item label="困难题" prop="simpleRatio">
             <el-input class="title-input" v-model="basicData.hardRatio"  :value="basicData.hardRatio" placeholder="正确率" style=" border:0px; width:120px;"></el-input>
            时间<el-input class="title-input" v-model="basicData.hardMinTime" :value="basicData.hardRatio"  placeholder="最小值" style=" border:0px; width:120px;"></el-input>--
            <el-input class="title-input" v-model="basicData.hardMaxTime" :value="basicData.hardRatio" placeholder="最大值" style=" border:0px; width:120px;"></el-input>
      </el-form-item>
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
      <el-form-item label="星数" prop="star">
       <el-input class="title-input" v-model="basicData.star"  :value="basicData.star" placeholder="星数" style=" border:0px; width:120px;"></el-input>
      </el-form-item>
       <el-form-item label="是否发布">
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
          type:'1',
          status:false,
          country:'中国',
          province:'福建',
          city:'厦门',
          simpleRatio:'50',
          simpleMinTime : '1',
          simpleMaxTime : '5',
          commonRatio:'40',
          commonMinTime : '3',
          commonMaxTime : '6',
          hardRatio:'20',
          hardMinTime : '3',
          hardMaxTime : '7',
          paragraph:'',
          star:'0'
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
          type: [
             { required: false, message: '类型不能为空', trigger: 'blur' }
          ],
          simpleRatio: [
            { required: true, message: '正确率和时间不能为空', trigger: 'blur' }
          ],
          country: [
            { required: true, message: '地区不能为空', trigger: 'blur' }
          ],
          star: [
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
              url: this.$http.adornUrl(`/smallVientianeTest/robot/getRobotById`),
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
                this.basicData.type = data.Result.type.toString();
                this.basicData.status = data.Result.status == 1 ? true : false;
                this.imageName = data.Result.headImgUrl;
                this.imageUrl = this.GLOBALUploadSourceURL + data.Result.headImgUrl;
                this.basicData.country = data.Result.country;
                this.basicData.province = data.Result.province;
                this.basicData.city = data.Result.city;
                this.basicData.simpleRatio = data.Result.simpleRatio;
                this.basicData.simpleMinTime = data.Result.simpleTime.split('-')[0];
                this.basicData.simpleMaxTime = data.Result.simpleTime.split('-')[1];
                this.basicData.commonRatio = data.Result.commonRatio;
                this.basicData.commonMinTime = data.Result.commonTime.split('-')[0];
                this.basicData.commonMaxTime = data.Result.commonTime.split('-')[1];
                this.basicData.hardRatio = data.Result.hardRatio; 
                this.basicData.hardMinTime = data.Result.hardTime.split('-')[0];
                this.basicData.hardMaxTime = data.Result.hardTime.split('-')[1];
                this.basicData.paragraph = data.Result.paragraph; 
                this.basicData.star = data.Result.star; 
              }
            });
          }
        });
        this.reqImageUrl = this.$http.adornUrl(`/smallVientianeTest/robot/uploadImage?token=${this.$cookie.get("token")}`);
        this.visible = true;
        this.$nextTick(() => {
            this.paragraphSelect();
        });
        
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
        if(this.imageName.length<=0){
          this.$message.error('请上传图片！');
          return;
        }
        if(this.basicData.type < 0 || this.basicData.type.length<=0){
          this.$message.error('请选择赛事！');
          return; 
        }
        if(this.basicData.sexDesc.length<=0){
          this.$message.error('请选择性别！');
          return; 
        }
        if(this.basicData.paragraph.length<=0){
          this.$message.error('请选择段位！');
          return; 
        }
        if(!this.isNumber(this.basicData.simpleRatio) || !this.isNumber(this.basicData.simpleMinTime) || !this.isNumber(this.basicData.simpleMaxTime) ||
            !this.isNumber(this.basicData.commonRatio) || !this.isNumber(this.basicData.commonMinTime) || !this.isNumber(this.basicData.commonMaxTime) ||
            !this.isNumber(this.basicData.hardRatio) || !this.isNumber(this.basicData.hardMinTime) || !this.isNumber(this.basicData.hardMaxTime)){
          this.$message.error('请输入数字');
          return; 
        }
        
        this.$refs['basicData'].validate((valid) => {
        if(valid){     

            var basicData = this.basicData ;
            var id = basicData.id;
            var nickname = basicData.nickname;
            var sexDesc = basicData.sexDesc;
            var headImgUrl = this.imageName;
            var type = basicData.type;
            var status = basicData.status ? 1 : 0;
            var country = basicData.country;
            var province = basicData.province;
            var city = basicData.city;
            var simpleRatio = basicData.simpleRatio;
            var simpleTime = basicData.simpleMinTime+"-"+basicData.simpleMaxTime;
            var commonRatio = basicData.commonRatio;
            var commonTime = basicData.commonMinTime+"-"+basicData.commonMaxTime;
            var hardRatio = basicData.hardRatio;
            var hardTime = basicData.hardMinTime+"-"+basicData.hardMaxTime;
            var paragraph = basicData.paragraph;
            var star = basicData.star;
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/robot/update'),
            method: 'post',
            data: {
              id: id,
              nickName: nickname,
              sexDesc: sexDesc,
              headImgUrl: headImgUrl,
              type: type,
              status: status,
              country: country,
              province: province,
              city: city,
              simpleRatio:simpleRatio,
              simpleTime:simpleTime,
              commonRatio:commonRatio,
              commonTime:commonTime,
              hardRatio:hardRatio,
              hardTime:hardTime,
              paragraph:paragraph,
              star:star
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
