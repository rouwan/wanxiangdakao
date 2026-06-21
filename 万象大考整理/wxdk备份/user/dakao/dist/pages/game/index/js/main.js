global.webpackJsonp([1],{

/***/ 216:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(218);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_9c0cc528_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(262);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(217)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-9c0cc528"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_9c0cc528_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\game\\index.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-9c0cc528", Component.options)
  } else {
    hotAPI.reload("data-v-9c0cc528", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 217:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 218:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator__ = __webpack_require__(18);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator__ = __webpack_require__(19);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_promise__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_promise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_promise__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__utils_index__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__config_dataFile__ = __webpack_require__(13);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__components_waitBox__ = __webpack_require__(219);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__components_match__ = __webpack_require__(223);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__components_questionBox__ = __webpack_require__(227);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__components_public_medalChange__ = __webpack_require__(238);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__components_utils_imgPreLoad__ = __webpack_require__(246);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__components_public_animateCss_success__ = __webpack_require__(250);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__components_public_animateCss_fail__ = __webpack_require__(254);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__components_public_animateCss_balance__ = __webpack_require__(258);





//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//












var dataStack = [];
/* harmony default export */ __webpack_exports__["a"] = ({
  components: {
    waitBox: __WEBPACK_IMPORTED_MODULE_8__components_waitBox__["a" /* default */],
    match: __WEBPACK_IMPORTED_MODULE_9__components_match__["a" /* default */],
    questionBox: __WEBPACK_IMPORTED_MODULE_10__components_questionBox__["a" /* default */],
    medalChange: __WEBPACK_IMPORTED_MODULE_11__components_public_medalChange__["a" /* default */],
    imgPreLoad: __WEBPACK_IMPORTED_MODULE_12__components_utils_imgPreLoad__["a" /* default */],
    success: __WEBPACK_IMPORTED_MODULE_13__components_public_animateCss_success__["a" /* default */],
    fail: __WEBPACK_IMPORTED_MODULE_14__components_public_animateCss_fail__["a" /* default */],
    balance: __WEBPACK_IMPORTED_MODULE_15__components_public_animateCss_balance__["a" /* default */]
  },
  onShareAppMessage: function onShareAppMessage(res) {
    var index = Object(__WEBPACK_IMPORTED_MODULE_5__utils_index__["c" /* getRondom */])(0, 2);
    return {
      title: __WEBPACK_IMPORTED_MODULE_6__config_dataFile__["c" /* shareInfo */][index].title,
      imageUrl: __WEBPACK_IMPORTED_MODULE_6__config_dataFile__["c" /* shareInfo */][index].imgUrl,
      path: "/pages/entry/index?userId=" + this.appInfo.userId
    };
  },
  data: function data() {
    return {
      pageType: 0,
      pageLife: "",
      socketTask: null,
      showWaitBox: false, //等待匹配
      showQuestion: false, //答题页
      matchTimer: null, //定时器
      matchTime: 10, //倒计时
      gameId: 0, //对局id
      isMatched: false, // 是否匹配到
      gameStart: false, //游戏是否开始
      relinkIndex: 0,
      subjectList: [], //题目数据列表
      userList: [], //用户数据列表
      type: 0, //会话类型 区分后端传入的数据用于哪种业务处理
      otherAns: [], //其他用户的答案
      isGameOver: false,
      oRun: false,
      gameGift: { energe: 0 },
      //升星动画
      medalInfo: {},
      newResult: {},
      isGameEndup: false,
      showStarBox: false,
      endType: "success",
      cwWord: "连胜",
      clearList: false, //是否在做清空操作，
      rank: false,
      oldGame: false, //断线重连
      netWork: false, //重连开关,
      // 圖片預加載
      getImg: false,
      showAntSuccess: false,
      showAntFail: false,
      showAntBalance: false,
      showMoon: true,
      // x心跳包监听网络状态
      heart: null,
      heartTimer: null, //心跳计时器，维护后台连接
      heartDelayTime: 0,
      heartTimes: 0,
      //心跳 设计 （new）
      limit: 0,
      heartCheck: {
        timeOut: 10000,
        bigTimer: null,
        smallTimer: null,
        relinkDelay: 5000,
        start: function start(_this) {
          var _this2 = this;

          _this.limit = 0;
          this.bigTimer = setTimeout(function () {
            if (_this.pageType == 1) {
              _this2.stop();
              return false;
            }
            try {
              _this.socketTask.send({ data: "{'type':0,'data':1}" });
            } catch (e) {
              console.log(e);
              _this2.stop();
            }
            _this2.smallTimer = setTimeout(function () {
              if (_this.pageType == 1) {
                return false;
              }
              wx.showLoading({ title: "断网重连中", mask: true });
              _this.socketTask.close();
            }, _this2.timeOut);
          }, this.timeOut);
        },
        reset: function reset(_this) {
          clearTimeout(this.bigTimer);
          clearTimeout(this.smallTimer);
          this.start(_this);
        },
        stop: function stop() {
          clearTimeout(this.bigTimer);
          clearTimeout(this.smallTimer);
        }
      },
      isHideLife: false,
      emojiIndexMe: -1,
      emojiIndexHe: -1
    };
  },


  computed: __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_7_vuex__["c" /* mapState */])(["SESSIONID", "appInfo", "socketState", "gameRecord", "isRank", "IMGHEAD", "cw", "openId", "EVN", "deviceInfo", "globalSoundPlayer", "rankIndex", "gamePageType"])),
  methods: __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_7_vuex__["b" /* mapMutations */])(["setSocketState", "setUserOneState", "setCw", "setGameRecord", "setIsRank", "setAppInfo", "setAppInfoByItem", "setRankIndex", "setStrengthCount", "setGameResult"]), {
    netWorkChange: function netWorkChange() {
      wx.onNetworkStatusChange(function (res) {
        if (!res.isConnected) {
          wx.showModal({
            title: "系统提示",
            content: "网络中断",
            showCancel: false,
            success: function success(res) {
              if (res.confirm) {
                // wx.showLoading({title:"尝试连接中",mask:true});
                wx.reLaunch({
                  url: "/pages/entry/index"
                });
              }
            }
          });
        }
      });
    },
    startHeart: function startHeart() {
      var _this3 = this;

      this.heart = setInterval(function () {
        if (_this3.socketTask.readyState == 1) {
          _this3.socketTask.send({ data: "{'type':0,'data':1}" });
        } else {
          _this3.stopHeart();
          wx.showModal({
            title: "系统提示",
            content: "网络问题,请重新登录",
            showCancel: false,
            success: function success(res) {
              if (res.confirm) {
                wx.reLaunch({ url: "/pages/entry/index" });
              }
            }
          });
          return false;
        }
        // this.stopHeartTimer();
        _this3.heartTimer = setInterval(function () {
          _this3.heartDelayTime++;
          if (_this3.heartDelayTime >= 10 && _this3.heartTimer != null) {
            _this3.stopHeart();
            _this3.closeSocket();
            wx.showModal({
              title: "系统提示",
              content: "网络问题,请重新登录",
              showCancel: false,
              success: function success(res) {
                if (res.confirm) {
                  wx.reLaunch({ url: "/pages/entry/index" });
                }
              }
            });
          }
        }, 1000);
      }, 12000);
    },
    stopHeart: function stopHeart() {
      if (this.heart) {
        clearInterval(this.heart);
        this.heart = null;
      }
      if (this.heartTimer) {
        this.stopHeartTimer();
      }
    },
    stopHeartTimer: function stopHeartTimer() {
      clearInterval(this.heartTimer);
      this.heartTimer = null;
      this.heartDelayTime = 0;
    },

    //socket 连接
    initSocket: function initSocket(_type) {
      var _this4 = this;

      //  初始化socket 建立连接
      if (this.socketTask) {
        return false;
      }
      wx.showLoading({ title: "加载中" });
      var type = 2;
      if (this.isRank) {
        type = 1;
      }
      this.fly.post("/wsLogin", {
        type: type
      }).then(function (res) {
        var url = "";
        if (_this4.ENV == "Dev") {
          url = "ws://127.0.0.1:8008/vientianetest/ws?code=" + res.data.Result.code + ("&&type=" + _type);
        } else if (_this4.EVN == "Production") {
          url = "wss://ggbsq.xingpanwang.com/vientianetest/ws?code=" + res.data.Result.code + ("&&type=" + _type);
        }
        if (_this4.isRank) {
          if (_this4.EVN == "Dev") {
            url = "ws://127.0.0.1:8008/vientianetest/wsRank?code=" + res.data.Result.code + ("&&type=" + _type);
          } else if (_this4.EVN == "Production") {
            url = "wss://ggbsq.xingpanwang.com/vientianetest/wsRank?code=" + res.data.Result.code + ("&&type=" + _type);
          }
        }

        _this4.socketTask = wx.connectSocket({
          url: url,
          data: {
            sid: _this4.SESSIONID
          },
          header: {
            "content-type": "application/json"
          },
          method: "GET"
        });
        _this4.onSocketOpen();
        _this4.onSocketError();
      });
    },
    onSocketOpen: function onSocketOpen() {
      var _this5 = this;

      //监听socket 连接 成功
      this.socketTask.onOpen(function (res) {
        console.log("WebSocket连接打开", _this5.socketTask.readyState);
        wx.hideLoading();
        // this.startHeart(); //开始心跳  old
        // this.netWorkChange();//断网处理
        if (_this5.pageType == 1) {
          _this5.closeSocket();
        }
        // 重置 重连限制
        clearInterval(_this5.relinkTimer);
        // 新 心跳尝试
        _this5.heartCheck.reset(_this5);
        _this5.setSocketState(_this5.socketTask.readyState);
        if (_this5.$root.$mp.query.isNew) {
          _this5.showWaitBox = true;
          _this5.sendSocketMessage("{'type':1,'data':1} ");
          delete _this5.$root.$mp.query.isNew;
        } else {
          _this5.checkOldGame(); //检测 是否 有未完成游戏
        }

        _this5.onSocketMessage(); //监听后端数据事件
        _this5.onSocketClose();
      });
    },
    reLink: function reLink() {
      var _this6 = this;

      console.log("sockt自动重连", this.limit + 1);
      clearInterval(this.relinkTimer);
      if (this.pageType == 1) {
        return false;
      }
      this.relinkTimer = setInterval(function () {
        if (_this6.limit < 5) {
          if (_this6.gameStart) {
            _this6.initSocket(2);
          } else {
            _this6.initSocket(1);
          }
          _this6.limit++;
        } else {
          clearInterval(_this6.relinkTimer);
          wx.showModal({
            title: "系统提示",
            content: "重连失败,请更换网络",
            showCancel: false,
            success: function success(res) {
              if (res.confirm) {
                wx.reLaunch({ url: "/pages/entry/index" });
              }
            }
          });
        }
      }, 5000);
    },
    onSocketClose: function onSocketClose() {
      var _this7 = this;

      this.socketTask.onClose(function (res) {
        console.log("监听socket关闭", _this7.socketTask.readyState);
        _this7.setSocketState(_this7.socketTask.readyState);
        _this7.socketTask = null;
        _this7.otherAns = [];
        if (!_this7.gameStart && !_this7.showWaitBox) {
          //游戏结束 主动断开 补充连
          return false;
        }
        if (!_this7.isHideLife) {
          _this7.reLink();
        }

        // this.stopHeart();   old
        console.log("onClose", _this7.pageType);
        // if (this.pageType == 0 && this.gameStart) {
        //   wx.showModal({
        //     title: "系统提示",
        //     content: "网络问题,请重新登录",
        //     showCancel: false,
        //     success: res => {
        //       if (res.confirm) {
        //         wx.reLaunch({ url: "/pages/entry/index" });
        //       }
        //     }
        //   });
        // }
      });
    },
    onSocketError: function onSocketError() {
      var _this8 = this;

      //监听socket 连接 失败
      this.socketTask.onError(function (res) {
        console.log("socket error socket state", _this8.socketTask.readyState);
        _this8.reLink();
      });
    },
    sendSocketMessage: function sendSocketMessage(msg, from) {
      var _this9 = this;

      return new __WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_promise___default.a(function (resolve, reject) {
        //发送消息
        if (from) {
          console.log(from);
        }
        if (_this9.socketTask.readyState === 1) {
          _this9.socketTask.send({
            data: msg,
            success: function success() {
              console.log(msg, new Date());
              resolve();
            },
            fail: function fail(res) {
              console.log("发送失败 :连接状态" + _this9.socketTask.readyState);
              reject();
            }
          });
        }
      });
    },
    onSocketMessage: function onSocketMessage() {
      var _this10 = this;

      //被动接受消息
      this.socketTask.onMessage(function (res) {
        //区分数据类型 处理不同业务
        var data = JSON.parse(res.data);
        switch (data.Type) {
          case 1:
            //开始匹配
            _this10.stopMatch(data, "new");
            console.log("接收type1", data, new Date());
            break;
          case 2:
            //获取重连数据
            _this10.checkOldGameHandle(data);
            console.log("接收type2", data, new Date());
            break;
          case 3:
            //匹配成功
            console.log("接受type3", data, new Date());
            break;

          case 4:
            // 区分是 用户的反馈 或者 对手的答题信息
            console.log("接收type4:", data, new Date());
            _this10.handleAnsewerResponse(data.Result);
            break;

          case 6:
            // 下一题
            console.log("接收type6-下一题", data, new Date());
            _this10.HandleNextQuestion(data);
            break;

          case 5:
            _this10.gameOver(data);
            console.log("接收type5,处理结束游戏断开连接");
            break;
          case 7:
            console.log("type7,游戏结束，带有最终信息，包括后续可能添加的经验等", data, new Date());
            _this10.gameEnd(data);
            break;
          case 0:
            // console.log("重置心跳", new Date());
            console.log("接收type0,重置时间", new Date());
            // this.stopHeartTimer();
            _this10.heartCheck.reset(_this10);
            break;
          case 15:
            _this10.sendEmoji(data);
        }
      });
    },
    closeSocket: function closeSocket() {
      var _this11 = this;

      return new __WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_promise___default.a(function (resolve, reject) {
        if (_this11.socketTask.readyState === 1) {
          _this11.socketTask.close({
            success: function success(res) {
              resolve();
            },
            fail: function fail() {
              reject();
            }
          });
        }
      });
    },


    //-------------- 匹配相关函数 ----------------------------------
    checkOldGame: function checkOldGame() {
      this.sendSocketMessage("{'type':2,'data':1}");
    },
    checkOldGameHandle: function checkOldGameHandle(data) {
      var _this12 = this;

      if (data.Result != "") {
        //游戏重连 直接开始游戏
        this.stopMatch(data, "old");
        this.gameStart = true;
        this.oldGame = true;
        console.log("旧游戏");
      } else {
        this.oldGame = false;
        if (this.showQuestion) {
          //游戏界面中，但是游戏结束了
          wx.showToast({
            title: "游戏已结束",
            duration: 1000,
            icon: "none"
          });
          this.gameStart = false;
          setTimeout(function () {
            _this12.$router.go(1);
          }, 1000);
        } else if (this.showQuestion) {
          console.log("显示对局结果,关闭无用socket");
          this.closeSocket();
        } else {
          //开始新游戏
          this.showWaitBox = true;
          this.gameStart = true;
          this.sendSocketMessage("{'type':1,'data':1} ");
        }
      }
    },
    stopMatch: function stopMatch(data, type) {
      var _this13 = this;

      if (type == "new") {
        //停止匹配
        this.gameStart = true;
        this.isMatched = true;
        setTimeout(function () {
          _this13.showWaitBox = false;
          if (_this13.pageLife != "onUnload") {
            _this13.matchClose();
          }
        }, 1600);
      } else {
        this.gameStart = true;
        this.showWaitBox = false;
        setTimeout(function () {
          if (_this13.showQuestion) {
            _this13.netWork = !_this13.netWork;
          } else {
            _this13.showQuestion = true;
          }
        }, 0);
      }

      // 初始数据赋值
      this.gameId = data.Result.gameId;
      this.userList = data.Result.userList;
      this.subjectList = data.Result.subjectList;
      this.relinkIndex = data.Result.answerIndex;

      // 调试
      for (var i = 0; i < this.subjectList.length; i++) {
        console.log(this.subjectList[i].answer);
      }
      // 排位处理
      if (this.isRank) {
        for (var _i = 0; _i < this.userList.length; _i++) {
          if (this.userList[_i].basicInfo.userId == this.appInfo.userId) {
            // 新结果处理
            this.medalInfo = this.userList[_i].abilityInfo;

            this.setCw(this.userList[_i].abilityInfo.continueWinNum);

            this.medalImg = this.IMGHEAD + this.userList[_i].abilityInfo.paragraphImage;
          }
        }
      }

      // beforeData;
    },
    matchCancle: function matchCancle() {
      //取消匹配
      this.showWaitBox = false;
      this.$router.back();
    },

    // ----------------配对成功 展示页 -------------------------
    matchClose: function matchClose() {
      this.showQuestion = true;
    },


    // ----------------答题处理 -------------------------
    submitAns: function submitAns(obj) {
      //提交答案到后端
      obj.data.gameId = this.gameId;
      obj = __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default()(obj);
      this.sendSocketMessage(obj, "send-submitAns");
    },
    rightHandle: function rightHandle(obj) {
      this.submitAns(obj);
    },
    failHandle: function failHandle(obj) {
      this.submitAns(obj);
    },
    nextQuestion: function nextQuestion() {
      this.otherAns = [];
      this.sendSocketMessage("{'type':6,'data':" + this.gameId + ",'index':" + this.rankIndex + "}");
    },
    answerEnd: function answerEnd() {
      this.otherAns = [];
      this.showMoon = false;
    },

    // ---------------- 反馈处理函数 -------------------------
    handleSendEmoji: function handleSendEmoji(data) {
      this.sendSocketMessage("{'type':15,'data':" + data + "}");
    },
    HandleNextQuestion: function HandleNextQuestion(data) {
      // console.log('type6',data)
      wx.hideLoading();
      this.setRankIndex(data.Result.index);
    },
    handleAnsewerResponse: function handleAnsewerResponse(data) {
      //接受答题反馈
      this.otherAns.push(data);
    },
    gameOver: function gameOver(data) {
      //type5 逃跑处理
      this.isGameOver = !this.isGameOver;
      this.otherAns = [];
      this.oRun = true;
      if (this.isRank) {
        this.gameEnd(data);
      } else {
        this.isGameEnd = !this.isGameEnd;
      }
      wx.showToast({
        title: "对方已逃跑,等待游戏结算",
        icon: "none",
        duration: 1000
      });
      setTimeout(function () {
        wx.showLoading({
          title: "结算中...",
          mask: true
        });
      }, 1000);
    },
    sendEmoji: function sendEmoji(data) {
      var _this14 = this;

      data = JSON.parse(data.Result);
      this.emojiIndex = data.index;
      if (data.id == this.appInfo.userId) {
        this.emojiIndexMe = data.index;
        setTimeout(function () {
          _this14.emojiIndexMe = -1;
        }, 1600);
      } else {
        this.emojiIndexHe = data.index;
        setTimeout(function () {
          _this14.emojiIndexHe = -1;
        }, 1600);
      }
    },
    resetTobBg: function resetTobBg() {
      wx.setNavigationBarColor({
        frontColor: "#ffffff",
        backgroundColor: "#000"
      });
    },
    gameEnd: function gameEnd(data) {
      var _this15 = this;

      //游戏正常完成
      this.setGameRecord(data);
      this.setRankIndex(0); //最后结算
      // this.stopHeart();
      // this.stopHeartTimer();
      this.heartCheck.stop(this);
      setTimeout(function () {
        _this15.resetTobBg();
        _this15.showQuestion = false;
      }, 2000);
      this.otherAns = [];
      this.gameStart = false;
      if (this.isRank) {
        //排位
        var u1 = void 0,
            u2 = void 0;
        // 处理升星动画 结果数据
        // 获取结算状态
        this.newResult = {};
        for (var i = 0; i < data.Result.userList.length; i++) {
          if (data.Result.userList[i].basicInfo.userId == this.appInfo.userId) {
            this.newResult = data.Result.userList[i].abilityInfo;
            u1 = data.Result.userList[i].abilityInfo.score;
            this.gameGift = {
              energe: this.newResult.rewardStrength,
              combatList: data.Result.userList[i].combatInfo || [],
              rightNum: this.newResult.rightNum,
              combat: this.newResult.combat
            };
          } else {
            u2 = data.Result.userList[i].abilityInfo.score;
          }
        }
        this.isGameEnd = !this.isGameEnd;
        if (u1 > u2 || this.oRun) {
          this.setGameResult('success');
        } else if (u1 < u2) {
          this.setGameResult('fail');
        } else if (u1 == u2) {
          this.setGameResult('balance');
        }
        setTimeout(function () {
          wx.hideLoading();
          if (u1 > u2 || _this15.oRun) {
            _this15.endType = "success";
            _this15.showAntSuccess = true;
            _this15.globalSoundPlayer.src = "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/success.mp3";
            // setTimeout(() => {
            //   this.showAntSuccess = false;
            // }, 3500);
          } else if (u1 < u2) {
            _this15.endType = "fail";
            _this15.showAntFail = true;
            _this15.globalSoundPlayer.src = "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/fail.mp3";
          } else {
            _this15.endType = "balance";
            _this15.showAntBalance = true;
            _this15.globalSoundPlayer.src = "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/balance.mp3";
          }
          _this15.globalSoundPlayer.play();
          if (_this15.oRun) {
            _this15.endType = "success";
            _this15.showAntSuccess = true;
          }
        }, 2000);
      }
      // 结束后重新获取基础信息
      this.closeSocket();
      this.stopHeart();
      console.log("游戏正常结束");
    },
    back: function back() {
      this.otherAns = [];
    },
    getMedal: function getMedal() {
      this.showAntSuccess = false;
      this.showAntFail = false;
      this.showAntBalance = false;
      this.closeStarBox('结算');
      // this.showStarBox = true;

      // setTimeout(() => {
      //   // 新结果处理
      //   this.medalInfo = this.newResult;
      // }, 1000);
    },
    closeStarBox: function closeStarBox(type) {
      this.showStarBox = false;
      if (type == "结算") {
        this.$router.replace("/pages/game/result");
      } else if (type == "逃跑") {
        this.$router.back();
      }
    }
  }),

  // ---------------- 实例生命周期 -------------------------

  mounted: function mounted() {
    var _this16 = this;

    // this.onNetworkStatusChange();
    setTimeout(function () {
      _this16.getImg = true;
    }, 35000);
    this.pageLife = "mounted";
  },
  onHide: function onHide() {
    //断开连接
    this.isHideLife = true;
    this.heartCheck.stop();
    this.pageType = 1; //离开当前页
    console.log(this.pageType);
    this.closeSocket();
    this.pageLife = "onHide";
  },
  onShow: function onShow() {
    this.pageLife = "onShow";
    this.pageType = 0;
    //断网重连处理
    if (this.$root.$mp.query.rankGame) {
      this.setIsRank(true);
    }
    if (this.$root.$mp.query.isNew && !this.isHideLife && !this.gameStart) {
      this.initSocket(1);
    } else if (!this.$root.$mp.query.isNew && this.isHideLife && this.gameStart) {
      this.initSocket(2);
    } else if (!this.$root.$mp.query.isNew && this.showWaitBox && !this.gameStart) {
      this.initSocket(1);
    }
    this.isHideLife = false;
  },
  onLoad: function onLoad(options) {
    dataStack.push(__WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default()({}, this.$data));
  },
  onUnload: function onUnload() {
    var _this17 = this;

    return __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default()( /*#__PURE__*/__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.mark(function _callee() {
      return __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.wrap(function _callee$(_context) {
        while (1) {
          switch (_context.prev = _context.next) {
            case 0:
              console.log("game onUnload");
              _this17.heartCheck.stop();
              _this17.showStarBox = false;
              _this17.pageLife = "onUnload";
              _this17.pageType = 1; //离开当前页面
              if (_this17.gameStart) {
                _this17.setGameResult("fail");
                wx.showModal({
                  title: "你放弃了对局",
                  content: "逃跑将使你的星级下降",
                  showCancel: false
                });
              }
              _this17.heartTimes = 0;
              _this17.showQuestion = false;
              _this17.gameStart = false;
              _this17.showWaitBox = false;
              _this17.questionBox = false;
              _this17.showAntSuccess = false;
              _this17.showAntFail = false;
              _this17.showAntBalance = false;
              _this17.isMatched = false;
              _this17.oldGame = false;
              _this17.otherAns = [];
              _this17.oRun = false;
              _this17.setRankIndex(0);
              _this17.setUserOneState(false);
              _this17.setIsRank(false);
              _this17.closeStarBox();
              setTimeout(function () {
                _this17.closeStarBox();
              }, 3000);
              _context.next = 25;
              return _this17.sendSocketMessage("{'type':5,'data':" + _this17.gameId + "}");

            case 25:
              _this17.closeSocket();

            case 26:
            case "end":
              return _context.stop();
          }
        }
      }, _callee, _this17);
    }))();
  }
});

