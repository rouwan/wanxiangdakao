<template>
  <div class="game-box">
    <!-- 撸猫游戏 start-->
    <div class="cat-game">
      <div class="process">
        <div class="txt">剩余时间:</div>
        <div class="p-body">
          <img class="p-bg"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/p-bg.png">
          <div class="p-line"
               :class="{'process-ant':gameStatus==1}"
               :style="pStyle"></div>
          <div class="sc"></div>
          <div class="sc"></div>
          <div class="sc"></div>
          <div class="sc"></div>
        </div>
      </div>
      <div class="platform">
        <img class="l-bg"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/l-bg.png">
        <!-- cat-1 -->
        <div class="cat-modal-1"
             :class="{show:timeType==1}"></div>
        <!-- cat-2 -->
        <div :class="{show:timeType==2}"
             class="cat-modal-2">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/modal-2.png">
          <img class="modal-2-ant"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/modal-2-act.png">
        </div>
        <!-- cat-3 -->
        <div :class="{show:timeType==3}"
             class="cat-modal-3">
          <img class="modal-3-l"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/modal-3-l.png">
          <img class="modal-3-r"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/modal-3-r.png">
          <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/modal-3.png">
        </div>
        <div v-if="gameStatus==1"
             class="gold-ant"></div>
      </div>
      <div class="gold-txt">获的金币数</div>
      <div class="gold">
        <img src="/static/gold.png"> {{gold}}
      </div>

      <div class="guide"
           v-if="showGuide">
        <div class="guide-txt">
          <!-- 点击次数越多,
          </br>
          获得金币越多 -->
          <div>温馨提示</div>
          快速、持续点击,可以获得更多金币哦
        </div>
      </div>
      <!-- hover-class="cf-act" -->
          <!-- :class="{'cf-act':cf}" -->
      <div class="cf"
           @click="catClick">
        <div v-if="showGuide"
             class="cf-mask"
             @click="startCatGame"></div>
        <img v-if="showGuide"
             class="finger"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/finger.png">
      </div>
      <div class="cat-ad"
           :class="{showAd:showCatAd}">
        <ad unit-id="adunit-bd7cb52099803182"></ad>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      gold:0,
      gameStatus: 0,
      timeType: 1,
      cf:false,
      showGuide: true,
      timer:null,
      gameTime:0,
      pStyle:"",
      addNum:1,
      showCatAd:false,
    };
  },

  methods: {
    startCatGame() {
      this.showGuide = false;
      this.gameStatus = 1; //游戏中
      setTimeout(() => {
          this.pStyle = "background:#f0af2b!important;";
      }, 1000);

      setTimeout(() => {
          this.addNum = 2;
          this.pStyle = "background:#f08c2b!important;";
      }, 2000);

      setTimeout(() => {
          this.addNum = 3;
          this.pStyle = "background:#f06c2b!important;";
      }, 3000);

      setTimeout(() => {
          this.addNum = 5;
          this.pStyle = "background:#f06c2b!important;";
      }, 4000);

      setTimeout(() => {
          console.log('游戏结束')
          this.gameStatus = 0;
          this.showCatAd=true;
          this.$emit("stopGame",this.gold);
      }, 5000);

    },
    catClick() {  
      if (this.gameStatus == 0) {
        wx.showToast({ title: "游戏已结束", icon: "none" });
        return false;
      }
      this.gold += this.addNum;
      if (this.gold >= 66) {
        this.timeType = 3;
      } else if (this.gold >= 33) {
        this.timeType = 2;
      }

    }
  }
};
</script>

<style lang="scss" scoped>
.game-box{
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  z-index:20;
}
.cat-btn {
  width: 288rpx;
  height: 105rpx;
  display: block;
  margin: 35rpx auto 0 auto;
  &:active {
    transform: scale(0.95);
  }
}

