<template >
  <el-dialog
    title='导入Word' 
    width="25%"
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form label-width="100px">
       <el-upload
        class="upload-demo"
        ref="upload"
        :action="reqExcelUrl"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-upload="beforeUploadHandle"
        :on-success="handleUploadSuccess"
        :show-file-list="true"
        :auto-upload="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
        <div slot="tip" class="el-upload__tip">只能上传docx文件</div>
        </el-upload>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDialog()">取消</el-button>
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
        fileList:[],
        reqExcelUrl:'',
        ExcelUrl:'',
        ExcelName:''
      }
    },
    created () {
     
    },
    destroyed(){
     
    },
    methods: {
      init(){
        this.visible = true;    
        this.reqExcelUrl = this.$http.adornUrl(`/smallLookGuess/uploadExcel?token=${this.$cookie.get('token')}`);
      },
      beforeUploadHandle (file) {   
        debugger;
        if (file.name.indexOf('.docx') < 0) {
            this.$message.error('只能上传docx文件');
            return false;
        }
        // if(this.fileList.length != 1){
        //     this.$message.error('最多只能上传一个文件');
        //     return false;
        // }
      },
      handleUploadSuccess(response, file, fileList){
          if(response.Tag < 0){
             this.$message.error(response.Message);
          }else{
             this.$message({
                    message: '恭喜你，导入成功！',
                    type: 'success'
                  }); 
             this.closeDialog();
             this.$emit('refreshDataList');
          }
      },
      submitUpload() {
        this.$refs.upload.submit();
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      closeDialog(){
        this.$emit('close');
      }     
    }
  }
</script>
