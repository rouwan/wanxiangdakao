<template>
  <div class="m-room-list">
    <img class="entry-bg"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-test.png">
    <div class="box">
      <!-- :scroll-top="messageIndex*37" -->
      <div class="link-menu"
           :class="{show:!showAdBox}"
           @click="showMenuFn">
        <div class="et-img bg-ad_btn"></div>
      </div>
      <img class="friend-rank"
           @click="toFriendRank"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/friend-rank.png">
      <scroll-view class="season-tag"
                   :scroll-y="true"
                   :scroll-with-animation="true"
                   :scroll-into-view="'id'+messageIndex">
        <cover-view class="season-mask"
                    @click.stop="toTalkRoom"></cover-view>
        <!-- {{season.year}}年{{season.month}}月赛季 -->
        <div class="tag-inner flex"
             v-if="messageListStorage&&messageListStorage.length>0"
             v-for="(item,index) in messageListStorage"
             :key="index"
             :id="'id'+index">

          <img v-if="item.type==0"
               :src="item.headImgUrl">
          <div v-if="item.type==0">:</div>
          <div v-if="item.type==0"
               class="tag-txt">
            {{item.message}}
          </div>
          <div class="top flex"
               v-if="item.type==1">
            恭喜
            <img :src="item.headImgUrl">
            <div>{{item.nickname}}</div>
            登顶
            <img :src="IMGHEAD+item.highParagraphImage">
            <div>最强王者</div>
          </div>
        </div>
        <!-- <div v-else>暂无发言</div> -->
      </scroll-view>

      <div class="text pos-a flex left-center">今天我答对了
        <div>{{rightNum}}题</div>
      </div>

      <!-- 模式选择 -->
      <img class="hybird pos-a left-center"
           hover-class="click-act"
           @click="toHybird"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/hybird.png">
      <img class="group pos-a left-center"
           hover-class="click-act"
           @click="toGroup"
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/3v3.png">

    </div>
    <!-- <div class="m-r-content">
      <div class="u-r-head flex">
        <div class="u-r-num op0">
          房间数12/26
        </div>
        <div class="u-btn-box">
          <div class="u-btn u-btn-green"
               @click="createdRoom">创建房间</div>
          <div class="u-btn u-btn-red"
               @click="quickStart">快速加入</div>
        </div>
      </div>

      <div class="u-empty"
           v-if="roomList.length==0">
        <img class="u-empty-img"
             src="https://ggbsq.xingpanwang.com/renren-

fast/smallUploadSource/vientianeTestMobileAsset/images/public/list-empty.png">
        <div class="u-empty-word">暂时没有房间</div>
      </div>

      <div class="u-r-card flex"
           v-if="roomList.length>0"
           v-for="(room,index) in roomList[currentPage]"
           :key="index">
        <div class="u-card-left flex">
          <div class="u-card-tt">房间号</div>
          <div class="u-card-id">{{room.id}}</div>
        </div>
        <div class="u-card-right flex">
          <div class="u-card-right-left flex">
            <div class="u-card-name">{{room.roomMasterName}}的房间</div>
            <div class="u-card-info">房间人数：{{room.userNum}}/4</div>
          </div>
          <div class="u-card-right-right">
            <div class="u-join-btn flex"
                 v-if="room.status==0&&room.userNum<4"
                 @click="checkRoomStatus(room.id)">加入</div>
            <div v-if="room.status==0&&room.userNum==4"
                 class="u-card-status">满人</div>
            <div v-if="room.status==1"
                 class="u-card-status">游戏中</div>
          </div>
        </div>
      </div>
      <div class="u-page-control flex">
        <img src="/static/icon/pre-page.png"
             @click="prePage">
        <img src="/static/icon/next-page.png"
             @click="nextPage">
      </div>
    </div> -->
    <!-- <img v-if="deviceInfo.brand!='HUAWEI'"
         class="moon"
         src="https://ggbsq.xingpanwang.com/renren-

fast/smallUploadSource/vientianeTestMobileAsset/images/public/moon.png"> -->
    <ad-box-new v-if="showAdBox"
                :tryGameName="tryGameName"
                @close="closeAdBox"></ad-box-new>
    <div class="ad-wrap">
      <ad unit-id="adunit-c0021eb44a5dc3e2"></ad>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { getRondom } from "@/utils/index";
import { shareInfoForRoom } from "@/config/dataFile";
import adBoxNew from "@/pages/entry/components/adBoxNew";