/***/ }),

/***/ 219:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_waitBox_vue__ = __webpack_require__(221);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_35aabb1a_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_waitBox_vue__ = __webpack_require__(222);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(220)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-35aabb1a"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_waitBox_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_35aabb1a_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_waitBox_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\game\\components\\waitBox.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] waitBox.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-35aabb1a", Component.options)
  } else {
    hotAPI.reload("data-v-35aabb1a", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 220:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 221:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_vuex__ = __webpack_require__(1);


//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
  props: ["reStart", "matchInfo", "isMatched"], //开关
  data: function data() {
    return {
      match: false,
      sex: "小哥哥",
      time: 0,
      mactchType: "匹配中",
      other: {}, //对战者信息
      randomTimer: null,
      randomNum: 0,
      randomColorNum: 0,
      staticRandomNum: 0,
      randomList: ["[神游太空的小哥哥]", "[神游太空的小姐姐]", "[贴心的小哥哥]", "[贴心的小姐姐]", "[闷骚的小哥哥]", "[可爱的萌妹子]", "[温柔的小哥哥]", "[温柔的小姐姐]", "[害羞的小哥哥]", "[害羞的小姐姐]", "[才华横溢的小哥哥]", "[才华横溢的小姐姐]", "[呆萌小哥哥]", "[呆萌小姐姐]", "[萌萌的小正太]", "[萌萌的小萝莉]", "[小帅哥]", "[小仙女]", "[颜值爆表的小哥哥]", "[颜值爆表的小姐姐]", "[上班摸鱼的小哥哥]", "[上班摸鱼的小姐姐]", "[智商超高的小哥哥]", "[智商超高的小姐姐]", "[傲娇的小哥哥]", "[傲娇的小姐姐]", "[爱抠脚的老大叔]", "[爱追星的怪阿姨]", "[腹黑的小哥哥]", "[蠢萌的小姐姐]", "[高冷的小哥哥]", "[高冷的小姐姐]"],
      staticList: ["年龄相仿的", "实力相近的"]
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_2_vuex__["c" /* mapState */])(["appInfo", "IMGHEAD"]), {
    wordColor: function wordColor() {
      var list = ["color:#fcee21", "color:#ff3c8d", "color:#93278f", "color:#26cfdf", "color:#ff9179"];
      return list[this.randomNum];
    }
  }),
  watch: {
    matchInfo: function matchInfo(val) {
      var _this = this;

      val.forEach(function (item) {
        if (item.basicInfo.userId != _this.appInfo.userId) {
          _this.other = JSON.parse(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(item));
          if (item.boot) {
            _this.other.basicInfo.avatarUrl = _this.IMGHEAD + _this.other.basicInfo.avatarUrl;
            if (_this.other.basicInfo.gender != "男") {
              _this.sex = "小姐姐";
            }
          }
        }
      });
      this.match = true;
      clearInterval(this.randomTimer);
    }
  },
  methods: {
    cancle: function cancle() {
      //取消匹配
      this.$emit("cancleMatch");
      clearInterval(this.randomTimer);
    },
    getRandom: function getRandom() {
      var _this2 = this;

      this.randomTimer = setInterval(function () {
        _this2.randomNum = parseInt(Math.ceil(Math.random() * 31 - 1));
        _this2.randomColorNum = parseInt(Math.ceil(Math.random() * 5 - 1));
        _this2.staticRandomNum = parseInt(Math.ceil(Math.random() * 2 - 1));
      }, 100);
    }
  },
  mounted: function mounted() {
    this.getRandom();
  }
});

