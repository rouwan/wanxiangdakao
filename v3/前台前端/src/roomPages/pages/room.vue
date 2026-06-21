<template>
  <div class="room">
    <div class="m-room" v-if="!showQuesModules&&!showResult">
      <div class="u-seat">
        <div class="u-seat-box" v-for="(item,index) in placeList" :key="index">

          <div class="u-seat-top">
            <img v-if="userList[index]" :src="IMGHEAD+userList[index].abilityInfo.paragraphImage">
            <div class="u-info" v-if="userList[index]">
              <div class="u-name">{{userList[index].basicInfo.nickName}}</div>
              <div class="u-area">
                <div>{{userList[index].basicInfo.province||'宇宙'}}</div>
                <div>{{userList[index].basicInfo.city||'火星'}}</div>
                <img v-if="userList[index].basicInfo.gender=='女'" class="girl" src="/static/girl.png">
                <img v-else class="boy" src="/static/boy.png">
              </div>
            </div>
            <div class="u-master" v-if="userList[index]&&(userList[index].basicInfo.userId==roomData.room.roomMaster)">房主</div>
          </div>

          <div class="u-seat-bottom">
            <img class="u-bg" src="/static/light-bg.png">
            <img v-if="userList[index]&&userList[index].basicInfo.avatarUrl&&userList[index].boot" class="u-head-img" :src="IMGHEAD+userList[index].basicInfo.avatarUrl">
            <img v-if="userList[index]&&userList[index].basicInfo.avatarUrl&&!userList[index].boot" class="u-head-img" :src="userList[index].basicInfo.avatarUrl">
            <img v-if="userList[index]&&!userList[index].basicInfo.avatarUrl" class="u-head-img" src="/static/default-head.png">
            <img v-if="userList[index]&&userList[index].isReady==1&&!userList[index].isMaster" class="img-ready" src="/static/ready.png" alt="">
            <div class="u-mask" v-if="userList[index]&&userList[index].isReady==0">
              未准备
              <div v-if="userRole=='房主'" class="u-kick" @click="kick(userList[index].basicInfo.userId)">踢</div>
            </div>
          </div>
          <!-- 表情框 -->
          <div class="u-emoji-box"  :class="{'showEmj':userList[index]&&seatList[index].showEmoji}">
            <div class="u-inner-box">
              <img :src="seatList[index].emojiUrl">
            </div>
          </div>
        </div>

      </div>
      <!-- 倒计时遮罩 -->
      <div class="timer-mask" v-if="showTimerMask">
        <div>玩家准备就绪~</div>
        {{autoStartTime}}
      </div>
      <div class="u-btn">
        <div class="u-btn-left" v-if="userRole=='房主'" @click="handleStartGame">开始游戏</div>
        <div class="u-btn-left" v-if="userRole=='民众'&&myInfo.isReady==0" @click="handleReadyClick">准备</div>
        <div class="u-btn-left" v-if="userRole=='民众'&&myInfo.isReady==1" @click="handleReadyClick">取消准备</div>

        <div class="u-btn-right" v-if="userNum>=4" @click="handleInvate">邀请</div>
        <div class="u-btn-right" v-else-if="userNum<4&&myInfo.isReady==1&&userRole=='民众'" @click="handleInvate2">邀请</div>
        <button v-else open-type='share' plain="true" class="u-btn-right">邀请</button>
        <img @click="emojiBtn=true" class="u-btn-emoji" src="/static/room/emoji-btn.png" alt="">

      </div>
    </div>

    <div class="m-ques-modal" v-if="showQuesModules">
      <ques-head :userList="userList" :ansList="ansList" :userNum="userNum"></ques-head>
      <div class="u-time">
        <time-count :small="true" :initBtn="initTimeCount" :startBtn="startTimeCount" :initTime="initTime" :stopBtn="stopTimeCount">
        </time-count>
      </div>
      <div class="u-title">
        <ques-title :list="list" :currentIndex="currentIndex" :idiomList="idiomList":poetryList="poetryList" :levelList="levelList" :imgLevelList="imgLevelList" :titleOff="titleOff" @antEnd="handleSelectIn">
        </ques-title>
      </div>
      <div class="u-select">
        <ques-select  :subjectList="list" 
                      :currentIndex="currentIndex" 
                      :show="showSelect" 
                      :showBothAns="showBothAns"
                      :ansList="ansList" 
                      :userNum="userNum" 
                      :hide="selectOff" 
                      @selected="handleSelect" 
                      @answerComplete="handleAnswerComplete">
        </ques-select>
      </div>
    </div>
    <div class="u-result" v-if="showResult">
      <ques-result :result="resultData" @close="handleCloseResult" @route="handleRoute"></ques-result>
    </div>
    <!-- <img v-if="deviceInfo.brand&&deviceInfo.brand!='HUAWEI'" class="moon" src="/static/entry/moon.png"> -->

    <!-- 人数满弹框 -->
    <div class="full-man-mask" v-if="showFullMask">
      <div class="f-bg"></div>
      <div class="f-inner">
        <div class="f-txt">房间已满,你不能邀请更多好友</div>
        <div class="f-btn" @click="closeFullMask">确定</div>
      </div>
    </div>

    <emoji-box v-if="emojiBtn" @close="emojiBtn=false" @sendEmoji="handleSendEmoji"></emoji-box>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import quesHead from "../components/quesHead.vue";
