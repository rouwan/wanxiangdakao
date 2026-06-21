<template>
  <!-- 头部 -->
 <div class="question-box"
       :class="{'blue-type':gamePageType==1}">
    <div class="head-box">
      <!-- <div class="name-box" :class="{'show':showHead}">
        <div class="u-head-name">{{myHeadInfo.name}}</div>
        <div class="u-head-name">{{otherHeadInfo.name}}</div>
      </div> -->
      <div class="u-head-left"
           :class="{'head-in':showHead}">
        <img class="u-img"
             :src="myHeadInfo.headImg"
             alt="">
        <div class="u-info">
          <div class="u-head-name">{{myHeadInfo.name}}</div>
          <div class="u-head-sore">{{myScore.score||0}}</div>
        </div>
        <img class="emoji-btn"
             src="/static/icon/emojiBtn.png"
             @click="showEmojiBox=!showEmojiBox">
        <div class="emoji-box"
             v-if="showEmojiBox">
          <img v-for="(item,index) in emojiList"
               :key="index"
               :src="item"
               @click="sendEmoji(index)">
        </div>
      </div>
      <div class="u-head-right"
           :class="{'head-in':showHead}">
        <div class="u-info">
          <div class="u-head-name">{{otherHeadInfo.name}}</div>
          <div class="u-head-sore">{{otherScore.score||0}}</div>
        </div>
        <img class="u-img"
             :src="otherHeadInfo.headImg"
             alt="">
      </div>

      <!-- canvas 机器时期 showTime-->
      <div class="round-cvs"
           v-if="showTime">
        <timeCount :initBtn="initTimeCount"
                   :empty="true"
                   :small="true"
                   :startBtn="startTimeCount"
                   :stopBtn="stopTimeCount"
                   :initTime="initTime"></timeCount>
      </div>
    </div>
    <img class="antEomji left130"
         :src="emojiList[emojiIndexMe]"
         :class="{'sendAnt':showEmojiMe}">
    <img class="antEomji right130"
         :src="emojiList[emojiIndexHe]"
         :class="{'sendAnt-r':showEmojiHe,}">
    <div class="main-conent">
      <!-- 答題畫面信息 -->
      <!-- showAnsList -->
      <div v-if="showAnsList">
        <answerList :myScore="myScore"
                    :netWork="isReLink"
                    :oldGame="oldGame"
                    :initTime="newTime"
                    :canNext="canNext"
                    :otherScore="otherScore"
                    :questionList="subjectList"
                    @showSelectComplete="showSelectComplete"
                    @success="answerSuccess"
                    @fail="answerFail"
                    @ansTimeOut="ansTimeOut"
                    @ansStart="ansStart"
                    @nextQuestion="sendNext"
                    :currentIndex="questionIndex">
        </answerList>
      </div>
    </div>

  </div>
 
</template>

