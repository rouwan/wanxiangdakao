<template>
  <el-dialog
    title='修改' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form :model="basicData" :rules="dataRule" ref="basicData" label-width="100px">
      <el-form-item label="物品类型">    
        <el-select v-model="basicData.goodType" ref="optionItem" placeholder="请选择" clearable style="margin-left: 15px;">
             <el-option
                v-for="item in optionItem"
                :key="item.type"
                :label="item.name"
                :value="item.type"
                >
            </el-option>
        </el-select>             
         <!-- <el-select  v-model="basicData.type" placeholder="请选择" clearable style="margin-left: 15px;">            
            <el-option key="1" label="小体力瓶" value="1" ></el-option>
            <el-option key="2" label="中体力瓶" value="2"  ></el-option>
            <el-option key="3" label="大体力瓶" value="3" ></el-option>
            <el-option key="4" label="小宝箱" value="4"  ></el-option>
            <el-option key="5" label="中宝箱" value="5"  ></el-option>
            <el-option key="6" label="大宝箱" value="6"  ></el-option>
            <el-option key="7" label="商" value="7"  ></el-option>
            <el-option key="8" label="文" value="8"  ></el-option>
            <el-option key="9" label="理" value="9"  ></el-option>
            <el-option key="10" label="艺" value="10"  ></el-option>
            <el-option key="11" label="体" value="11"  ></el-option>
            <el-option key="12" label="综" value="12"  ></el-option>
        </el-select>  -->
      </el-form-item> 
      <el-form-item label="最小值" prop="value">
        <el-input-number v-model="basicData.minValue" controls-position="right" :min="0" label="概率值" style="margin-left: 15px;border:0px; "></el-input-number> - 
        <el-input-number v-model="basicData.maxValue" controls-position="right" :min="0" label=""></el-input-number>
      </el-form-item>
      <el-form-item label="数量" prop="value">
        <el-input-number v-model="basicData.num" controls-position="right" :min="0" label="数量" style="margin-left: 15px;border:0px; "></el-input-number>        
      </el-form-item>
       <el-form-item label="描述" prop="description">
        <el-input type="textarea" rows="3" v-model="basicData.description" :value="basicData.description" class="title-input" style=" border:0px;"></el-input>
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
          minValue:0,
          maxValue:0,
          image:'', 
          num:0,
          goodType:'',
          description:'',
        },
        optionItem:[],
        typeName:["小体力瓶","小体力瓶","中体力瓶","大体力瓶","小宝箱","中宝箱","大宝箱","商","文","理","艺","体","综"],
        dataRule: {
          type: [
            { required: true, message: '类型不能为空', trigger: 'blur' }
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
          this.initGoodsType().then((resolve)=>{
            this.$nextTick(() => {            
              this.$refs["basicData"].resetFields();
              if (this.basicData.id) {
                this.$http({
                  url: this.$http.adornUrl(`/smallVientianeTest/prize/getPrizeById`),
                  method: "get",
                  params: this.$http.adornParams({
                    id: id
                  })
                }).then(({ data }) => {
                  if (data && data.Tag === 1) {
                    debugger;
                    this.basicData.id = data.Result.id;
                    this.basicData.goodType = parseInt(data.Result.goodType);                 
                    this.basicData.minValue = data.Result.minValue;
                    this.basicData.maxValue = data.Result.maxValue;
                    this.basicData.description = data.Result.description;
                    this.basicData.num = data.Result.num;
                    this.imageName = data.Result.image;
                    this.imageUrl = this.GLOBALUploadSourceURL + data.Result.image;
                  }
                });
              }
            });
        });               
      },
      initGoodsType() {
         return new Promise((resolve, reject)=>{
            this.$http({
              url: this.$http.adornUrl('/smallVientianeTest/goods/getPageList'),
              method: 'get',
              params: this.$http.adornParams({
                'keyWords': this.keyWords ,
                'pageIndex': 1,
                'pageSize': 100000
              })
            }).then(({data}) => {
              debugger
              if (data && data.Tag === 1) {
                debugger
                this.basicData.goodType = '';
                this.optionItem = data.Result;  
              }else{
                this.$message.error('错误，'+data.Message);
              }
               resolve();  
            })
         });
      },    
      // 表单提交
      dataFormSubmit () {        
        this.$refs['basicData'].validate((valid) => {
        if(valid){     
          var remark = "";
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/prize/update'),
            method: 'post',
            data: {
              id: this.basicData.id,            
              goodType: this.basicData.goodType,
              minValue: this.basicData.minValue,
              maxValue: this.basicData.maxValue,
              image: this.imageName,
              num: this.basicData.num,
              description: this.basicData.description,
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
