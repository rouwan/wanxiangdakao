global.webpackJsonp([16],{

/***/ 267:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(269);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_6d292800_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(270);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(268)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-6d292800"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_6d292800_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\rankPage\\index.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-6d292800", Component.options)
  } else {
    hotAPI.reload("data-v-6d292800", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 268:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 269:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__config_dataFile_js__ = __webpack_require__(13);


//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
      allList: { 1: {}, 2: {}, 3: {}, 4: {}, 5: {}, 6: {}, 7: {}, 8: {} },
      dataList: [],
      selfRank: {},
      dataReady: false,
      currentType: 1,
      iconPos: __WEBPACK_IMPORTED_MODULE_3__config_dataFile_js__["b" /* iconPos */], //数字 map
      tabList: [],
      giftList: [{
        name: "第一名",
        num: 1,
        url: "/static/icon/b-box.png"
      }, {
        name: "第二名",
        num: 1,
        url: "/static/icon/m-box.png"
      }, {
        name: "第三名",
        num: 1,
        url: "/static/icon/m-box.png"
      }, {
        name: "第四名",
        num: 1,
        url: "/static/icon/s-box.png"
      }, {
        name: "第五名",
        num: 1,
        url: "/static/icon/s-box.png"
      }],
      showGiftTip: false,
      query: ''
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_2_vuex__["c" /* mapState */])(["IMGHEAD", "appInfo"])),
  methods: {
    toUserInfo: function toUserInfo(data) {
      this.$router.push("/playerPages/pages/index?userId=" + data.userId);
    },
    changeRank: function changeRank(type) {
      this.currentType = type;
      if (!this.allList[type].dataList) {
        wx.showLoading({ title: "加载中", mask: true });
        this.getList(type);
      }
    },
    getNumList: function getNumList(list, type) {
      var arr = [];
      for (var i = 0; i < list.length; i++) {
        var l = [];
        l = list[i][type].toString().split("");
        arr.push(l);
      }
      return arr;
    },
    getList: function getList(type) {
      var _this = this;

      return new __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise___default.a(function (resolve, reject) {
        _this.fly.post("rank/getRankList", { type: type }).then(function (res) {
          var arr = [];
          var list = res.data.Result || [];
          var selfRankInfo = res.data.Result2;
          if (type == 2 || type == 6) {
            arr = _this.getNumList(list, "combat");
          }
          if (type == 3) {
            arr = _this.getNumList(list, "maxContinueWinNum");
          }
          if (type == 4) {
            arr = _this.getNumList(list, "maxSingleScore");
          }
          if (type == 5) {
            for (var i = 0; i < list.length; i++) {
              if (list[i].userId == _this.appInfo.userId) {
                selfRankInfo = list[i];
                selfRankInfo.index = i + 1;
              }
            }
          }
          if (type == 7 || type == 8) {
            arr = _this.getNumList(list, "winNum");
          }
          _this.$set(_this.allList, type, {
            dataList: res.data.Result,
            selfRank: selfRankInfo,
            numList: arr || []
          });
          _this.dataReady = true;
          wx.hideLoading();
          resolve();
        });
      });
    }
  },
  mounted: function mounted() {
    wx.showLoading({
      title: "加载中",
      icon: "none"
    });
    if (this.$root.$mp.query.god) {
      this.query = 'god';
      this.currentType = 2;
      this.tabList = [{ name: "战力榜", type: 2 }, { name: "连胜榜", type: 3 }, { name: "高分榜", type: 4 }];
    } else if (this.$root.$mp.query.friend) {
      this.query = 'friend';
      this.currentType = 7;
      this.tabList = [{ name: "好友榜", type: 7 }, { name: "胜场榜", type: 8 }];
    } else {
      this.currentType = 1;
      this.tabList = [{ name: "段位榜", type: 1 }, { name: "每日战力榜", type: 6 }, { name: "好友榜", type: 5 }];
    }
    this.getList(this.currentType);
  },
  onUnload: function onUnload() {
    this.dataReady = false;
    this.query = '';
    this.currentType = 1;
    this.allList = { 1: {}, 2: {}, 3: {}, 4: {}, 5: {}, 6: {}, 7: {}, 8: {} };
  }
});

