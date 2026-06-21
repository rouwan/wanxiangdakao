<template>
  <div class="m-menu">
    <div class="mask-bg"
         @click="close"></div>
    <div class="menu-box"
         :class="{show:showBox}">
      <div class="menu-box-head">
        <div class="head-box" @click="toUserInfo">
          <img v-if="appInfo.headImgUrl"
               :src="appInfo.headImgUrl">
          <img v-else
               src="/static/default-head.png">
        </div>
        <div class="menu-info">
          <div>{{appInfo.nickname}}</div>
          <div class="txt">{{appInfo.province||"宇宙"}} {{appInfo.city||"火星"}}</div>
        </div>
      </div>
      <div class="menu-box-list">
        <div :class="{hasTip:false}">
          <img @click="showInvateBoxFn(true)"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/invate.png">
        </div>
        <div :class="{hasTip:status.isFinishDayTask==1}">
          <img @click="showGiftBox(0)"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/task.png">
        </div>
        <div :class="{hasTip:status.isDaySign==1||status.isHitCat==1||status.isLuckNumber==1}">
          <img @click="showGiftBox(1)"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/dayGift.png">
        </div>
        <div :class="{hasTip:status.isBookUpLevel>0}">
          <img @click="toBook"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/book.png">
        </div>
        <div :class="{hasTip:appInfo.money>50&&hasTipLottery}">
          <img @click="toLottery"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/lottery.png">
        </div>
        <div :class="{hasTip:false}">
          <img @click="showPack=true"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/pack.png">
        </div>
        <div :class="{hasTip:false}">
          <img @click="showGodRank"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/god.png">
        </div>
        <div>
          <img @click="toProject"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/project.png">
        </div>
        <div :class="{hasTip:status.emailNoReadNum>0}">
          <img @click="showEmail=true"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/email.png">
        </div>
        <div :class="{hasTip:false}">
          <img @click="showSet"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/setting.png">
        </div>
      </div>
    </div>

    <email-modal v-if="showEmail"
                 @close="showEmail=false">
    </email-modal>

    <setting-modal v-if="showSetting"
                   @radioChangeOne="handleRadioOneChange"
                   @radioChangeTwo="handleRadioTwoChange"
                   @close="showSetting=false">
    </setting-modal>

    <pack-modal v-if="showPack"
                @close="showPack=false"></pack-modal>

                <invate-box v-if="showInvateBox" @close="showInvateBoxFn(false)"></invate-box>
                
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import settingModal from "@/components/modal/settingModal";
import emailModal from "@/components/modal/emailModal";
import packModal from "@/components/modal/packModal";
import invateBox from "@/components/public/invateBox";

export default {
  props:["status"],
  components: {
    "setting-modal": settingModal,
    "email-modal": emailModal,
    "pack-modal": packModal,
    "invate-box":invateBox
  },
  data() {
    return {
      showBox: false,
      showEmail: false,
      showSetting: false,
      showPack: false,
      showInvateBox:false,
    };
  },
  computed: {
    ...mapState(["appInfo", "globalBgPlayer", "globalSoundPlayer","hasTipLottery"])
  },
  methods: {
    ...mapMutations(["setMusic","setHasTipLottery"]),
    toUserInfo() {
      this.$router.push(
        `/playerPages/pages/index?userId=${this.appInfo.userId}`
      );
    },
    showInvateBoxFn(type){
      this.showInvateBox=type;
      this.$emit("invateBox",type);
    },
    toTalk() {
      this.$router.push(`/pages/talkRoom/index`);
    },
    toBook() {
      this.$router.push(`/pages/book/index`);
    },
    toLottery() {
      this.$router.push(`/pages/lottery/index`);
      this.setHasTipLottery(false);
    },
    toProject() {
      this.$router.push(`/subjectFactoryPages/pages/index`);
    },
    showSet(event) {
      console.log(event);
      this.showSetting = true;
    },
    showGiftBox(type) {
      this.$emit("showModal",type);
    },
    showGodRank() {
      this.$router.push("/pages/rankPage/index?god=true");
    },
    unAble() {
      wx.showToast({ title: "暂未开放", icon: "none" });
    },
    setMusic(volume, state, type) {
      if (type == "bg") {
        this.globalBgPlayer.volume = volume;
      } else if (type == "sound") {
        this.globalSoundPlayer.volume = volume;
      }
      if (volume != 0 && type == "bg") {
        this.globalBgPlayer.volume = 0.5;
      }
      if (state && type == "bg") {
        this.globalBgPlayer.play();
      }
      wx.setStorage({
        key: `musicObj[${type}]`,
        data: state
      });
    },
    handleRadioOneChange(value) {
      if (value) {
        this.setMusic(1, true, "bg");
      } else {
        this.setMusic(0, false, "bg");
      }
    },
    handleRadioTwoChange(value) {
      if (value) {
        this.setMusic(1, true, "sound");
      } else {
        this.setMusic(0, false, "sound");
      }
    },
    closeEmail() {
      this.showEmail = false;
    },
    close() {
      this.showBox = false;
      setTimeout(() => {
        this.$emit("close");
      }, 200);
    }
  },
  mounted() {
    setTimeout(() => {
      this.showBox = true;
    }, 200);
  }
};
</script>

