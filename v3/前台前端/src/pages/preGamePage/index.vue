<style lang="scss" scoped>
@import "../../scss/layout.scss";
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
.m-box {
  width: 100%;
  height: 100%;
  padding-top: 85rpx;
  box-sizing: border-box;
  position: relative;
}
.m-inner {
  opacity: 0;
  transition: all 0.5s ease;
}
.show {
  opacity: 1 !important;
}
.u-tt {
  @include flex;
  margin-bottom: 180rpx;
  padding-left: 70rpx;
  font-size: 50rpx;
  font-weight: bold;
  color: #fff;

  img {
    width: 72rpx;
    height: 74rpx;
    margin-left: 30rpx;
  }
}

.u-btn {
  width: 360rpx;
  height: 104rpx;
  @include flex;
  font-size: 42rpx;
  font-weight: bold;
  color: #fff;
  border-radius: 20rpx;
  margin: 0 auto;
  background: #ff3582;
  display: flex;
  justify-content: center;
  align-items: center;
  img {
    width: 28rpx;
    height: 48rpx;
    margin-left: 20rpx;
    margin-right: 6rpx;
  }
}
.mb0 {
  margin-bottom: 0 !important;
}
.u-top-name {
  position: absolute;
  left: 50%;
  top: -160rpx;
  transform: translateX(-50%);
  width: 310rpx;
  height: 100rpx;
}
.u-rank-num {
  margin-top: -50rpx;
  @include flex;
  font-size: 42rpx;
  width: 160rpx;
  height: 46rpx;
  border-radius: 10rpx;
  color: #fff;
  img {
    width: 54rpx;
    height: 54rpx;
  }
}
.u-rank-x {
  font-size: 24rpx;
  margin: 0 6rpx;
  align-self: flex-end;
}
.u-rank {
  margin: 40rpx 0 70rpx;
  @include flex;
  font-size: 44rpx;
  color: #fff;
  .u-rank-word {
    color: #d8df21;
    margin-left: 10rpx;
  }
}
.u-big-medal {
  width: 316rpx !important;
  height: 316rpx !important;
}

