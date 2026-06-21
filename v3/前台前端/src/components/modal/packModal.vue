<template>
  <div class="m-pack-modal">
    <div class="u-pack-modal-inner">
      <div class="close"
           @click="close"></div>
      <div class="u-pack-modal-head">
        <div>背包</div>
        <img src="/static/icon/pack-bg.png">
      </div>
      <div class="u-pack-content">
        <div class="u-goods">
          <div class="u-goods-item"
               v-for="(item,index) in emptyList"
               :key="index"
               :class="{'select':selectIndex==index&&goodList[selectIndex]}"
               @click="selectGood(index)">
            <img v-if="goodList[index]&&goodList[index].num>0"
                 :src="IMGHEAD+goodList[index].image"
                 :class="{rotate30:(goodList[index].type==1||goodList[index].type==2||goodList[index].type==3)&&!goodList[index].book}">
            <div v-if="goodList[index]&&goodList[index].num>0">{{goodList[index].num}}</div>
          </div>
        </div>
        <div class="u-goods-desc"
             v-if="goodList[selectIndex]&&goodList[selectIndex].num>0&&!goodList[selectIndex].book">
          {{goodList[selectIndex].description}}
        </div>
        <div class="u-goods-desc"
             v-if="goodList[selectIndex]&&goodList[selectIndex].num>0&&goodList[selectIndex].book">
          {{goodList[selectIndex].remark}}
        </div>
        <div class="u-btn-box"
             :class="{'mt64':!goodList[selectIndex]}">
          <div class="u-btn u-btn-big u-btn-green"
               @click="useGood(goodList[selectIndex],selectIndex)">使用</div>
        </div>

      </div>

      <!-- <div class="advice-submit" @click="submitAdvice">提交</div> -->
    </div>
    <gift-box v-if="showGiftBox"
              :bookList="bookList"
              :type="boxType"
              :isMaxLv="isMaxLv"
              @close="showGiftBox=false">
    </gift-box>
    <div class="ad-box" :class="{'huawei':HUAWEI}">
      <ad unit-id="adunit-973fbb985356319e"></ad>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import giftBox from "@/components/gift/gift-box";
export default {
  name: "packModal",
  props: [],
  components: {
    "gift-box": giftBox
  },
  data() {
    return {
      selectIndex: 0,
      emptyList: [],
      goodList: [],
      bookList: [],
      showGiftBox: false,
      boxType: 4,
      isMaxLv:false,
    };
  },
  computed: {
    ...mapState(["strengthCount", "goodStorageList", "maxStrength", "IMGHEAD","HUAWEI"])
  },
  methods: {
    ...mapMutations([
      "setStrengthCount",
      "setGoodStorageList",
      "setSingleGoodNum"
    ]),
    getEmptyList() {
      for (let i = 0; i < 16; i++) {
        this.emptyList.push(0);
      }
    },
    selectGood(index) {
      if (this.goodList[index] && this.goodList[index].num > 0) {
        this.selectIndex = index;
      }
    },
    async getGoodList() {
      let good = [],
        goodBook = [];
      // if (this.goodStorageList.length > 0) {
      //   this.goodList = this.goodStorageList;
      // } else {
        await this.fly.post("/equipment/getEquipmentList").then(res => {
          return new Promise((resolve, reject) => {
            good = res.data.Result;
            goodBook = res.data.Result2;
            for (let i = 0; i < good.length; i++) {
              if (good[i].num > 0) {
                this.goodList.push(good[i]);
              }
            }
            for (let i = 0; i < goodBook.length; i++) {
              if (goodBook[i].num > 0) {
                goodBook[i].book = true;
                this.goodList.push(goodBook[i]);
              }
            }
            this.setGoodStorageList(this.goodList);
            resolve();
          });
        });
      // }
    },
    useGood(good, index) {
      //可优化,使用节流函数，一定时间内的使用的次数 一次性请求，只做前端使用提示。
      if (!good || good.num <= 0) {
        return false;
      }
      let isEnerge, isBox, isBook;
      (good.type == 1 || good.type == 2 || good.type == 3) && !good.book
        ? (isEnerge = true)
        : (isEnerge = false);
      (good.type == 4 || good.type == 5 || good.type == 6) && !good.book
        ? (isBox = true)
        : (isBox = false);
      good.book ? (isBook = true) : (isBook = false);

      if (isBook) {
        this.$router.push("/pages/book/index");
        this.$emit("close");
        return false;
      }
      if (isEnerge && this.strengthCount == this.maxStrength) {
        wx.showToast({
          title: "体力已满,无法使用",
          icon: "none"
        });
      } else {
        this.fly
          .post("/equipment/useEquipment", {
            count: 1,
            type: good.type
          })
          .then(res => {
            let result;
            result = res.data;
            if (result.Tag == 1) {
              good.num--;
              if (good.num == 0) {
                this.goodList.splice(index, 1);
              }
              this.setSingleGoodNum({
                type: good.type,
                num: good.num
              });

              if (isEnerge) {
                let energe = {
                  1: 1,
                  2: 15,
                  3: 30
                };
                let addNum =
                  this.strengthCount + energe[good.type] > this.maxStrength
                    ? this.maxStrength
                    : this.strengthCount + energe[good.type];
                this.setStrengthCount(addNum);
                wx.showToast({
                  title: `体力+${energe[good.type]}`,
                  icon: "none"
                });
              }

              if (isBox) {
                this.bookList = result.Result;
                this.showGiftBox = true;
                this.boxType = good.type;
                if(result.Type==1){
                  this.isMaxLv=true;
                }
                this.goodList = [];
                this.setGoodStorageList([]);
                this.getGoodList();
              }
            }
            console.log("使用道具反馈", result);
          });
      }
    },
    close() {
      this.$emit("close");
    }
  },
  mounted() {
    this.getEmptyList();
    this.getGoodList();
  }
};
</script>

