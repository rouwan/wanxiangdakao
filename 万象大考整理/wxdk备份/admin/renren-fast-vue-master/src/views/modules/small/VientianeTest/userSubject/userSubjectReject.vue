<template>
  <el-dialog
    title='驳回' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    width="480px"
    >
    <el-form :rules="dataRule" ref="basicData"  label-width="100px">
       <el-form-item label="驳回理由">
        <el-input type="textarea" rows="3" v-model="rejectRemark" :value="rejectRemark" class="title-input" style=" border:0px; width:100%"></el-input>
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
        ids:'',    
        rejectRemark:'',
        dataRule: {
          rejectRemark: [{ required: true, message: '驳回理由不能为空', trigger: 'blur' }]
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
      init(ids,rejectRemark) {
        //debugger
        if (ids == "img[preview]") {return;}
        this.ids = ids;
        this.rejectRemark = rejectRemark;
        this.visible = true;
      },  
      // 表单提交
      dataFormSubmit () {
        debugger;
        if(this.ids.length <= 0){
            this.$message.error('请选择题目！');
            return;
        }
        else if(this.rejectRemark.length<=0 ){
            this.$message.error('请填写驳回理由！');
            return;
        }
        this.$http({
          url: this.$http.adornUrl('/smallVientianeTest/userSubject/updateRejectRemark'),
          method: 'post',
          data: {
            ids: this.ids,
            rejectRemark:this.rejectRemark
          }
        }).then(({data}) => { 
            if (data && data.Tag === 1) {
                  this.$message({
                  message: '恭喜你，提交成功！',
                  type: 'success'
                }); 
                this.closeDialog();
                this.$emit('refreshDataList');
            }else{
              this.$message.error('添加错误，'+data.Message);
            }
            //debugger
        })
      },
      closeDialog(){
        this.$emit('close');
      }
    }
  }
</script>
