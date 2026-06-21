global.webpackJsonp([19],{

/***/ 166:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(168);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_232958ce_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(189);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(167)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */

/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-232958ce"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_index_vue__["a" /* default */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_f_loat_mpvue_loader_lib_template_compiler_index_id_data_v_232958ce_hasScoped_true_transformToRequire_video_src_source_src_img_src_image_xlink_href_node_modules_f_loat_mpvue_loader_lib_selector_type_template_index_0_index_vue__["a" /* default */],
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\pages\\newYear\\index.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}
if (Component.options.functional) {console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-232958ce", Component.options)
  } else {
    hotAPI.reload("data-v-232958ce", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 167:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 168:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_array_from__ = __webpack_require__(169);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_array_from___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_array_from__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_set__ = __webpack_require__(173);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_set___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_set__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_vuex__ = __webpack_require__(1);



//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
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
      title: "万象新年专题挑战开始啦",
      path: "/pages/entry/index?userId=" + this.appInfo.userId
    };
  },
  onUnload: function onUnload() {
    this.isPass = false;
  },
  data: function data() {
    return {
      showModel: false,
      pageData: null,
      imgHead: "https://ggbsq.xingpanwang.com/",
      selectList: [],
      answerList: ["", ""],
      myAnswer: "", //用来监听答案正确与否,
      answerComplete: false,
      isPass: false,
      touchContext: null
    };
  },

  watch: {
    myAnswer: function myAnswer(value) {
      var _this = this;

      var reg = /^(?=.*\d.*\b)/;
      this.answerComplete = reg.test(value); //不包含数字 则回答完整
      if (!this.answerComplete) {
        if (value == this.pageData.answer) {
          console.log("回答正确");
          this.fly.post("/specialSubject/submitSubject", {
            subjectId: this.pageData.id,
            answer: value
          }).then(function (res) {
            wx.setStorage({
              key: "newFoodCard",
              data: _this.pageData.cardNumber
            });
            console.log(res);
            if (res.data.Tag == 1) {
              _this.isPass = false;
              //  回答正确
              wx.showModal({
                title: _this.pageData.answer,
                content: "\u3010\u89E3\u91CA\u3011" + _this.pageData.description + "\n\n\n\n",
                showCancel: false,
                success: function success() {
                  _this.getPageInit();
                }
              });
            } else if (res.data.Tag == 2) {
              _this.isPass = true;
              wx.showModal({
                title: _this.pageData.answer,
                content: "\u3010\u89E3\u91CA\u3011" + _this.pageData.description + "\n\n\n",
                showCancel: false,
                success: function success() {
                  wx.showToast({ title: "通关奖励已发放至邮箱", icon: "none" });
                }
              });
            }
          });
        } else {
          wx.showToast({
            title: "回答错误",
            icon: "none",
            duration: 500
          });
        }
      }
    }
  },
  computed: __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_3_vuex__["c" /* mapState */])(["appInfo", "IMGHEAD"])),
  methods: __WEBPACK_IMPORTED_MODULE_2_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_3_vuex__["b" /* mapMutations */])(["setAppInfo"]), {
    back: function back() {
      this.$router.back();
    },
    getPageInit: function getPageInit() {
      var _this2 = this;

      this.fly.post("/specialSubject/getSubject").then(function (res) {
        if (res.data.Tag == 2) {
          _this2.isPass = true;
          return false;
        }
        _this2.pageData = res.data.Result;
        _this2.myAnswer = "";
        _this2.answerList = [];
        _this2.initSelect();
        // 根据答案长度初始化 答案框 和 我的预设答案
        for (var i = 0; i < res.data.Result.answer.length; i++) {
          _this2.answerList[i] = {
            word: "",
            fromIndex: ""
          };
          _this2.myAnswer = _this2.myAnswer + i;
        }
      });
    },
    strUnique: function strUnique(str) {
      //字符串去重
      var se = new __WEBPACK_IMPORTED_MODULE_1_babel_runtime_core_js_set___default.a();
      str = str.split("");
      for (var i = 0; i < str.length; i++) {
        se.add(str[i]);
      }
      str = __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_array_from___default()(se).join("");
      return str;
    },
    randomSort: function randomSort(a) {
      //数组随机
      var arr = a,
          random = [],
          len = arr.length;
      for (var i = 0; i < len; i++) {
        var index = Math.floor(Math.random() * (len - i));
        random.push(a[index]);
        arr.splice(index, 1);
      }
      return random;
    },
    initSelect: function initSelect() {
      var _this3 = this;

      //初始化选择列表
      var str = "",
          len = 0,
          list = [],
          successIndex = void 0,
          //正确选项下标
      random = void 0;
      this.selectList = [];
      this.pageData.optionList.forEach(function (item, index) {
        // str += item.name;
        // len = str.length;
        if (item.sortId == _this3.pageData.optionSortId) {
          successIndex = index;
        }
      });

      //超过21个字
      // if (len > 21) {
      str = "";
      this.pageData.optionList.forEach(function (item, index) {
        if (successIndex != index) {
          str += item.name;
          len = str.length;
        }
      });
      console.log(str);
      // }
      // if (len < 21) {
      var newStr = "";
      str = this.strUnique(str, len);
      console.log(str);
      str += this.pageData.optionList[successIndex].name;
      console.log(str);

      str = str.replace(/(^\s*)|(\s*$)/g, "");
      len = str.length;
      if (len > 12) {
        str = str.substring(len - 12);
      }
      if (len < 12) {
        newStr = this.pageData.randomStr.substring(0, 12 - len);
      }
      str += newStr;
      len = str.length;
      // }
      list = str.split("");
      //---star重新排列字符串
      // list.sort();
      list = this.randomSort(list);
      // ---end 排列
      list.forEach(function (item, index) {
        var obj = {
          id: index,
          word: item,
          show: true,
          select: false
        };
        _this3.selectList.push(obj);
      });
    },
    addWord: function addWord(sItem, index) {
      if (this.answerComplete) {
        sItem.show = false;
        var min = this.getMinNullIndex();
        this.answerList[min] = {
          word: sItem.word,
          fromIndex: index
        };
        this.myAnswer = this.myAnswer.replace(min, sItem.word);
      }
    },
    removeWord: function removeWord(aItem, index) {
      if (aItem.word != "") {
        // 修改相同下标的答案
        this.myAnswer = this.myAnswer.split("");
        this.myAnswer[index] = index;
        this.myAnswer = this.myAnswer.join("");
        aItem.word = "";
        this.selectList[aItem.fromIndex].show = true;
      }
    },
    getMinNullIndex: function getMinNullIndex() {
      for (var i = 0; i < this.answerList.length; i++) {
        if (this.answerList[i].word == "") {
          return i;
        }
      }
    },
    hint: function hint() {
      // 回答完整
      if (this.answerComplete) {
        // 先提示 后请求 增加体验
        if (this.appInfo.money >= 10) {
          this.setAppInfo({ money: this.appInfo.money - 10 });
          var min = this.getMinNullIndex();
          var word = this.pageData.answer.slice(min, min + 1);
          var fromIndex = void 0;
          this.selectList.forEach(function (sel, index) {
            if (sel.word == word) {
              sel.show = false;
              fromIndex = index;
            }
          });
          this.answerList[min] = {
            word: word,
            fromIndex: fromIndex
          };
          this.myAnswer = this.myAnswer.replace(min, word);
          this.fly.post("/specialSubject/promptSubject", {
            subjectId: this.pageData.id
          }).then(function (res) {
            if (res.data.Tag == 1) {
              console.log("提示成功");
            }
          });
        } else {
          wx.showToast({ title: "金币不足", icon: "none" });
        }
      }
    }
  }),
  mounted: function mounted() {
    this.getPageInit();
  }
});

