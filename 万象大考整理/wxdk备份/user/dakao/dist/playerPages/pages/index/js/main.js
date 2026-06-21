global.webpackJsonp([4],{

/***/ 326:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(328);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_8b6fa102_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(330);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(327)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-8b6fa102"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_8b6fa102_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\playerPages\\pages\\index.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-8b6fa102", Component.options)
  } else {
    hotAPI.reload("data-v-8b6fa102", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 327:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 328:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__api_singleApi__ = __webpack_require__(329);

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
    return {
      title: "呔！晒出你的战绩来",
      path: "/pages/entry/index?userId=" + this.appInfo.userId
    };
  },
  data: function data() {
    return {
      dataReady: false,
      userId: 0,
      current: 0,
      expStyle: "",
      userInfo: {},
      headList: ["竞技数据", "战斗力", "赛季历史", "对战历史"],
      hisData: [],
      fightingNum: [],
      seasonData: [],
      totalFight: 0,
      fightList: [{ pType: 3, type: "文", num: 0 }, { pType: 4, type: "理", num: 0 }, { pType: 5, type: "艺", num: 0 }, { pType: 6, type: "体", num: 0 }, { pType: 2, type: "商", num: 0 }, { pType: 7, type: "综", num: 0 }],
      seasonList: [{
        paragraph: 11,
        paragraphImage: "/vientianeTestParagraphImage/2bb946f9-8831-4109-8b7c-e922a5895895.jpg",
        paragraphName: "不屈白银Ⅴ",
        seasonTime: "2018-11"
      }, {
        paragraph: 11,
        paragraphImage: "/vientianeTestParagraphImage/2bb946f9-8831-4109-8b7c-e922a5895895.jpg",
        paragraphName: "不屈白银Ⅴ",
        seasonTime: "2018-12"
      }, {
        paragraph: 11,
        paragraphImage: "/vientianeTestParagraphImage/2bb946f9-8831-4109-8b7c-e922a5895895.jpg",
        paragraphName: "不屈白银Ⅴ",
        seasonTime: "2019-01"
      }, {
        paragraph: 11,
        paragraphImage: "/vientianeTestParagraphImage/2bb946f9-8831-4109-8b7c-e922a5895895.jpg",
        paragraphName: "不屈白银Ⅴ",
        seasonTime: "2019-02"
      }],
      nowYear: 2019,
      seasonFomateList: [[], []],
      seasonIndex: 0
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["c" /* mapState */])(["IMGHEAD", "appInfo"])),
  methods: {
    getUserInfo: function getUserInfo(id) {
      var _this = this;

      wx.showLoading({ title: "加载中", mask: true });
      __WEBPACK_IMPORTED_MODULE_2__api_singleApi__["a" /* default */].getUserInfoById({ id: id, type: 1 }).then(function (res) {
        _this.userInfo = res.Result;
        _this.expStyle = "width:" + _this.userInfo.expValue / _this.userInfo.maxExpValue * 100 + "%;";
        _this.dataReady = true;
        wx.hideLoading();
        _this.formateData();
      });
    },
    handleTabClick: function handleTabClick(index) {
      var _this2 = this;

      this.current = index;
      var defind = {
        1: this.userInfo,
        2: this.fightingNum,
        3: this.seasonData,
        4: this.hisData
      };

      if (defind[index + 1].length == 0 || defind[index + 1].size == 0) {
        wx.showLoading({ title: "加载中", mask: true });
        __WEBPACK_IMPORTED_MODULE_2__api_singleApi__["a" /* default */].getUserInfoById({ id: this.userId, type: index + 1 }).then(function (res) {
          wx.hideLoading();
          if (index + 1 == 2) {
            //战斗力
            console.log("战斗力", res);
            _this2.fightingNum = res.Result.combatList;
            _this2.fomateFightList(_this2.fightingNum);
          }

          if (index + 1 == 3) {
            _this2.seasonData = res.Result.paragraphHistoryList;
            _this2.getSeasonList(res.Result.paragraphHistoryList);
          }
          if (index + 1 == 4) {
            console.log("对局历史", res);
            _this2.formateData(res.Result.rankHistoryList);
          }
        });
      }
    },
    fomateFightList: function fomateFightList(list) {
      for (var i = 0; i < list.length; i++) {
        for (var j = 0; j < this.fightList.length; j++) {
          if (this.fightList[j].pType == list[i].parentType) {
            this.fightList[j].num = list[i].combat;
            this.totalFight += parseInt(list[i].combat);
          }
        }
      }
    },
    formateData: function formateData(data) {
      var history = [],
          formatList = [];

      for (var n = 0; n < (data && data.length); n++) {
        history.push(JSON.parse(data[n].detail));
      }
      for (var i = 0; i < data.length; i++) {
        var myInfo = {},
            otherInfo = {};
        var myAns = [2, 2, 2, 2, 2],
            otherAns = [2, 2, 2, 2, 2];

        for (var j = 0; j < history[i].userList.length; j++) {
          if (this.userId == history[i].userList[j].basicInfo.userId) {
            myInfo = history[i].userList[j];
          } else {
            otherInfo = history[i].userList[j];
          }
        }

        for (var k = 0; k < history[i].subjectList.length; k++) {
          var ans = history[i].subjectList[k].answerList;
          if (ans) {
            for (var f = 0; f < ans.length; f++) {
              if (this.userId == ans[f].uid) {
                if (ans[f].right) {
                  myAns[k] = 0; //答对
                } else if (!ans[f].right && ans[f].answer > 0) {
                  myAns[k] = 1; //答错
                }
              } else {
                if (ans[f].right) {
                  otherAns[k] = 0; //答对
                } else if (!ans[f].right && ans[f].answer > 0) {
                  otherAns[k] = 1; //答错
                }
              }
            }
          }
        }
        formatList[i] = {
          exitUserId: data[i].exitUserId || 0,
          myInfo: myInfo,
          otherInfo: otherInfo,
          myAns: myAns,
          otherAns: otherAns
        };
      }
      this.hisData = formatList;
    },
    getSeasonList: function getSeasonList(list) {
      // let list = this.seasonList;
      this.seasonFomateList = [[], []];
      for (var i = 0; i < list.length; i++) {
        if (list[i].seasonTime.indexOf("2018") > -1) {
          this.seasonFomateList[0].push(list[i]);
        } else if (list[i].seasonTime.indexOf("2019") > -1) {
          this.seasonFomateList[1].push(list[i]);
        }
      }
      for (var j = 0; j < this.seasonFomateList.length; j++) {
        for (var k = 0; k < this.seasonFomateList[j].length; k++) {
          this.seasonFomateList[j][k].year = this.seasonFomateList[j][k].seasonTime.split("-")[0];
          this.seasonFomateList[j][k].day = this.seasonFomateList[j][k].seasonTime.split("-")[1];
        }
      }
    },
    selectYear: function selectYear(year, index) {
      if (year <= this.nowYear) {
        this.seasonIndex = index;
      }
    },
    showData: function showData(index) {
      this.current = index;
    }
  },
  mounted: function mounted() {
    this.userId = this.$root.$mp.query.userId;
    this.getUserInfo(this.userId);
    this.nowYear = new Date().getFullYear();
  },
  onUnload: function onUnload() {
    this.current = 0;
    this.dataReady = false;
    this.hisData = [];
    this.userInfo = {};
    this.seasonFomateList = [[], []];
    this.fightList = [{ pType: 3, type: "文", num: 0 }, { pType: 4, type: "理", num: 0 }, { pType: 5, type: "艺", num: 0 }, { pType: 6, type: "体", num: 0 }, { pType: 2, type: "商", num: 0 }, { pType: 7, type: "综", num: 0 }];
    this.fightingNum = [];
    this.totalFight = 0;
    this.seasonData = [];
  }
});

