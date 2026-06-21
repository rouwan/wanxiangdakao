<template>
  <div class="hyb-room">
    <!-- 背景 -->
    <img class="entry-bg"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-test.png">
    <!-- 主体 -->
    <img class="match"
         :class="{'show-match':roomType=='match'}"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/match-w.png">
    <img class="match-success"
         :class="{'show-match-success':roomType=='matchSuccess'}"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/match-s.png">
    <div class="hyb-box"
         :class="{'box-match':roomType=='match'||roomType=='matchSuccess'}">
      <div class="time-box flex"
           v-if="roomType=='matchSuccess'">{{ctime}}秒后进入</div>
      <div class="h-head">
        <img class="mode-type"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/hybird-type.png">
        <div class="gold-box">
          <img src="/static/gold.png">{{appInfo.money||0}}
        </div>
      </div>
      <div class="h-body">
        <div class="user-box flex"
             v-for="(seat,index) in emtptList"
             :key="index">
          <div class="user-head flex radius50">
            <img class="ques db-center"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/ques.png">

            <img v-if="userList[index]&&userList[index].boot"
                 class="head-img db-center radius50"
                 :src="IMGHEAD+userList[index].basicInfo.avatarUrl">

            <img v-if="userList[index]&&!userList[index].boot"
                 class="head-img db-center radius50"
                 :src="userList[index].basicInfo.avatarUrl">

            <div v-if="userList[index]&&userList[index].isReady==1&&masterId!=userList[index].basicInfo.userId&&roomType=='wait'"
                 class="ready db-center radius50">
              <img class="db-center"
                   src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/ready.png">
            </div>
            <div class="u-ant-cycle-fill"
                 v-if="!userList[index]&&roomType=='match'">
              <div class="u-ant-dot"></div>
              <div class="u-ant-dot"></div>
              <div class="u-ant-dot"></div>
            </div>
          </div>
          <div v-if="userList[index]"
               class="user-info">
            <div>{{userList[index].basicInfo.nickName||'无名氏'}}</div>
            <div>{{userList[index].basicInfo.city||'火星'}}</div>
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
      <img v-if="masterId==appInfo.userId"
           hover-class="click-active"
           @click="throttle(handleStartGame,400)"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/start-btn.png">
      <!-- 准备 -->
      <img v-if="masterId!=appInfo.userId&&userReady==0"
           hover-class="click-active"
           @click="handleReadyClick"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/ready-btn.png">
      <!-- 取消 -->
      <img v-if="masterId!=appInfo.userId&&userReady==1"
           @click="handleReadyClick"
           hover-class="click-active"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/cancel-btn.png">
      <!-- 邀请 -->
      <img v-if="userList.length==4||userReady==1&&masterId!=appInfo.userId"
           @click="unAbleInvate"
           hover-class="click-active"
           class="invate"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/invate-btn.png">
      <button v-else
              hover-class="click-active"
              open-type="share"
              plain="true">
        <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/invate-btn.png">
      </button>
    </div>

    <!-- 组件 -->
    <div class="m-ques-modal"
         v-if="showQuesModules">
      <ques-head :userList="userList"
                 :ansList="ansList"
                 :update="updateHead"
                 :userNum="userList.length"></ques-head>
      <div class="u-time">
        <time-count :small="true"
                    :initBtn="initTimeCount"
                    :startBtn="startTimeCount"
                    :initTime="initTime"
                    :stopBtn="stopTimeCount">
        </time-count>
      </div>
      <div class="u-title">
        <ques-title :list="list"
                    :currentIndex="currentIndex"
                    :idiomList="idiomList"
                    :poetryList="poetryList"
                    :levelList="levelList"
                    :imgLevelList="imgLevelList"
                    :titleOff="titleOff"
                    @antEnd="handleSelectIn">
        </ques-title>
      </div>
      <div class="u-select">
        <ques-select :subjectList="list"
                     :currentIndex="currentIndex"
                     :show="showSelect"
                     :showBothAns="showBothAns"
                     :ansList="ansList"
                     :userNum="userList.length"
                     :hide="selectOff"
                     @selected="handleSelect"
                     @answerComplete="handleAnswerComplete">
        </ques-select>
      </div>
    </div>
    <div class="u-result"
         v-if="showResult">
      <ques-result :result="resultData"
                   @close="handleCloseResult"
                   @route="handleRoute"></ques-result>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import quesHead from "../components/quesHead.vue";
