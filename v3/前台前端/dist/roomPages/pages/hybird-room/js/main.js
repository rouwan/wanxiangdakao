global.webpackJsonp([12],{

/***/ 298:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_hybird_room_vue__ = __webpack_require__(300);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_7b4a4334_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_hybird_room_vue__ = __webpack_require__(301);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(299)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-7b4a4334"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_hybird_room_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_7b4a4334_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_hybird_room_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\roomPages\\pages\\hybird-room.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] hybird-room.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-7b4a4334", Component.options)
  } else {
    hotAPI.reload("data-v-7b4a4334", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 299:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 300:
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
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__pages_game_components_timeCount_vue__ = __webpack_require__(37);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__utils_index__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__config_dataFile__ = __webpack_require__(13);





//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
    var index = Object(__WEBPACK_IMPORTED_MODULE_11__utils_index__["c" /* getRondom */])(0, __WEBPACK_IMPORTED_MODULE_12__config_dataFile__["g" /* shareInfoForRoom */].length - 1);
    var obj = {
      title: __WEBPACK_IMPORTED_MODULE_12__config_dataFile__["g" /* shareInfoForRoom */][index].title,
      imageUrl: __WEBPACK_IMPORTED_MODULE_12__config_dataFile__["g" /* shareInfoForRoom */][index].imgUrl,
      path: "/pages/entry/index?roomId=" + this.roomId + "&userId=" + this.appInfo.userId
    };
    if (this.showResult) {
      obj = {
        title: "快来看我智战群雄",
        path: "/pages/entry/index?userId=" + this.appInfo.userId
      };
    }
    return obj;
  },
  data: function data() {
    return {
      // boolean
      masterId: 0,
      //data
      userReady: 0,
      gameId: 0,
      roomId: 0,
      roomType: "wait",
      query: {}, //页面参数
      emtptList: ["1", "1", "1", "1"],
      userList: [],
      allReady: false,
      ctime: 3,
      // 组件控制
      showQuesModules: false,
      titleOff: true,
      selectOff: false,
      showTitle: true,
      showSelect: false, //
      showResult: false, //展示结果
      initTime: 10,
      initTimeCount: false,
      startTimeCount: false,
      stopTimeCount: false,
      updateHead: false,
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
      shareType: "",
      throttle: __WEBPACK_IMPORTED_MODULE_11__utils_index__["f" /* throttle */]
    };
  },

  components: {
    "ques-head": __WEBPACK_IMPORTED_MODULE_6__components_quesHead_vue__["a" /* default */],
    "time-count": __WEBPACK_IMPORTED_MODULE_10__pages_game_components_timeCount_vue__["a" /* default */],
    "ques-title": __WEBPACK_IMPORTED_MODULE_7__components_quesTitle_vue__["a" /* default */],
    "ques-select": __WEBPACK_IMPORTED_MODULE_8__components_quesSelect_vue__["a" /* default */],
    "ques-result": __WEBPACK_IMPORTED_MODULE_9__components_quesResult_vue__["a" /* default */]
  },
  computed: __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_5_vuex__["c" /* mapState */])({
    appInfo: function appInfo(state) {
      return state.appInfo;
    },
    EVN: function EVN(state) {
      return state.EVN;
    },
    IMGHEAD: function IMGHEAD(state) {
      return state.IMGHEAD;
    },
    HUAWEI: function HUAWEI(state) {
      return state.HUAWEI;
    },
    connected: function connected(state) {
      return state.socketStore.connected;
    },
    messageListeners: function messageListeners(state) {
      return state.socketStore.messageListeners;
    },
    messageBus: function messageBus(state) {
      return state.socketStore.messageBus;
    }
  })),
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
    // socket listener
    handleMessage: function handleMessage(message) {
      var _this = this;

      var eventHandleMap = {
        "socketType-1": function socketType1() {
          return _this.startMatch(message);
        }, //type1 初始化、重置房间信息
        socketType1: function socketType1() {
          return _this.updateRoom(message);
        }, //type1 初始化、重置房间信息
        socketType2: function socketType2() {
          return _this.checkOldGame(message);
        },
        socketType4: function socketType4() {
          return _this.answerResponse(message);
        }, //type4 答题信息
        socketType7: function socketType7() {
          return _this.gameEnd(message);
        }, //type7 游戏结束
        socketType6: function socketType6() {
          return _this.handleNextQuestion(message);
        }
      };
      //执行对应的方法
      if (typeof eventHandleMap["socketType" + message.Type] == "function") {
        eventHandleMap["socketType" + message.Type]();
      }
    },

    // socket handle
    createdRoom: function createdRoom() {
      this.send("{'type':13,'data':1,'modeType':1}", "创建房间");
    },
    startMatch: function startMatch() {
      this.roomType = "match";
      this.send("{'type':1,'data':'" + this.gameId + "'}"); //开始游戏(寻找匹配)
    },
    handleReadyClick: function handleReadyClick() {
      this.send("{'type':10,'data':'" + this.gameId + "'}");
    },
    handleStartGame: function handleStartGame() {
      // 检查准备状态
      var state = this.checkAllReady();
      if (state) {
        this.send("{'type':-1,'data':'" + this.gameId + "'}"); //开始匹配
      } else if (!state) {
        wx.showToast({
          title: "有玩家未准备",
          icon: "none"
        });
      }
    },
    handleNextQuestion: function handleNextQuestion(data) {
      var _this2 = this;

      console.log("接受type6", data);
      if (this.currentIndex != data.Result.index) {
        this.stopTimeCount = !this.stopTimeCount;
        setTimeout(function () {
          _this2.showBothAns = !_this2.showBothAns;
          setTimeout(function () {
            _this2.updateHead = !_this2.updateHead;
          }, 1000);
        }, 1000);
        setTimeout(function () {
          _this2.currentIndex = data.Result.index;
        }, 3000);
      }
    },
    checkOldGame: function checkOldGame(data) {
      var _this3 = this;

      // TODO 重置题目序号，时间，
      console.log("游戏重连--------------", data);
      if (data.Result == "") {
        wx.showModal({
          title: "系统提示",
          content: "对局已结束,离开房间",
          showCancel: false,
          success: function success(res) {
            if (res.confirm) {
              _this3.$router.back();
            }
          }
        });
      } else {
        this.initTime = Math.ceil(data.Result.room.restSecond / 1000);
        this.currentIndex = data.Result.room.answerIndex;
        this.startTimeCount = !this.startTimeCount;
      }
    },
    getList: function getList(res) {
      this.idiomList = [];
      this.levelList = [];
      this.poetryList = [];
      this.imgLevelList = [];
      this.list = res.Result.game.subjectList;
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
          var arr = this.list[i].title.replace("_", "?").split("");
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
    handleSelectIn: function handleSelectIn() {
      var _this4 = this;

      // 显示选项
      this.showSelect = !this.showSelect;
      setTimeout(function () {
        _this4.startTimeCount = !_this4.startTimeCount;
      }, 400);
      console.log("展示选项");
    },
    handleSelect: function handleSelect(obj) {
      obj.data.gameId = this.gameId;
      obj.data.roomId = this.roomId;
      obj = __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default()(obj);
      this.send(obj, "send-submitAns");
    },
    answerResponse: function answerResponse(data) {
      var _this5 = this;

      setTimeout(function () {
        console.log("response-type4", data.Result);
        _this5.ansList.push(data.Result);
      }, 100);
    },
    handleAnswerComplete: function handleAnswerComplete() {
      var _this6 = this;

      if (this.currentIndex < 4 && this.connected) {
        setTimeout(function () {
          _this6.ansList = [];
          _this6.initTime = 10;
        }, 1000);
        setTimeout(function () {
          _this6.initTimeCount = !_this6.initTimeCount;
          _this6.titleOff = !_this6.titleOff;
          _this6.selectOff = !_this6.selectOff;
        }, 1500);

        setTimeout(function () {
          _this6.send("{'type':6,'data':'" + _this6.gameId + "'}"); //下一题开始计时
        }, 4800);
      } else if (this.currentIndex == 4 && this.connected) {
        setTimeout(function () {
          _this6.initTimeCount = !_this6.initTimeCount;
          _this6.titleOff = !_this6.titleOff;
          _this6.selectOff = !_this6.selectOff;
          _this6.ansList = [];
        }, 1500);
      }
    },
    handleCloseResult: function handleCloseResult() {
      this.showResult = false;
    },

    // 房间状态
    updateRoom: function updateRoom(res) {
      var _this7 = this;

      console.log(res);
      this.gameId = res.Result.game.gameId;
      this.roomId = res.Result.room.roomId;
      this.userList = res.Result.room.userList;
      this.masterId = res.Result.room.roomMaster;
      for (var i = 0; i < this.userList.length; i++) {
        if (this.appInfo.userId == this.userList[i].basicInfo.userId) {
          this.userReady = this.userList[i].isReady;
        }
      }
      if (res.Result.room.roomStatus == 0 && res.Result.game.subjectList && res.Result.game.subjectList.length > 0) {
        this.getList(res);
        this.currentIndex = 0;
        this.roomType = "matchSuccess";
        var timer = setInterval(function (res) {
          _this7.ctime--;
        }, 1000);

        setTimeout(function () {
          clearInterval(timer);
          _this7.ctime = 3;
        }, 3500);

        setTimeout(function () {
          if (_this7.roomType == 'matchSuccess') {
            _this7.showQuesModules = true; //显示答题界面
          }
        }, 3000);

        setTimeout(function () {
          if (_this7.roomType == 'matchSuccess') {
            _this7.send("{'type':6,'data':'" + _this7.gameId + "'}"); //开始游戏
            _this7.roomType = "playing";
            _this7.ansList = [];
          }
        }, 5500);
      }
    },
    gameEnd: function gameEnd(value) {
      var _this8 = this;

      setTimeout(function () {
        _this8.showBothAns = !_this8.showBothAns;
      }, 1000);
      setTimeout(function () {
        console.log("end", value);
        _this8.resultData = value;
        _this8.roomType = "wait";
        _this8.setGameRecord(value);
        for (var i = 0; i < value.Result.room.userList.length; i++) {
          if (_this8.appInfo.userId == value.Result.room.userList[i].basicInfo.userId) {
            _this8.setAppInfo({ money: value.Result.room.userList[i].abilityInfo.money });
            // this.appInfo.money =
            //   value.Result.room.userList[i].abilityInfo.money;
          }
        }

        setTimeout(function () {
          _this8.showQuesModules = false;
          _this8.showResult = true;
          _this8.currentIndex = 0;
        }, 1000);
      }, 2500);
    },
    handleOnShow: function handleOnShow() {
      if (this.shareType == "result") {
        this.shareType = "";
      }
      this.hideForRoute = false;
      this.setPageType(2); //标识当前页面，断网重连时候可以使用
      console.log("onShow-page1");
      if (!this.connected) {
        this.connect({ type: 2 });
      }
    },
    handleOnHide: function handleOnHide() {
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

    // 特殊情况处理
    unAbleInvate: function unAbleInvate() {
      wx.showToast({ title: "人数已满或准备中不能邀请", icon: "none" });
    },
    checkAllReady: function checkAllReady() {
      var state = true;
      this.userList.forEach(function (item) {
        item.isReady == 0 ? state = false : null;
      });
      return state;
    }
  }),
  mounted: function mounted() {
    this.query = this.$root.$mp.query;
    this.addMessageListener(this.handleMessage);
    if (this.query.type == "hybird") {
      this.createdRoom();
    } else if (this.query.type == "messageBus") {
      //接收房间页的处理方法
      this.handleMessage(this.messageBus);
    }
  },
  onShow: function onShow() {
    this.throttle(this.handleOnShow, 200);
  },
  onHide: function onHide() {
    this.throttle(this.handleOnHide, 200);
  },
  onUnload: function onUnload() {
    var _this9 = this;

    return __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default()( /*#__PURE__*/__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.mark(function _callee() {
      return __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.wrap(function _callee$(_context) {
        while (1) {
          switch (_context.prev = _context.next) {
            case 0:
              _this9.currentIndex = 0;
              _this9.userList = [];
              _this9.isFirst = true;
              _this9.emojiBtn = false;
              _this9.showQuesModules = false;
              _this9.showResult = false;
              _this9.roomType = "wait";
              _this9.removeMessageListener(_this9.handleMessage);
              delete _this9.$root.$mp.query;
              _context.next = 11;
              return _this9.send("{'type':5,'data':'" + _this9.gameId + "'}", "离开页面");

            case 11:
              _this9.setPageType(0); //标识当前页面
              // this.close();

            case 12:
            case "end":
              return _context.stop();
          }
        }
      }, _callee, _this9);
    }))();
  }
});

