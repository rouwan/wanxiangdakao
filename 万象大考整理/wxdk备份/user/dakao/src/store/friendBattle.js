//好友对战 store
import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
import { getStorage,setStorage} from '@/utils/index.js'
const store = {
  namespaced: true,
  state: {
    roomId:0,
    roomStatus:0,//0有效 1失效
  },
  mutations: {
    setRoomId(state,roomId){//换成对象形式
      state.roomId = roomId;
    },
    setRoomStatus(state,status){
      state.roomStatus = status;
    }
  }
}
//获取本地缓存
getStorage(store.state,"roomId",0);
export default store