/***/ }),

/***/ 189:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
var render = function () {var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "g-wrap"
  }, [_c('div', {
    staticClass: "inner-wrap"
  }, [(_vm.pageData) ? _c('div', {
    staticClass: "gold"
  }, [_c('img', {
    attrs: {
      "src": "/static/gold.png",
      "alt": ""
    }
  }), _vm._v(_vm._s(_vm.appInfo.money) + "\n    ")]) : _vm._e(), _vm._v(" "), (_vm.pageData && !_vm.isPass) ? _c('div', {
    staticClass: "content"
  }, [_c('div', {
    staticClass: "current-level"
  }, [_vm._v("第" + _vm._s(_vm.pageData.cardNumber) + "关")]), _vm._v(" "), (_vm.pageData.modeType == 1) ? _c('div', {
    staticClass: "image-box"
  }, [_c('img', {
    attrs: {
      "mode": "aspectFit",
      "src": _vm.IMGHEAD + _vm.pageData.image,
      "alt": ""
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "image-title"
  }, [_vm._v(_vm._s(_vm.pageData.title))])]) : _vm._e(), _vm._v(" "), (_vm.pageData.modeType == 3) ? _c('div', {
    staticClass: "text-mode"
  }, [_vm._v("\n        " + _vm._s(_vm.pageData.title) + "\n      ")]) : _vm._e(), _vm._v(" "), _c('div', {
    staticClass: "anwer"
  }, _vm._l((_vm.answerList), function(aItem, index) {
    return _c('div', {
      key: index,
      staticClass: "text-item",
      attrs: {
        "eventid": '0-' + index
      },
      on: {
        "click": function($event) {
          _vm.removeWord(aItem, index)
        }
      }
    }, [_vm._v(_vm._s(aItem.word))])
  }))]) : _vm._e(), _vm._v(" "), (!_vm.isPass) ? _c('div', {
    staticClass: "select-area"
  }, [_c('div', {
    staticClass: "select-list"
  }, _vm._l((_vm.selectList), function(sItem, index) {
    return _c('div', {
      key: index,
      staticClass: "select-item",
      class: {
        'hideItem': !sItem.show
      },
      attrs: {
        "eventid": '1-' + index
      },
      on: {
        "click": function($event) {
          _vm.addWord(sItem, index)
        }
      }
    }, [_vm._v(_vm._s(sItem.word))])
  })), _vm._v(" "), _c('div', {
    staticClass: "btn-list"
  }, [_c('img', {
    staticClass: "hint",
    attrs: {
      "src": "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/newYear/promt.png",
      "alt": "",
      "eventid": '2'
    },
    on: {
      "click": _vm.hint
    }
  }), _vm._v(" "), _c('button', {
    attrs: {
      "open-type": "share"
    }
  }, [_c('img', {
    attrs: {
      "src": "/static/btn-help.png",
      "alt": ""
    }
  })])], 1)]) : _vm._e(), _vm._v(" "), (_vm.isPass) ? _c('div', {
    staticClass: "model-box pass-model"
  }, [_c('div', {
    staticClass: "mask"
  }), _vm._v(" "), _c('div', {
    staticClass: "model"
  }, [_c('img', {
    staticClass: "title",
    attrs: {
      "src": "/static/pass-model-head.png",
      "alt": ""
    }
  }), _vm._v(" "), _c('div', {
    staticClass: "text"
  }, [_vm._v("恭喜你通关了!")]), _vm._v(" "), _c('div', {
    staticClass: "model-btn"
  }, [_c('div', {
    staticClass: "btn-yellow help",
    attrs: {
      "eventid": '3'
    },
    on: {
      "click": _vm.back
    }
  }, [_vm._v("返回")])])])]) : _vm._e()]), _vm._v(" "), _c('div', {
    staticClass: "ad-box"
  }, [_c('ad', {
    attrs: {
      "unit-id": "adunit-b4aadf3766ad114d",
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
     require("vue-hot-reload-api").rerender("data-v-232958ce", esExports)
  }
}

/***/ })

},[333]);
//# sourceMappingURL=main.js.map