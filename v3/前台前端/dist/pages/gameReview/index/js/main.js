global.webpackJsonp([21],{

/***/ 202:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(204);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_8cc64862_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(207);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(203)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-8cc64862"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_8cc64862_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\gameReview\\index.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-8cc64862", Component.options)
  } else {
    hotAPI.reload("data-v-8cc64862", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 203:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 204:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign__ = __webpack_require__(20);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_get_iterator__ = __webpack_require__(36);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_get_iterator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_get_iterator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__config_dataFile__ = __webpack_require__(13);




//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//




//TODO
//1.一页当一个item  item包括用户信息， 当前题目分数，选项，正确与否
/* harmony default export */ __webpack_exports__["a"] = ({
  data: function data() {
    return {
      dataReady: false,
      current: 0,
      from: null,
      userList: [],

      dataToBakEnd: {}, //状态列表
      myInfo: {
        ansList: [],
        basicInfo: {}
      },
      oInfo: {
        ansList: [],
        basicInfo: {}
      },
      subjectList: [],
      likeList: [], //喜欢列表
      levelList: [],
      idiomList: [], //成语题目列表
      poetryList: [],
      imgLevelList: [],
      msgList: [],
      msgTimer: null,
      showMsgData: { userNmae: "", msg: "" },
      emoji: __WEBPACK_IMPORTED_MODULE_5__config_dataFile__["a" /* emoji */],
      initType: 1,
      showTimer: null
    };
  },

  watch: {
    msgList: function msgList(list) {
      var _this = this;

      if (list.length == 0) {
        clearInterval(this.msgTimer);
        this.msgTimer = null;
        return false;
      }
      if (list.length > 0 && this.msgTimer == null) {
        this.msgTimer = setInterval(function () {
          _this.showMsgData = list[0];
          _this.msgList.splice(0, 1);
        }, 1000);
      }
    }
  },
  computed: __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_4_vuex__["c" /* mapState */])(["gameRecord", "appInfo", "IMGHEAD", "deviceInfo"])),
  methods: __WEBPACK_IMPORTED_MODULE_3_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_4_vuex__["b" /* mapMutations */])({
    connect: "socketStore/connect",
    addMessageListener: "socketStore/addMessageListener",
    removeMessageListener: "socketStore/removeMessageListener",
    send: "socketStore/send",
    close: "socketStore/close"
  }), {
    initData: function initData(type) {
      var _this2 = this;

      var gameData = void 0;
      this.initType = 2;
      gameData = JSON.parse(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default()(this.gameRecord.Result));
      this.userList = gameData.userList;
      this.subjectList = gameData.subjectList;
      if (this.from == "room") {
        this.userList = gameData.room.userList;
        this.subjectList = gameData.game.subjectList;
      }
      // 筛选出 题目喜好
      if (type == 1) {
        for (var i = 0; i < this.subjectList.length; i++) {
          for (var k = 0; k < this.subjectList[i].likeStatusList.length; k++) {
            if (this.subjectList[i].likeStatusList[k].userId == this.appInfo.userId) {
              this.likeList[i] = {
                status: this.subjectList[i].likeStatusList[k].likeStatus,
                goodNum: this.subjectList[i].likeNum,
                badNum: this.subjectList[i].noLikeNum
              };
              this.dataToBakEnd[this.subjectList[i].id] = this.subjectList[i].likeStatusList[k].likeStatus;
            }
          }
        }
      }
      // }
      this.getRatioList(this.subjectList);
      this.classifyUserInfo(this.userList);
      this.getUserAnserInfo(this.subjectList);
      this.getIdiomList(this.subjectList);

      setTimeout(function () {
        _this2.dataReady = true;
      }, 0);
    },
    handMessage: function handMessage(message) {
      if (message.Type == 15) {
        var emojiInfo = JSON.parse(message.Result);
        this.msgList.push({
          userName: emojiInfo.userName,
          msg: this.emoji[emojiInfo.index].title
        });
        // this.showEmoji(message);
      }
    },
    swiperChange: function swiperChange(event) {
      this.current = event.mp.detail.current;
    },
    pre: function pre() {
      if (this.current != 0) {
        this.current--;
      }
    },
    next: function next() {
      if (this.current != this.subjectList.length - 1) {
        this.current++;
      }
    },

    // 获取题目难度列表
    getRatioList: function getRatioList(list) {
      try {
        var _iteratorNormalCompletion = true;
        var _didIteratorError = false;
        var _iteratorError = undefined;

        try {
          for (var _iterator = __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_get_iterator___default()(list), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
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
      } catch (err) {}
    },

    //序列化成语题目 和 诗词
    getIdiomList: function getIdiomList(list) {
      // 判断成语题 生成题目列表
      for (var i = 0; i < list.length; i++) {
        if (list[i].modeType == 5) {
          var wordList = list[i].title.replace("_", "?").split("");
          this.idiomList.push(wordList);
        } else {
          this.idiomList.push(0);
        }
        if (list[i].modeType == 7 || list[i].modeType == 8 || list[i].modeType == 9 || list[i].modeType == 10) {
          var arr = list[i].title.replace("_", "?").split("");
          this.poetryList.push(arr);
        } else {
          this.poetryList.push(0);
        }
      }
    },
    classifyUserInfo: function classifyUserInfo(userList) {
      for (var i = 0; i < userList.length; i++) {
        //分类用户信息
        if (this.appInfo.userId == userList[i].basicInfo.userId) {
          this.myInfo = userList[i];
          this.myInfo.ansList = [{ answer: -1, right: false }, { answer: -1, right: false }, { answer: -1, right: false }, { answer: -1, right: false }, { answer: -1, right: false }];
        } else {
          this.oInfo = userList[i];
          this.oInfo.ansList = [{ answer: -1, right: false }, { answer: -1, right: false }, { answer: -1, right: false }, { answer: -1, right: false }, { answer: -1, right: false }];
          if (userList[i].boot) {
            //机器人头像地址 加域名
            this.oInfo.basicInfo.avatarUrl = this.IMGHEAD + this.oInfo.basicInfo.avatarUrl;
          }
        }
      }
    },

    // 获取双方答题信息
    getUserAnserInfo: function getUserAnserInfo(list) {
      for (var i = 0; i < list.length; i++) {
        //题目列表 没有回答数据
        if (list[i].answerList) {
          for (var j = 0; j < list[i].answerList.length; j++) {
            //题目里的回答列表
            if (list[i].answerList[j].uid == this.appInfo.userId) {
              //分类推入 我的 或者 他的对象中
              this.myInfo.ansList[i] = list[i].answerList[j];
            } else {
              this.oInfo.ansList[i] = list[i].answerList[j];
            }
          }
        }
      }
    },
    updateLikeStatu: function updateLikeStatu(statusType) {
      if (this.likeList[this.current].status != statusType) {
        if (statusType == 1) {
          this.$set(this.likeList, this.current, __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default()(this.likeList[this.current], {
            goodNum: this.likeList[this.current].goodNum + 1
          }));
          if (this.likeList[this.current].status == 0) {
            this.$set(this.likeList, this.current, __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default()(this.likeList[this.current], {
              badNum: this.likeList[this.current].badNum - 1
            }));
          }
        } else {
          this.$set(this.likeList, this.current, __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default()(this.likeList[this.current], {
            badNum: this.likeList[this.current].badNum + 1
          }));
          if (this.likeList[this.current].status == 1) {
            this.$set(this.likeList, this.current, __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default()(this.likeList[this.current], {
              goodNum: this.likeList[this.current].goodNum - 1
            }));
          }
        }
        this.$set(this.likeList, this.current, __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default()(this.likeList[this.current], { status: statusType }));
        this.dataToBakEnd[this.subjectList[this.current].id] = statusType;
      } else {
        if (statusType == 1) {
          this.$set(this.likeList, this.current, __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default()(this.likeList[this.current], {
            goodNum: this.likeList[this.current].goodNum - 1
          }));
        } else {
          this.$set(this.likeList, this.current, __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default()(this.likeList[this.current], {
            badNum: this.likeList[this.current].badNum - 1
          }));
        }
        this.$set(this.likeList, this.current, __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default()(this.likeList[this.current], { status: -1 }));
        this.dataToBakEnd[this.subjectList[this.current].id] = -1;
      }
    },
    updateLikeList: function updateLikeList() {
      var handle = false;
      for (var item in this.dataToBakEnd) {
        if (item != -1) {
          handle = true;
        } else {
          handle = false;
        }
      }

      if (handle) {
        this.fly.post("/subject/updateLikeStatus", {
          likeList: __WEBPACK_IMPORTED_MODULE_2_babel_runtime_core_js_json_stringify___default()(this.dataToBakEnd).toString()
        }).then(function (res) {
          console.log("更新喜好列表", res);
        });
      }
    },
    throttle: function throttle(fn, delay) {
      clearTimeout(this.timer);
      this.timer = setTimeout(function () {
        fn();
      }, delay);
    },
    throttleProxy: function throttleProxy(str) {
      if (str == 1) {
        this.throttle(this.pre, 200);
      } else {
        this.throttle(this.next, 200);
      }
    },
    advice: function advice() {
      this.$router.push("/pages/questionAdvice/index?current=" + this.current + "&&from=" + this.from);
    }
  }),
  onHide: function onHide() {
    this.updateLikeList();
  },
  onUnload: function onUnload() {
    var _this3 = this;

    this.current = 0;
    this.idiomList = [];
    this.subjectList = [];
    this.levelList = [];
    this.idiomList = []; //成语题目列表
    this.imgLevelList = [];
    this.headList = [[], [], [], []];
    this.updateLikeList();
    this.dataToBakEnd = {};
    this.showTimer = setTimeout(function () {
      _this3.initType = 1;
    }, 30000);
    this.dataReady = false;
  },
  mounted: function mounted() {},
  onShow: function onShow() {
    clearTimeout(this.showTimer);
    this.from = this.$root.$mp.query.from;
    this.initData(this.initType);
  }
});

