<template>
  <div class="m-modal"
       @click="changeGift">
    <div class="u-modal-inner"
         v-if="showInner">
      <div class="u-head">
        <div>赛季奖励</div>
        <img class="u-head-img"
             src="/static/modal-bg.png">
      </div>
      <div class="u-content">
        <div class="u-tt">{{year}}年{{month}}月赛季段位</div>
        <img v-if="seasonData.paragraphImage"
             class="u-medal"
             :src="IMGHEAD+seasonData.paragraphImage">
        <div class="u-medal-name">{{seasonData.paragraphName}}</div>

        <div class="u-line">
          <div class="line-txt">获得奖励</div>
        </div>
        <div class="u-gift-list">
          <div class="gift-item"
               v-for="(item,index) in giftList[gIndex]"
               :key="index">
            <img :src="goodsMap[item.type].url">
            <div class="gift-num">{{item.num}}</div>
          </div>
        </div>
        <div class="u-confirm"
             @click.stop="confirmHandle">确定</div>
      </div>
    </div>
    <div class="get-gift"
         v-if="seasonData.paragraph&&showGift">
      <div class="img-box"
           :class="{'showAnt':showAnt}">
        <img class="box-bg"
             src="/static/icon/gift-light.png"
             alt="">
        <img class="img-gift"
             :src="goodsMap[giftList[gIndex][showIndex].type].url">
      </div>
      {{goodsMap[giftList[gIndex][showIndex].type].name}}X{{giftList[gIndex][showIndex].num}}
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  name: "seasonModal",
  props: ["seasonData"],
  data() {
    return {
      showInner: true,
      showGift: false,
      showAnt: false,
      showIndex: 0,
      giftList: [
        [{ type: 3, num: 4 }],
        [{ type: 3, num: 3 }],
        [{ type: 3, num: 1 }, { type: 2, num: 4 }, { type: 1, num: 20 }],
        [{ type: 3, num: 1 }, { type: 2, num: 4 }, { type: 1, num: 20 }],
        [{ type: 3, num: 1 }],
        [{ type: 2, num: 1 }, { type: 1, num: 15 }],
        [{ type: 2, num: 1 }, { type: 1, num: 10 }],
        [{ type: 2, num: 1 }, { type: 1, num: 5 }],
        [{ type: 1, num: 5 }]
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
          url: "/static/icon/book.png",
          name: "实体书"
        }
      }
    };
  },
  computed: {
    ...mapState(["IMGHEAD"]),
    year() {
      if (this.seasonData.seasonTime) {
        return this.seasonData.seasonTime.split("-")[0];
      }
    },
    month() {
      if (this.seasonData.seasonTime) {
        return this.seasonData.seasonTime.split("-")[1];
      }
    },
    gIndex() {
      if (this.seasonData.paragraph) {
        return Math.ceil(this.seasonData.paragraph / 5) - 1;
      }
    }
  },
  methods: {
    ...mapMutations([""]),

    confirmHandle() {
      this.showInner = false;
      this.showGift = true;
      setTimeout(() => {
        this.showAnt = true;
        if (this.showIndex == this.giftList[this.gIndex].length - 1) {
          setTimeout(() => {
            this.showGift = false;
            this.showAnt = false;
            this.close();
          }, 1000);
        }
      }, 0);
    },
    changeGift() {
      try {
        console.log(" this.giftList[this.gIndex]", this.giftList[this.gIndex])
        if (this.showIndex < this.giftList[this.gIndex].length - 1) {
          this.showAnt = false;
          setTimeout(() => {
            this.showIndex++;
            this.showAnt = true;
            if (this.showIndex == this.giftList[this.gIndex].length - 1) {
              setTimeout(() => {
                this.showGift = false;
                this.showAnt = false;
                this.close();
              }, 1000);
            }
          }, 100);
        }
      } catch (err) {
        console.log(err);
      }
    },
    close() {
      this.$emit("close");
    }
  },
  mounted() {
    this.giftList = this.giftList.reverse();
  }
};
</script>

<style lang="scss" scoped>
@import url("../../scss/s-modal.scss");
.m-modal {
  background: rgba(#000, 0.55);
}
.u-modal-inner {
  top: 90rpx;
}
.u-content {
  box-sizing: border-box;
  height: 740rpx;
  border-bottom: 30rpx;
  padding: 20rpx 12rpx 0 12rpx;
  text-align: center;
}
.u-tt {
  font-size: 32rpx;
  font-weight: bold;
  color: #a69ee3;
  line-height: 1;
  padding: 30rpx 0;
}

.u-medal {
  width: 220rpx;
  height: 220rpx;
}
.u-medal-name {
  font-size: 32rpx;
  font-weight: bold;
  color: #28235c;
  margin-bottom: 60rpx;
}
.u-line {
  width: 350rpx;
  height: 2rpx;
  background: #a69ee3;
  color: #a69ee3;
  position: relative;
  margin: 0 auto 45rpx auto;
}
.line-txt {
  width: 170rpx;
  height: 40rpx;
  font-size: 36rpx;
  font-weight: bold;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  background: #fff;
}
.u-gift-list {
  margin-bottom: 36rpx;
  display: flex;
  justify-content: center;
  align-items: center;
}
.gift-item {
  width: 114rpx;
  height: 114rpx;
  background: #a69ee3;
  border-radius: 10rpx;
  margin-right: 10rpx;
  position: relative;
  font-size: 36rpx;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  &:last-child {
    margin-right: 0;
  }
  img {
    width: 70rpx;
    height: 70rpx;
    transform: rotate(30deg);
  }
  .gift-num {
    position: absolute;
    right: 10rpx;
    bottom: 10rpx;
    color: #fff;
  }
}
.u-confirm {
  width: 300rpx;
  height: 84rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #fff;
  font-size: 36rpx;
  font-weight: bold;
  background: #85d85d;
  border-radius: 12rpx;
  margin: 0 auto;
}
.get-gift {
  z-index: 20;
  text-align: center;
  color: #fff;
  font-size: 40rpx;
  font-weight: bold;
}
.get-gift,
.img-gift {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.img-box {
  margin: 0 auto 20rpx auto;
}
.box-bg {
  width: 400rpx;
  height: 400rpx;
  opacity: 0;
}
.img-gift {
  width: 200rpx;
  height: 200rpx;
  top: 40%;
  transform: translate(-50%, -50%) scale(0);
}
.showAnt {
  .box-bg {
    animation: showBg 0.29s ease forwards;
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
</style>