/***/ }),

/***/ 222:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-match-box sky-bg-2"
  }, [_c('div', {
    staticClass: "u-match-type"
  }, [_vm._v(_vm._s(_vm.mactchType))]), _vm._v(" "), _c('div', {
    staticClass: "u-user-box"
  }, [_c('div', {
    staticClass: "u-left"
  }, [_c('div', {
    staticClass: "u-head"
  }, [_c('img', {
    attrs: {
      "src": _vm.appInfo.headImgUrl,
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-medal"
  }, [(_vm.appInfo.image) ? _c('img', {
    attrs: {
      "src": _vm.IMGHEAD + _vm.appInfo.image
    }
  }) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "u-name"
  }, [_vm._v(_vm._s(_vm.appInfo.nickname))]), _vm._v(" "), _c('div', {
    staticClass: "u-medal-name"
  }, [_vm._v(_vm._s(_vm.appInfo.paragraphName))])])]), _vm._v(" "), _c('img', {
    staticClass: "u-lighting",
    attrs: {
      "src": "/static/lighting.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-right"
  }, [(_vm.other && !_vm.other.basicInfo) ? _c('div', {
    staticClass: "u-ant"
  }, [_vm._m(0), _vm._v(" "), _c('div', {
    staticClass: "u-ant-medal"
  })]) : _vm._e(), _vm._v(" "), (_vm.other && _vm.other.basicInfo) ? _c('div', {
    staticClass: "u-data"
  }, [_c('div', {
    staticClass: "u-head"
  }, [_c('img', {
    attrs: {
      "src": _vm.other.basicInfo.avatarUrl,
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-medal"
  }, [_c('img', {
    attrs: {
      "src": _vm.IMGHEAD + _vm.other.abilityInfo.paragraphImage
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-name"
  }, [_vm._v(_vm._s(_vm.other.basicInfo.nickName))]), _vm._v(" "), _c('div', {
    staticClass: "u-medal-name"
  }, [_vm._v(_vm._s(_vm.other.abilityInfo.paragraphName))])])]) : _vm._e()])]), _vm._v(" "), _c('div', {
    staticClass: "u-match-tag"
  }, [_c('div', {
    staticClass: "u-tag-normal"
  }, [_vm._v("匹配到了一位")]), _vm._v(" "), (!_vm.match) ? _c('div', {
    staticClass: "u-tag-spec",
    style: (_vm.wordColor)
  }, [_vm._v(_vm._s(_vm.randomList[_vm.randomNum]))]) : _c('div', {
    staticClass: "u-tag-spec",
    style: (_vm.wordColor)
  }, [_vm._v(_vm._s(_vm.staticList[_vm.staticRandomNum]) + _vm._s(_vm.sex))])]), _vm._v(" "), (!_vm.isMatched) ? _c('div', {
    staticClass: "btn",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": function($event) {
        $event.stopPropagation();
        _vm.cancle($event)
      }
    }
  }, [_vm._v("取消匹配")]) : _vm._e()])
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-ant-head"
  }, [_c('div', {
    staticClass: "u-ant-big-cycle"
  }, [_c('img', {
    staticClass: "u-big-star",
    attrs: {
      "src": "/static/big-star.png",
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-ant-cycle"
  }, [_c('img', {
    staticClass: "u-small-star",
    attrs: {
      "src": "/static/small-star.png",
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-ant-cycle-fill"
  }, [_c('div', {
    staticClass: "u-ant-dot"
  }), _vm._v(" "), _c('div', {
    staticClass: "u-ant-dot"
  }), _vm._v(" "), _c('div', {
    staticClass: "u-ant-dot"
  })])])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-35aabb1a", esExports)
  }
}

/***/ }),

/***/ 223:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_match_vue__ = __webpack_require__(225);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_5cb8143c_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_match_vue__ = __webpack_require__(226);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(224)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-5cb8143c"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_match_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_5cb8143c_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_match_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\game\\components\\match.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] match.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-5cb8143c", Component.options)
  } else {
    hotAPI.reload("data-v-5cb8143c", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 224:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 225:
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


/* harmony default export */ __webpack_exports__["a"] = ({
  props: ["update", "userList"],
  data: function data() {
    return {
      show: false,
      showVs: false,
      myInfo: null, //我的信息
      otherInfo: null, //他人的信息
      times: 0
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["c" /* mapState */])(["userInfo", "IMGHEAD", "appInfo", "globalSoundPlayer"])),
  watch: {
    update: function update() {
      //一但变化，初始化 动画状态
      this.show = false;
      this.showVs = false;
    },
    userList: function userList(list) {
      for (var i = 0; i < list.length; i++) {
        if (list[i].basicInfo.userId == this.appInfo.userId) {
          this.myInfo = list[i];
        } else {
          this.otherInfo = list[i];
        }
      }
    }
  },
  methods: {
    getInfo: function getInfo() {
      if (this.userList) {
        for (var i = 0; i < this.userList.length; i++) {
          if (this.userList[i].basicInfo.userId == this.appInfo.userId) {
            this.myInfo = this.userList[i];
          } else {
            this.otherInfo = this.userList[i];
          }
        }
      }
    },
    animate: function animate() {
      var _this = this;

      setTimeout(function () {
        //进场动画
        _this.$nextTick(function () {
          _this.show = true;
        });
        setTimeout(function () {
          _this.showVs = true;
        }, 200);
      }, 300);

      setTimeout(function () {
        //退场动画
        _this.$nextTick(function () {
          _this.show = false;
          _this.showVs = false;
        });

        setTimeout(function () {
          _this.$emit('matchClose');
        }, 800);
      }, 2800);
    },
    initAudio: function initAudio() {
      this.globalSoundPlayer.src = "/static/audio/match.mp3";
    },
    matchSuccess: function matchSuccess() {
      if (this.times % 2 == 0) {
        this.globalSoundPlayer.play();
      }
      this.times++;
    }
  },
  mounted: function mounted() {
    this.animate();
    this.getInfo();
    this.initAudio();
  }
});

/***/ }),

/***/ 226:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "match-box"
  }, [_c('div', {
    staticClass: "user-one",
    class: {
      sliderDown: _vm.show
    },
    attrs: {
      "eventid": '0'
    },
    on: {
      "transitionend": _vm.matchSuccess
    }
  }, [(_vm.myInfo) ? _c('div', {
    staticClass: "user-box"
  }, [_c('img', {
    staticClass: "user-head",
    attrs: {
      "src": _vm.myInfo.basicInfo.avatarUrl,
      "alt": ""
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "user-info"
  }, [_c('div', {
    staticClass: "name"
  }, [_vm._v(_vm._s(_vm.myInfo.basicInfo.nickName))]), _vm._v(" "), _c('div', {
    staticClass: "winRate"
  }, [_vm._v("胜率：" + _vm._s(_vm.myInfo.abilityInfo.winRatio) + "%")]), _vm._v(" "), _c('div', {
    staticClass: "wins"
  }, [_vm._v("胜场：" + _vm._s(_vm.myInfo.abilityInfo.winNum) + "场")])])]) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "user-two",
    class: {
      sliderUp: _vm.show
    }
  }, [(_vm.otherInfo) ? _c('div', {
    staticClass: "user-box"
  }, [(_vm.otherInfo.boot) ? _c('img', {
    staticClass: "user-head",
    attrs: {
      "src": _vm.IMGHEAD + _vm.otherInfo.basicInfo.avatarUrl,
      "alt": ""
    }
  }) : _vm._e(), _vm._v(" "), (!_vm.otherInfo.boot && _vm.otherInfo.basicInfo.avatarUrl) ? _c('img', {
    staticClass: "user-head",
    attrs: {
      "src": _vm.otherInfo.basicInfo.avatarUrl,
      "alt": ""
    }
  }) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "user-info"
  }, [_c('div', {
    staticClass: "name"
  }, [_vm._v(_vm._s(_vm.otherInfo.basicInfo.nickName))]), _vm._v(" "), _c('div', {
    staticClass: "winRate"
  }, [_vm._v("胜率：" + _vm._s(_vm.otherInfo.abilityInfo.winRatio) + "%")]), _vm._v(" "), _c('div', {
    staticClass: "wins"
  }, [_vm._v("胜场：" + _vm._s(_vm.otherInfo.abilityInfo.winNum) + "场")])])]) : _vm._e()]), _vm._v(" "), _c('img', {
    staticClass: "vs",
    class: {
      showVs: _vm.showVs
    },
    attrs: {
      "src": "/static/vs.png",
      "alt": ""
    }
  })])
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-5cb8143c", esExports)
  }
}

/***/ }),

/***/ 227:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_questionBox_vue__ = __webpack_require__(229);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_4ec69c62_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_questionBox_vue__ = __webpack_require__(237);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(228)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-4ec69c62"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_questionBox_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_4ec69c62_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_questionBox_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\game\\components\\questionBox.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] questionBox.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-4ec69c62", Component.options)
  } else {
    hotAPI.reload("data-v-4ec69c62", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 228:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 229:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__timeCount__ = __webpack_require__(37);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__answerList__ = __webpack_require__(233);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__utils_index__ = __webpack_require__(9);


//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//





var dataStack = [];
/* harmony default export */ __webpack_exports__["a"] = ({
  props: ["subjectList", "userList", "otherAns", "gameOver", "isGameEnd", "oldGame", "netWork", "oRun", "emojiIndexMe", "emojiIndexHe", "sendToMe", "relinkIndex"],
  components: {
    timeCount: __WEBPACK_IMPORTED_MODULE_3__timeCount__["a" /* default */],
    answerList: __WEBPACK_IMPORTED_MODULE_4__answerList__["a" /* default */]
  },
  computed: __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_2_vuex__["c" /* mapState */])(["appInfo", "IMGHEAD", "userOneState", "userTwoState", "cw", "isRank", "globalSoundPlayer", "gameRecord", "rankIndex", "gamePageType"])),
  data: function data() {
    return {
      first: true,
      showTime: false,
      showAnsList: false,
      questionIndex: 0, //控制题目的选项
      myInfo: {},
      myHeadInfo: { headImg: "", name: "???", ansList: [], score: 0 },
      otherHeadInfo: { headImg: "", name: "???", ansList: [], score: 0 },
      myScore: {}, //我的分数
      otherInfo: {},
      otherScore: {}, //他人答题,
      resetBtn: false, //reset count
      initTime: 10,
      initTimeCount: false, //初始化计时器
      startTimeCount: false, //开始化计时器
      stopTimeCount: false, //停止化计时器
      canNext: false,
      gameResult: {
        otherScore: 0,
        myScore: 0
      },
      resultType: "",
      oRun: false,
      showEmojiMe: false,
      showEmojiHe: false,
      showEmojiBox: false,
      emojiList: ["/static/icon/0.png", "/static/icon/1.png", "/static/icon/2.png", "/static/icon/3.png"],

      // d动效相关控制
      gameStop: false,
      showHead: false,
      userResult: 0,
      withUserResult: 0,
      headMode: 0,
      myAnsList: [0, 0, 0, 0, 0], //我的回答集 0 超时未答，1 正确 2 错误
      otherAnsList: [0, 0, 0, 0, 0], //回答集
      //结果动画
      showBtn: false,

      // 奖励
      isReLink: false,
      newTime: 0
    };
  },

  watch: {
    rankIndex: function rankIndex(value) {
      //更新题目 更新 计时器
      this.canNext = !this.canNext;
    },
    netWork: function netWork() {
      console.log("network change");
      this.getCurrentQuetion();
      this.isReLink = true;
    },
    isGameEnd: function isGameEnd() {
      this.showTime = false;
      var list = this.gameRecord.Result.subjectList;
      var resObj = {
        oRun: this.oRun
      };
      Object(__WEBPACK_IMPORTED_MODULE_5__utils_index__["e" /* setStorage */])("rankGameResult", resObj);
    },
    otherAns: function otherAns(list) {
      if (list.length > 0) {
        for (var i = 0; i < list.length; i++) {
          if (list[i].uid != this.appInfo.userId) {
            this.otherScore = list[i];
            this.otherHeadInfo.score = list[i].score;

            if (list[i].timeout) {
              this.otherAnsList[this.questionIndex] = 0; //超时
            } else {
              if (list[i].right) {
                this.otherAnsList[this.questionIndex] = 1; //回答正确
              } else {
                this.otherAnsList[this.questionIndex] = 2; //回答错误
              }
            }
          }

          if (list[i].uid == this.appInfo.userId) {
            this.myScore = list[i];
            this.myHeadInfo.score = list[i].score;

            if (list[i].timeout) {
              this.myAnsList[this.questionIndex] = 0; //超时
            } else {
              if (list[i].right) {
                this.myAnsList[this.questionIndex] = 1; //回答正确
              } else {
                this.myAnsList[this.questionIndex] = 2; //回答错误
              }
            }
            this.setUserOneState(true); // 收到S端 A用户数据
          }
        }
      }
    },
    gameOver: function gameOver() {
      this.gameStop = true;
      this.stopTimeCount = !this.stopTimeCount;
      this.initTimeCount = !this.initTimeCount;
      this.showTime = false;
      this.showAnsList = false;
      this.showHead = false;
      this.oRun = true;
      this.gameResult.otherScore = 0;
    },
    emojiIndexMe: function emojiIndexMe(index) {
      var _this = this;

      console.log("emojiIndexMe", index);
      if (index == -1) {
        return false;
      }
      setTimeout(function () {
        _this.showEmojiMe = true;
      }, 100);
      setTimeout(function () {
        _this.showEmojiMe = false;
      }, 1600);
    },
    emojiIndexHe: function emojiIndexHe(index) {
      var _this2 = this;

      if (index == -1) {
        return false;
      }
      setTimeout(function () {
        _this2.showEmojiHe = true;
      }, 100);
      setTimeout(function () {
        _this2.showEmojiHe = false;
      }, 1600);
    }
  },
  methods: __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_2_vuex__["b" /* mapMutations */])({
    setUserOneState: "setUserOneState",
    setUserTwoState: "setUserTwoState",
    setCw: "setCw"
  }), {
    sendEmoji: function sendEmoji(index) {
      var obj = { index: index, id: this.appInfo.userId };
      obj = __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(obj);
      this.showEmojiBox = false;
      this.$emit("sendEmoji", obj);
    },
    getInfo: function getInfo() {
      //获取列表
      if (this.userList) {
        for (var i = 0; i < this.userList.length; i++) {
          if (this.userList[i].basicInfo.userId == this.appInfo.userId) {
            // 头部信息
            this.myHeadInfo = {
              headImg: this.userList[i].basicInfo.avatarUrl,
              name: this.userList[i].basicInfo.nickName
            };
          } else {
            this.otherInfo = this.userList[i];
            // 头部信息
            this.otherHeadInfo = {
              headImg: this.userList[i].basicInfo.avatarUrl,
              name: this.userList[i].basicInfo.nickName
            };
            if (this.otherInfo.boot) {
              this.otherHeadInfo.headImg = this.IMGHEAD + this.userList[i].basicInfo.avatarUrl;
            }
          }
        }
      }
    },
    getCurrentQuetion: function getCurrentQuetion() {
      var index = 0,
          user = {};
      // this.userList[0].index > this.userList[1].index
      //   ? (index = this.userList[1].index)
      //   : (index = this.userList[0].index);
      this.userList[0].basicInfo.userId == this.appInfo.userId ? user = this.userList[0] : user = this.userList[1];
      this.newTime = user.restSecond;
      this.initTime = Math.ceil(user.restSecond / 1000);
      this.questionIndex = this.relinkIndex; //获取当前题目
      this.setUserOneState(false);
      this.showAnsList = true;
      this.initTimeCount = !this.initTimeCount;
      this.startTimeCount = !this.startTimeCount;
    },
    getResult: function getResult() {
      if (this.gameResult.myScore > this.gameResult.otherScore || this.oRun) {
        this.resultType = "胜利";
      } else if (this.gameResult.myScore < this.gameResult.otherScore) {
        this.setCw(0);
        this.resultType = "失败";
      } else if (this.gameResult.myScore == this.gameResult.otherScore && !this.oRun) {
        this.setCw(0);
        this.resultType = "平局";
      }
    },
    start: function start() {
      var _this3 = this;

      //开始答题
      this.showTime = true;
      setTimeout(function () {
        _this3.showHead = true;
      }, 500);
      setTimeout(function () {
        _this3.checkShowAnsList();
      }, 1000);

      this.initTimeCount = !this.initTimeCount;
    },
    setUserId: function setUserId(obj) {
      obj.data.uid = this.appInfo.userId;
    },
    showSelectComplete: function showSelectComplete(res) {
      var _this4 = this;

      //当前题目答完  清空计时器，暂停进度条
      this.stopTimeCount = !this.stopTimeCount;
      if (this.questionIndex < 4) {
        setTimeout(function () {
          _this4.initTimeCount = !_this4.initTimeCount;
          _this4.questionIndex = _this4.rankIndex; //题目增加
          _this4.isReLink = false;
          _this4.initTime = 10;
          console.log("this.questionIndex", _this4.questionIndex);
        }, 2000);
      } else if (this.questionIndex == 4) {
        console.log("this.questionIndex==4?", this.questionIndex);
        this.stopTimeCount = !this.stopTimeCount;
        setTimeout(function () {
          _this4.$emit("answerEnd");
          _this4.initTimeCount = !_this4.initTimeCount;
        }, 1000);
      }
      this.gameResult = res;
    },
    sendNext: function sendNext() {
      this.startTimeCount = !this.startTimeCount;
      this.$emit("nextQuestion");
    },
    answerSuccess: function answerSuccess(obj) {
      // 答题正确
      this.setUserId(obj);
      this.$emit("ansRight", obj);
    },
    answerFail: function answerFail(obj) {
      // 答题错误
      this.setUserId(obj);
      this.$emit("ansFail", obj);
    },
    checkShowAnsList: function checkShowAnsList() {
      if (this.questionIndex != 4 && !this.gameStop || this.questionIndex == 4 && this.oldGame && this.gameStop) {
        this.showAnsList = true;
      }
    },
    selectAdvice: function selectAdvice(item) {
      this.adviceType = item.type;
    },
    changeTopBg: function changeTopBg() {
      var bgColor = "#28235c";
      if (this.gamePageType == 1) {
        bgColor = "#3f84fb";
      }
      wx.setNavigationBarColor({
        frontColor: "#ffffff",
        backgroundColor: bgColor
      });
    },
    submitAdvice: function submitAdvice() {
      var _this5 = this;

      this.fly.post("/feedBackUser", {
        userId: this.otherScore.uid,
        types: this.adviceType
      }).then(function (res) {
        wx.showToast({
          title: "提交成功,谢谢！",
          icon: "none"
        });
        _this5.showModal = false;
      });
    }
  }),
  onLoad: function onLoad(options) {
    dataStack.push(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default()({}, this.$data));
  },
  mounted: function mounted() {
    this.changeTopBg();
    this.getInfo();
    if (this.oldGame) {
      this.getCurrentQuetion();
    }
    this.start();
  }
});

