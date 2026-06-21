global.webpackJsonp([2],{

/***/ 119:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(121);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_762317e4_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(161);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(120)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-762317e4"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_762317e4_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\entry\\index.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-762317e4", Component.options)
  } else {
    hotAPI.reload("data-v-762317e4", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 120:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 121:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator__ = __webpack_require__(18);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator__ = __webpack_require__(19);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_promise__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_promise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_promise__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__utils_index__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__config_dataFile__ = __webpack_require__(13);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__components_modal_energeModal__ = __webpack_require__(70);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__components_modal_seasonModal__ = __webpack_require__(127);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__components_gift_day_gift__ = __webpack_require__(131);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__components_public_invateBox__ = __webpack_require__(71);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__components_menu__ = __webpack_require__(142);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__components_adBoxNew__ = __webpack_require__(72);




//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//













/* harmony default export */ __webpack_exports__["a"] = ({
  components: {
    "energe-modal": __WEBPACK_IMPORTED_MODULE_7__components_modal_energeModal__["a" /* default */],
    "season-modal": __WEBPACK_IMPORTED_MODULE_8__components_modal_seasonModal__["a" /* default */],
    "menu-box": __WEBPACK_IMPORTED_MODULE_11__components_menu__["a" /* default */],
    "ad-box-new": __WEBPACK_IMPORTED_MODULE_12__components_adBoxNew__["a" /* default */],
    "day-gift": __WEBPACK_IMPORTED_MODULE_9__components_gift_day_gift__["a" /* default */],
    "invate-box": __WEBPACK_IMPORTED_MODULE_10__components_public_invateBox__["a" /* default */]
  },
  onShareAppMessage: function onShareAppMessage(res) {
    console.log("entry-share");
    var info = __WEBPACK_IMPORTED_MODULE_5__config_dataFile__["c" /* shareInfo */];
    if (this.showDayGfit) {
      info = __WEBPACK_IMPORTED_MODULE_5__config_dataFile__["d" /* shareInfoForDayGift */];
    }
    if (this.showAdBox) {
      info = __WEBPACK_IMPORTED_MODULE_5__config_dataFile__["h" /* shareInfoForTask */];
    }
    if (this.showInvateBox) {
      info = __WEBPACK_IMPORTED_MODULE_5__config_dataFile__["f" /* shareInfoForInvate */];
    }
    var index = Object(__WEBPACK_IMPORTED_MODULE_4__utils_index__["c" /* getRondom */])(0, info.length - 1);
    return {
      title: __WEBPACK_IMPORTED_MODULE_5__config_dataFile__["c" /* shareInfo */][index].title,
      // imageUrl: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/shareImg/day-gift-1.png",
      imageUrl: info[index].imgUrl,
      path: "/pages/entry/index?userId=" + this.appInfo.userId
    };
  },
  data: function data() {
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
    strengthIntervalTimer: function strengthIntervalTimer(time) {
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
  computed: __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_6_vuex__["c" /* mapState */])({
    isAuthorize: function isAuthorize(state) {
      return state.isAuthorize;
    },
    IMGHEAD: function IMGHEAD(state) {
      return state.IMGHEAD;
    },
    appInfo: function appInfo(state) {
      return state.appInfo;
    },
    dayCount: function dayCount(state) {
      return state.dayCount;
    },
    roomId: function roomId(state) {
      return state.fStore.roomId;
    },
    roomStatus: function roomStatus(state) {
      return state.fStore.roomStatus;
    },
    musicState: function musicState(state) {
      return state.musicState;
    },
    globalBgPlayer: function globalBgPlayer(state) {
      return state.globalBgPlayer;
    },
    globalSoundPlayer: function globalSoundPlayer(state) {
      return state.globalSoundPlayer;
    },
    firstLaunch: function firstLaunch(state) {
      return state.firstLaunch;
    },
    strengthCount: function strengthCount(state) {
      return state.strengthCount;
    },
    maxStrength: function maxStrength(state) {
      return state.maxStrength;
    },
    deviceInfo: function deviceInfo(state) {
      return state.deviceInfo;
    },
    hasTipMenu: function hasTipMenu(state) {
      return state.hasTipMenu;
    },
    hasTipAd: function hasTipAd(state) {
      return state.hasTipAd;
    },
    HUAWEI: function HUAWEI(state) {
      return state.HUAWEI;
    },
    isUpdateUserInfo: function isUpdateUserInfo(state) {
      return state.isUpdateUserInfo;
    },
    tryGameName: function tryGameName(state) {
      return state.tryGameName;
    }
  }), {
    season: function season() {
      if (this.appInfo.seasonTime) {
        return {
          year: this.appInfo.seasonTime.split("-")[0],
          month: this.appInfo.seasonTime.split("-")[1]
        };
      }
    }
  }),
  methods: __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_6_vuex__["b" /* mapMutations */])({
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
  }), {
    catStart: function catStart() {
      console.log(this);
      this.$mp.page.selectComponent("#cat").start();
    },
    toNewYear: function toNewYear() {
      this.$router.push("/pages/newYear/index");
    },
    getFormatTime: function getFormatTime(time) {
      var m = void 0,
          s = void 0;
      m = Math.floor(time / 60);
      m = m + "";
      m.length < 2 ? m = "0" + m : m = m;
      s = Math.ceil(time % 60);
      s = s + "";
      s.length < 2 ? s = "0" + s : s = s;
      return m + ":" + s;
    },
    submitHandle: function submitHandle(id) {
      this.fly.post("template/updateFormId", {
        formId: id
      });
    },
    changeInvateBoxType: function changeInvateBoxType(type) {
      console.log("invate", type);
      this.showInvateBox = type;
    },
    submitFormId: function submitFormId(e) {
      var _this = this;

      console.log("12");
      var id = e.mp.detail.formId;
      Object(__WEBPACK_IMPORTED_MODULE_4__utils_index__["a" /* checkNewDay */])("fromId").then(function (res) {
        console.log("res", res);
        if (res) {
          wx.setStorage({
            key: "submitCount",
            data: 1
          });
          _this.submitHandle(id);
          return false;
        } else {
          wx.getStorage({
            key: "submitCount",
            success: function success(res) {
              if (res.data < 2) {
                _this.submitHandle(id);
                wx.setStorage({
                  key: "submitCount",
                  data: res.data + 1
                });
              }
            },
            fail: function fail() {
              _this.submitHandle(id);
              wx.setStorage({
                key: "submitCount",
                data: 1
              });
            }
          });
        }
      });
    },
    startMsgTimer: function startMsgTimer() {
      var _this2 = this;

      clearInterval(this.msgTimer);
      this.msgTimer = setInterval(function () {
        if (_this2.messageIndex == _this2.messageList.length - 1) {
          _this2.messageIndex = 0;
        } else {
          _this2.messageIndex++;
        }
      }, 3000);
    },
    getAllModuleType: function getAllModuleType() {
      var _this3 = this;

      this.fly.post("/user/getRealTimeData").then(function (res) {
        // 经验
        // 体力
        var data = res.data.Result;
        _this3.setGlobalStatus(data.statusInfo);
        _this3.expStyle = "width:" + data.abilityInfo.expValue / data.abilityInfo.maxExpValue * 100 + "%;";
        _this3.status = data.statusInfo;
        _this3.setAppInfo({ money: data.abilityInfo.money });
        // this.appInfo.money = data.abilityInfo.money;
        _this3.setStrengthCount(data.abilityInfo.strengthCount);
        _this3.strengthIntervalTimer = data.abilityInfo.strengthIntervalTimer;
        _this3.formatTime = _this3.getFormatTime(_this3.strengthIntervalTimer);
        if (_this3.strengthCount < 30) {
          _this3.startEnergeTimer();
        }

        // 聊天
        _this3.messageList = data.messageBoardList;
        _this3.setMessageListStorage(data.messageBoardList);
        _this3.startMsgTimer();

        //状态
        if (data.statusInfo.isDaySign == 1 || data.statusInfo.isHitCat == 1) {
          _this3.showDayGfit = true;
        }
        if (data.statusInfo.isHitCat == 1) {
          _this3.showCatNow = true;
          var t1 = setTimeout(function () {
            _this3.showCatNow = false;
            clearTimeout(t1);
          }, 2000);
        }
        _this3.isBookLevel = data.statusInfo.isBookUpLevel;
      });
    },
    stopMsgTimer: function stopMsgTimer() {
      clearInterval(this.msgTimer);
    },
    print: function print(str) {
      console.log(str);
    },
    handleShowModal: function handleShowModal(type) {
      this.giftType = type;
      this.showDayGfit = true;
    },
    showMenuFn: function showMenuFn(type) {
      if (type == "menu") {
        this.showMenu = true;
        this.setHasTipMenu(false);
      } else if (type == "ad") {
        this.showAdBox = true;
        this.setHasTipAd(false);
      }
      this.shake();
    },
    closeAdBox: function closeAdBox() {
      this.showAdBox = false;
    },
    shake: function shake() {
      wx.vibrateShort({
        success: function success() {
          console.log("点击震动");
        }
      });
    },
    getMusicSetting: function getMusicSetting() {
      var _this4 = this;

      wx.getStorage({
        key: "musicObj[bg]",
        success: function success(res) {
          if (!res.data) {
            _this4.setMusic(0, false, "bg");
          } else {
            _this4.setMusic(1, true, "bg");
          }
        },
        fail: function fail() {
          wx.setStorage({
            key: "musicObj[bg]",
            data: true
          });
          _this4.globalBgPlayer.play();
        }
      });
      wx.getStorage({
        key: "musicObj[sound]",
        success: function success(res) {
          if (!res.data) {
            _this4.setMusic(0, false, "sound");
          } else {
            _this4.setMusic(1, true, "sound");
          }
        },
        fail: function fail() {
          wx.setStorage({
            key: "musicObj[sound]",
            data: true
          });
        }
      });
    },
    unAble: function unAble() {
      wx.showToast({ title: "暂未开放", icon: "none" });
    },
    toLottery: function toLottery() {
      this.$router.push("/pages/lottery/index");
    },
    toBook: function toBook() {
      this.$router.push("/pages/book/index");
    },
    startEnergeTimer: function startEnergeTimer(type) {
      var _this5 = this;

      if (this.strengthCount < this.maxStrength) {
        this.energeTimer = setInterval(function () {
          _this5.strengthIntervalTimer--;
        }, 1000);
      }
    },
    stopEnergeTimer: function stopEnergeTimer() {
      clearInterval(this.energeTimer);
      wx.setStorage({
        key: "energeTimeTag",
        data: new Date().getTime()
      });
    },
    toSubject: function toSubject() {
      this.$router.push("/subjectFactoryPages/pages/index");
    },
    toUserInfo: function toUserInfo() {
      this.$router.push("/playerPages/pages/index?userId=" + this.appInfo.userId);
    },
    getDevice: function getDevice() {
      var _this6 = this;

      //获取设备信息
      var deviceInfo = {};
      wx.getSystemInfo({
        success: function success(res) {
          _this6.store.commit("setDeviceInfo", {
            width: res.windowWidth,
            height: res.windowHeight,
            brand: res.brand
          });
          console.log(res.brand.toLowerCase());
          if (res.brand.toLowerCase().indexOf("huawei") != -1 || res.brand.toLowerCase().indexOf("honor") != -1) {
            _this6.setHuawei(true);
          }
          deviceInfo = res;
        }
      });
      wx.getStorage({
        key: "hasDeviceInfo",
        fail: function fail() {
          wx.setStorage({
            key: "hasDeviceInfo",
            data: "true"
          });
          _this6.fly.post("/system", {
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
    countOutNum: function countOutNum() {
      console.log("调用跳转广告成功");
      this.fly.post("user/userToXcx");
    },
    countOutNumFail: function countOutNumFail() {
      console.log("调用跳转广告失败");
    },
    toRoom: function toRoom() {
      this.$router.push("/roomPages/pages/roomList");
    },
    toTalkRoom: function toTalkRoom() {
      if (this.showDayGfit || this.showPack || this.showMenu || this.showAdBox) {
        return false;
      }
      this.$router.push("/pages/talkRoom/index");
    },
    login: function login() {
      var _this7 = this;

      // 微信登录
      return new __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_promise___default.a(function (resolve, reject) {
        wx.login({
          success: function success(res) {
            var code = res.code;
            // 登录  title: "登录中,请稍后",
            wx.showLoading({
              title: "正在进入游戏,请稍后",
              mask: true
            });
            _this7.fly.get("/login", { code: code }).then(function (res) {
              if (res.data.Tag == 1) {
                _this7.setSessionId(res.data.SessionId); // 全局管理SesionId
                _this7.setSecretCode(res.data.Result.code); // 全局管理SesionId
                _this7.setOpenId(res.data.Result.openId); // 全局管理SesionId
                wx.hideLoading();
                // 用户信息初始化
                if (_this7.isAuthorize == 1) {
                  _this7.getInit();
                  _this7.getAllModuleType();
                }
                _this7.getDevice();
                console.log("登录成功");
                _this7.dataReady = true;
                resolve();
              }
            });
          },
          fail: function fail(res) {
            reject();
          }
        });
      });
    },
    checkGift: function checkGift() {
      var _this8 = this;

      /* 判定是否试玩成功 */
      var t1 = void 0,
          t2 = void 0;
      wx.getStorage({
        key: "tryTimeStart",
        success: function success(res) {
          t1 = res.data;
          t2 = new Date().getTime();
          if (t1) {
            console.log(t2 - t1.time);
            if (t2 - t1.time >= 20000) {
              console.log("试玩成功");
              // 试玩成功
              _this8.showAdBox = true;
              var t = setTimeout(function () {
                _this8.setTryGameName(t1.name);
                _this8.tryGameName = t1.name;
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
        fail: function fail() {
          console.log("未试玩游戏,不执行后续操作");
          t1 = 0;
        }
      });

      wx.removeStorage({ key: "tryTimeStart" });
    },
    initAudio: function initAudio() {
      var _this9 = this;

      var bgAudio = void 0,
          soundAudio = void 0;
      bgAudio = wx.createInnerAudioContext();
      soundAudio = wx.createInnerAudioContext();
      this.setGlobalBgPlayer(bgAudio);
      this.setGlobalSoundPlayer(soundAudio);
      this.globalBgPlayer.src = "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/gameBgm.mp3"; //bgm
      this.globalBgPlayer.volume = 0.5;
      this.globalBgPlayer.loop = true;

      this.globalBgPlayer.onPause(function (res) {
        _this9.setBgStatus("pause");
      });

      // this.globalBgPlayer.play();
    },
    setMusic: function setMusic(volume, state, type) {
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
        key: "musicObj[" + type + "]",
        data: state
      });
    },
    initPage: function initPage() {
      // 分享卡片进来，判断一下房间状态
      if ((this.$root.$mp.appOptions.scene == 1007 || this.$root.$mp.appOptions.scene == 1008) && this.$root.$mp.query.roomId && this.appInfo) {
        this.$router.push("/roomPages/pages/roomList?roomId=" + this.$root.$mp.query.roomId);
        this.$root.$mp.appOptions.scene = 1005;
      }
    },
    toRankList: function toRankList() {
      this.$router.push("/pages/rankPage/index");
    },
    weekChallenge: function weekChallenge() {
      this.$router.push("/pages/weekChallenge/index");
    },
    rankStart: function rankStart() {
      this.$router.push("/pages/preGamePage/index");
    },
    wait: function wait() {
      wx.showToast({
        title: "暂未开放,敬请期待",
        icon: "none"
      });
    },
    getInit: function getInit() {
      var _this10 = this;

      this.fly.post("/init", {
        userId: this.$root.$mp.query.userId || 0,
        isNew: this.isNewUser
      }).then(function (res) {
        _this10.seasonData = res.data.Result.seasonEnd;
        if (_this10.seasonData && _this10.seasonData.paragraph) {
          _this10.showSeason = true;
        }
        _this10.setAppInfo(res.data.Result);
        _this10.initTimer = setTimeout(function () {
          _this10.initPage();
        }, 500);

        // 显示每日福利
        if (_this10.appInfo.isDaySign == 1) {
          _this10.showDayGfit = true;
        }

        _this10.setDayCount(res.data.Result.dayCardNumber);
        _this10.setLotteryList(res.data.Result.prizeList);
        _this10.expStyle = "width:" + _this10.appInfo.expValue / _this10.appInfo.maxExpValue * 100 + "%;";
        wx.getStorage({
          key: "themeSet",
          success: function success(res) {
            _this10.setGamePageType(res.data);
          }
        });
      });
    },
    updateUserInfo: function updateUserInfo(event) {
      var _this11 = this;

      if (this.isUpdateUserInfo) {
        return false;
      }
      //每天请求一次
      var data = event.mp.detail.userInfo;
      this.fly.post("/authorize", {
        //后端授权
        nickName: data.nickName || "无名氏",
        avatarUrl: data.avatarUrl,
        gender: data.gender,
        city: data.city,
        province: data.province,
        country: data.country
      }).then(function () {
        _this11.appInfo.nickName = data.nickName;
        _this11.appInfo.avatarUrl = data.avatarUrl;
        _this11.appInfo.gender = data.gender;
        _this11.appInfo.city = data.city;
        _this11.appInfo.province = data.province;
        _this11.appInfo.country = data.country;

        _this11.setIsUpdateUserInfo(true);
        wx.setStorage({
          key: "day",
          data: new Date().getDate()
        });
      });
    },
    getUserInfo: function getUserInfo(event) {
      var _this12 = this;

      return __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default()( /*#__PURE__*/__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.mark(function _callee() {
        var data;
        return __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                data = event.mp.detail.userInfo;

                if (!data) {
                  _context.next = 7;
                  break;
                }

                wx.setStorage({
                  //记录授权状态
                  key: "isAuthorize",
                  data: "1"
                });
                _this12.isNewUser = 1;
                _context.next = 6;
                return _this12.login();

              case 6:
                _this12.fly.post("/authorize", {
                  //后端授权
                  nickName: data.nickName || "无名氏",
                  avatarUrl: data.avatarUrl,
                  gender: data.gender,
                  city: data.city,
                  province: data.province,
                  country: data.country
                }).then(function () {
                  _this12.getInit();
                  _this12.initPage();
                  _this12.setAuthorize(1);
                });

              case 7:
              case "end":
                return _context.stop();
            }
          }
        }, _callee, _this12);
      }))();
    },
    normalGame: function normalGame() {
      this.$router.push("/pages/game/index");
    },


    // -------------------------好友对战相关
    battleClickHandle: function battleClickHandle() {
      var _this13 = this;

      // TODO
      // 1.进入对战准备房间
      this.apiClickRoom().then(function (res) {
        if (res == 0) {
          _this13.$router.push("/pages/battleRoom/index");
        }
      });
    },
    getnewFoodCard: function getnewFoodCard() {
      var _this14 = this;

      wx.getStorage({
        key: "newFoodCard",
        success: function success(res) {
          _this14.newFoodCard = res.data;
        },
        fail: function fail() {
          _this14.newFoodCard = 0;
        }
      });
    },
    getRoomId: function getRoomId() {
      var _this15 = this;

      return new __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_promise___default.a(function (resolve, reject) {
        _this15.fly.post("/room/getRoomId").then(function (res) {
          _this15.setRoomId({ roomId: res.data.Result.code }); //顺便设置缓存
          resolve(res.data.Result.code);
        });
      });
    },
    apiClickRoom: function apiClickRoom() {
      var _this16 = this;

      return new __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_promise___default.a(function (resolve, reject) {
        _this16.fly.post("/room/clickRoom", { code: _this16.roomId }).then(function (res) {
          resolve(res.data.Result.isExpire);
        });
      });
    },


    // --------------各种弹框
    showSettingModal: function showSettingModal() {
      this.showSetting = true;
    }
  }),
  onShow: function onShow() {
    this.checkGift();
    this.getnewFoodCard();
    if (this.dataReady) {
      this.initPage();
      this.getAllModuleType();
    }
  },
  onHide: function onHide() {
    this.stopEnergeTimer();
    this.stopMsgTimer();
    clearInterval(this.initTimer);
  },
  mounted: function mounted() {
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
});

/***/ }),

/***/ 127:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_seasonModal_vue__ = __webpack_require__(129);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_9e6a9a8a_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_seasonModal_vue__ = __webpack_require__(130);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(128)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-9e6a9a8a"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_seasonModal_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_9e6a9a8a_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_seasonModal_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\modal\\seasonModal.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] seasonModal.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-9e6a9a8a", Component.options)
  } else {
    hotAPI.reload("data-v-9e6a9a8a", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 128:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 129:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vuex__ = __webpack_require__(1);

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//


/* harmony default export */ __webpack_exports__["a"] = ({
  name: "seasonModal",
  props: ["seasonData"],
  data: function data() {
    return {
      showInner: true,
      showGift: false,
      showAnt: false,
      showIndex: 0,
      giftList: [[{ type: 3, num: 4 }], [{ type: 3, num: 3 }], [{ type: 3, num: 1 }, { type: 2, num: 4 }, { type: 1, num: 20 }], [{ type: 3, num: 1 }, { type: 2, num: 4 }, { type: 1, num: 20 }], [{ type: 3, num: 1 }], [{ type: 2, num: 1 }, { type: 1, num: 15 }], [{ type: 2, num: 1 }, { type: 1, num: 10 }], [{ type: 2, num: 1 }, { type: 1, num: 5 }], [{ type: 1, num: 5 }]],
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

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["c" /* mapState */])(["IMGHEAD"]), {
    year: function year() {
      if (this.seasonData.seasonTime) {
        return this.seasonData.seasonTime.split("-")[0];
      }
    },
    month: function month() {
      if (this.seasonData.seasonTime) {
        return this.seasonData.seasonTime.split("-")[1];
      }
    },
    gIndex: function gIndex() {
      if (this.seasonData.paragraph) {
        return Math.ceil(this.seasonData.paragraph / 5) - 1;
      }
    }
  }),
  methods: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["b" /* mapMutations */])([""]), {
    confirmHandle: function confirmHandle() {
      var _this = this;

      this.showInner = false;
      this.showGift = true;
      setTimeout(function () {
        _this.showAnt = true;
        if (_this.showIndex == _this.giftList[_this.gIndex].length - 1) {
          setTimeout(function () {
            _this.showGift = false;
            _this.showAnt = false;
            _this.close();
          }, 1000);
        }
      }, 0);
    },
    changeGift: function changeGift() {
      var _this2 = this;

      try {
        console.log(" this.giftList[this.gIndex]", this.giftList[this.gIndex]);
        if (this.showIndex < this.giftList[this.gIndex].length - 1) {
          this.showAnt = false;
          setTimeout(function () {
            _this2.showIndex++;
            _this2.showAnt = true;
            if (_this2.showIndex == _this2.giftList[_this2.gIndex].length - 1) {
              setTimeout(function () {
                _this2.showGift = false;
                _this2.showAnt = false;
                _this2.close();
              }, 1000);
            }
          }, 100);
        }
      } catch (err) {
        console.log(err);
      }
    },
    close: function close() {
      this.$emit("close");
    }
  }),
  mounted: function mounted() {
    this.giftList = this.giftList.reverse();
  }
});

/***/ }),

