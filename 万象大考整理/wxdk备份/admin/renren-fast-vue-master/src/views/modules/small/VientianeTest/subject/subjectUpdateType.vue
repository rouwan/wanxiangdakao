<template>
  <el-dialog
    title='修改' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    width="480px"
    >
    <el-form :rules="dataRule" ref="basicData"  label-width="100px">
       <el-form-item label="大类" >
         <el-select  v-model="parentType" @change="selChange()" placeholder="请选择" clearable style="margin-left: 15px;">
            <el-option key="1" label="德" value="1" ></el-option>
            <el-option key="2" label="商" value="2" ></el-option>
            <el-option key="3" label="文" value="3" ></el-option>
            <el-option key="4" label="理" value="4" ></el-option>
            <el-option key="5" label="艺" value="5" ></el-option>
            <el-option key="6" label="体" value="6" ></el-option>
            <el-option key="7" label="综" value="7" ></el-option>
        </el-select> 
      </el-form-item>
        <el-form-item label="小类" prop="typeId" >
            <el-select v-model="typeId" ref="optionItem" placeholder="请选择" clearable style="margin-left: 15px;">
               <el-option
                v-for="item in optionItem"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                >
              </el-option>
            </el-select> 
        </el-form-item>
      <el-form-item label="题目难度" prop="levelType">
        <el-select  v-model="levelType" placeholder="请选择" clearable style="margin-left: 15px;float:left;">
          <el-option key="1" label="简单" value="1" ></el-option>
          <el-option key="2" label="一般" value="2" ></el-option>
          <el-option key="3" label="困难" value="3" ></el-option>
        </el-select> 
          <el-checkbox-group v-model="isLevelType" style="margin-left: 15px;float:left;">
             <el-checkbox :checked="isLevelType">是否修改</el-checkbox>
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
        ids:'',    
        parentType:'1',
        typeId: '',      
        optionItem: [],
        levelType:'1',
        isLevelType:false,
        dataRule: {
          typeId: [{ required: true, message: '小类不能为空', trigger: 'blur' }],
          levelType: [{ required: true, message: '题目难度不能为空', trigger: 'blur' }]
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
      init(ids) {
        debugger
        if (ids == "img[preview]") {return;}
        this.ids = ids;
        this.visible = true;
        this.selChange();
      },
      selChange(){
        debugger
        if(this.parentType == -1){
          this.typeId = '';
          this.optionItem = [];
          return
        }
        return new Promise((resolve, reject)=>{
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/subjectType/getSubjectTypeByTypeId'),
            method: 'get',
            params: this.$http.adornParams({
              'parentType': this.parentType
            })
          }).then(({data}) => {
            debugger
            if (data && data.Tag === 1) {
              debugger
              this.typeId = '';
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
        debugger;
        if(this.ids.length <= 0){
            this.$message.error('请选择题目！');
            return;
        }
        else if(this.typeId.length<=0 || this.typeId<=0){
            this.$message.error('请选择题目小类！');
            return;
        }else if(this.levelType<=0){
            this.$message.error('请选择题目难度！');
            return;
        }
        this.$http({
          url: this.$http.adornUrl('/smallVientianeTest/subject/updateSubjectTypeById'),
          method: 'post',
          data: {
            ids: this.ids,
            typeId:this.typeId,
            isLevelType: this.isLevelType ? 1 : 0,
            levelType: this.levelType 
          }
        }).then(({data}) => { 
            if (data && data.Tag === 1) {
                  this.$message({
                  message: '恭喜你，修改成功！',
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
