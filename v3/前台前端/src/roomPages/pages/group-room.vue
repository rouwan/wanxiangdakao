<template>
  <div class="hyb-room">
    <!-- 背景 -->
    <img class="entry-bg"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-test.png">
    <!-- 主体 -->
    <div class="match bg-match_w group-icon"
         :class="{'show-match':roomType=='match'}">
    </div>
    <div class="match-success bg-match_s group-icon"
         :class="{'show-match-success':roomType=='matchSuccess'}">
    </div>
    <div class="hyb-box"
         v-if="roomType!='playing'"
         :class="{'box-match':roomType=='match'||roomType=='matchSuccess'}">
      <div class="time-box flex"
           v-if="roomType=='matchSuccess'">{{ctime}}秒后进入</div>
      <div class="h-head">
        <div class="mode-type bg-group_type group-icon"></div>
        <div class="gold-box">
          <img src="/static/gold.png">{{appInfo.money||0}}
        </div>
      </div>
      <div class="h-body">
        <div class="user-box flex"
             v-for="(seat,seatIndex) in emtptList"
             :key="seatIndex">
          <div class="user-head flex radius50">
            <div class="ques db-center bg-ques group-icon"></div>

            <img v-if="userListL[seatIndex]&&userListL[seatIndex].boot"
                 class="head-img db-center radius50"
                 :src="IMGHEAD+userListL[seatIndex].basicInfo.avatarUrl">

            <img v-if="userListL[seatIndex]&&!userListL[seatIndex].boot"
                 class="head-img db-center radius50"
                 :src="userListL[seatIndex].basicInfo.avatarUrl">

            <div v-if="userListL[seatIndex]&&userListL[seatIndex].isReady==1&&masterId!=userListL[seatIndex].basicInfo.userId&&roomType=='wait'"
                 class="ready db-center radius50">
              <div class="db-center group-icon bg-ready"></div>
            </div>
            <div class="u-ant-cycle-fill"
                 v-if="!userListL[seatIndex]&&roomType=='match'">
              <div class="u-ant-dot"></div>
              <div class="u-ant-dot"></div>
              <div class="u-ant-dot"></div>
            </div>
          </div>
          <div v-if="userListL[seatIndex]"
               class="user-info">
            <div>{{userListL[seatIndex].basicInfo.nickName||'无名氏'}}</div>
            <div>{{userListL[seatIndex].basicInfo.city||'火星'}}</div>
          </div>
          <div v-else
               class="user-info-empty">
            <div></div>
            <div></div>
          </div>
        </div>
      </div>
    </div>
    <!-- 按钮区 -->
    <div class="btn-box flex"
         v-if="roomType=='wait'">
      <!-- 快速开始 -->
      <div v-if="masterId==appInfo.userId"
           class="group-icon bg-start_btn"
           hover-class="click-active "
           @click="throttle(handleStartGame,400)"></div>


  <view class="container">
    <!-- 下拉选择框 -->
    <picker mode="selector" :range="pickerOptions" @change="onPickerChange" :value="pickerSelectedValue">
      <view class="picker-item">{{ pickerSelectedValue || '请选择类别' }}</view>
    </picker>