/***/ 130:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-modal",
    attrs: {
      "eventid": '1'
    },
    on: {
      "click": _vm.changeGift
    }
  }, [(_vm.showInner) ? _c('div', {
    staticClass: "u-modal-inner"
  }, [_vm._m(0), _vm._v(" "), _c('div', {
    staticClass: "u-content"
  }, [_c('div', {
    staticClass: "u-tt"
  }, [_vm._v(_vm._s(_vm.year) + "年" + _vm._s(_vm.month) + "月赛季段位")]), _vm._v(" "), (_vm.seasonData.paragraphImage) ? _c('img', {
    staticClass: "u-medal",
    attrs: {
      "src": _vm.IMGHEAD + _vm.seasonData.paragraphImage
    }
  }) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "u-medal-name"
  }, [_vm._v(_vm._s(_vm.seasonData.paragraphName))]), _vm._v(" "), _vm._m(1), _vm._v(" "), _c('div', {
    staticClass: "u-gift-list"
  }, _vm._l((_vm.giftList[_vm.gIndex]), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "gift-item"
    }, [_c('img', {
      attrs: {
        "src": _vm.goodsMap[item.type].url
      }
    }), _vm._v(" "), _c('div', {
      staticClass: "gift-num"
    }, [_vm._v(_vm._s(item.num))])])
  })), _vm._v(" "), _c('div', {
    staticClass: "u-confirm",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": function($event) {
        $event.stopPropagation();
        _vm.confirmHandle($event)
      }
    }
  }, [_vm._v("确定")])])]) : _vm._e(), _vm._v(" "), (_vm.seasonData.paragraph && _vm.showGift) ? _c('div', {
    staticClass: "get-gift"
  }, [_c('div', {
    staticClass: "img-box",
    class: {
      'showAnt': _vm.showAnt
    }
  }, [_c('img', {
    staticClass: "box-bg",
    attrs: {
      "src": "/static/icon/gift-light.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "img-gift",
    attrs: {
      "src": _vm.goodsMap[_vm.giftList[_vm.gIndex][_vm.showIndex].type].url
    }
  })]), _vm._v("\n    " + _vm._s(_vm.goodsMap[_vm.giftList[_vm.gIndex][_vm.showIndex].type].name) + "X" + _vm._s(_vm.giftList[_vm.gIndex][_vm.showIndex].num) + "\n  ")]) : _vm._e()])
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-head"
  }, [_c('div', [_vm._v("赛季奖励")]), _vm._v(" "), _c('img', {
    staticClass: "u-head-img",
    attrs: {
      "src": "/static/modal-bg.png"
    }
  })])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-line"
  }, [_c('div', {
    staticClass: "line-txt"
  }, [_vm._v("获得奖励")])])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-9e6a9a8a", esExports)
  }
}

