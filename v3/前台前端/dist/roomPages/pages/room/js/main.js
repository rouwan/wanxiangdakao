global.webpackJsonp([11],{

/***/ 275:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_room_vue__ = __webpack_require__(277);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_3366eca3_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_room_vue__ = __webpack_require__(293);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(276)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-3366eca3"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_room_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_3366eca3_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_room_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\roomPages\\pages\\room.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] room.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-3366eca3", Component.options)
  } else {
    hotAPI.reload("data-v-3366eca3", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 276:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 277:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator__ = __webpack_require__(18);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator__ = __webpack_require__(19);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_get_iterator__ = __webpack_require__(36);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_get_iterator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_get_iterator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__components_quesHead_vue__ = __webpack_require__(75);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__components_quesTitle_vue__ = __webpack_require__(76);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__components_quesSelect_vue__ = __webpack_require__(77);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__components_quesResult_vue__ = __webpack_require__(78);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__components_emojiBox_vue__ = __webpack_require__(79);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__pages_game_components_timeCount_vue__ = __webpack_require__(37);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__utils_index__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__config_dataFile__ = __webpack_require__(13);





//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
  onShareAppMessage: function onShareAppMessage(res) {
    var index = Object(__WEBPACK_IMPORTED_MODULE_12__utils_index__["c" /* getRondom */])(0, 2);
    console.log(this._roomId);
    return {
      title: this.appInfo.nickname + __WEBPACK_IMPORTED_MODULE_13__config_dataFile__["g" /* shareInfoForRoom */][index].title,
      imageUrl: __WEBPACK_IMPORTED_MODULE_13__config_dataFile__["g" /* shareInfoForRoom */][index].imgUrl,
      path: "/pages/entry/index?roomId=" + this._roomId + "&userId=" + this.appInfo.userId
    };
  },
  components: {
    "ques-head": __WEBPACK_IMPORTED_MODULE_6__components_quesHead_vue__["a" /* default */],
    "time-count": __WEBPACK_IMPORTED_MODULE_11__pages_game_components_timeCount_vue__["a" /* default */],
    "ques-title": __WEBPACK_IMPORTED_MODULE_7__components_quesTitle_vue__["a" /* default */],
    "ques-select": __WEBPACK_IMPORTED_MODULE_8__components_quesSelect_vue__["a" /* default */],
    "ques-result": __WEBPACK_IMPORTED_MODULE_9__components_quesResult_vue__["a" /* default */],
    "emoji-box": __WEBPACK_IMPORTED_MODULE_10__components_emojiBox_vue__["a" /* default */]
  },
  data: function data() {
    return {
      // 生命周期
      hideForRoute: false,
      // 基础数据状态
      isHideLife: false,
      socketType: 1,
      socketTask: {},
      isFirst: true,
      _roomId: 0,
      gameStatus: "wait",
      socketStatus: "off",
      roomData: null,
      userRole: "民众",
      placeList: [0, 0, 0, 0],
      myInfo: {},
      userList: [],
      seatList: [{}, {}, {}, {}], //用户座位列表
      resultData: [],
      userNum: 0,
      throttle: __WEBPACK_IMPORTED_MODULE_12__utils_index__["f" /* throttle */],
      // 功能变量
      heart: null, //心跳timer
      heartTimer: null,
      delayTime: 0,
      readyType: false, //
      allReady: false,
      autoStartTimer: null,
      autoStartTime: 10,
      // 组件状态
      showQuesModules: false,
      titleOff: true,
      selectOff: false,
      initTime: 10,
      initTimeCount: false,
      startTimeCount: false,
      stopTimeCount: false,
      showTimerMask: false,
      showFullMask: false,
      showTitle: true,
      showSelect: false, //
      showResult: false, //展示结果
      emojiBtn: false,
      // 题目相关
      currentIndex: 0,
      list: [],
      levelList: [],
      idiomList: [],
      poetryList: [],
      imgLevelList: [],

      //答题相关
      ansList: [],
      showBothAns: false,
      query: {},
      emoji: __WEBPACK_IMPORTED_MODULE_13__config_dataFile__["a" /* emoji */]
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_5_vuex__["c" /* mapState */])({
    roomStatus: function roomStatus(state) {
      return state.fStore.roomStatus;
    },
    appInfo: function appInfo(state) {
      return state.appInfo;
    },
    EVN: function EVN(state) {
      return state.EVN;
    },
    IMGHEAD: function IMGHEAD(state) {
      return state.IMGHEAD;
    },
    deviceInfo: function deviceInfo(state) {
      return state.deviceInfo;
    },
    connected: function connected(state) {
      return state.socketStore.connected;
    },
    messageBus: function messageBus(state) {
      return state.socketStore.messageBus;
    },
    messageListeners: function messageListeners(state) {
      return state.socketStore.messageListeners;
    }
  })),
  watch: {
    // messageListeners(list){
    //   console.log("messageListeners",list);
    // },
    userList: function userList(list) {
      var _this = this;

      var _allReady = true;
      var num = 0;
      this.appInfo.userId == this.roomData.room.roomMaster ? this.userRole = '房主' : this.userRole = '民众';

      list.forEach(function (user, index) {
        user.basicInfo.userId == _this.appInfo.userId ? _this.myInfo = user : null;
        user.basicInfo.userId == _this.roomData.room.roomMaster ? user.isMaster = true : null;
        user.isReady == 0 ? _allReady = false : null;
        user.status == 1 ? num++ : null;
        if (_this.seatList[index].showEmoji) {
          _this.seatList[index].showEmoji = false;
        }
      });
      this.userNum = num;
      list.length > 1 ? this.allReady = _allReady : null;
      console.log("userNum--------------------------------", num);
    },
    allReady: function allReady(value) {
      if (value && this.userList.length == 4 && this.gameStatus != "playing") {
        this.startAutoStartGame();
        console.log("所有人都准备好了，开始倒计时.倒计时结束，自动开始");
      } else {
        this.stopAutoStartTimer();
      }
    },
    autoStartTime: function autoStartTime(val) {
      if (val <= 0) {
        this.stopAutoStartTimer();
        if (this.userRole == "房主") {
          this.handleStartGame();
        }
      }
    },
    delayTime: function delayTime(time) {
      if (time >= 4) {
        this.netWorkWeek();
      }
    }
  },
  methods: __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_5_vuex__["b" /* mapMutations */])({
    setAppInfo: "setAppInfo",
    setGameRecord: "setGameRecord",
    connect: "socketStore/connect",
    addMessageListener: "socketStore/addMessageListener",
    removeMessageListener: "socketStore/removeMessageListener",
    send: "socketStore/send",
    close: "socketStore/close",
    setPageType: "socketStore/setPageType"
  }), {
    createdRoom: function createdRoom() {
      this.send("{'type':13,'data':1,'modeType':1}", "创建房间");
    },
    netWorkWeek: function netWorkWeek() {
      this.clearHeart();
      if (this.connected) {
        this.closeSocket();
      }
      setTimeout(function () {
        wx.showModal({ title: "系统提示", content: "网络较差,请重新登录", showCancel: false, success: function success(res) {
            if (res.confirm) {
              wx.reLaunch({ url: '/pages/entry/index' });
            }
          } });
      }, 1000);
    },
    startAutoStartGame: function startAutoStartGame() {
      var _this2 = this;

      this.showTimerMask = true;
      this.autoStartTimer = setInterval(function () {
        _this2.autoStartTime--;
      }, 1000);
    },
    stopAutoStartTimer: function stopAutoStartTimer() {
      clearInterval(this.autoStartTimer);
      this.autoStartTime = 10;
      this.showTimerMask = false;
    },


    // socket 处理函数
    initRoomData: function initRoomData(data) {
      var _this3 = this;

      this.roomData = data.Result;
      this.userList = this.roomData.room.userList;
      this._roomId = this.roomData.room.roomId;
      if (this.roomData.room.roomStatus == 0 && this.roomData.game.subjectList && this.roomData.game.subjectList.length > 0) {
        this.getList();
        this.currentIndex = 0;
        this.showQuesModules = true; //显示答题界面
        this.currentIndex = 0;
        setTimeout(function () {
          _this3.send("{'type':6,'data':'" + _this3.roomData.game.gameId + "'}"); //开始游戏
          _this3.startGame();
        }, 2500);
      } else {
        this.gameStatus = 'wait';
      }
      console.log("type1:", data);
    },
    handleRoute: function handleRoute() {
      this.hideForRoute = true;
    },
    getList: function getList() {
      this.idiomList = [];
      this.levelList = [];
      this.poetryList = [];
      this.imgLevelList = [];
      this.list = this.roomData.game.subjectList;
      // this.list = JSON.parse(JSON.stringify(this.roomData.game.subjectList)); //深拷贝 问题数据
      for (var i = 0; i < this.list.length; i++) {
        // 判断成语题 生成题目列表
        if (this.list[i].modeType == 5) {
          var list = this.list[i].title.replace("_", "?").split("");
          this.idiomList.push(list);
        } else {
          this.idiomList.push(0);
        }

        if (this.list[i].modeType == 7 || this.list[i].modeType == 8 || this.list[i].modeType == 9 || this.list[i].modeType == 10) {
          var arr = this.list[i].title.replace('_', "?").split('');
          this.poetryList.push(arr);
        } else {
          this.poetryList.push(0);
        }
      }
      this.getRatioList(this.list);
    },
    getRatioList: function getRatioList(list) {
      var _iteratorNormalCompletion = true;
      var _didIteratorError = false;
      var _iteratorError = undefined;

      try {
        for (var _iterator = __WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_get_iterator___default()(list), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
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
    handleMessage: function handleMessage(message) {
      var _this4 = this;

      //被动接受消息
      //区分数据类型 处理不同业务
      // let data = JSON.parse(res.data);
      var eventHandleMap = {
        socketType1: function socketType1() {
          return _this4.initRoomData(message);
        }, //type1 初始化、重置房间信息
        socketType2: function socketType2() {
          return _this4.checkOldGame(message);
        },
        // socketType3: () => this.startGame(data), //type3 游戏开始
        socketType4: function socketType4() {
          return _this4.answerResponse(message);
        }, //type4 答题信息
        socketType7: function socketType7() {
          return _this4.gameEnd(message);
        }, //type7 游戏结束
        socketType6: function socketType6() {
          return _this4.handleNextQuestion(message);
        },
        socketType8: function socketType8() {
          return _this4.handleTick(message);
        }, //type8 被房主提出房间
        socketType15: function socketType15() {
          return _this4.handleShowEmoji(message);
        }, //type15 展示表情
        socketType0: function socketType0() {
          return _this4.stopHeartTimer();
        } //响应本轮心跳

      };
      //执行对应的方法
      if (typeof eventHandleMap["socketType" + message.Type] == "function") {
        eventHandleMap["socketType" + message.Type]();
      }
    },
    closeSocket: function closeSocket() {
      this.close();
    },
    handleSendEmoji: function handleSendEmoji(index) {
      this.send("{'type':15,'data':" + index + "}"); //发送表情
    },
    handleShowEmoji: function handleShowEmoji(message) {
      var _this5 = this;

      var emojiInfo = JSON.parse(message.Result);
      var userIndex = 0;
      for (var i = 0; i < this.userList.length; i++) {
        if (this.userList[i].basicInfo.userId == emojiInfo.userId) {
          userIndex = i;
          break;
        }
      }
      this.$set(this.seatList, userIndex, { emojiUrl: this.emoji[emojiInfo.index].imgUrl, showEmoji: true });
      setTimeout(function () {
        _this5.$set(_this5.seatList, userIndex, { emojiUrl: "", showEmoji: false });
      }, 2000);
    },
    handleNextQuestion: function handleNextQuestion(data) {
      var _this6 = this;

      console.log('接受type6', data);
      if (this.currentIndex != data.Result.index) {
        this.stopTimeCount = !this.stopTimeCount;
        setTimeout(function () {
          _this6.showBothAns = !_this6.showBothAns;
        }, 1000);
        setTimeout(function () {
          _this6.currentIndex = data.Result.index;
        }, 3000);
      }
    },
    startTimer: function startTimer() {
      this.startTimeCount = !this.startTimeCount;
    },
    stopTimer: function stopTimer() {
      this.stopTimeCount = !this.stopTimeCount;
    },
    startHeart: function startHeart() {
      var _this7 = this;

      this.heart = setInterval(function () {
        try {
          _this7.send({
            data: "{'type':0,'data':1}",
            success: function success() {
              _this7.stopHeartTimer();
              _this7.heartTimer = setInterval(function () {
                _this7.delayTime++;
              }, 1000);
            },
            fail: function fail(res) {
              console.log(res);
              if (!_this7.isHideLife) {
                _this7.netWorkWeek();
              }
            }
          });
          // throw"发送失败心跳"
        } catch (err) {
          console.log(err);
        }
      }, 5000);
    },
    stopHeart: function stopHeart() {
      clearInterval(this.heart);
    },
    startHeartTimer: function startHeartTimer() {
      var _this8 = this;

      this.heartTimer = setInterval(function () {
        _this8.delayTime++;
      }, 1000);
    },
    stopHeartTimer: function stopHeartTimer() {
      clearInterval(this.heartTimer);
      this.delayTime = 0;
    },
    clearHeart: function clearHeart() {
      this.stopHeart();
      this.stopHeartTimer();
    },
    startGame: function startGame() {
      this.gameStatus = 'playing';
      this.ansList = [];
    },
    gameStatus: function gameStatus(valu) {
      console.log('游戏状态', value);
    },
    gameEnd: function gameEnd(value) {
      var _this9 = this;

      setTimeout(function () {
        _this9.showBothAns = !_this9.showBothAns;
      }, 1000);
      setTimeout(function () {
        console.log("end", value);
        _this9.resultData = value;
        _this9.gameStatus = "wait";
        _this9.setGameRecord(value);
        for (var i = 0; i < value.Result.room.userList.length; i++) {
          if (_this9.appInfo.userId == value.Result.room.userList[i].basicInfo.userId) {
            _this9.setAppInfo({ money: value.Result.room.userList[i].abilityInfo.money });
            // this.appInfo.money = value.Result.room.userList[i].abilityInfo.money;
          }
        }

        if (_this9.$root.$mp.query.status) {
          delete _this9.$root.$mp.query.status;
        }
        setTimeout(function () {
          _this9.showQuesModules = false;
          _this9.showResult = true;
          _this9.currentIndex = 0;
          console.log("change index from gameEnd", _this9.currentIndex);
        }, 1000);
      }, 2500);
    },
    checkOldGame: function checkOldGame(data) {
      var _this10 = this;

      // TODO 重置题目序号，时间，
      console.log('游戏重连--------------', data);
      if (data.Result == '') {
        wx.showModal({
          title: "系统提示",
          content: "对局已结束,离开房间",
          showCancel: false,
          success: function success(res) {
            if (res.confirm) {
              _this10.$router.back();
            }
          }
        });
      } else {
        if (this.$root.$mp.query.status == 5) {
          this.showQuesModules = true;
        }
        this.initTime = Math.ceil(data.Result.room.restSecond / 1000);
        this.currentIndex = data.Result.room.answerIndex;
        console.log("change index from reLink", this.currentIndex);
        this.startTimeCount = !this.startTimeCount;
        console.log('重连index', data.Result.room.answerIndex);
        console.log("时间校准：", data.Result.room.restSecond);
      }
    },

    // 页面交互处理
    handleReadyClick: function handleReadyClick() {
      this.send("{'type':10,'data':'" + this.roomData.game.gameId + "'}");
    },
    kick: function kick(id) {
      var _this11 = this;

      wx.showModal({
        title: "系统提示",
        content: "确定请他(她)离开？",
        success: function success(res) {
          if (res.confirm) {
            _this11.send("{'type':8,'data':" + id + "}");
          }
        }
      });
    },
    handleTick: function handleTick() {
      var _this12 = this;

      wx.showModal({
        title: "系统提示",
        content: "你已被房主请离房间",
        showCancel: false,
        success: function success() {
          _this12.$router.back();
        }
      });
    },
    handleInvate: function handleInvate() {
      this.showFullMask = true;
    },
    handleInvate2: function handleInvate2() {
      wx.showToast({ title: "准备状态无法邀请", icon: "none" });
    },
    closeFullMask: function closeFullMask() {
      this.showFullMask = false;
    },
    checkAllReady: function checkAllReady() {
      var _this13 = this;

      var state = true;
      this.userList.forEach(function (item) {
        item.isReady == 0 ? (state = false, _this13.gameStatus == 'wait') : null;
      });
      return state;
    },
    handleStartGame: function handleStartGame() {
      // 检查准备状态
      var state = this.checkAllReady();
      console.log("state", state, "this.userNum>1", this.userNum, "this.gameStatus!='readGame", this.gameStatus);
      if (state && this.userNum > 1 && this.gameStatus != 'readyGame') {
        this.gameStatus = 'readyGame';
        this.send("{'type':1,'data':'" + this.roomData.game.gameId + "'}"); //发一次预备开始游戏
        this.stopAutoStartTimer();
        this.showTimerMask = false;
      } else if (state && this.userNum == 1) {
        wx.showToast({
          title: "请先邀请好友",
          icon: "none"
        });
      } else if (!state) {
        wx.showToast({
          title: "有玩家未准备",
          icon: "none"
        });
      }
    },
    handleSelectIn: function handleSelectIn() {
      var _this14 = this;

      // 显示选项
      this.showSelect = !this.showSelect;
      setTimeout(function () {
        _this14.startTimeCount = !_this14.startTimeCount;
      }, 400);
      console.log("展示选项");
    },
    handleSelect: function handleSelect(obj) {
      obj.data.gameId = this.roomData.game.gameId;
      obj.data.roomId = this._roomId;
      obj = __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default()(obj);
      this.send(obj, "send-submitAns");
    },
    answerResponse: function answerResponse(data) {
      var _this15 = this;

      setTimeout(function () {
        console.log("response-type4", data.Result);
        _this15.ansList.push(data.Result);
      }, 100);
    },
    handleAnswerComplete: function handleAnswerComplete() {
      var _this16 = this;

      if (this.currentIndex < 4 && this.connected) {
        setTimeout(function () {
          _this16.ansList = [];
          _this16.initTime = 10;
        }, 1000);
        setTimeout(function () {
          _this16.initTimeCount = !_this16.initTimeCount;
          _this16.titleOff = !_this16.titleOff;
          _this16.selectOff = !_this16.selectOff;
        }, 1500);

        setTimeout(function () {
          _this16.send("{'type':6,'data':'" + _this16.roomData.game.gameId + "'}"); //下一题开始计时
        }, 4800);
      } else if (this.currentIndex == 4 && this.connected) {
        setTimeout(function () {
          _this16.initTimeCount = !_this16.initTimeCount;
          _this16.titleOff = !_this16.titleOff;
          _this16.selectOff = !_this16.selectOff;
          _this16.ansList = [];
        }, 1500);
      }
    },
    handleCloseResult: function handleCloseResult() {
      this.showResult = false;
    },
    onNetWorkChange: function onNetWorkChange() {
      var _this17 = this;

      wx.onNetworkStatusChange(function (res) {
        if (!res.isConnected && _this17.connected) {
          _this17.closeSocket();
        }
      });
    }
  }),
  mounted: function mounted() {
    var _this18 = this;

    return __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default()( /*#__PURE__*/__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.mark(function _callee() {
      return __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.wrap(function _callee$(_context) {
        while (1) {
          switch (_context.prev = _context.next) {
            case 0:
              console.log("mounted");
              _this18.query = _this18.$root.$mp.query;
              _this18.addMessageListener(_this18.handleMessage);
              if (_this18.query.type == "create") {
                // todo 发送创建请求
                _this18.createdRoom();
              } else if (_this18.query.type == "messageBus") {
                _this18.handleMessage(_this18.messageBus);
              }

            case 4:
            case "end":
              return _context.stop();
          }
        }
      }, _callee, _this18);
    }))();
  },
  onShow: function onShow() {
    var _this19 = this;

    return __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default()( /*#__PURE__*/__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.mark(function _callee2() {
      return __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.wrap(function _callee2$(_context2) {
        while (1) {
          switch (_context2.prev = _context2.next) {
            case 0:
              console.log("onShow");
              _this19.isHideLife = false;
              _this19.hideForRoute = false;
              _this19.setPageType(2); //标识当前页面，断网重连时候可以使用
              if (!_this19.connected) {
                _this19.connect({ type: 2 });
              }

            case 5:
            case "end":
              return _context2.stop();
          }
        }
      }, _callee2, _this19);
    }))();
  },
  onHide: function onHide() {
    this.isHideLife = true;
    this.setPageType(0); //标识当前页面
    this.send("{'type':17,'data':'0'}", "隐藏告知服务端");
    if (this.hideForRoute) {
      this.setPageType(2); //标识当前页面
      return false;
    }
    try {
      this.close();
    } catch (err) {}
  },
  onUnload: function onUnload() {
    var _this20 = this;

    return __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default()( /*#__PURE__*/__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.mark(function _callee3() {
      return __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.wrap(function _callee3$(_context3) {
        while (1) {
          switch (_context3.prev = _context3.next) {
            case 0:
              // this.clearHeart();
              _this20.currentIndex = 0;
              _this20.userList = [];
              _this20.isFirst = true;
              _this20.emojiBtn = false;
              _this20.showQuesModules = false;
              _this20.showResult = false;
              _this20.gameStatus = "wait";
              delete _this20.$root.$mp.query;
              _context3.next = 10;
              return _this20.send("{'type':5,'data':'" + _this20.roomData.game.gameId + "'}", "离开页面");

            case 10:
              _this20.removeMessageListener(_this20.handleMessage);
              _this20.setPageType(0); //标识当前页面
              _this20.close();

            case 13:
            case "end":
              return _context3.stop();
          }
        }
      }, _callee3, _this20);
    }))();
  }
});

/***/ }),

