<template>
  <!--
    整体结构说明：
    ┌──────────────────────────────────┐
    │  head-box（顶部栏，fixed定位）      │
    │  ├ 我(左)：头像 + 昵称 + 分数 + 表情按钮 │
    │  ├ 对手(右)：分数 + 昵称 + 头像        │
    │  └ round-cvs（中央倒计时圆环）        │
    ├──────────────────────────────────┤
    │  antEomji ×2（表情飞行动画图层）     │
    ├──────────────────────────────────┤
    │  main-conent（主内容区）            │
    │  └ answerList（题目卡片组件）        │
    └──────────────────────────────────┘
  -->

  <!-- 最外层：答题页面主体，gamePageType==1 时切换为蓝色主题 -->
  <div class="question-box"
       :class="{'blue-type':gamePageType==1}">

    <!-- ===== 顶部玩家信息栏（fixed 吸附在屏幕顶部）===== -->
    <div class="head-box">

      <!-- 左侧：我方玩家信息 -->
      <!-- head-in 类：入场动画，从左侧滑入 -->
      <div class="u-head-left"
           :class="{'head-in':showHead}">
        <!-- 我方头像 -->
        <img class="u-img"
             :src="myHeadInfo.headImg"
             alt="">
        <!-- 我方昵称 + 当前分数 -->
        <div class="u-info">
          <div class="u-head-name">{{myHeadInfo.name}}</div>
          <div class="u-head-sore">{{myScore.score||0}}</div>
        </div>
        <!-- 表情按钮：点击展开表情选择面板 -->
        <img class="emoji-btn"
             src="/static/icon/emojiBtn.png"
             @click="showEmojiBox=!showEmojiBox">
        <!-- 表情选择面板（气泡弹出层，三角箭头指向上方） -->
        <div class="emoji-box"
             v-if="showEmojiBox">
          <img v-for="(item,index) in emojiList"
               :key="index"
               :src="item"
               @click="sendEmoji(index)">
        </div>
      </div>

      <!-- 右侧：对手玩家信息（镜像布局） -->
      <!-- head-in 类：入场动画，从右侧滑入 -->
      <div class="u-head-right"
           :class="{'head-in':showHead}">
        <!-- 对手分数 + 昵称 -->
        <div class="u-info">
          <div class="u-head-name">{{otherHeadInfo.name}}</div>
          <div class="u-head-sore">{{otherScore.score||0}}</div>
        </div>
        <!-- 对手头像 -->
        <img class="u-img"
             :src="otherHeadInfo.headImg"
             alt="">
      </div>

      <!-- 中央倒计时圆环（与 head-box 同级，居中悬浮） -->
      <!-- timeCount 组件：环形进度条倒计时，每道题 10s -->
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

    <!-- 我方发出的表情动画图（从左侧飞向右侧） -->
    <img class="antEomji left130"
         :src="emojiList[emojiIndexMe]"
         :class="{'sendAnt':showEmojiMe}">

    <!-- 对手发出的表情动画图（从右侧飞向左侧） -->
    <img class="antEomji right130"
         :src="emojiList[emojiIndexHe]"
         :class="{'sendAnt-r':showEmojiHe,}">

    <!-- 主内容区域 -->
    <div class="main-conent">
      <!-- answerList 组件：5 道答题卡片的列表，包含题目、选项、进度条 -->
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
let dataStack = []; // 页面数据快照栈（调试/恢复用）
export default {
  /** Props 由父组件 game/index.vue 传入 */
  props: [
    "subjectList",   // 题目列表（5道题）
    "userList",      // 对战双方用户信息
    "otherAns",      // 对手的答题回执列表
    "gameOver",      // 是否对手逃跑
    "isGameEnd",     // 是否游戏结束
    "oldGame",       // 是否断线重连恢复的旧对局
    "netWork",       // 网络重连触发标记
    "oRun",          // 对手是否逃跑
    "emojiIndexMe",  // 我方发出的表情索引（-1=无表情）
    "emojiIndexHe",  // 对方发出的表情索引
    "sendToMe",      // 发送给我的数据（保留字段）
    "relinkIndex"    // 断线重连时当前题目索引
  ],
  components: {
    timeCount: timeCount,
    answerList: answerList
  },
  computed: {
    /** 从 Vuex Store 映射状态 */
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
      showTime: false,          // 是否显示倒计时圆环
      showAnsList: false,       // 是否显示答题卡片列表
      questionIndex: 0,         // 当前题目索引（0~4，共5题）
      myInfo: {},
      myHeadInfo: { headImg: "", name: "???", ansList: [], score: 0 },   // 我方顶部信息
      otherHeadInfo: { headImg: "", name: "???", ansList: [], score: 0 }, // 对手顶部信息
      myScore: {},              // 我方当前分数对象
      otherInfo: {},
      otherScore: {},           // 对手当前分数对象
      resetBtn: false,
      initTime: 10,             // 每题倒计时初始值（秒）
      initTimeCount: false,     // 触发 timeCount 组件初始化（toggle 方式）
      startTimeCount: false,    // 触发 timeCount 组件开始计时
      stopTimeCount: false,     // 触发 timeCount 组件停止计时
      canNext: false,           // 是否允许进入下一题
      gameResult: {             // 本局结果（我方/对方分数）
        otherScore: 0,
        myScore: 0
      },
      resultType: "",           // 结果文案："胜利"/"失败"/"平局"
      oRun: false,              // 对手逃跑标记
      showEmojiMe: false,       // 我方表情飞行动画开关
      showEmojiHe: false,       // 对方表情飞行动画开关
      showEmojiBox: false,      // 表情选择面板开关
      emojiList: [              // 4 个表情图片路径
        "/static/icon/0.png",
        "/static/icon/1.png",
        "/static/icon/2.png",
        "/static/icon/3.png"
      ],
      // 动效相关控制
      gameStop: false,          // 游戏停止标记
      showHead: false,          // 顶部栏入场动画
      userResult: 0,
      withUserResult: 0,
      headMode: 0,
      myAnsList: [0, 0, 0, 0, 0],     // 我方5题作答记录：0=超时 1=正确 2=错误
      otherAnsList: [0, 0, 0, 0, 0],  // 对手5题作答记录
      showBtn: false,
      // 奖励
      isReLink: false,          // 是否重连状态
      newTime: 0                // 重连时服务端返回的剩余时间
    };
  },
  watch: {
    /** 监听 rankIndex 变化（父组件从 WebSocket 收到下一题索引）：切换题目、重置计时器 */
    rankIndex(value) {
      //更新题目 更新 计时器
      this.canNext = !this.canNext;
    },
    /** 监听网络重连标记：重新获取当前题目数据，标记重连状态 */
    netWork() {
      console.log("network change");
      this.getCurrentQuetion();
      this.isReLink = true;
    },
    /** 游戏结束：隐藏倒计时，保存结算数据到 storage */
    isGameEnd() {
      this.showTime = false;
      let list = this.gameRecord.Result.subjectList;
      let resObj = {
        oRun: this.oRun
      };
      setStorage("rankGameResult", resObj);
    },
    /**
     * 监听对手答题回执（otherAns 数组）：
     * - 区分每条回执是己方还是对方
     * - 更新双方分数和 myAnsList / otherAnsList 记录
     *   （0=超时 / 1=正确 / 2=错误）
     */
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
    /** 对手逃跑：停止计时、隐藏答题界面、记录逃跑状态 */
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
    /** 我方表情索引变化时触发飞行动画（延迟100ms，持续1.5s） */
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
    /** 对方表情索引变化时触发飞行动画 */
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
    /** 映射 Vuex mutations */
    ...mapMutations({
      setUserOneState: "setUserOneState",
      setUserTwoState: "setUserTwoState",
      setCw: "setCw"
    }),
    /**
     * 发送表情：将表情索引和 userId 序列化后通过 $emit 传给父组件，
     * 由父组件通过 WebSocket 发送给服务端
     */
    sendEmoji(index) {
      let obj = { index: index, id: this.appInfo.userId };
      obj = JSON.stringify(obj);
      this.showEmojiBox = false;
      this.$emit("sendEmoji", obj);
    },
    /**
     * 解析 userList 填充双方头部信息（头像、昵称）
     * - 区分 bot（系统头像）和真人（微信头像）
     */
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
    /**
     * 断线重连恢复：从 userList 找到己方数据恢复当前题目索引、
     * 剩余时间，并启动计时器、显示答题列表
     */
    getCurrentQuetion() {
      let index = 0,
        user = {};
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
    /** 根据 gameResult 的分数对比得出胜负文案 */
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

    /**
     * 开始答题动画序列：
     * 0ms  → 显示倒计时圆环
     * 500ms → 双方头像栏滑入
     * 1000ms → 显示答题卡片列表
     */
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

    /** 给提交的答案对象附加当前用户 uid */
    setUserId(obj) {
      obj.data.uid = this.appInfo.userId;
    },
    /**
     * 当前题目回答完成回调：
     * - 停止计时器
     * - 前4题：2s 后自动切到下一题并重置倒计时
     * - 第5题：1s 后通知父组件答题结束
     */
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
    /** 通知父组件请求下一题（触发 WebSocket type=6） */
    sendNext() {
      this.startTimeCount = !this.startTimeCount;
      this.$emit("nextQuestion");
    },
    /** 答题正确：附加 uid 后 emit 给父组件 */
    answerSuccess(obj) {
      // 答题正确
      this.setUserId(obj);
      this.$emit("ansRight", obj);
    },
    /** 答题错误：附加 uid 后 emit 给父组件 */
    answerFail(obj) {
      // 答题错误
      this.setUserId(obj);
      this.$emit("ansFail", obj);
    },

    /** 判断是否显示答题列表（排除已结束/重连中无需显示的情况） */
    checkShowAnsList() {
      if (
        (this.questionIndex != 4 && !this.gameStop) ||
        (this.questionIndex == 4 && this.oldGame && this.gameStop)
      ) {
        this.showAnsList = true;
      }
    },
    /** 举报类型选择 */
    selectAdvice(item) {
      this.adviceType = item.type;
    },
    /** 根据主题设置导航栏颜色（紫色默认 / 蓝色主题） */
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
    /** 提交举报反馈 */
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
  /** 页面加载时备份当前 data 快照到 dataStack */
  onLoad(options) {
    dataStack.push({
      ...this.$data
    });
  },
  /**
   * 页面挂载：
   * 1. 设置导航栏颜色
   * 2. 解析 userList 填充双方信息
   * 3. 如果是旧对局重连 → 恢复题目状态
   * 4. 启动答题动画序列
   */
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