<script>
import { mapState, mapMutations } from "vuex";
import timeCount from "./timeCount";
import answerList from "./answerList";
import { setStorage, clearStorage } from "@/utils/index";
let dataStack = [];
export default {
  props: [
    "subjectList",
    "userList",
    "otherAns",
    "gameOver",
    "isGameEnd",
    "oldGame",
    "netWork",
    "oRun",
    "emojiIndexMe",
    "emojiIndexHe",
    "sendToMe",
    "relinkIndex"
  ],
  components: {
    timeCount: timeCount,
    answerList: answerList
  },
  computed: {
    ...mapState([
      "appInfo",
      "IMGHEAD",
      "userOneState",
      "userTwoState",
      "cw",
      "isRank",
      "globalSoundPlayer",
      "gameRecord",
      "rankIndex",
      "gamePageType"
    ])
  },
  data() {
    return {
      first: true,
      showTime: false,
      showAnsList: false,
      questionIndex: 0, //控制题目的选项
      myInfo: {},
      myHeadInfo: { headImg: "", name: "???", ansList: [], score: 0 },
      otherHeadInfo: { headImg: "", name: "???", ansList: [], score: 0 },
      myScore: {}, //我的分数
      otherInfo: {},
      otherScore: {}, //他人答题,
      resetBtn: false, //reset count
      initTime: 10,
      initTimeCount: false, //初始化计时器
      startTimeCount: false, //开始化计时器
      stopTimeCount: false, //停止化计时器
      canNext: false,
      gameResult: {
        otherScore: 0,
        myScore: 0
      },
      resultType: "",
      oRun: false,
      showEmojiMe: false,
      showEmojiHe: false,
      showEmojiBox: false,
      emojiList: [
        "/static/icon/0.png",
        "/static/icon/1.png",
        "/static/icon/2.png",
        "/static/icon/3.png"
      ],

      // d动效相关控制
      gameStop: false,
      showHead: false,
      userResult: 0,
      withUserResult: 0,
      headMode: 0,
      myAnsList: [0, 0, 0, 0, 0], //我的回答集 0 超时未答，1 正确 2 错误
      otherAnsList: [0, 0, 0, 0, 0], //回答集
      //结果动画
      showBtn: false,

      // 奖励
      isReLink: false,
      newTime: 0
    };
  },
  watch: {
    rankIndex(value) {
      //更新题目 更新 计时器
      this.canNext = !this.canNext;
    },
    netWork() {
      console.log("network change");
      this.getCurrentQuetion();
      this.isReLink = true;
    },
    isGameEnd() {
      this.showTime = false;
      let list = this.gameRecord.Result.subjectList;
      let resObj = {
        oRun: this.oRun
      };
      setStorage("rankGameResult", resObj);
    },
    otherAns(list) {
      if (list.length > 0) {
        for (let i = 0; i < list.length; i++) {
          if (list[i].uid != this.appInfo.userId) {
            this.otherScore = list[i];
            this.otherHeadInfo.score = list[i].score;

            if (list[i].timeout) {
              this.otherAnsList[this.questionIndex] = 0; //超时
            } else {
              if (list[i].right) {
                this.otherAnsList[this.questionIndex] = 1; //回答正确
              } else {
                this.otherAnsList[this.questionIndex] = 2; //回答错误
              }
            }
          }

          if (list[i].uid == this.appInfo.userId) {
            this.myScore = list[i];
            this.myHeadInfo.score = list[i].score;

            if (list[i].timeout) {
              this.myAnsList[this.questionIndex] = 0; //超时
            } else {
              if (list[i].right) {
                this.myAnsList[this.questionIndex] = 1; //回答正确
              } else {
                this.myAnsList[this.questionIndex] = 2; //回答错误
              }
            }
            this.setUserOneState(true); // 收到S端 A用户数据
          }
        }
      }
    },
    gameOver() {
      this.gameStop = true;
      this.stopTimeCount = !this.stopTimeCount;
      this.initTimeCount = !this.initTimeCount;
      this.showTime = false;
      this.showAnsList = false;
      this.showHead = false;
      this.oRun = true;
      this.gameResult.otherScore = 0;
    },
    emojiIndexMe(index) {
      console.log("emojiIndexMe", index);
      if (index == -1) {
        return false;
      }
      setTimeout(() => {
        this.showEmojiMe = true;
      }, 100);
      setTimeout(() => {
        this.showEmojiMe = false;
      }, 1600);
    },
    emojiIndexHe(index) {
      if (index == -1) {
        return false;
      }
      setTimeout(() => {
        this.showEmojiHe = true;
      }, 100);
      setTimeout(() => {
        this.showEmojiHe = false;
      }, 1600);
    }
  },
  methods: {
    ...mapMutations({
      setUserOneState: "setUserOneState",
      setUserTwoState: "setUserTwoState",
      setCw: "setCw"
    }),
    sendEmoji(index) {
      let obj = { index: index, id: this.appInfo.userId };
      obj = JSON.stringify(obj);
      this.showEmojiBox = false;
      this.$emit("sendEmoji", obj);
    },
    getInfo() {
      //获取列表
      if (this.userList) {
        for (let i = 0; i < this.userList.length; i++) {
          if (this.userList[i].basicInfo.userId == this.appInfo.userId) {
            // 头部信息
            this.myHeadInfo = {
              headImg: this.userList[i].basicInfo.avatarUrl,
              name: this.userList[i].basicInfo.nickName
            };
          } else {
            this.otherInfo = this.userList[i];
            // 头部信息
            this.otherHeadInfo = {
              headImg: this.userList[i].basicInfo.avatarUrl,
              name: this.userList[i].basicInfo.nickName
            };
            if (this.otherInfo.boot) {
              this.otherHeadInfo.headImg =
                this.IMGHEAD + this.userList[i].basicInfo.avatarUrl;
            }
          }
        }
      }
    },
    getCurrentQuetion() {
      let index = 0,
        user = {};
      // this.userList[0].index > this.userList[1].index
      //   ? (index = this.userList[1].index)
      //   : (index = this.userList[0].index);
      this.userList[0].basicInfo.userId == this.appInfo.userId
        ? (user = this.userList[0])
        : (user = this.userList[1]);
      this.newTime = user.restSecond;
      this.initTime = Math.ceil(user.restSecond / 1000);
      this.questionIndex = this.relinkIndex; //获取当前题目
      this.setUserOneState(false);
      this.showAnsList = true;
      this.initTimeCount = !this.initTimeCount;
      this.startTimeCount = !this.startTimeCount;
    },
    getResult() {
      if (this.gameResult.myScore > this.gameResult.otherScore || this.oRun) {
        this.resultType = "胜利";
      } else if (this.gameResult.myScore < this.gameResult.otherScore) {
        this.setCw(0);
        this.resultType = "失败";
      } else if (
        this.gameResult.myScore == this.gameResult.otherScore &&
        !this.oRun
      ) {
        this.setCw(0);
        this.resultType = "平局";
      }
    },

    start() {
      //开始答题
      this.showTime = true;
      setTimeout(() => {
        this.showHead = true;
      }, 500);
      setTimeout(() => {
        this.checkShowAnsList();
      }, 1000);

      this.initTimeCount = !this.initTimeCount;
    },

    setUserId(obj) {
      obj.data.uid = this.appInfo.userId;
    },
    showSelectComplete(res) {
      //当前题目答完  清空计时器，暂停进度条
      this.stopTimeCount = !this.stopTimeCount;
      if (this.questionIndex < 4) {
        setTimeout(() => {
          this.initTimeCount = !this.initTimeCount;
          this.questionIndex = this.rankIndex; //题目增加
          this.isReLink = false;
          this.initTime = 10;
          console.log("this.questionIndex", this.questionIndex);
        }, 2000);
      } else if (this.questionIndex == 4) {
        console.log("this.questionIndex==4?", this.questionIndex);
        this.stopTimeCount = !this.stopTimeCount;
        setTimeout(() => {
          this.$emit("answerEnd");
          this.initTimeCount = !this.initTimeCount;
        }, 1000);
      }
      this.gameResult = res;
    },
    sendNext() {
      this.startTimeCount = !this.startTimeCount;
      this.$emit("nextQuestion");
    },
    answerSuccess(obj) {
      // 答题正确
      this.setUserId(obj);
      this.$emit("ansRight", obj);
    },
    answerFail(obj) {
      // 答题错误
      this.setUserId(obj);
      this.$emit("ansFail", obj);
    },

    checkShowAnsList() {
      if (
        (this.questionIndex != 4 && !this.gameStop) ||
        (this.questionIndex == 4 && this.oldGame && this.gameStop)
      ) {
        this.showAnsList = true;
      }
    },
    selectAdvice(item) {
      this.adviceType = item.type;
    },
    changeTopBg() {
      let bgColor = "#28235c";
      if (this.gamePageType == 1) {
        bgColor = "#3f84fb";
      }
      wx.setNavigationBarColor({
        frontColor: "#ffffff",
        backgroundColor: bgColor
      });
    },
    submitAdvice() {
      this.fly
        .post("/feedBackUser", {
          userId: this.otherScore.uid,
          types: this.adviceType
        })
        .then(res => {
          wx.showToast({
            title: "提交成功,谢谢！",
            icon: "none"
          });
          this.showModal = false;
        });
    }
  },
  onLoad(options) {
    dataStack.push({
      ...this.$data
    });
  },
  mounted() {
    this.changeTopBg();
    this.getInfo();
    if (this.oldGame) {
      this.getCurrentQuetion();
    }
    this.start();
  }
};
</script>

