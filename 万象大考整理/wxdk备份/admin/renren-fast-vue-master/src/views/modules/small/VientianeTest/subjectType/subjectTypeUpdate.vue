<template>
  <el-dialog
    title='修改' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    width="480px"
    >
    <el-form :model="basicData" :rules="dataRule" ref="basicData"  label-width="100px">
      <el-form-item label="父类类型" >
         <el-select  v-model="basicData.parentType" placeholder="请选择" clearable>
            <el-option key="1" label="德" value="1" ></el-option>
            <el-option key="2" label="商" value="2" ></el-option>
            <el-option key="3" label="文" value="3" ></el-option>
            <el-option key="4" label="理" value="4" ></el-option>
            <el-option key="5" label="艺" value="5" ></el-option>
            <el-option key="6" label="体" value="6" ></el-option>
            <el-option key="7" label="综" value="7" ></el-option>
        </el-select> 
      </el-form-item>
        <el-form-item label="类型名称" prop="name">
            <el-input v-model="basicData.name" :value="basicData.name" style=" border:0px;width:100% "></el-input>
        </el-form-item>
        <el-form-item label="顺序" prop="sortId">
         <el-input-number v-model="basicData.sortId" controls-position="right" :min="1" style="" ></el-input-number>
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
          parentType:'1',
          name:'',
          sortId:0
        },
        dataRule: {
          sortId: [
            { required: true, message: '顺序不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '类型名称不能为空', trigger: 'blur' }
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
        this.basicData.id = id || 0;
        this.visible = true;
        this.$nextTick(() => {
          this.$refs['basicData'].resetFields()
          if (this.basicData.id) {
            this.$http({
              url: this.$http.adornUrl(`/smallVientianeTest/subjectType/getSubjectTypeById`),
              method: 'get',
              params: this.$http.adornParams({
                  'id':id
              })
            }).then(({data}) => {
              if (data && data.Tag === 1) {     
                   debugger 
                   this.basicData.id = data.Result.id;    
                   this.basicData.parentType= data.Result.parentType.toString();
                   this.basicData.name = data.Result.name; 
                   this.basicData.sortId = data.Result.sortId; 
              }
            })
          }
        })

      },
      // 表单提交
      dataFormSubmit () {
        debugger;
        
        this.$refs['basicData'].validate((valid) => {
          if(valid){     
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/subjectType/update'),
            method: 'post',
            data: {
              id:this.basicData.id,
              parentType:this.basicData.parentType,
              name: this.basicData.name,
              sortId: this.basicData.sortId
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