.m-intro {
  width: 100%;
  height: 100%;
  position: fixed;
  background: rgba(#28235c, 0.95);
  left: 0;
  top: 0;
  z-index: 20;
}
.u-head {
  font-size: 54rpx;
  color: #fff;
  font-weight: bold;
  line-height: 1;
  position: relative;
  padding: 70rpx 0;
  @include flex;
  div {
    margin-right: 80rpx;
    &:last-child {
      margin-right: 0;
    }
  }
}
.t-act {
  color: #fbed21;
  position: relative;
  &::after {
    content: "";
    position: absolute;
    width: 160rpx;
    height: 12rpx;
    background: #fbed21;
    bottom: -30rpx;
    left: 50%;
    transform: translateX(-50%);
  }
}

.u-medal-list {
  width: 620rpx;
  height: 926rpx;
  margin: 0 auto;
  opacity: 0;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: 200rpx;
  z-index: 10;
  .u-medal-head {
    display: flex;
    justify-content: space-between;
    padding: 0 44rpx;
    font-size: 36rpx;
    color: #fff;
    font-weight: bold;
    margin-bottom: 20rpx;
  }
  .u-medal-box {
    width: 100%;
    height: 100%;
    overflow: scroll;
  }
  .u-medal-item {
    width: 100%;
    height: 180rpx;
    @include flex;
    justify-content: space-between;
    position: relative;
    color: #fff;
    font-size: 30rpx;
    box-sizing: border-box;
    padding: 0 44rpx;
    &:nth-of-type(1n + 1) {
      background: rgba(#7efbe3, 0.2);
    }
    &:nth-of-type(2n + 2) {
      background: rgba(#7efbe3, 0.1);
    }
    .u-item-left {
      @include flex;
      flex-direction: column;
      img {
        width: 96rpx;
        height: 96rpx;
      }
      .u-txt {
        position: relative;
      }
      .pos-txt {
        position: absolute;
        right: -120rpx;
        top: 50%;
        transform: translateY(-50%);
      }
    }
    .u-item-right {
      @include flex;
      justify-content: flex-end;
      .u-goods-item {
        width: 120rpx;
        @include flex;
        flex-direction: column;
        font-size: 24rpx;
        color: #abb4ea;
        margin-right: 30rpx;
        position: relative;
        font-weight: bold;
        & > div {
          @include flex;
          color: #fff;
          div {
            margin-right: 4rpx;
          }
        }
        img {
          width: 60rpx;
          height: 60rpx;
          margin-bottom: 10rpx;
        }
        .book {
          width: 53rpx;
          height: 62rpx;
        }
        &:last-child {
          margin-right: 0;
        }
        .u-num {
          font-size: 30rpx;
          color: #fff;
          position: absolute;
          right: 10rpx;
          top: 40rpx;
        }
      }
      .isEnerge {
        img {
          transform: rotate(30deg);
        }
      }
    }
  }
}

.u-rank-rule,
.u-score-rule {
  width: 650rpx;
  height: 850rpx;
  margin: 0 auto;
  background: rgba(#7efbe3, 0.2);
  padding: 48rpx 20rpx 0 48rpx;
  box-sizing: border-box;
  color: #29aae1;
  font-size: 30rpx;
  font-weight: bold;
  opacity: 0;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: 200rpx;
}
.u-fff {
  color: #fff;
  display: inline;
}
.u-rule {
  line-height: 1.2em;
  margin-bottom: 35rpx;
  &:last-child {
    margin-bottom: 0;
  }
}
.u-white {
  color: #fff;
  font-size: 26rpx;
  padding-left: 40rpx;
  box-sizing: border-box;
  margin-bottom: 10rpx;
}
.u-score-rule {
  padding-left: 20rpx;
  padding-right: 0;
  font-size: 26rpx;
  .u-rule {
    margin-bottom: 24;
  }
}

.u-table {
  margin: 0 auto;
  @include flex;
  flex-direction: column;
  justify-content: flex-start;
  width: 392rpx;
  height: 244rpx;
  border: 2rpx solid #9a9eb6;
  border-bottom: none;
  box-sizing: border-box;
  color: #fff;
  font-size: 28rpx;
  .u-tr {
    @include flex;
    width: 100%;
    height: 48rpx;
    .u-td {
      width: 50%;
      height: 100%;
      @include flex;
      border-right: 2rpx solid #9a9eb6;
      border-bottom: 2rpx solid #9a9eb6;
      &:last-child {
        border-right: none;
      }
    }
  }
}
.mb24 {
  margin-bottom: 24rpx !important;
}

.show {
  opacity: 1;
}
.u-energe {
  position: absolute;
  top: 50rpx;
  left: 80rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 130rpx;
  height: 44rpx;
  font-size: 24rpx;
  color: #fff;
  background: #191644;
  border-radius: 1000rpx;
  img {
    width: 63rpx;
    height: 83rpx;
    position: absolute;
    left: -30rpx;
    top: 50%;
    transform: translateY(-50%);
  }
}

// 新改动

.x-box {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 80rpx;
  display: flex;
  align-items: flex-end;
  color: #fff;
  .l-gold {
    margin-left: 60rpx;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 130rpx;
    height: 38rpx;
    font-size: 28rpx;
    border-radius: 1000rpx;
    background: #141139;
    img {
      width: 45rpx;
      height: 45rpx;
      position: absolute;
      left: -29rpx;
      top: 50%;
      transform: translateY(-50%);
    }
  }
  .m-energe {
    right: 40rpx;
    top: 40rpx;
    width: 140rpx;
    height: 38rpx;
    font-size: 28rpx;
    color: #fff;
    background: #141139;
    border-radius: 50rpx;
    img,
    .u-add-energe {
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
    }
    img {
      width: 60rpx;
      height: 80rpx;
      left: -18rpx;
    }
    .u-add-energe {
      right: -20rpx;
      width: 40rpx;
      height: 40rpx;
      background: #ff217b;
      border-radius: 50%;
      div {
        width: 6rpx;
        height: 20rpx;
        background: #fff;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        &:last-child {
          transform: translate(-50%, -50%) rotate(90deg);
        }
      }
    }
    .u-time-box {
      width: 100%;
      position: absolute;
      top: -26rpx;
      left: 50%;
      transform: translateX(-40%);
      text-align: center;
      font-size: 20rpx;
      color: #fff;
      .u-time {
        display: inline-block;
        color: #a69ee3;
        margin-right: 4rpx;
      }
    }
  }
}

// 重要 排位框
.m-wrap {
  position: relative;
}
.rank-t {
  top: -100rpx;
  width: 532rpx;
  height: 148rpx;
}
.rank-qa {
  width: 30rpx;
  height: 30rpx;
  top: -30rpx;
  right: 190rpx;
}
.ant-list {
  margin: 100rpx auto 0 auto;
  padding: 34rpx 20rpx 0 20rpx;
  width: 675rpx;
  height: 810rpx;
  border: 10rpx solid #82fbfb;
  border-radius: 20rpx;
  box-sizing: border-box;
  box-shadow: 0 0 20rpx 4rpx rgba(130, 251, 251, 0.58);
}
.r-item {
  margin-top: 18rpx;
  width: 614rpx;
  height: 171rpx;
  .info-box {
    box-sizing: border-box;
    justify-content: flex-start;
    width: 580rpx;
    height: 126rpx;
    .l {
      width: 124rpx;
      height: 124rpx;
      margin-right: 18rpx;
      img {
        width: 100rpx;
        height: 100rpx;
      }
    }
    .md {
      width: 336rpx;
      .md-medal-name {
        width: 134rpx;
        height: 37rpx;
        margin-bottom: 18rpx;
      }
      .star-box {
        justify-content: flex-start;
        height: 46rpx;
        img {
          width: 30rpx;
          height: 30rpx;
          margin-right: 4rpx;
        }
        .star-bg {
          height: 30rpx;
          justify-content: flex-start;
        }
        .star-over {
          left: 0;
          img {
            opacity: 0;
          }
          .star-show {
            opacity: 1;
          }
          .star-ant {
            animation: showStar 0.4s linear forwards;
          }
          @keyframes showStar {
            0% {
              opacity: 0;
              transform: scale(0);
            }
            50% {
              opacity: 1;
              transform: scale(1.2);
            }
            100% {
              opacity: 1;
              transform: scale(1);
            }
          }
        }
      }
    }
    .r {
      width: 106rpx;
      height: 106rpx;
      .r-gift {
        width: 80rpx;
        height: 80rpx;
      }
      .r-getted {
        width: 73rpx;
        height: 60rpx;
      }
    }
  }
  .item-bg {
    width: 100%;
    height: 100%;
  }
}
.task-type,
.top-type {
  height: 360rpx;
  position: relative;
  .item-bg-task {
    width: 100%;
    height: 324rpx;
    position: absolute;
    left: 0;
    bottom: 0;
  }
  .task-box {
    margin-top: 50rpx;
    width: 584rpx;
    height: 215rpx;
  }
  .task-over-ant {
    position: absolute;
    top: 40rpx;
    width: 516rpx;
    height: 132rpx;
    opacity: 0;
    background-size: 100%;
    background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/task-over.png");
    img {
      position: absolute;
      left: 106rpx;
      top: 32rpx;
      width: 70rpx;
      height: 70rpx;
    }
  }
  .show-over-ant {
    animation: show-over-ant 0.2s ease forwards;
  }
  @keyframes show-over-ant {
    0% {
      opacity: 1;
      transform: translate(-50%, 0) scale(1.2);
    }
    100% {
      opacity: 1;
      transform: translate(-50%, 0) scale(1);
    }
  }
  .hide-task-info {
    animation: hide-over-ant 0.2s ease forwards;
  }
  @keyframes hide-over-ant {
    0% {
      opacity: 1;
      transform: translate(-50%, -50%) scale(1);
    }
    100% {
      opacity: 0;
      transform: translate(-50%, -50%) scale(0.9);
    }
  }
  .task-list {
    top: 30rpx;
    width: 260rpx;
    height: 100rpx;
    justify-content: space-between;
  }
  .w350 {
    width: 350rpx;
  }
  .w520 {
    width: 520rpx;
  }
  .t-box {
    width: 44rpx;
    height: 44rpx;
    border: 3rpx solid #7367f3;
    background: #262069;
    border-radius: 50%;
  }
  .t-line {
    width: 100%;
    height: 12rpx;
    border: 3rpx solid #7367f3;
    background: #262069;
    box-sizing: border-box;
  }
  .t-icon,
  .t-icon-box {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background: #262069;
    z-index: 2;
  }
  .t-icon-over {
    &::after {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      border-radius: 50%;
      content: "";
      background: #9efcfc;
      width: 24rpx;
      height: 24rpx;
    }
  }
  .t-icon-fail {
    &::after {
      position: absolute;
      left: 50%;
      top: 50%;
      border-radius: 4rpx;
      content: "";
      background: #ffea3c;
      width: 6rpx;
      height: 24rpx;
      transform: translate(-50%, -50%) rotate(-45deg);
    }

    &::before {
      position: absolute;
      left: 50%;
      top: 50%;
      border-radius: 4rpx;
      content: "";
      background: #ffea3c;
      width: 6rpx;
      height: 24rpx;
      transform: translate(-50%, -50%) rotate(45deg);
    }
  }
  .t-arrow {
    top: -20rpx;
    width: 33rpx;
    height: 23rpx;
    z-index: 2;
    animation: float 1s ease infinite;
  }
  @keyframes float {
    0%,
    100% {
      transform: translate(-50%, 0);
    }
    50% {
      transform: translate(-50%, 10rpx);
    }
  }

  .t-desc {
    width: 490rpx;
    justify-content: space-between;
    bottom: 36rpx;
    font-size: 26rpx;
    color: #fff;
    font-weight: bold;
    & > div {
      div {
        color: #ffea3c;
      }
      img {
        width: 70rpx;
        height: 70rpx;
      }
    }
  }
  .task-head {
    width: 215rpx;
    height: 152rpx;
    top: 0;
    .head-medal {
      width: 140rpx;
      height: 140rpx;
    }
    .head-name-img {
      width: 167rpx;
      height: 46rpx;
      bottom: -2rpx;
    }
  }
}
.top-area {
  font-size: 26rpx;
  font-weight: bold;
  color: #fff;
  flex-direction: column;
  .star-img {
    width: 30rpx;
    height: 30rpx;
  }
}
.star-num {
  min-width: 130rpx;
  height: 40rpx;
  border-radius: 1000rpx;
  box-shadow: 0 0 10rpx 5rpx rgba(#fff, 0.5);
  margin-bottom: 30rpx;
  background: #1f1a5a;
  img {
    margin-right: 10rpx;
  }
}
.star-line-box {
  position: relative;
  width: 294rpx;
  height: 12rpx;
  background: #262069;
}
.star-line {
  left: 0;
  background: #82fbfb;
  height: 4rpx;
  width: 0;
  transition: all 0.2s linear;
}
.line-gold {
  width: 210rpx;
  justify-content: space-between;
  .gold-item {
    width: 16rpx;
    height: 16rpx;
    background: #fff100;
    img {
      width: 25rpx;
      height: 27rpx;
      bottom: -30rpx;
    }
  }
  .gold-item-getted {
    background: #fff;
    img {
      opacity: 0;
    }
  }
}

.line-start {
  left: -40rpx;
  width: 40rpx;
  height: 40rpx;
  background: #262069;
  border-radius: 50%;
  .line-text {
    bottom: -30rpx;
  }
}
.line-end {
  right: -45rpx;
  top: -20%;
  img {
    width: 55rpx;
    height: 52rpx;
  }
  .line-text {
    bottom: -26rpx;
  }
}

.ant-unlock-1 {
  animation: antUnlock-1 0.2s ease forwards;
}
.ant-unlock-2 {
  animation: antUnlock-2 0.2s ease forwards;
}
.trans {
  animation: trans 1s linear forwards;
}
@keyframes trans {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-1000rpx);
  }
}
@keyframes antUnlock-1 {
  0% {
    opacity: 1;
    transform: scale(1);
  }
  100% {
    opacity: 0;
    transform: scale(0.9);
  }
}
@keyframes antUnlock-2 {
  0% {
    opacity: 1;
    transform: scale(1.2);
  }
  100% {
    opacity: 1;
    transform: scale(1);
  }
}

.g-btn {
  display: block;
  width: 380rpx;
  height: 119rpx;
  margin: 26rpx auto 0 auto;
}
#lock {
  opacity: 0;
}
.show-lock {
  opacity: 1 !important;
}

.hide {
  opacity: 0 !important;
}
.mt30 {
  margin-top: 30rpx;
}
</style>

<template>
  <div class="m-box sky-bg-2">
    <div class="x-box">
      <div class="l-gold">
        <img src="/static/icon/gold.png"> {{appInfo.money}}
      </div>
      <div class="m-energe flex pos-a"
           @click="showEnerge=true">
        <!-- <div class="u-time-box"
               v-if="strengthCount<maxStrength">
            <div class="u-time">{{formatTime}}</div>
            +1
          </div> -->
        <img src="/static/entry/energe.png"> {{strengthCount||0}}/{{maxStrength}}
        <div class="u-add-energe">
          <div></div>
          <div></div>
        </div>
      </div>
    </div>
    <!-- 段位列表框 -->
    <!-- <div class="ant-list">
      
    </div> -->
    <div class="m-wrap">
      <img class="pos-a rank-t left-center"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/rank-t.png">
      <img class="pos-a rank-qa"
           @click="showIntro=true"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/rankQA.png">
      <scroll-view class="ant-list"
                   :scroll-y="true"
                   :scroll-into-view="viewId"
                   scroll-with-animation="true">
        <div class="r-item pos-r"
             :class="{'ant-unlock-1':item.isAntHideStep,
                      'ant-unlock-2':item.isAntShowStep,
                      'task-type':item.isTask,
                      'top-type':item.level>=9&&!item.isLock,
                      'hide':index<currentMedal&&hideItem,
                      'trans':showTrans&&index<=currentMedal}"
             v-for="(item,index) in medalList"
             :key="index"
             :id="item.id">
          <div class="info-box pos-a db-center flex"
               v-if="!item.isLock&&!item.isTask&&item.level<9">
            <div class="l flex">
              <img :src="IMGHEAD+item.paragraphImage">
            </div>
            <div class="md">
              <img class="md-medal-name"
                   :src="item.medalNameImg">
              <div class="star-box flex pos-r">
                <div class="star-bg flex">
                  <img class="star"
                       src="/static/star-black.png"
                       v-for="(starBg,bgIndex) in item.maxStar"
                       :key="bgIndex">
                </div>
                <div class="star-over flex pos-a top-center"
                     v-if="item.starList&&item.starList.length>0">
                  <img class="star-act"
                       :class="{'star-show':starAct.isStatic,'star-ant':starAct.isAnt}"
                       src="/static/star.png"
                       v-for="(starAct,actIndex) in item.starList"
                       :key="actIndex">
                </div>
              </div>
            </div>
            <div class="r flex">
              <img v-if="item.isReceive==2"
                   class="r-getted"
                   src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/getted.png">
              <img v-if="(item.isReceive==0||item.isReceive==1)&&item.goodType>0"
                   class="r-gift"
                   :src="goodsMap[item.goodType].url">
              <!-- <img src=""> -->
            </div>
          </div>

          <img v-if="!item.isLock&&!item.isTask&&item.level<9"
               class="item-bg"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/rank-unLock.png">
          <img v-if="item.isLock&&!item.isTask&&item.level<=9"
               class="item-bg"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/rank-lock.png">
          <img v-if="item.isTask||(item.level>=9&&!item.isLock)"
               class="item-bg-task"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/rank-unLock-big.png">

          <div class="task-box pos-a db-center"
               v-if="item.isTask||(item.level>=9&&!item.isLock)">
            <img class="w100 h100"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/rank-big-inner.png">
            <!-- 试炼区域 -->
            <div class="task-box-inner pos-a w100 h100 db-center"
                 :class="{'hide-task-info':taskOverStepHide&&index==currentMedal,}">
              <div class="task-list pos-a left-center flex"
                   :class="{'w350':taskInfo.total==7,'w520':taskInfo.total==10,'hide':(item.level>=9&&!isRefine)||(isRefine&&item.isReceive==1)}">
                <div class="t-box pos-r"
                     v-for="(taskItem,t) in taskList"
                     :key="t">
                  <div class="t-icon-box pos-a db-center">
                    <div class="t-icon pos-a db-center"
                         :class="{'t-icon-over':taskItem.status==1,'t-icon-fail':taskItem.status==0}"></div>
                  </div>
                  <img v-if="isRefine&&medalList[currentMedal].totalNum==t"
                       class="t-arrow pos-a left-center"
                       src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/arrow.png">
                </div>
                <div class="t-line pos db-center"></div>
              </div>
              <div class="t-desc flex pos-a left-center"
                   :class="{'hide':(item.level>=9&&!isRefine)||(isRefine&&item.isReceive==1)}">
                <div class="flex">通关条件：
                  <div>{{taskInfo.total}}局{{taskInfo.need}}胜</div>
                </div>
                <div class="flex">试炼奖励：<img :src="goodsMap[taskInfo.goodType].url"></div>
              </div>
            </div>
            <div class="task-over-ant left-center"
                 :class="{'show-over-ant':taskOverStepShow&&index==currentMedal||(isRefine&&item.isReceive==1)}">
              <img :src="goodsMap[taskInfo.goodType].url">
            </div>
            <!-- 王者升星区域 -->
            <div class="top-area pos-a db-center w100 h100 flex"
                 :class="{'hide':item.level<9||isRefine}">
              <div class="star-num flex">
                <img class="star-img"
                     src="/static/star.png"> {{item.star}}
              </div>
              <div class="star-line-box">
                <div class="star-line pos-a top-center"
                     :style="topInfo.style"></div>
                <div class="line-gold pos-a db-center flex ">
                  <div class="gold-item border-50 pos-r"
                       :class="{'gold-item-getted':!topInfo.hasGoldToGet&&topInfo.getIndex>=1}">
                    <img class="pos-a left-center"
                         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/gold.png">
                  </div>
                  <div class="gold-item border-50 pos-r"
                       :class="{'gold-item-getted':!topInfo.hasGoldToGet&&topInfo.getIndex>=2}">
                    <img class="pos-a left-center"
                         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/gold.png">
                  </div>
                  <div class="gold-item border-50 pos-r"
                       :class="{'gold-item-getted':!topInfo.hasGoldToGet&&topInfo.getIndex>=3}">
                    <img class="pos-a left-center"
                         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/gold.png">
                  </div>
                  <div class="gold-item border-50 pos-r"
                       :class="{'gold-item-getted':!topInfo.hasGoldToGet&&topInfo.getIndex>=4}">
                    <img class="pos-a left-center"
                         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/gold.png">
                  </div>
                </div>
                <div class="line-start pos-a top-center border-50 flex">
                  <img class="star-img"
                       src="/static/star.png">
                  <div class="line-text pos-a left-center">{{topInfo.start}}</div>
                </div>
                <div class="line-end pos-a top-center">
                  <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/task-icon.png">
                  <div class="line-text pos-a left-center">{{topInfo.end}}</div>
                </div>
              </div>
            </div>

          </div>
          <div class="task-head pos-a left-center"
               v-if="item.isTask||(item.level>=9&&!item.isLock)">
            <img class="w100 h100"
                 v-if="item.level<9"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/medal-bg-1.png">
            <img class="w100 h100"
                 v-if="item.level>=9"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/medal-bg-2.png">
            <img class="head-medal pos-a db-center"
                 :src="IMGHEAD+item.paragraphImage">
            <img class="head-name-img pos-a left-center"
                 v-if="item.level==4"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/task-g.png">
            <img class="head-name-img pos-a left-center"
                 v-if="item.level==7"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/task-d.png">
            <img class="head-name-img pos-a left-center"
                 v-if="item.level>=9&&isRefine"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/task-k.png">
            <img class="head-name-img pos-a left-center"
                 v-if="item.level>=9&&!isRefine"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/top.png">
          </div>
        </div>
      </scroll-view>
      <img class="g-btn"
           v-if="showGetBtn"
           @click="throttle(getGiftFn,200)"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/get-btn.png">
      <img class="g-btn"
           v-else
           @click="throttle(rankStart,200)"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/preGame/start-btn.png">

    </div>

    <!-- 段位介绍 -->
    <div class="m-intro"
         v-if="showIntro"
         @click="showIntro=false">
      <div class="u-head">
        <div v-for="(item,index) in introList"
             :key="index"
             :class="{'t-act':selectIndex==index}"
             @click.stop="select(index)">{{item}}</div>
      </div>
      <div class="u-medal-list"
           :class="{show:selectIndex==0}">
        <div class="u-medal-head">
          <div>段位名称</div>
          <div>赛季奖励</div>
        </div>
        <div class="u-medal-box">
          <div class="u-medal-item">
            <div class="u-item-left">
              <img :src="IMGHEAD+giftRankList[0].paragraphImage">
              <div class="u-txt">
                最强王者
                <div class="pos-txt">(前五名)</div>
              </div>
            </div>
            <div class="u-item-right">
              <div class="u-goods-item "
                   v-for="(good,gIndex) in giftList[0]"
                   :key="gIndex">
                <img class="book"
                     :src="goodsMap[good.type].url">
                <div>
                  {{goodsMap[good.type].name}}
                </div>
              </div>
            </div>
          </div>
          <div class="u-medal-item">
            <div class="u-item-left">
              <img :src="IMGHEAD+giftRankList[0].paragraphImage">
              <div class="u-txt">
                最强王者
                <div class="pos-txt">(2000星)</div>
              </div>
            </div>
            <div class="u-item-right">
              <div class="u-goods-item"
                   v-for="(good,gIndex) in giftList[1]"
                   :key="gIndex">
                <img :src="goodsMap[good.type].url">
                <div>
                  <div>{{good.num}}</div>
                  {{goodsMap[good.type].name}}
                </div>
              </div>
            </div>
          </div>
          <div class="u-medal-item">
            <div class="u-item-left">
              <img :src="IMGHEAD+giftRankList[0].paragraphImage">
              <div class="u-txt">
                最强王者
                <div class="pos-txt">(1500星)</div>
              </div>
            </div>
            <div class="u-item-right">
              <div class="u-goods-item"
                   v-for="(good,gIndex) in giftList[2]"
                   :key="gIndex">
                <img :src="goodsMap[good.type].url">
                <div>
                  <div>{{good.num}}</div>
                  {{goodsMap[good.type].name}}
                </div>

              </div>
            </div>
          </div>
          <div class="u-medal-item">
            <div class="u-item-left">
              <img :src="IMGHEAD+giftRankList[0].paragraphImage">
              <div class="u-txt">
                最强王者
                <div class="pos-txt">(1000星)</div>
              </div>
            </div>
            <div class="u-item-right">
              <div class="u-goods-item"
                   v-for="(good,gIndex) in giftList[3]"
                   :key="gIndex">
                <img :src="goodsMap[good.type].url">
                <div>
                  <div>{{good.num}}</div>
                  {{goodsMap[good.type].name}}
                </div>

              </div>
            </div>
          </div>
          <div class="u-medal-item">
            <div class="u-item-left">
              <img :src="IMGHEAD+giftRankList[0].paragraphImage">
              <div class="u-txt">
                最强王者
                <div class="pos-txt">(500星)</div>
              </div>
            </div>
            <div class="u-item-right">
              <div class="u-goods-item"
                   v-for="(good,gIndex) in giftList[4]"
                   :key="gIndex">
                <img :src="goodsMap[good.type].url">
                <div>
                  <div>{{good.num}}</div>
                  {{goodsMap[good.type].name}}
                </div>
              </div>
            </div>
          </div>

          <div class="u-medal-item"
               v-for="(item,index) in giftRankList"
               :key="index">
            <div class="u-item-left">
              <img :src="IMGHEAD+item.paragraphImage"> {{item.paragraphName}}
            </div>
            <div class="u-item-right">
              <div class="u-goods-item"
                   v-for="(good,gIndex) in giftList[index+5]"
                   :key="gIndex">
                <img :src="goodsMap[good.type].url">
                <div>
                  <div>{{good.num}}</div>
                  {{goodsMap[good.type].name}}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 赛季规则 -->
      <div class="u-rank-rule"
           :class="{show:selectIndex==1}">
        <div class="u-rule">
          1、赛季：<div class="u-fff">一个月</div>
        </div>
        <div class="u-rule">
          2、段位：
        </div>
        <div class="u-rule">
          <div class="u-fff">
            共有坚韧黑铁、英勇青铜、不屈白银、荣耀黄金、华贵白金、深沉黑金、璀璨钻石、超凡大师、最强王者等9大段位。
          </div>
        </div>
        <div class="u-rule">
          3、试炼：
        </div>
        <div class="u-rule">
          <div class="u-fff">黄金试炼：5局3胜</div> </br>
          <div class="u-fff">钻石试炼：7局4胜</div></br>
          <div class="u-fff">王者试炼：10局6胜</div></br>
          <div class="u-fff">王者整50星试炼：5局3胜</div></br>
          <div class="u-fff">王者整100星试炼：10局6胜</div>
        </div>
        <div class="u-rule">
          4、保护制：
        </div>
        <div class="u-rule">
          <div class="u-fff">当晋升一个段位，或通过一个试炼，本赛季内将不会再掉星回之前的段位</div>
        </div>
      </div>
      <!-- 得分规则 -->
      <div class="u-score-rule"
           :class="{show:selectIndex==2}">
        <div class="u-rule">1、获胜规则：5题答完,分数高者胜</div>
        <div class="u-rule">2、每题有10秒钟思考时间,超时未回答为0分</div>
        <div class="u-rule">3、每道题分数包括两部分,正确分,时间分和组合分</div>
        <div class="u-white">①:正确分:答对+100分,答错不加分</div>
        <div class="u-white">②:时间分:答对了才有时间分,时间分=剩余秒数*10</div>
        <!-- <div class="u-white mb24">③:组合分:连续答对有额外加分</div> -->
        <div class="u-rule">4、最后一题得分双倍,
          <div class="u-fff">不包括组合分</div>
        </div>
        <div class="u-rule">5、知识书加成,规则如下</div>
        <div class="u-table">
          <div class="u-tr"
               v-for="(item,index) in scoreList "
               :key="index">
            <div class="u-td">{{item[0]}}</div>
            <div class="u-td">{{item[1]}}</div>
          </div>
        </div>
      </div>
    </div>
    <energe-modal v-if="showEnerge"
                  @close="showEnerge=false"></energe-modal>

    <get-gift v-if="showGetGift"
              :giftList="showGiftList"
              @close="showGetGift=false"></get-gift>

  </div>