/***/ }),

/***/ 131:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_day_gift_vue__ = __webpack_require__(133);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_2d2c61d3_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_day_gift_vue__ = __webpack_require__(138);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(132)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-2d2c61d3"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_day_gift_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_2d2c61d3_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_day_gift_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\gift\\day-gift.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] day-gift.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-2d2c61d3", Component.options)
  } else {
    hotAPI.reload("data-v-2d2c61d3", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 132:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 133:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__utils_index__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__config_dataFile__ = __webpack_require__(13);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__get_gift__ = __webpack_require__(25);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__catGame__ = __webpack_require__(134);

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//








/* harmony default export */ __webpack_exports__["a"] = ({
  props: ["type", "showCatNow"],
  onShareAppMessage: function onShareAppMessage(res) {
    console.log("day");
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
    "get-gift": __WEBPACK_IMPORTED_MODULE_4__get_gift__["a" /* default */],
    "cat-game": __WEBPACK_IMPORTED_MODULE_5__catGame__["a" /* default */]
  },
  data: function data() {
    return {
      isDouble: false,
      menuIndex: 0,
      checkDay: 0,
      giftList: [{
        day: 1,
        description: "金币",
        id: 1,
        image: "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
        isLookNum: 1,
        name: "金币",
        num: 20,
        status: 0,
        type: 13
      }, {
        day: 1,
        description: "金币",
        id: 1,
        image: "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
        isLookNum: 1,
        name: "金币",
        num: 20,
        status: 0,
        type: 13
      }, {
        day: 1,
        description: "金币",
        id: 1,
        image: "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
        isLookNum: 1,
        name: "金币",
        num: 20,
        status: 0,
        type: 13
      }, {
        day: 1,
        description: "金币",
        id: 1,
        image: "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
        isLookNum: 1,
        name: "金币",
        num: 20,
        status: 0,
        type: 13
      }, {
        day: 1,
        description: "金币",
        id: 1,
        image: "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
        isLookNum: 1,
        name: "金币",
        num: 20,
        status: 0,
        type: 13
      }, {
        day: 1,
        description: "金币",
        id: 1,
        image: "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
        isLookNum: 1,
        name: "金币",
        num: 20,
        status: 0,
        type: 13
      }, {
        day: 1,
        description: "金币",
        id: 1,
        image: "/vientianeTestGoodsImage/92b3d07e-0320-440d-93d4-d4de33c292de.jpg",
        isLookNum: 1,
        name: "金币",
        num: 20,
        status: 0,
        type: 13
      }],
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
          icon: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/crown.png",
          route: "/roomPages/pages/roomList"
        },
        3: {
          icon: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/rank.png",
          route: "/pages/preGamePage/index"
        },
        4: {
          icon: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/prject.png",
          route: "/subjectFactoryPages/pages/index"
        },
        5: {
          icon: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/top.png",
          route: "/roomPages/pages/roomList"
        },
        6: {
          icon: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/lottery.png",
          route: "/pages/lottery/index"
        }
      },
      checkReady: false
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_3_vuex__["c" /* mapState */])(["IMGHEAD", "appInfo", "HUAWEI", "globalStatus"])),
  watch: {
    gold: function gold(val) {
      if (val >= 66) {
        this.timeType = 3;
      } else if (val >= 33) {
        this.timeType = 2;
      }
    }
  },
  methods: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_3_vuex__["b" /* mapMutations */])(["setAppInfo", "setGlobalStatusByItem"]), {
    throttle: __WEBPACK_IMPORTED_MODULE_1__utils_index__["f" /* throttle */],
    goTaskPlace: function goTaskPlace(taskType) {
      this.$router.push(this.taskMap[taskType].route);
      this.$emit("close");
    },
    changeMenu: function changeMenu(index) {
      this.menuIndex = index;
      if (index == 1) {
        return false;
      }
      this.getList(index + 1);
    },
    close: function close() {
      this.$emit("close");
    },
    goRank: function goRank() {
      this.$router.push("/pages/preGamePage/index");
      this.close();
    },
    handleStartClick: function handleStartClick() {
      if (this.canCat == 0) {
        wx.showModal({
          title: "系统提示",
          content: "撸猫时间：中午 11:00—14:00和晚上 17:00—22:00（总共两次机会）",
          showCancel: false
        });
        return false;
      } else {
        this.showCatGame = true;
      }
    },
    startCatGame: function startCatGame() {
      var _this = this;

      this.showGuide = false;
      this.gameStatus = 1; //游戏中
      this.timer = setInterval(function () {
        if (_this.gameTime >= 5000) {
          setTimeout(function () {
            _this.pStyle = "background:#f0af2b!important;";
          }, 100);
          _this.stopCatGame();
        }
        if (_this.gameTime >= 4000) {
          _this.addNum = 5;
          _this.pStyle = "background:#d33733!important;";
        } else if (_this.gameTime >= 3000) {
          _this.addNum = 3;
          _this.pStyle = "background:#f06c2b!important;";
        } else if (_this.gameTime >= 2000) {
          _this.addNum = 2;
          _this.pStyle = "background:#f08c2b!important;";
        } else if (_this.gameTime >= 1000) {
          // this.addNum = 1;
          _this.pStyle = "background:#f0af2b!important;";
        }
        _this.gameTime += 10;
      }, 10);
    },
    stopCatGame: function stopCatGame(gold) {
      var _this2 = this;

      this.canCat = 0; //一个时间段一次
      setTimeout(function () {
        _this2.showGfitAntBox([{
          url: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/many-gold.png",
          num: gold,
          name: "金币"
        }]);
        _this2.fly.post("dayWelfare/hitCat", {
          money: gold > 150 ? 150 : gold
        }).then(function (res) {
          console.log("金币增加", gold > 150 ? 150 : gold);
        });
        _this2.setAppInfo({ money: _this2.appInfo.money + (gold > 150 ? 150 : gold) });
      }, 1000);

      // TODO 金币展示 结算等
      setTimeout(function () {
        _this2.showCatGame = false;
      }, 4000);
    },
    getTaskGift: function getTaskGift() {
      var _this3 = this;

      if (this.receive) {
        wx.showToast({
          title: "已经领取过奖励啦",
          icon: "none"
        });
        return false;
      }
      if (this.taskComplete) {
        this.fly.post("dayTask/receiveGood").then(function () {
          _this3.showGiftList = [{
            name: "小宝箱",
            url: "/static/icon/s-box.png",
            num: 1
          }];
          _this3.showGetGift = true;
          _this3.receive = true;
        });
      } else {
        wx.showToast({
          title: "尚有未完成任务",
          icon: "none"
        });
      }
    },
    catClick: function catClick() {
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
    showGfitAntBox: function showGfitAntBox(list) {
      this.showGiftList = list;
      this.showGetGift = true;
    },
    sign: function sign(type) {
      var _this4 = this;

      var _type = type || 1;
      if (type == 1 && !this.checkReady) {
        return false;
      }
      if (this.globalStatus.isDaySign == 1) {
        this.fly.post("dayWelfare/sign", {
          type: _type,
          day: this.checkDay + 1
        }).then(function (res) {
          if (res.data.Tag == 1) {
            _this4.setGlobalStatusByItem({ isDaySign: 0 });
            // this.globalStatus.isDaySign = 0;
            _this4.giftList[_this4.checkDay].status = 1;
            if (_this4.giftList[_this4.checkDay].type == 13) {
              _this4.setAppInfo({ money: _this4.appInfo.money + _this4.giftList[_this4.checkDay].num * _type });
              // this.appInfo.money += this.giftList[this.checkDay].num * type;
            }
            _this4.showGfitAntBox([{
              url: _this4.IMGHEAD + _this4.giftList[_this4.checkDay].image,
              num: _this4.giftList[_this4.checkDay].num * _type,
              name: _this4.giftList[_this4.checkDay].name
            }]);
          }
        });
      } else {
        wx.showToast({ title: "已经签到过了~！", icon: "none" });
      }
    },
    getNumberGift: function getNumberGift(item) {
      var _this5 = this;

      this.fly.post("dayWelfare/numberReceive", {
        number: item.number
      }).then(function (res) {
        item.status = 1;
        var x = 1;
        if (item.isWin == 1) {
          x = 2;
        }
        _this5.isDouble = true;
        setTimeout(function () {
          _this5.isDouble = false;
        }, 2000);
        _this5.showGfitAntBox([{
          url: _this5.IMGHEAD + item.image,
          num: item.num * x,
          name: item.name
        }]);
        if (item.type == 13) {
          _this5.setAppInfo({ money: _this5.appInfo.money + item.num * x });
        }
      });
    },
    checkTaskComplete: function checkTaskComplete() {
      for (var i = 0; i < this.taskList.length; i++) {
        if (this.taskList[i].status == 0) {
          this.taskComplete = false;
          break;
        } else {
          this.taskComplete = true;
        }
      }
      for (var _i = 0; _i < this.taskList.length; _i++) {
        if (this.taskList[_i].receive == 0) {
          this.receive = false;
          break;
        } else {
          this.receive = true;
        }
      }
    },
    getTaskList: function getTaskList() {
      var _this6 = this;

      this.fly.post("/dayTask/getTaskList").then(function (res) {
        _this6.taskList = res.data.Result.taskList;
        _this6.checkTaskComplete();
      });
    },
    getList: function getList(type) {
      var _this7 = this;

      this.fly.post("dayWelfare/getDayWelfareList", {
        type: type
      }).then(function (res) {
        if (type == 1) {
          //签到列表
          _this7.giftList = res.data.Result.daySignList;
          for (var i = 0; i < _this7.giftList.length; i++) {
            if (_this7.giftList[i].status == 0) {
              _this7.checkDay = i;
              break;
            }
          }
          _this7.checkReady = true;
          _this7.canCat = res.data.Result.isHitCat;
          _this7.canNumber = res.data.Result.isLuckNumber;
        }
        if (type == 2) {
          console.log(res.data);
        }
        if (type == 3) {
          _this7.numList = res.data.Result;
        }
      });
    }
  }),
  onShow: function onShow() {
    if (this.hideType == "share") {
      this.sign(2);
      this.hideType = "";
    }
  },
  mounted: function mounted() {
    if (this.type == 1) {
      if (this.showCatNow) {
        this.showCatGame = true;
      }
      this.getList(1);
    } else if (this.type == 0) {
      this.getTaskList();
    }
  }
});