/***/ }),

/***/ 329:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vue__ = __webpack_require__(4);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_vue__);

//零散api集合


var singleApi = {

  getUserInfoById: function getUserInfoById(_ref) {
    var id = _ref.id,
        type = _ref.type;
    //获取用户 题目工厂基础题目数据（提交 ，通过 数量等）
    return new __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise___default.a(function (resolve, reject) {
      __WEBPACK_IMPORTED_MODULE_1_vue___default.a.prototype.fly.post('/user/getUser', {
        userId: id,
        type: type
      }).then(function (res) {
        resolve(res.data);
      });
    });
  }

};

/* harmony default export */ __webpack_exports__["a"] = (singleApi);

/***/ }),

/***/ 330:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-user sky-bg-2"
  }, [_c('div', {
    staticClass: "u-user-head"
  }, [(_vm.dataReady) ? _c('img', {
    attrs: {
      "src": _vm.userInfo.avatarUrl
    }
  }) : _c('img', {
    attrs: {
      "src": "/static/default-head.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "game-info"
  }, [_c('div', {
    staticClass: "info-top"
  }, [_c('div', {
    staticClass: "name"
  }, [_vm._v(_vm._s(_vm.userInfo.nickname))]), _vm._v(" "), (_vm.userInfo.gender == '女') ? _c('img', {
    staticClass: "girl",
    attrs: {
      "src": "/static/girl.png"
    }
  }) : _c('img', {
    staticClass: "boy",
    attrs: {
      "src": "/static/boy.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "province"
  }, [_vm._v(_vm._s(_vm.userInfo.province || "宇宙"))]), _vm._v(" "), _c('div', {
    staticClass: "city"
  }, [_vm._v(_vm._s(_vm.userInfo.city || "火星"))])]), _vm._v(" "), _c('div', {
    staticClass: "info-bottom"
  }, [_vm._v("\n        等级" + _vm._s(_vm.userInfo.grade) + "\n        "), _c('div', {
    staticClass: "exp-process"
  }, [_c('div', {
    staticClass: "exp-inner",
    style: (_vm.expStyle)
  }), _vm._v(" "), _c('div', {
    staticClass: "exp-num"
  }, [_vm._v(_vm._s((_vm.userInfo.expValue || 0) + "/" + (_vm.userInfo.maxExpValue || 100)))])])])])]), _vm._v(" "), _c('div', {
    staticClass: "list"
  }, [_c('div', {
    staticClass: "list-head"
  }, _vm._l((_vm.headList), function(item, index) {
    return _c('div', {
      key: index,
      class: {
        active: index == _vm.current
      },
      attrs: {
        "eventid": '0-' + index
      },
      on: {
        "click": function($event) {
          _vm.handleTabClick(index)
        }
      }
    }, [_vm._v(_vm._s(item))])
  })), _vm._v(" "), _c('div', {
    staticClass: "info-box"
  }, [(_vm.current == 0) ? _c('div', {
    staticClass: "total-data"
  }, [_c('div', {
    staticClass: "total-head"
  }, [_c('div', {
    staticClass: "head-item"
  }, [(_vm.userInfo.paragraphImage) ? _c('img', {
    attrs: {
      "src": _vm.IMGHEAD + _vm.userInfo.paragraphImage
    }
  }) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "item-name"
  }, [_vm._v(_vm._s(_vm.userInfo.paragraphName || "无段位信息"))])]), _vm._v(" "), _c('div', {
    staticClass: "head-item"
  }, [_c('div', {
    staticClass: "item-title"
  }, [_vm._v("总场数")]), _vm._v(" "), _c('div', {
    staticClass: "item-num"
  }, [_vm._v(_vm._s(_vm.userInfo.totalNum))])]), _vm._v(" "), _c('div', {
    staticClass: "head-item"
  }, [_c('div', {
    staticClass: "item-title"
  }, [_vm._v("胜率")]), _vm._v(" "), _c('div', {
    staticClass: "item-num"
  }, [_vm._v(_vm._s(_vm.userInfo.winRatio) + "%")])]), _vm._v(" "), _c('div', {
    staticClass: "head-item"
  }, [_c('div', {
    staticClass: "item-title"
  }, [_vm._v("逃跑率")]), _vm._v(" "), _c('div', {
    staticClass: "item-num"
  }, [_vm._v(_vm._s(_vm.userInfo.exitRatio) + "%")])])]), _vm._v(" "), _c('div', {
    staticClass: "total-content"
  }, [_c('div', {
    staticClass: "content-item"
  }, [_vm._v("\n            胜局数\n            "), _c('div', [_vm._v(_vm._s(_vm.userInfo.winNum))])]), _vm._v(" "), _c('div', {
    staticClass: "content-item"
  }, [_vm._v("\n            连胜纪录\n            "), _c('div', [_vm._v(_vm._s(_vm.userInfo.maxContinueWinNum) + "胜")])]), _vm._v(" "), _c('div', {
    staticClass: "content-item"
  }, [_vm._v("\n            平局数\n            "), _c('div', [_vm._v(_vm._s(_vm.userInfo.flatNum))])]), _vm._v(" "), _c('div', {
    staticClass: "content-item"
  }, [_vm._v("\n            单局最高\n            "), _c('div', [_vm._v(_vm._s(_vm.userInfo.maxSingleScore))])]), _vm._v(" "), _c('div', {
    staticClass: "content-item"
  }, [_vm._v("\n            败局数\n            "), _c('div', [_vm._v(_vm._s(_vm.userInfo.loseNum))])]), _vm._v(" "), _c('div', {
    staticClass: "content-item"
  }, [_vm._v("\n            全对纪录\n            "), _c('div', [_vm._v(_vm._s(_vm.userInfo.maxAllRightNum))])]), _vm._v(" "), _c('div', {
    staticClass: "content-item"
  }, [_vm._v("\n            逃跑数\n            "), _c('div', [_vm._v(_vm._s(_vm.userInfo.exitNum))])]), _vm._v(" "), _c('button', {
    staticClass: "bnt-share",
    attrs: {
      "plain": "true",
      "open-type": "share"
    }
  }, [_vm._v("分享战绩")])], 1)]) : _vm._e(), _vm._v(" "), (_vm.current == 1) ? _c('div', {
    staticClass: "total-fight"
  }, [_c('div', {
    staticClass: "f-head"
  }, [_c('img', {
    attrs: {
      "src": "/static/icon/fight.png"
    }
  }), _vm._v(" " + _vm._s(_vm.totalFight) + "\n        ")]), _vm._v(" "), _c('div', {
    staticClass: "f-list"
  }, _vm._l((_vm.fightList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "f-item"
    }, [_c('div', {
      staticClass: "f-type"
    }, [_vm._v(_vm._s(item.type))]), _vm._v(" "), _c('div', {
      staticClass: "f-num"
    }, [_vm._v(_vm._s(item.num))])])
  }))]) : _vm._e(), _vm._v(" "), (_vm.current == 2) ? _c('div', {
    staticClass: "season"
  }, [_c('div', {
    staticClass: "s-head"
  }, [_c('div', {
    staticClass: "h-item sActive",
    attrs: {
      "eventid": '1'
    },
    on: {
      "click": function($event) {
        _vm.selectYear(2018, 0)
      }
    }
  }, [_vm._v("2018年")]), _vm._v(" "), _c('div', {
    staticClass: "h-item",
    class: {
      sActive: _vm.nowYear >= 2019,
    },
    attrs: {
      "eventid": '2'
    },
    on: {
      "click": function($event) {
        _vm.selectYear(2019, 1)
      }
    }
  }, [_vm._v("2019年")])]), _vm._v(" "), (_vm.seasonFomateList[_vm.seasonIndex].length > 0) ? _c('div', {
    staticClass: "s-list"
  }, _vm._l((_vm.seasonFomateList[_vm.seasonIndex]), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "s-item"
    }, [_c('div', {
      staticClass: "s-time"
    }, [_vm._v(_vm._s(item.year) + "年" + _vm._s(item.day) + "月")]), _vm._v(" "), _c('div', {
      staticClass: "s-medal"
    }, [_c('img', {
      staticClass: "medal-img",
      attrs: {
        "src": _vm.IMGHEAD + item.paragraphImage,
        "alt": ""
      }
    }), _vm._v(" "), _c('div', {
      staticClass: "medal-info"
    }, [_c('div', {
      staticClass: "medal-star"
    }, [_c('img', {
      attrs: {
        "src": "/static/star.png",
        "alt": ""
      }
    }), _vm._v(" " + _vm._s(item.star) + "\n                ")]), _vm._v(" "), _c('div', {
      staticClass: "medal-name"
    }, [_vm._v("\n                  " + _vm._s(item.paragraphName) + "\n                ")])])])])
  })) : _vm._e()]) : _vm._e(), _vm._v(" "), (_vm.current == 3) ? _c('div', {
    staticClass: "game-data"
  }, _vm._l((_vm.hisData), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "g-item"
    }, [_c('div', {
      staticClass: "g-res"
    }, [(((item.myInfo.abilityInfo.score > item.otherInfo.abilityInfo.score) && _vm.userId != item.exitUserId) || item.otherInfo.basicInfo.userId == item.exitUserId) ? _c('img', {
      staticClass: "word-s",
      attrs: {
        "src": "/static/word-success.png"
      }
    }) : _vm._e(), _vm._v(" "), ((item.myInfo.abilityInfo.score < item.otherInfo.abilityInfo.score && item.otherInfo.basicInfo.userId != item.exitUserId) || _vm.userId == item.exitUserId) ? _c('img', {
      staticClass: "word-f",
      attrs: {
        "src": "/static/word-fail.png"
      }
    }) : _vm._e(), _vm._v(" "), ((item.myInfo.abilityInfo.score == item.otherInfo.abilityInfo.score) && _vm.userId != item.exitUserId && item.otherInfo.basicInfo.userId != item.exitUserId) ? _c('img', {
      staticClass: "word-b",
      attrs: {
        "src": "/static/word-balance.png"
      }
    }) : _vm._e()]), _vm._v(" "), _c('div', {
      staticClass: "g-user g-user-left"
    }, [_c('div', {
      staticClass: "user-top"
    }, [(item.myInfo.basicInfo.avatarUrl) ? _c('img', {
      attrs: {
        "src": item.myInfo.basicInfo.avatarUrl
      }
    }) : _c('img', {
      attrs: {
        "src": "/static/default-head.png"
      }
    }), _vm._v(" "), _c('div', {
      staticClass: "top-right"
    }, [_c('div', {
      staticClass: "score"
    }, [_vm._v(_vm._s(item.myInfo.abilityInfo.score))]), _vm._v(" "), _c('div', {
      staticClass: "ans"
    }, _vm._l((item.myAns), function(ans, ansIndex) {
      return _c('div', {
        key: ansIndex,
        staticClass: "ans-item"
      }, [(ans == 0) ? _c('img', {
        attrs: {
          "src": "/static/main_yes.png"
        }
      }) : _vm._e(), _vm._v(" "), (ans == 1) ? _c('img', {
        attrs: {
          "src": "/static/main_err.png"
        }
      }) : _vm._e(), _vm._v(" "), (ans == 2) ? _c('img', {
        attrs: {
          "src": "/static/main_none.png"
        }
      }) : _vm._e()])
    }))])]), _vm._v(" "), _c('div', {
      staticClass: "user-bottom"
    }, [_c('img', {
      attrs: {
        "src": _vm.IMGHEAD + item.myInfo.abilityInfo.paragraphImage
      }
    }), _vm._v(" " + _vm._s(item.myInfo.basicInfo.nickName) + "\n            ")])]), _vm._v(" "), _c('img', {
      staticClass: "g-vs",
      attrs: {
        "src": "/static/vs2.png"
      }
    }), _vm._v(" "), _c('div', {
      staticClass: "g-user g-user-left"
    }, [_c('div', {
      staticClass: "user-top"
    }, [(item.otherInfo.boot) ? _c('img', {
      attrs: {
        "src": _vm.IMGHEAD + item.otherInfo.basicInfo.avatarUrl
      }
    }) : _c('img', {
      attrs: {
        "src": item.otherInfo.basicInfo.avatarUrl
      }
    }), _vm._v(" "), _c('div', {
      staticClass: "top-right"
    }, [_c('div', {
      staticClass: "score"
    }, [_vm._v(_vm._s(item.otherInfo.abilityInfo.score))]), _vm._v(" "), _c('div', {
      staticClass: "ans"
    }, _vm._l((item.otherAns), function(ans2, ansIndex2) {
      return _c('div', {
        key: ansIndex2,
        staticClass: "ans-item"
      }, [(ans2 == 0) ? _c('img', {
        attrs: {
          "src": "/static/main_yes.png"
        }
      }) : _vm._e(), _vm._v(" "), (ans2 == 1) ? _c('img', {
        attrs: {
          "src": "/static/main_err.png"
        }
      }) : _vm._e(), _vm._v(" "), (ans2 == 2) ? _c('img', {
        attrs: {
          "src": "/static/main_none.png"
        }
      }) : _vm._e()])
    }))])]), _vm._v(" "), _c('div', {
      staticClass: "user-bottom"
    }, [_c('img', {
      attrs: {
        "src": _vm.IMGHEAD + item.otherInfo.abilityInfo.paragraphImage
      }
    }), _vm._v(" " + _vm._s(item.otherInfo.basicInfo.nickName) + "\n            ")])])])
  })) : _vm._e()])])])
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-8b6fa102", esExports)
  }
}

/***/ })

},[352]);
//# sourceMappingURL=main.js.map