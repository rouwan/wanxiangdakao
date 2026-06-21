<template>
  <el-dialog
    :title="'修改'+title" 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form :model="subject" :rules="dataRule" ref="subject"  label-width="100px">
      <el-form-item label="类型" prop="type" >
         <el-select  v-model="subject.modeType" placeholder="请选择" @change="changeModeType()" clearable style="margin-left: 15px;">
            <el-option key="3" label="文字" value="3" ></el-option>
            <el-option key="1" label="图文" value="1" ></el-option>
            <el-option key="2" label="音文" value="2"  ></el-option>
            <el-option key="4" label="判断" value="4"  ></el-option>
            <el-option key="5" label="成语" value="5"  ></el-option>
            <el-option key="6" label="单词" value="6"  ></el-option>
        </el-select> 
      </el-form-item>
      <el-form-item label="图片" prop="image" v-if="subject.modeType==1">
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
       <el-form-item label="音频" prop="aidio" v-if="subject.modeType==2">
            <el-upload
                  class="upload-demo"
                  :action="reqAudioUrl"
                  :show-file-list="false"
                  :before-upload="beforeUploadHandle"
                  :on-success="handleAvatarSuccess"
                  >
                  <el-button size="small" type="primary" style="margin-left: 15px;">点击上传</el-button>
            </el-upload>
        </el-form-item>
        <el-form-item label="" prop="">
          <audio   v-if="subject.modeType==2" :src="audioUrl"
                        controls="controls"
                        preload="auto" style="margin-left: 15px;">
          </audio>
        </el-form-item>
        <el-form-item label="分类" >
           <el-select  v-model="parentType" @change="selChange()" placeholder="请选择" clearable style="margin-left: 15px; width:110px;">
            <el-option key="-1" label="请选择" value="-1" ></el-option>
            <el-option key="1" label="德" value="1" ></el-option>
            <el-option key="2" label="商" value="2" ></el-option>
            <el-option key="3" label="文" value="3" ></el-option>
            <el-option key="4" label="理" value="4" ></el-option>
            <el-option key="5" label="艺" value="5" ></el-option>
            <el-option key="6" label="体" value="6" ></el-option>
            <el-option key="7" label="综" value="7" ></el-option>
        </el-select> 
         <el-select v-model="subject.typeId" ref="optionItem" placeholder="请选择" clearable style="margin-left: 15px;">
             <el-option
                v-for="item in optionItem"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                >
            </el-option>
        </el-select> 
      </el-form-item>
        <el-form-item label="题目" >
            <el-input v-model="subject.promptWord" :value="subject.promptWord" class="title-input" style=" border:0px;"></el-input>
        </el-form-item>
        <!-- <el-form-item label="混淆答案" prop="misAnswer">
            <el-input v-model="subject.misAnswer" :value="subject.misAnswer" class="title-input" style=" border:0px;" maxlength="15" minlength="15"></el-input>
        </el-form-item> -->
        <el-form-item :label="'选项'+wordIndex[index]" v-for="(option, index) in subject.option" :key="index" prop="option">
            <el-input v-model="option.option" :value="option.option" class="title-input" style=" border:0px;"></el-input>
            <el-button size="small" type="danger" @click="deleteSubejctOption(index)">删除选项</el-button>
        </el-form-item>
        <div style="position:absolute; margin-left:72%;">
            <el-button type="primary" icon="el-icon-arrow-left" @click="preSubject()" >上一题</el-button>
            <el-button type="primary" @click="nextSubject()">下一题<i class="el-icon-arrow-right el-icon--right"></i></el-button>
        </div>
        <el-button size="small" type="primary" @click="addSubejctOption()">添加选项</el-button>
        <el-form-item label="题目难度" prop="levelType" >
          <el-select  v-model="subject.levelType" placeholder="请选择" clearable style="margin-left: 15px;float:left;">
            <el-option key="1" label="简单" value="1" ></el-option>
            <el-option key="2" label="一般" value="2" ></el-option>
            <el-option key="3" label="困难" value="3" ></el-option>
          </el-select> 
          <el-checkbox-group v-model="subject.isLevelType" style="margin-left: 15px;float:left;">
             <el-checkbox :checked="subject.isLevelType">是否修改</el-checkbox>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="正确答案" prop="answer">
         <el-select v-model="subject.answer" placeholder="请选择" clearable style="margin-left: 15px;">
             <el-option
                v-for="item in rightAnswers"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
        </el-select> 
      </el-form-item>

      <el-form-item label="选项类型" prop="type" >
         <el-select  v-model="subject.type" placeholder="请选择" clearable style="margin-left: 15px;">
            <el-option key="1" label="选项型" value="1" ></el-option>
            <el-option key="2" label="选字型" value="2" ></el-option>
        </el-select> 
      </el-form-item>
       <el-form-item label="出题人" prop="createUserName">
          <el-input v-model="subject.createUserName" :value="subject.createUserName" class="title-input" style="width:230px; border:0px;"></el-input>
      </el-form-item>
      <el-form-item label="答案释义" prop="description">
        <el-input type="textarea" rows="3" v-model="subject.description" :value="subject.description" class="title-input" style=" border:0px;"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDialog()">取消</el-button>
      <!--审核过的不再修改-->
      <el-button type="primary" v-if="!(this.subjectStatus==1)" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { treeDataTranslate } from "@/utils";