/***/ }),

/***/ 233:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_answerList_vue__ = __webpack_require__(235);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_e0d65da2_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_answerList_vue__ = __webpack_require__(236);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(234)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-e0d65da2"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_answerList_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_e0d65da2_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_answerList_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\game\\components\\answerList.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] answerList.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-e0d65da2", Component.options)
  } else {
    hotAPI.reload("data-v-e0d65da2", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 234:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 235:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_get_iterator__ = __webpack_require__(36);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_get_iterator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_get_iterator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_object_assign__ = __webpack_require__(20);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_object_assign___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_object_assign__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_vuex__ = __webpack_require__(1);




//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
  props: ["questionList", "currentIndex", "otherScore", "myScore", "canNext", "oldGame", "netWork", "initTime"],
  data: function data() {
    return {
      timer: null,
      time: 10000,
      timeOut: false,
      list: null,
      myScoreNumber: 0,
      myProcess: "height:0%;",
      otherScoreNumber: 0,
      otherProcess: "height:0%;",
      otherAns: null,
      otherAnsOk: false,
      answerMap: {},
      result: { myScore: 0, otherScore: 0 }, //答题结果,
      idiomList: [], //成语题目列表
      poetryList: [], //诗词长度
      //动画相关
      isFirst: true,

      showTitle: false,
      showTitleType: false,
      showList: false,
      showProcess: false,
      showFailMask: false, //错误红色遮罩
      animationTime: 1,
      showDoubleTip: false,
      otherAnsFail: false,
      levelList: [],
      imgLevelList: [],
      dataReady: false,
      answerTimer: null,
      // 结果动画
      showAntSuccess: false,
      showAntFail: false,
      showAntBalance: false,
      // 连对动画
      showCrLeft: false,
      showCrRight: false,
      showLeftBook: false,
      showRightBook: false,
      bookTypeMap: {
        2: { img: "/static/icon/shang.png" },
        3: { img: "/static/icon/wen.png" },
        4: { img: "/static/icon/li.png" },
        5: { img: "/static/icon/yi.png" },
        6: { img: "/static/icon/ti.png" },
        7: { img: "/static/icon/zong.png" }
      }
    };
  },

  watch: {
    canNext: function canNext() {
      this.showOtherAns(); //展示双方答案
    },
    time: function time(val) {
      if (val <= 0) {
        this.stopTimer();
        this.timeOut = true;
        console.log("timeout");
      }
    },
    initTime: function initTime(val) {
      this.timeOut = false;
      this.time = val;
    },
    currentIndex: function currentIndex(val) {
      if (!this.netWork) {
        this.stopTimer();
        // 更新题目动画
        this.showTitleTag();
      } else {
        console.log("重连题目更新", val);
        this.oldGameShow();
      }
    },
    otherScore: function otherScore(val) {
      var _this = this;

      if (!val.right) {
        this.otherAnsFail = true;
        setTimeout(function () {
          _this.otherAnsFail = false;
        }, 500);
      }
      this.otherScoreNumber = val.score;
      this.otherProcess = "height:" + this.otherScoreNumber / 13.8 + "%;";
      this.result.otherScore = this.otherScoreNumber;
      if (val.continueRightNum >= 2) {
        this.showCrRight = true;
        setTimeout(function () {
          _this.showCrRight = false;
        }, 1500);
      }
      if (val.bookScore > 0) {
        this.showRightBook = true;
        setTimeout(function () {
          _this.showRightBook = false;
        }, 2500);
      }
    },
    myScore: function myScore(val) {
      var _this2 = this;

      console.log("myScore", val);
      this.myScoreNumber = val.score;
      this.myProcess = "height:" + this.myScoreNumber / 13.8 + "%;";
      this.result.myScore = this.myScoreNumber;
      clearTimeout(this.answerTimer);
      this.showMyAnswer(val);
      if (val.continueRightNum >= 2) {
        this.showCrLeft = true;
        setTimeout(function () {
          _this2.showCrLeft = false;
        }, 1500);
      }
      if (val.bookScore > 0) {
        this.showLeftBook = true;
        setTimeout(function () {
          _this2.showLeftBook = false;
        }, 2500);
      }
    }
  },
  computed: __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_4_vuex__["c" /* mapState */])(["userOneState", "gamePageType", "userTwoState", "isRank", "IMGHEAD", "globalSoundPlayer", "socketState"]), {
    answer: function answer() {
      //当前题目答案
      if (this.list && this.list[this.currentIndex]) {
        return this.list[this.currentIndex].answer;
      }
    },
    questionType: function questionType() {
      if (this.list) {
        var type = this.list[this.currentIndex].parentType;
        var obj = {
          1: "德",
          2: "商",
          3: "文",
          4: "理",
          5: "艺",
          6: "体",
          7: "综"
        };
        return obj[type];
      }
    }
  }),
  methods: __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_4_vuex__["b" /* mapMutations */])(["setUserOneState"]), {
    audioPlay: function audioPlay(type) {
      var _this3 = this;

      if (this.globalSoundPlayer.volume == 0) {
        return false;
      }
      if (type == "success") {
        this.globalSoundPlayer.src = "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/right.mp3";
      } else {
        this.globalSoundPlayer.src = "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/wrong.mp3";
      }
      this.globalSoundPlayer.play();
      setTimeout(function () {
        _this3.globalSoundPlayer.src = "";
      }, 3000);
    },
    showMyAnswer: function showMyAnswer(res) {
      var _this4 = this;

      for (var i = 0; i < this.list[res.index].optionList.length; i++) {
        if (this.list[res.index].optionList[i].sortId == res.answer) {
          var obj = JSON.parse(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default()(this.list[res.index]));
          if (res.right == true) {
            this.audioPlay("success");
            obj.optionList[i].state = "success";
            this.$set(this.list, res.index, __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_object_assign___default()(this.list[res.index], obj));
            break;
          } else {
            this.audioPlay("fail");
            obj.optionList[i].state = "fail";
            this.$set(this.list, res.index, __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_object_assign___default()(this.list[res.index], obj));
            this.showFailMask = true;
            setTimeout(function () {
              _this4.showFailMask = false;
            }, 400);
            break;
          }
        }
      }
    },

    // 可以发送type6
    sendNextToService: function sendNextToService() {
      this.showList = true;
      this.setUserOneState(false);
      this.startTimer();

      this.$emit("nextQuestion");
      console.log("ansList emit nextQuestion", this.currentIndex);
      if (this.currentIndex != 4) {
        this.showProcess = true;
      }
    },
    startTimer: function startTimer() {
      var _this5 = this;

      this.timeOut = false;
      this.timer = setInterval(function () {
        _this5.time -= 100;
      }, 100);
    },
    stopTimer: function stopTimer() {
      clearInterval(this.timer);
      this.time = 10000;
    },
    showTitleTag: function showTitleTag() {
      var _this6 = this;

      //题目类型
      this.showTitleType = true;
      if (this.currentIndex == 4) {
        //双倍提示
        this.showDoubleTip = true;
      }
      setTimeout(function () {
        //题目类型隐藏
        _this6.showTitleType = false;
        _this6.showDoubleTip = false;
      }, 1500);

      setTimeout(function () {
        //显示题目
        _this6.showTitle = true;
        setTimeout(function () {
          _this6.sendNextToService();
        }, 1000);
      }, 1600);
    },
    oldGameShow: function oldGameShow() {
      console.log('oldGame', this.currentIndex);
      this.timeOut = false;
      this.showTitle = true;
      this.showProcess = true;
      // this.sendNextToService();
    },
    getList: function getList() {
      this.list = JSON.parse(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default()(this.questionList)); //深拷贝 问题数据
      for (var i = 0; i < this.list.length; i++) {
        // 处理选项
        for (var j = 0; j < this.list[i].optionList.length; j++) {
          this.list[i].optionList[j].state = null;
          this.list[i].optionList[j].name = this.list[i].optionList[j].name.replace(/\s+/g, "");
          this.list[i].optionList[j].isSelect = false;
        }
        // 判断成语题 生成题目列表

        if (this.list[i].modeType == 5) {
          var list = this.list[i].title.replace("_", "?").split("");
          this.idiomList.push(list);
        } else {
          this.idiomList.push(0);
        }

        if (this.list[i].modeType == 7 || this.list[i].modeType == 8 || this.list[i].modeType == 9 || this.list[i].modeType == 10) {
          var _list = this.list[i].title.replace("_", "?").split("");
          this.poetryList.push(_list);
        } else {
          this.poetryList.push(0);
        }
      }

      this.dataReady = true;
      this.getRatioList(this.list);
    },
    getRatioList: function getRatioList(list) {
      var _iteratorNormalCompletion = true;
      var _didIteratorError = false;
      var _iteratorError = undefined;

      try {
        for (var _iterator = __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_get_iterator___default()(list), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
          var item = _step.value;

          if (item.rightRatio >= 66) {
            this.levelList.push({ type: "简单", blue: true });
            this.imgLevelList.push({ type: "简", blue: true });
          }
          if (item.rightRatio >= 33 && item.rightRatio < 66) {
            this.levelList.push({ type: "一般", yellow: true });
            this.imgLevelList.push({ type: "中", yellow: true });
          }
          if (item.rightRatio >= 0 && item.rightRatio < 33) {
            this.levelList.push({ type: "困难", red: true });
            this.imgLevelList.push({ type: "难", red: true });
          }
        }
      } catch (err) {
        _didIteratorError = true;
        _iteratorError = err;
      } finally {
        try {
          if (!_iteratorNormalCompletion && _iterator.return) {
            _iterator.return();
          }
        } finally {
          if (_didIteratorError) {
            throw _iteratorError;
          }
        }
      }
    },
    shake: function shake() {
      wx.vibrateShort({
        success: function success() {
          console.log("点击震动");
        }
      });
    },
    select: function select(item, index) {
      //TODO 检查答案，处理选中逻辑
      console.log("选项是否选过", item.select, "此题是否答过", this.userOneState, "是否超时", this.timeOut);
      if (!item.select && !this.userOneState && !this.timeOut) {
        this.shake();
        var answerMap = {};
        for (var i = 0; i < this.list[this.currentIndex].optionList.length; i++) {
          answerMap[this.list[this.currentIndex].optionList[i].name] = this.list[this.currentIndex].optionList[i].sortId;
        }
        var obj = {
          type: 4,
          data: {
            answer: answerMap[item.name],
            index: this.currentIndex,
            seconds: this.time, //到 questionBox在赋值
            timeout: false, // 默认false, qsb 赋值
            uid: 0 //qbs 赋值
          }
        };
        item.select = true;
        item.userOne = true;
        item.state = 'waitResoponse';
        this.answerTimer = setTimeout(function () {
          wx.showToast({
            title: "网络较差,请在良好环境下游戏",
            icon: "none"
          });
        }, 3000);
        if (item.sortId == this.list[this.currentIndex].optionSortId) {

          this.$emit("success", obj); //提交答案 send type4
        } else {

          this.$emit("fail", obj);
        }
        this.setUserOneState(true);
        this.stopTimer(); //计时器停止
      }
    },
    showOtherAns: function showOtherAns() {
      var _this7 = this;

      //双方都答题后 才显示 B用户数据  则此行为即为 可下一题状态 不需要在判定
      //显示对方选项
      var list = this.list[this.currentIndex].optionList;
      var _otherScore = this.otherScore || {};
      // 显示对手答案
      for (var i = 0; i < list.length; i++) {
        if (_otherScore.right && list[i].sortId == _otherScore.rightAnswer && _otherScore.index == this.currentIndex) {
          list[i].state = "success";
          list[i].select = true;
          list[i].userTwo = true;
        } else if (!_otherScore.right && list[i].sortId == _otherScore.answer && _otherScore.index == this.currentIndex) {
          list[i].state = "fail";
          list[i].select = true;
          list[i].userTwo = true;
        }
      }

      //展示题目正确选项 隐藏未选择选项
      for (var _i = 0; _i < list.length; _i++) {
        if (list[_i].state != "success" && list[_i].state != "fail" && list[_i].sortId != this.list[this.currentIndex].optionSortId) {
          list[_i].noSelect = true;
        } else if (list[_i].state != "success" && list[_i].state != "fail" && list[_i].sortId == this.list[this.currentIndex].optionSortId) {
          list[_i].state = "success";
          list[_i].select = true;
        }
      }

      setTimeout(function () {
        _this7.$emit("showSelectComplete", _this7.result);
      }, 400);

      // 上一题退场动画
      setTimeout(function () {
        _this7.showTitle = false;
        _this7.showList = false;
      }, 1500);
    }
  }),
  mounted: function mounted() {
    this.getList();
    if (this.oldGame) {
      this.oldGameShow();
    } else {
      this.showTitleTag();
    }
  },
  onUnload: function onUnload() {
    this.stopTimer();
  }
});