/***/ }),

/***/ 134:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_catGame_vue__ = __webpack_require__(136);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_97e0e574_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_catGame_vue__ = __webpack_require__(137);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(135)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-97e0e574"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_catGame_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_97e0e574_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_catGame_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\gift\\catGame.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] catGame.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-97e0e574", Component.options)
  } else {
    hotAPI.reload("data-v-97e0e574", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 135:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 136:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/* harmony default export */ __webpack_exports__["a"] = ({
  data: function data() {
    return {
      gold: 0,
      gameStatus: 0,
      timeType: 1,
      cf: false,
      showGuide: true,
      timer: null,
      gameTime: 0,
      pStyle: "",
      addNum: 1,
      showCatAd: false
    };
  },


  methods: {
    startCatGame: function startCatGame() {
      var _this = this;

      this.showGuide = false;
      this.gameStatus = 1; //游戏中
      setTimeout(function () {
        _this.pStyle = "background:#f0af2b!important;";
      }, 1000);

      setTimeout(function () {
        _this.addNum = 2;
        _this.pStyle = "background:#f08c2b!important;";
      }, 2000);

      setTimeout(function () {
        _this.addNum = 3;
        _this.pStyle = "background:#f06c2b!important;";
      }, 3000);

      setTimeout(function () {
        _this.addNum = 5;
        _this.pStyle = "background:#f06c2b!important;";
      }, 4000);

      setTimeout(function () {
        console.log('游戏结束');
        _this.gameStatus = 0;
        _this.showCatAd = true;
        _this.$emit("stopGame", _this.gold);
      }, 5000);
    },
    catClick: function catClick() {
      if (this.gameStatus == 0) {
        wx.showToast({ title: "游戏已结束", icon: "none" });
        return false;
      }
      this.gold += this.addNum;
      if (this.gold >= 66) {
        this.timeType = 3;
      } else if (this.gold >= 33) {
        this.timeType = 2;
      }
    }
  }
});

/***/ }),

/***/ 137:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "game-box"
  }, [_c('div', {
    staticClass: "cat-game"
  }, [_c('div', {
    staticClass: "process"
  }, [_c('div', {
    staticClass: "txt"
  }, [_vm._v("剩余时间:")]), _vm._v(" "), _c('div', {
    staticClass: "p-body"
  }, [_c('img', {
    staticClass: "p-bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/p-bg.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "p-line",
    class: {
      'process-ant': _vm.gameStatus == 1
    },
    style: (_vm.pStyle)
  }), _vm._v(" "), _c('div', {
    staticClass: "sc"
  }), _vm._v(" "), _c('div', {
    staticClass: "sc"
  }), _vm._v(" "), _c('div', {
    staticClass: "sc"
  }), _vm._v(" "), _c('div', {
    staticClass: "sc"
  })])]), _vm._v(" "), _c('div', {
    staticClass: "platform"
  }, [_c('img', {
    staticClass: "l-bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/l-bg.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "cat-modal-1",
    class: {
      show: _vm.timeType == 1
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "cat-modal-2",
    class: {
      show: _vm.timeType == 2
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/modal-2.png"
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "modal-2-ant",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/modal-2-act.png"
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "cat-modal-3",
    class: {
      show: _vm.timeType == 3
    }
  }, [_c('img', {
    staticClass: "modal-3-l",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/modal-3-l.png"
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "modal-3-r",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/modal-3-r.png"
    }
  }), _vm._v(" "), _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/modal-3.png"
    }
  })]), _vm._v(" "), (_vm.gameStatus == 1) ? _c('div', {
    staticClass: "gold-ant"
  }) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "gold-txt"
  }, [_vm._v("获的金币数")]), _vm._v(" "), _c('div', {
    staticClass: "gold"
  }, [_c('img', {
    attrs: {
      "src": "/static/gold.png"
    }
  }), _vm._v(" " + _vm._s(_vm.gold) + "\n    ")]), _vm._v(" "), (_vm.showGuide) ? _c('div', {
    staticClass: "guide"
  }, [_vm._m(0)]) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "cf",
    attrs: {
      "eventid": '1'
    },
    on: {
      "click": _vm.catClick
    }
  }, [(_vm.showGuide) ? _c('div', {
    staticClass: "cf-mask",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.startCatGame
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showGuide) ? _c('img', {
    staticClass: "finger",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/finger.png"
    }
  }) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "cat-ad",
    class: {
      showAd: _vm.showCatAd
    }
  }, [_c('ad', {
    attrs: {
      "unit-id": "adunit-bd7cb52099803182",
      "mpcomid": '0'
    }
  })], 1)])])
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "guide-txt"
  }, [_c('div', [_vm._v("温馨提示")]), _vm._v("\n        快速、持续点击,可以获得更多金币哦\n      ")])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-97e0e574", esExports)
  }
}

/***/ }),

/***/ 138:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "day-gift"
  }, [_c('div', {
    staticClass: "box",
    class: {
      hide: _vm.showCatGame
    }
  }, [_c('img', {
    staticClass: "bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/day-gift-bg.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "content"
  }, [(_vm.type == 1) ? _c('div', {
    staticClass: "menu"
  }, [_c('div', {
    class: {
      hasTip: _vm.globalStatus.isDaySign == 1
    }
  }, [(_vm.menuIndex == 0) ? _c('img', {
    staticClass: "check",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/check-act.png"
    }
  }) : _c('img', {
    staticClass: "check",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/check.png",
      "eventid": '0'
    },
    on: {
      "click": function($event) {
        _vm.changeMenu(0)
      }
    }
  })]), _vm._v(" "), _c('div', {
    class: {
      hasTip: _vm.canCat == 1
    }
  }, [(_vm.menuIndex == 1) ? _c('img', {
    staticClass: "cat",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/cat-act.png"
    }
  }) : _c('img', {
    staticClass: "cat",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/cat.png",
      "eventid": '1'
    },
    on: {
      "click": function($event) {
        _vm.changeMenu(1)
      }
    }
  })]), _vm._v(" "), _c('div', {
    class: {
      hasTip: _vm.canNumber == 1
    }
  }, [(_vm.menuIndex == 2) ? _c('img', {
    staticClass: "num",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/luck-num-act.png"
    }
  }) : _c('img', {
    staticClass: "num",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/luck-num.png",
      "eventid": '2'
    },
    on: {
      "click": function($event) {
        _vm.changeMenu(2)
      }
    }
  })])]) : _vm._e(), _vm._v(" "), (_vm.type == 0) ? _c('div', {
    staticClass: "task-head"
  }, [_c('img', {
    staticClass: "t-bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/taskGiftBg.png"
    }
  }), _vm._v(" "), (_vm.receive) ? _c('div', [_vm._v("已领取")]) : _c('img', {
    staticClass: "t-gift",
    attrs: {
      "src": "/static/icon/s-box.png"
    }
  })]) : _vm._e(), _vm._v(" "), (_vm.menuIndex == 0 && _vm.type == 1) ? _c('section', [_c('div', {
    staticClass: "check-box"
  }, _vm._l((_vm.giftList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "gift-item"
    }, [_c('div', {
      staticClass: "g-box"
    }, [(index == _vm.checkDay && _vm.globalStatus.isDaySign == 1) ? _c('img', {
      staticClass: "g-able",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/able.png"
      }
    }) : _vm._e(), _vm._v(" "), _c('img', {
      staticClass: "g-gift",
      attrs: {
        "src": _vm.IMGHEAD + item.image
      }
    }), _vm._v(" "), (item.isLookNum) ? _c('div', {
      staticClass: "g-num"
    }, [_vm._v(_vm._s(item.num))]) : _vm._e(), _vm._v(" "), (item.status == 1) ? _c('img', {
      staticClass: "g-receved",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved.png"
      }
    }) : _vm._e()]), _vm._v(" "), _c('div', {
      staticClass: "g-time"
    }, [_vm._v(_vm._s(item.description))])])
  })), _vm._v(" "), _c('button', {
    staticClass: "db-btn",
    attrs: {
      "open-type": "share",
      "plain": "true"
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/double.png",
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "sg-btn",
    attrs: {
      "eventid": '3'
    },
    on: {
      "click": function($event) {
        _vm.throttle(_vm.sign, 200)
      }
    }
  }, [_vm._v("普通领取")])], 1) : _vm._e(), _vm._v(" "), (_vm.menuIndex == 1 && _vm.type == 1) ? _c('section', [_c('div', {
    staticClass: "cat-bg"
  }), _vm._v(" "), _c('img', {
    staticClass: "cat-btn",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/play-cat.png",
      "eventid": '4'
    },
    on: {
      "click": _vm.handleStartClick
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "tip"
  }, [_vm._v("每天可以从这里获取金币哦")])]) : _vm._e(), _vm._v(" "), (_vm.menuIndex == 2 && _vm.type == 1) ? _c('section', [_c('div', {
    staticClass: "num-tip"
  }, [_vm._v("每周一0:00更新任务")]), _vm._v(" "), _c('div', {
    staticClass: "number-box pt44"
  }, _vm._l((_vm.numList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "number-gift-item"
    }, [_c('div', {
      staticClass: "score-box"
    }, [_c('img', {
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/score-box.png"
      }
    }), _vm._v(" "), _c('div', [_vm._v(_vm._s(item.number))])]), _vm._v(" "), _c('div', {
      staticClass: "gift-box"
    }, [_c('img', {
      attrs: {
        "src": _vm.IMGHEAD + item.image
      }
    }), _vm._v(" "), (item.isLookNum == 1) ? _c('div', {
      staticClass: "gift-num"
    }, [_vm._v(_vm._s(item.num))]) : _vm._e(), _vm._v(" "), (item.status == 1) ? _c('img', {
      staticClass: "r-tag",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved-tag.png"
      }
    }) : _vm._e()]), _vm._v(" "), (item.status == 0) ? _c('img', {
      staticClass: "gift-btn",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/get-btn.png",
        "eventid": '5-' + index
      },
      on: {
        "click": function($event) {
          _vm.getNumberGift(item)
        }
      }
    }) : _vm._e(), _vm._v(" "), (item.status == -1) ? _c('img', {
      staticClass: "gift-btn",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/go-btn.png",
        "eventid": '6-' + index
      },
      on: {
        "click": _vm.goRank
      }
    }) : _vm._e(), _vm._v(" "), (item.status == 1) ? _c('img', {
      staticClass: "gift-btn-receved",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved-txt.png"
      }
    }) : _vm._e()])
  })), _vm._v(" "), _c('div', {
    staticClass: "tip ft24"
  }, [_vm._v("排位赛中达到以上分数,可获得相应奖励,获胜情况下,奖励翻倍")])]) : _vm._e(), _vm._v(" "), (_vm.type == 0) ? _c('section', [_c('div', {
    staticClass: "number-box task-list"
  }, _vm._l((_vm.taskList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "number-gift-item task-item"
    }, [_c('div', {
      staticClass: "task-box"
    }, [(_vm.taskList[0] != 0) ? _c('img', {
      attrs: {
        "src": _vm.taskMap[item.type].icon
      }
    }) : _vm._e()]), _vm._v(" "), _c('div', {
      staticClass: "task-info"
    }, [_c('div', {
      staticClass: "t-top"
    }, [_vm._v("\n                " + _vm._s(item.description) + "\n              ")]), _vm._v(" "), _c('div', {
      staticClass: "t-bottom"
    }, [_c('div', [_vm._v("进度:")]), _vm._v("\n                " + _vm._s(item.status) + "/1\n              ")])]), _vm._v(" "), (item.status == 0) ? _c('img', {
      staticClass: "gift-btn",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/go-btn.png",
        "eventid": '7-' + index
      },
      on: {
        "click": function($event) {
          _vm.goTaskPlace(item.type)
        }
      }
    }) : _vm._e(), _vm._v(" "), (item.status == 1) ? _c('img', {
      staticClass: "gift-btn-receved",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/complete.png"
      }
    }) : _vm._e()])
  })), _vm._v(" "), _c('img', {
    staticClass: "get-task",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/get-btn-big.png",
      "eventid": '8'
    },
    on: {
      "click": _vm.getTaskGift
    }
  })]) : _vm._e()], 1), _vm._v(" "), (_vm.type == 1) ? _c('img', {
    staticClass: "tt",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/day-gift-tt.png"
    }
  }) : _vm._e(), _vm._v(" "), (_vm.type == 0) ? _c('img', {
    staticClass: "tt",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/tt-task.png"
    }
  }) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "close",
    attrs: {
      "eventid": '9'
    },
    on: {
      "click": _vm.close
    }
  })]), _vm._v(" "), (_vm.showCatGame) ? _c('cat-game', {
    attrs: {
      "eventid": '10',
      "mpcomid": '0'
    },
    on: {
      "stopGame": _vm.stopCatGame
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showGetGift) ? _c('get-gift', {
    attrs: {
      "giftList": _vm.showGiftList,
      "double": _vm.isDouble,
      "eventid": '11',
      "mpcomid": '1'
    },
    on: {
      "close": function($event) {
        _vm.showGetGift = false
      }
    }
  }) : _vm._e(), _vm._v(" "), (!_vm.showCatGame) ? _c('div', {
    staticClass: "main-ad",
    class: {
      'huawei': _vm.HUAWEI
    }
  }, [_c('ad', {
    attrs: {
      "unit-id": "adunit-e02686c515746fa0",
      "mpcomid": '2'
    }
  })], 1) : _vm._e()], 1)
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-2d2c61d3", esExports)
  }
}

