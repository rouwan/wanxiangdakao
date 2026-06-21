<template>
  <div class="m-select"
       v-if="dataReady">
    <div class="select-list"
         :class="{listIn:showList}">
      <div class="select-item"
           :class="{success:item.state=='success',transparent:(item.hideBg&&showAnswer),fail:item.state=='fail',big:list[currentIndex].modeType==4}"
           v-for="(item,sIndex) in list[currentIndex].optionList"
           :key="index"
           @click="select(item,sIndex)">
        <div class="select-item-txt">
          <img v-if="item.state=='success'&&item.userOne&&list[currentIndex].modeType!=4"
               class="left-img yes"
               src="/static/rank/yes.png"
               alt="">
          <img v-if="item.state=='fail'&&item.userOne&&list[currentIndex].modeType!=4"
               class="left-img err"
               src="/static/rank/error.png"
               alt="">
          <div class="txt"
               v-if="list[currentIndex].modeType!=4">
            {{item.name}}
          </div>
          <img v-if="list[currentIndex].modeType==4&&item.name=='对'&&!item.state"
               class="select-img select-yes"
               src="/static/rank/b_yes.png"
               alt="">
          <img v-if="list[currentIndex].modeType==4&&item.name=='错'&&!item.state"
               class="select-img select-err"
               src="/static/rank/b_error.png"
               alt="">

          <img v-if="list[currentIndex].modeType==4&&item.name=='对'&&item.state"
               class="select-img select-yes"
               src="/static/rank/yes_w.png"
               alt="">
          <img v-if="list[currentIndex].modeType==4&&item.name=='错'&&item.state"
               class="select-img select-err"
               src="/static/rank/error_w.png"
               alt="">

          <img v-if="list[currentIndex].modeType==4&&item.name=='对'&&item.state=='success'&&item.hideBg&&showAnswer"
               class="select-img select-yes"
               src="/static/rank/icon_yes_green.png"
               alt="">
          <img v-if="list[currentIndex].modeType==4&&item.name=='错'&&item.state=='success'&&item.hideBg&&showAnswer"
               class="select-img select-err"
               src="/static/rank/icon_no_green.png"
               alt="">

          <img v-if="list[currentIndex].modeType==4&&item.name=='对'&&item.state=='fail'&&item.hideBg&&showAnswer"
               class="select-img select-yes"
               src="/static/rank/icon_yes_red.png"
               alt="">
          <img v-if="list[currentIndex].modeType==4&&item.name=='错'&&item.state=='fail'&&item.hideBg&&showAnswer"
               class="select-img select-err"
               src="/static/rank/icon_no_red.png"
               alt="">
        </div>
        <div class="select-num"
             v-if="item.selectNum&&item.selectNum>0&&showAnswer">{{item.selectNum}}人</div>
        <div class="select-mask"
             :style="item.maskWidth">
          <div class="select-txt">
            <div v-if="list[currentIndex].modeType!=4">
              {{item.name}}
            </div>
            <img v-if="list[currentIndex].modeType==4&&item.name=='对'"
                 class="select-img select-yes"
                 src="/static/rank/yes_w.png"
                 alt="">
            <img v-if="list[currentIndex].modeType==4&&item.name=='错'"
                 class="select-img select-err"
                 src="/static/rank/error_w.png"
                 alt="">
            <div class="select-num">{{item.selectNum}}人</div>
          </div>

        </div>
      </div>
    </div>
    <!-- 答错遮罩 -->
    <div class="fail-mask"
         v-if="showFailMask"></div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  props: [
    "subjectList",
    "currentIndex",
    "show",
    "userNum",
    "ansList",
    "hide",
    "showBothAns"
  ],
  data() {
    return {
      dataReady: false,
      list: [],
      userState: false,
      showFailMask: false,
      showAnswer: false,
      showList: false,
      time: 10000,
      timeOut: false,
      thTimer: null
    };
  },
  computed: {
    ...mapState(["IMGHEAD", "globalSoundPlayer"])
  },
  watch: {
    subjectList(value) {
      this.list = JSON.parse(JSON.stringify(value)); //深拷贝 问题数据
      this.dataReady = true;
    },
    show() {
      this.showList = true;
      this.showAnswer = false;
      this.userState = false;
      this.timeOut = false;
      setTimeout(() => {
        console.log("queslect");
        this.startTimer();
      }, 500);
    },

    hide() {
      this.showList = false;
      this.stopTimer();
    },
    time(val) {
      if (val <= 0) {
        this.stopTimer();
        this.timeOut = true;
        console.log("timeout");
      }
    },
    showBothAns() {
      //展示答题结果
      this.showAnswer = true;
      this.list[this.currentIndex].optionList.forEach((item, index) => {
        if (item.sortId == this.list[this.currentIndex].optionSortId) {
          item.state = "success";
        } else if (
          item.selectNum > 0 &&
          item.sortId != this.list[this.currentIndex].optionSortId
        ) {
          item.state = "fail";
        }

        item.maskWidth = `width:${
          item.selectNum / this.userNum > 1
            ? 1 * 100
            : item.selectNum / this.userNum * 100
        }%;opacity:1`;
      });
      this.$emit("answerComplete");
    },
    ansList(list) {
      //统计选项 选中人数
      if (list.length > 0) {
        let _last = list.slice(list.length - 1);
        this.list[this.currentIndex].optionList.forEach((item, index) => {
          if (
            _last[0].answer == item.sortId &&
            _last[0].index == this.currentIndex
          ) {
            if (item.selectNum) {
              item.selectNum++;
              item.hideBg = true;
            } else {
              item.hideBg = true;
              item.selectNum = 1;
            }
          }
        });
      }
    }
  },
  methods: {
    throttleHandle(item,index, delay) {
      clearTimeout(this.thTimer);
      this.thTimer = setTimeout(() => {
        this.select(item,index);
      }, delay);
    },
    /** 播放答题音效（globalSoundPlayer 可能未初始化） */
    audioPlay(type) {
      if (!this.globalSoundPlayer) return;
      try {
        if (type == "success") {
          this.globalSoundPlayer.src =
            "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/right.mp3";
        } else {
          this.globalSoundPlayer.src =
            "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/wrong.mp3";
        }
        this.globalSoundPlayer.play();
        setTimeout(() => {
          if (this.globalSoundPlayer) this.globalSoundPlayer.src = "";
        }, 3000);
      } catch(e) {}
    },
    startTimer() {
      this.time = 10000;
      this.timer = setInterval(() => {
        this.time -= 100;
      }, 100);
    },
    stopTimer() {
      clearInterval(this.timer);
      this.time = 10000;
    },
    select(item, index) {
      //TODO 检查答案，处理选中逻辑
      console.log("click select", !item.select, !this.userState, !this.timeOut);
      if (!item.select && !this.userState && !this.timeOut) {
        let answerMap = {};
        for (
          let i = 0;
          i < this.list[this.currentIndex].optionList.length;
          i++
        ) {
          answerMap[
            this.list[this.currentIndex].optionList[i].name
          ] = this.list[this.currentIndex].optionList[i].sortId;
        }
        let obj = {
          type: 4,
          data: {
            answer: answerMap[item.name],
            index: this.currentIndex,
            seconds: this.time, //到 questionBox在赋值
            timeout: false, // 默认false, qsb 赋值
            uid: 0 //qbs 赋值
          }
        };
        item.select = true;
        item.userOne = true;
        this.userState = true;
        if (item.sortId == this.list[this.currentIndex].optionSortId) {
          item.state = "success";
          this.audioPlay("success");
          // this.$emit("success", obj); //提交答案 send type4
        } else {
          //答错的业务处理
          item.state = "fail";
          this.audioPlay("fail");
          this.showFailMask = true;
          setTimeout(() => {
            this.showFailMask = false;
          }, 400);
        }
        this.stopTimer(); //计时器停止
        this.$emit("selected", obj);
      }
    }
  },
  mounted() {
    setTimeout(() => {
      if (this.subjectList) {
        this.list = JSON.parse(JSON.stringify(this.subjectList)); //深拷贝 问题数据
        this.dataReady = true;
      }
    }, 100);
  },
  onUnload() {
    this.timeOut = false;
    this.userState = false;
  }
};
</script>

