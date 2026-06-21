global.webpackJsonp([7],{

/***/ 322:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_lookSubject_vue__ = __webpack_require__(324);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_404e3506_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_lookSubject_vue__ = __webpack_require__(325);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(323)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-404e3506"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_lookSubject_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_404e3506_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_lookSubject_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\subjectFactoryPages\\pages\\lookSubject.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] lookSubject.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-404e3506", Component.options)
  } else {
    hotAPI.reload("data-v-404e3506", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 323:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 324:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__api_subjectApi__ = __webpack_require__(38);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
      subjectId: -1,
      parentType: 3,
      createInfo: {
        img: "",
        name: ""
      },
      subjectStatus: 0,
      rejectRemark: "",
      quesTxt: "",
      descTxt: "",
      op1: "",
      op2: "",
      op3: "",
      op4: "",
      wordList: {
        0: { type: "未知", desc: "未知" },
        1: { type: "未知", desc: "未知" },
        2: { type: "未知", desc: "未知" },
        3: { type: "文科", desc: "人文历史" },
        4: { type: "理科", desc: "自然科学" },
        5: { type: "艺科", desc: "艺术文化" },
        6: { type: "体科", desc: "健康常识" },
        7: { type: "综合", desc: "综合学识" }
      }
    };
  },

  methods: {
    clearData: function clearData() {
      this.quesTxt = "";
      this.op1 = "";
      this.op2 = "";
      this.op3 = "";
      this.op4 = "";
      this.descTxt = "";
    },
    getSubjectById: function getSubjectById() {
      var _this = this;

      //更新题目
      wx.showLoading();
      __WEBPACK_IMPORTED_MODULE_0__api_subjectApi__["a" /* default */].getSubjectById({ id: this.subjectId }).then(function (res) {
        _this.quesTxt = res.Result.title;
        _this.descTxt = res.Result.description;
        _this.op1 = res.Result.optionList[0].name;
        _this.op2 = res.Result.optionList[1].name;
        _this.op3 = res.Result.optionList[2].name;
        _this.op4 = res.Result.optionList[3].name;
        _this.parentType = res.Result.parentType;
        _this.subjectStatus = res.Result.status;
        _this.rejectRemark = res.Result.rejectRemark;
        _this.createInfo.img = res.Result.createUserUrl;
        _this.createInfo.name = res.Result.createUserName;
        wx.hideLoading();
      });
    },
    handRouter: function handRouter() {
      this.$router.push("/subjectFactoryPages/pages/addSubject?subjectId=" + this.subjectId);
    }
  },

  onShow: function onShow() {
    this.parentType = this.$root.$mp.query.parentType || 1;
    this.subjectId = this.$root.$mp.query.subjectId || -1;
    this.getSubjectById();
  }
});

/***/ }),