/***/ }),

/***/ 236:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return (_vm.dataReady) ? _c('div', {
    staticClass: "ans-box",
    class: {
      'bule-type': _vm.gamePageType == 1
    }
  }, [_c('div', {
    staticClass: "title-type",
    class: {
      titleTypeIn: _vm.showTitleType
    }
  }, [_c('div', {
    staticClass: "title-txt rank-icon",
    class: {
      'rank-icon-word-de': _vm.questionType == '德',
        'rank-icon-word-shang': _vm.questionType == '商',
        'rank-icon-word-wen': _vm.questionType == '文',
        'rank-icon-word-li': _vm.questionType == '理',
        'rank-icon-word-yi': _vm.questionType == '艺',
        'rank-icon-word-ti': _vm.questionType == '体',
        'rank-icon-word-zong': _vm.questionType == '综',
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "tt-bg",
    attrs: {
      "src": "/static/title-bg.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "title-number"
  }, [_vm._v("第" + _vm._s(_vm.currentIndex + 1) + "题")]), _vm._v(" "), (_vm.showDoubleTip) ? _c('img', {
    staticClass: "tt-double",
    attrs: {
      "src": "/static/X2.png"
    }
  }) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "b-score-left",
    class: {
      'show-score': _vm.showLeftBook, 'shang': _vm.myScore.bookType == 2, 'wen': _vm.myScore.bookType == 3, 'li': _vm.myScore.bookType == 4, 'yi': _vm.myScore.bookType == 5, 'ti': _vm.myScore.bookType == 6, 'zong': _vm.myScore.bookType == 7,
    }
  }, [_c('img', {
    attrs: {
      "src": _vm.bookTypeMap[_vm.myScore.bookType || 2].img
    }
  }), _vm._v(" +" + _vm._s(_vm.myScore.bookScore || 0) + "\n    "), _c('div', {
    staticClass: "b-level"
  }, [_vm._v(_vm._s(_vm.myScore.bookLevel))])]), _vm._v(" "), _c('div', {
    staticClass: "b-score-right",
    class: {
      'show-score': _vm.showRightBook, 'shang': _vm.otherScore.bookType == 2, 'wen': _vm.otherScore.bookType == 3, 'li': _vm.otherScore.bookType == 4, 'yi': _vm.otherScore.bookType == 5, 'ti': _vm.otherScore.bookType == 6, 'zong': _vm.otherScore.bookType == 7,
    }
  }, [_c('img', {
    attrs: {
      "src": _vm.bookTypeMap[_vm.otherScore.bookType || 2].img
    }
  }), _vm._v(" +" + _vm._s(_vm.otherScore.bookScore || 0) + "\n    "), _c('div', {
    staticClass: "b-level"
  }, [_vm._v(_vm._s(_vm.otherScore.bookLevel))])]), _vm._v(" "), _c('div', {
    staticClass: "box-top"
  }, [(_vm.list[_vm.currentIndex].modeType == 3 || _vm.list[_vm.currentIndex].modeType == 4 || _vm.list[_vm.currentIndex].modeType == 6) ? _c('div', {
    staticClass: "m-word-title",
    class: {
      titleIn: _vm.showTitle, english: _vm.list[_vm.currentIndex].modeType == 6
    }
  }, [(_vm.isRank) ? _c('div', {
    staticClass: "u-word-level",
    class: {
      titleLevelIn: _vm.showTitleType, blue: _vm.levelList[_vm.currentIndex].blue, red: _vm.levelList[_vm.currentIndex].red, yellow: _vm.levelList[_vm.currentIndex].yellow,
    }
  }, [_vm._v("\n        " + _vm._s(_vm.levelList[_vm.currentIndex].type) + "\n      ")]) : _vm._e(), _vm._v("\n      " + _vm._s(_vm.list[_vm.currentIndex].title) + "\n    ")]) : _vm._e(), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType == 5) ? _c('div', {
    staticClass: "m-idiom",
    class: {
      titleIn: _vm.showTitle
    }
  }, [(_vm.isRank) ? _c('div', {
    staticClass: "u-word-level",
    class: {
      titleLevelIn: _vm.showTitleType, blue: _vm.levelList[_vm.currentIndex].blue, red: _vm.levelList[_vm.currentIndex].red, yellow: _vm.levelList[_vm.currentIndex].yellow,
    }
  }, [_vm._v("\n        " + _vm._s(_vm.levelList[_vm.currentIndex].type) + "\n      ")]) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "u-word-box"
  }, _vm._l((_vm.idiomList[_vm.currentIndex]), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "u-word"
    }, [_vm._v(_vm._s(item))])
  }))]) : _vm._e(), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType == 1) ? _c('div', {
    staticClass: "m-img",
    class: {
      titleIn: _vm.showTitle
    }
  }, [_c('div', {
    staticClass: "img-title"
  }, [_c('div', {
    staticClass: "img-level",
    class: {
      blue: _vm.levelList[_vm.currentIndex].blue, red: _vm.levelList[_vm.currentIndex].red, yellow: _vm.levelList[_vm.currentIndex].yellow
    }
  }, [_vm._v(_vm._s(_vm.levelList[_vm.currentIndex].type))]), _vm._v("\n        " + _vm._s(_vm.list[_vm.currentIndex].title) + "\n      ")]), _vm._v(" "), _c('div', {
    staticClass: "img-box",
    class: {
      titleIn: _vm.showTitle
    }
  }, [_c('img', {
    attrs: {
      "mode": "aspectFit",
      "src": _vm.IMGHEAD + _vm.list[_vm.currentIndex].image,
      "alt": ""
    }
  })])]) : _vm._e(), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType == 7 || _vm.list[_vm.currentIndex].modeType == 8 || _vm.list[_vm.currentIndex].modeType == 9 || _vm.list[_vm.currentIndex].modeType == 10) ? _c('div', {
    staticClass: "m-poetry",
    class: {
      'm-poetry-reverce': (_vm.list[_vm.currentIndex].modeType == 8 || _vm.list[_vm.currentIndex].modeType == 10), titleIn: _vm.showTitle
    }
  }, [(_vm.isRank) ? _c('div', {
    staticClass: "u-word-level",
    class: {
      titleLevelIn: _vm.showTitleType, blue: _vm.levelList[_vm.currentIndex].blue, red: _vm.levelList[_vm.currentIndex].red, yellow: _vm.levelList[_vm.currentIndex].yellow,
    }
  }, [_vm._v("\n        " + _vm._s(_vm.levelList[_vm.currentIndex].type) + "\n      ")]) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "u-sentence"
  }, [_vm._v(_vm._s(_vm.list[_vm.currentIndex].title) + "\n        "), (_vm.list[_vm.currentIndex].modeType == 7 || _vm.list[_vm.currentIndex].modeType == 9) ? _c('div', {
    staticClass: "u-symbol"
  }, [_vm._v(",")]) : _c('div', {
    staticClass: "u-symbol"
  }, [_vm._v("。")])]), _vm._v(" "), _c('div', {
    staticClass: "u-line"
  }, [_vm._l((_vm.poetryList[_vm.currentIndex]), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "u-line-item"
    })
  }), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType == 7 || _vm.list[_vm.currentIndex].modeType == 9) ? _c('div', {
    staticClass: "u-symbol"
  }, [_vm._v("。")]) : _c('div', {
    staticClass: "u-symbol"
  }, [_vm._v(",")])], 2)]) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "box-bottom"
  }, [_c('div', {
    staticClass: "score-process",
    class: {
      processIn: _vm.showProcess
    }
  }, [_c('div', {
    staticClass: "score"
  }, [_vm._v(_vm._s(_vm.myScoreNumber))]), _vm._v(" "), _c('div', {
    staticClass: "process",
    style: (_vm.myProcess)
  })]), _vm._v(" "), (_vm.dataReady) ? _c('div', {
    staticClass: "ans-center"
  }, [_c('div', {
    staticClass: "select-list",
    class: {
      listIn: _vm.showList
    }
  }, [_vm._l((_vm.list[_vm.currentIndex].optionList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "select-item",
      class: {
        success: item.state == 'success', fail: item.state == 'fail', wait: item.state == 'waitResoponse', noSelect: item.noSelect, big: _vm.list[_vm.currentIndex].modeType == 4
      },
      attrs: {
        "eventid": '0-' + index
      },
      on: {
        "click": function($event) {
          _vm.select(item, index)
        }
      }
    }, [(item.state == 'success' && item.userOne) ? _c('div', {
      staticClass: "yes-arrow-l"
    }) : _vm._e(), _vm._v(" "), (item.state == 'fail' && item.userOne) ? _c('div', {
      staticClass: "err-arrow-l"
    }) : _vm._e(), _vm._v(" "), _c('div', {
      staticClass: "select-item-txt"
    }, [(item.state == 'success' && item.userOne && _vm.list[_vm.currentIndex].modeType != 4) ? _c('img', {
      staticClass: "left-img yes",
      attrs: {
        "src": "/static/rank/yes.png",
        "alt": ""
      }
    }) : _vm._e(), _vm._v(" "), (item.state == 'fail' && item.userOne && _vm.list[_vm.currentIndex].modeType != 4) ? _c('img', {
      staticClass: "left-img err",
      attrs: {
        "src": "/static/rank/error.png",
        "alt": ""
      }
    }) : _vm._e(), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType != 4) ? _c('div', {
      staticClass: "txt"
    }, [_vm._v("\n              " + _vm._s(item.name) + "\n            ")]) : _vm._e(), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType == 4 && item.name == '对' && !item.state && _vm.gamePageType == 0) ? _c('img', {
      staticClass: "select-img select-yes",
      attrs: {
        "src": "/static/rank/b_yes.png",
        "alt": ""
      }
    }) : _vm._e(), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType == 4 && item.name == '错' && !item.state && _vm.gamePageType == 0) ? _c('img', {
      staticClass: "select-img select-err",
      attrs: {
        "src": "/static/rank/b_error.png",
        "alt": ""
      }
    }) : _vm._e(), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType == 4 && item.name == '对' && !item.state && _vm.gamePageType == 1) ? _c('img', {
      staticClass: "select-img select-yes",
      attrs: {
        "src": "/static/rank/d_yes.png",
        "alt": ""
      }
    }) : _vm._e(), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType == 4 && item.name == '错' && !item.state && _vm.gamePageType == 1) ? _c('img', {
      staticClass: "select-img select-err",
      attrs: {
        "src": "/static/rank/d_error.png",
        "alt": ""
      }
    }) : _vm._e(), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType == 4 && item.name == '对' && item.state) ? _c('img', {
      staticClass: "select-img select-yes",
      attrs: {
        "src": "/static/rank/yes_w.png",
        "alt": ""
      }
    }) : _vm._e(), _vm._v(" "), (_vm.list[_vm.currentIndex].modeType == 4 && item.name == '错' && item.state) ? _c('img', {
      staticClass: "select-img select-err",
      attrs: {
        "src": "/static/rank/error_w.png",
        "alt": ""
      }
    }) : _vm._e(), _vm._v(" "), (item.state == 'success' && item.userTwo && _vm.list[_vm.currentIndex].modeType != 4) ? _c('img', {
      staticClass: "right-img yes",
      attrs: {
        "src": "/static/rank/yes.png",
        "alt": ""
      }
    }) : _vm._e(), _vm._v(" "), (item.state == 'fail' && item.userTwo && _vm.list[_vm.currentIndex].modeType != 4) ? _c('img', {
      staticClass: "right-img err",
      attrs: {
        "src": "/static/rank/error.png",
        "alt": ""
      }
    }) : _vm._e()]), _vm._v(" "), (item.state == 'success' && item.userTwo) ? _c('div', {
      staticClass: "yes-arrow-r"
    }) : _vm._e(), _vm._v(" "), (item.state == 'fail' && item.userTwo) ? _c('div', {
      staticClass: "err-arrow-r"
    }) : _vm._e()])
  }), _vm._v(" "), _c('div', {
    staticClass: "creat-info",
    class: {
      'showCreat': _vm.showList
    }
  }, [_vm._v("\n          ——本题由\n          "), (!_vm.list[_vm.currentIndex].createUserUrl) ? _c('img', {
    attrs: {
      "src": "/static/default-head.png"
    }
  }) : _c('img', {
    attrs: {
      "src": _vm.list[_vm.currentIndex].createUserUrl
    }
  }), _vm._v(" "), _c('div', [_vm._v(_vm._s(_vm.list[_vm.currentIndex].createUserName))]), _vm._v(" 贡献——\n        ")])], 2)]) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "score-process",
    class: {
      processIn: _vm.showProcess
    }
  }, [_c('div', {
    staticClass: "score"
  }, [_vm._v(_vm._s(_vm.otherScoreNumber))]), _vm._v(" "), _c('div', {
    staticClass: "process",
    class: {
      otherAnsFail: _vm.otherAnsFail
    },
    style: (_vm.otherProcess)
  })])]), _vm._v(" "), (_vm.showFailMask) ? _c('div', {
    staticClass: "fail-mask"
  }) : _vm._e()]) : _vm._e()
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-e0d65da2", esExports)
  }
}