/***/ }),

/***/ 139:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 140:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__components_gift_get_gift__ = __webpack_require__(25);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//



/* harmony default export */ __webpack_exports__["a"] = ({
  data: function data() {
    return {
      showGetGift: false,
      userList: [],
      hideForShare: -1,
      gift: [{
        url: "/static/icon/m-box.png",
        name: "中宝箱",
        num: 1
      }]
    };
  },

  components: {
    "get-gift": __WEBPACK_IMPORTED_MODULE_0__components_gift_get_gift__["a" /* default */]
  },
  methods: {
    close: function close() {
      this.$emit("close");
    },
    initUserList: function initUserList() {
      var list = [];
      for (var i = 0; i < 100; i++) {
        list.push({
          headImgUrl: "",
          userId: 0,
          nickName: "",
          status: 0
        });
      }
      this.userList = list;
    },
    hideForShareFn: function hideForShareFn(index) {
      this.hideForShare = index;
    },
    getUserList: function getUserList() {
      var _this = this;

      this.fly.post("friend/getFriendList").then(function (res) {
        for (var i = 0; i < res.data.Result.length; i++) {
          _this.userList[i] = res.data.Result[i];
        }
      });
    }
  },
  onShow: function onShow() {
    var _this2 = this;

    if (this.hideForShare != -1) {
      this.fly.post("friend/receiveFriendGood", {
        userId: this.userList[this.hideForShare].userId,
        goodType: 5,
        goodNum: 1
      }).then(function () {
        _this2.showGetGift = true;
        _this2.userList[_this2.hideForShare].status = 1;
        _this2.hideForShare = -1;
      });
    }
  },
  mounted: function mounted() {
    this.initUserList();
    this.getUserList();
  }
});

/***/ }),

/***/ 141:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "invate-box mask-bg "
  }, [_c('div', {
    staticClass: "i-body pos-r"
  }, [_c('img', {
    staticClass: "bg w100 h100",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/invate-bg.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "list pos-a left-center"
  }, _vm._l((_vm.userList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "item w100 flex"
    }, [_c('div', {
      staticClass: "item-index border-50 flex"
    }, [_vm._v(_vm._s(index + 1))]), _vm._v(" "), _c('div', {
      staticClass: "item-head-box flex"
    }, [_c('div', {
      staticClass: "item-head flex",
      class: {
        'head-bg': item.userId != 0
      }
    }, [(item.userId == 0) ? _c('button', {
      staticClass: "item-empty w100 h100",
      attrs: {
        "open-type": "share",
        "plain": "true"
      }
    }, [_c('img', {
      staticClass: "w100 h100",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/empty-btn.png"
      }
    })]) : _c('img', {
      attrs: {
        "src": item.headImgUrl
      }
    })], 1), _vm._v(" "), _c('div', {
      staticClass: "item-name"
    }, [_vm._v(_vm._s(item.nickname))])]), _vm._v(" "), _vm._m(0, true), _vm._v(" "), _c('div', {
      staticClass: "item-stauts flex"
    }, [(item.status == 0 && item.userId != 0) ? _c('button', {
      staticClass: "get-btn",
      attrs: {
        "open-type": "share",
        "plain": "true",
        "eventid": '0-' + index
      },
      on: {
        "click": function($event) {
          _vm.hideForShareFn(index)
        }
      }
    }, [_c('img', {
      staticClass: "w100 h100",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/get-btn.png"
      }
    })]) : (item.status == 0 && item.userId == 0) ? _c('img', {
      staticClass: "btn-unable",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/get-btn-unable.png"
      }
    }) : _c('img', {
      staticClass: "receved-btn",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved-txt.png"
      }
    })], 1)])
  })), _vm._v(" "), _c('img', {
    staticClass: "close-btn pos-a",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/close-btn.png",
      "eventid": '1'
    },
    on: {
      "click": _vm.close
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "box pos-a",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/box.png"
    }
  }), _vm._v(" "), _c('button', {
    staticClass: "invate-btn left-center pos-a",
    attrs: {
      "open-type": "share",
      "plain": "true"
    }
  }, [_c('img', {
    staticClass: "w100 h100",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/invate-btn.png"
    }
  })])], 1), _vm._v(" "), (_vm.showGetGift) ? _c('get-gift', {
    attrs: {
      "giftList": _vm.gift,
      "eventid": '2',
      "mpcomid": '0'
    },
    on: {
      "close": function($event) {
        _vm.showGetGift = false
      }
    }
  }) : _vm._e()], 1)
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "item-gift flex border-50"
  }, [_c('img', {
    attrs: {
      "src": "/static/icon/m-box.png"
    }
  })])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-191c79be", esExports)
  }
}

/***/ }),

/***/ 142:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_menu_vue__ = __webpack_require__(144);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_0058e818_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_menu_vue__ = __webpack_require__(157);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(143)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-0058e818"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_menu_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_0058e818_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_menu_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\entry\\components\\menu.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] menu.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-0058e818", Component.options)
  } else {
    hotAPI.reload("data-v-0058e818", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 143:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 144:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__components_modal_settingModal__ = __webpack_require__(145);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__components_modal_emailModal__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__components_modal_packModal__ = __webpack_require__(149);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__components_public_invateBox__ = __webpack_require__(71);

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//







/* harmony default export */ __webpack_exports__["a"] = ({
  props: ["status"],
  components: {
    "setting-modal": __WEBPACK_IMPORTED_MODULE_2__components_modal_settingModal__["a" /* default */],
    "email-modal": __WEBPACK_IMPORTED_MODULE_3__components_modal_emailModal__["a" /* default */],
    "pack-modal": __WEBPACK_IMPORTED_MODULE_4__components_modal_packModal__["a" /* default */],
    "invate-box": __WEBPACK_IMPORTED_MODULE_5__components_public_invateBox__["a" /* default */]
  },
  data: function data() {
    return {
      showBox: false,
      showEmail: false,
      showSetting: false,
      showPack: false,
      showInvateBox: false
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["c" /* mapState */])(["appInfo", "globalBgPlayer", "globalSoundPlayer", "hasTipLottery"])),
  methods: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["b" /* mapMutations */])(["setMusic", "setHasTipLottery"]), {
    toUserInfo: function toUserInfo() {
      this.$router.push("/playerPages/pages/index?userId=" + this.appInfo.userId);
    },
    showInvateBoxFn: function showInvateBoxFn(type) {
      this.showInvateBox = type;
      this.$emit("invateBox", type);
    },
    toTalk: function toTalk() {
      this.$router.push("/pages/talkRoom/index");
    },
    toBook: function toBook() {
      this.$router.push("/pages/book/index");
    },
    toLottery: function toLottery() {
      this.$router.push("/pages/lottery/index");
      this.setHasTipLottery(false);
    },
    toProject: function toProject() {
      this.$router.push("/subjectFactoryPages/pages/index");
    },
    showSet: function showSet(event) {
      console.log(event);
      this.showSetting = true;
    },
    showGiftBox: function showGiftBox(type) {
      this.$emit("showModal", type);
    },
    showGodRank: function showGodRank() {
      this.$router.push("/pages/rankPage/index?god=true");
    },
    unAble: function unAble() {
      wx.showToast({ title: "暂未开放", icon: "none" });
    },
    setMusic: function setMusic(volume, state, type) {
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
        key: "musicObj[" + type + "]",
        data: state
      });
    },
    handleRadioOneChange: function handleRadioOneChange(value) {
      if (value) {
        this.setMusic(1, true, "bg");
      } else {
        this.setMusic(0, false, "bg");
      }
    },
    handleRadioTwoChange: function handleRadioTwoChange(value) {
      if (value) {
        this.setMusic(1, true, "sound");
      } else {
        this.setMusic(0, false, "sound");
      }
    },
    closeEmail: function closeEmail() {
      this.showEmail = false;
    },
    close: function close() {
      var _this = this;

      this.showBox = false;
      setTimeout(function () {
        _this.$emit("close");
      }, 200);
    }
  }),
  mounted: function mounted() {
    var _this2 = this;

    setTimeout(function () {
      _this2.showBox = true;
    }, 200);
  }
});

