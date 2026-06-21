<template>
  <div class="m-lottery sky-bg-2">
    <div class="l-head">
      <div class="l-gold">
        <img src="/static/icon/gold.png"> {{appInfo.money}}
      </div>
    </div>
    <div class="l-wrap"
         v-if="lotteryList.length>0">
      <div class="l-item"
           :class="{'selected':current==index}"
           v-for="(item,index) in lotteryList"
           :key="index">
        <div class="item-num">x{{item.num}}</div>
        <img class="item-gift"
             :class="{rotate30:index==6||index==8||index==13}"
             :src="IMGHEAD+item.image">
        <img class="item-border"
             src="/static/icon/selected-border.png">
      </div>
      <div class="l-view">
        <img class="view-bg"
             src="/static/icon/gift-show.png">
        <img v-if="current==-1"
             class="view-ques"
             src="/static/icon/ques.png">
        <img v-else
             :src="IMGHEAD+lotteryList[current].image"
             class="view-center"
             :class="{'finnal':current==final&&current!=-1}">

        <div class="view-txt"
             v-if="current==-1">
          尚未抽奖
        </div>
        <div class="view-txt c-fff"
             v-else>
          {{lotteryList[current].remark}}
        </div>

      </div>
    </div>
    <div class="gp-btn">
      <img class="btn-start"
           @click="start('')"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/btn-lottery.png">
      <img class="btn-start btn-start-ten"
           @click="start('ten')"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/btn-lottery-10.png">
    </div>

    <div class="get-gift"
         v-if="showAnt"
         @click="handleGetGift">
      <div class="mask"></div>
      <div class="img-box"
           :class="{'showAnt':showAnt}">
        <img class="box-bg"
             src="/static/icon/gift-light.png">
        <img class="img-gift"
             :src="IMGHEAD+lotteryList[current].image">
      </div>
      <div class="gift-txt"
           v-if="showAnt">
        {{lotteryList[current].remark}}
      </div>
    </div>

    <div class="auto-mask"
         v-if="showAutoMask">
      <img class="light-bg"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/box-light.png">
      <img class="box-head"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/box-head.png">
      <div class="gift-panel"
           :class="{'show-panel-ant':showPanelAnt}">
        <div v-for="(item,index) in 10"
             :key="index">
          <img :src="tenGiftList[index].url">
          <div class="p-text">X{{tenGiftList[index].num}}</div>
          {{tenGiftList[index].name}}
        </div>
      </div>
      <div class="auto-skip"
           @click="getTenGift">领取奖励</div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { getRondom } from "@/utils/index";
