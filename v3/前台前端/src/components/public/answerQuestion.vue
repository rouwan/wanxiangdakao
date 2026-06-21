<template>
  <div class="answerQuetion" v-if="DATA_READY">
    <div class="content" :class="{imgTitle:list[quesIndex].modeType==1}">
      <div class="score-process" :class="{processIn:SHOW_PROCESS}">
        <div class="score">{{myScoreNumber}}</div>
        <div class="process" :style="myProcess"></div>
      </div>
      <div class="ans-center">
        <div class="title" :class="{titleIn:SHOW_TITLE}">{{list[quesIndex].title}}</div>
        <div class="img-box" :class="{imgIn:SHOW_TITLE}" v-if="list[quesIndex].modeType==1">
          <img mode="aspectFit" :src="IMGHEAD+list[quesIndex].image" alt="">
        </div>
        <div class="select-list" :class="{listIn:SHOW_ANSWER_LIST}">
          <div class="select-item" :class="{success:item.state=='success',fail:item.state=='fail',noSelect:item.noSelect}" v-for="(item,index) in list[quesIndex].optionList" :key="index" @click="select(item,index)">
            <img v-if="item.state=='success'&&item.userOne" class="left-img" src="/static/sIcon.png" alt="">
            <img v-if="item.state=='fail'&&item.userOne" class="left-img" src="/static/fIcon.png" alt=""> {{item.name}}
            <img v-if="item.state=='success'&&item.userTwo" class="right-img" src="/static/sIcon.png" alt="">
            <img v-if="item.state=='fail'&&item.userTwo" class="right-img" src="/static/fIcon.png" alt="">
          </div>
        </div>
      </div>
      <div class="score-process" :class="{processIn:SHOW_PROCESS}">
        <div class="score">{{otherScoreNumber}}</div>
        <div class="process" :style="otherProcess" :class="{showFailStyle:SHOW_ANSWER_FAIL_STYLE}"></div>
      </div>
    </div>
    <!-- 答错遮罩 -->
    <div class="fail-mask" v-if="SHOW_FAIL_MASK"></div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  props: {
    subjectList: {
      //题目数据
      type: Object,
      default: [{ title: "测试题目", parentType: 1, modeType: 0 }]
    },
    quesIndex: {
      type: Number,
      default: 0
    },
    fadeIn: {
      type: Boolean,
      default: false
    },
    canNext: {
      type: Boolean,
      default: false
    },
    myScore: {
      type: Object
    },
    otherScore: {
      type: Object
    },
    identity: {
      type: Number
    }
  },
  data() {
    //状态值 用 大写 下划线
    return {
      DATA_READY: false,
      SHOW_TITLE: false,
      SHOW_ANSWER_LIST: false,
      SHOW_PROCESS: false,
      IS_QUESIONT_ANSWER: false,
      SHOW_ANSWER_FAIL_STYLE: false,
      SHOW_FAIL_MASK: false,
      list: null,
      // 分数条
      myScoreNumber: 0,
      otherScoreNumber: 0,
      myProcess: "height:0%;",
      otherProcess: "height:0%;",
      // 数据相关
      time: 10000,
      timer: null
    };
  },
  computed: {
    ...mapState(["IMGHEAD","globalSoundPlayer"])
  },
  watch: {
    fadeIn() {
      this.SHOW_TITLE = true;
      setTimeout(() => {
        this.SHOW_ANSWER_LIST = true;
        this.SHOW_PROCESS = true;
        this.startTimer();
      }, 1300);
    },
    time(value) {
      if (value <= 0) {
        this.stopTimer();
      }
    },
    otherScore(val) {
      if (val.score == 0) {
        this.SHOW_ANSWER_FAIL_STYLE = true;
        setTimeout(() => {
          this.SHOW_ANSWER_FAIL_STYLE = false;
        }, 500);
      }
      this.otherScoreNumber = val.score;
      this.otherProcess = `height:${this.otherScoreNumber / 12}%;`;
    },
    myScore(val) {
      this.myScoreNumber = val.score;
      this.myProcess = `height:${this.myScoreNumber / 12}%;`;
    },
    canNext() {
      this.showAllAns();
      this.stopTimer();
    },
    quesIndex() {
      this.IS_QUESIONT_ANSWER = false;
    }
  },
  methods: {
  
    initList() {
      // 初始化数据列表
      this.list = JSON.parse(JSON.stringify(this.subjectList));
    },
    // 答题操作
    select(item, index) {
      if (
        !item.select &&
        !this.IS_QUESIONT_ANSWER &&
        !this.timeOut &&
        this.identity != 2
      ) {
        this.stopTimer(); //计时器停止
        let answerMap = {};
        for (let i = 0; i < this.list[this.quesIndex].optionList.length; i++) {
          answerMap[this.list[this.quesIndex].optionList[i].name] = this.list[
            this.quesIndex
          ].optionList[i].sortId;
        }
        let obj = {
          type: 4,
          data: {
            answer: answerMap[item.name],
            index: this.quesIndex,
            seconds: this.time, //到 questionBox在赋值
            timeout: false, // 默认false, qsb 赋值
            uid: 0, //qbs 赋值,
            gameId: 0
          }
        };
        item.select = true;
        if (this.identity == 1) {
          item.userTwo = true;
        } else {
          item.userOne = true;
        }
        this.IS_QUESIONT_ANSWER = true;
        if (item.sortId == this.list[this.quesIndex].optionSortId) {
          item.state = "success";
          this.audioPlay("success");
          this.$emit("optClick", obj); //提交答案 send type4
        } else {
          // 答错的业务处理
          item.state = "fail";
          this.audioPlay("fail");
          this.SHOW_FAIL_MASK = true;
          setTimeout(() => {
            this.SHOW_FAIL_MASK = false;
          }, 400);
          this.$emit("optClick", obj);
        }
      }
    },
    showAnsItem(showInfo, list, type) {
      for (let i = 0; i < list.length; i++) {
        if (showInfo.right && list[i].sortId == showInfo.rightAnswer) {
          list[i].state = "success";
          list[i].select = true;
          if (type == "userOne") {
            list[i].userOne = true;
          } else if (type == "userTwo") {
            list[i].userTwo = true;
          }
          console.log(list[i]);
        } else if (!showInfo.right && list[i].sortId == showInfo.answer) {
          list[i].state = "fail";
          list[i].select = true;
          if (type == "userOne") {
            list[i].userOne = true;
          } else if (type == "userTwo") {
            list[i].userTwo = true;
          }
        }
      }
    },
    showAllAns() {
      //双方都答题后 才显示 B用户数据  则此行为即为 可下一题状态 不需要在判定
      //显示对方选项
      let list = this.list[this.quesIndex].optionList;
      let showInfo = this.otherScore;
      if (this.identity != 0) {
        showInfo = this.myScore;
        this.showAnsItem(showInfo, list, "userOne");
      } else {
        this.showAnsItem(showInfo, list, "userTwo");
      }
      if (this.identity == 2) {
        showInfo = this.otherScore;
        this.showAnsItem(showInfo, list, "userTwo");
      }

      //展示正确选项 隐藏未选择选项
      for (let i = 0; i < list.length; i++) {
        if (
          list[i].state != "success" &&
          list[i].state != "fail" &&
          list[i].sortId != this.otherScore.rightAnswer
        ) {
          list[i].noSelect = true;
        } else if (
          list[i].state != "success" &&
          list[i].state != "fail" &&
          list[i].sortId == this.otherScore.rightAnswer
        ) {
          list[i].state = "success";
          list[i].select = true;
        }
      }
      if (this.quesIndex != 4) {
        this.$emit("questionOk");
        console.log("请求下一题");
      }

      // 上一题退场动画
      setTimeout(() => {
        this.SHOW_TITLE = false;
        this.SHOW_ANSWER_LIST = false;
        if (this.quesIndex == 4) {
          this.SHOW_PROCESS = false;
          this.$emit("questionOk");
        }
      }, 1500);
    },
    // 计时器控制--答题时间记录
    startTimer() {
      console.log("timerkaishi");
      this.$emit("startTimer");
      this.time = 10000;
      this.timer = setInterval(() => {
        this.time -= 100;
      }, 100);
    },
    stopTimer() {
      this.$emit("stopTimer");
      clearInterval(this.timer);
    },
    /** 播放音效（globalSoundPlayer 可能未初始化） */
    audioPlay(type) {
      if (!this.globalSoundPlayer) return;
      try {
        if (type == "success") {
          this.globalSoundPlayer.src = "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/right.mp3";
        } else {
          this.globalSoundPlayer.src = "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/wrong.mp3";
        }
        this.globalSoundPlayer.play();
      } catch(e) {}
    }
  },
  mounted() {
    this.initList();
    setTimeout(() => {
      this.DATA_READY = true;
    }, 1500);
    // setTimeout(() => {
    //   this.startTimer();
    //   this.$emit("listShowed");
    // }, 3000);
  }
};
</script>

