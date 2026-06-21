<template>
  <div class="wrap sky-bg-3 ">
    <!-- 等待匹配 -->
    <waitBox v-if="showWaitBox"
             :isMatched="isMatched"
             :matchInfo="userList"
             @cancleMatch="matchCancle"
             @pageReady="HandleWaitBoxReady"></waitBox>
    <!-- 开始答题 -->
    <questionBox v-if="showQuestion"
                 :netWork="netWork"
                 :emojiIndexMe="emojiIndexMe"
                 :emojiIndexHe="emojiIndexHe"
                 :isGameEnd="isGameEnd"
                 :oldGame="oldGame"
                 :relinkIndex="relinkIndex"
                 :otherAns="otherAns"
                 :gameOver="isGameOver"
                 :userList="userList"
                 :subjectList="subjectList"
                 :oRun="oRun"
                 @back="back"
                 @ansRight="rightHandle"
                 @ansFail="failHandle"
                 @end="end"
                 @nextQuestion="nextQuestion"
                 @sendEmoji="handleSendEmoji">
    </questionBox>
    <!-- 结果动画 -->
    <success v-if="showAntSuccess"
             @close="getMedal"
             :gift="gameGift"></success>
    <fail v-if="showAntFail"
          @close="getMedal"
          :gift="gameGift"></fail>
    <balance v-if="showAntBalance"
             @close="getMedal"
             :gift="gameGift"></balance>
    <!-- 升星动画 -->
    <div class="result-box"
         v-if="showStarBox">
      <medalChange :medalInfo="medalInfo"
                   @close="closeStarBox('结算')"
                   :isRun="oRun"
                   @oRun="closeStarBox('逃跑')"></medalChange>
    </div>

    <!-- 图片预加载 -->
    <imgPreLoad v-if="getImg"></imgPreLoad>
  </div>
</template>

