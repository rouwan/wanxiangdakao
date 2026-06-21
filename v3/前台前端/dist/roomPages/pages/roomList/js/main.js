global.webpackJsonp([10],{

/***/ 294:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_roomList_vue__ = __webpack_require__(296);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_dd86f63e_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_roomList_vue__ = __webpack_require__(297);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(295)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-dd86f63e"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_roomList_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_dd86f63e_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_roomList_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\roomPages\\pages\\roomList.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] roomList.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-dd86f63e", Component.options)
  } else {
    hotAPI.reload("data-v-dd86f63e", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 295:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 296:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__utils_index__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__config_dataFile__ = __webpack_require__(13);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__pages_entry_components_adBoxNew__ = __webpack_require__(72);

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
    var index = Object(__WEBPACK_IMPORTED_MODULE_2__utils_index__["c" /* getRondom */])(0, __WEBPACK_IMPORTED_MODULE_3__config_dataFile__["g" /* shareInfoForRoom */].length - 1);
    return {
      title: this.appInfo.nickname + __WEBPACK_IMPORTED_MODULE_3__config_dataFile__["g" /* shareInfoForRoom */][index].title,
      imageUrl: __WEBPACK_IMPORTED_MODULE_3__config_dataFile__["g" /* shareInfoForRoom */][index].imgUrl,
      path: "/pages/entry/index?userId=" + this.appInfo.userId + "&roomId=0"
    };
  },
  components: {
    "ad-box-new": __WEBPACK_IMPORTED_MODULE_4__pages_entry_components_adBoxNew__["a" /* default */]
  },
  data: function data() {
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

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["c" /* mapState */])({
    IMGHEAD: function IMGHEAD(state) {
      return state.IMGHEAD;
    },
    deviceInfo: function deviceInfo(state) {
      return state.deviceInfo;
    },
    connected: function connected(state) {
      return state.socketStore.connected;
    },
    dataWrap: function dataWrap(state) {
      return state.socketStore.dataWrap;
    },
    appInfo: function appInfo(state) {
      return state.appInfo;
    },
    messageListStorage: function messageListStorage(state) {
      return state.messageListStorage;
    }
  })),
  watch: {},
  methods: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["b" /* mapMutations */])({
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
  }), {
    showMenuFn: function showMenuFn() {
      this.showAdBox = true;
      this.shake();
    },
    toHybird: function toHybird() {
      this.hideForRoute = true;
      this.$router.push("/roomPages/pages/hybird-room?type=hybird");
    },
    toGroup: function toGroup() {
      this.hideForRoute = true;
      this.$router.push("/roomPages/pages/group-room?type=group");
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
    startMsgTimer: function startMsgTimer() {
      var _this = this;

      clearInterval(this.msgTimer);
      this.msgTimer = setInterval(function () {
        if (_this.messageIndex == _this.messageListStorage.length - 1) {
          _this.messageIndex = 0;
        } else {
          _this.messageIndex++;
        }
      }, 3000);
    },
    stopMsgTimer: function stopMsgTimer() {
      clearInterval(this.msgTimer);
    },
    toTalkRoom: function toTalkRoom() {
      this.hideForRoute = true;
      this.$router.push("/pages/talkRoom/index");
    },
    handleMessage: function handleMessage(message) {
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
    initData: function initData(message) {
      var page = 0,
          total = 0,
          data = message.Result;
      total = data.length;
      this.pageNum = Math.ceil(total / this.pageSize);
      this.roomList = [];
      for (var i = 0; i < this.pageNum; i++) {
        this.roomList.push(data.splice(0, this.pageSize));
      }

      for (var j = this.currentPage; j <= this.currentPage && this.currentPage != 0; j--) {
        if (this.roomList[j] && this.roomList[j].length > 0) {
          this.currentPage = j;
          break;
        }
      }
    },
    routerRoomList: function routerRoomList() {
      this.send("{'type':12,'data':1}", "进入大厅");
    },
    createdRoom: function createdRoom() {
      //进入房间页 在发送创建请求
      this.hideForRoute = true;
      this.$router.push("/roomPages/pages/hybird-room?type=create");
    },
    quickStart: function quickStart() {
      this.send("{'type':14,'data':1}", "快速加入"); //先检测是否有可加入的房间
    },
    checkRoomStatus: function checkRoomStatus(id) {
      if (id != 0) {
        this.send("{'type':16,'data':'" + id + "'}", "判断房间状态");
      }
    },
    handleCheckRoomStatus: function handleCheckRoomStatus(message) {
      var _this2 = this;

      if (message.Result == 1) {
        wx.showToast({ title: "游戏已开始", icon: "none" });
        this.setPageType(0);
        this.close();
        var t1 = setTimeout(function () {
          _this2.$router.back();
          clearTimeout(t1);
        }, 1000);
      } else if (message.Result == 3) {
        wx.showToast({ title: "房间人满", icon: "none" });
        this.setPageType(0);
        this.close();
        var t2 = setTimeout(function () {
          _this2.$router.back();
          clearTimeout(t2);
        }, 1000);
      } else if (message.Result == -1) {
        wx.showToast({ title: "房间不存在", icon: "none" });
        this.setPageType(0);
        this.close();
        var t3 = setTimeout(function () {
          _this2.$router.back();
          clearTimeout(t3);
        }, 1000);
      }
    },
    clearPage: function clearPage() {
      this.removeMessageListener(this.handleMessage);
      this.removeOpenedListener(this.listerCard);
      if (!this.hideForRoute) {
        this.close();
      }
      delete this.$root.$mp.query;
    },
    prePage: function prePage() {
      if (this.currentPage > 0) {
        this.currentPage--;
      }
    },
    nextPage: function nextPage() {
      if (this.currentPage < this.pageNum - 1) {
        this.currentPage++;
      }
    },
    toFriendRank: function toFriendRank() {
      this.$router.push("/pages/rankPage/index?friend='true");
    },
    listerCard: function listerCard() {
      if (this.$root.$mp.query && this.$root.$mp.query.roomId) {
        var id = this.$root.$mp.query.roomId;
        this.checkRoomStatus(id);
      } else {
        // this.createdRoom();
      }
    },
    checkGift: function checkGift() {
      var _this3 = this;

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
              console.log("试玩成功--list");
              // 试玩成功
              _this3.showAdBox = true;
              var t0 = setTimeout(function () {
                console.log("t1", t1);
                _this3.setTryGameName(t1.name);
                _this3.tryGameName = t1.name;
                console.log(_this3.tryGameName);
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
    getRightNum: function getRightNum() {
      var _this4 = this;

      this.fly.post("/room/rankHallData").then(function (res) {
        _this4.rightNum = res.data.Result.todayRightSubNum;
      });
    }
  }),
  onShow: function onShow() {
    var _this5 = this;

    this.checkGift();
    this.getRightNum();
    this.hideForRoute = false;
    this.setPageType(1); //标识当前页面
    var t1 = setTimeout(function () {
      if (!_this5.connected && _this5.appInfo.userId) {
        _this5.connect({ type: 1 });
        _this5.addMessageListener(_this5.handleMessage);
        _this5.addOpenedListener(_this5.listerCard);
      }
      // } else if (this.appInfo.userId) {
      //   this.addMessageListener(this.handleMessage);
      //   // this.routerRoomList();
      // }
      _this5.startMsgTimer();
      clearTimeout(t1);
    }, 500);

    // this.fly.post("/room/getHeadData").then(res=>{
    //   console.log(res)
    // })
  },
  onHide: function onHide() {
    this.stopMsgTimer();
    if (!this.hideForRoute) {
      this.send("{'type':17,'data':'0'}", "隐藏告知服务端");
    }
    this.setPageType(0); //标识当前页面
    this.clearPage();
  },
  onUnload: function onUnload() {
    this.stopMsgTimer();
    this.setPageType(0); //标识当前页面
    this.removeMessageListener(this.handleMessage);
    this.removeOpenedListener(this.listerCard);
    delete this.$root.$mp.query;
    // this.clearPage();
    this.close();
    this.currentPage = 0;
  }
});

/***/ }),

