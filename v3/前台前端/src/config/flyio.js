import Fly from 'flyio/dist/npm/wx'
import store from '@/store'
export var fly = new Fly;

/* 切换开发环境 dev 开发，Production 正式环境 */
store.state.EVN = "Dev";
// fly 基础配置
fly.config.headers['Content-Type'] = 'application/x-www-form-urlencoded';//定义公共headers
fly.config.parseJson = false;//设置超时
fly.config.withCredentials =true;//允许跨域
//设置请求基地址
if (store.state.EVN == "Dev") {
  fly.config.baseURL = "http://localhost:8008/vientianetest/mobile/" // 本地后端
} else if (store.state.EVN == "Production") {
  fly.config.baseURL = "https://ggbsq.xingpanwang.com/vientianetest/mobile/" // 测试外网
}

// 设置拦截器方法 -----------------------------------------------
let rTime = 0;//重新请求次数 限制为三次
// 请求拦截器
export function flyRequest() {
  fly.interceptors.request.use(request => {
    if (request.url != "/login") {
      request.headers["Cookie"] = "JSESSIONID=" + store.state.SESSIONID;//服务端会话id,解决后端session问题
    }
  },err=>{
    console.log("请求报错",err)
  })
}
//响应拦截器
export function flyResponse() {
  fly.interceptors.response.use(response => {
    // -8 授权过期 重新登录（开发模式跳过，允许未登录浏览）
    if (response.data.Tag == -8 && rTime < 3) {
      rTime++;
      // Dev mode: skip redirect, keep response flowing
      if (store.state.EVN === 'Dev') {
        console.log('[DEV] 授权过期跳过，继续浏览');
        return response;
      }
      wx.showToast({
        title: "授权过期,请重新尝试",
        icon: "none"
      })
      setTimeout(() => {
        wx.reLaunch({
          url: '/pages/entry/index'
        })
      }, 500);
    }
    return response;
  },
  err=>{
    // wx.showToast({
    //   title:'网络不流畅，请稍后再试！',
    //   icon:'none',
    // });
  })
}