/***/ }),

/***/ 237:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "question-box",
    class: {
      'blue-type': _vm.gamePageType == 1
    }
  }, [_c('div', {
    staticClass: "head-box"
  }, [_c('div', {
    staticClass: "u-head-left",
    class: {
      'head-in': _vm.showHead
    }
  }, [_c('img', {
    staticClass: "u-img",
    attrs: {
      "src": _vm.myHeadInfo.headImg,
      "alt": ""
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-info"
  }, [_c('div', {
    staticClass: "u-head-name"
  }, [_vm._v(_vm._s(_vm.myHeadInfo.name))]), _vm._v(" "), _c('div', {
    staticClass: "u-head-sore"
  }, [_vm._v(_vm._s(_vm.myScore.score || 0))])]), _vm._v(" "), _c('img', {
    staticClass: "emoji-btn",
    attrs: {
      "src": "/static/icon/emojiBtn.png",
      "eventid": '0'
    },
    on: {
      "click": function($event) {
        _vm.showEmojiBox = !_vm.showEmojiBox
      }
    }
  }), _vm._v(" "), (_vm.showEmojiBox) ? _c('div', {
    staticClass: "emoji-box"
  }, _vm._l((_vm.emojiList), function(item, index) {
    return _c('img', {
      key: index,
      attrs: {
        "src": item,
        "eventid": '1-' + index
      },
      on: {
        "click": function($event) {
          _vm.sendEmoji(index)
        }
      }
    })
  })) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "u-head-right",
    class: {
      'head-in': _vm.showHead
    }
  }, [_c('div', {
    staticClass: "u-info"
  }, [_c('div', {
    staticClass: "u-head-name"
  }, [_vm._v(_vm._s(_vm.otherHeadInfo.name))]), _vm._v(" "), _c('div', {
    staticClass: "u-head-sore"
  }, [_vm._v(_vm._s(_vm.otherScore.score || 0))])]), _vm._v(" "), _c('img', {
    staticClass: "u-img",
    attrs: {
      "src": _vm.otherHeadInfo.headImg,
      "alt": ""
    }
  })]), _vm._v(" "), (_vm.showTime) ? _c('div', {
    staticClass: "round-cvs"
  }, [_c('timeCount', {
    attrs: {
      "initBtn": _vm.initTimeCount,
      "empty": true,
      "small": true,
      "startBtn": _vm.startTimeCount,
      "stopBtn": _vm.stopTimeCount,
      "initTime": _vm.initTime,
      "mpcomid": '0'
    }
  })], 1) : _vm._e()]), _vm._v(" "), _c('img', {
    staticClass: "antEomji left130",
    class: {
      'sendAnt': _vm.showEmojiMe
    },
    attrs: {
      "src": _vm.emojiList[_vm.emojiIndexMe]
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "antEomji right130",
    class: {
      'sendAnt-r': _vm.showEmojiHe,
    },
    attrs: {
      "src": _vm.emojiList[_vm.emojiIndexHe]
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "main-conent"
  }, [(_vm.showAnsList) ? _c('div', [_c('answerList', {
    attrs: {
      "myScore": _vm.myScore,
      "netWork": _vm.isReLink,
      "oldGame": _vm.oldGame,
      "initTime": _vm.newTime,
      "canNext": _vm.canNext,
      "otherScore": _vm.otherScore,
      "questionList": _vm.subjectList,
      "currentIndex": _vm.questionIndex,
      "eventid": '2',
      "mpcomid": '1'
    },
    on: {
      "showSelectComplete": _vm.showSelectComplete,
      "success": _vm.answerSuccess,
      "fail": _vm.answerFail,
      "ansTimeOut": _vm.ansTimeOut,
      "ansStart": _vm.ansStart,
      "nextQuestion": _vm.sendNext
    }
  })], 1) : _vm._e()])])
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-4ec69c62", esExports)
  }
}

/***/ }),

/***/ 238:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_medalChange_vue__ = __webpack_require__(240);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_19a0695e_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_medalChange_vue__ = __webpack_require__(245);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(239)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-19a0695e"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_medalChange_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_19a0695e_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_medalChange_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\public\\medalChange.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] medalChange.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-19a0695e", Component.options)
  } else {
    hotAPI.reload("data-v-19a0695e", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 239:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 240:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty__ = __webpack_require__(241);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_vuex__ = __webpack_require__(1);



var _props$data$computed$;

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//


/* harmony default export */ __webpack_exports__["a"] = (_props$data$computed$ = {
  props: ["medalInfo", "isRun"],
  data: function data() {
    return {
      needStar: 0,
      shrinkBtn: false, //光圈
      shrinkImgBtn: false, //旧图标
      magnifyBtn: false, //新图标 显示
      showLightBg: false,
      shineLightBg: false, //光圈闪现一次
      showColor: false, //彩带背景
      loopLight: false, //佛光
      showBtn: false,
      levelDownWord: false,
      isMedalDown: false,
      starTrans: true, //星星过渡效果
      fullStar: false, //旧段位满星状态
      bigMedalChange: false, //大段变化
      smallMedalUp: false,
      isMedalUp: false,
      streakUpWord: false,
      levelUpWord: false,
      eventOnce: true,
      showNewBg: false, //最强王者 段位背景图 更换
      winStreak: false, //是否连胜
      // 动画控制
      hideAllStar: false,
      // mock data
      starList: [], //星星列表
      medalName: "鱼跃龙门",
      medalNum: "Ⅰ",
      medalImg: "http://192.168.0.21:8012/vientianeTestParagraphImage/f58c2981-9aa4-4e78-90da-2991a9685973.jpg",
      newMedalName: "侮辱青铜",
      newMedalNum: "Ⅰ",
      newMedalImg: "http://192.168.0.21:8012/vientianeTestParagraphImage/d256f1c6-b1ba-4889-83f8-90379bae602f.jpg"

      // medalInfo: {
      //   continueWinNum: 0,
      //   paragraph: 3,
      //   paragraphImage:
      //     "http://192.168.4.108:8012/vientianeTestParagraphImage/f58c2981-9aa4-4e78-90da-2991a9685973.jpg",
      //   paragraphName: "不屈黄铜Ⅴ",
      //   star: 0
      // },
      // newData: {
      //   continueWinNum:0,
      //   paragraph: 2,
      //   paragraphImage:
      //     "http://192.168.4.108:8012/vientianeTestParagraphImage/d256f1c6-b1ba-4889-83f8-90379bae602f.jpg",
      //   paragraphName: "不屈黄铜Ⅰ",
      //   star: 3
      // }
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_2_vuex__["c" /* mapState */])(["IMGHEAD"])),
  watch: {
    medalInfo: function medalInfo(_new, _old) {
      var _this = this;

      // 初始化新段位名字和数字
      this.newMedalName = _new.paragraphName.slice(0, 4);

      if (_new.paragraph >= 41) {
        this.newMedalNum = "";
      } else {
        this.newMedalNum = _new.paragraphName.slice(4) || "";
      }
      // this.newMedalNum = _new.paragraphName.slice(4);
      this.newMedalImg = _new.paragraphImage;
      //区分 升级 掉级 升小段 掉小段 升大段 掉大段
      _old.star == this.getNeedStar(_old.paragraph) ? this.fullStar = true : this.fullStar = false; //判断满星
      _new.continueWinNum >= 3 && _old.paragraph < 30 ? (this.streakUpWord = true, this.showBtn = true) : null; //判断连胜
      _old.paragraphName.slice(0, 4) == _new.paragraphName.slice(0, 4) ? this.bigMedalChange = false : this.bigMedalChange = true; //是否大段变化

      _new.paragraph > _old.paragraph ? this.handleMedalUp() : null; //先升级 在升段
      _new.paragraph < _old.paragraph ? this.handleMedalDown() : null;

      if (_new.paragraph == _old.paragraph && _new.star > _old.star) {
        if (!this.streakUpWord) {
          this.levelUpWord = true;
          this.showBtn = true;
        }
        this.handleLevelUp();
        setTimeout(function () {
          _this.checkWinStreak();
        }, 500);
      }

      _new.paragraph == _old.paragraph && _new.star < _old.star ? this.handleLevelDown() : null;

      // 段位保护
      if (_new.paragraph == _old.paragraph && _new.star == _old.star) {
        this.showBtn = true;
      }
      setTimeout(function () {
        _this.needStar = _this.getNeedStar(_new.paragraph);
      }, 900);
    }
  }
}, __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default()(_props$data$computed$, "computed", __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_2_vuex__["c" /* mapState */])(["IMGHEAD"]))), __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default()(_props$data$computed$, "methods", {
  medalUp: function medalUp() {
    this.medalInfo = this.newData;
  },
  initStarList: function initStarList() {
    var list = [];
    for (var i = 0; i < this.needStar; i++) {
      var star = { static: false, act: false, down: false };
      if (i < this.medalInfo.star) {
        star.static = true;
      }
      list.push(star);
    }
    this.starList = list;
  },
  initEmptyStarList: function initEmptyStarList() {
    var list = [];
    for (var i = 0; i < this.needStar; i++) {
      var star = { static: false, act: false, down: false };
      list.push(star);
    }
    this.starList = list;
  },
  getNeedStar: function getNeedStar(paragraph) {
    if (paragraph < 6) {
      return 1;
    } else if (paragraph >= 6 && paragraph < 11) {
      return 2;
    } else if (paragraph >= 11 && paragraph < 21) {
      return 3;
    } else if (paragraph >= 21 && paragraph < 31) {
      return 4;
    } else {
      return 5;
    }
  },
  initMedalName: function initMedalName() {
    this.medalName = this.medalInfo.paragraphName.slice(0, 4) || "数据错误";
    if (this.medalInfo.paragraph >= 41) {
      this.medalNum = "";
    } else {
      this.medalNum = this.medalInfo.paragraphName.slice(4) || "";
    }
    this.medalImg = this.medalInfo.paragraphImage;
  },
  handleMedalUp: function handleMedalUp() {
    var _this2 = this;

    this.handleLevelUp(); //升级 加一星
    setTimeout(function () {
      _this2.checkWinStreak(); //连胜 在加一星
    }, 500);

    if (this.bigMedalChange) {
      //升大段
      setTimeout(function () {
        _this2.handleHideOldMedal();
      }, 600);
    } else {
      setTimeout(function () {
        _this2.isMedalUp = true;
        _this2.showBtn = true;
        _this2.streakUpWord = false;
        _this2.handleSmallMedalChange(); //小段位变化
        _this2.hanldeHideAllStar(); //隐藏星星
        setTimeout(function () {
          _this2.showLightBg = true;
        }, 150);
        if (_this2.fullStar) {
          setTimeout(function () {
            _this2.checkWinStreak(); //连胜 在加一星
          }, 500);
        }
      }, 600);
    }
  },
  handleMedalDown: function handleMedalDown() {
    var _this3 = this;

    if (this.bigMedalChange) {
      this.isMedalDown = true;
      this.showBtn = true;
      this.shrinkImgBtn = true;
      this.handleSmallMedalChange();
      setTimeout(function () {
        _this3.medalName = _this3.medalInfo.paragraphName.slice(0, 4);
      }, 150);
      setTimeout(function () {
        _this3.magnifyBtn = true;
      }, 300);
      setTimeout(function () {
        _this3.initStarList();
      }, 1000);
    } else {
      this.isMedalDown = true;
      this.showBtn = true;
      this.handleSmallMedalChange();
      this.initStarList();
      // this.initEmptyStarList();
    }
  },
  handleLevelUp: function handleLevelUp() {
    this.levelUp = true;
    for (var i = 0; i < this.starList.length; i++) {
      if (!this.starList[i].static) {
        this.starList[i].act = true;
        this.starList[i].static = true;
        if (this.eventOnce) {
          this.eventOnce = false; //执行一次
        }
        break;
      }
    }
  },
  handleSmallMedalChange: function handleSmallMedalChange() {
    var _this4 = this;

    //小段变化
    this.smallMedalUp = true;
    setTimeout(function () {
      if (_this4.medalInfo.paragraph >= 41) {
        _this4.medalNum = "";
        _this4.showNewBg = true;
      } else {
        _this4.medalNum = _this4.medalInfo.paragraphName.slice(4) || "";
      }
    }, 150);
  },
  handleLevelDown: function handleLevelDown() {
    this.levelDownWord = true;
    this.showBtn = true;
    var last = 0;
    for (var i = 0; i < this.starList.length; i++) {
      if (this.starList[i].static) {
        last = i;
      }
    }
    this.starList[last].down = true;
  },
  checkWinStreak: function checkWinStreak() {
    if (this.medalInfo.continueWinNum >= 3 && this.medalInfo.paragraph < 31) {
      this.winStreak = true;
      this.handleLevelUp();
    }
  },
  hanldeHideAllStar: function hanldeHideAllStar() {
    var _this5 = this;

    this.hideAllStar = true;
    setTimeout(function () {
      _this5.initEmptyStarList();
      _this5.hideAllStar = false;
    }, 300);
  },
  handleHideOldMedal: function handleHideOldMedal() {
    var _this6 = this;

    // 旧段位图标隐藏
    // this.shrinkBtn = true;
    // setTimeout(() => {

    // }, 300);
    this.hanldeHideAllStar();
    setTimeout(function () {
      _this6.shrinkImgBtn = true;
      _this6.handleSmallMedalChange();
      setTimeout(function () {
        _this6.medalName = _this6.medalInfo.paragraphName.slice(0, 4);
        if (_this6.medalInfo.paragraph > 41) {
          _this6.showNewBg = true;
        }
      }, 150);
      setTimeout(function () {
        _this6.handleShowNewMedal();
      }, 300);
    }, 290);
  },
  handleShowNewMedal: function handleShowNewMedal() {
    var _this7 = this;

    //新段位图标出现
    this.streakUpWord = false; //连胜标题隐藏
    this.levelUpWord = false; //星级标题隐藏
    this.isMedalUp = true; //段位标题出现
    this.showBtn = true;
    this.magnifyBtn = true;
    setTimeout(function () {
      _this7.shineLightBg = true; //光圈闪现
    }, 200);
    setTimeout(function () {
      _this7.shineLightBg = false;
      _this7.showLightBg = true;
      _this7.loopLight = true;
      _this7.showColor = true;
    }, 950);
    setTimeout(function () {
      _this7.initEmptyStarList();
      // 判断连胜
      if (_this7.fullStar) {
        _this7.checkWinStreak();
      }
    }, 420);
  },
  closeEvent: function closeEvent() {
    this.$emit("close");
  }
}), __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_defineProperty___default()(_props$data$computed$, "mounted", function mounted() {
  try {
    this.needStar = this.getNeedStar(this.medalInfo.paragraph); //2018-11-16
    this.initStarList(); //执行一次初始化 旧的 段位星级列表
    this.initMedalName(); //初始化 段位名字和数字
  } catch (err) {
    console.log(err);
    console.log('medalchange mounted error');
  }
}), _props$data$computed$);

