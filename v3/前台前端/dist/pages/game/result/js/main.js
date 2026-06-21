global.webpackJsonp([20],{

/***/ 263:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_result_vue__ = __webpack_require__(265);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_d64d4e30_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_result_vue__ = __webpack_require__(266);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(264)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-d64d4e30"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_result_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_d64d4e30_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_result_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\game\\result.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] result.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-d64d4e30", Component.options)
  } else {
    hotAPI.reload("data-v-d64d4e30", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 264:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 265:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign__ = __webpack_require__(20);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_regenerator__ = __webpack_require__(18);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_regenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_regenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_asyncToGenerator__ = __webpack_require__(19);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_asyncToGenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_asyncToGenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_promise__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_promise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_promise__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__utils_index__ = __webpack_require__(9);





//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
  onShareAppMessage: function onShareAppMessage(res) {
    return {
      title: "快来看我智战群雄",
      path: "/pages/entry/index?userId=" + this.appInfo.userId
    };
  },
  data: function data() {
    return {
      myInfo: {},
      otherInfo: {},
      myAnsList: [{ score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }],
      otherAnsList: [{ score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }],
      questionType: {
        1: "德",
        2: "商",
        3: "文",
        4: "理",
        5: "艺",
        6: "体",
        7: "综"
      },
      oRun: false,
      selectList: [{
        type: "1",
        text: "对方疑似作弊"
      }, {
        type: "2",
        text: "对方头像和昵称违规"
      }],
      adviceType: "1",
      dataReady: false,
      showModal: false
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_5_vuex__["c" /* mapState */])(["IMGHEAD", "gameRecord", "appInfo"])),
  methods: {
    getGIft: function getGIft() {
      this.fly.post("rank/receiveGood").then(function (res) {
        console.log(res);
      });
    },
    getRunType: function getRunType(key) {
      var _this = this;

      return new __WEBPACK_IMPORTED_MODULE_3_babel_runtime_core_js_promise___default.a(function (resolve, reject) {
        wx.getStorage({
          key: key,
          success: function success(res) {
            _this.oRun = res.data.oRun;
            resolve(res.data);
          },
          fail: function fail(res) {
            reject("获取缓存失败");
          }
        });
      });
    },
    getResult: function getResult() {
      var subject = this.gameRecord.Result.subjectList;
      var userList = this.gameRecord.Result.userList;

      for (var u = 0; u < userList.length; u++) {
        if (userList[u].basicInfo.userId == this.appInfo.userId) {
          this.myInfo = userList[u];
        } else {
          this.otherInfo = userList[u];
        }

        for (var i = 0; i < subject.length; i++) {
          if (subject[i].answerList) {
            for (var j = 0; j < subject[i].answerList.length; j++) {
              if (userList[u].basicInfo.userId == subject[i].answerList[j].uid && userList[u].basicInfo.userId == this.appInfo.userId) {
                this.myAnsList[i].score = subject[i].answerList[j].score;
                this.myAnsList[i].bookScore = subject[i].answerList[j].bookScore;

                if (subject[i].answerList[j].right) {
                  this.myAnsList[i].type = 1;
                } else if (!subject[i].answerList[j].right && subject[i].answerList[j].answer != "-1") {
                  this.myAnsList[i].type = 2;
                }
              } else if (userList[u].basicInfo.userId == subject[i].answerList[j].uid && userList[u].basicInfo.userId != this.appInfo.userId) {
                this.otherAnsList[i].score = subject[i].answerList[j].score;
                this.otherAnsList[i].bookScore = subject[i].answerList[j].bookScore;
                if (subject[i].answerList[j].right) {
                  this.otherAnsList[i].type = 1;
                } else if (!subject[i].answerList[j].right && subject[i].answerList[j].answer != "-1") {
                  this.otherAnsList[i].type = 2;
                }
              }
            }
          }
        }
      }
      this.dataReady = true;
    },
    toReview: function toReview() {
      this.$router.push("/pages/gameReview/index");
    },
    toBook: function toBook() {
      this.$router.push("/pages/book/index");
    },
    reStart: function reStart() {
      // this.fly.post('/rank/beforeMatch').then(res=>{
      //    if(res.data.Result.isBegin==1){
      //        this.$router.replace("/pages/game/index?rankGame=true&&isNew=true");
      //     }else{
      //       wx.showToast({title:"体力不足",icon:"none"});
      //     }
      // })
      this.$router.back();
    },
    selectAdvice: function selectAdvice(item) {
      this.adviceType = item.type;
    },
    submitAdvice: function submitAdvice() {
      var _this2 = this;

      this.fly.post("/feedBackUser", {
        userId: this.otherInfo.basicInfo.userId,
        types: this.adviceType
      }).then(function (res) {
        wx.showToast({
          title: "提交成功,谢谢！",
          icon: "none"
        });
        _this2.showModal = false;
      });
    }
  },
  mounted: function mounted() {
    var _this3 = this;

    return __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_asyncToGenerator___default()( /*#__PURE__*/__WEBPACK_IMPORTED_MODULE_1_babel_runtime_regenerator___default.a.mark(function _callee() {
      return __WEBPACK_IMPORTED_MODULE_1_babel_runtime_regenerator___default.a.wrap(function _callee$(_context) {
        while (1) {
          switch (_context.prev = _context.next) {
            case 0:
              _context.next = 2;
              return _this3.getResult("rankGameResult");

            case 2:
              _context.next = 4;
              return _this3.getRunType("rankGameResult");

            case 4:
            case "end":
              return _context.stop();
          }
        }
      }, _callee, _this3);
    }))();
  },
  onLoad: function onLoad(options) {
    dataStack.push(__WEBPACK_IMPORTED_MODULE_4_babel_runtime_helpers_extends___default()({}, this.$data));
    console.log("dataStack", dataStack);
  },
  onUnload: function onUnload() {
    this.dataReady = false;
    __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_object_assign___default()(this.$data, dataStack.pop());
    this.myAnsList = [{ score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }];
    this.otherAnsList = [{ score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }, { score: 0, type: 0, bookScore: 0 }];
    Object(__WEBPACK_IMPORTED_MODULE_6__utils_index__["b" /* clearStorage */])("rankGameResult");
  }
});