export default {
  onShareAppMessage: function(res) {
    let index = getRondom(0, shareInfoForRoom.length-1);
    return {
      title: this.appInfo.nickname + shareInfoForRoom[index].title,
      imageUrl: shareInfoForRoom[index].imgUrl,
      path: `/pages/entry/index?userId=${this.appInfo.userId}&roomId=0`
    };
  },
  components: {
    "ad-box-new": adBoxNew
  },
  data() {
    return {
      roomList: [],
      hideForRoute: false, //区分缩小onHide 和 路由切换的onHide周期
      // 分页信息
      currentPage: 0,
      pageSize: 5,
      pageNum: 0,
      messageIndex: 0,
      showAdBox: false,
      rightNum: 0,
      tryGameName: ""
    };
  },
  computed: {
    ...mapState({
      IMGHEAD: state => state.IMGHEAD,
      deviceInfo: state => state.deviceInfo,
      connected: state => state.socketStore.connected,
      dataWrap: state => state.socketStore.dataWrap,
      appInfo: state => state.appInfo,
      messageListStorage: state => state.messageListStorage
    })
  },
  watch: {},
  methods: {
    ...mapMutations({
      connect: "socketStore/connect",
      addMessageListener: "socketStore/addMessageListener",
      addOpenedListener: "socketStore/addOpenedListener",
      removeMessageListener: "socketStore/removeMessageListener",
      removeOpenedListener: "socketStore/removeOpenedListener",
      send: "socketStore/send",
      close: "socketStore/close",
      setMessageBus: "socketStore/setMessageBus",
      setPageType: "socketStore/setPageType",
      setTryGameName: "setTryGameName"
    }),
    showMenuFn() {
      this.showAdBox = true;
      this.shake();
    },
    toHybird() {
      this.hideForRoute = true;
      this.$router.push("/roomPages/pages/hybird-room?type=hybird");
    },
    toGroup() {
      this.hideForRoute = true;
      this.$router.push("/roomPages/pages/group-room?type=group");
    },
    closeAdBox() {
      this.showAdBox = false;
    },
    shake() {
      wx.vibrateShort({
        success: () => {
          console.log("点击震动");
        }
      });
    },
    startMsgTimer() {
      clearInterval(this.msgTimer);
      this.msgTimer = setInterval(() => {
        if (this.messageIndex == this.messageListStorage.length - 1) {
          this.messageIndex = 0;
        } else {
          this.messageIndex++;
        }
      }, 3000);
    },
    stopMsgTimer() {
      clearInterval(this.msgTimer);
    },
    toTalkRoom() {
      this.hideForRoute = true;
      this.$router.push("/pages/talkRoom/index");
    },
    handleMessage(message) {
      if (message.Type === 12) {
        this.initData(message);
      }

      if (message.Type === 13) {
        console.log(message);
      }

      if (message.Type === 1) {
        //进入房间
        this.setMessageBus(message);
        this.hideForRoute = true;
        console.log("mess-1", message);
        if (message.Result.room.modeType == 1) {
          this.$router.push("/roomPages/pages/hybird-room?type=messageBus");
        } else {
          this.$router.push("/roomPages/pages/group-room?type=messageBus");
        }
      }

      if (message.Type === 14) {
        //快速加入
        wx.showToast({ title: "暂无适合的房间", icon: "none" });
      }

      if (message.Type === 16) {
        this.handleCheckRoomStatus(message);
      }
    },
    initData(message) {
      let page = 0,
        total = 0,
        data = message.Result;
      total = data.length;
      this.pageNum = Math.ceil(total / this.pageSize);
      this.roomList = [];
      for (let i = 0; i < this.pageNum; i++) {
        this.roomList.push(data.splice(0, this.pageSize));
      }

      for (
        let j = this.currentPage;
        j <= this.currentPage && this.currentPage != 0;
        j--
      ) {
        if (this.roomList[j] && this.roomList[j].length > 0) {
          this.currentPage = j;
          break;
        }
      }
    },
    routerRoomList() {
      this.send("{'type':12,'data':1}", "进入大厅");
    },
    createdRoom() {
      //进入房间页 在发送创建请求
      this.hideForRoute = true;
      this.$router.push("/roomPages/pages/hybird-room?type=create");
    },
    quickStart() {
      this.send(`{'type':14,'data':1}`, "快速加入"); //先检测是否有可加入的房间
    },
    checkRoomStatus(id) {
      if (id != 0) {
        this.send(`{'type':16,'data':'${id}'}`, "判断房间状态");
      }
    },
    handleCheckRoomStatus(message) {
      if (message.Result == 1) {
        wx.showToast({ title: "游戏已开始", icon: "none" });
        this.setPageType(0);
        this.close();
        let t1 = setTimeout(() => {
          this.$router.back();
          clearTimeout(t1);
        }, 1000);
      } else if (message.Result == 3) {
        wx.showToast({ title: "房间人满", icon: "none" });
        this.setPageType(0);
        this.close();
        let t2 = setTimeout(() => {
          this.$router.back();
          clearTimeout(t2);
        }, 1000);
      } else if (message.Result == -1) {
        wx.showToast({ title: "房间不存在", icon: "none" });
        this.setPageType(0);
        this.close();
        let t3 = setTimeout(() => {
          this.$router.back();
          clearTimeout(t3);
        }, 1000);
      }
    },
    clearPage() {
      this.removeMessageListener(this.handleMessage);
      this.removeOpenedListener(this.listerCard);
      if (!this.hideForRoute) {
        this.close();
      }
      delete this.$root.$mp.query;
    },
    prePage() {
      if (this.currentPage > 0) {
        this.currentPage--;
      }
    },
    nextPage() {
      if (this.currentPage < this.pageNum - 1) {
        this.currentPage++;
      }
    },
    toFriendRank() {
      this.$router.push("/pages/rankPage/index?friend='true");
    },
    listerCard() {
      if (this.$root.$mp.query && this.$root.$mp.query.roomId) {
        let id = this.$root.$mp.query.roomId;
        this.checkRoomStatus(id);
      } else {
        // this.createdRoom();
      }
    },
    checkGift() {
      /* 判定是否试玩成功 */
      let t1, t2;
      wx.getStorage({
        key: "tryTimeStart",
        success: res => {
          t1 = res.data;
          t2 = new Date().getTime();
          if (t1) {
            console.log(t2 - t1.time);
            if (t2 - t1.time >= 20000) {
              console.log("试玩成功--list");
              // 试玩成功
              this.showAdBox = true;
              let t0 = setTimeout(() => {
                console.log("t1",t1);
                this.setTryGameName(t1.name);
                this.tryGameName = t1.name;
                console.log(this.tryGameName)
              }, 500);
            } else {
              console.log("失败");
              //试玩失败
              wx.showToast({
                title: "未试玩20秒",
                icon: "none"
              });
            }
          }
        },
        fail: () => {
          console.log("未试玩游戏,不执行后续操作");
          t1 = 0;
        }
      });

      wx.removeStorage({ key: "tryTimeStart" });
    },
    getRightNum() {
      this.fly.post("/room/rankHallData").then(res => {
        this.rightNum = res.data.Result.todayRightSubNum;
      });
    }
  },
  onShow() {
    this.checkGift();
    this.getRightNum();
    this.hideForRoute = false;
    this.setPageType(1); //标识当前页面
    let t1 = setTimeout(() => {
      if (!this.connected && this.appInfo.userId) {
        this.connect({ type: 1 });
        this.addMessageListener(this.handleMessage);
        this.addOpenedListener(this.listerCard);
      }
      // } else if (this.appInfo.userId) {
      //   this.addMessageListener(this.handleMessage);
      //   // this.routerRoomList();
      // }
      this.startMsgTimer();
      clearTimeout(t1);
    }, 500);

    // this.fly.post("/room/getHeadData").then(res=>{
    //   console.log(res)
    // })
  },
  onHide() {
    this.stopMsgTimer();
    if (!this.hideForRoute) {
      this.send(`{'type':17,'data':'0'}`, "隐藏告知服务端");
    }
    this.setPageType(0); //标识当前页面
    this.clearPage();
  },
  onUnload() {
    this.stopMsgTimer();
    this.setPageType(0); //标识当前页面
    this.removeMessageListener(this.handleMessage);
    this.removeOpenedListener(this.listerCard);
    delete this.$root.$mp.query;
    // this.clearPage();
    this.close();
    this.currentPage = 0;
  }
};
</script>

