import Fly from 'flyio/dist/npm/wx'
import store from '@/store'
export var fly = new Fly;

/* 切换开发环境 dev 开发，Production 正式环境 */
store.state.EVN = "Production"; 
// fly 基础配置
fly.config.headers['Content-Type'] = 'application/x-www-form-urlencoded';//定义公共headers
fly.config.parseJson = false;//设置超时
fly.config.withCredentials =true;//允许跨域
//设置请求基地址
if (store.state.EVN == "Dev") {
  fly.config.baseURL = "http://192.168.0.14:8009/vientianetest/mobile/" // 开发调试  
} else if (store.state.EVN == "Production") {
  fly.config.baseURL = "https://ggbsq.xingpanwang.com/vientianetest/mobile/" // 测试外网
  // fly.config.baseURL = "http://127.0.0.1:8008/vientianetest/mobile/" // 测试外网
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
    // -8 授权过期 重新登录
    if (response.data.Tag == -8 && rTime < 3) {
      rTime++;
      // 重新登录
      wx.showToast({
        title: "授权过期,请重新尝试",
        icon: "none"
      })
       reLogin();
      setTimeout(() => {
        wx.reLaunch({
          url: '/pages/entry/index'
        })
      }, 500);
    }
  },
  err=>{
    // wx.showToast({
    //   title:'网络不流畅，请稍后再试！',
    //   icon:'none',
    // });
  })
}