/***/ 293:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "room"
  }, [(!_vm.showQuesModules && !_vm.showResult) ? _c('div', {
    staticClass: "m-room"
  }, [_c('div', {
    staticClass: "u-seat"
  }, _vm._l((_vm.placeList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "u-seat-box"
    }, [_c('div', {
      staticClass: "u-seat-top"
    }, [(_vm.userList[index]) ? _c('img', {
      attrs: {
        "src": _vm.IMGHEAD + _vm.userList[index].abilityInfo.paragraphImage
      }
    }) : _vm._e(), _vm._v(" "), (_vm.userList[index]) ? _c('div', {
      staticClass: "u-info"
    }, [_c('div', {
      staticClass: "u-name"
    }, [_vm._v(_vm._s(_vm.userList[index].basicInfo.nickName))]), _vm._v(" "), _c('div', {
      staticClass: "u-area"
    }, [_c('div', [_vm._v(_vm._s(_vm.userList[index].basicInfo.province || '宇宙'))]), _vm._v(" "), _c('div', [_vm._v(_vm._s(_vm.userList[index].basicInfo.city || '火星'))]), _vm._v(" "), (_vm.userList[index].basicInfo.gender == '女') ? _c('img', {
      staticClass: "girl",
      attrs: {
        "src": "/static/girl.png"
      }
    }) : _c('img', {
      staticClass: "boy",
      attrs: {
        "src": "/static/boy.png"
      }
    })])]) : _vm._e(), _vm._v(" "), (_vm.userList[index] && (_vm.userList[index].basicInfo.userId == _vm.roomData.room.roomMaster)) ? _c('div', {
      staticClass: "u-master"
    }, [_vm._v("房主")]) : _vm._e()]), _vm._v(" "), _c('div', {
      staticClass: "u-seat-bottom"
    }, [_c('img', {
      staticClass: "u-bg",
      attrs: {
        "src": "/static/light-bg.png"
      }
    }), _vm._v(" "), (_vm.userList[index] && _vm.userList[index].basicInfo.avatarUrl && _vm.userList[index].boot) ? _c('img', {
      staticClass: "u-head-img",
      attrs: {
        "src": _vm.IMGHEAD + _vm.userList[index].basicInfo.avatarUrl
      }
    }) : _vm._e(), _vm._v(" "), (_vm.userList[index] && _vm.userList[index].basicInfo.avatarUrl && !_vm.userList[index].boot) ? _c('img', {
      staticClass: "u-head-img",
      attrs: {
        "src": _vm.userList[index].basicInfo.avatarUrl
      }
    }) : _vm._e(), _vm._v(" "), (_vm.userList[index] && !_vm.userList[index].basicInfo.avatarUrl) ? _c('img', {
      staticClass: "u-head-img",
      attrs: {
        "src": "/static/default-head.png"
      }
    }) : _vm._e(), _vm._v(" "), (_vm.userList[index] && _vm.userList[index].isReady == 1 && !_vm.userList[index].isMaster) ? _c('img', {
      staticClass: "img-ready",
      attrs: {
        "src": "/static/ready.png",
        "alt": ""
      }
    }) : _vm._e(), _vm._v(" "), (_vm.userList[index] && _vm.userList[index].isReady == 0) ? _c('div', {
      staticClass: "u-mask"
    }, [_vm._v("\n            未准备\n            "), (_vm.userRole == '房主') ? _c('div', {
      staticClass: "u-kick",
      attrs: {
        "eventid": '0-' + index
      },
      on: {
        "click": function($event) {
          _vm.kick(_vm.userList[index].basicInfo.userId)
        }
      }
    }, [_vm._v("踢")]) : _vm._e()]) : _vm._e()]), _vm._v(" "), _c('div', {
      staticClass: "u-emoji-box",
      class: {
        'showEmj': _vm.userList[index] && _vm.seatList[index].showEmoji
      }
    }, [_c('div', {
      staticClass: "u-inner-box"
    }, [_c('img', {
      attrs: {
        "src": _vm.seatList[index].emojiUrl
      }
    })])])])
  })), _vm._v(" "), (_vm.showTimerMask) ? _c('div', {
    staticClass: "timer-mask"
  }, [_c('div', [_vm._v("玩家准备就绪~")]), _vm._v("\n      " + _vm._s(_vm.autoStartTime) + "\n    ")]) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "u-btn"
  }, [(_vm.userRole == '房主') ? _c('div', {
    staticClass: "u-btn-left",
    attrs: {
      "eventid": '1'
    },
    on: {
      "click": _vm.handleStartGame
    }
  }, [_vm._v("开始游戏")]) : _vm._e(), _vm._v(" "), (_vm.userRole == '民众' && _vm.myInfo.isReady == 0) ? _c('div', {
    staticClass: "u-btn-left",
    attrs: {
      "eventid": '2'
    },
    on: {
      "click": _vm.handleReadyClick
    }
  }, [_vm._v("准备")]) : _vm._e(), _vm._v(" "), (_vm.userRole == '民众' && _vm.myInfo.isReady == 1) ? _c('div', {
    staticClass: "u-btn-left",
    attrs: {
      "eventid": '3'
    },
    on: {
      "click": _vm.handleReadyClick
    }
  }, [_vm._v("取消准备")]) : _vm._e(), _vm._v(" "), (_vm.userNum >= 4) ? _c('div', {
    staticClass: "u-btn-right",
    attrs: {
      "eventid": '5'
    },
    on: {
      "click": _vm.handleInvate
    }
  }, [_vm._v("邀请")]) : (_vm.userNum < 4 && _vm.myInfo.isReady == 1 && _vm.userRole == '民众') ? _c('div', {
    staticClass: "u-btn-right",
    attrs: {
      "eventid": '4'
    },
    on: {
      "click": _vm.handleInvate2
    }
  }, [_vm._v("邀请")]) : _c('button', {
    staticClass: "u-btn-right",
    attrs: {
      "open-type": "share",
      "plain": "true"
    }
  }, [_vm._v("邀请")]), _vm._v(" "), _c('img', {
    staticClass: "u-btn-emoji",
    attrs: {
      "src": "/static/room/emoji-btn.png",
      "alt": "",
      "eventid": '6'
    },
    on: {
      "click": function($event) {
        _vm.emojiBtn = true
      }
    }
  })], 1)]) : _vm._e(), _vm._v(" "), (_vm.showQuesModules) ? _c('div', {
    staticClass: "m-ques-modal"
  }, [_c('ques-head', {
    attrs: {
      "userList": _vm.userList,
      "ansList": _vm.ansList,
      "userNum": _vm.userNum,
      "mpcomid": '0'
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-time"
  }, [_c('time-count', {
    attrs: {
      "small": true,
      "initBtn": _vm.initTimeCount,
      "startBtn": _vm.startTimeCount,
      "initTime": _vm.initTime,
      "stopBtn": _vm.stopTimeCount,
      "mpcomid": '1'
    }
  })], 1), _vm._v(" "), _c('div', {
    staticClass: "u-title"
  }, [_c('ques-title', {
    attrs: {
      "list": _vm.list,
      "currentIndex": _vm.currentIndex,
      "idiomList": _vm.idiomList,
      "poetryList": _vm.poetryList,
      "levelList": _vm.levelList,
      "imgLevelList": _vm.imgLevelList,
      "titleOff": _vm.titleOff,
      "eventid": '7',
      "mpcomid": '2'
    },
    on: {
      "antEnd": _vm.handleSelectIn
    }
  })], 1), _vm._v(" "), _c('div', {
    staticClass: "u-select"
  }, [_c('ques-select', {
    attrs: {
      "subjectList": _vm.list,
      "currentIndex": _vm.currentIndex,
      "show": _vm.showSelect,
      "showBothAns": _vm.showBothAns,
      "ansList": _vm.ansList,
      "userNum": _vm.userNum,
      "hide": _vm.selectOff,
      "eventid": '8',
      "mpcomid": '3'
    },
    on: {
      "selected": _vm.handleSelect,
      "answerComplete": _vm.handleAnswerComplete
    }
  })], 1)], 1) : _vm._e(), _vm._v(" "), (_vm.showResult) ? _c('div', {
    staticClass: "u-result"
  }, [_c('ques-result', {
    attrs: {
      "result": _vm.resultData,
      "eventid": '9',
      "mpcomid": '4'
    },
    on: {
      "close": _vm.handleCloseResult,
      "route": _vm.handleRoute
    }
  })], 1) : _vm._e(), _vm._v(" "), (_vm.showFullMask) ? _c('div', {
    staticClass: "full-man-mask"
  }, [_c('div', {
    staticClass: "f-bg"
  }), _vm._v(" "), _c('div', {
    staticClass: "f-inner"
  }, [_c('div', {
    staticClass: "f-txt"
  }, [_vm._v("房间已满,你不能邀请更多好友")]), _vm._v(" "), _c('div', {
    staticClass: "f-btn",
    attrs: {
      "eventid": '10'
    },
    on: {
      "click": _vm.closeFullMask
    }
  }, [_vm._v("确定")])])]) : _vm._e(), _vm._v(" "), (_vm.emojiBtn) ? _c('emoji-box', {
    attrs: {
      "eventid": '11',
      "mpcomid": '5'
    },
    on: {
      "close": function($event) {
        _vm.emojiBtn = false
      },
      "sendEmoji": _vm.handleSendEmoji
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
     require("vue-hot-reload-api").rerender("data-v-3366eca3", esExports)
  }
}

/***/ })

},[343]);
//# sourceMappingURL=main.js.map