import quesTitle from "../components/quesTitle.vue";
import quesSelect from "../components/quesSelect.vue";
import quesResult from "../components/quesResult.vue";
import timeCount from "../../pages/game/components/timeCount.vue";
import { getRondom,throttle } from "@/utils/index";
import { shareInfoForRoom } from "@/config/dataFile";
export default {
  onShareAppMessage: function(res) {
    let index = getRondom(0, shareInfoForRoom.length-1);
    let obj = {
      title:shareInfoForRoom[index].title,
      imageUrl: shareInfoForRoom[index].imgUrl,
      path: `/pages/entry/index?roomId=${this.roomId}&userId=${
        this.appInfo.userId
      }`
    };
    if (this.showResult) {
      obj = {
        title: "快来看我智战群雄",
        path: `/pages/entry/index?userId=${this.appInfo.userId}`
      };
    }
    return obj;
  },
  data() {
    return {
      // boolean
      masterId: 0,
      //data
      userReady: 0,
      gameId: 0,
      roomId: 0,
      roomType: "wait",
      query: {}, //页面参数
      emtptList: ["1", "1", "1", "1"],
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
      updateHead: false,
      // 题目相关
      currentIndex: 0,
      list: [],
      levelList: [],
      idiomList: [],
      poetryList: [],
      imgLevelList: [],
      //答题相关
      ansList: [],
      showBothAns: false,
      shareType: "",
      throttle:throttle,
    };
  },
  components: {
    "ques-head": quesHead,
    "time-count": timeCount,
    "ques-title": quesTitle,
    "ques-select": quesSelect,
    "ques-result": quesResult
  },
  computed: {
    ...mapState({
      appInfo: state => state.appInfo,
      EVN: state => state.EVN,
      IMGHEAD: state => state.IMGHEAD,
      HUAWEI: state => state.HUAWEI,
      connected: state => state.socketStore.connected,
      messageListeners: state => state.socketStore.messageListeners,
      messageBus: state => state.socketStore.messageBus
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
    // socket listener
    handleMessage(message) {
      let eventHandleMap = {
        "socketType-1": () => this.startMatch(message), //type1 初始化、重置房间信息
        socketType1: () => this.updateRoom(message), //type1 初始化、重置房间信息
        socketType2: () => this.checkOldGame(message),
        socketType4: () => this.answerResponse(message), //type4 答题信息
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
      this.send(`{'type':13,'data':1,'modeType':1}`, "创建房间");
    },
    startMatch() {
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
        setTimeout(() => {
          this.showBothAns = !this.showBothAns;
          setTimeout(() => {
            this.updateHead = !this.updateHead;
          }, 1000);
        }, 1000);
        setTimeout(() => {
          this.currentIndex = data.Result.index;
        }, 3000);
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
      this.idiomList = [];
      this.levelList = [];
      this.poetryList = [];
      this.imgLevelList = [];
      this.list = res.Result.game.subjectList;
      // this.list = JSON.parse(JSON.stringify(this.roomData.game.subjectList)); //深拷贝 问题数据
      for (let i = 0; i < this.list.length; i++) {
        // 判断成语题 生成题目列表
        if (this.list[i].modeType == 5) {
          let list = this.list[i].title.replace("_", "?").split("");
          this.idiomList.push(list);
        } else {
          this.idiomList.push(0);
        }

        if (
          this.list[i].modeType == 7 ||
          this.list[i].modeType == 8 ||
          this.list[i].modeType == 9 ||
          this.list[i].modeType == 10
        ) {
          let arr = this.list[i].title.replace("_", "?").split("");
          this.poetryList.push(arr);
        } else {
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
    handleSelectIn() {
      // 显示选项
      this.showSelect = !this.showSelect;
      setTimeout(() => {
        this.startTimeCount = !this.startTimeCount;
      }, 400);
      console.log("展示选项");
    },
    handleSelect(obj) {
      obj.data.gameId = this.gameId;
      obj.data.roomId = this.roomId;
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
      if (this.currentIndex < 4 && this.connected) {
        setTimeout(() => {
          this.ansList = [];
          this.initTime = 10;
        }, 1000);
        setTimeout(() => {
          this.initTimeCount = !this.initTimeCount;
          this.titleOff = !this.titleOff;
          this.selectOff = !this.selectOff;
        }, 1500);

        setTimeout(() => {
          this.send(`{'type':6,'data':'${this.gameId}'}`); //下一题开始计时
        }, 4800);
      } else if (this.currentIndex == 4 && this.connected) {
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
    // 房间状态
    updateRoom(res) {
      console.log(res);
      this.gameId = res.Result.game.gameId;
      this.roomId = res.Result.room.roomId;
      this.userList = res.Result.room.userList;
      this.masterId = res.Result.room.roomMaster;
      for (let i = 0; i < this.userList.length; i++) {
        if (this.appInfo.userId == this.userList[i].basicInfo.userId) {
          this.userReady = this.userList[i].isReady;
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
        }, 1000);

        setTimeout(() => {
          clearInterval(timer);
          this.ctime = 3;
        }, 3500);

        setTimeout(() => {
          if(this.roomType=='matchSuccess'){
            this.showQuesModules = true; //显示答题界面
          }
        }, 3000);

        setTimeout(() => {
          if(this.roomType=='matchSuccess'){
            this.send(`{'type':6,'data':'${this.gameId}'}`); //开始游戏
            this.roomType = "playing";
            this.ansList = [];
          }
        }, 5500);
      }
    },

    gameEnd(value) {
      setTimeout(() => {
        this.showBothAns = !this.showBothAns;
      }, 1000);
      setTimeout(() => {
        console.log("end", value);
        this.resultData = value;
        this.roomType = "wait";
        this.setGameRecord(value);
        for (let i = 0; i < value.Result.room.userList.length; i++) {
          if (
            this.appInfo.userId ==
            value.Result.room.userList[i].basicInfo.userId
          ) {
            this.setAppInfo({money:value.Result.room.userList[i].abilityInfo.money});
            // this.appInfo.money =
            //   value.Result.room.userList[i].abilityInfo.money;
          }
        }

        setTimeout(() => {
          this.showQuesModules = false;
          this.showResult = true;
          this.currentIndex = 0;
        }, 1000);
      }, 2500);
    },
    handleOnShow() {
      if (this.shareType == "result") {
        this.shareType = "";
      }
      this.hideForRoute = false;
      this.setPageType(2); //标识当前页面，断网重连时候可以使用
      console.log("onShow-page1");
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
    if (this.query.type == "hybird") {
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
    this.showResult = false;
    this.roomType = "wait";
    this.removeMessageListener(this.handleMessage);
    delete this.$root.$mp.query;
    await this.send(`{'type':5,'data':'${this.gameId}'}`, "离开页面");
    this.setPageType(0); //标识当前页面
    // this.close();
  }
};
</script>

<style lang="scss" scoped>
@import "../../scss/layout.scss";
@import "../../scss/hybird.scss";
</style>