<style lang="scss" scoped>
.question-box {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  padding-top: 120rpx;
}

.head-box {
  padding: 0 24rpx 0 24rpx;
  box-sizing: border-box;
  // margin-bottom: 50rpx;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  position: fixed;
  left: 0;
  top: 0;
  height: 120rpx;
  width: 100%;
  z-index: 2;
  .u-head-left,
  .u-head-right {
    width: 320rpx;
    height: 110rpx;
    position: relative;
    transition: all 0.35s ease;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    box-sizing: border-box;
    overflow: visible;
    .u-img {
      width: 80rpx;
      height: 80rpx;
      border-radius: 50%;
      border: 6rpx solid #fff;
      box-sizing: border-box;
    }
    .u-info {
      display: flex;
      flex-direction: column;
      justify-content: center;
      color: #fff;
      font-weight: bold;
      .u-head-name {
        font-size: 26rpx;
        width: 140rpx;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }
      .u-head-score {
        font-size: 38rpx;
      }
    }
  }
  .u-head-left {
    background: #258ff2;
    padding-left: 16rpx;
    border-top-left-radius: 2000rpx;
    border-bottom-left-radius: 2000rpx;
    .u-info {
      margin-left: 10rpx;
      align-items: flex-start;
      text-align: left;
    }
    &::after {
      width: 130rpx;
      height: 130rpx;
      background: #28235c;
      content: "";
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
      right: -90rpx;
      border-radius: 50%;
    }
  }
  .u-head-right {
    padding-right: 16rpx;
    justify-content: flex-end;
    background: #fe3a89;
    border-top-right-radius: 2000rpx;
    border-bottom-right-radius: 2000rpx;
    .u-info {
      margin-right: 10rpx;
      align-items: flex-end;
      text-align: right;
    }
    &::after {
      width: 130rpx;
      height: 130rpx;
      background: #28235c;
      content: "";
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
      left: -90rpx;
      border-radius: 50%;
    }
  }
}
// 天蓝主题
.blue-type .u-head-left::after,
.blue-type .u-head-right::after {
  background: #3f84fb;
}

