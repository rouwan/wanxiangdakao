<template>
  <div class="ant-title-item">
    <div class="title-type" :class="{titleTypeIn:showTitleType}" @animationend="animationEndHandle">{{questionType}}<img src="/static/titleType.png" alt=""></div>
    <div class="title-number" :class="{titleNumberIn:showTitleType}">第{{quesIndex+1}}题</div>
    <div class="double-tip" :class="{doubuleTipIn:showDoubleTip}">
      <div class="x2">X2</div>
    </div>
  </div>
</template>

<script>
// description:
// animation title item , 带有进场 出场动画 的 题目类型 题目次序 双倍积分提示的 页面组件  入场后 自动退场
export default {
  props: {
    quesIndex: {
      //题目次序
      type: Number || String,
      required: true
      // default:0
    },
    quesMode: {
      //题目类型 分类
      type: Number || String,
      required: true
      // default: 1
    },
    fadeIn: {
      type: Boolean,
      required: true
      // default: false
    }
  },
  data() {
    return {
      time: 0,
      showTitleType: false,
      showDoubleTip: false
    };
  },
  computed: {
    questionType() {
        let obj = {
          1: "德",
          2: "商",
          3: "文",
          4: "理",
          5: "艺",
          6: "体",
          7: "图"
        };
        return obj[this.quesMode];
    }
  },
  watch: {
    fadeIn() {
      console.log("fadeIn");
      this.showTitleType = true;
      setTimeout(() => {
      this.showTitleType = false;
      }, 1500);
      if (this.quesIndex == 4) {
        this.showDoubleTip = true;
        setTimeout(() => {
        this.showDoubleTip = false;
        }, 1500);
      }
    },
    quesIndex() {
      console.log("watchdfe");
    }
  },
  methods: {
    animationEndHandle() {
      // this.showTitleType = false;
      // this.showDoubleTip = false;
      this.$emit("antEnd"); //提交 动画结束事件
    }
  },
  mounted() {
    console.log("jinlai");
  }
};
</script>

<style lang="scss" scoped>
.title-type,
.title-number {
  width: 100%;
  position: absolute;
  left: 50%;
  opacity: 0;
  text-align: center;
}
.title-type {
  transform: translate(-50%, 180rpx);
  font-size: 60rpx;
  color: #67c29f;
  text-align: center;
  img {
    position: absolute;
    width: 265rpx;
    height: 92rpx;
    bottom: -46rpx;
    left: 50%;
    transform: translateX(-50%);
  }
}
.title-number {
  transform: translate(-50%, 310rpx);
  font-size: 40rpx;
  color: #6b6a6b;
  text-align: center;
  font-size: 90rpx;
  font-weight: bold;
}
.titleTypeIn {
  animation: ttIn 1.28s ease;
}
.titleNumberIn {
  animation: tnIn 1.28s ease;
}

@keyframes ttIn {
  0% {
    transform: translate(-50%, 80rpx);
    opacity: 0;
  }
  33% {
    transform: translate(-50%, 100rpx);
    opacity: 1;
  }
  66% {
    transform: translate(-50%, 100rpx);
    opacity: 1;
  }
  100% {
    transform: translate(-50%, 120rpx);
    opacity: 0;
  }
}

@keyframes tnIn {
  0% {
    transform: translate(-50%, 210rpx) scale(0);
    opacity: 1;
  }
  33% {
    transform: translate(-50%, 230rpx) scale(1);
    opacity: 1;
  }
  66% {
    transform: translate(-50%, 230rpx) scale(1);
    opacity: 1;
  }
  100% {
    transform: translate(-50%, 230rpx) scale(0);
    opacity: 0;
  }
}

@keyframes dtIn {
  0% {
    transform: translate(-50%, 380rpx) scale(0);
    opacity: 1;
  }
  33% {
    transform: translate(-50%, 380rpx) scale(1);
    opacity: 1;
  }
  66% {
    transform: translate(-50%, 380rpx) scale(1);
    opacity: 1;
  }
  100% {
    transform: translate(-50%, 380rpx) scale(0);
    opacity: 0;
  }
}
.doubuleTipIn {
  animation: dtIn 1.28s ease;
}

.double-tip {
  position: absolute;
  width: 522rpx;
  height: 46rpx;
  background: rgba(#67c29f, 0.23);
  border-radius: 1000rpx;
  left: 50%;
  transform: translate(-50%, 0rpx) scale(0);
  opacity: 0;
  top: 0;
  &:before,
  &:after {
    position: absolute;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
    height: 46rpx;
    border-radius: 1000rpx;
  }
  &:before {
    content: "";
    width: 424rpx;
    background: rgba(#67c29f, 0.46);
  }
  &:after {
    width: 320rpx;
    background: #67c29f;
    content: "双倍得分";
    text-align: left;
    padding-left: 46rpx;
    box-sizing: border-box;
    line-height: 46rpx;
    font-size: 36rpx;
    color: #fff;
  }
  .x2 {
    font-size: 72rpx;
    color: #fff;
    font-weight: bold;
    -webkit-text-stroke: 2rpx #67c29f;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(40%, -50%);
    z-index: 5;
  }
}
</style>
