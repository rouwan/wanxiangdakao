global.webpackJsonp([6],{

/***/ 318:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_mySubjectList_vue__ = __webpack_require__(320);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_a74e97e4_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_mySubjectList_vue__ = __webpack_require__(321);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(319)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-a74e97e4"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_mySubjectList_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_a74e97e4_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_mySubjectList_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\subjectFactoryPages\\pages\\mySubjectList.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] mySubjectList.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-a74e97e4", Component.options)
  } else {
    hotAPI.reload("data-v-a74e97e4", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 319:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 320:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vuex__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__api_subjectApi__ = __webpack_require__(38);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__components_gift_get_gift__ = __webpack_require__(25);

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
  components: {
    "get-gift": __WEBPACK_IMPORTED_MODULE_3__components_gift_get_gift__["a" /* default */]
  },
  data: function data() {
    return {
      showGetGift: false,
      dataReady: false,
      current: 0,
      hasQues: false,
      gift: [{ name: "book", url: "", num: 1 }],
      subjectList: [[], [], []],
      tabList: ["已通过", "审核中", "未通过"],
      subjectType: { 0: "审核中", 1: "已通过", 2: "未通过" },
      indexMap: { 0: 1, 1: 0, 2: 2 },
      pageSize: 10,
      pageIndex: 0,
      statusList: [],
      typeMap: {
        2: "商",
        3: "文",
        4: "理",
        5: "艺",
        6: "体",
        7: "综"
      },
      bookMap: {
        2: { img: "/static/icon/shang.png", name: "商科" },
        3: { img: "/static/icon/wen.png", name: "文科" },
        4: { img: "/static/icon/li.png", name: "理科" },
        5: { img: "/static/icon/yi.png", name: "艺科" },
        6: { img: "/static/icon/ti.png", name: "体科" },
        7: { img: "/static/icon/zong.png", name: "综科" }
      }
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["c" /* mapState */])(["appInfo"])),
  methods: {
    // getList(){
    //   subjectApi.getSubjectList({pageIndex:0,pageSize:100}).then(res=>{
    //     this.subjectList[0]=res.Result;
    //     if(this.subjectList[0].length>0){
    //       this.hasQues=true;
    //     }
    //     this.dataReady=true;
    //     wx.hideLoading();
    //   })
    // },
    getGiftFn: function getGiftFn(item, index) {
      var _this = this;

      this.fly.post("subject/receiveGood", {
        subjectId: item.id
      }).then(function (res) {
        console.log(res);
        _this.statusList[index] = 1;
        for (var key in res.data.Result) {
          _this.gift = [{
            name: _this.bookMap[key].name,
            url: _this.bookMap[key].img,
            num: res.data.Result[key]
          }];
        }
        setTimeout(function () {
          _this.showGetGift = true;
        }, 500);
      });
    },
    changeList: function changeList(index) {
      var _this2 = this;

      if (index == 0) {
        this.pageIndex = Math.ceil(this.subjectList[0].length / this.pageSize) - 1;
      }
      if (this.subjectList[index].length == 0) {
        wx.showLoading({
          title: "加载中",
          mask: true
        });
        this.pageIndex = 0;
        __WEBPACK_IMPORTED_MODULE_2__api_subjectApi__["a" /* default */].getSubjectListByStatus({
          status: this.indexMap[index],
          pageIndex: this.pageIndex,
          pageSize: this.pageSize
        }).then(function (res) {
          _this2.subjectList[index] = res.Result;
          _this2.current = index;
          _this2.dataReady = true;
          if (_this2.subjectList[index].length > 0) {
            _this2.hasQues = true;
          }
          if (index == 0) {
            _this2.statusList = [];
            for (var i = 0; i < _this2.subjectList[index].length; i++) {
              _this2.statusList.push(_this2.subjectList[index][i].isReceive);
            }
          }
          wx.hideLoading();
        });
      } else {
        this.current = index;
      }
    },
    loadNextPage: function loadNextPage() {
      var _this3 = this;

      if (this.subjectList[this.current].length >= this.pageSize * (this.pageIndex + 1)) {
        wx.showLoading({
          title: "加载中",
          mask: true
        });
        this.pageIndex++;
        __WEBPACK_IMPORTED_MODULE_2__api_subjectApi__["a" /* default */].getSubjectListByStatus({
          status: this.indexMap[this.current],
          pageIndex: this.pageIndex,
          pageSize: this.pageSize
        }).then(function (res) {
          for (var i = 0; i < res.Result.length; i++) {
            _this3.subjectList[_this3.current].push(res.Result[i]);
          }
          if (_this3.current == 0) {
            _this3.statusList = [];
            for (var _i = 0; _i < _this3.subjectList[_this3.current].length; _i++) {
              _this3.statusList.push(_this3.subjectList[_this3.current][_i].isReceive);
            }
          }
          wx.hideLoading();
        });
      }
    },
    handleRoute: function handleRoute(id) {
      this.$router.push("/subjectFactoryPages/pages/lookSubject?subjectId=" + id);
    },
    toSelectSubject: function toSelectSubject() {
      this.$router.push("/subjectFactoryPages/pages/selectSubject");
    }
  },
  mounted: function mounted() {
    this.changeList(0);
  },
  onUnload: function onUnload() {
    this.current = 0;
    this.dataReady = false;
    this.subjectList = [[], [], [], []];
  }
});