/***/ 297:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-room-list"
  }, [_c('img', {
    staticClass: "entry-bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-test.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "box"
  }, [_c('div', {
    staticClass: "link-menu",
    class: {
      show: !_vm.showAdBox
    },
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.showMenuFn
    }
  }, [_c('div', {
    staticClass: "et-img bg-ad_btn"
  })]), _vm._v(" "), _c('img', {
    staticClass: "friend-rank",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/friend-rank.png",
      "eventid": '1'
    },
    on: {
      "click": _vm.toFriendRank
    }
  }), _vm._v(" "), _c('scroll-view', {
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
  }), _vm._v(" "), _vm._l((_vm.messageListStorage), function(item, index) {
    return (_vm.messageListStorage && _vm.messageListStorage.length > 0) ? _c('div', {
      key: index,
      staticClass: "tag-inner flex",
      attrs: {
        "id": 'id' + index
      }
    }, [(item.type == 0) ? _c('img', {
      attrs: {
        "src": item.headImgUrl
      }
    }) : _vm._e(), _vm._v(" "), (item.type == 0) ? _c('div', [_vm._v(":")]) : _vm._e(), _vm._v(" "), (item.type == 0) ? _c('div', {
      staticClass: "tag-txt"
    }, [_vm._v("\n            " + _vm._s(item.message) + "\n          ")]) : _vm._e(), _vm._v(" "), (item.type == 1) ? _c('div', {
      staticClass: "top flex"
    }, [_vm._v("\n            恭喜\n            "), _c('img', {
      attrs: {
        "src": item.headImgUrl
      }
    }), _vm._v(" "), _c('div', [_vm._v(_vm._s(item.nickname))]), _vm._v("\n            登顶\n            "), _c('img', {
      attrs: {
        "src": _vm.IMGHEAD + item.highParagraphImage
      }
    }), _vm._v(" "), _c('div', [_vm._v("最强王者")])]) : _vm._e()]) : _vm._e()
  })], 2), _vm._v(" "), _c('div', {
    staticClass: "text pos-a flex left-center"
  }, [_vm._v("今天我答对了\n        "), _c('div', [_vm._v(_vm._s(_vm.rightNum) + "题")])]), _vm._v(" "), _c('img', {
    staticClass: "hybird pos-a left-center",
    attrs: {
      "hover-class": "click-act",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/hybird.png",
      "eventid": '3'
    },
    on: {
      "click": _vm.toHybird
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "group pos-a left-center",
    attrs: {
      "hover-class": "click-act",
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newRoom/3v3.png",
      "eventid": '4'
    },
    on: {
      "click": _vm.toGroup
    }
  })], 1), _vm._v(" "), (_vm.showAdBox) ? _c('ad-box-new', {
    attrs: {
      "tryGameName": _vm.tryGameName,
      "eventid": '5',
      "mpcomid": '1'
    },
    on: {
      "close": _vm.closeAdBox
    }
  }) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "ad-wrap"
  }, [_c('ad', {
    attrs: {
      "unit-id": "adunit-c0021eb44a5dc3e2",
      "mpcomid": '2'
    }
  })], 1)], 1)
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-dd86f63e", esExports)
  }
}

/***/ })

},[344]);
//# sourceMappingURL=main.js.map