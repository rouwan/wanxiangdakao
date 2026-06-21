// import '../static/sdk/ald-stat'
import Vue from 'vue'
import App from '@/App'
import emailModal from '@/components/modal/emailModal.vue'
import store from '@/store/index.js'
import router from 'mpvue-router-patch'
import {fly,flyRequest,flyResponse} from '@/config/flyio' //flyio 请求插件.

let log={};
Vue.prototype.fly = fly;//vue 原型挂载属性
Vue.prototype.store = store;//vue 原型挂载属性

Vue.prototype.$log = (desc,data)=>{  //全局日志方法
  data = JSON.stringify(data);
  let content = desc+data;
  console.log(content);
  Vue.prototype.fly.post("/user/writeLog",{
    content:content
  })
};
Vue.use(router)//路由
Vue.config.productionTip = false
App.store = store
App.mpType = 'app'

flyRequest(); //请求拦截
flyResponse();//响应拦截
const app = new Vue(App)
app.$mount()

let count=0;
let timer=null;
function getAdList(){
    Vue.prototype.fly.post("/getToXcxList").then(res=>{
      console.log(res)
    })
    count++;
}
 



export default {
  // 这个字段走 app.json
  config: {
    pages: [],
    window: {
      backgroundTextStyle: 'light',
      navigationBarBackgroundColor: '#000',
      navigationBarTitleText: '万象大考',
      navigationBarTextStyle: 'white',
      backgroundColor:'#28235d',
    },
    navigateToMiniProgramAppIdList: [
      "wx4aa94d3af51b01ce",//汽车冲鸭
      "wxea56b2d76c30cd2c",//还钱花
      "wx6b751ee0ef7702fa",//妖精
      "wx08a22523d259abdd",//欢乐小黄胖
      "wxe4f198bc849bfae1",//集步换礼
      "wxded312966e93a87e",//弹射奇兵
      "wxa059f0becd1b47d4",//生肖来了
      "wxca338da970e994ba",//豆小游
      "wx6abcab3f1b8c0a1d",//碰碰车
    ]
  }
}