/***/ }),

/***/ 145:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_settingModal_vue__ = __webpack_require__(147);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_109ccddc_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_settingModal_vue__ = __webpack_require__(148);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(146)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-109ccddc"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_settingModal_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_109ccddc_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_settingModal_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\modal\\settingModal.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] settingModal.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-109ccddc", Component.options)
  } else {
    hotAPI.reload("data-v-109ccddc", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 146:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 147:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vuex__ = __webpack_require__(1);

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//


/* harmony default export */ __webpack_exports__["a"] = ({
  name: "settingModal",
  props: [],
  data: function data() {
    return {
      showRadio: true,
      radioOne: false,
      radioTwo: false,
      showThemeView: false,
      radioIndex: 0,
      viewIndex: 0,
      radioList: [{ name: "星空", color: "color:#a065ea" }, { name: "天蓝", color: "color:#557ef3" }]
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["c" /* mapState */])([""])),
  methods: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["b" /* mapMutations */])(["setGamePageType"]), {
    radioChangeOne: function radioChangeOne() {
      this.radioOne = !this.radioOne;
      this.$emit('radioChangeOne', this.radioOne);
    },
    radioChangeTwo: function radioChangeTwo() {
      this.radioTwo = !this.radioTwo;
      this.$emit('radioChangeTwo', this.radioTwo);
    },
    close: function close() {
      this.$emit('close');
    },
    viewTheme: function viewTheme(index) {
      this.showThemeView = true;
      this.viewIndex = index;
    },
    changeTheme: function changeTheme(index) {
      this.radioIndex = index;
      this.setGamePageType(index);
      wx.setStorage({
        key: "themeSet",
        data: index
      });
    },
    getSetting: function getSetting() {
      var _this = this;

      wx.getStorage({
        key: "musicObj[bg]",
        success: function success(res) {
          _this.radioOne = res.data;
        },
        fail: function fail() {
          _this.radioOne = true;
        }
      });
      wx.getStorage({
        key: "themeSet",
        success: function success(res) {
          _this.radioIndex = res.data;
        },
        fail: function fail() {
          _this.radioIndex = 0;
        }
      });
      wx.getStorage({
        key: "musicObj[sound]",
        success: function success(res) {
          _this.radioTwo = res.data;
        },
        fail: function fail() {
          _this.radioTwo = true;
        }
      });
    }
  }),
  mounted: function mounted() {
    this.getSetting();
  }
});

/***/ }),

/***/ 148:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-modal"
  }, [_c('div', {
    staticClass: "u-modal-inner"
  }, [_c('div', {
    staticClass: "close",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.close
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-head"
  }, [(_vm.showRadio) ? _c('div', [_vm._v("设置")]) : _c('div', [_vm._v("公告")]), _vm._v(" "), _c('img', {
    staticClass: "u-head-img",
    attrs: {
      "src": "/static/modal-bg.png",
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-content"
  }, [(_vm.showRadio) ? _c('div', {
    staticClass: "u-radio-box"
  }, [_c('div', {
    staticClass: "u-radio",
    class: {
      'u-radio-act': _vm.radioOne
    },
    attrs: {
      "eventid": '1'
    },
    on: {
      "click": _vm.radioChangeOne
    }
  }, [_c('div', {
    staticClass: "u-radio-name"
  }, [_vm._v("音乐")]), _vm._v(" "), _vm._m(0)]), _vm._v(" "), _c('div', {
    staticClass: "u-radio",
    class: {
      'u-radio-act': _vm.radioTwo
    },
    attrs: {
      "eventid": '2'
    },
    on: {
      "click": _vm.radioChangeTwo
    }
  }, [_c('div', {
    staticClass: "u-radio-name"
  }, [_vm._v("音效")]), _vm._v(" "), _vm._m(1)])]) : _vm._e(), _vm._v(" "), (_vm.showRadio) ? _c('div', {
    staticClass: "theme"
  }, [_c('div', {
    staticClass: "t-t"
  }, [_vm._v("\n          主题选择\n        ")]), _vm._v(" "), _vm._l((_vm.radioList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "t-radio-box",
      class: {
        'selected': index == _vm.radioIndex
      }
    }, [_c('div', {
      staticClass: "t-radio",
      attrs: {
        "eventid": '3-' + index
      },
      on: {
        "click": function($event) {
          _vm.changeTheme(index)
        }
      }
    }), _vm._v(" "), _c('div', {
      staticClass: "t-radio-label",
      style: (item.color),
      attrs: {
        "eventid": '4-' + index
      },
      on: {
        "click": function($event) {
          _vm.changeTheme(index)
        }
      }
    }, [_vm._v(_vm._s(item.name))]), _vm._v(" "), _c('div', {
      staticClass: "t-btn",
      attrs: {
        "eventid": '5-' + index
      },
      on: {
        "click": function($event) {
          _vm.viewTheme(index)
        }
      }
    }, [_vm._v("预览")])])
  })], 2) : _c('div', {
    staticClass: "u-notice-box"
  }, [_c('div', {
    staticClass: "u-notice-head"
  }, [_vm._v("游戏宗旨:")]), _vm._v(" "), _c('div', {
    staticClass: "u-notice-content"
  }, [_vm._v("公平竞技、巩固知识！")]), _vm._v(" "), _c('div', {
    staticClass: "u-notice-head"
  }, [_vm._v("温馨提示:")]), _vm._v(" "), _c('div', {
    staticClass: "u-notice-content"
  }, [_vm._v("适度游戏益脑，沉迷游戏伤身，")]), _vm._v(" "), _c('div', {
    staticClass: "u-notice-content"
  }, [_vm._v("合理安排时间，享受生活快乐！")]), _vm._v(" "), _c('div', {
    staticClass: "u-notice-content"
  }, [_vm._v("添加微信号：bingiling，进入万象大考互助群")])]), _vm._v(" "), _c('div', {
    staticClass: "u-btn-box"
  }, [(_vm.showRadio) ? _c('div', {
    staticClass: "u-btn u-btn-red",
    attrs: {
      "eventid": '7'
    },
    on: {
      "click": function($event) {
        _vm.showRadio = false
      }
    }
  }, [_vm._v("公告")]) : _c('div', {
    staticClass: "u-btn u-btn-red",
    attrs: {
      "eventid": '6'
    },
    on: {
      "click": function($event) {
        _vm.showRadio = true
      }
    }
  }, [_vm._v("设置")]), _vm._v(" "), _c('button', {
    staticClass: "u-btn u-btn-green",
    attrs: {
      "open-type": "contact"
    }
  }, [_vm._v("客服")])], 1)])]), _vm._v(" "), (_vm.showThemeView) ? _c('div', {
    staticClass: "theme-view",
    attrs: {
      "eventid": '8'
    },
    on: {
      "click": function($event) {
        _vm.showThemeView = false
      }
    }
  }, [(_vm.viewIndex == 0) ? _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/theme-1.png"
    }
  }) : _vm._e(), _vm._v(" "), (_vm.viewIndex == 1) ? _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/theme-2.png"
    }
  }) : _vm._e()]) : _vm._e()])
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-radio-body"
  }, [_c('div', {
    staticClass: "u-radio-ball"
  }), _vm._v(" "), _c('div', {
    staticClass: "u-radio-txt-act"
  }, [_vm._v("开")]), _vm._v(" "), _c('div', {
    staticClass: "u-radio-txt"
  }, [_vm._v("关")])])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-radio-body"
  }, [_c('div', {
    staticClass: "u-radio-ball"
  }), _vm._v(" "), _c('div', {
    staticClass: "u-radio-txt-act"
  }, [_vm._v("开")]), _vm._v(" "), _c('div', {
    staticClass: "u-radio-txt"
  }, [_vm._v("关")])])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-109ccddc", esExports)
  }
}

/***/ }),

/***/ 149:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_packModal_vue__ = __webpack_require__(151);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_3a098a85_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_packModal_vue__ = __webpack_require__(156);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(150)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-3a098a85"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_packModal_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_3a098a85_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_packModal_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\modal\\packModal.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] packModal.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-3a098a85", Component.options)
  } else {
    hotAPI.reload("data-v-3a098a85", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 150:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 151:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator__ = __webpack_require__(18);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_promise__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_promise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_promise__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_asyncToGenerator__ = __webpack_require__(19);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_asyncToGenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_asyncToGenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__components_gift_gift_box__ = __webpack_require__(152);




//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//



/* harmony default export */ __webpack_exports__["a"] = ({
  name: "packModal",
  props: [],
  components: {
    "gift-box": __WEBPACK_IMPORTED_MODULE_5__components_gift_gift_box__["a" /* default */]
  },
  data: function data() {
    return {
      selectIndex: 0,
      emptyList: [],
      goodList: [],
      bookList: [],
      showGiftBox: false,
      boxType: 4,
      isMaxLv: false
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_4_vuex__["c" /* mapState */])(["strengthCount", "goodStorageList", "maxStrength", "IMGHEAD", "HUAWEI"])),
  methods: __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_4_vuex__["b" /* mapMutations */])(["setStrengthCount", "setGoodStorageList", "setSingleGoodNum"]), {
    getEmptyList: function getEmptyList() {
      for (var i = 0; i < 16; i++) {
        this.emptyList.push(0);
      }
    },
    selectGood: function selectGood(index) {
      if (this.goodList[index] && this.goodList[index].num > 0) {
        this.selectIndex = index;
      }
    },
    getGoodList: function getGoodList() {
      var _this = this;

      return __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_asyncToGenerator___default()( /*#__PURE__*/__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.mark(function _callee() {
        var good, goodBook;
        return __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                good = [], goodBook = [];
                // if (this.goodStorageList.length > 0) {
                //   this.goodList = this.goodStorageList;
                // } else {

                _context.next = 3;
                return _this.fly.post("/equipment/getEquipmentList").then(function (res) {
                  return new __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_promise___default.a(function (resolve, reject) {
                    good = res.data.Result;
                    goodBook = res.data.Result2;
                    for (var i = 0; i < good.length; i++) {
                      if (good[i].num > 0) {
                        _this.goodList.push(good[i]);
                      }
                    }
                    for (var _i = 0; _i < goodBook.length; _i++) {
                      if (goodBook[_i].num > 0) {
                        goodBook[_i].book = true;
                        _this.goodList.push(goodBook[_i]);
                      }
                    }
                    _this.setGoodStorageList(_this.goodList);
                    resolve();
                  });
                });

              case 3:
              case "end":
                return _context.stop();
            }
          }
        }, _callee, _this);
      }))();
    },
    useGood: function useGood(good, index) {
      var _this2 = this;

      //可优化,使用节流函数，一定时间内的使用的次数 一次性请求，只做前端使用提示。
      if (!good || good.num <= 0) {
        return false;
      }
      var isEnerge = void 0,
          isBox = void 0,
          isBook = void 0;
      (good.type == 1 || good.type == 2 || good.type == 3) && !good.book ? isEnerge = true : isEnerge = false;
      (good.type == 4 || good.type == 5 || good.type == 6) && !good.book ? isBox = true : isBox = false;
      good.book ? isBook = true : isBook = false;

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
        this.fly.post("/equipment/useEquipment", {
          count: 1,
          type: good.type
        }).then(function (res) {
          var result = void 0;
          result = res.data;
          if (result.Tag == 1) {
            good.num--;
            if (good.num == 0) {
              _this2.goodList.splice(index, 1);
            }
            _this2.setSingleGoodNum({
              type: good.type,
              num: good.num
            });

            if (isEnerge) {
              var energe = {
                1: 1,
                2: 15,
                3: 30
              };
              var addNum = _this2.strengthCount + energe[good.type] > _this2.maxStrength ? _this2.maxStrength : _this2.strengthCount + energe[good.type];
              _this2.setStrengthCount(addNum);
              wx.showToast({
                title: "\u4F53\u529B+" + energe[good.type],
                icon: "none"
              });
            }

            if (isBox) {
              _this2.bookList = result.Result;
              _this2.showGiftBox = true;
              _this2.boxType = good.type;
              if (result.Type == 1) {
                _this2.isMaxLv = true;
              }
              _this2.goodList = [];
              _this2.setGoodStorageList([]);
              _this2.getGoodList();
            }
          }
          console.log("使用道具反馈", result);
        });
      }
    },
    close: function close() {
      this.$emit("close");
    }
  }),
  mounted: function mounted() {
    this.getEmptyList();
    this.getGoodList();
  }
});