/***/ }),

/***/ 245:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "wrap medal-bg"
  }, [(true) ? _c('div', {
    staticClass: "medal-box"
  }, [_c('div', {
    staticClass: " m-word medal-word",
    class: {
      'show-medal-title': _vm.levelUpWord && _vm.medalInfo.paragraph >= 41
    }
  }, [_vm._v("星级提升")]), _vm._v(" "), _c('div', {
    staticClass: " m-word-blue",
    class: {
      'show-medal-title': _vm.levelUpWord && _vm.medalInfo.paragraph < 41
    }
  }, [_vm._v("星级提升")]), _vm._v(" "), _c('div', {
    staticClass: "m-word medal-word",
    class: {
      'show-medal-title': _vm.isMedalUp
    }
  }, [_vm._v("段位提升")]), _vm._v(" "), _c('div', {
    staticClass: " m-word-blue",
    class: {
      'show-medal-title': _vm.streakUpWord
    }
  }, [_vm._v("连胜奖励：更多星数")]), _vm._v(" "), _c('div', {
    staticClass: " m-word-black",
    class: {
      'show-medal-title': _vm.levelDownWord
    }
  }, [_vm._v("星级降低")]), _vm._v(" "), _c('div', {
    staticClass: " m-word-black medal-word",
    class: {
      'show-medal-title': _vm.isMedalDown
    }
  }, [_vm._v("段位降低")]), _vm._v(" "), _c('div', {
    staticClass: "m-color",
    class: {
      'color-down': _vm.showColor
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/color-div.png",
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-light-bg",
    class: {
      'show': _vm.showLightBg
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/light-bg.png",
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-light-bg left60",
    class: {
      'show': _vm.showLightBg && _vm.medalInfo.paragraph >= 41
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/topLightBg.png",
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "m-light-cycle",
    class: {
      'short-big': _vm.shineLightBg, 'loop-big': _vm.loopLight
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/light-cycle.png",
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "m-top-star",
    class: {
      showTopStar: _vm.medalInfo.paragraph >= 41
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/star.png"
    }
  }), _vm._v("\n      x\n      "), _c('div', {
    staticClass: "star-num"
  }, [_vm._v(_vm._s(_vm.medalInfo.star))])]), _vm._v(" "), (_vm.medalInfo.paragraph < 41) ? _c('div', {
    staticClass: "m-star-list",
    class: {
      'star-hide-1': _vm.hideAllStar && _vm.needStar == 1,
        'star-hide-2': _vm.hideAllStar && _vm.needStar == 2,
        'star-hide-3': _vm.hideAllStar && _vm.needStar == 3,
        'star-hide-4': _vm.hideAllStar && _vm.needStar == 4,
        'star-hide-5': _vm.hideAllStar && _vm.needStar == 5,
        'star-1': _vm.needStar == 1,
        'star-2': _vm.needStar == 2,
        'star-3': _vm.needStar == 3, 'star-4': _vm.needStar == 4,
        'star-5': _vm.needStar == 5
    }
  }, _vm._l((_vm.starList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "u-star"
    }, [_c('img', {
      staticClass: "u-star-act",
      class: {
        tansStar: item.trans,
          showStaticStar: item.static,
          showStar: item.act,
          'downStar-0': item.down && ((_vm.needStar == 1 && index == 0) || (_vm.needStar == 3 && index == 1) || (_vm.needStar == 5 && index == 2)),
          'downStar-l-5': item.down && ((_vm.needStar == 2 && index == 0) || (_vm.needStar == 4 && index == 1)),
          'downStar-r-5': item.down && ((_vm.needStar == 2 && index == 1) || (_vm.needStar == 4 && index == 2)),
          'downStar-l-10': item.down && ((_vm.needStar == 3 && index == 0) || (_vm.needStar == 4 && index == 1)),
          'downStar-r-10': item.down && ((_vm.needStar == 3 && index == 2) || (_vm.needStar == 4 && index == 3)),
          'downStar-l-15': item.down && ((_vm.needStar == 4 && index == 0) || (_vm.needStar == 5 && index == 0)),
          'downStar-r-15': item.down && ((_vm.needStar == 4 && index == 3) || (_vm.needStar == 5 && index == 4))
      },
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/star.png"
      }
    })])
  })) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "m-medal"
  }, [_c('div', {
    staticClass: "u-img",
    class: {
      shrinkImg: _vm.shrinkImgBtn
    }
  }, [_c('img', {
    attrs: {
      "src": _vm.IMGHEAD + _vm.medalImg
    }
  })]), _vm._v(" "), (_vm.magnifyBtn) ? _c('div', {
    staticClass: "u-img",
    class: {
      magnify: _vm.magnifyBtn
    }
  }, [_c('img', {
    attrs: {
      "src": _vm.IMGHEAD + _vm.newMedalImg
    }
  })]) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "u-medal-level",
    class: {
      'small-medal-change': _vm.smallMedalUp
    }
  }, [(_vm.showNewBg || _vm.medalInfo.paragraph >= 41) ? _c('img', {
    attrs: {
      "src": "/static/rank/topMedalBg.png"
    }
  }) : _c('img', {
    attrs: {
      "src": "/static/rank/medal-bg.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-medal-name"
  }, [_vm._v("\n            " + _vm._s(_vm.medalName) + _vm._s(_vm.medalNum) + " \n          ")])]), _vm._v(" "), _c('img', {
    staticClass: "u-btn",
    class: {
      'show': _vm.showBtn
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/close-btn-new.png",
      "alt": "",
      "eventid": '0'
    },
    on: {
      "click": _vm.closeEvent
    }
  })]) : _vm._e()])
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-19a0695e", esExports)
  }
}

/***/ }),

/***/ 246:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_imgPreLoad_vue__ = __webpack_require__(248);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_6e30297e_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_imgPreLoad_vue__ = __webpack_require__(249);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(247)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-6e30297e"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_imgPreLoad_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_6e30297e_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_imgPreLoad_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\utils\\imgPreLoad.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] imgPreLoad.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-6e30297e", Component.options)
  } else {
    hotAPI.reload("data-v-6e30297e", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 247:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 248:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
//
//
//
//
//
//

/* harmony default export */ __webpack_exports__["a"] = ({
  data: function data() {
    return {
      imgList: [
      // ant success
      "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/light.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/ball.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/ball_light.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/red_cat.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/blue_cat.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/banner.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/sheng.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/li.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/star.png",
      //ant fail
      "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/ball.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/red_cat.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/blue_cat.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/banner.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/shi.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/bai.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/snow.png",
      //ant balance
      "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antBalance/ball.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antBalance/red_cat.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antBalance/blue_cat.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antBalance/banner.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antBalance/ping.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antBalance/ju.png",
      //public
      "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/arrow.png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/balanceTop(new).png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/failTop(new).png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/gift-bg(new).png", "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/successTop(new).png"]
    };
  },
  mounted: function mounted() {
    // this.imgList.forEach(item => {
    //   wx.getImageInfo({
    //     src: item,
    //   });
    // });
  }
});

/***/ }),

/***/ 249:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "empty"
  }, _vm._l((_vm.imgList), function(item, index) {
    return _c('img', {
      key: index,
      attrs: {
        "src": item
      }
    })
  }))
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-6e30297e", esExports)
  }
}

/***/ }),

/***/ 250:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_success_vue__ = __webpack_require__(252);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_03d1344f_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_success_vue__ = __webpack_require__(253);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(251)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-03d1344f"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_success_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_03d1344f_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_success_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\public\\animateCss\\success.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] success.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-03d1344f", Component.options)
  } else {
    hotAPI.reload("data-v-03d1344f", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 251:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 252:
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

/* harmony default export */ __webpack_exports__["a"] = ({
  props: ["gift"],
  data: function data() {
    return {
      ant1: false, //大球
      ant2: false, //红猫
      ant3: false, //蓝猫
      ant4: false, //文字
      ant5: false, //横幅
      ant6: false, //星星
      ant7: false, //炫光
      ant8: false, //奖励
      ant9: false, //结算弹窗
      shengIn: false,
      starBgList: [0, 0, 0, 0, 0],
      starActList: [],
      isShowClear: false,
      oldCombat: 0,
      fightType: {
        2: "商+",
        3: "文+",
        4: "理+",
        5: "艺+",
        6: "体+",
        7: "综+"
      }
      // gift: {
      //   energe: 0,
      //   combatList: [{ combat: 1, parentType: 3 }],
      //   rightNum: 5,
      //   combat: 197
      // },

    };
  },


  methods: {
    initData: function initData() {
      this.starActList = [];
      this.oldCombat = this.gift.combat;
      for (var i = 0; i < this.gift.rightNum; i++) {
        this.starActList.push(0);
      }
      if (this.gift.combatList.length > 0) {
        this.isShowClear = true;
        for (var _i = 0; _i < this.gift.combatList.length; _i++) {
          this.oldCombat = this.oldCombat - parseInt(this.gift.combatList[_i].combat);
        }
      } else {
        this.isShowClear = false;
      }
    },
    close: function close() {
      this.$emit("close");
      // this.shengIn = !this.shengIn;
    }
  },
  mounted: function mounted() {
    var _this = this;

    this.initData();
    setTimeout(function () {
      _this.ant1 = true;
      setTimeout(function () {
        _this.ant2 = true; //字体跳入
        setTimeout(function () {
          _this.ant3 = true; //面板进入
          setTimeout(function () {
            _this.ant4 = true; //显示星星
            setTimeout(function () {
              _this.ant5 = true;
              setTimeout(function () {
                _this.ant6 = true;
              }, 200);
            }, 200 * _this.gift.rightNum);
          }, 200);
        }, 200);
      }, 200);
    }, 500);
  }
});

/***/ }),

/***/ 253:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-ant-box",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.close
    }
  }, [_c('img', {
    staticClass: "u-light",
    class: {
      'light-rotate': _vm.ant5
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/light.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "m-clear-box",
    class: {
      'show-clear-box': _vm.ant3
    }
  }, [_c('img', {
    staticClass: "m-box-bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/gift-bg(new).png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "c-content"
  }, [_c('div', {
    staticClass: "c-head"
  }, [_vm._v("答题评分")]), _vm._v(" "), _c('div', {
    staticClass: "c-star-list"
  }, [_c('div', {
    staticClass: "c-bg-list"
  }, _vm._l((_vm.starBgList), function(item, index) {
    return _c('img', {
      key: index,
      staticClass: "c-star-bg",
      attrs: {
        "src": "/static/star-black.png"
      }
    })
  })), _vm._v(" "), _c('div', {
    staticClass: "c-act-list"
  }, _vm._l((_vm.starActList), function(item, index) {
    return _c('img', {
      key: index,
      staticClass: "c-star-act",
      class: {
        'show-star': _vm.ant4
      },
      attrs: {
        "src": "/static/star.png"
      }
    })
  }))]), _vm._v(" "), _vm._m(0), _vm._v(" "), _c('div', {
    staticClass: "c-fight"
  }, [_c('div', {
    staticClass: "f-old"
  }, [_vm._v(_vm._s(_vm.oldCombat))]), _vm._v(" "), _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/arrow.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "f-new"
  }, [_vm._v(_vm._s(_vm.gift.combat))])]), _vm._v(" "), _c('div', {
    staticClass: "c-num-box",
    class: {
      'align-center': _vm.gift.combatList.length == 1
    }
  }, _vm._l((_vm.gift.combatList), function(item, index) {
    return _c('div', {
      key: index
    }, [_vm._v(_vm._s(_vm.fightType[item.parentType]) + _vm._s(item.combat))])
  })), _vm._v(" "), _vm._m(1), _vm._v(" "), _c('div', {
    staticClass: "c-gift"
  }, [_c('img', {
    attrs: {
      "src": "/static/entry/energe-normal.png"
    }
  }), _vm._v("+" + _vm._s(_vm.gift.energe || 0) + "\n      ")])])]), _vm._v(" "), _c('img', {
    staticClass: "m-box-top",
    class: {
      show: _vm.ant1
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/banner.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-sheng-box",
    class: {
      'word-in': _vm.ant2
    }
  }, [_c('img', {
    staticClass: "u-sheng",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/sheng.png",
      "alt": ""
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-li-box",
    class: {
      'word-in': _vm.ant2
    }
  }, [_c('img', {
    staticClass: "u-li",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/li.png",
      "alt": ""
    }
  })]), _vm._v(" "), _c('img', {
    staticClass: "star-mask",
    class: {
      show: _vm.ant5
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antSuccess/star-mask.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "tip",
    class: {
      show: _vm.ant6
    }
  }, [_vm._v("点击任意键继续")])])
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "c-line"
  }, [_c('div', [_vm._v("战力提升")])])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "c-line"
  }, [_c('div', [_vm._v("获得奖励")])])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-03d1344f", esExports)
  }
}

