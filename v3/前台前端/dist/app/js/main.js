global.webpackJsonp([23],{

/***/ 80:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vue__ = __webpack_require__(4);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_vue__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__App__ = __webpack_require__(83);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__components_modal_emailModal_vue__ = __webpack_require__(6);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__store_index_js__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_mpvue_router_patch__ = __webpack_require__(7);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__config_flyio__ = __webpack_require__(8);

// import '../static/sdk/ald-stat'





 //flyio 请求插件.

var log = {};
__WEBPACK_IMPORTED_MODULE_1_vue___default.a.prototype.fly = __WEBPACK_IMPORTED_MODULE_6__config_flyio__["a" /* fly */]; //vue 原型挂载属性
__WEBPACK_IMPORTED_MODULE_1_vue___default.a.prototype.store = __WEBPACK_IMPORTED_MODULE_4__store_index_js__["a" /* default */]; //vue 原型挂载属性

__WEBPACK_IMPORTED_MODULE_1_vue___default.a.prototype.$log = function (desc, data) {
  //全局日志方法
  data = __WEBPACK_IMPORTED_MODULE_0_babel_runtime_core_js_json_stringify___default()(data);
  var content = desc + data;
  console.log(content);
  __WEBPACK_IMPORTED_MODULE_1_vue___default.a.prototype.fly.post("/user/writeLog", {
    content: content
  });
};
__WEBPACK_IMPORTED_MODULE_1_vue___default.a.use(__WEBPACK_IMPORTED_MODULE_5_mpvue_router_patch__["a" /* default */]); //路由
__WEBPACK_IMPORTED_MODULE_1_vue___default.a.config.productionTip = false;
__WEBPACK_IMPORTED_MODULE_2__App__["a" /* default */].store = __WEBPACK_IMPORTED_MODULE_4__store_index_js__["a" /* default */];
__WEBPACK_IMPORTED_MODULE_2__App__["a" /* default */].mpType = 'app';

Object(__WEBPACK_IMPORTED_MODULE_6__config_flyio__["b" /* flyRequest */])(); //请求拦截
Object(__WEBPACK_IMPORTED_MODULE_6__config_flyio__["c" /* flyResponse */])(); //响应拦截
var app = new __WEBPACK_IMPORTED_MODULE_1_vue___default.a(__WEBPACK_IMPORTED_MODULE_2__App__["a" /* default */]);
app.$mount();

var count = 0;
var timer = null;
function getAdList() {
  __WEBPACK_IMPORTED_MODULE_1_vue___default.a.prototype.fly.post("/getToXcxList").then(function (res) {
    console.log(res);
  });
  count++;
}

/* harmony default export */ __webpack_exports__["default"] = ({
  // 这个字段走 app.json
  config: {
    pages: [],
    window: {
      backgroundTextStyle: 'light',
      navigationBarBackgroundColor: '#000',
      navigationBarTitleText: '万象大考',
      navigationBarTextStyle: 'white',
      backgroundColor: '#28235d'
    },
    navigateToMiniProgramAppIdList: []
  }
});

/***/ }),

/***/ 83:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_App_vue__ = __webpack_require__(85);
var disposed = false
function injectStyle (ssrContext) {
  if (disposed) return
  __webpack_require__(84)
}
var normalizeComponent = __webpack_require__(0)
/* script */

/* template */
var __vue_template__ = null
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = null
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null
var Component = normalizeComponent(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_f_loat_mpvue_loader_lib_selector_type_script_index_0_App_vue__["a" /* default */],
  __vue_template__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "src\\App.vue"
if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key.substr(0, 2) !== "__"})) {console.error("named exports are not supported in *.vue files.")}

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-2aebd89a", Component.options)
  } else {
    hotAPI.reload("data-v-2aebd89a", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["a"] = (Component.exports);


/***/ }),

/***/ 84:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 85:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_vuex__ = __webpack_require__(1);



/* harmony default export */ __webpack_exports__["a"] = ({
  mounted: function mounted() {
    this.updateSet(); //APP 更新通用方法
    this.checkAuthorize(); //检查授权状态
  },
  onShow: function onShow() {
    if (this.bgStatus == 'pause' && this.globalBgPlayer) {
      try {
        this.globalBgPlayer.play();
      } catch (e) {}
    }
    console.log(this.$root.$mp);
  },

  computed: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["c" /* mapState */])(["bgStatus", "globalBgPlayer"])),
  methods: __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_extends___default()({}, Object(__WEBPACK_IMPORTED_MODULE_1_vuex__["b" /* mapMutations */])(["setBgStatus"]), {
    updateSet: function updateSet() {
      //通用app更新 提示 更新
      var updateManager = wx.getUpdateManager();
      updateManager.onCheckForUpdate(function (res) {
        // 请求完新版本信息的回调
      });
      updateManager.onUpdateReady(function () {
        wx.showModal({
          title: "更新提示",
          content: "新版本已经准备好，是否重启应用？",
          success: function success(res) {
            if (res.confirm) {
              // 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
              updateManager.applyUpdate();
            }
          }
        });
      });
      updateManager.onUpdateFailed(function () {
        // 新的版本下载失败
        wx.showModal({
          title: "更新提示",
          content: "新版本下载失败",
          showCancel: false
        });
      });
    },
    checkAuthorize: function checkAuthorize() {
      try {
        var val = wx.getStorageSync('isAuthorize');
        this.store.commit("setAuthorize", val);
      } catch (e) {
        try {
          wx.setStorageSync('isAuthorize', '0');
        } catch (e2) {}
        this.store.commit("setAuthorize", 0);
      }
    }
  })
});

/***/ })

},[80]);
//# sourceMappingURL=main.js.map