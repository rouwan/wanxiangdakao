global.webpackJsonp([9],{

/***/ 314:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_addSubject_vue__ = __webpack_require__(316);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_33cc6b8b_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_addSubject_vue__ = __webpack_require__(317);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(315)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-33cc6b8b"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_addSubject_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_33cc6b8b_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_addSubject_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\subjectFactoryPages\\pages\\addSubject.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] addSubject.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-33cc6b8b", Component.options)
  } else {
    hotAPI.reload("data-v-33cc6b8b", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 315:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 316:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__api_subjectApi__ = __webpack_require__(38);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__utils_index__ = __webpack_require__(9);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
      parentType: 1,
      quesTxt: "",
      descTxt: "",
      op1: "",
      op2: "",
      op3: "",
      op4: "",
      wordList: {
        0: { type: "未知", desc: "未知" },
        1: { type: "未知", desc: "未知" },
        2: { type: "商科", desc: "经济金融" },
        3: { type: "文科", desc: "人文历史" },
        4: { type: "理科", desc: "自然科学" },
        5: { type: "艺科", desc: "艺术文化" },
        6: { type: "体科", desc: "健康常识" },
        7: { type: "综合", desc: "综合学识" }
      }
    };
  },

  methods: {
    handleAddSubject: function handleAddSubject() {
      var _this = this;

      console.log("提交1");
      if (this.quesTxt == "") {
        wx.showToast({ title: "题目不能为空", icon: "none" });
      } else if (this.op1 == "" || this.op2 == "" || this.op3 == "" || this.op4 == "") {
        wx.showToast({ title: "选项不能为空", icon: "none" });
      } else if (this.descTxt == "") {
        wx.showToast({ title: "解析不能为空", icon: "none" });
      } else {
        var _option = this.op1 + "%#@" + this.op2 + "%#@" + this.op3 + "%#@" + this.op4;
        console.log("提交2");
        __WEBPACK_IMPORTED_MODULE_0__api_subjectApi__["a" /* default */].updateSubject({
          id: this.subjectId,
          parentType: this.parentType,
          answer: 1,
          title: this.quesTxt,
          description: this.descTxt,
          option: _option
        }).then(function (res) {
          if (res.Tag == 1) {
            wx.showToast({ title: "提交成功", icon: "none" });
            _this.clearData();
            setTimeout(function () {
              _this.$router.push('/subjectFactoryPages/pages/mySubjectList');
            }, 1500);
          } else {
            wx.showToast({ title: res.Message, icon: "none", duration: 3000 });
          }
        });
      }
    },
    clearData: function clearData() {
      this.quesTxt = "";
      this.op1 = "";
      this.op2 = "";
      this.op3 = "";
      this.op4 = "";
      this.descTxt = "";
    },
    getStorage: function getStorage() {
      var _this2 = this;

      var key = "parentType" + this.parentType;
      wx.getStorage({
        key: key,
        success: function success(res) {
          _this2.quesTxt = res.data.quesTxt;
          _this2.op1 = res.data.op1;
          _this2.op2 = res.data.op2;
          _this2.op3 = res.data.op3;
          _this2.op4 = res.data.op4;
          _this2.descTxt = res.data.descTxt;
        }
      });
    },
    editSubject: function editSubject() {
      var _this3 = this;

      //更新题目
      __WEBPACK_IMPORTED_MODULE_0__api_subjectApi__["a" /* default */].getSubjectById({ id: this.subjectId }).then(function (res) {
        _this3.quesTxt = res.Result.title;
        _this3.descTxt = res.Result.description;
        _this3.parentType = res.Result.parentType;
        _this3.op1 = res.Result.optionList[0].name;
        _this3.op2 = res.Result.optionList[1].name;
        _this3.op3 = res.Result.optionList[2].name;
        _this3.op4 = res.Result.optionList[3].name;
      });
    }
  },

  onShow: function onShow() {
    this.parentType = this.$root.$mp.query.parentType || 1;
    this.subjectId = this.$root.$mp.query.subjectId || -1;
    if (this.subjectId == -1) {
      this.getStorage();
    } else {
      this.editSubject();
    }
  },
  onUnload: function onUnload() {
    var key = "parentType" + this.parentType;
    Object(__WEBPACK_IMPORTED_MODULE_1__utils_index__["e" /* setStorage */])(key, {
      quesTxt: this.quesTxt,
      descTxt: this.descTxt,
      op1: this.op1,
      op2: this.op2,
      op3: this.op3,
      op4: this.op4
    });
  }
});

/***/ }),

/***/ 317:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "m-edit-subject sky-bg-2"
  }, [_c('div', {
    staticClass: "u-head"
  }, [_c('div', {
    staticClass: "u-type"
  }, [_vm._v(_vm._s(_vm.wordList[_vm.parentType].type))]), _vm._v(_vm._s(_vm.wordList[_vm.parentType].desc))]), _vm._v(" "), _c('div', {
    staticClass: "u-area"
  }, [_c('textarea', {
    directives: [{
      name: "model",
      rawName: "v-model",
      value: (_vm.quesTxt),
      expression: "quesTxt"
    }],
    attrs: {
      "placeholder-style": "color:#a69ee3;font-size:30rpx;",
      "placeholder": "题目字数在40个字以内",
      "maxlength": "40",
      "eventid": '0'
    },
    domProps: {
      "value": (_vm.quesTxt)
    },
    on: {
      "input": function($event) {
        if ($event.target.composing) { return; }
        _vm.quesTxt = $event.target.value
      }
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-word-tip"
  }, [_vm._v(_vm._s(_vm.quesTxt.length || 0) + "/40")])]), _vm._v(" "), _c('div', {
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
      "eventid": '1'
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
      "eventid": '2'
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
      "eventid": '3'
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
      "eventid": '4'
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
    staticClass: "u-area"
  }, [_c('textarea', {
    directives: [{
      name: "model",
      rawName: "v-model",
      value: (_vm.descTxt),
      expression: "descTxt"
    }],
    attrs: {
      "placeholder-style": "color:#a69ee3;font-size:30rpx;",
      "placeholder": "解析字数在500个字以内",
      "maxlength": "500",
      "eventid": '5'
    },
    domProps: {
      "value": (_vm.descTxt)
    },
    on: {
      "input": function($event) {
        if ($event.target.composing) { return; }
        _vm.descTxt = $event.target.value
      }
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "u-word-tip"
  }, [_vm._v(_vm._s(_vm.descTxt.length || 0) + "/500")])]), _vm._v(" "), _c('div', {
    staticClass: "u-submit",
    attrs: {
      "eventid": '6'
    },
    on: {
      "click": _vm.handleAddSubject
    }
  }, [_vm._v("提交题目")])])
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
     require("vue-hot-reload-api").rerender("data-v-33cc6b8b", esExports)
  }
}

/***/ })

},[349]);
//# sourceMappingURL=main.js.map