/***/ }),

/***/ 152:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_gift_box_vue__ = __webpack_require__(154);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_059b0380_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_gift_box_vue__ = __webpack_require__(155);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(153)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-059b0380"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_gift_box_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_059b0380_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_gift_box_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\gift\\gift-box.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] gift-box.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-059b0380", Component.options)
  } else {
    hotAPI.reload("data-v-059b0380", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 153:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 154:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/* harmony default export */ __webpack_exports__["a"] = ({
  props: ["bookList", "type", "isMaxLv"],
  data: function data() {
    return {
      antBox: false,
      antBoxTop: false,
      antBoxBottom: false,
      showBg: false,
      showEl: false,
      showBook: false,
      type: 4, //宝箱类型
      boxMap: {
        4: {
          top: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/s-b-t.png",
          light: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/s-b-l.png",
          bottom: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/s-b-b.png"
        },
        5: {
          top: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/m-b-t.png",
          light: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/m-b-l.png",
          bottom: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/m-b-b.png"
        },
        6: {
          top: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/b-b-t.png",
          light: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/b-b-l.png",
          bottom: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/b-b-b.png"
        }
      },
      bookMap: {
        2: { img: "/static/icon/shang.png", name: "商科" },
        3: { img: "/static/icon/wen.png", name: "文科" },
        4: { img: "/static/icon/li.png", name: "理科" },
        5: { img: "/static/icon/yi.png", name: "艺科" },
        6: { img: "/static/icon/ti.png", name: "体科" },
        7: { img: "/static/icon/zong.png", name: "综科" }
      },
      bottleMap: {
        1: { img: "/static/icon/s-eng.png", name: "小体力瓶" },
        2: { img: "/static/icon/m-eng.png", name: "中体力瓶" },
        3: { img: "/static/icon/b-eng.png", name: "大体力瓶" }
      },
      bookListFormat: []
    };
  },

  methods: {
    close: function close() {
      if (this.showBook) {
        this.$emit("close");
      }
    },
    formatList: function formatList() {
      this.bookListFormat = [];
      for (var key in this.bookList) {
        this.bookListFormat.push({
          type: key,
          num: this.bookList[key]
        });
      }
    }
  },
  mounted: function mounted() {
    var _this = this;

    this.formatList();
    setTimeout(function () {
      //boxShake
      _this.antBox = true;
      setTimeout(function () {
        //boxTop
        _this.antBoxTop = true;
        _this.antBoxBottom = true;
        setTimeout(function () {
          //show Bg
          _this.showBg = true;
          setTimeout(function () {
            //show book
            _this.showEl = true;
            _this.showBook = true;
            if (_this.isMaxLv) {
              wx.showToast({ title: "知识书已满", icon: "none" });
            }
          }, 200);
        }, 750);
      }, 1000);
    }, 500);
  }
});

/***/ }),

/***/ 155:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "gift-box",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.close
    }
  }, [_c('img', {
    staticClass: "light-bg",
    class: {
      show: _vm.showBg
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/box-light.png"
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "box-head",
    class: {
      show: _vm.showEl
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/giftAnt/box-head.png"
    }
  }), _vm._v(" "), (_vm.bookListFormat.length > 0) ? _c('div', {
    staticClass: "book-wrap",
    class: {
      show: _vm.showEl, antBook: _vm.showBook
    }
  }, _vm._l((_vm.bookListFormat), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "book"
    }, [(!_vm.isMaxLv) ? _c('img', {
      attrs: {
        "src": _vm.bookMap[item.type].img
      }
    }) : _vm._e(), _vm._v(" "), (!_vm.isMaxLv) ? _c('div', {
      staticClass: "book-name"
    }, [_vm._v(_vm._s(_vm.bookMap[item.type].name) + "X" + _vm._s(item.num))]) : _vm._e(), _vm._v(" "), (_vm.isMaxLv) ? _c('img', {
      attrs: {
        "src": _vm.bottleMap[item.type].img
      }
    }) : _vm._e(), _vm._v(" "), (_vm.isMaxLv) ? _c('div', {
      staticClass: "book-name"
    }, [_vm._v(_vm._s(_vm.bottleMap[item.type].name) + "X" + _vm._s(item.num))]) : _vm._e()])
  })) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "box-wrap",
    class: {
      'box-shake': _vm.antBox
    }
  }, [_c('img', {
    staticClass: "b-b",
    class: {
      'boxBottomAnt': _vm.antBoxBottom
    },
    attrs: {
      "src": _vm.boxMap[_vm.type].bottom
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "b-l",
    class: {
      show: _vm.showBook
    },
    attrs: {
      "src": _vm.boxMap[_vm.type].light
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "b-t",
    class: {
      'boxTopAnt': _vm.antBoxTop
    },
    attrs: {
      "src": _vm.boxMap[_vm.type].top
    }
  })])])
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-059b0380", esExports)
  }
}

/***/ }),

/***/ 156:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-pack-modal"
  }, [_c('div', {
    staticClass: "u-pack-modal-inner"
  }, [_c('div', {
    staticClass: "close",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.close
    }
  }), _vm._v(" "), _vm._m(0), _vm._v(" "), _c('div', {
    staticClass: "u-pack-content"
  }, [_c('div', {
    staticClass: "u-goods"
  }, _vm._l((_vm.emptyList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "u-goods-item",
      class: {
        'select': _vm.selectIndex == index && _vm.goodList[_vm.selectIndex]
      },
      attrs: {
        "eventid": '1-' + index
      },
      on: {
        "click": function($event) {
          _vm.selectGood(index)
        }
      }
    }, [(_vm.goodList[index] && _vm.goodList[index].num > 0) ? _c('img', {
      class: {
        rotate30: (_vm.goodList[index].type == 1 || _vm.goodList[index].type == 2 || _vm.goodList[index].type == 3) && !_vm.goodList[index].book
      },
      attrs: {
        "src": _vm.IMGHEAD + _vm.goodList[index].image
      }
    }) : _vm._e(), _vm._v(" "), (_vm.goodList[index] && _vm.goodList[index].num > 0) ? _c('div', [_vm._v(_vm._s(_vm.goodList[index].num))]) : _vm._e()])
  })), _vm._v(" "), (_vm.goodList[_vm.selectIndex] && _vm.goodList[_vm.selectIndex].num > 0 && !_vm.goodList[_vm.selectIndex].book) ? _c('div', {
    staticClass: "u-goods-desc"
  }, [_vm._v("\n        " + _vm._s(_vm.goodList[_vm.selectIndex].description) + "\n      ")]) : _vm._e(), _vm._v(" "), (_vm.goodList[_vm.selectIndex] && _vm.goodList[_vm.selectIndex].num > 0 && _vm.goodList[_vm.selectIndex].book) ? _c('div', {
    staticClass: "u-goods-desc"
  }, [_vm._v("\n        " + _vm._s(_vm.goodList[_vm.selectIndex].remark) + "\n      ")]) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "u-btn-box",
    class: {
      'mt64': !_vm.goodList[_vm.selectIndex]
    }
  }, [_c('div', {
    staticClass: "u-btn u-btn-big u-btn-green",
    attrs: {
      "eventid": '2'
    },
    on: {
      "click": function($event) {
        _vm.useGood(_vm.goodList[_vm.selectIndex], _vm.selectIndex)
      }
    }
  }, [_vm._v("使用")])])])]), _vm._v(" "), (_vm.showGiftBox) ? _c('gift-box', {
    attrs: {
      "bookList": _vm.bookList,
      "type": _vm.boxType,
      "isMaxLv": _vm.isMaxLv,
      "eventid": '3',
      "mpcomid": '0'
    },
    on: {
      "close": function($event) {
        _vm.showGiftBox = false
      }
    }
  }) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "ad-box",
    class: {
      'huawei': _vm.HUAWEI
    }
  }, [_c('ad', {
    attrs: {
      "unit-id": "adunit-973fbb985356319e",
      "mpcomid": '1'
    }
  })], 1)], 1)
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-pack-modal-head"
  }, [_c('div', [_vm._v("背包")]), _vm._v(" "), _c('img', {
    attrs: {
      "src": "/static/icon/pack-bg.png"
    }
  })])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-3a098a85", esExports)
  }
}

/***/ }),