<style lang="scss" scoped>
.select-box {
  margin-top: 80rpx;
  display: flex;
  justify-content: center;
}

.select-list {
  height: 490rpx;
  margin: 0 60rpx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .select-item {
    width: 500rpx;
    height: 106rpx;
    position: relative;
    font-size: 40rpx;
    border: 2rpx solid #17c4c9;
    color: #7cf7e4;
    border-radius: 10rpx;
    box-sizing: border-box;
    opacity: 0;
    transform: scale(0);
    // filter: drop-shadow(0 0 6px rgba(0, 0, 0, 0.4));
    transition: all 0.4s ease;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20rpx;
    &:last-child {
      margin-bottom: 0;
    }
    .select-item-txt {
      position: relative;
      min-width: 250rpx;
      // max-width: 300rpx;
      text-align: center;
      font-weight: bold;
    }
    .select-num {
      position: absolute;
      right: 24rpx;
      top: 50%;
      transform: translateY(-50%);
    }
    .select-mask {
      width: 0;
      height: 100%;
      transition: width 0.2s linear;
      overflow: hidden;
      color: #fff;
      position: absolute;
      left: 0;
      top: 0;
      opacity: 0;
      .select-txt {
        position: absolute;
        width: 500rpx;
        // height: 106rpx;
        font-size: 40rpx;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        left: 0;
        top: 0;
      }
      .select-num {
        color: #fff !important;
      }
    }
    .yes {
      width: 40rpx;
      height: 35rpx;
    }
    .err {
      width: 31rpx;
      height: 35rpx;
    }

    .select-img {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%) !important;
    }
    .select-yes {
      width: 101rpx;
      height: 82rpx;
    }
    .select-err {
      width: 80rpx;
      height: 82rpx;
    }
    img {
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
    }
    .left-img {
      left: -40rpx;
    }
    .right-img {
      right: -40rpx;
    }
  }
  .big {
    height: 190rpx;
  }
  .success,
  .fail {
    color: #fff;
    animation: btnScale 0.4s ease;
  }

  .success {
    background: #85d85d;
    border-color: #85d85d;
    box-shadow: 0 0 10rpx 0rpx rgba(#85d85d, 0.9);
    .select-num {
      color: #85d85d !important;
    }
    .select-mask {
      background: #85d85d;
    }
  }
  .fail {
    background: #ff217b;
    border-color: #ff217b;
    box-shadow: 0 0 20rpx 0rpx rgba(#ff217b, 0.9);
    .select-num {
      color: #ff217b !important;
    }
    .select-mask {
      background: #ff217b;
    }
  }
  .transparent {
    background: transparent !important;
    color: #7cf7e4 !important;
  }
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
@keyframes btnScale {
  50% {
    transform: scale(1.2);
  }
  100% {
    transform: scale(1);
  }
}
.listIn {
  .select-item {
    transform: scale(1);
    opacity: 1;
  }
}
</style>