<script>
import { getRondom } from "@/utils/index";
import { shareInfo } from "@/config/dataFile";
import { mapState, mapMutations } from "vuex";
import waitBox from "./components/waitBox";
import match from "./components/match";
import questionBox from "./components/questionBox";
import medalChange from "@/components/public/medalChange";
import imgPreLoad from "@/components/utils/imgPreLoad";
import success from "@/components/public/animateCss/success";
import fail from "@/components/public/animateCss/fail";
import balance from "@/components/public/animateCss/balance";
let dataStack = [];
export default {
  components: {
    waitBox,
    match,
    questionBox,
    medalChange,
    imgPreLoad,
    success,
    fail,
    balance
  },
  onShareAppMessage: function(res) {
    let index = getRondom(0, 2);
    return {
      title: shareInfo[index].title,
      imageUrl: shareInfo[index].imgUrl,
      path: `/pages/entry/index?userId=${this.appInfo.userId}`
    };
  },
  data() {
    return {
      pageType: 0,
      pageLife: "",
      socketTask:null,
      showWaitBox: false, //等待匹配
      showQuestion: false, //答题页
      matchTimer: null, //定时器
      matchTime: 10, //倒计时
      gameId: 0, //对局id
      isMatched: false, // 是否匹配到
      gameStart: false, //游戏是否开始
      relinkIndex:0,
      subjectList: [], //题目数据列表
      userList: [], //用户数据列表
      type: 0, //会话类型 区分后端传入的数据用于哪种业务处理
      otherAns: [], //其他用户的答案
      isGameOver: false,
      oRun: false,
      gameGift: { energe: 0 },
      //升星动画
      medalInfo: {},
      newResult: {},
      isGameEndup: false,
      showStarBox: false,
      endType: "success",
      cwWord: "连胜",
      clearList: false, //是否在做清空操作，
      rank: false,
      oldGame: false, //断线重连
      netWork: false, //重连开关,
      // 圖片預加載
      getImg: false,
      showAntSuccess: false,
      showAntFail: false,
      showAntBalance: false,
      showMoon: true,
      // x心跳包监听网络状态
      heart: null,
      heartTimer: null, //心跳计时器，维护后台连接
      heartDelayTime: 0,
      heartTimes: 0,
      //心跳 设计 （new）
      limit: 0,
      heartCheck: {
        timeOut: 10000,
        bigTimer: null,
        smallTimer: null,
        relinkDelay: 5000,
        start: function(_this) {
          _this.limit = 0;
          this.bigTimer = setTimeout(() => {
            if(_this.pageType==1){
              this.stop();
              return false;
            }
            try{
              _this.socketTask.send({ data: "{'type':0,'data':1}" });
            }catch(e){
              console.log(e);
              this.stop();
            }
            this.smallTimer = setTimeout(() => {
              if(_this.pageType==1){
                return false;
              }
              wx.showLoading({ title: "断网重连中", mask: true });
              _this.socketTask.close();
            }, this.timeOut);

          }, this.timeOut);
        },
        reset: function(_this) {
          clearTimeout(this.bigTimer);
          clearTimeout(this.smallTimer);
          this.start(_this);
        },
        stop() {
          clearTimeout(this.bigTimer);
          clearTimeout(this.smallTimer);
        }
      },
      isHideLife: false,
      emojiIndexMe: -1,
      emojiIndexHe: -1
    };
  },

  computed: {
    ...mapState([
      "SESSIONID",
      "appInfo",
      "socketState",
      "gameRecord",
      "isRank",
      "IMGHEAD",
      "cw",
      "openId",
      "EVN",
      "deviceInfo",
      "globalSoundPlayer",
      "rankIndex",
      "gamePageType"
    ])
  },
  methods: {
    ...mapMutations([
      "setSocketState",
      "setUserOneState",
      "setCw",
      "setGameRecord",
      "setIsRank",
      "setAppInfo",
      "setAppInfoByItem",
      "setRankIndex",
      "setStrengthCount",
      "setGameResult"
    ]),
    netWorkChange() {
      wx.onNetworkStatusChange(res => {
        if (!res.isConnected) {
          wx.showModal({
            title: "系统提示",
            content: "网络中断",
            showCancel: false,
            success: res => {
              if (res.confirm) {
                // wx.showLoading({title:"尝试连接中",mask:true});
                wx.reLaunch({
                  url: "/pages/entry/index"
                });
              }
            }
          });
        }
      });
    },

    startHeart() {
      this.heart = setInterval(() => {
        if (this.socketTask.readyState == 1) {
          this.socketTask.send({ data: "{'type':0,'data':1}" });
        } else {
          this.stopHeart();
          wx.showModal({
            title: "系统提示",
            content: "网络问题,请重新登录",
            showCancel: false,
            success: res => {
              if (res.confirm) {
                wx.reLaunch({ url: "/pages/entry/index" });
              }
            }
          });
          return false;
        }
        // this.stopHeartTimer();
        this.heartTimer = setInterval(() => {
          this.heartDelayTime++;
          if (this.heartDelayTime >= 10 && this.heartTimer != null) {
            this.stopHeart();
            this.closeSocket();
            wx.showModal({
              title: "系统提示",
              content: "网络问题,请重新登录",
              showCancel: false,
              success: res => {
                if (res.confirm) {
                  wx.reLaunch({ url: "/pages/entry/index" });
                }
              }
            });
          }
        }, 1000);
      }, 12000);
    },
    stopHeart() {
      if (this.heart) {
        clearInterval(this.heart);
        this.heart = null;
      }
      if (this.heartTimer) {
        this.stopHeartTimer();
      }
    },
    stopHeartTimer() {
      clearInterval(this.heartTimer);
      this.heartTimer = null;
      this.heartDelayTime = 0;
    },
    //socket 连接
    initSocket(_type) {
      //  初始化socket 建立连接
      if(this.socketTask){
        return false;
      }
      wx.showLoading({ title: "加载中" });
      let type = 2;
      if (this.isRank) {
        type = 1;
      }
      this.fly
        .post("/wsLogin", {
          type: type
        })
        .then(res => {
          let url = "";
          if (this.ENV == "Dev") {
            url =
              "ws://127.0.0.1:8008/vientianetest/ws?code=" +
              res.data.Result.code +
              `&&type=${_type}`;
          } else if (this.EVN == "Production") {
            url =
              "wss://ggbsq.xingpanwang.com/vientianetest/ws?code=" +
              res.data.Result.code +
              `&&type=${_type}`;
          }
          if (this.isRank) {
            if (this.EVN == "Dev") {
              url =
                "ws://127.0.0.1:8008/vientianetest/wsRank?code=" +
                res.data.Result.code +
                `&&type=${_type}`;
            } else if (this.EVN == "Production") {
              url =
                "wss://ggbsq.xingpanwang.com/vientianetest/wsRank?code=" +
                res.data.Result.code +
                `&&type=${_type}`;
            }
          }
       
          this.socketTask = wx.connectSocket({
            url: url,
            data: {
              sid: this.SESSIONID
            },
            header: {
              "content-type": "application/json"
            },
            method: "GET"
          });
          this.onSocketOpen();
          this.onSocketError();
        });
    },
    onSocketOpen() {
      //监听socket 连接 成功
      this.socketTask.onOpen(res => {
        console.log("WebSocket连接打开", this.socketTask.readyState);
        wx.hideLoading();
        // this.startHeart(); //开始心跳  old
        // this.netWorkChange();//断网处理
        if(this.pageType==1){
          this.closeSocket();
        }
        // 重置 重连限制
        clearInterval(this.relinkTimer);
        // 新 心跳尝试
        this.heartCheck.reset(this);
        this.setSocketState(this.socketTask.readyState);
        if (this.$root.$mp.query.isNew) {
          this.showWaitBox = true;
          this.sendSocketMessage("{'type':1,'data':1} ");
          delete this.$root.$mp.query.isNew;
        } else {
          this.checkOldGame(); //检测 是否 有未完成游戏
        }

        this.onSocketMessage(); //监听后端数据事件
        this.onSocketClose();
      });
    },
    reLink() {
      console.log("sockt自动重连", this.limit + 1);
      clearInterval(this.relinkTimer);
      if(this.pageType==1){
        return false;
      }
      this.relinkTimer = setInterval(() => {
        if (this.limit < 5) {
          if(this.gameStart){
            this.initSocket(2);
          }else{
            this.initSocket(1);
          }
          this.limit++;
        } else {
          clearInterval(this.relinkTimer);
          wx.showModal({
            title: "系统提示",
            content: "重连失败,请更换网络",
            showCancel: false,
            success: res => {
              if (res.confirm) {
                wx.reLaunch({ url: "/pages/entry/index" });
              }
            }
          });
        }
      }, 5000);
    },
    onSocketClose() {
      this.socketTask.onClose(res => {
        console.log("监听socket关闭", this.socketTask.readyState);
        this.setSocketState(this.socketTask.readyState);
        this.socketTask=null;
        this.otherAns = [];
        if(!this.gameStart&&!this.showWaitBox){ //游戏结束 主动断开 补充连
          return false;
        }
        if (!this.isHideLife) {
          this.reLink();
        }

        // this.stopHeart();   old
        console.log("onClose", this.pageType);
        // if (this.pageType == 0 && this.gameStart) {
        //   wx.showModal({
        //     title: "系统提示",
        //     content: "网络问题,请重新登录",
        //     showCancel: false,
        //     success: res => {
        //       if (res.confirm) {
        //         wx.reLaunch({ url: "/pages/entry/index" });
        //       }
        //     }
        //   });
        // }
      });
    },
    onSocketError() {
      //监听socket 连接 失败
      this.socketTask.onError(res => {
        console.log("socket error socket state", this.socketTask.readyState);
        this.reLink();
      });
    },

    sendSocketMessage(msg, from) {
      return new Promise((resolve, reject) => {
        //发送消息
        if (from) {
          console.log(from);
        }
        if (this.socketTask.readyState === 1) {
          this.socketTask.send({
            data: msg,
            success: () => {
              console.log(msg, new Date());
              resolve();
            },
            fail: res => {
              console.log("发送失败 :连接状态" + this.socketTask.readyState);
              reject();
            }
          });
        }
      });
    },

    onSocketMessage() {
      //被动接受消息
      this.socketTask.onMessage(res => {
        //区分数据类型 处理不同业务
        let data = JSON.parse(res.data);
        switch (data.Type) {
          case 1: //开始匹配
            this.stopMatch(data, "new");
            console.log("接收type1", data, new Date());
            break;
          case 2: //获取重连数据
            this.checkOldGameHandle(data);
            console.log("接收type2", data, new Date());
            break;
          case 3: //匹配成功
            console.log("接受type3", data, new Date());
            break;

          case 4: // 区分是 用户的反馈 或者 对手的答题信息
            console.log("接收type4:", data, new Date());
            this.handleAnsewerResponse(data.Result);
            break;

          case 6: // 下一题
            console.log("接收type6-下一题",data, new Date());
            this.HandleNextQuestion(data);
            break;

          case 5:
            this.gameOver(data);
            console.log("接收type5,处理结束游戏断开连接");
            break;
          case 7:
            console.log(
              "type7,游戏结束，带有最终信息，包括后续可能添加的经验等",
              data,
              new Date()
            );
            this.gameEnd(data);
            break;
          case 0:
            // console.log("重置心跳", new Date());
            console.log("接收type0,重置时间", new Date());
            // this.stopHeartTimer();
            this.heartCheck.reset(this);
            break;
          case 15:
            this.sendEmoji(data);
        }
      });
    },

    closeSocket() {
      return new Promise((resolve, reject) => {
        if (this.socketTask.readyState === 1) {
          this.socketTask.close({
            success: res => {
              resolve();
            },
            fail: () => {
              reject();
            }
          });
        }
      });
    },

    //-------------- 匹配相关函数 ----------------------------------
    checkOldGame() {
      this.sendSocketMessage("{'type':2,'data':1}");
    },
    checkOldGameHandle(data) {
      if (data.Result != "") {
        //游戏重连 直接开始游戏
        this.stopMatch(data, "old");
        this.gameStart = true;
        this.oldGame = true;
        console.log("旧游戏");
      } else {
        this.oldGame = false;
        if (this.showQuestion) {
          //游戏界面中，但是游戏结束了
          wx.showToast({
            title: "游戏已结束",
            duration: 1000,
            icon: "none"
          });
          this.gameStart = false;
          setTimeout(() => {
            this.$router.go(1);
          }, 1000);
        } else if (this.showQuestion) {
          console.log("显示对局结果,关闭无用socket");
          this.closeSocket();
        } else {
          //开始新游戏
          this.showWaitBox = true;
          this.gameStart = true;
          this.sendSocketMessage("{'type':1,'data':1} ");
        }
      }
    },
    stopMatch(data, type) {
      if (type == "new") {
        //停止匹配
        this.gameStart = true;
        this.isMatched = true;
        setTimeout(() => {
          this.showWaitBox = false;
          if (this.pageLife != "onUnload") {
            this.matchClose();
          }
        }, 1600);
      } else {
        this.gameStart = true;
        this.showWaitBox = false;
        setTimeout(() => {
          if (this.showQuestion) {
            this.netWork = !this.netWork;
          } else {
            this.showQuestion = true;
          }
        }, 0);
      }

      // 初始数据赋值
      this.gameId = data.Result.gameId;
      this.userList = data.Result.userList;
      this.subjectList = data.Result.subjectList;
      this.relinkIndex = data.Result.answerIndex;

      // 调试
      for(let i=0;i<this.subjectList.length;i++){
        console.log(this.subjectList[i].answer);
      }
      // 排位处理
      if (this.isRank) {
        for (let i = 0; i < this.userList.length; i++) {
          if (this.userList[i].basicInfo.userId == this.appInfo.userId) {
            // 新结果处理
            this.medalInfo = this.userList[i].abilityInfo;

            this.setCw(this.userList[i].abilityInfo.continueWinNum);

            this.medalImg =
              this.IMGHEAD + this.userList[i].abilityInfo.paragraphImage;
          }
        }
      }

      // beforeData;
    },

    matchCancle() {
      //取消匹配
      this.showWaitBox = false;
      this.$router.back();
    },
    // ----------------配对成功 展示页 -------------------------
    matchClose() {
      this.showQuestion = true;
    },

    // ----------------答题处理 -------------------------
    submitAns(obj) {
      //提交答案到后端
      obj.data.gameId = this.gameId;
      obj = JSON.stringify(obj);
      this.sendSocketMessage(obj, "send-submitAns");
    },
    rightHandle(obj) {
      this.submitAns(obj);
    },
    failHandle(obj) {
      this.submitAns(obj);
    },
    nextQuestion() {
      this.otherAns = [];
      this.sendSocketMessage(`{'type':6,'data':${this.gameId},'index':${this.rankIndex}}`);
    },

    answerEnd() {
      this.otherAns = [];
      this.showMoon = false;
    },
    // ---------------- 反馈处理函数 -------------------------
    handleSendEmoji(data) {
      this.sendSocketMessage(`{'type':15,'data':${data}}`);
    },
    HandleNextQuestion(data) {
      // console.log('type6',data)
      wx.hideLoading();
      this.setRankIndex(data.Result.index);
    },
    handleAnsewerResponse(data) {
      //接受答题反馈
      this.otherAns.push(data);
    },
    gameOver(data) {
      //type5 逃跑处理
      this.isGameOver = !this.isGameOver;
      this.otherAns = [];
      this.oRun = true;
      if (this.isRank) {
        this.gameEnd(data);
      } else {
        this.isGameEnd = !this.isGameEnd;
      }
      wx.showToast({
        title: "对方已逃跑,等待游戏结算",
        icon: "none",
        duration: 1000
      });
      setTimeout(() => {
        wx.showLoading({
          title: "结算中...",
          mask: true
        });
      }, 1000);
    },
    sendEmoji(data) {
      data = JSON.parse(data.Result);
      this.emojiIndex = data.index;
      if (data.id == this.appInfo.userId) {
        this.emojiIndexMe = data.index;
        setTimeout(() => {
          this.emojiIndexMe = -1;
        }, 1600);
      } else {
        this.emojiIndexHe = data.index;
        setTimeout(() => {
          this.emojiIndexHe = -1;
        }, 1600);
      }
    },
    resetTobBg() {
      wx.setNavigationBarColor({
        frontColor: "#ffffff",
        backgroundColor: "#000"
      });
    },
    gameEnd(data) {
      //游戏正常完成
      this.setGameRecord(data);
      this.setRankIndex(0); //最后结算
      // this.stopHeart();
      // this.stopHeartTimer();
      this.heartCheck.stop(this);
      setTimeout(() => {
        this.resetTobBg();
        this.showQuestion = false;
      }, 2000);
      this.otherAns = [];
      this.gameStart = false;
      if (this.isRank) {
        //排位
        let u1, u2;
        // 处理升星动画 结果数据
        // 获取结算状态
        this.newResult = {};
        for (let i = 0; i < data.Result.userList.length; i++) {
          if (data.Result.userList[i].basicInfo.userId == this.appInfo.userId) {
            this.newResult = data.Result.userList[i].abilityInfo;
            u1 = data.Result.userList[i].abilityInfo.score;
            this.gameGift = {
              energe: this.newResult.rewardStrength,
              combatList: data.Result.userList[i].combatInfo || [],
              rightNum: this.newResult.rightNum,
              combat: this.newResult.combat
            };
          } else {
            u2 = data.Result.userList[i].abilityInfo.score;
          }
        }
        this.isGameEnd = !this.isGameEnd;
        if(u1>u2||this.oRun){
          this.setGameResult('success');
        }else if(u1<u2){
          this.setGameResult('fail');
        }else if(u1==u2){
          this.setGameResult('balance');
        }
        setTimeout(() => {
          wx.hideLoading();
          if (u1 > u2 || this.oRun) {
            this.endType = "success";
            this.showAntSuccess = true;
            this.globalSoundPlayer.src =
              "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/success.mp3";
            // setTimeout(() => {
            //   this.showAntSuccess = false;
            // }, 3500);
          } else if (u1 < u2) {
            this.endType = "fail";
            this.showAntFail = true;
            this.globalSoundPlayer.src =
              "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/fail.mp3";
          } else {
            this.endType = "balance";
            this.showAntBalance = true;
            this.globalSoundPlayer.src =
              "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/balance.mp3";
          }
          this.globalSoundPlayer.play();
          if (this.oRun) {
            this.endType = "success";
            this.showAntSuccess = true;
          }
        }, 2000);
      }
      // 结束后重新获取基础信息
      this.closeSocket();
      this.stopHeart();
      console.log("游戏正常结束");
    },
    back() {
      this.otherAns = [];
    },
    getMedal() {
      this.showAntSuccess = false;
      this.showAntFail = false;
      this.showAntBalance = false;
      this.closeStarBox('结算');
      // this.showStarBox = true;

      // setTimeout(() => {
      //   // 新结果处理
      //   this.medalInfo = this.newResult;
      // }, 1000);
    },

    closeStarBox(type) {
      this.showStarBox = false;
      if (type == "结算") {
        this.$router.replace("/pages/game/result");
      } else if (type == "逃跑") {
        this.$router.back();
      }
    }
  },

  // ---------------- 实例生命周期 -------------------------

  mounted() {
    // this.onNetworkStatusChange();
    setTimeout(() => {
      this.getImg = true;
    }, 35000);
    this.pageLife = "mounted";
  },
  onHide() {
    //断开连接
    this.isHideLife = true;
    this.heartCheck.stop();
    this.pageType = 1; //离开当前页
    console.log(this.pageType);
    this.closeSocket();
    this.pageLife = "onHide";
  },
  onShow() {
    this.pageLife = "onShow";
    this.pageType = 0;
    //断网重连处理
    if (this.$root.$mp.query.rankGame) {
      this.setIsRank(true);
    }
    if (this.$root.$mp.query.isNew && !this.isHideLife && !this.gameStart) {
      this.initSocket(1);
    } else if (
      !this.$root.$mp.query.isNew &&
      this.isHideLife &&
      this.gameStart
    ) {
      this.initSocket(2);
    }else if(!this.$root.$mp.query.isNew&&this.showWaitBox&&!this.gameStart){
      this.initSocket(1);
    }
    this.isHideLife = false;
  },
  onLoad(options) {
    dataStack.push({
      ...this.$data
    });
  },
  async onUnload() {
    console.log("game onUnload");
    this.heartCheck.stop();
    this.showStarBox = false;
    this.pageLife = "onUnload";
    this.pageType = 1; //离开当前页面
    if (this.gameStart) {
      this.setGameResult("fail");
      wx.showModal({
        title: "你放弃了对局",
        content: "逃跑将使你的星级下降",
        showCancel: false
      });
    }
    this.heartTimes = 0;
    this.showQuestion = false;
    this.gameStart = false;
    this.showWaitBox = false;
    this.questionBox = false;
    this.showAntSuccess = false;
    this.showAntFail = false;
    this.showAntBalance = false;
    this.isMatched = false;
    this.oldGame = false;
    this.otherAns = [];
    this.oRun = false;
    this.setRankIndex(0);
    this.setUserOneState(false);
    this.setIsRank(false);
    this.closeStarBox();
    setTimeout(() => {
      this.closeStarBox();
    }, 3000);
    await this.sendSocketMessage(`{'type':5,'data':${this.gameId}}`);
    this.closeSocket();
  }
};
</script>

