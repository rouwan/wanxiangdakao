<template>
  <div class="day-gift">
    <div class="box"
         :class="{hide:showCatGame}">
      <img class="bg"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/day-gift-bg.png">
      <div class="content">
        <div class="menu"
             v-if="type==1">
          <div :class="{hasTip:globalStatus.isDaySign==1}">
            <img v-if="menuIndex==0"
                 class="check"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/check-act.png">
            <img v-else
                 class="check"
                 @click="changeMenu(0)"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/check.png">
          </div>
          <div :class="{hasTip:canCat==1}">
            <img v-if="menuIndex==1"
                 class="cat"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/cat-act.png">
            <img v-else
                 class="cat"
                 @click="changeMenu(1)"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/cat.png">
          </div>

          <div :class="{hasTip:canNumber==1}">
            <img v-if="menuIndex==2"
                 class="num"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/luck-num-act.png">
            <img v-else
                 class="num"
                 @click="changeMenu(2)"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/luck-num.png">
          </div>
        </div>
        <!-- type==0 每日任务-->
        <div class="task-head"
             v-if="type==0">
          <img class="t-bg"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/taskGiftBg.png">
          <div v-if="receive">已领取</div>
          <img v-else
               class="t-gift"
               src="/static/icon/s-box.png">
        </div>
        <!-- 签到区域 -->
        <section v-if="menuIndex==0&&type==1">
          <div class="check-box">
            <div class="gift-item"
                 v-for="(item,index) in giftList"
                 :key="index">
              <div class="g-box">
                <img v-if="index==checkDay&&globalStatus.isDaySign==1"
                     class="g-able"
                     src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/able.png">
                <img class="g-gift"
                     :src="IMGHEAD+item.image">
                <div class="g-num"
                     v-if="item.isLookNum">{{item.num}}</div>
                <img class="g-receved"
                     v-if="item.status==1"
                     src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved.png">
              </div>
              <div class="g-time">{{item.description}}</div>
            </div>
          </div>

          <button class="db-btn"
                  open-type="share"
                  plain="true"><img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/double.png"
                 alt=""></button>
          <div class="sg-btn"
               @click="throttle(sign,200)">普通领取</div>
        </section>

        <!-- 撸猫 -->
        <section v-if="menuIndex==1&&type==1">
          <div class="cat-bg"></div>
          <img class="cat-btn"
               @click="handleStartClick"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/play-cat.png">
          <div class="tip">每天可以从这里获取金币哦</div>
        </section>

        <!-- 幸运数字 -->
        <section v-if="menuIndex==2&&type==1">
          <div class="num-tip">每周一0:00更新任务</div>
          <div class="number-box pt44">
            <div class="number-gift-item"
                 v-for="(item,index) in numList"
                 :key="index">
              <div class="score-box">
                <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/score-box.png">
                <div>{{item.number}}</div>
              </div>
              <div class="gift-box">
                <img :src="IMGHEAD+item.image">
                <div class="gift-num"
                     v-if="item.isLookNum==1">{{item.num}}</div>
                <img class="r-tag"
                     v-if="item.status==1"
                     src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved-tag.png">
              </div>
              <img v-if="item.status==0"
                   class="gift-btn"
                   @click="getNumberGift(item)"
                   src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/get-btn.png">
              <img v-if="item.status==-1"
                   @click="goRank"
                   class="gift-btn"
                   src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/go-btn.png">
              <img v-if="item.status==1"
                   class="gift-btn-receved"
                   src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved-txt.png">
            </div>
          </div>
          <div class="tip ft24">排位赛中达到以上分数,可获得相应奖励,获胜情况下,奖励翻倍</div>
        </section>

        <!-- 任务列表 -->
        <section v-if="type==0">
          <div class="number-box task-list">
            <div class="number-gift-item task-item"
                 v-for="(item,index) in taskList"
                 :key="index">
              <div class="task-box">
                <img v-if="taskList[0]!=0"
                     :src="taskMap[item.type].icon">
              </div>
              <div class="task-info">
                <div class="t-top">
                  {{item.description}}
                </div>
                <div class="t-bottom">
                  <div>进度:</div>
                  {{item.status}}/1
                </div>
              </div>
              <img v-if="item.status==0"
                   class="gift-btn"
                   @click="goTaskPlace(item.type)"
                   src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/go-btn.png">

              <img v-if="item.status==1"
                   class="gift-btn-receved"
                   src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/complete.png">
            </div>
          </div>
          <img class="get-task"
               @click="getTaskGift"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/get-btn-big.png">
        </section>
      </div>

      <img class="tt"
           v-if="type==1"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/day-gift-tt.png">
      <img class="tt"
           v-if="type==0"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/tt-task.png">
      <div class="close"
           @click="close"></div>
    </div>
    <!-- 撸猫游戏 -->
  <cat-game  v-if="showCatGame" @stopGame="stopCatGame"></cat-game>

    <!-- 撸猫游戏 end -->
    <get-gift v-if="showGetGift"
              :giftList="showGiftList"
              :double="isDouble"
              @close="showGetGift=false"></get-gift>
    <div class="main-ad"
         v-if="!showCatGame"
         :class="{'huawei':HUAWEI}">
      <ad unit-id="adunit-e02686c515746fa0"></ad>
    </div>
  </div>