<view class="picker-label">当前选择: {{pickerSelectedValue }}</view>

  </view>

     

      <!-- 准备 -->
      <div v-if="masterId!=appInfo.userId&&userReady==0"
           hover-class="click-active"
           class="group-icon bg-ready_btn"
           @click="handleReadyClick"></div>
      <!-- 取消 -->
      <div v-if="masterId!=appInfo.userId&&userReady==1"
           @click="handleReadyClick"
           class="group-icon bg-cancel_btn"
           hover-class="click-active"></div>
      <div v-if="userList.length==3||userReady==1&&masterId!=appInfo.userId"
           @click="unAbleInvate"
           hover-class="click-active"
           class="invate group-icon bg-invate_btn">
      </div>
      <button v-else
              hover-class="click-active"
              open-type="share"
              plain="true">
        <div class="group-icon bg-invate_btn"></div>
      </button>
    </div>

    <!-- 开始游戏界面 showQuesModules-->
    <div class="m-ques-modal"
         v-if="showQuesModules">

      <div class="u-time"
           v-if="showTop"
           :class="{'show-top':showTop}">
        <time-count :empty="true"
                    :small="true"
                    :initBtn="initTimeCount"
                    :startBtn="startTimeCount"
                    :initTime="initTime"
                    :stopBtn="stopTimeCount">
        </time-count>
      </div>
      <div class="top-area w100 flex"
           :class="{'show-top':showTop}">
        <!-- 左边分组用户 -->
        <div class="g-l flex flex-col"
             :class="{'show':showUserList}">
          <div class="u pos-r"
               v-for="(uLeft,uindex) in userListL"
               :key="uindex">
            <div :class="{'show-score':uLeft.showScore}"
                 class="score pos-a left-center">{{uLeft.score}}</div>
            <div class="self-tag pos-a left-center">
              <img class="w100 h100"
                   v-if="uLeft.basicInfo.userId==appInfo.userId"
                   src="/static/rank/arrow-d.png">
            </div>
            <img v-if="uLeft.boot"
                 class="w100 h100 border-50"
                 :src="IMGHEAD+uLeft.basicInfo.avatarUrl">
            <img v-else
                 class="w100 h100 border-50"
                 :src="uLeft.basicInfo.avatarUrl">
            <div class="u-mask w100 h100 pos-a flex border-50"
                 v-if="uLeft.showScore">
              <img v-if="uLeft.right"
                   class="yes pos-a db-center"
                   src="/static/rank/yes-v.png">
              <img v-if="!uLeft.right"
                   class="err pos-a db-center"
                   src="/static/rank/err-v.png">
            </div>
          </div>

        </div>
        <!-- 右边分组用户 -->
        <div class="g-r flex flex-col"
             :class="{'show':showUserList}">
          <div class="u pos-r"
               v-for="(u,ind) in userListR"
               :key="ind">
            <div :class="{'show-score':u.showScore}"
                 class="score pos-a left-center">{{u.score}}</div>
            <div class="self-tag pos-a left-center">
              <img class="w100 h100"
                   v-if="u.basicInfo.userId==appInfo.userId"
                   src="/static/rank/arrow-d.png">
            </div>
            <img class="w100 h100 border-50"
                 :src="IMGHEAD+u.basicInfo.avatarUrl">
            <div class="u-mask w100 h100 pos-a flex border-50"
                 v-if="u.showScore">
              <img v-if="u.right"
                   class="yes"
                   src="/static/rank/yes-v.png">
              <img v-if="!u.right"
                   class="err"
                   src="/static/rank/err-v.png">
            </div>
          </div>

        </div>
        <!-- 总分框 -->
        <div class="t-score pos-a flex left-center">
          <div class="t-l w100 h100 flex">{{groupScoreLeft}}</div>
          <div class="t-r w100 h100 flex">{{groupScoreRight}}</div>
        </div>
        <!-- 题目显示区域 暂时两种类型 -->
        <div class="ques-title pos-a left-center">
          <!-- 题目抬头 -->
          <div class="ques-tag flex flex-col pos-a top-center"
               :class="{'show-tag':showQuesTag,'show-tag-delay':showQuesTag&&currentIndex==7}">
            <div class="flex tag-p1"
                 v-if="currentIndex==0">第一题</div>
            <div class="flex tag-p1"
                 v-if="currentIndex==1">第二题</div>
            <div class="flex tag-p1"
                 v-if="currentIndex==2">第三题</div>
            <div class="flex tag-p1"
                 v-if="currentIndex==3">第四题</div>
            <div class="flex tag-p1"
                 v-if="currentIndex==4">第五题</div>
            <div class="flex tag-p1"
                 v-if="currentIndex==5">第六题</div>
            <div class="flex tag-p1"
                 v-if="currentIndex==6">第七题</div>
            <div class="flex tag-p1"
                 v-if="currentIndex==7">第八题</div>
            <div class="flex tag-p2"><img src="/static/rank/icon-book.png">{{quesType[list[currentIndex].parentType]}}科</div>

            <div class="q-double flex flex-col pos-a">
              <div>最后一题</div>
              <div>得分X2</div>
            </div>
          </div>
          <!-- 题目 -->
          <div :class="{'show-ques':showQuestion}"
               class="ques-box flex">
            <!-- 图文 -->
            <div class="q-img"
                 v-if="list[currentIndex].modeType==1">
              <div class="q-img-box flex">
                <img :src="IMGHEAD+list[currentIndex].image"
                     mode="aspectFit">
              </div>
              <div class="txt">{{list[currentIndex].title}}</div>
            </div>
            <!-- 文字 -->
            <div class="q-word"
                 v-else>
              {{list[currentIndex].title}}
            </div>
          </div>
        </div>
      </div>

      <!-- 选项区域 -->
      <div class="bottom-area w100 flex flex-col"
           :class="{'show-select':showQuestion}">
        <div class="select-item flex border-1000 pos-r"
             v-for="(item,sIndex) in list[currentIndex].optionList"
             :key="sIndex"
             @click="handleSelectClick(item,sIndex)"
             :class="{'right-type':item.state=='right','error-type':item.state=='error'}">
          {{item.name}}
          <!-- 选择的用户列表 -->
          <div class="s-l-list pos-a top-center">
            <div class="s-l-item"
                 :class="{'show-ans-head':userx.userId==appInfo.userId&&showMySelect||showBothAns}"
                 v-for="(userx,xIndex) in item.opl"
                 :key="xIndex">
              <img v-if="userx.userId>0"
                   :src="userx.avatarUrl">
              <img v-else
                   :src="IMGHEAD+userx.avatarUrl">
            </div>

          </div>
          <div class="s-r-list pos-a top-center">
            <div class="s-r-item"
                 :class="{'show-ans-head':showBothAns}"
                 v-for="(userr,ur) in item.opr"
                 :key="ur">
              <img v-if="userr.userId>0"
                   :src="userr.avatarUrl">
              <img v-else
                   :src="IMGHEAD+userr.avatarUrl">
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="showResult"
         class="m-ques-result">
      <img :class="{'show-r-t':showResultTitle&&groupScoreLeft>groupScoreRight}"
           class="r-t-img-s pos-a left-center"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/success-word.png">
      <img :class="{'show-r-t':showResultTitle&&groupScoreLeft==groupScoreRight}"
           class="r-t-img-b pos-a left-center"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/balance-word.png">
      <img :class="{'show-r-t':showResultTitle&&groupScoreLeft<groupScoreRight}"
           class="r-t-img-f pos-a left-center"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/fail-word.png">
      <div class="r-s-l pos-a flex"
           :class="{'show-r-s':showResultTitle}">
        {{groupScoreLeft}}
      </div>
      <div class="r-s-r pos-a flex"
           :class="{'show-r-s':showResultTitle}">
        {{groupScoreRight}}
      </div>
      <div class="r-l-l pos-a"
           :class="{'show-r-l':showForm}">
        <div class="flex"
             v-for="(user,rlIndex) in resultData.Result.room.leftRankList"
             :key="rlIndex">
          <img v-if="user.boot"
               :src="IMGHEAD+user.basicInfo.avatarUrl">
          <img v-else
               :src="user.basicInfo.avatarUrl">
          <img class="mvp"
               :class="{'show-mvp':groupScoreLeft>groupScoreRight&&user.basicInfo.userId==mvpIdL&&showMvp}"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/mvp.png">
          <div>
            <div class="r-name">{{user.basicInfo.nickName}}</div>
            <div class="r-score">{{user.abilityInfo.score}}</div>
          </div>
        </div>
      </div>
      <div class="r-r-l pos-a"
           :class="{'show-r-l':showForm}">
        <div class="flex"
             v-for="(userR,rIndex) in resultData.Result.room.rightRankList"
             :key="rIndex">
          <img v-if="userR.boot"
               :src="IMGHEAD+userR.basicInfo.avatarUrl">
          <img v-else
               :src="userR.basicInfo.avatarUrl">
          <img class="mvp"
               :class="{'show-mvp':groupScoreLeft<groupScoreRight&&userR.basicInfo.userId==mvpIdR&&showMvp}"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/mvp.png">
          <div>
            <div class="r-name">{{userR.basicInfo.nickName}}</div>
            <div class="r-score">{{userR.abilityInfo.score}}</div>
          </div>
        </div>
      </div>
      <div class="r-gift pos-a left-center"
           :class="{'show-gift':showGift}">
        <div class="g-t">奖励</div>
        <div class="g-g flex">
          <img src="/static/gold.png">
          <div v-if="myResult.abilityInfo&&showResult">X{{goldValue}}</div>
        </div>
      </div>
      <img @click="backRoom"
           class="r-btn pos-a btn-back"
           :class="{'show-gift':showGift}"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/btn-more.png">
       <img @click="toGameReview"
           class="r-btn pos-a btn-pro"
           :class="{'show-gift':showGift}"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/btn-pro.png">
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import timeCount from "../../pages/game/components/timeCount.vue";
import { getRondom, throttle } from "@/utils/index";
import { shareInfoForGroup } from "@/config/dataFile";
export default {
  onShareAppMessage: function(res) {
    let index = getRondom(0, 1);
    let obj = {
      title: shareInfoForGroup[index].title,
      imageUrl: shareInfoForGroup[index].imgUrl,
      path: `/pages/entry/index?roomId=${this.roomId}&userId=${
        this.appInfo.userId
      }`
    };
    if (index == 1) {
      obj.title = this.appInfo.nickname + shareInfoForGroup[index].title;
    }
    return obj;
  },
  data() {
    return {
    pickerOptions: ['人文历史', '自然科学', '艺术文化', '健康常识', '经济金融', '生活应用'],
    pickerSelectedValue: '',

      // boolean
      masterId: 0,
      //data
      userReady: 0,
      gameId: 0,
      roomId: 0,
      roomType: "wait",
      query: {}, //页面参数
      emtptList: 3,
      userList: [],
      allReady: false,
      ctime: 3,
      // 组件控制
      showQuesModules: false,
      titleOff: true,
      selectOff: false,
      showTitle: true,
      showSelect: false, //
      showResult: false, //展示结果
      initTime: 10,
      initTimeCount: false,
      startTimeCount: false,
      stopTimeCount: false,
      // 题目相关
      currentIndex: 0,
      list: [],
      levelList: [],
      //答题相关
      showBothAns: false,
      shareType: "",
      throttle: throttle,
      // 新增
      mine: {}, //个人用户信息
      groupScoreLeft: 0,
      groupScoreRight: 0,
      showTop: false,
      showUserList: false,
      showQuesTag: false,
      showQuestion: false,
      quesType: {
        2: "商",
        3: "文",
        4: "理",
        5: "艺",
        6: "体",
        7: "综"
      },
      optionList: [],
      allUserList: [],
      userListL: [], //左边用户列表
      userListR: [], //右边用户列表
      isAnsCurrent: false, //是否回答过当前题目
      ansTimer: null,
      ansTime: 10000, //初始10S 精度100
      timeOut: false,
      isGameEnd: false,
      showResultTitle: false,
      showForm: false,
      showGift: false,
      showMvp: false,
      showMySelect: false,
      resultData: {},
      myResult: {},
      mvpWrapL: 0,
      mvpWrapR: 0,
      mvpIdL: 0,
      mvpIdR: 0,
      goldValue: 0,
      deldayHandleList: [] //type4 统一处理
    };
  },
  components: {
    "time-count": timeCount
  },
  computed: {
    ...mapState({
      appInfo: state => state.appInfo,
      EVN: state => state.EVN,
      IMGHEAD: state => state.IMGHEAD,
      HUAWEI: state => state.HUAWEI,
      connected: state => state.socketStore.connected,
      messageListeners: state => state.socketStore.messageListeners,
      messageBus: state => state.socketStore.messageBus,
      globalSoundPlayer: state => state.globalSoundPlayer
    })
  },
  methods: {
    ...mapMutations({
      setAppInfo: "setAppInfo",
      setGameRecord: "setGameRecord",
      connect: "socketStore/connect",
      addMessageListener: "socketStore/addMessageListener",
      removeMessageListener: "socketStore/removeMessageListener",
      send: "socketStore/send",
      close: "socketStore/close",
      setPageType: "socketStore/setPageType"
    }),
    // 对局menthods
      toGameReview(){
      this.$router.push('/pages/gameReview/index?from=room');
    },
    //题目类型选择
    onPickerChange(e) {
    const selectedIndex = e.detail.value; // 获取选中项的索引
    this.pickerSelectedValue = this.pickerOptions[selectedIndex]; // 更新选中项的值
    },
    handleShowUserList() {
      let t1 = setTimeout(() => {
        this.showTop = true;
      }, 500);
      let t2 = setTimeout(() => {
        this.showUserList = true;
      }, 1000);
      let t3 = setTimeout(() => {
        this.handleShowQuesTag();
      }, 2000);
    },
    handleShowQuesTag() {
      this.showQuesTag = true;
      let delay = 0;
      if (this.currentIndex == 7) {
        delay = 1000;
      }
      let t1 = setTimeout(() => {
        this.showQuesTag = false;
      }, 1500 + delay);
      let t2 = setTimeout(() => {
        this.handleShowQuestion();
      }, 2000 + delay);
    },
    handleShowQuestion() {
      if (this.roomType == "playing") {
        this.showQuestion = true;
        let t1 = setTimeout(() => {
          this.startAnswer();
        }, 500);
      }
    },
    shake() {
      wx.vibrateShort({
        success: () => {}
      });
    },
    startAnswer() {
      //开始当前题目
      this.startTimeCount = !this.startTimeCount;
      this.startAnsTimer();
      this.send(`{'type':6,'data':'${this.gameId}'}`);
    },
    /** 播放答题音效（globalSoundPlayer 可能未初始化） */
    audioPlay(type) {
      if (!this.globalSoundPlayer) return;
      if (this.globalSoundPlayer.volume == 0) {
        return false;
      }
      try {
        if (type == "success") {
          this.globalSoundPlayer.src =
            "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/right.mp3";
        } else {
          this.globalSoundPlayer.src =
            "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/wrong.mp3";
        }
        this.globalSoundPlayer.play();
        let t1 = setTimeout(() => {
          if (this.globalSoundPlayer) this.globalSoundPlayer.src = "";
        }, 3000);
      } catch(e) {}
    },

    handleSelectClick(item, index) {
      // 判定 是否点过、是否时间到、是否答过此题
      if (!item.select && !this.timeOut && !this.isAnsCurrent) {
        this.isAnsCurrent = true;
        item.select = true;
        // setTimeout(() => {
        this.pushItemLimitSingle(item.opl, this.mine.basicInfo);
        setTimeout(() => {
          this.showMySelect = true;
        }, 400);
        // }, 500);

        if (item.sortId == this.list[this.currentIndex].optionSortId) {
          item.state = "right";
          this.audioPlay("success");
        } else {
          item.state = "error";
          this.audioPlay("fail");
        }
        let obj = {
          type: 4,
          data: {
            answer: item.sortId,
            index: this.currentIndex,
            seconds: this.ansTime,
            timeout: false,
            uid: this.mine.basicInfo.userId,
            gameId: this.gameId,
            roomId: this.roomId
          }
        };
        obj = JSON.stringify(obj);
        this.send(obj, "send-submitAns");
      }
    },
    handleShowRightAns() {
      let list = this.list[this.currentIndex];
      for (let i = 0; i < list.optionList.length; i++) {
        if (list.optionList[i].sortId == list.optionSortId) {
          list.optionList[i].state = "right";
        }
      }
    },
    startAnsTimer() {
      clearInterval(this.ansTimer);
      this.timeOut = false;
      this.ansTime = 10000;
      this.isAnsCurrent = false;

      this.ansTimer = setInterval(() => {
        if (this.ansTime <= 0) {
          this.timeOut = true;
          clearInterval(this.ansTimer);
        }
        this.ansTime -= 100;
      }, 100);
    },
    // socket listener
    handleMessage(message) {
      let eventHandleMap = {
        "socketType-1": () => this.startMatch(message), //type1 初始化、重置房间信息
        socketType1: () => this.updateRoom(message), //type1 初始化、重置房间信息
        socketType2: () => this.checkOldGame(message),
        socketType4: () => this.answerDelay(message), //type4 答题信息
        socketType7: () => this.gameEnd(message), //type7 游戏结束
        socketType6: () => this.handleNextQuestion(message)
      };
      //执行对应的方法
    
      if (typeof eventHandleMap[`socketType${message.Type}`] == "function") {
        eventHandleMap[`socketType${message.Type}`]();
      }
    },
    // socket handle
    createdRoom() {
      this.send(`{'type':13,'data':1,'modeType':2}`, "创建房间");
    },
    startMatch() {
      console.log("收到-1");
      this.roomType = "match";
      this.send(`{'type':1,'data':'${this.gameId}'}`); //开始游戏(寻找匹配)
    },
    handleReadyClick() {
      this.send(`{'type':10,'data':'${this.gameId}'}`);
    },
    handleStartGame() {
      // 检查准备状态
      let state = this.checkAllReady();
      if (state) {
        this.send(`{'type':-1,'data':'${this.gameId}'}`); //开始匹配
      } else if (!state) {
        wx.showToast({
          title: "有玩家未准备",
          icon: "none"
        });
      }
    },
    handleNextQuestion(data) {
      console.log("接受type6", data);
      if (this.currentIndex != data.Result.index) {
        this.stopTimeCount = !this.stopTimeCount;
        this.handleShowRightAns();
        let t1 = setTimeout(() => {
          this.showUsersSelect();
          setTimeout(() => {
            this.showBothAns = true;
          }, 500);
          clearInterval(this.ansTimer);
        }, 1000);
        let t2 = setTimeout(() => {
          // 展示选项 先隐藏
          this.showQuestion = false;
          this.showMySelect = false;
        }, 2500);
        let t3 = setTimeout(() => {
          // 下一题
          this.currentIndex = data.Result.index;
          this.initTimeCount = !this.initTimeCount;
          this.initTime = 10;
          this.showBothAns = false;
          this.changeScore(this.userListL, null, "del");
          this.changeScore(this.userListR, null, "del");
          this.handleShowQuesTag();
        }, 3500);
      }
    },
    checkOldGame(data) {
      // TODO 重置题目序号，时间，
      console.log("游戏重连--------------", data);
      if (data.Result == "") {
        wx.showModal({
          title: "系统提示",
          content: "对局已结束,离开房间",
          showCancel: false,
          success: res => {
            if (res.confirm) {
              this.$router.back();
            }
          }
        });
      } else {
        this.initTime = Math.ceil(data.Result.room.restSecond / 1000);
        this.currentIndex = data.Result.room.answerIndex;
        this.startTimeCount = !this.startTimeCount;
      }
    },
    getList(res) {
      this.levelList = [];
      this.list = res.Result.game.subjectList;
      for (let i = 0; i < this.list.length; i++) {
        // 处理选项
        for (let j = 0; j < this.list[i].optionList.length; j++) {
          this.list[i].optionList[j].state = null;
          this.list[i].optionList[j].name = this.list[i].optionList[
            j
          ].name.replace(/\s+/g, "");
          this.list[i].optionList[j].opl = [];
          this.list[i].optionList[j].opr = [];
        }
      }
    },
    changeScore(list, data, type) {
      if (type == "add") {
        for (let i = 0; i < list.length; i++) {
          if (list[i].basicInfo.userId == data.uid) {
            list[i].score = data.scoreSub;
            list[i].showScore = true;
            list[i].right = data.right;
          }
        }
      } else if (type == "del") {
        for (let i = 0; i < list.length; i++) {
          // list[i].score = -1;
          list[i].right = false;
          list[i].showScore = false;
        }
      }
    },
    getUserById(id) {
      let list = this.allUserList;
      for (let i = 0; i < list.length; i++) {
        if (list[i].basicInfo.userId == id) {
          return list[i].basicInfo;
          break;
        }
      }
    },
    pushItemLimitSingle(list, item) {
      if (list.length == 0) {
        list.push(item);
        return false;
      } else {
        try {
          for (let i = 0; i < list.length; i++) {
            if (list[i].userId == item.userId) {
              //若出错，可能对象属性顺序不对（备注）
              return false;
              break;
            }
          }

          if (item.userId == this.mine.basicInfo.userId) {
            list.unshift(item);
          } else {
            list.push(item);
          }
        } catch (e) {
          console.log(e);
        }
      }
    },
    answerDelay(data) {
      this.deldayHandleList.push(data);
      this.changeScore(this.userListL, data.Result, "add");
      this.changeScore(this.userListR, data.Result, "add");
    },
    showUsersSelect(data) {
      for (let i = 0; i < this.deldayHandleList.length; i++) {
        let info = this.deldayHandleList[i].Result;
        let isLeft = false;
        let optionList = this.list[this.currentIndex].optionList;
        let userInfo = this.getUserById(info.uid);
        console.log("response-type4", info, new Date());

        // uesrList 赋值
        // 先判断 是左边右边，在赋值
        // this.changeScore(this.userListL, info, "add");
        // this.changeScore(this.userListR, info, "add");

        // 给选项赋值
        // 选项列表，左边右边 插入
        if (this.mine.groupId == info.groupId) {
          isLeft = true;
          if (info.score > this.mvpWrapL) {
            this.mvpWrapL = info.score;
            this.mvpIdL = info.uid;
          }
          this.groupScoreLeft = info.groupScore;
        } else {
          if (info.score > this.mvpWrapR) {
            this.mvpWrapR = info.score;
            this.mvpIdR = info.uid;
          }
          this.groupScoreRight = info.groupScore;
        }

        for (let i = 0; i < optionList.length; i++) {
          if (optionList[i].sortId == info.answer) {
            if (isLeft) {
              this.pushItemLimitSingle(optionList[i].opl, userInfo);
            } else {
              this.pushItemLimitSingle(optionList[i].opr, userInfo);
            }
          }
        }
      }
      setTimeout(() => {
        this.deldayHandleList = [];
      }, 2000);
    },
    handleCloseResult() {
      this.showResult = false;
    },
    getUserList(u) {
      //分类用户列表
      this.mine = {};
      let init = { score: 0, right: false, showScore: false };
      this.userListL = [];
      this.userListR = [];
      for (let i = 0; i < u.length; i++) {
        if (u[i].basicInfo.userId == this.appInfo.userId) {
          this.mine = u[i];
          break;
          // this.userListL.push(Object.assign(u[i], init));
        }
      }

      for (let i = 0; i < u.length; i++) {
        if (
          u[i].groupId == this.mine.groupId
          // &&
          // u[i].basicInfo.userId != this.mine.basicInfo.userId
        ) {
          this.userListL.push(Object.assign(u[i], init));
        } else if (u[i].groupId != this.mine.groupId) {
          this.userListR.push(Object.assign(u[i], init));
        }
      }
    },
    handleUpdateRoom(res) {
      this.gameId = res.Result.game.gameId;
      this.roomId = res.Result.room.roomId;
      this.allUserList = res.Result.room.userList;
      this.masterId = res.Result.room.roomMaster;
      // 分类用户列表
      this.getUserList(this.allUserList);

      for (let i = 0; i < this.allUserList.length; i++) {
        if (this.appInfo.userId == this.allUserList[i].basicInfo.userId) {
          this.userReady = this.allUserList[i].isReady;
          console.log(this.userReady);
        }
      }

      if (
        res.Result.room.roomStatus == 0 &&
        res.Result.game.subjectList &&
        res.Result.game.subjectList.length > 0
      ) {
        this.getList(res);
        this.currentIndex = 0;
        this.roomType = "matchSuccess";
        let timer = setInterval(res => {
          this.ctime--;
          if (this.ctime <= 0) {
            clearInterval(timer);
            this.ctime = 3;
          }
        }, 1000);

        let t1 = setTimeout(() => {
          if (this.roomType == "matchSuccess") {
            this.showQuesModules = true; //显示答题界面
            this.roomType = "playing";
            this.handleShowUserList();
          }
        }, 3000);
      }
    },
    // 房间状态
    updateRoom(res) {
      if (this.isGameEnd) {
        let t1 = setTimeout(() => {
          this.handleUpdateRoom(res);
        }, 3500);
      } else {
        this.handleUpdateRoom(res);
      }
    },
    backRoom() {
      this.showResult = false;
      this.showResultTitle = false;
      this.showForm = false;
      this.showMvp = false;
      this.showGift = false;
      this.showUserList = false;
      this.list = [];
      this.resultData = {};
      this.myResult = {};
      this.groupScoreLeft = 0;
      this.groupScoreRight = 0;
      this.mvpWrapR = 0;
      this.mvpWrapL = 0;
      this.mvpIdL = 0;
      this.mvpIdR = 0;
      this.showBothAns = false;
    },
    autoClearTimer(fn, time) {
      let t = setTimeout(() => {
        fn();
        clearTimeout(t);
      }, time);
    },
    gameEnd(value) {
      this.isGameEnd = true;
      this.stopTimeCount = !this.stopTimeCount;
      let t1 = setTimeout(() => {
        this.isGameEnd = false;
      }, 3500);
      this.handleShowRightAns();
      this.showUsersSelect();
      setTimeout(() => {
        this.showBothAns = true;
      }, 1000);
      setTimeout(() => {
        this.resultData = value;
        let list = value.Result.room.userList;
        for (let i = 0; i < list.length; i++) {
          if (list[i].basicInfo.userId == this.appInfo.userId) {
            this.myResult = list[i];
          }
        }
        this.roomType = "wait";
        this.setGameRecord(value);
        for (let i = 0; i < value.Result.room.userList.length; i++) {
          if (
            this.appInfo.userId ==
            value.Result.room.userList[i].basicInfo.userId
          ) {
            this.goldValue =
              value.Result.room.userList[i].abilityInfo.money -
              this.appInfo.money;
            this.setAppInfo({
              money: value.Result.room.userList[i].abilityInfo.money
            });

            break;
            // this.appInfo.money =
            //   value.Result.room.userList[i].abilityInfo.money;
          }
        }

        let t4 = setTimeout(() => {
          this.showQuesModules = false;
          this.showTop = false;
          this.showUserList = false;
          this.showQuestion = false;
          this.showResult = true;
          this.currentIndex = 0;
          let t5 = setTimeout(() => {
            this.showResultTitle = true;
          }, 500);
          let t6 = setTimeout(() => {
            this.showForm = true;
          }, 700);
          let t7 = setTimeout(() => {
            this.showMvp = true;
          }, 1000);
          let t8 = setTimeout(() => {
            this.showGift = true;
          }, 1200);
        }, 1000);
      }, 2500);
    },
    handleOnShow() {
      if (this.shareType == "result") {
        this.shareType = "";
      }
      this.hideForRoute = false;
      this.setPageType(2); //标识当前页面，断网重连时候可以使用
      if (!this.connected) {
        this.connect({ type: 2 });
      }
    },
    handleOnHide() {
      this.setPageType(0); //标识当前页面
      this.send(`{'type':17,'data':'0'}`, "隐藏告知服务端");
      if (this.hideForRoute) {
        this.setPageType(2); //标识当前页面
        return false;
      }
      try {
        this.close();
      } catch (err) {}
    },
    // 特殊情况处理
    unAbleInvate() {
      wx.showToast({ title: "人数已满或准备中不能邀请", icon: "none" });
    },
    checkAllReady() {
      let state = true;
      this.userList.forEach(item => {
        item.isReady == 0 ? (state = false) : null;
      });
      return state;
    }
  },
  mounted() {
    this.query = this.$root.$mp.query;
    this.addMessageListener(this.handleMessage);
    if (this.query.type == "group") {
      this.createdRoom();
    } else if (this.query.type == "messageBus") {
      //接收房间页的处理方法
      this.handleMessage(this.messageBus);
    }
  },
  onShow() {
    this.throttle(this.handleOnShow, 200);
  },
  onHide() {
    this.throttle(this.handleOnHide, 200);
  },
  async onUnload() {
    this.currentIndex = 0;
    this.userList = [];
    this.isFirst = true;
    this.emojiBtn = false;
    this.showQuesModules = false;
    this.showQuestion = false;
    this.showResult = false;
    this.showResultTitle = false;
    this.showForm = false;
    this.showGift = false;
    this.userListL = [];
    this.userListR = [];
    this.backRoom();
    this.roomType = "wait";
    // setTimeout(() => {
    //   this.showQuesModules = false;
    //   setTimeout(() => {
    //     this.roomType = "wait";
    //   }, 2500);
    // }, 3000);

    delete this.$root.$mp.query;
    await this.send(`{'type':5,'data':'${this.gameId}'}`, "离开页面");
    this.removeMessageListener(this.handleMessage);
    this.setPageType(0); //标识当前页面
    // this.close();
  }
};



