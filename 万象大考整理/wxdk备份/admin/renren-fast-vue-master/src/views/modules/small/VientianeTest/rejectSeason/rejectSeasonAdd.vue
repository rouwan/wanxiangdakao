<template>
  <el-dialog
    title='添加' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    width="480px"
    >
    <el-form :model="basicData" :rules="dataRule" ref="basicData"  label-width="100px">
       <el-form-item label="理由" prop="season">
        <el-input type="textarea" rows="2" v-model="basicData.season" :value="basicData.season" class="title-input" style=" border:0px; width:100%"></el-input>
      </el-form-item>
       <el-form-item label="说明" prop="description">
        <el-input type="textarea" rows="2" v-model="basicData.description" :value="basicData.description" class="title-input" style=" border:0px; width:100%"></el-input>
      </el-form-item>
       <el-form-item label="修改建议" prop="advice">
        <el-input type="textarea" rows="1" v-model="basicData.advice" :value="basicData.advice" class="title-input" style=" border:0px; width:100%"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sortId" >  
        <el-input-number  v-model="basicData.sortId" controls-position="right" :min="0" label="类型" style="border:0px; "></el-input-number>            
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
        basicData:{
          id:0,
          season:'',
          description:'',
          advice:'',
          sortId:1
        },
        dataRule: {
          sortId: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          season: [
            { required: true, message: '理由不能为空', trigger: 'blur' }
          ],
          description: [
            { required: true, message: '说明不能为空', trigger: 'blur' }
          ],
          advice: [
            { required: true, message: '建议不能为空', trigger: 'blur' }
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
        
        this.$refs['basicData'].validate((valid) => {
          if(valid){     
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/rejectSeason/add'),
            method: 'post',
            data: {
              advice:this.basicData.advice,
              season:this.basicData.season,
              description:this.basicData.description,
              sortId:this.basicData.sortId
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