/***/ }),

/***/ 301:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "hyb-room"
  }, [_c('img', {
    staticClass: "entry-bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-test.png"
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "match",
    class: {
      'show-match': _vm.roomType == 'match'
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/match-w.png"
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "match-success",
    class: {
      'show-match-success': _vm.roomType == 'matchSuccess'
    },
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/match-s.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "hyb-box",
    class: {
      'box-match': _vm.roomType == 'match' || _vm.roomType == 'matchSuccess'
    }
  }, [(_vm.roomType == 'matchSuccess') ? _c('div', {
    staticClass: "time-box flex"
  }, [_vm._v(_vm._s(_vm.ctime) + "秒后进入")]) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "h-head"
  }, [_c('img', {
    staticClass: "mode-type",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/hybird-type.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "gold-box"
  }, [_c('img', {
    attrs: {
      "src": "/static/gold.png"
    }
  }), _vm._v(_vm._s(_vm.appInfo.money || 0) + "\n      ")])]), _vm._v(" "), _c('div', {
    staticClass: "h-body"
  }, _vm._l((_vm.emtptList), function(seat, index) {
    return _c('div', {
      key: index,
      staticClass: "user-box flex"
    }, [_c('div', {
      staticClass: "user-head flex radius50"
    }, [_c('img', {
      staticClass: "ques db-center",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/ques.png"
      }
    }), _vm._v(" "), (_vm.userList[index] && _vm.userList[index].boot) ? _c('img', {
      staticClass: "head-img db-center radius50",
      attrs: {
        "src": _vm.IMGHEAD + _vm.userList[index].basicInfo.avatarUrl
      }
    }) : _vm._e(), _vm._v(" "), (_vm.userList[index] && !_vm.userList[index].boot) ? _c('img', {
      staticClass: "head-img db-center radius50",
      attrs: {
        "src": _vm.userList[index].basicInfo.avatarUrl
      }
    }) : _vm._e(), _vm._v(" "), (_vm.userList[index] && _vm.userList[index].isReady == 1 && _vm.masterId != _vm.userList[index].basicInfo.userId && _vm.roomType == 'wait') ? _c('div', {
      staticClass: "ready db-center radius50"
    }, [_c('img', {
      staticClass: "db-center",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/ready.png"
      }
    })]) : _vm._e(), _vm._v(" "), (!_vm.userList[index] && _vm.roomType == 'match') ? _c('div', {
      staticClass: "u-ant-cycle-fill"
    }, [_c('div', {
      staticClass: "u-ant-dot"
    }), _vm._v(" "), _c('div', {
      staticClass: "u-ant-dot"
    }), _vm._v(" "), _c('div', {
      staticClass: "u-ant-dot"
    })]) : _vm._e()]), _vm._v(" "), (_vm.userList[index]) ? _c('div', {
      staticClass: "user-info"
    }, [_c('div', [_vm._v(_vm._s(_vm.userList[index].basicInfo.nickName || '无名氏'))]), _vm._v(" "), _c('div', [_vm._v(_vm._s(_vm.userList[index].basicInfo.city || '火星'))])]) : _c('div', {
      staticClass: "user-info-empty"
    }, [_c('div'), _vm._v(" "), _c('div')])])
  }))]), _vm._v(" "), (_vm.roomType == 'wait') ? _c('div', {
    staticClass: "btn-box flex"
  }, [(_vm.masterId == _vm.appInfo.userId) ? _c('img', {
    attrs: {
      "hover-class": "click-active",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/start-btn.png",
      "eventid": '0'
    },
    on: {
      "click": function($event) {
        _vm.throttle(_vm.handleStartGame, 400)
      }
    }
  }) : _vm._e(), _vm._v(" "), (_vm.masterId != _vm.appInfo.userId && _vm.userReady == 0) ? _c('img', {
    attrs: {
      "hover-class": "click-active",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/ready-btn.png",
      "eventid": '1'
    },
    on: {
      "click": _vm.handleReadyClick
    }
  }) : _vm._e(), _vm._v(" "), (_vm.masterId != _vm.appInfo.userId && _vm.userReady == 1) ? _c('img', {
    attrs: {
      "hover-class": "click-active",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/cancel-btn.png",
      "eventid": '2'
    },
    on: {
      "click": _vm.handleReadyClick
    }
  }) : _vm._e(), _vm._v(" "), (_vm.userList.length == 4 || _vm.userReady == 1 && _vm.masterId != _vm.appInfo.userId) ? _c('img', {
    staticClass: "invate",
    attrs: {
      "hover-class": "click-active",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/invate-btn.png",
      "eventid": '3'
    },
    on: {
      "click": _vm.unAbleInvate
    }
  }) : _c('button', {
    attrs: {
      "hover-class": "click-active",
      "open-type": "share",
      "plain": "true"
    }
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/invate-btn.png"
    }
  })])], 1) : _vm._e(), _vm._v(" "), (_vm.showQuesModules) ? _c('div', {
    staticClass: "m-ques-modal"
  }, [_c('ques-head', {
    attrs: {
      "userList": _vm.userList,
      "ansList": _vm.ansList,
      "update": _vm.updateHead,
      "userNum": _vm.userList.length,
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
      "eventid": '4',
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
      "userNum": _vm.userList.length,
      "hide": _vm.selectOff,
      "eventid": '5',
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
      "eventid": '6',
      "mpcomid": '4'
    },
    on: {
      "close": _vm.handleCloseResult,
      "route": _vm.handleRoute
    }
  })], 1) : _vm._e()])
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-7b4a4334", esExports)
  }
}

/***/ })

},[345]);
//# sourceMappingURL=main.js.map