</script>

<style lang="scss" scoped>
@import "../../scss/layout.scss";
@import "../../scss/hybird.scss";
@import "../../scss/3v3-img-map.scss";
.bg-start_btn {
  margin-right: 100rpx;
}

.h-head {
  .mode-type {
    width: 226rpx;
    height: 76rpx;
  }
}
.u-time {
  top: 38rpx;
  opacity: 0;
  transition: all 0.2s ease;
}

.top-area {
  height: 464rpx;
  justify-content: space-between;
  box-sizing: border-box;
  padding: 0 20rpx;
  opacity: 0;
  transition: all 0.2s ease;
}
.show-top {
  opacity: 1;
}

.g-l,
.g-r {
  transition: all 0.2s ease;
  margin-top: 80rpx;
  .u {
    width: 90rpx;
    height: 90rpx;
    margin-bottom: 50rpx;
    .score {
      width: 80rpx;
      text-align: center;
      top: -40rpx;
      font-size: 36rpx;
      font-weight: bold;
      -webkit-text-stroke: 2rpx #fff;
      z-index: 4;
      color: #418ff0;
      opacity: 0;
      transform: translate(-50%, 20rpx);
      transition: all 0.2s ease;
    }
    .show-score {
      opacity: 1;
      transform: translate(-50%, 0);
    }
    .self-tag {
      width: 50rpx;
      height: 35rpx;
      top: -25rpx;
      z-index: 2;
    }
    &:last-child {
      margin-bottom: 0;
    }
    & > img {
      border: 6rpx solid #fff;
      box-sizing: border-box;
      // box-shadow: 0 6rpx 0rpx 0rpx #5eb1ea;
    }
    .u-mask {
      top: 0;
      left: 0;
      background: rgba(#000, 0.42);
      .yes {
        width: 41rpx;
        height: 41rpx;
      }
      .err {
        width: 37rpx;
        height: 37rpx;
      }
    }
  }
}

.g-l {
  transform: translateX(-140rpx);
}
.g-r {
  transform: translateX(140rpx);
  .u {
    .score {
      color: #ff4789;
    }
    & > img {
      // box-shadow: 0 6rpx 0rpx 0rpx #e57492;
    }
  }
}

.show {
  transform: translate(0);
}

.ques-title {
  top: 90rpx;
  width: 475rpx;
  height: 465rpx;
  border: 4rpx solid #000;
  border-radius: 32rpx;
  background: #fff;
  box-sizing: border-box;
  font-size: 36rpx;
  color: #333;
  .ques-box {
    width: 100%;
    height: 100%;
    padding: 0 20rpx;
    font-weight: bold;
    box-sizing: border-box;
    opacity: 0;
    transition: all 0.2s ease;
    .q-img {
      margin-top: 40rpx;
      .q-img-box {
        width: 340rpx;
        height: 238rpx;
        margin: 0 auto 20rpx auto;
        img {
          max-width: 340rpx;
          max-height: 238rpx;
        }
      }
      .txt {
        text-align: center;
      }
    }
    .q-word {
      text-align: justify;
    }
  }
  .show-ques {
    opacity: 1;
  }
  .ques-tag {
    width: 100%;
    height: 260rpx;
    box-sizing: border-box;
    background: #56589f;
    color: #fff;
    font-size: 40rpx;
    font-weight: bold;
    opacity: 0;
    transition: all 0.2s linear;
    background-size: 20rpx 20rpx;
    background-image: linear-gradient(
      -45deg,
      #464791 25%,
      #56589f 0,
      #56589f 50%,
      #464791 0,
      #464791 75%,
      #56589f 0
    );
    .tag-p1 {
      font-size: 60rpx;
      margin-bottom: 20rpx;
      opacity: 0;
    }
    .tag-p2 {
      opacity: 0;
      img {
        width: 49rpx;
        height: 46rpx;
        margin-right: 10rpx;
      }
    }
    .q-double {
      width: 100%;
      left: 50%;
      top: 50%;
      opacity: 0;
      transform: translate(-50%, -50%) scale(0);
      div {
        text-align: center;
        &:nth-of-type(1) {
          font-size: 60rpx;
          font-weight: bold;
          color: #fff;
        }
        &:nth-of-type(2) {
          font-size: 48rpx;
          color: #62f4db;
        }
      }
    }
  }
  .show-tag {
    opacity: 1;
    .tag-p1 {
      animation: tag-in-t 1s 0.2s linear forwards;
    }
    .tag-p2 {
      animation: tag-in-b 1s 0.2s linear forwards;
    }
  }
  .show-tag-delay {
    .tag-p1 {
      animation: tag-in-t 1s 1.2s linear forwards;
    }
    .tag-p2 {
      animation: tag-in-b 1s 1.2s linear forwards;
    }
    .q-double {
      animation: showDouble 1s ease;
    }
  }
  @keyframes showDouble {
    0%,
    100% {
      opacity: 0;
      transform: translate(-50%, -50%) scale(0);
    }
    25%,
    75% {
      opacity: 1;
      transform: translate(-50%, -50%) scale(1);
    }
  }
  @keyframes tag-in-t {
    0% {
      opacity: 0;
      transform: translateX(-50rpx);
    }
    25% {
      opacity: 1;
      transform: translateX(-20rpx);
    }
    75% {
      opacity: 1;
      transform: translateX(0);
    }
    100% {
      opacity: 0;
      transform: translateX(50rpx);
    }
  }
  @keyframes tag-in-b {
    0% {
      opacity: 0;
      transform: translateX(50rpx);
    }
    25% {
      opacity: 1;
      transform: translateX(20rpx);
    }
    75% {
      opacity: 1;
      transform: translateX(0);
    }
    100% {
      opacity: 0;
      transform: translateX(-50rpx);
    }
  }
}
.t-score {
  top: 40rpx;
  width: 426rpx;
  height: 108rpx;
  border-radius: 1000rpx;
  border: 4rpx solid #000;
  overflow: hidden;
  font-size: 36rpx;
  color: #fff;
  text-shadow: 0 6rpx 0 rgba(40, 35, 92, 0.42);
  font-weight: bold;
  z-index: 2;
  .t-l {
    background: #418ff0;
  }
  .t-r {
    background: #ff4789;
  }
}
.bottom-area {
  margin-top: 60rpx;
  font-size: 36rpx;
  color: #000;
  .select-item {
    background: #fff;
    width: 475rpx;
    height: 104rpx;
    margin-bottom: 24rpx;
    font-weight: bold;
    opacity: 0;
    transform: scale(0);
    transition: all 0.2s linear;
    .s-l-list,
    .s-r-list {
      width: 200rpx;
      height: 100%;
      div {
        width: 72rpx;
        height: 72rpx;
        position: absolute;
        top: 50%;
        transform: translate(0, -50%);
        opacity: 0;
        transition: all 0.2s ease;
        img {
          width: 100%;
          height: 100%;
          border: 4rpx solid #fff;
          box-sizing: border-box;
          border-radius: 50%;
        }
      }
      .show-ans-head {
        opacity: 1;
        transform: translate(0, -50%) !important;
      }
    }
    .s-l-list {
      left: -170rpx;
      .s-l-item {
        img {
          // box-shadow: 0 4rpx 0rpx 0rpx #5eb1ea;
        }
        &:nth-of-type(1) {
          z-index: 3;
          right: 0;
        }
        &:nth-of-type(2) {
          z-index: 2;
          right: 50rpx;
          transform: translate(50rpx, -50%);
        }
        &:nth-of-type(3) {
          z-index: 1;
          right: 100rpx;
          transform: translate(100rpx, -50%);
        }
      }
    }
    .s-r-list {
      right: -170rpx;
      .s-r-item {
        img {
          // box-shadow: 0 4rpx 0rpx 0rpx #e57492;
        }
        &:nth-of-type(1) {
          z-index: 3;
          left: 0;
        }
        &:nth-of-type(2) {
          z-index: 2;
          left: 50rpx;
          transform: translate(-50rpx, -50%);
        }
        &:nth-of-type(3) {
          z-index: 1;
          left: 100rpx;
          transform: translate(-100rpx, -50%);
        }
      }
    }
  }
  .right-type {
    background: #83d65d;
    color: #fff;
    animation: select 0.4s ease;
  }
  .error-type {
    background: #ff217b;
    color: #fff;
    animation: select 0.4s ease;
  }
  @keyframes select {
    0%,
    100% {
      transform: scale(1);
    }
    50% {
      transform: scale(1.2);
    }
  }
}
.show-select {
  .select-item {
    opacity: 1;
    transform: scale(1);
  }
}
.m-ques-result {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  background: #fff;
  z-index: 10;
  background-size: 750rpx 100%;
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-test.png");
}
.r-t-img-s,
.r-t-img-b,
.r-t-img-f {
  top: 50rpx;
  opacity: 0;
  transform: translate(-50%, -40rpx);
  transition: all 0.2s ease;
}
.r-t-img-s {
  width: 264rpx;
  height: 134rpx;
}
.r-t-img-b {
  width: 256rpx;
  height: 129rpx;
}
.r-t-img-f {
  width: 272rpx;
  height: 139rpx;
}
.show-r-t {
  opacity: 1;
  transform: translate(-50%, 0);
}
.r-s-l,
.r-s-r {
  top: 180rpx;
  width: 200rpx;
  height: 76rpx;
  color: #fff;
  font-size: 50rpx;
  box-sizing: border-box;
  font-weight: bold;
  border: 3rpx solid #211042;
  transition: all 0.2s ease;
}
.r-s-l {
  left: 0;
  border-top-right-radius: 40rpx;
  background: #3f90ef;
  transform: translateX(-200rpx);
}
.r-s-r {
  right: 0;
  border-top-left-radius: 40rpx;
  background: #e93e7a;
  transform: translateX(200rpx);
}
.show-r-s {
  transform: translateX(0);
}
.r-l-l {
  width: 382rpx;
  height: 471rpx;
  top: 253rpx;
  left: 0;
  padding-left: 30rpx;
  padding-top: 40rpx;
  box-sizing: border-box;
  background-size: 100%;
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/l-bg.png");
  transition: all 0.2s ease;
  transform: translateX(-382rpx);
  border-top: 4rpx solid #24194d;
  // box-shadow: -40rpx 10rpx 10rpx 0 rgba(#24194d, 0.5);
  & > div {
    margin-bottom: 48rpx;
    justify-content: flex-start;
    position: relative;
    img {
      width: 92rpx;
      height: 92rpx;
      border: 4rpx solid #fff;
      border-radius: 50%;
      margin-right: 20rpx;
    }
    .mvp {
      width: 90rpx;
      height: 44rpx;
      position: absolute;
      left: 4rpx;
      top: -20rpx;
      margin: 0;
      border: 0;
      transition: all 0.2s ease;
      transform: scale(1.4);
      opacity: 0;
    }
    div {
      width: 134rpx;
      color: #fff;
      .r-name {
        width: 134rpx;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        font-size: 28rpx;
        text-align: left;
        margin-bottom: 10rpx;
        line-height: 1;
      }
      .r-score {
        width: 100%;
        border-radius: 1000rpx;
        background: #12569b;
        padding: 2rpx 0;
        text-align: center;
        font-size: 32rpx;
      }
    }
    &:last-child {
      margin-bottom: 0;
    }
  }
}
.r-r-l {
  width: 428rpx;
  height: 471rpx;
  top: 253rpx;
  right: 0;
  padding-right: 30rpx;
  padding-top: 40rpx;
  box-sizing: border-box;
  background-size: 100%;
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/r-bg.png");
  transition: all 0.2s ease;
  transform: translateX(428rpx);
  border-top: 4rpx solid #24194d;
  // box-shadow: 20rpx 10rpx 10rpx 0 rgba(#24194d, 0.5);
  & > div {
    margin-bottom: 48rpx;
    position: relative;
    justify-content: flex-end;
    img {
      width: 92rpx;
      height: 92rpx;
      border: 4rpx solid #fff;
      border-radius: 50%;
      margin-right: 20rpx;
    }
    .mvp {
      width: 90rpx;
      height: 44rpx;
      position: absolute;
      left: 150rpx;
      top: -20rpx;
      margin: 0;
      border: 0;
      transition: all 0.2s ease;
      transform: scale(1.4);
      opacity: 0;
    }
    div {
      width: 134rpx;
      color: #fff;
      .r-name {
        width: 134rpx;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        font-size: 28rpx;
        text-align: left;
        margin-bottom: 10rpx;
        line-height: 1;
      }
      .r-score {
        width: 100%;
        border-radius: 1000rpx;
        background: #af2c5e;
        padding: 2rpx 0;
        text-align: center;
        font-size: 32rpx;
      }
    }
    &:last-child {
      margin-bottom: 0;
    }
  }
}
.show-mvp {
  opacity: 1 !important;
  transform: scale(1) !important;
}
.show-r-l {
  transform: translateX(0);
}
.r-gift {
  width: 180rpx;
  top: 765rpx;
  color: #fff;
  text-align: center;
  transition: all 0.2s ease;
  opacity: 0;
  .g-t {
    margin-bottom: 20rpx;
    line-height: 1;
    font-size: 28rpx;
  }
  .g-g {
    width: 100%;
    height: 170rpx;
    flex-direction: column;
    border-radius: 10rpx;
    background: rgba(#000, 0.3);
    img {
      width: 72rpx;
      height: 75rpx;
    }
    div {
      font-size: 35rpx;
      text-align: center;
    }
  }
}
.r-btn {
  top: 1025rpx;
  width: 214rpx;
  height: 92rpx;
  opacity: 0;
  transition: all 0.2s ease;
}
.btn-back{
  left: 100rpx;
}
.btn-pro{
  right: 100rpx;
}
.show-gift {
  opacity: 1;
}
</style>