</template>

<script>
import getGift from "@/components/gift/get-gift";
import { getRondom,throttle } from "@/utils/index";
import { shareInfo } from "@/config/dataFile";
import { mapState, mapMutations } from "vuex";
import energeModal from "@/components/modal/energeModal";

let dataStack = [];
export default {
  onShareAppMessage: function(res) {
    let index = getRondom(0, 2);
    return {
      title: shareInfo[index].title,
      imageUrl: shareInfo[index].imgUrl,
      path: `/pages/entry/index?userId=${this.appInfo.userId}`
    };
  },
  components: {
    "energe-modal": energeModal,
    "get-gift": getGift
  },
  data() {
    return {
      throttle:throttle,
      showGiftList: [],
      showGetGift: false,
      showEnerge: false,
      top: false,
      selectIndex: 0,
      needStar: 0,
      dataReady: false,
      introList: ["段位", "赛季", "得分"],
      scoreList: [
        ["知识书", "加分"],
        ["等级1", "+0.5%"],
        ["等级10", "+5%"],
        ["等级20", "+10%"],
        ["等级30", "+15%"]
      ],
      giftList: [
        [{ type: 8, num: 1 }],
        [{ type: 7, num: 3000 }], //2000 star
        [{ type: 7, num: 2500 }],
        [{ type: 7, num: 2000 }],
        [{ type: 7, num: 1500 }],
        [{ type: 7, num: 1000 }],
        [{ type: 7, num: 700 }],
        [{ type: 7, num: 500 }],
        [{ type: 7, num: 300 }],
        [{ type: 7, num: 200 }],
        [{ type: 7, num: 100 }],
        [{ type: 7, num: 50 }],
        [{ type: 7, num: 30 }],
        [{ type: 7, num: 10 }]
      ],
      goodsMap: {
        1: {
          url: "/static/icon/s-eng.png",
          name: "小体力瓶"
        },
        2: {
          url: "/static/icon/m-eng.png",
          name: "中体力瓶"
        },
        3: {
          url: "/static/icon/b-eng.png",
          name: "大体力瓶"
        },
        4: {
          url: "/static/icon/s-box.png",
          name: "小宝箱"
        },
        5: {
          url: "/static/icon/m-box.png",
          name: "中宝箱"
        },
        6: {
          url: "/static/icon/b-box.png",
          name: "大宝箱"
        },
        7: {
          url: "/static/icon/gold.png",
          name: "金币"
        },
        8: {
          url: "/static/icon/book.png",
          name: "实体书"
        }
      },
      info: {
        index: 1,
        paragraphImage: "",
        paragraphName: "不屈白银Ⅳ",
        seasonTime: "2018-10",
        star: 0
      },
      showGetBtn: false,
      showIntro: false,
      isRefine: false, //是否是试炼
      viewId: "",
      isScroll: true,
      showLock: false,
      showTrans: false,
      hideItem: false,
      fullStar: false,
      currentMedal: 0,
      taskOverStepHide: false,
      taskOverStepShow: false,
      topInfo: {
        style: "",
        receiveStar: 0,
        start: 0,
        end: 0,
        hasGoldToGet: false,
        getIndex: 0
      },
      medalList: [],
      indexMap: {
        8: 0,
        7: 1,
        6: 2,
        5: 3,
        4: 4,
        3: 5,
        2: 6,
        1: 7,
        0: 8
      },
      giftRankList:[],
      mockList: [
        {
          level: 1,
          star: 1,
          maxStar: 1,
          paragraphName: "坚韧黑铁",
          paragraphImage:
            "/vientianeTestParagraphImage/d718ef83-5955-4a8c-8813-d81c65bc1ee6.jpg",
          isReceive: 2,
          goodType: 4,
          goodNum: 1,
          totalNum: 0,
          winNum: 0,
          maxTotalNum: 0,
          maxWinNum: 0
        },
        {
          level: 2,
          star: 0,
          maxStar: 2,
          paragraphName: "英勇黄铜",
          paragraphImage:
            "/vientianeTestParagraphImage/d718ef83-5955-4a8c-8813-d81c65bc1ee6.jpg",
          isReceive: 2,
          goodType: 4,
          goodNum: 1,
          totalNum: 0,
          winNum: 0,
          maxTotalNum: 0,
          maxWinNum: 0
        },
        {
          level: 3,
          star: 0,
          maxStar: 3,
          paragraphName: "不屈白银",
          paragraphImage:
            "/vientianeTestParagraphImage/d718ef83-5955-4a8c-8813-d81c65bc1ee6.jpg",
          isReceive: 2,
          goodType: 4,
          goodNum: 1,
          totalNum: 0,
          winNum: 0,
          maxTotalNum: 0,
          maxWinNum: 0
        },
        {
          level: 4,
          star: 0,
          maxStar: 4,
          paragraphName: "XX黄金",
          paragraphImage:
            "/vientianeTestParagraphImage/d718ef83-5955-4a8c-8813-d81c65bc1ee6.jpg",
          isReceive: 2,
          isRefine: 1,
          goodType: 4,
          goodNum: 1,
          play: "1,0,1,1,",
          totalNum: 5,
          winNum: 0,
          maxTotalNum: 5,
          maxWinNum: 0
        },
        {
          level: 5,
          star: 0,
          maxStar: 5,
          paragraphName: "XX白金",
          paragraphImage:
            "/vientianeTestParagraphImage/d718ef83-5955-4a8c-8813-d81c65bc1ee6.jpg",
          isReceive: 2,
          goodType: 4,
          goodNum: 1,
          totalNum: 0,
          winNum: 0,
          maxTotalNum: 0,
          maxWinNum: 0
        },
        {
          level: 5,
          star: 0,
          maxStar: 5,
          paragraphName: "XX黑金",
          paragraphImage:
            "/vientianeTestParagraphImage/d718ef83-5955-4a8c-8813-d81c65bc1ee6.jpg",
          isReceive: 2,
          goodType: 4,
          goodNum: 1,
          totalNum: 0,
          winNum: 0,
          maxTotalNum: 0,
          maxWinNum: 0
        },
        {
          level: 5,
          star: 0,
          maxStar: 5,
          paragraphName: "XX钻石",
          paragraphImage:
            "/vientianeTestParagraphImage/d718ef83-5955-4a8c-8813-d81c65bc1ee6.jpg",
          isReceive: 2,
          goodType: 4,
          play: "",
          goodNum: 1,
          totalNum: 0,
          winNum: 0,
          maxTotalNum: 7,
          maxWinNum: 0
        },
        {
          level: 5,
          star: 0,
          maxStar: 5,
          paragraphName: "XX大师",
          paragraphImage:
            "/vientianeTestParagraphImage/d718ef83-5955-4a8c-8813-d81c65bc1ee6.jpg",
          isReceive: 2,
          goodType: 4,
          goodNum: 1,
          totalNum: 0,
          winNum: 0,
          maxTotalNum: 0,
          maxWinNum: 0
        },
        {
          level: 9,
          isRefine: 0,
          star: 10,
          receiveStar: 0,
          maxStar: 5,
          paragraphName: "XX王者",
          paragraphImage:
            "/vientianeTestParagraphImage/d718ef83-5955-4a8c-8813-d81c65bc1ee6.jpg",
          isReceive: 0,
          goodType: 4,
          play: "",
          goodNum: 1,
          totalNum: 0,
          winNum: 0,
          maxTotalNum: 10,
          maxWinNum: 0
        }
      ],
      taskList: [
        { status: 1 },
        { status: 0 },
        { status: -1 },
        { status: -1 },
        { status: -1 }
      ]
    };
  },
  computed: {
    ...mapState([
      "IMGHEAD",
      "appInfo",
      "deviceInfo",
      "strengthCount",
      "maxStrength",
      "gameResult"
    ]),
    taskInfo() {
      let obj = {
        total: 5,
        need: 3,
        goodType: 5,
        num: 1
      };
      if (this.currentMedal == 2) {
        obj = {
          total: 7,
          need: 4,
          goodType: 6,
          num: 1
        };
      } else if (
        this.medalList.length > 0 &&
        this.currentMedal == 0 &&
        this.medalList[0].star == 0
      ) {
        obj = {
          total: 10,
          need: 6,
          goodType: 6,
          num: 3
        };
      } else if (
        this.medalList.length > 0 &&
        this.currentMedal == 0 &&
        this.medalList[0].star >= 50
      ) {
        if ((this.medalList[0].star / 50) % 2 == 0) {
          obj = {
            total: 10,
            need: 6,
            goodType: 6,
            num: 1
          };
        } else {
          obj = {
            total: 5,
            need: 3,
            goodType: 5,
            num: 1
          };
        }
      }
      return obj;
    },
    season() {
      if (this.appInfo.seasonTime) {
        return {
          year: this.appInfo.seasonTime.split("-")[0],
          month: this.appInfo.seasonTime.split("-")[1]
        };
      }
    }
  },
  methods: {
    ...mapMutations(["setStrengthCount", "setGameResult", "setAppInfo"]),
    antTest() {
      this.viewId = "last";
      this.isScroll = false;
      this.showLock = false;
    },
    showGiftFn(type, num) {
      this.showGiftList = [
        {
          name: this.goodsMap[type].name,
          url: this.goodsMap[type].url,
          num: num
        }
      ];
      this.showGetGift = true;
    },
    antShow() {
      this.showLock = true;
      this.isScroll = true;
      setTimeout(() => {
        this.viewId = "lock";
      }, 0);
    },
    initTaskList(index, type) {
      let l1 = 3,
        l2 = 6,
        l3 = 8;
      if (type == 2) {
        l1 = 0;
        l2 = 2;
        l3 = 5;
      }
      if (index == l1 || index == l2 || index == l3) {
        // 初始化试炼任务列表
        let list = this.medalList[index];
        let record = list.play.split(",") || [];
        let arr = [];
        for (let a = 0; a < record.length; a++) {
          if (record[a] != "," && record[a] != "") {
            arr.push({ status: record[a] });
          }
        }
        let x = arr.length;
        for (let i = 0; i < list.maxTotalNum - x; i++) {
          arr.push({ status: -1 });
        }
        this.taskList = arr;
      }
    },
    initMedalList(list) {
      /*  重新处理列表，新增显示状态属性
          所有数组内元素新增 属性 isLock=true
          判断当前段位 属性isLock=false
          设置id, 作为scroll to view 的索引 id 以m开头 i为数字
          
       */
      try {
        for (let i = 0; i < list.length; i++) {
          let starArr = [];
          for (let s = 0; s < list[i].maxStar; s++) {
            if (s < list[i].star) {
              starArr.push({ isStatic: true, isAnt: false });
            } else {
              starArr.push({ isStatic: false, isAnt: false });
            }
          }
          let obj = {
            id: `m${i}`,
            isLock: true,
            isAntHideStep: false,
            isAntShowStep: false,
            isTask: false,
            starList: starArr,
            medalNameImg: `${
              this.IMGHEAD
            }/vientianeTestMobileAsset/images/preGame/medal-${i}.png`
          };
          obj = Object.assign(list[i], obj);
          this.$set(this.medalList, i, obj);
        }
        for (let j = 0; j < this.medalList.length; j++) {
          if (this.medalList[j].isReceive == 2) {
            this.medalList[j].isLock = false;
          }
          if (
            this.medalList[j].isReceive == 0 ||
            this.medalList[j].isReceive == 1
          ) {
            this.medalList[j].isLock = false;
            this.currentMedal = this.indexMap[j];
            if (this.medalList[j].isReceive == 1) {
              this.showGetBtn = true;
              console.log("line--1582");
            }
            //  判断是不是王者获取王者信息 提取 initTopInfo
            if (this.currentMedal == 0) {
              this.initTopInfo();
            }
            //  判断是不是 试炼
            if (
              this.medalList[this.indexMap[this.currentMedal]].isRefine == 1
            ) {
              this.isRefine = true;
              this.medalList[this.indexMap[this.currentMedal]].isTask = true;
              if (this.currentMedal == 0) {
                if ((this.medalList[8].star / 50) % 2 == 0) {
                  this.medalList[8].maxTotalNum = 10;
                  this.medalList[8].maxWinNum = 6;
                  this.medalList[8].isRefine = 1;
                } else {
                  this.medalList[8].maxTotalNum = 5;
                  this.medalList[8].maxWinNum = 3;
                  this.medalList[8].isRefine = 1;
                }
              }
              this.initTaskList(this.indexMap[this.currentMedal], 1); //初始化试炼进程列表
              // this.setObj(this.medalList,this.indexMap[this.currentMedal],{isTask:true,});
            }
            break;
          }
        }
        this.medalList.reverse();
      } catch (err) {
        console.log(err);
      }
    },
    initTopInfo(type) {
      let index = 8;
      if (type == 2) {
        index = 0;
      }
      let receiveStar = this.medalList[index].receiveStar;
      let starNum = this.medalList[index].star;
      let start = Math.floor(starNum / 50) * 50;
      let end = start + 50;
      let style = `width:${(starNum - start) / 50 * 100}%;`;
      let c = Math.floor((starNum - start) / 10);
      let l = Math.floor((receiveStar - start) / 10);
      let getIndex = l; //当前获取的阶段奖励
      let hasGoldToGet = false;
      if (c > l && c <= 4) {
        hasGoldToGet = true;
        console.log("L1655");
        this.medalList[index].receiveStar = starNum;
        this.showGetBtn = true;
        // if(this.medalList[index]==0){
        //   this.showGetBtn=true;
        //  console.log('line--1634')
        // }
      }
      this.topInfo = { style, receiveStar, start, end, hasGoldToGet, getIndex };
    },
    initScrollTop() {
      if (this.indexMap[this.currentMedal] <= 3) {
        this.viewId = `m3`; //
      } else {
        if (this.isRefine) {
          this.viewId = `m${this.indexMap[this.currentMedal]}`;
        } else {
          this.viewId = `m${this.indexMap[this.currentMedal - 1]}`;
        }
      }
      setTimeout(() => {
        this.isScroll = false; //初始后禁止滚动
      }, 1000);
    },
    // 动画方法
    antShowStar(type) {
      //type ='add' 升星  else 降星
      /*  当前显示段位 升星
        一次只升一个星
       */
      return new Promise((resolve, reject) => {
        let list = this.medalList;
        let current = this.currentMedal;
        console.log("11");
        for (let i = 0; i < list[current].starList.length; i++) {
          console.log(type);
          if (type == "add") {
            //加星
            if (!list[current].starList[i].isStatic) {
              let st = list[current].star + 1;
              list[current].starList[i].isAnt = true;
              list[current].starList[i].isStatic = true;
              list[current].star++;

              console.log(
                "执行加星",
                list[current].star,
                list[current].maxStar,
                st
              );

              if (st >= list[current].maxStar) {
                this.fullStar = true;
                resolve(this.fullStar);
              } else {
                this.fullStar = false;
                reject();
              }
              break;
            }
          } else {
            if (!list[current].starList[i].isStatic && i != 0) {
              list[current].starList[i - 1].isAnt = false;
              list[current].starList[i - 1].isStatic = false;
              list[current].star--;
              break;
            } else if (i == list[current].starList.length - 1) {
              list[current].starList[i].isAnt = false;
              list[current].starList[i].isStatic = false;
              list[current].star--;
            }
          }
        }
      });
    },
    antUnlockMedal() {
      //解锁新段位
      let current = this.currentMedal - 1;
      this.$set(
        this.medalList,
        current,
        Object.assign(this.medalList[current], { isAntUnlock: true })
      );
      this.medalList[current].isAntHideStep = true;
      this.currentMedal -= 1;
      console.log("新单位", this.medalList[this.currentMedal]);
      setTimeout(() => {
        this.$set(
          this.medalList,
          current,
          Object.assign(this.medalList[current], {
            isLock: false,
            isAntShowStep: true,
            isAntHideStep: false
          })
        );
        if (current <= 5) {
          this.isScroll = true;
          this.viewId = `m${this.indexMap[this.currentMedal - 1]}`;
          setTimeout(() => {
            this.isScroll = false;
          }, 500);
        }
      }, 200);
    },
    antShowTask() {
      /* 展示试炼
        隐藏列表以外的 item 
        滚动到下下个item
        下个item 隐藏
        在出现 显示试炼 isTask true
       */
      this.isRefine = true;
      this.isScroll = true;
      this.currentMedal -= 1;
      // this.viewId=`m${this.indexMap[this.currentMedal]}`;
      this.initTaskList(this.currentMedal, 2);
      this.setObj(this.medalList, this.currentMedal, { isAntHideStep: true });
      setTimeout(() => {
        this.isScroll = false;
        this.setObj(this.medalList, this.currentMedal, {
          isTask: true,
          isAntShowStep: true,
          isAntHideStep: false
        });
      }, 200);
    },
    getGiftFn() {
      // 领取奖励
      this.fly.post("rank/receiveGood").then(res => {
        this.setObj(this.medalList, this.currentMedal, { isReceive: 2 });
        if (
          this.currentMedal - 1 == 5 ||
          this.currentMedal - 1 == 2 ||
          this.currentMedal - 1 == 0
        ) {
          // 领取后解锁试炼
          this.showGiftFn(
            this.medalList[this.currentMedal].goodType,
            this.medalList[this.currentMedal].goodNum
          );
          this.isRefine = true;
          this.antShowTask();
        } else if (
          this.isRefine &&
          this.medalList[this.currentMedal].isReceive &&
          this.medalList[this.currentMedal].level < 9
        ) {
          // 王者以下试炼领取奖励
          this.showGiftFn(this.taskInfo.goodType, this.taskInfo.num);
          this.antTaskOver(2);
          this.setObj(this.medalList, this.currentMedal, { isReceive: 0 });
        } else if (
          !this.isRefine &&
          this.medalList[this.currentMedal].level >= 9 &&
          this.topInfo.hasGoldToGet
        ) {
          // 王者 金币点 领奖
          this.topInfo.hasGoldToGet = false;
          this.topInfo.getIndex += 1;
          this.showGiftFn(7, 10);
          // 展示金币获取动效
        } else if (
          this.isRefine &&
          this.medalList[this.currentMedal].level >= 9
        ) {
          // 展示领取动效
          this.showGiftFn(this.taskInfo.goodType, this.taskInfo.num);
          this.isRefine = false;
          this.taskOverStepShow = false;
          this.setObj(this.medalList, this.currentMedal, { isRefine: 0 });
        } else {
          this.antUnlockMedal(); //普通段位领取奖励
          this.showGiftFn(
            this.medalList[this.currentMedal].goodType,
            this.medalList[this.currentMedal].goodNum
          );
        }
        this.showGetBtn = false;
      });
    },
    setObj(obj, key, props) {
      this.$set(obj, key, Object.assign(obj[key], props));
    },
    antTaskOver(step) {
      /* 先展示成功动画 在位移
        istask =false 
        再显示
        TODO 判定是否是王者试炼 ，是的话显示王者信息
       */
      if (step == 1) {
        this.showGetBtn = true;
        console.log("line--1761");
        this.taskOverStepHide = true;
        setTimeout(() => {
          this.taskOverStepShow = true;
        }, 200);
      }

      if (step == 2) {
        if (this.currentMedal < 9) {
          //王者以下试炼成功
          this.showTrans = true;
          setTimeout(() => {
            this.isScroll = true;
            this.setObj(this.medalList, this.currentMedal, {
              isTask: false,
              isAntShowStep: false,
              isAntHideStep: false,
              isLock: false
            });
            this.viewId = `m${this.indexMap[this.currentMedal - 1]}`;
            this.showTrans = false;
            this.taskOverStepHide = false;
            this.taskOverStepShow = false;
          }, 500);
          setTimeout(() => {
            this.isRefine = false;
            this.isScroll = false;
          }, 1200);
        } else {
          //王者试炼
        }
      }
    },
    antEvProxy(type) {
      //数据更新 事件转发  type 对局胜利或者失败 逻辑判断 方法跳转
      /*  第一次层 胜利失败
       */
      if (type == "balance") {
        return false;
      }
      let m = this.currentMedal,
        s, //？胜利
        r, //？试炼
        t; //？王者段位

      type == "success" ? (s = true) : (s = false);
      r = this.isRefine;
      m == 0 ? (t = true) : (t = false);

      console.log("this.isRefine--1993", this.isRefine);
      if (s && !r && !t) {
        //普通升星;
        console.log("普通升星");
        this.antShowStar("add").then(res => {
          console.log("line 1990");
          if (res) {
            console.log("普通升星 是否满星", res);
            this.showGetBtn = true;
            console.log("line-1811");
          }
        });
        this.setGameResult("balance");
        return false;
      }

      if (!s && !r && !t) {
        //普通降星
        this.antShowStar("sub");
        this.setGameResult("balance");
        return false;
      }

      if (s && r) {
        //试炼胜利一局判断结果
        this.medalList[m].totalNum += 1;
        this.medalList[m].winNum += 1;
        this.medalList[m].play = this.medalList[m].play + ",1";
        this.initTaskList(m, 2);

        // 试炼通关 领取奖励
        if (this.medalList[m].winNum >= this.medalList[m].maxWinNum) {
          setTimeout(() => {
            this.antTaskOver(1); //步骤二 点击领取才执行
          }, 500);
        }
        this.setGameResult("balance");

        return false;
      }

      if (!s && r) {
        //试炼胜利一局判断结果
        this.medalList[m].totalNum += 1;
        if (
          this.medalList[m].totalNum - this.medalList[m].winNum >
          this.medalList[m].maxTotalNum - this.medalList[m].maxWinNum
        ) {
          //试炼失败
          // 试炼失败
          this.medalList[m].totalNum = 0;
          this.medalList[m].winNum = 0;
          this.medalList[m].play = "";
          this.initTaskList(m, 2);
          this.setGameResult("balance");

          return false;
        } else {
          this.medalList[m].play = this.medalList[m].play + ",0";
          this.initTaskList(m, 2);
          this.setGameResult("balance");

          return false;
        }
      }
      //王者 胜利
      if (s && !r && t) {
        this.medalList[m].star++;
        // 判断是否满足10星阶段

        // 判断是否满足试炼条件
        if (this.medalList[m].star > 0 && this.medalList[m].star % 50 == 0) {
          if ((this.medalList[m].star / 50) % 2 == 0) {
            this.medalList[m].maxTotalNum = 10;
            this.medalList[m].maxWinNum = 6;
            this.medalList[m].isRefine = 1;
            this.medalList[m].receiveStar = this.medalList[m].star;
            this.isRefine = true;
            this.initTaskList(this.currentMedal, 2);
          } else {
            this.medalList[m].maxTotalNum = 5;
            this.medalList[m].maxWinNum = 3;
            this.medalList[m].isRefine = 1;
            this.medalList[m].receiveStar = this.medalList[m].star;
            this.isRefine = true;
            this.initTaskList(this.currentMedal, 2);
          }
        }
        this.initTopInfo(2);
        this.setGameResult("balance");
      }
      // 王者失败
      if (!s && !r && t) {
        if (this.medalList[m].star % 50 == 0) {
          this.setGameResult("balance");

          return false;
        } else {
          this.medalList[m].star--;
          this.initTopInfo(2);
          this.setGameResult("balance");
        }
      }
      // 王者试炼
    },
    getRankData() {
      wx.showLoading({
        title: "加载中",
        mask: true
      });
      this.fly.post("/getSeasonParagraphInfo").then(res => {
        this.info = res.data.Result;
        if (!this.info || !this.info.userParagraphList) {
          // API 无数据(如本地测试环境)——用模拟数据渲染页面
          this.info = this.info || {};
          this.info.userParagraphList = this.info.userParagraphList || [
            { paragraph:1, paragraphName:'青铜', star:1, rank:0, image:'', isTask:false },
            { paragraph:2, paragraphName:'白银', star:0, rank:0, image:'', isTask:false },
            { paragraph:3, paragraphName:'黄金', star:0, rank:0, image:'', isTask:false },
            { paragraph:4, paragraphName:'铂金', star:0, rank:0, image:'', isTask:false },
            { paragraph:5, paragraphName:'钻石', star:0, rank:0, image:'', isTask:false },
            { paragraph:6, paragraphName:'星耀', star:0, rank:0, image:'', isTask:false },
            { paragraph:7, paragraphName:'王者', star:0, rank:0, image:'', isTask:false },
          ];
          this.info.paragraph = 3;
          this.info.paragraphName = '黄金';
          console.log('[DEV] 使用模拟段位数据');
        }
        this.giftRankList = JSON.parse(JSON.stringify(this.info)).userParagraphList.reverse();
        this.medalList = JSON.parse(
          JSON.stringify(this.info.userParagraphList)
        );
        this.initMedalList(this.medalList);
        this.initScrollTop();
        this.dataReady = true;
        wx.hideLoading();
      }).catch(() => {
        wx.hideLoading();
        this.dataReady = true;
      });
    },
    select(index) {
      this.selectIndex = index;
    },
    getStrength() {
      this.fly.post("/user/getStrength").then(res => {
        this.setStrengthCount(res.data.Result.strengthCount);
      });
    },
    rankStart() {
      if (this.strengthCount >= 3) {
        this.setGameResult("balance");
        this.$router.push("/pages/game/index?rankGame=true&&isNew=true");
      } else {
        this.fly.post("/rank/beforeMatch").then(res => {
          this.setGameResult("balance");
          if (res.data.Result.isBegin == 1) {
            this.$router.push("/pages/game/index?rankGame=true&&isNew=true");
          } else {
            wx.showToast({ title: "体力不足", icon: "none" });
          }
        });
      }

      // this.$router.push("/pages/game/index?rankGame=true&&isNew=true");
    },
    init() {
      this.fly
        .post("/init", {
          userId: this.appInfo.userId,
          isNew: 0
        })
        .then(res => {
          this.setAppInfo(res.data.Result);
        });
    }
  },
  onLoad(options) {
    dataStack.push({
      ...this.$data
    });
  },
  mounted() {
    this.getRankData();
    console.log("mounted");
  },
  onShow() {
    // this.getRankData();
    // setTimeout(() => {
    //   this.antEvProxy('success');
    // }, 2000);
    if (this.gameResult != "balance") {
      // gameResult  success fail run
      this.antEvProxy(this.gameResult);
      this.init();
    } else {
      // 逃跑处理
    }

    // this.isScroll=true;
    // this.getRankData();
    // setTimeout(() => {
    //   if(this.dataReady){
    //     this.initScrollTop();
    //   }
    // }, 0);
    this.getStrength();
  },
  onUnload() {
    this.isScroll = true;
    this.showIntro = false;
    Object.assign(this.$data, dataStack.pop());
    console.log(this.viewId);
  }
};
</script>

