<template>
  <div class="invate-box mask-bg ">
    <div class="i-body pos-r">
      <img class="bg w100 h100"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/ad-bg.png">
      <div class="list pos-a left-center">
        <div class="item w100 flex"
             v-for="(item,index) in giftList"
             :key="index">
          <div class="item-head flex">
            <img :src="imgHead+item.imgName">
          </div>
          <div class="item-name">{{item.name}}</div>
          <div class="item-gift flex border-50">
            <img src="/static/icon/s-box.png">
          </div>
          <div class="item-status flex">
            <button v-if="item.type&&!item.status"
                    open-type="share"
                    plain="true"
                    @click="setHideIndex(index)"
                    class="get-btn">
              <img class="w100 h100"
                   src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/get-btn-y.png">
            </button>
            <navigator v-else-if="!item.type"
                       target="miniProgram"
                       open-type="navigate"
                       :app-id="item.appId"
                       @success="countOutNum(item.name,1,item.status,5,1)"
                       @fail="countOutNumFail">
              <img class="try-btn"
                   src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/try-btn.png">
            </navigator>

            <img v-else
                 class="receved-btn"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved-txt.png">
          </div>
        </div>

      </div>

      <div class="like-box flex pos-a">
        <div class="l-item"
             v-for="(item,index) in likeList"
             :key="index">
          <navigator target="miniProgram"
                     class="flex"
                     open-type="navigate"
                     :app-id="item.appId"
                     @success="countOutNum(item.name,0,0,0,0)"
                     @fail="countOutNumFail">
            <img :src="imgHead+item.imgName">
          </navigator>
          <div class="ad-name">{{item.name}}</div>
        </div>
      </div>
      <img class="close-btn pos-a"
           @click="close"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/close-btn.png">
    </div>
    <get-gift v-if="showGetGift"
              @close="showGetGift=false"
              :giftList="gift"></get-gift>
  </div>
</template>

<script>
import getGift from "@/components/gift/get-gift";
export default {
  props: ["tryGameName"],
  components: {
    "get-gift": getGift
  },
  data() {
    return {
      hideIndex: -1,
      imgHead:
        "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/out-link/",
      checkAdList: [], //放入缓存 判断当天是否领取过
      giftList: [
        {
          name: "我要当妖精",
          appId: "wx6b751ee0ef7702fa",
          path:"?c=wxdk&ald_media_id=6903&ald_link_key=6d99e94c246cc75e&ald_position_id=0",
          imgName: "yaojing.png",
          type: 0, //未试玩
          status: 0, //未领取
          goodType: 4,
          goodNum: 1
        },
        {
          name: "欢乐步数换钱花",
          appId: "wxea56b2d76c30cd2c",
          path:
            "pages/index/index?ald_media_id=14441&ald_link_key=cf8dac4db27bb20b",
          imgName: "huanqianhua.jpg",
          type: 0, //未试玩
          status: 0, //未领取
          goodType: 4,
          goodNum: 1
        },
        {
          name: "汽车冲鸭",
          appId: "wx4aa94d3af51b01ce",
          path: "pages/index?source=wxdk&ald_media_id=5220&ald_link_key=e9340db94a15efe9",
          imgName: "qiche.jpg",
          type: 0, //未试玩
          status: 0,
          goodType: 4,
          goodNum: 1
        },
        {
          name: "集步换礼",
          appId: "wxe4f198bc849bfae1",
          path: "hc_step/pages/index/index?a=wanxiangdakao&ald_media_id=15744&ald_link_key=af0c2038fe116c8f",
          imgName: "jibu.jpg",
          type: 0, //未试玩
          status: 0,
          goodType: 4,
          goodNum: 1
        }
      ],
      likeList: [
         {
          name: "重力碰碰车",
          appId: "wx6abcab3f1b8c0a1d",
          path: "",
          imgName: "pengpengche.jpg"
        },
        {
          name: "弹射奇兵",
          appId: "wxded312966e93a87e",
          path: "index?channelId=10025",
          imgName: "qibing.png"
        },
        {
          name: "欢乐小黄胖",
          appId: "wx08a22523d259abdd",
          path: "",
          imgName: "xiaohuangpang.jpg"
        },
        {
          name: "豆小游",
          appId: "wxca338da970e994ba",
          path: "page/index/index",
          imgName: "douxiaoyou.png"
        },

        {
          name: "生肖来了",
          appId: "wxa059f0becd1b47d4",
          path: "",
          imgName: "shengxiao.png"
        }
      ],
      showGetGift: false,
      gift: [
        {
          url: "/static/icon/s-box.png",
          name: "小宝箱",
          num: 1
        }
      ]
    };
  },
  watch: {
    tryGameName(value) {
      if (value != "" && this.checkAdList.length > 0) {
        for (let i = 0; i < this.checkAdList.length; i++) {
          if (this.giftList[i].name == value) {
            this.checkAdList[i].type = 1;
            this.giftList[i].type = 1;
            this.fly.post("user/userToXcx", {
              name: this.giftList[i].name,
              type: 1,
              status: 0,
              goodType: 4,
              goodNum: 1
            });
            this.setCheckAdList();
            break;
          }
        }
      }
    }
  },
  methods: {
    close() {
      this.$emit("close");
    },
    setHideIndex(index) {
      this.hideIndex = index;
    },
    getGiftFn(index) {
      console.log("领取");
      this.showGetGift = true;
      this.giftList[index].status = 1;
      this.checkAdList[index].status = 1;
      this.setCheckAdList();
      this.fly.post("user/userToXcx", {
        name: this.giftList[index].name,
        type: 1,
        status: 1,
        goodType: 4,
        goodNum: 1
      });
      this.hideIndex = -1;
    },
    initCheckAdList() {
      for (let i = 0; i < this.giftList.length; i++) {
        this.checkAdList[i] = {
          day: new Date().getDate(),
          type: 0,
          status: 0
        };
      }
      this.setCheckAdList();
    },
    setCheckAdList() {
      wx.setStorage({
        key: "checkAdList",
        data: this.checkAdList
      });
    },
    checkStorage() {
      wx.getStorage({
        key: "checkAdList",
        success: res => {
          let isOld = false;
          this.checkAdList = res.data;
          if (new Date().getDate() != this.checkAdList[0].day) {
            //判断缓存是否过期
            isOld = true;
          }
          if (isOld) {
            this.initCheckAdList();
          } else {
            this.giftList.forEach((item, index) => {
              item.status = this.checkAdList[index].status;
              item.type = this.checkAdList[index].type;
            });
          }
        },
        fail: () => {
          this.initCheckAdList();
        }
      });
    },

    countOutNum(name, type, status, goodType, goodNum) {
      /* name -程序名称
         type -0，未试玩  1，已经试玩  
         status -0 未领取 1，已领取    
         首次调用试玩 -判定20s 后重新回到程序
         判定20s 调用该方法，设置体验时间t1 
         下次进入程序 判断t2 大于t1 则弹窗弹出广告弹窗，获得奖励，更新对应列表
      */

      if (type == 1) {
        //需要判定时间
        console.log("set tryTimeStar");
        let obj = {
          time: new Date().getTime(),
          name: name
        };
        wx.setStorage({
          key: "tryTimeStart",
          data: obj
        });
      } else {
        this.fly.post("user/userToXcx", {
          name: name,
          type: type,
          status: status,
          goodType: goodType,
          goodNum: goodNum
        });
      }
    }
  },
  onShow() {
    if (this.hideIndex != -1) {
      this.getGiftFn(this.hideIndex);
    }
  },
  mounted() {
    this.checkStorage();
  }
};
</script>