/***/ }),

/***/ 207:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "wrap sky-bg"
  }, [_c('div', {
    staticClass: "desc"
  }, [_c('img', {
    staticClass: "title",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/title.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "advice",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.advice
    }
  }, [_c('img', {
    attrs: {
      "src": "/static/icon/gantan.png",
      "alt": ""
    }
  }), _vm._v(" 题目纠错\n  ")]), _vm._v(" "), _c('div', {
    staticClass: "like-box"
  }, [(_vm.likeList[_vm.current]) ? _c('div', {
    attrs: {
      "eventid": '1'
    },
    on: {
      "click": function($event) {
        _vm.updateLikeStatu(1)
      }
    }
  }, [(_vm.likeList[_vm.current] && _vm.likeList[_vm.current].status == 1) ? _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/gp.png"
    }
  }) : _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/gp-null.png"
    }
  }), _vm._v(" (" + _vm._s(_vm.likeList[_vm.current].goodNum) + ")\n    ")]) : _vm._e(), _vm._v(" "), (_vm.likeList[_vm.current]) ? _c('div', {
    attrs: {
      "eventid": '2'
    },
    on: {
      "click": function($event) {
        _vm.updateLikeStatu(0)
      }
    }
  }, [(_vm.likeList[_vm.current] && _vm.likeList[_vm.current].status == 0) ? _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/bp.png"
    }
  }) : _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/icon/bp-null.png"
    }
  }), _vm._v("  (" + _vm._s(_vm.likeList[_vm.current].badNum) + ")\n    ")]) : _vm._e()]), _vm._v(" "), (_vm.dataReady) ? _c('div', {
    staticClass: "desc-text"
  }, [_vm._v(_vm._s(_vm.subjectList[_vm.current].description))]) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "quesIndex"
  }, [_c('div', {
    staticClass: "q-txt"
  }, [_vm._v("\n      第" + _vm._s(_vm.current + 1) + "/" + _vm._s(_vm.subjectList.length) + "题\n    ")])]), _vm._v(" "), (_vm.dataReady) ? _c('div', {
    staticClass: "swiper-wrap"
  }, [_c('img', {
    staticClass: "left-btn",
    attrs: {
      "src": "/static/arrow.png",
      "eventid": '3'
    },
    on: {
      "click": function($event) {
        _vm.throttleProxy(1)
      }
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "right-btn",
    attrs: {
      "src": "/static/arrow.png",
      "eventid": '4'
    },
    on: {
      "click": function($event) {
        _vm.throttleProxy(2)
      }
    }
  }), _vm._v(" "), _c('swiper', {
    attrs: {
      "current": _vm.current,
      "eventid": '5'
    },
    on: {
      "animationfinish": _vm.swiperChange
    }
  }, _vm._l((_vm.subjectList), function(subject, index) {
    return _c('swiper-item', {
      key: index,
      attrs: {
        "mpcomid": '0-' + index
      }
    }, [_c('div', {
      staticClass: "box-top"
    }, [(subject.modeType == 3 || subject.modeType == 4 || subject.modeType == 6) ? _c('div', {
      staticClass: "m-word-title",
      class: {
        titleIn: _vm.showTitle, english: subject.modeType == 6
      }
    }, [_vm._v("\n            " + _vm._s(subject.title) + "\n          ")]) : _vm._e(), _vm._v(" "), (subject.modeType == 5) ? _c('div', {
      staticClass: "m-idiom",
      class: {
        titleIn: _vm.showTitle
      }
    }, [_c('div', {
      staticClass: "u-word-box"
    }, _vm._l((_vm.idiomList[_vm.current]), function(item, wordIndex) {
      return _c('div', {
        key: wordIndex,
        staticClass: "u-word"
      }, [_vm._v(_vm._s(item))])
    }))]) : _vm._e(), _vm._v(" "), (subject.modeType == 1) ? _c('div', {
      staticClass: "m-img"
    }, [_c('div', {
      staticClass: "img-title"
    }, [_vm._v("\n              " + _vm._s(subject.title) + "\n            ")]), _vm._v(" "), _c('div', {
      staticClass: "img-box"
    }, [_c('img', {
      attrs: {
        "mode": "aspectFit",
        "src": _vm.IMGHEAD + subject.image,
        "alt": ""
      }
    })])]) : _vm._e(), _vm._v(" "), (subject.modeType == 7 || subject.modeType == 8 || subject.modeType == 9 || subject.modeType == 10) ? _c('div', {
      staticClass: "m-poetry",
      class: {
        'm-poetry-reverce': (subject.modeType == 8 || subject.modeType == 10), titleIn: _vm.showTitle
      }
    }, [_c('div', {
      staticClass: "u-sentence"
    }, [_vm._v(_vm._s(subject.title) + "\n              "), (subject.modeType == 7 || subject.modeType == 9) ? _c('div', {
      staticClass: "u-symbol"
    }, [_vm._v(",")]) : _c('div', {
      staticClass: "u-symbol"
    }, [_vm._v("。")])]), _vm._v(" "), _c('div', {
      staticClass: "u-line"
    }, [_vm._l((_vm.poetryList[_vm.current]), function(poetry, poetryIndex) {
      return _c('div', {
        key: poetryIndex,
        staticClass: "u-line-item"
      })
    }), _vm._v(" "), (subject.modeType == 7 || subject.modeType == 9) ? _c('div', {
      staticClass: "u-symbol"
    }, [_vm._v("。")]) : _c('div', {
      staticClass: "u-symbol"
    }, [_vm._v(",")])], 2)]) : _vm._e()]), _vm._v(" "), _c('div', {
      staticClass: "select-list"
    }, _vm._l((subject.optionList), function(item, indexKey) {
      return _c('div', {
        key: indexKey,
        staticClass: "select-item",
        class: {
          'judge-type': subject.modeType == 4, success: item.sortId == subject.optionSortId, fail: ((!_vm.myInfo.ansList[_vm.current].right && _vm.myInfo.ansList[_vm.current].answer == item.sortId) || (!_vm.oInfo.ansList[_vm.current].right && _vm.oInfo.ansList[_vm.current].answer == item.sortId && !_vm.from))
        }
      }, [(_vm.myInfo.ansList[_vm.current].right && _vm.myInfo.ansList[_vm.current].answer == item.sortId) ? _c('div', {
        staticClass: "yes-arrow-l"
      }) : _vm._e(), _vm._v(" "), (!_vm.myInfo.ansList[_vm.current].right && _vm.myInfo.ansList[_vm.current].answer == item.sortId) ? _c('div', {
        staticClass: "err-arrow-l"
      }) : _vm._e(), _vm._v(" "), _c('div', {
        staticClass: "select-item-txt"
      }, [(_vm.myInfo.ansList[_vm.current].right && _vm.myInfo.ansList[_vm.current].answer == item.sortId && subject.modeType != 4) ? _c('img', {
        staticClass: "left-img yes",
        attrs: {
          "src": "/static/rank/yes.png",
          "alt": ""
        }
      }) : _vm._e(), _vm._v(" "), (!_vm.myInfo.ansList[_vm.current].right && _vm.myInfo.ansList[_vm.current].answer == item.sortId && subject.modeType != 4) ? _c('img', {
        staticClass: "left-img err",
        attrs: {
          "src": "/static/rank/error.png",
          "alt": ""
        }
      }) : _vm._e(), _vm._v(" "), (subject.modeType != 4) ? _c('div', {
        staticClass: "txt"
      }, [_vm._v("\n                " + _vm._s(item.name) + "\n              ")]) : _vm._e(), _vm._v(" "), (subject.modeType == 4 && item.name == '对' && (_vm.myInfo.ansList[_vm.current].answer != item.sortId && _vm.oInfo.ansList[_vm.current].answer != item.sortId)) ? _c('img', {
        staticClass: "select-img select-yes",
        attrs: {
          "src": "/static/rank/b_yes.png",
          "alt": ""
        }
      }) : _vm._e(), _vm._v(" "), (subject.modeType == 4 && item.name == '错' && (_vm.myInfo.ansList[_vm.current].answer != item.sortId && _vm.oInfo.ansList[_vm.current].answer != item.sortId)) ? _c('img', {
        staticClass: "select-img select-err",
        attrs: {
          "src": "/static/rank/b_error.png",
          "alt": ""
        }
      }) : _vm._e(), _vm._v(" "), (subject.modeType == 4 && item.name == '对' && (_vm.myInfo.ansList[_vm.current].answer == item.sortId || _vm.oInfo.ansList[_vm.current].answer == item.sortId || item.sortId == subject.optionSortId)) ? _c('img', {
        staticClass: "select-img select-yes",
        attrs: {
          "src": "/static/rank/yes_w.png",
          "alt": ""
        }
      }) : _vm._e(), _vm._v(" "), (subject.modeType == 4 && item.name == '错' && (_vm.myInfo.ansList[_vm.current].answer == item.sortId || _vm.oInfo.ansList[_vm.current].answer == item.sortId || item.sortId == subject.optionSortId)) ? _c('img', {
        staticClass: "select-img select-err",
        attrs: {
          "src": "/static/rank/error_w.png",
          "alt": ""
        }
      }) : _vm._e(), _vm._v(" "), (_vm.oInfo.ansList[_vm.current].right && _vm.oInfo.ansList[_vm.current].answer == item.sortId && subject.modeType != 4 && !_vm.from) ? _c('img', {
        staticClass: "right-img yes",
        attrs: {
          "src": "/static/rank/yes.png",
          "alt": ""
        }
      }) : _vm._e(), _vm._v(" "), (!_vm.oInfo.ansList[_vm.current].right && _vm.oInfo.ansList[_vm.current].answer == item.sortId && subject.modeType != 4 && !_vm.from) ? _c('img', {
        staticClass: "right-img err",
        attrs: {
          "src": "/static/rank/error.png",
          "alt": ""
        }
      }) : _vm._e()]), _vm._v(" "), (!_vm.from && _vm.oInfo.ansList[_vm.current].right && _vm.oInfo.ansList[_vm.current].answer == item.sortId) ? _c('div', {
        staticClass: "yes-arrow-r"
      }) : _vm._e(), _vm._v(" "), (!_vm.from && !_vm.oInfo.ansList[_vm.current].right && _vm.oInfo.ansList[_vm.current].answer == item.sortId) ? _c('div', {
        staticClass: "err-arrow-r"
      }) : _vm._e()])
    }))])
  }))], 1) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "creat-info",
    class: {
      'showCreat': true
    }
  }, [_vm._v("\n    ——本题由\n    "), (_vm.subjectList[_vm.current] && _vm.subjectList[_vm.current].createUserUrl) ? _c('img', {
    attrs: {
      "src": _vm.subjectList[_vm.current].createUserUrl
    }
  }) : _c('img', {
    attrs: {
      "src": "/static/default-head.png"
    }
  }), _vm._v(" "), (_vm.subjectList[_vm.current] && _vm.subjectList[_vm.current].createUserName) ? _c('div', [_vm._v(_vm._s(_vm.subjectList[_vm.current].createUserName))]) : _vm._e(), _vm._v(" 贡献——\n  ")])])
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-8cc64862", esExports)
  }
}

/***/ })

},[336]);
//# sourceMappingURL=main.js.map