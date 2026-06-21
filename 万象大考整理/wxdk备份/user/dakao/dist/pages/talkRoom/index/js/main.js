global.webpackJsonp([15],{

/***/ 271:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(273);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_d13f413e_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(274);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(272)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-d13f413e"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_d13f413e_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\talkRoom\\index.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-d13f413e", Component.options)
  } else {
    hotAPI.reload("data-v-d13f413e", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 272:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 273:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise__);
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


/* harmony default export */ __webpack_exports__["a"] = ({
  data: function data() {
    return {
      msgValue: "",
      dataList: [],
      dist: null,
      limitTime: 10000,
      sendLimit: false,
      timer: null,
      pageSize: 10,
      pageIndex: 0,
      totalCount: 0,
      viewImg: ["https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/qrCode.png"],
      storyImg: ["https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/story-img.jpg"]
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_2_vuex__["c" /* mapState */])(["IMGHEAD", "appInfo"])),
  methods: {
    getData: function getData(config) {
      var _this = this;

      return new __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_promise___default.a(function (resolve, reject) {
        _this.fly.post("/getMessageBoardList", {
          pageSize: config.pageSize,
          pageIndex: config.pageIndex
        }).then(function (res) {
          resolve(res);
          // this.dist = 0;
        });
      });
    },
    showQrCode: function showQrCode(type) {
      var list = this.viewImg;
      if (type == 'story') {
        list = this.storyImg;
      }
      wx.previewImage({
        urls: list // 需要预览的图片http链接列表
      });
    },
    toUserInfo: function toUserInfo(id) {
      this.$router.push("/playerPages/pages/index?userId=" + id);
    },
    getNextPage: function getNextPage() {
      var _this2 = this;

      if (this.dataList.length == this.totalCount) {
        wx.showToast({ title: "我是有底线的", icon: "none" });
      }
      var page = Math.floor(this.dataList.length / this.pageSize);
      this.getData({ pageSize: 10, pageIndex: page }).then(function (res) {
        _this2.dataList.splice(page * 10, 10);
        _this2.dataList.concat(res.data.Result);
        for (var i = 0; i < res.data.Result.length; i++) {
          _this2.dataList.push(res.data.Result[i]);
        }
      });
    },
    handleSubmit: function handleSubmit() {
      var _this3 = this;

      if (!this.sendLimit) {
        if (this.msgValue == "") {
          wx.showToast({
            title: "写点东西呗~！",
            icon: "none"
          });
        } else {
          this.sendLimit = true;
          setTimeout(function () {
            _this3.sendLimit = false;
          }, this.limitTime);

          this.fly.post("/addMessageBoard", {
            message: this.msgValue
          }).then(function (res) {
            var arr = [{
              type: 0,
              headImgUrl: _this3.appInfo.headImgUrl,
              id: 0,
              message: _this3.msgValue,
              nickname: _this3.appInfo.nickname,
              paragraphImage: _this3.appInfo.image,
              paragraphName: _this3.appInfo.paragraphName,
              userId: _this3.userId
            }];
            _this3.dataList = arr.concat(_this3.dataList);
            _this3.msgValue = "";
          });
        }
      } else {
        wx.showToast({
          title: "不要发言太快~！",
          icon: "none"
        });
      }
    }
  },
  mounted: function mounted() {
    var _this4 = this;

    this.getData({ pageSize: this.pageSize, pageIndex: this.pageIndex }).then(function (res) {
      _this4.totalCount = res.data.TotalCount;
      _this4.dataList = res.data.Result;
    });
  },
  onUnload: function onUnload() {
    clearInterval(this.timer);
    this.pageIndex = 0;
  }
});

/***/ }),

/***/ 274:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-talk sky-bg"
  }, [_c('div', {
    staticClass: "u-text"
  }, [_c('input', {
    directives: [{
      name: "model",
      rawName: "v-model",
      value: (_vm.msgValue),
      expression: "msgValue"
    }],
    staticClass: "u-input",
    attrs: {
      "type": "text",
      "eventid": '0'
    },
    domProps: {
      "value": (_vm.msgValue)
    },
    on: {
      "input": function($event) {
        if ($event.target.composing) { return; }
        _vm.msgValue = $event.target.value
      }
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-submit",
    attrs: {
      "eventid": '1'
    },
    on: {
      "click": _vm.handleSubmit
    }
  }, [_vm._v("提交")])]), _vm._v(" "), _c('scroll-view', {
    staticClass: "u-scroll",
    attrs: {
      "scroll-y": "true",
      "scroll-with-animation": "true",
      "eventid": '5'
    },
    on: {
      "scrolltolower": _vm.getNextPage
    }
  }, [_vm._l((_vm.dataList), function(item, index) {
    return (item.message) ? _c('div', {
      key: index,
      staticClass: "u-msg",
      attrs: {
        "eventid": '2-' + index
      },
      on: {
        "click": function($event) {
          _vm.toUserInfo(item.userId)
        }
      }
    }, [(item.type == 0) ? _c('div', {
      staticClass: "u-user"
    }, [_c('img', {
      attrs: {
        "src": _vm.IMGHEAD + item.paragraphImage
      }
    }), _vm._v(" "), _c('div', {
      staticClass: "u-medal-name"
    }, [_vm._v(_vm._s(item.paragraphName))]), _vm._v("\n          " + _vm._s(item.nickname) + "\n        ")]) : _vm._e(), _vm._v(" "), (item.type == 0) ? _c('img', {
      attrs: {
        "src": item.headImgUrl
      }
    }) : _vm._e(), _vm._v(" "), (item.type == 0) ? _c('div', {
      staticClass: "u-txt"
    }, [_vm._v(_vm._s(item.message))]) : _vm._e(), _vm._v(" "), (item.type == 1) ? _c('div', {
      staticClass: "top"
    }, [_vm._v("\n          恭喜\n          "), _c('img', {
      attrs: {
        "src": item.headImgUrl
      }
    }), _vm._v(" "), _c('div', [_vm._v(_vm._s(item.nickname))]), _vm._v("\n            登顶\n          "), _c('img', {
      attrs: {
        "src": _vm.IMGHEAD + item.highParagraphImage
      }
    }), _vm._v(" "), _c('div', [_vm._v("最强王者")])]) : _vm._e()]) : _vm._e()
  }), _vm._v(" "), _c('img', {
    staticClass: "story",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/story-icon.jpg",
      "eventid": '3'
    },
    on: {
      "click": function($event) {
        _vm.showQrCode('story')
      }
    }
  }), _vm._v(" "), _c('img', {
    staticClass: "red-bag",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/redBag.png",
      "eventid": '4'
    },
    on: {
      "click": function($event) {
        _vm.showQrCode('bag')
      }
    }
  })], 2)], 1)
}
var staticRenderFns = []
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-d13f413e", esExports)
  }
}

/***/ })

},[342]);
//# sourceMappingURL=main.js.map