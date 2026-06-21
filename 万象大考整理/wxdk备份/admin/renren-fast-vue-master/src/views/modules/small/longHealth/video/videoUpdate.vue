<template>
  <el-dialog
    title='新增' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <el-form :model="basicData" :rules="dataRule" ref="subject" label-width="100px">
        <el-form-item label="视频">
            <el-upload                  
                class="upload-demo"
                :action="reqVideoUrl"
                :show-file-list="false"
                :before-upload="beforeUploadHandle"
                :on-success="handleAvatarSuccess"
                >
            <el-button size="small" type="primary" style="margin-left: 15px;">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <video-player  class="video-player vjs-custom-skin "
            ref="videoPlayer"
            :playsinline="true"
            :options="playerOptions"
            style="width:340px;margin-left: 15px;">
          </video-player>
        </el-form-item>
        <el-form-item label="某一帧" >
          <div>
           <el-input-number v-model="basicData.frame" controls-position="right" :min="1" style="margin-left: 15px;" ></el-input-number>
            <i class="el-icon-search" @click="getProcessImg()"></i>
            </div>
           <img :src="imageUrl" style="width:340px; height:190px;margin-left: 15px;">         
       </el-form-item>
        <el-form-item label="标题" prop="title">
            <el-input v-model="basicData.title" :value="basicData.title" class="title-input" style=" border:0px;width: 350px;margin-left: 15px;"></el-input>
        </el-form-item>
       <el-form-item label="顺序" prop="sortId">
         <el-input-number v-model="basicData.sortId" controls-position="right" :min="1"  style="margin-left: 15px;" ></el-input-number>
       </el-form-item>
       <el-form-item label="是否发布" >
         <el-checkbox-group v-model="basicData.status">
           <el-checkbox :checked="basicData.status"  style="margin-left: 15px;"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDialog()"  >取消</el-button>
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
        defaultOption:'3',
        reqVideoUrl:'',
        videoUrl:'',
        videoName:'',
        imageUrl:'',
        basicData:{
          id:1,
          video:'',
          title:'',
          seconds:'',
          status:false,      
          frame:0, // 某一帧  
          image:'',
        },
        playerOptions : {
          playbackRates: [0.7, 1.0, 1.5, 2.0], //播放速度
          autoplay: false, //如果true,浏览器准备好时开始回放。
          muted: false, // 默认情况下将会消除任何音频。
          loop: false, // 导致视频一结束就重新开始。
          preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
          language: 'zh-CN',
          aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
          fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
          sources: [{
            type: "",
            src:""
            //src: "http://vali.cp31.ott.cibntv.net/youku/696a7e879a37715f12e62163/03000801005A817437F917003E88030B15B175-3826-605C-043F-CE535079A7CF.mp4?sid=053386497600010004574_00_A4f21064bd049a55027b018432b4deec1&sign=81ab8cbf8b4a41a2f9edd07b7c7c0d16&ctype=50" //url地址
          }],
          // poster: "../../static/images/test.jpg", //你的封面地址
          // width: document.documentElement.clientWidth,
          notSupportedMessage: '此视频暂无法播放，请稍后再试', //允许覆盖Video.js无法播放媒体源时显示的默认信息。
          controlBar: {
            timeDivider: true,
            durationDisplay: true,
            remainingTimeDisplay: false,
            fullscreenToggle: true  //全屏按钮
          }
        },
        dataRule: {
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          seconds:[
            { required: true, message: '时长不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    created () { 
    },
    destroyed(){
      //console.log("over!!!");
    },
    methods: {
      init (id) {
        debugger
        if(id == "img[preview]"){return ;}

        this.basicData.id = id || 0;
        this.visible = true;
        this.$nextTick(() => {
          this.$refs['subject'].resetFields()
          if (this.basicData.id) {
            this.$http({
              url: this.$http.adornUrl(`/smallLongHealth/video/getVideoById`),
              method: 'get',
              params: this.$http.adornParams({
                  'id':id
              })
            }).then(({data}) => {
              if (data && data.Tag === 1) {     
                   this.basicData.id = data.Result.id; 
                   this.basicData.video = data.Result.video; 
                   this.basicData.title = data.Result.title;
                   this.basicData.sortId = data.Result.sortId;
                   this.basicData.status = data.Result.status == 1 ?　true : false;
                   this.videoName = data.Result.video;
                   this.playerOptions.sources[0].src=this.GLOBALUploadSourceURL+data.Result.video;
                   this.basicData.seconds = data.Result.seconds;
                   this.basicData.frame = data.Result.frame;
                   this.imageUrl = this.GLOBALUploadSourceURL+data.Result.image;
                   this.basicData.image = data.Result.image;
              }
            })
          }
        })

        this.reqVideoUrl = this.$http.adornUrl(`/smallLongHealth/video/uploadVideo?token=${this.$cookie.get('token')}`);
        this.visible = true;
      },
       getProcessImg(){
        this.$http({
          url: this.$http.adornUrl(`/smallLongHealth/video/getProcessImg`),
          method: 'get',
          params: this.$http.adornParams({
              'filePath':this.videoName,
              'frame':this.basicData.frame,
          })
        }).then(({data}) => {
          if(data.Tag==1){
            this.$message({
                    message: '恭喜你，截取成功！',
                    type: 'success'
            }); 
            this.imageUrl = this.GLOBALUploadSourceURL+data.Result;
            this.basicData.image = data.Result;
            console.log(this.basicData.image);
          }else{
            this.$message.error('出错了：'+data.Message);
          }
        })
      },
       // 上传之前
      beforeUploadHandle (file) {
        debugger;
        if (file.type !== 'video/mp4') {
          this.$message.error('只支持mp4的视频！');
          return false;
        }
      },
      handleAvatarSuccess(response, file, fileList){
          //音频路径        
          debugger
          this.videoName = response.Result.fileName;
          this.videoUrl = this.GLOBALUploadSourceURL+response.Result.fileName;
          this.playerOptions.sources[0].src=this.GLOBALUploadSourceURL+response.Result.fileName;
          this.basicData.seconds = response.Result.time;
      },
      // 表单提交
      dataFormSubmit () {
         debugger;
        if(this.videoName.length<=0){
            this.$message.error('请上传视频！');
            return;
        }

        this.$refs['subject'].validate((valid) => {
        if(valid){     
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl('/smallLongHealth/video/update'),
            method: 'post',
            data: {
              id:this.basicData.id,
              video: this.videoName,
              title: this.basicData.title,
              seconds: this.basicData.seconds,
              sortId:this.basicData.sortId,
              status: this.basicData.status ? 1 : 0 ,
              image: this.basicData.image,
              frame: this.basicData.frame,
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
