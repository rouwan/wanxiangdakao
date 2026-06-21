global.webpackJsonp([8],{

/***/ 306:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(308);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_ba533f7c_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(309);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(307)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-ba533f7c"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_ba533f7c_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\subjectFactoryPages\\pages\\index.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-ba533f7c", Component.options)
  } else {
    hotAPI.reload("data-v-ba533f7c", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 307:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 308:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator__ = __webpack_require__(18);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator__ = __webpack_require__(19);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__api_subjectApi__ = __webpack_require__(38);



//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
      dataReady: false,
      showRule: false,
      mySubject: {},
      pStyle: "",
      wantBtn: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/subjectFactory/subject-want.png",
      subjectBtn: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/subjectFactory/subject-sub.png"
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_3_vuex__["c" /* mapState */])(["appInfo"])),
  methods: {
    handRouter: function handRouter(route) {
      this.$router.push("/subjectFactoryPages/pages/" + route);
    }
  },
  onShow: function onShow() {
    var _this = this;

    return __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_asyncToGenerator___default()( /*#__PURE__*/__WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.mark(function _callee() {
      return __WEBPACK_IMPORTED_MODULE_0_babel_runtime_regenerator___default.a.wrap(function _callee$(_context) {
        while (1) {
          switch (_context.prev = _context.next) {
            case 0:
              _context.next = 2;
              return __WEBPACK_IMPORTED_MODULE_4__api_subjectApi__["a" /* default */].getSubjectNum();

            case 2:
              _this.mySubject = _context.sent;

              _this.pStyle = "width:" + _this.mySubject.Result.passNum / _this.mySubject.Result.nextNum * 100 + "%;";
              _this.dataReady = true;

            case 5:
            case "end":
              return _context.stop();
          }
        }
      }, _callee, _this);
    }))();
  },
  onUnload: function onUnload() {
    this.showRule = false;
  }
});

/***/ }),

/***/ 309:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-subject sky-bg-2"
  }, [_c('div', {
    staticClass: "m-user"
  }, [_c('img', {
    staticClass: "u-btn",
    attrs: {
      "src": "/static/ques.png",
      "eventid": '0'
    },
    on: {
      "click": function($event) {
        _vm.showRule = !_vm.showRule
      }
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "u-head-img-1",
    attrs: {
      "src": _vm.appInfo.headImgUrl
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-info"
  }, [_c('div', {
    staticClass: "u-name"
  }, [_vm._v("\n        " + _vm._s(_vm.appInfo.nickname) + "\n      ")]), _vm._v(" "), _c('div', {
    staticClass: "u-row"
  }, [(_vm.dataReady) ? _c('div', {
    staticClass: "u-row-item-small"
  }, [_vm._v("等级" + _vm._s(_vm.mySubject.Result.level) + "   " + _vm._s(_vm.mySubject.Result.name) + "\n        ")]) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "u-row"
  }, [_c('div', {
    staticClass: "process"
  }, [_c('div', {
    staticClass: "process-inner",
    style: (_vm.pStyle)
  })])]), _vm._v(" "), _c('div', {
    staticClass: "u-row"
  }, [_c('div', {
    staticClass: "u-row-item"
  }, [_vm._v("出题数:\n          "), (_vm.dataReady) ? _c('div', {
    staticClass: "u-txt"
  }, [_vm._v(_vm._s(_vm.mySubject.Result.totalNum))]) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "u-row-item"
  }, [_vm._v("影响力:\n          "), (_vm.dataReady) ? _c('div', {
    staticClass: "u-txt"
  }, [_vm._v(_vm._s(_vm.mySubject.Result.effectNum))]) : _vm._e()])])])]), _vm._v(" "), _c('div', {
    staticClass: "u-btn-set"
  }, [_c('img', {
    attrs: {
      "src": _vm.wantBtn,
      "eventid": '1'
    },
    on: {
      "click": function($event) {
        _vm.handRouter('selectSubject')
      }
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-btn-subject",
    attrs: {
      "eventid": '2'
    },
    on: {
      "click": function($event) {
        _vm.handRouter('mySubjectList')
      }
    }
  }, [_c('img', {
    attrs: {
      "src": _vm.subjectBtn
    }
  })]), _vm._v(" "), (_vm.showRule) ? _c('div', {
    staticClass: "m-rule",
    attrs: {
      "eventid": '3'
    },
    on: {
      "click": function($event) {
        _vm.showRule = !_vm.showRule
      }
    }
  }, [_c('div', {
    staticClass: "u-rule-head"
  }, [_vm._v("1、字数要求:")]), _vm._v(" "), _vm._m(0), _vm._v(" "), _c('div', {
    staticClass: "u-rule-head"
  }, [_vm._v("2、题目要求:")]), _vm._v(" "), _vm._m(1), _vm._v(" "), _vm._m(2), _vm._v(" "), _vm._m(3), _vm._v(" "), _vm._m(4), _vm._v(" "), _c('div', {
    staticClass: "u-rule-head"
  }, [_vm._v("3、注意事项:")]), _vm._v(" "), _vm._m(5), _vm._v(" "), _vm._m(6), _vm._v(" "), _vm._m(7)]) : _vm._e()])
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-txt"
  }, [_vm._v("\n      题目字数≤\n      "), _c('div', {
    staticClass: "p-fff"
  }, [_vm._v("30")]), _vm._v("; 选项字数≤\n      "), _c('div', {
    staticClass: "p-fff"
  }, [_vm._v("10")]), _vm._v("; 解析字数≤\n      "), _c('div', {
    staticClass: "p-fff"
  }, [_vm._v("500")])])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-txt"
  }, [_c('div', {
    staticClass: "u-num"
  }, [_vm._v("①")]), _vm._v("题目要\n      "), _c('div', {
    staticClass: "p-fff"
  }, [_vm._v("完整")])])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-txt"
  }, [_c('div', {
    staticClass: "u-num"
  }, [_vm._v("②")]), _vm._v(" "), _c('div', {
    staticClass: "p-fff"
  }, [_vm._v("有争议")]), _vm._v("的题目不予通过")])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-txt"
  }, [_c('div', {
    staticClass: "u-num"
  }, [_vm._v("③")]), _vm._v("涉及\n      "), _c('div', {
    staticClass: "p-fff"
  }, [_vm._v("政治敏感")]), _vm._v("的题目不予通过")])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-txt"
  }, [_c('div', {
    staticClass: "u-num"
  }, [_vm._v("④")]), _vm._v("解析要\n      "), _c('div', {
    staticClass: "p-fff"
  }, [_vm._v("合理")]), _vm._v(",要对题目进行适当分析")])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-txt"
  }, [_c('div', {
    staticClass: "u-num"
  }, [_vm._v("①")]), _vm._v("为保证题目质量,每个人一天最多出\n      "), _c('div', {
    staticClass: "p-fff"
  }, [_vm._v("50")]), _vm._v("题")])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-txt"
  }, [_c('div', {
    staticClass: "u-num"
  }, [_vm._v("②")]), _vm._v("如果题目\n      "), _c('div', {
    staticClass: "p-fff"
  }, [_vm._v("通过率过低")]), _vm._v(",将可能被撤销出题权限")])
},function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-txt"
  }, [_c('div', {
    staticClass: "u-num"
  }, [_vm._v("③")]), _vm._v("为了感谢您对知识传播的贡献,采纳的题目,将在释义区\n      "), _c('div', {
    staticClass: "p-fff"
  }, [_vm._v("标明出题者的头像和名字")])])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-ba533f7c", esExports)
  }
}

/***/ })

},[347]);
//# sourceMappingURL=main.js.map