/***/ 325:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-edit-subject sky-bg-2"
  }, [_c('div', {
    staticClass: "u-head"
  }, [(_vm.wordList[_vm.parentType]) ? _c('div', {
    staticClass: "u-type"
  }, [_vm._v("\n      " + _vm._s(_vm.wordList[_vm.parentType].type || "未知type-parentType") + "\n    ")]) : _vm._e(), _vm._v("\n      " + _vm._s(_vm.wordList[_vm.parentType].desc || "未知type") + "\n    "), (_vm.subjectStatus == 2) ? _c('div', {
    staticClass: "u-edit",
    attrs: {
      "eventid": '0'
    },
    on: {
      "click": _vm.handRouter
    }
  }, [_c('img', {
    attrs: {
      "src": "/static/bi.png"
    }
  }), _vm._v(" 重新修改\n    ")]) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "u-area"
  }, [_c('textarea', {
    directives: [{
      name: "model",
      rawName: "v-model.lazy",
      value: (_vm.quesTxt),
      expression: "quesTxt",
      modifiers: {
        "lazy": true
      }
    }],
    attrs: {
      "placeholder-style": "color:#a69ee3;font-size:30rpx;",
      "disabled": "true",
      "placeholder": "题目字数在30个字以内",
      "maxlength": "30",
      "eventid": '1'
    },
    domProps: {
      "value": (_vm.quesTxt)
    },
    on: {
      "change": function($event) {
        _vm.quesTxt = $event.target.value
      }
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-selct u-yes"
  }, [_c('img', {
    attrs: {
      "src": "/static/rank/yes.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('input', {
    directives: [{
      name: "model",
      rawName: "v-model.lazy",
      value: (_vm.op1),
      expression: "op1",
      modifiers: {
        "lazy": true
      }
    }],
    attrs: {
      "type": "text",
      "maxlength": "10",
      "placeholder-style": "color:#fff;font-size:28rpx;",
      "placeholder": "请输入正确选项",
      "eventid": '2'
    },
    domProps: {
      "value": (_vm.op1)
    },
    on: {
      "change": function($event) {
        _vm.op1 = $event.target.value
      }
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-selct"
  }, [_c('img', {
    attrs: {
      "src": "/static/rank/error.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('input', {
    directives: [{
      name: "model",
      rawName: "v-model.lazy",
      value: (_vm.op2),
      expression: "op2",
      modifiers: {
        "lazy": true
      }
    }],
    attrs: {
      "type": "text",
      "maxlength": "10",
      "placeholder-style": "color:#fff;font-size:28rpx;",
      "placeholder": "请输入错误选项",
      "eventid": '3'
    },
    domProps: {
      "value": (_vm.op2)
    },
    on: {
      "change": function($event) {
        _vm.op2 = $event.target.value
      }
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-selct"
  }, [_c('img', {
    attrs: {
      "src": "/static/rank/error.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('input', {
    directives: [{
      name: "model",
      rawName: "v-model.lazy",
      value: (_vm.op3),
      expression: "op3",
      modifiers: {
        "lazy": true
      }
    }],
    attrs: {
      "type": "text",
      "maxlength": "10",
      "placeholder-style": "color:#fff;font-size:28rpx;",
      "placeholder": "请输入错误选项",
      "eventid": '4'
    },
    domProps: {
      "value": (_vm.op3)
    },
    on: {
      "change": function($event) {
        _vm.op3 = $event.target.value
      }
    }
  })]), _vm._v(" "), _c('div', {
    staticClass: "u-selct mb50"
  }, [_c('img', {
    attrs: {
      "src": "/static/rank/error.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('input', {
    directives: [{
      name: "model",
      rawName: "v-model.lazy",
      value: (_vm.op4),
      expression: "op4",
      modifiers: {
        "lazy": true
      }
    }],
    attrs: {
      "type": "text",
      "maxlength": "10",
      "placeholder-style": "color:#fff;font-size:28rpx;",
      "placeholder": "请输入错误选项",
      "eventid": '5'
    },
    domProps: {
      "value": (_vm.op4)
    },
    on: {
      "change": function($event) {
        _vm.op4 = $event.target.value
      }
    }
  })]), _vm._v(" "), _vm._m(0), _vm._v(" "), _c('div', {
    staticClass: "u-desc pl30"
  }, [_vm._v("\n    " + _vm._s(_vm.descTxt) + "\n    "), _c('div', {
    staticClass: "u-info"
  }, [_vm._v("\n      ——\n      "), (_vm.createInfo.img) ? _c('img', {
    attrs: {
      "src": _vm.createInfo.img
    }
  }) : _c('img', {
    attrs: {
      "src": "/static/default-head.png"
    }
  }), _vm._v(" \n      " + _vm._s(_vm.createInfo.name) + "\n    ")])]), _vm._v(" "), (_vm.subjectStatus == 2) ? _c('div', {
    staticClass: "fail-desc"
  }, [_vm._v("\n    " + _vm._s(_vm.rejectRemark || "驳回理由:不符合规定") + "\n  ")]) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "u-mask"
  })])
}
var staticRenderFns = [function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "u-hr"
  }, [_c('div', {
    staticClass: "u-hr-txt"
  }, [_vm._v("本题解析")])])
}]
render._withStripped = true
var esExports = { render: render, staticRenderFns: staticRenderFns }
/* harmony default export */ __webpack_exports__["a"] = (esExports);
if (false) {
  module.hot.accept()
  if (module.hot.data) {
     require("vue-hot-reload-api").rerender("data-v-404e3506", esExports)
  }
}

/***/ })

},[351]);
//# sourceMappingURL=main.js.map