<style lang="scss" scoped>
.wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  overflow-y: scroll;
}

.time {
  font-size: 48rpx;
  color: #000;
  margin-bottom: 45rpx;
  line-height: 1;
}

.animate-box {
  width: 572rpx;
  height: 572rpx;
  margin-bottom: 120rpx;
  position: relative;
  & > div {
    position: absolute;
    width: 360rpx;
    height: 360rpx;
    border: 8rpx solid #67c29f;
    border-radius: 50%;
    transform: translate(-50%, -50%);
    top: 50%;
    left: 50%;
  }
  .cycle-1 {
    display: flex;
    justify-content: center;
    align-items: center;
    img {
      width: 184rpx;
      height: 184rpx;
      border: 6rpx solid #fff;
      border-radius: 50%;
      box-shadow: 0 0 20rpx 0rpx rgba(#000, 0.5);
    }
    .ball-box {
      width: 100%;
      height: 100%;
      position: absolute;
      left: 0;
      top: 0;
      animation: rotate 3s linear infinite;
      .ball {
        width: 34rpx;
        height: 34rpx;
        background: #67c29f;
        border-radius: 50%;
        position: absolute;
        bottom: -17rpx;
        left: 50%;
        margin-left: -17rpx;
      }
    }
  }
  .cycle-2 {
    animation: amplification-1 2s linear infinite;
    border-width: 5rpx;
  }
  .cycle-3 {
    animation: amplification-2 2s linear infinite;
    border-width: 3rpx;
  }
}

@keyframes rotate {
  0% {
    transform: rotate(0);
  }
  25% {
    transform: rotate(90deg);
  }
  60% {
    transform: rotate(180deg);
  }
  80% {
    transform: rotate(270deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

@keyframes amplification-1 {
  0% {
    transform: translate(-50%, -50%) scale(1); // border-width: 8rpx;
    opacity: 0.8;
  }
  100% {
    transform: translate(-50%, -50%) scale(1.5); // border-width: 5rpx;
    opacity: 0;
  }
}

@keyframes amplification-2 {
  0% {
    transform: translate(-50%, -50%) scale(1); // border-width: 8rpx;
    opacity: 0.6;
  }
  100% {
    transform: translate(-50%, -50%) scale(2.1); // border-width: 3rpx;
    opacity: 0;
  }
}

// 升星动画
.show {
  opacity: 1 !important;
}

.rotate {
  animation: rotate2 180s ease infinite;
}

@keyframes rotate2 {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(3600deg);
  }
}

.z50 {
  z-index: 50 !important;
}

.star-box {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  z-index: -1;
  transition: all 0.6s ease;
  opacity: 0;
}

.medal {
  .m-name {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20rpx;
    .name-word {
      width: 82rpx;
      height: 82rpx;
      line-height: 82rpx;
      text-align: center;
      font-size: 42rpx;
      color: #000;
      border: 1rpx solid #ababab;
      box-sizing: border-box;
      border-radius: 50%;
      margin-right: 18rpx;
      &:last-child {
        margin-right: 0;
      }
    }
  }
  .m-img {
    position: relative;
    margin-bottom: 20rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    .img-rang {
      width: 415rpx;
      height: 415rpx;
    }
    .img-medal {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      z-index: 2;
      border: 8rpx solid #5fa288;
      border-radius: 50%;
      display: flex;
      align-items: center;
      width: 320rpx;
      height: 320rpx;
      img {
        width: 312rpx;
        height: 312rpx;
        border-radius: 50%;
        border: 12rpx solid #fff;
        box-sizing: border-box;
        opacity: 0;
        transition: all 1.5s ease;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
      }
      .old-img {
        z-index: 1;
      }
      .new-img {
        z-index: 1;
      }
    }
  }
  .m-star {
    width: 230rpx;
    height: 60rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
    position: relative;
    .m-mask {
      display: flex;
      justify-content: space-between;
      align-items: center;
      position: absolute;
      width: 100%;
      height: 100%;
      z-index: 10;
      div {
        width: 100%;
        height: 100%;
        background: #fff;
        transition: all 0.4s ease;
      }
    }
    .w0 {
      width: 0 !important;
    }
    .star {
      width: 46rpx;
      height: 60rpx;
      margin-right: 33rpx;
      background-size: 46rpx 60rpx;
      background-repeat: no-repeat;
      background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAA7CAMAAAAzfEgtAAAAP1BMVEUAAADR0dHW1tb9/f3b29vw8PDT09P7+/vs7Oz5+fn29vbj4+Pn5+fh4eHd3d3Y2Njy8vLl5eXf39/p6en////KggEBAAAAAXRSTlMAQObYZgAAAW9JREFUSMeVlu16gyAMhZsYAoKiUO//Wme359GNJODOT/s2zcdJ6qsn75aVkOEUbzmFOJnotJcNGmEw4JgYJGwE3zMI8WLAkUCKZqO8FRS9jdAOFZh3ox0FFKGVCGn05nV6xv/QEUeZjGOzlbdKgzN6Qiq9vHQlld6s6YCqaCTOKs1J51ewRLtiWOgoi1YS9MSurXOg0gQfaf3tFRgrNRMaKVzjZ3iiOChU+kGOaJwOPsTxO/wMTxWM1LmmUuWP0gdfBPxzgqYgvvAxTzVX2ZOSDYlVNte3ysaEjrHxfNQEn3rmOz+0rSf74G9cPRRBtKa5493FmQfJvAWOf4s/BqVS5yZG2cjSPPL23ayKxchf9AqyD174er//L5X9y3LPSgiLgAHHuypnMuEzmr2YtJT0x0EPN1tOw9ZuGW98hNOIzlcqYoA2ffPd+LWh+/kvh/ZOQDq8xZcut4H6emfpcLUp0d2wKu9KRgZgzMnJF58vF3gOaRxJC2QAAAAASUVORK5CYII=");
      &:last-child {
        margin-right: 0;
      }
      img {
        width: 46rpx;
        height: 60rpx;
        opacity: 0;
        transition: all 0.6s ease;
        transform: scale(1.5);
      }
      .starActive {
        opacity: 1;
        transform: scale(1);
      }
    }
  }
  .m-cloud {
    position: relative;
    height: 61rpx;
    margin-top: 8rpx;
    margin-bottom: 120rpx;
    text-align: center;
    img {
      width: 181rpx;
      height: 61rpx;
      vertical-align: top;
      opacity: 0;
      transition: all 0.6s ease;
    }
    .cl {
      transform: translateX(-78rpx);
      opacity: 1;
    }
    .cr {
      transform: translateX(78rpx);
      opacity: 1;
    }
  }
  .m-cw {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 48rpx;
    color: #777676;
    margin-bottom: 80rpx;
    opacity: 0;
    transition: all 0.6s ease;
    .word {
      padding: 0 15rpx;
      transform: scale(1.3);
      transition: all 0.6s ease;
    }
    .line {
      height: 4rpx;
      width: 120rpx;
      background: #aaaaaa;
    }
  }
  .show {
    .word {
      transform: scale(1);
    }
  }
  .tap-tip {
    font-size: 30rpx;
    color: #aaaaaa;
    text-align: center;
    opacity: 0;
    transition: all 0.6s ease;
  }
}
.moon {
  width: 750rpx;
  height: 225rpx;
  position: fixed;
  // bottom:calc(-410rpx + 20vh);
  bottom: -160rpx;
}
@media screen and (min-height: 720px) {
  .moon {
    bottom: 0;
  }
}
.result-box {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
}
</style>