/***/ }),

/***/ 270:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "out-box"
  }, [_c('img', {
    staticClass: "entry-bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-test.png"
    }
  }), _vm._v(" "), (_vm.dataReady) ? _c('div', {
    staticClass: "body"
  }, [_c('div', {
    staticClass: "top  flex",
    class: {
      'friend-type': _vm.query == 'friend'
    }
  }, [_vm._l((_vm.tabList), function(item, index) {
    return _c('div', {
      key: index,
      class: {
        'selected': _vm.currentType == item.type
      },
      attrs: {
        "eventid": '0-' + index
      },
      on: {
        "click": function($event) {
          _vm.changeRank(item.type)
        }
      }
    }, [_vm._v(_vm._s(item.name))])
  }), _vm._v(" "), (_vm.query == 'friend') ? _c('img', {
    staticClass: "tip-box",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/box.png",
      "eventid": '1'
    },
    on: {
      "click": function($event) {
        _vm.showGiftTip = true
      }
    }
  }) : _vm._e()], 2), _vm._v(" "), _c('div', {
    staticClass: "bigMiddle"
  }, _vm._l((_vm.allList[_vm.currentType].dataList), function(data, index) {
    return _c('div', {
      key: index,
      staticClass: "bigRankList",
      attrs: {
        "eventid": '2-' + index
      },
      on: {
        "click": function($event) {
          _vm.toUserInfo(data)
        }
      }
    }, [_c('div', {
      staticClass: "rankList",
      class: {
        big: _vm.currentType == 3
      }
    }, [_c('div', {
      staticClass: "rank"
    }, [(index == 0) ? _c('div', {
      staticClass: "rank-num rank-icon rank-icon-num-1"
    }) : (index == 1) ? _c('div', {
      staticClass: "rank-num rank-icon rank-icon-num-2"
    }) : (index == 2) ? _c('div', {
      staticClass: "rank-num rank-icon rank-icon-num-3"
    }) : (index == 3) ? _c('div', {
      staticClass: "rank-num rank-icon rank-icon-num-4"
    }) : (index == 4) ? _c('div', {
      staticClass: "rank-num rank-icon rank-icon-num-5"
    }) : _c('span', [_vm._v(_vm._s(index + 1))])]), _vm._v(" "), (_vm.currentType != 3) ? _c('div', {
      staticClass: "headImg"
    }, [_c('img', {
      attrs: {
        "src": data.headImgUrl
      }
    })]) : _vm._e(), _vm._v(" "), (_vm.currentType != 3) ? _c('div', {
      staticClass: "u-info"
    }, [_c('div', {
      staticClass: "ninckName"
    }, [_vm._v(_vm._s(data.nickName))]), _vm._v(" "), _c('div', {
      staticClass: "place"
    }, [_vm._v(_vm._s(data.province || '宇宙') + " " + _vm._s(data.city || '火星'))])]) : _vm._e(), _vm._v(" "), (_vm.currentType == 1 || _vm.currentType == 5) ? _c('div', {
      staticClass: "paraghImg"
    }, [(data.paragraphImage) ? _c('img', {
      attrs: {
        "src": _vm.IMGHEAD + data.paragraphImage
      }
    }) : _vm._e()]) : _vm._e(), _vm._v(" "), (_vm.currentType == 1 || _vm.currentType == 5) ? _c('div', {
      staticClass: "paraghStar"
    }, [_c('div', {
      staticClass: "star"
    }, [_c('img', {
      attrs: {
        "src": "/static/star.png"
      }
    }), _vm._v(" "), _c('span', [_vm._v(_vm._s(data.star))])]), _vm._v(" "), _c('div', {
      staticClass: "paraghName"
    }, [_vm._v(_vm._s(data.paragraphName))])]) : _vm._e(), _vm._v(" "), (_vm.currentType == 2 || _vm.currentType == 6 || _vm.currentType == 7 || _vm.currentType == 8) ? _c('div', {
      staticClass: "rank-spec"
    }, [(_vm.currentType == 2 || _vm.currentType == 6) ? _c('div', {
      staticClass: "s-t"
    }, [_vm._v("战斗力")]) : _vm._e(), _vm._v(" "), (_vm.currentType == 7 || _vm.currentType == 8) ? _c('div', {
      staticClass: "s-t"
    }, [_vm._v("胜场")]) : _vm._e(), _vm._v(" "), _c('div', {
      staticClass: "s-b"
    }, _vm._l((_vm.allList[_vm.currentType].numList[index]), function(item, iconIndex) {
      return _c('img', {
        key: iconIndex,
        attrs: {
          "src": _vm.iconPos[item]
        }
      })
    }))]) : _vm._e(), _vm._v(" "), (_vm.currentType == 4) ? _c('div', {
      staticClass: "rank-spec"
    }, [_c('div', {
      staticClass: "s-t"
    }, [_vm._v("最高分")]), _vm._v(" "), _c('div', {
      staticClass: "s-b"
    }, _vm._l((_vm.allList[_vm.currentType].numList[index]), function(item, iconIndex) {
      return _c('img', {
        key: iconIndex,
        attrs: {
          "src": _vm.iconPos[item]
        }
      })
    }))]) : _vm._e(), _vm._v(" "), (_vm.currentType == 3) ? _c('div', {
      staticClass: "c-l"
    }, [_c('img', {
      staticClass: "c-head",
      attrs: {
        "src": data.headImgUrl
      }
    }), _vm._v(" "), _c('div', {
      staticClass: "c-name"
    }, [_vm._v(_vm._s(data.nickName))]), _vm._v(" "), _c('div', {
      staticClass: "c-type"
    }, [_vm._v("连胜者")])]) : _vm._e(), _vm._v(" "), (_vm.currentType == 3) ? _c('div', {
      staticClass: "c-m"
    }, [_c('div', {
      staticClass: "c-img"
    }, _vm._l((_vm.allList[_vm.currentType].numList[index]), function(item, iconIndex) {
      return _c('img', {
        key: iconIndex,
        attrs: {
          "src": _vm.iconPos[item]
        }
      })
    })), _vm._v(" "), _c('div', {
      staticClass: "c-tag"
    }, [_vm._v("连胜")]), _vm._v(" "), (data.endWinUserId != 0) ? _c('div', {
      staticClass: "c-status stoped"
    }, [_vm._v("已终结")]) : _c('div', {
      staticClass: "c-status"
    }, [_vm._v("未终结")])]) : _vm._e(), _vm._v(" "), (_vm.currentType == 3) ? _c('div', {
      staticClass: "c-r"
    }, [(data.endWinUserId < 0) ? _c('img', {
      staticClass: "c-head",
      attrs: {
        "src": _vm.IMGHEAD + data.endHeadImgUrl
      }
    }) : (data.endWinUserId > 0) ? _c('img', {
      staticClass: "c-head",
      attrs: {
        "src": data.endHeadImgUrl
      }
    }) : _c('img', {
      staticClass: "c-head",
      attrs: {
        "src": "/static/empty-head.png"
      }
    }), _vm._v(" "), (data.endWinUserId != 0) ? _c('div', {
      staticClass: "c-name"
    }, [_vm._v(_vm._s(data.endNickName))]) : _c('div', {
      staticClass: "c-name"
    }, [_vm._v("暂无终结者")]), _vm._v(" "), _c('div', {
      staticClass: "c-type"
    }, [_vm._v("终结者")])]) : _vm._e()])])
  })), _vm._v(" "), (_vm.allList[_vm.currentType].selfRank && _vm.allList[_vm.currentType].selfRank.index) ? _c('div', {
    staticClass: "bigBottom"
  }, [_c('div', {
    staticClass: "rank"
  }, [_vm._v(_vm._s(_vm.allList[_vm.currentType].selfRank.index < 0 ? "-" : _vm.allList[_vm.currentType].selfRank.index))]), _vm._v(" "), _c('div', {
    staticClass: "headImg"
  }, [_c('img', {
    attrs: {
      "src": _vm.allList[_vm.currentType].selfRank.headImgUrl
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "ninckName"
  }, [_vm._v(_vm._s(_vm.allList[_vm.currentType].selfRank.nickName))]), _vm._v(" "), (_vm.currentType == 1 || _vm.currentType == 5) ? _c('div', {
    staticClass: "paraghImg"
  }, [(_vm.allList[_vm.currentType].selfRank.paragraphImage) ? _c('img', {
    attrs: {
      "src": _vm.IMGHEAD + _vm.allList[_vm.currentType].selfRank.paragraphImage
    }
  }) : _vm._e()]) : _vm._e(), _vm._v(" "), (_vm.currentType == 1 || _vm.currentType == 5) ? _c('div', {
    staticClass: "paraghStar"
  }, [_c('div', {
    staticClass: "star"
  }, [_c('img', {
    attrs: {
      "src": "/static/star.png"
    }
  }), _vm._v(" "), _c('span', [_vm._v(_vm._s(_vm.allList[_vm.currentType].selfRank.star))])]), _vm._v(" "), _c('div', {
    staticClass: "paraghName"
  }, [_vm._v(_vm._s(_vm.allList[_vm.currentType].selfRank.paragraphName))])]) : _vm._e(), _vm._v(" "), (_vm.currentType == 2 || _vm.currentType == 6) ? _c('div', {
    staticClass: "b-fight"
  }, [_c('div', {
    staticClass: "b-l"
  }, [_vm._v("战斗力")]), _vm._v(" "), _c('div', {
    staticClass: "b-r"
  }, [_vm._v(_vm._s(_vm.allList[_vm.currentType].selfRank.combat))])]) : _vm._e(), _vm._v(" "), (_vm.currentType == 4) ? _c('div', {
    staticClass: "b-fight"
  }, [_c('div', {
    staticClass: "b-l"
  }, [_vm._v("最高分")]), _vm._v(" "), _c('div', {
    staticClass: "b-r"
  }, [_vm._v(_vm._s(_vm.allList[_vm.currentType].selfRank.maxSingleScore))])]) : _vm._e(), _vm._v(" "), (_vm.currentType == 3) ? _c('div', {
    staticClass: "b-fight"
  }, [_c('div', {
    staticClass: "b-l"
  }, [_vm._v("连胜")]), _vm._v(" "), _c('div', {
    staticClass: "b-r"
  }, [_vm._v(_vm._s(_vm.allList[_vm.currentType].selfRank.maxContinueWinNum) + "场")])]) : _vm._e(), _vm._v(" "), (_vm.currentType == 7 || _vm.currentType == 8) ? _c('div', {
    staticClass: "b-fight"
  }, [_c('div', {
    staticClass: "b-l"
  }, [_vm._v("胜场")]), _vm._v(" "), _c('div', {
    staticClass: "b-r"
  }, [_vm._v(_vm._s(_vm.allList[_vm.currentType].selfRank.winNum) + "场")])]) : _vm._e()]) : _vm._e()]) : _vm._e(), _vm._v(" "), (_vm.showGiftTip) ? _c('div', {
    staticClass: "mask-bg"
  }, [_c('div', {
    staticClass: "gift-modal pos-a left-center"
  }, [_c('img', {
    staticClass: "modal-head",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/mvp-gift.png"
    }
  }), _vm._v(" "), _vm._l((_vm.giftList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "line flex"
    }, [_vm._v("\n          " + _vm._s(item.name) + "\n          "), _c('img', {
      attrs: {
        "src": item.url
      }
    }), _vm._v(" x" + _vm._s(item.num) + "\n        ")])
  }), _vm._v(" "), _c('div', {
    staticClass: "text"
  }, [_vm._v("好友榜、MVP榜每周一凌晨2点更新")]), _vm._v(" "), _c('img', {
    staticClass: "close-btn pos-a",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/close-btn.png",
      "eventid": '3'
    },
    on: {
      "click": function($event) {
        _vm.showGiftTip = false
      }
    }
  })], 2)]) : _vm._e()])
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-6d292800", esExports)
  }
}

/***/ })

},[341]);
//# sourceMappingURL=main.js.map