import Icon from "@/icons";
export default {
  data() {
    return {
      visible: false,
      defaultOption: "3",
      reqAudioUrl: "",
      audioUrl: "",
      audioName: "",
      reqImageUrl: "",
      imageUrl: "",
      imageName: "",
      wordIndex: ["A", "B", "C", "D", "E", "F", "G", "H", "I"],
      rightAnswers: [],
      subjectStatus: 0,
      parentType: "-1",
      title:"",
      userSubjectId:0,
      subject: {
        id: 1,
        image: "",
        audio: "",
        answer: "1",
        description: "",
        misAnswer: "",
        promptWord: "",
        type: "0",
        typeId: "",
        sortId: "0",
        modeType: "1",
        createUserName:'',
        levelType: "1",
        isLevelType: false,
        option: [
          // { id:0,option:'',sortId:1}
        ]
      },
      optionItem: [],
      dataRule: {
        answer: [{ required: true, message: "答案不能为空", trigger: "blur" }],
        description: [{ required: true, message: "答案释义不能为空", trigger: "blur" }],
        promptWord: [{ required: true, message: "提示文字不能为空", trigger: "blur" }],
        aidio: [{ required: false, message: "请上传音频", trigger: "blur" }],
        type: [{ required: true, message: "类型不能为空", trigger: "blur" }],
        option: [{ required: true, message: "选项不能为空", trigger: "blur" }],
        rightRatio: [{ required: true, message: "题目难度不能为空", trigger: "blur" }],
        createUserName:[{ required: true, message: '出题人不能为空', trigger: 'blur' }]
      }
    };
  },
  created() {
    this.keyupSubmit();
    this.iconList = Icon.getNameList();
  },
  destroyed() {
    console.log("over!!!");
  },
  methods: {
    keyupSubmit(){
      document.onkeydown=e=>{
        let _key=window.event.keyCode;
        if(_key===37){
          this.preSubject()
        }else if(_key===39){
          this.nextSubject()
        }
      }
  },
   preSubject(){
     debugger;
     this.getNextOrPreSubjectById(this.userSubjectId,'/smallVientianeTest/userSubject/getPreSubjectById');
   },
   nextSubject(){
     debugger;
     this.getNextOrPreSubjectById(this.userSubjectId,'/smallVientianeTest/userSubject/getNextSubjectById');
   },
   getNextOrPreSubjectById(id,url){
     this.$http({
          url: this.$http.adornUrl(url),
          method: 'get',
          params: this.$http.adornParams({
            'id': id
          })
        }).then(({data}) => {
          if (data && data.Tag === 1) {
             if(data.Result.parentType > 0){
                this.selChange().then((resolve)=>{
                  this.userSubjectId = data.Result.userSubjectId;
                  this.subject.id = data.Result.id;
                  this.subject.image = data.Result.image;
                  this.subject.audio = data.Result.audio;
                  this.subject.answer = data.Result.optionSortId.toString();
                  this.subject.misAnswer = data.Result.mixAnswer;
                  this.subject.promptWord = data.Result.promptWord;
                  this.subject.type = data.Result.type.toString();
                  this.subject.sortId = data.Result.sortId;
                  this.subjectStatus = data.Result.status;
                  this.subject.description = data.Result.description;
                  this.subject.modeType = data.Result.modeType.toString();
                  this.subject.createUserName = data.Result.createUserName;
                  this.subject.levelType = data.Result.levelType.toString();
                  this.title = "-" + data.Result.sortId;
                  if(data.Result.typeId > 0){
                    this.subject.typeId = data.Result.typeId;
                    this.parentType = data.Result.parentType.toString();
                  }
            
                  var optionObj = data.Result2;
                  this.subject.option = [];
                  this.rightAnswers = [];
                  for (var i = 0; i < optionObj.length; i++) {
                    var obj = {
                      id: optionObj[i].id,
                      option: optionObj[i].option,
                      sortId: optionObj[i].sortId
                    };
                    this.subject.option.push(obj);
                    var optionLen = this.subject.option.length;
                    if(this.subject.modeType == 4){ //判断题
                      var answerObj = { id: optionLen.toString(), name: optionObj[i].option };
                      this.rightAnswers.push(answerObj);
                    }else{
                      var word = this.wordIndex[optionLen - 1];
                      var answerObj = { id: optionLen.toString(), name: word };
                      this.rightAnswers.push(answerObj);
                    }
                      
                  
                  }
                  this.audioName = this.subject.audio;
                  this.audioUrl = this.GLOBALUploadSourceURL + this.subject.audio;
                  this.imageName = this.subject.image;
                  this.imageUrl = this.GLOBALUploadSourceURL + this.subject.image;
                });
              }
              
          }else{
            this.$message.error('错误，'+data.Message);
          }
        })
   },
   selChange(){
      debugger
      if(this.parentType == -1){
        this.subject.typeId = '';
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
            this.subject.typeId = '';
            this.optionItem = data.Result;  
          }else{
            this.$message.error('错误，'+data.Message);
          }
          resolve();
        })
      });
    },
  changeModeType(){
        this.subject.option = [{id:0,option:'',sortId:1},{id:0,option:'',sortId:1},{id:0,option:'',sortId:1},{id:0,option:'',sortId:1}];
        this.rightAnswers = [{id: '1', name: 'A'},{id: '2', name: 'B'},{id: '3', name: 'C'},{id: '4', name: 'D'}];
        if(this.subject.modeType == 4){
            this.subject.option = [{id:0,option:'对',sortId:1},{id:0,option:'错',sortId:1}];
            this.rightAnswers = [{id: '1', name: '对'},{id: '2', name: '错'}];
        } 
   },
   init(id,parentType) {
      debugger
      if (id == "img[preview]") {return;}
      parentType = parentType || '-1';
      debugger;
      this.parentType = parentType;
      if(parentType > 0){
        this.selChange().then((resolve)=>{
          this.controllerInit(id);   
        });
      }else{
        this.controllerInit(id);   
      }
    },
    controllerInit(id) {
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["subject"].resetFields();
        if (this.subject.id) {
          this.$http({
            url: this.$http.adornUrl(`/smallVientianeTest/userSubject/getSubjectById`),
            method: "get",
            params: this.$http.adornParams({
              id: id
            })
          }).then(({ data }) => {
            if (data && data.Tag === 1) {
              debugger;
              this.userSubjectId = data.Result.userSubjectId;
              this.subject.id = data.Result.id;
              this.subject.image = data.Result.image;
              this.subject.audio = data.Result.audio;
              this.subject.answer = data.Result.optionSortId.toString();
              this.subject.misAnswer = data.Result.mixAnswer;
              this.subject.promptWord = data.Result.promptWord;
              this.subject.type = data.Result.type.toString();
              this.subject.sortId = data.Result.sortId;
              this.subjectStatus = data.Result.status;
              this.subject.description = data.Result.description;
              this.subject.modeType = data.Result.modeType.toString();
              this.subject.createUserName = data.Result.createUserName;
              this.subject.levelType = data.Result.levelType.toString();
              this.title = "-" + data.Result.sortId;
              if(data.Result.typeId > 0){
                this.subject.typeId = data.Result.typeId;
                this.parentType = data.Result.parentType.toString();
              }
        
              var optionObj = data.Result2;
              this.subject.option = [];
              for (var i = 0; i < optionObj.length; i++) {
                var obj = {
                  id: optionObj[i].id,
                  option: optionObj[i].option,
                  sortId: optionObj[i].sortId
                };
                this.subject.option.push(obj);
                var optionLen = this.subject.option.length;
                if(this.subject.modeType == 4){ //判断题
                  var answerObj = { id: optionLen.toString(), name: optionObj[i].option };
                  this.rightAnswers.push(answerObj);
                }else{
                  var word = this.wordIndex[optionLen - 1];
                  var answerObj = { id: optionLen.toString(), name: word };
                  this.rightAnswers.push(answerObj);
                }
                  
               
              }
              this.audioName = this.subject.audio;
              this.audioUrl = this.GLOBALUploadSourceURL + this.subject.audio;
              this.imageName = this.subject.image;
              this.imageUrl = this.GLOBALUploadSourceURL + this.subject.image;
            }
          });
        }
      });
      this.reqImageUrl = this.$http.adornUrl(`/smallVientianeTest/subject/uploadImage?token=${this.$cookie.get("token")}`);
      this.reqAudioUrl = this.$http.adornUrl(`/smallVientianeTest/subject/uploadAudio?token=${this.$cookie.get("token")}`);
      this.visible = true;
    },
    // 上传之前
    beforeUploadHandle(file) {
      debugger;
      if (this.subject.modeType == 2) {
        if (file.type !== "audio/mp3" && file.type !== "audio/wma") {
          this.$message.error("只支持mp3、wma格式的音频！");
          return false;
        }
      } else if (this.subject.modeType == 1) {
        if (
          file.type !== "image/jpg" &&
          file.type !== "image/jpeg" &&
          file.type !== "image/png" &&
          file.type !== "image/gif"
        ) {
          this.$message.error("只支持jpg、png、gif格式的图片！");
          return false;
        }
      }
    },
    handleAvatarSuccess(response, file, fileList) {
      //音频路径
      this.audioName = response.Result;
      this.audioUrl = this.GLOBALUploadSourceURL + response.Result;
      //图片路径
      this.imageName = response.Result;
      this.imageUrl = this.GLOBALUploadSourceURL + response.Result;
    },
    addSubejctOption() {
      debugger;
      var obj = { id: 0, option: "", sortId: 1 };
      this.subject.option.push(obj);
      var optionLen = this.subject.option.length;
      var word = this.wordIndex[optionLen - 1];
      var answerObj = { id: optionLen, name: word };
      this.rightAnswers.push(answerObj);
    },
    deleteSubejctOption(index) {
      this.subject.option.splice(index, 1);
      this.rightAnswers.splice(this.rightAnswers.length - 1, 1);
      this.subject.answer = "";
    },
    // 表单提交
    dataFormSubmit() {
     debugger;
        if(this.subject.modeType == 1){
          if(this.imageName.length<=0){
            this.$message.error('请上传图片！');
            return;
          }
        }else if(this.subject.modeType == 2){
          if(this.audioName.length<=0){
            this.$message.error('请上传音频！');
            return;
          }
        }
        if(this.subject.typeId <=0 || this.subject.typeId.length<=0){
          this.$message.error('请选择分类！');
          return; 
        }
        if(this.subject.answer.length<=0){
          this.$message.error('请选择正确答案！');
          return; 
        }
        var isEmpty = false;
        for(var i=0 ;i<this.subject.option.length;i++){
          if(this.subject.option[i].option.length<=0){
            isEmpty = true;
          }
        }
        if(isEmpty){
         this.$message.error('请填写选项内容(注:不能为空)！');
          return; 
        }
        this.$refs['subject'].validate((valid) => {
        if(valid){     

            var subject = this.subject ;
            var subjectId = subject.id;
            var answer = subject.answer;
            var promptWord = subject.promptWord;
            var typeId = subject.typeId;
            var type = subject.type;
            var misAnswer = subject.misAnswer;
            var audioName = this.subject.modeType == 2 ? this.audioName : '' ;
            var imageName = this.subject.modeType == 1 ? this.imageName : '' ;
            var sortId = subject.sortId;
            var description = subject.description;
            var createUserName = subject.createUserName;
            var isLevelType = subject.isLevelType ? 1 : 0;
            var levelType = subject.levelType;
            var optionStr = '';
            for(var i=0;i<subject.option.length;i++){
              var option = subject.option[i];
              var optionSortId = i+1;
              optionStr += option.id +'@#'+option.option +'%#@';
            
            }
            console.log('optionStr = '+optionStr);
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallVientianeTest/subject/updateSubjectById'),
            method: 'post',
            data: {
              id:subjectId,
              answer: answer,
              image: imageName,
              audioName: audioName,
              promptWord: promptWord,
              optionStr: optionStr,
              typeId: typeId,
              type: type,
              modeType:this.subject.modeType,
              sortId:sortId,
              description:description,
              createUserName:createUserName,
              isLevelType:isLevelType,
              levelType: levelType
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
    closeDialog() {
      this.$emit("close");
    }
  }
};
</script>

<style lang="scss">
.title-input {
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
  -webkit-transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 43%;
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
  border-color: #409eff;
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
