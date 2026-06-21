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
    /** 监听体力倒计时变化：归零时重置并恢复1点体力 */
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
    /** 从 appInfo.seasonTime 解析出赛季年份和月份 */
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
    /** 启动猫动画 */
    catStart() {
      console.log(this);
      this.$mp.page.selectComponent("#cat").start();
    },
    /** 跳转到新年活动（食物卡片）页面 */
    toNewYear() {
      this.$router.push("/pages/newYear/index");
    },
    /** 将秒数格式化为 mm:ss 字符串 */
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
    /** 向后端提交微信 formId（用于模板消息推送） */
    submitHandle(id) {
      this.fly.post("template/updateFormId", {
        formId: id
      });
    },
    /** 切换邀请弹窗的显示/隐藏 */
    changeInvateBoxType(type) {
      console.log("invate", type);
      this.showInvateBox = type;
    },
    /** 表单提交时获取 formId，每日限制提交次数后上报 */
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
    /** 启动消息滚动定时器，每隔3秒切换到下一条消息 */
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
    /** 获取所有模块的实时数据：经验、体力、消息、签到状态等 */
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
    /** 停止消息滚动定时器 */
    stopMsgTimer() {
      clearInterval(this.msgTimer);
    },

    /** 调试打印 */
    print(str) {
      console.log(str);
    },
    /** 从菜单触发显示每日礼包弹窗，可指定礼包类型 */
    handleShowModal(type) {
      this.giftType = type;
      this.showDayGfit = true;
    },
    /** 显示菜单弹窗或广告弹窗，同时触发震动反馈 */
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
    /** 关闭广告弹窗 */
    closeAdBox() {
      this.showAdBox = false;
    },
    /** 触发手机短震动反馈 */
    shake() {
      wx.vibrateShort({
        success: () => {
          console.log("点击震动");
        }
      });
    },
    /** 初始化音乐设置（音频服务器已宕，代码已注释，仅写 storage 兜底） */
    getMusicSetting() {
      // 音频已注释，直接跳过
      try { wx.setStorageSync('musicObj[bg]', true); } catch(e) {}
      try { wx.setStorageSync('musicObj[sound]', true); } catch(e) {}
    },
    /** 功能未开放 Toast 提示 */
    unAble() {
      wx.showToast({ title: "暂未开放", icon: "none" });
    },
    /** 跳转到抽奖页面 */
    toLottery() {
      this.$router.push("/pages/lottery/index");
    },
    /** 跳转到图鉴页面 */
    toBook() {
      this.$router.push("/pages/book/index");
    },

    /** 启动体力恢复计时器：体力未满时每秒减1秒倒计时 */
    startEnergeTimer(type) {
      if (this.strengthCount < this.maxStrength) {
        this.energeTimer = setInterval(() => {
          this.strengthIntervalTimer--;
        }, 1000);
      }
    },
    /** 停止体力恢复计时器，记录当前时间用于下次恢复计算 */
    stopEnergeTimer() {
      clearInterval(this.energeTimer);
      wx.setStorage({
        key: "energeTimeTag",
        data: new Date().getTime()
      });
    },
    /** 跳转到专题工厂页面 */
    toSubject() {
      this.$router.push("/subjectFactoryPages/pages/index");
    },
    /** 跳转到玩家个人主页 */
    toUserInfo() {
      this.$router.push(
        `/playerPages/pages/index?userId=${this.appInfo.userId}`
      );
    },
    /** 获取设备信息（分辨率、品牌等），华为/荣耀设备做特殊标记，首次启动上报给后端 */
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
    /** 跳转外部小程序广告成功的回调（计数上报） */
    countOutNum() {
      console.log("调用跳转广告成功");
      this.fly.post("user/userToXcx");
    },
    /** 跳转外部小程序广告失败的回调 */
    countOutNumFail() {
      console.log("调用跳转广告失败");
    },
    /** 跳转到房间列表页面 */
    toRoom() {
      this.$router.push("/roomPages/pages/roomList");
    },
    /** 跳转到聊天室（弹窗打开时拦截不跳转） */
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
    /**
     * 微信登录流程
     * - Dev 模式下使用固定的 dev code 跳过 wx.login
     * - 正式环境调用 wx.login 获取临时 code 换取 session
     * - 登录成功后依次调用 getInit / getAllModuleType / getDevice
     */
    login() {
      // 微信登录（开发模式下跳过 wx.login，直接使用 dev code）
      return new Promise((resolve, reject) => {
        wx.showLoading({ title: "正在进入游戏,请稍后", mask: true });
        let doLogin = (code) => {
          this.fly.get("/login", { code: code }).then(res => {
            if (res.data.Tag == 1) {
              this.setSessionId(res.data.SessionId);
              this.setSecretCode(res.data.Result.code);
              this.setOpenId(res.data.Result.openId);
              wx.hideLoading();
              this.getInit();
              this.getAllModuleType();
              this.getDevice();
              console.log("登录成功");
              this.dataReady = true;
              resolve();
            } else {
              wx.hideLoading();
              reject();
            }
          }).catch(() => { wx.hideLoading(); reject(); });
        };
        if (this.store.state.EVN === 'Dev') {
          doLogin('dev_test_01');
        } else {
          wx.login({
            success: res => doLogin(res.code),
            fail: () => reject()
          });
        }
      });
    },
    /**
     * 检查试玩奖励：从 storage 读取试玩开始时间，
     * 超过 20 秒则判定试玩成功并显示广告弹窗
     */
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
    /**
     * 初始化背景音乐和音效播放器（已废弃）
     * CDN 服务器已宕，音频相关代码暂时注释
     */
    initAudio() {
      // 音频初始化已注释——CDN 服务器已宕
      /*
      let bgAudio, soundAudio;
      bgAudio = wx.createInnerAudioContext();
      soundAudio = wx.createInnerAudioContext();
      this.setGlobalBgPlayer(bgAudio);
      this.setGlobalSoundPlayer(soundAudio);
      this.globalBgPlayer.src =
        "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/gameBgm.mp3";
      this.globalBgPlayer.volume = 0.5;
      this.globalBgPlayer.loop = true;
      this.globalBgPlayer.onPause(res => {
        this.setBgStatus("pause");
      });
      */
    },
    /** 设置音乐/音效开关状态（音频已废弃，仅写 storage） */
    setMusic(volume, state, type) {
      // 音频已注释
      try { wx.setStorageSync(`musicObj[${type}]`, state); } catch(e) {}
    },

    /**
     * 分享卡片入口处理：从群聊分享卡片（scene 1007/1008）进入时，
     * 如果携带 roomId 则直接跳转到对应房间
     */
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

    /** 跳转到排行榜页面 */
    toRankList() {
      this.$router.push("/pages/rankPage/index");
    },
    /** 跳转到周挑战页面 */
    weekChallenge() {
      this.$router.push("/pages/weekChallenge/index");
    },
    /** 点击排位按钮，跳转到赛前准备页面 */
    rankStart() {
      this.$router.push("/pages/preGamePage/index");
    },
    /** 「敬请期待」Toast 提示 */
    wait() {
      wx.showToast({
        title: "暂未开放,敬请期待",
        icon: "none"
      });
    },
    /**
     * 获取初始化数据：赛季信息、用户属性、每日签到状态、
     * 抽奖列表、经验进度，并延迟 500ms 执行 initPage
     */
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

    /**
     * 更新用户微信信息（头像、昵称、性别、地区）到后端，
     * 每天只请求一次（isUpdateUserInfo 控制）
     */
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
    /**
     * 微信授权登录入口：获取用户信息 → 记录授权状态 →
     * 调 login() → 同步用户信息到后端 → 标记已授权
     */
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
    /** 跳转到普通游戏页面 */
    normalGame() {
      this.$router.push("/pages/game/index");
    },

    // -------------------------好友对战相关
    /** 好友对战入口：先请求房间状态，未过期则跳转到对战房间 */
    battleClickHandle() {
      // TODO
      // 1.进入对战准备房间
      this.apiClickRoom().then(res => {
        if (res == 0) {
          this.$router.push("/pages/battleRoom/index");
        }
      });
    },
    /** 从 storage 读取新年食物卡片已通关数 */
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
    /** 向服务端请求分配房间 ID，返回 Promise<roomId> */
    getRoomId() {
      return new Promise((resolve, reject) => {
        this.fly.post("/room/getRoomId").then(res => {
          this.setRoomId({ roomId: res.data.Result.code }); //顺便设置缓存
          resolve(res.data.Result.code);
        });
      });
    },
    /** 向服务端查询当前房间是否已过期，返回 Promise<isExpire> */
    apiClickRoom() {
      return new Promise((resolve, reject) => {
        this.fly.post("/room/clickRoom", { code: this.roomId }).then(res => {
          resolve(res.data.Result.isExpire);
        });
      });
    },

    // --------------各种弹框
    /** 显示设置弹窗 */
    showSettingModal() {
      this.showSetting = true;
    }
  },
  /** 页面显示时：检查试玩奖励、读取食物卡片进度、已登录则刷新数据 */
  onShow() {
    this.checkGift();
    this.getnewFoodCard();
    if (this.dataReady) {
      this.initPage();
      this.getAllModuleType();
    }
  },
  /** 页面隐藏时：停止体力计时器、消息滚动定时器、初始化延迟定时器 */
  onHide() {
    this.stopEnergeTimer();
    this.stopMsgTimer();
    clearInterval(this.initTimer);
  },
  /**
   * 组件挂载
   * - Dev 模式：跳过授权检查直接登录
   * - 正式环境：已授权则自动登录
   * - 首次启动初始化音频（已废弃）和音乐设置
   */
  mounted() {
    console.log("git版本-->", "v4.3.1");
    // Dev mode: skip auth check, login directly
    if (this.store.state.EVN === 'Dev') {
      this.setAuthorize(1);
      this.login();
    } else if (this.isAuthorize == 1) {
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