/***/ }),

/***/ 266:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return (_vm.dataReady) ? _c('div', {
    staticClass: "result sky-bg-3"
  }, [_c('div', {
    staticClass: "inner"
  }, [_c('div', {
    staticClass: "r-head"
  }, [_c('div', {
    staticClass: "r-user-head",
    class: {
      'success-type': _vm.myInfo.abilityInfo.score > _vm.otherInfo.abilityInfo.score || _vm.oRun
    }
  }, [_c('img', {
    staticClass: "crown",
    attrs: {
      "src": "/static/crown.png"
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "r-user-img",
    attrs: {
      "src": _vm.myInfo.basicInfo.avatarUrl,
      "alt": ""
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "r-user-name"
  }, [_vm._v(_vm._s(_vm.myInfo.basicInfo.nickName))]), _vm._v(" "), _c('div', {
    staticClass: "r-user-score"
  }, [_vm._v(_vm._s(_vm.myInfo.abilityInfo.score || 0))])]), _vm._v(" "), _c('div', {
    staticClass: "r-r"
  }, [(_vm.myInfo.abilityInfo.score > _vm.otherInfo.abilityInfo.score || _vm.oRun) ? _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/success-head.png",
      "alt": ""
    }
  }) : _vm._e(), _vm._v(" "), (_vm.myInfo.abilityInfo.score < _vm.otherInfo.abilityInfo.score) ? _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/fail-head.png",
      "alt": ""
    }
  }) : _vm._e(), _vm._v(" "), ((_vm.myInfo.abilityInfo.score == _vm.otherInfo.abilityInfo.score) && !_vm.oRun) ? _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/balance-head.png",
      "alt": ""
    }
  }) : _vm._e(), _vm._v(" "), (_vm.myInfo.abilityInfo.continueWinNum > 1) ? _c('div', [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/feather_l.png",
      "alt": ""
    }
  }), _vm._v(" " + _vm._s(_vm.myInfo.abilityInfo.continueWinNum) + "连胜\n          "), _c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/rank/feather_r.png",
      "alt": ""
    }
  })]) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "r-user-head",
    class: {
      'success-type': _vm.otherInfo.abilityInfo.score > _vm.myInfo.abilityInfo.score
    }
  }, [_c('img', {
    staticClass: "crown",
    attrs: {
      "src": "/static/crown.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "r-user-img-box"
  }, [(_vm.otherInfo.boot) ? _c('img', {
    staticClass: "r-user-img",
    attrs: {
      "src": _vm.IMGHEAD + _vm.otherInfo.basicInfo.avatarUrl
    }
  }) : _c('img', {
    staticClass: "r-user-img",
    attrs: {
      "src": _vm.otherInfo.basicInfo.avatarUrl
    }
  }), _vm._v(" "), (_vm.oRun) ? _c('div', {
    staticClass: "r-user-run"
  }, [_vm._v("逃跑")]) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "r-user-name"
  }, [_vm._v(_vm._s(_vm.otherInfo.basicInfo.nickName))]), _vm._v(" "), _c('div', {
    staticClass: "r-user-score"
  }, [_vm._v(_vm._s(_vm.otherInfo.abilityInfo.score || 0))])])]), _vm._v(" "), _c('div', {
    staticClass: "game-table"
  }, [_c('div', {
    staticClass: "t-head"
  }, [_c('img', {
    attrs: {
      "src": _vm.IMGHEAD + _vm.myInfo.abilityInfo.paragraphImage
    }
  }), _vm._v(" " + _vm._s(_vm.myInfo.abilityInfo.paragraphName) + "\n        "), _c('div', {
    staticClass: "vs"
  }, [_vm._v("VS")]), _vm._v("\n        " + _vm._s(_vm.otherInfo.abilityInfo.paragraphName) + "\n        "), _c('img', {
    attrs: {
      "src": _vm.IMGHEAD + _vm.otherInfo.abilityInfo.paragraphImage
    }
  })]), _vm._v(" "), _vm._l((_vm.myAnsList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "t-line"
    }, [_c('div', [(item.type == 1) ? _c('img', {
      attrs: {
        "src": "/static/yes.png"
      }
    }) : _vm._e(), _vm._v(" "), (item.type == 2) ? _c('img', {
      attrs: {
        "src": "/static/err.png"
      }
    }) : _vm._e(), _vm._v(" "), (item.type == 0) ? _c('img', {
      attrs: {
        "src": "/static/none.png"
      }
    }) : _vm._e(), _vm._v(" "), _c('div', {
      staticClass: "t-score"
    }, [_vm._v(_vm._s(item.score) + "\n            "), (item.score > 0) ? _c('div', {
      staticClass: "b-score"
    }, [_vm._v("+" + _vm._s(item.bookScore))]) : _vm._e()])]), _vm._v(" "), _c('div', [_c('div', {
      staticClass: "t-score"
    }, [_vm._v(_vm._s(_vm.otherAnsList[index].score) + "\n            "), (_vm.otherAnsList[index].score > 0) ? _c('div', {
      staticClass: "b-score"
    }, [_vm._v("+" + _vm._s(_vm.otherAnsList[index].bookScore))]) : _vm._e()]), _vm._v(" "), (_vm.otherAnsList[index].type == 1) ? _c('img', {
      attrs: {
        "src": "/static/yes.png"
      }
    }) : _vm._e(), _vm._v(" "), (_vm.otherAnsList[index].type == 2) ? _c('img', {
      attrs: {
        "src": "/static/err.png"
      }
    }) : _vm._e(), _vm._v(" "), (_vm.otherAnsList[index].type == 0) ? _c('img', {
      attrs: {
        "src": "/static/none.png"
      }
    }) : _vm._e()])])
  }), _vm._v(" "), _c('div', {
    staticClass: "t-divition"
  }, [_c('div', [_vm._v(_vm._s(_vm.questionType[_vm.gameRecord.Result.subjectList[0].parentType]))]), _vm._v(" "), _c('div', [_vm._v(_vm._s(_vm.questionType[_vm.gameRecord.Result.subjectList[1].parentType]))]), _vm._v(" "), _c('div', [_vm._v(_vm._s(_vm.questionType[_vm.gameRecord.Result.subjectList[2].parentType]))]), _vm._v(" "), _c('div', [_vm._v(_vm._s(_vm.questionType[_vm.gameRecord.Result.subjectList[3].parentType]))]), _vm._v(" "), _c('div', [_vm._v(_vm._s(_vm.questionType[_vm.gameRecord.Result.subjectList[4].parentType]))])])], 2)]), _vm._v(" "), _c('div', {
    staticClass: "btn-box"
  }, [_c('div', {
    staticClass: "btn-game",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.reStart
    }
  }, [_vm._v("\n      再玩一局\n    ")]), _vm._v(" "), (!_vm.oRun) ? _c('div', {
    staticClass: "btn-back",
    attrs: {
      "eventid": '1'
    },
    on: {
      "click": _vm.toReview
    }
  }, [_vm._v("\n      本局回顾\n    ")]) : _vm._e(), _vm._v(" "), (_vm.myInfo.abilityInfo.score <= _vm.otherInfo.abilityInfo.score) ? _c('div', {
    staticClass: "btn-book",
    attrs: {
      "eventid": '2'
    },
    on: {
      "click": _vm.toBook
    }
  }, [_vm._v("\n      知识升级\n    ")]) : _vm._e(), _vm._v(" "), (_vm.myInfo.abilityInfo.score > _vm.otherInfo.abilityInfo.score) ? _c('button', {
    staticClass: "btn-share",
    attrs: {
      "plain": "true",
      "open-type": "share"
    }
  }, [_vm._v("\n      分享战绩\n    ")]) : _vm._e()], 1), _vm._v(" "), _c('div', {
    staticClass: "ad-box"
  }, [_c('ad', {
    attrs: {
      "unit-id": "adunit-aa03c7a8d0838a81",
      "mpcomid": '0'
    }
  })], 1), _vm._v(" "), (_vm.showModal) ? _c('div', {
    staticClass: "advice-modal"
  }, [_c('div', {
    staticClass: "mask"
  }), _vm._v(" "), _c('div', {
    staticClass: "modal"
  }, [_c('div', {
    staticClass: "close",
    attrs: {
      "eventid": '3'
    },
    on: {
      "click": function($event) {
        _vm.showModal = false
      }
    }
  }), _vm._v(" "), _vm._m(0), _vm._v(" "), _c('div', {
    staticClass: "content"
  }, [_vm._v("请您选择相应的举报类型,我们会尽快审查和处理,您的反馈是对我们最大的支持。")]), _vm._v(" "), _vm._l((_vm.selectList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "select-item",
      attrs: {
        "eventid": '4-' + index
      },
      on: {
        "click": function($event) {
          _vm.selectAdvice(item)
        }
      }
    }, [_c('div', {
      staticClass: "select-box"
    }, [(_vm.adviceType == item.type) ? _c('img', {
      attrs: {
        "src": "/static/gou.png",
        "alt": ""
      }
    }) : _vm._e()]), _vm._v("\n        " + _vm._s(item.text) + "\n      ")])
  }), _vm._v(" "), _c('div', {
    staticClass: "advice-submit",
    attrs: {
      "eventid": '5'
    },
    on: {
      "click": _vm.submitAdvice
    }
  }, [_vm._v("提交")])], 2)]) : _vm._e()]) : _vm._e()
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "head"
  }, [_vm._v("\n        举报玩家\n        "), _c('img', {
    attrs: {
      "src": "/static/modal-bg.png",
      "alt": ""
    }
  })])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-d64d4e30", esExports)
  }
}

/***/ })

},[340]);
//# sourceMappingURL=main.js.map