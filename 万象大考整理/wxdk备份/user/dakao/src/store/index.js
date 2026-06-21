// https://vuex.vuejs.org/zh-cn/intro.html
// make sure to call Vue.use(Vuex) if using a module system
import Vue from 'vue'
import Vuex from 'vuex'
import friendBattleStroe from './friendBattle'
import webSocket from './webSocket'
Vue.use(Vuex)

const store = new Vuex.Store({
  namespaced:true,
  state: {
    HUAWEI:false,
    appInfo:{},//版本信息 + 用户id
    isAuthorize:false,
    musicState:true,//全局音乐状态
    globalBgPlayer:null,
    globalSoundPlayer:null,
    bgStatus:"",
    IMGHEAD:"",
    gamePageType:0,//默认  1 天蓝
    // IMGHEAD: "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource",//图片地址前缀
    // // IMGHEAD: "http://192.168.2.191:8012",//图片地址前缀
    SESSIONID: "",//登凭证
    secretCode:"",
    openId:0,//socketId
    SCENE: 0,//场景值
    userInfo: {},//用户信息
    deviceInfo: {},//设备信息,
    socketState:0,//1连接，0关闭
    userOneState:false, //用户A 答题状态
    userTwoState:false,// 用户B 答题状态
    cw:0,//我的连胜场次
    gameRecord:{},//对局记录，回顾用
    // 排位
    isRank:false,
    rankIndex:0,
    dayCount:0,
    isDayChallenge:0,
    firstLaunch:true,//第一次进入首页
    // 体力 
    goodStorageList:[],
    strengthCount:0,
    maxStrength:30,
    //邮件
    emailList:[],
    // 转盘
    lotteryList:[],
    // 首页配置
    hasTipMenu:true,
    hasTipAd:true,
    hasTipLottery:true,
    hasTipBook:0,
    isUpdateUserInfo:false,//今日是否更新过用户信息
    globalStatus:{},
    messageListStorage:[],
    tryGameName:'',
    gameResult:"balance",
  },
  mutations: {
    setGameResult(state,type){
      state.gameResult=type;
    },
    setMessageListStorage(state,list){
      state.messageListStorage=list;
    },
    setGlobalStatus(state,value){
      state.globalStatus = value;
    },
    setIsUpdateUserInfo(state,type){
      state.isUpdateUserInfo = type;
    },
    setHuawei(state,type){
      state.HUAWEI = type;
    },
    setHasTipBook(state,type){
      state.hasTipBook = type;
    },
    setHasTipLottery(state,type){
      state.hasTipLottery = type;
    },
    setHasTipMenu(state,type){
      state.hasTipMenu = type;
    },
    setHasTipAd(state,type){
      state.hasTipAd = type;
    },
    setLotteryList(state,list){
      state.lotteryList = list;
    },
    setGamePageType(state,type){
      state.gamePageType = type;
    },
    setFirstLaunch(state,value){
      state.firstLaunch = value;
    },
    setOpenId(state,openId){
      state.openId = openId;
    },
    setIsDayChallenge(state,boolean){
        state.isDayChallenge = boolean;
    },
    setDayCount(state,count){
      state.dayCount = count;
    },
    setGlobalBgPlayer(state,player){ //背景乐
      state.globalBgPlayer = player;
    },
    setGlobalSoundPlayer(state,player){ //音效
      state.globalSoundPlayer = player;
    },
    setMusicState(state,type){
      state.musicState = type;
    },
    setIsRank(state,value){
      state.isRank = value;
    },
    setGameRecord(state,record){
      state.gameRecord = record;
    },
    setCw(state,info){
      state.cw = info;
    },
    setUserOneState(state,type){
      state.userOneState = type;
    },
    setUserTwoState(state,type){
      state.userTwoState = type;
    },
    setSocketState(state,boolean){
      state.socketState =boolean;
    },
    setAppInfo(state, obj) {
      state.appInfo=Object.assign(state.appInfo,obj);
    },
    setAppInfoByItem(state,obj){
      for (let item in obj){
        state.appInfo[item] = obj[item];
      }
    },
    setUserInfo(state, userInfo) {
      for (let item in userInfo){
        state.userInfo[item] = userInfo[item];
      }
      // state.userInfo = userInfo;
    },
    setGlobalStatusByItem(state, obj) {
      for (let item in obj){
        state.globalStatus[item] = obj[item];
      }
      // state.userInfo = userInfo;
    },
    setAuthorize(state,boolean){
      state.isAuthorize = boolean;
    },
    setSessionId(state, id) {
      state.SESSIONID = id;
    },
    setSecretCode(state, code) {
      state.secretCode = code;
    },
    
    setDeviceInfo(state, info) {
      state.deviceInfo = info;
    },
    setScene(state, scene) {
      state.SCENE = scene;
    },
    setBgStatus(state,value){
      state.bgStatus = value;
    },
    // 答题信息  后续 拆分
    setRankIndex(state,index){
      state.rankIndex = index;
    },
    setStrengthCount(state,num){
      state.strengthCount = num;
    },
    setGoodStorageList(state,list){
      state.goodStorageList = list;
    },
    setSingleGoodNum(state,good){
      state.goodStorageList
      for(let i=0;i<state.goodStorageList.length;i++){
        if(state.goodStorageList[i].type==good.type){
          if(good.num==0){
            console.log("数量0")
            state.goodStorageList.splice(i,1);
          }else{
            state.goodStorageList[i].num = good.num;
          }
        }
      }
    },
    setEmailList(state,list){
      state.emailList = list;
    },
    setTryGameName(state,name){
      state.tryGameName = name;
    },
  },
  modules:{
    fStore:friendBattleStroe,
    socketStore:webSocket,
  },
})

function checkDay(){  // 昨天 日期等于今天日期 则今天授权过。。
  let yDay=0;
  let today = new Date().getDate();
  wx.getStorage({
    key:"day",
    success:res=>{
      yDay = res.data;
      if(yDay==today){
        store.state.isUpdateUserInfo = true;
      }
    },
    fail:()=>{
      wx.setStorage({
        key:"day",
        data:0,
      })
    }
  })
}
checkDay();
setTimeout(() => {
  if(store.state.EVN=='Dev'){
    // store.state.IMGHEAD = "http://192.168.0.21:8012";
    store.state.IMGHEAD = "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource";
  }else{
    store.state.IMGHEAD = "https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource";
  }
}, 0);
export default store