<style lang="scss" scoped="scoped">
.m-pack-modal {
  position: fixed;
  z-index: 25;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  background: rgba(#000, 0.55);
}

.u-pack-modal-inner {
  position: relative;
  width: 582rpx;
  height: 776rpx;
  padding: 82rpx 48rpx 0 48rpx;
  margin: 160rpx auto 0 auto;
  box-sizing: border-box;
  border: 8rpx solid #a69ee3;
  border-radius: 32rpx;
  background: #49428c;
}

.u-pack-modal-head {
  width: 100%;
  height: 120rpx;
  position: absolute;
  top: -78rpx;
  left: 0;
  & > div {
    width: 100%;
    text-align: center;
    font-size: 52rpx;
    font-weight: bold;
    color: #fff;
    position: absolute;
    top: 6rpx;
    left: 0;
    z-index: 10;
  }
  img {
    width: 545rpx;
    height: 120rpx;
  }
}

.u-pack-content {
  box-sizing: border-box;
}

.close {
  width: 90rpx;
  height: 90rpx;
  position: absolute;
  right: -22rpx;
  top: -40rpx;
  background: #fff;
  border-radius: 50%;
  z-index: 5;
}
.close::before,
.close::after {
  content: "";
  position: absolute;
  left: 50%;
  top: 50%;
  width: 60rpx;
  height: 6rpx;
  background: #ff217b;
  border-radius: 20%;
}

.close::before {
  transform: translate(-50%, -50%) rotate(45deg);
}
.close::after {
  transform: translate(-50%, -50%) rotate(-45deg);
}

.u-goods {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}
.u-goods-item {
  width: 105rpx;
  height: 105rpx;
  box-sizing: border-box;
  border: 5rpx solid #655da9;
  border-radius: 8rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #28235c;
  margin-right: 18rpx;
  margin-bottom: 16rpx;
  position: relative;
  &:nth-of-type(4n + 4) {
    margin-right: 0;
  }
  img {
    width: 80rpx;
    height: 80rpx;
  }
  .rotate30 {
    transform: rotate(30deg);
  }
  div {
    position: absolute;
    font-size: 24rpx;
    font-weight: bold;
    color: #fff;
    right: 6rpx;
    bottom: 6rpx;
    text-shadow: 0 0 10rpx rgba(#28235c, 1);
  }
}
.select {
  border-color: #fff !important;
}
.u-goods-desc {
  font-size: 30rpx;
  font-weight: bold;
  color: #a69ee3;
  margin-bottom: 30rpx;
  margin-top: 14rpx;
}
.u-btn-big {
  width: 314rpx;
  height: 84rpx;
}
.mt64 {
  margin-top: 84rpx;
}
.ad-box {
  position: fixed;
  width: 100%;
  bottom: 0;
  left: 0;
  z-index: 5;
}
.huawei{
  transform: translateY(100rpx);
}
</style>