/***/ }),

/***/ 321:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-subject-list sky-bg-2"
  }, [_c('div', {
    staticClass: "u-head"
  }, _vm._l((_vm.tabList), function(item, index) {
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
          _vm.changeList(index)
        }
      }
    }, [_vm._v("\n      " + _vm._s(item) + "\n    ")])
  })), _vm._v(" "), (_vm.dataReady && _vm.subjectList[_vm.current].length > 0) ? _c('scroll-view', {
    staticClass: "u-list",
    attrs: {
      "scroll-y": "true",
      "scroll-with-animation": "true",
      "eventid": '3'
    },
    on: {
      "scrolltolower": _vm.loadNextPage
    }
  }, _vm._l((_vm.subjectList[_vm.current]), function(item, index) {
    return _c('div', {
      key: index,
      staticClass: "u-item",
      class: {
        'u-type-1': item.status == 0, 'u-type-2': item.status == 2
      },
      attrs: {
        "eventid": '2-' + index
      },
      on: {
        "click": function($event) {
          _vm.handleRoute(item.id)
        }
      }
    }, [_c('div', {
      staticClass: "u-title"
    }, [_c('div', [_vm._v("【" + _vm._s(_vm.typeMap[item.parentType]) + "】")]), _vm._v(_vm._s(item.title))]), _vm._v(" "), _c('div', {
      staticClass: "u-info"
    }, [(_vm.current != 0) ? _c('div', {
      staticClass: "u-status"
    }, [_c('div', {
      staticClass: "u-ball"
    }), _vm._v("\n          " + _vm._s(_vm.subjectType[item.status]) + "\n        ")]) : _vm._e(), _vm._v(" "), (_vm.current == 0) ? _c('div', {
      staticClass: "u-num"
    }, [_c('div', {
      staticClass: "th"
    }, [_vm._v("正确率:")]), _vm._v(" "), _c('div', {
      staticClass: "txt"
    }, [_vm._v(_vm._s(item.rightRatio) + "%")]), _vm._v(" "), _c('div', {
      staticClass: "th"
    }, [_vm._v("答题人数:")]), _vm._v(" "), _c('div', {
      staticClass: "txt"
    }, [_vm._v(_vm._s(item.replyNum))])]) : _vm._e(), _vm._v(" "), (_vm.current == 0) ? _c('div', {
      staticClass: "btn"
    }, [(_vm.statusList[index] == 0) ? _c('img', {
      staticClass: "get",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/getBook.png",
        "eventid": '1-' + index
      },
      on: {
        "click": function($event) {
          $event.stopPropagation();
          _vm.getGiftFn(item, index)
        }
      }
    }) : _c('img', {
      staticClass: "receved",
      attrs: {
        "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved-txt.png"
      }
    })]) : _vm._e()])])
  })) : _vm._e(), _vm._v(" "), (_vm.subjectList[_vm.current].length == 0) ? _c('div', {
    staticClass: "u-empty"
  }, [_c('img', {
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/list-empty.png"
    }
  }), _vm._v(" "), (!_vm.hasQues && _vm.subjectList[_vm.current].length == 0) ? _c('div', {
    staticClass: "u-empty-word"
  }, [_vm._v("您尚未参与出题")]) : _vm._e(), _vm._v(" "), (_vm.hasQues && _vm.subjectList[_vm.current].length == 0) ? _c('div', {
    staticClass: "u-empty-word"
  }, [_vm._v("暂无题目")]) : _vm._e(), _vm._v(" "), (!_vm.hasQues && _vm.subjectList[_vm.current].length == 0) ? _c('div', {
    staticClass: "u-empty-btn",
    attrs: {
      "eventid": '4'
    },
    on: {
      "click": _vm.toSelectSubject
    }
  }, [_vm._v("我要出题")]) : _vm._e()]) : _vm._e(), _vm._v(" "), (_vm.showGetGift) ? _c('get-gift', {
    attrs: {
      "giftList": _vm.gift,
      "eventid": '5',
      "mpcomid": '0'
    },
    on: {
      "close": function($event) {
        _vm.showGetGift = false
      }
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
     require("vue-hot-reload-api").rerender("data-v-a74e97e4", esExports)
  }
}

/***/ })

},[350]);
//# sourceMappingURL=main.js.map