</template>

<script>
import { getRondom } from "@/utils/index";
import { shareInfo } from "@/config/dataFile";
import { mapState, mapMutations } from "vuex";
import {throttle} from "@/utils/index"
import getGift from "./get-gift";
import catGame from "./catGame";

export default {
  props: ["type", "showCatNow"],
  onShareAppMessage: function(res) {
    console.log("day")
    if (res.from == "button") {
      this.hideType = "share";
      console.log("from button");
    }
    // let index = getRondom(0, 2);
    // return {
    //   title: shareInfo[index].title,
    //   imageUrl: shareInfo[index].imgUrl,
    //   // imageUrl: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/shareImg/day-gift-1.png",
      
    //   path: `/pages/entry/index?userId=${this.appInfo.userId}`
    // };
  },
  components: {
    "get-gift": getGift,
    "cat-game":catGame,
  },
  data() {
    return {
      isDouble: false,
      menuIndex: 0,
      checkDay: 0,
      giftList: [
        {
          day: 1,
          description: "金币",
          id: 1,
          image:
            "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
          isLookNum: 1,
          name: "金币",
          num: 20,
          status: 0,
          type: 13
        },
        {
          day: 1,
          description: "金币",
          id: 1,
          image:
            "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
          isLookNum: 1,
          name: "金币",
          num: 20,
          status: 0,
          type: 13
        },
        {
          day: 1,
          description: "金币",
          id: 1,
          image:
            "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
          isLookNum: 1,
          name: "金币",
          num: 20,
          status: 0,
          type: 13
        },
        {
          day: 1,
          description: "金币",
          id: 1,
          image:
            "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
          isLookNum: 1,
          name: "金币",
          num: 20,
          status: 0,
          type: 13
        },
        {
          day: 1,
          description: "金币",
          id: 1,
          image:
            "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
          isLookNum: 1,
          name: "金币",
          num: 20,
          status: 0,
          type: 13
        },
        {
          day: 1,
          description: "金币",
          id: 1,
          image:
            "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
          isLookNum: 1,
          name: "金币",
          num: 20,
          status: 0,
          type: 13
        },
        {
          day: 1,
          description: "金币",
          id: 1,
          image:
            "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
          isLookNum: 1,
          name: "金币",
          num: 20,
          status: 0,
          type: 13
        }
      ],
      canCat: 0,
      canNumber: 0,
      numList: [],
      hideType: "",
      showCatAd: false,
      showGuide: true,
      cf: false,
      pStyle: "",
      showCatGame: false,
      gameStatus: 0, //0 可以游戏 1游戏中
      gameTime: 0,
      timer: null,
      timeType: 1,
      gold: 0,
      addNum: 1, //0-1=1,1-2=2,2-3=3,3-4=4,4-5=5,
      showGetGift: false,
      showGiftList: [],
      taskList: [0, 0, 0],
      taskComplete: false,
      receive: false,
      taskMap: {
        1: {
          icon: "",
          route: ""
        },
        2: {
          icon:
            "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/crown.png",
          route: "/roomPages/pages/roomList"
        },
        3: {
          icon:
            "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/rank.png",
          route: "/pages/preGamePage/index"
        },
        4: {
          icon:
            "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/prject.png",
          route: "/subjectFactoryPages/pages/index"
        },
        5: {
          icon:
            "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/top.png",
          route: "/roomPages/pages/roomList"
        },
        6: {
          icon:
            "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/lottery.png",
          route: "/pages/lottery/index"
        }
      },
      checkReady:false,
    };
  },
  computed: {
    ...mapState(["IMGHEAD", "appInfo", "HUAWEI", "globalStatus"])
  },
  watch: {
    gold(val) {
      if (val >= 66) {
        this.timeType = 3;
      } else if (val >= 33) {
        this.timeType = 2;
      }
    }
  },
  methods: {
    ...mapMutations(["setAppInfo","setGlobalStatusByItem"]),
    throttle:throttle,
    goTaskPlace(taskType) {
      this.$router.push(this.taskMap[taskType].route);
      this.$emit("close");
    },
    changeMenu(index) {
      this.menuIndex = index;
      if (index == 1) {
        return false;
      }
      this.getList(index + 1);
    },
    close() {
      this.$emit("close");
    },
    goRank() {
      this.$router.push("/pages/preGamePage/index");
      this.close();
    },
    handleStartClick() {
      if (this.canCat == 0) {
        wx.showModal({
          title: "系统提示",
          content:
            "撸猫时间：中午 11:00—14:00和晚上 17:00—22:00（总共两次机会）",
          showCancel: false
        });
        return false;
      } else {
        this.showCatGame = true;
      }
    },
    startCatGame() {
      this.showGuide = false;
      this.gameStatus = 1; //游戏中
      this.timer = setInterval(() => {
        if (this.gameTime >= 5000) {
          setTimeout(() => {
            this.pStyle = "background:#f0af2b!important;";
          }, 100);
          this.stopCatGame();
        }
        if (this.gameTime >= 4000) {
          this.addNum = 5;
          this.pStyle = "background:#d33733!important;";
        } else if (this.gameTime >= 3000) {
          this.addNum = 3;
          this.pStyle = "background:#f06c2b!important;";
        } else if (this.gameTime >= 2000) {
          this.addNum = 2;
          this.pStyle = "background:#f08c2b!important;";
        } else if (this.gameTime >= 1000) {
          // this.addNum = 1;
          this.pStyle = "background:#f0af2b!important;";
        }
        this.gameTime += 10;
      }, 10);
    },
    stopCatGame(gold) {

      this.canCat = 0; //一个时间段一次
      setTimeout(() => {
        this.showGfitAntBox([
          {
            url:
              "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/many-gold.png",
            num: gold,
            name: "金币"
          }
        ]);
        this.fly
          .post("dayWelfare/hitCat", {
            money: gold>150?150:gold
          })
          .then(res => {
            console.log("金币增加",gold>150?150:gold);
          });
          this.setAppInfo({money:this.appInfo.money+(gold>150?150:gold)});
      }, 1000);

      // TODO 金币展示 结算等
      setTimeout(() => {
        this.showCatGame = false;
      }, 4000);
    },
    getTaskGift() {
      if (this.receive) {
        wx.showToast({
          title: "已经领取过奖励啦",
          icon: "none"
        });
        return false;
      }
      if (this.taskComplete) {
        this.fly.post("dayTask/receiveGood").then(() => {
          this.showGiftList = [
            {
              name: "小宝箱",
              url: "/static/icon/s-box.png",
              num: 1
            }
          ];
          this.showGetGift = true;
          this.receive = true;
        });
      } else {
        wx.showToast({
          title: "尚有未完成任务",
          icon: "none"
        });
      }
    },
    catClick() {
      if (this.gameStatus == 0) {
        wx.showToast({ title: "游戏已结束", icon: "none" });
        return false;
      }
      // this.cf = true;
      this.gold += this.addNum;
      // setTimeout(() => {
      //   this.cf = false;
      // }, 80);
    },
    showGfitAntBox(list) {
      this.showGiftList = list;
      this.showGetGift = true;
    },
    sign(type) {
      let _type=type||1;
      if(type==1&&!this.checkReady){
        return false;
      }
      if (this.globalStatus.isDaySign == 1) {
        this.fly
          .post("dayWelfare/sign", {
            type: _type,
            day: this.checkDay + 1
          })
          .then(res => {
            if (res.data.Tag == 1) {
              this.setGlobalStatusByItem({isDaySign:0});
              // this.globalStatus.isDaySign = 0;
              this.giftList[this.checkDay].status = 1;
              if (this.giftList[this.checkDay].type == 13) {
               this.setAppInfo({money:this.appInfo.money+this.giftList[this.checkDay].num * _type});
                // this.appInfo.money += this.giftList[this.checkDay].num * type;
              }
              this.showGfitAntBox([
                {
                  url: this.IMGHEAD + this.giftList[this.checkDay].image,
                  num: this.giftList[this.checkDay].num * _type,
                  name: this.giftList[this.checkDay].name
                }
              ]);
            }
          });
      } else {
        wx.showToast({ title: "已经签到过了~！", icon: "none" });
      }
    },

    getNumberGift(item) {
      this.fly
        .post("dayWelfare/numberReceive", {
          number: item.number
        })
        .then(res => {
          item.status = 1;
          let x = 1;
          if (item.isWin == 1) {
            x = 2;
          }
          this.isDouble = true;
          setTimeout(() => {
            this.isDouble = false;
          }, 2000);
          this.showGfitAntBox([
            {
              url: this.IMGHEAD + item.image,
              num: item.num * x,
              name: item.name
            }
          ]);
          if (item.type == 13) {
          this.setAppInfo({money:this.appInfo.money+item.num * x});
          }
        });
    },
    checkTaskComplete() {
      for (let i = 0; i < this.taskList.length; i++) {
        if (this.taskList[i].status == 0) {
          this.taskComplete = false;
          break;
        } else {
          this.taskComplete = true;
        }
      }
      for (let i = 0; i < this.taskList.length; i++) {
        if (this.taskList[i].receive == 0) {
          this.receive = false;
          break;
        } else {
          this.receive = true;
        }
      }
    },

    getTaskList() {
      this.fly.post("/dayTask/getTaskList").then(res => {
        this.taskList = res.data.Result.taskList;
        this.checkTaskComplete();
      });
    },
    getList(type) {
      this.fly
        .post("dayWelfare/getDayWelfareList", {
          type: type
        })
        .then(res => {
          if (type == 1) {
            //签到列表
            this.giftList = res.data.Result.daySignList;
            for (let i = 0; i < this.giftList.length; i++) {
              if (this.giftList[i].status == 0) {
                this.checkDay = i;
                break;
              }
            }
            this.checkReady=true;
            this.canCat = res.data.Result.isHitCat;
            this.canNumber = res.data.Result.isLuckNumber;
          }
          if (type == 2) {
            console.log(res.data);
          }
          if (type == 3) {
            this.numList = res.data.Result;
          }
        });
    }
  },
  onShow() {
    if (this.hideType == "share") {
      this.sign(2);
      this.hideType = "";
    }
  },
  mounted() {
    if (this.type == 1) {
      if (this.showCatNow) {
        this.showCatGame = true;
      }
      this.getList(1);
    } else if (this.type == 0) {
      this.getTaskList();
    }
  }
};
</script>