.blue-type .u-head-left {
  background: #66cafc;
}
.blue-type .u-head-right {
  background: #fa537f;
}

.head-in {
  transform: translate(0, 0) !important;
}
//答題頁面-header
.round-cvs {
  position: fixed;
  left: 50%;
  top: 10rpx;
  opacity: 0;
  z-index: 100;
  transform: translateX(-50%);
  animation: showTime 0.5s ease forwards;
}

@keyframes showTime {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

//內容
.main-conent {
  //勝利內容
  .btn-box {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 50rpx;
    font-size: 36rpx;
    color: #fff;
    margin-top: 50rpx;
  }
  .btn-game,
  .btn-back {
    height: 86rpx;
    line-height: 86rpx;
    text-align: center;
    border-radius: 30rpx;
  }
  .btn-game {
    width: 298rpx;
    background: #ff217b;
  }
  .btn-back {
    width: 220rpx;
    background: #85d85d;
    margin-left: 70rpx;
  }
}

//user長度
.w12r {
  width: 1.2rem;
}

.w25r {
  width: 2.5rem;
}

.endStyle {
  width: 360rpx;
  height: 175rpx;
  transform: translateX(0);
  img {
    width: 148rpx;
    height: 148rpx;
  }
  .score {
    font-size: 48rpx;
  }
}

.success {
  width: 420rpx;
  height: 200rpx;
  transform: translateX(0);
  img {
    width: 172rpx;
    height: 172rpx;
  }
}

.fail {
  width: 320rpx !important;
  height: 175rpx !important;
  background-color: #d6d6d6 !important;
  img {
    width: 150rpx;
    height: 150rpx;
  }
}

// 新增 回答记录列表
.ans-list {
  width: 200rpx;
  display: flex;
  justify-content: space-around;
  align-items: center;
  position: absolute;
  bottom: -50rpx;
}

.ans0 {
  width: 20rpx;
  height: 4rpx;
  background: #abadab;
}

.ans1 {
  width: 34rpx;
  height: 34rpx;
  border-radius: 50%;
  box-sizing: border-box;
  border: 4rpx solid #67c29f;
}

.ans2 {
  width: 40rpx; // height: 4rpx;
  // background: #fd5c5c;
  // border-radius: 10rpx;
  // transform: rotate(45deg);
  position: relative;
  &:after {
    position: absolute;
    top: 50%;
    left: 50%;
    content: "";
    width: 40rpx;
    height: 4rpx;
    background: #fd5c5c;
    border-radius: 10rpx;
    transform: translate(-50%, -50%) rotate(-45deg);
  }
  &:before {
    position: absolute;
    top: 50%;
    left: 50%;
    content: "";
    width: 40rpx;
    height: 4rpx;
    background: #fd5c5c;
    border-radius: 10rpx;
    transform: translate(-50%, -50%) rotate(45deg);
  }
}

// 动效附加
.headIn {
  transform: translateX(0);
}

.result {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  box-sizing: border-box;
  padding: 50rpx 44rpx 0 44rpx;
  overflow: scroll;
  .success-type {
    .r-user-score {
      color: #09ddb8 !important;
    }
    .crown {
      opacity: 1 !important;
    }
  }
}

.r-head {
  width: 100%;
  display: flex;
  justify-content: space-between;
  position: relative;
  margin-bottom: 40rpx;
  .r-user-head {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 176rpx;
    height: 290rpx;
    color: #fff;
    position: relative;
    &:first-child {
      .crown {
        left: 15rpx;
      }
    }
    &:last-child {
      .crown {
        right: 15rpx;
        transform: rotate(70deg);
      }
    }
    .crown {
      position: absolute;
      top: -15rpx;
      width: 55rpx;
      height: 54rpx;
      opacity: 0;
    }
    .r-user-img {
      width: 120rpx;
      height: 120rpx;
      box-sizing: border-box;
      border: 4rpx solid #9491ae;
      border-radius: 50%;
      margin-bottom: 12rpx;
    }
    .r-user-run {
      position: absolute;
      width: 120rpx;
      height: 120rpx;
      line-height: 120rpx;
      border-radius: 50%;
      text-align: center;
      background: rgba(#4f4b48, 0.7);
      font-size: 34rpx;
      color: #ffff00;
      top: 46rpx;
      left: 28rpx;
    }
    .r-user-name {
      width: 176rpx;
      text-align: center;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      font-size: 28rpx;
      color: #fff;
      margin-bottom: 30rpx;
    }
    .r-user-score {
      font-size: 54rpx;
      font-weight: bold;
      color: #fff;
    }
  }
}

.r-r {
  position: absolute;
  left: 50%;
  top: -50rpx;
  transform: translateX(-50%);
  & > img {
    width: 380rpx;
    height: 336rpx;
  }
  div {
    position: absolute;
    left: 50%;
    top: 300rpx;
    width: 100%;
    transform: translateX(-50%);
    font-size: 30rpx;
    color: #fff;
    display: flex;
    justify-content: center;
    align-items: center;
    img {
      width: 53rpx;
      height: 52rpx;
    }
  }
}

.game-table {
  width: 100%;
  position: relative;
  color: #fff;
  .t-head {
    width: 100%;
    height: 84rpx;
    background: rgba(#7efbe3, 0.5);
    font-size: 32rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    img {
      width: 124rpx;
      height: 124rpx;
      position: absolute;
      top: -50rpx;
      &:first-child {
        left: 0;
      }
      &:last-child {
        right: 0;
      }
    }
    .vs {
      font-size: 50rpx;
      font-weight: bold;
      background: linear-gradient(180deg, #fff, #4ec5fe);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      margin: 0 30rpx;
    }
  }
  .t-line {
    width: 100%;
    height: 84rpx;
    display: flex;
    justify-content: space-between;
    &:nth-of-type(1n + 1) {
      background: rgba(#7efbe3, 0.2);
    }
    &:nth-of-type(2n + 2) {
      background: rgba(#7efbe3, 0.1);
    }
    & > div {
      width: 254rpx;
      height: 100%;
      display: flex;
      align-items: center;
      position: relative;
      img {
        width: 72rpx;
        height: 71rpx;
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
      }
      &:first-child {
        justify-content: flex-end;
        left: 30rpx;
        img {
          left: 0;
        }
        .t-score {
          margin-right: 60rpx;
        }
      }
      &:last-child {
        justify-content: flex-start;
        right: 30rpx;
        img {
          right: 0;
        }
        .t-score {
          margin-left: 60rpx;
        }
      }

      .t-score {
        width: 110rpx;
        text-align: center;
        font-size: 40rpx;
        font-weight: bold;
      }
    }
    .hide {
      opacity: 0;
    }
  }
  .t-divition {
    width: 168rpx;
    position: absolute;
    left: 50%;
    top: 84rpx;
    transform: translateX(-50%);
    background: rgba(#617192, 0.5);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    div {
      display: flex;
      justify-content: center;
      align-items: center;
      width: 100%;
      height: 84rpx;
      color: #fff;
      font-size: 28rpx;
    }
  }
}

.advice {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  // position: absolute;
  // bottom: 20rpx;
  // left: 44rpx;
  img {
    width: 44rpx;
    height: 36rpx;
    margin-right: 10rpx;
  }
  font-size: 22rpx;
  color: #00c6d9;
}

.advice-modal,
.advice-modal .mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 10;
}

.mask {
  background: rgba(#000, 0.7);
}

.modal {
  position: absolute;
  top: 223rpx;
  left: 50%;
  transform: translateX(-50%);
  width: 588rpx;
  height: 656rpx;
  border-radius: 8rpx;
  padding: 8rpx;
  box-sizing: border-box;
  background: #fff;
  z-index: 15;

  .close {
    width: 96rpx;
    height: 96rpx;
    position: absolute;
    right: -30rpx;
    top: -30rpx;
    background: #fff;
    border-radius: 50%;
    z-index: 5;
    &:before,
    &:after {
      content: "";
      position: absolute;
      left: 50%;
      top: 50%;
      width: 60rpx;
      height: 6rpx;
      background: #ef8a7f;
    }
    &:before {
      transform: translate(-50%, -50%) rotate(45deg);
    }
    &:after {
      transform: translate(-50%, -50%) rotate(-45deg);
    }
  }
  .head {
    font-size: 52rpx;
    color: #fff;
    font-weight: bold;
    line-height: 1;
    margin-bottom: 40rpx;
    padding: 25rpx 0;
    text-align: center;
    background: #ef8a7f;
    border-top-left-radius: 10rpx;
    border-top-right-radius: 10rpx;
    overflow: hidden;
    position: relative;
    img {
      width: 182rpx;
      height: 130rpx;
      right: -30rpx;
      bottom: -30rpx;
      position: absolute;
    }
  }
  .content {
    font-size: 30rpx;
    color: #000;
    text-align: center;
    line-height: 1.5em;
    margin-bottom: 33rpx;
    padding: 0 60rpx;
    box-sizing: border-box;
    margin-bottom: 40rpx;
  }
  .select-item {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-bottom: 40rpx;
    font-size: 36rpx;
    color: #ef8a7f;
    padding: 0 60rpx;
    box-sizing: border-box;
    .select-box {
      width: 50rpx;
      height: 50rpx;
      border: 4rpx solid #aaaaaa;
      border-radius: 4rpx;
      box-sizing: border-box;
      position: relative;
      margin-right: 15rpx;
      img {
        width: 60rpx;
        height: 44rpx;
        position: absolute;
        top: -5rpx;
        right: -20rpx;
      }
    }
  }
  .advice-submit {
    width: 480rpx;
    height: 100rpx;
    line-height: 100rpx;
    margin: 0 auto;
    text-align: center;
    font-size: 44rpx;
    color: #fff;
    background: #85d85d;
    border-radius: 26rpx;
  }
}

.name-box {
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 0 170rpx;
  box-sizing: border-box;
  position: absolute;
  left: 0;
  bottom: 25rpx;
  font-size: 28rpx;
  color: #fff;
  opacity: 0;
  transition: all 0.35s ease;
  .u-head-name {
    width: 100rpx;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
  }
}
.show {
  opacity: 1 !important;
}
//顏色
.green {
  background-color: #67c29f;
}

.gray {
  background-color: #d9d9d9;
}

.red {
  background-color: #eb7c7c;
}

// 新风格

.blue-type {
  background: #3f84fb;
}

.emoji-btn {
  position: absolute;
  left: 0;
  bottom: 0;
  width: 40rpx;
  height: 40rpx;
}
.emoji-box {
  position: absolute;
  left: 0;
  bottom: -110rpx;
  width: 370rpx;
  height: 90rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #fff;
  border-radius: 16rpx;
  &::before {
    position: absolute;
    left: 30rpx;
    top: -22rpx;
    content: "";
    height: 0;
    width: 0;
    border-bottom: 26rpx solid #fff;
    border-left: 26rpx dashed transparent;
    border-right: 26rpx dashed transparent;
  }
  img {
    width: 54rpx;
    height: 54rpx;
    margin-right: 30rpx;
    &:last-child {
      margin-right: 0;
    }
  }
}

.antEomji {
  position: absolute;
  width: 80rpx;
  height: 80rpx;
  top: 106rpx;
  transform: scale(0) translate(0, -50%);
  opacity: 0;
  z-index: 50;
}
.left130 {
  left: 130rpx;
}
.right130 {
  right: 130rpx;
}
.sendAnt {
  animation: send 1.5s linear;
  -webkit-animation: send 1.5s linear;
}
.sendAnt-r {
  animation: send-r 1.5s linear;
  -webkit-animation: send-r 1.5s linear;
}
@keyframes send {
  0% {
    opacity: 0;
    transform: scale(0) translate(0, -50%);
  }
  20% {
    opacity: 1;
    transform: scale(1) translate(0, -50%);
  }
  30% {
    opacity: 1;
    transform: scale(1) translate(0, -50%);
  }
  80% {
    opacity: 1;
    transform: scale(1) translate(350rpx, -50%);
  }
  100% {
    opacity: 0;
    transform: scale(1) translate(500rpx, -50%);
  }
}
@keyframes send-r {
  0% {
    opacity: 0;
    transform: scale(0) translate(0, -50%);
  }
  20% {
    opacity: 1;
    transform: scale(1) translate(0, -50%);
  }
  30% {
    opacity: 1;
    transform: scale(1) translate(0, -50%);
  }
  80% {
    opacity: 1;
    transform: scale(1) translate(-350rpx, -50%);
  }
  100% {
    opacity: 0;
    transform: scale(1) translate(-500rpx, -50%);
  }
}
</style>