<style lang="scss" scoped>
@import "../../../scss/layout.scss";

.i-body {
  width: 658rpx;
  height: 1050rpx;
  margin: 70rpx auto 20rpx auto;
}
.list {
  top: 240rpx;
  width: 580rpx;
  height: 540rpx;
  overflow: scroll;
  -webkit-overflow-scrolling: touch;
}
.item {
  height: 150rpx;
  background: #efebda;
  border-radius: 20rpx;
  justify-content: flex-start;
  position: relative;
  margin-bottom: 16rpx;
  &::after {
    content: "";
    width: 580rpx;
    height: 150rpx;
    position: absolute;
    border-radius: 40rpx;
    left: 50%;
    transform: translateX(-50%);
    bottom: -8rpx;
    background: #077ca0;
    z-index: -1;
  }
}

.item-head {
  width: 120rpx;
  height: 120rpx;
  border-radius: 12rpx;
  background: #fff;
  margin: 0 10rpx 0 20rpx;
  & > img {
    width: 100rpx;
    height: 100rpx;
    border-radius: 12rpx;
  }
}
.item-name {
  font-size: 30rpx;
  font-weight: bold;
  text-align: center;
  width: 130rpx;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #4d4d4d;
}
.item-gift {
  margin: 0 20rpx;
  width: 90rpx;
  height: 90rpx;
  background: linear-gradient(180deg, #27f099, #219dd2);
  img {
    width: 80rpx;
    height: 80rpx;
  }
}
.item-status {
  width: 160rpx;
  navigator {
    width: 157rpx;
    height: 58rpx;
  }
}

.get-btn,
.try-btn {
  width: 157rpx;
  height: 62rpx;
}
.receved-btn {
  width: 119rpx;
  height: 55rpx;
  transform: rotate(-15deg);
}
.close-btn {
  width: 92rpx;
  height: 92rpx;
  top: 10rpx;
  right: -20rpx;
}

.like-box {
  width: 100%;
  bottom: 40rpx;
  left: 0;
}
.l-item {
  width: 100rpx;
  margin-right: 16rpx;
  &:last-child {
    margin-right: 0;
  }
  navigator {
    width: 100rpx;
    height: 100rpx;
    background: #fff;
    border-radius: 16rpx;
    img {
      border-radius: 12rpx;
      width: 85rpx;
      height: 85rpx;
    }
  }
}

.ad-name {
  width: 100rpx;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: 24rpx;
  color: #fff;
  text-align: center;
}
</style>
