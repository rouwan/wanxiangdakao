global.webpackJsonp([17],{

/***/ 208:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(210);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_6220ac63_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(211);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(209)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-6220ac63"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_6220ac63_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\questionAdvice\\index.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-6220ac63", Component.options)
  } else {
    hotAPI.reload("data-v-6220ac63", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 209:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 210:
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


/* harmony default export */ __webpack_exports__["a"] = ({
  data: function data() {
    return {
      //1:排版有问题,2:题目不严谨,3:有错别字,4:题目错误,5:题目缺乏价值,6:其他问题
      typeList: {
        1: "排版有问题",
        2: "题目不严谨",
        3: "有错别字",
        4: "题目错误",
        5: "题目缺乏价值",
        6: "其他问题"
      },
      remark: "",
      selectType: -1,
      id: 0,
      qId: 0,
      from: ""
    };
  },

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["c" /* mapState */])(["gameRecord"])),
  methods: {
    getId: function getId() {
      this.id = this.$root.$mp.query.current;
      this.from = this.$root.$mp.query.from;
      if (this.gameRecord) {
        if (this.from == "room") {
          this.qId = this.gameRecord.Result.game.subjectList[this.id].id;
        } else {
          this.qId = this.gameRecord.Result.subjectList[this.id].id;
        }

        console.log(this.qId);
      }
    },
    onType: function onType(index) {
      this.selectType = index;
      console.log(this.selectType);
    },
    onSubmit: function onSubmit() {
      var _this = this;

      if (this.selectType == -1) {
        wx.showToast({ title: "请选择纠错类型", icon: "none" });
        return false;
      }
      if (this.qId == 0) {
        wx.showToast({ title: "页面数据有误,请重新进入", icon: "none" });
        return false;
      }
      this.fly.post("/feedBackSubject", {
        subjectId: this.qId,
        types: this.selectType,
        remark: this.remark
      }).then(function (res) {
        //输出请求数据
        wx.showToast({
          title: "提交成功",
          icon: "success",
          duration: 1000
        });
        setTimeout(function () {
          _this.$router.back();
        }, 1100);
      }).catch(function (err) {
        console.log(err.status, err.message);
      });
    }
  },
  onUnload: function onUnload() {
    this.selectType = -1;
  },
  onShow: function onShow() {
    this.getId();
  }
});

/***/ }),

/***/ 211:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "body sky-bg"
  }, [_vm._m(0), _vm._v(" "), _c('div', {
    staticClass: "middle"
  }, [_c('div', {
    staticClass: "typeList"
  }, _vm._l((_vm.typeList), function(type, index) {
    return _c('div', {
      key: index,
      staticClass: "type",
      class: {
        selectType: index == _vm.selectType
      },
      attrs: {
        "eventid": '0-' + index
      },
      on: {
        "click": function($event) {
          _vm.onType(index)
        }
      }
    }, [_vm._v(_vm._s(type))])
  }))]), _vm._v(" "), _c('div', {
    staticClass: "bottom"
  }, [_c('div', {
    staticClass: "title"
  }, [_vm._v("详细情况")]), _vm._v(" "), _c('div', {
    staticClass: "remark"
  }, [_c('textarea', {
    directives: [{
      name: "model",
      rawName: "v-model",
      value: (_vm.remark),
      expression: "remark"
    }],
    staticClass: "remarkText",
    attrs: {
      "maxlength": "300",
      "placeholder": "我有意见",
      "eventid": '1'
    },
    domProps: {
      "value": (_vm.remark)
    },
    on: {
      "input": function($event) {
        if ($event.target.composing) { return; }
        _vm.remark = $event.target.value
      }
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "submitBtn",
    attrs: {
      "eventid": '2'
    },
    on: {
      "click": _vm.onSubmit
    }
  }, [_vm._v("提交")])]), _vm._v(" "), _c('img', {
    staticClass: "moon",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/moon.png"
    }
  })])
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "top"
  }, [_c('div', {
    staticClass: "title"
  }, [_vm._v("题目纠错")])])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-6220ac63", esExports)
  }
}

/***/ })

},[337]);
//# sourceMappingURL=main.js.map