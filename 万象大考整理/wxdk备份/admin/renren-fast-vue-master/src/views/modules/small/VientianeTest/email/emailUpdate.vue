<template>
  <el-dialog
    title='修改' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form :model="email" :rules="dataRule" ref="email"  label-width="100px">
        <el-form-item label="标题" prop="title">
            <el-input v-model="email.title" :value="email.title" class="title-input" style=" border:0px;width: 230px;"></el-input>
        </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input type="textarea" rows="3" v-model="email.content" :value="email.content" class="title-input" style=" border:0px;width: 360px;"></el-input>
      </el-form-item>
      <el-form-item label="奖品" style="margin-top: 50px;">
        <el-checkbox v-for="gift in giftList"  :key="gift.type" :label="gift.name" :checked="gift.checked" v-model="gift.checked">{{gift.name}} 
          <el-input v-model="gift.num" :value="gift.num" class="title-input" style=" border:0px;width: 85px;"></el-input>
        </el-checkbox>
      </el-form-item> 
      <el-form-item label="是否下发手机" >
         <el-checkbox-group v-model="email.status">
           <el-checkbox :checked="email.status"></el-checkbox>
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
        giftList:[{checked:false,type:1,num:0,name:'小体力瓶'},
                  {checked:false,type:2,num:0,name:'中体力瓶'},
                  {checked:false,type:3,num:0,name:'大体力瓶'}], 
        email:{
          id:0,
          title:'',
          content:'',
          gift:'',
          status:false          
        },
        dataRule: {
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          content:[
            { required: true, message: '内容不能为空', trigger: 'blur' }
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
        debugger
        debugger
        if(id == "img[preview]"){return ;}
        this.email.id = id || 0;
        this.visible = true;
        this.isDisabled = !this.isAuth('small:dataConfig:update');
        this.$nextTick(() => {
          this.$refs['email'].resetFields()
          if (this.email.id) {
            this.$http({
              url: this.$http.adornUrl(`/smallVientianeTest/email/getEmailById`),
              method: 'get',
              params: this.$http.adornParams({
                  'id':id
              })
            }).then(({data}) => {
              if (data && data.Tag === 1) {         
                   this.email.id = data.Result.id; 
                   this.email.title = data.Result.title; 
                   this.email.content = data.Result.content; 
                   this.email.gift = data.Result.gift;
                   var giftArr = data.Result.gift.split(";");
                    debugger
                   for(var i=0;i<giftArr.length;i++){
                      if(giftArr[i].length<=0){
                        continue;
                      }
                      var giftType = giftArr[i].split(",")[0];
                      var giftNum = giftArr[i].split(",")[1];
                      debugger
                      this.giftList[giftType-1].checked = true;
                      this.giftList[giftType-1].num = giftNum;
                   }
                   this.email.status = data.Result.status == 1 ?　true : false;
                   
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        var gift = "";
        var giftError = "";

        for(var i=0;i<this.giftList.length;i++){
            if(this.giftList[i].checked){
              if(this.giftList[i].num <= 0){
                giftError += "体力数量不能小于1，请修改！";
              }
              debugger
              gift += this.giftList[i].type + "," + this.giftList[i].num + ";";
          }
        }      
        if(giftError.length > 0){
           this.$message.error('添加错误，'+ giftError);
           return;
        }
        this.email.gift = gift;
        this.$refs['email'].validate((valid) => {
          if(valid){     
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/email/update'),
            method: 'post',
            data: {
              id: this.email.id,
              title: this.email.title,
              content: this.email.content,
              gift: this.email.gift,
              status: this.email.status ? 1 : 0 
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
