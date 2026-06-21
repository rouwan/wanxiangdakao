global.webpackJsonp([22],{

/***/ 190:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(192);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_3ee2f8ba_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(193);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(191)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-3ee2f8ba"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_3ee2f8ba_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\book\\index.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-3ee2f8ba", Component.options)
  } else {
    hotAPI.reload("data-v-3ee2f8ba", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 191:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 192:
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
//
//
//


// import lottery from "@/components/public/lottery";
/* harmony default export */ __webpack_exports__["a"] = ({
  data: function data() {
    return {
      showTip: false,
      dataReady: false,
      ticket: 0,
      maxLevel: 30,
      bookList: [],
      bookType: {
        2: {
          bookName: "商",
          img: "/static/icon/shang.png",
          pWidth: "width:0%;"
        },
        3: { bookName: "文", img: "/static/icon/wen.png", pWidth: "width:0%;" },
        4: { bookName: "理", img: "/static/icon/li.png", pWidth: "width:0%;" },
        5: { bookName: "艺", img: "/static/icon/yi.png", pWidth: "width:0%;" },
        6: { bookName: "体", img: "/static/icon/ti.png", pWidth: "width:0%;" },
        7: { bookName: "综", img: "/static/icon/zong.png", pWidth: "width:0%;" }
      },
      typeMap: {
        3: 8,
        4: 9,
        5: 10,
        6: 11,
        2: 7,
        7: 12
      }
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["c" /* mapState */])(["goodStorageList", "appInfo"])),
  methods: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["b" /* mapMutations */])(["setSingleGoodNum"]), {
    handleShowTip: function handleShowTip() {
      var _this = this;

      setTimeout(function () {
        _this.showTip = true;
        setTimeout(function () {
          _this.showTip = false;
        }, 5000);
      }, 2000);
    },
    getList: function getList() {
      var _this2 = this;

      this.fly.post("/book/getUserBookList").then(function (res) {
        _this2.bookList = res.data.Result.bookList;
        _this2.ticket = res.data.Result.ticket;
        _this2.maxLevel = res.data.Result.maxBookLevel;
        for (var i = 0; i < _this2.bookList.length; i++) {
          var p = _this2.bookList[i].num / _this2.bookList[i].nextNum * 100;
          p >= 100 ? p = 100 : p = p;
          _this2.bookType[_this2.bookList[i].type].pWidth = "width:" + p + "%;";
        }
        _this2.dataReady = true;
      });
    },
    update: function update(item) {
      var _this3 = this;

      this.fly.post("book/upLevelByType", {
        type: item.type
      }).then(function (res) {
        if (res.data.Tag == 1) {
          if (_this3.goodStorageList.length > 0) {
            _this3.setSingleGoodNum({
              type: _this3.typeMap[item.type],
              num: item.num - item.nextNum
            });
          }

          item.level = res.data.Result.level;
          item.num = res.data.Result.num;
          item.addition = res.data.Result.addition;
          item.nextNum = res.data.Result.nextNum;
          _this3.bookType[item.type].pWidth = "width:" + (item.num / item.nextNum * 100 >= 100 ? 100 : item.num / item.nextNum * 100) + "%";
        }
      });
    }
  }),
  mounted: function mounted() {
    // this.handleShowTip();
    this.getList();
  }
});

/***/ }),

/***/ 193:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-book"
  }, [_c('img', {
    staticClass: "u-bg",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/book-bg.png"
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "book-content"
  }, [_c('div', {
    staticClass: "book-head"
  }, [_c('img', {
    attrs: {
      "src": "/static/icon/book-head.png"
    }
  }), _vm._v(" "), (false) ? _c('div', {
    staticClass: "card"
  }, [_c('img', {
    staticClass: "card-img",
    attrs: {
      "src": "/static/icon/book-card.png"
    }
  }), _vm._v(" " + _vm._s(_vm.ticket) + "\n        "), _c('img', {
    staticClass: "card-tip",
    class: {
      show: _vm.showTip
    },
    attrs: {
      "src": "/static/icon/book-tip.png"
    }
  })]) : _vm._e()]), _vm._v(" "), (_vm.dataReady) ? _c('div', {
    staticClass: "book-list"
  }, [_vm._l((_vm.bookList), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "book-item"
    }, [_c('div', {
      staticClass: "item-left"
    }, [_c('img', {
      staticClass: "book-bg",
      attrs: {
        "src": "/static/icon/book-box-bg.png"
      }
    }), _vm._v(" "), _c('img', {
      staticClass: "book-book",
      attrs: {
        "src": _vm.bookType[item.type].img
      }
    }), _vm._v(" "), _c('div', {
      staticClass: "book-name"
    }, [_vm._v(_vm._s(_vm.bookType[item.type].bookName) + "科")])]), _vm._v(" "), _c('div', {
      staticClass: "item-center"
    }, [_c('div', {
      staticClass: "grade"
    }, [_c('div', [_vm._v("\n              等级" + _vm._s(item.level) + "\n            ")]), _vm._v(" "), (item.level == _vm.maxLevel) ? _c('div', {
      staticClass: "g-txt"
    }, [_vm._v(_vm._s(item.num) + "/\n              "), _c('div', {
      staticClass: "big-txt"
    }, [_vm._v("8")])]) : _c('div', {
      staticClass: "g-txt"
    }, [_vm._v(_vm._s(item.num) + "/" + _vm._s(item.nextNum))])]), _vm._v(" "), _c('div', {
      staticClass: "process"
    }, [(item.num >= item.nextNum || item.level == _vm.maxLevel) ? _c('img', {
      staticClass: "full",
      attrs: {
        "src": "/static/icon/book-p-act.png"
      }
    }) : _vm._e(), _vm._v(" "), _c('div', {
      staticClass: "process-inner",
      style: (_vm.bookType[item.type].pWidth)
    })]), _vm._v(" "), _c('div', [_vm._v(_vm._s(_vm.bookType[item.type].bookName) + "科得分+" + _vm._s(item.addition) + "%")])]), _vm._v(" "), _c('div', {
      staticClass: "item-right"
    }, [(item.level >= _vm.maxLevel) ? _c('img', {
      staticClass: "l-full",
      attrs: {
        "src": "/static/icon/book-manji.png"
      }
    }) : (item.num < item.nextNum) ? _c('img', {
      staticClass: "l-btn",
      attrs: {
        "src": "/static/icon/book-btn.png"
      }
    }) : (item.num >= item.nextNum) ? _c('img', {
      staticClass: "l-btn",
      attrs: {
        "src": "/static/icon/book-btn-act.png",
        "eventid": '0-' + index
      },
      on: {
        "click": function($event) {
          _vm.update(item)
        }
      }
    }) : _vm._e()])])
  }), _vm._v(" "), _c('div', {
    staticClass: "place"
  }), _vm._v(" "), _c('div', {
    staticClass: "place"
  })], 2) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "ad-box"
  }, [_c('ad', {
    attrs: {
      "unit-id": "adunit-275f5d9761ad9db1",
      "mpcomid": '0'
    }
  })], 1)])
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-3ee2f8ba", esExports)
  }
}

/***/ })

},[334]);
//# sourceMappingURL=main.js.map