//websocket store
import Vue from 'vue'
import Vuex from 'vuex'
import store from '@/store'
Vue.use(Vuex)
/* 主要使用方式  --各个页面将在socket生命周期中使用的方法 在页面中调用 对应addListener函数 相当于把方法注册到全局的监听器 */
const state = {
  socket: null,
  connected: false,
  messageBus: {},
  openedListeners: [],//socket 打开执行队列
  closedListeners: [],//socket 关闭执行队列
  messageListeners: [],//socket 接收到消息 执行度列
  beforeConectMessage: [],//发送失败的消息队列，socket 连接上以后重新发送
  heart: null,
  delayHeart: null,
  delayTime: 0,
  timeOut: 12000,
  pageType: 1,//页面标识，0首页，大厅 1 ，房间 2,3错题回顾
}


const _setSocket = (socket) => {
  state.socket = socket
}

const _addListener = (listenerArr, listener) => {
  var exist = false
  for (let l in listenerArr) {
    if (l === listener) {
      exist = true
      break;
    }
  }
  if (!exist) {
    listenerArr.push(listener)
  }
}

const _removeListener = (listenerArr, listener) => {
  let index = -1
  for (let i = 0; i < listenerArr.length; i++) {
    if (listenerArr[i] === listener) {
      index = i
      break
    }
  }
  if (index !== -1) {
    listenerArr.splice(index, 1)
  }
}

const _addOpenedListener = (listener) => {
  _addListener(state.openedListeners, listener)
}

const _addClosedListener = (listener) => {
  _addListener(state.closedListeners, listener)
}

const _addMessageListener = (listener) => {
  _addListener(state.messageListeners, listener)
}

const startHeart = () => {
  state.heart = setInterval(() => {
    mutations.send(state, `{'type':0,'data':0}`);
    startCountDonw();
  }, state.timeOut)
}

const stopHeart = () => {
  clearInterval(state.heart);
  state.heart = null;
  stopCountDown();
}

const startCountDonw = () => {
  state.delayHeart = setInterval(() => {
      state.delayTime++;
      console.log(state.delayHeart, "单次心跳延迟计时", state.delayTime, "s");
      if (state.delayTime >= 10 && state.delayHeart != null) {
        stopHeart();
        mutations.send(state, `{'type':5,'data':0}`);
        mutations.close(state);
        // wx.showModal({
        //   title: "系统提示", content: "网络状态较差", showCancel: false, success: res => {
        //     if (res.confirm) {
        //       wx.reLaunch({
        //         url: '/pages/entry/index'
        //       })
        //     }
        //   }
        // });
      }
  }, 1000)
}

const stopCountDown = () => {
  clearInterval(state.delayHeart);
  state.delayHeart = null;
  state.delayTime = 0;
}

// 网络断开处理 和 心跳延迟分开处理。
const networkChange = () => {
  return wx.onNetworkStatusChange((res) => {
    if (!res.isConnected) {
      stopHeart();
      wx.showModal({
        title: "系统提示", content: "网络中断", showCancel: false, success: res => {
          if (res.confirm) {
            wx.reLaunch({
              url: '/pages/entry/index'
            })
          }
        }
      })
    }
  })
}

const mutations = {

  connect(state, { type }) { //type 判断那个页面链接
    return new Promise((resolve, reject) => {
      if (state.connected) {
        console.log('websocket已连接')
        resolve()
      }
      if (!state.socket) {
        let url = "";
        if (store.state.EVN == "Dev") {
          url = `ws://127.0.0.1:8008/vientianetest/wsRoom?code=${store.state.secretCode}&type=${type}&userId=${store.state.appInfo.userId}`;
        } else {
          url = `wss://ggbsq.xingpanwang.com/vientianetest/wsRoom?code=${store.state.secretCode}&type=${type}&userId=${store.state.appInfo.userId}`;
        }
        state.socket = wx.connectSocket({
          url: url,
          header: {
            "content-type": "application/json"
          },
          method: "GET"
        });
      }
      state.socket.onOpen(() => {
        state.connected = true
        console.log('websocket连接成功')
        startHeart();
        networkChange();
      
        for (let i = 0; i < state.openedListeners.length; i++) {
          state.openedListeners[i]()
        }
        // for (let i=0;i< state.beforeConectMessage.length;i++){
        //   mutations.send(state,state.beforeConectMessage[i]);
        // }
        resolve()
      })
      state.socket.onMessage((msg) => {
        try {
          // console.log('收到消息：',msg.data.Type);
          var message = JSON.parse(msg.data) || {};
          for (let i = 0; i < state.messageListeners.length; i++) {
            state.messageListeners[i](message)
          }
          if (message.Type === 0) {
            console.log("delayHeart", state.delayHeart, "clear");
            stopCountDown();
          }
        } catch (e) {
          console.log(e)
        }
      })
      state.socket.onClose(res => {
        stopHeart();
        console.log('websocket断开', res);
        if (state.pageType == 1 || state.pageType == 2||state.pageType==3) {
          wx.showModal({
            title: "系统提示", content: "网络状态较差", showCancel: false, success: res => {
              if (res.confirm) {
                wx.reLaunch({
                  url: '/pages/entry/index'
                })
              }
            }
          })
        }
  
        state.socket = null;
        state.connected = false;
        state.beforeConectMessage = [];
        for (let listener in state.closedListeners) {
          if (typeof listener === 'function') {
            listener()
          }
        }
      })
 
    })
  },
  send(state, msg) {
    //发送消息

    if (state.connected) {
      return new Promise((resolve, reject) => {
        state.socket.send({
          data: msg,
          success: res => {
            console.log(msg);
            resolve();
          },
          fail: res => {
            console.log("发送失败" + msg);
          }
        });
      });
    } else {
      state.beforeConectMessage.push(msg);
      // if(msg.split("")[8]=="0"&&state.pageType!=0){
      //   console.log("停止心跳")
      //   wx.showModal({title:"系统提示",content:"网络状态较差",showCancel:false,success:res=>{
      //     if(res.confirm){
      //       wx.reLaunch({
      //         url: '/pages/entry/index'
      //       })
      //     }
      //   }});
      // }
      console.log("socket is closed from:", msg);
    }
  },
  addOpenedListener(state, listener) {
    _addOpenedListener(listener)
  },
  addClosedListener(state, listener) {
    _addClosedListener(listener)
  },
  addMessageListener(state, listener) {
    _addMessageListener(listener)
  },
  removeOpenedListener(state, listener) {
    _removeListener(state.openedListeners, listener)
  },
  removeClosedListener(state, listener) {
    _removeListener(state.closedListeners, listener)
  },
  removeMessageListener(state, listener) {
    _removeListener(state.messageListeners, listener)
  },

  close(state) {
    if (state.socket) {
      try {
        state.socket.close()
        _setSocket(null)
      } catch (e) {
        console.log(e)
      }
    }
  },
  setMessageBus(state, data) {
    state.messageBus = data;
  },
  setPageType(state, type) {
    state.pageType = type;
  }
}







const _store = {
  namespaced: true,
  state: state,
  mutations: mutations,
}
//获取本地缓存
export default _store