import quesTitle from "../components/quesTitle.vue";
import quesSelect from "../components/quesSelect.vue";
import quesResult from "../components/quesResult.vue";
import emojiBox from "../components/emojiBox.vue";

import timeCount from "../../pages/game/components/timeCount.vue";
import {getRondom} from "@/utils/index"
import {shareInfoForRoom} from "@/config/dataFile"
import { throttle } from "@/utils/index";
import {emoji} from "@/config/dataFile"

export default {
  onShareAppMessage:function(res) {
    let index = getRondom(0,2);
    console.log(this._roomId)
    return {
      title:this.appInfo.nickname+shareInfoForRoom[index].title,
      imageUrl: shareInfoForRoom[index].imgUrl,
      path: `/pages/entry/index?roomId=${this._roomId}&userId=${this.appInfo.userId}`,
    };
  },
  components: {
    "ques-head": quesHead,
    "time-count": timeCount,
    "ques-title": quesTitle,
    "ques-select": quesSelect,
    "ques-result": quesResult,
    "emoji-box":emojiBox,
  },
  data() {
    return {
      // 生命周期
      hideForRoute:false,
      // 基础数据状态
      isHideLife:false,
      socketType:1,
      socketTask:{},
      isFirst: true,
      _roomId: 0,
      gameStatus:"wait",
      socketStatus: "off",
      roomData: null,
      userRole: "民众",
      placeList: [0, 0, 0, 0],
      myInfo: {},
      userList: [],
      seatList:[{},{},{},{}],//用户座位列表
      resultData: [],
      userNum: 0,
      throttle:throttle,
      // 功能变量
      heart: null, //心跳timer
      heartTimer:null,
      delayTime:0,
      readyType: false, //
      allReady: false,
      autoStartTimer: null,
      autoStartTime: 10,
      // 组件状态
      showQuesModules: false,
      titleOff: true,
      selectOff: false,
      initTime: 10,
      initTimeCount: false,
      startTimeCount: false,
      stopTimeCount: false,
      showTimerMask: false,
      showFullMask: false,
      showTitle: true,
      showSelect: false, //
      showResult: false, //展示结果
      emojiBtn:false,
      // 题目相关
      currentIndex: 0,
      list: [],
      levelList: [],
      idiomList: [],
      poetryList:[],
      imgLevelList: [],

      //答题相关
      ansList: [],
      showBothAns:false,
      query:{},
      emoji:emoji,
    };
  },
  computed: {
    ...mapState({
      roomStatus: state => state.fStore.roomStatus,
      appInfo: state => state.appInfo,
      EVN: state => state.EVN,
      IMGHEAD: state => state.IMGHEAD,
      deviceInfo: state => state.deviceInfo,
      connected:state=>state.socketStore.connected,
      messageBus:state=>state.socketStore.messageBus,
      messageListeners:state=>state.socketStore.messageListeners,
    }),
  },
  watch: {
    // messageListeners(list){
    //   console.log("messageListeners",list);
    // },
    userList(list) {
      let _allReady = true;
      let num = 0;
      this.appInfo.userId == this.roomData.room.roomMaster?this.userRole='房主':this.userRole='民众';

      list.forEach((user,index) => {
        user.basicInfo.userId == this.appInfo.userId?this.myInfo=user:null;
        user.basicInfo.userId == this.roomData.room.roomMaster?user.isMaster = true:null;
        user.isReady == 0?_allReady=false:null;
        user.status == 1?num++:null;
        if(this.seatList[index].showEmoji){
          this.seatList[index].showEmoji=false;
        }
      });
      this.userNum = num;
      list.length > 1?this.allReady = _allReady:null;
      console.log("userNum--------------------------------", num);
    },

    allReady(value) {
      if (value && this.userList.length == 4&&this.gameStatus!="playing") {
        this.startAutoStartGame();
        console.log("所有人都准备好了，开始倒计时.倒计时结束，自动开始");
      } else {
        this.stopAutoStartTimer();
      }
    },
    autoStartTime(val) {
      if (val <= 0) {
        this.stopAutoStartTimer();
        if (this.userRole == "房主") {
          this.handleStartGame();
        }
      }
    },
    delayTime(time){
      if(time>=4){
        this.netWorkWeek();
      }
    },
  },
  methods: {
    ...mapMutations({
      setAppInfo:"setAppInfo",
      setGameRecord:"setGameRecord",
      connect:"socketStore/connect",
      addMessageListener:"socketStore/addMessageListener",
      removeMessageListener:"socketStore/removeMessageListener",
      send:"socketStore/send",
      close:"socketStore/close",
      setPageType:"socketStore/setPageType"
    }),
    createdRoom(){
      this.send(`{'type':13,'data':1,'modeType':1}`,"创建房间");
    },
    netWorkWeek(){
      this.clearHeart();
      if(this.connected){
        this.closeSocket();
      }
      setTimeout(() => {
        wx.showModal({title:"系统提示",content:"网络较差,请重新登录",showCancel:false,success:res=>{
          if(res.confirm){
            wx.reLaunch({url:'/pages/entry/index'});
          }
        }})
      }, 1000);
    
    },
    startAutoStartGame() {
      this.showTimerMask = true;
      this.autoStartTimer = setInterval(() => {
        this.autoStartTime--;
      }, 1000);
    },
    stopAutoStartTimer() {
      clearInterval(this.autoStartTimer);
      this.autoStartTime = 10;
      this.showTimerMask = false;
    },

 
    // socket 处理函数
    initRoomData(data) {
      this.roomData = data.Result;
      this.userList = this.roomData.room.userList;
      this._roomId = this.roomData.room.roomId;
      if (
        this.roomData.room.roomStatus == 0 &&
        this.roomData.game.subjectList &&
        this.roomData.game.subjectList.length > 0
      ) {
        this.getList();
        this.currentIndex=0;
        this.showQuesModules = true; //显示答题界面
        this.currentIndex=0;
        setTimeout(() => {
          this.send(`{'type':6,'data':'${this.roomData.game.gameId}'}`); //开始游戏
          this.startGame();
        }, 2500);
      }else{
        this.gameStatus='wait';
      }
      console.log("type1:", data);
    },
    handleRoute(){
      this.hideForRoute=true;
    },
    getList() {
      this.idiomList = [];
      this.levelList = [];
      this.poetryList=[];
      this.imgLevelList = [];
      this.list = this.roomData.game.subjectList;
      // this.list = JSON.parse(JSON.stringify(this.roomData.game.subjectList)); //深拷贝 问题数据
      for (let i = 0; i < this.list.length; i++) {
        // 判断成语题 生成题目列表
        if (this.list[i].modeType == 5) {
          let list = this.list[i].title.replace("_", "?").split("");
          this.idiomList.push(list);
        } else {
          this.idiomList.push(0);
        }

        if(this.list[i].modeType==7||this.list[i].modeType==8||this.list[i].modeType==9||this.list[i].modeType==10){
          let arr = this.list[i].title.replace('_',"?").split('');
          this.poetryList.push(arr);
        }else{
          this.poetryList.push(0);
        }

      }
      this.getRatioList(this.list);
    },
    getRatioList(list) {
      for (let item of list) {
        if (item.rightRatio >= 66) {
          this.levelList.push({ type: "简单", blue: true });
          this.imgLevelList.push({ type: "简", blue: true });
        }
        if (item.rightRatio >= 33 && item.rightRatio < 66) {
          this.levelList.push({ type: "一般", yellow: true });
          this.imgLevelList.push({ type: "中", yellow: true });
        }
        if (item.rightRatio >= 0 && item.rightRatio < 33) {
          this.levelList.push({ type: "困难", red: true });
          this.imgLevelList.push({ type: "难", red: true });
        }
      }
    },
  

    handleMessage(message) {
      //被动接受消息
        //区分数据类型 处理不同业务
        // let data = JSON.parse(res.data);
        let eventHandleMap = {
          socketType1: () => this.initRoomData(message), //type1 初始化、重置房间信息
          socketType2: () => this.checkOldGame(message),
          // socketType3: () => this.startGame(data), //type3 游戏开始
          socketType4: () => this.answerResponse(message), //type4 答题信息
          socketType7: () => this.gameEnd(message), //type7 游戏结束
          socketType6: () => this.handleNextQuestion(message),
          socketType8: () => this.handleTick(message), //type8 被房主提出房间
          socketType15: () => this.handleShowEmoji(message), //type15 展示表情
          socketType0: () => this.stopHeartTimer() //响应本轮心跳
          
        };
        //执行对应的方法
        if (typeof eventHandleMap[`socketType${message.Type}`] == "function") {
          eventHandleMap[`socketType${message.Type}`]();
        }
    },
    closeSocket() {
      this.close();
    },
    handleSendEmoji(index){
      this.send(`{'type':15,'data':${index}}`); //发送表情
    },
    handleShowEmoji(message){
      let emojiInfo = JSON.parse(message.Result);
      let userIndex =0;
      for(let i=0;i<this.userList.length;i++){
        if(this.userList[i].basicInfo.userId==emojiInfo.userId){
          userIndex=i;
          break;
        }
      }
      this.$set(this.seatList,userIndex,{emojiUrl:this.emoji[emojiInfo.index].imgUrl,showEmoji:true});
      setTimeout(()=>{
        this.$set(this.seatList,userIndex,{emojiUrl:"",showEmoji:false});
      }, 2000);

    },

    handleNextQuestion(data) {
      console.log('接受type6',data)
      if(this.currentIndex!=data.Result.index){
        this.stopTimeCount=!this.stopTimeCount;
        setTimeout(() => {
          this.showBothAns=!this.showBothAns;
        }, 1000);
        setTimeout(() => {
          this.currentIndex = data.Result.index;
        }, 3000);
      }
   
    },
    startTimer() {
      this.startTimeCount = !this.startTimeCount;
    },
    stopTimer() {
      this.stopTimeCount = !this.stopTimeCount;
    },
    startHeart() {
      this.heart = setInterval(() => {
      try {
          this.send({
            data: "{'type':0,'data':1}",
            success: () => {
              this.stopHeartTimer();
              this.heartTimer = setInterval(() => {
                this.delayTime++;
              }, 1000);
            },
            fail: res => {
              console.log(res)
              if(!this.isHideLife){
                this.netWorkWeek();
              }
            }
          }); 
          // throw"发送失败心跳"
        } catch (err) {console.log(err)}
      }, 5000);
    },
    stopHeart() {
      clearInterval(this.heart);
    },
    startHeartTimer(){
      this.heartTimer = setInterval(()=>{
        this.delayTime++;
      },1000)
    },
    stopHeartTimer(){
      clearInterval(this.heartTimer);
      this.delayTime=0;
    },
    clearHeart(){
      this.stopHeart();
      this.stopHeartTimer();
    },
    startGame() {
      this.gameStatus='playing';
      this.ansList=[];
    },
    gameStatus(valu){
      console.log('游戏状态',value)
    },
    gameEnd(value) {
      setTimeout(() => {
        this.showBothAns=!this.showBothAns;
      }, 1000);
      setTimeout(() => {
        console.log("end", value);
        this.resultData = value;
        this.gameStatus="wait";
        this.setGameRecord(value);
        for(let i=0;i<value.Result.room.userList.length;i++){
          if(this.appInfo.userId == value.Result.room.userList[i].basicInfo.userId){
            this.setAppInfo({money:value.Result.room.userList[i].abilityInfo.money});
            // this.appInfo.money = value.Result.room.userList[i].abilityInfo.money;
          }
        }

        if(this.$root.$mp.query.status){
          delete this.$root.$mp.query.status;
        }
        setTimeout(() => {
          this.showQuesModules = false;
          this.showResult = true;
          this.currentIndex = 0;
          console.log("change index from gameEnd",this.currentIndex);
        }, 1000);
      }, 2500);
 
    },
    checkOldGame(data){
      // TODO 重置题目序号，时间，
      console.log('游戏重连--------------',data);
      if(data.Result==''){
        wx.showModal({
          title:"系统提示",
          content:"对局已结束,离开房间",
          showCancel:false,
          success:res=>{
            if(res.confirm){
              this.$router.back();
            }
          }
        })
      }else{
        if(this.$root.$mp.query.status==5){
          this.showQuesModules=true;
        }
        this.initTime =Math.ceil(data.Result.room.restSecond/1000);
        this.currentIndex = data.Result.room.answerIndex;
        console.log("change index from reLink",this.currentIndex);
        this.startTimeCount=!this.startTimeCount;
        console.log('重连index',data.Result.room.answerIndex);
        console.log("时间校准：",data.Result.room.restSecond);
      }
     
    },
    // 页面交互处理
    handleReadyClick() {
      this.send(`{'type':10,'data':'${this.roomData.game.gameId}'}`);
    },
    kick(id) {
      wx.showModal({
        title: "系统提示",
        content: "确定请他(她)离开？",
        success: res => {
          if (res.confirm) {
            this.send(`{'type':8,'data':${id}}`);
          }
        }
      });
    },
    handleTick() {
      wx.showModal({
        title: "系统提示",
        content: "你已被房主请离房间",
        showCancel: false,
        success: () => {
          this.$router.back();
        }
      });
    },

    handleInvate() {
      this.showFullMask = true;
    },
    handleInvate2(){
      wx.showToast({title:"准备状态无法邀请",icon:"none"});
    },
    closeFullMask() {
      this.showFullMask = false;
    },
    checkAllReady() {
      let state = true;
      this.userList.forEach(item => {
        item.isReady == 0 ? (state = false,this.gameStatus=='wait') : null;
      });
      return state;
    },
    handleStartGame() {
      // 检查准备状态
      let state = this.checkAllReady();
      console.log("state",state,"this.userNum>1",this.userNum,"this.gameStatus!='readGame",this.gameStatus)
      if (state && this.userNum > 1&&this.gameStatus!='readyGame') {
        this.gameStatus='readyGame';
        this.send(`{'type':1,'data':'${this.roomData.game.gameId}'}`);//发一次预备开始游戏
        this.stopAutoStartTimer();
        this.showTimerMask=false;
      } else if (state && this.userNum == 1) {
        wx.showToast({
          title: "请先邀请好友",
          icon: "none"
        });
      } else if (!state) {
        wx.showToast({
          title: "有玩家未准备",
          icon: "none"
        });
      }
    },
    handleSelectIn() {
      // 显示选项
      this.showSelect = !this.showSelect;
      setTimeout(() => {
        this.startTimeCount = !this.startTimeCount;
      }, 400);
      console.log("展示选项");
    },
    handleSelect(obj) {
      obj.data.gameId = this.roomData.game.gameId;
      obj.data.roomId = this._roomId;
      obj = JSON.stringify(obj);
      this.send(obj, "send-submitAns");
    },
    answerResponse(data) {
      setTimeout(() => {
        console.log("response-type4", data.Result);
        this.ansList.push(data.Result);
      }, 100);
    },
    handleAnswerComplete() {
      if (this.currentIndex < 4&&this.connected) {
        setTimeout(() => {
          this.ansList = [];
          this.initTime=10;
        }, 1000);
        setTimeout(() => {
          this.initTimeCount = !this.initTimeCount;
          this.titleOff = !this.titleOff;
          this.selectOff = !this.selectOff;
        }, 1500);
     
          setTimeout(() => {
            this.send(`{'type':6,'data':'${this.roomData.game.gameId}'}`); //下一题开始计时
          }, 4800);
      } else if(this.currentIndex == 4&&this.connected) {
          setTimeout(() => {
          this.initTimeCount = !this.initTimeCount;
          this.titleOff = !this.titleOff;
          this.selectOff = !this.selectOff;
          this.ansList = [];
        }, 1500);
   
      }
    },
    handleCloseResult() {
      this.showResult = false;
    },
    onNetWorkChange(){
      wx.onNetworkStatusChange(res=>{
        if(!res.isConnected&&this.connected){
          this.closeSocket();
        }
      })
    },
  },
  async mounted() {
    console.log("mounted");
    this.query =this.$root.$mp.query;
    this.addMessageListener(this.handleMessage)
    if(this.query.type=="create"){
      // todo 发送创建请求
      this.createdRoom();
    }else if(this.query.type=="messageBus"){
      this.handleMessage(this.messageBus);
    }

  },
  async onShow() {
    console.log("onShow")
    this.isHideLife=false;
    this.hideForRoute = false;
    this.setPageType(2); //标识当前页面，断网重连时候可以使用
    if(!this.connected){
      this.connect({type:2});
    }
  },
  onHide() {
    this.isHideLife=true;
    this.setPageType(0); //标识当前页面
    this.send(`{'type':17,'data':'0'}`,"隐藏告知服务端");
    if(this.hideForRoute){ 
      this.setPageType(2); //标识当前页面
      return false;
    }
      try {
        this.close();
      } catch (err) {}
   
  },
  async onUnload() {
    // this.clearHeart();
    this.currentIndex = 0;
    this.userList=[];
    this.isFirst = true;
    this.emojiBtn=false;
    this.showQuesModules = false;
    this.showResult = false;
    this.gameStatus="wait";
    delete this.$root.$mp.query;
    await this.send(`{'type':5,'data':'${this.roomData.game.gameId}'}`, "离开页面");
    this.removeMessageListener(this.handleMessage);
    this.setPageType(0); //标识当前页面
    this.close();
  }
};
</script>

