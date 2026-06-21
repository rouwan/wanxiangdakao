<template>
  <el-dialog
    title='修改' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form :model="baseData" :rules="dataRule" ref="baseData"  label-width="100px" >       
      <el-form-item label="类型" prop="type" >
         <el-select  v-model="baseData.type" placeholder="请选择" clearable style="margin-left: 15px;">           
            <el-option key="1" label="战斗力,连胜纪录" value="1" ></el-option>           
        </el-select> 
      </el-form-item>
       <el-form-item label="清空时间" prop="clearTime" style="margin-left: 15px;">
            <el-date-picker
              v-model="baseData.clearTime"
              type="date"
              placeholder="选择日期"> 
            </el-date-picker>
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
        baseData:{
          id:0,
          clearTime:'',
          type:'1'          
        },
        dataRule: {
          type: [
            { required: true, message: '类型不能为空', trigger: 'blur' }
          ],
          clearTime:[
            { required: true, message: '清空时间不能为空', trigger: 'blur' }
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
        this.visible = true;
      },
      // 表单提交
      dataFormSubmit () {
        debugger;
        
        this.$refs['baseData'].validate((valid) => {
          if(valid){     
          // 新增
          
          var thiz = this;
          var time = this.baseData.clearTime.getFullYear() + "-" + (parseInt(this.baseData.clearTime.getMonth()) + 1) + "-" + this.baseData.clearTime.getDate();
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/clearDataTime/add'),
            method: 'post',
            data: {
              clearTime: time,
              type: this.baseData.type
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