<style lang="scss" scoped>
@import "../../scss/et-img-map.scss";
@import "../../scss/layout.scss";

.op0 {
  opacity: 0;
}

.m-r-content {
  padding: 0 44rpx;
  box-sizing: border-box;
  color: #fff;
}
.u-r-head {
  justify-content: space-between;
  height: 144rpx;
}
.u-r-num {
  font-size: 30rpx;
}
.u-btn-box {
  justify-content: flex-end;
}
.u-btn {
  width: 150rpx;
  height: 54rpx;
  font-size: 26rpx;
  font-weight: bold;
  margin-right: 18rpx;
  border-radius: 10rpx;
  &:last-child {
    margin-right: 0;
  }
}

.u-r-card {
  width: 664rpx;
  height: 140rpx;
  border-radius: 16rpx;
  background: #4a427f;
  margin-bottom: 20rpx;
}
.u-card-left {
  flex-direction: column;
  width: 140rpx;
  height: 106rpx;
  border-right: 2rpx solid #7c74b9;
  div {
    text-align: center;
  }
}
.u-card-tt,
.u-card-info {
  font-size: 24rpx;
  font-weight: bold;
  color: #a69ee3;
}
.u-card-tt {
  margin-bottom: 10rpx;
}
.u-card-id {
  font-family: 32rpx;
  font-weight: bold;
  color: #7efce4;
}
.u-card-right {
  width: 530rpx;
  padding: 0 30rpx;
  justify-content: space-between;
}
.u-card-right-left {
  flex-direction: column;
  align-items: flex-start;
}
.u-card-name {
  font-size: 30rpx;
  font-weight: bold;
  color: #fff;
  margin-bottom: 20rpx;
  width: 300rpx;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.u-card-right-right {
  width: 140rpx;
  text-align: center;
  font-size: 30rpx;
  color: #a69ee3;
}
.u-join-btn {
  width: 136rpx;
  height: 74rpx;
  font-size: 30rpx;
  color: #fff;
  background: #85d85d;
  border-radius: 10rpx;
}
.u-card-status {
  font-weight: bold;
}
.u-page-control {
  position: absolute;
  top: 970rpx;
  left: 50%;
  transform: translateX(-50%);
  width: 610rpx;
  justify-content: space-between;
  img {
    width: 165rpx;
    height: 44rpx;
  }
}
.u-empty {
  position: absolute;
  left: 50%;
  top: 200rpx;
  transform: translateX(-50%);
}
.u-empty-img {
  margin: 0 auto;
  display: block;
  width: 360rpx;
  height: 386rpx;
}
.u-empty-word {
  margin-top: 40rpx;
  font-size: 54rpx;
  color: #a69ee3;
  text-align: center;
}

.entry-bg {
  position: fixed;
  left: 0;
  top: 0;
  width: 750rpx;
  // height: 1624rpx;
  height: 100%;
  z-index: 10;
  // margin-top: -120rpx;
}

.box {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 10;
}
.season-tag {
  position: absolute;
  z-index: 10;
  left: 50%;
  transform: translateX(-50%);
  top: 60rpx;
  width: 500rpx;
  height: 74rpx;
  padding-left: 20rpx;
  box-sizing: border-box;

  overflow: hidden;
  font-size: 26rpx;
  color: #fff;
  background: rgba(#000, 0.5);
  border-radius: 1000rpx;
  .season-mask {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    z-index: 2;
  }
  & > div {
    padding-left: 20rpx;
    box-sizing: border-box;
  }
  .tag-inner {
    justify-content: flex-start;
    width: 100%;
    height: 100%;
    .tag-txt {
      width: 420rpx;
      text-overflow: ellipsis;
      white-space: nowrap;
      overflow: hidden;
    }
    img {
      width: 50rpx;
      height: 50rpx;
      margin-right: 10rpx;
      border-radius: 50%;
    }
  }
}
.top {
  width: 100%;
  height: 100%;
  box-sizing: border-box;

  font-size: 24rpx;
  color: #fff;
  font-weight: bold;
  img {
    margin: 0 10rpx;
    &:nth-of-type(1) {
      width: 40rpx;
      height: 40rpx;
      box-sizing: border-box;
      border: 4rpx solid #fff;
      border-radius: 50%;
    }
    &:nth-of-type(2) {
      width: 45rpx;
      height: 45rpx;
    }
  }
  div {
    width: 5em;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #82fbfb;
    text-align: center;
  }
}

.link-menu {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  position: absolute;
  top: 50rpx;
  left: 0;
  height: 86rpx;
  width: 80rpx;
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
  border-top-left-radius: 40rpx;
  border-bottom-left-radius: 40rpx;
  opacity: 0;
  transform: rotate(180deg) translateX(80rpx);
  transition: all 0.2s ease;
  background: rgba(255, 255, 255, 0.5);
  z-index: 10;
  img {
    width: 69rpx;
    height: 81rpx;
  }
}
.show {
  opacity: 1;
  transform: rotate(180deg) translateX(0);
}
.friend-rank {
  position: absolute;
  right: 10rpx;
  top: 40rpx;
  width: 102rpx;
  height: 100rpx;
}
.text {
  top: 170rpx;
  font-size: 28rpx;
  color: #fff;
  div {
    font-size: 30rpx;
    color: #7ef8e0;
  }
}
.hybird,
.group {
  width: 675rpx;
  height: 322rpx;
  z-index: 5;
}
.hybird {
  top: 260rpx;
}
.group {
  top: 580rpx;
}
.click-act {
  transform: translateX(-50%) scale(0.95);
}
</style>
