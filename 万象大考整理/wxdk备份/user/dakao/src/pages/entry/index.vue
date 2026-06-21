<template>
  <div class="out-box">
    <!-- <img class="entry-bg"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-bg.png"> -->
    <img class="entry-bg"
         lazy-load='true'
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-test.png">
    <div class="wrap">
      <div class="head-menu"
           :class="{'hasTip':hasTipMenu,show:!showMenu}"
           @click="showMenuFn('menu')">
        <div class="et-img bg-menu"></div>
      </div>
      <div class="link-menu"
           lazy-load='true'
           :class="{'hasTipAd':hasTipAd,show:!showAdBox}"
           @click="showMenuFn('ad')">
        <div class="et-img bg-ad_btn"></div>
      </div>

      <!-- 头部 -->
      <div class="head">
        <div class="gold-box">
          <img src="/static/gold.png"> {{appInfo.money}}
        </div>
        <scroll-view class="season-tag"
                     :scroll-y="true"
                     :scroll-with-animation="true"
                     :scroll-into-view="'id'+messageIndex">
          <cover-view class="season-mask"
                      @click.stop="toTalkRoom"></cover-view>
          <!-- {{season.year}}年{{season.month}}月赛季 -->
          <div class="tag-inner"
               v-if="messageList&&messageList.length>0"
               v-for="(item,index) in messageList"
               :key="index"
               :id="'id'+index">

            <img v-if="item.type==0"
                 lazy-load='true'
                 :src="item.headImgUrl">
            <div v-if="item.type==0">:</div>
            <div v-if="item.type==0"
                 class="tag-txt">
              {{item.message}}
            </div>
            <div class="top"
                 v-if="item.type==1">
              恭喜
              <img lazy-load='true'
                   :src="item.headImgUrl">
              <div>{{item.nickname}}</div>
              登顶
              <img lazy-load='true'
                   :src="IMGHEAD+item.highParagraphImage">
              <div>最强王者</div>
            </div>
          </div>

        </scroll-view>
      </div>
      <img class="star-bg"
           lazy-load='true'
           src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/star-bg.png">

      <div class="icon-box">
        <img lazy-load='true'
             @click="showDayGfit=true;"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/dayGift.png">
        <img lazy-load='true'
             @click="showInvateBox=true;"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/invate.png">
        <img lazy-load='true'
             @click="toLottery"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/lottery.png">
        <img lazy-load='true'
             @click="toRankList"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/rank.png">
      </div>
      <!-- 内容 -->
      <div class="content">
        <!-- 排位入口 -->
        <form @submit="submitFormId"
              report-submit="true">
          <!-- <button > 提交id</button> -->
          <button @getuserinfo="updateUserInfo($event)"
                  @click="rankStart"
                  form-type="submit"
                  open-type="getUserInfo"
                  lang="zh_CN"
                  hover-class="click-active"
                  class="rank"
                  plain="true">
            <div>
              <div class="et-img bg-rank"></div>
              <!-- 段位信息 -->
            </div>
          </button>
        </form>

        <div class="btn-box">
          <div class="btn-place"></div>
          <form @submit="submitFormId"
                report-submit="true">
            <!-- <button > 提交id</button> -->
            <button form-type="submit"
                    class="btn-group"
                    @click="toRoom"
                    hover-class="click-active"
                    plain="true">
              <div class="et-img bg-group"></div>
              <!-- 段位信息 -->
            </button>
          </form>
          <!-- <div class="btn-group"
               @click="toRoom"
               hover-class="click-active">
            <img src="/static/entry/group.png">
          </div> -->
          <div class="btn-rank"
               @click="toNewYear"
               hover-class="click-active">
            <div class="card">第{{newFoodCard}}关</div>
            <!-- <img class="btn-food"src="/static/btn-food.png"> -->
          </div>

        </div>
      </div>
      <!-- 登录页面 -->
      <div class="login-page sky-bg"
           v-if="isAuthorize=='0'">
        <img lazy-load='true'
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/logo.png"
             alt="">
        <button class="login-btn"
                @getuserinfo="getUserInfo($event)"
                open-type="getUserInfo"
                lang="zh_CN">

                
          <!--<img lazy-load='true' src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/login-btn.png" alt=""> -->
          <img lazy-load='true' src="/static/login-btn3.png" alt="">
          


        </button>
        <img v-if="true"
             lazy-load='true'
             class="moon"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/moon.png">
      </div>
      <!-- wx ad -->
      <div class="wx-ad"
           :class="{'huawei':HUAWEI}">
        <ad unit-id="adunit-1f1cc70776e2e407"></ad>
      </div>
      <energe-modal v-if="showEnerge"
                    @close="showEnerge=false"></energe-modal>
      <season-modal v-if="showSeason"
                    :seasonData="seasonData"
                    @close="showSeason=false"></season-modal>
      <menu-box v-if="showMenu"
                :status="status"
                @invateBox="changeInvateBoxType"
                @showModal="handleShowModal"
                @close="showMenu=false"></menu-box>
      <ad-box-new v-if="showAdBox"
                  :tryGameName="tryGameName"
                  @close="closeAdBox"></ad-box-new>

      <day-gift v-if="showDayGfit"
                :type="giftType"
                :showCatNow="showCatNow"
                @close="showDayGfit=false"></day-gift>
      <invate-box v-if="showInvateBox"
                  @close="showInvateBox=false"></invate-box>

    </div>
  </div>