.cat-game {
  position: fixed;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  background: rgba(#000, 0.8);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  z-index: 30;
}
.process {
  width: 640rpx;
  .txt {
    padding: 40rpx 0 20rpx;
    font-size: 32rpx;
    font-weight: bold;
    color: #fff;
    text-align: left;
    line-height: 1;
  }
  .p-body {
    width: 631rpx;
    height: 59rpx;
    position: relative;
    .p-bg {
      width: 100%;
      height: 100%;
    }
    .p-line {
      position: absolute;
      left: 25rpx;
      top: 50%;
      width: 580rpx;
      height: 22rpx;
      transform: translate(0, -50%);
      background: #ffd647;
      border-radius: 1000rpx;
      transition: all 0.1s linear;
    }
    .process-ant {
      animation: process 5s linear forwards;
    }
    @keyframes process {
      0% {
        width: 580rpx;
      }
      100% {
        width: 0;
      }
    }
    .sc {
      position: absolute;
      top: 50%;
      left: 108rpx;
      width: 35rpx;
      height: 35rpx;
      border-radius: 50%;
      background: #fff;
      transform: translateY(-50%);
      &::after {
        content: "";
        position: absolute;
        transform: translate(-50%, -50%);
        left: 50%;
        top: 50%;
        width: 14rpx;
        height: 14rpx;
        border-radius: 50%;
        background: #d33733;
      }

      &:nth-of-type(2) {
        left: 234rpx;
        &::after {
          background: #f06c2b;
        }
      }
      &:nth-of-type(3) {
        left: 360rpx;
        &::after {
          background: #f08c2b;
        }
      }
      &:nth-of-type(4) {
        left: 488rpx;
        &::after {
          background: #f0af2b;
        }
      }
    }
  }
}

.platform {
  margin-top: 20rpx;
  position: relative;
  .l-bg {
    width: 609rpx;
    height: 532rpx;
  }
  .cat-modal-1,
  .cat-modal-2,
  .cat-modal-3 {
    width: 335rpx;
    height: 435rpx;
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    bottom: 35rpx;
    opacity: 0;
    img {
      width: 100%;
      height: 100%;
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
    }
  }
  .cat-modal-1 {
    background-size: 335rpx 435rpx;
    background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/eye.png");
    background-repeat: no-repeat;
    animation: modal-1 1.4s steps(1) infinite;
  }
  @keyframes modal-1 {
    0%,
    29.4% {
      background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/eye.png");
    }
    5.8%,
    23.5% {
      background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/eye-act.png");
    }
  }

  .modal-2-ant {
    animation: modal-2-ant 1.6s linear infinite;
    transform-origin: 60% 73%;
  }
  @keyframes modal-2-ant {
    0%,
    50%,
    100% {
      transform: translate(-50%, -50%) rotate(0);
    }
    25% {
      transform: translate(-50%, -50%) rotate(-20deg);
    }
    75% {
      transform: translate(-50%, -50%) rotate(20deg);
    }
  }

  .modal-3-l {
    animation: modal-3-l 1.6s linear infinite;
    transform-origin: 32% 69%;
  }
  .modal-3-r {
    animation: modal-3-r 1.6s linear infinite;
    transform-origin: 64% 69%;
  }
  @keyframes modal-3-l {
    0%,
    50%,
    100% {
      transform: translate(-50%, -50%) rotate(0);
    }
    25% {
      transform: translate(-50%, -50%) rotate(-40deg);
    }
    75% {
      transform: translate(-50%, -50%) rotate(40deg);
    }
  }
  @keyframes modal-3-r {
    0%,
    50%,
    100% {
      transform: translate(-50%, -50%) rotate(0);
    }
    25% {
      transform: translate(-50%, -50%) rotate(40deg);
    }
    75% {
      transform: translate(-50%, -50%) rotate(-40deg);
    }
  }
}

.gold-ant {
  width: 450rpx;
  height: 450rpx;
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  background-size: 4950rpx 450rpx;
  background-repeat: no-repeat;
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/gold-ant.png");
  background-position: 0 0;
  animation: gold-ant 0.5s steps(11, end) infinite;
}

@keyframes gold-ant {
  to {
    background-position: -4950rpx 0;
  }
}
.gold-txt {
  font-size: 32rpx;
  font-weight: bold;
  color: #fff;
  text-align: center;
  margin-top: 20rpx;
  line-height: 1;
}

.gold {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 60rpx;
  font-weight: bold;
  color: #ffeca3;
  margin-top: 20rpx;
  img {
    width: 70rpx;
    height: 70rpx;
    margin-right: 20rpx;
  }
}

.cf {
  width: 334rpx;
  height: 258rpx;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: 900rpx;
  background-size: 334rpx 258rpx;
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/cf.png");
  background-repeat: no-repeat;
  .cf-mask {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    z-index: 1;
  }
  &:active{
    background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/cf-act.png");
  }
}
.cf-act {
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/cf-act.png");
}

.guide {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(#000, 0.7);
  .guide-txt {
    position: absolute;
    top: 742rpx;
    left: 50%;
    transform: translateX(-50%);
    font-size: 36rpx;
    line-height: 1.5em;
    font-weight: bold;
    color: #fff;
    text-align: center;
    width: 100%;
    div {
      color: #ffe998;
      margin-bottom: 20rpx;
    }
  }
}
.finger {
  position: absolute;
  top: 0rpx;
  right: -150rpx;
  width: 192rpx;
  height: 173rpx;
  animation: click 2s linear infinite;
}
@keyframes click {
  0%,
  50%,
  100% {
    transform: translate(-40%) rotate(0);
  }
  25%,
  75% {
    transform: translate(-40%) rotate(-30deg);
  }
}
.hasTip {
  position: relative;
  &::after {
    content: "";
    position: absolute;
    right: 0;
    top: 0;
    width: 26rpx;
    height: 26rpx;
    background: #ff2121;
    border: 3rpx solid #fff;
    box-sizing: border-box;
    border-radius: 50%;
  }
}
.cat-ad {
  position: fixed;
  left: 0;
  top: 970rpx;
  width: 100%;
  opacity: 0;
  z-index: -1;
}
.hide {
  opacity: 0 !important;
}
.show {
  opacity: 1 !important;
}
.showAd {
  opacity: 1;
  z-index: 1;
}
</style>


</style>