<style lang="scss" scoped>
.day-gift {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  z-index: 20;
  background: rgba(#000, 0.8);
  display: flex;
  justify-content: center;
}
.box {
  position: relative;
  margin-top: 100rpx;
  width: 665rpx;
  height: 890rpx;
  border-radius: 50rpx;
  border: 12rpx solid #fff;
  box-sizing: border-box;
  background: linear-gradient(180deg, #6b9aff, #367cd7);
}
.bg {
  width: 100%;
  height: 100%;
  border-radius: 32rpx;
}

.content {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  padding-top: 216rpx;
  box-sizing: border-box;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
}

.menu {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: 90rpx;
  width: 410rpx;
  height: 102rpx;
  display: flex;
  align-items: center;
  justify-content: space-around;
  z-index: 2;
}

.check {
  width: 101rpx;
  height: 98rpx;
}

.cat {
  width: 102rpx;
  height: 100rpx;
}
.num {
  width: 102rpx;
  height: 93rpx;
}

.check-box {
  width: 570rpx;
  height: 412rpx;
  padding: 45rpx 30rpx;
  box-sizing: border-box;
  background: #fff;
  border-radius: 32rpx;
  color: #fff;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  align-content: flex-start;
  flex-wrap: wrap;
  font-family: "黑体";
}
.gift-item {
  width: 116rpx;
  margin-bottom: 20rpx;
  margin-right: 14rpx;
  &:nth-of-type(4),
  &:nth-of-type(7) {
    margin-right: 0;
  }
}
.g-box {
  width: 116rpx;
  height: 116rpx;
  margin-bottom: 10rpx;
  border-radius: 10rpx;
  background: #f6e38f;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

.g-gift {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 100rpx;
  height: 100rpx;
}
.g-num {
  position: absolute;
  bottom: 14rpx;
  right: 14rpx;
  font-size: 32rpx;
  font-weight: bold;
  -webkit-text-fill-color: #fff;
  -webkit-text-stroke: 2rpx #150628;
}
.g-able,
.g-receved {
  position: absolute;
  left: 0;
}

.g-able {
  width: 121rpx;
  height: 137rpx;
  top: -21rpx;
  left: -2rpx;
}
.g-receved {
  width: 116rpx;
  height: 116rpx;
  top: 0;
}
.g-time {
  width: 116rpx;
  font-size: 24rpx;
  font-weight: bold;
  color: #90574a;
  text-align: center;
}
.tt {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: -78rpx;
  width: 640rpx;
  height: 367rpx;
}

.close {
  width: 90rpx;
  height: 90rpx;
  position: absolute;
  right: -30rpx;
  top: -30rpx;
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

.db-btn {
  margin-top: 34rpx;
  margin-bottom: 30rpx;
  width: 288rpx;
  height: 105rpx;
  padding: 0;
  border: none;
  img {
    width: 100%;
    height: 100%;
  }
  &:active {
    transform: scale(0.95);
  }
}
.sg-btn {
  text-align: center;
  font-size: 30rpx;
  font-weight: bold;
  color: #fff;
}

.number-box {
  width: 570rpx;
  height: 545rpx;
  padding: 10rpx;
  border-radius: 32rpx;
  border: 10rpx solid #fff;
  box-sizing: border-box;
  background: #19396a;
  position: relative;
  // display: flex;
  // flex-direction: column;
  // justify-content: flex-start;
  // align-items: center;
  overflow: scroll;
}
.pt44 {
  padding-top: 44rpx;
}
.num-tip {
  position: absolute;
  left: 10rpx;
  top: 10rpx;
  font-size: 24rpx;
  padding: 4rpx 0;
  text-align: center;
  background: #e06748;
  border-top-left-radius: 20rpx;
  border-top-right-radius: 20rpx;
  color: #fff;
  width: 550rpx;
  z-index: 5;
}
.number-gift-item {
  width: 530rpx;
  height: 124rpx;
  margin-bottom: 10rpx;
  border: 6rpx solid #fff;
  box-sizing: border-box;
  border-radius: 10rpx;
  background: #f6e38f;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.score-box {
  position: relative;
  width: 112rpx;
  height: 90rpx;
  margin: 0 100rpx 0 20rpx;
  img {
    width: 100%;
    height: 100%;
  }
  div {
    position: absolute;
    left: 50%;
    bottom: 6rpx;
    transform: translateX(-50%);
    font-size: 30rpx;
    font-weight: bold;
    color: #fff;
  }
}
.gift-box {
  width: 90rpx;
  height: 90rpx;
  margin-right: 40rpx;
  border-radius: 50%;
  border: 5rpx solid #fff;
  box-sizing: border-box;
  background: #e9ab4f;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  img {
    width: 75rpx;
    height: 75rpx;
  }
  .r-tag {
    position: absolute;
    right: -10rpx;
    bottom: -4rpx;
    width: 38rpx;
    height: 38rpx;
  }
  .gift-num {
    font-size: 20rpx;
    font-weight: bold;
    color: #fff;
    position: absolute;
    right: 10rpx;
    bottom: 10rpx;
    text-shadow: 0 0 10rpx #000;
  }
}

.gift-btn {
  width: 140rpx;
  height: 65rpx;
}
.gift-btn-receved {
  width: 119rpx;
  height: 55rpx;
  margin-left: 12rpx;
}
.tip {
  width: 500rpx;
  margin: 30rpx auto 0 auto;
  font-size: 30rpx;
  color: #fff;
  font-weight: bold;
  text-align: center;
}
.ft24 {
  font-size: 24rpx;
}
// cat

.cat-bg {
  width: 410rpx;
  height: 410rpx;
  margin-left: 80rpx;
  background-size: 392rpx 397rpx;
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/hi-cat.png");
  background-repeat: no-repeat;
  animation: welcome 1.4s steps(1) infinite;
  // animation-iteration-count:100;
}
@keyframes welcome {
  0%,
  29.4% {
    background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/hi-cat.png");
  }
  5.8%,
  23.5% {
    background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/hi-cat-act.png");
  }
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
.main-ad {
  position: fixed;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 200rpx;
}
.huawei {
  bottom: -120rpx;
}

.showAd {
  opacity: 1;
  z-index: 1;
}
.task-head {
  width: 100%;
  display: flex;
  justify-content: center;
  position: absolute;
  top: 80rpx;
  left: 0;
  .t-bg {
    width: 185rpx;
    height: 130rpx;
  }
  div,
  .t-gift {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -55%);
    font-size: 24rpx;
    font-weight: bold;
    color: #6b9aff;
  }
  .t-gift {
    width: 80rpx;
    height: 80rpx;
  }
}
.task-list {
  height: 464rpx;
  overflow: hidden;
  padding: 18rpx 10rpx;
}
.task-item {
  margin-bottom: 18rpx;
}
.task-box {
  width: 90rpx;
  height: 90rpx;
  margin-left: 20rpx;
  display: flex;
  justify-content: center;
  align-items: center;
  background-size: 100%;
  background-repeat: no-repeat;
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/icon-bg.png");
  img {
    width: 80rpx;
    height: 80rpx;
  }
}
.task-info {
  width: 240rpx;
  height: 90rpx;
  font-size: 24rpx;
  margin-right: 10rpx;
  & > div {
    width: 100%;
    padding-left: 20rpx;
    box-sizing: border-box;
  }
  .t-top {
    color: #925a4e;
    margin-bottom: 10rpx;
    font-weight: bold;
  }
  .t-bottom {
    color: #fff;
    background: #e9ab4f;
    display: flex;
    align-items: center;
    border-top-right-radius: 10rpx;
    border-bottom-right-radius: 10rpx;
    div {
      margin-right: 10rpx;
    }
  }
}
.get-task {
  width: 298rpx;
  height: 93rpx;
  margin: 50rpx auto 0 auto;
  display: block;
}
.hide {
  opacity: 0 !important;
}
.show {
  opacity: 1 !important;
}
section {
  position: relative;
}
</style>