export default {
  data() {
    return {
      status: 1,
      current: -1,
      final: -1,
      result: 0,
      count: 1,
      tenGiftList: [
        { url: "/static/icon/b-box.png", name: "大宝箱" },
        { url: "/static/icon/b-box.png", name: "大宝箱" },
        { url: "/static/icon/b-box.png", name: "大宝箱" },
        { url: "/static/icon/b-box.png", name: "大宝箱" },
        { url: "/static/icon/b-box.png", name: "大宝箱" },
        { url: "/static/icon/b-box.png", name: "大宝箱" },
        { url: "/static/icon/b-box.png", name: "大宝箱" },
        { url: "/static/icon/b-box.png", name: "大宝箱" },
        { url: "/static/icon/b-box.png", name: "大宝箱" },
        { url: "/static/icon/b-box.png", name: "大宝箱" }
      ],
      resultList: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      type: "",
      showAutoMask: false,
      playTimer: null,
      showAnt: false,
      showPanelAnt: false
    };
  },
  computed: {
    ...mapState(["appInfo", "goodStorageList", "lotteryList", "IMGHEAD"])
  },
  methods: {
    ...mapMutations(["setAppInfo"]),
    start(type) {
      this.type = type;
      let apiType = 1;
      let limitMoney = 50;

      if (this.appInfo.money < limitMoney) {
        wx.showToast({ title: "金币不足", icon: "none" });
        return false;
      }
      if (this.status == 0) {
        wx.showToast({ title: "抽奖中", icon: "none" });
        return false;
      }
      if (type == "ten") {
        limitMoney = 500;
        apiType = 2;
      }

      this.fly
        .post("user/randomPrize", {
          type: apiType
        })
        .then(
          res => {
            if (res.data.Tag < 0) {
              wx.showToast({ title: "金币不足", icon: "none" });
              return false;
            }
            this.status = 0;
            this.final = -1;
            this.current = getRondom(0, 13);
            this.setAppInfo({ money: this.appInfo.money - limitMoney });
            // this.appInfo.money = this.appInfo.money - 50;
            this.result = res.data.Result;
            if (apiType == 2) {
              // this.tenGiftList = res.data.Result;
              this.showAutoMask = true;
              for (let i = 0; i < 10; i++) {
                this.tenGiftList[i] = {
                  url: this.IMGHEAD + this.lotteryList[this.result[i]].image,
                  name: this.lotteryList[this.result[i]].remark,
                  num:this.lotteryList[this.result[i]].num
                };
              }
              setTimeout(() => {
                this.showPanelAnt = true;
              }, 200);
              this.status = 1;
              return false;
            }

            // if (this.goodStorageList.length > 0) {
            //   for (let i = 0; i < this.goodStorageList.length; i++) {
            //     if (this.lotteryList[this.result].type == this.goodStorageList[i].type) {
            //       this.goodStorageList[i].num =
            //         parseInt(this.lotteryList[this.result].num) +
            //         parseInt(this.goodStorageList[i].num);
            //     }
            //   }
            //   for (let i = 0; i < this.goodStorageList.length; i++) {
            //     if (this.lotteryList[this.result].type == this.goodStorageList[i].type) {
            //       break;
            //     }
            //     if (
            //       (this.lotteryList[this.result].type !=
            //         this.goodStorageList[i].type &&
            //         this.lotteryList[this.result].type!=undefined&&
            //         i == this.goodStorageList.length - 1)
            //     ) {
            //       this.goodStorageList.push(
            //         JSON.parse(JSON.stringify(this.lotteryList))[this.result]
            //         // this.lotteryList[this.result]
            //         );
            //     }
            //   }
            //   this.goodStorageList.sort(this.keysrt("type", false));
            // }

            //   setTimeout(() => {
            //   this.startTimer(delayTime);

            //   let t = setInterval(() => {
            //     //加速
            //     delayTime = delayTime - 40;

            //     this.startTimer(delayTime);

            //     if (delayTime <= 40) {
            //       clearInterval(t);

            //       setTimeout(() => {
            //         let t2 = setInterval(() => {
            //           //减速
            //           delayTime = delayTime + 40;
            //           this.startTimer(delayTime);
            //         }, 500);

            //         setTimeout(() => {
            //           this.final = this.result; //获得结果 （可以后端给）
            //           clearInterval(t2);
            //         }, 2000);
            //       }, 3000);
            //     }
            //   }, 500);
            // }, 500);
            this.play();
          },
          err => {
            wx.showToast({ title: "抽奖失败,请重试", icon: "none" });
          }
        );
    },
    getTenGift() {
      this.showAutoMask = false;
      this.showPanelAnt = false;
    },
    keysrt(key, desc) {
      return function(a, b) {
        return desc ? ~~(a[key] < b[key]) : ~~(a[key] > b[key]);
      };
    },
    play() {
      let delayTime = 100;

      setTimeout(() => {
        this.startTimer(delayTime);

        let t = setInterval(() => {
          //加速
          delayTime = delayTime - 30;

          this.startTimer(delayTime);

          if (delayTime <= 40) {
            clearInterval(t);

            let t2 = setInterval(() => {
              //减速
              delayTime = delayTime + 40;
              this.startTimer(delayTime);
            }, 500);

            setTimeout(() => {
              this.final = this.result; //获得结果 （可以后端给）
              clearInterval(t2);
            }, 1000);
          }
        }, 500);
      }, 200);
    },
    handleGetGift() {
      this.showAnt = false;
      this.current = -1;
      this.final = -1;
    },
    startTimer(delayTime) {
      clearInterval(this.playTimer);
      this.playTimer = setInterval(() => {
        this.current++;
        if (this.current == 14) {
          this.current = 0;
        }
        if (this.current == this.final && this.type != "ten") {
          clearInterval(this.playTimer);
          console.log(
            "恭喜中奖,奖品是：",
            this.lotteryList[this.current].remark
          );
          setTimeout(() => {
            this.showAnt = true;
          }, 1000);
          // 初始化妆状态
          this.status = 1;
        }

        // if(this.current == this.final&&this.type=='ten'){
        //   clearInterval(this.playTimer);
        //   this.status = 1;
        //   this.tenGiftList[this.count-1]={
        //     url:this.IMGHEAD+this.lotteryList[this.current].image,
        //     name:this.lotteryList[this.current].remark
        //   };
        //   if(this.count>9){
        //     this.count=0;
        //     console.log("展示 十连抽结果")
        //   }else{
        //     this.count++;
        //     setTimeout(() => {
        //       this.start('ten');
        //     }, 1000);
        //   }
        // }
      }, delayTime);
    }
  }
};
</script>