/***/ 157:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-menu"
  }, [_c('div', {
    staticClass: "mask-bg",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.close
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "menu-box",
    class: {
      show: _vm.showBox
    }
  }, [_c('div', {
    staticClass: "menu-box-head"
  }, [_c('div', {
    staticClass: "head-box",
    attrs: {
      "eventid": '1'
    },
    on: {
      "click": _vm.toUserInfo
    }
  }, [(_vm.appInfo.headImgUrl) ? _c('img', {
    attrs: {
      "src": _vm.appInfo.headImgUrl
    }
  }) : _c('img', {
    attrs: {
      "src": "/static/default-head.png"
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "menu-info"
  }, [_c('div', [_vm._v(_vm._s(_vm.appInfo.nickname))]), _vm._v(" "), _c('div', {
    staticClass: "txt"
  }, [_vm._v(_vm._s(_vm.appInfo.province || "宇宙") + " " + _vm._s(_vm.appInfo.city || "火星"))])])]), _vm._v(" "), _c('div', {
    staticClass: "menu-box-list"
  }, [_c('div', {
    class: {
      hasTip: false
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/invate.png",
      "eventid": '2'
    },
    on: {
      "click": function($event) {
        _vm.showInvateBoxFn(true)
      }
    }
  })]), _vm._v(" "), _c('div', {
    class: {
      hasTip: _vm.status.isFinishDayTask == 1
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/task.png",
      "eventid": '3'
    },
    on: {
      "click": function($event) {
        _vm.showGiftBox(0)
      }
    }
  })]), _vm._v(" "), _c('div', {
    class: {
      hasTip: _vm.status.isDaySign == 1 || _vm.status.isHitCat == 1 || _vm.status.isLuckNumber == 1
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/dayGift.png",
      "eventid": '4'
    },
    on: {
      "click": function($event) {
        _vm.showGiftBox(1)
      }
    }
  })]), _vm._v(" "), _c('div', {
    class: {
      hasTip: _vm.status.isBookUpLevel > 0
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/book.png",
      "eventid": '5'
    },
    on: {
      "click": _vm.toBook
    }
  })]), _vm._v(" "), _c('div', {
    class: {
      hasTip: _vm.appInfo.money > 50 && _vm.hasTipLottery
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/lottery.png",
      "eventid": '6'
    },
    on: {
      "click": _vm.toLottery
    }
  })]), _vm._v(" "), _c('div', {
    class: {
      hasTip: false
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/pack.png",
      "eventid": '7'
    },
    on: {
      "click": function($event) {
        _vm.showPack = true
      }
    }
  })]), _vm._v(" "), _c('div', {
    class: {
      hasTip: false
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/god.png",
      "eventid": '8'
    },
    on: {
      "click": _vm.showGodRank
    }
  })]), _vm._v(" "), _c('div', [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/project.png",
      "eventid": '9'
    },
    on: {
      "click": _vm.toProject
    }
  })]), _vm._v(" "), _c('div', {
    class: {
      hasTip: _vm.status.emailNoReadNum > 0
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/email.png",
      "eventid": '10'
    },
    on: {
      "click": function($event) {
        _vm.showEmail = true
      }
    }
  })]), _vm._v(" "), _c('div', {
    class: {
      hasTip: false
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/setting.png",
      "eventid": '11'
    },
    on: {
      "click": _vm.showSet
    }
  })])])]), _vm._v(" "), (_vm.showEmail) ? _c('email-modal', {
    attrs: {
      "eventid": '12',
      "mpcomid": '0'
    },
    on: {
      "close": function($event) {
        _vm.showEmail = false
      }
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showSetting) ? _c('setting-modal', {
    attrs: {
      "eventid": '13',
      "mpcomid": '1'
    },
    on: {
      "radioChangeOne": _vm.handleRadioOneChange,
      "radioChangeTwo": _vm.handleRadioTwoChange,
      "close": function($event) {
        _vm.showSetting = false
      }
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showPack) ? _c('pack-modal', {
    attrs: {
      "eventid": '14',
      "mpcomid": '2'
    },
    on: {
      "close": function($event) {
        _vm.showPack = false
      }
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showInvateBox) ? _c('invate-box', {
    attrs: {
      "eventid": '15',
      "mpcomid": '3'
    },
    on: {
      "close": function($event) {
        _vm.showInvateBoxFn(false)
      }
    }
  }) : _vm._e()], 1)
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-0058e818", esExports)
  }
}

/***/ }),

/***/ 161:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "out-box"
  }, [_c('img', {
    staticClass: "entry-bg",
    attrs: {
      "lazy-load": "true",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-test.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "wrap"
  }, [_c('div', {
    staticClass: "head-menu",
    class: {
      'hasTip': _vm.hasTipMenu, show: !_vm.showMenu
    },
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": function($event) {
        _vm.showMenuFn('menu')
      }
    }
  }, [_c('div', {
    staticClass: "et-img bg-menu"
  })]), _vm._v(" "), _c('div', {
    staticClass: "link-menu",
    class: {
      'hasTipAd': _vm.hasTipAd, show: !_vm.showAdBox
    },
    attrs: {
      "lazy-load": "true",
      "eventid": '1'
    },
    on: {
      "click": function($event) {
        _vm.showMenuFn('ad')
      }
    }
  }, [_c('div', {
    staticClass: "et-img bg-ad_btn"
  })]), _vm._v(" "), _c('div', {
    staticClass: "head"
  }, [_c('div', {
    staticClass: "gold-box"
  }, [_c('img', {
    attrs: {
      "src": "/static/gold.png"
    }
  }), _vm._v(" " + _vm._s(_vm.appInfo.money) + "\n      ")]), _vm._v(" "), _c('scroll-view', {
    staticClass: "season-tag",
    attrs: {
      "scroll-y": true,
      "scroll-with-animation": true,
      "scroll-into-view": 'id' + _vm.messageIndex
    }
  }, [_c('cover-view', {
    staticClass: "season-mask",
    attrs: {
      "eventid": '2',
      "mpcomid": '0'
    },
    on: {
      "click": function($event) {
        $event.stopPropagation();
        _vm.toTalkRoom($event)
      }
    }
  }), _vm._v(" "), _vm._l((_vm.messageList), function(item, index) {
    return (_vm.messageList && _vm.messageList.length > 0) ? _c('div', {
      key: index,
      staticClass: "tag-inner",
      attrs: {
        "id": 'id' + index
      }
    }, [(item.type == 0) ? _c('img', {
      attrs: {
        "lazy-load": "true",
        "src": item.headImgUrl
      }
    }) : _vm._e(), _vm._v(" "), (item.type == 0) ? _c('div', [_vm._v(":")]) : _vm._e(), _vm._v(" "), (item.type == 0) ? _c('div', {
      staticClass: "tag-txt"
    }, [_vm._v("\n            " + _vm._s(item.message) + "\n          ")]) : _vm._e(), _vm._v(" "), (item.type == 1) ? _c('div', {
      staticClass: "top"
    }, [_vm._v("\n            恭喜\n            "), _c('img', {
      attrs: {
        "lazy-load": "true",
        "src": item.headImgUrl
      }
    }), _vm._v(" "), _c('div', [_vm._v(_vm._s(item.nickname))]), _vm._v("\n            登顶\n            "), _c('img', {
      attrs: {
        "lazy-load": "true",
        "src": _vm.IMGHEAD + item.highParagraphImage
      }
    }), _vm._v(" "), _c('div', [_vm._v("最强王者")])]) : _vm._e()]) : _vm._e()
  })], 2)], 1), _vm._v(" "), _c('img', {
    staticClass: "star-bg",
    attrs: {
      "lazy-load": "true",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/star-bg.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "icon-box"
  }, [_c('img', {
    attrs: {
      "lazy-load": "true",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/dayGift.png",
      "eventid": '3'
    },
    on: {
      "click": function($event) {
        _vm.showDayGfit = true;
      }
    }
  }), _vm._v(" "), _c('img', {
    attrs: {
      "lazy-load": "true",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/invate.png",
      "eventid": '4'
    },
    on: {
      "click": function($event) {
        _vm.showInvateBox = true;
      }
    }
  }), _vm._v(" "), _c('img', {
    attrs: {
      "lazy-load": "true",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/lottery.png",
      "eventid": '5'
    },
    on: {
      "click": _vm.toLottery
    }
  }), _vm._v(" "), _c('img', {
    attrs: {
      "lazy-load": "true",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/menu/rank.png",
      "eventid": '6'
    },
    on: {
      "click": _vm.toRankList
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "content"
  }, [_c('form', {
    attrs: {
      "report-submit": "true",
      "eventid": '8'
    },
    on: {
      "submit": _vm.submitFormId
    }
  }, [_c('button', {
    staticClass: "rank",
    attrs: {
      "form-type": "submit",
      "open-type": "getUserInfo",
      "lang": "zh_CN",
      "hover-class": "click-active",
      "plain": "true",
      "eventid": '7'
    },
    on: {
      "getuserinfo": function($event) {
        _vm.updateUserInfo($event)
      },
      "click": _vm.rankStart
    }
  }, [_c('div', [_c('div', {
    staticClass: "et-img bg-rank"
  })])])], 1), _vm._v(" "), _c('div', {
    staticClass: "btn-box"
  }, [_c('div', {
    staticClass: "btn-place"
  }), _vm._v(" "), _c('form', {
    attrs: {
      "report-submit": "true",
      "eventid": '10'
    },
    on: {
      "submit": _vm.submitFormId
    }
  }, [_c('button', {
    staticClass: "btn-group",
    attrs: {
      "form-type": "submit",
      "hover-class": "click-active",
      "plain": "true",
      "eventid": '9'
    },
    on: {
      "click": _vm.toRoom
    }
  }, [_c('div', {
    staticClass: "et-img bg-group"
  })])], 1), _vm._v(" "), _c('div', {
    staticClass: "btn-rank",
    attrs: {
      "hover-class": "click-active",
      "eventid": '11'
    },
    on: {
      "click": _vm.toNewYear
    }
  }, [_c('div', {
    staticClass: "card"
  }, [_vm._v("第" + _vm._s(_vm.newFoodCard) + "关")])])], 1)], 1), _vm._v(" "), (_vm.isAuthorize == '0') ? _c('div', {
    staticClass: "login-page sky-bg"
  }, [_c('img', {
    attrs: {
      "lazy-load": "true",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/logo.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('button', {
    staticClass: "login-btn",
    attrs: {
      "open-type": "getUserInfo",
      "lang": "zh_CN",
      "eventid": '12'
    },
    on: {
      "getuserinfo": function($event) {
        _vm.getUserInfo($event)
      }
    }
  }, [_c('img', {
    attrs: {
      "lazy-load": "true",
      "src": "/static/login-btn3.png",
      "alt": ""
    }
  })]), _vm._v(" "), (true) ? _c('img', {
    staticClass: "moon",
    attrs: {
      "lazy-load": "true",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/moon.png"
    }
  }) : _vm._e()], 1) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "wx-ad",
    class: {
      'huawei': _vm.HUAWEI
    }
  }, [_c('ad', {
    attrs: {
      "unit-id": "adunit-1f1cc70776e2e407",
      "mpcomid": '1'
    }
  })], 1), _vm._v(" "), (_vm.showEnerge) ? _c('energe-modal', {
    attrs: {
      "eventid": '13',
      "mpcomid": '2'
    },
    on: {
      "close": function($event) {
        _vm.showEnerge = false
      }
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showSeason) ? _c('season-modal', {
    attrs: {
      "seasonData": _vm.seasonData,
      "eventid": '14',
      "mpcomid": '3'
    },
    on: {
      "close": function($event) {
        _vm.showSeason = false
      }
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showMenu) ? _c('menu-box', {
    attrs: {
      "status": _vm.status,
      "eventid": '15',
      "mpcomid": '4'
    },
    on: {
      "invateBox": _vm.changeInvateBoxType,
      "showModal": _vm.handleShowModal,
      "close": function($event) {
        _vm.showMenu = false
      }
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showAdBox) ? _c('ad-box-new', {
    attrs: {
      "tryGameName": _vm.tryGameName,
      "eventid": '16',
      "mpcomid": '5'
    },
    on: {
      "close": _vm.closeAdBox
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showDayGfit) ? _c('day-gift', {
    attrs: {
      "type": _vm.giftType,
      "showCatNow": _vm.showCatNow,
      "eventid": '17',
      "mpcomid": '6'
    },
    on: {
      "close": function($event) {
        _vm.showDayGfit = false
      }
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showInvateBox) ? _c('invate-box', {
    attrs: {
      "eventid": '18',
      "mpcomid": '7'
    },
    on: {
      "close": function($event) {
        _vm.showInvateBox = false
      }
    }
  }) : _vm._e()], 1)])
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-762317e4", esExports)
  }
}

/***/ }),

/***/ 71:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_invateBox_vue__ = __webpack_require__(140);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_191c79be_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_invateBox_vue__ = __webpack_require__(141);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(139)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-191c79be"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_invateBox_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_191c79be_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_invateBox_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\public\\invateBox.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] invateBox.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-191c79be", Component.options)
  } else {
    hotAPI.reload("data-v-191c79be", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ })

},[331]);
//# sourceMappingURL=main.js.map