<style lang="scss" scoped>

@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
.room {
  width: 100%;
  height: 100%;
  background-size: 100%;
  background-position-y: -120rpx;
  background-image: url("data:image/png;base64,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");
}
.m-ques-modal {
  position: fixed;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  padding-top: 58rpx;
  box-sizing: border-box;
}
.u-time {
  width: 100%;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: 230rpx;
  display: flex;
  justify-content: center;
  z-index: 4;
}
.u-title {
  position: relative;
  display: flex;
  justify-content: center;
  margin-top: 114rpx;
  height: 293rpx;
}

.m-room {
  position: fixed;
  z-index: 1;
  width: 100%;
  height: 100%;
  color: #fff;
  .u-seat {
    width: 100%;
    box-sizing: border-box;
    padding: 70rpx 30rpx 0 30rpx;
    @include flex;
    flex-wrap: wrap;
  }
  .u-seat-box {
    width: 324rpx;
    height: 412rpx;
    @include flex;
    flex-direction: column;
    border: 2rpx solid #6f6f8e;
    border-radius: 10rpx;
    margin-right: 18rpx;
    margin-bottom: 26rpx;
    position: relative;
    &:nth-of-type(2n + 2) {
      margin-right: 0;
    }
  }
  .u-emoji-box{
    position: absolute;
    width: 306rpx;
    height: 288rpx;
    left:50%;
    transform: translateX(-50%) scale(0);
    top: -20rpx;
    background-size: 100%;
    background-repeat: no-repeat;
    background-image: url("data:image/png;base64,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");
    opacity: 0;
    transition: all .2s ease;
  }
  .showEmj{
    opacity: 1;
    transform:translateX(-50%) scale(1) ;
  }
  .u-inner-box{
    width: 306rpx;
    height: 220rpx;
    @include flex;
    img{
     width:264rpx;
    height:210rpx;
    }
  }
  .u-seat-top {
    width: 100%;
    height: 118rpx;
    @include flex;
    justify-content: flex-start;
    border-bottom: 2rpx solid #6f6f8e;
    box-sizing: border-box;
    background: #2d308c;
    img {
      width: 78rpx;
      height: 78rpx;
      margin-left: 20rpx;
      margin-right: 12rpx;
    }
    .u-info {
      .u-name {
        font-size: 32rpx;
        color: #fbed21;
        width: 142rpx;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
        margin-bottom: 6rpx;
      }
      .u-area {
        @include flex;
        justify-content: flex-start;
        font-size: 20rpx;
        div {
          margin-right: 8rpx;
        }
        img {
          margin-left: 4rpx;
        }
        .girl {
          width: 14rpx;
          height: 22rpx;
        }
        .boy {
          width: 24rpx;
          height: 21rpx;
        }
      }
    }
    .u-master {
      width: 54rpx;
      height: 46rpx;
      border: 2rpx solid #fff;
      border-radius: 12rpx;
      @include flex;
      font-size: 20rpx;
    }
  }
  .u-seat-bottom {
    width: 100%;
    height: 294rpx;
    position: relative;
    .img-ready {
      width: 123rpx;
      height: 56rpx;
      position: absolute;
      left: 50%;
      bottom: 10rpx;
      transform: translateX(-50%);
    }
    .u-bg,
    .u-head-img,
    .u-mask {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
    }
    .u-bg {
      width: 380rpx;
      height: 371rpx;
      top: 55%;
    }
    .u-head-img,
    .u-mask {
      width: 150rpx;
      height: 150rpx;
      border-radius: 50%;
    }
    .u-mask {
      background: rgba(#000, 0.5);
      @include flex;
      font-size: 42rpx;
      .u-kick {
        position: absolute;
        bottom: -60rpx;
        left: 50%;
        transform: translateX(-50%);
        width: 60rpx;
        height: 60rpx;
        border-radius: 50%;
        background: #ff217b;
        @include flex;
        font-size: 28rpx;
      }
    }
  }
}
.u-btn {
  position: fixed;
  z-index: 30;
  top: 1000rpx;
  width: 100%;
  @include flex;
  justify-content: center;
  .u-btn-left,
  .u-btn-right {
    @include flex;
    font-size: 28rpx;
    border-radius: 20rpx;
  }
  .u-btn-left {
    width: 294rpx;
    height: 86rpx;
    margin-right:30rpx;
    background: #84d75d;
  }
  .u-btn-right {
    width: 214rpx;
    height: 86rpx;
    background: #ff217b;
    padding: 0;
    margin: 0;
    margin-right: 30rpx;
    color: #fff;
  }
  .u-btn-emoji{
    width: 73rpx;
    height: 70rpx;
  }
}
.timer-mask {
  width: 100%;
  height: 100%;
  background: rgba(#000, 0.5);
  color: #fff;
  position: fixed;
  display: flex;
  flex-direction: column;
  left: 0;
  top: 0;
  z-index: 20;
  justify-content: flex-start;
  align-items: center;
  font-size: 50rpx;
  font-weight: bold;
  div {
    font-size: 35rpx;
    font-weight: normal;
    margin-bottom: 80rpx;
    margin-top: 400rpx;
  }
}

.full-man-mask {
  width: 100%;
  height: 100%;
  background: rgba(#000, 0.35);
  position: fixed;
  left: 0;
  top: 0;
  z-index: 40;
  .f-bg {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    top: 340rpx;
    width: 650rpx;
    height: 350rpx;
    background: rgba(#1e1a42, 0.7);
    border-radius: 12rpx;
  }
  .f-inner {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    top: 360rpx;
    width: 606rpx;
    height: 306rpx;
    background: #2a2e73;
    border-radius: 12rpx;
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    flex-direction: column;
    .f-txt {
      font-size: 34rpx;
      text-align: center;
      margin: 88rpx 0;
    }
    .f-btn {
      width: 128rpx;
      height: 48rpx;
      display: flex;
      align-items: center;
      justify-content: center;
      text-align: center;
      background: #ff217b;
      border-radius: 12rpx;
      font-size: 22rpx;
    }
  }
}
</style>
