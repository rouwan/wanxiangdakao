<template>
  <div class="invate-box mask-bg ">
    <div class="i-body pos-r">
      <img class="bg w100 h100"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/invate-bg.png">
      <div class="list pos-a left-center">
        <div class="item w100 flex"
             v-for="(item,index) in userList"
             :key="index">
          <div class="item-index border-50 flex">{{index+1}}</div>
          <div class="item-head-box flex">
            <div class="item-head flex"
                 :class="{'head-bg':item.userId!=0}">
              <button v-if="item.userId==0"
                      class="item-empty w100 h100"
                      open-type="share"
                      plain="true">
                <img class="w100 h100"
                     src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/empty-btn.png">
              </button>
              <img v-else
                   :src="item.headImgUrl">
            </div>
            <div class="item-name">{{item.nickname}}</div>
          </div>

          <div class="item-gift flex border-50">
            <img src="/static/icon/m-box.png">
          </div>
          <div class="item-stauts flex">
            <button v-if="item.status==0&&item.userId!=0"
                    open-type="share"
                    plain="true"
                    @click="hideForShareFn(index)"
                    class="get-btn">
              <img class="w100 h100"
                   src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/get-btn.png">
            </button>
            <img v-else-if="item.status==0&&item.userId==0"
                 class="btn-unable"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/get-btn-unable.png">
            <img v-else
                 class="receved-btn"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved-txt.png">
          </div>
        </div>

      </div>

      <img class="close-btn pos-a"
           @click="close"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/close-btn.png">
      <img class="box pos-a"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/box.png">
      <button class="invate-btn left-center pos-a"
              open-type="share"
              plain="true">
        <img class="w100 h100"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/invate-btn.png">
      </button>
    </div>
    <get-gift v-if="showGetGift"
              @close="showGetGift=false"
              :giftList="gift"></get-gift>
  </div>
</template>

<script>
import getGift from "@/components/gift/get-gift";

export default {
  data() {
    return {
      showGetGift: false,
      userList: [],
      hideForShare: -1,
      gift: [
        {
          url: "/static/icon/m-box.png",
          name: "中宝箱",
          num: 1
        }
      ]
    };
  },
  components: {
    "get-gift": getGift
  },
  methods: {
    close() {
      this.$emit("close");
    },
    initUserList() {
      let list = [];
      for (let i = 0; i < 100; i++) {
        list.push({
          headImgUrl: "",
          userId: 0,
          nickName: "",
          status: 0
        });
      }
      this.userList = list;
    },
    hideForShareFn(index) {
      this.hideForShare = index;
    },
    getUserList() {
      this.fly.post("friend/getFriendList").then(res => {
        for (let i = 0; i < res.data.Result.length; i++) {
          this.userList[i] = res.data.Result[i];
        }
      });
    }
  },
  onShow() {
    if (this.hideForShare != -1) {
      this.fly
        .post("friend/receiveFriendGood", {
          userId: this.userList[this.hideForShare].userId,
          goodType: 5,
          goodNum: 1
        })
        .then(() => {
          this.showGetGift = true;
          this.userList[this.hideForShare].status = 1;
          this.hideForShare = -1;
        });
    }
  },
  mounted() {
    this.initUserList();
    this.getUserList();
  }
};
</script>

<style lang="scss" scoped>
@import "../../scss/layout.scss";

.i-body {
  width: 658rpx;
  height: 966rpx;
  margin: 70rpx auto 20rpx auto;
}
.list {
  top: 330rpx;
  width: 580rpx;
  height: 510rpx;
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
.item-index {
  margin-left: 30rpx;
  width: 46rpx;
  height: 46rpx;
  background: linear-gradient(180deg, #48eee9, #28a8f2, #0f7ff1);
  color: #fff;
  font-size: 26rpx;
}
.item-head-box{
  width: 170rpx;
  flex-direction: column;
  div {
    font-size: 30rpx;
    color: #4d4d4d;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    text-align: center;
  }
}
.item-head {
  width: 90rpx!important;
  height: 90rpx;
  border-radius: 12rpx;
  flex-direction: column;
  margin-top: 10rpx;
  margin-bottom: 4rpx;
  & > img {
    width: 75rpx;
    height: 75rpx;
    border-radius: 12rpx;
  }
}
.head-bg {
  background: linear-gradient(180deg, #48eee9, #28a8f2, #0f7ff1);
}
.item-empty {
  width: 86rpx;
  height: 92rpx;
}
.item-gift {
  margin-right: 50rpx;
  width: 108rpx;
  height: 108rpx;
  background: linear-gradient(180deg, #27f099, #219dd2);
  img {
    width: 90rpx;
    height: 90rpx;
  }
}
.item-status {
  width: 146rpx;
}

.get-btn,.btn-unable {
  width: 146rpx;
  height: 75rpx;
  border-radius:40rpx;
  box-shadow:5rpx 8rpx 10rpx -2rpx rgba(107, 107, 107, 0.6)
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
.box {
  width: 178rpx;
  height: 170rpx;
  top: 160rpx;
  right: -20rpx;
}
.invate-btn {
  width: 172rpx;
  height: 66rpx;
  bottom: 30rpx;
}
</style>