</template>

<script>
import { getRondom } from "@/utils/index";
import {
  shareInfo,
  shareInfoForDayGift,
  shareInfoForTask,
  shareInfoForInvate
} from "@/config/dataFile";
import { mapState, mapMutations } from "vuex";
import energeModal from "@/components/modal/energeModal";
import seasonModal from "@/components/modal/seasonModal";
import dayGift from "@/components/gift/day-gift";
import invateBox from "@/components/public/invateBox";

import menu from "./components/menu";
import adBoxNew from "./components/adBoxNew";
import { checkNewDay } from "@/utils/index";

export default {
  components: {
    "energe-modal": energeModal,
    "season-modal": seasonModal,
    "menu-box": menu,
    "ad-box-new": adBoxNew,
    "day-gift": dayGift,
    "invate-box": invateBox
  },
  onShareAppMessage: function(res) {
    console.log("entry-share");
    let info = shareInfo;
    if (this.showDayGfit) {
      info = shareInfoForDayGift;
    }
    if (this.showAdBox) {
      info = shareInfoForTask;
    }
    if (this.showInvateBox) {
      info = shareInfoForInvate;
    }
    let index = getRondom(0, info.length - 1);
    return {
      title: shareInfo[index].title,
      // imageUrl: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/shareImg/day-gift-1.png",
      imageUrl: info[index].imgUrl,
      path: `/pages/entry/index?userId=${this.appInfo.userId}`
    };
  },
  data() {
    return {
      token: "",
      fId: "",
      isNewUser: 0,
      dataReady: false,
      gradeList: [],
      expStyle: "",
      seasonData: {},
      // 弹窗控制
      showSeason: false,
      showEnerge: false,
      showPack: false,
      energeTimer: null,
      showDayGfit: false,
      showMenu: false,
      initTime: 120, //初始360s
      strengthIntervalTimer: 0, //初始
      formatTime: "00:00",
      showAdBox: false,
      showAd: true,
      msgTimer: null,
      messageList: [],
      messageIndex: 0,
      showLottery: true,
      isBookLevel: 0,
      status: {},
      initTimer: null,
      tryGameName: "",
      giftType: 1,
      showCatNow: false,
      showInvateBox: false,
      newFoodCard: 0
    };
  },
  watch: {
    strengthIntervalTimer(time) {
      if (time <= 0) {
        this.strengthIntervalTimer = this.initTime;
        if (this.strengthCount + 1 > this.maxStrength) {
          this.setStrengthCount(this.maxStrength);
        } else {
          this.setStrengthCount(this.strengthCount + 1);
        }
      }
      this.formatTime = this.getFormatTime(time);
    }
  },
  computed: {
    ...mapState({
      isAuthorize: state => state.isAuthorize,
      IMGHEAD: state => state.IMGHEAD,
      appInfo: state => state.appInfo,
      dayCount: state => state.dayCount,
      roomId: state => state.fStore.roomId,
      roomStatus: state => state.fStore.roomStatus,
      musicState: state => state.musicState,
      globalBgPlayer: state => state.globalBgPlayer,
      globalSoundPlayer: state => state.globalSoundPlayer,
      firstLaunch: state => state.firstLaunch,
      strengthCount: state => state.strengthCount,
      maxStrength: state => state.maxStrength,
      deviceInfo: state => state.deviceInfo,
      hasTipMenu: state => state.hasTipMenu,
      hasTipAd: state => state.hasTipAd,
      HUAWEI: state => state.HUAWEI,
      isUpdateUserInfo: state => state.isUpdateUserInfo,
      tryGameName: state => state.tryGameName
    }),
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
    ...mapMutations({
      setAuthorize: "setAuthorize",
      setAppInfo: "setAppInfo",
      setSessionId: "setSessionId",
      setDayCount: "setDayCount",
      setOpenId: "setOpenId",
      setRoomId: "fStore/setRoomId",
      setRoomStatus: "fStore/setRoomStatus",
      setMusicState: "setMusicState",
      setGlobalBgPlayer: "setGlobalBgPlayer",
      setGlobalSoundPlayer: "setGlobalSoundPlayer",
      setFirstLaunch: "setFirstLaunch",
      setBgStatus: "setBgStatus",
      setStrengthCount: "setStrengthCount",
      setSecretCode: "setSecretCode",
      setGamePageType: "setGamePageType",
      setLotteryList: "setLotteryList",
      setHasTipMenu: "setHasTipMenu",
      setHasTipAd: "setHasTipAd",
      setHuawei: "setHuawei",
      setIsUpdateUserInfo: "setIsUpdateUserInfo",
      setGlobalStatus: "setGlobalStatus",
      setMessageListStorage: "setMessageListStorage",
      setTryGameName: "setTryGameName"
    }),
    catStart() {
      console.log(this);
      this.$mp.page.selectComponent("#cat").start();
    },
    toNewYear() {
      this.$router.push("/pages/newYear/index");
    },
    getFormatTime(time) {
      let m, s;
      m = Math.floor(time / 60);
      m = m + "";
      m.length < 2 ? (m = "0" + m) : (m = m);
      s = Math.ceil(time % 60);
      s = s + "";
      s.length < 2 ? (s = "0" + s) : (s = s);
      return m + ":" + s;
    },
    submitHandle(id) {
      this.fly.post("template/updateFormId", {
        formId: id
      });
    },
    changeInvateBoxType(type) {
      console.log("invate", type);
      this.showInvateBox = type;
    },
    submitFormId(e) {
      console.log("12");
      let id = e.mp.detail.formId;
      checkNewDay("fromId").then(res => {
        console.log("res", res);
        if (res) {
          wx.setStorage({
            key: "submitCount",
            data: 1
          });
          this.submitHandle(id);
          return false;
        } else {
          wx.getStorage({
            key: "submitCount",
            success: res => {
              if (res.data < 2) {
                this.submitHandle(id);
                wx.setStorage({
                  key: "submitCount",
                  data: res.data + 1
                });
              }
            },
            fail: () => {
              this.submitHandle(id);
              wx.setStorage({
                key: "submitCount",
                data: 1
              });
            }
          });
        }
      });
    },
    startMsgTimer() {
      clearInterval(this.msgTimer);
      this.msgTimer = setInterval(() => {
        if (this.messageIndex == this.messageList.length - 1) {
          this.messageIndex = 0;
        } else {
          this.messageIndex++;
        }
      }, 3000);
    },
    getAllModuleType() {
      this.fly.post("/user/getRealTimeData").then(res => {
        // 经验
        // 体力
        let data = res.data.Result;
        this.setGlobalStatus(data.statusInfo);
        this.expStyle = `width:${data.abilityInfo.expValue /
          data.abilityInfo.maxExpValue *
          100}%;`;
        this.status = data.statusInfo;
        this.setAppInfo({ money: data.abilityInfo.money });
        // this.appInfo.money = data.abilityInfo.money;
        this.setStrengthCount(data.abilityInfo.strengthCount);
        this.strengthIntervalTimer = data.abilityInfo.strengthIntervalTimer;
        this.formatTime = this.getFormatTime(this.strengthIntervalTimer);
        if (this.strengthCount < 30) {
          this.startEnergeTimer();
        }

        // 聊天
        this.messageList = data.messageBoardList;
        this.setMessageListStorage(data.messageBoardList);
        this.startMsgTimer();

        //状态
        if (data.statusInfo.isDaySign == 1 || data.statusInfo.isHitCat == 1) {
          this.showDayGfit = true;
        }
        if (data.statusInfo.isHitCat == 1) {
          this.showCatNow = true;
          let t1 = setTimeout(() => {
            this.showCatNow = false;
            clearTimeout(t1);
          }, 2000);
        }
        this.isBookLevel = data.statusInfo.isBookUpLevel;
      });
    },
    stopMsgTimer() {
      clearInterval(this.msgTimer);
    },

    print(str) {
      console.log(str);
    },
    handleShowModal(type) {
      this.giftType = type;
      this.showDayGfit = true;
    },
    showMenuFn(type) {
      if (type == "menu") {
        this.showMenu = true;
        this.setHasTipMenu(false);
      } else if (type == "ad") {
        this.showAdBox = true;
        this.setHasTipAd(false);
      }
      this.shake();
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
    getMusicSetting() {
      wx.getStorage({
        key: "musicObj[bg]",
        success: res => {
          if (!res.data) {
            this.setMusic(0, false, "bg");
          } else {
            this.setMusic(1, true, "bg");
          }
        },
        fail: () => {
          wx.setStorage({
            key: "musicObj[bg]",
            data: true
          });
          this.globalBgPlayer.play();
        }
      });
      wx.getStorage({
        key: "musicObj[sound]",
        success: res => {
          if (!res.data) {
            this.setMusic(0, false, "sound");
          } else {
            this.setMusic(1, true, "sound");
          }
        },
        fail: () => {
          wx.setStorage({
            key: "musicObj[sound]",
            data: true
          });
        }
      });
    },
    unAble() {
      wx.showToast({ title: "暂未开放", icon: "none" });
    },
    toLottery() {
      this.$router.push("/pages/lottery/index");
    },
    toBook() {
      this.$router.push("/pages/book/index");
    },

    startEnergeTimer(type) {
      if (this.strengthCount < this.maxStrength) {
        this.energeTimer = setInterval(() => {
          this.strengthIntervalTimer--;
        }, 1000);
      }
    },
    stopEnergeTimer() {
      clearInterval(this.energeTimer);
      wx.setStorage({
        key: "energeTimeTag",
        data: new Date().getTime()
      });
    },
    toSubject() {
      this.$router.push("/subjectFactoryPages/pages/index");
    },
    toUserInfo() {
      this.$router.push(
        `/playerPages/pages/index?userId=${this.appInfo.userId}`
      );
    },
    getDevice() {
      //获取设备信息
      let deviceInfo = {};
      wx.getSystemInfo({
        success: res => {
          this.store.commit("setDeviceInfo", {
            width: res.windowWidth,
            height: res.windowHeight,
            brand: res.brand
          });
          console.log(res.brand.toLowerCase());
          if (
            res.brand.toLowerCase().indexOf("huawei") != -1 ||
            res.brand.toLowerCase().indexOf("honor") != -1
          ) {
            this.setHuawei(true);
          }
          deviceInfo = res;
        }
      });
      wx.getStorage({
        key: "hasDeviceInfo",
        fail: () => {
          wx.setStorage({
            key: "hasDeviceInfo",
            data: "true"
          });
          this.fly.post("/system", {
            brand: deviceInfo.brand,
            model: deviceInfo.model,
            pixelRatio: deviceInfo.pixelRatio,
            screenWidth: deviceInfo.screenWidth,
            screenHeight: deviceInfo.screenHeight,
            windowHeight: deviceInfo.windowHeight,
            windowWidth: deviceInfo.windowWidth,
            statusBarHeight: deviceInfo.statusBarHeight,
            language: deviceInfo.language,
            version: deviceInfo.version,
            system: deviceInfo.system,
            platform: deviceInfo.platform,
            fontSizeSetting: deviceInfo.fontSizeSetting,
            SDKVersion: deviceInfo.SDKVersion
          });
        }
      });
    },
    countOutNum() {
      console.log("调用跳转广告成功");
      this.fly.post("user/userToXcx");
    },
    countOutNumFail() {
      console.log("调用跳转广告失败");
    },
    toRoom() {
      this.$router.push("/roomPages/pages/roomList");
    },
    toTalkRoom() {
      if (
        this.showDayGfit ||
        this.showPack ||
        this.showMenu ||
        this.showAdBox
      ) {
        return false;
      }
      this.$router.push("/pages/talkRoom/index");
    },
    login() {
      // 微信登录
      return new Promise((resolve, reject) => {
        wx.login({
          success: res => {
            let code = res.code;
            // 登录  title: "登录中,请稍后",
            wx.showLoading({
              title: "正在进入游戏,请稍后",
              mask: true
            });
            this.fly.get("/login", { code: code }).then(res => {
              if (res.data.Tag == 1) {
                this.setSessionId(res.data.SessionId); // 全局管理SesionId
                this.setSecretCode(res.data.Result.code); // 全局管理SesionId
                this.setOpenId(res.data.Result.openId); // 全局管理SesionId
                wx.hideLoading();
                // 用户信息初始化
                if (this.isAuthorize == 1) {
                  this.getInit();
                  this.getAllModuleType();
                }
                this.getDevice();
                console.log("登录成功");
                this.dataReady = true;
                resolve();
              }
            });
          },
          fail: res => {
            reject();
          }
        });
      });
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
              console.log("试玩成功");
              // 试玩成功
              this.showAdBox = true;
              let t = setTimeout(() => {
                this.setTryGameName(t1.name);
                this.tryGameName = t1.name;
                clearTimeout(t);
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
    initAudio() {
      let bgAudio, soundAudio;
      bgAudio = wx.createInnerAudioContext();
      soundAudio = wx.createInnerAudioContext();
      this.setGlobalBgPlayer(bgAudio);
      this.setGlobalSoundPlayer(soundAudio);
      this.globalBgPlayer.src =
        "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/gameBgm.mp3"; //bgm
      this.globalBgPlayer.volume = 0.5;
      this.globalBgPlayer.loop = true;

      this.globalBgPlayer.onPause(res => {
        this.setBgStatus("pause");
      });

      // this.globalBgPlayer.play();
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

    initPage() {
      // 分享卡片进来，判断一下房间状态
      if (
        (this.$root.$mp.appOptions.scene == 1007 ||
          this.$root.$mp.appOptions.scene == 1008) &&
        this.$root.$mp.query.roomId &&
        this.appInfo
      ) {
        this.$router.push(
          `/roomPages/pages/roomList?roomId=${this.$root.$mp.query.roomId}`
        );
        this.$root.$mp.appOptions.scene = 1005;
      }
    },

    toRankList() {
      this.$router.push("/pages/rankPage/index");
    },
    weekChallenge() {
      this.$router.push("/pages/weekChallenge/index");
    },
    rankStart() {
      this.$router.push("/pages/preGamePage/index");
    },
    wait() {
      wx.showToast({
        title: "暂未开放,敬请期待",
        icon: "none"
      });
    },
    getInit() {
      this.fly
        .post("/init", {
          userId: this.$root.$mp.query.userId || 0,
          isNew: this.isNewUser
        })
        .then(res => {
          this.seasonData = res.data.Result.seasonEnd;
          if (this.seasonData && this.seasonData.paragraph) {
            this.showSeason = true;
          }
          this.setAppInfo(res.data.Result);
          this.initTimer = setTimeout(() => {
            this.initPage();
          }, 500);

          // 显示每日福利
          if (this.appInfo.isDaySign == 1) {
            this.showDayGfit = true;
          }

          this.setDayCount(res.data.Result.dayCardNumber);
          this.setLotteryList(res.data.Result.prizeList);
          this.expStyle = `width:${this.appInfo.expValue /
            this.appInfo.maxExpValue *
            100}%;`;
          wx.getStorage({
            key: "themeSet",
            success: res => {
              this.setGamePageType(res.data);
            }
          });
        });
    },

    updateUserInfo(event) {
      if (this.isUpdateUserInfo) {
        return false;
      }
      //每天请求一次
      let data = event.mp.detail.userInfo;
      this.fly
        .post("/authorize", {
          //后端授权
          nickName: data.nickName || "无名氏",
          avatarUrl: data.avatarUrl,
          gender: data.gender,
          city: data.city,
          province: data.province,
          country: data.country
        })
        .then(() => {
          this.appInfo.nickName = data.nickName;
          this.appInfo.avatarUrl = data.avatarUrl;
          this.appInfo.gender = data.gender;
          this.appInfo.city = data.city;
          this.appInfo.province = data.province;
          this.appInfo.country = data.country;

          this.setIsUpdateUserInfo(true);
          wx.setStorage({
            key: "day",
            data: new Date().getDate()
          });
        });
    },
    async getUserInfo(event) {
      let data = event.mp.detail.userInfo;
      if (data) {
        wx.setStorage({
          //记录授权状态
          key: "isAuthorize",
          data: "1"
        });
        this.isNewUser = 1;
        await this.login();
        this.fly
          .post("/authorize", {
            //后端授权
            nickName: data.nickName || "无名氏",
            avatarUrl: data.avatarUrl,
            gender: data.gender,
            city: data.city,
            province: data.province,
            country: data.country
          })
          .then(() => {
            this.getInit();
            this.initPage();
            this.setAuthorize(1);
          });
      }
    },
    normalGame() {
      this.$router.push("/pages/game/index");
    },

    // -------------------------好友对战相关
    battleClickHandle() {
      // TODO
      // 1.进入对战准备房间
      this.apiClickRoom().then(res => {
        if (res == 0) {
          this.$router.push("/pages/battleRoom/index");
        }
      });
    },
    getnewFoodCard() {
      wx.getStorage({
        key: "newFoodCard",
        success: res => {
          this.newFoodCard = res.data;
        },
        fail: () => {
          this.newFoodCard = 0;
        }
      });
    },
    getRoomId() {
      return new Promise((resolve, reject) => {
        this.fly.post("/room/getRoomId").then(res => {
          this.setRoomId({ roomId: res.data.Result.code }); //顺便设置缓存
          resolve(res.data.Result.code);
        });
      });
    },
    apiClickRoom() {
      return new Promise((resolve, reject) => {
        this.fly.post("/room/clickRoom", { code: this.roomId }).then(res => {
          resolve(res.data.Result.isExpire);
        });
      });
    },

    // --------------各种弹框
    showSettingModal() {
      this.showSetting = true;
    }
  },
  onShow() {
    this.checkGift();
    this.getnewFoodCard();
    if (this.dataReady) {
      this.initPage();
      this.getAllModuleType();
    }
  },
  onHide() {
    this.stopEnergeTimer();
    this.stopMsgTimer();
    clearInterval(this.initTimer);
  },
  mounted() {
    console.log("git版本-->", "v4.3.1");
    if (this.isAuthorize == 1) {
      this.login();
    }
    if (this.firstLaunch) {
      this.initAudio();
      this.setFirstLaunch(false);
    }
    this.getMusicSetting();
  }
};
</script>

<style lang="scss" scoped>
@import "../../scss/et-img-map.scss";
button {
  padding: 0;
}
.out-box {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.entry-bg {
  width: 750rpx;
  // height: 1624rpx;
  height: 100%;
  // margin-top: -120rpx;
}
.wrap {
  position: fixed;
  left: 0;
  top: 0;
  height: 100%;
  width: 100%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  box-sizing: border-box;

  // background-color: #28235d;
}
.head-menu,
.link-menu {
  position: absolute;
  top: 120rpx;
  width: 100rpx;
  height: 86rpx;
  display: flex;
  align-items: center;
  border-top-right-radius: 40rpx;
  border-bottom-right-radius: 40rpx;
  background: rgba(255, 255, 255, 0.5);
  z-index: 10;
}
.head-menu {
  left: 0;
  justify-content: flex-end;
  opacity: 0;
  transform: translateX(-100rpx);
  transition: all 0.2s ease;
}
.link-menu {
  width: 80rpx;
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
  border-top-left-radius: 40rpx;
  border-bottom-left-radius: 40rpx;
  right: 0;
  justify-content: flex-start;
  opacity: 0;
  transform: translateX(80rpx);
  transition: all 0.2s ease;
  img {
    width: 69rpx;
    height: 81rpx;
  }
}
.season-tag {
  width: 480rpx;
  height: 48rpx;
  margin-left: 30rpx;
  padding-left: 20rpx;
  box-sizing: border-box;
  overflow: hidden;
  font-size: 24rpx;
  color: #fff;
  background: #000;
  border-radius: 1000rpx;
  position: relative;
  .season-mask {
    position: fixed;
    right: 30rpx;
    top: 30rpx;
    width: 480rpx;
    height: 48rpx;
    z-index: 2;
  }
  & > div {
    padding-left: 20rpx;
    box-sizing: border-box;
  }
  .tag-inner {
    display: flex;
    align-items: center;
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
      width: 38rpx;
      height: 38rpx;
      margin-right: 10rpx;
      border-radius: 50%;
    }
  }
}
.head {
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  align-items: center;
  flex-shrink: 0;
  width: 100%;
  height: 106rpx;
  z-index: 1;
  // border: 2rpx solid #442d8e;
  box-sizing: border-box;
  background: #351d4d;
  font-size: 22rpx;
  position: relative;

  .img-box {
    position: relative;
    width: 225rpx;
    height: 76rpx;
    margin: 60rpx;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    border-radius: 1000rpx;
    background: #1b0d32;
    padding-left: 40rpx;
    box-sizing: border-box;

    img {
      position: absolute;
      top: 50%;
      left: -60rpx;
      transform: translateY(-50%);
      width: 76rpx;
      height: 76rpx;
      border: 2rpx solid #fff;
      border-radius: 50%;
    }
    .game-info {
      text-align: left;
      color: #fff;
      .name {
        width: 160rpx;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        font-size: 28rpx;
      }
      .info-bottom {
        font-size: 20rpx;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        .exp-process {
          width: 64rpx;
          height: 12rpx;
          padding: 2rpx 3rpx;
          margin-left: 10rpx;
          background: #ffffff;
          box-sizing: border-box;
          border-radius: 1000rpx;
        }
        .exp-inner {
          width: 0%;
          height: 100%;
          background: #7dfbe5;
          border-radius: 1000rpx;
          transition: all 0.2s ease;
        }
      }
    }
  }
  .gold-box {
    position: relative;
    width: 150rpx;
    height: 42rpx;
    font-size: 28rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-left: 60rpx;
    padding-left: 20rpx;
    box-sizing: border-box;
    background: #000;
    font-weight: bold;
    border: 2rpx solid #6f45a2;
    border-radius: 1000rpx;
    color: #fff;
    img {
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
      left: -20rpx;
      width: 47rpx;
      height: 47rpx;
    }
  }
}

.content {
  text-align: center;
  box-sizing: border-box;
  margin-top: 110rpx;
}
.rank {
  width: 664rpx;
  height: 369rpx;
  padding: 0;
  position: relative;
  border: none;
  margin-bottom: 10rpx;
  img {
    width: 664rpx;
    height: 369rpx;
    vertical-align: top;
  }
}

.g-box {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: -20rpx;
  .week-entry,
  .normal-entry {
    width: 348rpx;
    height: 318rpx;
    vertical-align: top;
  }
}
.rank-list {
  & > img {
    width: 714rpx;
    height: 362rpx;
    vertical-align: top;
  }
}

.f-battle {
  margin-bottom: -20rpx;
  img {
    width: 694rpx;
    height: 240rpx;
  }
}
.logo {
  width: 462rpx;
  height: 452rpx;
  margin-bottom: 200rpx;
}
.btn-success {
  margin-bottom: 60rpx;
}

.m-btn {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-top: 60rpx;
  & > div {
    width: 136rpx;
    height: 160rpx;
    position: relative;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .hasTip {
    &::after {
      top: 30rpx;
      left: 40rpx;
    }
  }
  button {
    padding: 0;
    margin: 0;
    background: transparent;
    border: none;
    width: 86rpx;
    height: 86rpx;
    img {
      width: 100%;
      height: 100%;
    }
  }
}
.login-page {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  flex-direction: column;
  z-index: 30;
  & > img {
    width: 628rpx;
    height: 628rpx;
    margin-top: 40rpx;
  }
  .login-btn {
    position: absolute;
    top: 750rpx;
    left: 50%;
    transform: translateX(-50%);
    border: none;
    background: transparent;
    img {
      width: 205rpx;
      height: 205rpx;
    }
  }
}
.btn-box {
  display: flex;
  justify-content: center;
  position: relative;
  img {
    width: 100%;
    height: 100%;
    border-bottom-right-radius: 50rpx;
  }
  .btn-rank {
    width: 390rpx;
    height: 331rpx;
    margin-top: -10rpx;
    background-image: url("https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newYear/btn-food.png");
    background-size: 100%;
    background-repeat: no-repeat;
    .card {
      position: absolute;
      right: 37rpx;
      bottom: 45rpx;
      width: 150rpx;
      height: 46rpx;
      color: #fff;
      font-weight: bold;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 28rpx;
    }
    .btn-food {
      width: 390rpx;
      height: 331rpx;
    }
  }
  .btn-group {
    width: 421rpx;
    height: 308rpx;
    margin-right: -136rpx;
    border-bottom-left-radius: 50rpx;
  }
  .btn-place {
    width: 152rpx;
    height: 310rpx;
    position: absolute;
    left: 50%;
    top: 0;
    transform: translateX(-30%);
  }
}
.click-active {
  transform: scale(0.95);
}

.out-link-box {
  width: 96rpx;
  height: 96rpx;
  border-top-right-radius: 2000rpx;
  border-bottom-right-radius: 2000rpx;
  position: absolute;
  top: 540rpx;
  left: 0;
  background: rgba(#fff, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  navigator,
  img {
    width: 84rpx;
    height: 84rpx;
    border-radius: 50%;
  }
}
.moon {
  width: 750rpx !important;
  height: 225rpx !important;
  position: fixed;
  // bottom:calc(-410rpx + 20vh);
  bottom: -120rpx;
}
@media screen and (min-height: 720px) {
  .moon {
    bottom: 0;
  }
}

.hasTip,
.hasTipAd {
  &::after {
    content: "";
    width: 16rpx;
    height: 16rpx;
    background: #ff217b;
    border-radius: 50%;
    position: absolute;
  }
}
.hasTip {
  &::after {
    top: 4rpx;
    right: 4rpx;
  }
}
.hasTipAd {
  &::after {
    content: "";
    top: 4rpx;
    left: 4rpx;
  }
}
.star-bg {
  position: absolute;
  width: 658rpx;
  height: 270rpx;
  top: 0;
  left: 50rpx;
}
.show {
  opacity: 1 !important;
  transform: translateX(0) !important;
}
.wx-ad {
  position: fixed;
  left: 0;
  bottom: 0;
  width: 100%;
}
.huawei {
  transform: translateY(120rpx);
}
.top {
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
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
    width: 4em;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #82fbfb;
    text-align: center;
  }
}

.icon-box {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: 120rpx;
  width: 500rpx;
  display: flex;
  justify-content: space-between;
  img {
    width: 100rpx;
    height: 91rpx;
  }
}
</style>