<style lang="scss" scoped>
@mixin flex {
  display: flex;
  align-items: center;
  justify-content: center;
}
.content {
  position: relative;
  @include flex;
  align-items: flex-end;
}
// 通用 可优化样式
.ans-center {
  // min-height: 100rpx;
  height: 900rpx;
  transition: all 0.4s ease;
  max-width: 630rpx;
}

.imgType{
  height: auto!important;
  margin-top: 30rpx;
}
.imgTitle {
  .title {
    font-size: 34rpx;
    height: auto;
    margin-bottom:20rpx;
    height: 100rpx;
  }
}
.img-box {
  width: 500rpx;
  height: 278rpx;
  margin: 0 auto 30rpx auto;
  opacity: 0;
  transition: all 0.8s ease;
  img {
    width: 100%;
    height: 100%;
  }
}
.title {
  height: 220rpx;
  margin: 30rpx 0 60rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  font-size: 42rpx;
  box-sizing: border-box;
  opacity: 0;
  transition: all 0.8s ease;
}

.select-list {
  height: 590rpx;
  min-width: 630rpx;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  .select-item {
    width: 542rpx;
    height: 120rpx;
    position: relative;
    font-size: 40rpx;
    border: 3rpx solid #ababab;
    border-radius: 10rpx;
    box-sizing: border-box;
    transition: all 0.4s ease;
    opacity: 0;
    transform: scale(0);
    // filter: drop-shadow(0 0 6px rgba(0, 0, 0, 0.4));
    box-shadow: 0 0 20rpx 6rpx rgba(0, 0, 0, 0.25);
    display: flex;
    justify-content: center;
    align-items: center;
    background-size: 536rpx 114rpx;
    background-repeat: repeat-y;
    background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAhgAAAByCAIAAACeFj9SAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA39pVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMDY3IDc5LjE1Nzc0NywgMjAxNS8wMy8zMC0yMzo0MDo0MiAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDowMmE0MWEzZi0zMjMxLWI4NDMtYWI5YS1iYThhZDBjZmYzNDUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6RjA2NUQxRjRBMTE1MTFFOEI3MkJBRjM4MEVCMTkzMTciIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6RjA2NUQxRjNBMTE1MTFFOEI3MkJBRjM4MEVCMTkzMTciIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChXaW5kb3dzKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjdhZDg0ZmViLTVjM2QtYzU0NC05ZmVmLWJjMmRiNmIwYzVmMyIgc3RSZWY6ZG9jdW1lbnRJRD0iYWRvYmU6ZG9jaWQ6cGhvdG9zaG9wOjU3YjVhOWI3LTdhOWEtYjU0OS1iODQ1LThjOGZh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");
    img {
      position: absolute;
      width: 28rpx;
      height: 28rpx;
      top: 50%;
      transform: translateY(-50%);
    }
    .left-img {
      left: 14rpx;
    }
    .right-img {
      right: 14rpx;
    }
  }

  .success,
  .fail {
    border: 6rpx solid #fff;
    color: #fff;
    animation: btnScale 0.4s ease;
  }
  .success {
    background: #67c29f;
  }
  .fail {
    background: #fd5c5c;
  }
  .noSelect {
    transform: scale(0) !important;
    opacity: 0 !important;
  }
}
.imgTitle {
  .select-list {
    height: 448rpx;
    .select-item {
      width: 433rpx;
      height: 96rpx;
      font-size: 32rpx;
      box-shadow: 0 0 10rpx 2rpx rgba(0, 0, 0, 0.25);
      img {
        width: 40rpx;
        height: 40rpx;
      }
    }
  }
}
.listIn {
  .select-item {
    transform: scale(1);
    opacity: 1;
  }
}
.titleIn {
  opacity: 1;
}

.imgIn {
  opacity: 1;
}
@keyframes btnScale {
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}

// 进度条
.processIn {
  transform: translateX(0) !important;
}
.score-process {
  box-sizing: border-box;
  width: 30rpx;
  height: 564rpx;
  border: 3rpx solid #858585;
  padding: 4rpx;
  border-radius: 1000rpx;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  margin-top: 20rpx;
  transition: all 0.4s ease;
  transform: translateX(-120rpx);
  &:last-child {
    transform: translateX(120rpx);
  }
  .score {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    font-size: 40rpx;
    color: #676565;
    line-height: 1;
    top: -40rpx;
  }
  .process {
    width: 100%;
    background: #67c29f;
    border-radius: 1000rpx;
    height: 0;
    transition: all 0.3s linear;
  }
}
.showFailStyle {
  background: #fd5c5c !important;
}
.fail-mask {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 50;
  background: rgba(red, 0.2);
}
// 通用 可优化样式-----------end
</style>