<style lang="scss" scoped>
.m-lottery {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  color: #fff;
  font-weight: bold;
  box-sizing: border-box;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 30;
}

.l-head {
  width: 100%;
  height: 100rpx;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-right: 45rpx;
  box-sizing: border-box;
}
.l-gold {
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
.l-wrap {
  width: 690rpx;
  box-sizing: border-box;
  height: 880rpx;
  position: relative;
  .l-view {
    position: absolute;
    width: 336rpx;
    height: 350rpx;
    left: 50%;
    top: 45%;
    transform: translate(-50%, -50%);
    .view-bg {
      width: 100%;
      height: 100%;
      display: block;
    }
    .view-txt {
      text-align: center;
      color: #a69ee3;
      font-size: 44rpx;
      font-weight: bold;
      margin-top: 40rpx;
    }
    .c-fff {
      color: #fff;
    }
    .view-ques {
      width: 69rpx;
      height: 105rpx;
    }
    .view-center {
      width: 120rpx;
      height: 120rpx;
    }
    .view-center,
    .view-ques {
      position: absolute;
      left: 50%;
      top: 48%;
      transform: translate(-50%, -50%);
    }
  }
  .finnal {
    animation: getGfit 1s ease;
  }
  @keyframes getGfit {
    0% {
      transform: translate(-50%, -50%) scale(1);
    }
    50% {
      transform: translate(-50%, -50%) scale(1.2);
    }
    100% {
      transform: translate(-50%, -50%) scale(1);
    }
  }
  .l-item {
    position: absolute;
    width: 140rpx;
    height: 140rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    background: #524c99;
    border: 6rpx solid #7f78c4;
    border-radius: 10rpx;
    box-sizing: border-box;
    font-size: 24rpx;
    &:nth-of-type(1),
    &:nth-of-type(2),
    &:nth-of-type(3),
    &:nth-of-type(4) {
      top: 0;
    }
    &:nth-of-type(4),
    &:nth-of-type(5),
    &:nth-of-type(6),
    &:nth-of-type(7),
    &:nth-of-type(8) {
      right: 0;
    }
    &:nth-of-type(1),
    &:nth-of-type(11),
    &:nth-of-type(12),
    &:nth-of-type(13),
    &:nth-of-type(14) {
      left: 0;
    }

    &:nth-of-type(8),
    &:nth-of-type(9),
    &:nth-of-type(10),
    &:nth-of-type(11) {
      bottom: 0;
    }

    &:nth-of-type(2),
    &:nth-of-type(10) {
      left: 184rpx;
    }
    &:nth-of-type(3),
    &:nth-of-type(9) {
      left: 368rpx;
    }
    &:nth-of-type(4) {
      left: 552rpx;
    }
    &:nth-of-type(5),
    &:nth-of-type(14) {
      top: 184rpx;
    }
    &:nth-of-type(6),
    &:nth-of-type(13) {
      top: 368rpx;
    }
    &:nth-of-type(7),
    &:nth-of-type(12) {
      top: 552rpx;
    }
    &:nth-of-type(8),
    &:nth-of-type(11) {
      top: 736rpx;
    }
    .item-border {
      width: 184rpx;
      height: 184rpx;
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      opacity: 0;
    }
    .item-gift {
      width: 90rpx;
      height: 90rpx;
    }
    .item-num {
      position: absolute;
      right: 10rpx;
      bottom: 10rpx;
      font-size: 40rpx;
      font-weight: bold;
      z-index: 2;
    }
    .rotate30 {
      transform: rotate(30deg);
    }
  }
  .selected {
    img {
      opacity: 1;
    }
  }
}

.gp-btn {
  display: flex;
  justify-content: center;
}
.btn-start {
  width: 340rpx;
  height: 120rpx;
  margin-top: 54rpx;
  &:active {
    transform: scale(0.95);
  }
}
.btn-start-ten {
  margin-left: 30rpx;
  width: 320rpx;
}
.get-gift {
  z-index: 20;
  text-align: center;
  color: #fff;
  font-size: 40rpx;
  font-weight: bold;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-direction: column;
}
.img-box {
  margin-top: 280rpx;
  position: relative;
}
.gift-txt {
  text-align: center;
}
.mask {
  position: fixed;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  background: rgba(#000, 0.7);
  z-index: -1;
}
.get-gift,
.img-gift {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.box-bg {
  width: 400rpx;
  height: 400rpx;
  opacity: 0;
}
.img-gift {
  width: 200rpx;
  height: 200rpx;
  top: 50%;
  transform: translate(-50%, -50%) scale(0);
}
.showAnt {
  .box-bg {
    animation: showBg 0.5s ease forwards;
  }
  .img-gift {
    animation: showGift 0.29s ease forwards;
  }
}
@keyframes showBg {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
@keyframes showGift {
  0% {
    transform: translate(-50%, -50%) scale(0);
  }
  57.1% {
    transform: translate(-50%, -50%) scale(1.2);
  }
  100% {
    transform: translate(-50%, -50%) scale(1);
  }
}

.auto-mask {
  position: fixed;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  z-index: 20;
  background: rgba(#000, 0.9);
  .box-head {
    width: 437rpx;
    height: 162rpx;
    margin-bottom: 100rpx;
  }
  .light-bg {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 1031rpx;
    height: 1665rpx;
    z-index: -1;
  }
  .gift-panel {
    width: 690rpx;
    height: 500rpx;
    margin-bottom: 150rpx;
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;
    & > div {
      width: 150rpx;
      height: 150rpx;
      margin-right: 22rpx;
      margin-bottom: 50rpx;
      font-size: 24rpx;
      font-weight: bold;
      color: #fff;
      text-align: center;
      transition: all 0.2s ease;
      transform: scale(0);
      &:nth-child(1) {
        transition-delay: 0;
      }
      &:nth-child(2) {
        transition-delay: 0.1s;
      }
      &:nth-child(3) {
        transition-delay: 0.2s;
      }
      &:nth-child(4) {
        transition-delay: 0.3s;
      }
      &:nth-child(5) {
        transition-delay: 0.4s;
      }
      &:nth-child(6) {
        transition-delay: 0.5s;
      }
      &:nth-child(7) {
        transition-delay: 0.6s;
      }
      &:nth-child(8) {
        transition-delay: 0.7s;
      }
      &:nth-child(9) {
        transition-delay: 0.8s;
      }
      &:nth-child(10) {
        transition-delay: 0.9s;
      }

      img {
        display: block;
        width: 100rpx;
        height: 100rpx;
        margin: 0 auto 10rpx auto;
      }
      .p-text {
        position: absolute;
        right: 10rpx;
        font-size: 30rpx;
        top: 70rpx;
      }
    }
    .show-panel-item {
      transform: scale(1);
    }
  }
  .show-panel-ant {
    div {
      transform: scale(1);
    }
  }
  .auto-text {
    font-size: 40rpx;
    font-weight: bold;
    color: #fff;
    text-align: center;
    margin: 50rpx 0;
  }
  .auto-count {
    font-size: 40rpx;
    font-weight: bold;
    text-align: center;
    margin-bottom: 50rpx;
  }
  .auto-skip {
    width: 320rpx;
    height: 100rpx;
    margin-bottom: 50rpx;
    border-radius: 20rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 28rpx;
    font-weight: bold;
    background: #ffba26;
    color: #fff;
  }
}
</style>