<style lang="scss" scoped>
.m-menu {
  z-index: 10;
}
.m-menu,
.mask-bg {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
}
.mask-bg {
  background: rgba(#000, 0.5);
}
.menu-box {
  position: absolute;
  left: 0;
  top: 120rpx;
  width: 563rpx;
  height: 988rpx;
  padding-left: 134rpx;
  box-sizing: border-box;
  transform: translateX(-600rpx);
  transition: all 0.2s ease;
  // padding: 15rpx;
  box-sizing: border-box;
  background-repeat: no-repeat;
  background-size: 563rpx 988rpx;
  background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/menu-box.png");
  color: #fff;
}

.show {
  transform: translateX(-63rpx);
}

.menu-box-head,
.menu-box-list {
  width: 306rpx;
  display: flex;
  justify-content: center;
  align-items: center;
}
.menu-box-head {
  height: 108rpx;
  margin-top: 34rpx;
  font-size: 30rpx;
  margin-bottom: 40rpx;
  border: 4rpx solid #218da8;
  box-sizing: border-box;
  border-radius: 20rpx;
  background: #fff;
  position: relative;
  background-image: repeating-linear-gradient(
    -45deg,
    #39f5f5,
    #39f5f5 15px,
    #2ce4e8 0,
    #2ce4e8 30px
  );
  .head-box {
    width: 120rpx;
    height: 120rpx;
    padding: 6rpx;
    border: 4rpx solid #218da8;
    box-sizing: border-box;
    background: #fff;
    border-radius: 50%;
    position: absolute;
    transform: translateY(-50%);
    top: 50%;
    left: -60rpx;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
    }
  }
  .menu-info {
    width: 100%;
    div {
      text-align: left;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
      color: #fff;
      box-sizing: border-box;
      &:first-child {
        padding: 4rpx 0 4rpx 80rpx;
        font-size: 24rpx;
        background: #218da8;
      }
      &:last-child {
        width: 120rpx;
        text-align: center;
        padding: 2rpx 10rpx;
        border: 2rpx solid #000;
        border-radius: 10rpx;
        box-sizing: border-box;
        font-size: 20rpx;
        background: #71dc5d;
        margin-left: 80rpx;
      }
    }
    .txt {
      margin-top: 10rpx;
    }
  }
}
.menu-box-list {
  position: absolute;
  left: 110rpx;
  top: 177rpx;
  box-sizing: border-box;
  flex-wrap: wrap;
  justify-content: space-around;
  div {
    width: 110rpx;
    height: 101rpx;
    margin-right: 60rpx;
    margin-bottom: 50rpx;
    position: relative;
    img {
      width: 100%;
      height: 100%;
    }
    &:nth-of-type(2n + 2) {
      margin-right: 0;
    }
  }
  .hasTip {
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
}

</style>
