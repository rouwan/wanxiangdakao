<template>
  <el-dialog
    title='修改' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form :model="dataConfig" :rules="dataRule" ref="dataConfig"  label-width="100px">
        <el-form-item label="键/值" prop="value">
            <el-input v-model="dataConfig.name" :value="dataConfig.name" class="title-input" style=" border:0px;width: 230px;" :disabled="isDisabled"></el-input>
            ->
            <el-input v-model="dataConfig.value" :value="dataConfig.value" class="title-input" style=" border:0px;width: 105px;"></el-input>
        </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input type="textarea" rows="3" v-model="dataConfig.description" :value="dataConfig.description" class="title-input" style=" border:0px;width: 360px;"></el-input>
      </el-form-item>
      <el-form-item label="是否下发手机" >
         <el-checkbox-group v-model="dataConfig.type">
           <el-checkbox  :checked="dataConfig.type" :disabled="isDisabled"></el-checkbox>
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
        isDisabled:false,
        dataConfig:{
          id:0,
          name:'',
          value:'',
          description:'',
          type:false
                    
        },
        dataRule: {
          value: [
            { required: true, message: '键/值不能为空', trigger: 'blur' }
          ],
          description:[
            { required: true, message: '答案释义不能为空', trigger: 'blur' }
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
        if(id == "img[preview]"){return ;}
        this.dataConfig.id = id || 0;
        this.visible = true;
        this.isDisabled = !this.isAuth('small:dataConfig:update');
        this.$nextTick(() => {
          this.$refs['dataConfig'].resetFields()
          if (this.dataConfig.id) {
            this.$http({
              url: this.$http.adornUrl(`/smallLookGuess/dataConfig/getDataConfigById`),
              method: 'get',
              params: this.$http.adornParams({
                  'id':id
              })
            }).then(({data}) => {
              if (data && data.Tag === 1) {         
                   this.dataConfig.name = data.Result.name; 
                   this.dataConfig.value = data.Result.value; 
                   this.dataConfig.description = data.Result.description;
                   this.dataConfig.type = data.Result.type == 1 ?　true : false;
                   
              }
            })
          }
        })

      },
      // 表单提交
      dataFormSubmit () {
        debugger;
        
        this.$refs['dataConfig'].validate((valid) => {
          if(valid){     
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallLookGuess/dataConfig/update'),
            method: 'post',
            data: {
              id:this.dataConfig.id,
              name: this.dataConfig.name,
              value: this.dataConfig.value,
              description: this.dataConfig.description,
              type: this.dataConfig.type ? 1 : 0 
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
                this.$message.error('修改错误，'+data.Message);
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