/***/ }),

/***/ 254:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_fail_vue__ = __webpack_require__(256);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_db8087fc_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_fail_vue__ = __webpack_require__(257);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(255)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-db8087fc"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_fail_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_db8087fc_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_fail_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\public\\animateCss\\fail.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] fail.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-db8087fc", Component.options)
  } else {
    hotAPI.reload("data-v-db8087fc", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 255:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 256:
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

/* harmony default export */ __webpack_exports__["a"] = ({
  props: ["gift"],
  data: function data() {
    return {
      ant1: false, //大球
      ant2: false, //红猫
      ant3: false, //蓝猫
      ant4: false, //文字
      ant5: false, //横幅
      ant6: false, //星星
      ant7: false, //奖励
      ant8: false, //结算
      starBgList: [0, 0, 0, 0, 0],
      starActList: [],
      isShowClear: false,
      oldCombat: 0,
      fightType: {
        2: "商+",
        3: "文+",
        4: "理+",
        5: "艺+",
        6: "体+",
        7: "综+"
      }
      // gift: {
      //   energe: 0,
      //   combatList: [{ combat: 1, parentType: 3 }],
      //   rightNum: 5,
      //   combat: 197
      // }
    };
  },

  methods: {
    initData: function initData() {
      this.starActList = [];
      this.oldCombat = this.gift.combat;
      for (var i = 0; i < this.gift.rightNum; i++) {
        this.starActList.push(0);
      }
      if (this.gift.combatList.length > 0) {
        this.isShowClear = true;
        for (var _i = 0; _i < this.gift.combatList.length; _i++) {
          this.oldCombat = this.oldCombat - parseInt(this.gift.combatList[_i].combat);
        }
      } else {
        this.isShowClear = false;
      }
    },
    close: function close() {
      this.$emit("close");
    }
  },
  mounted: function mounted() {
    var _this = this;

    this.initData();
    setTimeout(function () {
      _this.ant1 = true;
      setTimeout(function () {
        _this.ant2 = true;
        setTimeout(function () {
          _this.ant3 = true;
          setTimeout(function () {
            _this.ant4 = true;
            setTimeout(function () {
              _this.ant5 = true;
              setTimeout(function () {
                _this.ant6 = true;
              }, 200);
            }, 200 * _this.gift.rightNum);
          }, 200);
        }, 200);
      }, 200);
    }, 500);
  }
});

/***/ }),

/***/ 257:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-ant-box",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.close
    }
  }, [_c('div', {
    staticClass: "m-clear-box",
    class: {
      'sliderIn': _vm.ant3
    }
  }, [_c('img', {
    staticClass: "m-box-bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/gift-bg(new).png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "c-content"
  }, [_c('div', {
    staticClass: "c-head"
  }, [_vm._v("答题评分")]), _vm._v(" "), _c('div', {
    staticClass: "c-star-list"
  }, [_c('div', {
    staticClass: "c-bg-list"
  }, _vm._l((_vm.starBgList), function(item, index) {
    return _c('img', {
      key: index,
      staticClass: "c-star-bg",
      attrs: {
        "src": "/static/star-black.png"
      }
    })
  })), _vm._v(" "), _c('div', {
    staticClass: "c-act-list"
  }, _vm._l((_vm.starActList), function(item, index) {
    return _c('img', {
      key: index,
      staticClass: "c-star-act",
      class: {
        'show-star': _vm.ant4
      },
      attrs: {
        "src": "/static/star.png"
      }
    })
  }))]), _vm._v(" "), _vm._m(0), _vm._v(" "), _c('div', {
    staticClass: "c-fight"
  }, [_c('div', {
    staticClass: "f-old"
  }, [_vm._v(_vm._s(_vm.oldCombat))]), _vm._v(" "), _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/arrow.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "f-new"
  }, [_vm._v(_vm._s(_vm.gift.combat))])]), _vm._v(" "), _c('div', {
    staticClass: "c-num-box",
    class: {
      'align-center': _vm.gift.combatList.length == 1
    }
  }, _vm._l((_vm.gift.combatList), function(item, index) {
    return _c('div', {
      key: index
    }, [_vm._v(_vm._s(_vm.fightType[item.parentType]) + _vm._s(item.combat))])
  })), _vm._v(" "), _vm._m(1), _vm._v(" "), _c('div', {
    staticClass: "c-gift"
  }, [_c('img', {
    attrs: {
      "src": "/static/entry/energe-normal.png"
    }
  }), _vm._v("+" + _vm._s(_vm.gift.energe || 0) + "\n      ")])])]), _vm._v(" "), _c('img', {
    staticClass: "m-box-top",
    class: {
      show: _vm.ant1
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/banner.png"
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "u-sheng",
    class: {
      'u-word-in': _vm.ant2
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/shi.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "u-li",
    class: {
      'u-word-in': _vm.ant2
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/bai.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "u-star",
    class: {
      'u-star-in': _vm.ant5
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antFail/snow-mask.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "tip",
    class: {
      show: _vm.ant6
    }
  }, [_vm._v("点击任意键继续")])])
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "c-line"
  }, [_c('div', [_vm._v("战力提升")])])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "c-line"
  }, [_c('div', [_vm._v("获得奖励")])])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-db8087fc", esExports)
  }
}

/***/ }),

/***/ 258:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_balance_vue__ = __webpack_require__(260);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_318f4ea8_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_balance_vue__ = __webpack_require__(261);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(259)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-318f4ea8"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_balance_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_318f4ea8_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_balance_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\components\\public\\animateCss\\balance.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] balance.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-318f4ea8", Component.options)
  } else {
    hotAPI.reload("data-v-318f4ea8", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 259:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 260:
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

/* harmony default export */ __webpack_exports__["a"] = ({
  props: ["gift"],
  data: function data() {
    return {
      ant1: false, //大球
      ant2: false, //红猫
      ant3: false, //蓝猫
      ant4: false, //文字
      ant5: false, //横幅
      ant6: false, //星星
      ant7: false, //奖励
      ant8: false, //结算
      starBgList: [0, 0, 0, 0, 0],
      starActList: [],
      isShowClear: false,
      oldCombat: 0,
      fightType: {
        2: "商+",
        3: "文+",
        4: "理+",
        5: "艺+",
        6: "体+",
        7: "综+"
      }
      // gift: {
      //   energe: 0,
      //   combatList: [{ combat: 1, parentType: 3 }],
      //   rightNum: 5,
      //   combat: 197
      // }
    };
  },

  methods: {
    initData: function initData() {
      this.starActList = [];
      this.oldCombat = this.gift.combat;
      for (var i = 0; i < this.gift.rightNum; i++) {
        this.starActList.push(0);
      }
      if (this.gift.combatList.length > 0) {
        this.isShowClear = true;
        for (var _i = 0; _i < this.gift.combatList.length; _i++) {
          this.oldCombat = this.oldCombat - parseInt(this.gift.combatList[_i].combat);
        }
      } else {
        this.isShowClear = false;
      }
    },
    close: function close() {
      this.$emit("close");
    }
  },
  mounted: function mounted() {
    var _this = this;

    this.initData();
    setTimeout(function () {
      _this.ant1 = true;
      setTimeout(function () {
        _this.ant2 = true;
        setTimeout(function () {
          _this.ant3 = true;
          setTimeout(function () {
            _this.ant4 = true;
            setTimeout(function () {
              _this.ant5 = true;
            }, 200 * _this.gift.rightNum);
          }, 200);
        }, 200);
      }, 200);
    }, 500);
  }
});

/***/ }),

/***/ 261:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-ant-box",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.close
    }
  }, [_c('div', {
    staticClass: "m-clear-box",
    class: {
      'sliderIn': _vm.ant3
    }
  }, [_c('img', {
    staticClass: "m-box-bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/gift-bg(new).png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "c-content"
  }, [_c('div', {
    staticClass: "c-head"
  }, [_vm._v("答题评分")]), _vm._v(" "), _c('div', {
    staticClass: "c-star-list"
  }, [_c('div', {
    staticClass: "c-bg-list"
  }, _vm._l((_vm.starBgList), function(item, index) {
    return _c('img', {
      key: index,
      staticClass: "c-star-bg",
      attrs: {
        "src": "/static/star-black.png"
      }
    })
  })), _vm._v(" "), _c('div', {
    staticClass: "c-act-list"
  }, _vm._l((_vm.starActList), function(item, index) {
    return _c('img', {
      key: index,
      staticClass: "c-star-act",
      class: {
        'show-star': _vm.ant4
      },
      attrs: {
        "src": "/static/star.png"
      }
    })
  }))]), _vm._v(" "), _vm._m(0), _vm._v(" "), _c('div', {
    staticClass: "c-fight"
  }, [_c('div', {
    staticClass: "f-old"
  }, [_vm._v(_vm._s(_vm.oldCombat))]), _vm._v(" "), _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/arrow.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "f-new"
  }, [_vm._v(_vm._s(_vm.gift.combat))])]), _vm._v(" "), _c('div', {
    staticClass: "c-num-box",
    class: {
      'align-center': _vm.gift.combatList.length == 1
    }
  }, _vm._l((_vm.gift.combatList), function(item, index) {
    return _c('div', {
      key: index
    }, [_vm._v(_vm._s(_vm.fightType[item.parentType]) + _vm._s(item.combat))])
  })), _vm._v(" "), _vm._m(1), _vm._v(" "), _c('div', {
    staticClass: "c-gift"
  }, [_c('img', {
    attrs: {
      "src": "/static/entry/energe-normal.png"
    }
  }), _vm._v("+" + _vm._s(_vm.gift.energe || 0) + "\n      ")])])]), _vm._v(" "), _c('img', {
    staticClass: "m-box-top",
    class: {
      show: _vm.ant1
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antBalance/banner.png"
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "u-sheng",
    class: {
      'u-word-in': _vm.ant2
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antBalance/ping.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "u-li",
    class: {
      'u-word-in': _vm.ant2
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/antBalance/ju.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "tip",
    class: {
      show: _vm.ant5
    }
  }, [_vm._v("点击任意键继续")])])
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "c-line"
  }, [_c('div', [_vm._v("战力提升")])])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "c-line"
  }, [_c('div', [_vm._v("获得奖励")])])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-318f4ea8", esExports)
  }
}

/***/ }),

/***/ 262:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "wrap sky-bg-3 "
  }, [(_vm.showWaitBox) ? _c('waitBox', {
    attrs: {
      "isMatched": _vm.isMatched,
      "matchInfo": _vm.userList,
      "eventid": '0',
      "mpcomid": '0'
    },
    on: {
      "cancleMatch": _vm.matchCancle,
      "pageReady": _vm.HandleWaitBoxReady
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showQuestion) ? _c('questionBox', {
    attrs: {
      "netWork": _vm.netWork,
      "emojiIndexMe": _vm.emojiIndexMe,
      "emojiIndexHe": _vm.emojiIndexHe,
      "isGameEnd": _vm.isGameEnd,
      "oldGame": _vm.oldGame,
      "relinkIndex": _vm.relinkIndex,
      "otherAns": _vm.otherAns,
      "gameOver": _vm.isGameOver,
      "userList": _vm.userList,
      "subjectList": _vm.subjectList,
      "oRun": _vm.oRun,
      "eventid": '1',
      "mpcomid": '1'
    },
    on: {
      "back": _vm.back,
      "ansRight": _vm.rightHandle,
      "ansFail": _vm.failHandle,
      "end": _vm.end,
      "nextQuestion": _vm.nextQuestion,
      "sendEmoji": _vm.handleSendEmoji
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showAntSuccess) ? _c('success', {
    attrs: {
      "gift": _vm.gameGift,
      "eventid": '2',
      "mpcomid": '2'
    },
    on: {
      "close": _vm.getMedal
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showAntFail) ? _c('fail', {
    attrs: {
      "gift": _vm.gameGift,
      "eventid": '3',
      "mpcomid": '3'
    },
    on: {
      "close": _vm.getMedal
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showAntBalance) ? _c('balance', {
    attrs: {
      "gift": _vm.gameGift,
      "eventid": '4',
      "mpcomid": '4'
    },
    on: {
      "close": _vm.getMedal
    }
  }) : _vm._e(), _vm._v(" "), (_vm.showStarBox) ? _c('div', {
    staticClass: "result-box"
  }, [_c('medalChange', {
    attrs: {
      "medalInfo": _vm.medalInfo,
      "isRun": _vm.oRun,
      "eventid": '5',
      "mpcomid": '5'
    },
    on: {
      "close": function($event) {
        _vm.closeStarBox('结算')
      },
      "oRun": function($event) {
        _vm.closeStarBox('逃跑')
      }
    }
  })], 1) : _vm._e(), _vm._v(" "), (_vm.getImg) ? _c('imgPreLoad', {
    attrs: {
      "mpcomid": '6'
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
     require("vue-hot-reload-api").rerender("data-v-9c0cc528", esExports)
  }
}

/***/ })

},[339]);
//# sourceMappingURL=main.js.map